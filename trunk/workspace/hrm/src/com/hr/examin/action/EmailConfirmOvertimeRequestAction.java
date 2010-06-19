package com.hr.examin.action;

import com.hr.examin.action.beans.EmailConfirmParamBean;
import com.hr.examin.action.beans.OvertimeFormBean;

public class EmailConfirmOvertimeRequestAction extends DeptOvertimeSearchAction {
    private EmailConfirmParamBean param;

    public String deptOtApproveOrReject() throws Exception {
        setApproveOper(this.param.getOperation());
        init();
        return super.deptOtApproveOrReject();
    }

    public void init() {
        setSrcAction("deptOvertimeSearch");

        OvertimeFormBean of_Bean = new OvertimeFormBean();
        of_Bean.setOrAppComment(this.param.getComments());
        setOf_Bean(of_Bean);
    }

    public EmailConfirmParamBean getParam() {
        return this.param;
    }

    public void setParam(EmailConfirmParamBean param) {
        this.param = param;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.action.EmailConfirmOvertimeRequestAction JD-Core Version: 0.5.4
 */