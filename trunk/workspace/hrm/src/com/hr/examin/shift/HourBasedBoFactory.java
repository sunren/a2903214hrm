package com.hr.examin.shift;

import com.hr.examin.bo.interfaces.IAttendmonthlyBO;
import com.hr.examin.core.ExaminBoFactory;
import java.util.HashMap;
import java.util.Map;

public class HourBasedBoFactory extends ExaminBoFactory {
    private static final String[] PROP_ARR = { "attmDutyHours", "attmOnDutyHours",
            "attmLeaveHours", "attmLeaveAnnualHours", "attmLeaveTiaoxiuHours",
            "attmLeaveCasualHours", "attmLeaveSickHours", "attmLeaveSick01Hours",
            "attmLeaveWeddingHours", "attmLeaveMaternityHours", "attmLeaveTravelHours",
            "attmLeaveOtherHours", "attmLeaveOuterHours", "attmOvertimeHours",
            "attmOtWeekendHours", "attmOtNormalHours", "attmOtHolidayHours", "attmOffDutyHours" };

    public IAttendmonthlyBO createAttendmonthlyBo() {
        return (IAttendmonthlyBO) getBean("hourBasedAttendMonthlyBo");
    }

    public Map<String, String> createJSPDisplayTextMap() {
        Map map = new HashMap();
        String[] hourProps = getDisplayHourProperties();
        for (int i = 0; i < hourProps.length; ++i) {
            map.put(hourProps[i], "小时");
        }
        return map;
    }

    protected String[] getDisplayHourProperties() {
        return PROP_ARR;
    }

    protected String[] getDisplayDayProperties() {
        return PROP_ARR;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.shift.HourBasedBoFactory JD-Core Version: 0.5.4
 */