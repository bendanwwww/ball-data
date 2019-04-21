package com.ball.data.crawler;

import com.alibaba.fastjson.JSON;
import com.ball.data.dto.NewsDto;
import com.ball.data.mq.SendMessage;
import com.ball.data.service.NewsService;
import com.ball.data.utils.PropertyUtils;
import com.ball.tools.UrlTools;
import com.ball.tools.ValidateTools;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * sohu 爬虫
 */
@Component
public class SohuCrawler implements CrawlerInterface{

    private static final String SOHU_URL = PropertyUtils.getString("sohu_url");

    private static final Logger log = LoggerFactory.getLogger(SohuCrawler.class);


    @Autowired
    private SendMessage sendMessage;
    @Autowired
    private NewsService newsService;


    @Override
    public void getUrl() {
        Document doc;
        try {
            doc = Jsoup.connect(SOHU_URL).get();
            // 新闻li
            Elements newsLi = doc.getElementsByAttributeValue("class", "feed-item");
            for (Element element : newsLi) {
                // 新闻链接
                String newHref = null;
                Elements newsDetails = element.getElementsByTag("article");
                if(ValidateTools.validateListNull(newsDetails)) {
                    Element newsDetail = newsDetails.get(0);
                    Elements newHrefs = newsDetail.getElementsByTag("a");
                    if(ValidateTools.validateListNull(newHrefs)) {
                        newHref = newHrefs.get(0).attr("href");
                        newHref = UrlTools.changeUrl(newHref);
                        log.info("sohu news url: {}", newHref);
                    }
                }
                sendMessage.sendNewsMessage("ball_news", "sohu", newHref);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void getContent(String url) {
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            // 存库实体
            NewsDto news = new NewsDto();
            // 获取新闻类型
            Integer newsType = newsType(url);



            // 获取新闻内容
            // 封面图片
            String coverPic = "";
            // 详情图片
            List<String> newsPicList = new ArrayList<String>();
            // 文字详情
            String content = "";
            // 新闻标题
            String title = "";
            // 图组和新闻分开处理
            switch (newsType) {
                // 新闻
                case 0:
                    // 获取新闻标题
                    Elements newsTitleDiv = doc.getElementsByAttributeValue("class", "text-title");
                    if(ValidateTools.validateListNull(newsTitleDiv)) {
                        Elements newsTitleH1 = newsTitleDiv.get(0).getElementsByTag("h1");
                        if(ValidateTools.validateListNull(newsTitleH1)) {
                            title = newsTitleH1.get(0).text();
                        }
                    }
                    // 新闻内容
                    Elements newsDetails = doc.getElementsByTag("article");
                    if(ValidateTools.validateListNull(newsDetails)) {
                        Elements newContentP = newsDetails.get(0).getElementsByTag("p");
                        // 获取详情
                        if(ValidateTools.validateListNull(newContentP)) {
                            StringBuffer contentTmp = new StringBuffer();
                            for(Element p : newContentP){
                                // 处理无需文字
                                String notNeedClass = "editor-name";
                                if(notNeedClass.equals(p.attr("data-role"))) {
                                    continue;
                                }
                                // 如果是图片
                                // 图片 class
                                String imgClass = "ql-align-center";
                                if(imgClass.equals(p.attr("class"))) {
                                    Elements imgs = p.getElementsByTag("img");
                                    if(ValidateTools.validateListNull(imgs)) {
                                        // 图片占位符
                                        contentTmp.append("{");
                                        contentTmp.append(newsPicList.size());
                                        contentTmp.append("}");
                                        // 拼装图片
                                        String imgUrl = imgs.attr("src");
                                        newsPicList.add(imgUrl);
                                    }
                                }else{
                                    // 如果是文字
                                    String text = p.text();
                                    // 段落开头缩进
                                    contentTmp.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    //具体文字
                                    contentTmp.append(text);
                                }
                                // 添加换行符
                                contentTmp.append("<br/>");
                                content = contentTmp.toString();
                            }
                        }
                    }
                    break;
                // 图组
                case 1:
                    // 获取新闻标题
                    Elements picTitleDiv = doc.getElementsByAttributeValue("id", "article-title-hash");
                    if(ValidateTools.validateListNull(picTitleDiv)) {
                        title = picTitleDiv.get(0).text();
                    }
                    // 获取图片
                    Elements newsPictures = doc.getElementsByAttributeValue("class", "pic-area");
                    if(ValidateTools.validateListNull(newsPictures)) {
                        Elements pics = newsPictures.get(0).getElementsByTag("img");
                        if(ValidateTools.validateListNull(newsPictures)){
                            for(Element img : pics){
                                newsPicList.add(img.attr("src"));
                            }
                        }
                    }
                    // 获取文字
                    Elements newsTexts = doc.getElementsByAttributeValue("class", "txt");
                    if(ValidateTools.validateListNull(newsTexts)) {
                        Elements newContentP = newsPictures.get(0).getElementsByTag("p");
                        if(ValidateTools.validateListNull(newContentP)) {
                            StringBuffer contentTmp = new StringBuffer();
                            for(int i = 0 ; i < newContentP.size() ; i++) {
                                // 拼装详情
                                String text = newContentP.get(i).text();
                                // 文字
                                contentTmp.append(text);
                                // 图片占位符
                                contentTmp.append("{");
                                contentTmp.append(i);
                                contentTmp.append("}");
                                // 添加换行符
                                contentTmp.append("<br/>");
                                content = contentTmp.toString();
                            }
                        }
                    }
                    break;
                default:
                    break;
            }

            // 拼装实体
            news.setTitle(title);
            news.setHref(url);
            news.setNewsType(newsType);
            news.setContent(content);
            // TODO
            news.setSource("sohu");
            if(ValidateTools.validateListNull(newsPicList)){
                news.setCoverPic(newsPicList.get(0));
                news.setNewspic(newsPicList.toString());
            }
            log.info(JSON.toJSONString(news));
            // 入库
            newsService.saveNews(news);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("content: {}", url);
    }


    /**
     * 判断是图组还是新闻
     * @param url
     * @return  0: 新闻 1: 图组
     */
    private Integer newsType(String url){
        if(url.contains("picture")){
            return 1;
        }else{
            return 0;
        }
    }

    public static void main(String[] args) {
        CrawlerInterface crawler = new SohuCrawler();
        crawler.getContent("http://www.sohu.com/a/309384725_458722?sec=wd");
    }
}
