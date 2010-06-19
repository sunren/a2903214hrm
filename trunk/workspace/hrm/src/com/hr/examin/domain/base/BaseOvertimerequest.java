package com.hr.examin.domain.base;

import com.hr.base.BaseDomain;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Location;
import com.hr.examin.domain.Overtimerequest;
import com.hr.examin.domain.Overtimetype;
import com.hr.profile.domain.Employee;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public abstract class BaseOvertimerequest extends BaseDomain implements Serializable {
    public static String REF = "Overtimerequest";
    public static String PROP_OR_EMP_DEPT = "orEmpDept";
    public static String PROP_OR_REASON = "orReason";
    public static String PROP_OR_EMP_LOCATION_NO = "orEmpLocationNo";
    public static String PROP_OR_END_DATE = "orEndDate";
    public static String PROP_OR_START_DATE = "orStartDate";
    public static String PROP_OR_LAST_CHANGE_TIME = "orLastChangeTime";
    public static String PROP_OR_CREATE_BY = "orCreateBy";
    public static String PROP_OR_CREATE_TIME = "orCreateTime";
    public static String PROP_OR_ID = "orId";
    public static String PROP_OR_EMP_NO = "orEmpNo";
    public static String PROP_OR_NO = "orNo";
    public static String PROP_OR_TOTAL_HOURS = "orTotalHours";
    public static String PROP_OR_NEXT_APPROVER = "orNextApprover";
    public static String PROP_OR_STATUS = "orStatus";
    public static String PROP_OR_OT_NO = "orOtNo";
    public static String PROP_OR_LAST_CHANGE_BY = "orLastChangeBy";
    public static String PROP_OR_TIAOXIU_HOURS = "orTiaoxiuHours";
    public static String PROP_OR_SECURITY_NO = "orSecurityNo";

    private int hashCode = -2147483648;
    private String orId;
    private Integer orNo;
    private String orReason;
    private Date orStartDate;
    private Date orEndDate;
    private BigDecimal orTotalHours;
    private BigDecimal orTiaoxiuHours;
    private Date orTiaoxiuExpire;
    private Integer orStatus;
    private Date orCreateTime;
    private Date orLastChangeTime;
    private String orNextApprover;
    private String orSecurityNo;
    private Employee orCreateBy;
    private Overtimetype orOtNo;
    private Employee orLastChangeBy;
    private Department orEmpDept;
    private Employee orEmpNo;
    private Location orEmpLocationNo;

    public BaseOvertimerequest() {
        initialize();
    }

    public BaseOvertimerequest(String orId) {
        setOrId(orId);
        initialize();
    }

    public BaseOvertimerequest(String orId, Overtimetype orOtNo, Department orEmpDept,
            Employee orEmpNo, Location orEmpLocationNo, Integer orNo, String orReason,
            Date orStartDate, Date orEndDate, BigDecimal orTotalHours, Integer orStatus,
            Date orCreateTime, Date orLastChangeTime) {
        setOrId(orId);
        setOrOtNo(orOtNo);
        setOrEmpDept(orEmpDept);
        setOrEmpNo(orEmpNo);
        setOrEmpLocationNo(orEmpLocationNo);
        setOrNo(orNo);
        setOrReason(orReason);
        setOrStartDate(orStartDate);
        setOrEndDate(orEndDate);
        setOrTotalHours(orTotalHours);
        setOrStatus(orStatus);
        setOrCreateTime(orCreateTime);
        setOrLastChangeTime(orLastChangeTime);
        initialize();
    }

    protected void initialize() {
        this.orSecurityNo = UUID.randomUUID().toString();
    }

    public String getOrId() {
        return this.orId;
    }

    public void setOrId(String orId) {
        this.orId = orId;
        this.hashCode = -2147483648;
    }

    public Integer getOrNo() {
        return this.orNo;
    }

    public void setOrNo(Integer orNo) {
        this.orNo = orNo;
    }

    public String getOrReason() {
        return this.orReason;
    }

    public void setOrReason(String orReason) {
        this.orReason = orReason;
    }

    public Date getOrStartDate() {
        return this.orStartDate;
    }

    public void setOrStartDate(Date orStartDate) {
        this.orStartDate = orStartDate;
    }

    public Date getOrEndDate() {
        return this.orEndDate;
    }

    public void setOrEndDate(Date orEndDate) {
        this.orEndDate = orEndDate;
    }

    public BigDecimal getOrTotalHours() {
        return this.orTotalHours;
    }

    public void setOrTotalHours(BigDecimal orTotalHours) {
        this.orTotalHours = orTotalHours;
    }

    public Integer getOrStatus() {
        return this.orStatus;
    }

    public void setOrStatus(Integer orStatus) {
        this.orStatus = orStatus;
    }

    public Date getOrCreateTime() {
        return this.orCreateTime;
    }

    public void setOrCreateTime(Date orCreateTime) {
        this.orCreateTime = orCreateTime;
    }

    public Date getOrLastChangeTime() {
        return this.orLastChangeTime;
    }

    public void setOrLastChangeTime(Date orLastChangeTime) {
        this.orLastChangeTime = orLastChangeTime;
    }

    public Employee getOrCreateBy() {
        return this.orCreateBy;
    }

    public void setOrCreateBy(Employee orCreateBy) {
        this.orCreateBy = orCreateBy;
    }

    public Overtimetype getOrOtNo() {
        return this.orOtNo;
    }

    public void setOrOtNo(Overtimetype orOtNo) {
        this.orOtNo = orOtNo;
    }

    public Employee getOrLastChangeBy() {
        return this.orLastChangeBy;
    }

    public void setOrLastChangeBy(Employee orLastChangeBy) {
        this.orLastChangeBy = orLastChangeBy;
    }

    public Department getOrEmpDept() {
        return this.orEmpDept;
    }

    public void setOrEmpDept(Department orEmpDept) {
        this.orEmpDept = orEmpDept;
    }

    public Employee getOrEmpNo() {
        return this.orEmpNo;
    }

    public void setOrEmpNo(Employee orEmpNo) {
        this.orEmpNo = orEmpNo;
    }

    public Location getOrEmpLocationNo() {
        return this.orEmpLocationNo;
    }

    public void setOrEmpLocationNo(Location orEmpLocationNo) {
        this.orEmpLocationNo = orEmpLocationNo;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Overtimerequest))
            return false;

        Overtimerequest overtimerequest = (Overtimerequest) obj;
        if ((null == getOrId()) || (null == overtimerequest.getOrId()))
            return false;
        return getOrId().equals(overtimerequest.getOrId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getOrId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getOrId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }

    public String getOrNextApprover() {
        return this.orNextApprover;
    }

    public void setOrNextApprover(String orNextApprover) {
        this.orNextApprover = orNextApprover;
    }

    public BigDecimal getOrTiaoxiuHours() {
        return this.orTiaoxiuHours;
    }

    public void setOrTiaoxiuHours(BigDecimal orTiaoxiuHours) {
        this.orTiaoxiuHours = orTiaoxiuHours;
    }

    public String getOrSecurityNo() {
        return this.orSecurityNo;
    }

    public void setOrSecurityNo(String orSecurityNo) {
        this.orSecurityNo = orSecurityNo;
    }

    public Date getOrTiaoxiuExpire() {
        return this.orTiaoxiuExpire;
    }

    public void setOrTiaoxiuExpire(Date orTiaoxiuExpire) {
        this.orTiaoxiuExpire = orTiaoxiuExpire;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.domain.base.BaseOvertimerequest JD-Core Version: 0.5.4
 */