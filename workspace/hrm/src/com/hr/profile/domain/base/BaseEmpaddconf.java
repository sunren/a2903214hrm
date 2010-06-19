package com.hr.profile.domain.base;

import com.hr.base.BaseDomain;
import com.hr.profile.domain.Empaddconf;
import java.io.Serializable;

public abstract class BaseEmpaddconf extends BaseDomain implements Serializable {
    public static String REF = "Empaddconf";
    public static String PROP_EADC_FIELD_TYPE = "eadcFieldType";
    public static String PROP_EADC_TABLE_NAME = "eadcTableName";
    public static String PROP_EADC_COMMENTS = "eadcComments";
    public static String PROP_EADC_ID = "eadcId";
    public static String PROP_EADC_FIELD_VALUE = "eadcFieldValue";
    public static String PROP_EADC_FIELD_NAME = "eadcFieldName";
    public static String PROP_EADC_SEQ = "eadcSeq";

    private int hashCode = -2147483648;
    private String eadcId;
    private String eadcTableName;
    private Integer eadcSeq;
    private String eadcFieldType;
    private String eadcFieldValue;
    private String eadcComments;
    private String eadcFieldName;

    public BaseEmpaddconf() {
        initialize();
    }

    public BaseEmpaddconf(String eadcId) {
        setEadcId(eadcId);
        initialize();
    }

    protected void initialize() {
    }

    public String getEadcId() {
        return this.eadcId;
    }

    public void setEadcId(String eadcId) {
        this.eadcId = eadcId;
        this.hashCode = -2147483648;
    }

    public String getEadcTableName() {
        return this.eadcTableName;
    }

    public void setEadcTableName(String eadcTableName) {
        this.eadcTableName = eadcTableName;
    }

    public Integer getEadcSeq() {
        return this.eadcSeq;
    }

    public void setEadcSeq(Integer eadcSeq) {
        this.eadcSeq = eadcSeq;
    }

    public String getEadcFieldType() {
        return this.eadcFieldType;
    }

    public void setEadcFieldType(String eadcFieldType) {
        this.eadcFieldType = eadcFieldType;
    }

    public String getEadcFieldValue() {
        return this.eadcFieldValue;
    }

    public void setEadcFieldValue(String eadcFieldValue) {
        this.eadcFieldValue = eadcFieldValue;
    }

    public String getEadcComments() {
        return this.eadcComments;
    }

    public void setEadcComments(String eadcComments) {
        this.eadcComments = eadcComments;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Empaddconf))
            return false;

        Empaddconf empaddconf = (Empaddconf) obj;
        if ((null == getEadcId()) || (null == empaddconf.getEadcId()))
            return false;
        return getEadcId().equals(empaddconf.getEadcId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getEadcId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getEadcId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }

    public String getEadcFieldName() {
        return this.eadcFieldName;
    }

    public void setEadcFieldName(String eadcFieldName) {
        this.eadcFieldName = eadcFieldName;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.domain.base.BaseEmpaddconf JD-Core Version: 0.5.4
 */