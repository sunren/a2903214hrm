package com.hr.report.action.customize;

import com.hr.base.BaseAction;
import com.hr.report.bo.CustomizeReportBo;
import com.hr.report.domain.ReportDef;
import com.hr.spring.SpringBeanFactory;
import java.util.List;

public class ListReportAction extends BaseAction {
    private ReportDef report;
    private List<ReportDef> reportList;

    public String execute() throws Exception {
        CustomizeReportBo customizeReportBo = (CustomizeReportBo) SpringBeanFactory
                .getBean("customizeReportBo");
        if (this.report == null) {
            this.report = new ReportDef();
        }
        this.report.setReportType(Integer.valueOf(0));
        this.reportList = customizeReportBo.findReport(this.report);
        return "success";
    }

    public List<ReportDef> getReportList() {
        return this.reportList;
    }

    public void setReportList(List<ReportDef> reportList) {
        this.reportList = reportList;
    }

    public ReportDef getReport() {
        return this.report;
    }

    public void setReport(ReportDef report) {
        this.report = report;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.action.customize.ListReportAction JD-Core Version: 0.5.4
 */