package com.ball.data.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class YmlUtils {

    private static String ymlUrl = "data.yml";
    private static Map<String, Object> ymlMap = new HashMap<String, Object>();

    static {
        try {
            Yaml yaml = new Yaml();
            URL url = YmlUtils.class.getClassLoader().getResource(ymlUrl);
            if (url != null) {
                ymlMap = (LinkedHashMap) yaml.load(new FileInputStream(url.getFile()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getString(String name) {
        return ymlMap.get(name);
    }
}
