package com.hr.recruitment.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.base.Status;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.bo.IEmpTypeBO;
import com.hr.configuration.bo.IJobTitleBo;
import com.hr.configuration.bo.ILocationBO;
import com.hr.configuration.domain.JobTitle;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.recruitment.bo.IRecruitplanBo;
import com.hr.recruitment.domain.Recruitplan;
import com.hr.spring.SpringBeanFactory;
import java.util.List;

public class UpdateRecruitplanInitADM extends BaseAction implements Status, Constants {
    private List allDept;
    private List emptype;
    private List allLocation;
    private Recruitplan recruitplan;
    private String id;
    private List allStatus;
    private List<JobTitle> jobTitles;

    public String execute() throws Exception {
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        IDepartmentBO deptbo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        IRecruitplanBo recruitplanBo = (IRecruitplanBo) getBean("recruitplanBO");
        setAllDept(deptbo.FindEnabledDepartment());
        ILocationBO localbo = (ILocationBO) SpringBeanFactory.getBean("locationBO");
        setAllLocation(localbo.FindEnabledLocation());
        setAllStatus(recruitplanBo.getRecruitplanStatus());
        IEmpTypeBO emptypebo = (IEmpTypeBO) SpringBeanFactory.getBean("emptypeBO");
        setEmptype(emptypebo.FindEnabledEmpType());

        IJobTitleBo jobTitleBo = (IJobTitleBo) SpringBeanFactory.getBean("jobTitleBo");
        this.jobTitles = jobTitleBo.FindEnabledJobTitle();
        if (getRecruitplan() == null) {
            String[] fetch = { "recpDepartmentNo", "recpCreateBy", "recpLastChangeBy", "recpType",
                    "recpJobTitle" };
            this.recruitplan = recruitplanBo.loadRecruitplan(this.id, fetch);
        }
        return "success";
    }

    public Recruitplan getRecruitplan() {
        return this.recruitplan;
    }

    public void setRecruitplan(Recruitplan recruitplan) {
        this.recruitplan = recruitplan;
    }

    public List getAllDept() {
        return this.allDept;
    }

    public void setAllDept(List allDept) {
        this.allDept = allDept;
    }

    public List getAllLocation() {
        return this.allLocation;
    }

    public void setAllLocation(List allLocation) {
        this.allLocation = allLocation;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List getAllStatus() {
        return this.allStatus;
    }

    public void setAllStatus(List allStatus) {
        this.allStatus = allStatus;
    }

    public List getEmptype() {
        return this.emptype;
    }

    public void setEmptype(List emptype) {
        this.emptype = emptype;
    }

    public String getId() {
        return this.id;
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
 * com.hr.recruitment.action.UpdateRecruitplanInitADM JD-Core Version: 0.5.4
 */