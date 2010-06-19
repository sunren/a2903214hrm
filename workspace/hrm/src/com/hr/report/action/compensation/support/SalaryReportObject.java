package com.hr.report.action.compensation.support;

import java.io.Serializable;
import java.math.BigDecimal;

public class SalaryReportObject implements Serializable {
    private String name;
    private BigDecimal total;
    private String name2;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getName2() {
        return this.name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.action.compensation.support.SalaryReportObject JD-Core Version: 0.5.4
 */