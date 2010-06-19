package com.hr.training.domain.base;

import com.hr.base.BaseDomain;
import com.hr.training.domain.Trcourse;
import com.hr.training.domain.Trtype;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public abstract class BaseTrtype extends BaseDomain implements Serializable {
    public static String REF = "Trtype";
    public static String PROP_TRT_DESC = "trtDesc";
    public static String PROP_TRT_NAME = "trtName";
    public static String PROP_TRT_NO = "trtNo";

    private int hashCode = -2147483648;
    private String trtNo;
    private String trtName;
    private String trtDesc;
    private Set<Trcourse> trcourses;

    public BaseTrtype() {
        initialize();
    }

    public BaseTrtype(String trtNo) {
        setTrtNo(trtNo);
        initialize();
    }

    public BaseTrtype(String trtNo, String trtName) {
        setTrtNo(trtNo);
        setTrtName(trtName);
        initialize();
    }

    protected void initialize() {
    }

    public String getTrtNo() {
        return this.trtNo;
    }

    public void setTrtNo(String trtNo) {
        this.trtNo = trtNo;
        this.hashCode = -2147483648;
    }

    public String getTrtName() {
        return this.trtName;
    }

    public void setTrtName(String trtName) {
        this.trtName = trtName;
    }

    public String getTrtDesc() {
        return this.trtDesc;
    }

    public void setTrtDesc(String trtDesc) {
        this.trtDesc = trtDesc;
    }

    public Set<Trcourse> getTrcourses() {
        return this.trcourses;
    }

    public void setTrcourses(Set<Trcourse> trcourses) {
        this.trcourses = trcourses;
    }

    public void addTotrcourses(Trcourse trcourse) {
        if (null == getTrcourses())
            setTrcourses(new TreeSet());
        getTrcourses().add(trcourse);
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Trtype))
            return false;

        Trtype trtype = (Trtype) obj;
        if ((null == getTrtNo()) || (null == trtype.getTrtNo()))
            return false;
        return getTrtNo().equals(trtype.getTrtNo());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getTrtNo())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getTrtNo().hashCode();
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
 * com.hr.training.domain.base.BaseTrtype JD-Core Version: 0.5.4
 */