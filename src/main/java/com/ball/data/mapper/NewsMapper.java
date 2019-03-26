package com.ball.data.mapper;

import com.ball.data.domain.News;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface NewsMapper {

    void insertNews(News entity);
}
