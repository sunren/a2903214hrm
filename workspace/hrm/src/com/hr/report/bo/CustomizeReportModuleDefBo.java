package com.hr.report.bo;

import com.hr.report.domain.ReportModuleDef;
import java.util.List;

public abstract interface CustomizeReportModuleDefBo {
    public abstract List list(String paramString1, String paramString2);

    public abstract ReportModuleDef getReportModuleDef(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.bo.CustomizeReportModuleDefBo JD-Core Version: 0.5.4
 */