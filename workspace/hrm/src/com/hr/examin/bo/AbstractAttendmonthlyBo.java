package com.hr.examin.bo;

import com.hr.base.Status;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.examin.bo.interfaces.IAttendmonthlyBO;
import com.hr.examin.bo.interfaces.ILeavecalendarBO;
import com.hr.examin.dao.interfaces.IAttenddailyDAO;
import com.hr.examin.domain.Attendmonthly;
import com.hr.examin.domain.Attendperiod;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Leavetype;
import com.hr.examin.domain.Overtimerequest;
import com.hr.examin.domain.Overtimetype;
import com.hr.examin.shift.AttendDailyHandler;
import com.hr.examin.support.AttendDailyMemory;
import com.hr.examin.support.AttendmonthlyFactory;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.MyTools;
import com.hr.util.Pager;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.ObjectUtils;

public abstract class AbstractAttendmonthlyBo implements IAttendmonthlyBO, Status {
    private static final String DEFAULT_LATE_MINUTE_CONFIT = "5,15;15,30;30,90";
    private static final String DEFAULT_EARLY_LEAVE_CONFIG = "5,15;15,30;30,90";
    private static final String EARLY_LEAVE_AND_LATE_CONFIG_REG = "^\\d+\\,\\d+\\;((\\d+\\,\\d+)|(\\s*))\\;((\\d+\\,\\d+)|(\\s*))";
    protected IAttenddailyDAO attenddailyDAO;
    protected ILeavecalendarBO lc_BO;
    protected LrDataCheckImpl lrDataCheck;

    public boolean addAttendmonthly(Attendmonthly attendmonthly) {
        this.attenddailyDAO.saveObject(attendmonthly);
        return true;
    }

    public boolean deleteAttendmonthly(String id) {
        Attendmonthly attendmonthly = (Attendmonthly) this.attenddailyDAO
                .loadObject(Attendmonthly.class, id, null, new boolean[0]);
        this.attenddailyDAO.deleteObject(attendmonthly);
        return true;
    }

    public boolean exeMonthlySummary(String year, String month) {
        deleteAttendmonthlyByDate(year, month);

        importAttendmonthlyData(year, month);

        saveStatus(year, month);
        return true;
    }

    public List<Employee> computeEmployeeList(Date startDate, Date endDate) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.add(Restrictions.le(Employee.PROP_EMP_JOIN_DATE, endDate));
        detachedCriteria.add(Restrictions.or(Restrictions.eq(Employee.PROP_EMP_STATUS, Integer
                .valueOf(1)), Restrictions.and(Restrictions.eq(Employee.PROP_EMP_STATUS, Integer
                .valueOf(0)), Restrictions.ge(Employee.PROP_EMP_TERMINATE_DATE, startDate))));

        List empList = this.attenddailyDAO.findByCriteria(detachedCriteria);
        return empList;
    }

    private void saveStatus(String year, String month) {
        Attendperiod period = new Attendperiod();

        period = new Attendperiod();
        period.setAttpYearmonth(year + month);
        period.setAttpMonth(month);
        period.setAttpYear(year);
        period.setAttpStatus(new Integer(0));
        this.attenddailyDAO.saveObject(period);
    }

    protected boolean importAttendmonthlyData(String year, String month) {
        Map dbConfigMap = DatabaseSysConfigManager.getInstance().getProperties();

        Integer intYear = Integer.valueOf(year);
        Integer intMonth = Integer.valueOf(month);
        Date[] dateArr = ExaminDateUtil.getDateArray(intYear.intValue(), intMonth.intValue(),
                                                     (String) dbConfigMap
                                                             .get("sys.examin.month.sum"));
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
        Date startDate = dateArr[0];
        Date endDate = dateArr[1];

        String dayConf = (String) dbConfigMap.get("sys.examin.leave.mode");
        BigDecimal hourPerday = new BigDecimal(dayConf.split(",")[1]);

        String latePeriodConfig = (String) dbConfigMap.get("sys.shift.late.minute");
        String earlyPeriodConfig = (String) dbConfigMap.get("sys.shift.earlyleave.minute");

        latePeriodConfig = checkDbConfigProps(
                                              latePeriodConfig,
                                              "^\\d+\\,\\d+\\;((\\d+\\,\\d+)|(\\s*))\\;((\\d+\\,\\d+)|(\\s*))",
                                              "5,15;15,30;30,90");

        earlyPeriodConfig = checkDbConfigProps(
                                               earlyPeriodConfig,
                                               "^\\d+\\,\\d+\\;((\\d+\\,\\d+)|(\\s*))\\;((\\d+\\,\\d+)|(\\s*))",
                                               "5,15;15,30;30,90");

        List empList = computeEmployeeList(startDate, endDate);
        Map empMap = convertEmpListToMap(empList);

        List leaveRequestList = getLeaveRequest(startDate, endDate, empList);

        List overtimeRequestList = getOvertimeRequest(startDate, endDate, empList);

        AttendDailyHandler handler = new AttendDailyHandler();
        handler.setEmpList(empList);
        handler.setStartDate(startDate);
        handler.setEndDate(endDate);
        handler.setPager(null);
        List<AttendDailyMemory> daily = handler.generateRecords();

        Map resultMap = initAttendMonthlyMap(empList, leaveRequestList, overtimeRequestList,
                                             startDate, endDate, hourPerday);
        String yearStr = String.valueOf(year);
        String monthStr = String.valueOf(month);
        String empId = null;
        for (AttendDailyMemory mem : daily) {
            empId = mem.getEmpId();
            Attendmonthly monthly = (Attendmonthly) resultMap.get(empId);
            if (monthly == null) {
                continue;
            }

            monthly.setAttmYear(yearStr);
            monthly.setAttmMonth(monthStr);
            monthly.setAttmYearmonth(yearStr + monthStr);

            doSummaryAttendance(monthly, mem);

            monthly.setAttmEarlyLeave(ExaminDateUtil.getExaminDatePeriod(monthly
                    .getAttmEarlyLeave(), earlyPeriodConfig));
            monthly.setAttmLateTimes(ExaminDateUtil.getExaminDatePeriod(monthly.getAttmLateTimes(),
                                                                        latePeriodConfig));

            Employee emp = (Employee) empMap.get(empId);
            if (emp != null) {
                monthly.setAttmDept(emp.getEmpDeptNo());
                monthly.setAttmLocation(emp.getEmpLocationNo());
                monthly.setAttmEmptype(emp.getEmpType());
                monthly.setAttmPbNo(emp.getEmpPbNo());
            }

            resultMap.put(empId, monthly);
        }

        this.attenddailyDAO.saveOrupdate(resultMap.values());
        return true;
    }

    private Map<String, Employee> convertEmpListToMap(List<Employee> empList) {
        Map empMap = new HashMap();
        if ((empList == null) || (empList.size() == 0)) {
            return empMap;
        }

        for (Employee emp : empList) {
            empMap.put(emp.getId(), emp);
        }
        return empMap;
    }

    private String checkDbConfigProps(String str, String regex, String defaultStr) {
        if (StringUtils.isEmpty(str)) {
            return defaultStr;
        }
        boolean match = Pattern.matches(regex, str);
        if (!match) {
            return defaultStr;
        }
        return str;
    }

    protected void doSummaryAttendance(Attendmonthly monthly, AttendDailyMemory mem) {
        monthly.setAttmDutyDays(monthly.getAttmDutyDays().add(mem.getOughtDutyDays()));

        BigDecimal offDutyDay = mem.getAbsentDays();
        monthly.setAttmOffDutyDays(monthly.getAttmOffDutyDays().add(offDutyDay));

        BigDecimal leaveDay = mem.getLeaveDays();

        BigDecimal onDuty = mem.getOughtDutyDays().subtract(offDutyDay).subtract(leaveDay);
        monthly.setAttmOnDutyDays(monthly.getAttmOnDutyDays().add(onDuty));

        monthly.setAttmDutyHours(monthly.getAttmDutyHours().add(mem.getOughtDutyHours()));

        monthly.setAttmOffDutyHours(monthly.getAttmOffDutyHours().add(mem.getAbsentHours()));

        BigDecimal leaveHour = mem.getLeaveHours();

        BigDecimal onDutyHours = mem.getOughtDutyHours().subtract(mem.getAbsentHours())
                .subtract(leaveHour);
        monthly.setAttmOnDutyHours(monthly.getAttmOnDutyHours().add(onDutyHours));
    }

    protected void doSummaryLeaveRequestData(Attendmonthly attdm, List<Leaverequest> requestList,
            Date startDate, Date endDate, BigDecimal hourPerDay) {
        long startDateLong = startDate.getTime();
        long endDateLong = endDate.getTime();
        String empId = attdm.getAttmEmpId().getId();
        LrDataCheckImpl lrCheckBO = (LrDataCheckImpl) SpringBeanFactory.getBean("lrDataCheck");

        Map hourMap = new HashMap();
        Map dayMap = new HashMap();

        for (Leaverequest lr : requestList) {
            String emp = lr.getLrEmpNo().getId();
            if (!emp.equals(empId)) {
                continue;
            }
            long lrStartLong = lr.getLrStartDate().getTime();
            long lrEndLong = lr.getLrEndDate().getTime();
            Integer key = lr.getLrLtNo().getLtSpecialCat();
            BigDecimal totalHour = new BigDecimal(0);
            BigDecimal totalDay = new BigDecimal(0);

            if ((lrStartLong < startDateLong) && (lrEndLong > endDateLong)) {
                List tmpList = new ArrayList();
                tmpList.add(lr);

                double[] beginTotal = lrCheckBO.getLeaveHoursBeforAfter(tmpList, startDate);

                Calendar c = Calendar.getInstance();
                c.setTime(endDate);

                double[] toTotal = lrCheckBO.getLeaveHoursBeforAfter(tmpList, c.getTime());
                BigDecimal tmp = new BigDecimal(beginTotal[0]);
                tmp = tmp.add(new BigDecimal(toTotal[1]));

                totalHour = lr.getLrTotalHours().subtract(tmp);
                totalDay = totalHour.divide(hourPerDay);
            } else if ((lrStartLong < startDateLong) && (startDateLong < lrEndLong)
                    && (lrEndLong < endDateLong)) {
                List tmpList = new ArrayList();
                tmpList.add(lr);
                double[] beginTotal = lrCheckBO.getLeaveHoursBeforAfter(tmpList, startDate);
                BigDecimal tmp = new BigDecimal(beginTotal[0]);

                totalHour = lr.getLrTotalHours().subtract(tmp);
                totalDay = totalHour.divide(hourPerDay);
            } else if ((startDateLong < lrStartLong) && (lrStartLong < endDateLong)
                    && (endDateLong < lrEndLong)) {
                List tmpList = new ArrayList();
                tmpList.add(lr);

                Calendar c = Calendar.getInstance();
                c.setTime(endDate);

                double[] toTotal = lrCheckBO.getLeaveHoursBeforAfter(tmpList, c.getTime());
                BigDecimal tmp = new BigDecimal(toTotal[1]);

                totalHour = lr.getLrTotalHours().subtract(tmp);
                totalDay = totalHour.divide(hourPerDay);
            } else {
                totalHour = lr.getLrTotalHours();
                totalDay = lr.getLrTotalDays();
            }
            if (hourMap.containsKey(key))
                hourMap.put(key, ((BigDecimal) hourMap.get(key)).add(totalHour));
            else {
                hourMap.put(key, totalHour);
            }
            if (dayMap.containsKey(key))
                dayMap.put(key, ((BigDecimal) dayMap.get(key)).add(totalDay));
            else {
                dayMap.put(key, totalDay);
            }
        }

        attdm.setAttmLeaveCasualHours((BigDecimal) hourMap.get(Integer.valueOf(0)));
        attdm.setAttmLeaveCasualDays((BigDecimal) dayMap.get(Integer.valueOf(0)));
        attdm.setAttmLeaveAnnualHours((BigDecimal) hourMap.get(Integer.valueOf(1)));
        attdm.setAttmLeaveAnnualDays((BigDecimal) dayMap.get(Integer.valueOf(1)));
        attdm.setAttmLeaveSickHours((BigDecimal) hourMap.get(Integer.valueOf(3)));
        attdm.setAttmLeaveSickDays((BigDecimal) dayMap.get(Integer.valueOf(3)));
        attdm.setAttmLeaveSick01Hours((BigDecimal) hourMap.get(Integer.valueOf(4)));
        attdm.setAttmLeaveSick01Days((BigDecimal) dayMap.get(Integer.valueOf(4)));
        attdm.setAttmLeaveSick02Hours((BigDecimal) hourMap.get(Integer.valueOf(5)));
        attdm.setAttmLeaveSick02Days((BigDecimal) dayMap.get(Integer.valueOf(5)));
        attdm.setAttmLeaveWeddingHours((BigDecimal) hourMap.get(Integer.valueOf(6)));
        attdm.setAttmLeaveWeddingDays((BigDecimal) dayMap.get(Integer.valueOf(6)));
        attdm.setAttmLeaveMaternityHours((BigDecimal) hourMap.get(Integer.valueOf(7)));
        attdm.setAttmLeaveMaternityDays((BigDecimal) dayMap.get(Integer.valueOf(7)));
        attdm.setAttmLeaveTravelHours((BigDecimal) hourMap.get(Integer.valueOf(8)));
        attdm.setAttmLeaveTravelDays((BigDecimal) dayMap.get(Integer.valueOf(8)));
        attdm.setAttmLeaveOuterHours((BigDecimal) hourMap.get(Integer.valueOf(9)));
        attdm.setAttmLeaveOuterDays((BigDecimal) dayMap.get(Integer.valueOf(9)));
        attdm.setAttmLeaveTiaoxiuHours((BigDecimal) hourMap.get(Integer.valueOf(10)));
        attdm.setAttmLeaveTiaoxiuDays((BigDecimal) dayMap.get(Integer.valueOf(10)));
        attdm.setAttmLeaveTiaoxiu01Hours((BigDecimal) hourMap.get(Integer.valueOf(11)));
        attdm.setAttmLeaveTiaoxiu01Days((BigDecimal) dayMap.get(Integer.valueOf(11)));
        attdm.setAttmLeaveOtherHours((BigDecimal) hourMap.get(Integer.valueOf(20)));
        attdm.setAttmLeaveOtherDays((BigDecimal) dayMap.get(Integer.valueOf(20)));

        AttendmonthlyFactory.initAttendMonthlyProperties(attdm);
        BigDecimal bd = new BigDecimal(0);
        bd = bd.add(attdm.getAttmLeaveAnnualHours());
        bd = bd.add(attdm.getAttmLeaveCasualHours());
        bd = bd.add(attdm.getAttmLeaveMaternityHours());
        bd = bd.add(attdm.getAttmLeaveSickHours());
        bd = bd.add(attdm.getAttmLeaveTravelHours());
        bd = bd.add(attdm.getAttmLeaveWeddingHours());
        bd = bd.add(attdm.getAttmLeaveOtherHours());
        bd = bd.add(attdm.getAttmLeaveSick01Hours());
        bd = bd.add(attdm.getAttmLeaveOuterHours());
        bd = bd.add(attdm.getAttmLeaveTiaoxiuHours());
        bd = bd.add(attdm.getAttmLeaveTiaoxiu01Hours());
        bd = bd.add(attdm.getAttmLeaveSick02Hours());
        attdm.setAttmLeaveHours(bd);

        BigDecimal days = new BigDecimal(0);
        days = days.add(attdm.getAttmLeaveAnnualDays());
        days = days.add(attdm.getAttmLeaveCasualDays());
        days = days.add(attdm.getAttmLeaveMaternityDays());
        days = days.add(attdm.getAttmLeaveSickDays());
        days = days.add(attdm.getAttmLeaveTravelDays());
        days = days.add(attdm.getAttmLeaveWeddingDays());
        days = days.add(attdm.getAttmLeaveOtherDays());
        days = days.add(attdm.getAttmLeaveSick01Days());
        days = days.add(attdm.getAttmLeaveOuterDays());
        days = days.add(attdm.getAttmLeaveTiaoxiuDays());
        days = days.add(attdm.getAttmLeaveTiaoxiu01Days());
        days = days.add(attdm.getAttmLeaveSick02Days());
        attdm.setAttmLeaveDays(days);
    }

    public Long getCommenTimes(Date on, Date off, Date start, Date end) {
        long seconds = 0L;
        if ((end.getTime() < on.getTime()) || (start.getTime() > off.getTime()))
            return Long.valueOf(seconds);
        if ((start.getTime() < on.getTime()) && (off.getTime() < end.getTime())) {
            seconds = (off.getTime() - on.getTime()) / 1000L;
            return Long.valueOf(seconds);
        }
        if ((start.getTime() < on.getTime()) && (on.getTime() < end.getTime())) {
            seconds = (end.getTime() - on.getTime()) / 1000L;
            return Long.valueOf(seconds);
        }
        if ((on.getTime() <= start.getTime()) && (end.getTime() <= off.getTime())) {
            seconds = (end.getTime() - start.getTime()) / 1000L;
            return Long.valueOf(seconds);
        }
        if ((start.getTime() < off.getTime()) && (off.getTime() < end.getTime())) {
            seconds = (off.getTime() - start.getTime()) / 1000L;
            return Long.valueOf(seconds);
        }

        return Long.valueOf(seconds);
    }

    protected void doSummaryOvertimeRequestData(Attendmonthly monthlyData,
            List<Overtimerequest> orList) {
        String empId = monthlyData.getAttmEmpId().getId();

        Map map = new HashMap();
        for (Overtimerequest or : orList) {
            String tmpId = or.getOrEmpNo().getId();

            if (tmpId.equals(empId)) {
                String key = or.getOrOtNo().getOtName();
                if (map.containsKey(key))
                    map.put(key, ((BigDecimal) map.get(key)).add(or.getOrTotalHours()));
                else {
                    map.put(key, or.getOrTotalHours());
                }
            }
        }

        monthlyData.setAttmOtHolidayHours((BigDecimal) map.get("节假日加班"));
        monthlyData.setAttmOtNormalHours((BigDecimal) map.get("普通加班"));
        monthlyData.setAttmOtWeekendHours((BigDecimal) map.get("周末加班"));
        AttendmonthlyFactory.initAttendMonthlyProperties(monthlyData);
        BigDecimal bd = new BigDecimal(0);
        bd = bd.add(monthlyData.getAttmOtHolidayHours());
        bd = bd.add(monthlyData.getAttmOtNormalHours());
        bd = bd.add(monthlyData.getAttmOtWeekendHours());
        monthlyData.setAttmOvertimeHours(bd);
    }

    protected List<Leaverequest> getLeaveRequest(Date startDate, Date endDate,
            List<Employee> empList) {
        Calendar toDate = Calendar.getInstance();
        toDate.setTime(endDate);
        toDate.add(5, 1);
        DetachedCriteria detachedCrteria = DetachedCriteria.forClass(Leaverequest.class);
        detachedCrteria.setFetchMode("lrLtNo", FetchMode.JOIN);
        detachedCrteria.add(Restrictions.or(Restrictions.or(Restrictions.and(Restrictions
                .ge(Leaverequest.PROP_LR_START_DATE, startDate), Restrictions
                .lt(Leaverequest.PROP_LR_START_DATE, toDate.getTime())), Restrictions
                .and(Restrictions.lt(Leaverequest.PROP_LR_END_DATE, toDate.getTime()), Restrictions
                        .ge(Leaverequest.PROP_LR_END_DATE, startDate))), Restrictions
                .and(Restrictions.lt(Leaverequest.PROP_LR_START_DATE, startDate), Restrictions
                        .ge(Leaverequest.PROP_LR_END_DATE, toDate.getTime()))));

        detachedCrteria.add(Restrictions.in(Leaverequest.PROP_LR_STATUS, new Integer[] {
                Integer.valueOf(15), Integer.valueOf(16) }));
        if (empList.size() > 0) {
            detachedCrteria.add(Restrictions.in("lrEmpNo", empList));
        }
        return this.attenddailyDAO.findByCriteria(detachedCrteria);
    }

    protected List<Overtimerequest> getOvertimeRequest(Date startDate, Date endDate,
            List<Employee> empList) {
        Calendar toDate = Calendar.getInstance();
        toDate.setTime(endDate);
        toDate.add(5, 1);
        DetachedCriteria detachedCrteria = DetachedCriteria.forClass(Overtimerequest.class);
        detachedCrteria.setFetchMode("orOtNo", FetchMode.JOIN);
        detachedCrteria.add(Restrictions.or(Restrictions.and(Restrictions.ge("orStartDate",
                                                                             startDate),
                                                             Restrictions.lt("orStartDate", toDate
                                                                     .getTime())), Restrictions
                .and(Restrictions.lt("orEndDate", toDate.getTime()), Restrictions.ge("orEndDate",
                                                                                     startDate))));

        detachedCrteria.add(Restrictions.in("orStatus", new Integer[] { Integer.valueOf(15),
                Integer.valueOf(16) }));
        detachedCrteria.add(Restrictions.isNull(Overtimerequest.PROP_OR_TIAOXIU_HOURS));
        if (empList.size() > 0) {
            detachedCrteria.add(Restrictions.in("orEmpNo", empList));
        }
        return this.attenddailyDAO.findByCriteria(detachedCrteria);
    }

    public Attendmonthly getAttendmonthly(String id) {
        return (Attendmonthly) this.attenddailyDAO.loadObject(Attendmonthly.class, id, null,
                                                              new boolean[0]);
    }

    public List<Attendmonthly> searchAttendmonthly(Pager page, DetachedCriteria dc) {
        List result;
        if (page == null)
            result = this.attenddailyDAO.findByCriteria(dc);
        else {
            result = this.attenddailyDAO.findByCriteria(dc, page.getPageSize(), page
                    .getCurrentPage());
        }

        return result;
    }

    public boolean updateAttendmonthly(Attendmonthly attendmonthly) {
        this.attenddailyDAO.updateObject(attendmonthly);
        return true;
    }

    public boolean deleteAttendmonthlyByDate(String year, String month) {
        String hql = "delete from Attendmonthly where attmYear = '" + year + "' and attmMonth = '"
                + month + "'";

        this.attenddailyDAO.exeHql(hql);
        String sql = "delete from Attendperiod where attpYear='" + year + "' and attpMonth='"
                + month + "'";
        this.attenddailyDAO.exeHql(sql);

        return true;
    }

    public Attendmonthly saveOrUpdateAttendmonthly(Attendmonthly attend) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendmonthly.class);
        detachedCriteria.add(Restrictions.eq(Attendmonthly.PROP_ATTM_YEARMONTH, attend
                .getAttmYearmonth()));

        detachedCriteria
                .add(Restrictions.eq(Attendmonthly.PROP_ATTM_EMP_ID, attend.getAttmEmpId()));

        List result = this.attenddailyDAO.findByCriteria(detachedCriteria);
        if ((result == null) || (result.size() <= 0)) {
            AttendmonthlyFactory.initAttendMonthlyProperties(attend);
            this.attenddailyDAO.saveObject(attend);
            Attendperiod period = new Attendperiod();
            period.setAttpYearmonth(attend.getAttmYearmonth());
            period.setAttpMonth(attend.getAttmMonth());
            period.setAttpYear(attend.getAttmYear());
            period.setAttpStatus(Integer.valueOf(0));
            this.attenddailyDAO.saveObject(period);
            return attend;
        }
        Attendmonthly attendFromDB = (Attendmonthly) result.get(0);
        AttendmonthlyFactory.initAttendMonthlyProperties(attend);
        AttendmonthlyFactory.initAttendMonthlyProperties(attendFromDB);
        attendFromDB.setAttmLateTimes(attend.getAttmLateTimes());
        attendFromDB.setAttmEarlyLeave(attend.getAttmEarlyLeave());
        attendFromDB.setAttmAbsentDays(attend.getAttmAbsentDays());
        attendFromDB.setAttmAbsentHours(attend.getAttmAbsentHours());

        attendFromDB.setAttmField01(attend.getAttmField01());
        attendFromDB.setAttmField02(attend.getAttmField02());
        attendFromDB.setAttmField03(attend.getAttmField03());
        attendFromDB.setAttmField04(attend.getAttmField04());
        attendFromDB.setAttmField05(attend.getAttmField05());
        attendFromDB.setAttmField06(attend.getAttmField06());
        attendFromDB.setAttmField07(attend.getAttmField07());
        attendFromDB.setAttmField08(attend.getAttmField08());
        attendFromDB.setAttmField09(attend.getAttmField09());
        attendFromDB.setAttmField10(attend.getAttmField10());
        attendFromDB.setAttmField11(attend.getAttmField11());
        attendFromDB.setAttmField12(attend.getAttmField12());
        attendFromDB.setAttmField13(attend.getAttmField13());
        attendFromDB.setAttmField14(attend.getAttmField14());
        attendFromDB.setAttmField15(attend.getAttmField15());
        attendFromDB.setAttmField16(attend.getAttmField16());
        attendFromDB.setAttmField17(attend.getAttmField17());
        attendFromDB.setAttmField18(attend.getAttmField18());
        attendFromDB.setAttmField19(attend.getAttmField19());
        attendFromDB.setAttmField20(attend.getAttmField20());
        attendFromDB.setAttmField21(attend.getAttmField21());
        attendFromDB.setAttmField22(attend.getAttmField22());
        attendFromDB.setAttmField23(attend.getAttmField23());
        attendFromDB.setAttmField24(attend.getAttmField24());

        String dayConf = DatabaseSysConfigManager.getInstance()
                .getProperty("sys.examin.leave.mode");
        if ("day".equals(dayConf.split(",")[0])) {
            attendFromDB.setAttmDutyDays(attend.getAttmDutyDays());
            attendFromDB.setAttmOnDutyDays(attend.getAttmOnDutyDays());
            attendFromDB.setAttmOffDutyDays(attend.getAttmOffDutyDays());

            attendFromDB.setAttmLeaveAnnualDays(attend.getAttmLeaveAnnualDays());
            attendFromDB.setAttmLeaveTiaoxiuDays(attend.getAttmLeaveTiaoxiuDays());
            attendFromDB.setAttmLeaveTiaoxiu01Days(attend.getAttmLeaveTiaoxiu01Days());
            attendFromDB.setAttmLeaveCasualDays(attend.getAttmLeaveCasualDays());
            attendFromDB.setAttmLeaveMaternityDays(attend.getAttmLeaveMaternityDays());
            attendFromDB.setAttmLeaveOtherDays(attend.getAttmLeaveOtherDays());
            attendFromDB.setAttmLeaveSickDays(attend.getAttmLeaveSickDays());
            attendFromDB.setAttmLeaveSick01Days(attend.getAttmLeaveSick01Days());
            attendFromDB.setAttmLeaveSick02Days(attend.getAttmLeaveSick02Days());
            attendFromDB.setAttmLeaveTravelDays(attend.getAttmLeaveTravelDays());
            attendFromDB.setAttmLeaveOuterDays(attend.getAttmLeaveOuterDays());
            attendFromDB.setAttmLeaveWeddingDays(attend.getAttmLeaveWeddingDays());

            BigDecimal attmLeaveDays = AttendmonthlyFactory.calculateLeaveTotalDays(attend);
            attendFromDB.setAttmLeaveDays(attmLeaveDays);
        } else {
            attendFromDB.setAttmDutyHours(attend.getAttmDutyHours());
            attendFromDB.setAttmOnDutyHours(attend.getAttmOnDutyHours());
            attendFromDB.setAttmOffDutyHours(attend.getAttmOffDutyHours());

            attendFromDB.setAttmLeaveAnnualHours(attend.getAttmLeaveAnnualHours());
            attendFromDB.setAttmLeaveTiaoxiuHours(attend.getAttmLeaveTiaoxiuHours());
            attendFromDB.setAttmLeaveTiaoxiu01Hours(attend.getAttmLeaveTiaoxiu01Hours());
            attendFromDB.setAttmLeaveCasualHours(attend.getAttmLeaveCasualHours());
            attendFromDB.setAttmLeaveMaternityHours(attend.getAttmLeaveMaternityHours());
            attendFromDB.setAttmLeaveOtherHours(attend.getAttmLeaveOtherHours());
            attendFromDB.setAttmLeaveSickHours(attend.getAttmLeaveSickHours());
            attendFromDB.setAttmLeaveSick01Hours(attend.getAttmLeaveSick01Hours());
            attendFromDB.setAttmLeaveSick02Hours(attend.getAttmLeaveSick02Hours());
            attendFromDB.setAttmLeaveTravelHours(attend.getAttmLeaveTravelHours());
            attendFromDB.setAttmLeaveOuterHours(attend.getAttmLeaveOuterHours());
            attendFromDB.setAttmLeaveWeddingHours(attend.getAttmLeaveWeddingHours());

            BigDecimal attmLeaveHours = AttendmonthlyFactory.calculateLeaveTotalHours(attend);
            attendFromDB.setAttmLeaveHours(attmLeaveHours);
        }

        attendFromDB.setAttmOtHolidayHours(attend.getAttmOtHolidayHours());
        attendFromDB.setAttmOtNormalHours(attend.getAttmOtNormalHours());
        attendFromDB.setAttmOtWeekendHours(attend.getAttmOtWeekendHours());

        BigDecimal attmOvertimeHours = AttendmonthlyFactory.calculateOtTotalHours(attend);
        attendFromDB.setAttmOvertimeHours(attmOvertimeHours);

        attendFromDB.setAttmComments(attend.getAttmComments());

        this.attenddailyDAO.updateObject(attendFromDB);
        return attendFromDB;
    }

    public List getYearList() {
        Calendar date = Calendar.getInstance();
        Integer currentyear = Integer.valueOf(date.get(1));
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendmonthly.class);
        List year = this.attenddailyDAO.findByCriteria(detachedCriteria.setProjection(Projections
                .distinct(Projections.min("attmYear"))));
        String temp = (String) year.get(0);
        if ((temp == null) || ("".equals(temp))) {
            temp = currentyear + "";
        }
        Integer init = Integer.valueOf(MyTools.parseNumber(temp, Integer.class).intValue());
        year.clear();

        if (date.get(2) == 11)
            currentyear = Integer.valueOf(currentyear.intValue() + 1);
        Integer localInteger1;
        Integer localInteger2;
        for (Integer i = init; i.intValue() <= currentyear.intValue(); localInteger2 = i = Integer
                .valueOf(i.intValue() + 1)) {
            year.add(i.toString());

            localInteger1 = i;
        }

        return year;
    }

    public void updateObject(Object object) {
        this.attenddailyDAO.updateObject(object);
    }

    public void saveObject(Object obj) {
        this.attenddailyDAO.saveObject(obj);
    }

    public Attendperiod loadAttendperiod(String year, String month) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendperiod.class);
        detachedCriteria.add(Restrictions.eq("attpYear", year));
        detachedCriteria.add(Restrictions.eq("attpMonth", month));
        List list = this.attenddailyDAO.findByCriteria(detachedCriteria);
        if (list.size() > 0) {
            return (Attendperiod) list.get(0);
        }
        return null;
    }

    public Map<String, Attendmonthly> getAttmByMonth(String yearMonth) {
        String year = yearMonth.substring(0, 4);
        String month = yearMonth.substring(4);
        Map monthly = new HashMap();
        Attendperiod attdPeriod = loadAttendperiod(year, month);
        if ((attdPeriod == null) || (attdPeriod.getAttpStatus().intValue() != 2)) {
            return monthly;
        }

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendmonthly.class);
        detachedCriteria.add(Restrictions.eq("attmYearmonth", yearMonth));
        List<Attendmonthly> attendmonthList = this.attenddailyDAO.findByCriteria(detachedCriteria);

        for (Attendmonthly attendmonthly : attendmonthList) {
            monthly.put(attendmonthly.getAttmEmpId().getId(), attendmonthly);
        }
        return monthly;
    }

    public boolean setAttmByPay(String yearMonth, Empsalarypay[] payArray) {
        if (ObjectUtils.isEmpty(payArray))
            return false;

        List empList = new ArrayList();
        for (Empsalarypay pay : payArray) {
            empList.add(pay.getEspEmpno());
        }
        Map monthly = getAttmMapByEmp(yearMonth, empList);
        if (monthly == null)
            return false;

        for (Empsalarypay pay : payArray) {
            Attendmonthly attendMonthly = (Attendmonthly) monthly.get(pay.getEspEmpno().getId());
            pay.setAttendMonthly(attendMonthly);
        }
        return true;
    }

    public Map<String, Attendmonthly> getAttmMapByEmp(String yearMonth, List<Employee> empList) {
        if ((empList == null) || (empList.size() == 0))
            return null;
        String year = yearMonth.substring(0, 4);
        String month = yearMonth.substring(4);
        Attendperiod attdPeriod = loadAttendperiod(year, month);
        if ((attdPeriod == null) || (attdPeriod.getAttpStatus().intValue() != 2)) {
            return null;
        }

        DetachedCriteria dc = DetachedCriteria.forClass(Attendmonthly.class);
        if (empList.size() < 500) {
            Set empIds = new HashSet();
            Employee emp;
            for (Iterator i$ = empList.iterator(); i$.hasNext(); empIds.add(emp.getId()))
                emp = (Employee) i$.next();
            dc
                    .add(Restrictions.in(Attendmonthly.PROP_ATTM_EMP_ID + "." + Employee.PROP_ID,
                                         empIds));
        }
        dc.add(Restrictions.eq(Attendmonthly.PROP_ATTM_YEARMONTH, yearMonth));
        List<Attendmonthly> attendmonthList = this.attenddailyDAO.findByCriteria(dc);

        Map monthly = new HashMap();
        for (Attendmonthly attendmonthly : attendmonthList) {
            monthly.put(attendmonthly.getAttmEmpId().getId(), attendmonthly);
        }
        return monthly;
    }

    /** @deprecated */
    public Map<String, Attendmonthly> getAttmByPay(List<Empsalarypay> salaryPay, String yearMonth) {
        String year = yearMonth.substring(0, 4);
        String month = yearMonth.substring(4);
        Map monthly = new HashMap();
        Attendperiod attdPeriod = loadAttendperiod(year, month);
        if ((attdPeriod == null) || (attdPeriod.getAttpStatus().intValue() != 2)) {
            return monthly;
        }

        List empIds = new ArrayList();
        for (int i = 0; i < salaryPay.size(); ++i)
            empIds.add(((Empsalarypay) salaryPay.get(i)).getEspEmpno().getId());

        DetachedCriteria dc = DetachedCriteria.forClass(Attendmonthly.class);
        dc.add(Restrictions.eq("attmYearmonth", yearMonth));
        if (empIds.size() > 0)
            dc.add(Restrictions.in(Attendmonthly.PROP_ATTM_EMP_ID + ".id", empIds));
        List<Attendmonthly> attendmonthList = this.attenddailyDAO.findByCriteria(dc);

        for (Attendmonthly attendmonthly : attendmonthList) {
            monthly.put(attendmonthly.getAttmEmpId().getId(), attendmonthly);
        }
        return monthly;
    }

    public Attendmonthly getAttmByEmpId(String empId, String yearMonth) {
        String year = yearMonth.substring(0, 4);
        String month = yearMonth.substring(4);

        Attendperiod attdPeriod = loadAttendperiod(year, month);
        if ((attdPeriod == null) || (attdPeriod.getAttpStatus().intValue() != 2)) {
            return null;
        }

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendmonthly.class);
        detachedCriteria.add(Restrictions.eq("attmYearmonth", yearMonth));
        detachedCriteria.add(Restrictions.eq(Attendmonthly.PROP_ATTM_EMP_ID + ".id", empId));
        List result = this.attenddailyDAO.findByCriteria(detachedCriteria);
        if (result.size() > 0)
            return (Attendmonthly) result.get(0);
        return null;
    }

    protected Map<String, Attendmonthly> initAttendMonthlyMap(List<Employee> empList,
            List<Leaverequest> leaveRequestList, List<Overtimerequest> overtimeRequestList,
            Date beginDate, Date endDate, BigDecimal hourPerday) {
        Map result = new HashMap();
        for (Employee emp : empList) {
            Attendmonthly monthly = AttendmonthlyFactory.createAttendMonthly();
            monthly.setAttmEmpId(emp);

            doSummaryLeaveRequestData(monthly, leaveRequestList, beginDate, endDate, hourPerday);

            doSummaryOvertimeRequestData(monthly, overtimeRequestList);
            result.put(emp.getId(), monthly);
        }
        return result;
    }

    public List<Attendmonthly> getEmpMonthlyOfYear(List<Employee> empList, String year) {
        String yearMonthStart = year + "00";
        String yearMonthEnd = Integer.parseInt(year) + 1 + "00";

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendmonthly.class);
        detachedCriteria.add(Restrictions.in(Attendmonthly.PROP_ATTM_EMP_ID, empList));
        detachedCriteria.add(Restrictions.gt(Attendmonthly.PROP_ATTM_YEARMONTH, yearMonthStart));
        detachedCriteria.add(Restrictions.lt(Attendmonthly.PROP_ATTM_YEARMONTH, yearMonthEnd));
        List result = this.attenddailyDAO.findByCriteria(detachedCriteria);
        return result;
    }

    public boolean deleteAttendmonthly(Attendmonthly attendmonthly) {
        this.attenddailyDAO.deleteObject(attendmonthly);
        return true;
    }

    public IAttenddailyDAO getAttenddailyDAO() {
        return this.attenddailyDAO;
    }

    public void setAttenddailyDAO(IAttenddailyDAO attenddailyDAO) {
        this.attenddailyDAO = attenddailyDAO;
    }

    public ILeavecalendarBO getLc_BO() {
        return this.lc_BO;
    }

    public void setLc_BO(ILeavecalendarBO lc_BO) {
        this.lc_BO = lc_BO;
    }

    public LrDataCheckImpl getLrDataCheck() {
        return this.lrDataCheck;
    }

    public void setLrDataCheck(LrDataCheckImpl lrDataCheck) {
        this.lrDataCheck = lrDataCheck;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.bo.AbstractAttendmonthlyBo JD-Core Version: 0.5.4
 */