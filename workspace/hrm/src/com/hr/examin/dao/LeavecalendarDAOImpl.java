package com.hr.examin.dao;

import com.hr.examin.dao.interfaces.ILeavecalendarDAO;
import com.hr.examin.domain.Leavecalendar;
import com.hr.hibernate.HibernateUtil;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class LeavecalendarDAOImpl extends HibernateUtil implements ILeavecalendarDAO {
    public void updateLeavecalendar(Leavecalendar lc) {
        getHibernateTemplate().clear();

        updateObject(lc);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.dao.LeavecalendarDAOImpl JD-Core Version: 0.5.4
 */