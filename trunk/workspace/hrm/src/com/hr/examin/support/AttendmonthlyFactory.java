package com.hr.examin.support;

import com.hr.examin.domain.Attendmonthly;
import java.math.BigDecimal;

public class AttendmonthlyFactory {
    public static void initAttendMonthlyProperties(Attendmonthly monthly) {
        monthly.setAttmAbsentDays(BigDecimalUtils.convertToZeroIfNull(monthly.getAttmAbsentDays()));
        monthly.setAttmAbsentHours(BigDecimalUtils
                .convertToZeroIfNull(monthly.getAttmAbsentHours()));

        monthly.setAttmDutyHours(BigDecimalUtils.convertToZeroIfNull(monthly.getAttmDutyHours()));
        monthly.setAttmDutyDays(BigDecimalUtils.convertToZeroIfNull(monthly.getAttmDutyDays()));

        monthly.setAttmOffDutyHours(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmOffDutyHours()));
        monthly.setAttmOffDutyDays(BigDecimalUtils
                .convertToZeroIfNull(monthly.getAttmOffDutyDays()));

        monthly.setAttmOnDutyHours(BigDecimalUtils
                .convertToZeroIfNull(monthly.getAttmOnDutyHours()));
        monthly.setAttmOnDutyDays(BigDecimalUtils.convertToZeroIfNull(monthly.getAttmOnDutyDays()));

        monthly.setAttmEarlyLeave(BigDecimalUtils.convertToZeroIfNull(monthly.getAttmEarlyLeave()));

        monthly.setAttmLateTimes(BigDecimalUtils.convertToZeroIfNull(monthly.getAttmLateTimes()));

        monthly.setAttmLeaveCasualHours(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveCasualHours()));
        monthly.setAttmLeaveCasualDays(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveCasualDays()));

        monthly.setAttmLeaveAnnualHours(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveAnnualHours()));
        monthly.setAttmLeaveAnnualDays(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveAnnualDays()));

        monthly.setAttmLeaveSickHours(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveSickHours()));
        monthly.setAttmLeaveSickDays(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveSickDays()));

        monthly.setAttmLeaveSick01Hours(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveSick01Hours()));
        monthly.setAttmLeaveSick01Days(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveSick01Days()));

        monthly.setAttmLeaveSick02Hours(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveSick02Hours()));
        monthly.setAttmLeaveSick02Days(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveSick02Days()));

        monthly.setAttmLeaveWeddingHours(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveWeddingHours()));
        monthly.setAttmLeaveWeddingDays(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveWeddingDays()));

        monthly.setAttmLeaveMaternityHours(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveMaternityHours()));
        monthly.setAttmLeaveMaternityDays(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveMaternityDays()));

        monthly.setAttmLeaveTravelHours(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveTravelHours()));
        monthly.setAttmLeaveTravelDays(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveTravelDays()));

        monthly.setAttmLeaveOuterHours(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveOuterHours()));
        monthly.setAttmLeaveOuterDays(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveOuterDays()));

        monthly.setAttmLeaveTiaoxiuHours(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveTiaoxiuHours()));
        monthly.setAttmLeaveTiaoxiuDays(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveTiaoxiuDays()));

        monthly.setAttmLeaveTiaoxiu01Hours(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveTiaoxiu01Hours()));
        monthly.setAttmLeaveTiaoxiu01Days(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveTiaoxiu01Days()));

        monthly.setAttmLeaveOtherHours(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveOtherHours()));
        monthly.setAttmLeaveOtherDays(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmLeaveOtherDays()));

        monthly.setAttmLeaveHours(BigDecimalUtils.convertToZeroIfNull(monthly.getAttmLeaveHours()));
        monthly.setAttmLeaveDays(BigDecimalUtils.convertToZeroIfNull(monthly.getAttmLeaveDays()));

        monthly.setAttmOtHolidayHours(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmOtHolidayHours()));

        monthly.setAttmOtNormalHours(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmOtNormalHours()));

        monthly.setAttmOtWeekendHours(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmOtWeekendHours()));

        monthly.setAttmOvertimeHours(BigDecimalUtils.convertToZeroIfNull(monthly
                .getAttmOvertimeHours()));
    }

    public static BigDecimal calculateLeaveTotalDays(Attendmonthly monthly) {
        BigDecimal attmLeaveDays = new BigDecimal(0);
        attmLeaveDays = attmLeaveDays.add(monthly.getAttmLeaveAnnualDays());
        attmLeaveDays = attmLeaveDays.add(monthly.getAttmLeaveTiaoxiuDays());
        attmLeaveDays = attmLeaveDays.add(monthly.getAttmLeaveTiaoxiu01Days());
        attmLeaveDays = attmLeaveDays.add(monthly.getAttmLeaveCasualDays());
        attmLeaveDays = attmLeaveDays.add(monthly.getAttmLeaveMaternityDays());
        attmLeaveDays = attmLeaveDays.add(monthly.getAttmLeaveSickDays());
        attmLeaveDays = attmLeaveDays.add(monthly.getAttmLeaveSick01Days());
        attmLeaveDays = attmLeaveDays.add(monthly.getAttmLeaveSick02Days());
        attmLeaveDays = attmLeaveDays.add(monthly.getAttmLeaveTravelDays());
        attmLeaveDays = attmLeaveDays.add(monthly.getAttmLeaveOuterDays());
        attmLeaveDays = attmLeaveDays.add(monthly.getAttmLeaveWeddingDays());
        attmLeaveDays = attmLeaveDays.add(monthly.getAttmLeaveOtherDays());
        return attmLeaveDays;
    }

    public static BigDecimal calculateLeaveTotalHours(Attendmonthly monthly) {
        BigDecimal attmLeaveHours = new BigDecimal(0);
        attmLeaveHours = attmLeaveHours.add(monthly.getAttmLeaveAnnualHours());
        attmLeaveHours = attmLeaveHours.add(monthly.getAttmLeaveTiaoxiuHours());
        attmLeaveHours = attmLeaveHours.add(monthly.getAttmLeaveTiaoxiu01Hours());
        attmLeaveHours = attmLeaveHours.add(monthly.getAttmLeaveCasualHours());
        attmLeaveHours = attmLeaveHours.add(monthly.getAttmLeaveMaternityHours());
        attmLeaveHours = attmLeaveHours.add(monthly.getAttmLeaveSickHours());
        attmLeaveHours = attmLeaveHours.add(monthly.getAttmLeaveSick01Hours());
        attmLeaveHours = attmLeaveHours.add(monthly.getAttmLeaveSick02Hours());
        attmLeaveHours = attmLeaveHours.add(monthly.getAttmLeaveTravelHours());
        attmLeaveHours = attmLeaveHours.add(monthly.getAttmLeaveOuterHours());
        attmLeaveHours = attmLeaveHours.add(monthly.getAttmLeaveWeddingHours());
        attmLeaveHours = attmLeaveHours.add(monthly.getAttmLeaveOtherHours());
        return attmLeaveHours;
    }

    public static BigDecimal calculateOtTotalHours(Attendmonthly monthly) {
        BigDecimal attmOvertimeHours = new BigDecimal(0);
        attmOvertimeHours = attmOvertimeHours.add(monthly.getAttmOtHolidayHours());
        attmOvertimeHours = attmOvertimeHours.add(monthly.getAttmOtNormalHours());
        attmOvertimeHours = attmOvertimeHours.add(monthly.getAttmOtWeekendHours());
        return attmOvertimeHours;
    }

    public static Attendmonthly createAttendMonthly() {
        Attendmonthly m = new Attendmonthly();
        initAttendMonthlyProperties(m);
        return m;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.support.AttendmonthlyFactory JD-Core Version: 0.5.4
 */