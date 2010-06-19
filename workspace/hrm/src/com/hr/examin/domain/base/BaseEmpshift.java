package com.hr.examin.domain.base;

import com.hr.base.BaseDomain;
import com.hr.examin.domain.Attendshift;
import com.hr.profile.domain.Employee;
import java.io.Serializable;
import java.util.Date;

public class BaseEmpshift extends BaseDomain implements Serializable {
    public static String ref = "Empshif";
    public static String PROP_ID = "id";
    public static String PROP_EMPSHIFT_EMP_NO = "empshiftEmpNo";
    public static String PROP_EMPSHIFT_SHIFT_NO = "empshiftShiftNo";
    public static String PROP_EMPSHIFT_DATE = "empshiftDate";
    public static String PROP_EMPSHIFT_CREATE_BY = "empshiftCreateBy";
    public static String PROP_EMPSHIFT_CREATE_TIME = "empshiftCreateTime";
    public static String PROP_EMPSHIFT_LAST_CHANGE_BY = "empshiftLastChangeBy";
    public static String PROP_EMPSHIFT_LAST_CHANGE_TIME = "empshiftLastChangeTIme";
    private String id;
    private Employee empshiftEmpNo;
    private Attendshift empshiftShiftNo;
    private Date empshiftDate;
    private String empshiftCreateBy;
    private Date empshiftCreateTime;
    private String empshiftLastChangeBy;
    private Date empshiftLastChangeTime;

    public BaseEmpshift() {
        initialize();
    }

    public BaseEmpshift(String id) {
        setId(id);
        initialize();
    }

    public BaseEmpshift(String id, Employee empshiftEmpNo, Attendshift empshiftShiftNo,
            Date empshiftDate) {
        setId(id);
        setEmpshiftEmpNo(empshiftEmpNo);
        setEmpshiftShiftNo(empshiftShiftNo);
        setEmpshiftDate(empshiftDate);
        initialize();
    }

    protected void initialize() {
    }

    public String getEmpshiftCreateBy() {
        return this.empshiftCreateBy;
    }

    public void setEmpshiftCreateBy(String empshiftCreateBy) {
        this.empshiftCreateBy = empshiftCreateBy;
    }

    public Date getEmpshiftDate() {
        return this.empshiftDate;
    }

    public void setEmpshiftDate(Date empshiftDate) {
        this.empshiftDate = empshiftDate;
    }

    public Employee getEmpshiftEmpNo() {
        return this.empshiftEmpNo;
    }

    public void setEmpshiftEmpNo(Employee empshiftEmpNo) {
        this.empshiftEmpNo = empshiftEmpNo;
    }

    public String getEmpshiftLastChangeBy() {
        return this.empshiftLastChangeBy;
    }

    public void setEmpshiftLastChangeBy(String empshiftLastChangeBy) {
        this.empshiftLastChangeBy = empshiftLastChangeBy;
    }

    public Attendshift getEmpshiftShiftNo() {
        return this.empshiftShiftNo;
    }

    public void setEmpshiftShiftNo(Attendshift empshiftShiftNo) {
        this.empshiftShiftNo = empshiftShiftNo;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getEmpshiftCreateTime() {
        return this.empshiftCreateTime;
    }

    public void setEmpshiftCreateTime(Date empshiftCreateTime) {
        this.empshiftCreateTime = empshiftCreateTime;
    }

    public Date getEmpshiftLastChangeTime() {
        return this.empshiftLastChangeTime;
    }

    public void setEmpshiftLastChangeTime(Date empshiftLastChangeTime) {
        this.empshiftLastChangeTime = empshiftLastChangeTime;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.domain.base.BaseEmpshift JD-Core Version: 0.5.4
 */