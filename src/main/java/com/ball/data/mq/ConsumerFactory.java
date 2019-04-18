package com.ball.data.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.ball.data.conf.GlobalVariables;
import com.ball.data.crawler.CrawlerInterface;
import com.ball.data.utils.PropertyUtils;
import com.ball.tools.UrlTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ConsumerFactory {

    private static final String MQ_ADDR = PropertyUtils.getString("mq_addr");

    private static final Logger log = LoggerFactory.getLogger(ConsumerFactory.class);

    @Bean
    public DefaultMQPushConsumer defaultBallNewsConsumer() throws MQClientException {
        DefaultMQPushConsumer ballNewsConsumer = new DefaultMQPushConsumer("news-group");
        ballNewsConsumer.setNamesrvAddr(MQ_ADDR);
        // 设置消费topic
        ballNewsConsumer.subscribe("ball_news", "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        ballNewsConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 设置监听器
        ballNewsConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                MessageExt msg = msgs.get(0);
                // 消息tag
                String messageTag = msg.getTags();
                // 消息体
                String url = new String(msg.getBody());
                // 判重校验
                if(!UrlTools.validateRepeat(url)){
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                // 消费逻辑
                CrawlerInterface crawlerInterface = (CrawlerInterface) GlobalVariables.getObject(messageTag);
                crawlerInterface.getContent(url);
                log.info("news mq consumer messageExt: {}", JSON.toJSONString(msg));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        ballNewsConsumer.start();
        return ballNewsConsumer;
    }
}
