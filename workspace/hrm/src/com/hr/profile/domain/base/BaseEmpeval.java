package com.hr.profile.domain.base;

import com.hr.base.BaseDomain;
import com.hr.configuration.domain.Department;
import com.hr.profile.domain.Empeval;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.PositionBase;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseEmpeval extends BaseDomain implements Serializable {
    public static String REF = "Empeval";
    public static String PROP_EE_LAST_CHANGE_BY = "eeLastChangeBy";
    public static String PROP_EE_END_DATE = "eeEndDate";
    public static String PROP_EE_COMMENTS = "eeComments";
    public static String PROP_EE_ID = "eeId";
    public static String PROP_EMPLOYEE_BY_EE_MANAGER = "employeeByEeManager";
    public static String PROP_EE_CREATE_BY = "eeCreateBy";
    public static String PROP_EE_START_DATE = "eeStartDate";
    public static String PROP_EE_CREATE_DATE = "eeCreateDate";
    public static String PROP_EE_RATE = "eeRate";
    public static String PROP_EE_ATTACHMENT = "eeAttachment";
    public static String PROP_EMPLOYEE_BY_EE_EMP_NO = "employeeByEeEmpNo";
    public static String PROP_EE_LAST_CHANGE_TIME = "eeLastChangeTime";
    public static String PROP_EE_TYPE = "eeType";
    public static String PROP_DEPARTMENT = "department";
    public static String PROP_EE_PB_NO = "eePbNo";

    private int hashCode = -2147483648;
    private String eeId;
    private String eeType;
    private Date eeStartDate;
    private Date eeEndDate;
    private String eeRate;
    private String eeComments;
    private String eeAttachment;
    private String eeCreateBy;
    private Date eeCreateDate;
    private Date eeLastChangeTime;
    private String eeLastChangeBy;
    private Employee employeeByEeEmpNo;
    private Department department;
    private Employee employeeByEeManager;
    private PositionBase eePbNo;

    public BaseEmpeval() {
        initialize();
    }

    public BaseEmpeval(String eeId) {
        setEeId(eeId);
        initialize();
    }

    protected void initialize() {
    }

    public String getEeId() {
        return this.eeId;
    }

    public void setEeId(String eeId) {
        this.eeId = eeId;
        this.hashCode = -2147483648;
    }

    public String getEeType() {
        return this.eeType;
    }

    public void setEeType(String eeType) {
        this.eeType = eeType;
    }

    public Date getEeStartDate() {
        return this.eeStartDate;
    }

    public void setEeStartDate(Date eeStartDate) {
        this.eeStartDate = eeStartDate;
    }

    public Date getEeEndDate() {
        return this.eeEndDate;
    }

    public void setEeEndDate(Date eeEndDate) {
        this.eeEndDate = eeEndDate;
    }

    public String getEeRate() {
        return this.eeRate;
    }

    public void setEeRate(String eeRate) {
        this.eeRate = eeRate;
    }

    public String getEeComments() {
        return this.eeComments;
    }

    public void setEeComments(String eeComments) {
        this.eeComments = eeComments;
    }

    public String getEeAttachment() {
        return this.eeAttachment;
    }

    public void setEeAttachment(String eeAttachment) {
        this.eeAttachment = eeAttachment;
    }

    public String getEeCreateBy() {
        return this.eeCreateBy;
    }

    public void setEeCreateBy(String eeCreateBy) {
        this.eeCreateBy = eeCreateBy;
    }

    public Date getEeCreateDate() {
        return this.eeCreateDate;
    }

    public void setEeCreateDate(Date eeCreateDate) {
        this.eeCreateDate = eeCreateDate;
    }

    public Date getEeLastChangeTime() {
        return this.eeLastChangeTime;
    }

    public void setEeLastChangeTime(Date eeLastChangeTime) {
        this.eeLastChangeTime = eeLastChangeTime;
    }

    public String getEeLastChangeBy() {
        return this.eeLastChangeBy;
    }

    public void setEeLastChangeBy(String eeLastChangeBy) {
        this.eeLastChangeBy = eeLastChangeBy;
    }

    public Employee getEmployeeByEeEmpNo() {
        return this.employeeByEeEmpNo;
    }

    public void setEmployeeByEeEmpNo(Employee employeeByEeEmpNo) {
        this.employeeByEeEmpNo = employeeByEeEmpNo;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getEmployeeByEeManager() {
        return this.employeeByEeManager;
    }

    public void setEmployeeByEeManager(Employee employeeByEeManager) {
        this.employeeByEeManager = employeeByEeManager;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Empeval))
            return false;

        Empeval empeval = (Empeval) obj;
        if ((null == getEeId()) || (null == empeval.getEeId()))
            return false;
        return getEeId().equals(empeval.getEeId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getEeId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getEeId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }

    public PositionBase getEePbNo() {
        return this.eePbNo;
    }

    public void setEePbNo(PositionBase eePbNo) {
        this.eePbNo = eePbNo;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.domain.base.BaseEmpeval JD-Core Version: 0.5.4
 */