package com.hr.profile.domain.base;

import com.hr.base.BaseDomain;
import com.hr.profile.domain.Emphistoryjob;
import com.hr.profile.domain.Employee;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseEmphistoryjob extends BaseDomain implements Serializable {
    public static String REF = "Emphistoryjob";
    public static String PROP_EHJ_COMPANY = "ehjCompany";
    public static String PROP_EMPLOYEE = "employee";
    public static String PROP_EHJ_DATE_END = "ehjDateEnd";
    public static String PROP_EHJ_DATE_START = "ehjDateStart";
    public static String PROP_EHJ_CREATE_DATE = "ehjCreateDate";
    public static String PROP_EHJ_CREATE_BY = "ehjCreateBy";
    public static String PROP_EHJ_DESCRIPTION = "ehjDescription";
    public static String PROP_EHJ_POSITION = "ehjPosition";
    public static String PROP_EHJ_ID = "ehjId";
    public static String PROP_EHJ_LAST_CHANGE_BY = "ehjLastChangeBy";
    public static String PROP_EHJ_LAST_CHANGE_TIME = "ehjLastChangeTime";

    private int hashCode = -2147483648;
    private String ehjId;
    private String ehjCompany;
    private Date ehjDateStart;
    private Date ehjDateEnd;
    private String ehjPosition;
    private String ehjDescription;
    private String ehjCreateBy;
    private Date ehjCreateDate;
    private String ehjLastChangeBy;
    private Date ehjLastChangeTime;
    private Employee employee;

    public BaseEmphistoryjob() {
        initialize();
    }

    public BaseEmphistoryjob(String ehjId) {
        setEhjId(ehjId);
        initialize();
    }

    protected void initialize() {
    }

    public String getEhjId() {
        return this.ehjId;
    }

    public void setEhjId(String ehjId) {
        this.ehjId = ehjId;
        this.hashCode = -2147483648;
    }

    public String getEhjCompany() {
        return this.ehjCompany;
    }

    public void setEhjCompany(String ehjCompany) {
        this.ehjCompany = ehjCompany;
    }

    public Date getEhjDateStart() {
        return this.ehjDateStart;
    }

    public void setEhjDateStart(Date ehjDateStart) {
        this.ehjDateStart = ehjDateStart;
    }

    public Date getEhjDateEnd() {
        return this.ehjDateEnd;
    }

    public void setEhjDateEnd(Date ehjDateEnd) {
        this.ehjDateEnd = ehjDateEnd;
    }

    public String getEhjPosition() {
        return this.ehjPosition;
    }

    public void setEhjPosition(String ehjPosition) {
        this.ehjPosition = ehjPosition;
    }

    public String getEhjDescription() {
        return this.ehjDescription;
    }

    public void setEhjDescription(String ehjDescription) {
        this.ehjDescription = ehjDescription;
    }

    public String getEhjCreateBy() {
        return this.ehjCreateBy;
    }

    public void setEhjCreateBy(String ehjCreateBy) {
        this.ehjCreateBy = ehjCreateBy;
    }

    public Date getEhjCreateDate() {
        return this.ehjCreateDate;
    }

    public void setEhjCreateDate(Date ehjCreateDate) {
        this.ehjCreateDate = ehjCreateDate;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String toString() {
        return this.ehjId + this.ehjDateStart + this.ehjDateEnd + this.ehjCompany
                + this.ehjPosition + this.ehjDescription + this.ehjCreateBy + this.ehjCreateDate
                + this.employee;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Emphistoryjob))
            return false;

        Emphistoryjob emphistoryjob = (Emphistoryjob) obj;
        if ((null == getEhjId()) || (null == emphistoryjob.getEhjId()))
            return false;
        return getEhjId().equals(emphistoryjob.getEhjId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getEhjId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getEhjId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String getEhjLastChangeBy() {
        return this.ehjLastChangeBy;
    }

    public void setEhjLastChangeBy(String ehjLastChangeBy) {
        this.ehjLastChangeBy = ehjLastChangeBy;
    }

    public Date getEhjLastChangeTime() {
        return this.ehjLastChangeTime;
    }

    public void setEhjLastChangeTime(Date ehjLastChangeTime) {
        this.ehjLastChangeTime = ehjLastChangeTime;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.domain.base.BaseEmphistoryjob JD-Core Version: 0.5.4
 */