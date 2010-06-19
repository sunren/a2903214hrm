package com.hr.report.bo;

import com.hr.report.dao.IReportDAO;
import com.hr.report.domain.ReportSets;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class CustomizeReportSetsBoImpl implements CustomizeReportSetsBo {
    private IReportDAO reportDAO;

    public List<String> addReportSets(ReportSets reportSets) {
        List errors = new ArrayList();
        try {
            this.reportDAO.saveObject(reportSets);
        } catch (Exception ex) {
            ex.printStackTrace();
            errors.add("保存输出列参数时发生错误，保存失败！");
        }
        return errors;
    }

    public List deleteReportSets(String reportSetsId) {
        List errors = new ArrayList();
        if (StringUtils.isEmpty(reportSetsId)) {
            errors.add("错误的请求参数！删除失败＄1�7");
            return errors;
        }
        try {
            this.reportDAO.deleteObject(ReportSets.class, reportSetsId);
        } catch (Exception ex) {
            ex.printStackTrace();
            errors.add("删除输出参数列时发生异常，删除失败！");
        }
        return errors;
    }

    public List deleteReportSetsByReportId(String reportId) {
        List errors = new ArrayList();
        if (StringUtils.isEmpty(reportId)) {
            errors.add("错误的请求参数！删除失败＄1�7");
            return errors;
        }
        String hql = "delete from ReportSets where reportDef='" + reportId + "'";
        try {
            this.reportDAO.exeHql(hql);
        } catch (Exception ex) {
            ex.printStackTrace();
            errors.add("删除记录时发生错误！，删除失败！");
        }
        return errors;
    }

    public List getObjects(Class clas, String[] fetch) {
        return this.reportDAO.getObjects(clas, fetch);
    }

    public List getOutputColumnsByReportDefId(String reportId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportSets.class);
        detachedCriteria.add(Restrictions.eq("reportSetsReportDef.reportId", reportId));
        detachedCriteria.setFetchMode("reportSetsReportSetsDef", FetchMode.JOIN);
        return this.reportDAO.findByCriteria(detachedCriteria);
    }

    public List<String> updateReportSets(ReportSets reportSets) {
        List errors = new ArrayList();
        if ((reportSets == null) || (StringUtils.isEmpty(reportSets.getReportSetsId()))) {
            errors.add("缺少必要的参数，保存失败＄1�7");
            return errors;
        }
        try {
            this.reportDAO.updateObject(reportSets);
        } catch (Exception ex) {
            ex.printStackTrace();
            errors.add("更新输出列时发生异常，修改失败！");
        }
        return errors;
    }

    public IReportDAO getReportDAO() {
        return this.reportDAO;
    }

    public void setReportDAO(IReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.bo.CustomizeReportSetsBoImpl JD-Core Version: 0.5.4
 */