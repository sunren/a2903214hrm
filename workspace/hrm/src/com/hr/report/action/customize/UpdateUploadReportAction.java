package com.hr.report.action.customize;

import com.hr.base.BaseAction;
import com.hr.report.bo.CustomizeReportBo;
import com.hr.report.domain.ReportDef;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import java.io.File;
import javax.servlet.http.HttpSession;

public class UpdateUploadReportAction extends BaseAction {
    private File reportFile;
    private ReportDef report;

    public String execute() throws Exception {
        CustomizeReportBo customizeReportBo = (CustomizeReportBo) SpringBeanFactory
                .getBean("customizeReportBo");
        Userinfo user = (Userinfo) getSession().getAttribute("userinfo");

        return "success";
    }

    public File getReportFile() {
        return this.reportFile;
    }

    public void setReportFile(File reportFile) {
        this.reportFile = reportFile;
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
 * com.hr.report.action.customize.UpdateUploadReportAction JD-Core Version: 0.5.4
 */