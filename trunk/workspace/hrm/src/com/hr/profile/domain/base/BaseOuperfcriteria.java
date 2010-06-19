package com.hr.profile.domain.base;

import com.hr.configuration.domain.Department;
import com.hr.profile.domain.Ouperfcriteria;
import com.hr.profile.domain.PositionBase;
import java.io.Serializable;

public abstract class BaseOuperfcriteria implements Serializable {
    public static String REF = "Ouperfcriteria";
    public static String PROP_OUP_DEPT = "oupDept";
    public static String PROP_OUP_RATE = "oupRate";
    public static String PROP_OUP_SORT_ID = "oupSortId";
    public static String PROP_OUP_PB = "oupPb";
    public static String PROP_OUP_NAME = "oupName";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private String oupName;
    private Integer oupRate;
    private Integer oupSortId;
    private Department oupDept;
    private PositionBase oupPb;

    public BaseOuperfcriteria() {
        initialize();
    }

    public BaseOuperfcriteria(String id) {
        setId(id);
        initialize();
    }

    public BaseOuperfcriteria(String id, String oupName, Integer oupRate, Integer oupSortId) {
        setId(id);
        setOupName(oupName);
        setOupRate(oupRate);
        setOupSortId(oupSortId);
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

    public String getOupName() {
        return this.oupName;
    }

    public void setOupName(String oupName) {
        this.oupName = oupName;
    }

    public Integer getOupRate() {
        return this.oupRate;
    }

    public void setOupRate(Integer oupRate) {
        this.oupRate = oupRate;
    }

    public Integer getOupSortId() {
        return this.oupSortId;
    }

    public void setOupSortId(Integer oupSortId) {
        this.oupSortId = oupSortId;
    }

    public Department getOupDept() {
        return this.oupDept;
    }

    public void setOupDept(Department oupDept) {
        this.oupDept = oupDept;
    }

    public PositionBase getOupPb() {
        return this.oupPb;
    }

    public void setOupPb(PositionBase oupPb) {
        this.oupPb = oupPb;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Ouperfcriteria))
            return false;

        Ouperfcriteria ouperfcriteria = (Ouperfcriteria) obj;
        if ((null == getId()) || (null == ouperfcriteria.getId()))
            return false;
        return getId().equals(ouperfcriteria.getId());
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
 * com.hr.profile.domain.base.BaseOuperfcriteria JD-Core Version: 0.5.4
 */