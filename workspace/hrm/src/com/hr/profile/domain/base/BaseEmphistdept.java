package com.hr.profile.domain.base;

import com.hr.configuration.domain.Department;
import com.hr.profile.domain.Emphistdept;
import com.hr.profile.domain.Employee;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseEmphistdept implements Serializable {
    public static String REF = "Emphistdept";
    public static String PROP_EHD_VALID_TO = "ehdValidTo";
    public static String PROP_EHD_IS_LATEST = "ehdIsLatest";
    public static String PROP_EHD_VALID_FROM = "ehdValidFrom";
    public static String PROP_EHD_DEPT_NO = "ehdDeptNo";
    public static String PROP_EHD_EMP_NO = "ehdEmpNo";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Integer id;
    private Date ehdValidFrom;
    private Date ehdValidTo;
    private Integer ehdIsLatest;
    private Employee ehdEmpNo;
    private Department ehdDeptNo;

    public BaseEmphistdept() {
        initialize();
    }

    public BaseEmphistdept(Integer id) {
        setId(id);
        initialize();
    }

    public BaseEmphistdept(Integer id, Employee ehdEmpNo, Department ehdDeptNo, Date ehdValidFrom,
            Integer ehdIsLatest) {
        setId(id);
        setEhdEmpNo(ehdEmpNo);
        setEhdDeptNo(ehdDeptNo);
        setEhdValidFrom(ehdValidFrom);
        setEhdIsLatest(ehdIsLatest);
        initialize();
    }

    protected void initialize() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public Date getEhdValidFrom() {
        return this.ehdValidFrom;
    }

    public void setEhdValidFrom(Date ehdValidFrom) {
        this.ehdValidFrom = ehdValidFrom;
    }

    public Date getEhdValidTo() {
        return this.ehdValidTo;
    }

    public void setEhdValidTo(Date ehdValidTo) {
        this.ehdValidTo = ehdValidTo;
    }

    public Integer getEhdIsLatest() {
        return this.ehdIsLatest;
    }

    public void setEhdIsLatest(Integer ehdIsLatest) {
        this.ehdIsLatest = ehdIsLatest;
    }

    public Employee getEhdEmpNo() {
        return this.ehdEmpNo;
    }

    public void setEhdEmpNo(Employee ehdEmpNo) {
        this.ehdEmpNo = ehdEmpNo;
    }

    public Department getEhdDeptNo() {
        return this.ehdDeptNo;
    }

    public void setEhdDeptNo(Department ehdDeptNo) {
        this.ehdDeptNo = ehdDeptNo;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Emphistdept))
            return false;

        Emphistdept emphistdept = (Emphistdept) obj;
        if ((null == getId()) || (null == emphistdept.getId()))
            return false;
        return getId().equals(emphistdept.getId());
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
 * com.hr.profile.domain.base.BaseEmphistdept JD-Core Version: 0.5.4
 */