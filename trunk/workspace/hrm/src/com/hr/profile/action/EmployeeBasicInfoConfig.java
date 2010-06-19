package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.compensation.bo.IEmpSalaryAcctBo;
import com.hr.compensation.domain.Empsalaryacct;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.bo.IEmpTypeBO;
import com.hr.configuration.bo.IJobTitleBo;
import com.hr.configuration.bo.IJobgradeBO;
import com.hr.configuration.bo.ILocationBO;
import com.hr.configuration.domain.Jobgrade;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import java.util.List;

public class EmployeeBasicInfoConfig extends BaseAction {
    private List emptypeList;
    private List allLocation;
    private List allDept;
    private List allBusinessUnit;
    private List allJobTitle;
    private List allGroup;
    private List<Empsalaryacct> empsalaryacctList;
    private List<Jobgrade> jobgradeList;

    public String execute() throws Exception {
        ILocationBO localbo = (ILocationBO) SpringBeanFactory.getBean("locationBO");
        this.allLocation = localbo.FindAllLocation();

        IDepartmentBO deptbo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        this.allDept = deptbo.FindAllDepartment();

        IEmpTypeBO bo = (IEmpTypeBO) SpringBeanFactory.getBean("emptypeBO");
        this.emptypeList = bo.FindAllEmpType();

        IJobTitleBo jobTitleBo = (IJobTitleBo) SpringBeanFactory.getBean("jobTitleBo");
        this.allJobTitle = jobTitleBo.FindAllJobTitle();

        IEmpSalaryAcctBo empSalaryAcctBo = (IEmpSalaryAcctBo) getBean("empsalaryacctBo");
        this.empsalaryacctList = empSalaryAcctBo.getObjects(Empsalaryacct.class, null);

        IJobgradeBO jobgradeBo = (IJobgradeBO) SpringBeanFactory.getBean("jobgradeBO");
        this.jobgradeList = jobgradeBo.FindAllJobgrade();
        return "success";
    }

    public String getEmpNameById(String empId) {
        if ((empId == null) || (empId.equals(""))) {
            return null;
        }

        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");

        if (empId.indexOf(",") >= 0) {
            String[] ids = empId.split(",");
            String name = empBo.loadEmp(ids[0], null).getEmpName();
            return name + ",...";
        }
        return empBo.loadEmp(empId, null).getEmpName();
    }

    public List getAllBusinessUnit() {
        return this.allBusinessUnit;
    }

    public void setAllBusinessUnit(List allBusinessUnit) {
        this.allBusinessUnit = allBusinessUnit;
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

    public List getEmptypeList() {
        return this.emptypeList;
    }

    public void setEmptypeList(List emptypeList) {
        this.emptypeList = emptypeList;
    }

    public List getAllJobTitle() {
        return this.allJobTitle;
    }

    public void setAllJobTitle(List allJobTitle) {
        this.allJobTitle = allJobTitle;
    }

    public List getAllGroup() {
        return this.allGroup;
    }

    public void setAllGroup(List allGroup) {
        this.allGroup = allGroup;
    }

    public List<Empsalaryacct> getEmpsalaryacctList() {
        return this.empsalaryacctList;
    }

    public void setEmpsalaryacctList(List<Empsalaryacct> empsalaryacctList) {
        this.empsalaryacctList = empsalaryacctList;
    }

    public List<Jobgrade> getJobgradeList() {
        return this.jobgradeList;
    }

    public void setJobgradeList(List<Jobgrade> jobgradeList) {
        this.jobgradeList = jobgradeList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.EmployeeBasicInfoConfig JD-Core Version: 0.5.4
 */