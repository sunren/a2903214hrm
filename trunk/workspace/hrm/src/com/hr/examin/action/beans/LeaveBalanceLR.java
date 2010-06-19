package com.hr.examin.action.beans;

public class LeaveBalanceLR {
    private String lrId;
    private String lrStartDate;
    private String lrEndDate;
    private double lrTotalHours;
    private String lrStatus;

    public LeaveBalanceLR(String inputlrId, String inputlrStartDate, String inputlrEndDate,
            double inputlrTotalHours, String inputlrStatus) {
        this.lrId = inputlrId;
        this.lrStartDate = inputlrStartDate;
        this.lrEndDate = inputlrEndDate;
        this.lrTotalHours = inputlrTotalHours;
        this.lrStatus = inputlrStatus;
    }

    public String getLrId() {
        return this.lrId;
    }

    public void setLrId(String lrId) {
        this.lrId = lrId;
    }

    public String getLrStartDate() {
        return this.lrStartDate;
    }

    public void setLrStartDate(String lrStartDate) {
        this.lrStartDate = lrStartDate;
    }

    public String getLrEndDate() {
        return this.lrEndDate;
    }

    public void setLrEndDate(String lrEndDate) {
        this.lrEndDate = lrEndDate;
    }

    public double getLrTotalHours() {
        return this.lrTotalHours;
    }

    public void setLrTotalHours(double lrTotalHours) {
        this.lrTotalHours = lrTotalHours;
    }

    public String getLrStatus() {
        return this.lrStatus;
    }

    public void setLrStatus(String lrStatus) {
        this.lrStatus = lrStatus;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.action.beans.LeaveBalanceLR JD-Core Version: 0.5.4
 */