package com.hr.profile.action;

import com.hr.base.BaseAction;
import javax.servlet.http.HttpSession;

public class CreateBasicInfo extends BaseAction {
    private String empNo;

    public String execute() throws Exception {
        if ((this.empNo != null) && (this.empNo.trim().length() > 0)) {
            getSession().setAttribute("curr_oper_no", this.empNo);
        } else {
            getSession().removeAttribute("curr_oper_no");
        }
        return "success";
    }

    public String getEmpNo() {
        return this.empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.CreateBasicInfo JD-Core Version: 0.5.4
 */