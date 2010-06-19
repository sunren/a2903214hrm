package com.hr.report.bo;

import com.hr.report.dao.IReportDAO;
import com.hr.report.domain.ReportSetsDef;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class CustomizeReportSetsDefBoImpl implements CustomizeReportSetsDefBo {
    private IReportDAO reportDAO;

    public List<String> deleteReportSetsDef(String reportSetsDefId) {
        return null;
    }

    public List getAll() {
        return null;
    }

    public List getObjects(Class clas, String[] fetch) {
        return null;
    }

    public List getReportSetsDefByTableName(String tableName) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportSetsDef.class);
        detachedCriteria.add(Restrictions.eq("reportSetsDefTable", tableName));
        return this.reportDAO.findByCriteria(detachedCriteria);
    }

    public List<String> saveReportSetsDef(ReportSetsDef reportSetsDef) {
        return null;
    }

    public List<String> updateReportSetsDef(ReportSetsDef reportSetsDef) {
        return null;
    }

    public List getReportSetsDefByModuleAndTableName(Integer moduleId, String tableName,
            Integer flag) {
        List list = null;

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportSetsDef.class);

        detachedCriteria.add(Restrictions.eq("reportSetsDefModule", moduleId));
        if (StringUtils.isNotEmpty(tableName)) {
            detachedCriteria.add(Restrictions.eq("reportSetsDefTable", tableName));
        }
        detachedCriteria.add(Restrictions.eq("reportSetsDefFlag", flag));

        list = this.reportDAO.findByCriteria(detachedCriteria);
        return list;
    }

    public IReportDAO getReportDAO() {
        return this.reportDAO;
    }

    public void setReportDAO(IReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

    public ReportSetsDef getReportSetsDef(String fieldName) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportSetsDef.class);

        detachedCriteria.add(Restrictions.eq("reportSetsDefField", fieldName));
        List list = this.reportDAO.findByCriteria(detachedCriteria);
        return (ReportSetsDef) list.get(0);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.bo.CustomizeReportSetsDefBoImpl JD-Core Version: 0.5.4
 */