package com.hr.profile.bo;

import com.hr.base.FileOperate;
import com.hr.profile.dao.IEmpEduHisDao;
import com.hr.profile.domain.Emphistoryedu;
import com.hr.profile.domain.Employee;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class EmpEduHisBoImpl implements IEmpEduHisBo {
    private IEmpEduHisDao empEduHisDao;

    public String insert(Emphistoryedu emphis) {
        this.empEduHisDao.saveObject(emphis);
        return emphis.getEheId();
    }

    public List<Emphistoryedu> search(String employeeId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Emphistoryedu.class);
        detachedCriteria.setFetchMode(Emphistoryedu.PROP_EMPLOYEE, FetchMode.DEFAULT);
        detachedCriteria.add(Restrictions.eq(Emphistoryedu.PROP_EMPLOYEE + "." + Employee.PROP_ID,
                                             employeeId));
        detachedCriteria.addOrder(Order.desc(Emphistoryedu.PROP_EHE_DATE_START));
        List result = this.empEduHisDao.findByCriteria(detachedCriteria);
        return result;
    }

    public boolean deleteAttach(String contractId, String fileName) {
        try {
            FileOperate.deleteFile("sys.profile.file.path", fileName);
            String hql = "update Emphistoryedu set eheAttachment=null where eheId='" + contractId
                    + "'";
            this.empEduHisDao.exeHql(hql);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public void delete(String eheId) {
        String hql = "delete from Emphistoryedu where eheId='" + eheId + "'";
        this.empEduHisDao.exeHql(hql);
    }

    public void update(Emphistoryedu emphis) {
        this.empEduHisDao.saveOrupdate(emphis);
    }

    public List<Emphistoryedu> search(Emphistoryedu empHis) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Emphistoryedu.class);
        detachedCriteria.setFetchMode(Emphistoryedu.PROP_EMPLOYEE, FetchMode.DEFAULT);
        detachedCriteria.add(Restrictions.eq(Emphistoryedu.PROP_EHE_ID, empHis.getEheId()));
        detachedCriteria.addOrder(Order.desc(Emphistoryedu.PROP_EHE_DATE_START));
        List emphisObj = this.empEduHisDao.findByCriteria(detachedCriteria);
        return emphisObj;
    }

    public Emphistoryedu load(String eheId, String[] fetches) {
        return (Emphistoryedu) this.empEduHisDao.loadObject(Emphistoryedu.class, eheId, fetches,
                                                            new boolean[0]);
    }

    public IEmpEduHisDao getEmpEduHisDao() {
        return this.empEduHisDao;
    }

    public void setEmpEduHisDao(IEmpEduHisDao empEduHisDao) {
        this.empEduHisDao = empEduHisDao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.EmpEduHisBoImpl JD-Core Version: 0.5.4
 */