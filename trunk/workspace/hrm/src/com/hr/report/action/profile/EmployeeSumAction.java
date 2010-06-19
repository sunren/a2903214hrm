package com.hr.report.action.profile;

import com.hr.profile.bo.IEmployeeBo;
import com.hr.util.DateUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import org.apache.axis.utils.StringUtils;

public class EmployeeSumAction extends EmployeeReportAction {
    private static final long serialVersionUID = 1L;

    public String execute() throws Exception {
        String result = super.execute();
        ServletRequest request = getRequest();
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        if (!checkDate(startDate, endDate)) {
            return "input";
        }
        String groupBy = request.getParameter("groupBy");

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        List empSumList = empBo.getEmpSumReport(startDate, endDate, groupBy);
        Map params = new HashMap();
        params.put("empSumList", empSumList);
        request.setAttribute("_params", params);
        return result;
    }

    private boolean checkDate(String startDate, String endDate) {
        boolean flag = true;
        String mes = "";
        if (StringUtils.isEmpty(startDate)) {
            mes = mes + "开始时间为空！";
        }
        if (StringUtils.isEmpty(startDate)) {
            mes = mes + "结束时间为空";
        }

        if (DateUtil.parseDateByFormat(startDate, "yyyy-MM-dd")
                .compareTo(DateUtil.parseDateByFormat(endDate, "yyyy-MM-dd")) > 0) {
            mes = mes + "开始时间大于结束时间！";
        }
        if (!"".equals(mes)) {
            addActionError(mes);
            flag = false;
        }
        return flag;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.action.profile.EmployeeSumAction JD-Core Version: 0.5.4
 */