package com.hr.examin.shift;

import com.hr.examin.support.AttendDailyMemory;

public abstract interface ExceptionDataFilter {
    public abstract boolean doFilter(AttendDailyMemory paramAttendDailyMemory, Integer paramInteger);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.shift.ExceptionDataFilter JD-Core Version: 0.5.4
 */