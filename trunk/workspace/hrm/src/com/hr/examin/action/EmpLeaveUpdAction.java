package com.hr.examin.action;

import com.hr.examin.action.beans.ExaminSearchBean;
import com.hr.examin.action.beans.LeaveFormBean;
import com.hr.examin.domain.Leaverequest;

public class EmpLeaveUpdAction extends EmpExaminAction {
    private static final long serialVersionUID = 1L;
    private LeaveFormBean lf_Bean;
    private Leaverequest lr;
    private String lrUpdateId;
    private String srcAction;
    private ExaminSearchBean es_Bean;

    public String executeInit() throws Exception {
        if (this.lf_Bean == null)
            this.lf_Bean = new LeaveFormBean();

        this.lr = initLfBean("update", this.lf_Bean, this.lrUpdateId);
        if (this.lr == null)
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

        return wfObjOpSingle(operate, this.lf_Bean, this.lrUpdateId);
    }

    public LeaveFormBean getLf_Bean() {
        return this.lf_Bean;
    }

    public void setLf_Bean(LeaveFormBean lf_Bean) {
        this.lf_Bean = lf_Bean;
    }

    public String getLrUpdateId() {
        return this.lrUpdateId;
    }

    public void setLrUpdateId(String lrUpdateId) {
        this.lrUpdateId = lrUpdateId;
    }

    public String getSrcAction() {
        return this.srcAction;
    }

    public void setSrcAction(String srcAction) {
        this.srcAction = srcAction;
    }

    public ExaminSearchBean getEs_Bean() {
        return this.es_Bean;
    }

    public void setEs_Bean(ExaminSearchBean es_Bean) {
        this.es_Bean = es_Bean;
    }

    public Leaverequest getLr() {
        return this.lr;
    }

    public void setLr(Leaverequest lr) {
        this.lr = lr;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.action.EmpLeaveUpdAction JD-Core Version: 0.5.4
 */