package com.hr.configuration.domain;

import com.hr.configuration.domain.base.BaseDepartment;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.PositionBase;
import java.util.List;

public class Department extends BaseDepartment {
    private static final long serialVersionUID = 1L;
    private String deptHeader;
    private Employee deptHead;
    private Integer orderNum = Integer.valueOf(0);

    private Integer actualNum = Integer.valueOf(0);
    private Department parentDept;
    private List<Department> subDeptList;
    private PositionBase respPb;

    public Department() {
    }

    public Department(String id) {
        super(id);
    }

    public Department(String id, String departmentName, Integer departmentSortId) {
        super(id, departmentName, departmentSortId);
    }

    public String getDeptHeader() {
        return this.deptHeader;
    }

    public void setDeptHeader(String deptHeader) {
        this.deptHeader = deptHeader;
    }

    public static long getSerialVersionUID() {
        return 1L;
    }

    public Employee getDeptHead() {
        return this.deptHead;
    }

    public void setDeptHead(Employee deptHead) {
        this.deptHead = deptHead;
    }

    public Integer getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getActualNum() {
        return this.actualNum;
    }

    public void setActualNum(Integer actualNum) {
        this.actualNum = actualNum;
    }

    public List<Department> getSubDeptList() {
        return this.subDeptList;
    }

    public void setSubDeptList(List<Department> subDeptList) {
        this.subDeptList = subDeptList;
    }

    public Department getParentDept() {
        return this.parentDept;
    }

    public void setParentDept(Department parentDept) {
        this.parentDept = parentDept;
    }

    public PositionBase getRespPb() {
        return this.respPb;
    }

    public void setRespPb(PositionBase respPb) {
        this.respPb = respPb;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.domain.Department JD-Core Version: 0.5.4
 */