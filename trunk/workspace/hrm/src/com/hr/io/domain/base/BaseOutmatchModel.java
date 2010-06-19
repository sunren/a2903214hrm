package com.hr.io.domain.base;

import com.hr.io.domain.Iodef;
import com.hr.io.domain.OutmatchModel;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseOutmatchModel implements Serializable {
    public static String REF = "OutmatchModel";
    public static String PROP_OMM_OUTPUT_TYPE = "ommOutputType";
    public static String PROP_OMM_CREATE_TIME = "ommCreateTime";
    public static String PROP_OMM_IO = "ommIo";
    public static String PROP_OMM_NO_TITLE = "ommNoTitle";
    public static String PROP_OMM_CREATE_BY = "ommCreateBy";
    public static String PROP_OMM_STATISTIC_PLACE = "ommStatisticPlace";
    public static String PROP_OMM_ID = "ommId";
    public static String PROP_OMM_ROW_HEIGHT = "ommRowHeight";
    public static String PROP_OMM_STATISTIC_DISPLAY_TYPE = "ommStatisticDisplayType";
    public static String PROP_OMM_DESC = "ommDesc";
    public static String PROP_OMM_LAST_CHANGE_TIME = "ommLastChangeTime";
    public static String PROP_OMM_DEFAULT = "ommDefault";
    public static String PROP_OMM_NAME = "ommName";
    public static String PROP_OMM_LAST_CHANGE_BY = "ommLastChangeBy";

    private int hashCode = -2147483648;
    private String ommId;
    private String ommName;
    private String ommDesc;
    private Date ommCreateTime;
    private Date ommLastChangeTime;
    private Integer ommStatisticPlace;
    private String ommOutputType;
    private Integer ommNoTitle;
    private Integer ommDefault;
    private String ommLastChangeBy;
    private String ommCreateBy;
    private Integer ommStatisticDisplayType;
    private Integer ommRowHeight;
    private Iodef ommIo;

    public BaseOutmatchModel() {
        initialize();
    }

    public BaseOutmatchModel(String ommId) {
        setOmmId(ommId);
        initialize();
    }

    public BaseOutmatchModel(String ommId, Iodef ommIo, String ommName, Date ommCreateTime,
            Date ommLastChangeTime, Integer ommStatisticPlace, String ommOutputType,
            Integer ommNoTitle, Integer ommDefault, String ommLastChangeBy, String ommCreateBy,
            Integer ommStatisticDisplayType) {
        setOmmId(ommId);
        setOmmIo(ommIo);
        setOmmName(ommName);
        setOmmCreateTime(ommCreateTime);
        setOmmLastChangeTime(ommLastChangeTime);
        setOmmStatisticPlace(ommStatisticPlace);
        setOmmOutputType(ommOutputType);
        setOmmNoTitle(ommNoTitle);
        setOmmDefault(ommDefault);
        setOmmLastChangeBy(ommLastChangeBy);
        setOmmCreateBy(ommCreateBy);
        setOmmStatisticDisplayType(ommStatisticDisplayType);
        initialize();
    }

    protected void initialize() {
    }

    public String getOmmId() {
        return this.ommId;
    }

    public void setOmmId(String ommId) {
        this.ommId = ommId;
        this.hashCode = -2147483648;
    }

    public String getOmmName() {
        return this.ommName;
    }

    public void setOmmName(String ommName) {
        this.ommName = ommName;
    }

    public String getOmmDesc() {
        return this.ommDesc;
    }

    public void setOmmDesc(String ommDesc) {
        this.ommDesc = ommDesc;
    }

    public Date getOmmCreateTime() {
        return this.ommCreateTime;
    }

    public void setOmmCreateTime(Date ommCreateTime) {
        this.ommCreateTime = ommCreateTime;
    }

    public Date getOmmLastChangeTime() {
        return this.ommLastChangeTime;
    }

    public void setOmmLastChangeTime(Date ommLastChangeTime) {
        this.ommLastChangeTime = ommLastChangeTime;
    }

    public Integer getOmmStatisticPlace() {
        return this.ommStatisticPlace;
    }

    public void setOmmStatisticPlace(Integer ommStatisticPlace) {
        this.ommStatisticPlace = ommStatisticPlace;
    }

    public String getOmmOutputType() {
        return this.ommOutputType;
    }

    public void setOmmOutputType(String ommOutputType) {
        this.ommOutputType = ommOutputType;
    }

    public Integer getOmmNoTitle() {
        return this.ommNoTitle;
    }

    public void setOmmNoTitle(Integer ommNoTitle) {
        this.ommNoTitle = ommNoTitle;
    }

    public Integer getOmmDefault() {
        return this.ommDefault;
    }

    public void setOmmDefault(Integer ommDefault) {
        this.ommDefault = ommDefault;
    }

    public String getOmmLastChangeBy() {
        return this.ommLastChangeBy;
    }

    public void setOmmLastChangeBy(String ommLastChangeBy) {
        this.ommLastChangeBy = ommLastChangeBy;
    }

    public String getOmmCreateBy() {
        return this.ommCreateBy;
    }

    public void setOmmCreateBy(String ommCreateBy) {
        this.ommCreateBy = ommCreateBy;
    }

    public Integer getOmmStatisticDisplayType() {
        return this.ommStatisticDisplayType;
    }

    public void setOmmStatisticDisplayType(Integer ommStatisticDisplayType) {
        this.ommStatisticDisplayType = ommStatisticDisplayType;
    }

    public Integer getOmmRowHeight() {
        return this.ommRowHeight;
    }

    public void setOmmRowHeight(Integer ommRowHeight) {
        this.ommRowHeight = ommRowHeight;
    }

    public Iodef getOmmIo() {
        return this.ommIo;
    }

    public void setOmmIo(Iodef ommIo) {
        this.ommIo = ommIo;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof OutmatchModel))
            return false;

        OutmatchModel outmatchModel = (OutmatchModel) obj;
        if ((null == getOmmId()) || (null == outmatchModel.getOmmId()))
            return false;
        return getOmmId().equals(outmatchModel.getOmmId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getOmmId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getOmmId().hashCode();
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
 * com.hr.io.domain.base.BaseOutmatchModel JD-Core Version: 0.5.4
 */