package com.hr.profile.domain.base;

import com.hr.base.BaseDomain;
import com.hr.configuration.domain.Department;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Empquit;
import com.hr.profile.domain.PositionBase;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseEmpquit extends BaseDomain implements Serializable {
    public static String REF = "Empquit";
    public static String PROP_EQ_CREATE_DATE = "eqCreateDate";
    public static String PROP_EQ_TYPE = "eqType";
    public static String PROP_EMPLOYEE = "employee";
    public static String PROP_ER_COMMENTS = "erComments";
    public static String PROP_EQ_PERMISSION = "eqPermission";
    public static String PROP_EQ_ID = "eqId";
    public static String PROP_EQ_LAST_CHANGE_TIME = "eqLastChangeTime";
    public static String PROP_EQ_REASON = "eqReason";
    public static String PROP_EQ_DATE = "eqDate";
    public static String PROP_EQ_CREATE_BY = "eqCreateBy";
    public static String PROP_EQ_LAST_CHANGE_BY = "eqLastChangeBy";
    public static String PROP_EQ_DEPT_NO = "eqDeptNo";
    public static String PROP_EQ_PB_NO = "eqPbNo";

    private int hashCode = -2147483648;
    private String eqId;
    private String eqType = "0";
    private Date eqDate;
    private String eqReason;
    private String erComments;
    private String eqCreateBy;
    private Date eqCreateDate;
    private Date eqLastChangeTime;
    private String eqLastChangeBy;
    private Employee employee;
    private Employee eqPermission;
    private Department eqDeptNo;
    private PositionBase eqPbNo;

    public BaseEmpquit() {
        initialize();
    }

    public BaseEmpquit(String eqId) {
        setEqId(eqId);
        initialize();
    }

    protected void initialize() {
    }

    public String getEqId() {
        return this.eqId;
    }

    public void setEqId(String eqId) {
        this.eqId = eqId;
        this.hashCode = -2147483648;
    }

    public String getEqType() {
        return this.eqType;
    }

    public void setEqType(String eqType) {
        this.eqType = eqType;
    }

    public Date getEqDate() {
        return this.eqDate;
    }

    public void setEqDate(Date eqDate) {
        this.eqDate = eqDate;
    }

    public Employee getEqPermission() {
        return this.eqPermission;
    }

    public void setEqPermission(Employee eqPermission) {
        this.eqPermission = eqPermission;
    }

    public String getEqReason() {
        return this.eqReason;
    }

    public void setEqReason(String eqReason) {
        this.eqReason = eqReason;
    }

    public String getErComments() {
        return this.erComments;
    }

    public void setErComments(String erComments) {
        this.erComments = erComments;
    }

    public String getEqCreateBy() {
        return this.eqCreateBy;
    }

    public void setEqCreateBy(String eqCreateBy) {
        this.eqCreateBy = eqCreateBy;
    }

    public Date getEqCreateDate() {
        return this.eqCreateDate;
    }

    public void setEqCreateDate(Date eqCreateDate) {
        this.eqCreateDate = eqCreateDate;
    }

    public Date getEqLastChangeTime() {
        return this.eqLastChangeTime;
    }

    public void setEqLastChangeTime(Date eqLastChangeTime) {
        this.eqLastChangeTime = eqLastChangeTime;
    }

    public String getEqLastChangeBy() {
        return this.eqLastChangeBy;
    }

    public void setEqLastChangeBy(String eqLastChangeBy) {
        this.eqLastChangeBy = eqLastChangeBy;
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
        if (!(obj instanceof Empquit))
            return false;

        Empquit empquit = (Empquit) obj;
        if ((null == getEqId()) || (null == empquit.getEqId()))
            return false;
        return getEqId().equals(empquit.getEqId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getEqId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getEqId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }

    public Department getEqDeptNo() {
        return this.eqDeptNo;
    }

    public void setEqDeptNo(Department eqDeptNo) {
        this.eqDeptNo = eqDeptNo;
    }

    public PositionBase getEqPbNo() {
        return this.eqPbNo;
    }

    public void setEqPbNo(PositionBase eqPbNo) {
        this.eqPbNo = eqPbNo;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.domain.base.BaseEmpquit JD-Core Version: 0.5.4
 */