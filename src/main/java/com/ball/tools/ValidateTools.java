package com.ball.tools;

import java.util.List;

public class ValidateTools {

    /**
     * 校验对象非空
     * @param o
     * @return
     */
    public static boolean validateObjectNull(Object o){
        return o != null;
    }

    /**
     * 校验列表非空
     * @param l
     * @return
     */
    public static boolean validateListNull(List l){
        return l != null && l.size() > 0;
    }

    /**
     * 校验字符串非空
     * @param s
     * @return
     */
    public static boolean validateStrNull(String s){
        return s != null && !"".equals(s.trim());
    }

    /**
     * 校验字符串非空
     * @param s
     * @return
     */
    public static boolean validateStrNull(String... s){
        for(String str : s){
            if(str == null || "".equals(str.trim())){
                return false;
            }
        }
        return true;
    }
}
