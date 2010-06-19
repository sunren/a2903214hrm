package com.hr.examin.dao.interfaces;

import com.hr.examin.domain.Leavecalendar;
import com.hr.hibernate.IHibernateUtil;

public abstract interface ILeavecalendarDAO extends IHibernateUtil {
    public abstract void updateLeavecalendar(Leavecalendar paramLeavecalendar);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.dao.interfaces.ILeavecalendarDAO JD-Core Version: 0.5.4
 */