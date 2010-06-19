package com.hr.report.dao;

import com.hr.hibernate.IHibernateUtil;
import java.util.List;

public abstract interface IReportDAO extends IHibernateUtil {
    public abstract List subEmpNos(String paramString, Integer paramInteger);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.dao.IReportDAO JD-Core Version: 0.5.4
 */