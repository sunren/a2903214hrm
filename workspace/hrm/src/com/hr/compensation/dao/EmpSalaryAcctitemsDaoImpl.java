package com.hr.compensation.dao;

import com.hr.hibernate.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

public class EmpSalaryAcctitemsDaoImpl extends HibernateUtil implements IEmpSalaryAcctitemsDao {
    public <E> E loadObjectByEsaId(String hql) {
        Session session = getSession();
        Query query = session.createQuery(hql);
        return (E) query.uniqueResult();
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.dao.EmpSalaryAcctitemsDaoImpl JD-Core Version: 0.5.4
 */