package com.ball.data.service;

import com.ball.data.domain.News;
import com.ball.data.dto.NewsDto;
import com.ball.data.mapper.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 新闻相关
 */

@Component
public class NewsService {

    @Autowired
    private NewsMapper newsMapper;

    /**
     * 保存新闻
     * @param newsDto
     */
    public void saveNews(NewsDto newsDto) {
        // TODO 校验newsDto
        // 存库实体
        News news = new News();
        news.setTitle(newsDto.getTitle());
        news.setHref(newsDto.getHref());
        news.setCoverPic(newsDto.getContent());
        news.setNewspic(newsDto.getNewspic());
        news.setContent(newsDto.getContent());
        news.setGetDate(new Date());
        news.setSource(news.getSource());
        news.setClicks(0);
        news.setNewsType(news.getNewsType());
        // TODO
        news.setAbstract1("");
        news.setAbstract2("");
        newsMapper.insertNews(news);
    }
}
