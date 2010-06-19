package com.hr.profile.bo;

import com.hr.base.FileOperate;
import com.hr.profile.dao.IEmpEvalDao;
import com.hr.profile.domain.Empeval;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DateUtil;
import com.hr.util.Pager;
import java.util.Date;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class EmpEvalBoImpl implements IEmpEvalBo {
    IEmpEvalDao empEvalDao;

    public List<Empeval> findEvalByEmpNo(String empNo) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empeval.class);
        detachedCriteria.setFetchMode(Empeval.PROP_EMPLOYEE_BY_EE_EMP_NO, FetchMode.JOIN);
        detachedCriteria.setFetchMode(Empeval.PROP_EMPLOYEE_BY_EE_MANAGER, FetchMode.JOIN);
        detachedCriteria.setFetchMode(Empeval.PROP_DEPARTMENT, FetchMode.JOIN);
        detachedCriteria.setFetchMode(Empeval.PROP_EE_PB_NO, FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq(Empeval.PROP_EMPLOYEE_BY_EE_EMP_NO + "."
                + Employee.PROP_ID, empNo));
        detachedCriteria.addOrder(Order.desc(Empeval.PROP_EE_START_DATE));
        List result = this.empEvalDao.findByCriteria(detachedCriteria);
        return result;
    }

    public List<Empeval> findEval(DetachedCriteria dc, Pager page, String searchOrExport) {
        if ((page == null) || ("export".equals(searchOrExport))) {
            return this.empEvalDao.findByCriteria(dc);
        }

        page.splitPage(dc);
        return this.empEvalDao.findByCriteria(dc, page.getPageSize(), page.getCurrentPage());
    }

    public Empeval getById(String evalId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empeval.class);
        detachedCriteria.setFetchMode(Empeval.PROP_EMPLOYEE_BY_EE_EMP_NO, FetchMode.JOIN);
        detachedCriteria.setFetchMode(Empeval.PROP_EMPLOYEE_BY_EE_MANAGER, FetchMode.JOIN);
        detachedCriteria.setFetchMode(Empeval.PROP_DEPARTMENT, FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq(Empeval.PROP_EE_ID, evalId));
        List result = this.empEvalDao.findByCriteria(detachedCriteria);
        if (result.size() == 0) {
            return null;
        }
        return (Empeval) result.get(0);
    }

    public boolean insertEval(Empeval empEval) {
        try {
            this.empEvalDao.saveObject(empEval);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public String insert(Empeval empEval) {
        this.empEvalDao.saveObject(empEval);
        IEmployeeBo employeeBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        Employee employee = employeeBo.loadEmp(empEval.getEmployeeByEeEmpNo().getId(), null);
        employee.setEmpPerfRate(empEval.getEeRate());
        employee.setEmpPerfDate(new Date());
        employeeBo.updateEmp(employee, employee.getId());
        return empEval.getEeId();
    }

    public void delete(String eeId) {
        String hql = "delete from Empeval where eeId='" + eeId + "'";
        this.empEvalDao.exeHql(hql);
    }

    public void update(Empeval empEval, String EvalId) {
        Empeval oldEval = getById(EvalId);
        if (empEval.getEeAttachment() != null) {
            if (oldEval != null) {
                FileOperate.deleteFile("sys.profile.file.path", oldEval.getEeAttachment());
            }
            oldEval.setEeAttachment(empEval.getEeAttachment());
        } else if (oldEval != null) {
            empEval.setEeAttachment(oldEval.getEeAttachment());
        }

        oldEval.setEeComments(empEval.getEeComments());
        oldEval.setEeRate(empEval.getEeRate());
        oldEval.setEmployeeByEeManager(empEval.getEmployeeByEeManager());
        oldEval.setEeLastChangeBy(empEval.getEeLastChangeBy());
        oldEval.setEeLastChangeTime(new Date());

        oldEval.setEeLastChangeTime(empEval.getEeLastChangeTime());

        this.empEvalDao.saveOrupdate(oldEval);
        IEmployeeBo employeeBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        Employee employee = employeeBo.loadEmp(oldEval.getEmployeeByEeEmpNo().getId(), null);
        employee.setEmpPerfRate(empEval.getEeRate());
        employee.setEmpPerfDate(new Date());
        employeeBo.updateEmp(employee, employee.getId());
    }

    public boolean deleteAttach(String evalId, String fileName) {
        try {
            FileOperate.deleteFile("sys.profile.file.path", fileName);
            String hql = "update Empeval set eeAttachment=null where eeId='" + evalId + "'";
            this.empEvalDao.exeHql(hql);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public int repeatInTimeNum(Empeval empeval) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empeval.class);
        detachedCriteria.add(Restrictions.eq(Empeval.PROP_EMPLOYEE_BY_EE_EMP_NO, empeval
                .getEmployeeByEeEmpNo()));
        detachedCriteria.add(Restrictions.eq(Empeval.PROP_EE_TYPE, empeval.getEeType()));
        detachedCriteria.add(Restrictions.eq(Empeval.PROP_EE_START_DATE, DateUtil
                .convDateTimeToDate(empeval.getEeStartDate())));
        detachedCriteria.add(Restrictions.eq(Empeval.PROP_EE_END_DATE, DateUtil
                .convDateTimeToDate(empeval.getEeEndDate())));
        int result = this.empEvalDao.findRowCountByCriteria(detachedCriteria);
        return result;
    }

    public Empeval getEmpevalInTest(String empDistinctNo) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empeval.class);
        detachedCriteria.add(Restrictions.eq(Empeval.PROP_EMPLOYEE_BY_EE_EMP_NO, empDistinctNo));
        detachedCriteria.add(Restrictions.eq(Empeval.PROP_EE_TYPE, "试用期"));
        List evalList = this.empEvalDao.findByCriteria(detachedCriteria);
        if (evalList.size() > 0) {
            return (Empeval) evalList.get(0);
        }
        return null;
    }

    public boolean hasPprobationaryPeriodEvalRecord(String empId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empeval.class);
        detachedCriteria.add(Restrictions.eq("employeeByEeEmpNo.id", empId));
        detachedCriteria.add(Restrictions.eq("eeType", "试用期"));
        return this.empEvalDao.findByCriteria(detachedCriteria).size() > 0;
    }

    public IEmpEvalDao getEmpEvalDao() {
        return this.empEvalDao;
    }

    public void setEmpEvalDao(IEmpEvalDao empEvalDao) {
        this.empEvalDao = empEvalDao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.EmpEvalBoImpl JD-Core Version: 0.5.4
 */