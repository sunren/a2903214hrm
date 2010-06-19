package com.hr.examin.domain.base;

import com.hr.base.BaseDomain;
import com.hr.configuration.domain.Location;
import com.hr.examin.domain.Leavecalendar;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseLeavecalendar extends BaseDomain implements Serializable {
    public static String REF = "Leavecalendar";
    public static String PROP_LC_DATE = "lcDate";
    public static String PROP_LC_SIGN = "lcSign";
    public static String PROP_LC_LOCATION_NO = "lcLocationNo";
    public static String PROP_LC_ID = "lcId";
    public static String PROP_LC_DATE_DESC = "lcDateDesc";

    private int hashCode = -2147483648;
    private String lcId;
    private Date lcDate;
    private Integer lcSign;
    private String lcDateDesc;
    private Location lcLocationNo;

    public BaseLeavecalendar() {
        initialize();
    }

    public BaseLeavecalendar(String lcId) {
        setLcId(lcId);
        initialize();
    }

    public BaseLeavecalendar(String lcId, Date lcDate, Integer lcSign) {
        setLcId(lcId);
        setLcDate(lcDate);
        setLcSign(lcSign);
        initialize();
    }

    protected void initialize() {
    }

    public String getLcId() {
        return this.lcId;
    }

    public void setLcId(String lcId) {
        this.lcId = lcId;
        this.hashCode = -2147483648;
    }

    public Date getLcDate() {
        return this.lcDate;
    }

    public void setLcDate(Date lcDate) {
        this.lcDate = lcDate;
    }

    public Integer getLcSign() {
        return this.lcSign;
    }

    public void setLcSign(Integer lcSign) {
        this.lcSign = lcSign;
    }

    public String getLcDateDesc() {
        return this.lcDateDesc;
    }

    public void setLcDateDesc(String lcDateDesc) {
        this.lcDateDesc = lcDateDesc;
    }

    public Location getLcLocationNo() {
        return this.lcLocationNo;
    }

    public void setLcLocationNo(Location lcLocationNo) {
        this.lcLocationNo = lcLocationNo;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Leavecalendar))
            return false;

        Leavecalendar leavecalendar = (Leavecalendar) obj;
        if ((null == getLcId()) || (null == leavecalendar.getLcId()))
            return false;
        return getLcId().equals(leavecalendar.getLcId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getLcId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getLcId().hashCode();
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
 * com.hr.examin.domain.base.BaseLeavecalendar JD-Core Version: 0.5.4
 */