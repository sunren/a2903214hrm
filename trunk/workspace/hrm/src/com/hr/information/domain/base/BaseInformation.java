package com.hr.information.domain.base;

import com.hr.base.BaseDomain;
import com.hr.configuration.domain.Infoclass;
import com.hr.information.domain.Information;
import com.hr.profile.domain.Employee;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseInformation extends BaseDomain implements Serializable {
    public static String REF = "Information";

    public static String PROP_INFO_PIC_DESC = "infoPicDesc";

    public static String PROP_INFO_TAG = "infoTag";

    public static String PROP_INFO_CONTENT = "infoContent";

    public static String PROP_INFO_CREATE_BY = "infoCreateBy";

    public static String PROP_INFO_PIC_NAME = "infoPicName";

    public static String PROP_INFO_FILE_DESC = "infoFileDesc";

    public static String PROP_INFO_VIEWED_NUM = "infoViewedNum";

    public static String PROP_INFO_LAST_CHANGE_BY = "infoLastChangeBy";

    public static String PROP_INFO_CLASS = "infoClass";

    public static String PROP_INFO_FILE_NAME = "infoFileName";

    public static String PROP_INFO_LAST_CHANGE_TIME = "infoLastChangeTime";

    public static String PROP_INFO_BREIF = "infoBreif";

    public static String PROP_INFO_CREATE_TIME = "infoCreateTime";

    public static String PROP_INFO_STATUS = "infoStatus";

    public static String PROP_ID = "id";

    public static String PROP_INFO_TITLE = "infoTitle";

    private int hashCode = -2147483648;
    private String id;
    private String infoTag;
    private String infoTitle;
    private String infoBreif;
    private String infoContent;
    private String infoFileName;
    private String infoFileDesc;
    private String infoPicName;
    private String infoPicDesc;
    private Integer infoViewedNum;
    private Integer infoStatus;
    private Date infoCreateTime;
    private Date infoLastChangeTime;
    private Employee infoCreateBy;
    private Infoclass infoClass;
    private Employee infoLastChangeBy;

    public BaseInformation() {
        initialize();
    }

    public BaseInformation(String id) {
        setId(id);
        initialize();
    }

    public BaseInformation(String id, Employee infoCreateBy, Infoclass infoClass,
            Employee infoLastChangeBy, String infoTitle, String infoBreif, String infoContent,
            Integer infoViewedNum, Integer infoStatus, Date infoCreateTime, Date infoLastChangeTime) {
        setId(id);
        setInfoCreateBy(infoCreateBy);
        setInfoClass(infoClass);
        setInfoLastChangeBy(infoLastChangeBy);
        setInfoTitle(infoTitle);
        setInfoBreif(infoBreif);
        setInfoContent(infoContent);
        setInfoViewedNum(infoViewedNum);
        setInfoStatus(infoStatus);
        setInfoCreateTime(infoCreateTime);
        setInfoLastChangeTime(infoLastChangeTime);
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

    public String getInfoTag() {
        return this.infoTag;
    }

    public void setInfoTag(String infoTag) {
        this.infoTag = infoTag;
    }

    public String getInfoTitle() {
        return this.infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }

    public String getInfoBreif() {
        return this.infoBreif;
    }

    public void setInfoBreif(String infoBreif) {
        this.infoBreif = infoBreif;
    }

    public String getInfoContent() {
        return this.infoContent;
    }

    public void setInfoContent(String infoContent) {
        this.infoContent = infoContent;
    }

    public String getInfoFileName() {
        return this.infoFileName;
    }

    public void setInfoFileName(String infoFileName) {
        this.infoFileName = infoFileName;
    }

    public String getInfoFileDesc() {
        return this.infoFileDesc;
    }

    public void setInfoFileDesc(String infoFileDesc) {
        this.infoFileDesc = infoFileDesc;
    }

    public String getInfoPicName() {
        return this.infoPicName;
    }

    public void setInfoPicName(String infoPicName) {
        this.infoPicName = infoPicName;
    }

    public String getInfoPicDesc() {
        return this.infoPicDesc;
    }

    public void setInfoPicDesc(String infoPicDesc) {
        this.infoPicDesc = infoPicDesc;
    }

    public Integer getInfoViewedNum() {
        return this.infoViewedNum;
    }

    public void setInfoViewedNum(Integer infoViewedNum) {
        this.infoViewedNum = infoViewedNum;
    }

    public Integer getInfoStatus() {
        return this.infoStatus;
    }

    public void setInfoStatus(Integer infoStatus) {
        this.infoStatus = infoStatus;
    }

    public Date getInfoCreateTime() {
        return this.infoCreateTime;
    }

    public void setInfoCreateTime(Date infoCreateTime) {
        this.infoCreateTime = infoCreateTime;
    }

    public Date getInfoLastChangeTime() {
        return this.infoLastChangeTime;
    }

    public void setInfoLastChangeTime(Date infoLastChangeTime) {
        this.infoLastChangeTime = infoLastChangeTime;
    }

    public Employee getInfoCreateBy() {
        return this.infoCreateBy;
    }

    public void setInfoCreateBy(Employee infoCreateBy) {
        this.infoCreateBy = infoCreateBy;
    }

    public Infoclass getInfoClass() {
        return this.infoClass;
    }

    public void setInfoClass(Infoclass infoClass) {
        this.infoClass = infoClass;
    }

    public Employee getInfoLastChangeBy() {
        return this.infoLastChangeBy;
    }

    public void setInfoLastChangeBy(Employee infoLastChangeBy) {
        this.infoLastChangeBy = infoLastChangeBy;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Information)) {
            return false;
        }
        Information information = (Information) obj;
        if ((null == getId()) || (null == information.getId())) {
            return false;
        }
        return getId().equals(information.getId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getId()) {
                return super.hashCode();
            }
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
 * com.hr.information.domain.base.BaseInformation JD-Core Version: 0.5.4
 */