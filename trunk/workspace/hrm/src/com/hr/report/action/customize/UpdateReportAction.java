package com.hr.report.action.customize;

import com.hr.base.BaseAction;
import com.hr.report.domain.ReportDef;
import com.hr.report.domain.ReportParams;

public class UpdateReportAction extends BaseAction {
    private ReportDef report;
    private ReportParams params;

    public String execute() throws Exception {
        return "success";
    }

    public ReportDef getReport() {
        return this.report;
    }

    public void setReport(ReportDef report) {
        this.report = report;
    }

    public ReportParams getParams() {
        return this.params;
    }

    public void setParams(ReportParams params) {
        this.params = params;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.action.customize.UpdateReportAction JD-Core Version: 0.5.4
 */