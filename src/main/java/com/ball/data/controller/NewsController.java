package com.ball.data.controller;

import com.ball.data.crawler.SohuCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("news")
public class NewsController {

    @Autowired
    SohuCrawler sohuCrawler;

    @RequestMapping("/")
    public String home(HttpServletRequest request) {
        return request.getRequestURI();
    }

    @RequestMapping("/sohuNews")
    public void sohuNews() {
        sohuCrawler.getUrl();
    }
}
