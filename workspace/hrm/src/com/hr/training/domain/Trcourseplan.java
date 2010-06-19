package com.hr.training.domain;

import com.hr.profile.domain.Employee;
import com.hr.training.domain.base.BaseTrcourseplan;
import java.math.BigDecimal;
import java.util.Date;

public class Trcourseplan extends BaseTrcourseplan {
    private static final long serialVersionUID = 1L;

    public Trcourseplan() {
    }

    public Trcourseplan(String trcpId) {
        super(trcpId);
    }

    public Trcourseplan(String trcpId, Employee trcpCreateBy, Trcourse trcpCourseNo,
            Employee trcpLastChangeBy, BigDecimal trcpBudgetHour, Date trcpStartDate,
            Date trcpEndDate, Integer trcpStatus, Date trcpCreateTime, Date trcpLastChangeTime) {
        super(trcpId, trcpCreateBy, trcpCourseNo, trcpLastChangeBy, trcpBudgetHour, trcpStartDate,
                trcpEndDate, trcpStatus, trcpCreateTime, trcpLastChangeTime);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.training.domain.Trcourseplan JD-Core Version: 0.5.4
 */