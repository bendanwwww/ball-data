package com.ball.data.conf;

import com.ball.data.utils.BeanUtil;
import com.ball.data.utils.YmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 初始化爬虫相关bean
 *
 * @author lsy
 */
@Service
public class GlobalVariables {

    private static final Logger log = LoggerFactory.getLogger(GlobalVariables.class);

    private static Map<String, Object> paramsMap = new HashMap<String, Object>();

    public void initParam() {
        try {
            HashMap<String, String> newsMap = (HashMap) YmlUtils.getString("news");
            Iterator<String> it = newsMap.keySet().iterator();
            while (it.hasNext()) {
                String newsKey = it.next();
                String classAddr = newsMap.get(newsKey);
                // 获取相关bean
                Object obj = BeanUtil.getBean(Class.forName(classAddr));
                paramsMap.put(newsKey, obj);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            System.exit(0);
        }
    }

    public static Object getObject(String  name){
        return paramsMap.get(name);
    }
}
