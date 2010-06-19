package com.hr.examin.domain.base;

import com.hr.base.BaseDomain;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Location;
import com.hr.examin.domain.Attendshift;
import java.io.Serializable;

public class BaseAttendshiftareadept extends BaseDomain implements Serializable {
    public static String REF = "BaseAttendshiftareadept";
    public static String PROP_ASAD_ID = "id";
    public static String PROP_ASAD_SHIFT_ID = "asadShiftId";
    public static String PROP_ASAD_AREA_ID = "asadAreaId";
    public static String PROP_ASAD_DEPT_ID = "asadDeptId";
    private String id;
    private Attendshift asadShiftId;
    private Location asadAreaId;
    private Department asadDeptId;

    public BaseAttendshiftareadept() {
        initialize();
    }

    protected void initialize() {
    }

    public Location getAsadAreaId() {
        return this.asadAreaId;
    }

    public void setAsadAreaId(Location asadAreaId) {
        this.asadAreaId = asadAreaId;
    }

    public Department getAsadDeptId() {
        return this.asadDeptId;
    }

    public void setAsadDeptId(Department asadDeptId) {
        this.asadDeptId = asadDeptId;
    }

    public Attendshift getAsadShiftId() {
        return this.asadShiftId;
    }

    public void setAsadShiftId(Attendshift asadShiftId) {
        this.asadShiftId = asadShiftId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.domain.base.BaseAttendshiftareadept JD-Core Version: 0.5.4
 */