package com.hr.profile.domain;

import com.hr.configuration.domain.Department;
import com.hr.profile.domain.base.BaseDepthist;
import java.util.Date;

public class Depthist extends BaseDepthist {
    private static final long serialVersionUID = 1L;

    public Depthist() {
    }

    public Depthist(String id) {
        super(id);
    }

    public Depthist(String id, Department dhiDeptNo, Department dhiDeptSupId, String dhiDeptName,
            Integer dhiDeptStatus, Date dhiValidFrom) {
        super(id, dhiDeptNo, dhiDeptSupId, dhiDeptName, dhiDeptStatus, dhiValidFrom);
    }

    public Department assembleDeptByDh() {
        Department dept = new Department();
        dept.setId(getDhiDeptNo().getId());
        dept.setDepartmentName(getDhiDeptName());
        dept.setDepartmentParentId(getDhiDeptSupId());
        return dept;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.domain.Depthist JD-Core Version: 0.5.4
 */