package com.hr.report.bo;

import com.hr.report.domain.ReportJoinDef;
import java.util.List;

public abstract interface CustomizeReportJoinDefBo {
    public abstract List getObjects(Class paramClass, String[] paramArrayOfString);

    public abstract List loadAll();

    public abstract List findJoinDefListByMainTableName(String paramString);

    public abstract ReportJoinDef findByMainTableAndAssistanceTable(String paramString1,
            String paramString2);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.bo.CustomizeReportJoinDefBo JD-Core Version: 0.5.4
 */