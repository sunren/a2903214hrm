package com.hr.profile.bo;

import com.hr.configuration.domain.Department;
import com.hr.profile.domain.EmpHistOrg;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.PositionBase;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract interface IEmpHistOrgBo {
    public abstract List exeHqlList(String paramString);

    public abstract int getEhosNum(Date paramDate, String paramString);

    public abstract Employee getDeptHead(Date paramDate, String paramString);

    public abstract Map<String, EmpHistOrg> getEmphistorgOfDept(List<Department> paramList);

    public abstract EmpHistOrg getLatestEmphistorg(Employee paramEmployee);

    public abstract boolean isExistPb(String paramString);

    public abstract void saveEmpHistOrg(Employee paramEmployee, Department paramDepartment,
            PositionBase paramPositionBase);

    public abstract void saveLatestEmpHistOrg(Employee paramEmployee, Department paramDepartment,
            PositionBase paramPositionBase);

    public abstract List<EmpHistOrg> gernerateEmpHistOrg(Employee paramEmployee,
            Department paramDepartment, PositionBase paramPositionBase, Date paramDate);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.IEmpHistOrgBo JD-Core Version: 0.5.4
 */