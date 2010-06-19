package com.hr.homepage.bo;

import java.util.ArrayList;
import java.util.List;

public abstract interface IIFindTip {
    public abstract ArrayList<String> getTip101();

    public abstract ArrayList<String> getTip1012();

    public abstract ArrayList<String> getTip111(String paramString);

    public abstract ArrayList<String> getTip301();

    public abstract ArrayList<String> getTip601();

    public abstract ArrayList<String> getTip201();

    public abstract ArrayList<String> getTip401();

    public abstract ArrayList<String> getTip4012();

    public abstract ArrayList<String> getTip4112(String paramString);

    public abstract ArrayList<String> getTip411(String paramString);

    public abstract ArrayList<String> getTip3012();

    public abstract ArrayList<String> getTip3112(String[] paramArrayOfString);

    public abstract ArrayList<String> getTip311(String paramString);

    public abstract ArrayList<String> getTip6012();

    public abstract ArrayList<String> getTip6112(String[] paramArrayOfString);

    public abstract ArrayList<String> getTip611(String paramString);

    public abstract ArrayList<String> getTip2012();

    public abstract ArrayList<String> getTip2112(String paramString);

    public abstract ArrayList<String> getTip211(String paramString);

    public abstract List<String> getEmployeeAdditionalTip();
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.homepage.bo.IIFindTip JD-Core Version: 0.5.4
 */