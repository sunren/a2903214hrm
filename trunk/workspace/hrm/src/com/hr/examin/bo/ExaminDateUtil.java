package com.hr.examin.bo;

import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.DateUtil;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

public class ExaminDateUtil {
    private static ExaminDateUtil dateUtil;

    public static ExaminDateUtil getInstance() {
        if (dateUtil == null) {
            dateUtil = new ExaminDateUtil();
        }
        return dateUtil;
    }

    public int getTime(Date startDate, Date endDate, String str, Date day) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);

        String[] s = str.split(",");
        int totalMinute = 0;
        if ((s != null) && (s.length > 0)) {
            for (int i = 0; i < s.length; ++i) {
                totalMinute += getActualTime(s[i], day, startDate, endDate);
            }
        }
        return totalMinute;
    }

    private int[] convertFromStringToInt(String[] str) {
        int[] temp = new int[str.length];
        for (int i = 0; i < str.length; ++i) {
            try {
                temp[i] = Integer.parseInt(str[i]);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("您在转换数组的第 " + (i + 1) + " 个元素的时�1�7�出错了");
                temp[i] = 0;
            }
        }
        return temp;
    }

    private int getActualTime(String timeArrange, Date day, Date startDate, Date endDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(14, 0);
        startDate = c.getTime();
        c.setTime(endDate);
        c.set(14, 0);
        endDate = c.getTime();

        String[] s = timeArrange.split("-");
        String[] sBegin = s[0].split(":");

        int[] begin = convertFromStringToInt(sBegin);
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(day);
        startCalendar.set(10, begin[0]);
        startCalendar.set(12, begin[1]);
        startCalendar.set(14, 0);
        Date start = startCalendar.getTime();
        startDate = setDate(startDate);

        String[] sEnd = s[1].split(":");

        int[] end = convertFromStringToInt(sEnd);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(day);
        if (end[0] < begin[0]) {
            endCalendar.add(6, 1);
        }
        endCalendar.set(10, end[0]);
        endCalendar.set(12, end[1]);
        endCalendar.set(14, 0);
        endDate = setDate(endDate);
        start = setDate(start);

        Date finish = endCalendar.getTime();
        finish = setDate(finish);

        int time = 0;
        if (endDate.compareTo(start) > 0) {
            if (endDate.compareTo(start) > 0) {
                if (endDate.compareTo(finish) < 0) {
                    if (startDate.compareTo(start) < 0) {
                        time = getTimeBetweenTwoDate(start, endDate);
                    } else {
                        time = getTimeBetweenTwoDate(startDate, endDate);
                    }

                } else if (startDate.compareTo(finish) >= 0)
                    time = 0;
                else if (startDate.compareTo(start) <= 0) {
                    time = getTimeBetweenTwoDate(start, finish);
                } else {
                    time = getTimeBetweenTwoDate(startDate, finish);
                }

            }

        }

        return time;
    }

    private int getTimeBetweenTwoDate(Date start, Date end) {
        long total = end.getTime() - start.getTime();
        int totalMinute = (int) (total / 60000L);
        return totalMinute;
    }

    public Date setDate(Date date) {
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date a = new Date();
        try {
            String str = sp.format(date);
            a = sp.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    public static int isTimeInShift(Date checkDT, Date empshiftDate, String attsSession) {
        Date[] shiftDT = getSplitDateByAttendShift(empshiftDate, attsSession);

        int segments = shiftDT.length / 2;

        for (int i = 0; i < segments; ++i) {
            int j = 2 * i;

            long diff1 = DateUtil.dateDiff(shiftDT[j], checkDT, 13);
            long diff2 = DateUtil.dateDiff(checkDT, shiftDT[(j + 1)], 13);

            if ((diff1 > 0L) && (diff2 > 0L))
                return 2;
            if ((diff1 == 0L) && (diff2 == 0L))
                return 4;
            if (diff1 == 0L)
                return 1;
            if (diff2 == 0L)
                return 3;

        }

        return 0;
    }

    public static int minutesInShift(Date checkDateTimeStart, Date checkDateTimeEnd,
            Date empshiftDate, String attsSession) {
        Date[] shiftDateTime = getSplitDateByAttendShift(empshiftDate, attsSession);

        int segments = shiftDateTime.length / 2;
        int totalMinutes = 0;

        for (int i = 0; i < segments; ++i) {
            int j = 2 * i;
            if (checkDateTimeStart.after(shiftDateTime[j])) {
                shiftDateTime[j] = checkDateTimeStart;
            }
            if (checkDateTimeEnd.before(shiftDateTime[(j + 1)])) {
                shiftDateTime[(j + 1)] = checkDateTimeEnd;
            }
            if (shiftDateTime[j].before(shiftDateTime[(j + 1)])) {
                totalMinutes += DateUtil.dateDiff(shiftDateTime[j], shiftDateTime[(j + 1)], 12);
            }
        }
        return totalMinutes;
    }

    public static Date[] getShiftArr(Date empshiftDate, String attsSession) {
        Date[] shiftDateTime = getSplitDateByAttendShift(empshiftDate, attsSession);
        Date[] resultArr = new Date[2];
        resultArr[0] = shiftDateTime[0];
        resultArr[1] = shiftDateTime[(shiftDateTime.length - 1)];
        return resultArr;
    }

    public static double getDefaultHoursPerDay() {
        String leaveMode = (String) DatabaseSysConfigManager.getInstance().getProperties()
                .get("sys.examin.leave.mode");
        if ((leaveMode == null) || (leaveMode.trim().equals(""))) {
            leaveMode = "day,8";
        }
        String[] str = leaveMode.split(",");
        if ((str.length == 0) || (str.length < 2) || (str[1] == null) || (str[1].trim().equals(""))) {
            return 8.0D;
        }
        double hour = 8.0D;
        try {
            hour = Double.parseDouble(str[1]);
        } catch (Exception e) {
            hour = 8.0D;
        }
        return hour;
    }

    public static Date[] getDateArray(int year, int month, String monthSum) {
        if ((monthSum == null) || (monthSum == "")) {
            monthSum = "1-1";
        }
        String[] monthSumArray = monthSum.split("-");
        Integer beginMonth = Integer.valueOf(Integer.parseInt(monthSumArray[0]));
        Integer beginDay = Integer.valueOf(Integer.parseInt(monthSumArray[1]));

        Calendar cal = new GregorianCalendar();
        if (beginMonth.intValue() == 1) {
            cal.set(year, month - 1, beginDay.intValue(), 0, 0, 0);
            cal.set(14, 0);
        } else {
            cal.set(year, month - 2, beginDay.intValue(), 0, 0, 0);
            cal.set(14, 0);
        }

        Date beginDate = cal.getTime();
        Date endDate = DateUtil.dateTimeAdd(cal.getTime(), 1, 2);
        endDate = DateUtil.dateAdd(endDate, -1);
        Date[] startEndDates = { beginDate, endDate };
        return startEndDates;
    }

    public static Date[] getDateArray(String yearmonth, String monthSum) {
        int year = Integer.parseInt(yearmonth.substring(0, 4));
        int month = Integer.parseInt(yearmonth.substring(4));
        return getDateArray(year, month, monthSum);
    }

    public static Date[] getDateArray(Date date, String monthSum) {
        int year = DateUtil.getYear(date);
        int month = DateUtil.getMonth(date);
        int day = DateUtil.getDay(date);
        Date beginDate = DateUtil.getYearMonthFirstDay(year, month);

        if (monthSum != null) {
            String[] monthSumArray = monthSum.split("-");
            if ("0".equals(monthSumArray[0])) {
                Integer dayOffset = Integer.valueOf(Integer.parseInt(monthSumArray[1]) - 1);
                beginDate = DateUtil.dateAdd(beginDate, dayOffset.intValue());

                if (day < dayOffset.intValue()) {
                    beginDate = DateUtil.dateTimeAdd(beginDate, -1, 2);
                }
            }
        }
        Date endDate = DateUtil.dateAdd(DateUtil.dateTimeAdd(beginDate, 1, 2), -1);
        Date[] startEndDates = { beginDate, endDate };
        return startEndDates;
    }

    public static Date[] getSplitDateByAttendShift(Date empShiftDate, String str) {
        String[] timeRange = str.split(":|-|,");
        Date[] shiftDateTime = new Date[timeRange.length / 2];
        for (int i = 0; i < timeRange.length / 2; ++i) {
            int hh = Integer.parseInt(timeRange[(2 * i)]);
            int mi = Integer.parseInt(timeRange[(2 * i + 1)]);
            shiftDateTime[i] = DateUtil.parseDateTimeToD(empShiftDate, hh, mi, 0);
        }
        return shiftDateTime;
    }

    public static BigDecimal getExaminDatePeriod(BigDecimal minutes, String config) {
        if ((minutes == null) || (minutes.compareTo(new BigDecimal(0)) == 0)) {
            return new BigDecimal(0);
        }
        String[] arrs = config.split(";");
        for (int i = arrs.length - 1; i >= 0; --i) {
            String[] period = arrs[i].split(",");
            if (period.length == 2) {
                BigDecimal from = new BigDecimal(period[0]);
                BigDecimal to = new BigDecimal(period[1]);
                if ((minutes.compareTo(from) > 0) && (minutes.compareTo(to) <= 0)) {
                    return new BigDecimal(i + 1);
                }
                if (minutes.compareTo(to) > 0) {
                    return new BigDecimal(i + 1);
                }
            }
        }

        return new BigDecimal(0);
    }

    public static BigDecimal convertHourToDay(BigDecimal number, String config, String dayConfig) {
        BigDecimal total = new BigDecimal(config.split(",")[0]);
        BigDecimal half = new BigDecimal(config.split(",")[1]);
        BigDecimal hourPerday = new BigDecimal(dayConfig.split(",")[1]);

        BigDecimal days = number.divide(hourPerday, 2, 4);
        BigDecimal quotient = days.divide(new BigDecimal(1), 0, 1);
        BigDecimal residual = days.subtract(quotient);

        if (residual.compareTo(total.divide(new BigDecimal(100))) >= 0)
            return quotient.add(new BigDecimal(1));
        if (residual.compareTo(half.divide(new BigDecimal(100))) >= 0) {
            return quotient.add(new BigDecimal(0.5D));
        }
        return quotient;
    }

    public static BigDecimal convertMinutesToHours(BigDecimal number, String config) {
        String[] configArr = config.split(",");
        long half = 0L;
        long one = 0L;
        one = Long.parseLong(configArr[0]);
        half = Long.parseLong(configArr[1]);
        long minus = number.longValue();

        double result = minus / 60L;
        long temp = minus % 60L;
        if ((half <= temp) && (temp < one))
            result += 0.5D;
        else if (one <= temp) {
            result += 1.0D;
        }
        return new BigDecimal(result);
    }

    public static boolean applyLRByDay(String leaveType, Integer empShiftType) {
        if (leaveType.equals("0")) {
            return (empShiftType.intValue() == 0) || (empShiftType.intValue() == 2);
        }

        return !leaveType.equals("1");
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.bo.ExaminDateUtil JD-Core Version: 0.5.4
 */