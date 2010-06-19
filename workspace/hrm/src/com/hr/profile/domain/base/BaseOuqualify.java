package com.hr.profile.domain.base;

import com.hr.profile.domain.Ouqualify;
import com.hr.profile.domain.PositionBase;
import java.io.Serializable;

public abstract class BaseOuqualify implements Serializable {
    public static String REF = "Ouqualify";
    public static String PROP_OUQ_PB_ID = "ouqPbId";
    public static String PROP_OUQ_NAME = "ouqName";
    public static String PROP_OUQ_DESC = "ouqDesc";
    public static String PROP_OUQ_SORT_ID = "ouqSortId";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private String ouqName;
    private String ouqDesc;
    private Integer ouqSortId;
    private PositionBase ouqPbId;

    public BaseOuqualify() {
        initialize();
    }

    public BaseOuqualify(String id) {
        setId(id);
        initialize();
    }

    public BaseOuqualify(String id, String ouqName, Integer ouqSortId) {
        setId(id);
        setOuqName(ouqName);
        setOuqSortId(ouqSortId);
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

    public String getOuqName() {
        return this.ouqName;
    }

    public void setOuqName(String ouqName) {
        this.ouqName = ouqName;
    }

    public String getOuqDesc() {
        return this.ouqDesc;
    }

    public void setOuqDesc(String ouqDesc) {
        this.ouqDesc = ouqDesc;
    }

    public Integer getOuqSortId() {
        return this.ouqSortId;
    }

    public void setOuqSortId(Integer ouqSortId) {
        this.ouqSortId = ouqSortId;
    }

    public PositionBase getOuqPbId() {
        return this.ouqPbId;
    }

    public void setOuqPbId(PositionBase ouqPbId) {
        this.ouqPbId = ouqPbId;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Ouqualify))
            return false;

        Ouqualify ouqualify = (Ouqualify) obj;
        if ((null == getId()) || (null == ouqualify.getId()))
            return false;
        return getId().equals(ouqualify.getId());
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
 * com.hr.profile.domain.base.BaseOuqualify JD-Core Version: 0.5.4
 */