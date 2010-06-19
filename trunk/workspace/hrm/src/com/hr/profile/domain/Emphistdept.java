package com.hr.profile.domain;

import com.hr.configuration.domain.Department;
import com.hr.profile.domain.base.BaseEmphistdept;
import java.util.Date;

public class Emphistdept extends BaseEmphistdept {
    private static final long serialVersionUID = 1L;

    public Emphistdept() {
    }

    public Emphistdept(Integer id) {
        super(id);
    }

    public Emphistdept(Integer id, Employee ehdEmpNo, Department ehdDeptNo, Date ehdValidFrom,
            Integer ehdIsLatest) {
        super(id, ehdEmpNo, ehdDeptNo, ehdValidFrom, ehdIsLatest);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.domain.Emphistdept JD-Core Version: 0.5.4
 */