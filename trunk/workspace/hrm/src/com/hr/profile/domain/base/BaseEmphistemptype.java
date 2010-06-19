package com.hr.profile.domain.base;

import com.hr.configuration.domain.Emptype;
import com.hr.profile.domain.Emphistemptype;
import com.hr.profile.domain.Employee;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseEmphistemptype implements Serializable {
    public static String REF = "Emphistemptype";
    public static String PROP_EHET_EMP_NO = "ehetEmpNo";
    public static String PROP_EHET_EMPTYPE_NO = "ehetEmptypeNo";
    public static String PROP_EHET_VALID_TO = "ehetValidTo";
    public static String PROP_EHET_IS_LATEST = "ehetIsLatest";
    public static String PROP_ID = "id";
    public static String PROP_EHET_VALID_FROM = "ehetValidFrom";

    private int hashCode = -2147483648;
    private String id;
    private Date ehetValidFrom;
    private Date ehetValidTo;
    private Integer ehetIsLatest;
    private Emptype ehetEmptypeNo;
    private Employee ehetEmpNo;

    public BaseEmphistemptype() {
        initialize();
    }

    public BaseEmphistemptype(String id) {
        setId(id);
        initialize();
    }

    public BaseEmphistemptype(String id, Emptype ehetEmptypeNo, Employee ehetEmpNo,
            Date ehetValidFrom, Integer ehetIsLatest) {
        setId(id);
        setEhetEmptypeNo(ehetEmptypeNo);
        setEhetEmpNo(ehetEmpNo);
        setEhetValidFrom(ehetValidFrom);
        setEhetIsLatest(ehetIsLatest);
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

    public Date getEhetValidFrom() {
        return this.ehetValidFrom;
    }

    public void setEhetValidFrom(Date ehetValidFrom) {
        this.ehetValidFrom = ehetValidFrom;
    }

    public Date getEhetValidTo() {
        return this.ehetValidTo;
    }

    public void setEhetValidTo(Date ehetValidTo) {
        this.ehetValidTo = ehetValidTo;
    }

    public Integer getEhetIsLatest() {
        return this.ehetIsLatest;
    }

    public void setEhetIsLatest(Integer ehetIsLatest) {
        this.ehetIsLatest = ehetIsLatest;
    }

    public Emptype getEhetEmptypeNo() {
        return this.ehetEmptypeNo;
    }

    public void setEhetEmptypeNo(Emptype ehetEmptypeNo) {
        this.ehetEmptypeNo = ehetEmptypeNo;
    }

    public Employee getEhetEmpNo() {
        return this.ehetEmpNo;
    }

    public void setEhetEmpNo(Employee ehetEmpNo) {
        this.ehetEmpNo = ehetEmpNo;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Emphistemptype))
            return false;

        Emphistemptype emphistemptype = (Emphistemptype) obj;
        if ((null == getId()) || (null == emphistemptype.getId()))
            return false;
        return getId().equals(emphistemptype.getId());
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
 * com.hr.profile.domain.base.BaseEmphistemptype JD-Core Version: 0.5.4
 */