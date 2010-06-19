package com.hr.examin.action;

import com.hr.examin.action.beans.OvertimeFormBean;
import com.hr.examin.domain.Overtimerequest;

public class EmpOvertimeUpdAction extends EmpExaminAction {
    private static final long serialVersionUID = 1L;
    private OvertimeFormBean of_Bean;
    private Overtimerequest otr;
    private String orIdUp;
    private String overLimit;
    private String infoMeg;
    private String errorMsg;
    private String status;

    public String executeInit() throws Exception {
        if (this.of_Bean == null)
            this.of_Bean = new OvertimeFormBean();

        this.otr = initOfBean("update", this.of_Bean, this.orIdUp);
        if (this.otr == null)
            return "error";

        return "success";
    }

    public String execute() throws Exception {
        String operate = "update";
        String info = this.authorityCondition;

        if ("ALL".equals(info))
            operate = "gm-update";
        if ("HR".equals(info))
            operate = "hr-update";
        if ("SUB".equals(info))
            operate = "mgr-update";

        return wfObjOpSingle(operate, this.of_Bean, this.orIdUp);
    }

    public String getOrIdUp() {
        return this.orIdUp;
    }

    public void setOrIdUp(String orIdUp) {
        this.orIdUp = orIdUp;
    }

    public String getOverLimit() {
        return this.overLimit;
    }

    public void setOverLimit(String overLimit) {
        this.overLimit = overLimit;
    }

    public OvertimeFormBean getOf_Bean() {
        return this.of_Bean;
    }

    public void setOf_Bean(OvertimeFormBean of_Bean) {
        this.of_Bean = of_Bean;
    }

    public String getInfoMeg() {
        return this.infoMeg;
    }

    public void setInfoMeg(String infoMeg) {
        this.infoMeg = infoMeg;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Overtimerequest getOtr() {
        return this.otr;
    }

    public void setOtr(Overtimerequest otr) {
        this.otr = otr;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.action.EmpOvertimeUpdAction JD-Core Version: 0.5.4
 */