package com.hr.report.action.profile.support;

import java.io.Serializable;

public class EmpSumReport implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int total;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.action.profile.support.EmpSumReport JD-Core Version: 0.5.4
 */