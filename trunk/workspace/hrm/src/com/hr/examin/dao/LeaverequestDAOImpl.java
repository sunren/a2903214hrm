package com.hr.examin.dao;

import com.hr.examin.dao.interfaces.ILeaverequestDAO;
import com.hr.hibernate.HibernateUtil;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class LeaverequestDAOImpl extends HibernateUtil implements ILeaverequestDAO {
    public void saveObjectOther(Object obj) {
        getHibernateTemplate().clear();
        saveObject(obj);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.dao.LeaverequestDAOImpl JD-Core Version: 0.5.4
 */