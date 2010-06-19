package com.hr.configuration.domain.base;

import com.hr.base.BaseDomain;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.configuration.domain.Department;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public abstract class BaseDepartment extends BaseDomain implements Serializable {
    public static String REF = "Department";
    public static String PROP_DEPARTMENT_SORT_ID = "departmentSortId";
    public static String PROP_DEPARTMENT_NAME = "departmentName";
    public static String PROP_DEPARTMENT_DESC = "departmentDesc";
    public static String PROP_ID = "id";
    public static String PROP_DEPARTMENT_STATUS = "departmentStatus";
    public static String PROP_DEPARTMENT_PARENT_ID = "departmentParentId";
    public static String PROP_DEPARTMENT_CATE = "departmentCate";
    public static String PROP_DEPARTMENT_BUSINESS_DESC = "departmentBusinessDesc";
    public static String PROP_DEPARTMENT_BUSINESS_ATTACH = "departmentBusinessAttach";
    public static String PROP_DEPARTMENT_CREATE_TIME = "departmentCreateDate";
    public static String PROP_DEPARTMENT_END_TIME = "departmentEndDate";
    public static String PROP_DEPARTMENT_TRADE = "departmentTrade";
    public static String PROP_DEPARTMENT_PERSONINLAW = "departmentPersonInlaw";
    public static String PROP_DEPARTMENT_MEMO = "departmentMemo";

    private int hashCode = -2147483648;
    private String id;
    private String departmentName;
    private String departmentDesc;
    private Integer departmentSortId;
    private Integer departmentStatus;
    private Integer departmentCate;
    private String departmentBusinessDesc;
    private String departmentBusinessAttach;
    private Date departmentCreateDate;
    private Date departmentEndDate;
    private String departmentTrade;
    private String departmentPersonInlaw;
    private String departmentMemo;
    private Department departmentParentId;
    public String headNo;
    public String headName;
    private Set<Empsalarypay> empsalarypaids;
    private Set<Employee> employees;

    public BaseDepartment() {
        initialize();
    }

    public BaseDepartment(String id) {
        setId(id);
        initialize();
    }

    public BaseDepartment(String id, String departmentName, Integer departmentSortId) {
        setId(id);
        setDepartmentName(departmentName);
        setDepartmentSortId(departmentSortId);
        initialize();
    }

    protected void initialize() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentDesc() {
        return this.departmentDesc;
    }

    public void setDepartmentDesc(String departmentDesc) {
        this.departmentDesc = departmentDesc;
    }

    public Integer getDepartmentSortId() {
        return this.departmentSortId;
    }

    public void setDepartmentSortId(Integer departmentSortId) {
        this.departmentSortId = departmentSortId;
    }

    public Set<Empsalarypay> getEmpsalarypaids() {
        return this.empsalarypaids;
    }

    public void setEmpsalarypaids(Set<Empsalarypay> empsalarypaids) {
        this.empsalarypaids = empsalarypaids;
    }

    public void addToempsalarypaids(Empsalarypay empsalarypaid) {
        if (null == getEmpsalarypaids())
            setEmpsalarypaids(new TreeSet());
        getEmpsalarypaids().add(empsalarypaid);
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public void addToemployees(Employee employee) {
        if (null == getEmployees())
            setEmployees(new TreeSet());
        getEmployees().add(employee);
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Department))
            return false;

        Department department = (Department) obj;
        if ((null == getId()) || (null == department.getId()))
            return false;
        return getId().equals(department.getId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }

    public String getDepartmentHead() {
        if (getId() == null) {
            return null;
        }
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        String hql = "SELECT orgheadsEmpNo from Orgheads where orgheadsOrgNo ='" + getId()
                + "' and orgheadsResponsibility='deptheader'";

        List list = empBo.exeHqlList(hql, new int[0]);
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return (String) list.get(0);
    }

    public String getDepartmentOtherHeads() {
        if (getId() == null) {
            return null;
        }
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        String hql = "SELECT orgheadsEmpNo from Orgheads where orgheadsOrgNo ='" + getId()
                + "' and orgheadsResponsibility='deptdeputy'";

        List list = empBo.exeHqlList(hql, new int[0]);
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        String result = (String) list.get(0);
        for (int i = 1; i < list.size(); ++i) {
            result = result + "," + (String) list.get(i);
        }
        return result;
    }

    public String getHeadNo() {
        return this.headNo;
    }

    public void setHeadNo(String headNo) {
        this.headNo = headNo;
    }

    public String getHeadName() {
        return this.headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public Integer getDepartmentStatus() {
        return this.departmentStatus;
    }

    public void setDepartmentStatus(Integer departmentStatus) {
        this.departmentStatus = departmentStatus;
    }

    public Integer getDepartmentCate() {
        return this.departmentCate;
    }

    public void setDepartmentCate(Integer departmentCate) {
        this.departmentCate = departmentCate;
    }

    public String getDepartmentBusinessDesc() {
        return this.departmentBusinessDesc;
    }

    public void setDepartmentBusinessDesc(String departmentBusinessDesc) {
        this.departmentBusinessDesc = departmentBusinessDesc;
    }

    public String getDepartmentBusinessAttach() {
        return this.departmentBusinessAttach;
    }

    public void setDepartmentBusinessAttach(String departmentBusinessAttach) {
        this.departmentBusinessAttach = departmentBusinessAttach;
    }

    public String getDepartmentTrade() {
        return this.departmentTrade;
    }

    public void setDepartmentTrade(String departmentTrade) {
        this.departmentTrade = departmentTrade;
    }

    public String getDepartmentMemo() {
        return this.departmentMemo;
    }

    public void setDepartmentMemo(String departmentMemo) {
        this.departmentMemo = departmentMemo;
    }

    public String getDepartmentPersonInlaw() {
        return this.departmentPersonInlaw;
    }

    public void setDepartmentPersonInlaw(String departmentPersonInlaw) {
        this.departmentPersonInlaw = departmentPersonInlaw;
    }

    public Department getDepartmentParentId() {
        return this.departmentParentId;
    }

    public void setDepartmentParentId(Department departmentParentId) {
        this.departmentParentId = departmentParentId;
    }

    public Date getDepartmentCreateDate() {
        return this.departmentCreateDate;
    }

    public void setDepartmentCreateDate(Date departmentCreateDate) {
        this.departmentCreateDate = departmentCreateDate;
    }

    public Date getDepartmentEndDate() {
        return this.departmentEndDate;
    }

    public void setDepartmentEndDate(Date departmentEndDate) {
        this.departmentEndDate = departmentEndDate;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.domain.base.BaseDepartment JD-Core Version: 0.5.4
 */