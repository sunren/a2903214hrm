package com.hr.report.bo;

import com.hr.report.dao.IReportDAO;
import com.hr.report.domain.ReportJoinDef;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class CustomizeReportJoinDefBoImpl implements CustomizeReportJoinDefBo {
    private IReportDAO reportDAO;

    public List getObjects(Class clas, String[] fetch) {
        return this.reportDAO.getObjects(clas, fetch);
    }

    public List loadAll() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportJoinDef.class);

        return this.reportDAO.findByCriteria(detachedCriteria);
    }

    public List findJoinDefListByMainTableName(String tableName) {
        List list = null;
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportJoinDef.class);

        detachedCriteria.add(Restrictions.eq("reportJoinDefMainTable", tableName));

        list = this.reportDAO.findByCriteria(detachedCriteria);
        return list;
    }

    public IReportDAO getReportDAO() {
        return this.reportDAO;
    }

    public void setReportDAO(IReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

    public ReportJoinDef findByMainTableAndAssistanceTable(String mainTable, String assistanceTable) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportJoinDef.class);

        detachedCriteria.add(Restrictions.eq("reportJoinDefMainTable", mainTable));

        detachedCriteria.add(Restrictions.eq("reportJoinDefAssistantTable", assistanceTable));

        List result = this.reportDAO.findByCriteria(detachedCriteria);
        if (!result.isEmpty()) {
            return (ReportJoinDef) result.get(0);
        }
        return null;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.bo.CustomizeReportJoinDefBoImpl JD-Core Version: 0.5.4
 */