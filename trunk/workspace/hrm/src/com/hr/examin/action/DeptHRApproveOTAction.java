package com.hr.examin.action;

import com.hr.examin.action.beans.OvertimeFormBean;
import com.hr.examin.domain.Overtimerequest;
import java.util.Date;

public class DeptHRApproveOTAction extends EmpExaminAction {
    private static final long serialVersionUID = 1L;
    private OvertimeFormBean of_Bean;
    private Overtimerequest otr;
    private String orIdUp;
    private int gmanager;
    private String srcAction;
    private String infoMeg;
    private String isTiaoxiu;
    private String tiaoXiuTime;
    private Date otTiaoxiuExpire;
    private Integer expireTiaoxiu;

    public DeptHRApproveOTAction() {
        this.expireTiaoxiu = Integer.valueOf(0);
    }

    public String showOT_M() throws Exception {
        if (this.of_Bean == null)
            this.of_Bean = new OvertimeFormBean();

        this.otr = initOfBean("modify", this.of_Bean, this.orIdUp);
        if (this.otr == null)
            return "error";

        return "success";
    }

    public String showOT_HR() throws Exception {
        if (this.of_Bean == null)
            this.of_Bean = new OvertimeFormBean();

        this.otr = initOfBean("modify", this.of_Bean, this.orIdUp);
        if (this.otr == null)
            return "error";

        return "success";
    }

    public String getOrIdUp() {
        return this.orIdUp;
    }

    public void setOrIdUp(String orIdUp) {
        this.orIdUp = orIdUp;
    }

    public OvertimeFormBean getOf_Bean() {
        return this.of_Bean;
    }

    public void setOf_Bean(OvertimeFormBean of_Bean) {
        this.of_Bean = of_Bean;
    }

    public Overtimerequest getOtr() {
        return this.otr;
    }

    public void setOtr(Overtimerequest otr) {
        this.otr = otr;
    }

    public String getSrcAction() {
        return this.srcAction;
    }

    public void setSrcAction(String srcAction) {
        this.srcAction = srcAction;
    }

    public String getInfoMeg() {
        return this.infoMeg;
    }

    public void setInfoMeg(String infoMeg) {
        this.infoMeg = infoMeg;
    }

    public int getGmanager() {
        return this.gmanager;
    }

    public void setGmanager(int gmanager) {
        this.gmanager = gmanager;
    }

    public String getIsTiaoxiu() {
        return this.isTiaoxiu;
    }

    public void setIsTiaoxiu(String isTiaoxiu) {
        this.isTiaoxiu = isTiaoxiu;
    }

    public String getTiaoXiuTime() {
        return this.tiaoXiuTime;
    }

    public void setTiaoXiuTime(String tiaoXiuTime) {
        this.tiaoXiuTime = tiaoXiuTime;
    }

    public Date getOtTiaoxiuExpire() {
        return this.otTiaoxiuExpire;
    }

    public void setOtTiaoxiuExpire(Date otTiaoxiuExpire) {
        this.otTiaoxiuExpire = otTiaoxiuExpire;
    }

    public Integer getExpireTiaoxiu() {
        return this.expireTiaoxiu;
    }

    public void setExpireTiaoxiu(Integer expireTiaoxiu) {
        this.expireTiaoxiu = expireTiaoxiu;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.action.DeptHRApproveOTAction JD-Core Version: 0.5.4
 */