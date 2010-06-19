package com.hr.examin.domain;

import com.hr.configuration.domain.Department;
import com.hr.examin.domain.base.BaseAttendmonthly;
import com.hr.profile.domain.Employee;
import java.math.BigDecimal;

public class Attendmonthly extends BaseAttendmonthly {
    private static final long serialVersionUID = 1L;
    private Department attmBranch;

    public Attendmonthly() {
    }

    public Attendmonthly(String id) {
        super(id);
    }

    public Attendmonthly(String id, Employee attmEmpId, String attmYearmonth, String attmYear,
            String attmMonth, BigDecimal attmDutyDays, BigDecimal attmDutyHours,
            BigDecimal attmOnDutyDays, BigDecimal attmOnDutyHours, BigDecimal attmOffDutyDays,
            BigDecimal attmOffDutyHours) {
        super(id, attmEmpId, attmYearmonth, attmYear, attmMonth, attmDutyDays, attmDutyHours,
                attmOnDutyDays, attmOnDutyHours, attmOffDutyDays, attmOffDutyHours);
    }

    public Department getAttmBranch() {
        return this.attmBranch;
    }

    public void setAttmBranch(Department attmBranch) {
        this.attmBranch = attmBranch;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.domain.Attendmonthly JD-Core Version: 0.5.4
 */