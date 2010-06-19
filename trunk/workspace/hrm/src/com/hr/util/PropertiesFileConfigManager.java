package com.hr.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class PropertiesFileConfigManager implements SysConfigManager {
    private static final PropertiesFileConfigManager instance = new PropertiesFileConfigManager();
    private static final String SYS_CONFIG_FILE_NAME = "sys_config.properties";
    private static final Map<String, String> SYS_CONFIG_PROPERTIES_CACHE = new HashMap();
    private static final String SYS_CONFIG_DEFAULT_ENCODING = "GBK";
    private PropertiesConfiguration config;
    private static final char DEFAULT_DELIMITER = '!';

    private PropertiesFileConfigManager() {
        try {
            PropertiesConfiguration.setDefaultListDelimiter('!');
            this.config = new PropertiesConfiguration("sys_config.properties");
            this.config.setEncoding("GBK");
            this.config.getProperties("a");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static PropertiesFileConfigManager getInstance() {
        return instance;
    }

    public void addNewProperty(String key, String value) {
        this.config.clearProperty(key);
        this.config.addProperty(key, value);
        SYS_CONFIG_PROPERTIES_CACHE.put(key, value);
    }

    public String getProperty(String key) {
        if (SYS_CONFIG_PROPERTIES_CACHE.containsKey(key)) {
            return (String) SYS_CONFIG_PROPERTIES_CACHE.get(key);
        }
        String value = this.config.getString(key);
        return (value == null) ? "" : value;
    }

    public void removeProperty(String key) {
        this.config.clearProperty(key);
        if (SYS_CONFIG_PROPERTIES_CACHE.containsKey(key))
            SYS_CONFIG_PROPERTIES_CACHE.remove(key);
    }

    public void setProperty(String key, String value) {
        this.config.setProperty(key, value);
        SYS_CONFIG_PROPERTIES_CACHE.put(key, value);
    }

    public String getDatabaseSchemaName() {
        String driverClassName = this.config.getString("jdbc.driverClassName");

        String DEFAULT_SCHEMA = "365hrm";
        String url = this.config.getString("jdbc.url");

        if (driverClassName.indexOf("mysql") != -1) {
            if (url == null)
                return "365hrm";
            int index = url.indexOf("?");
            if (index == -1)
                return "365hrm";
            int end = index;
            while ((url.charAt(index) != '/') && (index > 0)) {
                --index;
            }
            return url.substring(index + 1, end);
        }
        int startIndex = url.lastIndexOf(":");
        return url.substring(startIndex + 1, url.length());
    }

    public void saveChange() {
        try {
            this.config.save();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getProperties() {
        Iterator it = this.config.getKeys();
        while (it.hasNext()) {
            String key = (String) it.next();
            SYS_CONFIG_PROPERTIES_CACHE.put(key, (String) this.config.getProperty(key));
        }
        return SYS_CONFIG_PROPERTIES_CACHE;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.util.PropertiesFileConfigManager JD-Core Version: 0.5.4
 */