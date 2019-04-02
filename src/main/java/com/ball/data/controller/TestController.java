package com.ball.data.controller;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 测试方法
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    DefaultMQProducer defaultBallNewsProducer;

    @RequestMapping("/")
    public String home(HttpServletRequest request) {
        return request.getRequestURI();
    }

    @RequestMapping("/test")
    public void test() {
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000); //每秒发送一次MQ
                Message msg = new Message(
                        "ball_news",    //topic
                        "test",         //tag
                        (new Date() + "Hello RocketMQ,QuickStart" + i).getBytes());
                SendResult sendResult = defaultBallNewsProducer.send(msg);
                System.out.println(sendResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
