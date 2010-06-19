package com.hr.report.dao;

import com.hr.hibernate.HibernateUtil;
import java.util.List;

public class ReportDAOImpl extends HibernateUtil implements IReportDAO {
    public List subEmpNos(String no, Integer status) {
        String stat = " and empStatus=" + status + " ";
        return exeHqlList("select id from Employee where id!=empSupNo.id " + stat + " and id != '"
                + no.trim() + "' and empSupNo.id='" + no.trim() + "'");
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.dao.ReportDAOImpl JD-Core Version: 0.5.4
 */