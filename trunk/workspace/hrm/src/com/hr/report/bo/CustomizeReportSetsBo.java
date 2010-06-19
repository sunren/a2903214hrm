package com.hr.report.bo;

import com.hr.report.domain.ReportSets;
import java.util.List;

public abstract interface CustomizeReportSetsBo {
    public abstract List getObjects(Class paramClass, String[] paramArrayOfString);

    public abstract List getOutputColumnsByReportDefId(String paramString);

    public abstract List deleteReportSetsByReportId(String paramString);

    public abstract List deleteReportSets(String paramString);

    public abstract List<String> addReportSets(ReportSets paramReportSets);

    public abstract List<String> updateReportSets(ReportSets paramReportSets);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.bo.CustomizeReportSetsBo JD-Core Version: 0.5.4
 */