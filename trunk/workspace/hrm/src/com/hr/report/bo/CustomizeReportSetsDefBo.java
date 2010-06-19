package com.hr.report.bo;

import com.hr.report.domain.ReportSetsDef;
import java.util.List;

public abstract interface CustomizeReportSetsDefBo {
    public abstract List getObjects(Class paramClass, String[] paramArrayOfString);

    public abstract List getReportSetsDefByTableName(String paramString);

    public abstract List getReportSetsDefByModuleAndTableName(Integer paramInteger1,
            String paramString, Integer paramInteger2);

    public abstract List getAll();

    public abstract List<String> deleteReportSetsDef(String paramString);

    public abstract List<String> saveReportSetsDef(ReportSetsDef paramReportSetsDef);

    public abstract List<String> updateReportSetsDef(ReportSetsDef paramReportSetsDef);

    public abstract ReportSetsDef getReportSetsDef(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.bo.CustomizeReportSetsDefBo JD-Core Version: 0.5.4
 */