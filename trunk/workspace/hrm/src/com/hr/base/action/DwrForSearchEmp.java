package com.hr.base.action;

import com.hr.compensation.bo.ISalaryPaidBo;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Location;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.BaseCrit;
import java.util.ArrayList;
import java.util.List;
import org.apache.axis.utils.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class DwrForSearchEmp {
    public String searchEmp(String empStr, String deptStr, String local, String condition) {
        Department depart = null;
        if ((!StringUtils.isEmpty(deptStr)) && (!deptStr.split(",")[0].equals("rootid"))) {
            depart = new Department(deptStr);
        }

        Location location = null;
        if (!StringUtils.isEmpty(local)) {
            location = new Location(local);
        }

        List<Employee> empList = new ArrayList<Employee>();
        empList = selectEmp(depart, empStr, location, condition);

        String result = "";
        for (Employee emp : empList) {
            if (!result.equals("")) {
                result = result + "&";
            }
            result = result + emp.getEmpDistinctNo() + ";" + emp.getEmpName() + ";" + emp.getId();
        }

        return result;
    }

    private List<Employee> selectEmp(Department depart, String empStr, Location location,
            String condition) {
        IEmployeeBo employeeBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        List empList = new ArrayList();
        DetachedCriteria dcEmp = DetachedCriteria.forClass(Employee.class);

        BaseCrit.addEmpDC(dcEmp, null, empStr);
        BaseCrit.addDeptDC(dcEmp, Employee.PROP_EMP_DEPT_NO, Employee.PROP_EMP_PB_NO, Integer
                .valueOf(1), depart);
        BaseCrit.addDC(dcEmp, Employee.PROP_EMP_LOCATION_NO, Location.PROP_ID,
                       new Object[] { location });

        String[] conditionArray = condition.split("&");

        if (conditionArray[0].equals("empNeedCard")) {
            dcEmp.add(Restrictions.eq(Employee.PROP_EMP_STATUS, Integer.valueOf(1)));
            dcEmp.add(Restrictions
                    .or(Restrictions.eq(Employee.PROP_EMP_SHIFT_TYPE, Integer.valueOf(2)),
                        Restrictions.eq(Employee.PROP_EMP_SHIFT_TYPE, Integer.valueOf(3))));
            empList = employeeBo.findByCriteria(dcEmp);
        } else if (conditionArray[0].equals("empNotUser")) {
            dcEmp.add(Restrictions.eq("empStatus", new Integer(1)));
            dcEmp
                    .add(Restrictions
                            .sqlRestriction("emp_no not in (select ui_emp_no from userinfo) order by emp_name"));
            empList = employeeBo.findByCriteria(dcEmp);
        } else if (conditionArray[0].equals("empHasComp")) {
            DetachedCriteria dcPay = DetachedCriteria.forClass(Empsalarypay.class);
            dcPay.createAlias(Empsalarypay.PROP_ESP_EMPNO, "espEmpno", 1);

            BaseCrit.addDC(dcPay, Empsalarypay.PROP_ESP_YEARMONTH, "eq",
                           new String[] { conditionArray[1] });
            BaseCrit.addEmpDC(dcPay, Empsalarypay.PROP_ESP_EMPNO, empStr);
            BaseCrit.addDeptDC(dcPay, Empsalarypay.PROP_ESP_DEPT, Empsalarypay.PROP_ESP_PB_NO,
                               null, depart);
            BaseCrit.addDC(dcPay, Empsalarypay.PROP_ESP_LOCATION, Location.PROP_ID,
                           new Object[] { location });

            ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) SpringBeanFactory.getBean("salaryPaidBo");
            List<Empsalarypay> payList = salaryPaidBo.findByCriteria(dcPay);
            if ((payList != null) && (!payList.isEmpty())) {
                for (Empsalarypay emppay : payList) {
                    empList.add(emppay.getEspEmpno());
                }
            }
        }
        return empList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.base.action.DwrForSearchEmp JD-Core Version: 0.5.4
 */