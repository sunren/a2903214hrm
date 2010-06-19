package com.hr.configuration.dao;

import com.hr.configuration.domain.Emailtemplate;
import com.hr.hibernate.HibernateUtil;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class EmailtemplateDAOImpl extends HibernateUtil implements IEmailtemplateDAO {
    public Emailtemplate loadOne(String oneId) {
        return (Emailtemplate) getHibernateTemplate().get(Emailtemplate.class, oneId);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.dao.EmailtemplateDAOImpl JD-Core Version: 0.5.4
 */