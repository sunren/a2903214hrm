package com.hr.io.domain.base;

import com.hr.io.domain.Outmatch;
import com.hr.io.domain.OutmatchBasic;
import com.hr.io.domain.OutmatchModel;
import java.io.Serializable;

public abstract class BaseOutmatch implements Serializable {
    public static String REF = "Outmatch";
    public static String PROP_OM_IS_GROUP = "omIsGroup";
    public static String PROP_OM_ID = "omId";
    public static String PROP_OM_SORT_ID = "omSortId";
    public static String PROP_OM_OMM = "omOmm";
    public static String PROP_OM_OMB = "omOmb";
    public static String PROP_OM_STATISTIC = "omStatistic";
    public static String PROP_OM_FORMAT = "omFormat";
    public static String PROP_OM_FIELD_DESC = "omFieldDesc";
    public static String PROP_OM_COLUMN_WIDTH = "omColumnWidth";

    private int hashCode = -2147483648;
    private String omId;
    private String omFieldDesc;
    private String omStatistic;
    private String omFormat;
    private Integer omSortId;
    private Integer omColumnWidth;
    private Integer omIsGroup;
    private OutmatchBasic omOmb;
    private OutmatchModel omOmm;

    public BaseOutmatch() {
        initialize();
    }

    public BaseOutmatch(String omId) {
        setOmId(omId);
        initialize();
    }

    public BaseOutmatch(String omId, OutmatchBasic omOmb, OutmatchModel omOmm, String omFieldDesc,
            Integer omSortId, Integer omColumnWidth, Integer omIsGroup) {
        setOmId(omId);
        setOmOmb(omOmb);
        setOmOmm(omOmm);
        setOmFieldDesc(omFieldDesc);
        setOmSortId(omSortId);
        setOmColumnWidth(omColumnWidth);
        setOmIsGroup(omIsGroup);
        initialize();
    }

    protected void initialize() {
    }

    public String getOmId() {
        return this.omId;
    }

    public void setOmId(String omId) {
        this.omId = omId;
        this.hashCode = -2147483648;
    }

    public String getOmFieldDesc() {
        return this.omFieldDesc;
    }

    public void setOmFieldDesc(String omFieldDesc) {
        this.omFieldDesc = omFieldDesc;
    }

    public String getOmStatistic() {
        return this.omStatistic;
    }

    public void setOmStatistic(String omStatistic) {
        this.omStatistic = omStatistic;
    }

    public String getOmFormat() {
        return this.omFormat;
    }

    public void setOmFormat(String omFormat) {
        this.omFormat = omFormat;
    }

    public Integer getOmSortId() {
        return this.omSortId;
    }

    public void setOmSortId(Integer omSortId) {
        this.omSortId = omSortId;
    }

    public Integer getOmColumnWidth() {
        return this.omColumnWidth;
    }

    public void setOmColumnWidth(Integer omColumnWidth) {
        this.omColumnWidth = omColumnWidth;
    }

    public Integer getOmIsGroup() {
        return this.omIsGroup;
    }

    public void setOmIsGroup(Integer omIsGroup) {
        this.omIsGroup = omIsGroup;
    }

    public OutmatchBasic getOmOmb() {
        return this.omOmb;
    }

    public void setOmOmb(OutmatchBasic omOmb) {
        this.omOmb = omOmb;
    }

    public OutmatchModel getOmOmm() {
        return this.omOmm;
    }

    public void setOmOmm(OutmatchModel omOmm) {
        this.omOmm = omOmm;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Outmatch))
            return false;

        Outmatch outmatch = (Outmatch) obj;
        if ((null == getOmId()) || (null == outmatch.getOmId()))
            return false;
        return getOmId().equals(outmatch.getOmId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getOmId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getOmId().hashCode();
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
 * com.hr.io.domain.base.BaseOutmatch JD-Core Version: 0.5.4
 */