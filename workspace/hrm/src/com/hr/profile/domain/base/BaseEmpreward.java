package com.hr.profile.domain.base;

import com.hr.base.BaseDomain;
import com.hr.configuration.domain.Department;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Empreward;
import com.hr.profile.domain.PositionBase;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseEmpreward extends BaseDomain implements Serializable {
    public static String REF = "Empreward";
    public static String PROP_ER_CONTENT = "erContent";
    public static String PROP_ER_PB_NO = "erPbNo";
    public static String PROP_ER_TYPE = "erType";
    public static String PROP_ER_FORM = "erForm";
    public static String PROP_EMPLOYEE = "employee";
    public static String PROP_ER_LAST_CHANGE_TIME = "erLastChangeTime";
    public static String PROP_ER_ID = "erId";
    public static String PROP_ER_CREATE_BY = "erCreateBy";
    public static String PROP_ER_EXE_DATE = "erExeDate";
    public static String PROP_DEPARTMENT = "department";
    public static String PROP_ER_LAST_CHANGE_BY = "erLastChangeBy";
    public static String PROP_ER_CREATE_DATE = "erCreateDate";

    private int hashCode = -2147483648;
    private String erId;
    private String erType;
    private String erForm;
    private String erContent;
    private String erCreateBy;
    private Date erCreateDate;
    private Date erLastChangeTime;
    private String erLastChangeBy;
    private Date erExeDate;
    private Department department;
    private Employee employee;
    private PositionBase erPbNo;

    public BaseEmpreward() {
        initialize();
    }

    public BaseEmpreward(String erId) {
        setErId(erId);
        initialize();
    }

    protected void initialize() {
    }

    public String getErId() {
        return this.erId;
    }

    public void setErId(String erId) {
        this.erId = erId;
        this.hashCode = -2147483648;
    }

    public String getErType() {
        return this.erType;
    }

    public void setErType(String erType) {
        this.erType = erType;
    }

    public String getErContent() {
        return this.erContent;
    }

    public void setErContent(String erContent) {
        this.erContent = erContent;
    }

    public String getErCreateBy() {
        return this.erCreateBy;
    }

    public void setErCreateBy(String erCreateBy) {
        this.erCreateBy = erCreateBy;
    }

    public Date getErCreateDate() {
        return this.erCreateDate;
    }

    public void setErCreateDate(Date erCreateDate) {
        this.erCreateDate = erCreateDate;
    }

    public Date getErLastChangeTime() {
        return this.erLastChangeTime;
    }

    public void setErLastChangeTime(Date erLastChangeTime) {
        this.erLastChangeTime = erLastChangeTime;
    }

    public String getErLastChangeBy() {
        return this.erLastChangeBy;
    }

    public void setErLastChangeBy(String erLastChangeBy) {
        this.erLastChangeBy = erLastChangeBy;
    }

    public Date getErExeDate() {
        return this.erExeDate;
    }

    public void setErExeDate(Date erExeDate) {
        this.erExeDate = erExeDate;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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
        if (!(obj instanceof Empreward))
            return false;

        Empreward empreward = (Empreward) obj;
        if ((null == getErId()) || (null == empreward.getErId()))
            return false;
        return getErId().equals(empreward.getErId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getErId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getErId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }

    public String getErForm() {
        return this.erForm;
    }

    public void setErForm(String erForm) {
        this.erForm = erForm;
    }

    public PositionBase getErPbNo() {
        return this.erPbNo;
    }

    public void setErPbNo(PositionBase erPbNo) {
        this.erPbNo = erPbNo;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.domain.base.BaseEmpreward JD-Core Version: 0.5.4
 */