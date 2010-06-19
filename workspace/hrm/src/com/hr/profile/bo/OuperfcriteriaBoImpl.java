package com.hr.profile.bo;

import com.hr.configuration.domain.Department;
import com.hr.profile.dao.IOuperfcriteriaDao;
import com.hr.profile.domain.Ouperfcriteria;
import com.hr.profile.domain.PositionBase;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class OuperfcriteriaBoImpl implements IOuperfcriteriaBo {
    private IOuperfcriteriaDao ouperfcriteriaDao;

    public IOuperfcriteriaDao getOuperfcriteriaDao() {
        return this.ouperfcriteriaDao;
    }

    public void setOuperfcriteriaDao(IOuperfcriteriaDao ouperfcriteriaDao) {
        this.ouperfcriteriaDao = ouperfcriteriaDao;
    }

    public List<Ouperfcriteria> getPbPerfcriteria(String pbId) {
        DetachedCriteria dc = DetachedCriteria.forClass(Ouperfcriteria.class);
        dc.add(Restrictions.eq(Ouperfcriteria.PROP_OUP_PB, new PositionBase(pbId)));
        dc.addOrder(Order.asc(Ouperfcriteria.PROP_OUP_SORT_ID));
        return this.ouperfcriteriaDao.findByCriteria(dc);
    }

    public List<Ouperfcriteria> getDeptPerfcriteria(String deptId) {
        DetachedCriteria dc = DetachedCriteria.forClass(Ouperfcriteria.class);
        dc.add(Restrictions.eq(Ouperfcriteria.PROP_OUP_DEPT, new Department(deptId)));
        dc.addOrder(Order.asc(Ouperfcriteria.PROP_OUP_SORT_ID));
        return this.ouperfcriteriaDao.findByCriteria(dc);
    }

    public boolean addPbPerfcr(Ouperfcriteria pbPerfcr) {
        boolean duplicateName = duplicateName(pbPerfcr, "add");

        if (!duplicateName) {
            pbPerfcr.setOupSortId(Integer.valueOf(0));
            this.ouperfcriteriaDao.saveObject(pbPerfcr);
            return true;
        }
        return false;
    }

    private boolean duplicateName(Ouperfcriteria perfcr, String operate) {
        DetachedCriteria dc = DetachedCriteria.forClass(Ouperfcriteria.class);
        if (perfcr.getOupPb() == null)
            dc.add(Restrictions.eq(Ouperfcriteria.PROP_OUP_DEPT, perfcr.getOupPb()));
        else {
            dc.add(Restrictions.eq(Ouperfcriteria.PROP_OUP_PB, new PositionBase(perfcr.getOupPb()
                    .getId())));
        }

        dc.add(Restrictions.eq(Ouperfcriteria.PROP_OUP_NAME, perfcr.getOupName()));
        if (operate.equals("update")) {
            dc
                    .add(Restrictions.not(Restrictions.eq(Ouperfcriteria.PROP_ID, perfcr.getId()
                            .trim())));
        }

        List result = this.ouperfcriteriaDao.findByCriteria(dc);

        return (result != null) && (result.size() != 0);
    }

    public boolean updatePbPerfcr(Ouperfcriteria pbPerfcr) {
        boolean duplicateName = duplicateName(pbPerfcr, "update");

        if (!duplicateName) {
            Ouperfcriteria old = (Ouperfcriteria) this.ouperfcriteriaDao
                    .loadObject(Ouperfcriteria.class, pbPerfcr.getId(), null, new boolean[0]);

            old.setOupName(pbPerfcr.getOupName());
            old.setOupRate(pbPerfcr.getOupRate());
            this.ouperfcriteriaDao.updateObject(old);
            return true;
        }
        return false;
    }

    public boolean delPbPerfcr(String id) {
        try {
            this.ouperfcriteriaDao.deleteObject(Ouperfcriteria.class, id);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void savePbPerfcrOrder(String[] ids) {
        if (ids == null)
            return;
        int sort = 1;
        int len = ids.length;
        for (int i = 0; i < len; ++i) {
            this.ouperfcriteriaDao.exeHql("update Ouperfcriteria set oupSortId=" + sort
                    + " where id ='" + ids[i] + "'");

            ++sort;
        }
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.OuperfcriteriaBoImpl JD-Core Version: 0.5.4
 */