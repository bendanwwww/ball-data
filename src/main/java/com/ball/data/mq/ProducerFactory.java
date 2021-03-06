package com.ball.data.mq;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.ball.data.utils.PropertyUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.rocketmq.client.producer.*;

@Configuration
public class ProducerFactory {

    // 接入点
    private static final String MQ_ADDR = PropertyUtils.getString("mq_addr");
    // 失败重试次数
    private static final Integer MQ_FAILED_NUMBER = Integer.parseInt(PropertyUtils.getString("mq_faild_number"));

    @Bean
    public DefaultMQProducer defaultBallNewsProducer() throws MQClientException {
        DefaultMQProducer ballNewsProducer = new DefaultMQProducer("news-group");
        ballNewsProducer.setNamesrvAddr(MQ_ADDR);
        ballNewsProducer.setInstanceName("ball-news-producer");
        ballNewsProducer.setRetryTimesWhenSendFailed(MQ_FAILED_NUMBER);
        ballNewsProducer.start();
        return ballNewsProducer;
    }
}
