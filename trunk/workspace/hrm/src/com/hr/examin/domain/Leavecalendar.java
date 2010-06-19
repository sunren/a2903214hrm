package com.hr.examin.domain;

import com.hr.examin.domain.base.BaseLeavecalendar;
import java.util.Date;

public class Leavecalendar extends BaseLeavecalendar {
    private static final long serialVersionUID = 1L;
    public static final int LC_SIGN_HOLIDAY = 0;
    public static final int LC_LAW_HOLIDAY = 2;
    public static final int LC_SIGN_WORKDAY = 1;

    public Leavecalendar() {
    }

    public Leavecalendar(String lcId) {
        super(lcId);
    }

    public Leavecalendar(String lcId, Date lcDate, Integer lcSign) {
        super(lcId, lcDate, lcSign);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.domain.Leavecalendar JD-Core Version: 0.5.4
 */