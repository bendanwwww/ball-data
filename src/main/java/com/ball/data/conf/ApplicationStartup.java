package com.ball.data.conf;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 容器启动时执行方法
 */
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        GlobalVariables globalVariables = contextRefreshedEvent.getApplicationContext().getBean(GlobalVariables.class);
        globalVariables.initParam();

    }

//    public static Map<MessageEnum, Map> getMessageMap() {
//        return messageMap;
//    }
//
//    public static void setMessageMap(Map<MessageEnum, Map> messageMap) {
//        ApplicationStartup.messageMap = messageMap;
//    }
}
