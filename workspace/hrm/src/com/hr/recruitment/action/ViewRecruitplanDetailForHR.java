package com.hr.recruitment.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.base.Status;
import com.hr.recruitment.bo.IRecruitapplierBo;
import com.hr.recruitment.bo.IRecruitplanBo;
import com.hr.recruitment.domain.Recruitapplier;
import com.hr.recruitment.domain.Recruitplan;
import java.util.List;

public class ViewRecruitplanDetailForHR extends BaseAction implements Status, Constants {
    private Recruitplan plan;
    private String id;
    private int factNumber;
    private List applierStatus;
    private List applierList;
    private Recruitapplier applier;
    private int errorFlag;

    public ViewRecruitplanDetailForHR() {
        this.errorFlag = 1;
    }

    public String getRecpStatus(int no) {
        IRecruitplanBo recruitplanBo = (IRecruitplanBo) getBean("recruitplanBO");
        return recruitplanBo.findStatusByRecpStatusNo(no);
    }

    public String execute() throws Exception {
        IRecruitplanBo recruitplanBo = (IRecruitplanBo) getBean("recruitplanBO");
        String[] fetch = { "recpDepartmentNo", "recpCreateBy", "recpLastChangeBy", "recpType",
                "recpWorkLocation", "recpJobTitle" };
        if (this.errorFlag == 0) {
            clearErrorsAndMessages();
        }
        this.plan = recruitplanBo.loadRecruitplan(this.id, fetch);
        this.plan.setRecpJobDesc(this.plan.getRecpJobDesc().replaceAll("\r\n", "<br>"));
        this.plan.setRecpComments(this.plan.getRecpComments().replaceAll("\r\n", "<br>"));
        this.plan.setRecpJobSkill(this.plan.getRecpJobSkill().replaceAll("\r\n", "<br>"));
        this.plan.setRecpLanguageSkill(this.plan.getRecpLanguageSkill().replaceAll("\r\n", "<br>"));
        IRecruitapplierBo applierBo = (IRecruitapplierBo) getBean("applierBo");
        this.applierStatus = applierBo.getApplierStatus();
        this.applierList = applierBo.searchApplier(this.applier, null, this.id);
        this.factNumber = applierBo.getNumberOfApplierInPlan(this.id).intValue();
        return "success";
    }

    public Recruitplan getPlan() {
        return this.plan;
    }

    public void setPlan(Recruitplan plan) {
        this.plan = plan;
    }

    public String getId() {
        if (this.id == null) {
            return this.plan.getId();
        }
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Recruitapplier getApplier() {
        return this.applier;
    }

    public void setApplier(Recruitapplier applier) {
        this.applier = applier;
    }

    public List getApplierList() {
        return this.applierList;
    }

    public void setApplierList(List applierList) {
        this.applierList = applierList;
    }

    public List getApplierStatus() {
        return this.applierStatus;
    }

    public void setApplierStatus(List applierStatus) {
        this.applierStatus = applierStatus;
    }

    public int getFactNumber() {
        return this.factNumber;
    }

    public void setFactNumber(int factNumber) {
        this.factNumber = factNumber;
    }

    public int getErrorFlag() {
        return this.errorFlag;
    }

    public void setErrorFlag(int errorFlag) {
        this.errorFlag = errorFlag;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.recruitment.action.ViewRecruitplanDetailForHR JD-Core Version: 0.5.4
 */