package com.hr.report.action.profile;

import com.hr.base.BaseAction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletRequest;
import org.apache.commons.lang.StringUtils;

public class EmployeeReportAction extends BaseAction {
    private String reportFileLocation;
    private String reportFormat;

    public EmployeeReportAction() {
        this.reportFileLocation = "";

        this.reportFormat = "HTML";
    }

    public String execute() throws Exception {
        if (StringUtils.isEmpty(this.reportFileLocation)) {
            addErrorInfo("错误的请求参数！");
            return "input";
        }

        ServletRequest request = getRequest();

        if ("PDF".equalsIgnoreCase(this.reportFormat)) {
            request.setAttribute("_format", "PDF");
            request.setAttribute("_contentType", "attachment");
            request.setAttribute("_fileName", "empReport");
        } else {
            request.setAttribute("_format", "HTML");
        }

        Map params = new HashMap();
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        params.put("startDate", handleDate(startDate));
        params.put("endDate", handleDate(endDate));
        request.setAttribute("_params", params);

        request.setAttribute("_report", this.reportFileLocation);
        return "success";
    }

    public String getReportFileLocation() {
        return this.reportFileLocation;
    }

    public void setReportFileLocation(String reportFileLocation) {
        this.reportFileLocation = reportFileLocation;
    }

    public String getReportFormat() {
        return this.reportFormat;
    }

    public void setReportFormat(String reportFormat) {
        this.reportFormat = reportFormat;
    }

    protected String handleDate(String dateString) {
        String result = "";
        Calendar now = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isEmpty(StringUtils.trim(dateString))) {
            now.set(5, 1);
            result = format.format(now.getTime());
        } else {
            try {
                format.parse(dateString);
                result = dateString;
            } catch (ParseException e) {
                now.set(5, 1);
                result = format.format(now);
            }
        }
        return result;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.action.profile.EmployeeReportAction JD-Core Version: 0.5.4
 */