package com.hr.report.action.customize;

import com.hr.base.BaseAction;
import com.hr.report.bo.CustomizeReportBo;
import com.hr.report.domain.ReportDef;
import com.hr.spring.SpringBeanFactory;
import java.util.List;

public class ReportPreListAction extends BaseAction {
    private ReportDef report;
    private List reports;

    public String execute() throws Exception {
        CustomizeReportBo customizeReportBo = (CustomizeReportBo) SpringBeanFactory
                .getBean("customizeReportBo");
        if (this.report == null) {
            this.report = new ReportDef();
        }
        this.report.setReportType(Integer.valueOf(1));
        this.reports = customizeReportBo.findReport(this.report);
        return "success";
    }

    public ReportDef getReport() {
        return this.report;
    }

    public void setReport(ReportDef report) {
        this.report = report;
    }

    public List getReports() {
        return this.reports;
    }

    public void setReports(List reports) {
        this.reports = reports;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.action.customize.ReportPreListAction JD-Core Version: 0.5.4
 */