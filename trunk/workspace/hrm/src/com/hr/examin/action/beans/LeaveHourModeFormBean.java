package com.hr.examin.action.beans;

import com.hr.examin.domain.Leaverequest;

public class LeaveHourModeFormBean {
    private Leaverequest lr;
    private int beginHour;
    private int beginMinute;
    private int endHour;
    private int endMinute;

    public Leaverequest getLr() {
        return this.lr;
    }

    public void setLr(Leaverequest lr) {
        this.lr = lr;
    }

    public int getBeginHour() {
        return this.beginHour;
    }

    public void setBeginHour(int beginHour) {
        this.beginHour = beginHour;
    }

    public int getEndHour() {
        return this.endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getBeginMinute() {
        return this.beginMinute;
    }

    public void setBeginMinute(int beginMinute) {
        this.beginMinute = beginMinute;
    }

    public int getEndMinute() {
        return this.endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.action.beans.LeaveHourModeFormBean JD-Core Version: 0.5.4
 */