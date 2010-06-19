package com.hr.profile.domain.base;

import com.hr.base.BaseDomain;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Emptransfer;
import com.hr.profile.domain.PositionBase;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseEmptransfer extends BaseDomain implements Serializable {
    public static String REF = "Emptransfer";
    public static String PROP_EFT_ID = "eftId";
    public static String PROP_EFT_LAST_CHANGE_BY = "eftLastChangeBy";
    public static String PROP_EMPLOYEE = "employee";
    public static String PROP_EFT_TRANSFER_DATE = "eftTransferDate";
    public static String PROP_EFT_LAST_CHANGE_TIME = "eftLastChangeTime";
    public static String PROP_EFT_TRANSFER_TYPE = "eftTransferType";
    public static String PROP_EFT_COMMENTS = "eftComments";
    public static String PROP_EFT_REASON = "eftReason";
    public static String PROP_EFT_CREATE_BY = "eftCreateBy";
    public static String PROP_EFT_CREATE_DATE = "eftCreateDate";
    public static String PROP_EFT_OLD_EMP_TYPE = "eftOldEmpType";
    public static String PROP_EFT_NEW_EMP_TYPE = "eftNewEmpType";
    public static String PROP_EFT_OLD_DEPT_NO = "eftOldDeptNo";
    public static String PROP_EFT_NEW_DEPT_NO = "eftNewDeptNo";
    public static String PROP_EFT_OLD_PB_NO = "eftOldPbId";
    public static String PROP_EFT_NEW_PB_NO = "eftNewPbId";

    private int hashCode = -2147483648;
    private String eftId;
    private Date eftTransferDate;
    private String eftTransferType;
    private String eftReason;
    private String eftComments;
    private String eftCreateBy;
    private Date eftCreateDate;
    private Date eftLastChangeTime;
    private String eftLastChangeBy;
    private Employee employee;
    private Department eftOldDeptNo;
    private Department eftNewDeptNo;
    private Emptype eftOldEmpType;
    private Emptype eftNewEmpType;
    private PositionBase eftOldPbId;
    private PositionBase eftNewPbId;

    public BaseEmptransfer() {
        initialize();
    }

    public BaseEmptransfer(String eftId) {
        setEftId(eftId);
        initialize();
    }

    protected void initialize() {
    }

    public PositionBase getEftOldPbId() {
        return this.eftOldPbId;
    }

    public void setEftOldPbId(PositionBase eftOldPbId) {
        this.eftOldPbId = eftOldPbId;
    }

    public PositionBase getEftNewPbId() {
        return this.eftNewPbId;
    }

    public void setEftNewPbId(PositionBase eftNewPbId) {
        this.eftNewPbId = eftNewPbId;
    }

    public String getEftId() {
        return this.eftId;
    }

    public void setEftId(String eftId) {
        this.eftId = eftId;
        this.hashCode = -2147483648;
    }

    public Date getEftTransferDate() {
        return this.eftTransferDate;
    }

    public void setEftTransferDate(Date eftTransferDate) {
        this.eftTransferDate = eftTransferDate;
    }

    public String getEftTransferType() {
        return this.eftTransferType;
    }

    public void setEftTransferType(String eftTransferType) {
        this.eftTransferType = eftTransferType;
    }

    public String getEftReason() {
        return this.eftReason;
    }

    public void setEftReason(String eftReason) {
        this.eftReason = eftReason;
    }

    public String getEftComments() {
        return this.eftComments;
    }

    public void setEftComments(String eftComments) {
        this.eftComments = eftComments;
    }

    public String getEftCreateBy() {
        return this.eftCreateBy;
    }

    public void setEftCreateBy(String eftCreateBy) {
        this.eftCreateBy = eftCreateBy;
    }

    public Date getEftCreateDate() {
        return this.eftCreateDate;
    }

    public void setEftCreateDate(Date eftCreateDate) {
        this.eftCreateDate = eftCreateDate;
    }

    public Date getEftLastChangeTime() {
        return this.eftLastChangeTime;
    }

    public void setEftLastChangeTime(Date eftLastChangeTime) {
        this.eftLastChangeTime = eftLastChangeTime;
    }

    public String getEftLastChangeBy() {
        return this.eftLastChangeBy;
    }

    public void setEftLastChangeBy(String eftLastChangeBy) {
        this.eftLastChangeBy = eftLastChangeBy;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Department getEftOldDeptNo() {
        return this.eftOldDeptNo;
    }

    public void setEftOldDeptNo(Department eftOldDeptNo) {
        this.eftOldDeptNo = eftOldDeptNo;
    }

    public Department getEftNewDeptNo() {
        return this.eftNewDeptNo;
    }

    public void setEftNewDeptNo(Department eftNewDeptNo) {
        this.eftNewDeptNo = eftNewDeptNo;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Emptransfer))
            return false;

        Emptransfer emptransfer = (Emptransfer) obj;
        if ((null == getEftId()) || (null == emptransfer.getEftId()))
            return false;
        return getEftId().equals(emptransfer.getEftId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getEftId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getEftId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }

    public Emptype getEftOldEmpType() {
        return this.eftOldEmpType;
    }

    public void setEftOldEmpType(Emptype eftOldEmpType) {
        this.eftOldEmpType = eftOldEmpType;
    }

    public Emptype getEftNewEmpType() {
        return this.eftNewEmpType;
    }

    public void setEftNewEmpType(Emptype eftNewEmpType) {
        this.eftNewEmpType = eftNewEmpType;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.domain.base.BaseEmptransfer JD-Core Version: 0.5.4
 */