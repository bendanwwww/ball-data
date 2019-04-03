package com.ball.data.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.ball.data.utils.PropertyUtils;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class ConsumerFactory {

    private static final String MQ_ADDR = PropertyUtils.getString("mq_addr");

    @Bean
    public DefaultMQPushConsumer defaultBallNewsConsumer() throws MQClientException {
        DefaultMQPushConsumer ballNewsConsumer = new DefaultMQPushConsumer();
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
                // 消费逻辑

                System.out.println(JSON.toJSONString(msg));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        ballNewsConsumer.start();
        return ballNewsConsumer;
    }
}
