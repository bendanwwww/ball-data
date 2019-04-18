package com.ball.tools;

import com.ball.data.utils.PropertyUtils;
import com.ball.data.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class UrlTools {

    private static final String REPEAT_TIME = PropertyUtils.getString("repeat_time");

    @Autowired
    private static RedisUtil redisUtil;

    /**
     * 标准url转换
     * @param url
     * @return
     */
    public static String changeUrl(String url) {
        if(!ValidateTools.validateStrNull(url)){
            return url;
        }
        //sohu
        if("//".equals(url.substring(0, 2))){
            url = "https:" + url;
        }
        return url;
    }

    /**
     * url判重
     * @param url
     * @return
     */
    public static boolean validateRepeat(String url) {
        Integer timeout = Integer.parseInt(REPEAT_TIME);
        Object tmp = redisUtil.get(url);
        if(ValidateTools.validateObjectNull(tmp)){
            redisUtil.set(url, (new Date()).getTime(), timeout);
            return true;
        }else{
            return false;
        }
    }

    public static void main(String[] args) {
        String url = "//www.sohu.com/picture/308719952?sec=wd";
        System.out.println(url.substring(0, 1));
    }
}
