package com.hr.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;

public class SystemPropertiesReader {
    private static final String FILE_NAME = "leaverequest_zh_CN.properties";
    private static SystemPropertiesReader reader = new SystemPropertiesReader();
    private Properties properties;

    private SystemPropertiesReader() {
        this.properties = new Properties();
        InputStream in = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("leaverequest_zh_CN.properties");
        try {
            this.properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
            in = null;
        }
    }

    public static SystemPropertiesReader getInstance() {
        return reader;
    }

    public String getValue(String key) {
        return this.properties.getProperty(key);
    }

    public String getValue(String key, String defaultValue) {
        String rs = getValue(key);
        return StringUtils.defaultIfEmpty(rs, defaultValue);
    }

    public boolean getBoolean(String key) {
        String value = getValue(key);
        return "true".equalsIgnoreCase(value);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.SystemPropertiesReader JD-Core Version: 0.5.4
 */