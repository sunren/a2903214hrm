package com.hr.examin.domain.base;

import com.hr.examin.domain.Attendperiod;
import java.io.Serializable;

public abstract class BaseAttendperiod implements Serializable {
    public static String REF = "Attendperiod";
    public static String PROP_ATTP_YEAR = "attpYear";
    public static String PROP_ATTP_YEARMONTH = "attpYearmonth";
    public static String PROP_ATTP_STATUS = "attpStatus";
    public static String PROP_ATTP_MONTH = "attpMonth";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private String attpYearmonth;
    private String attpYear;
    private String attpMonth;
    private Integer attpStatus;

    public BaseAttendperiod() {
        initialize();
    }

    public BaseAttendperiod(String id) {
        setId(id);
        initialize();
    }

    public BaseAttendperiod(String id, String attpYearmonth, String attpYear, String attpMonth,
            Integer attpStatus) {
        setId(id);
        setAttpYearmonth(attpYearmonth);
        setAttpYear(attpYear);
        setAttpMonth(attpMonth);
        setAttpStatus(attpStatus);
        initialize();
    }

    protected void initialize() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getAttpYearmonth() {
        return this.attpYearmonth;
    }

    public void setAttpYearmonth(String attpYearmonth) {
        this.attpYearmonth = attpYearmonth;
    }

    public String getAttpYear() {
        return this.attpYear;
    }

    public void setAttpYear(String attpYear) {
        this.attpYear = attpYear;
    }

    public String getAttpMonth() {
        return this.attpMonth;
    }

    public void setAttpMonth(String attpMonth) {
        this.attpMonth = attpMonth;
    }

    public Integer getAttpStatus() {
        return this.attpStatus;
    }

    public void setAttpStatus(Integer attpStatus) {
        this.attpStatus = attpStatus;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Attendperiod))
            return false;

        Attendperiod attendperiod = (Attendperiod) obj;
        if ((null == getId()) || (null == attendperiod.getId()))
            return false;
        return getId().equals(attendperiod.getId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.domain.base.BaseAttendperiod JD-Core Version: 0.5.4
 */