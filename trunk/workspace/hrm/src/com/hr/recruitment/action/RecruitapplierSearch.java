package com.hr.recruitment.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.bo.IJobTitleBo;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.JobTitle;
import com.hr.profile.domain.Employee;
import com.hr.recruitment.bo.IRecruitapplierBo;
import com.hr.recruitment.bo.IRecruitchannelBo;
import com.hr.recruitment.domain.Recruitapplier;
import com.hr.recruitment.domain.Recruitchannel;
import com.hr.recruitment.domain.Recruitplan;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.Pager;
import java.util.List;

public class RecruitapplierSearch extends BaseAction {
    private List RecruitchannelList;
    private List<Recruitapplier> applierList;
    private Recruitapplier applier;
    private List applierStatus;
    private List applierDepartment;
    private List allDepartment;
    private List<JobTitle> jobTitles;
    private Pager page;

    public RecruitapplierSearch() {
        this.page = new Pager();
    }

    public String execute() throws Exception {
        IRecruitapplierBo applierBo = (IRecruitapplierBo) getBean("applierBo");
        if (this.applier == null) {
            this.applier = new Recruitapplier();
        }

        if ("DEPT".equalsIgnoreCase(this.authorityCondition)) {
            this.applier.getRecaPlan().getRecpDepartmentNo().setId(getCurrentEmp().getDeptNo());
        }
        IRecruitchannelBo recruitchannelBO = (IRecruitchannelBo) getBean("channelBo");
        IDepartmentBO bo = (IDepartmentBO) getBean("departmentBO");
        this.allDepartment = bo.FindAllDepartment();
        this.RecruitchannelList = recruitchannelBO.getObjects(Recruitchannel.class, null);
        this.applierList = applierBo.searchApplierWithoutId(this.applier, this.page);
        IJobTitleBo jobTitleBo = (IJobTitleBo) SpringBeanFactory.getBean("jobTitleBo");
        this.jobTitles = jobTitleBo.FindAllJobTitle();

        int sizeOfApplierList = this.applierList.size();
        for (int i = 0; i < sizeOfApplierList; ++i) {
            Recruitplan TempPlan = ((Recruitapplier) this.applierList.get(i)).getRecaPlan();
            TempPlan.setRecpJobDesc((TempPlan.getRecpJobDesc() != null) ? TempPlan.getRecpJobDesc()
                    .replaceAll("\r\n", "<br>") : " ");
            TempPlan.setRecpComments((TempPlan.getRecpComments() != null) ? TempPlan
                    .getRecpComments().replaceAll("\r\n", "<br>") : " ");
            TempPlan.setRecpJobSkill((TempPlan.getRecpJobSkill() != null) ? TempPlan
                    .getRecpJobSkill().replaceAll("\r\n", "<br>") : " ");
            TempPlan.setRecpLanguageSkill((TempPlan.getRecpLanguageSkill() != null) ? TempPlan
                    .getRecpLanguageSkill().replaceAll("\r\n", "<br>") : " ");
            ((Recruitapplier) this.applierList.get(i)).setRecaPlan(TempPlan);
        }

        this.applierDepartment = applierBo.getAllAplierDepartment();
        this.applierStatus = applierBo.getApplierStatus();
        return "success";
    }

    public List getRecruitchannelList() {
        return this.RecruitchannelList;
    }

    public void setRecruitchannelList(List recruitchannelList) {
        this.RecruitchannelList = recruitchannelList;
    }

    public List getApplierList() {
        return this.applierList;
    }

    public void setApplierList(List applierList) {
        this.applierList = applierList;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public Recruitapplier getApplier() {
        return this.applier;
    }

    public void setApplier(Recruitapplier applier) {
        this.applier = applier;
    }

    public List getApplierStatus() {
        return this.applierStatus;
    }

    public void setApplierStatus(List applierStatus) {
        this.applierStatus = applierStatus;
    }

    public List getApplierDepartment() {
        return this.applierDepartment;
    }

    public void setApplierDepartment(List applierDepartment) {
        this.applierDepartment = applierDepartment;
    }

    public List getAllDepartment() {
        return this.allDepartment;
    }

    public void setAllDepartment(List allDepartment) {
        this.allDepartment = allDepartment;
    }

    public List<JobTitle> getJobTitles() {
        return this.jobTitles;
    }

    public void setJobTitles(List<JobTitle> jobTitles) {
        this.jobTitles = jobTitles;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.recruitment.action.RecruitapplierSearch JD-Core Version: 0.5.4
 */