package com.hr.report.action.compensation;

import com.hr.base.BaseAction;
import com.hr.compensation.bo.IEmpsalarydatadefBo;
import com.hr.compensation.bo.IEmpsalaryperiodBo;
import com.hr.compensation.bo.ISalaryPaidBo;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.domain.Jobgrade;
import com.hr.spring.SpringBeanFactory;
import java.util.Calendar;
import java.util.List;

public class SalaryReportInit extends BaseAction {
    private List items;
    private List departments;
    private List<String> years;
    private List jobgrades;
    private String year;
    private String month;

    public String execute() throws Exception {
        IEmpsalarydatadefBo defBo = (IEmpsalarydatadefBo) getBean("empsalarydatadefBo");
        this.items = defBo.searchAll();

        IEmpsalaryperiodBo salaryperiod = (IEmpsalaryperiodBo) getBean("empsalaryperiodBO");
        this.years = salaryperiod.getAllYear();

        ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) getBean("salaryPaidBo");
        IDepartmentBO deptbo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        this.departments = deptbo.FindEnabledDepartment();

        this.jobgrades = salaryPaidBo.getObjects(Jobgrade.class, null);

        Calendar now = Calendar.getInstance();
        this.year = String.valueOf(now.get(1));
        int tmpMonth = now.get(2) + 1;
        this.month = String.valueOf("0" + tmpMonth);

        return "success";
    }

    public List getItems() {
        return this.items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public List getDepartments() {
        return this.departments;
    }

    public void setDepartments(List departments) {
        this.departments = departments;
    }

    public List<String> getYears() {
        return this.years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }

    public List getJobgrades() {
        return this.jobgrades;
    }

    public void setJobgrades(List jobgrades) {
        this.jobgrades = jobgrades;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.action.compensation.SalaryReportInit JD-Core Version: 0.5.4
 */