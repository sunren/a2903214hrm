package com.hr.recruitment.bo.impl;

import com.hr.base.Constants;
import com.hr.base.Status;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.configuration.dao.IStatusDAO;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.JobTitle;
import com.hr.configuration.domain.Location;
import com.hr.configuration.domain.Statusconf;
import com.hr.configuration.domain.StatusconfPK;
import com.hr.profile.domain.Employee;
import com.hr.recruitment.bo.IRecruitplanBo;
import com.hr.recruitment.dao.IRecruitplanDAO;
import com.hr.recruitment.domain.Recruitapplier;
import com.hr.recruitment.domain.Recruitplan;
import com.hr.recruitment.domain.base.BaseRecruitplan;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.Pager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class RecruitplanBoImpl implements IRecruitplanBo, Constants, Status {
    private static final String RP_LAST_CHANGE_BY = BaseRecruitplan.PROP_RECP_LAST_CHANGE_BY;

    private static final String RP_CREATE_BY = BaseRecruitplan.PROP_RECP_CREATE_BY;

    private static final String RP_DEPT_NO = BaseRecruitplan.PROP_RECP_DEPARTMENT_NO;

    private static final String RP_TYPE = BaseRecruitplan.PROP_RECP_TYPE;

    private static final String RP_WORKLOCATION = BaseRecruitplan.PROP_RECP_WORK_LOCATION;
    private static final String PROP_RECP_JOB_TITLE = "recpJobTitle";
    private IRecruitplanDAO dao;
    private IStatusDAO statusDAO;

    public List deleteRecruitplan(String id) {
        List errors = new ArrayList();
        Recruitplan plan = (Recruitplan) this.dao.loadObject(Recruitplan.class, id, null,
                                                             new boolean[0]);
        if (errors.size() < 1) {
            this.dao.deleteObject(plan);
        }
        return errors;
    }

    public List getObjects(Class clas, String[] fetch) {
        return this.dao.getObjects(clas, null);
    }

    public List<String> insertRecruitplan(Recruitplan recruitplan, String empNo) {
        List errors = new ArrayList();
        try {
            recruitplan.setRecpCreateBy(new Employee(empNo));
            recruitplan.setRecpLastChangeBy(new Employee(empNo));
            recruitplan.setRecpCreateTime(new Date());
            recruitplan.setRecpLastChangeTime(new Date());
            this.dao.saveObject(recruitplan);
            if (recruitplan.getRecpStatus().intValue() != 1) {
                addLog(recruitplan, empNo);
            }
            return errors;
        } catch (Exception e) {
            errors.add("添加招聘方案失败!");
        }
        return errors;
    }

    public Recruitplan loadRecruitplan(String id, String[] fetch) {
        return (Recruitplan) this.dao.loadObject(Recruitplan.class, id, fetch, new boolean[0]);
    }

    public List searchRecruitplan(Recruitplan plan, Pager page, String viewAll) {
        DetachedCriteria dc = DetachedCriteria.forClass(Recruitplan.class);
        String[] fetch = { RP_DEPT_NO, RP_CREATE_BY, RP_LAST_CHANGE_BY, RP_TYPE, RP_WORKLOCATION,
                "recpJobTitle" };
        for (int i = 0; i < fetch.length; ++i) {
            dc.setFetchMode(fetch[i], FetchMode.JOIN);
        }
        if (viewAll == null) {
            dc.add(Restrictions.or(Restrictions.ge(Recruitplan.PROP_RECP_END_DATE, new Date()),
                                   Restrictions.isNull(Recruitplan.PROP_RECP_END_DATE)));
        }

        checkOrderMethod(dc, page.getOrder());
        addCriteria(plan, dc);
        splitPage(dc, page);
        List result = this.dao.findByCriteria(dc, page.getPageSize(), page.getCurrentPage());
        return result;
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
            detachedCriteria.addOrder(Order.desc("recpLastChangeTime"));
        }
    }

    public List updateRecruitplan(Recruitplan plan, String empNo) {
        List errors = new ArrayList();
        try {
            plan.setRecpLastChangeBy(new Employee(empNo));
            plan.setRecpLastChangeTime(new Date());
            this.dao.saveOrupdate(plan);
            return errors;
        } catch (Exception e) {
            errors.add("更新招聘方案失败＄1�7");
        }
        return errors;
    }

    public boolean updateRecruitplan(Integer status, String id, String comments, String empNo) {
        try {
            String commSql = "";
            if ((comments != null) && (comments.trim().length() > 0)) {
                commSql = ", recpComments='" + comments.trim() + "' ";
            }
            if ((empNo != null) && (empNo.trim().length() > 0)) {
                commSql = commSql + ", recpLastChangeBy='" + empNo.trim() + "' ";
            }
            commSql = commSql + ", recpLastChangeTime=current_timestamp";
            this.dao.exeHql(" update Recruitplan set recpStatus=" + String.valueOf(status)
                    + commSql + " where id ='" + id + "'");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List searchApproveRecruitplanDept(Recruitplan plan, Pager page) {
        DetachedCriteria dc = DetachedCriteria.forClass(Recruitplan.class);
        String[] fetch = { RP_DEPT_NO, RP_CREATE_BY, RP_LAST_CHANGE_BY, RP_TYPE, RP_WORKLOCATION,
                "recpJobTitle" };
        for (int i = 0; i < fetch.length; ++i) {
            dc.setFetchMode(fetch[i], FetchMode.JOIN);
        }
        dc.add(Restrictions.eq("recpStatus", Integer.valueOf(2)));
        addCriteria(plan, dc);
        checkOrderMethod(dc, page.getOrder());
        splitPage(dc, page);
        List result = this.dao.findByCriteria(dc, page.getPageSize(), page.getCurrentPage());
        return result;
    }

    public List searchApproveRecruitplanHR(Recruitplan plan, Pager page) {
        DetachedCriteria dc = DetachedCriteria.forClass(Recruitplan.class);
        String[] fetch = { RP_DEPT_NO, RP_CREATE_BY, RP_LAST_CHANGE_BY, RP_TYPE, RP_WORKLOCATION,
                "recpJobTitle" };
        for (int i = 0; i < fetch.length; ++i) {
            dc.setFetchMode(fetch[i], FetchMode.JOIN);
        }
        dc.add(Restrictions.eq("recpStatus", Integer.valueOf(11)));
        addCriteria(plan, dc);
        checkOrderMethod(dc, page.getOrder());
        splitPage(dc, page);
        List result = this.dao.findByCriteria(dc, page.getPageSize(), page.getCurrentPage());
        return result;
    }

    private void splitPage(DetachedCriteria detachedCriteria, Pager page) {
        int size = this.dao.findRowCountByCriteria(detachedCriteria);
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

    private void addCriteria(Recruitplan plan, DetachedCriteria detachedCriteria) {
        if (plan != null) {
            if ((plan.getRecpJobTitle() != null)
                    && (plan.getRecpJobTitle().getJobtitleNo().trim().length() > 0))
                detachedCriteria.add(Restrictions.eq(Recruitplan.PROP_RECP_JOB_TITLE, plan
                        .getRecpJobTitle()));
            if ((plan.getRecpJobDesc() != null) && (plan.getRecpJobDesc().trim().length() > 0))
                detachedCriteria.add(Restrictions.like(Recruitplan.PROP_RECP_JOB_DESC, plan
                        .getRecpJobDesc().trim(), MatchMode.ANYWHERE));
            if (plan.getDepts() != null) {
                detachedCriteria.add(Restrictions.in(Recruitplan.PROP_RECP_DEPARTMENT_NO + "."
                        + Department.PROP_ID, plan.getDepts()));
            }
            if ((plan.getRecpDepartmentNo() != null)
                    && (plan.getRecpDepartmentNo().getId().trim().length() > 0))
                detachedCriteria.add(Restrictions.eq(Recruitplan.PROP_RECP_DEPARTMENT_NO, plan
                        .getRecpDepartmentNo()));
            if ((plan.getRecpWorkLocation() != null)
                    && (plan.getRecpWorkLocation().getId() != null)
                    && (plan.getRecpWorkLocation().getId().trim().length() > 0))
                detachedCriteria.add(Restrictions.like(Recruitplan.PROP_RECP_WORK_LOCATION + "."
                        + "id", plan.getRecpWorkLocation().getId().trim(), MatchMode.ANYWHERE));
            if ((plan.getRecpStartDate() != null) && (plan.getRecpEndDate() != null))
                detachedCriteria.add(Restrictions.between(Recruitplan.PROP_RECP_START_DATE, plan
                        .getRecpStartDate(), plan.getRecpEndDate()));
            if ((plan.getRecpStartDate() != null) && (plan.getRecpEndDate() == null))
                detachedCriteria.add(Restrictions.ge(Recruitplan.PROP_RECP_START_DATE, plan
                        .getRecpStartDate()));
            if ((plan.getRecpStartDate() == null) && (plan.getRecpEndDate() != null))
                detachedCriteria.add(Restrictions.le(Recruitplan.PROP_RECP_START_DATE, plan
                        .getRecpEndDate()));
            if ((plan.getRecpLanguageSkill() != null)
                    && (plan.getRecpLanguageSkill().trim().length() > 0))
                detachedCriteria.add(Restrictions.like(Recruitplan.PROP_RECP_LANGUAGE_SKILL, plan
                        .getRecpLanguageSkill().trim(), MatchMode.ANYWHERE));
            if ((plan.getRecpJobSkill() != null) && (plan.getRecpJobSkill().trim().length() > 0))
                detachedCriteria.add(Restrictions.like(Recruitplan.PROP_RECP_JOB_SKILL, plan
                        .getRecpJobSkill().trim(), MatchMode.ANYWHERE));
            if (plan.getRecpStatus() != null)
                detachedCriteria.add(Restrictions.eq(Recruitplan.PROP_RECP_STATUS, plan
                        .getRecpStatus()));
            if (plan.getRecpNumberExpect() != null)
                detachedCriteria.add(Restrictions.eq(Recruitplan.PROP_RECP_NUMBER_EXPECT, plan
                        .getRecpNumberExpect()));
            if (plan.getRecpCreateBy() != null)
                detachedCriteria.add(Restrictions.eq(Recruitplan.PROP_RECP_CREATE_BY, plan
                        .getRecpCreateBy()));
        }
    }

    public String findStatusByRecpStatusNo(int no) {
        if (no >= 0) {
            try {
                Statusconf stconf = (Statusconf) this.dao.loadObject(Statusconf.class,
                                                                     new StatusconfPK(Integer
                                                                             .valueOf(no),
                                                                             "recruitplan"), null,
                                                                     new boolean[0]);
                if (stconf != null)
                    return stconf.getStatusconfDesc();
            } catch (Exception e) {
                return "无状怄1�7";
            }
        }
        return "无状怄1�7";
    }

    public List getRecruitplanStatus() {
        return this.statusDAO.getStatusByTable("recruitplan");
    }

    public IRecruitplanDAO getDao() {
        return this.dao;
    }

    public void setDao(IRecruitplanDAO dao) {
        this.dao = dao;
    }

    public IStatusDAO getStatusDAO() {
        return this.statusDAO;
    }

    public void setStatusDAO(IStatusDAO statusDAO) {
        this.statusDAO = statusDAO;
    }

    public boolean loadCompaplanByEmpNo(String empNo) {
        return false;
    }

    public void updateRecruitplanStatusAsSubmit(String changIds, String empNo, int status) {
        this.dao.exeHql(" update Recruitplan set recpStatus=" + status
                + ",recpLastChangeTime=current_timestamp" + " where id ='" + changIds + "'");

        if (changIds != null) {
            Recruitplan recruitplan = (Recruitplan) this.dao.loadObject(Recruitplan.class,
                                                                        changIds, null,
                                                                        new boolean[0]);
            addLog(recruitplan, empNo);
        }
    }

    public void addLog(Recruitplan recruitplan, String empNo) {
        ISysLogBO logBO = (ISysLogBO) SpringBeanFactory.getBean("logBO");
        String tempText = "";
        switch (recruitplan.getRecpStatus().intValue()) {
        case 2:
            tempText = "提交计划";
            break;
        case 11:
            tempText = "部门批准";
            break;
        case 12:
            tempText = "HR备案";
            break;
        case 21:
            tempText = "拒绝";
            break;
        case 22:
            tempText = "作废";
            break;
        case 23:
            tempText = "中止";
            break;
        case 31:
            tempText = "关闭";
        }
        try {
            logBO.addToSyslog("recruitplan", empNo, recruitplan.getRecpCreateBy().getId(),
                              recruitplan.getId().toString(), 0, tempText, recruitplan
                                      .getRecpComments());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String updateRecruitplanAsComment(String id, String recpComments, Integer status) {
        try {
            Recruitplan rp = (Recruitplan) this.dao.loadObject(Recruitplan.class, id, null,
                                                               new boolean[0]);
            if (rp == null) {
                return "ERROR:此招聘计划在数据库中不存在！";
            }
            if ((rp.getRecpStatus().intValue() != 21) && (rp.getRecpStatus().intValue() != 1))
                return "ERROR:招聘计划状�1�7�不是草稿或已拒绝，无法提交";
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.dao.exeHql(" update Recruitplan set recpComments='" + recpComments + "',recpStatus='"
                + status + "' where id ='" + id + "'");

        return null;
    }

    public List searchRecruitplanforHR(Recruitplan plan, Pager page, String viewAll) {
        DetachedCriteria dc = DetachedCriteria.forClass(Recruitplan.class);
        String[] fetch = { RP_DEPT_NO, RP_CREATE_BY, RP_LAST_CHANGE_BY, RP_TYPE, RP_WORKLOCATION,
                "recpJobTitle" };
        for (int i = 0; i < fetch.length; ++i) {
            dc.setFetchMode(fetch[i], FetchMode.JOIN);
        }
        if (viewAll == null) {
            dc.add(Restrictions.or(Restrictions.ge(Recruitplan.PROP_RECP_END_DATE, new Date()),
                                   Restrictions.isNull(Recruitplan.PROP_RECP_END_DATE)));
        }

        dc.add(Restrictions.ne("recpStatus", Integer.valueOf(1)));

        checkOrderMethod(dc, page.getOrder());
        addCriteria(plan, dc);
        splitPage(dc, page);
        return this.dao.findByCriteria(dc, page.getPageSize(), page.getCurrentPage());
    }

    public List update_closeRecruitplan(String id, String empNo) {
        ISysLogBO logBO = (ISysLogBO) SpringBeanFactory.getBean("logBO");
        List errors = new ArrayList();
        String[] fetch = { "recpCreateBy" };
        Recruitplan plan = (Recruitplan) this.dao.loadObject(Recruitplan.class, id, fetch,
                                                             new boolean[0]);
        if ((plan == null) || (plan.getRecpStatus().intValue() == 22)
                || (plan.getRecpStatus().intValue() == 23)
                || (plan.getRecpStatus().intValue() == 31)) {
            errors.add("在您执行之前此条计划已经被关闭了!");
            return errors;
        }

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Recruitapplier.class);
        detachedCriteria.setFetchMode("recaPlan", FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq("recaPlan.id", plan.getId()));
        detachedCriteria.add(Restrictions.eq("recaStatus", Integer.valueOf(13)));
        int factNum = this.dao.findRowCountByCriteria(detachedCriteria);

        String timeSql = "', recp_end_date=current_timestamp ";
        if (factNum == 0) {
            this.dao.exeHql(" update Recruitplan set recp_status='22" + timeSql + " where id ='"
                    + id + "'");
            try {
                logBO.addToSyslog("recruitplan", empNo, plan.getRecpCreateBy().getId(), plan
                        .getId().toString(), 0, "作废", null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if ((factNum > 0) && (factNum < plan.getRecpNumberExpect().intValue())) {
            this.dao.exeHql(" update Recruitplan set recp_status='23" + timeSql + " where id ='"
                    + id + "'");
            try {
                logBO.addToSyslog("recruitplan", empNo, plan.getRecpCreateBy().getId(), plan
                        .getId().toString(), 0, "中止", null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (factNum >= plan.getRecpNumberExpect().intValue()) {
            this.dao.exeHql(" update Recruitplan set recp_status='31" + timeSql + " where id ='"
                    + id + "'");
            try {
                logBO.addToSyslog("recruitplan", empNo, plan.getRecpCreateBy().getId(), plan
                        .getId().toString(), 0, "关闭", null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        addLog(plan, empNo);
        return errors;
    }

    public List getApprovedPlanList() {
        DetachedCriteria dc = DetachedCriteria.forClass(Recruitplan.class);
        String[] fetch = { RP_DEPT_NO, RP_CREATE_BY, RP_LAST_CHANGE_BY, RP_TYPE, RP_WORKLOCATION,
                "recpJobTitle" };
        for (int i = 0; i < fetch.length; ++i) {
            dc.setFetchMode(fetch[i], FetchMode.JOIN);
        }
        dc.add(Restrictions.eq("recpStatus", Integer.valueOf(12)));
        return this.dao.findByCriteria(dc);
    }

    public void updatePlan(Recruitplan plan) {
        this.dao.updateObject(plan);
    }

    public List searchApprovePlanWithCondition(String condition) {
        DetachedCriteria dc = DetachedCriteria.forClass(Recruitplan.class);
        String[] fetch = { RP_DEPT_NO, RP_CREATE_BY, RP_LAST_CHANGE_BY, RP_TYPE, RP_WORKLOCATION,
                "recpJobTitle" };
        for (int i = 0; i < fetch.length; ++i) {
            dc.setFetchMode(fetch[i], FetchMode.JOIN);
        }
        dc.add(Restrictions.eq("recpStatus", Integer.valueOf(12)));
        dc.add(Restrictions.like("recpJobTitle", "%" + condition.trim() + "%"));
        return this.dao.findByCriteria(dc);
    }

    public String replaceStr(String str) {
        return str.replace("%", "\\%");
    }

    @SuppressWarnings("unchecked")
    public Integer getMaxRecordNo(String tableName, String columnName) {
        List<Integer> maxNoList = this.dao.exeHqlList("select max(" + columnName + ") from " + tableName);
        Integer maxID;
        if ((maxNoList == null) || (maxNoList.size() < 1) || (maxNoList.get(0) == null))
            maxID = Integer.valueOf(1);
        else
            maxID = Integer.valueOf(1 + maxNoList.get(0).intValue());
        return maxID;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.recruitment.bo.impl.RecruitplanBoImpl JD-Core Version: 0.5.4
 */