package com.ball.data.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class YmlUtils {

    private static final Logger log = LoggerFactory.getLogger(YmlUtils.class);


    private static String ymlUrl = "data.yml";
    private static Map<String, Object> ymlMap = new HashMap<String, Object>();

    static {
        try {
            Yaml yaml = new Yaml();
            URL url = YmlUtils.class.getClassLoader().getResource(ymlUrl);
            if (url != null) {
                ymlMap = (HashMap) yaml.load(new FileInputStream(url.getFile()));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static Object getString(String name) {
        return ymlMap.get(name);
    }
}
