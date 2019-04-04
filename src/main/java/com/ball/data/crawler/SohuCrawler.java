package com.ball.data.crawler;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.ball.data.utils.PropertyUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class SohuCrawler implements CrawlerInterface{

    private static final String SOHU_URL = PropertyUtils.getString("sohu_url");

    @Autowired
    DefaultMQProducer defaultBallNewsProducer;

    @Override
    public void getUrl() {
        Document doc;
        try {
            doc = Jsoup.connect(SOHU_URL).get();
            Elements listDiv = doc.getElementsByAttributeValue("class", "f14list");
            for (Element element : listDiv) {
                Elements links = element.getElementsByTag("a");
                for (Element link : links) {
                    String linkHref = link.attr("href");
                    String titleText = link.text().trim();
                    if(!linkHref.split("\\.")[0].equals("http://pic")) {
                        String[] sca = getContent(linkHref);
                        Message msg = new Message(
                                "ball_news",    //topic
                                "sohu",         //tag
                                ("sohu url").getBytes());
                        SendResult sendResult = defaultBallNewsProducer.send(msg);
                        System.out.println(sendResult);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] getContent(String url){
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
