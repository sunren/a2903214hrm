package com.hr.io.domain.base;

import com.hr.io.domain.Inmatch;
import com.hr.io.domain.InmatchBasic;
import com.hr.io.domain.InmatchModel;
import java.io.Serializable;

public abstract class BaseInmatch implements Serializable {
    public static String REF = "Inmatch";
    public static String PROP_IM_FIELD_DESC = "imFieldDesc";
    public static String PROP_IM_IS_INPUT = "imIsInput";
    public static String PROP_IM_FORMAT = "imFormat";
    public static String PROP_IM_IMM = "imImm";
    public static String PROP_IM_DETECT_ERROR = "imDetectError";
    public static String PROP_IM_ID = "imId";
    public static String PROP_IM_SAMPLE_WIDTH = "imSampleWidth";
    public static String PROP_IM_SAMPLE = "imSample";
    public static String PROP_IM_SORT_ID = "imSortId";
    public static String PROP_IM_REQUIRED = "imRequired";
    public static String PROP_IM_IMB = "imImb";

    private int hashCode = -2147483648;
    private String imId;
    private String imFieldDesc;
    private String imFormat;
    private Integer imDetectError;
    private Integer imRequired;
    private Integer imIsInput;
    private Integer imSortId;
    private String imSample;
    private Integer imSampleWidth;
    private InmatchModel imImm;
    private InmatchBasic imImb;

    public BaseInmatch() {
        initialize();
    }

    public BaseInmatch(String imId) {
        setImId(imId);
        initialize();
    }

    public BaseInmatch(String imId, InmatchModel imImm, InmatchBasic imImb, String imFieldDesc,
            Integer imDetectError, Integer imRequired, Integer imIsInput, Integer imSortId,
            String imSample) {
        setImId(imId);
        setImImm(imImm);
        setImImb(imImb);
        setImFieldDesc(imFieldDesc);
        setImDetectError(imDetectError);
        setImRequired(imRequired);
        setImIsInput(imIsInput);
        setImSortId(imSortId);
        setImSample(imSample);
        initialize();
    }

    protected void initialize() {
    }

    public String getImId() {
        return this.imId;
    }

    public void setImId(String imId) {
        this.imId = imId;
        this.hashCode = -2147483648;
    }

    public String getImFieldDesc() {
        return this.imFieldDesc;
    }

    public void setImFieldDesc(String imFieldDesc) {
        this.imFieldDesc = imFieldDesc;
    }

    public String getImFormat() {
        return this.imFormat;
    }

    public void setImFormat(String imFormat) {
        this.imFormat = imFormat;
    }

    public Integer getImDetectError() {
        return this.imDetectError;
    }

    public void setImDetectError(Integer imDetectError) {
        this.imDetectError = imDetectError;
    }

    public Integer getImRequired() {
        return this.imRequired;
    }

    public void setImRequired(Integer imRequired) {
        this.imRequired = imRequired;
    }

    public Integer getImIsInput() {
        return this.imIsInput;
    }

    public void setImIsInput(Integer imIsInput) {
        this.imIsInput = imIsInput;
    }

    public Integer getImSortId() {
        return this.imSortId;
    }

    public void setImSortId(Integer imSortId) {
        this.imSortId = imSortId;
    }

    public String getImSample() {
        return this.imSample;
    }

    public void setImSample(String imSample) {
        this.imSample = imSample;
    }

    public Integer getImSampleWidth() {
        return this.imSampleWidth;
    }

    public void setImSampleWidth(Integer imSampleWidth) {
        this.imSampleWidth = imSampleWidth;
    }

    public InmatchModel getImImm() {
        return this.imImm;
    }

    public void setImImm(InmatchModel imImm) {
        this.imImm = imImm;
    }

    public InmatchBasic getImImb() {
        return this.imImb;
    }

    public void setImImb(InmatchBasic imImb) {
        this.imImb = imImb;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Inmatch))
            return false;

        Inmatch inmatch = (Inmatch) obj;
        if ((null == getImId()) || (null == inmatch.getImId()))
            return false;
        return getImId().equals(inmatch.getImId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getImId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getImId().hashCode();
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
 * com.hr.io.domain.base.BaseInmatch JD-Core Version: 0.5.4
 */