package com.hr.report.bo;

import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.domain.Department;
import com.hr.examin.bo.ExaminDateUtil;
import com.hr.examin.domain.Attendmonthly;
import com.hr.profile.domain.Employee;
import com.hr.report.action.examin.support.AttendmonthlyReportObject;
import com.hr.report.dao.IReportDAO;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.MyTools;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class ExaminReportBoImpl implements IExaminReportBo {
    private IReportDAO reportDao;

    public List getAlltendMlyObjects(String reportTypeSelect, String Year, String Month) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendmonthly.class);
        detachedCriteria.createAlias("attmEmpId", "emp", 1);
        detachedCriteria.setFetchMode("emp.empDeptNo", FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq(Attendmonthly.PROP_ATTM_YEAR, Year));
        detachedCriteria.add(Restrictions.eq(Attendmonthly.PROP_ATTM_MONTH, Month));
        List<Attendmonthly> attendmonthlyList = this.reportDao.findByCriteria(detachedCriteria);
        Map<String, BigDecimal> resultMap = new HashMap<String, BigDecimal>();
        String type = "";
        if (reportTypeSelect.equals("1"))
            type = "attmLeaveHours";
        else if (reportTypeSelect.equals("2"))
            type = "attmOvertimeHours";
        else if (reportTypeSelect.equals("3"))
            type = "attmLateTimes";
        else if (reportTypeSelect.equals("4"))
            type = "attmEarlyLeave";
        else if (reportTypeSelect.equals("5")) {
            type = "attmAbsentDays";
        }
        BigDecimal initValue = new BigDecimal(0);
        IDepartmentBO deptbo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        List<Department> deptList = deptbo.FindEnabledDepartment();
        for (Department dept : deptList) {
            resultMap.put(dept.getDepartmentName(), initValue);
        }

        for (Attendmonthly objattendmonthly : attendmonthlyList) {
            formatAttendmonthlyResults(type, resultMap, objattendmonthly);
        }
        if (type.equals("attmLeaveHours")) {
            for (Department dept : deptList) {
                BigDecimal tmp = (BigDecimal) resultMap.get(dept.getDepartmentName());
                double doublevalue = tmp.doubleValue();
                double hoursPerDay = ExaminDateUtil.getDefaultHoursPerDay();
                doublevalue = MyTools.round(doublevalue / hoursPerDay, 2);

                tmp = new BigDecimal(Double.toString(doublevalue));
                resultMap.put(dept.getDepartmentName(), tmp);
            }
        }
        return getResultFromMap(resultMap);
    }

    private void formatAttendmonthlyResults(String type, Map<String, BigDecimal> resultMap,
            Attendmonthly objattendmonthly) {
        String name = objattendmonthly.getAttmEmpId().getEmpDeptNo().getDepartmentName();
        BigDecimal value = (getValue(objattendmonthly, type) != null) ? (BigDecimal) getValue(
                                                                                              objattendmonthly,
                                                                                              type)
                : new BigDecimal(0);
        if (resultMap.containsKey(name)) {
            BigDecimal tmp = (BigDecimal) resultMap.get(name);
            resultMap.put(name, tmp.add(value));
        } else {
            resultMap.put(name, value);
        }
    }

    private List getResultFromMap(Map<String, BigDecimal> resultMap) {
        List result = new ArrayList();
        for (String key : resultMap.keySet()) {
            AttendmonthlyReportObject obj = new AttendmonthlyReportObject();
            obj.setName(key);
            obj.setTotal((BigDecimal) resultMap.get(key));
            result.add(obj);
        }
        return result;
    }

    private Object getValue(Object obj, String expression) {
        OgnlContext context = new OgnlContext();
        context.put("attendmonthly", obj);
        try {
            Object parseExpression = Ognl.parseExpression("attendmonthly." + expression);
            return Ognl.getValue(parseExpression, context);
        } catch (OgnlException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setValue(Object obj, String expression, Object value) {
        OgnlContext context = new OgnlContext();
        context.put("", obj);
        try {
            Object parseExpression = Ognl.parseExpression(expression);
            Ognl.setValue(parseExpression, context, obj, value);
        } catch (OgnlException e) {
            e.printStackTrace();
        }
    }

    public IReportDAO getReportDao() {
        return this.reportDao;
    }

    public void setReportDao(IReportDAO reportDao) {
        this.reportDao = reportDao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.bo.ExaminReportBoImpl JD-Core Version: 0.5.4
 */