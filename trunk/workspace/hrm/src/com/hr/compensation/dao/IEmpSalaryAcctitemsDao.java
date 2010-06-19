package com.hr.compensation.dao;

import com.hr.hibernate.IHibernateUtil;

public abstract interface IEmpSalaryAcctitemsDao extends IHibernateUtil {
    public abstract <E> E loadObjectByEsaId(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.dao.IEmpSalaryAcctitemsDao JD-Core Version: 0.5.4
 */