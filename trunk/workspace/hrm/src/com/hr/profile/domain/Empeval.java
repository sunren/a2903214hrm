package com.hr.profile.domain;

import com.hr.profile.domain.base.BaseEmpeval;
import com.hr.util.DateUtil;
import java.util.Calendar;
import java.util.Date;

public class Empeval extends BaseEmpeval {
    private static final long serialVersionUID = 1L;

    public Empeval() {
    }

    public Empeval(String eeId) {
        super(eeId);
    }

    public static Date[] getStartEndDate(String eeType, Date date) {
        Date[] result = new Date[2];
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (eeType.compareTo("月度") == 0) {
            result[0] = DateUtil.getFirstDayInMonth(cal).getTime();
            result[1] = DateUtil.getLastDayInMonth(cal).getTime();
        } else if (eeType.compareTo("季度") == 0) {
            result[0] = DateUtil.getFirstDayInQuartz(cal).getTime();
            result[1] = DateUtil.getLastDayInQuartz(cal).getTime();
        } else if (eeType.compareTo("半年") == 0) {
            result[0] = DateUtil.getFirstDayInHalfYear(cal).getTime();
            result[1] = DateUtil.getLastDayInHalfYear(cal).getTime();
        } else if (eeType.compareTo("全年") == 0) {
            result[0] = DateUtil.getFirstDayInYear(cal).getTime();
            result[1] = DateUtil.getLastDayInYear(cal).getTime();
        } else {
            result[0] = null;
            result[1] = null;
        }
        return result;
    }

    public Date[] getStartEndDate(Date date) {
        return getStartEndDate(getEeType(), date);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.domain.Empeval JD-Core Version: 0.5.4
 */