package com.hr.recruitment.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.base.Status;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.bo.IEmpTypeBO;
import com.hr.configuration.bo.IJobTitleBo;
import com.hr.configuration.bo.ILocationBO;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.JobTitle;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.recruitment.bo.IRecruitplanBo;
import com.hr.recruitment.domain.Recruitplan;
import com.hr.spring.SpringBeanFactory;
import java.util.List;

public class UpdateRecruitplanInit extends BaseAction implements Status, Constants {
    private List allDept;
    private List emptype;
    private List allLocation;
    private Recruitplan recruitplan;
    private String id;
    private String departmentName;
    private String departmentId;
    private List allStatus;
    private List<JobTitle> jobTitles;

    public String execute() throws Exception {
        IRecruitplanBo recruitplanBo = (IRecruitplanBo) getBean("recruitplanBO");
        if (getRecruitplan() == null) {
            String[] fetch = { "recpDepartmentNo", "recpCreateBy", "recpLastChangeBy", "recpType",
                    "recpJobTitle" };
            this.recruitplan = recruitplanBo.loadRecruitplan(this.id, fetch);
        }

        String[] fetchDept = { "empDeptNo" };
        if (!getCurrentEmpNo().equals(this.recruitplan.getRecpCreateBy().getId())) {
            return "noauth";
        }
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        IDepartmentBO deptbo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        Employee tempEmp = empBo.loadEmp(getCurrentEmpNo(), fetchDept);
        this.departmentName = tempEmp.getEmpDeptNo().getDepartmentName();
        this.departmentId = tempEmp.getEmpDeptNo().getId();
        setAllDept(deptbo.FindEnabledDepartment());
        ILocationBO localbo = (ILocationBO) SpringBeanFactory.getBean("locationBO");
        setAllLocation(localbo.FindEnabledLocation());

        setAllStatus(recruitplanBo.getRecruitplanStatus());
        IEmpTypeBO emptypebo = (IEmpTypeBO) SpringBeanFactory.getBean("emptypeBO");
        setEmptype(emptypebo.FindEnabledEmpType());

        IJobTitleBo jobTitleBo = (IJobTitleBo) SpringBeanFactory.getBean("jobTitleBo");
        this.jobTitles = jobTitleBo.FindEnabledJobTitle();
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

    public String getId() {
        return this.id;
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

    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
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
 * com.hr.recruitment.action.UpdateRecruitplanInit JD-Core Version: 0.5.4
 */