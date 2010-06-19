package com.hr.profile.bo;

import com.hr.profile.dao.IEmpRewardDao;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Empreward;
import com.hr.util.Pager;
import java.util.Date;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class EmpRewardBoImpl implements IEmpRewardBo {
    IEmpRewardDao empRewardDao;

    public List<Empreward> findRewardByEmpNo(String empNo) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empreward.class);
        detachedCriteria.setFetchMode(Empreward.PROP_EMPLOYEE, FetchMode.JOIN);
        detachedCriteria.setFetchMode(Empreward.PROP_DEPARTMENT, FetchMode.JOIN);
        detachedCriteria.setFetchMode(Empreward.PROP_ER_PB_NO, FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq(Empreward.PROP_EMPLOYEE + "." + Employee.PROP_ID,
                                             empNo));
        detachedCriteria.addOrder(Order.desc(Empreward.PROP_ER_EXE_DATE));
        List result = this.empRewardDao.findByCriteria(detachedCriteria);
        return result;
    }

    public List<Empreward> findReward(DetachedCriteria dc, Pager page, String searchOrExport) {
        if ((page == null) || ("export".equals(searchOrExport))) {
            return this.empRewardDao.findByCriteria(dc);
        }

        page.splitPage(dc);
        return this.empRewardDao.findByCriteria(dc, page.getPageSize(), page.getCurrentPage());
    }

    public Empreward getById(String rewardId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empreward.class);
        detachedCriteria.setFetchMode(Empreward.PROP_EMPLOYEE, FetchMode.JOIN);
        detachedCriteria.setFetchMode(Empreward.PROP_DEPARTMENT, FetchMode.JOIN);
        detachedCriteria.setFetchMode(Empreward.PROP_ER_PB_NO, FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq(Empreward.PROP_ER_ID, rewardId));
        List result = this.empRewardDao.findByCriteria(detachedCriteria);
        if (result.size() == 0) {
            return null;
        }
        return (Empreward) result.get(0);
    }

    public boolean insertEmp(Empreward empReward) {
        try {
            this.empRewardDao.saveObject(empReward);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String insert(Empreward empReward) {
        this.empRewardDao.saveObject(empReward);
        return empReward.getErId();
    }

    public void delete(String etfId) {
        String hql = "delete from Empreward where erId='" + etfId + "'";
        this.empRewardDao.exeHql(hql);
    }

    public void update(Empreward empReward, String rewardId) {
        Empreward oldReward = getById(rewardId);
        oldReward.setErContent(empReward.getErContent());
        oldReward.setErExeDate(empReward.getErExeDate());
        oldReward.setErType(empReward.getErType());
        oldReward.setErForm(empReward.getErForm());
        oldReward.setErLastChangeBy(empReward.getErLastChangeBy());
        oldReward.setErLastChangeTime(new Date());
        this.empRewardDao.saveObject(oldReward);
    }

    public IEmpRewardDao getEmpRewardDao() {
        return this.empRewardDao;
    }

    public void setEmpRewardDao(IEmpRewardDao empRewardDao) {
        this.empRewardDao = empRewardDao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.EmpRewardBoImpl JD-Core Version: 0.5.4
 */