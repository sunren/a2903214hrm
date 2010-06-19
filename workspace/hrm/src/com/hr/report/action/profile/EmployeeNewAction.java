package com.hr.report.action.profile;

import com.hr.profile.bo.IEmployeeBo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;

public class EmployeeNewAction extends EmployeeReportAction {
    private static final long serialVersionUID = 1L;

    public String execute() throws Exception {
        String result = super.execute();
        ServletRequest request = getRequest();
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String groupBy = request.getParameter("groupBy");

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        List empSumList = empBo.getEmpNewReport(startDate, endDate, groupBy);
        Map params = new HashMap();
        params.put("empSumList", empSumList);
        request.setAttribute("_params", params);
        return result;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.action.profile.EmployeeNewAction JD-Core Version: 0.5.4
 */