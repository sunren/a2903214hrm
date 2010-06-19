package com.hr.examin.domain;

import com.hr.examin.domain.base.BaseOvertimetype;
import java.math.BigDecimal;

public class Overtimetype extends BaseOvertimetype {
    private static final long serialVersionUID = 1L;

    public Overtimetype() {
    }

    public Overtimetype(String otNo) {
        super(otNo);
    }

    public Overtimetype(String otNo, String otName, String otDesc, BigDecimal otHourRate,
            Integer otSortId) {
        super(otNo, otName, otDesc, otHourRate, otSortId);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.domain.Overtimetype JD-Core Version: 0.5.4
 */