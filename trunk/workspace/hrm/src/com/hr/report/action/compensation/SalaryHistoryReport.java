package com.hr.report.action.compensation;

import com.hr.base.BaseAction;
import com.hr.compensation.domain.Empsalarydatadef;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Jobgrade;
import com.hr.report.action.compensation.support.SalaryReportParamBean;
import com.hr.report.bo.SalaryReportBo;
import com.hr.spring.SpringBeanFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import org.apache.commons.lang.StringUtils;

public class SalaryHistoryReport extends BaseAction {
    private String reportFormat;
    private SalaryReportParamBean bean;
    private static final String REPORT_FILE_LOCATION = "/report/compensation/salary_analysis_history.rptdesign";

    public SalaryHistoryReport() {
        this.reportFormat = "HTML";
    }

    public String execute() throws Exception {
        ServletRequest request = getRequest();

        if ("PDF".equalsIgnoreCase(this.reportFormat)) {
            request.setAttribute("_format", "PDF");
            request.setAttribute("_contentType", "attachment");
            request.setAttribute("_fileName", "empReport");
        } else {
            request.setAttribute("_format", "HTML");
        }

        SalaryReportBo salaryReportBo = (SalaryReportBo) SpringBeanFactory
                .getBean("salaryReportBo");

        Empsalarydatadef dataDef = (Empsalarydatadef) salaryReportBo
                .loadObject(Empsalarydatadef.class, this.bean.getItemId(), null);
        if (dataDef == null) {
            addErrorInfo("不存在的帐套项目");
            return "input";
        }
        String title = dataDef.getEsddName();
        title = (title == null) ? "" : title;
        title = title + "发放历史分析";
        String tmpTitle = "";
        if (StringUtils.isNotEmpty(this.bean.getDepartmentId())) {
            Department dep = (Department) salaryReportBo.loadObject(Department.class, this.bean
                    .getDepartmentId(), null);
            if (dep != null) {
                tmpTitle = dep.getDepartmentName();
            }
        }
        if (StringUtils.isNotEmpty(this.bean.getJobgradeId())) {
            Jobgrade grade = (Jobgrade) salaryReportBo.loadObject(Jobgrade.class, this.bean
                    .getJobgradeId(), null);
            if (null != grade) {
                tmpTitle = tmpTitle + grade.getJobGradeName();
            }
        }
        title = tmpTitle + title;

        List reportItems = salaryReportBo.getSalaryReportHistory(this.bean);

        Map params = new HashMap();
        params.put("reportObjects", reportItems);
        params.put("title", title);
        request.setAttribute("_params", params);

        request.setAttribute("_report", "/report/compensation/salary_analysis_history.rptdesign");
        return "success";
    }

    public SalaryReportParamBean getBean() {
        return this.bean;
    }

    public void setBean(SalaryReportParamBean bean) {
        this.bean = bean;
    }

    public String getReportFormat() {
        return this.reportFormat;
    }

    public void setReportFormat(String reportFormat) {
        this.reportFormat = reportFormat;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.action.compensation.SalaryHistoryReport JD-Core Version: 0.5.4
 */