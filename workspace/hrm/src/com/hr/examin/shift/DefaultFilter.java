package com.hr.examin.shift;

import com.hr.examin.support.AttendDailyMemory;
import java.math.BigDecimal;

public class DefaultFilter implements ExceptionDataFilter {
    BigDecimal zero;

    public DefaultFilter() {
        this.zero = new BigDecimal(0);
    }

    public boolean doFilter(AttendDailyMemory memory, Integer searchType) {
        boolean flag = false;
        if ((searchType == null) || (searchType.intValue() == 0))
            flag = true;
        else if (searchType.intValue() == 1) {
            if ((memory != null) && (memory.getComments() != null)
                    && (!memory.getComments().trim().equals("")))
                flag = false;
            else
                flag = true;
        } else if (searchType.intValue() == 2) {
            if ((memory != null) && (memory.getComments() != null)
                    && (!memory.getComments().trim().equals("")))
                flag = true;
        } else if (searchType.intValue() == 3) {
            if ((memory != null) && (memory.getLateMinutes() != null)
                    && (memory.getLateMinutes().compareTo(this.zero) > 0))
                flag = true;
        } else if (searchType.intValue() == 4) {
            if ((memory != null) && (memory.getEarlyMinutes() != null)
                    && (memory.getEarlyMinutes().compareTo(this.zero) > 0))
                flag = true;
        } else if (searchType.intValue() == 5) {
            if ((memory != null) && (memory.getAbsentHours() != null)
                    && (memory.getAbsentHours().compareTo(this.zero) > 0))
                flag = true;
        } else if (searchType.intValue() == 6) {
            if ((memory != null) && (memory.getLeaveHours() != null)
                    && (memory.getLeaveHours().compareTo(this.zero) > 0))
                flag = true;
        } else if (searchType.intValue() == 7) {
            if ((memory != null) && (memory.getOvertimeHours() != null)
                    && (memory.getOvertimeHours().compareTo(this.zero) > 0))
                flag = true;
        } else if ((searchType.intValue() == 8) && (memory != null)
                && (memory.getComments() != null) && (memory.getComments().indexOf("刷卡") > -1)) {
            flag = true;
        }

        return flag;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.shift.DefaultFilter JD-Core Version: 0.5.4
 */