package com.hr.compensation.domain.base;

import com.hr.base.BaseDomain;
import com.hr.compensation.domain.Empsalaryacct;
import com.hr.compensation.domain.Empsalaryacctversion;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseEmpsalaryacctversion extends BaseDomain implements Serializable {
    public static String REF = "Empsalaryacctversion";
    public static String PROP_ESAV_VALID_TO = "esavValidTo";
    public static String PROP_ESAV_CREATE_TIME = "esavCreateTime";
    public static String PROP_ESAV_CREATE_BY = "esavCreateBy";
    public static String PROP_ESAV_VALID_FROM = "esavValidFrom";
    public static String PROP_ESAV_LAST_CHANGE_TIME = "esavLastChangeTime";
    public static String PROP_ESAV_LAST_CHANGE_BY = "esavLastChangeBy";
    public static String PROP_ESAV_ESAC = "esavEsac";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private Date esavValidFrom;
    private Date esavValidTo;
    private String esavCreateBy;
    private Date esavCreateTime;
    private String esavLastChangeBy;
    private Date esavLastChangeTime;
    private Empsalaryacct esavEsac;

    public BaseEmpsalaryacctversion() {
        initialize();
    }

    public BaseEmpsalaryacctversion(String id) {
        setId(id);
        initialize();
    }

    public BaseEmpsalaryacctversion(String id, Empsalaryacct esavEsac, Date esavValidFrom,
            String esavCreateBy, Date esavCreateTime, String esavLastChangeBy,
            Date esavLastChangeTime) {
        setId(id);
        setEsavEsac(esavEsac);
        setEsavValidFrom(esavValidFrom);
        setEsavCreateBy(esavCreateBy);
        setEsavCreateTime(esavCreateTime);
        setEsavLastChangeBy(esavLastChangeBy);
        setEsavLastChangeTime(esavLastChangeTime);
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

    public Date getEsavValidFrom() {
        return this.esavValidFrom;
    }

    public void setEsavValidFrom(Date esavValidFrom) {
        this.esavValidFrom = esavValidFrom;
    }

    public Date getEsavValidTo() {
        return this.esavValidTo;
    }

    public void setEsavValidTo(Date esavValidTo) {
        this.esavValidTo = esavValidTo;
    }

    public String getEsavCreateBy() {
        return this.esavCreateBy;
    }

    public void setEsavCreateBy(String esavCreateBy) {
        this.esavCreateBy = esavCreateBy;
    }

    public Date getEsavCreateTime() {
        return this.esavCreateTime;
    }

    public void setEsavCreateTime(Date esavCreateTime) {
        this.esavCreateTime = esavCreateTime;
    }

    public String getEsavLastChangeBy() {
        return this.esavLastChangeBy;
    }

    public void setEsavLastChangeBy(String esavLastChangeBy) {
        this.esavLastChangeBy = esavLastChangeBy;
    }

    public Date getEsavLastChangeTime() {
        return this.esavLastChangeTime;
    }

    public void setEsavLastChangeTime(Date esavLastChangeTime) {
        this.esavLastChangeTime = esavLastChangeTime;
    }

    public Empsalaryacct getEsavEsac() {
        return this.esavEsac;
    }

    public void setEsavEsac(Empsalaryacct esavEsac) {
        this.esavEsac = esavEsac;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Empsalaryacctversion))
            return false;

        Empsalaryacctversion empsalaryacctversion = (Empsalaryacctversion) obj;
        if ((null == getId()) || (null == empsalaryacctversion.getId()))
            return false;
        return getId().equals(empsalaryacctversion.getId());
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
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.domain.base.BaseEmpsalaryacctversion JD-Core Version: 0.5.4
 */