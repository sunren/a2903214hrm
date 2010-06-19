package com.hr.examin.action;

import com.hr.examin.action.beans.OvertimeFormBean;

public class EmpOvertimeAddAction extends EmpExaminAction {
    private static final long serialVersionUID = 1L;
    private OvertimeFormBean of_Bean;
    private String infoMeg;

    public String executeInit() throws Exception {
        if (this.of_Bean == null)
            this.of_Bean = new OvertimeFormBean();

        initOfBean("create", this.of_Bean, null);

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

        return wfObjOpSingle(operate, this.of_Bean, null);
    }

    public OvertimeFormBean getOf_Bean() {
        return this.of_Bean;
    }

    public void setOf_Bean(OvertimeFormBean Of_Bean) {
        this.of_Bean = Of_Bean;
    }

    public String getInfoMeg() {
        return this.infoMeg;
    }

    public void setInfoMeg(String infoMeg) {
        this.infoMeg = infoMeg;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.action.EmpOvertimeAddAction JD-Core Version: 0.5.4
 */