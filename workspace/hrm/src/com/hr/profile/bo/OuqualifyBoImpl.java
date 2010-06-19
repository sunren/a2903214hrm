package com.hr.profile.bo;

import com.hr.profile.dao.IOuqualifyDao;
import com.hr.profile.domain.Ouqualify;
import com.hr.profile.domain.PositionBase;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class OuqualifyBoImpl implements IOuqualifyBo {
    private IOuqualifyDao ouqualifyDao;

    public IOuqualifyDao getOuqualifyDao() {
        return this.ouqualifyDao;
    }

    public void setOuqualifyDao(IOuqualifyDao ouqualifyDao) {
        this.ouqualifyDao = ouqualifyDao;
    }

    public List<Ouqualify> getpbQualifyByPbId(String pbId) {
        DetachedCriteria dc = DetachedCriteria.forClass(Ouqualify.class);
        dc.add(Restrictions.eq(Ouqualify.PROP_OUQ_PB_ID, new PositionBase(pbId)));
        dc.addOrder(Order.asc(Ouqualify.PROP_OUQ_SORT_ID));
        return this.ouqualifyDao.findByCriteria(dc);
    }

    public boolean addPbQualify(Ouqualify pbQualify) {
        boolean duplicateName = duplicateName(pbQualify, "add");

        if (!duplicateName) {
            pbQualify.setOuqSortId(Integer.valueOf(0));
            this.ouqualifyDao.saveObject(pbQualify);
            return true;
        }
        return false;
    }

    public boolean updatePbQualify(Ouqualify pbQualify) {
        boolean duplicateName = duplicateName(pbQualify, "update");

        if (!duplicateName) {
            Ouqualify old = (Ouqualify) this.ouqualifyDao.loadObject(Ouqualify.class, pbQualify
                    .getId(), null, new boolean[0]);
            old.setOuqName(pbQualify.getOuqName());
            old.setOuqDesc(pbQualify.getOuqDesc());
            this.ouqualifyDao.updateObject(old);
            return true;
        }
        return false;
    }

    private boolean duplicateName(Ouqualify pbQualify, String operate) {
        List resultList = null;
        DetachedCriteria dc = DetachedCriteria.forClass(Ouqualify.class);
        dc.add(Restrictions.eq(Ouqualify.PROP_OUQ_PB_ID, pbQualify.getOuqPbId()));
        dc.add(Restrictions.eq(Ouqualify.PROP_OUQ_NAME, pbQualify.getOuqName()));
        if (operate.equals("update")) {
            dc.add(Restrictions.not(Restrictions.eq(Ouqualify.PROP_ID, pbQualify.getId().trim())));
        }
        resultList = this.ouqualifyDao.findByCriteria(dc);

        return (resultList != null) && (resultList.size() != 0);
    }

    public boolean delPbQualify(String id) {
        try {
            this.ouqualifyDao.deleteObject(Ouqualify.class, id);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void savePbQualifyOrder(String[] ids) {
        if (ids == null)
            return;
        int sort = 1;
        int len = ids.length;
        for (int i = 0; i < len; ++i) {
            this.ouqualifyDao.exeHql("update Ouqualify set ouqSortId=" + sort + " where id ='"
                    + ids[i] + "'");

            ++sort;
        }
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.OuqualifyBoImpl JD-Core Version: 0.5.4
 */