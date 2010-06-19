package com.hr.profile.bo;

import com.hr.profile.dao.IEmpRelationsDao;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Emprelations;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class EmpRelationsBoImpl implements IEmpRelationsBo {
    private IEmpRelationsDao empRelationsDao;

    public String insert(Emprelations empRelations) {
        this.empRelationsDao.saveObject(empRelations);
        return empRelations.getErelId();
    }

    public List<Emprelations> search(String employeeId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Emprelations.class);
        detachedCriteria.setFetchMode(Emprelations.PROP_EREL_EMPLOYEE, FetchMode.DEFAULT);
        detachedCriteria.add(Restrictions.eq(Emprelations.PROP_EREL_EMPLOYEE + "."
                + Employee.PROP_ID, employeeId));
        List result = this.empRelationsDao.findByCriteria(detachedCriteria);
        return result;
    }

    public void delete(Emprelations erel) {
        this.empRelationsDao.deleteObject(erel);
    }

    public void update(Emprelations empRelations) {
        this.empRelationsDao.updateObject(empRelations);
    }

    public List<Emprelations> search(Emprelations empRelations) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Emprelations.class);
        detachedCriteria.setFetchMode(Emprelations.PROP_EREL_EMPLOYEE, FetchMode.DEFAULT);
        detachedCriteria.add(Restrictions.eq(Emprelations.PROP_EREL_ERELID, empRelations
                .getErelId()));
        List empRelationsObj = this.empRelationsDao.findByCriteria(detachedCriteria);
        return empRelationsObj;
    }

    public IEmpRelationsDao getEmpRelationsDao() {
        return this.empRelationsDao;
    }

    public void setEmpRelationsDao(IEmpRelationsDao empRelationsDao) {
        this.empRelationsDao = empRelationsDao;
    }

    public Emprelations load(String ehrId, String[] fetches) {
        return (Emprelations) this.empRelationsDao.loadObject(Emprelations.class, ehrId, fetches,
                                                              new boolean[0]);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.EmpRelationsBoImpl JD-Core Version: 0.5.4
 */