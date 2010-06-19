package com.hr.compensation.domain;

import com.hr.compensation.domain.base.BaseEmpsalaryperiod;

public class Empsalaryperiod extends BaseEmpsalaryperiod {
    private static final long serialVersionUID = 1L;

    public Empsalaryperiod() {
    }

    public Empsalaryperiod(String id) {
        super(id);
    }

    public Empsalaryperiod(String id, String espdYearmonth, Integer espdStatus) {
        super(id, espdYearmonth, espdStatus);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.domain.Empsalaryperiod JD-Core Version: 0.5.4
 */