package com.hr.profile.bo;

import com.hr.base.FileOperate;
import com.hr.profile.dao.IEmpTrainHisDao;
import com.hr.profile.domain.Emphistorytrain;
import com.hr.profile.domain.Employee;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class EmpTrainHisBoImpl implements IEmpTrainHisBo {
    private IEmpTrainHisDao empTrainHisDao;

    public String insert(Emphistorytrain emphis) {
        this.empTrainHisDao.saveObject(emphis);
        return emphis.getEhtId();
    }

    public List<Emphistorytrain> search(String employeeId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Emphistorytrain.class);
        detachedCriteria.setFetchMode(Emphistorytrain.PROP_EMPLOYEE, FetchMode.DEFAULT);
        detachedCriteria.add(Restrictions
                .eq(Emphistorytrain.PROP_EMPLOYEE + "." + Employee.PROP_ID, employeeId));
        detachedCriteria.addOrder(Order.desc(Emphistorytrain.PROP_EHT_START_DATE));
        List result = this.empTrainHisDao.findByCriteria(detachedCriteria);
        return result;
    }

    public void delete(String ehtId) {
        String hql = "delete from Emphistorytrain where ehtId='" + ehtId + "'";
        this.empTrainHisDao.exeHql(hql);
    }

    public void update(Emphistorytrain emphis) {
        this.empTrainHisDao.saveOrupdate(emphis);
    }

    public List<Emphistorytrain> search(Emphistorytrain empHis) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Emphistorytrain.class);
        detachedCriteria.setFetchMode(Emphistorytrain.PROP_EMPLOYEE, FetchMode.DEFAULT);
        detachedCriteria.add(Restrictions.eq(Emphistorytrain.PROP_EHT_ID, empHis.getEhtId()));
        detachedCriteria.addOrder(Order.desc(Emphistorytrain.PROP_EHT_START_DATE));
        List emphisObj = this.empTrainHisDao.findByCriteria(detachedCriteria);
        return emphisObj;
    }

    public boolean deleteAttach(String contractId, String fileName) {
        try {
            FileOperate.deleteFile("sys.profile.file.path", fileName);
            String hql = "update Emphistorytrain set ehtAttatchment=null where ehtId='"
                    + contractId + "'";
            this.empTrainHisDao.exeHql(hql);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public IEmpTrainHisDao getEmpTrainHisDao() {
        return this.empTrainHisDao;
    }

    public void setEmpTrainHisDao(IEmpTrainHisDao empTrainHisDao) {
        this.empTrainHisDao = empTrainHisDao;
    }

    public Emphistorytrain load(String ehtId, String[] fetches) {
        return (Emphistorytrain) this.empTrainHisDao.loadObject(Emphistorytrain.class, ehtId,
                                                                fetches, new boolean[0]);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.EmpTrainHisBoImpl JD-Core Version: 0.5.4
 */