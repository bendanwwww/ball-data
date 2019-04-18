package com.ball.data.mq;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.ball.tools.ValidateTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SendMessage {

    private static final Logger log = LoggerFactory.getLogger(SendMessage.class);

    @Autowired
    DefaultMQProducer defaultBallNewsProducer;

    /**
     * 发送新闻消息
     * @param topic
     * @param tag
     * @param url
     */
    public Boolean sendNewsMessage(String topic, String tag, String url) {
        try {
            if(ValidateTools.validateStrNull(topic, tag, url)){
                Message msg = new Message(topic, tag, url.getBytes());
                SendResult sendResult = defaultBallNewsProducer.send(msg);
                log.info("send news mq: {}", sendResult);
                System.out.println(sendResult);
                return Boolean.TRUE;
            }else{
                return Boolean.FALSE;
            }

        }catch (Exception e) {
            log.error(e.getMessage());
        }
        return Boolean.FALSE;
    }
}
