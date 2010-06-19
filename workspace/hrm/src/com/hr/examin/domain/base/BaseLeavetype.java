package com.hr.examin.domain.base;

import com.hr.base.BaseDomain;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Leavetype;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public abstract class BaseLeavetype extends BaseDomain implements Serializable {
    public static String REF = "Leavetype";
    public static String PROP_LT_MAX_APPLY_DAYS = "ltMaxApplyDays";
    public static String PROP_LT_MAX_APPLY_HOURS = "ltMaxApplyHours";
    public static String PROP_LT_MIN_APPLY_DAYS = "ltMinApplyDays";
    public static String PROP_LT_MIN_APPLY_HOURS = "ltMinApplyHours";
    public static String PROP_LT_NO = "ltNo";
    public static String PROP_LT_SPECIAL_CAT = "ltSpecialCat";
    public static String PROP_LT_DESC = "ltDesc";
    public static String PROP_LT_SORT_ID = "ltSortId";
    public static String PROP_LT_INCL_HOLIDAY = "ltInclHoliday";
    public static String PROP_LT_NAME = "ltName";
    public static final int ANNUAL_LEAVE = 1;
    public static final int TIAOXIU_NOEXP = 10;
    public static final int PAID_SICK_LEAVE = 5;
    private int hashCode = -2147483648;
    private String ltNo;
    private String ltName;
    private String ltDesc;
    private BigDecimal ltMinApplyDays;
    private BigDecimal ltMinApplyHours;
    private BigDecimal ltMaxApplyDays;
    private BigDecimal ltMaxApplyHours;
    private Date ltBalForward;
    private BigDecimal ltBalForwardDayLimit;
    private BigDecimal ltBalForwardHourLimit;
    private BigDecimal ltBalForwardPerLimit;
    private Integer ltBalForwardRounding;
    private String ltDaysOfYear;
    private String ltHoursOfYear;
    private String ltDaysForRelease;
    private String ltHoursForRelease;
    private String ltReleaseMethod;
    private Integer ltReleaseRounding;
    private String ltOtherParameter;
    private Integer ltNeedComments;
    private Integer ltInclHoliday;
    private Integer ltSpecialCat;
    private Integer ltSortId;
    private Set<Leaverequest> leaverequests;

    public BaseLeavetype() {
        initialize();
    }

    public BaseLeavetype(String ltNo) {
        setLtNo(ltNo);
        initialize();
    }

    public BaseLeavetype(String ltNo, String ltName, String ltDesc, Integer ltInclHoliday,
            Integer ltAnnual, Integer ltSortId) {
        setLtNo(ltNo);
        setLtName(ltName);
        setLtDesc(ltDesc);
        setLtInclHoliday(ltInclHoliday);
        setLtSpecialCat(ltAnnual);
        setLtSortId(ltSortId);
        initialize();
    }

    protected void initialize() {
    }

    public String getLtNo() {
        return this.ltNo;
    }

    public void setLtNo(String ltNo) {
        this.ltNo = ltNo;
        this.hashCode = -2147483648;
    }

    public String getLtName() {
        return this.ltName;
    }

    public void setLtName(String ltName) {
        this.ltName = ltName;
    }

    public String getLtDesc() {
        return this.ltDesc;
    }

    public void setLtDesc(String ltDesc) {
        this.ltDesc = ltDesc;
    }

    public BigDecimal getLtMaxApplyDays() {
        return this.ltMaxApplyDays;
    }

    public void setLtMaxApplyDays(BigDecimal ltMaxApplyDays) {
        this.ltMaxApplyDays = ltMaxApplyDays;
    }

    public Integer getLtInclHoliday() {
        return this.ltInclHoliday;
    }

    public void setLtInclHoliday(Integer ltInclHoliday) {
        this.ltInclHoliday = ltInclHoliday;
    }

    public Integer getLtSpecialCat() {
        return this.ltSpecialCat;
    }

    public void setLtSpecialCat(Integer ltSpecialCat) {
        this.ltSpecialCat = ltSpecialCat;
    }

    public Integer getLtSortId() {
        return this.ltSortId;
    }

    public void setLtSortId(Integer ltSortId) {
        this.ltSortId = ltSortId;
    }

    public Set<Leaverequest> getLeaverequests() {
        return this.leaverequests;
    }

    public void setLeaverequests(Set<Leaverequest> leaverequests) {
        this.leaverequests = leaverequests;
    }

    public void addToleaverequests(Leaverequest leaverequest) {
        if (null == getLeaverequests())
            setLeaverequests(new TreeSet());
        getLeaverequests().add(leaverequest);
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Leavetype))
            return false;

        Leavetype leavetype = (Leavetype) obj;
        if ((null == getLtNo()) || (null == leavetype.getLtNo()))
            return false;
        return getLtNo().equals(leavetype.getLtNo());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getLtNo())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getLtNo().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public BigDecimal getLtMaxApplyHours() {
        return this.ltMaxApplyHours;
    }

    public void setLtMaxApplyHours(BigDecimal ltMaxApplyHours) {
        this.ltMaxApplyHours = ltMaxApplyHours;
    }

    public Date getLtBalForward() {
        return this.ltBalForward;
    }

    public void setLtBalForward(Date ltBalForward) {
        this.ltBalForward = ltBalForward;
    }

    public BigDecimal getLtBalForwardDayLimit() {
        return this.ltBalForwardDayLimit;
    }

    public void setLtBalForwardDayLimit(BigDecimal ltBalForwardDayLimit) {
        this.ltBalForwardDayLimit = ltBalForwardDayLimit;
    }

    public BigDecimal getLtBalForwardHourLimit() {
        return this.ltBalForwardHourLimit;
    }

    public void setLtBalForwardHourLimit(BigDecimal ltBalForwardHourLimit) {
        this.ltBalForwardHourLimit = ltBalForwardHourLimit;
    }

    public BigDecimal getLtBalForwardPerLimit() {
        return this.ltBalForwardPerLimit;
    }

    public void setLtBalForwardPerLimit(BigDecimal ltBalForwardPerLimit) {
        this.ltBalForwardPerLimit = ltBalForwardPerLimit;
    }

    public Integer getLtBalForwardRounding() {
        return this.ltBalForwardRounding;
    }

    public void setLtBalForwardRounding(Integer ltBalForwardRounding) {
        this.ltBalForwardRounding = ltBalForwardRounding;
    }

    public String getLtDaysOfYear() {
        return this.ltDaysOfYear;
    }

    public void setLtDaysOfYear(String ltDaysOfYear) {
        this.ltDaysOfYear = ltDaysOfYear;
    }

    public String getLtHoursOfYear() {
        return this.ltHoursOfYear;
    }

    public void setLtHoursOfYear(String ltHoursOfYear) {
        this.ltHoursOfYear = ltHoursOfYear;
    }

    public String getLtDaysForRelease() {
        return this.ltDaysForRelease;
    }

    public void setLtDaysForRelease(String ltDaysForRelease) {
        this.ltDaysForRelease = ltDaysForRelease;
    }

    public String getLtHoursForRelease() {
        return this.ltHoursForRelease;
    }

    public void setLtHoursForRelease(String ltHoursForRelease) {
        this.ltHoursForRelease = ltHoursForRelease;
    }

    public String getLtReleaseMethod() {
        return this.ltReleaseMethod;
    }

    public void setLtReleaseMethod(String ltReleaseMethod) {
        this.ltReleaseMethod = ltReleaseMethod;
    }

    public Integer getLtReleaseRounding() {
        return this.ltReleaseRounding;
    }

    public void setLtReleaseRounding(Integer ltReleaseRounding) {
        this.ltReleaseRounding = ltReleaseRounding;
    }

    public String getLtOtherParameter() {
        return this.ltOtherParameter;
    }

    public void setLtOtherParameter(String ltOtherParameter) {
        this.ltOtherParameter = ltOtherParameter;
    }

    public String toString() {
        return super.toString();
    }

    public Integer getLtNeedComments() {
        return this.ltNeedComments;
    }

    public void setLtNeedComments(Integer ltNeedComments) {
        this.ltNeedComments = ltNeedComments;
    }

    public BigDecimal getLtMinApplyDays() {
        return this.ltMinApplyDays;
    }

    public void setLtMinApplyDays(BigDecimal ltMinApplyDays) {
        this.ltMinApplyDays = ltMinApplyDays;
    }

    public BigDecimal getLtMinApplyHours() {
        return this.ltMinApplyHours;
    }

    public void setLtMinApplyHours(BigDecimal ltMinApplyHours) {
        this.ltMinApplyHours = ltMinApplyHours;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.domain.base.BaseLeavetype JD-Core Version: 0.5.4
 */