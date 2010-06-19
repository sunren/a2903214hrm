package com.hr.configuration.bo;

import com.hr.configuration.domain.Syslog;
import java.util.List;

public abstract interface ISysLogBO {
    public abstract List insertLog(Syslog paramSyslog, String paramString);

    public abstract List insertLog(Syslog paramSyslog);

    public abstract List<Syslog> getLogs(String paramString1, String paramString2);

    public abstract void deleteLogs(String paramString1, String paramString2);

    public abstract void addToSyslog(String paramString1, String paramString2, String paramString3,
            String paramString4, int paramInt, String paramString5, String paramString6);

    public abstract void addLog(List<Object> paramList);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.bo.ISysLogBO JD-Core Version: 0.5.4
 */