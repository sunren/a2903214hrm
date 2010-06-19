package com.hr.examin.bo;

import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Location;
import com.hr.examin.bo.interfaces.IEmpshiftBo;
import com.hr.examin.dao.interfaces.IEmpshiftDAO;
import com.hr.examin.domain.Attendshift;
import com.hr.examin.domain.Attendshiftareadept;
import com.hr.examin.domain.Empshift;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Overtimerequest;
import com.hr.profile.domain.Employee;
import com.hr.util.DateUtil;
import java.math.BigDecimal;
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
import java.util.TreeSet;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class EmpshiftBOImpl implements IEmpshiftBo {
    private IEmpshiftDAO empshiftDAO;

    public boolean addEmpshift(Empshift empshift) {
        this.empshiftDAO.saveObject(empshift);
        return true;
    }

    public boolean deleteEmpshift(Empshift empshift) {
        this.empshiftDAO.deleteObject(empshift);
        return true;
    }

    public Empshift getEmpshift(String id) {
        this.empshiftDAO.loadObject(Empshift.class, id, null, new boolean[0]);
        return null;
    }

    public boolean updateEmpshift(Empshift empshift) {
        this.empshiftDAO.saveOrupdate(empshift);
        return false;
    }

    public boolean batchSaveEmpshift(List<Empshift> empshiftList, List<Empshift> deleteList) {
        if (deleteList.size() > 0) {
            String shiftIdStr = "";
            for (Empshift empshift : deleteList) {
                shiftIdStr = shiftIdStr + "'" + empshift.getId() + "',";
            }
            shiftIdStr = shiftIdStr.substring(0, shiftIdStr.length() - 1);
            String hql = "delete from Empshift where id in (" + shiftIdStr + ")";
            this.empshiftDAO.exeHql(hql);
        }
        this.empshiftDAO.saveOrupdate(empshiftList);
        return true;
    }

    public List<Empshift> getEmpShiftList(Date startDate, Date endDate, String empId) {
        DetachedCriteria dc = DetachedCriteria.forClass(Empshift.class);
        startDate = DateUtil.convDateTimeToDate(startDate);
        endDate = DateUtil.convDateTimeToDate(endDate);

        dc.setFetchMode(Empshift.PROP_EMPSHIFT_SHIFT_NO, FetchMode.JOIN);
        dc.add(Restrictions.eq(Empshift.PROP_EMPSHIFT_EMP_NO, new Employee(empId)));
        dc.add(Restrictions.ge(Empshift.PROP_EMPSHIFT_DATE, startDate));
        dc.add(Restrictions.le(Empshift.PROP_EMPSHIFT_DATE, endDate));
        dc.addOrder(Order.asc(Empshift.PROP_EMPSHIFT_DATE));
        return this.empshiftDAO.findByCriteria(dc);
    }

    public HashMap<String, Empshift> getEmpshiftList(String[] empIds, Date[] allDays) {
        DetachedCriteria dc = DetachedCriteria.forClass(Empshift.class);
        dc.setFetchMode(Empshift.PROP_EMPSHIFT_SHIFT_NO, FetchMode.JOIN);
        dc.add(Restrictions.in("empshiftEmpNo.id", empIds));
        dc.add(Restrictions.in(Empshift.PROP_EMPSHIFT_DATE, allDays));

        List<Empshift> empshiftList = this.empshiftDAO.findByCriteria(dc);
        HashMap map = new HashMap();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (Empshift empshift : empshiftList) {
            map
                    .put(empshift.getEmpshiftEmpNo().getId()
                            + format.format(empshift.getEmpshiftDate()), empshift);
        }
        return map;
    }

    public HashMap<String, Empshift> getEmpshiftList(String[] empIds, Date startDate, Date endDate) {
        DetachedCriteria dc = DetachedCriteria.forClass(Empshift.class);
        dc.setFetchMode(Empshift.PROP_EMPSHIFT_SHIFT_NO, FetchMode.JOIN);
        dc.add(Restrictions.in("empshiftEmpNo.id", empIds));
        dc.add(Restrictions.ge(Empshift.PROP_EMPSHIFT_DATE, startDate));
        dc.add(Restrictions.le(Empshift.PROP_EMPSHIFT_DATE, endDate));

        List<Empshift> empshiftList = this.empshiftDAO.findByCriteria(dc);
        HashMap map = new HashMap();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (Empshift empshift : empshiftList) {
            map
                    .put(empshift.getEmpshiftEmpNo().getId()
                            + format.format(empshift.getEmpshiftDate()), empshift);
        }
        return map;
    }

    public Integer isInWorkShift(Date examinCheckDate, List<Empshift> list) {
        int inShift = 0;

        if ((list != null) && (list.size() >= 1)) {
            Iterator empShiftIterator = list.iterator();
            while (empShiftIterator.hasNext()) {
                Empshift es = (Empshift) empShiftIterator.next();

                inShift = ExaminDateUtil.isTimeInShift(examinCheckDate, es.getEmpshiftDate(), es
                        .getEmpshiftShiftNo().getAttsSession());

                if (inShift != 0) {
                    return Integer.valueOf(inShift);
                }
            }
        }
        return Integer.valueOf(inShift);
    }

    public int computeTotalLeaveMinutes(Date startDate, Date endDate, List<Empshift> list) {
        int minuteInShift = 0;

        if ((list != null) && (list.size() >= 1)) {
            Iterator empShiftIterator = list.iterator();
            while (empShiftIterator.hasNext()) {
                Empshift es = (Empshift) empShiftIterator.next();

                int i = DateUtil.dateDiff(startDate, es.getEmpshiftDate(), 5);
                int j = DateUtil.dateDiff(endDate, es.getEmpshiftDate(), 5);

                if (i < -1)
                    continue;
                if (j > 0) {
                    continue;
                }

                if (((i != -1) && (j != -1))
                        || ((es.getEmpshiftShiftNo().getAttsNightShift().intValue() != 1)
                                && (i != 0) && (j != 0))) {
                    minuteInShift += ExaminDateUtil.minutesInShift(startDate, endDate, es
                            .getEmpshiftDate(), es.getEmpshiftShiftNo().getAttsSession());
                } else {
                    double minute = es.getEmpshiftShiftNo().getAttsWorkingHour().doubleValue() * 60.0D;
                    minuteInShift = (int) (minuteInShift + minute);
                }
            }
        }
        return minuteInShift;
    }

    public List<String> validateShift(List<Empshift> empShiftList, Map<String, Empshift> oldShiftMap) {
        List errorList = new ArrayList();
        if (empShiftList.isEmpty()) {
            return errorList;
        }

        TreeSet dateSet = new TreeSet();
        Set empSet = new HashSet();
        for (Empshift empShift : empShiftList) {
            dateSet.add(empShift.getEmpshiftDate());
            empSet.add(empShift.getEmpshiftEmpNo().getId());
        }
        List<Leaverequest> leaveRequestList = getEmpLeaveRequestList((Date) dateSet.first(), (Date) dateSet
                .last(), empSet);
        List<Overtimerequest> overtimeRequest = getEmpOvertimeRequestList((Date) dateSet.first(), (Date) dateSet
                .last(), empSet);

        ExaminDateUtil examinDateUtil = ExaminDateUtil.getInstance();
        for (Iterator i$ = empShiftList.iterator(); i$.hasNext();) {
            Empshift empShift = (Empshift) i$.next();
            Date shiftDate = empShift.getEmpshiftDate();
            String key = empShift.getEmpshiftEmpNo().getId() + DateUtil.formatDate(shiftDate);
            Attendshift oldShift = (oldShiftMap.get(key) == null) ? null : ((Empshift) oldShiftMap
                    .get(key)).getEmpshiftShiftNo();
            Attendshift newShift = empShift.getEmpshiftShiftNo();

            if ((newShift == null) && (oldShift == null))
                continue;
            if ((newShift != null) && (oldShift != null)
                    && (newShift.getId().equals(oldShift.getId()))) {
                continue;
            }

            for (Leaverequest lr : leaveRequestList) {
                if (!empShift.getEmpshiftEmpNo().getId().equals(lr.getLrEmpNo().getId())) {
                    continue;
                }

                if (oldShift != null) {
                    int todayLrTotal = ExaminDateUtil.minutesInShift(lr.getLrStartDate(), lr
                            .getLrEndDate(), shiftDate, oldShift.getAttsSession());
                    if (todayLrTotal > 0) {
                        errorList.add(lr.getLrEmpNo().getEmpName() + DateUtil.formatDate(shiftDate) + oldShift.getAttsName() + "有请假(编号为" + lr.getLrNo() + ")！");
                    }
                } else if ((shiftDate.compareTo(lr.getLrStartDate()) >= 0)
                        && (shiftDate.compareTo(lr.getLrEndDate()) <= 0)) {
                    errorList.add(lr.getLrEmpNo().getEmpName() + DateUtil.formatDate(shiftDate) + newShift.getAttsName() + "有请假(编号为" + lr.getLrNo() + ")！");
                }

            }

            if (newShift == null) {
                continue;
            }
            for (Overtimerequest or : overtimeRequest) {
                if (!empShift.getEmpshiftEmpNo().getId().equals(or.getOrEmpNo().getId())) {
                    continue;
                }

                int todayOrTotal = ExaminDateUtil.minutesInShift(or.getOrStartDate(), or
                        .getOrEndDate(), shiftDate, newShift.getAttsSession());
                if (todayOrTotal > 0) {
                    errorList.add(or.getOrEmpNo().getEmpName() + DateUtil.formatDate(shiftDate) + newShift.getAttsName() + "与加班冲突(编号为" + or.getOrNo() + ")！");
                }

                if (newShift.getAttsNightShift().intValue() == 1) {
                    Date nextDay = DateUtil.dateAdd(shiftDate, 1);
                    int nextDayTotal = ExaminDateUtil.minutesInShift(or.getOrStartDate(), or
                            .getOrEndDate(), nextDay, newShift.getAttsSession());
                    if (nextDayTotal > 0)
                        errorList.add(or.getOrEmpNo().getEmpName() + DateUtil.formatDate(nextDay) + newShift.getAttsName() + "与加班冲突(编号为" + or.getOrNo() + ")！");
                }
            }
        }

        Empshift empShift;
        Date shiftDate;
        Attendshift newShift;
        return errorList;
    }

    private List<Overtimerequest> getEmpOvertimeRequestList(Date startDate, Date endDate,
            Set<String> emps) {
        Calendar toDate = Calendar.getInstance();
        toDate.setTime(endDate);
        toDate.add(5, 1);
        DetachedCriteria detachedCrteria = DetachedCriteria.forClass(Overtimerequest.class);
        detachedCrteria.setFetchMode(Overtimerequest.PROP_OR_EMP_NO, FetchMode.JOIN);
        detachedCrteria.add(Restrictions.or(Restrictions.and(Restrictions.ge("orStartDate",
                                                                             startDate),
                                                             Restrictions.lt("orStartDate", toDate
                                                                     .getTime())), Restrictions
                .and(Restrictions.lt("orEndDate", toDate.getTime()), Restrictions.ge("orEndDate",
                                                                                     startDate))));

        detachedCrteria.add(Restrictions.not(Restrictions.in(Overtimerequest.PROP_OR_STATUS,
                                                             new Integer[] { Integer.valueOf(21),
                                                                     Integer.valueOf(22) })));
        detachedCrteria.add(Restrictions.in("orEmpNo.id", emps));
        return this.empshiftDAO.findByCriteria(detachedCrteria);
    }

    private List<Leaverequest> getEmpLeaveRequestList(Date startDate, Date endDate, Set<String> emps) {
        Calendar toDate = Calendar.getInstance();
        toDate.setTime(endDate);
        toDate.add(5, 1);
        DetachedCriteria detachedCrteria = DetachedCriteria.forClass(Leaverequest.class);
        detachedCrteria.setFetchMode(Leaverequest.PROP_LR_EMP_NO, FetchMode.JOIN);
        detachedCrteria.add(Restrictions.or(Restrictions.or(Restrictions.and(Restrictions
                .ge(Leaverequest.PROP_LR_START_DATE, startDate), Restrictions
                .lt(Leaverequest.PROP_LR_START_DATE, toDate.getTime())), Restrictions
                .and(Restrictions.lt(Leaverequest.PROP_LR_END_DATE, toDate.getTime()), Restrictions
                        .ge(Leaverequest.PROP_LR_END_DATE, startDate))), Restrictions
                .and(Restrictions.lt(Leaverequest.PROP_LR_START_DATE, startDate), Restrictions
                        .ge(Leaverequest.PROP_LR_END_DATE, toDate.getTime()))));

        detachedCrteria.add(Restrictions.not(Restrictions.in(Leaverequest.PROP_LR_STATUS,
                                                             new Integer[] { Integer.valueOf(21),
                                                                     Integer.valueOf(22) })));
        detachedCrteria.add(Restrictions.in("lrEmpNo.id", emps));
        return this.empshiftDAO.findByCriteria(detachedCrteria);
    }

    public IEmpshiftDAO getEmpshiftDAO() {
        return this.empshiftDAO;
    }

    public void setEmpshiftDAO(IEmpshiftDAO empshiftDAO) {
        this.empshiftDAO = empshiftDAO;
    }

    private Map<String, Set<String>> getAllRelationShift() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendshiftareadept.class);
        detachedCriteria.createAlias("asadShiftId", "shift", 1);
        detachedCriteria.add(Restrictions.eq("shift.attsStatus", new Integer(1)));
        List<Attendshiftareadept> relaList = this.empshiftDAO.findByCriteria(detachedCriteria);
        Map map = new HashMap();
        Set tempSet = null;
        String key = null;
        for (Attendshiftareadept asad : relaList) {
            key = (asad.getAsadAreaId() != null) ? asad.getAsadAreaId().getId() : asad
                    .getAsadDeptId().getId();
            tempSet = (Set) map.get(key);
            if (map.get(key) == null) {
                tempSet = new HashSet();
                map.put(key, tempSet);
            }
            tempSet.add(asad.getAsadShiftId().getId());
        }
        return map;
    }

    private Map<String, Employee> getEmpMapByIds(String[] Ids) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.add(Restrictions.in("id", Ids));
        List<Employee> empList = this.empshiftDAO.findByCriteria(detachedCriteria);
        Map empMap = new HashMap();
        for (Employee emp : empList) {
            empMap.put(emp.getId(), emp);
        }
        return empMap;
    }

    private List<String> getNonStrictShift() {
        String hql = "select id from Attendshift where attsStatus=1 and  attsStricked=0";
        List shiftIdList = this.empshiftDAO.exeHqlList(hql);
        return shiftIdList;
    }

    public void checkShiftSchedule(List<String> errorList, String[] empIds,
            List<Empshift> empshiftList) {
        if ((empshiftList == null) || (empshiftList.size() == 0)) {
            return;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Map empMap = getEmpMapByIds(empIds);
        List nonStrictShift = getNonStrictShift();
        Map relationShift = getAllRelationShift();
        Employee emp = null;
        String shiftId = null;
        Set locShifts = null;
        Set deptShifts = null;
        String error = null;
        for (Empshift empshift : empshiftList) {
            emp = (Employee) empMap.get(empshift.getEmpshiftEmpNo().getId());
            if (empshift.getEmpshiftShiftNo() == null) {
                continue;
            }
            shiftId = empshift.getEmpshiftShiftNo().getId();
            if (nonStrictShift.contains(shiftId)) {
                continue;
            }

            locShifts = (Set) relationShift.get(emp.getEmpLocationNo().getId());
            deptShifts = (Set) relationShift.get(emp.getEmpDeptNo().getId());
            if ((locShifts != null) && (locShifts.contains(shiftId)))
                continue;
            if ((deptShifts != null) && (deptShifts.contains(shiftId))) {
                continue;
            }

            error = emp.getEmpName() + " " + format.format(empshift.getEmpshiftDate()) + " "
                    + empshift.getEmpshiftShiftNo().getAttsShortName() + " 排班出错：该员工不可以排该班次！";

            errorList.add(error);
        }
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.bo.EmpshiftBOImpl JD-Core Version: 0.5.4
 */