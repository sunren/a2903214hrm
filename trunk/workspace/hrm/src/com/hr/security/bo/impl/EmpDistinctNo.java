package com.hr.security.bo.impl;

import com.hr.base.BaseAction;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class EmpDistinctNo extends BaseAction {
    private static final long serialVersionUID = 1L;
    static IEmployeeBo empBo = null;

    public static String getEmpDistinctNo(String id) {
        String[] fetch = { "empDistinctNo" };
        Employee employee = getEmployeeBo().loadEmp(id, fetch);
        if (employee != null) {
            return employee.getEmpDistinctNo();
        }
        return null;
    }

    public static Employee getEmpNo(String empDistinctNo) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.add(Restrictions.eq("empDistinctNo", empDistinctNo));
        List list = getEmployeeBo().findByCriteria(detachedCriteria);
        if ((list == null) || (list.size() == 0))
            return null;
        return (Employee) list.get(0);
    }

    public static String getEmpName(String id) {
        String[] fetch = { "empName" };
        Employee employee = getEmployeeBo().loadEmp(id, fetch);
        if (employee != null) {
            return employee.getEmpName();
        }
        return null;
    }

    public static IEmployeeBo getEmployeeBo() {
        if (empBo == null)
            empBo = (IEmployeeBo) getBean("empBo");
        return empBo;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.bo.impl.EmpDistinctNo JD-Core Version: 0.5.4
 */