package com.hr.report.action.compensation.support;

import java.io.Serializable;

public class SalaryReportParamBean implements Serializable {
    private String startYear;
    private String endYear;
    private String startMonth;
    private String endMonth;
    private String departmentId;
    private String jobgradeId;
    private String itemId;
    private String itemId2;

    public SalaryReportParamBean() {
        this.startYear = "";

        this.endYear = "";

        this.startMonth = "";

        this.endMonth = "";

        this.departmentId = "";

        this.jobgradeId = "";

        this.itemId = "";

        this.itemId2 = "";
    }

    public String getStartYear() {
        return this.startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return this.endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public String getStartMonth() {
        return this.startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getEndMonth() {
        return this.endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public String getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getJobgradeId() {
        return this.jobgradeId;
    }

    public void setJobgradeId(String jobgradeId) {
        this.jobgradeId = jobgradeId;
    }

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId2() {
        return this.itemId2;
    }

    public void setItemId2(String itemId2) {
        this.itemId2 = itemId2;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.action.compensation.support.SalaryReportParamBean JD-Core Version: 0.5.4
 */