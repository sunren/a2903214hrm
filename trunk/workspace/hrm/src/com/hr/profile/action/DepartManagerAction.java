package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.domain.Department;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.bo.IOrgheadsBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Orgheads;
import com.hr.spring.SpringBeanFactory;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class DepartManagerAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    List<Department> deptList;
    List<Orgheads> deptHeads;

    public DepartManagerAction() {
        this.deptHeads = new ArrayList<Orgheads>();
    }

    @SuppressWarnings("unchecked")
    public String execute() throws Exception {
        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        IOrgheadsBo headsBo = (IOrgheadsBo) SpringBeanFactory.getBean("headsBo");

        this.deptList = deptBo.FindAllDepartment();

        List<Orgheads> tmpDeptHeadsList = headsBo
                .exeHqlList("from Orgheads where orgheadsResponsibility like '%dept%'");
        List<String> orgheadempIdlist = new ArrayList<String>();
        for (Orgheads orgheadObj : tmpDeptHeadsList) {
            orgheadempIdlist.add(orgheadObj.getOrgheadsEmpNo());
        }

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        List<?> emplist;
        if (orgheadempIdlist.size() == 0) {
            emplist = null;
        } else {
            detachedCriteria.add(Restrictions.in(Employee.PROP_ID, orgheadempIdlist));
            emplist = empBo.findByCriteria(detachedCriteria);
        }
        DetachedCriteria dc = DetachedCriteria.forClass(Department.class);
        dc.addOrder(Order.asc("departmentSortId"));
        List<?> deptlist = empBo.findByCriteria(dc);
        Employee temp = new Employee();
        Department depttemp = new Department();
        for (Orgheads orgheads : tmpDeptHeadsList) {
            for (int i = 0; i < ((emplist == null) ? 0 : emplist.size()); ++i) {
                if (orgheads.getOrgheadsEmpNo().equals(((Employee) emplist.get(i)).getId())) {
                    temp = (Employee) emplist.get(i);
                    break;
                }
            }
            if (null == temp) {
                continue;
            }
            for (int j = 0; j < ((deptlist == null) ? 0 : deptlist.size()); ++j) {
                if (orgheads.getOrgheadsOrgNo().equals(((Department) deptlist.get(j)).getId())) {
                    depttemp = (Department) deptlist.get(j);
                }
            }
            if (null == depttemp) {
                continue;
            }
            orgheads.setEmpName(temp.getEmpName());
            orgheads.setOrgName(depttemp.getDepartmentName());
            this.deptHeads.add(orgheads);
        }
        return "success";
    }

    public List<Department> getDeptList() {
        return this.deptList;
    }

    public void setDeptList(List<Department> deptList) {
        this.deptList = deptList;
    }

    public List<Orgheads> getDeptHeads() {
        return this.deptHeads;
    }

    public void setDeptHeads(List<Orgheads> deptHeads) {
        this.deptHeads = deptHeads;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.DepartManagerAction JD-Core Version: 0.5.4
 */