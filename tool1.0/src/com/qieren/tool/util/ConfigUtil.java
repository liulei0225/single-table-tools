package com.qieren.tool.util;

import java.io.IOException;
import java.util.Properties;

/**
 * @author qieren
 * @version 1.0
 * @since 2013-11-04
 */
public class ConfigUtil {
    private static Properties properties;

    private static Properties getProperties() {
        if (properties == null) {
            Properties _properties = new Properties();
            try {
                _properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("common-config.properties"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            properties = _properties;
        }
        return properties;
    }

    public static String get(String key) {
        return getProperties().getProperty(key);
    }
}
