package com.hr.profile.domain.base;

import com.hr.configuration.domain.Department;
import com.hr.profile.domain.PositionBase;
import com.hr.profile.domain.PositionBaseHist;
import java.io.Serializable;
import java.util.Date;

public abstract class BasePositionBaseHist implements Serializable {
    public static String REF = "PositionBaseHist";
    public static String PROP_PBHI_PB_STATUS = "pbhiPbStatus";
    public static String PROP_PBHI_VALID_FROM = "pbhiValidFrom";
    public static String PROP_PBHI_MAX_EMPLOYEE = "pbhiMaxEmployee";
    public static String PROP_PBHI_PB_ID = "pbhiPbId";
    public static String PROP_PBHI_VALID_TO = "pbhiValidTo";
    public static String PROP_PBHI_DEPT_ID = "pbhiDeptId";
    public static String PROP_PBHI_IN_CHARGE = "pbhiInCharge";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private Integer pbhiInCharge;
    private Integer pbhiMaxEmployee;
    private Integer pbhiPbStatus;
    private Date pbhiValidFrom;
    private Date pbhiValidTo;
    private PositionBase pbhiPbId;
    private Department pbhiDeptId;

    public BasePositionBaseHist() {
        initialize();
    }

    public BasePositionBaseHist(String id) {
        setId(id);
        initialize();
    }

    public BasePositionBaseHist(String id, PositionBase pbhiPbId, Integer pbhiPbStatus,
            Date pbhiValidFrom) {
        setId(id);
        setPbhiPbId(pbhiPbId);
        setPbhiPbStatus(pbhiPbStatus);
        setPbhiValidFrom(pbhiValidFrom);
        initialize();
    }

    public BasePositionBaseHist(String id, PositionBase pbhiPbId, Department pbhiDeptId,
            Integer pbhiPbStatus, Integer pbhiMaxEmployee, Integer pbhiInCharge,
            Date pbhiValidFrom, Date pbhiValidTo) {
        this.id = id;
        this.pbhiPbId = pbhiPbId;
        this.pbhiDeptId = pbhiDeptId;
        this.pbhiPbStatus = pbhiPbStatus;
        this.pbhiMaxEmployee = pbhiMaxEmployee;
        this.pbhiInCharge = pbhiInCharge;
        this.pbhiValidFrom = pbhiValidFrom;
        this.pbhiValidTo = pbhiValidTo;
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

    public Integer getPbhiInCharge() {
        return this.pbhiInCharge;
    }

    public void setPbhiInCharge(Integer pbhiInCharge) {
        this.pbhiInCharge = pbhiInCharge;
    }

    public Integer getPbhiMaxEmployee() {
        return this.pbhiMaxEmployee;
    }

    public void setPbhiMaxEmployee(Integer pbhiMaxEmployee) {
        this.pbhiMaxEmployee = pbhiMaxEmployee;
    }

    public Integer getPbhiPbStatus() {
        return this.pbhiPbStatus;
    }

    public void setPbhiPbStatus(Integer pbhiPbStatus) {
        this.pbhiPbStatus = pbhiPbStatus;
    }

    public Date getPbhiValidFrom() {
        return this.pbhiValidFrom;
    }

    public void setPbhiValidFrom(Date pbhiValidFrom) {
        this.pbhiValidFrom = pbhiValidFrom;
    }

    public Date getPbhiValidTo() {
        return this.pbhiValidTo;
    }

    public void setPbhiValidTo(Date pbhiValidTo) {
        this.pbhiValidTo = pbhiValidTo;
    }

    public PositionBase getPbhiPbId() {
        return this.pbhiPbId;
    }

    public void setPbhiPbId(PositionBase pbhiPbId) {
        this.pbhiPbId = pbhiPbId;
    }

    public Department getPbhiDeptId() {
        return this.pbhiDeptId;
    }

    public void setPbhiDeptId(Department pbhiDeptId) {
        this.pbhiDeptId = pbhiDeptId;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof PositionBaseHist))
            return false;

        PositionBaseHist positionBaseHist = (PositionBaseHist) obj;
        if ((null == getId()) || (null == positionBaseHist.getId()))
            return false;
        return getId().equals(positionBaseHist.getId());
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
 * com.hr.profile.domain.base.BasePositionBaseHist JD-Core Version: 0.5.4
 */