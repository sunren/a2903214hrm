package com.hr.examin.action;

import com.hr.base.DWRUtil;
import com.hr.examin.bo.ExaminDateUtil;
import com.hr.examin.bo.interfaces.IAttdoriginaldataBO;
import com.hr.examin.bo.interfaces.IAttendmonthlyBO;
import com.hr.examin.core.ExaminBoFactory;
import com.hr.examin.domain.Attendmonthly;
import com.hr.examin.shift.AttendDailyHandler;
import com.hr.examin.support.RecordSorter;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.DateUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class DwrForAttend {
    private static String SUCCESS = "SUCCESS";
    private static String ERROR = "ERROR";
    private static String NOAUTH = "NOAUTH";

    public Map<String, Object> getEmpAttendmonthly(String id) {
        String auth = DWRUtil.checkAuth("DwrForAttend", "getEmpAttendmonthly");
        Map rs = new HashMap();
        if ("error".equals(auth)) {
            rs.put(ERROR, "无权操作");
            return rs;
        }
        IAttendmonthlyBO attendmothlyBO = getAttendmonthlyBO();
        Attendmonthly monthly = attendmothlyBO.getAttendmonthly(id);
        rs.put(SUCCESS, monthly);
        return rs;
    }

    public String addCardData(String empIdStr, String attdDateStr, String attdCardTimeStr,
            String aodTdMachineNo, String memo, String curentEmpName) {
        if ("error".equals(DWRUtil.checkAuth("originalDataImportSho", "execute"))) {
            return NOAUTH;
        }
        if (StringUtils.isEmpty(empIdStr)) {
            return "请选择要补卡的员工";
        }
        Date attdDate = DateUtil.parseDateByFormat(attdDateStr, "yyyy-MM-dd");
        Date attdCardTime = DateUtil.parseDateByFormat(attdCardTimeStr, "yyyy-MM-dd HH:mm:ss");
        String[] empNoArray = empIdStr.split(";");
        IAttdoriginaldataBO originaldataBo = (IAttdoriginaldataBO) SpringBeanFactory
                .getBean("attdoriginaldataBO");
        originaldataBo.saveOriginalData(empNoArray, attdDate, attdCardTime, aodTdMachineNo, memo,
                                        curentEmpName);
        return SUCCESS;
    }

    public Map<String, Object> getAttendDailyMemory(String id, String year, String month) {
        Map rs = new HashMap();
        String auth = DWRUtil.checkAuth("DwrForAttend", "getAttendDailyMemory");
        if ("error".equals(auth)) {
            rs.put(ERROR, "无权操作");
            return rs;
        }
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        Employee emp = empBo.loadEmp(id, null);
        if (emp == null) {
            rs.put(ERROR, "员工资料不存在！操作失败");
            return rs;
        }

        List empList = new ArrayList();
        empList.add(emp);
        Integer intYear = Integer.valueOf(year);
        Integer intMonth = Integer.valueOf(month);
        String config = DatabaseSysConfigManager.getInstance().getProperty("sys.examin.month.sum");
        Date[] dateArr = ExaminDateUtil.getDateArray(intYear.intValue(), intMonth.intValue(),
                                                     config);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String sStr = format.format(dateArr[0]);
        String eStr = format.format(dateArr[1]);
        try {
            Date d1 = format.parse(sStr);
            Date d2 = format.parse(eStr);
            dateArr[0] = d1;
            dateArr[1] = d2;
        } catch (ParseException e) {
        }
        AttendDailyHandler handler = new AttendDailyHandler();
        handler.setEmpList(empList);
        handler.setStartDate(dateArr[0]);
        handler.setEndDate(dateArr[1]);
        List list = handler.generateRecords();
        RecordSorter sorter = new RecordSorter(list);
        sorter.doSort("examinDate", "asc");
        rs.put(SUCCESS, list);
        return rs;
    }

    public Map<String, Object> saveEmpAttendmonthly(Attendmonthly attendmonthly, String empId)
            throws Exception {
        Map rs = new HashMap();
        String auth = DWRUtil.checkAuth("DwrForAttend", "saveEmpAttendmonthly");
        if ("error".equals(auth)) {
            rs.put(ERROR, "无权操作");
            return rs;
        }
        attendmonthly.setAttmEmpId(new Employee(empId));
        IAttendmonthlyBO attendmothlyBO = getAttendmonthlyBO();
        Attendmonthly data = attendmothlyBO.saveOrUpdateAttendmonthly(attendmonthly);
        rs.put(SUCCESS, data);
        return rs;
    }

    private IAttendmonthlyBO getAttendmonthlyBO() {
        ExaminBoFactory factory = ExaminBoFactory.getInstance();
        return factory.createAttendmonthlyBo();
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.action.DwrForAttend JD-Core Version: 0.5.4
 */