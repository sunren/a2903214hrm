package com.hr.configuration.bo;

import com.hr.configuration.dao.IBenefitTypeDAO;
import com.hr.configuration.domain.BenefitType;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class BenefitTypeBOImpl implements IBenefitTypeBO {
    private IBenefitTypeDAO benefitTypeDao;

    public List<BenefitType> findAll() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BenefitType.class);
        detachedCriteria.setFetchMode("benefitTypeLocNo", FetchMode.JOIN);
        detachedCriteria.addOrder(Order.asc("benefitTypeSortId"));
        return this.benefitTypeDao.findByCriteria(detachedCriteria);
    }

    public BenefitType searchById(String id) {
        if (id == null) {
            return null;
        }

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BenefitType.class);
        detachedCriteria.add(Restrictions.eq("id", id));
        List list = this.benefitTypeDao.findByCriteria(detachedCriteria);
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return (BenefitType) list.get(0);
    }

    public List<BenefitType> findNameByobj(BenefitType benefitType) {
        DetachedCriteria dc = DetachedCriteria.forClass(BenefitType.class);

        dc.add(Restrictions.eq("benefitTypeName", benefitType.getBenefitTypeName().trim()));

        List list = this.benefitTypeDao.findByCriteria(dc);
        return list;
    }

    public List<BenefitType> findSameNameByobj(BenefitType benefitType) {
        DetachedCriteria dc = DetachedCriteria.forClass(BenefitType.class);
        dc.add(Restrictions.eq("benefitTypeName", benefitType.getBenefitTypeName().trim()));

        dc.add(Restrictions.ne("id", benefitType.getId().trim()));
        List list = this.benefitTypeDao.findByCriteria(dc);
        return list;
    }

    public boolean addBenefitType(BenefitType benefitType) {
        try {
            this.benefitTypeDao.saveObject(benefitType);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean updateBenefitType(BenefitType benefitType) {
        if ((benefitType.getId() == null) || (benefitType.getId().equals("")))
            return false;
        try {
            this.benefitTypeDao.updateObject(benefitType);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean delBenefitType(Class<BenefitType> name, String id) {
        try {
            List check = new ArrayList();
            boolean isRefed = false;
            String hql = "select count(*) from Employee where empBenefitType='" + id + "'";
            check = this.benefitTypeDao.exeHqlList(hql);
            if (Integer.valueOf(check.get(0).toString()).intValue() > 0) {
                isRefed = true;
            }

            BenefitType benefitType = (BenefitType) this.benefitTypeDao.loadObject(name, id, null,
                                                                                   new boolean[0]);
            if ((!isRefed) && (benefitType != null)) {
                this.benefitTypeDao.deleteObject(benefitType);
                return true;
            }
            return false;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean saveBenefitTypeSortIdByBatch(String[] ids) {
        try {
            int length = ids.length;
            int sortId = 1;
            for (int i = 0; i < length; ++i) {
                this.benefitTypeDao.exeHql("update BenefitType set benefitTypeSortId=" + sortId
                        + " where id ='" + ids[i] + "'");

                ++sortId;
            }
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public IBenefitTypeDAO getBenefitTypeDao() {
        return this.benefitTypeDao;
    }

    public void setBenefitTypeDao(IBenefitTypeDAO benefitTypeDao) {
        this.benefitTypeDao = benefitTypeDao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.bo.BenefitTypeBOImpl JD-Core Version: 0.5.4
 */