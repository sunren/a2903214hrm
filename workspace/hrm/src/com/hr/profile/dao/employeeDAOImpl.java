package com.hr.profile.dao;

import com.hr.hibernate.HibernateUtil;
import java.util.List;

public class employeeDAOImpl extends HibernateUtil implements IEmployeeDAO {
    public List subEmpNos(String no, Integer status) {
        String stat = " and empStatus=" + status + " ";
        return exeHqlList("select id from Employee where id!=empSupNo.id " + stat + " and id != '"
                + no.trim() + "' and empSupNo.id='" + no.trim() + "'");
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.dao.employeeDAOImpl JD-Core Version: 0.5.4
 */