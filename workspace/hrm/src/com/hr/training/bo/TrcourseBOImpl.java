package com.hr.training.bo;

import com.hr.training.dao.ITrcourseDAO;
import com.hr.training.domain.Trcourse;
import com.hr.training.domain.Trtype;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.Pager;
import java.util.Date;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class TrcourseBOImpl implements ITrcourseBO {
    private ITrcourseDAO trcourseDAO;
    private String[] fetch;

    public TrcourseBOImpl() {
        this.fetch = new String[] { Trcourse.PROP_TRC_CREATE_BY, Trcourse.PROP_TRC_LAST_CHANGE_BY,
                Trcourse.PROP_TRC_TYPE };
    }

    public void addTrc(Trcourse trc) {
        Date now = new Date();
        trc.setTrcCreateTime(now);
        trc.setTrcLastChangeTime(now);
        this.trcourseDAO.saveObject(trc);
    }

    public void deleteTrc(String trcNo) {
        this.trcourseDAO.deleteObject(loadTrc(trcNo));
    }

    public Trcourse loadTrc(String trcNo) {
        return (Trcourse) this.trcourseDAO.loadObject(Trcourse.class, trcNo, this.fetch,
                                                      new boolean[0]);
    }

    public void updateTrc(Trcourse trc) {
        trc.setTrcLastChangeTime(new Date());
        this.trcourseDAO.saveOrupdate(trc);
    }

    public List search(Trcourse trc, Pager page) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Trcourse.class);
        for (int i = 0; i < this.fetch.length; ++i) {
            detachedCriteria.setFetchMode(this.fetch[i], FetchMode.JOIN);
        }

        checkOrderMethod(detachedCriteria, page.getOrder());

        if ((trc != null) && (trc.getTrcNo() != null))
            detachedCriteria.add(Restrictions.like(Trcourse.PROP_TRC_NO, "%"
                    + replaceStr(trc.getTrcNo().trim()) + "%"));
        if ((trc != null) && (trc.getTrcName() != null)) {
            detachedCriteria.add(Restrictions.like(Trcourse.PROP_TRC_NAME, "%"
                    + replaceStr(trc.getTrcName().trim()) + "%"));
        }
        if ((trc != null) && (trc.getTrcType() != null) && (trc.getTrcType().getTrtNo() != null)) {
            detachedCriteria.add(Restrictions.like(Trcourse.PROP_TRC_TYPE + "."
                    + Trtype.PROP_TRT_NO, "%" + replaceStr(trc.getTrcType().getTrtNo()) + "%"));
        }

        if ((trc != null) && (trc.getTrcStatus() != null)) {
            detachedCriteria.add(Restrictions.eq(Trcourse.PROP_TRC_STATUS, trc.getTrcStatus()));
        }
        int size = this.trcourseDAO.findRowCountByCriteria(detachedCriteria);

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
        List result = this.trcourseDAO.findByCriteria(detachedCriteria, pageSize, page
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
                detachedCriteria.createAlias(pram[0].substring(0, pram[0].lastIndexOf(".")), "ord");
                orde = "ord." + pram[0].substring(pram[0].lastIndexOf(".") + 1);
            }
            if ((pram.length == 2) && (pram[1].equalsIgnoreCase("up")))
                detachedCriteria.addOrder(Order.asc(orde));
            else
                detachedCriteria.addOrder(Order.desc(orde));
        } else {
            detachedCriteria.addOrder(Order.desc("trcLastChangeTime"));
        }
    }

    public ITrcourseDAO getTrcourseDAO() {
        return this.trcourseDAO;
    }

    public void setTrcourseDAO(ITrcourseDAO trcourseDAO) {
        this.trcourseDAO = trcourseDAO;
    }

    public List loadAll() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Trcourse.class);
        detachedCriteria.setFetchMode(Trcourse.PROP_TRC_CREATE_BY, FetchMode.JOIN);
        detachedCriteria.setFetchMode(Trcourse.PROP_TRC_LAST_CHANGE_BY, FetchMode.JOIN);
        detachedCriteria.setFetchMode(Trcourse.PROP_TRC_TYPE, FetchMode.JOIN);
        detachedCriteria.addOrder(Order.asc(Trcourse.PROP_TRC_NO));
        return this.trcourseDAO.findByCriteria(detachedCriteria);
    }

    public String replaceStr(String str) {
        return str.replace("%", "\\%");
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.training.bo.TrcourseBOImpl JD-Core Version: 0.5.4
 */