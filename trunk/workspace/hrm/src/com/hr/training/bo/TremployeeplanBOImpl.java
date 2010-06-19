package com.hr.training.bo;

import com.hr.configuration.dao.IStatusDAO;
import com.hr.configuration.domain.Department;
import com.hr.profile.domain.Employee;
import com.hr.training.dao.ITrcourseDAO;
import com.hr.training.dao.ITrcourseplanDAO;
import com.hr.training.dao.ITremployeeplanDAO;
import com.hr.training.domain.Trcourse;
import com.hr.training.domain.Trcourseplan;
import com.hr.training.domain.Tremployeeplan;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.Pager;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class TremployeeplanBOImpl implements ITremployeeplanBO {
    private ITremployeeplanDAO tremployeeplanDAO;
    private ITrcourseDAO trcourseDAO;
    private ITrcourseplanDAO trcourseplanDAO;
    private String[] fetch;
    private IStatusDAO statusDAO;

    public TremployeeplanBOImpl() {
        this.fetch = new String[] { Tremployeeplan.PROP_TRP_TRAINEE_NO,
                Tremployeeplan.PROP_TRP_TRCP + "." + Trcourseplan.PROP_TRCP_COORDINATOR,
                Tremployeeplan.PROP_TRP_CREATE_BY, Tremployeeplan.PROP_TRP_LAST_CHANGE_BY,
                Tremployeeplan.PROP_TRP_TRAINEE_DEPT, Tremployeeplan.PROP_TRP_TRCP,
                Tremployeeplan.PROP_TRP_TRCP + "." + Trcourseplan.PROP_TRCP_COURSE_NO };
    }

    public void delete(String trepId) {
        this.tremployeeplanDAO.deleteObject(Tremployeeplan.class, trepId);
    }

    public void delete(Tremployeeplan trep) {
        this.tremployeeplanDAO.deleteObject(trep);
    }

    public Tremployeeplan loadById(String trepId) {
        return (Tremployeeplan) this.tremployeeplanDAO.loadObject(Tremployeeplan.class, trepId,
                                                                  this.fetch, new boolean[0]);
    }

    public void save(Tremployeeplan trep) {
        java.util.Date now = new java.util.Date();
        trep.setTrpCreateTime(now);
        trep.setTrpLastChangeTime(now);
        this.tremployeeplanDAO.saveObject(trep);
    }

    public void saveOrupdate(Tremployeeplan trep) {
        trep.setTrpLastChangeTime(new java.util.Date());
        this.tremployeeplanDAO.saveOrupdate(trep);
    }

    public List<Tremployeeplan> search(String traineeNo, String trcName, String trcpTeacher,
            String trcpLocation, Integer trpStatus, Pager page) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Tremployeeplan.class);

        for (int i = 0; i < this.fetch.length; ++i) {
            detachedCriteria.setFetchMode(this.fetch[i], FetchMode.JOIN);
        }

        if ((page.getOrder() == null) || (page.getOrder().equals(""))) {
            detachedCriteria.createAlias(Tremployeeplan.PROP_TRP_TRCP, "trpTrcp");
            detachedCriteria.addOrder(Order.desc("trpTrcp.trcpStartDate"));
            detachedCriteria.createAlias("trpTrcp.trcpCourseNo", "cource");
            detachedCriteria.addOrder(Order.asc("cource.trcName"));
        }

        checkOrderMethod(detachedCriteria, page.getOrder());

        if ((traineeNo != null) && (traineeNo.trim().length() > 0)) {
            detachedCriteria.add(Restrictions.eq(Tremployeeplan.PROP_TRP_TRAINEE_NO + "."
                    + Employee.PROP_ID, traineeNo.trim()));
        }

        if ((trcName != null) && (trcName.trim().length() > 0)) {
            DetachedCriteria trcDC = DetachedCriteria.forClass(Trcourse.class);
            trcDC.add(Restrictions.ilike(Trcourse.PROP_TRC_NAME, replaceStr(trcName),
                                         MatchMode.ANYWHERE));
            trcDC.setProjection(Projections.property(Trcourse.PROP_TRC_NO));
            List trcNos = this.trcourseDAO.findByCriteria(trcDC);
            if ((trcNos == null) || (trcNos.size() <= 0)) {
                return new ArrayList();
            }
            DetachedCriteria trcpDC = DetachedCriteria.forClass(Trcourseplan.class);
            trcpDC.setFetchMode(Trcourseplan.PROP_TRCP_COURSE_NO, FetchMode.JOIN);
            trcpDC.add(Restrictions.in(Trcourseplan.PROP_TRCP_COURSE_NO + "."
                    + Trcourse.PROP_TRC_NO, trcNos));
            trcpDC.setProjection(Projections.property(Trcourseplan.PROP_TRCP_ID));
            List trcpIds = this.trcourseplanDAO.findByCriteria(trcpDC);
            if ((trcpIds != null) && (trcpIds.size() > 0)) {
                detachedCriteria.add(Restrictions.in(Tremployeeplan.PROP_TRP_TRCP + "."
                        + Trcourseplan.PROP_TRCP_ID, trcpIds));
            }
        }

        if ((trcpTeacher != null) && (trcpTeacher.trim().length() > 0)) {
            if ((page.getOrder() == null) || (page.getOrder().equals(""))) {
                detachedCriteria.add(Restrictions.like("trpTrcp." + Trcourseplan.PROP_TRCP_TEACHER,
                                                       "%" + trcpTeacher.trim() + "%"));
            } else if (page.getOrder().startsWith("trpTrcp.")) {
                detachedCriteria.add(Restrictions.like("ord0." + Trcourseplan.PROP_TRCP_TEACHER,
                                                       "%" + trcpTeacher.trim() + "%"));
            } else {
                detachedCriteria.createAlias(Tremployeeplan.PROP_TRP_TRCP, "trpTrcp");
                detachedCriteria.add(Restrictions.like("trpTrcp." + Trcourseplan.PROP_TRCP_TEACHER,
                                                       "%" + trcpTeacher.trim() + "%"));
            }
        }

        if ((trcpLocation != null) && (trcpLocation.trim().length() > 0)) {
            if ((page.getOrder() == null) || (page.getOrder().equals(""))) {
                detachedCriteria.add(Restrictions
                        .like("trpTrcp." + Trcourseplan.PROP_TRCP_LOCATION, "%"
                                + trcpLocation.trim() + "%"));
            } else if (page.getOrder().startsWith("trpTrcp.")) {
                detachedCriteria.add(Restrictions.like("ord0." + Trcourseplan.PROP_TRCP_LOCATION,
                                                       "%" + trcpLocation.trim() + "%"));
            } else {
                detachedCriteria.createAlias(Tremployeeplan.PROP_TRP_TRCP, "trpTrcp");
                detachedCriteria.add(Restrictions
                        .like("trpTrcp." + Trcourseplan.PROP_TRCP_LOCATION, "%"
                                + trcpLocation.trim() + "%"));
            }

        }

        if (trpStatus != null) {
            detachedCriteria.add(Restrictions.eq(Tremployeeplan.PROP_TRP_STATUS, trpStatus));
        }
        int size = this.tremployeeplanDAO.findRowCountByCriteria(detachedCriteria);

        int pageSize = Integer.valueOf(
                                       DatabaseSysConfigManager.getInstance()
                                               .getProperty("sys.split.pages")).intValue();
        detachedCriteria.setProjection(null);

        page.init(size, pageSize);

        if ((page.getOperate() != null) && ("previous".equalsIgnoreCase(page.getOperate()))) {
            page.previous();
        }
        if ((page.getOperate() != null) && ("next".equalsIgnoreCase(page.getOperate()))) {
            page.next();
        }
        if ((page.getOperate() != null) && ("first".equalsIgnoreCase(page.getOperate()))) {
            page.first();
        }
        if ((page.getOperate() != null) && ("last".equalsIgnoreCase(page.getOperate()))) {
            page.last();
        }
        List result = this.tremployeeplanDAO.findByCriteria(detachedCriteria, pageSize, page
                .getCurrentPage());

        return result;
    }

    public List search(String[] depts, String emp, String trcName, String trcpTeacher,
            String trcpLocation, Integer trpStatus, Pager page) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Tremployeeplan.class);

        for (int i = 0; i < this.fetch.length; ++i) {
            detachedCriteria.setFetchMode(this.fetch[i], FetchMode.JOIN);
        }
        if ((page.getOrder() == null) || (page.getOrder().equals(""))) {
            detachedCriteria.createAlias(Tremployeeplan.PROP_TRP_TRCP, "trpTrcp");
            detachedCriteria.addOrder(Order.desc("trpTrcp.trcpStartDate"));
            detachedCriteria.createAlias(Tremployeeplan.PROP_TRP_TRAINEE_NO, "traineeNo");
            detachedCriteria.addOrder(Order.asc("traineeNo.empName"));
        }

        checkOrderMethod(detachedCriteria, page.getOrder());

        if ((depts != null) && (depts.length > 0)) {
            detachedCriteria.add(Restrictions.in(Tremployeeplan.PROP_TRP_TRAINEE_DEPT + "."
                    + Department.PROP_ID, depts));
        }
        if ((emp != null) && (emp.trim().length() > 0)) {
            if ((page.getOrder() == null) || (page.getOrder().equals(""))) {
                detachedCriteria.add(Restrictions.or(Restrictions.like("traineeNo."
                        + Employee.PROP_EMP_DISTINCT_NO, "%" + emp.trim() + "%"), Restrictions
                        .like("traineeNo." + Employee.PROP_EMP_NAME, "%" + emp.trim() + "%")));
            } else if (page.getOrder().startsWith("trpTraineeNo.")) {
                detachedCriteria.add(Restrictions.or(Restrictions.like("ord0."
                        + Employee.PROP_EMP_DISTINCT_NO, "%" + emp.trim() + "%"), Restrictions
                        .like("ord0." + Employee.PROP_EMP_NAME, "%" + emp.trim() + "%")));
            } else {
                detachedCriteria.createAlias(Tremployeeplan.PROP_TRP_TRAINEE_NO, "traineeNo");
                detachedCriteria.add(Restrictions.or(Restrictions.like("traineeNo."
                        + Employee.PROP_EMP_DISTINCT_NO, "%" + emp.trim() + "%"), Restrictions
                        .like("traineeNo." + Employee.PROP_EMP_NAME, "%" + emp.trim() + "%")));
            }

        }

        if ((trcName != null) && (trcName.trim().length() > 0)) {
            DetachedCriteria trcDC = DetachedCriteria.forClass(Trcourse.class);
            trcDC.add(Restrictions.ilike(Trcourse.PROP_TRC_NAME, replaceStr(trcName),
                                         MatchMode.ANYWHERE));
            trcDC.setProjection(Projections.property(Trcourse.PROP_TRC_NO));
            List trcNos = this.trcourseDAO.findByCriteria(trcDC);
            if ((trcNos == null) || (trcNos.size() <= 0))
                return new ArrayList();
            DetachedCriteria trcpDC = DetachedCriteria.forClass(Trcourseplan.class);
            trcpDC.setFetchMode(Trcourseplan.PROP_TRCP_COURSE_NO, FetchMode.JOIN);
            trcpDC.add(Restrictions.in(Trcourseplan.PROP_TRCP_COURSE_NO + "."
                    + Trcourse.PROP_TRC_NO, trcNos));
            trcpDC.setProjection(Projections.property(Trcourseplan.PROP_TRCP_ID));
            List trcpIds = this.trcourseplanDAO.findByCriteria(trcpDC);

            detachedCriteria.add(Restrictions.in(Tremployeeplan.PROP_TRP_TRCP + "."
                    + Trcourseplan.PROP_TRCP_ID, trcpIds));
        }

        if ((trcpTeacher != null) && (trcpTeacher.trim().length() > 0)) {
            if ((page.getOrder() == null) || (page.getOrder().equals(""))) {
                detachedCriteria.add(Restrictions.like("trpTrcp." + Trcourseplan.PROP_TRCP_TEACHER,
                                                       "%" + trcpTeacher.trim() + "%"));
            } else if (page.getOrder().startsWith("trpTrcp.")) {
                detachedCriteria.add(Restrictions.like("ord0." + Trcourseplan.PROP_TRCP_TEACHER,
                                                       "%" + trcpTeacher.trim() + "%"));
            } else {
                detachedCriteria.createAlias(Tremployeeplan.PROP_TRP_TRCP, "trpTrcp");
                detachedCriteria.add(Restrictions.like("trpTrcp." + Trcourseplan.PROP_TRCP_TEACHER,
                                                       "%" + trcpTeacher.trim() + "%"));
            }
        }

        if ((trcpLocation != null) && (trcpLocation.trim().length() > 0)) {
            if ((page.getOrder() == null) || (page.getOrder().equals(""))) {
                detachedCriteria.add(Restrictions
                        .like("trpTrcp." + Trcourseplan.PROP_TRCP_LOCATION, "%"
                                + trcpLocation.trim() + "%"));
            } else if (page.getOrder().startsWith("trpTrcp.")) {
                detachedCriteria.add(Restrictions.like("ord0." + Trcourseplan.PROP_TRCP_LOCATION,
                                                       "%" + trcpLocation.trim() + "%"));
            } else {
                detachedCriteria.createAlias(Tremployeeplan.PROP_TRP_TRCP, "trpTrcp");
                detachedCriteria.add(Restrictions
                        .like("trpTrcp." + Trcourseplan.PROP_TRCP_LOCATION, "%"
                                + trcpLocation.trim() + "%"));
            }

        }

        if (trpStatus != null) {
            detachedCriteria.add(Restrictions.eq(Tremployeeplan.PROP_TRP_STATUS, trpStatus));
        }
        int size = this.tremployeeplanDAO.findRowCountByCriteria(detachedCriteria);

        int pageSize = Integer.valueOf(
                                       DatabaseSysConfigManager.getInstance()
                                               .getProperty("sys.split.pages")).intValue();
        detachedCriteria.setProjection(null);

        page.init(size, pageSize);

        if ((page.getOperate() != null) && ("previous".equalsIgnoreCase(page.getOperate()))) {
            page.previous();
        }
        if ((page.getOperate() != null) && ("next".equalsIgnoreCase(page.getOperate()))) {
            page.next();
        }
        if ((page.getOperate() != null) && ("first".equalsIgnoreCase(page.getOperate()))) {
            page.first();
        }
        if ((page.getOperate() != null) && ("last".equalsIgnoreCase(page.getOperate()))) {
            page.last();
        }
        List result = this.tremployeeplanDAO.findByCriteria(detachedCriteria, pageSize, page
                .getCurrentPage());
        return result;
    }

    public boolean hasApplied(String empNo, String trcpId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Tremployeeplan.class);
        detachedCriteria.setFetchMode(Tremployeeplan.PROP_TRP_TRAINEE_NO, FetchMode.JOIN)
                .setFetchMode(Tremployeeplan.PROP_TRP_TRCP, FetchMode.JOIN);

        if ((empNo != null) && (empNo.trim().length() > 0)) {
            detachedCriteria.add(Restrictions.eq(Tremployeeplan.PROP_TRP_TRAINEE_NO + "."
                    + Employee.PROP_ID, empNo.trim()));
        }
        if (trcpId != null) {
            detachedCriteria.add(Restrictions.eq(Tremployeeplan.PROP_TRP_TRCP + "."
                    + Trcourseplan.PROP_TRCP_ID, trcpId));
        }
        return this.tremployeeplanDAO.findByCriteria(detachedCriteria).size() > 0;
    }

    public int countAppliedAmount(String trcpId) {
        String hql = "select count(*) from Tremployeeplan where trp_trcp_id='" + trcpId
                + "' and trp_status !=" + 21 + " and trp_status !=" + 22;

        return ((Long) this.tremployeeplanDAO.exeHqlList(hql).get(0)).intValue();
    }

    public int hasAppliedEmp(String trcpId) {
        String hql = "select count(*) from Tremployeeplan where trp_trcp_id='" + trcpId + "'";
        return ((Long) this.tremployeeplanDAO.exeHqlList(hql).get(0)).intValue();
    }

    public void batchSetStatus(String id, int status, String empId) {
        String hql = "update Tremployeeplan set trp_status=" + status + ", trp_last_change_by='"
                + empId + "', trp_last_change_time='"
                + new java.sql.Date(new java.util.Date().getTime()) + "' where trp_id ='" + id
                + "'";

        this.tremployeeplanDAO.exeHql(hql);
    }

    public void checkOrderMethod(DetachedCriteria detachedCriteria, String order) {
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
                    detachedCriteria.createAlias(fetc + fetch[len], "ord" + len, 1);

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
            detachedCriteria.addOrder(Order.desc(Tremployeeplan.PROP_TRP_LAST_CHANGE_TIME));
        }
    }

    public String replaceStr(String str) {
        return str.replace("%", "\\%");
    }

    public List getCompeplanStatusInRecruitplan() {
        return this.statusDAO.getCompeplanStatusInRecruitplan("tremployeeplan");
    }

    public ITremployeeplanDAO getTremployeeplanDAO() {
        return this.tremployeeplanDAO;
    }

    public void setTremployeeplanDAO(ITremployeeplanDAO tremployeeplanDAO) {
        this.tremployeeplanDAO = tremployeeplanDAO;
    }

    public ITrcourseDAO getTrcourseDAO() {
        return this.trcourseDAO;
    }

    public void setTrcourseDAO(ITrcourseDAO trcourseDAO) {
        this.trcourseDAO = trcourseDAO;
    }

    public ITrcourseplanDAO getTrcourseplanDAO() {
        return this.trcourseplanDAO;
    }

    public void setTrcourseplanDAO(ITrcourseplanDAO trcourseplanDAO) {
        this.trcourseplanDAO = trcourseplanDAO;
    }

    public IStatusDAO getStatusDAO() {
        return this.statusDAO;
    }

    public void setStatusDAO(IStatusDAO statusDAO) {
        this.statusDAO = statusDAO;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.training.bo.TremployeeplanBOImpl JD-Core Version: 0.5.4
 */