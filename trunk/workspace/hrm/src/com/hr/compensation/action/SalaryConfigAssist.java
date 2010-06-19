package com.hr.compensation.action;

import java.math.BigDecimal;
import java.util.List;

public class SalaryConfigAssist {
    private String empNo;
    private String empName;
    private String empDept;
    private String empType;
    private String empLocation;
    private String empSupName;
    private String empStatus;
    private String empBankName;
    private String empCount;
    private String salaryLevel;
    private String acceptName;
    private String salaryAccept;
    private String costCenter;
    private List<BigDecimal> list;

    public SalaryConfigAssist() {
        this.empNo = null;

        this.empName = null;

        this.empDept = null;

        this.empType = null;

        this.empLocation = null;
        this.empSupName = null;

        this.empStatus = null;

        this.empBankName = null;

        this.empCount = null;

        this.salaryLevel = null;

        this.acceptName = null;

        this.salaryAccept = null;

        this.costCenter = null;

        this.list = null;
    }

    public String getEmpDept() {
        return this.empDept;
    }

    public void setEmpDept(String empDept) {
        this.empDept = empDept;
    }

    public String getEmpName() {
        return this.empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpNo() {
        return this.empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpStatus() {
        return this.empStatus;
    }

    public void setEmpStatus(String empStatus) {
        this.empStatus = empStatus;
    }

    public String getEmpLocation() {
        return this.empLocation;
    }

    public void setEmpLocation(String empLocation) {
        this.empLocation = empLocation;
    }

    public String getEmpSupName() {
        return this.empSupName;
    }

    public void setEmpSupName(String empSupName) {
        this.empSupName = empSupName;
    }

    public String getEmpType() {
        return this.empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public List<BigDecimal> getList() {
        return this.list;
    }

    public void setList(List<BigDecimal> list) {
        this.list = list;
    }

    public String getEmpCount() {
        return this.empCount;
    }

    public void setEmpCount(String empCount) {
        this.empCount = empCount;
    }

    public String getSalaryAccept() {
        return this.salaryAccept;
    }

    public void setSalaryAccept(String salaryAccept) {
        this.salaryAccept = salaryAccept;
    }

    public String getSalaryLevel() {
        return this.salaryLevel;
    }

    public void setSalaryLevel(String salaryLevel) {
        this.salaryLevel = salaryLevel;
    }

    public String getAcceptName() {
        return this.acceptName;
    }

    public void setAcceptName(String acceptName) {
        this.acceptName = acceptName;
    }

    public String getEmpBankName() {
        return this.empBankName;
    }

    public void setEmpBankName(String empBankName) {
        this.empBankName = empBankName;
    }

    public String getCostCenter() {
        return this.costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.action.SalaryConfigAssist JD-Core Version: 0.5.4
 */