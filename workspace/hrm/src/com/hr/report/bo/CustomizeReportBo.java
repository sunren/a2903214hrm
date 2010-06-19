package com.hr.report.bo;

import com.hr.report.domain.ReportDef;
import com.hr.report.domain.ReportParams;
import com.hr.report.domain.ReportSets;
import java.io.File;
import java.util.List;

public abstract interface CustomizeReportBo {
    public abstract List getObjects(Class paramClass, String[] paramArrayOfString);

    public abstract List<ReportDef> findReport(ReportDef paramReportDef);

    public abstract List<String> updateReport(ReportDef paramReportDef, File paramFile);

    public abstract List<String> deleteReport(String paramString);

    public abstract List<String> addReport(List<ReportSets> paramList, ReportDef paramReportDef,
            List<ReportParams> paramList1);

    public abstract ReportDef loadReport(String paramString, String[] paramArrayOfString);

    public abstract List<String> addReport(ReportDef paramReportDef, File paramFile);

    public abstract List<String> updateReport(List<ReportSets> paramList, ReportDef paramReportDef,
            List<ReportParams> paramList1);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.bo.CustomizeReportBo JD-Core Version: 0.5.4
 */