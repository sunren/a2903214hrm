package com.hr.compensation.domain.base;

import com.hr.base.BaseDomain;
import com.hr.compensation.domain.Empsalaryperiod;
import java.io.Serializable;

public abstract class BaseEmpsalaryperiod extends BaseDomain implements Serializable {
    public static String REF = "Empsalaryperiod";
    public static String PROP_ESPD_YEARMONTH = "espdYearmonth";
    public static String PROP_ESPD_STATUS = "espdStatus";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private String espdYearmonth;
    private Integer espdStatus;

    public BaseEmpsalaryperiod() {
        initialize();
    }

    public BaseEmpsalaryperiod(String id) {
        setId(id);
        initialize();
    }

    public BaseEmpsalaryperiod(String id, String espdYearmonth, Integer espdStatus) {
        setId(id);
        setEspdYearmonth(espdYearmonth);
        setEspdStatus(espdStatus);
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

    public String getEspdYearmonth() {
        return this.espdYearmonth;
    }

    public void setEspdYearmonth(String espdYearmonth) {
        this.espdYearmonth = espdYearmonth;
    }

    public Integer getEspdStatus() {
        return this.espdStatus;
    }

    public void setEspdStatus(Integer espdStatus) {
        this.espdStatus = espdStatus;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Empsalaryperiod))
            return false;

        Empsalaryperiod empsalaryperiod = (Empsalaryperiod) obj;
        if ((null == getId()) || (null == empsalaryperiod.getId()))
            return false;
        return getId().equals(empsalaryperiod.getId());
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
 * com.hr.compensation.domain.base.BaseEmpsalaryperiod JD-Core Version: 0.5.4
 */