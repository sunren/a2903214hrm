package com.hr.profile.bo;

import com.hr.profile.dao.IEmpJobHisDao;
import com.hr.profile.domain.Emphistoryjob;
import com.hr.profile.domain.Employee;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class EmpJobHisBoImpl implements IEmpJobHisBo {
    private IEmpJobHisDao empJobHisDao;

    public String insert(Emphistoryjob emphis) {
        this.empJobHisDao.saveObject(emphis);
        return emphis.getEhjId();
    }

    public List<Emphistoryjob> search(String employeeId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Emphistoryjob.class);
        detachedCriteria.setFetchMode(Emphistoryjob.PROP_EMPLOYEE, FetchMode.DEFAULT);
        detachedCriteria.add(Restrictions.eq(Emphistoryjob.PROP_EMPLOYEE + "." + Employee.PROP_ID,
                                             employeeId));
        detachedCriteria.addOrder(Order.desc(Emphistoryjob.PROP_EHJ_DATE_START));
        List result = this.empJobHisDao.findByCriteria(detachedCriteria);
        return result;
    }

    public void delete(Emphistoryjob ehj) {
        this.empJobHisDao.deleteObject(ehj);
    }

    public void update(Emphistoryjob emphis) {
        this.empJobHisDao.saveOrupdate(emphis);
    }

    public List<Emphistoryjob> search(Emphistoryjob empHis) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Emphistoryjob.class);
        detachedCriteria.setFetchMode(Emphistoryjob.PROP_EMPLOYEE, FetchMode.DEFAULT);
        detachedCriteria.add(Restrictions.eq(Emphistoryjob.PROP_EHJ_ID, empHis.getEhjId()));
        detachedCriteria.addOrder(Order.desc(Emphistoryjob.PROP_EHJ_DATE_START));
        List emphisObj = this.empJobHisDao.findByCriteria(detachedCriteria);
        return emphisObj;
    }

    public IEmpJobHisDao getEmpJobHisDao() {
        return this.empJobHisDao;
    }

    public void setEmpJobHisDao(IEmpJobHisDao empJobHisDao) {
        this.empJobHisDao = empJobHisDao;
    }

    public Emphistoryjob load(String ehjId, String[] fetches) {
        return (Emphistoryjob) this.empJobHisDao.loadObject(Emphistoryjob.class, ehjId, fetches,
                                                            new boolean[0]);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.EmpJobHisBoImpl JD-Core Version: 0.5.4
 */