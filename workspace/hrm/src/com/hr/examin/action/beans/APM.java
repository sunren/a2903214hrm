package com.hr.examin.action.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class APM {
    public static final int APM_AM = 0;
    public static final int APM_PM = 1;
    public static final String APM_AM_DESCRIPTION = "上午";
    public static final String APM_PM_DESCRIPTION = "下午";
    public static final String APM_ERROR_DESCRIPTION = "Error!";
    private int apm;
    private String name;

    public APM(int inputApm, String inputName) {
        this.apm = inputApm;
        this.name = inputName;
    }

    public int getApm() {
        return this.apm;
    }

    public void setApm(int apm) {
        this.apm = apm;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getDescription(int apm) {
        if (apm == 0)
            return "上午";
        if (apm == 1) {
            return "下午";
        }
        return "Error!";
    }

    public static String getDescription(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        if (calendar.get(9) == 0) {
            return "上午";
        }
        return "下午";
    }

    public static List<APM> getAPMList() {
        List result = new ArrayList();
        result.add(new APM(0, "上午"));
        result.add(new APM(1, "下午"));
        return result;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.action.beans.APM JD-Core Version: 0.5.4
 */