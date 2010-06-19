package com.hr.profile.bo;

import com.hr.configuration.domain.Department;
import com.hr.profile.dao.IEmpHistOrgDao;
import com.hr.profile.domain.EmpHistOrg;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.PositionBase;
import com.hr.spring.SpringBeanFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class EmpHistOrgBoImpl implements IEmpHistOrgBo {
    private IEmpHistOrgDao empHistOrgDao;

    public IEmpHistOrgDao getEmpHistOrgDao() {
        return this.empHistOrgDao;
    }

    public void setEmpHistOrgDao(IEmpHistOrgDao empHistOrgDao) {
        this.empHistOrgDao = empHistOrgDao;
    }

    public int getEhosNum(Date queryDate, String deptId) {
        DetachedCriteria dc = DetachedCriteria.forClass(EmpHistOrg.class);
        dc.add(Restrictions.eq(EmpHistOrg.PROP_EHO_DEPT_NO, new Department(deptId)));
        dc.add(Restrictions.le(EmpHistOrg.PROP_EHO_VALID_FROM, queryDate));
        dc.add(Restrictions.or(Restrictions.ge(EmpHistOrg.PROP_EHO_VALID_TO, queryDate),
                               Restrictions.isNull(EmpHistOrg.PROP_EHO_VALID_TO)));

        return this.empHistOrgDao.findByCriteria(dc).size();
    }

    @SuppressWarnings("unchecked")
    public Employee getDeptHead(Date queryDate, String pbId) {
        DetachedCriteria dc = DetachedCriteria.forClass(EmpHistOrg.class);
        dc.setFetchMode(EmpHistOrg.PROP_EHO_EMP_NO, FetchMode.JOIN);
        dc.add(Restrictions.eq(EmpHistOrg.PROP_EHO_PB_NO, new PositionBase(pbId)));
        dc.add(Restrictions.le(EmpHistOrg.PROP_EHO_VALID_FROM, queryDate));
        dc.add(Restrictions.or(Restrictions.ge(EmpHistOrg.PROP_EHO_VALID_TO, queryDate),
                               Restrictions.isNull(EmpHistOrg.PROP_EHO_VALID_TO)));

        List<EmpHistOrg> ehoList = this.empHistOrgDao.findByCriteria(dc);
        if ((ehoList != null) && (ehoList.size() == 1)) {
            return ((EmpHistOrg) ehoList.get(0)).getEhoEmpNo();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Map<String, EmpHistOrg> getEmphistorgOfDept(List<Department> deptList) {
        DetachedCriteria dc = DetachedCriteria.forClass(EmpHistOrg.class);
        dc.add(Restrictions.in(EmpHistOrg.PROP_EHO_DEPT_NO, deptList));
        dc.add(Restrictions.isNull(EmpHistOrg.PROP_EHO_VALID_TO));

        List<EmpHistOrg> ehoList = this.empHistOrgDao.findByCriteria(dc);
        Map<String, EmpHistOrg> emphistorgMap = new HashMap<String, EmpHistOrg>();
        for (EmpHistOrg histOrg : ehoList) {
            emphistorgMap.put(histOrg.getEhoEmpNo().getId(), histOrg);
        }

        return emphistorgMap;
    }

    @SuppressWarnings("unchecked")
    public EmpHistOrg getLatestEmphistorg(Employee emp) {
        DetachedCriteria dc = DetachedCriteria.forClass(EmpHistOrg.class);
        dc.add(Restrictions.eq(EmpHistOrg.PROP_EHO_EMP_NO, emp));
        dc.add(Restrictions.isNull(EmpHistOrg.PROP_EHO_VALID_TO));
        List<EmpHistOrg> ehoList = this.empHistOrgDao.findByCriteria(dc);

        if ((ehoList != null) && (ehoList.size() > 0))
            return (EmpHistOrg) ehoList.get(0);
        return null;
    }

    public List<?> exeHqlList(String hql) {
        return this.empHistOrgDao.exeHqlList(hql);
    }

    @SuppressWarnings("unchecked")
    public boolean isExistPb(String pbId) {
        DetachedCriteria dc = DetachedCriteria.forClass(EmpHistOrg.class);
        dc.add(Restrictions.eq(EmpHistOrg.PROP_EHO_PB_NO, new PositionBase(pbId)));
        List<EmpHistOrg> ehoList = this.empHistOrgDao.findByCriteria(dc);

        return (ehoList != null) && (ehoList.size() != 0);
    }

    public void saveEmpHistOrg(Employee emp, Department newDept, PositionBase newPB) {
        IEmpHistOrgBo emphistorgBo = (IEmpHistOrgBo) SpringBeanFactory.getBean("empHistOrgBo");

        EmpHistOrg latestHistOrg = emphistorgBo.getLatestEmphistorg(emp);

        if ((latestHistOrg != null) && (latestHistOrg.getEhoValidTo() == null)) {
            latestHistOrg.setEhoValidTo(new Date());
            latestHistOrg.setEhoIsLatest(new Integer(0));
        }

        EmpHistOrg newHistOrg = new EmpHistOrg(null, emp, (newDept == null) ? emp.getEmpDeptNo()
                : newDept, (newPB == null) ? emp.getEmpPbNo() : newPB, new Date(), new Integer(1));

        if (latestHistOrg != null)
            this.empHistOrgDao.saveOrupdate(latestHistOrg);
        if (newHistOrg == null)
            return;
        this.empHistOrgDao.saveOrupdate(newHistOrg);
    }

    public void saveLatestEmpHistOrg(Employee emp, Department newDept, PositionBase newPB) {
        IEmpHistOrgBo emphistorgBo = (IEmpHistOrgBo) SpringBeanFactory.getBean("empHistOrgBo");

        EmpHistOrg latestHistOrg = emphistorgBo.getLatestEmphistorg(emp);

        if (latestHistOrg != null) {
            latestHistOrg.setEhoDeptNo(newDept);
            latestHistOrg.setEhoPbNo(newPB);

            this.empHistOrgDao.saveOrupdate(latestHistOrg);
        } else {
            EmpHistOrg newHistOrg = new EmpHistOrg(null, emp, (newDept == null) ? emp
                    .getEmpDeptNo() : newDept, (newPB == null) ? emp.getEmpPbNo() : newPB,
                    new Date(), new Integer(1));
            this.empHistOrgDao.saveOrupdate(newHistOrg);
        }
    }

    public List<EmpHistOrg> gernerateEmpHistOrg(Employee emp, Department newDept,
            PositionBase newPB, Date date) {
        IEmpHistOrgBo emphistorgBo = (IEmpHistOrgBo) SpringBeanFactory.getBean("empHistOrgBo");

        EmpHistOrg latestHistOrg = emphistorgBo.getLatestEmphistorg(emp);

        if ((latestHistOrg != null) && (latestHistOrg.getEhoValidTo() == null)) {
            latestHistOrg.setEhoValidTo((date == null) ? new Date() : date);
            latestHistOrg.setEhoIsLatest(new Integer(0));
        }

        EmpHistOrg newHistOrg = new EmpHistOrg(null, emp, (newDept == null) ? emp.getEmpDeptNo()
                : newDept, (newPB == null) ? emp.getEmpPbNo() : newPB, new Date(), new Integer(1));

        List<EmpHistOrg> list = new ArrayList<EmpHistOrg>();
        if (latestHistOrg != null)
            list.add(latestHistOrg);
        if (newHistOrg != null)
            list.add(newHistOrg);

        return list;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.EmpHistOrgBoImpl JD-Core Version: 0.5.4
 */