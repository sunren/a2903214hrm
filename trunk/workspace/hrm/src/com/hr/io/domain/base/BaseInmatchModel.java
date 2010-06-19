package com.hr.io.domain.base;

import com.hr.io.domain.InmatchModel;
import com.hr.io.domain.Iodef;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseInmatchModel implements Serializable {
    public static String REF = "InmatchModel";
    public static String PROP_IMM_IO = "immIo";
    public static String PROP_IMM_INPUT_TYPE = "immInputType";
    public static String PROP_IMM_LAST_CHANGE_BY = "immLastChangeBy";
    public static String PROP_IMM_DESC = "immDesc";
    public static String PROP_IMM_ID = "immId";
    public static String PROP_IMM_IMPORT_MODE = "immImportMode";
    public static String PROP_IMM_NAME = "immName";
    public static String PROP_IMM_CREATE_TIME = "immCreateTime";
    public static String PROP_IMM_DEFAULT = "immDefault";
    public static String PROP_IMM_NO_TITLE = "immNoTitle";
    public static String PROP_IMM_LAST_CHANGE_TIME = "immLastChangeTime";
    public static String PROP_IMM_CREATE_BY = "immCreateBy";

    private int hashCode = -2147483648;
    private String immId;
    private String immName;
    private String immDesc;
    private String immInputType;
    private Integer immNoTitle;
    private Integer immImportMode;
    private Integer immDefault;
    private Date immCreateTime;
    private Date immLastChangeTime;
    private String immLastChangeBy;
    private String immCreateBy;
    private Iodef immIo;

    public BaseInmatchModel() {
        initialize();
    }

    public BaseInmatchModel(String immId) {
        setImmId(immId);
        initialize();
    }

    public BaseInmatchModel(String immId, Iodef immIo, String immName, String immInputType,
            Integer immNoTitle, Integer immImportMode, Integer immDefault, Date immCreateTime,
            Date immLastChangeTime, String immLastChangeBy, String immCreateBy) {
        setImmId(immId);
        setImmIo(immIo);
        setImmName(immName);
        setImmInputType(immInputType);
        setImmNoTitle(immNoTitle);
        setImmImportMode(immImportMode);
        setImmDefault(immDefault);
        setImmCreateTime(immCreateTime);
        setImmLastChangeTime(immLastChangeTime);
        setImmLastChangeBy(immLastChangeBy);
        setImmCreateBy(immCreateBy);
        initialize();
    }

    protected void initialize() {
    }

    public String getImmId() {
        return this.immId;
    }

    public void setImmId(String immId) {
        this.immId = immId;
        this.hashCode = -2147483648;
    }

    public String getImmName() {
        return this.immName;
    }

    public void setImmName(String immName) {
        this.immName = immName;
    }

    public String getImmDesc() {
        return this.immDesc;
    }

    public void setImmDesc(String immDesc) {
        this.immDesc = immDesc;
    }

    public String getImmInputType() {
        return this.immInputType;
    }

    public void setImmInputType(String immInputType) {
        this.immInputType = immInputType;
    }

    public Integer getImmNoTitle() {
        return this.immNoTitle;
    }

    public void setImmNoTitle(Integer immNoTitle) {
        this.immNoTitle = immNoTitle;
    }

    public Integer getImmImportMode() {
        return this.immImportMode;
    }

    public void setImmImportMode(Integer immImportMode) {
        this.immImportMode = immImportMode;
    }

    public Integer getImmDefault() {
        return this.immDefault;
    }

    public void setImmDefault(Integer immDefault) {
        this.immDefault = immDefault;
    }

    public Date getImmCreateTime() {
        return this.immCreateTime;
    }

    public void setImmCreateTime(Date immCreateTime) {
        this.immCreateTime = immCreateTime;
    }

    public Date getImmLastChangeTime() {
        return this.immLastChangeTime;
    }

    public void setImmLastChangeTime(Date immLastChangeTime) {
        this.immLastChangeTime = immLastChangeTime;
    }

    public String getImmLastChangeBy() {
        return this.immLastChangeBy;
    }

    public void setImmLastChangeBy(String immLastChangeBy) {
        this.immLastChangeBy = immLastChangeBy;
    }

    public String getImmCreateBy() {
        return this.immCreateBy;
    }

    public void setImmCreateBy(String immCreateBy) {
        this.immCreateBy = immCreateBy;
    }

    public Iodef getImmIo() {
        return this.immIo;
    }

    public void setImmIo(Iodef immIo) {
        this.immIo = immIo;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof InmatchModel))
            return false;

        InmatchModel inmatchModel = (InmatchModel) obj;
        if ((null == getImmId()) || (null == inmatchModel.getImmId()))
            return false;
        return getImmId().equals(inmatchModel.getImmId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getImmId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getImmId().hashCode();
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
 * com.hr.io.domain.base.BaseInmatchModel JD-Core Version: 0.5.4
 */