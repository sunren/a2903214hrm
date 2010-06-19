package com.hr.recruitment.bo.impl;

import com.hr.base.FileOperate;
import com.hr.base.Status;
import com.hr.configuration.dao.StatusDAOImpl;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.JobTitle;
import com.hr.profile.domain.Employee;
import com.hr.recruitment.bo.IRecruitapplierBo;
import com.hr.recruitment.dao.IRecruitapplierDAO;
import com.hr.recruitment.domain.Recruitapplier;
import com.hr.recruitment.domain.Recruitplan;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.Pager;
import com.hr.util.PropertiesFileConfigManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class RecruitapplierBoImpl implements IRecruitapplierBo, Status {
    private IRecruitapplierDAO applierDAO;

    public List getObjects(Class c, String[] fetch) {
        return this.applierDAO.getObjects(c, fetch);
    }

    public Recruitapplier loadApplier(Object id, String[] fetch) {
        return (Recruitapplier) this.applierDAO.loadObject(Recruitapplier.class, id, fetch,
                                                           new boolean[0]);
    }

    public List updateApplier(Recruitapplier applier, String empNo) {
        List errors = new ArrayList();
        try {
            applier.setRecaLastChangeBy(new Employee(empNo));
            applier.setRecaLastChangeTime(new Date());
            this.applierDAO.updateObject(applier);
            return errors;
        } catch (Exception e) {
            e.printStackTrace();
            errors.add("更新申请者失败!");
        }
        return errors;
    }

    public List deleteApplier(String id) {
        List errors = new ArrayList();
        Recruitapplier applier = (Recruitapplier) this.applierDAO.loadObject(Recruitapplier.class,
                                                                             id, null,
                                                                             new boolean[0]);
        if (applier == null) {
            errors.add("此人在数据库中不存在");
        }
        try {
            String filepath = FileOperate.getFileHomePath()
                    + PropertiesFileConfigManager.getInstance()
                            .getProperty("sys.recruitment.applier.path");

            filepath = filepath + "/";

            String fileName = applier.getRecaAttachmentName();
            String file = filepath + fileName;
            File f = new File(file);
            if (f.exists()) {
                f.delete();
            }
            this.applierDAO.deleteObject(applier);
        } catch (Exception e) {
            errors.add("删除失败");
        }
        return errors;
    }

    public List searchApplierWithoutId(Recruitapplier applier, Pager page) {
        DetachedCriteria dc = DetachedCriteria.forClass(Recruitapplier.class);
        String[] fetch = { "recaCreateBy", "recaChannel", "recaPlan", "recaPlan.recpLastChangeBy",
                "recaPlan.recpCreateBy", "recaLastChangeBy", "recaPlan.recpDepartmentNo",
                "recaPlan.recpType", "recaPlan.recpWorkLocation", "recaPlan.recpJobTitle" };

        for (int i = 0; i < fetch.length; ++i) {
            dc.setFetchMode(fetch[i], FetchMode.JOIN);
        }
        addCriteria(applier, dc);
        splitPage(dc, page);
        return this.applierDAO.findByCriteria(dc, page.getPageSize(), page.getCurrentPage());
    }

    private void addCriteria(Recruitapplier applier, DetachedCriteria dc) {
        if (applier != null) {
            if ((applier.getRecaName() != null) && (applier.getRecaName().trim().length() > 0))
                dc.add(Restrictions.like("recaName", applier.getRecaName().trim(),
                                         MatchMode.ANYWHERE));
            if ((applier.getRecaPlan() != null)
                    && (applier.getRecaPlan().getRecpDepartmentNo() != null)
                    && (applier.getRecaPlan().getRecpDepartmentNo().getId() != null)
                    && (applier.getRecaPlan().getRecpDepartmentNo().getId().trim().length() > 0)) {
                dc
                        .add(Restrictions
                                .sqlRestriction(
                                                "reca_plan_id in(select recp_id from recruitplan where recp_department_no in (select department_no from department where department_no =?))",
                                                applier.getRecaPlan().getRecpDepartmentNo().getId()
                                                        .trim(), Hibernate.STRING));
            }
            if ((applier.getRecaPlan() != null)
                    && (applier.getRecaPlan().getRecpJobTitle() != null)
                    && (applier.getRecaPlan().getRecpJobTitle().getJobtitleNo().trim().length() > 0))
                dc
                        .add(Restrictions
                                .sqlRestriction("reca_plan_id in(select recp_id from recruitplan where recp_job_title ='"
                                        + applier.getRecaPlan().getRecpJobTitle().getJobtitleNo()
                                                .trim() + "')"));
            if (applier.getRecaStatus() != null)
                dc
                        .add(Restrictions
                                .sqlRestriction(
                                                "reca_id in(select reca_id from recruitapplier where reca_status = ?)",
                                                applier.getRecaStatus(), Hibernate.INTEGER));
        }
    }

    private void splitPage(DetachedCriteria detachedCriteria, Pager page) {
        checkOrderMethod(detachedCriteria, page.getOrder());
        int size = this.applierDAO.findRowCountByCriteria(detachedCriteria);
        int pageSize = Integer.valueOf(
                                       DatabaseSysConfigManager.getInstance()
                                               .getProperty("sys.split.pages")).intValue();
        detachedCriteria.setProjection(null);
        page.init(size, pageSize);

        if ((page.getOperate() != null) && ("previous".equalsIgnoreCase(page.getOperate())))
            page.previous();
        if ((page.getOperate() != null) && ("next".equalsIgnoreCase(page.getOperate())))
            page.next();
        if ((page.getOperate() != null) && ("first".equalsIgnoreCase(page.getOperate())))
            page.first();
        if ((page.getOperate() != null) && ("last".equalsIgnoreCase(page.getOperate())))
            page.last();
    }

    private void checkOrderMethod(DetachedCriteria detachedCriteria, String order) {
        if ((order != null) && (order.trim().length() > 1) && (order.indexOf('-') != -1)) {
            String[] pram = order.split("-");
            if ((pram[0] == null) || (pram[0].length() < 1)) {
                return;
            }
            String[] fetch = pram[0].split("\\.");
            String orde = pram[0];
            if (fetch.length > 1) {
                String str = "";
                String fetc = "";
                for (int len = 0; len < fetch.length - 1; ++len) {
                    detachedCriteria.createAlias(fetc + fetch[len], "ord" + len);
                    fetc = fetc + fetch[len] + ".";
                    str = "ord" + len + ".";
                }
                orde = str + pram[0].substring(pram[0].lastIndexOf(".") + 1);
            }
            if ((pram.length == 2) && (pram[1].equalsIgnoreCase("up")))
                detachedCriteria.addOrder(Order.asc(orde));
            else
                detachedCriteria.addOrder(Order.desc(orde));
        } else {
            detachedCriteria.addOrder(Order.desc("recaInterviewTime"));
        }
    }

    public List searchApplier(Recruitapplier applier, Pager page, String id) {
        DetachedCriteria dc = DetachedCriteria.forClass(Recruitapplier.class);
        String[] fetch = { "recaCreateBy", "recaChannel", "recaPlan", "recaLastChangeBy",
                "recaPlan.recpDepartmentNo", "recaPlan.recpType", "recaPlan.recpJobTitle" };
        for (int i = 0; i < fetch.length; ++i) {
            dc.setFetchMode(fetch[i], FetchMode.JOIN);
        }
        dc.add(Restrictions.eq("recaPlan.id", id));
        return this.applierDAO.findByCriteria(dc);
    }

    public List insertApplier(Recruitapplier applier, String empNo) {
        List errors = new ArrayList();
        try {
            Employee employee = new Employee(empNo);
            applier.setRecaCreateBy(employee);
            applier.setRecaLastChangeBy(employee);
            applier.setRecaCreateTime(new Date());
            applier.setRecaLastChangeTime(new Date());
            this.applierDAO.saveObject(applier);
            return errors;
        } catch (Exception e) {
            e.printStackTrace();
            errors.add("添加招聘成员失败失败!");
        }
        return errors;
    }

    public IRecruitapplierDAO getApplierDAO() {
        return this.applierDAO;
    }

    public void setApplierDAO(IRecruitapplierDAO applierDAO) {
        this.applierDAO = applierDAO;
    }

    public List getApplierStatus() {
        StatusDAOImpl statusDAO = (StatusDAOImpl) SpringBeanFactory.getBean("statusDAO");
        return statusDAO.getStatusByTable("recruitapplier");
    }

    public List getAllAplierDepartment() {
        DetachedCriteria dc = DetachedCriteria.forClass(Recruitapplier.class);
        dc.setFetchMode("recaPlan", FetchMode.JOIN);
        dc.setFetchMode("recaPlan.recpDepartmentNo", FetchMode.JOIN);
        return this.applierDAO.findByCriteria(dc);
    }

    public void updateApplierRemark(String comment, Integer status, String id) {
        if (status != null) {
            this.applierDAO.updateHqlQuery("update Recruitapplier r  set r.recaComment='" + comment
                    + "',r.recaInterviewTime=" + null + ",r.recaStatus='" + status + "' where id='"
                    + id + "'");
        } else
            this.applierDAO.updateHqlQuery("update Recruitapplier r  set r.recaComment='" + comment
                    + "' where id='" + id + "'");
    }

    public Integer getNumberOfApplierInPlan(String PlanId) {
        DetachedCriteria dc = DetachedCriteria.forClass(Recruitapplier.class);
        dc.add(Restrictions.eq("recaPlan.id", PlanId));
        dc.add(Restrictions.eq("recaStatus", Integer.valueOf(13)));
        return Integer.valueOf(this.applierDAO.findRowCountByCriteria(dc));
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.recruitment.bo.impl.RecruitapplierBoImpl JD-Core Version: 0.5.4
 */