package com.hr.io.domain.base;

import com.hr.io.domain.Iodef;
import com.hr.io.domain.OutmatchBasic;
import java.io.Serializable;

public abstract class BaseOutmatchBasic implements Serializable {
    public static String REF = "OutmatchBasic";
    public static String PROP_OMB_DATA_TYPE = "ombDataType";
    public static String PROP_OMB_FIELD_DESC = "ombFieldDesc";
    public static String PROP_OMB_FIELD_NAME = "ombFieldName";
    public static String PROP_OMB_IO = "ombIo";
    public static String PROP_OMB_ID = "ombId";
    public static String PROP_OMB_SORT_ID = "ombSortId";
    public static String PROP_OMB_FORMAT = "ombFormat";
    public static String PROP_OMB_COLUMN_WIDTH = "ombColumnWidth";
    public static String PROP_OMB_CAN_GROUP = "ombCanGroup";

    private int hashCode = -2147483648;
    private String ombId;
    private String ombFieldName;
    private Integer ombCanGroup;
    private String ombDataType;
    private String ombFieldDesc;
    private Integer ombSortId;
    private String ombFormat;
    private Integer ombColumnWidth;
    private Iodef ombIo;

    public BaseOutmatchBasic() {
        initialize();
    }

    public BaseOutmatchBasic(String ombId) {
        setOmbId(ombId);
        initialize();
    }

    public BaseOutmatchBasic(String ombId, Iodef ombIo, String ombFieldName, Integer ombCanGroup,
            String ombDataType, String ombFieldDesc, Integer ombSortId, Integer ombColumnWidth) {
        setOmbId(ombId);
        setOmbIo(ombIo);
        setOmbFieldName(ombFieldName);
        setOmbCanGroup(ombCanGroup);
        setOmbDataType(ombDataType);
        setOmbFieldDesc(ombFieldDesc);
        setOmbSortId(ombSortId);
        setOmbColumnWidth(ombColumnWidth);
        initialize();
    }

    protected void initialize() {
    }

    public String getOmbId() {
        return this.ombId;
    }

    public void setOmbId(String ombId) {
        this.ombId = ombId;
        this.hashCode = -2147483648;
    }

    public String getOmbFieldName() {
        return this.ombFieldName;
    }

    public void setOmbFieldName(String ombFieldName) {
        this.ombFieldName = ombFieldName;
    }

    public Integer getOmbCanGroup() {
        return this.ombCanGroup;
    }

    public void setOmbCanGroup(Integer ombCanGroup) {
        this.ombCanGroup = ombCanGroup;
    }

    public String getOmbDataType() {
        return this.ombDataType;
    }

    public void setOmbDataType(String ombDataType) {
        this.ombDataType = ombDataType;
    }

    public String getOmbFieldDesc() {
        return this.ombFieldDesc;
    }

    public void setOmbFieldDesc(String ombFieldDesc) {
        this.ombFieldDesc = ombFieldDesc;
    }

    public Integer getOmbSortId() {
        return this.ombSortId;
    }

    public void setOmbSortId(Integer ombSortId) {
        this.ombSortId = ombSortId;
    }

    public String getOmbFormat() {
        return this.ombFormat;
    }

    public void setOmbFormat(String ombFormat) {
        this.ombFormat = ombFormat;
    }

    public Integer getOmbColumnWidth() {
        return this.ombColumnWidth;
    }

    public void setOmbColumnWidth(Integer ombColumnWidth) {
        this.ombColumnWidth = ombColumnWidth;
    }

    public Iodef getOmbIo() {
        return this.ombIo;
    }

    public void setOmbIo(Iodef ombIo) {
        this.ombIo = ombIo;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof OutmatchBasic))
            return false;

        OutmatchBasic outmatchBasic = (OutmatchBasic) obj;
        if ((null == getOmbId()) || (null == outmatchBasic.getOmbId()))
            return false;
        return getOmbId().equals(outmatchBasic.getOmbId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getOmbId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getOmbId().hashCode();
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
 * com.hr.io.domain.base.BaseOutmatchBasic JD-Core Version: 0.5.4
 */