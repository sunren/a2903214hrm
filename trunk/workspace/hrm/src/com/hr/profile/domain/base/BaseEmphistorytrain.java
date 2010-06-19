package com.hr.profile.domain.base;

import com.hr.base.BaseDomain;
import com.hr.profile.domain.Emphistorytrain;
import com.hr.profile.domain.Employee;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseEmphistorytrain extends BaseDomain implements Serializable {
    public static String REF = "Emphistorytrain";
    public static String PROP_EHT_ID = "ehtId";
    public static String PROP_EHT_CREATE_DATE = "ehtCreateDate";
    public static String PROP_EHT_CERFIFICATE = "ehtCertificate";
    public static String PROP_EMPLOYEE = "employee";
    public static String PROP_EHT_COURSE = "ehtCourse";
    public static String PROP_EHT_DEPARTMENT = "ehtDepartment";
    public static String PROP_EHT_CREATE_BY = "ehtCreateBy";
    public static String PROP_EHT_END_DATE = "ehtEndDate";
    public static String PROP_EHT_START_DATE = "ehtStartDate";
    public static String PROP_EHT_LOCATION = "ehtLocation";
    public static String PROP_EHT_ATTATCHMENT = "ehtAttatchment";
    public static String PROP_EHT_LAST_CHANGE_BY = "ehtLastChangeBy";
    public static String PROP_EHT_LAST_CHANGE_TIME = "ehtLastChangeTime";
    public static String PROP_EHT_COMMENTS = "ehtComments";

    private int hashCode = -2147483648;
    private String ehtId;
    private String ehtDepartment;
    private String ehtLocation;
    private Date ehtStartDate;
    private Date ehtEndDate;
    private String ehtCourse;
    private String ehtCertificate;
    private String ehtCreateBy;
    private Date ehtCreateDate;
    private String ehtLastChangeBy;
    private Date ehtLastChangeTime;
    private String ehtAttatchment;
    private String ehtComments;
    private Employee employee;

    public BaseEmphistorytrain() {
        initialize();
    }

    public BaseEmphistorytrain(String ehtId) {
        setEhtId(ehtId);
        initialize();
    }

    protected void initialize() {
    }

    public String getEhtId() {
        return this.ehtId;
    }

    public void setEhtId(String ehtId) {
        this.ehtId = ehtId;
        this.hashCode = -2147483648;
    }

    public String getEhtDepartment() {
        return this.ehtDepartment;
    }

    public void setEhtDepartment(String ehtDepartment) {
        this.ehtDepartment = ehtDepartment;
    }

    public String getEhtLocation() {
        return this.ehtLocation;
    }

    public void setEhtLocation(String ehtLocation) {
        this.ehtLocation = ehtLocation;
    }

    public Date getEhtStartDate() {
        return this.ehtStartDate;
    }

    public void setEhtStartDate(Date ehtStartDate) {
        this.ehtStartDate = ehtStartDate;
    }

    public Date getEhtEndDate() {
        return this.ehtEndDate;
    }

    public void setEhtEndDate(Date ehtEndDate) {
        this.ehtEndDate = ehtEndDate;
    }

    public String getEhtCourse() {
        return this.ehtCourse;
    }

    public void setEhtCourse(String ehtCourse) {
        this.ehtCourse = ehtCourse;
    }

    public String getEhtCertificate() {
        return this.ehtCertificate;
    }

    public void setEhtCertificate(String ehtCertificate) {
        this.ehtCertificate = ehtCertificate;
    }

    public String getEhtCreateBy() {
        return this.ehtCreateBy;
    }

    public void setEhtCreateBy(String ehtCreateBy) {
        this.ehtCreateBy = ehtCreateBy;
    }

    public Date getEhtCreateDate() {
        return this.ehtCreateDate;
    }

    public void setEhtCreateDate(Date ehtCreateDate) {
        this.ehtCreateDate = ehtCreateDate;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Emphistorytrain))
            return false;

        Emphistorytrain emphistorytrain = (Emphistorytrain) obj;
        if ((null == getEhtId()) || (null == emphistorytrain.getEhtId()))
            return false;
        return getEhtId().equals(emphistorytrain.getEhtId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getEhtId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getEhtId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }

    public void setEhtLastChangeBy(String ehtLastChangeBy) {
        this.ehtLastChangeBy = ehtLastChangeBy;
    }

    public Date getEhtLastChangeTime() {
        return this.ehtLastChangeTime;
    }

    public void setEhtLastChangeTime(Date ehtLastChangeTime) {
        this.ehtLastChangeTime = ehtLastChangeTime;
    }

    public String getEhtLastChangeBy() {
        return this.ehtLastChangeBy;
    }

    public String getEhtAttatchment() {
        return this.ehtAttatchment;
    }

    public void setEhtAttatchment(String ehtAttatchment) {
        this.ehtAttatchment = ehtAttatchment;
    }

    public String getEhtComments() {
        return this.ehtComments;
    }

    public void setEhtComments(String ehtComments) {
        this.ehtComments = ehtComments;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.domain.base.BaseEmphistorytrain JD-Core Version: 0.5.4
 */