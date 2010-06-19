package com.hr.profile.domain.base;

import com.hr.configuration.domain.Department;
import com.hr.profile.domain.EmpHistOrg;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.PositionBase;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseEmpHistOrg implements Serializable {
    public static String REF = "EmpHistOrg";
    public static String PROP_EHO_VALID_TO = "ehoValidTo";
    public static String PROP_EHO_DEPT_NO = "ehoDeptNo";
    public static String PROP_EHO_VALID_FROM = "ehoValidFrom";
    public static String PROP_EHO_IS_LATEST = "ehoIsLatest";
    public static String PROP_EHO_EMP_NO = "ehoEmpNo";
    public static String PROP_ID = "id";
    public static String PROP_EHO_PB_NO = "ehoPbNo";

    private int hashCode = -2147483648;
    private String id;
    private Date ehoValidFrom;
    private Date ehoValidTo;
    private Integer ehoIsLatest;
    private Employee ehoEmpNo;
    private Department ehoDeptNo;
    private PositionBase ehoPbNo;

    public BaseEmpHistOrg() {
        initialize();
    }

    public BaseEmpHistOrg(String id) {
        setId(id);
        initialize();
    }

    public BaseEmpHistOrg(String id, Employee ehoEmpNo, Department ehoDeptNo, PositionBase ehoPbNo,
            Date ehoValidFrom, Integer ehoIsLatest) {
        setId(id);
        setEhoEmpNo(ehoEmpNo);
        setEhoDeptNo(ehoDeptNo);
        setEhoPbNo(ehoPbNo);
        setEhoValidFrom(ehoValidFrom);
        setEhoIsLatest(ehoIsLatest);
        initialize();
    }

    protected void initialize() {
    }

    public Date getEhoValidFrom() {
        return this.ehoValidFrom;
    }

    public void setEhoValidFrom(Date ehoValidFrom) {
        this.ehoValidFrom = ehoValidFrom;
    }

    public Date getEhoValidTo() {
        return this.ehoValidTo;
    }

    public void setEhoValidTo(Date ehoValidTo) {
        this.ehoValidTo = ehoValidTo;
    }

    public Integer getEhoIsLatest() {
        return this.ehoIsLatest;
    }

    public void setEhoIsLatest(Integer ehoIsLatest) {
        this.ehoIsLatest = ehoIsLatest;
    }

    public Employee getEhoEmpNo() {
        return this.ehoEmpNo;
    }

    public void setEhoEmpNo(Employee ehoEmpNo) {
        this.ehoEmpNo = ehoEmpNo;
    }

    public Department getEhoDeptNo() {
        return this.ehoDeptNo;
    }

    public void setEhoDeptNo(Department ehoDeptNo) {
        this.ehoDeptNo = ehoDeptNo;
    }

    public PositionBase getEhoPbNo() {
        return this.ehoPbNo;
    }

    public void setEhoPbNo(PositionBase ehoPbNo) {
        this.ehoPbNo = ehoPbNo;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof EmpHistOrg))
            return false;

        EmpHistOrg empHistOrg = (EmpHistOrg) obj;
        if ((null == getId()) || (null == empHistOrg.getId()))
            return false;
        return getId().equals(empHistOrg.getId());
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

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.domain.base.BaseEmpHistOrg JD-Core Version: 0.5.4
 */