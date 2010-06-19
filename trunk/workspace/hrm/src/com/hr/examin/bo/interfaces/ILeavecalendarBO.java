package com.hr.examin.bo.interfaces;

import com.hr.configuration.domain.Location;
import com.hr.examin.domain.Leavecalendar;
import java.util.Date;
import java.util.List;

public abstract interface ILeavecalendarBO {
    public abstract int isACalendarDay(Date paramDate, Location paramLocation);

    public abstract Leavecalendar loadLeavecalendar(String paramString);

    public abstract List<String> insertLeavecalendar(Leavecalendar paramLeavecalendar);

    public abstract List<String> deleteLeavecalendar(String paramString);

    public abstract List<String> updateLeavecalendar(Leavecalendar paramLeavecalendar);

    public abstract List<Leavecalendar> getCalendarByYear(int paramInt);

    public abstract List<Leavecalendar> getCalendarListByDay(Date paramDate1, Date paramDate2,
            Location paramLocation);

    public abstract List<Leavecalendar> getCalendarListByDay(Date paramDate1, Date paramDate2);

    public abstract int getWorkDays(Date paramDate1, Date paramDate2,
            List<Leavecalendar> paramList, Location paramLocation);

    public abstract int isACalendarDay(Date paramDate, List<Leavecalendar> paramList,
            Location paramLocation);

    public abstract int getWorkingDaysByLocal(Date paramDate1, Date paramDate2,
            Location paramLocation);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.bo.interfaces.ILeavecalendarBO JD-Core Version: 0.5.4
 */