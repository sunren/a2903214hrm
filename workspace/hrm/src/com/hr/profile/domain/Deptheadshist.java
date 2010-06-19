package com.hr.profile.domain;

import com.hr.configuration.domain.Department;
import com.hr.profile.domain.base.BaseDeptheadshist;
import java.util.Date;

public class Deptheadshist extends BaseDeptheadshist {
    private static final long serialVersionUID = 1L;

    public Deptheadshist() {
    }

    public Deptheadshist(String id) {
        super(id);
    }

    public Deptheadshist(String id, Employee dhhEmpNo, Department dhhDeptNo, Integer dhhIsHead,
            Date dhhValidFrom, String clientNo) {
        super(id, dhhEmpNo, dhhDeptNo, dhhIsHead, dhhValidFrom, clientNo);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.domain.Deptheadshist JD-Core Version: 0.5.4
 */