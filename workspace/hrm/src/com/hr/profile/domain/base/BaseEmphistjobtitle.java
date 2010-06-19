package com.hr.profile.domain.base;

import com.hr.configuration.domain.JobTitle;
import com.hr.profile.domain.Emphistjobtitle;
import com.hr.profile.domain.Employee;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseEmphistjobtitle implements Serializable {
    public static String REF = "Emphistjobtitle";
    public static String PROP_EHJT_EMP_NO = "ehjtEmpNo";
    public static String PROP_EHJT_VALID_FROM = "ehjtValidFrom";
    public static String PROP_EHJT_VALID_TO = "ehjtValidTo";
    public static String PROP_EHJT_IS_LATEST = "ehjtIsLatest";
    public static String PROP_EHJT_JOBTITLE_NO = "ehjtJobtitleNo";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private Date ehjtValidFrom;
    private Date ehjtValidTo;
    private Integer ehjtIsLatest;
    private JobTitle ehjtJobtitleNo;
    private Employee ehjtEmpNo;

    public BaseEmphistjobtitle() {
        initialize();
    }

    public BaseEmphistjobtitle(String id) {
        setId(id);
        initialize();
    }

    public BaseEmphistjobtitle(String id, JobTitle ehjtJobtitleNo, Employee ehjtEmpNo,
            Date ehjtValidFrom, Integer ehjtIsLatest) {
        setId(id);
        setEhjtJobtitleNo(ehjtJobtitleNo);
        setEhjtEmpNo(ehjtEmpNo);
        setEhjtValidFrom(ehjtValidFrom);
        setEhjtIsLatest(ehjtIsLatest);
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

    public Date getEhjtValidFrom() {
        return this.ehjtValidFrom;
    }

    public void setEhjtValidFrom(Date ehjtValidFrom) {
        this.ehjtValidFrom = ehjtValidFrom;
    }

    public Date getEhjtValidTo() {
        return this.ehjtValidTo;
    }

    public void setEhjtValidTo(Date ehjtValidTo) {
        this.ehjtValidTo = ehjtValidTo;
    }

    public Integer getEhjtIsLatest() {
        return this.ehjtIsLatest;
    }

    public void setEhjtIsLatest(Integer ehjtIsLatest) {
        this.ehjtIsLatest = ehjtIsLatest;
    }

    public JobTitle getEhjtJobtitleNo() {
        return this.ehjtJobtitleNo;
    }

    public void setEhjtJobtitleNo(JobTitle ehjtJobtitleNo) {
        this.ehjtJobtitleNo = ehjtJobtitleNo;
    }

    public Employee getEhjtEmpNo() {
        return this.ehjtEmpNo;
    }

    public void setEhjtEmpNo(Employee ehjtEmpNo) {
        this.ehjtEmpNo = ehjtEmpNo;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Emphistjobtitle))
            return false;

        Emphistjobtitle emphistjobtitle = (Emphistjobtitle) obj;
        if ((null == getId()) || (null == emphistjobtitle.getId()))
            return false;
        return getId().equals(emphistjobtitle.getId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getId())
                return super.hashCode();

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
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.domain.base.BaseEmphistjobtitle JD-Core Version: 0.5.4
 */