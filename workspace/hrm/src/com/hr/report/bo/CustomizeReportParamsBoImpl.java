package com.hr.report.bo;

import com.hr.report.dao.IReportDAO;
import com.hr.report.domain.ReportParams;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class CustomizeReportParamsBoImpl implements CustomizeReportParamsBo {
    private IReportDAO reportDAO;

    public List<String> addReportParams(ReportParams params) {
        List errors = new ArrayList();
        try {
            this.reportDAO.saveObject(params);
        } catch (Exception ex) {
            ex.printStackTrace();
            errors.add("保存报表参数时发生错误，保存失败!");
        }
        return errors;
    }

    public List<String> deleteReportParams(String paramsId) {
        List errors = new ArrayList();
        try {
            this.reportDAO.deleteObject(ReportParams.class, paramsId);
        } catch (Exception ex) {
            ex.printStackTrace();
            errors.add("删除参数id为" + paramsId + "的报表参数记录失败！");
        }
        return errors;
    }

    public List<ReportParams> findReportParams(String reportId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportParams.class);

        detachedCriteria.add(Restrictions.eq("reportDef.reportId", reportId));
        detachedCriteria.setFetchMode("reportSetsDef", FetchMode.JOIN);
        detachedCriteria.addOrder(Order.asc("reportParamsSortId"));
        return this.reportDAO.findByCriteria(detachedCriteria);
    }

    public List<String> updateReportParams(ReportParams params) {
        List errors = new ArrayList();
        try {
            this.reportDAO.updateObject(params);
        } catch (Exception ex) {
            ex.printStackTrace();
            errors.add("修改定制报表参数失败!");
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
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.bo.CustomizeReportParamsBoImpl JD-Core Version: 0.5.4
 */