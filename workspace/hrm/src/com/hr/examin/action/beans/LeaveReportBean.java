package com.hr.examin.action.beans;

import java.math.BigDecimal;

public class LeaveReportBean {
    private String empId;
    private String empName;
    private String leaveTypeId;
    private String leaveTypeName;
    private Integer leaveTypeCategory;
    private String leaveTypeCategoryName;
    private BigDecimal usableDays;
    private BigDecimal usableHours;
    private BigDecimal usedDays;
    private BigDecimal usedHours;
    private BigDecimal yearEndUsableDays;
    private BigDecimal yearEndUsableHours;
    private BigDecimal approvingDays;
    private BigDecimal approvingHours;

    public LeaveReportBean() {
        this.leaveTypeCategory = Integer.valueOf(20);

        this.usableDays = new BigDecimal(0);

        this.usableHours = new BigDecimal(0);

        this.usedDays = new BigDecimal(0);

        this.usedHours = new BigDecimal(0);

        this.yearEndUsableDays = new BigDecimal(0);

        this.yearEndUsableHours = new BigDecimal(0);

        this.approvingDays = new BigDecimal(0);

        this.approvingHours = new BigDecimal(0);
    }

    public String getEmpId() {
        return this.empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return this.empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getLeaveTypeId() {
        return this.leaveTypeId;
    }

    public void setLeaveTypeId(String leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public String getLeaveTypeName() {
        return this.leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }

    public Integer getLeaveTypeCategory() {
        return this.leaveTypeCategory;
    }

    public void setLeaveTypeCategory(Integer leaveTypeCategory) {
        this.leaveTypeCategory = leaveTypeCategory;
    }

    public String getLeaveTypeCategoryName() {
        return this.leaveTypeCategoryName;
    }

    public void setLeaveTypeCategoryName(String leaveTypeCategoryName) {
        this.leaveTypeCategoryName = leaveTypeCategoryName;
    }

    public BigDecimal getUsableDays() {
        return this.usableDays;
    }

    public void setUsableDays(BigDecimal usableDays) {
        this.usableDays = usableDays;
    }

    public BigDecimal getUsableHours() {
        return this.usableHours;
    }

    public void setUsableHours(BigDecimal usableHours) {
        this.usableHours = usableHours;
    }

    public BigDecimal getUsedDays() {
        return this.usedDays;
    }

    public void setUsedDays(BigDecimal usedDays) {
        this.usedDays = usedDays;
    }

    public BigDecimal getUsedHours() {
        return this.usedHours;
    }

    public void setUsedHours(BigDecimal usedHours) {
        this.usedHours = usedHours;
    }

    public BigDecimal getYearEndUsableDays() {
        return this.yearEndUsableDays;
    }

    public void setYearEndUsableDays(BigDecimal yearEndUsableDays) {
        this.yearEndUsableDays = yearEndUsableDays;
    }

    public BigDecimal getYearEndUsableHours() {
        return this.yearEndUsableHours;
    }

    public void setYearEndUsableHours(BigDecimal yearEndUsableHours) {
        this.yearEndUsableHours = yearEndUsableHours;
    }

    public BigDecimal getApprovingDays() {
        return this.approvingDays;
    }

    public void setApprovingDays(BigDecimal approvingDays) {
        this.approvingDays = approvingDays;
    }

    public BigDecimal getApprovingHours() {
        return this.approvingHours;
    }

    public void setApprovingHours(BigDecimal approvingHours) {
        this.approvingHours = approvingHours;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.action.beans.LeaveReportBean JD-Core Version: 0.5.4
 */