package com.hr.profile.domain.base;

import com.hr.configuration.domain.Department;
import com.hr.profile.domain.Deptheads;
import com.hr.profile.domain.Employee;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseDeptheads implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4024979713291894548L;
    public static String REF = "Deptheads";
    public static String PROP_DHE_VALID_FROM = "dheValidFrom";
    public static String PROP_DHE_DEPT_NO = "dheDeptNo";
    public static String PROP_CLIENT_NO = "clientNo";
    public static String PROP_DHE_IS_HEAD = "dheIsHead";
    public static String PROP_DHE_EMP_NO = "dheEmpNo";
    public static String PROP_DHE_VALID_TO = "dheValidTo";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private Integer dheIsHead;
    private Date dheValidFrom;
    private Date dheValidTo;
    private String clientNo;
    private Employee dheEmpNo;
    private Department dheDeptNo;

    public BaseDeptheads() {
        initialize();
    }

    public BaseDeptheads(String id) {
        setId(id);
        initialize();
    }

    public BaseDeptheads(String id, Employee dheEmpNo, Department dheDeptNo, Integer dheIsHead,
            Date dheValidFrom, String clientNo) {
        setId(id);
        setDheEmpNo(dheEmpNo);
        setDheDeptNo(dheDeptNo);
        setDheIsHead(dheIsHead);
        setDheValidFrom(dheValidFrom);
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

    public Integer getDheIsHead() {
        return this.dheIsHead;
    }

    public void setDheIsHead(Integer dheIsHead) {
        this.dheIsHead = dheIsHead;
    }

    public Date getDheValidFrom() {
        return this.dheValidFrom;
    }

    public void setDheValidFrom(Date dheValidFrom) {
        this.dheValidFrom = dheValidFrom;
    }

    public Date getDheValidTo() {
        return this.dheValidTo;
    }

    public void setDheValidTo(Date dheValidTo) {
        this.dheValidTo = dheValidTo;
    }

    public String getClientNo() {
        return this.clientNo;
    }

    public void setClientNo(String clientNo) {
        this.clientNo = clientNo;
    }

    public Employee getDheEmpNo() {
        return this.dheEmpNo;
    }

    public void setDheEmpNo(Employee dheEmpNo) {
        this.dheEmpNo = dheEmpNo;
    }

    public Department getDheDeptNo() {
        return this.dheDeptNo;
    }

    public void setDheDeptNo(Department dheDeptNo) {
        this.dheDeptNo = dheDeptNo;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Deptheads))
            return false;

        Deptheads deptheads = (Deptheads) obj;
        if ((null == getId()) || (null == deptheads.getId()))
            return false;
        return getId().equals(deptheads.getId());
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
 * com.hr.profile.domain.base.BaseDeptheads JD-Core Version: 0.5.4
 */