package com.hr.examin.action;

import com.hr.examin.action.beans.LeaveFormBean;
import com.hr.examin.domain.Leaverequest;

public class DeptHRApproveLRAction extends EmpExaminAction {
    private static final long serialVersionUID = 1L;
    private LeaveFormBean lf_Bean;
    private Leaverequest lr;
    private String lrUpdateId;
    private int gmanager;
    private String srcAction;

    public String executeInit() throws Exception {
        if (this.lf_Bean == null)
            this.lf_Bean = new LeaveFormBean();

        this.lr = initLfBean("modify", this.lf_Bean, this.lrUpdateId);
        if (this.lr == null)
            return "error";

        return "success";
    }

    public String getSrcAction() {
        return this.srcAction;
    }

    public void setSrcAction(String srcAction) {
        this.srcAction = srcAction;
    }

    public String getLrUpdateId() {
        return this.lrUpdateId;
    }

    public void setLrUpdateId(String lrUpdateId) {
        this.lrUpdateId = lrUpdateId;
    }

    public LeaveFormBean getLf_Bean() {
        return this.lf_Bean;
    }

    public void setLf_Bean(LeaveFormBean lf_Bean) {
        this.lf_Bean = lf_Bean;
    }

    public Leaverequest getLr() {
        return this.lr;
    }

    public void setLr(Leaverequest lr) {
        this.lr = lr;
    }

    public int getGmanager() {
        return this.gmanager;
    }

    public void setGmanager(int gmanager) {
        this.gmanager = gmanager;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.action.DeptHRApproveLRAction JD-Core Version: 0.5.4
 */