package com.hr.examin.domain.base;

import com.hr.base.BaseDomain;
import com.hr.examin.domain.Leavebalance;
import com.hr.profile.domain.Employee;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public abstract class BaseLeavebalance extends BaseDomain implements Serializable {
    public static String REF = "Leavebalance";
    public static String PROP_LB_YEAR = "lbYear";
    public static String PROP_LB_ID = "lbId";
    public static String PROP_LB_COMMENTS = "lbComments";
    public static String PROP_LB_BAL_END_DATE = "lbBalEndDate";
    public static String PROP_LB_EMP_NO = "lbEmpNo";

    /** @deprecated */
    public static String PROP_LB_BAL_FORWARD = "lbBalForward";
    public static String PROP_LB_DAYS_OF_YEAR = "lbDaysOfYear";

    public static String PROP_LB_TIAOXIU_FORWARD = "lbTiaoxiuForward";

    public static String PROP_LB_LEAVE_TYPE = "lbLeaveType";
    public static String PROP_LB_BAL_FORWARD_DAY = "lbBalForwardDay";
    public static String PROP_LB_BAL_FORWARD_HOUR = "lbBalForwardHour";
    public static String PROP_LB_HOURS_OF_YEAR = "lbHoursOfYear";
    public static String PROP_LB_DAYS_FOR_RELEASE = "lbDaysForRelease";
    public static String PROP_LB_HOURS_FOR_RELEASE = "lbHoursForRelease";
    public static String PROP_LB_RELEASE_START_DATE = "lbReleaseStartDate";
    public static String PORP_LB_OTHER_DAYS = "lbOtherDays";
    public static String PROP_LB_OTHER_HOURS = "lbOtherHours";
    public static String PROP_LB_STATUS = "lbStatus";

    private int hashCode = -2147483648;
    private String lbId;
    private String lbYear;
    private BigDecimal lbBalForward = new BigDecimal(0);
    private Date lbBalEndDate;
    private BigDecimal lbDaysOfYear = new BigDecimal(0);
    private String lbComments;
    private BigDecimal lbTiaoxiuForward = new BigDecimal(0);

    private BigDecimal lbTiaoxiuCurrent = new BigDecimal(0);

    private BigDecimal lbTiaoxiuYearCanUse = new BigDecimal(0);
    private Integer lbLeaveType;
    private BigDecimal lbBalForwardDay;
    private BigDecimal lbBalForwardHour;
    private BigDecimal lbHoursOfYear;
    private BigDecimal lbDaysForRelease;
    private BigDecimal lbHoursForRelease;
    private Date lbReleaseStartDate;
    private BigDecimal lbOtherDays;
    private BigDecimal lbOtherHours;
    private Integer lbStatus;
    private String tiaoxiuChoice = "1";

    private String nianjiaChoice = "1";
    private Employee lbEmpNo;

    public BaseLeavebalance() {
        initialize();
    }

    public BaseLeavebalance(String lbId) {
        setLbId(lbId);
        initialize();
    }

    public BaseLeavebalance(String lbId, Employee lbEmpNo, String lbYear) {
        setLbId(lbId);
        setLbEmpNo(lbEmpNo);
        setLbYear(lbYear);
        initialize();
    }

    protected void initialize() {
    }

    public String getLbId() {
        return this.lbId;
    }

    public void setLbId(String lbId) {
        this.lbId = lbId;
        this.hashCode = -2147483648;
    }

    public String getLbYear() {
        return this.lbYear;
    }

    public void setLbYear(String lbYear) {
        this.lbYear = lbYear;
    }

    /** @deprecated */
    public BigDecimal getLbBalForward() {
        return this.lbBalForward;
    }

    /** @deprecated */
    public void setLbBalForward(BigDecimal lbBalForward) {
        this.lbBalForward = lbBalForward;
    }

    public Date getLbBalEndDate() {
        return this.lbBalEndDate;
    }

    public void setLbBalEndDate(Date lbBalEndDate) {
        this.lbBalEndDate = lbBalEndDate;
    }

    public BigDecimal getLbDaysOfYear() {
        return this.lbDaysOfYear;
    }

    public void setLbDaysOfYear(BigDecimal lbDaysOfYear) {
        this.lbDaysOfYear = lbDaysOfYear;
    }

    public String getLbComments() {
        return this.lbComments;
    }

    public void setLbComments(String lbComments) {
        this.lbComments = lbComments;
    }

    public Employee getLbEmpNo() {
        return this.lbEmpNo;
    }

    public void setLbEmpNo(Employee lbEmpNo) {
        this.lbEmpNo = lbEmpNo;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Leavebalance))
            return false;

        Leavebalance leavebalance = (Leavebalance) obj;
        if ((null == getLbId()) || (null == leavebalance.getLbId()))
            return false;
        return getLbId().equals(leavebalance.getLbId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getLbId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getLbId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }

    public BigDecimal getLbTiaoxiuCurrent() {
        return this.lbTiaoxiuCurrent;
    }

    public void setLbTiaoxiuCurrent(BigDecimal lbTiaoxiuCurrent) {
        this.lbTiaoxiuCurrent = lbTiaoxiuCurrent;
    }

    /** @deprecated */
    public BigDecimal getLbTiaoxiuForward() {
        return this.lbTiaoxiuForward;
    }

    /** @deprecated */
    public void setLbTiaoxiuForward(BigDecimal lbTiaoxiuForward) {
        this.lbTiaoxiuForward = lbTiaoxiuForward;
    }

    public String getNianjiaChoice() {
        return this.nianjiaChoice;
    }

    public void setNianjiaChoice(String nianjiaChoice) {
        this.nianjiaChoice = nianjiaChoice;
    }

    public String getTiaoxiuChoice() {
        return this.tiaoxiuChoice;
    }

    public void setTiaoxiuChoice(String tiaoxiuChoice) {
        this.tiaoxiuChoice = tiaoxiuChoice;
    }

    public BigDecimal getLbTiaoxiuYearCanUse() {
        return this.lbTiaoxiuYearCanUse;
    }

    public void setLbTiaoxiuYearCanUse(BigDecimal lbTiaoxiuYearCanUse) {
        this.lbTiaoxiuYearCanUse = lbTiaoxiuYearCanUse;
    }

    public Integer getLbLeaveType() {
        return this.lbLeaveType;
    }

    public void setLbLeaveType(Integer lbLeaveType) {
        this.lbLeaveType = lbLeaveType;
    }

    public BigDecimal getLbBalForwardDay() {
        return this.lbBalForwardDay;
    }

    public void setLbBalForwardDay(BigDecimal lbBalForwardDay) {
        this.lbBalForwardDay = lbBalForwardDay;
    }

    public BigDecimal getLbBalForwardHour() {
        return this.lbBalForwardHour;
    }

    public void setLbBalForwardHour(BigDecimal lbBalForwardHour) {
        this.lbBalForwardHour = lbBalForwardHour;
    }

    public BigDecimal getLbHoursOfYear() {
        return this.lbHoursOfYear;
    }

    public void setLbHoursOfYear(BigDecimal lbHoursOfYear) {
        this.lbHoursOfYear = lbHoursOfYear;
    }

    public BigDecimal getLbDaysForRelease() {
        return this.lbDaysForRelease;
    }

    public void setLbDaysForRelease(BigDecimal lbDaysForRelease) {
        this.lbDaysForRelease = lbDaysForRelease;
    }

    public BigDecimal getLbHoursForRelease() {
        return this.lbHoursForRelease;
    }

    public void setLbHoursForRelease(BigDecimal lbHoursForRelease) {
        this.lbHoursForRelease = lbHoursForRelease;
    }

    public Date getLbReleaseStartDate() {
        return this.lbReleaseStartDate;
    }

    public void setLbReleaseStartDate(Date lbReleaseStartDate) {
        this.lbReleaseStartDate = lbReleaseStartDate;
    }

    public BigDecimal getLbOtherDays() {
        return this.lbOtherDays;
    }

    public void setLbOtherDays(BigDecimal lbOtherDays) {
        this.lbOtherDays = lbOtherDays;
    }

    public BigDecimal getLbOtherHours() {
        return this.lbOtherHours;
    }

    public void setLbOtherHours(BigDecimal lbOtherHours) {
        this.lbOtherHours = lbOtherHours;
    }

    public Integer getLbStatus() {
        return this.lbStatus;
    }

    public void setLbStatus(Integer lbStatus) {
        this.lbStatus = lbStatus;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.domain.base.BaseLeavebalance JD-Core Version: 0.5.4
 */