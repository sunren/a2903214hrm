package com.hr.security.bo;

import com.hr.util.Pager;
import java.util.ArrayList;

public abstract interface LogBo {
    public abstract ArrayList getLogRecorders(String paramString, Pager paramPager);

    public abstract boolean clearLogRecorders(String paramString);

    public abstract boolean updateXML(String paramString1, String paramString2);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.bo.LogBo JD-Core Version: 0.5.4
 */