package com.ball.data.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {
    private static String propUrl = "prop.properties";
    private static Properties prop = new Properties();

    public PropertyUtils() {
    }

    public static String getString(String name) {
        return prop.getProperty(name);
    }

    static {
        InputStream in = null;
        try {
            in = (new ClassPathResource(propUrl)).getInputStream();
            prop.load(in);
            in.close();
        } catch (Exception var10) {
            var10.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException var9) {
                    var9.printStackTrace();
                }
            }

        }
    }
}
