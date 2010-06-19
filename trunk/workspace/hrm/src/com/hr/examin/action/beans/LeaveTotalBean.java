package com.hr.examin.action.beans;

public class LeaveTotalBean {
    private String type;
    private String totaldays;
    private String totalTimes;
    private String toApproveDays;
    private String toApproveTimes;

    public LeaveTotalBean() {
    }

    public LeaveTotalBean(String type, String totaldays, String totalTimes, String toApproveDays,
            String toApproveTimes) {
        this.type = type;
        this.totaldays = totaldays;
        this.totalTimes = totalTimes;
        this.toApproveDays = toApproveDays;
        this.toApproveTimes = toApproveTimes;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTotaldays() {
        return this.totaldays;
    }

    public void setTotaldays(String totaldays) {
        this.totaldays = totaldays;
    }

    public String getTotalTimes() {
        return this.totalTimes;
    }

    public void setTotalTimes(String totalTimes) {
        this.totalTimes = totalTimes;
    }

    public String getToApproveDays() {
        return this.toApproveDays;
    }

    public void setToApproveDays(String toApproveDays) {
        this.toApproveDays = toApproveDays;
    }

    public String getToApproveTimes() {
        return this.toApproveTimes;
    }

    public void setToApproveTimes(String toApproveTimes) {
        this.toApproveTimes = toApproveTimes;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.action.beans.LeaveTotalBean JD-Core Version: 0.5.4
 */