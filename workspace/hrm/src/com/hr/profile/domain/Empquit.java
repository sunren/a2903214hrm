package com.hr.profile.domain;

import com.hr.configuration.domain.Department;
import com.hr.profile.domain.base.BaseEmpquit;

public class Empquit extends BaseEmpquit {
    private static final long serialVersionUID = 1L;
    Position position = new Position();
    Department dept = new Department();

    public Empquit() {
    }

    public Empquit(String eqId) {
        super(eqId);
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Department getDept() {
        return this.dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.domain.Empquit JD-Core Version: 0.5.4
 */