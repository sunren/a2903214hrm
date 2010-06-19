package com.hr.examin.domain.base;

import com.hr.base.BaseDomain;
import com.hr.examin.domain.Attendshiftowner;
import java.io.Serializable;

public abstract class BaseAttendshiftowner extends BaseDomain implements Serializable {
    public static String REF = "Attendshiftowner";
    public static String PROP_ATTSO_GROUP_ID = "attsoGroupId";
    public static String PROP_ATTSO_DEPT_ID = "attsoDeptId";
    public static String PROP_ATTSO_BU_ID = "attsoBuId";
    public static String PROP_ATTSO_EMP_NO = "attsoEmpNo";
    public static String PROP_ATTSO_LOC_ID = "attsoLocId";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private String attsoEmpNo;
    private String attsoDeptId;
    private String attsoBuId;
    private String attsoLocId;
    private String attsoGroupId;

    public BaseAttendshiftowner() {
        initialize();
    }

    public BaseAttendshiftowner(String id) {
        setId(id);
        initialize();
    }

    public BaseAttendshiftowner(String id, String attsoEmpNo) {
        setId(id);
        setAttsoEmpNo(attsoEmpNo);
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

    public String getAttsoEmpNo() {
        return this.attsoEmpNo;
    }

    public void setAttsoEmpNo(String attsoEmpNo) {
        this.attsoEmpNo = attsoEmpNo;
    }

    public String getAttsoDeptId() {
        return this.attsoDeptId;
    }

    public void setAttsoDeptId(String attsoDeptId) {
        this.attsoDeptId = attsoDeptId;
    }

    public String getAttsoBuId() {
        return this.attsoBuId;
    }

    public void setAttsoBuId(String attsoBuId) {
        this.attsoBuId = attsoBuId;
    }

    public String getAttsoLocId() {
        return this.attsoLocId;
    }

    public void setAttsoLocId(String attsoLocId) {
        this.attsoLocId = attsoLocId;
    }

    public String getAttsoGroupId() {
        return this.attsoGroupId;
    }

    public void setAttsoGroupId(String attsoGroupId) {
        this.attsoGroupId = attsoGroupId;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Attendshiftowner))
            return false;

        Attendshiftowner attendshiftowner = (Attendshiftowner) obj;
        if ((null == getId()) || (null == attendshiftowner.getId()))
            return false;
        return getId().equals(attendshiftowner.getId());
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
 * com.hr.examin.domain.base.BaseAttendshiftowner JD-Core Version: 0.5.4
 */