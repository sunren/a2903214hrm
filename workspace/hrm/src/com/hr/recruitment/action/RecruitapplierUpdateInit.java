package com.hr.recruitment.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IJobTitleBo;
import com.hr.configuration.domain.JobTitle;
import com.hr.recruitment.bo.IRecruitapplierBo;
import com.hr.recruitment.bo.IRecruitchannelBo;
import com.hr.recruitment.bo.IRecruitplanBo;
import com.hr.recruitment.domain.Recruitapplier;
import com.hr.recruitment.domain.Recruitchannel;
import com.hr.spring.SpringBeanFactory;
import java.util.List;

public class RecruitapplierUpdateInit extends BaseAction {
    private List RecruitchannelList;
    private List RecruitplanList;
    private Recruitapplier applier;
    private String applierId;
    private String flagFrom;
    private List applierStatus;
    private List<JobTitle> jobTitles;

    public String execute() throws Exception {
        IRecruitchannelBo recruitchannelBO = (IRecruitchannelBo) getBean("channelBo");
        IRecruitplanBo recruitplanBO = (IRecruitplanBo) getBean("recruitplanBO");
        IRecruitapplierBo applierBo = (IRecruitapplierBo) getBean("applierBo");
        this.RecruitplanList = recruitplanBO.getApprovedPlanList();
        this.RecruitchannelList = recruitchannelBO.getObjects(Recruitchannel.class, null);
        this.applierStatus = applierBo.getApplierStatus();
        IJobTitleBo jobTitleBo = (IJobTitleBo) SpringBeanFactory.getBean("jobTitleBo");
        this.jobTitles = jobTitleBo.FindAllJobTitle();
        if ((this.applier != null) && ("".equals(this.applierId)))
            this.applierId = this.applier.getId();
        String[] fetch = { "recaChannel", "recaLastChangeBy", "recaPlan", "recaCreateBy",
                "recaPlan.recpDepartmentNo", "recaPlan.recpType", "recaPlan.recpJobTitle" };
        this.applier = applierBo.loadApplier(this.applierId, fetch);
        if ("PageDetail".equals(this.flagFrom)) {
            return "successForDetail";
        }
        return "success";
    }

    public Recruitapplier getApplier() {
        return this.applier;
    }

    public void setApplier(Recruitapplier applier) {
        this.applier = applier;
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

    public String getFlagFrom() {
        return this.flagFrom;
    }

    public void setFlagFrom(String flagFrom) {
        this.flagFrom = flagFrom;
    }

    public String getApplierId() {
        return this.applierId;
    }

    public void setApplierId(String applierId) {
        this.applierId = applierId;
    }

    public List<JobTitle> getJobTitles() {
        return this.jobTitles;
    }

    public void setJobTitles(List<JobTitle> jobTitles) {
        this.jobTitles = jobTitles;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.recruitment.action.RecruitapplierUpdateInit JD-Core Version: 0.5.4
 */