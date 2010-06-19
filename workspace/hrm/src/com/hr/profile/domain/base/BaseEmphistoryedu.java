package com.hr.profile.domain.base;

import com.hr.base.BaseDomain;
import com.hr.profile.domain.Emphistoryedu;
import com.hr.profile.domain.Employee;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseEmphistoryedu extends BaseDomain implements Serializable {
    public static String REF = "Emphistoryedu";
    public static String PROP_EHE_DEGREE = "eheDegree";
    public static String PROP_EMPLOYEE = "employee";
    public static String PROP_EHE_CREATE_DATE = "eheCreateDate";
    public static String PROP_EHE_DATE_START = "eheDateStart";
    public static String PROP_EHE_SCHOOL = "eheSchool";
    public static String PROP_EHE_CREATE_BY = "eheCreateBy";
    public static String PROP_EHE_MAJOR = "eheMajor";
    public static String PROP_EHE_DATE_END = "eheDateEnd";
    public static String PROP_EHE_ATTATCHMENT = "eheAttachment";
    public static String PROP_EHE_ID = "eheId";
    public static String PROP_EHE_LAST_CHANGE_BY = "eheLastChangeBy";
    public static String PROP_EHE_LAST_CHANGE_TIME = "eheLastChangeTime";
    public static String PROP_EHE_COMMENTS = "eheComments";

    private int hashCode = -2147483648;
    private String eheId;
    private Date eheDateStart;
    private Date eheDateEnd;
    private String eheSchool;
    private String eheMajor;
    private String eheDegree;
    private String eheCreateBy;
    private Date eheCreateDate;
    private String eheLastChangeBy;
    private Date eheLastChangeTime;
    private String eheAttachment;
    private String eheComments;
    private Employee employee;

    public BaseEmphistoryedu() {
        initialize();
    }

    public BaseEmphistoryedu(String eheId) {
        setEheId(eheId);
        initialize();
    }

    protected void initialize() {
    }

    public String getEheId() {
        return this.eheId;
    }

    public void setEheId(String eheId) {
        this.eheId = eheId;
        this.hashCode = -2147483648;
    }

    public Date getEheDateStart() {
        return this.eheDateStart;
    }

    public void setEheDateStart(Date eheDateStart) {
        this.eheDateStart = eheDateStart;
    }

    public Date getEheDateEnd() {
        return this.eheDateEnd;
    }

    public void setEheDateEnd(Date eheDateEnd) {
        this.eheDateEnd = eheDateEnd;
    }

    public String getEheSchool() {
        return this.eheSchool;
    }

    public void setEheSchool(String eheSchool) {
        this.eheSchool = eheSchool;
    }

    public String getEheMajor() {
        return this.eheMajor;
    }

    public void setEheMajor(String eheMajor) {
        this.eheMajor = eheMajor;
    }

    public String getEheDegree() {
        return this.eheDegree;
    }

    public void setEheDegree(String eheDegree) {
        this.eheDegree = eheDegree;
    }

    public String getEheCreateBy() {
        return this.eheCreateBy;
    }

    public void setEheCreateBy(String eheCreateBy) {
        this.eheCreateBy = eheCreateBy;
    }

    public Date getEheCreateDate() {
        return this.eheCreateDate;
    }

    public void setEheCreateDate(Date eheCreateDate) {
        this.eheCreateDate = eheCreateDate;
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
        if (!(obj instanceof Emphistoryedu))
            return false;

        Emphistoryedu emphistoryedu = (Emphistoryedu) obj;
        if ((null == getEheId()) || (null == emphistoryedu.getEheId()))
            return false;
        return getEheId().equals(emphistoryedu.getEheId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getEheId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getEheId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }

    public String getEheLastChangeBy() {
        return this.eheLastChangeBy;
    }

    public void setEheLastChangeBy(String eheLastChangeBy) {
        this.eheLastChangeBy = eheLastChangeBy;
    }

    public Date getEheLastChangeTime() {
        return this.eheLastChangeTime;
    }

    public void setEheLastChangeTime(Date eheLastChangeTime) {
        this.eheLastChangeTime = eheLastChangeTime;
    }

    public String getEheAttachment() {
        return this.eheAttachment;
    }

    public void setEheAttachment(String eheAttachment) {
        this.eheAttachment = eheAttachment;
    }

    public String getEheComments() {
        return this.eheComments;
    }

    public void setEheComments(String eheComments) {
        this.eheComments = eheComments;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.domain.base.BaseEmphistoryedu JD-Core Version: 0.5.4
 */