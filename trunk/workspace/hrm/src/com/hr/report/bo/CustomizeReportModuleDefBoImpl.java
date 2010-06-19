package com.hr.report.bo;

import com.hr.report.dao.IReportDAO;
import com.hr.report.domain.ReportModuleDef;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class CustomizeReportModuleDefBoImpl implements CustomizeReportModuleDefBo {
    private IReportDAO reportDAO;

    public List list(String moduleId, String flag) {
        Integer id = Integer.valueOf(moduleId);
        Integer f = Integer.valueOf(flag);
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportModuleDef.class);
        detachedCriteria.add(Restrictions.eq("reportModuleDefModule", id));
        if ((f.intValue() == 1) || (f.intValue() == 0)) {
            detachedCriteria.add(Restrictions.eq("reportModuleDefFlag", f));
        }
        detachedCriteria.addOrder(Order.asc("reportModuleDefFlag"));
        return this.reportDAO.findByCriteria(detachedCriteria);
    }

    public IReportDAO getReportDAO() {
        return this.reportDAO;
    }

    public void setReportDAO(IReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

    public ReportModuleDef getReportModuleDef(String tableName) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportModuleDef.class);
        detachedCriteria.add(Restrictions.eq("reportModuleDefTable", tableName));
        return (ReportModuleDef) this.reportDAO.findByCriteria(detachedCriteria).get(0);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.bo.CustomizeReportModuleDefBoImpl JD-Core Version: 0.5.4
 */