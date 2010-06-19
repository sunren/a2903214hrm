package com.hr.security.bo;

import com.hr.util.Pager;
import java.util.List;

public abstract interface BackupRBo {
    public abstract List searchAll(String paramString, Pager paramPager);

    public abstract String sqlbin(String paramString);

    public abstract boolean resume(String paramString1, String paramString2, String paramString3,
            String paramString4, String paramString5);

    public abstract boolean saveBackup(String paramString1, String paramString2,
            String paramString3, String paramString4, String paramString5);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.bo.BackupRBo JD-Core Version: 0.5.4
 */