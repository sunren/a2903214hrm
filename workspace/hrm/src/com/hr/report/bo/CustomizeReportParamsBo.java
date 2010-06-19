package com.hr.report.bo;

import com.hr.report.domain.ReportParams;
import java.util.List;

public abstract interface CustomizeReportParamsBo {
    public abstract List<String> addReportParams(ReportParams paramReportParams);

    public abstract List<String> updateReportParams(ReportParams paramReportParams);

    public abstract List<String> deleteReportParams(String paramString);

    public abstract List<ReportParams> findReportParams(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.bo.CustomizeReportParamsBo JD-Core Version: 0.5.4
 */