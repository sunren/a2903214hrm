package com.hr.profile.domain.base;

import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Position;
import com.hr.profile.domain.PositionBase;
import java.io.Serializable;

public abstract class BasePosition implements Serializable {
    public static String REF = "Position";
    public static String PROP_POSITION_PARENT_ID = "positionParentId";
    public static String PROP_POSITION_EMP_NO = "positionEmpNo";
    public static String PROP_POSITION_TAKE_NUM = "positionTakeNum";
    public static String PROP_CLIENT_NO = "clientNo";
    public static String PROP_POSITION_PB_ID = "positionPbId";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private Integer positionTakeNum;
    private String clientNo;
    private PositionBase positionPbId;
    private Position positionParentId;
    private Employee positionEmpNo;

    public BasePosition() {
        initialize();
    }

    public BasePosition(String id) {
        setId(id);
        initialize();
    }

    public BasePosition(String id, String clientNo) {
        setId(id);
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

    public Integer getPositionTakeNum() {
        return this.positionTakeNum;
    }

    public void setPositionTakeNum(Integer positionTakeNum) {
        this.positionTakeNum = positionTakeNum;
    }

    public String getClientNo() {
        return this.clientNo;
    }

    public void setClientNo(String clientNo) {
        this.clientNo = clientNo;
    }

    public PositionBase getPositionPbId() {
        return this.positionPbId;
    }

    public void setPositionPbId(PositionBase positionPbId) {
        this.positionPbId = positionPbId;
    }

    public Position getPositionParentId() {
        return this.positionParentId;
    }

    public void setPositionParentId(Position positionParentId) {
        this.positionParentId = positionParentId;
    }

    public Employee getPositionEmpNo() {
        return this.positionEmpNo;
    }

    public void setPositionEmpNo(Employee positionEmpNo) {
        this.positionEmpNo = positionEmpNo;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Position))
            return false;

        Position position = (Position) obj;
        if ((null == getId()) || (null == position.getId()))
            return false;
        return getId().equals(position.getId());
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
 * com.hr.profile.domain.base.BasePosition JD-Core Version: 0.5.4
 */