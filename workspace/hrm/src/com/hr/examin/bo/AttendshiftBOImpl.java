package com.hr.examin.bo;

import com.hr.base.BaseAction;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Location;
import com.hr.examin.bo.interfaces.IAttendshiftBO;
import com.hr.examin.dao.interfaces.IAttendshiftDAO;
import com.hr.examin.domain.Attendperiod;
import com.hr.examin.domain.Attendshift;
import com.hr.examin.domain.Attendshiftareadept;
import com.hr.examin.domain.Empshift;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.Pager;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class AttendshiftBOImpl implements IAttendshiftBO {
    private IAttendshiftDAO attendshiftDAO;

    public List getObjects(Class clas, String[] fetch) {
        List result = this.attendshiftDAO.getObjects(clas, fetch);
        return result;
    }

    public <E> E loadObject(Class<E> obj, Object id, String[] fetch, boolean[] cacheable) {
        return this.attendshiftDAO.loadObject(obj, id, fetch, cacheable);
    }

    public boolean updateObject(Object obj) {
        this.attendshiftDAO.updateObject(obj);
        return true;
    }

    public boolean saveObject(Object obj) {
        this.attendshiftDAO.saveObject(obj);
        return true;
    }

    public boolean delObject(Object obj) {
        this.attendshiftDAO.deleteObject(obj);
        return true;
    }

    public boolean saveSchedule(String[] empid, Attendshift attdShift, Date attdDutyDate1,
            Date attdDutyDate2, Date defaultDate) {
        HttpSession session = WebContextFactory.get().getSession();
        Userinfo loginuser = (Userinfo) session.getAttribute("userinfo");
        for (int i = 0; i < empid.length; ++i) {
            Date attdDutyDate = attdDutyDate1;

            while ((attdDutyDate.before(attdDutyDate2)) || (attdDutyDate.equals(attdDutyDate2))) {
                Empshift empshift = isExist(empid[i], attdDutyDate);
                if ((empshift == null) && (!attdShift.getId().equalsIgnoreCase("0"))) {
                    empshift = new Empshift();

                    empshift.setEmpshiftEmpNo(new Employee(empid[i]));
                    empshift.setEmpshiftDate(attdDutyDate);
                    empshift.setEmpshiftShiftNo(attdShift);
                    empshift.setEmpshiftCreateBy(loginuser.getEmployee().getEmpName());
                    empshift.setEmpshiftCreateTime(new Date());
                    this.attendshiftDAO.saveObject(empshift);
                } else if (empshift != null) {
                    if (attdShift.getId().equalsIgnoreCase("0")) {
                        this.attendshiftDAO.deleteObject(empshift);
                    } else {
                        empshift.setEmpshiftShiftNo(attdShift);
                        empshift.setEmpshiftLastChangeBy(loginuser.getEmployee().getEmpName());
                        empshift.setEmpshiftLastChangeTime(new Date());
                        this.attendshiftDAO.updateObject(empshift);
                    }
                }
                attdDutyDate = nextDate(attdDutyDate);
            }
        }
        return false;
    }

    public boolean batchUpdateEmpshift(List<Empshift> empshiftList) {
        List<Empshift> toDeleteList = new ArrayList();
        List saveOrUpdateList = new ArrayList();
        for (Empshift empshift : empshiftList) {
            if (empshift.getEmpshiftShiftNo() == null) {
                toDeleteList.add(empshift);
            }

            saveOrUpdateList.add(empshift);
        }

        if ((toDeleteList != null) && (toDeleteList.size() > 0)) {
            StringBuffer deleteIds = new StringBuffer("");
            for (Empshift empshift : toDeleteList) {
                deleteIds = deleteIds.append("'").append(empshift.getId()).append("',");
            }
            String deleteIdStr = deleteIds.substring(0, deleteIds.length() - 1);
            StringBuffer hql = new StringBuffer("");
            hql = hql.append("delete from Empshift where id in (").append(deleteIdStr).append(")");
            this.attendshiftDAO.exeHql(hql.toString());
        }

        this.attendshiftDAO.saveOrupdate(saveOrUpdateList);

        return true;
    }

    public String getRealDate(String startDateStr, String endDateStr, String dayStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        if (Integer.parseInt(dayStr) < 10) {
            dayStr = "0" + dayStr;
        }

        Date realDate = null;
        try {
            startDate = format.parse(startDateStr);
            endDate = format.parse(endDateStr);
            realDate = format.parse(startDateStr.substring(0, 8) + dayStr);
            if ((realDate.getTime() < startDate.getTime())
                    || (endDate.getTime() < realDate.getTime())) {
                realDate = format.parse(endDateStr.substring(0, 8) + dayStr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return format.format(realDate);
    }

    private String addMonth(String yearMonth, int count) {
        String[] ymArr = yearMonth.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, Integer.parseInt(ymArr[0]));
        calendar.set(2, Integer.parseInt(ymArr[1]) - 1);
        calendar.add(2, count);
        return new SimpleDateFormat("yyyy-MM").format(calendar.getTime());
    }

    private Empshift isExist(String empid, Date attdDutyDate) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empshift.class);
        detachedCriteria.createAlias("empshiftShiftNo", "shift", 1);
        detachedCriteria.add(Restrictions.eq("empshiftEmpNo.id", empid));
        detachedCriteria.add(Restrictions.eq(Empshift.PROP_EMPSHIFT_DATE, attdDutyDate));
        List result = this.attendshiftDAO.findByCriteria(detachedCriteria);

        if ((result != null) && (result.size() > 0)) {
            return (Empshift) result.get(0);
        }
        return null;
    }

    public Boolean isUsed(Attendshift attdShift) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empshift.class);
        detachedCriteria.add(Restrictions.eq(Empshift.PROP_EMPSHIFT_SHIFT_NO, attdShift));
        List result = this.attendshiftDAO.findByCriteria(detachedCriteria);
        if ((result != null) && (result.size() > 0)) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public Date nextDate(Date attdDutyDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(attdDutyDate);
        calendar.set(5, calendar.get(5) + 1);

        return calendar.getTime();
    }

    public List<Employee> searchEmployee(Pager page, DetachedCriteria dc) {
        List result = null;
        if (page != null)
            result = this.attendshiftDAO.findByCriteria(dc, page.getPageSize(), page
                    .getCurrentPage());
        else {
            result = this.attendshiftDAO.findByCriteria(dc);
        }

        return result;
    }

    public List<Empshift> searchAttendSchedule(Pager page, DetachedCriteria dc) {
        List result = this.attendshiftDAO.findByCriteria(dc);
        return result;
    }

    public List<Attendshift> searchAttendShift(String attsShortName, Integer status) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendshift.class);
        if ((attsShortName != null) && (attsShortName.trim().length() > 0))
            detachedCriteria.add(Restrictions.eq(Attendshift.PROP_ATTS_SHORT_NAME, attsShortName
                    .trim()));
        if (status != null)
            detachedCriteria.add(Restrictions.eq(Attendshift.PROP_ATTS_STATUS, status));
        detachedCriteria.addOrder(Order.asc(Attendshift.PROP_ATTS_SORT_ID));
        List result = this.attendshiftDAO.findByCriteria(detachedCriteria);

        if ((result == null) || (result.size() == 0))
            return null;
        return result;
    }

    public Attendshift getNonStrictDefaultAttendshift() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendshift.class);
        detachedCriteria.add(Restrictions.eq(Attendshift.PROP_ATTS_IS_DEFAULT, new Integer(1)));
        detachedCriteria.add(Restrictions.eq(Attendshift.PROP_ATTS_STRICKED, new Integer(0)));
        detachedCriteria.add(Restrictions.eq(Attendshift.PROP_ATTS_STATUS, new Integer(1)));

        List shiftList = this.attendshiftDAO.findByCriteria(detachedCriteria);
        if ((shiftList != null) && (shiftList.size() > 0))
            return (Attendshift) shiftList.get(0);

        return null;
    }

    public List<Attendshiftareadept> searchDefaultAttendShiftRela() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendshiftareadept.class);
        detachedCriteria.createAlias("asadShiftId", "shift", 1);
        detachedCriteria.add(Restrictions.eq("shift.attsIsDefault", new Integer(1)));
        detachedCriteria.add(Restrictions.eq("shift.attsStatus", new Integer(1)));
        detachedCriteria.addOrder(Order.desc("asadAreaId"));
        List result = this.attendshiftDAO.findByCriteria(detachedCriteria);
        return result;
    }

    public Attendshift getDefaultAttendshiftByEmp(List<Attendshiftareadept> relaList,
            Attendshift nonStrictDefaultShift, Employee emp) {
        if ((relaList == null) || (relaList.size() == 0))
            return nonStrictDefaultShift;

        for (Attendshiftareadept asad : relaList) {
            if ((asad.getAsadAreaId() != null)
                    && (asad.getAsadAreaId().getId().equals(emp.getEmpLocationNo().getId()))) {
                return asad.getAsadShiftId();
            }
            if ((asad.getAsadDeptId() != null)
                    && (asad.getAsadDeptId().getId().equals(emp.getEmpDeptNo().getId()))) {
                return asad.getAsadShiftId();
            }

        }

        return nonStrictDefaultShift;
    }

    public Attendshift getDefaultAttendshiftByEmp(Employee emp) {
        List<Attendshiftareadept> asadList = searchDefaultAttendShiftRela();
        Attendshift nonStrictDefaultShift = getNonStrictDefaultAttendshift();
        if ((asadList == null) || (asadList.size() == 0))
            return nonStrictDefaultShift;

        for (Attendshiftareadept asad : asadList) {
            if ((asad.getAsadAreaId() != null) && (emp.getEmpLocationNo() != null)
                    && (asad.getAsadAreaId().getId().equals(emp.getEmpLocationNo().getId()))) {
                return asad.getAsadShiftId();
            }
            if ((asad.getAsadDeptId() != null) && (emp.getEmpDeptNo() != null)
                    && (asad.getAsadDeptId().getId().equals(emp.getEmpDeptNo().getId()))) {
                return asad.getAsadShiftId();
            }

        }

        return nonStrictDefaultShift;
    }

    public List<Attendshift> searchAttendShiftById(String attsId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendshift.class);
        detachedCriteria.add(Restrictions.eq(Attendshift.PROP_ID, attsId));
        detachedCriteria.addOrder(Order.asc(Attendshift.PROP_ATTS_SORT_ID));
        List result = this.attendshiftDAO.findByCriteria(detachedCriteria);
        if ((result == null) || (result.size() == 0)) {
            return null;
        }

        return result;
    }

    public List<Attendshift> searchAttendmonthly(Pager page, String emp, Employee employee) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendshift.class);
        detachedCriteria.createAlias("attmEmpId", "emp", 1);

        if ((emp != null) && (emp.trim().length() > 0)) {
            detachedCriteria.add(Restrictions.or(Restrictions.like("emp.empDistinctNo", emp.trim(),
                                                                   MatchMode.ANYWHERE),
                                                 Restrictions.like("emp.empName", emp.trim(),
                                                                   MatchMode.ANYWHERE)));
        }

        if ((employee != null) && (employee.getEmpDeptNo() != null)
                && (employee.getEmpDeptNo().getId().trim().length() > 0)) {
            detachedCriteria.add(Restrictions.eq("emp.empDeptNo", employee.getEmpDeptNo()));
        }

        if ((employee != null) && (employee.getEmpLocationNo() != null)
                && (employee.getEmpLocationNo().getId().trim().length() > 0)) {
            detachedCriteria.add(Restrictions.eq("emp.empLocationNo", employee.getEmpLocationNo()));
        }

        BaseAction.addOrders(detachedCriteria, page, new String[] { "emp."
                + Employee.PROP_EMP_DISTINCT_NO });

        page.splitPage(detachedCriteria);
        List result = this.attendshiftDAO.findByCriteria(detachedCriteria, page.getPageSize(), page
                .getCurrentPage());

        return result;
    }

    public void saveSort(String[] idArray) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendshift.class);
        detachedCriteria.add(Restrictions.in(Attendshift.PROP_ID, idArray));

        List result = this.attendshiftDAO.findByCriteria(detachedCriteria);
        Attendshift tmp = null;
        for (int i = 0; i < idArray.length; ++i)
            for (int j = 0; j < result.size(); ++j) {
                tmp = (Attendshift) result.get(j);
                if (tmp.getId().equals(idArray[i])) {
                    tmp.setAttsSortId(Integer.valueOf(i));
                    this.attendshiftDAO.updateObject(tmp);
                    break;
                }
            }
    }

    public void saveDefault(String id, int isDefault) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendshift.class);
        List result = this.attendshiftDAO.findByCriteria(detachedCriteria);
        for (int j = 0; j < result.size(); ++j) {
            Attendshift tmp = (Attendshift) result.get(j);
            if (tmp.getId().equals(id)) {
                tmp.setAttsIsDefault(Integer.valueOf(isDefault));
                this.attendshiftDAO.updateObject(tmp);
                return;
            }
        }
    }

    public void saveStatus(String id, int status) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendshift.class);
        List result = this.attendshiftDAO.findByCriteria(detachedCriteria);
        for (int j = 0; j < result.size(); ++j) {
            Attendshift tmp = (Attendshift) result.get(j);
            if (tmp.getId().equals(id)) {
                tmp.setAttsStatus(Integer.valueOf(status));
                this.attendshiftDAO.updateObject(tmp);
                return;
            }
        }
    }

    public int getGlobalDefaultCount() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendshift.class);
        detachedCriteria.add(Restrictions.eq(Attendshift.PROP_ATTS_STRICKED, new Integer(0)));
        detachedCriteria.add(Restrictions.eq(Attendshift.PROP_ATTS_IS_DEFAULT, new Integer(1)));
        detachedCriteria.add(Restrictions.eq(Attendshift.PROP_ATTS_STATUS, new Integer(1)));
        List result = this.attendshiftDAO.findByCriteria(detachedCriteria);
        if ((result != null) && (result.size() > 0)) {
            return result.size();
        }
        return 0;
    }

    public IAttendshiftDAO getAttendshiftDAO() {
        return this.attendshiftDAO;
    }

    public void setAttendshiftDAO(IAttendshiftDAO attendshiftDAO) {
        this.attendshiftDAO = attendshiftDAO;
    }

    public int getMaxSortId() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendshift.class);
        detachedCriteria
                .setProjection(Projections.projectionList().add(Projections.max("attsSortId")));
        Integer maxId = new Integer(0);
        maxId = (Integer) this.attendshiftDAO.findByCriteria(detachedCriteria).get(0);
        if (maxId == null) {
            return 0;
        }
        return maxId.intValue();
    }

    public boolean isDefault(String attsId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendshift.class);
        detachedCriteria.add(Restrictions.eq(Attendshift.PROP_ID, attsId));
        List result = this.attendshiftDAO.findByCriteria(detachedCriteria);
        if ((result != null) && (result.size() > 0)) {
            Attendshift temp = (Attendshift) result.get(0);
            if ((temp != null) && (temp.getAttsIsDefault() != null)
                    && (temp.getAttsIsDefault().intValue() == 1)) {
                return true;
            }
        }

        return false;
    }

    public BigDecimal getShiftHours(String attsId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendshift.class);
        detachedCriteria.add(Restrictions.eq(Attendshift.PROP_ID, attsId));
        List result = this.attendshiftDAO.findByCriteria(detachedCriteria);

        if ((result != null) && (result.size() > 0)) {
            Attendshift temp = (Attendshift) result.get(0);
            return temp.getAttsWorkingHour();
        }

        return null;
    }

    public Attendperiod getCurrentPeriod(String year, String month) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendperiod.class);
        detachedCriteria.add(Restrictions.eq("attpYear", year));
        detachedCriteria.add(Restrictions.eq("attpMonth", month));
        List result = this.attendshiftDAO.findByCriteria(detachedCriteria);
        Attendperiod period = null;
        if ((result != null) && (result.size() > 0)) {
            period = (Attendperiod) result.get(0);
        }
        return period;
    }

    public Long getCommenTimes(Date on, Date off, Date start, Date end) {
        long seconds = 0L;
        if ((on == null) || (off == null) || (start == null) || (end == null)) {
            return Long.valueOf(seconds);
        }
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

    public List<Attendshiftareadept> getAsadListByShift(String shiftId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendshiftareadept.class);
        detachedCriteria.setFetchMode("asadShiftId", FetchMode.JOIN);
        detachedCriteria.setFetchMode("asadAreaId", FetchMode.JOIN);
        detachedCriteria.setFetchMode("asadDeptId", FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq("asadShiftId.id", shiftId));
        return this.attendshiftDAO.findByCriteria(detachedCriteria);
    }

    public void deleteAsadListByShift(String shiftId) {
        String hql = "delete from Attendshiftareadept where asadShiftId = '" + shiftId + "'";
        this.attendshiftDAO.exeHql(hql);
    }

    public String saveOrUpdateShift(Attendshift attdShift, String[] locIds, String[] deptIds) {
        List asadList = new ArrayList();
        Attendshiftareadept asad = null;

        for (int i = 0; (locIds != null) && (i < locIds.length); ++i) {
            asad = new Attendshiftareadept(attdShift, new Location(locIds[i]), null);
            asadList.add(asad);
        }
        for (int i = 0; (deptIds != null) && (i < deptIds.length); ++i) {
            asad = new Attendshiftareadept(attdShift, null, new Department(deptIds[i]));
            asadList.add(asad);
        }

        if ((attdShift.getId() == null) || (attdShift.getId().trim().length() == 0)) {
            attdShift.setAttsSortId(Integer.valueOf(getMaxSortId() + 1));
            saveObject(attdShift);

            this.attendshiftDAO.saveOrupdate(asadList);
        } else {
            updateObject(attdShift);

            deleteAsadListByShift(attdShift.getId());

            this.attendshiftDAO.saveOrupdate(asadList);
        }

        return "succ";
    }

    public boolean deleteShift(Attendshift attdShift) {
        delObject(attdShift);
        deleteAsadListByShift(attdShift.getId());

        return true;
    }

    public List<Attendshift> getShiftIdsByRestrict(String[] empIds) {
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        List empList = empBo.searchEmpArray(empIds);
        Employee emp = null;
        Set locIdSet = new HashSet();
        Set deptIdSet = new HashSet();
        for (int i = 0; (empList != null) && (i < empList.size()); ++i) {
            emp = (Employee) empList.get(i);
            locIdSet.add(emp.getEmpLocationNo().getId());
            deptIdSet.add(emp.getEmpDeptNo().getId());
        }

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attendshiftareadept.class);
        detachedCriteria.add(Restrictions.or(Restrictions.in("asadAreaId.id", locIdSet),
                                             Restrictions.in("asadDeptId.id", deptIdSet)));

        List relaList = this.attendshiftDAO.findByCriteria(detachedCriteria);
        Set shiftIdSet = new HashSet();
        for (int i = 0; i < relaList.size(); ++i) {
            shiftIdSet.add(((Attendshiftareadept) relaList.get(i)).getAsadShiftId().getId());
        }

        detachedCriteria = DetachedCriteria.forClass(Attendshift.class);
        detachedCriteria.add(Restrictions.eq(Attendshift.PROP_ATTS_STATUS, new Integer(1)));
        if (shiftIdSet.size() > 0) {
            detachedCriteria.add(Restrictions.or(Restrictions.ne(Attendshift.PROP_ATTS_STRICKED,
                                                                 new Integer(1)), Restrictions
                    .in(Attendshift.PROP_ID, shiftIdSet)));
        } else {
            detachedCriteria.add(Restrictions.ne(Attendshift.PROP_ATTS_STRICKED, new Integer(1)));
        }
        detachedCriteria.addOrder(Order.asc(Attendshift.PROP_ATTS_SORT_ID));

        List result = this.attendshiftDAO.findByCriteria(detachedCriteria);
        return result;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.bo.AttendshiftBOImpl JD-Core Version: 0.5.4
 */