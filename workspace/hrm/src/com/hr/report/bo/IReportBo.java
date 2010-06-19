package com.hr.report.bo;

import java.util.List;

public abstract interface IReportBo {
    public abstract List getObjects(Class paramClass, String[] paramArrayOfString);

    public abstract List<TableCell> getEmpLRHistory(String paramString1, String paramString2,
            String paramString3, int paramInt, String paramString4);

    public abstract List<TableCell> getEmpLOTHistory(String paramString1, String paramString2,
            String paramString3, int paramInt, String paramString4);

    public abstract List exeHqlList(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.bo.IReportBo JD-Core Version: 0.5.4
 */