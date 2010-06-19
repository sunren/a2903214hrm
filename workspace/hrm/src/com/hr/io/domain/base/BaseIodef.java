package com.hr.io.domain.base;

import com.hr.io.domain.Iodef;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseIodef implements Serializable {
    public static String REF = "Iodef";
    public static String PROP_IO_MODULE = "ioModule";
    public static String PROP_IO_IMPORT_MODE = "ioImportMode";
    public static String PROP_IO_LAST_CHANGE_TIME = "ioLastChangeTime";
    public static String PROP_IO_IS_EXTEND = "ioIsExtend";
    public static String PROP_IO_ID = "ioId";
    public static String PROP_IO_DESC = "ioDesc";
    public static String PROP_IO_FETCH_NAMES = "ioFetchNames";
    public static String PROP_IO_CREATE_TIME = "ioCreateTime";
    public static String PROP_IO_LAST_CHANGE_BY = "ioLastChangeBy";
    public static String PROP_IO_CLASS_NAME = "ioClassName";
    public static String PROP_IO_NAME = "ioName";
    public static String PROP_IO_FILE_HAS_TITLE = "ioFileHasTitle";
    public static String PROP_IO_TYPE = "ioType";
    public static String PROP_IO_MATCH_TYPE = "ioMatchType";
    public static String PROP_IO_AUTHORITY = "ioAuthority";
    public static String PROP_IO_CREATE_BY = "ioCreateBy";
    public static String PROP_IO_FILE_PATH = "ioFilePath";

    private int hashCode = -2147483648;
    private String ioId;
    private String ioName;
    private String ioDesc;
    private Integer ioType;
    private Integer ioFilePath;
    private Integer ioFileHasTitle;
    private Integer ioMatchType;
    private Integer ioModule;
    private String ioAuthority;
    private Integer ioIsExtend;
    private Integer ioImportMode;
    private String ioClassName;
    private String ioFetchNames;
    private String ioCreateBy;
    private Date ioCreateTime;
    private String ioLastChangeBy;
    private Date ioLastChangeTime;

    public BaseIodef() {
        initialize();
    }

    public BaseIodef(String ioId) {
        setIoId(ioId);
        initialize();
    }

    public BaseIodef(String ioId, String ioName, Integer ioType, Integer ioFilePath,
            Integer ioFileHasTitle, Integer ioMatchType, Integer ioModule, String ioAuthority,
            String ioClassName, String ioFetchNames, Date ioCreateTime, Date ioLastChangeTime) {
        setIoId(ioId);
        setIoName(ioName);
        setIoType(ioType);
        setIoFilePath(ioFilePath);
        setIoFileHasTitle(ioFileHasTitle);
        setIoMatchType(ioMatchType);
        setIoModule(ioModule);
        setIoAuthority(ioAuthority);
        setIoClassName(ioClassName);
        setIoFetchNames(ioFetchNames);
        setIoCreateTime(ioCreateTime);
        setIoLastChangeTime(ioLastChangeTime);
        initialize();
    }

    protected void initialize() {
    }

    public String getIoId() {
        return this.ioId;
    }

    public void setIoId(String ioId) {
        this.ioId = ioId;
        this.hashCode = -2147483648;
    }

    public String getIoName() {
        return this.ioName;
    }

    public void setIoName(String ioName) {
        this.ioName = ioName;
    }

    public String getIoDesc() {
        return this.ioDesc;
    }

    public void setIoDesc(String ioDesc) {
        this.ioDesc = ioDesc;
    }

    public Integer getIoType() {
        return this.ioType;
    }

    public void setIoType(Integer ioType) {
        this.ioType = ioType;
    }

    public Integer getIoFilePath() {
        return this.ioFilePath;
    }

    public void setIoFilePath(Integer ioFilePath) {
        this.ioFilePath = ioFilePath;
    }

    public Integer getIoFileHasTitle() {
        return this.ioFileHasTitle;
    }

    public void setIoFileHasTitle(Integer ioFileHasTitle) {
        this.ioFileHasTitle = ioFileHasTitle;
    }

    public Integer getIoMatchType() {
        return this.ioMatchType;
    }

    public void setIoMatchType(Integer ioMatchType) {
        this.ioMatchType = ioMatchType;
    }

    public Integer getIoModule() {
        return this.ioModule;
    }

    public void setIoModule(Integer ioModule) {
        this.ioModule = ioModule;
    }

    public String getIoAuthority() {
        return this.ioAuthority;
    }

    public void setIoAuthority(String ioAuthority) {
        this.ioAuthority = ioAuthority;
    }

    public Integer getIoIsExtend() {
        return this.ioIsExtend;
    }

    public void setIoIsExtend(Integer ioIsExtend) {
        this.ioIsExtend = ioIsExtend;
    }

    public Integer getIoImportMode() {
        return this.ioImportMode;
    }

    public void setIoImportMode(Integer ioImportMode) {
        this.ioImportMode = ioImportMode;
    }

    public String getIoClassName() {
        return this.ioClassName;
    }

    public void setIoClassName(String ioClassName) {
        this.ioClassName = ioClassName;
    }

    public String getIoFetchNames() {
        return this.ioFetchNames;
    }

    public void setIoFetchNames(String ioFetchNames) {
        this.ioFetchNames = ioFetchNames;
    }

    public String getIoCreateBy() {
        return this.ioCreateBy;
    }

    public void setIoCreateBy(String ioCreateBy) {
        this.ioCreateBy = ioCreateBy;
    }

    public Date getIoCreateTime() {
        return this.ioCreateTime;
    }

    public void setIoCreateTime(Date ioCreateTime) {
        this.ioCreateTime = ioCreateTime;
    }

    public String getIoLastChangeBy() {
        return this.ioLastChangeBy;
    }

    public void setIoLastChangeBy(String ioLastChangeBy) {
        this.ioLastChangeBy = ioLastChangeBy;
    }

    public Date getIoLastChangeTime() {
        return this.ioLastChangeTime;
    }

    public void setIoLastChangeTime(Date ioLastChangeTime) {
        this.ioLastChangeTime = ioLastChangeTime;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Iodef))
            return false;

        Iodef iodef = (Iodef) obj;
        if ((null == getIoId()) || (null == iodef.getIoId()))
            return false;
        return getIoId().equals(iodef.getIoId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getIoId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getIoId().hashCode();
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
 * com.hr.io.domain.base.BaseIodef JD-Core Version: 0.5.4
 */