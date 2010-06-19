package com.hr.report.action.compensation;

import com.hr.base.BaseAction;
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

public class SalaryCostReport extends BaseAction {
    private String reportFormat;
    private SalaryReportParamBean bean;
    private static final String REPORT_FILE_LOCATION = "/report/compensation/salary_analysis_cost.rptdesign";

    public SalaryCostReport() {
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

        String title = "人工成本分析";
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

        List reportItems = salaryReportBo.getSalaryReportCost(this.bean);

        Map params = new HashMap();
        params.put("reportObjects", reportItems);
        params.put("title", title);
        request.setAttribute("_params", params);

        request.setAttribute("_report", "/report/compensation/salary_analysis_cost.rptdesign");
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
 * com.hr.report.action.compensation.SalaryCostReport JD-Core Version: 0.5.4
 */