package com.hr.report.action.customize;

import com.hr.base.BaseAction;
import com.hr.report.bo.CustomizeReportBo;
import com.hr.report.domain.ReportDef;
import com.hr.spring.SpringBeanFactory;
import java.io.File;
import java.io.PrintStream;
import java.util.List;

public class UploadReportAction extends BaseAction {
    private ReportDef report;
    private File reportFile;

    public String execute() throws Exception {
        CustomizeReportBo customizeReportBo = (CustomizeReportBo) SpringBeanFactory
                .getBean("customizeReportBo");
        List errors = customizeReportBo.addReport(this.report, this.reportFile);
        if (!errors.isEmpty()) {
            addErrorInfo(errors);
            return "input";
        }
        System.out.println(this.reportFile.getName());
        addSuccessInfo("新增定制报表成功＄1�7");
        return "success";
    }

    public String init() throws Exception {
        return "success";
    }

    public ReportDef getReport() {
        return this.report;
    }

    public void setReport(ReportDef report) {
        this.report = report;
    }

    public File getReportFile() {
        return this.reportFile;
    }

    public void setReportFile(File reportFile) {
        this.reportFile = reportFile;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.action.customize.UploadReportAction JD-Core Version: 0.5.4
 */