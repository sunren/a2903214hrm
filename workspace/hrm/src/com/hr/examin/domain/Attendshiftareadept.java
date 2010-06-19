package com.hr.examin.domain;

import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Location;
import com.hr.examin.domain.base.BaseAttendshiftareadept;

public class Attendshiftareadept extends BaseAttendshiftareadept {
    private static final long serialVersionUID = 1L;

    public Attendshiftareadept() {
    }

    public Attendshiftareadept(Attendshift asadShiftId, Location asadAreaId, Department asadDeptId) {
        setAsadShiftId(asadShiftId);
        setAsadAreaId(asadAreaId);
        setAsadDeptId(asadDeptId);
        initialize();
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.domain.Attendshiftareadept JD-Core Version: 0.5.4
 */