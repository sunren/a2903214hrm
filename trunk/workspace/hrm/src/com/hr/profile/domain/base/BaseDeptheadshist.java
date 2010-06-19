package com.hr.profile.domain.base;

import com.hr.configuration.domain.Department;
import com.hr.profile.domain.Deptheadshist;
import com.hr.profile.domain.Employee;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseDeptheadshist implements Serializable {
    public static String REF = "Deptheadshist";
    public static String PROP_DHH_EMP_NO = "dhhEmpNo";
    public static String PROP_DHH_VALID_TO = "dhhValidTo";
    public static String PROP_DHH_DEPT_NO = "dhhDeptNo";
    public static String PROP_CLIENT_NO = "clientNo";
    public static String PROP_DHH_IS_HEAD = "dhhIsHead";
    public static String PROP_DHH_VALID_FROM = "dhhValidFrom";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private Integer dhhIsHead;
    private Date dhhValidFrom;
    private Date dhhValidTo;
    private String clientNo;
    private Employee dhhEmpNo;
    private Department dhhDeptNo;

    public BaseDeptheadshist() {
        initialize();
    }

    public BaseDeptheadshist(String id) {
        setId(id);
        initialize();
    }

    public BaseDeptheadshist(String id, Employee dhhEmpNo, Department dhhDeptNo, Integer dhhIsHead,
            Date dhhValidFrom, String clientNo) {
        setId(id);
        setDhhEmpNo(dhhEmpNo);
        setDhhDeptNo(dhhDeptNo);
        setDhhIsHead(dhhIsHead);
        setDhhValidFrom(dhhValidFrom);
        setClientNo(clientNo);
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

    public Integer getDhhIsHead() {
        return this.dhhIsHead;
    }

    public void setDhhIsHead(Integer dhhIsHead) {
        this.dhhIsHead = dhhIsHead;
    }

    public Date getDhhValidFrom() {
        return this.dhhValidFrom;
    }

    public void setDhhValidFrom(Date dhhValidFrom) {
        this.dhhValidFrom = dhhValidFrom;
    }

    public Date getDhhValidTo() {
        return this.dhhValidTo;
    }

    public void setDhhValidTo(Date dhhValidTo) {
        this.dhhValidTo = dhhValidTo;
    }

    public String getClientNo() {
        return this.clientNo;
    }

    public void setClientNo(String clientNo) {
        this.clientNo = clientNo;
    }

    public Employee getDhhEmpNo() {
        return this.dhhEmpNo;
    }

    public void setDhhEmpNo(Employee dhhEmpNo) {
        this.dhhEmpNo = dhhEmpNo;
    }

    public Department getDhhDeptNo() {
        return this.dhhDeptNo;
    }

    public void setDhhDeptNo(Department dhhDeptNo) {
        this.dhhDeptNo = dhhDeptNo;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Deptheadshist))
            return false;

        Deptheadshist deptheadshist = (Deptheadshist) obj;
        if ((null == getId()) || (null == deptheadshist.getId()))
            return false;
        return getId().equals(deptheadshist.getId());
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
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.domain.base.BaseDeptheadshist JD-Core Version: 0.5.4
 */