package com.hr.training.domain.base;

import com.hr.base.BaseDomain;
import com.hr.profile.domain.Employee;
import com.hr.training.domain.Trcourse;
import com.hr.training.domain.Trcourseplan;
import com.hr.training.domain.Trtype;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public abstract class BaseTrcourse extends BaseDomain implements Serializable {
    public static String REF = "Trcourse";
    public static String PROP_TRC_NO = "trcNo";
    public static String PROP_TRC_CREATE_BY = "trcCreateBy";
    public static String PROP_TRC_STATUS = "trcStatus";
    public static String PROP_TRC_NAME = "trcName";
    public static String PROP_TRC_TYPE = "trcType";
    public static String PROP_TRC_LAST_CHANGE_TIME = "trcLastChangeTime";
    public static String PROP_TRC_INFO = "trcInfo";
    public static String PROP_TRC_LAST_CHANGE_BY = "trcLastChangeBy";
    public static String PROP_TRC_CREATE_TIME = "trcCreateTime";
    public static String PROP_TRC_FILE_NAME = "trcFileName";

    private int hashCode = -2147483648;
    private String trcNo;
    private String trcName;
    private String trcInfo;
    private Integer trcStatus;
    private String trcFileName;
    private Date trcCreateTime;
    private Date trcLastChangeTime;
    private Trtype trcType;
    private Employee trcCreateBy;
    private Employee trcLastChangeBy;
    private Set<Trcourseplan> trcourseplans;

    public BaseTrcourse() {
        initialize();
    }

    public BaseTrcourse(String trcNo) {
        setTrcNo(trcNo);
        initialize();
    }

    public BaseTrcourse(String trcNo, Trtype trcType, Employee trcCreateBy,
            Employee trcLastChangeBy, String trcName, String trcInfo, Integer trcStatus,
            Date trcCreateTime, Date trcLastChangeTime) {
        setTrcNo(trcNo);
        setTrcType(trcType);
        setTrcCreateBy(trcCreateBy);
        setTrcLastChangeBy(trcLastChangeBy);
        setTrcName(trcName);
        setTrcInfo(trcInfo);
        setTrcStatus(trcStatus);
        setTrcCreateTime(trcCreateTime);
        setTrcLastChangeTime(trcLastChangeTime);
        initialize();
    }

    protected void initialize() {
    }

    public String getTrcNo() {
        return this.trcNo;
    }

    public void setTrcNo(String trcNo) {
        this.trcNo = trcNo;
        this.hashCode = -2147483648;
    }

    public String getTrcName() {
        return this.trcName;
    }

    public void setTrcName(String trcName) {
        this.trcName = trcName;
    }

    public String getTrcInfo() {
        return this.trcInfo;
    }

    public void setTrcInfo(String trcInfo) {
        this.trcInfo = trcInfo;
    }

    public Integer getTrcStatus() {
        return this.trcStatus;
    }

    public void setTrcStatus(Integer trcStatus) {
        this.trcStatus = trcStatus;
    }

    public String getTrcFileName() {
        return this.trcFileName;
    }

    public void setTrcFileName(String trcFileName) {
        this.trcFileName = trcFileName;
    }

    public Date getTrcCreateTime() {
        return this.trcCreateTime;
    }

    public void setTrcCreateTime(Date trcCreateTime) {
        this.trcCreateTime = trcCreateTime;
    }

    public Date getTrcLastChangeTime() {
        return this.trcLastChangeTime;
    }

    public void setTrcLastChangeTime(Date trcLastChangeTime) {
        this.trcLastChangeTime = trcLastChangeTime;
    }

    public Trtype getTrcType() {
        return this.trcType;
    }

    public void setTrcType(Trtype trcType) {
        this.trcType = trcType;
    }

    public Employee getTrcCreateBy() {
        return this.trcCreateBy;
    }

    public void setTrcCreateBy(Employee trcCreateBy) {
        this.trcCreateBy = trcCreateBy;
    }

    public Employee getTrcLastChangeBy() {
        return this.trcLastChangeBy;
    }

    public void setTrcLastChangeBy(Employee trcLastChangeBy) {
        this.trcLastChangeBy = trcLastChangeBy;
    }

    public Set<Trcourseplan> getTrcourseplans() {
        return this.trcourseplans;
    }

    public void setTrcourseplans(Set<Trcourseplan> trcourseplans) {
        this.trcourseplans = trcourseplans;
    }

    public void addTotrcourseplans(Trcourseplan trcourseplan) {
        if (null == getTrcourseplans())
            setTrcourseplans(new TreeSet());
        getTrcourseplans().add(trcourseplan);
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Trcourse))
            return false;

        Trcourse trcourse = (Trcourse) obj;
        if ((null == getTrcNo()) || (null == trcourse.getTrcNo()))
            return false;
        return getTrcNo().equals(trcourse.getTrcNo());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getTrcNo())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getTrcNo().hashCode();
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
 * com.hr.training.domain.base.BaseTrcourse JD-Core Version: 0.5.4
 */