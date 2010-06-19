package com.hr.report.action.profile;

import com.hr.report.bo.SalaryReportBo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;

public class EmployeeDimission extends EmployeeReportAction {
    public String execute() throws Exception {
        String result = super.execute();
        ServletRequest request = getRequest();
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        SalaryReportBo salaryReportBo = (SalaryReportBo) getBean("salaryReportBo");
        List dimissionRate = salaryReportBo.getEmployeeDimissionRate(startDate, endDate);
        Map params = new HashMap();
        params.put("rateList", dimissionRate);
        params.put("startDate", handleDate(startDate));
        params.put("endDate", handleDate(endDate));
        request.setAttribute("_params", params);
        return result;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.action.profile.EmployeeDimission JD-Core Version: 0.5.4
 */