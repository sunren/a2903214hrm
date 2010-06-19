package com.hr.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateUtil {
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static final SimpleDateFormat DEFAULT_DATE_TIME_FROMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public static final int[] precisionArr = { 1, 2, 5, 11, 12, 13, 14 };
    public static final int Year = 0;
    public static final int Month = 1;
    public static final int Day_of_Month = 2;
    public static final int Hour_of_Day = 3;
    public static final int Minute = 4;
    public static final int Second = 5;
    public static final String Attd_Date_Format = "yyyyMMddHHmmss";

    public static String formatTodayDate() {
        String sDate = DEFAULT_DATE_FORMAT.format(new Date());
        return sDate;
    }

    public static String formatTodayDateTime() {
        String sDate = DEFAULT_DATE_TIME_FROMAT.format(new Date());
        return sDate;
    }

    public static String formatTodayToS(String strFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
        String sDate = sdf.format(new Date());
        return sDate;
    }

    public static String formatDate(Date myDate) {
        String sDate = DEFAULT_DATE_FORMAT.format(myDate);
        return sDate;
    }

    public static String formatDateTime(Date myDate) {
        String sDate = DEFAULT_DATE_TIME_FROMAT.format(myDate);
        return sDate;
    }

    public static String formatDateToS(String strDate, String strFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
        String sDate = sdf.format(sdf.parse(strDate));
        return sDate;
    }

    public static String formatDateToS(Date myDate, String strFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
        String sDate = sdf.format(myDate);
        return sDate;
    }

    public static String formatDateByApm(Date date, Integer[] apm) {
        if ((apm == null) || (apm.length == 0) || (apm[0] == null))
            return formatDateToS(date, "yyyy-MM-dd HH:mm");
        if (apm[0].intValue() == 0) {
            return formatDateToS(date, "yyyy-MM-dd") + " 上午";
        }
        return formatDateToS(date, "yyyy-MM-dd") + " 下午";
    }

    public static Date parseDateByFormat(String dateStr, String strFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Calendar parseDateTime(String baseDate) {
        Calendar cal = new GregorianCalendar();
        int yy = Integer.parseInt(baseDate.substring(0, 4));
        int mm = Integer.parseInt(baseDate.substring(5, 7)) - 1;
        int dd = Integer.parseInt(baseDate.substring(8, 10));
        int hh = 0;
        int mi = 0;
        int ss = 0;
        if (baseDate.length() > 12) {
            hh = Integer.parseInt(baseDate.substring(11, 13));
        }
        if (baseDate.length() > 15) {
            mi = Integer.parseInt(baseDate.substring(14, 16));
        }
        if (baseDate.length() > 18) {
            ss = Integer.parseInt(baseDate.substring(17, 19));
        }
        cal.set(yy, mm, dd, hh, mi, ss);
        cal.set(14, 0);
        return cal;
    }

    public static Calendar parseDateTimeToC(Date baseDate, int hh, int mi, int ss) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(baseDate);
        cal.set(11, hh);
        cal.set(12, mi);
        cal.set(13, ss);
        cal.set(14, 0);
        return cal;
    }

    public static Date parseDateTimeToD(Date baseDate, int hh, int mi, int ss) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(baseDate);
        cal.set(11, hh);
        cal.set(12, mi);
        cal.set(13, ss);
        cal.set(14, 0);
        return cal.getTime();
    }

    /** @deprecated */
    public static Date convDateTimeToHour1(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(13, 0);
        cal.set(14, 0);

        if (cal.get(12) < 15)
            cal.set(12, 0);
        else if (cal.get(12) < 45)
            cal.set(12, 30);
        else
            cal.set(12, 60);
        return cal.getTime();
    }

    public static Date convDateTimeToDate(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static String convDateToYM(Date date) {
        return formatDateToS(date, "yyyyMM");
    }

    public static Date convYMToMonFirst(String yearMonth) {
        Calendar cal = new GregorianCalendar();
        int year = Integer.valueOf(yearMonth.substring(0, 4)).intValue();
        int month = Integer.valueOf(yearMonth.substring(4)).intValue();
        cal.set(year, month - 1, 1, 0, 0, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date convYMToNextMonFirst(String yearMonth) {
        Calendar cal = new GregorianCalendar();
        int year = Integer.valueOf(yearMonth.substring(0, 4)).intValue();
        int month = Integer.valueOf(yearMonth.substring(4)).intValue();
        cal.set(year, month, 1, 0, 0, 0);
        return cal.getTime();
    }

    public static Date convYMToMonEnd(String yearMonth) {
        Calendar cal = new GregorianCalendar();
        int year = Integer.valueOf(yearMonth.substring(0, 4)).intValue();
        int month = Integer.valueOf(yearMonth.substring(4)).intValue();
        cal.set(year, month, 0, 0, 0, 0);
        return cal.getTime();
    }

    public static String yearMonthAdd(String yearMonth, int iCount, int iType) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(convYMToMonFirst(yearMonth));
        cal.add(iType, iCount);
        return convDateToYM(cal.getTime());
    }

    public static Calendar convDateTimeToDateCal(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal;
    }

    public static int getYear(String strDate) {
        Calendar cal = parseDateTime(strDate);
        return cal.get(1);
    }

    public static int getYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        return c.get(1);
    }

    public static int getMonth(String strDate) {
        Calendar cal = parseDateTime(strDate);
        return cal.get(2) + 1;
    }

    public static int getMonth(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        return c.get(2) + 1;
    }

    public static int getDay(String strDate) {
        Calendar cal = parseDateTime(strDate);
        return cal.get(5);
    }

    public static int getDay(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        return c.get(5);
    }

    public static int getHour(String strDate) {
        Calendar cal = parseDateTime(strDate);
        return cal.get(11);
    }

    public static int getHour(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        return c.get(11);
    }

    public static int getMinute(String strDate) {
        Calendar cal = parseDateTime(strDate);
        return cal.get(12);
    }

    public static int getMinute(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        return c.get(12);
    }

    public static int getWeekDay(String strDate) {
        Calendar cal = parseDateTime(strDate);
        return cal.get(7);
    }

    public static int getWeekDay(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        return c.get(7);
    }

    public static String getWeekDayName(String strDate) {
        String[] mName = { "旄1�7", "丄1�7", "亄1�7", "丄1�7", "囄1�7", "亄1�7", "兄1�7" };
        int iWeek = getWeekDay(strDate);
        iWeek -= 1;
        return "星期" + mName[iWeek];
    }

    public static String getWeekDayName(Date date) {
        String[] mName = { "旄1�7", "丄1�7", "亄1�7", "丄1�7", "囄1�7", "亄1�7", "兄1�7" };
        int iWeek = getWeekDay(date);
        iWeek -= 1;
        return "星期" + mName[iWeek];
    }

    public static String dateTimeAdd(String strDate, int iCount, int iType) {
        Calendar cal = parseDateTime(strDate);
        cal.add(iType, iCount);
        SimpleDateFormat sdf = null;
        if ((iType == 1) || (iType == 2) || (iType == 5))
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        else
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sDate = sdf.format(cal.getTime());
        return sDate;
    }

    public static Date dateTimeAdd(Date date, int iCount, int iType) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(iType, iCount);
        return cal.getTime();
    }

    public static Date dateAdd(Date date, int iCount) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(5, iCount);
        return cal.getTime();
    }

    public static int dateDiff(String strDateBegin, String strDateEnd, int iType) {
        Calendar calBegin = parseDateTime(strDateBegin);
        Calendar calEnd = parseDateTime(strDateEnd);
        long lBegin = calBegin.getTimeInMillis();
        long lEnd = calEnd.getTimeInMillis();
        if (iType == 13)
            return (int) ((lEnd - lBegin) / 1000L);
        if (iType == 12)
            return (int) ((lEnd - lBegin) / 60000L);
        if (iType == 10)
            return (int) ((lEnd - lBegin) / 3600000L);
        if (iType == 5) {
            return (int) ((lEnd - lBegin) / 86400000L);
        }
        return -1;
    }

    public static int dateDiff(Date dateBegin, Date dateEnd, int iType) {
        Calendar calBegin = new GregorianCalendar();
        calBegin.setTime(dateBegin);
        Calendar calEnd = new GregorianCalendar();
        calEnd.setTime(dateEnd);
        long lBegin = calBegin.getTimeInMillis();
        long lEnd = calEnd.getTimeInMillis();
        if (iType == 13)
            return (int) ((lEnd - lBegin) / 1000L);
        if (iType == 12)
            return (int) ((lEnd - lBegin) / 60000L);
        if (iType == 10)
            return (int) ((lEnd - lBegin) / 3600000L);
        if (iType == 5) {
            return (int) ((lEnd - lBegin) / 86400000L);
        }
        return -1;
    }

    public static boolean isDateRemind(Date dateToRemind, Date dateCurrent, int days, int cMode) {
        if (cMode <= -1) {
            int dayDiff = dateDiff(dateCurrent, dateToRemind, 5);

            return dayDiff <= days;
        }

        if (cMode == 0) {
            int dayDiff = dateDiff(dateCurrent, dateToRemind, 5);

            return (dayDiff <= days) && (dayDiff >= 0);
        }

        int yearDiffRate = (getYear(dateCurrent) - getYear(dateToRemind)) / cMode;
        Date newDateToRemind = dateTimeAdd(dateToRemind, yearDiffRate * cMode, 1);

        if (dateDiff(newDateToRemind, dateCurrent, 5) > 0) {
            newDateToRemind = dateTimeAdd(newDateToRemind, cMode, 1);
        }
        int dayDiff = dateDiff(dateCurrent, newDateToRemind, 5);

        return (dayDiff <= days) && (dayDiff >= 0);
    }

    public static boolean isLeapYear(int yearNum) {
        boolean isLeep = false;

        if ((yearNum % 4 == 0) && (yearNum % 100 != 0))
            isLeep = true;
        else if (yearNum % 400 == 0)
            isLeep = true;
        else {
            isLeep = false;
        }
        return isLeep;
    }

    public static int getWeekNumOfYear() {
        Calendar calendar = Calendar.getInstance();
        int iWeekNum = calendar.get(3);
        return iWeekNum;
    }

    public static int getWeekNumOfYearDay(String strDate) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = format.parse(strDate);
        calendar.setTime(curDate);
        int iWeekNum = calendar.get(3);
        return iWeekNum;
    }

    public static int getWeekNumOfYearDay(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int iWeekNum = calendar.get(3);
        return iWeekNum;
    }

    public static String getYearWeekFirstDay(int yearNum, int weekNum) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.set(1, yearNum);
        cal.set(3, weekNum);
        cal.set(7, 2);

        String tempYear = Integer.toString(yearNum);
        String tempMonth = Integer.toString(cal.get(2) + 1);
        String tempDay = Integer.toString(cal.get(5));
        String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
        return formatDateToS(tempDate, "yyyy-MM-dd");
    }

    public static String getYearWeekEndDay(int yearNum, int weekNum) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.set(1, yearNum);
        cal.set(3, weekNum + 1);
        cal.set(7, 1);

        String tempYear = Integer.toString(yearNum);
        String tempMonth = Integer.toString(cal.get(2) + 1);
        String tempDay = Integer.toString(cal.get(5));
        String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
        return formatDateToS(tempDate, "yyyy-MM-dd");
    }

    public static Date getYearMonthFirstDay(int yearNum, int monthNum) {
        Calendar cal = new GregorianCalendar();
        cal.set(yearNum, monthNum - 1, 1, 0, 0, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getNextYearMonthFirstDay(int yearNum, int monthNum) {
        Calendar cal = new GregorianCalendar();
        cal.set(yearNum, monthNum, 1, 0, 0, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getYearMonthEndDay(int yearNum, int monthNum) {
        Calendar cal = new GregorianCalendar();
        cal.set(yearNum, monthNum, 0, 0, 0, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getYearMonthFirstDay(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(5, 1);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getNextYearMonthFirstDay(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(2, 1);
        cal.set(5, 1);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getYearMonthEndDay(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(5, cal.getActualMaximum(5));
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getYearFirstDay(int yearNum) {
        Calendar cal = new GregorianCalendar();
        cal.set(yearNum, 0, 1, 0, 0, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getNextYearFirstDay(int yearNum) {
        Calendar cal = new GregorianCalendar();
        cal.set(yearNum, 12, 1, 0, 0, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getYearEndDay(int yearNum) {
        Calendar cal = new GregorianCalendar();
        cal.set(yearNum, 12, 0, 0, 0, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static String getWeek(String strDate, int weekNum) {
        Calendar c = parseDateTime(strDate);
        if (weekNum == 1)
            c.set(7, 2);
        else if (weekNum == 2)
            c.set(7, 3);
        else if (weekNum == 3)
            c.set(7, 4);
        else if (weekNum == 4)
            c.set(7, 5);
        else if (weekNum == 5)
            c.set(7, 6);
        else if (weekNum == 6)
            c.set(7, 7);
        else if (weekNum == 0)
            c.set(7, 1);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    public static Date getWeek(Date date, int weekNum) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        if (weekNum == 1)
            c.set(7, 2);
        else if (weekNum == 2)
            c.set(7, 3);
        else if (weekNum == 3)
            c.set(7, 4);
        else if (weekNum == 4)
            c.set(7, 5);
        else if (weekNum == 5)
            c.set(7, 6);
        else if (weekNum == 6)
            c.set(7, 7);
        else if (weekNum == 0)
            c.set(7, 1);
        return c.getTime();
    }

    public static int getDaysBetween(Date startDate, Date endDate) {
        Calendar d1 = new GregorianCalendar();
        d1 = convDateTimeToDateCal(startDate);
        Calendar d2 = new GregorianCalendar();
        d2 = convDateTimeToDateCal(endDate);

        if (d1.after(d2)) {
            Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        int days = d2.get(6) - d1.get(6);
        int y2 = d2.get(1);
        if (d1.get(1) != y2) {
            d1 = (Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(6);
                d1.add(1, 1);
            } while (d1.get(1) != y2);
        }
        return days;
    }

    public static Date getNextMonday(Date date) {
        Calendar result = new GregorianCalendar();
        result.setTime(date);
        do
            result.add(5, 1);
        while (result.get(7) != 2);
        return result.getTime();
    }

    public static int getWorkingDay(Date startDate, Date endDate) {
        if (startDate.after(endDate)) {
            Date swap = startDate;
            startDate = endDate;
            endDate = swap;
        }

        int actualDays = dateDiff(startDate, endDate, 5) + 1;
        int weekDays = actualDays / 7 * 5;
        startDate = dateAdd(startDate, actualDays / 7 * 7);

        int count = 0;
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(startDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(endDate);
        for (int i = 0; !cal1.after(cal2); ++i) {
            if ((cal1.get(7) != 7) && (cal1.get(7) != 1)) {
                ++count;
            }
            cal1.add(5, 1);
        }

        return weekDays + count;
    }

    public static int compareTwoDay(Date date1, Date date2) {
        if ((date1 == null) || (date2 == null)) {
            return -2;
        }
        date1 = convDateTimeToDate(date1);
        date2 = convDateTimeToDate(date2);

        if (date1.compareTo(date2) < 0)
            return 1;
        if (date1.compareTo(date2) == 0) {
            return 0;
        }
        return -1;
    }

    public static Calendar getFirstDayInMonth(Calendar now) {
        Calendar tmp = (Calendar) now.clone();
        int day = tmp.get(5);
        tmp.add(5, 1 - day);
        return tmp;
    }

    public static Calendar getFirstDayInQuartz(Calendar now) {
        Calendar tmp = (Calendar) now.clone();
        int day = tmp.get(5);
        tmp.add(5, 1 - day);
        int month = tmp.get(2);
        tmp.add(2, 0 - month % 3);
        return tmp;
    }

    public static Calendar getFirstDayInHalfYear(Calendar now) {
        Calendar tmp = (Calendar) now.clone();
        int day = tmp.get(5);
        tmp.add(5, 1 - day);
        int month = tmp.get(2);
        tmp.add(2, 0 - month % 6);
        return tmp;
    }

    public static Calendar getFirstDayInYear(Calendar now) {
        Calendar tmp = (Calendar) now.clone();
        int day = tmp.get(5);
        tmp.add(5, 1 - day);
        tmp.set(2, 0);
        return tmp;
    }

    public static Calendar getLastDayInMonth(Calendar now) {
        Calendar tmp = (Calendar) now.clone();
        tmp.set(5, tmp.getActualMaximum(5));
        return tmp;
    }

    public static Calendar getLastDayInQuartz(Calendar now) {
        Calendar tmp = getFirstDayInQuartz(now);
        tmp.add(2, 3);
        tmp.add(5, -1);
        return tmp;
    }

    public static Calendar getLastDayInHalfYear(Calendar now) {
        Calendar tmp = getFirstDayInHalfYear(now);
        tmp.add(2, 6);
        tmp.add(5, -1);
        return tmp;
    }

    public static Calendar getLastDayInYear(Calendar now) {
        Calendar tmp = getFirstDayInYear(now);
        tmp.add(2, 12);
        tmp.add(5, -1);
        return tmp;
    }

    public static boolean hasConflict(List<Date> dList1, List<Date> dList2) {
        int length1 = dList1.size();
        int length2 = dList2.size();

        if ((length1 == 0) || (length2 == 0)) {
            return true;
        }
        int p1 = 0;
        int p2 = 0;
        Date d11 = (Date) dList1.get(p1++);
        Date d12 = (Date) dList1.get(p1++);
        Date d21 = (Date) dList2.get(p2++);
        Date d22 = (Date) dList2.get(p2++);
        while (true) {
            int result = hasConflict(d11, d12, d21, d22);
            if (result == -1) {
                if (p1 >= length1) {
                    return true;
                }
                d11 = (Date) dList1.get(p1++);
                d12 = (Date) dList1.get(p1++);
            } else if (result == 1) {
                if (p2 >= length2) {
                    return true;
                }
                d21 = (Date) dList2.get(p2++);
                d22 = (Date) dList2.get(p2++);
            } else {
                return false;
            }
        }
    }

    public static int hasConflict(List<Date> dList1, Date dateBgn, Date dateEnd) {
        int length1 = dList1.size();

        if (length1 == 0) {
            return 0;
        }
        for (int i = 0; i < length1;) {
            Date d1 = (Date) dList1.get(i++);
            Date d2 = (Date) dList1.get(i++);
            int result = hasConflict(dateBgn, dateEnd, d1, d2);
            if (result == -1) {
                return 0;
            }
            if (result == 0) {
                if ((isEqual(dateBgn, d1)) && (isEqual(dateEnd, d2))) {
                    return 1;
                }
                return -1;
            }
        }
        return 0;
    }

    public static int addDate(List<Date> dList1, Date dateBgn, Date dateEnd) {
        int length1 = dList1.size();
        for (int i = 0; i < length1;) {
            Date d1 = (Date) dList1.get(i++);
            Date d2 = (Date) dList1.get(i++);
            int result = hasConflict(dateBgn, dateEnd, d1, d2);
            if (result == -1) {
                i -= 2;
                dList1.add(i, dateEnd);
                dList1.add(i, dateBgn);
                return i;
            }
            if (result == 0) {
                if ((isEqual(dateBgn, d1)) && (isEqual(dateEnd, d2))) {
                    return -2;
                }
                return -1;
            }
        }
        dList1.add(dateBgn);
        dList1.add(dateEnd);
        return length1;
    }

    public static int hasConflict(Date d1Bgn, Date d1End, Date d2Bgn, Date d2End) {
        if (compareTwoDay(d1Bgn, d2End) == -1) {
            return 1;
        }
        if (compareTwoDay(d2Bgn, d1End) == -1) {
            return -1;
        }
        return 0;
    }

    public static List<Date> getIntervalByMonth(Date startDate, Date endDate) {
        if (startDate.compareTo(endDate) > 0) {
            return null;
        }
        List dateList = new ArrayList();
        Calendar start = new GregorianCalendar();
        start.setTime(startDate);
        start = formatCalendar(start, "0");
        Calendar end = new GregorianCalendar();
        end.setTime(endDate);
        end = formatCalendar(end, "0");
        while (start.compareTo(end) <= 0) {
            dateList.add(start.getTime());
            start.set(2, start.get(2) + 1);
        }
        return dateList;
    }

    public static List<Date> getIntervalByYear(Date startDate, Date endDate) {
        if (startDate.compareTo(endDate) > 0) {
            return null;
        }
        List dateList = new ArrayList();
        Calendar start = new GregorianCalendar();
        start.setTime(startDate);
        start = formatCalendar(start, "1");
        Calendar end = new GregorianCalendar();
        end.setTime(endDate);
        end = formatCalendar(end, "1");
        while (start.compareTo(end) <= 0) {
            dateList.add(start.getTime());
            start.set(1, start.get(1) + 1);
        }
        return dateList;
    }

    public static Calendar formatCalendar(Calendar cal, String format) {
        if (cal == null)
            return null;
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        if (format.equals("0")) {
            cal.set(5, 1);
        } else if (format.equals("1")) {
            cal.set(5, 1);
            cal.set(2, 0);
        }
        return cal;
    }

    private static boolean isEqual(Date d1, Date d2) {
        if ((d1 == null) && (d2 == null)) {
            return true;
        }

        return compareTwoDay(d1, d2) == 0;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name: com.hr.util.DateUtil
 * JD-Core Version: 0.5.4
 */