package com.hr.examin.shift;

import com.hr.examin.bo.ExaminDateUtil;
import com.hr.examin.bo.interfaces.IAttendshiftBO;
import com.hr.examin.dao.interfaces.IAttdoriginaldataDAO;
import com.hr.examin.dao.interfaces.IEmpshiftDAO;
import com.hr.examin.dao.interfaces.ILeavecalendarDAO;
import com.hr.examin.dao.interfaces.ILeaverequestDAO;
import com.hr.examin.dao.interfaces.IOvertimerequestDAO;
import com.hr.examin.domain.Attdoriginaldata;
import com.hr.examin.domain.Attendshift;
import com.hr.examin.domain.Empshift;
import com.hr.examin.domain.Leavecalendar;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Overtimerequest;
import com.hr.examin.support.AttendDailyMemory;
import com.hr.examin.support.RecordSorter;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.DateUtil;
import com.hr.util.Pager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class AttendDailyHandler {
    private Date startDate;
    private Date endDate;
    private Integer searchType;
    private List<Employee> empList;
    private Pager pager;
    private Map<String, String> sysConfigProperties;
    public BigDecimal zero;
    public String unnomalColor;

    public AttendDailyHandler() {
        this.zero = new BigDecimal(0);
        this.unnomalColor = "#FF0000";
    }

    public List<AttendDailyMemory> generateRecords() {
        if (this.empList == null) {
            this.empList = new ArrayList(0);
        }
        if (this.startDate == null) {
            this.startDate = new Date();
        }
        if (this.endDate == null) {
            this.endDate = new Date();
        }
        this.sysConfigProperties = DatabaseSysConfigManager.getInstance().getProperties();

        Map attendDailyMap = initRecords(this.startDate, this.endDate, this.empList);
        Map originalDataMap = provideOriginalData(this.startDate, this.endDate, this.empList);
        Map lrMap = provideLeaveRequestData(this.startDate, this.endDate, this.empList);
        Map orMap = provideOvertimeRequestData(this.startDate, this.endDate, this.empList);

        List records = calcRecords(attendDailyMap, originalDataMap, lrMap, orMap);

        doFilter(records, this.searchType);

        records = doSort(records);

        if (this.pager != null) {
            return doPages(records);
        }
        return records;
    }

    private void doFilter(List<AttendDailyMemory> dataList, Integer searchType) {
        ExceptionDataFilter doNothingFilter = new DefaultFilter();
        Iterator iterator = dataList.iterator();
        AttendDailyMemory bean = null;
        while (iterator.hasNext()) {
            bean = (AttendDailyMemory) iterator.next();
            boolean flag = doNothingFilter.doFilter(bean, searchType);
            if (!flag)
                iterator.remove();
        }
    }

    private List<AttendDailyMemory> doSort(List<AttendDailyMemory> records) {
        if (this.pager == null) {
            return records;
        }
        RecordSorter sorter = new RecordSorter(records);
        String property = null;
        String order = null;
        if ((this.pager.getOrder() != null) && (this.pager.getOrder().indexOf("-") > -1)) {
            String[] temp = this.pager.getOrder().split("-");
            property = temp[0];
            order = temp[1];
        }
        sorter.doSort(property, order);

        return records;
    }

    private List<AttendDailyMemory> doPages(List<AttendDailyMemory> records) {
        int size = records.size();

        int pageSize = this.pager.getPageSize();

        this.pager.init(size, pageSize);

        if (this.pager.getCurrentPage() == 0)
            this.pager.setCurrentPage(1);

        int fromIndex = (this.pager.getCurrentPage() - 1) * pageSize;
        int toIndex = this.pager.getCurrentPage() * pageSize;
        if (fromIndex < 0) {
            fromIndex = 0;
        }
        if (toIndex > size) {
            toIndex = size;
        }

        return records.subList(fromIndex, toIndex);
    }

    public Map<String, Attendshift> provideEmpShiftData(Date start, Date end, List<Employee> emps) {
        if ((emps == null) || (emps.isEmpty())) {
            return new HashMap(0);
        }

        IEmpshiftDAO dao = (IEmpshiftDAO) SpringBeanFactory.getBean("empshiftDAO");
        DetachedCriteria detachedCrteria = DetachedCriteria.forClass(Empshift.class);
        detachedCrteria.setFetchMode("empshiftShiftNo", FetchMode.JOIN);
        detachedCrteria.add(Restrictions.ge("empshiftDate", start));
        detachedCrteria.add(Restrictions.le("empshiftDate", end));
        if ((emps.size() > 0) && (emps.size() < 500)) {
            detachedCrteria.add(Restrictions.in("empshiftEmpNo", emps));
        }
        List<Empshift> empShiftList = dao.findByCriteria(detachedCrteria);

        Map resultMap = new HashMap();
        for (Empshift empShift : empShiftList) {
            String key = empShift.getEmpshiftEmpNo().getId()
                    + DateUtil.formatDate(empShift.getEmpshiftDate());
            resultMap.put(key, empShift.getEmpshiftShiftNo());
        }
        return resultMap;
    }

    private HashMap<String, Attendshift> getAttendShift(Collection<Attendshift> shiftSet) {
        HashMap resultMap = new HashMap();
        Iterator iterator = shiftSet.iterator();
        while (iterator.hasNext()) {
            Attendshift shift = (Attendshift) iterator.next();
            resultMap.put(shift.getId(), shift);
        }
        return resultMap;
    }

    private void provideDefaultShift(Map<String, Attendshift> empShiftMap, Date start, Date end,
            List<Employee> empList) {
        ILeavecalendarDAO dao = (ILeavecalendarDAO) SpringBeanFactory.getBean("leavecalendarDAO");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Leavecalendar.class);
        detachedCriteria.add(Restrictions.ge("lcDate", start));
        detachedCriteria.add(Restrictions.le("lcDate", end));
        List<Leavecalendar> leList = dao.findByCriteria(detachedCriteria);

        IAttendshiftBO attendshiftBo = (IAttendshiftBO) SpringBeanFactory.getBean("attendshiftBO");
        List asadList = attendshiftBo.searchDefaultAttendShiftRela();
        Attendshift nonStrictDefaultShift = attendshiftBo.getNonStrictDefaultAttendshift();

        Calendar calendar = Calendar.getInstance();
        Date compareObj = (Date) start.clone();
        Attendshift defaultShift = null;
        boolean needShift = false;
        boolean inLeaveCalendar = false;
        while (compareObj.compareTo(end) <= 0) {
            calendar.setTime(compareObj);
            inLeaveCalendar = false;
            needShift = false;
            for (Leavecalendar leavecalendar : leList) {
                if (leavecalendar.getLcDate().equals(compareObj)) {
                    inLeaveCalendar = true;
                    if (leavecalendar.getLcSign().intValue() == 1) {
                        needShift = true;
                        break;
                    }
                }
            }
            if ((!inLeaveCalendar) && (calendar.get(7) != 7) && (calendar.get(7) != 1)) {
                needShift = true;
            }

            if (!needShift) {
                calendar.add(5, 1);
                compareObj = calendar.getTime();
            }

            for (Employee emp : empList) {
                if (emp.getEmpShiftType().intValue() == 3) {
                    continue;
                }
                defaultShift = attendshiftBo.getDefaultAttendshiftByEmp(asadList,
                                                                        nonStrictDefaultShift, emp);
                String key = emp.getId() + DateUtil.formatDate(compareObj);
                empShiftMap.put(key, defaultShift);
            }

            calendar.add(5, 1);
            compareObj = calendar.getTime();
        }
    }

    private Map<String, AttendDailyMemory> initRecords(Date start, Date end, List<Employee> empList) {
        Map empShiftMap = provideEmpShiftData(start, end, empList);

        provideDefaultShift(empShiftMap, start, end, empList);

        Map memoryDataMap = new HashMap();
        Calendar calendar = Calendar.getInstance();
        Date compareDateObj = (Date) start.clone();
        calendar.setTime(compareDateObj);
        while (compareDateObj.compareTo(end) <= 0) {
            for (Employee employee : empList) {
                if (employee.getEmpStatus().intValue() == 0) {
                    if (employee.getEmpTerminateDate() == null)
                        continue;
                    if (compareDateObj.compareTo(employee.getEmpTerminateDate()) > 0) {
                        continue;
                    }
                }
                if (compareDateObj.compareTo(employee.getEmpJoinDate()) < 0) {
                    continue;
                }

                String dateString = DateUtil.formatDate(compareDateObj);
                AttendDailyMemory bean = new AttendDailyMemory();
                bean.setEmpObj(employee);
                bean.setEmpName(employee.getEmpName());
                bean.setEmpDistinctNo(employee.getEmpDistinctNo());
                bean.setEmpId(employee.getId());
                bean.setExaminDate(compareDateObj);
                Attendshift shift = (Attendshift) empShiftMap.get(employee.getId() + dateString);
                if (shift != null) {
                    bean.setShiftName(shift.getAttsName());
                    bean.setShiftId(shift.getId());
                    bean.setShift(shift);
                }
                memoryDataMap.put(employee.getId() + dateString, bean);
            }
            calendar.add(5, 1);
            compareDateObj = calendar.getTime();
        }
        return memoryDataMap;
    }

    private Map<String, List<Attdoriginaldata>> provideOriginalData(Date startDate, Date endDate,
            List<Employee> emps) {
        Date tempEndDate = DateUtil.dateAdd(endDate, 1);

        IAttdoriginaldataDAO dao = (IAttdoriginaldataDAO) SpringBeanFactory
                .getBean("attdoriginaldataDAO");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attdoriginaldata.class);
        if ((emps.size() > 0) && (emps.size() <= 500)) {
            detachedCriteria.add(Restrictions.in("attdEmp.id", getEmpIds(emps)));
        }
        detachedCriteria.add(Restrictions.ge(Attdoriginaldata.PROP_AOD_ATTD_DATE, startDate));
        detachedCriteria.add(Restrictions.le(Attdoriginaldata.PROP_AOD_ATTD_DATE, tempEndDate));
        List<Attdoriginaldata> resultList = dao.findByCriteria(detachedCriteria);

        Map origEmpDateMap = new HashMap();
        String key = null;
        List tempList = null;

        for (Attdoriginaldata data : resultList) {
            key = data.getAttdEmp().getId() + DateUtil.formatDate(data.getAodAttdDate());
            tempList = (List) origEmpDateMap.get(key);
            if (tempList == null) {
                tempList = new ArrayList();
                origEmpDateMap.put(key, tempList);
            }
            tempList.add(data);
        }

        return origEmpDateMap;
    }

    private String[] getEmpIds(List<Employee> emps) {
        String[] empIds = new String[emps.size()];
        Employee emp = null;
        for (int i = 0; (emps != null) && (i < emps.size()); ++i) {
            emp = (Employee) emps.get(i);
            empIds[i] = emp.getId();
        }
        return empIds;
    }

    private Map<String, List<Leaverequest>> provideLeaveRequestData(Date startDate, Date endDate,
            List<Employee> emps) {
        Date tempEndDate = DateUtil.dateAdd(endDate, 2);

        Integer[] status = { Integer.valueOf(15), Integer.valueOf(16) };
        ILeaverequestDAO dao = (ILeaverequestDAO) SpringBeanFactory.getBean("leaverequestDAO");
        DetachedCriteria detachedCrteria = DetachedCriteria.forClass(Leaverequest.class);
        if ((emps.size() > 0) && (emps.size() <= 500)) {
            detachedCrteria.add(Restrictions.in("lrEmpNo.id", getEmpIds(emps)));
        }
        detachedCrteria.add(Restrictions.in(Leaverequest.PROP_LR_STATUS, status));
        detachedCrteria.add(Restrictions.or(Restrictions.or(Restrictions.and(Restrictions
                .ge(Leaverequest.PROP_LR_START_DATE, startDate), Restrictions
                .lt(Leaverequest.PROP_LR_START_DATE, tempEndDate)), Restrictions.and(Restrictions
                .ge(Leaverequest.PROP_LR_END_DATE, startDate), Restrictions
                .lt(Leaverequest.PROP_LR_END_DATE, tempEndDate))), Restrictions.and(Restrictions
                .lt(Leaverequest.PROP_LR_START_DATE, startDate), Restrictions
                .ge(Leaverequest.PROP_LR_END_DATE, tempEndDate))));

        List<Leaverequest> lrList = dao.findByCriteria(detachedCrteria);

        Map empLrMap = new HashMap();
        List tempLrList = null;
        for (Leaverequest lr : lrList) {
            tempLrList = (List) empLrMap.get(lr.getLrEmpNo().getId());
            if (tempLrList == null) {
                tempLrList = new ArrayList();
                empLrMap.put(lr.getLrEmpNo().getId(), tempLrList);
            }
            tempLrList.add(lr);
        }

        return empLrMap;
    }

    private Map<String, List<Overtimerequest>> provideOvertimeRequestData(Date startDate,
            Date endDate, List<Employee> emps) {
        Date tempEndDate = DateUtil.dateAdd(endDate, 2);

        Integer[] status = { Integer.valueOf(15), Integer.valueOf(16) };
        IOvertimerequestDAO dao = (IOvertimerequestDAO) SpringBeanFactory
                .getBean("overtimerequestDAO");
        DetachedCriteria detachedCrteria = DetachedCriteria.forClass(Overtimerequest.class);
        if ((emps.size() > 0) && (emps.size() <= 500)) {
            detachedCrteria.add(Restrictions.in("orEmpNo.id", getEmpIds(emps)));
        }
        detachedCrteria.add(Restrictions.in(Overtimerequest.PROP_OR_STATUS, status));
        detachedCrteria.add(Restrictions.and(Restrictions.ge(Overtimerequest.PROP_OR_START_DATE,
                                                             startDate), Restrictions
                .lt(Overtimerequest.PROP_OR_START_DATE, tempEndDate)));

        detachedCrteria.add(Restrictions.isNull(Overtimerequest.PROP_OR_TIAOXIU_HOURS));
        List<Overtimerequest> orList = dao.findByCriteria(detachedCrteria);

        Map empOrMap = new HashMap();
        List tempOrList = null;
        for (Overtimerequest or : orList) {
            tempOrList = (List) empOrMap.get(or.getOrEmpNo().getId());
            if (tempOrList == null) {
                tempOrList = new ArrayList();
                empOrMap.put(or.getOrEmpNo().getId(), tempOrList);
            }
            tempOrList.add(or);
        }

        return empOrMap;
    }

    private List<AttendDailyMemory> calcRecords(Map<String, AttendDailyMemory> map,
            Map<String, List<Attdoriginaldata>> originalDataMap,
            Map<String, List<Leaverequest>> lrMap, Map<String, List<Overtimerequest>> orMap) {
        List dataList = new ArrayList();
        String calcDayConfig = (String) this.sysConfigProperties.get("sys.shift.card.calcday");
        String dayHourConfig = (String) this.sysConfigProperties.get("sys.examin.leave.mode");

        Set hashSet = map.keySet();
        Iterator iterator = hashSet.iterator();
        String key = null;
        String empId = null;
        AttendDailyMemory bean = null;
        List origDataList = null;
        List lrList = null;
        List orList = null;
        String temp = null;

        while (iterator.hasNext()) {
            key = (String) iterator.next();
            bean = (AttendDailyMemory) map.get(key);
            empId = bean.getEmpId();

            origDataList = (List) originalDataMap.get(key);
            lrList = (List) lrMap.get(empId);
            orList = (List) orMap.get(empId);

            getLeaveHours(bean, lrList, this.sysConfigProperties);

            if (bean.getEmpObj().getEmpShiftType().intValue() != 0) {
                temp = getOnOffDutyTime(bean, origDataList, this.sysConfigProperties);
                if ("leaveButCard".equals(temp)) {
                    getOrHours(bean, orList);
                    bean.setDutyHours(bean.getOvertimeHours());
                    dataList.add(bean);
                }

            }

            getOrHours(bean, orList);

            gernerateNewAttdSession(bean, lrList);
            calcOthers(bean, this.sysConfigProperties);

            bean.convertFieldsOfDay(calcDayConfig, dayHourConfig);

            dataList.add(bean);
        }

        return dataList;
    }

    private void getLeaveHours(AttendDailyMemory bean, List<Leaverequest> lrList,
            Map<String, String> sysMap) {
        if (bean.getShift() == null) {
            return;
        }
        if (lrList == null) {
            return;
        }
        int minutes = 0;

        for (Leaverequest lr : lrList) {
            minutes += ExaminDateUtil.minutesInShift(lr.getLrStartDate(), lr.getLrEndDate(), bean
                    .getExaminDate(), bean.getShift().getAttsSession());
        }

        bean.setLeaveHours(ExaminDateUtil
                .convertMinutesToHours(new BigDecimal(minutes), (String) sysMap
                        .get("sys.shift.card.calchour")));
    }

    private String getOnOffDutyTime(AttendDailyMemory bean, List<Attdoriginaldata> origDataList,
            Map<String, String> sysMap) {
        if (bean.getShift() != null) {
            String startMinute = (String) sysMap.get("sys.shift.start.minute");
            String endMinute = (String) sysMap.get("sys.shift.end.minute");

            Date[] onOffTimes = ExaminDateUtil.getShiftArr(bean.getExaminDate(), bean.getShift()
                    .getAttsSession());
            Date onTime = DateUtil.dateTimeAdd(onOffTimes[0], -Integer.parseInt(startMinute), 12);
            Date offTime = DateUtil.dateTimeAdd(onOffTimes[1], Integer.parseInt(endMinute), 12);

            deleteInvalidRec(origDataList, sysMap, onTime, offTime);
        }

        if ((origDataList == null) || (origDataList.size() == 0)) {
            if (bean.getShift() == null)
                return null;

            if (bean.getLeaveHours().compareTo(bean.getShift().getAttsWorkingHour()) >= 0) {
                return null;
            }
            bean.setComments("未刷卡，缺勤");
            bean.setDisplayColor(this.unnomalColor);
            return null;
        }

        if (origDataList.size() == 1) {
            bean.setOnDutyTime(((Attdoriginaldata) origDataList.get(0)).getAodCardTime());
            bean.setOffDutyTime(((Attdoriginaldata) origDataList.get(0)).getAodCardTime());

            if (bean.getShift() == null)
                bean.setComments("无班次，有一次刷卡");
            else {
                bean.setComments("只有一次刷卡，缺勤");
            }
            bean.setDisplayColor(this.unnomalColor);
            return null;
        }

        bean.setOnDutyTime(getMinorMaxDateData(origDataList, "min"));
        bean.setOffDutyTime(getMinorMaxDateData(origDataList, "max"));

        if (bean.getShift() == null) {
            bean.setComments("无班次，有上下班刷卡");
            bean.setDisplayColor(this.unnomalColor);
            return null;
        }
        if (bean.getLeaveHours().compareTo(bean.getShift().getAttsWorkingHour()) >= 0) {
            bean.setComments("全天请假，有刷卡");
            bean.setDisplayColor(this.unnomalColor);
            return "leaveButCard";
        }

        return null;
    }

    private void getOrHours(AttendDailyMemory bean, List<Overtimerequest> orList) {
        if (orList == null)
            return;

        BigDecimal orHours = new BigDecimal(0);
        Iterator iterator = orList.iterator();
        while (iterator.hasNext()) {
            Overtimerequest or = (Overtimerequest) iterator.next();
            Date orDate = DateUtil.convDateTimeToDate(or.getOrStartDate());

            if (orDate.equals(bean.getExaminDate())) {
                orHours = orHours.add(or.getOrTotalHours());
            } else if ((DateUtil.dateDiff(bean.getExaminDate(), orDate, 5) == 1)
                    && (bean.getShift() != null)
                    && (bean.getShift().getAttsNightShift().intValue() == 1)) {
                Date offDutyTime = ExaminDateUtil.getShiftArr(bean.getExaminDate(), bean.getShift()
                        .getAttsSession())[1];
                if (or.getOrStartDate().getTime() == offDutyTime.getTime()) {
                    orHours = orHours.add(or.getOrTotalHours());
                    iterator.remove();
                }
            }

        }

        bean.setOvertimeHours(orHours);
    }

    private void gernerateNewAttdSession(AttendDailyMemory bean, List<Leaverequest> lrList) {
        if (bean.getShift() == null)
            return;

        List<Date[]> cutList = new ArrayList();

        Date[] shiftDateTime = ExaminDateUtil.getSplitDateByAttendShift(bean.getExaminDate(), bean
                .getShift().getAttsSession());

        for (int i = 0; i < shiftDateTime.length / 2; ++i) {
            Date[] cutDate = new Date[2];
            cutDate[0] = shiftDateTime[(i * 2)];
            cutDate[1] = shiftDateTime[(i * 2 + 1)];
            cutList.add(cutDate);
            if (lrList == null) {
                continue;
            }

            for (Leaverequest lr : lrList) {
                if (lr.getLrEndDate().getTime() <= cutDate[0].getTime())
                    continue;
                if (cutDate[1].getTime() <= lr.getLrStartDate().getTime()) {
                    continue;
                }

                if ((lr.getLrStartDate().getTime() <= cutDate[0].getTime())
                        && (cutDate[1].getTime() <= lr.getLrEndDate().getTime())) {
                    cutList.remove(cutDate);
                    break;
                }

                if ((lr.getLrStartDate().getTime() <= cutDate[0].getTime())
                        && (cutDate[0].getTime() < lr.getLrEndDate().getTime())
                        && (lr.getLrEndDate().getTime() < cutDate[1].getTime())) {
                    cutDate[0] = lr.getLrEndDate();
                }

                if ((lr.getLrStartDate().getTime() < cutDate[1].getTime())
                        && (cutDate[1].getTime() <= lr.getLrEndDate().getTime())
                        && (lr.getLrStartDate().getTime() > cutDate[0].getTime())) {
                    cutDate[1] = lr.getLrStartDate();
                }

                if ((cutDate[0].getTime() < lr.getLrStartDate().getTime())
                        && (lr.getLrEndDate().getTime() < cutDate[1].getTime())) {
                    Date tempDate = cutDate[1];
                    cutDate[1] = lr.getLrStartDate();

                    Date[] newCutDate = new Date[2];
                    newCutDate[0] = lr.getLrEndDate();
                    newCutDate[1] = tempDate;
                    cutList.add(newCutDate);
                }
            }
        }

        bean.getShift().setNewAttdSessionList(cutList);

        String newAttdSession = "";
        for (Date[] cutDate : cutList) {
            String cutStrStart = DateUtil.formatDateToS(cutDate[0], "yyyy-MM-dd HH:mm")
                    .substring(11);
            String cutStrEnd = DateUtil.formatDateToS(cutDate[1], "yyyy-MM-dd HH:mm").substring(11);
            newAttdSession = newAttdSession + cutStrStart + "-" + cutStrEnd + ",";
        }
        bean.getShift().setNewAttdSession(newAttdSession);
    }

    private void calcOthers(AttendDailyMemory bean, Map<String, String> sysMap) {
        if (bean.getShift() == null) {
            bean.setDutyHours(bean.getOvertimeHours());
            return;
        }

        if (bean.getEmpObj().getEmpShiftType().intValue() == 0) {
            bean.setDutyHours(bean.getShift().getAttsWorkingHour().subtract(bean.getLeaveHours())
                    .add(bean.getOvertimeHours()));
            bean.setOughtDutyHours(bean.getShift().getAttsWorkingHour());

            return;
        }

        if ((bean.getOnDutyTime() == null) || (bean.getOffDutyTime() == null)
                || (bean.getOnDutyTime().getTime() == bean.getOffDutyTime().getTime())) {
            bean.setDutyHours(bean.getOvertimeHours());
            bean.setOughtDutyHours(bean.getShift().getAttsWorkingHour());
            bean
                    .setAbsentHours(bean.getShift().getAttsWorkingHour()
                            .subtract(bean.getLeaveHours()));
            return;
        }

        String config = (String) sysMap.get("sys.examin.absent.minute");
        BigDecimal absentConfig = new BigDecimal(
                ((config != null) && (config.trim().length() > 0)) ? config : "120");
        BigDecimal lateMinutes = new BigDecimal(0);
        BigDecimal earlyMinutes = new BigDecimal(0);
        BigDecimal absentHours = new BigDecimal(0);
        String lateMinutes_str = (String) sysMap.get("sys.shift.late.minute");
        String earlyleave_str = (String) sysMap.get("sys.shift.earlyleave.minute");
        BigDecimal lateMinutes_b = new BigDecimal(lateMinutes_str.split(",")[0]);
        BigDecimal earlyleave_b = new BigDecimal(earlyleave_str.split(",")[0]);

        List newSessList = bean.getShift().getNewAttdSessionList();
        Date oughtOnDutyTime = ((Date[]) newSessList.get(0))[0];
        Date oughtOffDutyTime = ((Date[]) newSessList.get(newSessList.size() - 1))[1];

        Date onDutyTime = bean.getOnDutyTime();
        Date offDutyTime = bean.getOffDutyTime();
        lateMinutes = new BigDecimal((onDutyTime.getTime() - oughtOnDutyTime.getTime()) / 60000L);
        if (lateMinutes.compareTo(lateMinutes_b) >= 0) {
            long minutesInSpare = getShiftSpareInTimeSession(bean.getShift(), bean.getExaminDate(),
                                                             oughtOnDutyTime, onDutyTime);
            lateMinutes = lateMinutes.subtract(new BigDecimal(minutesInSpare));
            bean.setLateMinutes(lateMinutes);
            bean
                    .setComments(((bean.getComments() != null) && (bean.getComments().trim()
                            .length() > 0)) ? bean.getComments() + ",迟到" : "迟到");
            bean.setDisplayColor(this.unnomalColor);
        }

        earlyMinutes = new BigDecimal((oughtOffDutyTime.getTime() - offDutyTime.getTime()) / 60000L);
        if (earlyMinutes.compareTo(earlyleave_b) >= 0) {
            long minutesInSpare = getShiftSpareInTimeSession(bean.getShift(), bean.getExaminDate(),
                                                             offDutyTime, oughtOffDutyTime);
            earlyMinutes = earlyMinutes.subtract(new BigDecimal(minutesInSpare));
            bean.setEarlyMinutes(earlyMinutes);
            bean
                    .setComments(((bean.getComments() != null) && (bean.getComments().trim()
                            .length() > 0)) ? bean.getComments() + ",早退" : "早退");
            bean.setDisplayColor(this.unnomalColor);
        }

        if (lateMinutes.add(earlyMinutes).compareTo(absentConfig) >= 0) {
            absentHours = ExaminDateUtil
                    .convertMinutesToHours(
                                           new BigDecimal(lateMinutes.add(earlyMinutes).longValue()),
                                           (String) sysMap.get("sys.shift.card.calchour"));
            bean.setAbsentHours(absentHours);
            bean.setComments("缺勤");
            bean.setDisplayColor(this.unnomalColor);

            bean.setLateMinutes(this.zero);
            bean.setEarlyMinutes(this.zero);
        }

        BigDecimal dutyHours = bean.getShift().getAttsWorkingHour()
                .subtract(
                          ExaminDateUtil.convertMinutesToHours(new BigDecimal(lateMinutes
                                  .add(earlyMinutes).longValue()), (String) sysMap
                                  .get("sys.shift.card.calchour"))).subtract(bean.getLeaveHours())
                .add(bean.getOvertimeHours());

        bean.setDutyHours(dutyHours);

        bean.setOughtDutyHours(bean.getShift().getAttsWorkingHour());
    }

    private long getShiftSpareInTimeSession(Attendshift shift, Date examinDate, Date start, Date end) {
        long minutes = 0L;
        ExaminDateUtil examinDateUtil = ExaminDateUtil.getInstance();
        List cutList = shift.getNewAttdSessionList();
        if (cutList == null) {
            return minutes;
        }

        if (cutList.size() == 1) {
            long commentM = getCommenTimes(((Date[]) cutList.get(0))[0],
                                           ((Date[]) cutList.get(0))[1], start, end).longValue();
            if (commentM > 0L) {
                return (end.getTime() - start.getTime()) / 60000L - commentM;
            }
            return (end.getTime() - start.getTime()) / 60000L;
        }

        String spareSession = "";
        Date[] cutArr = null;
        Date[] cutArrNext = null;
        for (int i = 0; i <= cutList.size() - 2; ++i) {
            cutArr = (Date[]) cutList.get(i);
            cutArrNext = (Date[]) cutList.get(i + 1);
            String time1 = DateUtil.formatDateToS(cutArr[1], "yyyy-MM-dd HH:mm").substring(11);
            String time2 = DateUtil.formatDateToS(cutArrNext[0], "yyyy-MM-dd HH:mm").substring(11);
            spareSession = spareSession + time1 + "-" + time2 + ",";
        }

        minutes = ExaminDateUtil.minutesInShift(start, end, examinDate, spareSession
                .substring(0, spareSession.length() - 1));

        return minutes;
    }

    private Date getMinorMaxDateData(List<Attdoriginaldata> origDataList, String minormax) {
        Attdoriginaldata data = (Attdoriginaldata) origDataList.get(0);
        Date maxDate = data.getAodCardTime();
        Date minDate = data.getAodCardTime();

        for (Attdoriginaldata dataTemp : origDataList) {
            Date currDate = dataTemp.getAodCardTime();
            if (currDate.getTime() > maxDate.getTime())
                maxDate = currDate;
            if (currDate.getTime() < minDate.getTime())
                minDate = currDate;
        }

        if ("min".equals(minormax)) {
            return minDate;
        }
        return maxDate;
    }

    private void deleteInvalidRec(List<Attdoriginaldata> origList, Map<String, String> sysMap,
            Date onTime_b, Date offTime_a) {
        if ((origList == null) || (origList.size() == 0)) {
            return;
        }

        String configValue = (String) sysMap.get("sys.shift.cardrepeat.minute");
        Long minute = Long.valueOf(Long.parseLong(configValue));

        Attdoriginaldata data = null;
        Attdoriginaldata data1 = null;
        Date currentDate = null;
        Date currentDate1 = null;

        Iterator iterator = origList.iterator();
        while (iterator.hasNext()) {
            data = (Attdoriginaldata) iterator.next();
            if ((data.getAodCardTime().getTime() >= onTime_b.getTime())
                    && (offTime_a.getTime() >= data.getAodCardTime().getTime()))
                continue;
            iterator.remove();
        }

        for (int i = 0; (origList != null) && (i < origList.size()); ++i) {
            data = (Attdoriginaldata) origList.get(i);
            currentDate = data.getAodCardTime();
            if (currentDate != null) {
                iterator = origList.iterator();
                while (iterator.hasNext()) {
                    data1 = (Attdoriginaldata) iterator.next();
                    if ((data1 != null) && (data != null)
                            && (data1.getAodId().equals(data.getAodId()))) {
                        continue;
                    }
                    currentDate1 = data1.getAodCardTime();
                    if (Math.abs(currentDate1.getTime() - currentDate.getTime()) < minute
                            .longValue() * 60L * 1000L)
                        ;
                    iterator.remove();
                }
            }
        }
    }

    public Long getCommenTimes(Date on, Date off, Date start, Date end) {
        long seconds = 0L;
        if (end.getTime() >= on.getTime())
            if (start.getTime() <= off.getTime()) {
                if ((start.getTime() < on.getTime()) && (off.getTime() < end.getTime()))
                    seconds = (off.getTime() - on.getTime()) / 1000L;
                else if ((start.getTime() < on.getTime()) && (on.getTime() < end.getTime()))
                    seconds = (end.getTime() - on.getTime()) / 1000L;
                else if ((on.getTime() <= start.getTime()) && (end.getTime() <= off.getTime()))
                    seconds = (end.getTime() - start.getTime()) / 1000L;
                else if ((start.getTime() < off.getTime()) && (off.getTime() < end.getTime())) {
                    seconds = (off.getTime() - start.getTime()) / 1000L;
                }
            }
        return Long.valueOf(seconds / 60L);
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setEmpList(List<Employee> empList) {
        this.empList = empList;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public Integer getSearchType() {
        return this.searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.shift.AttendDailyHandler JD-Core Version: 0.5.4
 */