package com.hr.common;

import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.bo.IOrgheadsBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public abstract class AbstractPrincipalManager {
    private static final int ACTIVE_STAUS = 1;

    public List<Employee> queryEmployees(String queryString) {
        return getEmployeeBo().findByCriteria(getSimpeCriteria(queryString));
    }

    public abstract List<String> queryEmployeesInChargeByOrgheads(String paramString);

    protected IEmployeeBo getEmployeeBo() {
        return (IEmployeeBo) SpringBeanFactory.getBean("empBo");
    }

    protected IOrgheadsBo getOrgheadsBo() {
        return (IOrgheadsBo) SpringBeanFactory.getBean("headsBo");
    }

    protected DetachedCriteria getSimpeCriteria(String queryString) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.setFetchMode("empDeptNo", FetchMode.JOIN);
        detachedCriteria.createAlias("empDeptNo", "dept")
                .add(
                     Restrictions.or(Restrictions.or(Restrictions.like("empName", queryString,
                                                                       MatchMode.ANYWHERE),
                                                     Restrictions.like("empDistinctNo",
                                                                       queryString,
                                                                       MatchMode.ANYWHERE)),
                                     Restrictions.like("dept.departmentName", queryString,
                                                       MatchMode.ANYWHERE)))
                .add(Restrictions.eq("empStatus", Integer.valueOf(1)));

        return detachedCriteria;
    }

    protected String listToString(List<String> list) {
        StringBuffer buffer = new StringBuffer();
        for (String s : list) {
            buffer.append("'").append(s).append("'").append(",");
        }
        String tmpResult = buffer.toString();
        if (tmpResult.length() > 1) {
            tmpResult = tmpResult.substring(0, tmpResult.length() - 1);
        }
        return tmpResult;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.common.AbstractPrincipalManager JD-Core Version: 0.5.4
 */