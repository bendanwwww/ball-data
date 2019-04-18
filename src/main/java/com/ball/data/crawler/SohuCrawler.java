package com.ball.data.crawler;

import com.ball.data.mq.SendMessage;
import com.ball.data.utils.PropertyUtils;
import com.ball.tools.ValidateTools;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SohuCrawler implements CrawlerInterface{

    private static final String SOHU_URL = PropertyUtils.getString("sohu_url");

    @Autowired
    SendMessage sendMessage;


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
                        System.out.println(newHref);
                    }
                }
                sendMessage.sendNewsMessage("ball_news", "sohu", newHref);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] getContent(String url) {
        Document doc;
        String[] srcAndContent = new String[3];
        try {
            doc = Jsoup.connect(url).get();
            Elements conDiv = doc.getElementsByAttributeValue("itemprop", "articleBody");
            if(conDiv.size() > 0){
                Elements cons = conDiv.get(0).getElementsByTag("p");
                Elements imgs = conDiv.get(0).getElementsByAttributeValue("class", "fl-pic");
                String src = "";
                String content = "";
                String abstract1 = "";
                //获取新闻图片
                if(imgs.size() > 0){
                    src = imgs.get(0).getElementsByTag("img").get(0).attr("src");
                }else{
                    imgs = conDiv.get(0).getElementsByAttributeValue("class", "tableImg");
                    if(imgs.size() > 0){
                        src = imgs.get(0).getElementsByTag("img").get(0).attr("src");
                    }
                }
                //获取新闻内容
                int num = 0;
                for (Element link : cons) {
                    if(link.text() != null && !link.text().replaceAll("　", "").equals("")){
                        if(num == 0){
                            if(link.text().replaceAll("　", "").length() < 35){
                                abstract1 = link.text().replaceAll("　", "");
                            }else{
                                abstract1 = link.text().replaceAll("　", "").substring(0, 34) + "...";
                            }
                        }
                        content = content + link.text().replaceAll("　", "") + "<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";//新闻内容
                        num++;
                    }
                }
                srcAndContent[0] = content;
                srcAndContent[1] = src;
                srcAndContent[2] = abstract1;
                return srcAndContent;
            }else{
                conDiv = doc.getElementsByAttributeValue("class", "article");
                if(conDiv.size() > 0){
                    Elements cons = conDiv.get(0).getElementsByTag("p");
                    String src = "";
                    String content = "";
                    String abstract1 = "";
                    //获取新闻内容
                    int num = 0;
                    for (Element link : cons) {
                        if(link.getElementsByAttributeValue("data-role", "original-title").size() > 0){
                            continue;
                        }
                        if(link.getElementsByAttributeValue("data-role", "editor-name").size() > 0){
                            continue;
                        }
                        if(link.getElementsByTag("img").size() > 0){
                            src = link.getElementsByTag("img").get(0).attr("src").replaceAll("//", "http://");
                            continue;
                        }
                        if(link.text() != null && !link.text().replaceAll("　", "").equals("")){
                            if(num == 0){
                                if(link.text().replaceAll("　", "").length() < 35){
                                    abstract1 = link.text().replaceAll("　", "");
                                }else{
                                    abstract1 = link.text().replaceAll("　", "").substring(0, 34) + "...";
                                }
                            }
                            content = content + link.text().replaceAll("　", "") + "<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";//新闻内容
                            num++;
                        }
                    }
                    srcAndContent[0] = content;
                    srcAndContent[1] = src;
                    srcAndContent[2] = abstract1;
                    return srcAndContent;
                }else{
                    return null;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args){
        SohuCrawler crawler = new SohuCrawler();
        crawler.getUrl();
    }
}
