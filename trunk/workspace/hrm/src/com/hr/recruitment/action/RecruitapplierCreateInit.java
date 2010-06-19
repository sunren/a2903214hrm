package com.hr.recruitment.action;

import com.hr.base.BaseAction;
import com.hr.recruitment.bo.IRecruitapplierBo;
import com.hr.recruitment.bo.IRecruitchannelBo;
import com.hr.recruitment.bo.IRecruitplanBo;
import com.hr.recruitment.domain.Recruitapplier;
import com.hr.recruitment.domain.Recruitchannel;
import com.hr.recruitment.domain.Recruitplan;
import java.util.List;

public class RecruitapplierCreateInit extends BaseAction {
    private List RecruitchannelList;
    private List RecruitplanList;
    private List applierStatus;
    private Recruitapplier applier;
    private String planID;
    private Recruitplan plan;
    private int show;

    public RecruitapplierCreateInit() {
        this.applier = new Recruitapplier();
    }

    public String execute() throws Exception {
        IRecruitchannelBo recruitchannelBO = (IRecruitchannelBo) getBean("channelBo");
        IRecruitplanBo recruitplanBO = (IRecruitplanBo) getBean("recruitplanBO");
        IRecruitapplierBo applierBo = (IRecruitapplierBo) getBean("applierBo");
        if (this.planID != null) {
            this.plan = recruitplanBO.loadRecruitplan(this.planID, null);
            this.applier.setRecaPlan(this.plan);
        }
        this.RecruitplanList = recruitplanBO.getApprovedPlanList();
        this.RecruitchannelList = recruitchannelBO.getObjects(Recruitchannel.class, null);
        this.applierStatus = applierBo.getApplierStatus();
        if (this.planID == null) {
            return "success";
        }

        return "successForDetail";
    }

    public List chooseRecruitplanList(String condition) {
        IRecruitplanBo recruitplanBO = (IRecruitplanBo) getBean("recruitplanBO");
        return recruitplanBO.searchApprovePlanWithCondition(condition);
    }

    public List getRecruitchannelList() {
        return this.RecruitchannelList;
    }

    public void setRecruitchannelList(List recruitchannelList) {
        this.RecruitchannelList = recruitchannelList;
    }

    public List getRecruitplanList() {
        return this.RecruitplanList;
    }

    public void setRecruitplanList(List recruitplanList) {
        this.RecruitplanList = recruitplanList;
    }

    public List getApplierStatus() {
        return this.applierStatus;
    }

    public void setApplierStatus(List applierStatus) {
        this.applierStatus = applierStatus;
    }

    public String getPlanID() {
        return this.planID;
    }

    public void setPlanID(String planID) {
        this.planID = planID;
    }

    public Recruitapplier getApplier() {
        return this.applier;
    }

    public void setApplier(Recruitapplier applier) {
        this.applier = applier;
    }

    public int getShow() {
        return this.show;
    }

    public void setShow(int show) {
        this.show = show;
    }

    public Recruitplan getPlan() {
        return this.plan;
    }

    public void setPlan(Recruitplan plan) {
        this.plan = plan;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.recruitment.action.RecruitapplierCreateInit JD-Core Version: 0.5.4
 */