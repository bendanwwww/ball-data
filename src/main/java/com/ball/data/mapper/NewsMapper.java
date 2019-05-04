package com.ball.data.mapper;

import com.ball.data.domain.News;
import org.apache.ibatis.annotations.Mapper;

/**
 * 新闻相关
 *
 * @author lsy
 */
@Mapper
public interface NewsMapper {

    void insertNews(News entity);
}
