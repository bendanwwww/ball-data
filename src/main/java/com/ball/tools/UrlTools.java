package com.ball.tools;

public class UrlTools {

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

    public static void main(String[] args) {
        String url = "//www.sohu.com/picture/308719952?sec=wd";
        System.out.println(url.substring(0, 1));
    }
}
