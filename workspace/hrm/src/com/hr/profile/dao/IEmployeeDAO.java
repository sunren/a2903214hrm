package com.hr.profile.dao;

import com.hr.hibernate.IHibernateUtil;
import java.util.List;

public abstract interface IEmployeeDAO extends IHibernateUtil {
    public abstract List subEmpNos(String paramString, Integer paramInteger);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.dao.IEmployeeDAO JD-Core Version: 0.5.4
 */