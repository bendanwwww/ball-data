package com.ball.tools;

public class StringTools {

    /**
     * 校验是否包含子字符串
     * @param s1    大字符串
     * @param s2    子串
     * @return
     */
    public static boolean haveString(String s1, String s2){
        if(s1 == null || s2 == null) {
            return false;
        }
        return s1.indexOf(s2) != -1;
    }
}
