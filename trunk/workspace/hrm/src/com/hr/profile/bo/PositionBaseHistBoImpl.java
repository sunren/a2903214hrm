package com.hr.profile.bo;

import com.hr.configuration.domain.Department;
import com.hr.profile.dao.IPositionBaseHistDao;
import com.hr.profile.domain.PositionBase;
import com.hr.profile.domain.PositionBaseHist;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class PositionBaseHistBoImpl implements IPositionBaseHistBo {
    private IPositionBaseHistDao positionBaseHistDao;

    public IPositionBaseHistDao getPositionBaseHistDao() {
        return this.positionBaseHistDao;
    }

    public void setPositionBaseHistDao(IPositionBaseHistDao positionBaseHistDao) {
        this.positionBaseHistDao = positionBaseHistDao;
    }

    public List<PositionBaseHist> getRequirePbhList(Date queryDate, String deptId) {
        DetachedCriteria dc = DetachedCriteria.forClass(PositionBaseHist.class);

        dc.add(Restrictions.eq(PositionBaseHist.PROP_PBHI_PB_STATUS, Integer.valueOf(1)));
        dc.add(Restrictions.eq(PositionBaseHist.PROP_PBHI_DEPT_ID, new Department(deptId)));
        dc.add(Restrictions.le(PositionBaseHist.PROP_PBHI_VALID_FROM, queryDate));
        dc.add(Restrictions.or(Restrictions.ge(PositionBaseHist.PROP_PBHI_VALID_TO, queryDate),
                               Restrictions.isNull(PositionBaseHist.PROP_PBHI_VALID_TO)));

        return this.positionBaseHistDao.findByCriteria(dc);
    }

    public String getDeptChargePb(Date queryDate, String deptId) {
        DetachedCriteria dc = DetachedCriteria.forClass(PositionBaseHist.class);
        dc.add(Restrictions.eq(PositionBaseHist.PROP_PBHI_IN_CHARGE, Integer.valueOf(1)));
        dc.add(Restrictions.eq(PositionBaseHist.PROP_PBHI_PB_STATUS, Integer.valueOf(1)));
        dc.add(Restrictions.eq(PositionBaseHist.PROP_PBHI_DEPT_ID, new Department(deptId)));
        dc.add(Restrictions.le(PositionBaseHist.PROP_PBHI_VALID_FROM, queryDate));
        dc.add(Restrictions.or(Restrictions.ge(PositionBaseHist.PROP_PBHI_VALID_TO, queryDate),
                               Restrictions.isNull(PositionBaseHist.PROP_PBHI_VALID_TO)));

        List pbList = this.positionBaseHistDao.findByCriteria(dc);
        if ((pbList != null) && (pbList.size() == 1)) {
            return ((PositionBaseHist) pbList.get(0)).getPbhiPbId().getId();
        }
        return null;
    }

    public PositionBaseHist getLatestPBHist(PositionBase pb) {
        DetachedCriteria dc = DetachedCriteria.forClass(PositionBaseHist.class);
        dc.add(Restrictions.eq(PositionBaseHist.PROP_PBHI_PB_ID, pb));
        dc.add(Restrictions.isNull(PositionBaseHist.PROP_PBHI_VALID_TO));

        List pbHistList = this.positionBaseHistDao.findByCriteria(dc);
        if (pbHistList.size() > 0) {
            return (PositionBaseHist) pbHistList.get(0);
        }
        return null;
    }

    public List<PositionBaseHist> getPbHistsOfPb(String pbId) {
        DetachedCriteria dc = DetachedCriteria.forClass(PositionBaseHist.class);
        dc.add(Restrictions.eq(PositionBaseHist.PROP_PBHI_PB_ID, new PositionBase(pbId)));
        return this.positionBaseHistDao.findByCriteria(dc);
    }

    public List exeHqlList(String hql) {
        return this.positionBaseHistDao.exeHqlList(hql);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.PositionBaseHistBoImpl JD-Core Version: 0.5.4
 */