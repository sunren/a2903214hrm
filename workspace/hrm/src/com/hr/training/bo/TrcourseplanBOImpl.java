package com.hr.training.bo;

import com.hr.configuration.domain.Department;
import com.hr.profile.domain.Employee;
import com.hr.training.dao.ITrcourseplanDAO;
import com.hr.training.dao.ITremployeeplanDAO;
import com.hr.training.domain.Trcourse;
import com.hr.training.domain.Trcourseplan;
import com.hr.training.domain.Tremployeeplan;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.Pager;
import java.util.Date;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class TrcourseplanBOImpl implements ITrcourseplanBO {
    private ITrcourseplanDAO trcourseplanDAO;
    private ITremployeeplanDAO tremployeeplanDAO;
    private String[] fetch;

    public TrcourseplanBOImpl() {
        this.fetch = new String[] { Trcourseplan.PROP_TRCP_COURSE_NO,
                Trcourseplan.PROP_TRCP_COORDINATOR, Trcourseplan.PROP_TRCP_CREATE_BY,
                Trcourseplan.PROP_TRCP_LAST_CHANGE_BY };
    }

    public Trcourseplan loadById(String trcpId) {
        return (Trcourseplan) this.trcourseplanDAO.loadObject(Trcourseplan.class, trcpId,
                                                              this.fetch, new boolean[0]);
    }

    public void save(Trcourseplan trcp) {
        Date now = new Date();
        trcp.setTrcpCreateTime(now);
        trcp.setTrcpLastChangeTime(now);
        if (trcp.getTrcpDeptLimit() == null) {
            trcp.setTrcpDeptLimit("");
        }
        if ((trcp.getTrcpDeptLimit() != null) && (trcp.getTrcpDeptLimit().trim().length() > 0))
            trcp.setTrcpDeptLimit(", " + trcp.getTrcpDeptLimit() + ", ");
        this.trcourseplanDAO.saveObject(trcp);
    }

    public void delete(Trcourseplan trcp) {
        this.trcourseplanDAO.deleteObject(trcp);
    }

    public void delete(String trcpId) {
        this.trcourseplanDAO.deleteObject(Trcourseplan.class, trcpId);
    }

    public void saveOrupdate(Trcourseplan trcp) {
        if (trcp.getTrcpDeptLimit() == null) {
            trcp.setTrcpDeptLimit("");
        }
        if ((trcp.getTrcpDeptLimit() != null) && (trcp.getTrcpDeptLimit().trim().length() > 0))
            trcp.setTrcpDeptLimit(", " + trcp.getTrcpDeptLimit() + ", ");
        trcp.setTrcpLastChangeTime(new Date());
        this.trcourseplanDAO.saveOrupdate(trcp);
    }

    public List getTrcpByTrc(String trcNo, Pager page) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Trcourseplan.class);
        for (int i = 0; i < this.fetch.length; ++i) {
            detachedCriteria.setFetchMode(this.fetch[i], FetchMode.JOIN);
        }
        detachedCriteria.add(Restrictions.eq("trcpCourseNo.trcNo", trcNo));

        if (page == null) {
            detachedCriteria.addOrder(Order.desc(Trcourseplan.PROP_TRCP_LAST_CHANGE_TIME));
            return this.trcourseplanDAO.findByCriteria(detachedCriteria);
        }

        checkOrderMethod(detachedCriteria, page.getOrder());
        int size = this.trcourseplanDAO.findRowCountByCriteria(detachedCriteria);

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
        return this.trcourseplanDAO.findByCriteria(detachedCriteria, pageSize, page
                .getCurrentPage());
    }

    public List getEnrollableTrcp(Employee trainee, String trcName, String trcpTeacher,
            String trcpLocation, Pager page) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Trcourseplan.class);
        for (int i = 0; i < this.fetch.length; ++i) {
            detachedCriteria.setFetchMode(this.fetch[i], FetchMode.JOIN);
        }
        if ((page.getOrder() == null) || (page.getOrder().trim().length() <= 0)) {
            detachedCriteria.addOrder(Order.desc(Trcourseplan.PROP_TRCP_START_DATE));
            detachedCriteria.addOrder(Order.desc(Trcourseplan.PROP_TRCP_COURSE_NO + "."
                    + Trcourse.PROP_TRC_NO));
        }

        checkOrderMethod(detachedCriteria, page.getOrder());

        detachedCriteria.add(Restrictions.eq(Trcourseplan.PROP_TRCP_STATUS, new Integer(1)));

        Date now = new Date();
        detachedCriteria.add(Restrictions.or(Restrictions
                .isNull(Trcourseplan.PROP_TRCP_ENROLL_START_DATE), Restrictions
                .le(Trcourseplan.PROP_TRCP_ENROLL_START_DATE, now)));

        detachedCriteria.add(Restrictions.or(Restrictions
                .isNull(Trcourseplan.PROP_TRCP_ENROLL_END_DATE), Restrictions
                .ge(Trcourseplan.PROP_TRCP_ENROLL_END_DATE, now)));

        if ((trcName != null) && (trcName.length() > 0)) {
            if (!page.getOrder().startsWith("trcpCourseNo.trcName")) {
                detachedCriteria.createAlias("trcpCourseNo", "trcpC");
                detachedCriteria.add(Restrictions.like("trcpC.trcName", "%"
                        + replaceStr(trcName.trim()) + "%"));
            } else {
                detachedCriteria.add(Restrictions.like("ord.trcName", "%"
                        + replaceStr(trcName.trim()) + "%"));
            }
        }
        if ((trcpTeacher != null) && (trcpTeacher.length() > 0)) {
            detachedCriteria.add(Restrictions.like(Trcourseplan.PROP_TRCP_TEACHER, "%"
                    + trcpTeacher.trim() + "%"));
        }

        if ((trcpLocation != null) && (trcpLocation.length() > 0)) {
            detachedCriteria.add(Restrictions.like(Trcourseplan.PROP_TRCP_LOCATION, "%"
                    + trcpLocation.trim() + "%"));
        }

        DetachedCriteria detachedCriteria2 = DetachedCriteria.forClass(Tremployeeplan.class);
        detachedCriteria2.add(Restrictions.eq(Tremployeeplan.PROP_TRP_TRAINEE_NO + "."
                + Employee.PROP_ID, trainee.getId()));

        detachedCriteria2.setProjection(Projections.property(Tremployeeplan.PROP_TRP_TRCP + "."
                + Trcourseplan.PROP_TRCP_ID));

        List appliedTrcpId = this.tremployeeplanDAO.findByCriteria(detachedCriteria2);
        if ((appliedTrcpId != null) && (appliedTrcpId.size() > 0)) {
            detachedCriteria.add(Restrictions.not(Restrictions.in(Trcourseplan.PROP_TRCP_ID,
                                                                  appliedTrcpId)));
        }

        String hql = "select departmentName from Department where id='"
                + trainee.getEmpDeptNo().getId() + "'";
        String dept = (String) (String) this.trcourseplanDAO.exeHqlList(hql).get(0);
        detachedCriteria.add(Restrictions.or(Restrictions.like(Trcourseplan.PROP_TRCP_DEPT_LIMIT,
                                                               ", " + dept + ", ",
                                                               MatchMode.ANYWHERE), Restrictions
                .eq(Trcourseplan.PROP_TRCP_DEPT_LIMIT, "")));

        int size = this.trcourseplanDAO.findRowCountByCriteria(detachedCriteria);

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
        return this.trcourseplanDAO.findByCriteria(detachedCriteria, pageSize, page
                .getCurrentPage());
    }

    public List search(String trcName, String trcpLocation, Integer trcpStatus, Pager page) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Trcourseplan.class);

        for (int i = 0; i < this.fetch.length; ++i) {
            detachedCriteria.setFetchMode(this.fetch[i], FetchMode.JOIN);
        }
        checkOrderMethod(detachedCriteria, page.getOrder());

        if (trcName != null) {
            if (!page.getOrder().startsWith("trcpCourseNo.trcName"))
                detachedCriteria.createAlias("trcpCourseNo", "ord");
            detachedCriteria.add(Restrictions.like("ord.trcName", "%" + replaceStr(trcName.trim())
                    + "%"));
        }
        if (trcpLocation != null) {
            detachedCriteria.add(Restrictions.like(Trcourseplan.PROP_TRCP_LOCATION, "%"
                    + trcpLocation.trim() + "%"));
        }
        if (trcpStatus != null) {
            detachedCriteria.add(Restrictions.eq(Trcourseplan.PROP_TRCP_STATUS, trcpStatus));
        }
        int size = this.trcourseplanDAO.findRowCountByCriteria(detachedCriteria);

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
        List result = this.trcourseplanDAO.findByCriteria(detachedCriteria, pageSize, page
                .getCurrentPage());
        return result;
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
                detachedCriteria.createAlias(pram[0].substring(0, pram[0].lastIndexOf(".")), "ord",
                                             1);
                orde = "ord." + pram[0].substring(pram[0].lastIndexOf(".") + 1);
            }
            if ((pram.length == 2) && (pram[1].equalsIgnoreCase("up")))
                detachedCriteria.addOrder(Order.asc(orde));
            else
                detachedCriteria.addOrder(Order.desc(orde));
        } else {
            detachedCriteria.addOrder(Order.desc(Trcourseplan.PROP_TRCP_LAST_CHANGE_TIME));
        }
    }

    public String replaceStr(String str) {
        return str.replace("%", "\\%");
    }

    public ITrcourseplanDAO getTrcourseplanDAO() {
        return this.trcourseplanDAO;
    }

    public void setTrcourseplanDAO(ITrcourseplanDAO trcourseplanDAO) {
        this.trcourseplanDAO = trcourseplanDAO;
    }

    public ITremployeeplanDAO getTremployeeplanDAO() {
        return this.tremployeeplanDAO;
    }

    public void setTremployeeplanDAO(ITremployeeplanDAO tremployeeplanDAO) {
        this.tremployeeplanDAO = tremployeeplanDAO;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.training.bo.TrcourseplanBOImpl JD-Core Version: 0.5.4
 */