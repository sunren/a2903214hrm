package com.hr.compensation.action;

import com.hr.base.BaseAction;
import com.hr.base.Status;
import com.hr.compensation.bo.ICompaplanBo;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryadj;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Ecptype;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Jobgrade;
import com.hr.configuration.domain.Location;
import com.hr.configuration.domain.Statusconf;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.SimpleEmployee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.BaseCrit;
import com.hr.util.Pager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;

public class SearchBatchCompaplan extends BaseAction implements Status {
    private static final long serialVersionUID = -4234940898871087425L;
    private Employee emp;
    private Empsalaryadj empSalaryAdj;
    private List<Empsalaryconfig> salaryconfList;
    private List<Jobgrade> jobgradePro;
    private List<Department> departmentList;
    private List<Ecptype> ecptypeList;
    private List<SimpleEmployee> managers;
    private List<Location> location;
    private List<Empsalaryacctversion> esaList;
    private Pager page;
    private List<Statusconf> empStatus;
    private List<Emptype> empType;
    private List<String> yearAndMonth;
    private String curEffDate;

    public SearchBatchCompaplan() {
        this.emp = new Employee();
        this.empSalaryAdj = new Empsalaryadj();

        this.page = new Pager();
    }

    public String execute() throws Exception {
        DetachedCriteria dc = searchConfWithEmp_DC();
        addCriteria(dc);

        getDrillDownList();

        this.yearAndMonth = getCalendarYearAndMonth();
        this.curEffDate = ((String) this.yearAndMonth.get(2));

        ICompaplanBo compaplanBo = (ICompaplanBo) SpringBeanFactory.getBean("compaplanBo");
        this.salaryconfList = compaplanBo.mysearchBatchCompaplan(dc, this.page);

        compaplanBo.calcSalaryConfWithComp(this.salaryconfList);

        return "success";
    }

    private DetachedCriteria searchConfWithEmp_DC() {
        DetachedCriteria dc = DetachedCriteria.forClass(Empsalaryconfig.class);
        dc.setFetchMode("employee", FetchMode.JOIN);
        dc.setFetchMode("employee.empType", FetchMode.JOIN);
        dc.setFetchMode("employee.empDeptNo", FetchMode.JOIN);
        dc.setFetchMode("employee.empLocationNo", FetchMode.JOIN);
        dc.setFetchMode("escEsavId", FetchMode.JOIN);
        dc.setFetchMode("escJobgrade", FetchMode.JOIN);
        dc.createAlias("employee", "emp");
        return dc;
    }

    private void addCriteria(DetachedCriteria dc) {
        BaseCrit.addEmpDC(dc, "emp", this.emp.getEmpName());
        BaseCrit.addDeptDC(dc, "emp." + Employee.PROP_EMP_DEPT_NO,
                           "emp." + Employee.PROP_EMP_PB_NO, Integer.valueOf(1), this.emp
                                   .getEmpDeptNo());
        BaseCrit.addDC(dc, "emp." + Employee.PROP_EMP_TYPE, Emptype.PROP_ID,
                       new Object[] { this.emp.getEmpType() });
        BaseCrit.addDC(dc, "emp." + Employee.PROP_EMP_LOCATION_NO, Location.PROP_ID,
                       new Object[] { this.emp.getEmpLocationNo() });
        BaseCrit.addDC(dc, "emp." + Employee.PROP_EMP_STATUS, "eq", new Integer[] { Integer
                .valueOf(1) });
    }

    private void getDrillDownList() {
        this.departmentList = getDrillDown("Department", new String[0]);
        this.location = getDrillDown("Location", new String[0]);
        this.empType = getDrillDown("EmpType", new String[0]);
        this.jobgradePro = getDrillDown("Jobgrade", new String[0]);
        this.esaList = getDrillDown("EmpSalaryAcct", new String[0]);
        this.ecptypeList = getDrillDown("EcpType", new String[0]);
    }

    private List getCalendarYearAndMonth() {
        List yearAndMonth = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        int startYear = calendar.get(1);
        int startMonth = calendar.get(2);
        yearAndMonth.add(startYear
                + "-"
                + ((startMonth > 9) ? Integer.valueOf(startMonth) : new StringBuilder().append("0")
                        .append(startMonth).toString()));
        for (int i = 0; i < 13; ++i) {
            int currentYear = startYear;
            int currentMonth = startMonth + i;
            calendar.set(currentYear, currentMonth, 1);
            currentYear = calendar.get(1);
            currentMonth = calendar.get(2) + 1;
            if (currentMonth < 10)
                yearAndMonth.add(currentYear + "-0" + currentMonth);
            else
                yearAndMonth.add(currentYear + "-" + currentMonth);
        }
        return yearAndMonth;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public List<Department> getDepartmentList() {
        return this.departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public List<Ecptype> getEcptypeList() {
        return this.ecptypeList;
    }

    public void setEcptypeList(List<Ecptype> ecptypeList) {
        this.ecptypeList = ecptypeList;
    }

    public List<Jobgrade> getJobgradePro() {
        return this.jobgradePro;
    }

    public void setJobgradePro(List<Jobgrade> jobgradePro) {
        this.jobgradePro = jobgradePro;
    }

    public List<Location> getLocation() {
        return this.location;
    }

    public void setLocation(List<Location> location) {
        this.location = location;
    }

    public List<Empsalaryacctversion> getEsaList() {
        return this.esaList;
    }

    public void setEsaList(List<Empsalaryacctversion> esaList) {
        this.esaList = esaList;
    }

    public Employee getEmp() {
        return this.emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public List<SimpleEmployee> getManagers() {
        return this.managers;
    }

    public void setManagers(List<SimpleEmployee> managers) {
        this.managers = managers;
    }

    public Empsalaryadj getEmpSalaryAdj() {
        return this.empSalaryAdj;
    }

    public void setEmpSalaryAdj(Empsalaryadj empSalaryAdj) {
        this.empSalaryAdj = empSalaryAdj;
    }

    public List<Empsalaryconfig> getSalaryconfList() {
        return this.salaryconfList;
    }

    public void setSalaryconfList(List<Empsalaryconfig> salaryconfList) {
        this.salaryconfList = salaryconfList;
    }

    public List<Statusconf> getEmpStatus() {
        return this.empStatus;
    }

    public void setEmpStatus(List<Statusconf> empStatus) {
        this.empStatus = empStatus;
    }

    public List<Emptype> getEmpType() {
        return this.empType;
    }

    public void setEmpType(List<Emptype> empType) {
        this.empType = empType;
    }

    public List<String> getYearAndMonth() {
        return this.yearAndMonth;
    }

    public void setYearAndMonth(List<String> yearAndMonth) {
        this.yearAndMonth = yearAndMonth;
    }

    public String getCurEffDate() {
        return this.curEffDate;
    }

    public void setCurEffDate(String curEffDate) {
        this.curEffDate = curEffDate;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.action.SearchBatchCompaplan JD-Core Version: 0.5.4
 */