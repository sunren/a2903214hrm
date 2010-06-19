package com.hr.examin.action;

import com.hr.examin.action.beans.LeaveFormBean;

public class EmpLeaveAddAction extends EmpExaminAction {
    private static final long serialVersionUID = 1L;
    private LeaveFormBean lf_Bean;
    private String infoMeg;

    public String executeInit() throws Exception {
        if (this.lf_Bean == null)
            this.lf_Bean = new LeaveFormBean();

        initLfBean("create", this.lf_Bean, null);

        return "success";
    }

    public String execute() throws Exception {
        String operate = "create";
        String info = this.authorityCondition;

        if ("ALL".equals(info))
            operate = "gm-create";
        if ("HR".equals(info))
            operate = "hr-create";
        if ("SUB".equals(info))
            operate = "mgr-create";

        return wfObjOpSingle(operate, this.lf_Bean, null);
    }

    public LeaveFormBean getLf_Bean() {
        return this.lf_Bean;
    }

    public void setLf_Bean(LeaveFormBean lf_Bean) {
        this.lf_Bean = lf_Bean;
    }

    public String getInfoMeg() {
        return this.infoMeg;
    }

    public void setInfoMeg(String infoMeg) {
        this.infoMeg = infoMeg;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.action.EmpLeaveAddAction JD-Core Version: 0.5.4
 */