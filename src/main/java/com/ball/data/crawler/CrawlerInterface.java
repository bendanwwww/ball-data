package com.ball.data.crawler;

/**
 * 爬虫基本接口
 */
public interface CrawlerInterface {

    public void getUrl();

    public void getContent(String url);
}
