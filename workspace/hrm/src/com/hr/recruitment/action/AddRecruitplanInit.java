package com.hr.recruitment.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.bo.IEmpTypeBO;
import com.hr.configuration.bo.IJobTitleBo;
import com.hr.configuration.bo.ILocationBO;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.JobTitle;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.recruitment.domain.Recruitplan;
import com.hr.spring.SpringBeanFactory;
import java.util.Date;
import java.util.List;

public class AddRecruitplanInit extends BaseAction {
    private List allDept;
    private List emptype;
    private List allLocation;
    private Recruitplan recruitplan;
    private String departmentName;
    private String departmentId;
    private List<JobTitle> jobTitles;

    public AddRecruitplanInit() {
        this.recruitplan = new Recruitplan();
    }

    public String execute() throws Exception {
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");

        String[] fetch = { "empDeptNo" };
        Employee tmpEmp = empBo.loadEmp(getCurrentEmpNo(), fetch);
        if ((tmpEmp != null) && (tmpEmp.getEmpDeptNo() != null)) {
            this.departmentName = tmpEmp.getEmpDeptNo().getDepartmentName();
            this.departmentId = tmpEmp.getEmpDeptNo().getId();
        }
        IDepartmentBO deptbo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        setAllDept(deptbo.FindEnabledDepartment());
        ILocationBO localbo = (ILocationBO) SpringBeanFactory.getBean("locationBO");
        setAllLocation(localbo.FindEnabledLocation());
        IEmpTypeBO emptypebo = (IEmpTypeBO) SpringBeanFactory.getBean("emptypeBO");
        setEmptype(emptypebo.FindEnabledEmpType());
        this.recruitplan.setRecpStartDate(new Date());

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

    public List getAllLocation() {
        return this.allLocation;
    }

    public void setAllLocation(List allLocation) {
        this.allLocation = allLocation;
    }

    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List getEmptype() {
        return this.emptype;
    }

    public void setEmptype(List emptype) {
        this.emptype = emptype;
    }

    public List getAllDept() {
        return this.allDept;
    }

    public void setAllDept(List allDept) {
        this.allDept = allDept;
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
 * com.hr.recruitment.action.AddRecruitplanInit JD-Core Version: 0.5.4
 */