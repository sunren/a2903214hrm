package com.hr.profile.domain.base;

import com.hr.profile.domain.Emphistposition;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Position;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseEmphistposition implements Serializable {
    public static String REF = "Emphistposition";
    public static String PROP_EHPOS_VALID_TO = "ehposValidTo";
    public static String PROP_EHPOS_VALID_FROM = "ehposValidFrom";
    public static String PROP_EHPOS_POSITION_NO = "ehposPositionNo";
    public static String PROP_EHPOS_IS_LATEST = "ehposIsLatest";
    public static String PROP_EHPOS_EMP_NO = "ehposEmpNo";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private Date ehposValidFrom;
    private Date ehposValidTo;
    private Integer ehposIsLatest;
    private Employee ehposEmpNo;
    private Position ehposPositionNo;

    public BaseEmphistposition() {
        initialize();
    }

    public BaseEmphistposition(String id) {
        setId(id);
        initialize();
    }

    public BaseEmphistposition(String id, Employee ehposEmpNo, Position ehposPositionNo,
            Date ehposValidFrom, Integer ehposIsLatest) {
        setId(id);
        setEhposEmpNo(ehposEmpNo);
        setEhposPositionNo(ehposPositionNo);
        setEhposValidFrom(ehposValidFrom);
        setEhposIsLatest(ehposIsLatest);
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

    public Date getEhposValidFrom() {
        return this.ehposValidFrom;
    }

    public void setEhposValidFrom(Date ehposValidFrom) {
        this.ehposValidFrom = ehposValidFrom;
    }

    public Date getEhposValidTo() {
        return this.ehposValidTo;
    }

    public void setEhposValidTo(Date ehposValidTo) {
        this.ehposValidTo = ehposValidTo;
    }

    public Integer getEhposIsLatest() {
        return this.ehposIsLatest;
    }

    public void setEhposIsLatest(Integer ehposIsLatest) {
        this.ehposIsLatest = ehposIsLatest;
    }

    public Employee getEhposEmpNo() {
        return this.ehposEmpNo;
    }

    public void setEhposEmpNo(Employee ehposEmpNo) {
        this.ehposEmpNo = ehposEmpNo;
    }

    public Position getEhposPositionNo() {
        return this.ehposPositionNo;
    }

    public void setEhposPositionNo(Position ehposPositionNo) {
        this.ehposPositionNo = ehposPositionNo;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Emphistposition))
            return false;

        Emphistposition emphistposition = (Emphistposition) obj;
        if ((null == getId()) || (null == emphistposition.getId()))
            return false;
        return getId().equals(emphistposition.getId());
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
 * com.hr.profile.domain.base.BaseEmphistposition JD-Core Version: 0.5.4
 */