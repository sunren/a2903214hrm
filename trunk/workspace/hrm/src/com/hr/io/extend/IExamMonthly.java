package com.hr.io.extend;

import com.hr.examin.bo.interfaces.IAttendmonthlyBO;
import com.hr.examin.domain.Attendmonthly;
import com.hr.examin.domain.Attendperiod;
import com.hr.examin.support.AttendmonthlyFactory;
import com.hr.io.domain.InmatchModel;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class IExamMonthly extends ICheckAndInsert {
    public int[] insertTransmit(List insertList, CommonParameters commonParas) {
        String msgNotExist = "编号为{0}的员工不存在";

        String[] yearMonthStrArr = commonParas.getImportParameter().split(",");
        String year = yearMonthStrArr[0];
        String month = yearMonthStrArr[1];
        String yearMonth = commonParas.getImportParameter().replace(",", "");

        Map empNoMap = buildEmployeeNoMap();

        Map dbDataMap = buildMonthlyEmpIdSet(yearMonth);

        int hasTitle = 1 - commonParas.getInmatchModel().getImmNoTitle().intValue();
        Map<String, ObjAndRow> objInsertMap = new HashMap<String, ObjAndRow>();
        int rowNum = hasTitle + 1;
        List<Attendmonthly> monthlyList = insertList;
        for (Attendmonthly monthly : monthlyList) {
            objInsertMap.put(monthly.getAttmEmpId().getEmpDistinctNo(), new ObjAndRow(Integer
                    .valueOf(rowNum++), monthly));
        }

        int[] result = { 0, 0 };

        Iterator iter = objInsertMap.entrySet().iterator();
        String empDistinctNo = null;
        Attendmonthly monthly = null;
        int currentRow = 0;
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            ObjAndRow objAndRowTmp = (ObjAndRow) entry.getValue();
            empDistinctNo = (String) entry.getKey();
            currentRow = objAndRowTmp.rowNum.intValue();
            monthly = objAndRowTmp.monthly;

            monthly.setAttmYear(year);
            monthly.setAttmMonth(month);
            monthly.setAttmYearmonth(yearMonth);
            Employee emp = (Employee) empNoMap.get(empDistinctNo);

            if (!empNoMap.containsKey(empDistinctNo)) {
                commonParas.addErrorMessage(msgNotExist, Integer.valueOf(currentRow),
                                            new String[] { empDistinctNo });
            } else if (dbDataMap.containsKey(emp.getId())) {
                try {
                    commonParas.copy1To2(monthly, dbDataMap.get(emp.getId()));
                    objAndRowTmp.monthly = ((Attendmonthly) dbDataMap.get(emp.getId()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            objAndRowTmp.monthly.setAttmEmpId(emp);

            if (emp != null) {
                objAndRowTmp.monthly.setAttmDept(emp.getEmpDeptNo());
                objAndRowTmp.monthly.setAttmLocation(emp.getEmpLocationNo());
                objAndRowTmp.monthly.setAttmEmptype(emp.getEmpType());
                objAndRowTmp.monthly.setAttmPbNo(emp.getEmpPbNo());
            }

        }

        if (commonParas.getIoMessages().hasErrorMsg()) {
            return result;
        }

        IAttendmonthlyBO attendmothlyBO = (IAttendmonthlyBO) SpringBeanFactory
                .getBean("attendmonthlyBo");
        Attendperiod period = attendmothlyBO.loadAttendperiod(year, month);

        IAttendmonthlyBO attendmonthlyBo = (IAttendmonthlyBO) SpringBeanFactory
                .getBean("attendmonthlyBo");
        for (ObjAndRow row : objInsertMap.values()) {
            calculateTotalDaysAndHours(row.monthly);
            if (StringUtils.isEmpty(row.monthly.getId())) {
                attendmonthlyBo.saveObject(row.monthly);
                if (period == null) {
                    period = new Attendperiod();
                    period.setAttpYearmonth(year + month);
                    period.setAttpMonth(month);
                    period.setAttpYear(year);
                    period.setAttpStatus(new Integer(0));
                    attendmonthlyBo.saveObject(period);
                }
            } else {
                attendmonthlyBo.updateObject(row.monthly);
            }
            result[0] += 1;
        }

        return result;
    }

    private void calculateTotalDaysAndHours(Attendmonthly month) {
        AttendmonthlyFactory.initAttendMonthlyProperties(month);

        BigDecimal totalHours = AttendmonthlyFactory.calculateLeaveTotalHours(month);
        month.setAttmLeaveHours(totalHours);

        BigDecimal totalDays = AttendmonthlyFactory.calculateLeaveTotalDays(month);
        month.setAttmLeaveDays(totalDays);

        BigDecimal otTotalHours = AttendmonthlyFactory.calculateOtTotalHours(month);
        month.setAttmOvertimeHours(otTotalHours);
    }

    private Map<String, Attendmonthly> buildMonthlyEmpIdSet(String yearMonth) {
        IEmployeeBo esaBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendmonthly.class);
        detachedCriteria.setFetchMode(Attendmonthly.PROP_ATTM_EMP_ID, FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq(Attendmonthly.PROP_ATTM_YEARMONTH, yearMonth));

        List<Attendmonthly> attendmonthList = esaBo.findByCriteria(detachedCriteria);

        Map monthly = new HashMap();
        for (Attendmonthly attendmonthly : attendmonthList) {
            monthly.put(attendmonthly.getAttmEmpId().getId(), attendmonthly);
        }
        return monthly;
    }

    private Map<String, Employee> buildEmployeeNoMap() {
        IEmployeeBo esaBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        List empList = esaBo.findByCriteria(detachedCriteria);
        int size = empList.size();
        Map rs = new HashMap();
        for (int i = 0; i < size; ++i) {
            rs.put(((Employee) empList.get(i)).getEmpDistinctNo(), empList.get(i));
        }
        return rs;
    }

    private class ObjAndRow {
        public Integer rowNum;
        public Attendmonthly monthly;

        ObjAndRow(Integer rowNum, Attendmonthly monthly) {
            this.rowNum = rowNum;
            this.monthly = monthly;
        }
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.extend.IExamMonthly JD-Core Version: 0.5.4
 */