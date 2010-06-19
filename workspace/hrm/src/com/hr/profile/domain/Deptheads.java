package com.hr.profile.domain;

import com.hr.configuration.domain.Department;
import com.hr.profile.domain.base.BaseDeptheads;
import java.util.Date;

public class Deptheads extends BaseDeptheads {
    private static final long serialVersionUID = 1L;

    public Deptheads() {
    }

    public Deptheads(String id) {
        super(id);
    }

    public Deptheads(String id, Employee dheEmpNo, Department dheDeptNo, Integer dheIsHead,
            Date dheValidFrom, String clientNo) {
        super(id, dheEmpNo, dheDeptNo, dheIsHead, dheValidFrom, clientNo);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.domain.Deptheads JD-Core Version: 0.5.4
 */