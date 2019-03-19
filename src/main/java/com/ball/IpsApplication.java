package com.ball;


import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.feign.EnableFeignClients;


/**
 * 启动类
 * @author lsy
 */


@SpringBootApplication
//@EnableDiscoveryClient
//@EnableFeignClients
public class IpsApplication {

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(IpsApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
        
    }

}
