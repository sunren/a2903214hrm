package com.hr.compensation.action;

import java.math.BigDecimal;

public class SalaryPaidConverge {
    private String empDepartment;
    private int empNumber;
    private int lastMonthEmpNumber;
    private int quitEmpNumber;
    private int joinEmpNumber;
    private BigDecimal salaryAmount;
    private BigDecimal lastMonthSalaryAmount;
    private BigDecimal salaryDifference;

    public SalaryPaidConverge() {
        this.empDepartment = null;

        this.empNumber = 0;

        this.lastMonthEmpNumber = 0;

        this.quitEmpNumber = 0;

        this.joinEmpNumber = 0;

        this.salaryAmount = new BigDecimal(0.0D);

        this.lastMonthSalaryAmount = new BigDecimal(0.0D);

        this.salaryDifference = new BigDecimal(0.0D);
    }

    public String getEmpDepartment() {
        return this.empDepartment;
    }

    public void setEmpDepartment(String empDepartment) {
        this.empDepartment = empDepartment;
    }

    public int getEmpNumber() {
        return this.empNumber;
    }

    public void setEmpNumber(int empNumber) {
        this.empNumber = empNumber;
    }

    public int getLastMonthEmpNumber() {
        return this.lastMonthEmpNumber;
    }

    public void setLastMonthEmpNumber(int lastMonthNumber) {
        this.lastMonthEmpNumber = lastMonthNumber;
    }

    public int getQuitEmpNumber() {
        return this.quitEmpNumber;
    }

    public void setQuitEmpNumber(int quitEmpNumber) {
        this.quitEmpNumber = quitEmpNumber;
    }

    public int getJoinEmpNumber() {
        return this.joinEmpNumber;
    }

    public void setJoinEmpNumber(int joinEmpNumber) {
        this.joinEmpNumber = joinEmpNumber;
    }

    public BigDecimal getSalaryAmount() {
        return this.salaryAmount;
    }

    public void setSalaryAmount(BigDecimal salaryAmount) {
        this.salaryAmount = salaryAmount;
    }

    public BigDecimal getLastMonthSalaryAmount() {
        return this.lastMonthSalaryAmount;
    }

    public void setLastMonthSalaryAmount(BigDecimal lastMonthSalaryAmount) {
        this.lastMonthSalaryAmount = lastMonthSalaryAmount;
    }

    public BigDecimal getSalaryDifference() {
        return this.salaryDifference;
    }

    public void setSalaryDifference(BigDecimal salaryDifference) {
        this.salaryDifference = salaryDifference;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.action.SalaryPaidConverge JD-Core Version: 0.5.4
 */