package com.hr.recruitment.action;

import com.hr.base.BaseAction;
import com.hr.compensation.bo.ICompaplanBo;
import com.hr.configuration.bo.IJobTitleBo;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.JobTitle;
import com.hr.configuration.domain.Location;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.recruitment.bo.IRecruitplanBo;
import com.hr.recruitment.domain.Recruitplan;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.Pager;
import java.util.List;

public class SearchRecruitplanForHR extends BaseAction {
    private List recruitplanList;
    private Pager page;
    private Recruitplan plan;
    private List allLocation;
    private List allDept;
    private String changIds;
    private String id;
    private String viewAll;
    private List allStatus;
    private List<JobTitle> jobTitles;

    public SearchRecruitplanForHR() {
        this.page = new Pager();
    }

    public String getRecpStatus(int no) {
        IRecruitplanBo recruitplanBo = (IRecruitplanBo) getBean("recruitplanBO");
        return recruitplanBo.findStatusByRecpStatusNo(no);
    }

    public String execute() throws Exception {
        if (this.plan == null) {
            this.plan = new Recruitplan();
        }
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        if ((this.authorityCondition != null) && (this.authorityCondition.equalsIgnoreCase("DEPT"))) {
            this.plan.setDepts(getCurrentEmp().getDeptInChargeOld());
        }

        ICompaplanBo compaplanBo = (ICompaplanBo) getBean("compaplanBo");
        IRecruitplanBo recruitplanBo = (IRecruitplanBo) getBean("recruitplanBO");
        setAllLocation(compaplanBo.getObjects(Location.class, null));
        setAllDept(compaplanBo.getObjects(Department.class, null));
        setAllStatus(recruitplanBo.getRecruitplanStatus());

        IJobTitleBo jobTitleBo = (IJobTitleBo) SpringBeanFactory.getBean("jobTitleBo");
        this.jobTitles = jobTitleBo.FindAllJobTitle();

        if ("close".equalsIgnoreCase(this.page.getOperate())) {
            if ((this.authorityCondition != null)
                    && (!this.authorityCondition.equalsIgnoreCase("ADM"))) {
                return "noauth";
            }
            List ls = recruitplanBo.update_closeRecruitplan(this.id, getCurrentEmpNo());
            if ((ls == null) || (ls.size() < 1)) {
                addSuccessInfo("关闭招聘计划成功");
            } else {
                return "error";
            }
        }
        setRecruitplanList(recruitplanBo.searchRecruitplanforHR(this.plan, this.page, this.viewAll));
        return "success";
    }

    public String getChangIds() {
        return this.changIds;
    }

    public void setChangIds(String changIds) {
        this.changIds = changIds;
    }

    public List getAllLocation() {
        return this.allLocation;
    }

    public void setAllLocation(List allLocation) {
        this.allLocation = allLocation;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public List getRecruitplanList() {
        return this.recruitplanList;
    }

    public void setRecruitplanList(List recruitplanList) {
        this.recruitplanList = recruitplanList;
    }

    public Recruitplan getPlan() {
        return this.plan;
    }

    public void setPlan(Recruitplan plan) {
        this.plan = plan;
    }

    public List getAllDept() {
        return this.allDept;
    }

    public void setAllDept(List allDept) {
        this.allDept = allDept;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getViewAll() {
        return this.viewAll;
    }

    public void setViewAll(String viewAll) {
        this.viewAll = viewAll;
    }

    public List getAllStatus() {
        return this.allStatus;
    }

    public void setAllStatus(List allStatus) {
        this.allStatus = allStatus;
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
 * com.hr.recruitment.action.SearchRecruitplanForHR JD-Core Version: 0.5.4
 */