package com.hr.examin.domain;

import com.hr.examin.domain.base.BaseAttendperiod;

public class Attendperiod extends BaseAttendperiod {
    private static final long serialVersionUID = 1L;

    public Attendperiod() {
    }

    public Attendperiod(String id) {
        super(id);
    }

    public Attendperiod(String id, String attpYearmonth, String attpYear, String attpMonth,
            Integer attpStatus) {
        super(id, attpYearmonth, attpYear, attpMonth, attpStatus);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.domain.Attendperiod JD-Core Version: 0.5.4
 */