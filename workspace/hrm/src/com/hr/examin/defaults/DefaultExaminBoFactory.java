package com.hr.examin.defaults;

import com.hr.examin.bo.interfaces.IAttendmonthlyBO;
import com.hr.examin.core.ExaminBoFactory;

public class DefaultExaminBoFactory extends ExaminBoFactory {
    public IAttendmonthlyBO createAttendmonthlyBo() {
        return (IAttendmonthlyBO) getBean("attendmonthlyBo");
    }

    protected String[] getDisplayDayProperties() {
        return new String[] { "attmDutyHours", "attmOnDutyHours", "attmOffDutyHours",
                "attmLeaveHours", "attmLeaveAnnualHours", "attmLeaveCasualHours",
                "attmLeaveSickHours", "attmLeaveTiaoxiuHours", "attmLeaveSick01Hours",
                "attmLeaveWeddingHours", "attmLeaveMaternityHours", "attmLeaveTravelHours",
                "attmLeaveOtherHours", "attmLeaveOtherHours", "attmLeaveOuterHours" };
    }

    protected String[] getDisplayHourProperties() {
        return new String[] { "attmOvertimeHours", "attmOtWeekendHours", "attmOtNormalHours",
                "attmOtHolidayHours" };
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.defaults.DefaultExaminBoFactory JD-Core Version: 0.5.4
 */