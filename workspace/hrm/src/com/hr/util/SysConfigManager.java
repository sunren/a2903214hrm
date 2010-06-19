package com.hr.util;

import java.util.Map;

public abstract interface SysConfigManager {
    public abstract void addNewProperty(String paramString1, String paramString2);

    public abstract void removeProperty(String paramString);

    public abstract String getProperty(String paramString);

    public abstract void setProperty(String paramString1, String paramString2);

    public abstract void saveChange();

    public abstract Map<String, String> getProperties();
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.SysConfigManager JD-Core Version: 0.5.4
 */