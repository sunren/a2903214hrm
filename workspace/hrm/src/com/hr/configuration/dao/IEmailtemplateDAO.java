package com.hr.configuration.dao;

import com.hr.configuration.domain.Emailtemplate;
import com.hr.hibernate.IHibernateUtil;

public abstract interface IEmailtemplateDAO extends IHibernateUtil {
    public abstract Emailtemplate loadOne(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.dao.IEmailtemplateDAO JD-Core Version: 0.5.4
 */