package com.hr.examin.action.beans;

import com.hr.util.MyTools;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Hours {
    private int hour;
    private String description;

    public static List<Hours> getHourList() {
        List list = new ArrayList();
        int limit = 24;
        for (int i = 0; i < limit; ++i) {
            Hours h = new Hours();
            h.setHour(i);
            h.setDescription(String.valueOf(i) + "炄1�7");
            list.add(h);
        }
        return list;
    }

    public static List<Hours> getHourList_24() {
        List list = new ArrayList();
        int limit = 24;
        for (int i = 0; i <= limit; ++i) {
            Hours h = new Hours();
            h.setHour(i);
            h.setDescription(String.valueOf(i) + "炄1�7");
            list.add(h);
        }
        return list;
    }

    public static String getAM_PMDescription(Integer type) {
        if ((type == null) || (type.intValue() == 0)) {
            return "上午";
        }
        return "下午";
    }

    public static String getAM_PMDescriptionInStartDate(Date startDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        if (c.get(9) == 1) {
            return "下午";
        }
        return "上午";
    }

    public static String getAM_PMDescription(Date startDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        if (c.get(9) == 0) {
            return "0";
        }
        return "1";
    }

    public static String getAM_PMDescriptionInEndDate(Date endDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        c.set(12, c.get(12) - 1);
        if (c.get(9) == 1) {
            return "下午";
        }
        return "上午";
    }

    public static double getTotalDay(double totalHours, double hoursPerDay) {
        double temp = totalHours / hoursPerDay;
        temp = MyTools.round(temp, 2);
        return temp;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.action.beans.Hours JD-Core Version: 0.5.4
 */