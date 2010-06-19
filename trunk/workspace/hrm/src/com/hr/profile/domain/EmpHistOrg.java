package com.hr.profile.domain;

import com.hr.configuration.domain.Department;
import com.hr.profile.domain.base.BaseEmpHistOrg;
import java.util.Date;

public class EmpHistOrg extends BaseEmpHistOrg {
    private static final long serialVersionUID = 1L;

    public EmpHistOrg() {
    }

    public EmpHistOrg(String id) {
        super(id);
    }

    public EmpHistOrg(String id, Employee ehoEmpNo, Department ehoDeptNo, PositionBase ehoPbNo,
            Date ehoValidFrom, Integer ehoIsLatest) {
        super(id, ehoEmpNo, ehoDeptNo, ehoPbNo, ehoValidFrom, ehoIsLatest);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.domain.EmpHistOrg JD-Core Version: 0.5.4
 */