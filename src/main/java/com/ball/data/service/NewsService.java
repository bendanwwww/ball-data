package com.ball.data.service;

import com.ball.data.domain.News;
import com.ball.data.dto.NewsDto;
import com.ball.data.mapper.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 新闻相关
 */

@Service
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
        news.setCoverPic(newsDto.getCoverPic());
        news.setNewsPic(newsDto.getNewsPic());
        news.setContent(newsDto.getContent());
        news.setPureText(newsDto.getPureContent());
        news.setGetDate(new Date());
        news.setSource(newsDto.getSource());
        news.setClicks(0);
        news.setNewsType(newsDto.getNewsType());
        // TODO
        news.setIsTop(0);
        news.setAbstract1("");
        news.setAbstract2("");
        newsMapper.insertNews(news);
    }
}
