package com.hr.examin.action;

import com.hr.examin.action.beans.EmailConfirmParamBean;
import com.hr.examin.action.beans.LeaveFormBean;
import javax.servlet.http.HttpSession;

public class EmailHRLeaveSearchAction extends HRLeaveSearchAction {
    private EmailConfirmParamBean param;

    public String hrLeaveApproveOrReject() throws Exception {
        setApproveOper(this.param.getOperation());
        init();
        String rs = super.hrLeaveApproveOrReject();
        getSession().invalidate();
        return rs;
    }

    public void init() {
        LeaveFormBean lf_Bean = new LeaveFormBean();
        lf_Bean.setLrAppComment(this.param.getComments());
        setLf_Bean(lf_Bean);
    }

    public EmailConfirmParamBean getParam() {
        return this.param;
    }

    public void setParam(EmailConfirmParamBean param) {
        this.param = param;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.action.EmailHRLeaveSearchAction JD-Core Version: 0.5.4
 */