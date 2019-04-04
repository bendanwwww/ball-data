package com.ball.data.crawler;

public interface CrawlerInterface {

    public void getUrl();

    public String[] getContent(String url);
}
