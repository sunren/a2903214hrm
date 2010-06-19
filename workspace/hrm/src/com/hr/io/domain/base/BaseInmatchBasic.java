package com.hr.io.domain.base;

import com.hr.io.domain.InmatchBasic;
import com.hr.io.domain.Iodef;
import java.io.Serializable;

public abstract class BaseInmatchBasic implements Serializable {
    public static String REF = "InmatchBasic";
    public static String PROP_IMB_SAMPLE = "imbSample";
    public static String PROP_IMB_FORMAT = "imbFormat";
    public static String PROP_IMB_SORT_ID = "imbSortId";
    public static String PROP_IMB_ID = "imbId";
    public static String PROP_IMB_FIELD_NAME = "imbFieldName";
    public static String PROP_IMB_DETECT_ERROR = "imbDetectError";
    public static String PROP_IMB_REQUIRED = "imbRequired";
    public static String PROP_IMB_IO = "imbIo";
    public static String PROP_IMB_FIELD_DESC = "imbFieldDesc";
    public static String PROP_IMB_DATA_TYPE = "imbDataType";

    private int hashCode = -2147483648;
    private String imbId;
    private String imbFieldName;
    private String imbFieldDesc;
    private String imbDataType;
    private String imbFormat;
    private Integer imbDetectError;
    private Integer imbRequired;
    private Integer imbSortId;
    private String imbSample;
    private Iodef imbIo;

    public BaseInmatchBasic() {
        initialize();
    }

    public BaseInmatchBasic(String imbId) {
        setImbId(imbId);
        initialize();
    }

    public BaseInmatchBasic(String imbId, Iodef imbIo, String imbFieldName, String imbFieldDesc,
            String imbDataType, Integer imbDetectError, Integer imbRequired, Integer imbSortId,
            String imbSample) {
        setImbId(imbId);
        setImbIo(imbIo);
        setImbFieldName(imbFieldName);
        setImbFieldDesc(imbFieldDesc);
        setImbDataType(imbDataType);
        setImbDetectError(imbDetectError);
        setImbRequired(imbRequired);
        setImbSortId(imbSortId);
        setImbSample(imbSample);
        initialize();
    }

    protected void initialize() {
    }

    public String getImbId() {
        return this.imbId;
    }

    public void setImbId(String imbId) {
        this.imbId = imbId;
        this.hashCode = -2147483648;
    }

    public String getImbFieldName() {
        return this.imbFieldName;
    }

    public void setImbFieldName(String imbFieldName) {
        this.imbFieldName = imbFieldName;
    }

    public String getImbFieldDesc() {
        return this.imbFieldDesc;
    }

    public void setImbFieldDesc(String imbFieldDesc) {
        this.imbFieldDesc = imbFieldDesc;
    }

    public String getImbDataType() {
        return this.imbDataType;
    }

    public void setImbDataType(String imbDataType) {
        this.imbDataType = imbDataType;
    }

    public String getImbFormat() {
        return this.imbFormat;
    }

    public void setImbFormat(String imbFormat) {
        this.imbFormat = imbFormat;
    }

    public Integer getImbDetectError() {
        return this.imbDetectError;
    }

    public void setImbDetectError(Integer imbDetectError) {
        this.imbDetectError = imbDetectError;
    }

    public Integer getImbRequired() {
        return this.imbRequired;
    }

    public void setImbRequired(Integer imbRequired) {
        this.imbRequired = imbRequired;
    }

    public Integer getImbSortId() {
        return this.imbSortId;
    }

    public void setImbSortId(Integer imbSortId) {
        this.imbSortId = imbSortId;
    }

    public String getImbSample() {
        return this.imbSample;
    }

    public void setImbSample(String imbSample) {
        this.imbSample = imbSample;
    }

    public Iodef getImbIo() {
        return this.imbIo;
    }

    public void setImbIo(Iodef imbIo) {
        this.imbIo = imbIo;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof InmatchBasic))
            return false;

        InmatchBasic inmatchBasic = (InmatchBasic) obj;
        if ((null == getImbId()) || (null == inmatchBasic.getImbId()))
            return false;
        return getImbId().equals(inmatchBasic.getImbId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getImbId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getImbId().hashCode();
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
 * com.hr.io.domain.base.BaseInmatchBasic JD-Core Version: 0.5.4
 */