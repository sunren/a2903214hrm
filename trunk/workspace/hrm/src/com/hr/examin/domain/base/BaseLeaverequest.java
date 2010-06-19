package com.hr.examin.domain.base;

import com.hr.base.BaseDomain;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Location;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Leavetype;
import com.hr.profile.domain.Employee;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public abstract class BaseLeaverequest extends BaseDomain implements Serializable {
    public static String REF = "Leaverequest";
    public static String PROP_LR_ID = "lrId";
    public static String PROP_LR_NO = "lrNo";
    public static String PROP_LR_EMP_NO = "lrEmpNo";
    public static String PROP_LR_EMP_DEPT = "lrEmpDept";
    public static String PROP_LR_EMP_LOCATION_NO = "lrEmpLocationNo";
    public static String PROP_LR_LT_NO = "lrLtNo";
    public static String PROP_LR_REASON = "lrReason";
    public static String PROP_LR_START_DATE = "lrStartDate";
    public static String PROP_LR_START_APM = "lrStartApm";
    public static String PROP_LR_END_DATE = "lrEndDate";
    public static String PROP_LR_END_APM = "lrEndApm";
    public static String PROP_LR_TOTAL_HOURS = "lrTotalHours";
    public static String PROP_LR_TOTAL_DAYS = "lrTotalDays";
    public static String PROP_LR_NEXT_APPROVER = "lrNextApprover";
    public static String PROP_LR_STATUS = "lrStatus";
    public static String PROP_LR_CREATE_BY = "lrCreateBy";
    public static String PROP_LR_CREATE_TIME = "lrCreateTime";
    public static String PROP_LR_LAST_CHANGE_BY = "lrLastChangeBy";
    public static String PROP_LR_LAST_CHANGE_TIME = "lrLastChangeTime";
    public static String PROP_LR_SECURITY_NO = "lrSecurityNo";

    private int hashCode = -2147483648;
    private String lrId;
    private Integer lrNo;
    private String lrReason;
    private Date lrStartDate;
    private Integer lrStartApm;
    private Date lrEndDate;
    private Integer lrEndApm;
    private BigDecimal lrTotalHours;
    private BigDecimal lrTotalDays;
    private String lrNextApprover;
    private Integer lrStatus;
    private Date lrCreateTime;
    private Date lrLastChangeTime;
    private String lrSecurityNo;
    private Employee lrCreateBy;
    private Department lrEmpDept;
    private Leavetype lrLtNo;
    private Employee lrLastChangeBy;
    private Employee lrEmpNo;
    private Location lrEmpLocationNo;

    public BaseLeaverequest() {
        initialize();
    }

    public BaseLeaverequest(String lrId) {
        setLrId(lrId);
        initialize();
    }

    public BaseLeaverequest(String lrId, Department lrEmpDept, Leavetype lrLtNo, Employee lrEmpNo,
            Location lrEmpLocationNo, Integer lrNo, String lrReason, Date lrStartDate,
            Date lrEndDate, BigDecimal lrTotalHours, Integer lrStatus, Date lrCreateTime,
            Date lrLastChangeTime) {
        setLrId(lrId);
        setLrEmpDept(lrEmpDept);
        setLrLtNo(lrLtNo);
        setLrEmpNo(lrEmpNo);
        setLrEmpLocationNo(lrEmpLocationNo);
        setLrNo(lrNo);
        setLrReason(lrReason);
        setLrStartDate(lrStartDate);
        setLrEndDate(lrEndDate);
        setLrTotalHours(lrTotalHours);
        setLrStatus(lrStatus);
        setLrCreateTime(lrCreateTime);
        setLrLastChangeTime(lrLastChangeTime);
        initialize();
    }

    protected void initialize() {
        this.lrSecurityNo = UUID.randomUUID().toString();
    }

    public String getLrId() {
        return this.lrId;
    }

    public void setLrId(String lrId) {
        this.lrId = lrId;
        this.hashCode = -2147483648;
    }

    public Integer getLrNo() {
        return this.lrNo;
    }

    public void setLrNo(Integer lrNo) {
        this.lrNo = lrNo;
    }

    public String getLrReason() {
        return this.lrReason;
    }

    public void setLrReason(String lrReason) {
        this.lrReason = lrReason;
    }

    public Date getLrStartDate() {
        return this.lrStartDate;
    }

    public void setLrStartDate(Date lrStartDate) {
        this.lrStartDate = lrStartDate;
    }

    public Date getLrEndDate() {
        return this.lrEndDate;
    }

    public void setLrEndDate(Date lrEndDate) {
        this.lrEndDate = lrEndDate;
    }

    public BigDecimal getLrTotalHours() {
        return this.lrTotalHours;
    }

    public void setLrTotalHours(BigDecimal lrTotalHours) {
        this.lrTotalHours = lrTotalHours;
    }

    public BigDecimal getLrTotalDays() {
        return this.lrTotalDays;
    }

    public void setLrTotalDays(BigDecimal lrTotalDays) {
        this.lrTotalDays = lrTotalDays;
    }

    public Integer getLrStatus() {
        return this.lrStatus;
    }

    public void setLrStatus(Integer lrStatus) {
        this.lrStatus = lrStatus;
    }

    public Date getLrCreateTime() {
        return this.lrCreateTime;
    }

    public void setLrCreateTime(Date lrCreateTime) {
        this.lrCreateTime = lrCreateTime;
    }

    public Date getLrLastChangeTime() {
        return this.lrLastChangeTime;
    }

    public void setLrLastChangeTime(Date lrLastChangeTime) {
        this.lrLastChangeTime = lrLastChangeTime;
    }

    public Employee getLrCreateBy() {
        return this.lrCreateBy;
    }

    public void setLrCreateBy(Employee lrCreateBy) {
        this.lrCreateBy = lrCreateBy;
    }

    public Department getLrEmpDept() {
        return this.lrEmpDept;
    }

    public void setLrEmpDept(Department lrEmpDept) {
        this.lrEmpDept = lrEmpDept;
    }

    public Leavetype getLrLtNo() {
        return this.lrLtNo;
    }

    public void setLrLtNo(Leavetype lrLtNo) {
        this.lrLtNo = lrLtNo;
    }

    public Employee getLrLastChangeBy() {
        return this.lrLastChangeBy;
    }

    public void setLrLastChangeBy(Employee lrLastChangeBy) {
        this.lrLastChangeBy = lrLastChangeBy;
    }

    public Employee getLrEmpNo() {
        return this.lrEmpNo;
    }

    public void setLrEmpNo(Employee lrEmpNo) {
        this.lrEmpNo = lrEmpNo;
    }

    public Location getLrEmpLocationNo() {
        return this.lrEmpLocationNo;
    }

    public void setLrEmpLocationNo(Location lrEmpLocationNo) {
        this.lrEmpLocationNo = lrEmpLocationNo;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Leaverequest))
            return false;

        Leaverequest leaverequest = (Leaverequest) obj;
        if ((null == getLrId()) || (null == leaverequest.getLrId()))
            return false;
        return getLrId().equals(leaverequest.getLrId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getLrId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getLrId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }

    public String getLrNextApprover() {
        return this.lrNextApprover;
    }

    public void setLrNextApprover(String lrNextApprover) {
        this.lrNextApprover = lrNextApprover;
    }

    public Integer getLrStartApm() {
        return this.lrStartApm;
    }

    public void setLrStartApm(Integer lrStartApm) {
        this.lrStartApm = lrStartApm;
    }

    public Integer getLrEndApm() {
        return this.lrEndApm;
    }

    public void setLrEndApm(Integer lrEndApm) {
        this.lrEndApm = lrEndApm;
    }

    public String getLrSecurityNo() {
        return this.lrSecurityNo;
    }

    public void setLrSecurityNo(String lrSecurityNo) {
        this.lrSecurityNo = lrSecurityNo;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.domain.base.BaseLeaverequest JD-Core Version: 0.5.4
 */