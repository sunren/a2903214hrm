package com.hr.report.action.userinfos;

import com.hr.base.BaseAction;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class UserInfoAction extends BaseAction {
    private static final long serialVersionUID = -1451122398244075965L;

    public String execute() throws Exception {
        ServletRequest request = getRequest();

        if ((getRequest().getParameter("_format") != null)
                && (getRequest().getParameter("_format").equals("pdf")))
            request.setAttribute("_format", "PDF");
        else {
            request.setAttribute("_format", "HTML");
        }

        Map params = new HashMap();

        request.setAttribute("_params", params);

        request.setAttribute("_report", "/report/roles/UserInfoAll.rptdesign");
        return "success";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.action.userinfos.UserInfoAction JD-Core Version: 0.5.4
 */