package com.ball.data.conf;

import com.ball.data.utils.YmlUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class GlobalVariables {

    private static Map<String, Object> paramsMap = new HashMap<String, Object>();

    public void initParam() {
        try {
            HashMap<String, String> newsMap = (LinkedHashMap) YmlUtils.getString("news");
            Iterator<String> it = newsMap.keySet().iterator();
            while (it.hasNext()) {
                String newsKey = it.next();
                String classAddr = newsMap.get(newsKey);
                Object obj = (Object) Class.forName(classAddr);
                paramsMap.put(newsKey, obj);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static Object getObject(String  name){
        return paramsMap.get(name);
    }
}
