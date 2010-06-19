package com.hr.examin.domain.base;

import com.hr.base.BaseDomain;
import com.hr.examin.domain.Overtimerequest;
import com.hr.examin.domain.Overtimetype;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;

public abstract class BaseOvertimetype extends BaseDomain implements Serializable {
    public static String REF = "Overtimetype";
    public static String PROP_OT_HOUR_RATE = "otHourRate";
    public static String PROP_OT_OVER_LIMIT = "otOverLimit";
    public static String PROP_OT_NO = "otNo";
    public static String PROP_OT_SORT_ID = "otSortId";
    public static String PROP_OT_DESC = "otDesc";
    public static String PROP_OT_NAME = "otName";

    private int hashCode = -2147483648;
    private String otNo;
    private String otName;
    private String otDesc;
    private Integer otOverLimit;
    private BigDecimal otHourRate;
    private Integer otSortId;
    private Set<Overtimerequest> overtimerequests;

    public BaseOvertimetype() {
        initialize();
    }

    public BaseOvertimetype(String otNo) {
        setOtNo(otNo);
        initialize();
    }

    public BaseOvertimetype(String otNo, String otName, String otDesc, BigDecimal otHourRate,
            Integer otSortId) {
        setOtNo(otNo);
        setOtName(otName);
        setOtDesc(otDesc);
        setOtHourRate(otHourRate);
        setOtSortId(otSortId);
        initialize();
    }

    protected void initialize() {
    }

    public String getOtNo() {
        return this.otNo;
    }

    public void setOtNo(String otNo) {
        this.otNo = otNo;
        this.hashCode = -2147483648;
    }

    public String getOtName() {
        return this.otName;
    }

    public void setOtName(String otName) {
        this.otName = otName;
    }

    public String getOtDesc() {
        return this.otDesc;
    }

    public void setOtDesc(String otDesc) {
        this.otDesc = otDesc;
    }

    public Integer getOtOverLimit() {
        return this.otOverLimit;
    }

    public void setOtOverLimit(Integer otOverLimit) {
        this.otOverLimit = otOverLimit;
    }

    public BigDecimal getOtHourRate() {
        return this.otHourRate;
    }

    public void setOtHourRate(BigDecimal otHourRate) {
        this.otHourRate = otHourRate;
    }

    public Integer getOtSortId() {
        return this.otSortId;
    }

    public void setOtSortId(Integer otSortId) {
        this.otSortId = otSortId;
    }

    public Set<Overtimerequest> getOvertimerequests() {
        return this.overtimerequests;
    }

    public void setOvertimerequests(Set<Overtimerequest> overtimerequests) {
        this.overtimerequests = overtimerequests;
    }

    public void addToOvertimerequests(Overtimerequest overtimerequest) {
        if (null == getOvertimerequests())
            setOvertimerequests(new TreeSet());
        getOvertimerequests().add(overtimerequest);
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Overtimetype))
            return false;

        Overtimetype overtimetype = (Overtimetype) obj;
        if ((null == getOtNo()) || (null == overtimetype.getOtNo()))
            return false;
        return getOtNo().equals(overtimetype.getOtNo());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getOtNo())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getOtNo().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.domain.base.BaseOvertimetype JD-Core Version: 0.5.4
 */