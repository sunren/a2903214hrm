package com.hr.profile.domain.base;

import com.hr.configuration.domain.Department;
import com.hr.profile.domain.Depthist;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseDepthist implements Serializable {
    public static String REF = "Depthist";
    public static String PROP_DHI_DEPT_NAME = "dhiDeptName";
    public static String PROP_DHI_DEPT_STATUS = "dhiDeptStatus";
    public static String PROP_DHI_DEPT_SUP_ID = "dhiDeptSupId";
    public static String PROP_DHI_DEPT_NO = "dhiDeptNo";
    public static String PROP_DHI_VALID_TO = "dhiValidTo";
    public static String PROP_ID = "id";
    public static String PROP_DHI_VALID_FROM = "dhiValidFrom";

    private int hashCode = -2147483648;
    private String id;
    private String dhiDeptName;
    private Integer dhiDeptStatus;
    private Date dhiValidFrom;
    private Date dhiValidTo;
    private Department dhiDeptNo;
    private Department dhiDeptSupId;

    public BaseDepthist() {
        initialize();
    }

    public BaseDepthist(String id) {
        setId(id);
        initialize();
    }

    public BaseDepthist(String id, Department dhiDeptNo, Department dhiDeptSupId,
            String dhiDeptName, Integer dhiDeptStatus, Date dhiValidFrom) {
        setId(id);
        setDhiDeptNo(dhiDeptNo);
        setDhiDeptSupId(dhiDeptSupId);
        setDhiDeptName(dhiDeptName);
        setDhiDeptStatus(dhiDeptStatus);
        setDhiValidFrom(dhiValidFrom);
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

    public String getDhiDeptName() {
        return this.dhiDeptName;
    }

    public void setDhiDeptName(String dhiDeptName) {
        this.dhiDeptName = dhiDeptName;
    }

    public Integer getDhiDeptStatus() {
        return this.dhiDeptStatus;
    }

    public void setDhiDeptStatus(Integer dhiDeptStatus) {
        this.dhiDeptStatus = dhiDeptStatus;
    }

    public Date getDhiValidFrom() {
        return this.dhiValidFrom;
    }

    public void setDhiValidFrom(Date dhiValidFrom) {
        this.dhiValidFrom = dhiValidFrom;
    }

    public Date getDhiValidTo() {
        return this.dhiValidTo;
    }

    public void setDhiValidTo(Date dhiValidTo) {
        this.dhiValidTo = dhiValidTo;
    }

    public Department getDhiDeptNo() {
        return this.dhiDeptNo;
    }

    public void setDhiDeptNo(Department dhiDeptNo) {
        this.dhiDeptNo = dhiDeptNo;
    }

    public Department getDhiDeptSupId() {
        return this.dhiDeptSupId;
    }

    public void setDhiDeptSupId(Department dhiDeptSupId) {
        this.dhiDeptSupId = dhiDeptSupId;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Depthist))
            return false;

        Depthist depthist = (Depthist) obj;
        if ((null == getId()) || (null == depthist.getId()))
            return false;
        return getId().equals(depthist.getId());
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
 * com.hr.profile.domain.base.BaseDepthist JD-Core Version: 0.5.4
 */