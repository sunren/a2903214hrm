package com.hr.examin.domain;

import com.hr.examin.domain.base.BaseLeavebalance;
import com.hr.profile.domain.Employee;

public class Leavebalance extends BaseLeavebalance {
    private static final long serialVersionUID = 1L;
    private String empId;

    public Leavebalance() {
    }

    public Leavebalance(String lbId) {
        super(lbId);
    }

    public Leavebalance(String lbId, Employee lbEmpNo, String lbYear) {
        super(lbId, lbEmpNo, lbYear);
    }

    public String getEmpId() {
        return this.empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.domain.Leavebalance JD-Core Version: 0.5.4
 */