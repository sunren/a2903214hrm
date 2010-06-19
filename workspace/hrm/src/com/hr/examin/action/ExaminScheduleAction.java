package com.hr.examin.action;

import com.hr.base.BaseDownloadAction;
import com.hr.base.DWRUtil;
import com.hr.base.Status;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.bo.IEmpTypeBO;
import com.hr.configuration.bo.ILocationBO;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Location;
import com.hr.examin.bo.ExaminDateUtil;
import com.hr.examin.bo.interfaces.IAttendshiftBO;
import com.hr.examin.bo.interfaces.IEmpshiftBo;
import com.hr.examin.domain.Attendperiod;
import com.hr.examin.domain.Attendshift;
import com.hr.examin.domain.Attendshiftareadept;
import com.hr.examin.domain.Empshift;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.BaseCrit;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.DateUtil;
import com.hr.util.MyTools;
import com.hr.util.Pager;
import com.hr.util.StringUtil;
import com.hr.util.SysConfigManager;
import com.hr.util.output.FieldOperate;
import java.awt.Color;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ExaminScheduleAction extends BaseDownloadAction implements Status {
    private static final long serialVersionUID = 1L;
    private List scheduleList;
    private List<Employee> empList;
    private Pager page;
    private String emp;
    private Employee employee;
    private String year;
    private String month;
    private String[] empId;
    private List dayList;
    private List<Department> departs;
    private List<Location> locations;
    private List<Emptype> empTypes;
    private String operation;
    private String operationId;
    private List shiftList;
    private String empIdString;
    private String empNameString;
    private Attendshift attdShift;
    private Date attdDutyDate1;
    private Date attdDutyDate2;
    private Date defaultDate;
    private IAttendshiftBO attdShiftBo;
    private List daysList;
    private List weeksList;
    private Date dayFrom;
    private Date dayTo;
    private String dayFrom1;
    private String dayTo1;
    private String monthFlag;
    private String dayFlag;
    private String flag;
    private String searchOrExport;
    private String outmatchModelId;
    public static final String outputIoName = "OExaminShift";
    private int resultStatus;

    public ExaminScheduleAction() {
        this.scheduleList = null;
        this.empList = null;
        this.page = new Pager();
        this.emp = null;
        this.employee = null;

        this.dayList = null;

        this.operation = null;
        this.operationId = null;

        this.shiftList = null;

        this.defaultDate = DateUtil.parseDateByFormat("20000101", "yyyyMMdd");

        this.attdShiftBo = ((IAttendshiftBO) getBean("attendshiftBO"));

        this.dayFrom = null;
        this.dayTo = null;

        this.dayFrom1 = null;
        this.dayTo1 = null;

        this.monthFlag = null;
        this.dayFlag = null;

        this.flag = "";

        this.resultStatus = 1;
    }

    public String execute() throws Exception {
        execParameter();

        getDrillDownList();

        this.empList = searchEmp();

        this.scheduleList = searchAttendSchedule();

        if ("export".equals(this.searchOrExport)) {
            generateEmpshiftList();
            List titleInfoList = generateTitleList();

            return downloadToExcel(this.empList, "OExaminShift", null, "", titleInfoList);
        }

        this.shiftList = this.attdShiftBo.searchAttendShift(null, new Integer(1));

        Attendperiod period = this.attdShiftBo.getCurrentPeriod(this.year, this.month);
        if ((period != null)
                && (period.getAttpStatus() != null)
                && (((period.getAttpStatus().intValue() == 1) || (period.getAttpStatus().intValue() == 2))))
            this.flag = "false";
        else {
            this.flag = "true";
        }
        return "success";
    }

    private List<Employee> searchEmp() {
        DetachedCriteria dc = DetachedCriteria.forClass(Employee.class, "emp");

        if ("SUB".equals(this.authorityCondition)) {
            addSubDC(dc, "emp", 4);
        }

        addCriteria(dc);

        addOrders(dc, null, new String[] { "emp." + Employee.PROP_EMP_NAME + "-up" });
        this.page.splitPage(dc);

        if ("export".equals(this.searchOrExport))
            return this.attdShiftBo.searchEmployee(null, dc);
        return this.attdShiftBo.searchEmployee(this.page, dc);
    }

    private List<Empshift> searchAttendSchedule() {
        DetachedCriteria dc = DetachedCriteria.forClass(Empshift.class);
        dc.setFetchMode("empshiftEmpNo", FetchMode.JOIN);
        dc.setFetchMode("empshiftShiftNo", FetchMode.JOIN);
        dc.add(Restrictions.lt(Empshift.PROP_EMPSHIFT_DATE, this.dayTo));
        dc.add(Restrictions.ge(Empshift.PROP_EMPSHIFT_DATE, this.dayFrom));

        if ((this.empList != null) && (this.empList.size() > 0)) {
            dc.add(Restrictions.in(Empshift.PROP_EMPSHIFT_EMP_NO, this.empList));
        }

        dc.addOrder(Order.asc(Empshift.PROP_EMPSHIFT_EMP_NO));

        return this.attdShiftBo.searchAttendSchedule(this.page, dc);
    }

    private void getDrillDownList() {
        IEmpTypeBO emptypebo = (IEmpTypeBO) SpringBeanFactory.getBean("emptypeBO");
        this.empTypes = emptypebo.FindEnabledEmpType();
        ILocationBO localbo = (ILocationBO) SpringBeanFactory.getBean("locationBO");
        this.locations = localbo.FindEnabledLocation();
        IDepartmentBO deptbo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        this.departs = deptbo.FindEnabledDepartment();
    }

    private void execParameter() {
        SysConfigManager dbConfigManager = DatabaseSysConfigManager.getInstance();
        Map dbMap = dbConfigManager.getProperties();
        String configValue = (String) dbMap.get("sys.examin.month.sum");
        String[] configInit = { "1", "1" };
        String[] config = ((configValue != null) && (!configValue.equals("")) && (configValue
                .indexOf("-") > -1)) ? configValue.split("-") : configInit;
        this.monthFlag = config[0];
        this.dayFlag = config[1];

        if ((this.year == null) || (this.month == null) || (this.year.equals(""))
                || (this.month.equals(""))) {
            this.year = DateUtil.formatDateToS(new Date(), "yyyy");
            this.month = DateUtil.formatDateToS(new Date(), "MM");
        }

        Map map = getEndDay(this.year, this.month);
        this.dayList = ((List) map.get("tmpList"));
        this.dayFrom = ((Date) map.get("dayFrom"));
        this.dayTo = ((Date) map.get("dayTo"));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.dayTo);
        calendar.add(5, -1);
        this.dayFrom1 = DateUtil.formatDateToS(this.dayFrom, "yyyy-MM-dd");
        this.dayTo1 = DateUtil.formatDateToS(calendar.getTime(), "yyyy-MM-dd");

        if (this.employee != null)
            return;
        this.employee = new Employee();
    }

    private void addCriteria(DetachedCriteria dc) {
        dc.createAlias("emp." + Employee.PROP_EMP_DEPT_NO, "empOrgDept", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_LOCATION_NO, "empOrgLoc", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_PB_NO, "empPbNo", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_TYPE, "empType", 1);

        BaseCrit.addEmpDC(dc, "emp", this.emp);
        BaseCrit.addDC(dc, "emp." + Employee.PROP_EMP_SHIFT_TYPE, "eq", new Integer[] { Integer
                .valueOf(3) });
        BaseCrit.addDeptDC(dc, "emp." + Employee.PROP_EMP_DEPT_NO,
                           "emp." + Employee.PROP_EMP_PB_NO, null, this.employee.getEmpDeptNo());
        BaseCrit.addDC(dc, "emp." + Employee.PROP_EMP_LOCATION_NO, Location.PROP_ID,
                       new Object[] { this.employee.getEmpLocationNo() });
        BaseCrit.addDC(dc, "emp." + Employee.PROP_EMP_TYPE, Emptype.PROP_ID,
                       new Object[] { this.employee.getEmpType() });

        dc.add(Restrictions.le("emp." + Employee.PROP_EMP_JOIN_DATE, this.dayTo));
        dc.add(Restrictions.or(Restrictions.eq("emp." + Employee.PROP_EMP_STATUS, Integer
                .valueOf(1)), Restrictions.and(Restrictions.eq("emp." + Employee.PROP_EMP_STATUS,
                                                               Integer.valueOf(0)), Restrictions
                .ge("emp." + Employee.PROP_EMP_TERMINATE_DATE, this.dayFrom))));
    }

    private void generateEmpshiftList() {
        Map empshfitMap = convertListToMap(this.scheduleList);
        Employee currEmp = null;
        Empshift empshift = null;
        Date tempDate;
        for (int i = 0; (this.empList != null) && (i < this.empList.size()); ++i) {
            currEmp = (Employee) this.empList.get(i);
            for (tempDate = this.dayFrom; tempDate.compareTo(this.dayTo) <= 0;) {
                empshift = (Empshift) empshfitMap.get(currEmp.getId()
                        + DateUtil.formatDateToS(tempDate, "yyyy-MM-dd"));
                currEmp.getSsNameList().add(
                                            (empshift == null) ? "" : empshift.getEmpshiftShiftNo()
                                                    .getAttsShortName());
                tempDate = DateUtil.dateAdd(tempDate, 1);
            }
        }
    }

    private List<FieldOperate> generateTitleList() {
        List titleInfoList = new ArrayList();
        String day = null;
        int count = 0;
        Calendar cal = Calendar.getInstance();
        FieldOperate field = null;
        for (Date tempDate = this.dayFrom; tempDate.compareTo(this.dayTo) < 0;) {
            cal.setTime(tempDate);
            day = DateUtil.formatDateToS(tempDate, "yyyy-MM-dd").substring(8);
            field = new FieldOperate("ssNameList." + count, day, "String", null, 8);
            if ((cal.get(7) == 7) || (cal.get(7) == 1)) {
                field.setColBgColor(new Color(255, 255, 0));
            }
            titleInfoList.add(field);
            tempDate = DateUtil.dateAdd(tempDate, 1);
            ++count;
        }

        return titleInfoList;
    }

    private Map<String, Empshift> convertListToMap(List<Empshift> list) {
        Map map = new HashMap();
        if ((list == null) || (list.size() == 0)) {
            return map;
        }
        for (Empshift shift : list) {
            map.put(shift.getEmpshiftEmpNo().getId()
                    + DateUtil.formatDateToS(shift.getEmpshiftDate(), "yyyy-MM-dd"), shift);
        }

        return map;
    }

    public HashMap getEndDay(String year, String month) {
        HashMap map = new HashMap();
        Date dayFrom = null;
        Date dayTo = null;

        SysConfigManager dbConfigManager = DatabaseSysConfigManager.getInstance();
        Map dbMap = dbConfigManager.getProperties();
        String configValue = (String) dbMap.get("sys.examin.month.sum");

        String startMonthStr = ((configValue != null) && (configValue.indexOf("-") > -1)) ? configValue
                .split("-")[0]
                : "";
        String startDayStr = ((configValue != null) && (configValue.indexOf("-") > -1)) ? configValue
                .split("-")[1]
                : "";
        Integer startMonth = Integer
                .valueOf(((startMonthStr != null) && (!startMonthStr.equals(""))) ? Integer
                        .parseInt(startMonthStr) : 0);
        Integer startDay = Integer
                .valueOf(((startDayStr != null) && (!startDayStr.equals(""))) ? Integer
                        .parseInt(startDayStr) : 1);

        List tmpList = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, Integer.parseInt(year));
        calendar.set(2, Integer.parseInt(month) - 1);
        calendar.set(5, startDay.intValue());
        calendar.add(2, startMonth.intValue() - 1);
        int day = calendar.getActualMaximum(5);
        dayFrom = calendar.getTime();

        for (int i = 1; i <= day; ++i) {
            tmpList.add(Integer.valueOf(calendar.get(7)));
            tmpList.add(Integer.valueOf(calendar.get(5)));
            calendar.add(5, 1);
        }

        dayTo = calendar.getTime();

        dayFrom = DateUtil.parseDateByFormat(DateUtil.formatDateToS(dayFrom, "yyyy-MM-dd"),
                                             "yyyy-MM-dd");
        dayTo = DateUtil.parseDateByFormat(DateUtil.formatDateToS(dayTo, "yyyy-MM-dd"),
                                           "yyyy-MM-dd");
        map.put("tmpList", tmpList);
        map.put("dayFrom", dayFrom);
        map.put("dayTo", dayTo);

        return map;
    }

    public Attendshift updateOneSchedule(String attdEmpId, String attdDutyDate,
            Attendshift attdShift) {
        if (!"0".equals(attdShift.getId()))
            attdShift = (Attendshift) this.attdShiftBo.loadObject(Attendshift.class, attdShift
                    .getId(), null, new boolean[0]);

        String empIdStr = attdEmpId + ",";
        String shift_day_str = attdShift.getId() + ":" + attdDutyDate.substring(8) + "##";

        String msg = batchUpdateSchedule(attdDutyDate, attdDutyDate, empIdStr, shift_day_str);

        if (!"succ".equals(msg))
            attdShift.setFlag(msg);

        return attdShift;
    }

    public List<Attendshift> getCommonShiftList(String idStr) {
        String[] empIds = idStr.split(",");
        List shiftList = this.attdShiftBo.getShiftIdsByRestrict(empIds);
        return shiftList;
    }

    public String batchUpdateSchedule(String startDate, String endDate, String empIdStr,
            String shift_day_str) {
        String message = "";

        String auth = DWRUtil.checkAuth("examinScheduleSearch", "execute");
        if ("error".equalsIgnoreCase(auth)) {
            return "noauth";
        }
        if ((shift_day_str == null) || (shift_day_str.trim().length() == 0)) {
            return message;
        }

        IEmpshiftBo empshiftBo = (IEmpshiftBo) getBean("empshiftBO");

        String[] empIdArr = ((empIdStr != null) && (empIdStr.indexOf(",") > -1)) ? empIdStr
                .split(",") : null;

        Map dateMap = convertDayStr(startDate, endDate, shift_day_str);
        Date[] allDateArray = (Date[]) (Date[]) dateMap.get("alldate");
        String[] shiftdateArray = (String[]) (String[]) dateMap.get("shiftdate");

        HashMap existEmpshiftMap = empshiftBo.getEmpshiftList(empIdArr, allDateArray);

        Map allShiftMap = getAllShiftMap();

        List empshiftList = generateEmpshiftList(empIdArr, shiftdateArray, existEmpshiftMap,
                                                 allShiftMap);

        List<String> errorList = empshiftBo.validateShift(empshiftList, existEmpshiftMap);

        empshiftBo.checkShiftSchedule(errorList, empIdArr, empshiftList);

        if (errorList.size() > 0) {
            for (String error : errorList) {
                message = message + " " + error;
            }
            return message;
        }

        boolean updateFlag = this.attdShiftBo.batchUpdateEmpshift(empshiftList);

        if ((message.trim().length() == 0) && (updateFlag)) {
            message = "succ";
        }
        return message;
    }

    private Map<String, Object> convertDayStr(String startDate, String endDate, String shift_day_str) {
        String[] shiftDayArr = ((shift_day_str != null) && (shift_day_str.indexOf("##") > -1)) ? shift_day_str
                .split("##")
                : null;
        Map result = new HashMap();

        Set<String> shiftDateSet = new HashSet();
        String tempShiftId = null;
        String tempDate = null;
        String[] shiftDayTempArr = null;
        String[] dayArr = null;

        for (int i = 0; i < shiftDayArr.length; ++i) {
            shiftDayTempArr = shiftDayArr[i].split(":");
            tempShiftId = shiftDayTempArr[0];
            dayArr = shiftDayTempArr[1].split(",");
            for (int j = 0; j < dayArr.length; ++j) {
                tempDate = this.attdShiftBo.getRealDate(startDate, endDate, dayArr[j]);
                shiftDateSet.add(tempShiftId + "," + tempDate);
            }
        }
        Date[] allDateArray = new Date[shiftDateSet.size()];
        String[] shiftDateArray = new String[shiftDateSet.size()];
        int index = 0;
        for (String shiftdate : shiftDateSet) {
            allDateArray[index] = DateUtil.parseDateByFormat(shiftdate.split(",")[1], "yyyy-MM-dd");
            shiftDateArray[index] = shiftdate;
            ++index;
        }

        result.put("alldate", allDateArray);
        result.put("shiftdate", shiftDateArray);
        return result;
    }

    private Map<String, Attendshift> getAllShiftMap() {
        List<Attendshift> allShiftList = this.attdShiftBo.searchAttendShift(null, null);
        Map allShiftMap = new HashMap();
        for (Attendshift shift : allShiftList) {
            allShiftMap.put(shift.getId(), shift);
        }

        return allShiftMap;
    }

    private List<Empshift> generateEmpshiftList(String[] empIds, String[] shiftdateArray,
            HashMap<String, Empshift> existEmpshiftMap, Map<String, Attendshift> allShiftMap) {
        List empshiftList = new ArrayList();
        Employee currentEmp = getCurrentEmp();

        String empId = null;
        Date date = null;
        Empshift empshift = null;
        Empshift empshiftTemp = null;
        String[] shiftdayArr = null;
        Attendshift shift = null;
        for (int i = 0; i < empIds.length; ++i) {
            empId = empIds[i];
            for (int j = 0; j < shiftdateArray.length; ++j) {
                shiftdayArr = shiftdateArray[j].split(",");
                shift = (Attendshift) allShiftMap.get(shiftdayArr[0]);
                empshift = (Empshift) existEmpshiftMap.get(empId + "" + shiftdayArr[1]);
                if (empshift != null) {
                    try {
                        empshiftTemp = (Empshift) BeanUtils.cloneBean(empshift);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (("0".equals(shiftdayArr[0])) && (empshift != null)) {
                    empshiftTemp.setEmpshiftShiftNo(null);
                    empshiftList.add(empshiftTemp);
                } else if ((!"0".equals(shiftdayArr[0])) && (empshift == null)) {
                    try {
                        date = DateUtil.parseDateByFormat(shiftdayArr[1], "yyyy-MM-dd");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    empshift = new Empshift();
                    empshift.setEmpshiftEmpNo(new Employee(empId));
                    empshift.setEmpshiftDate(date);
                    empshift.setEmpshiftShiftNo(shift);
                    empshift.setEmpshiftLastChangeBy(currentEmp.getEmpName());
                    empshift.setEmpshiftLastChangeTime(new Date());
                    empshiftList.add(empshift);
                } else if ((!"0".equals(shiftdayArr[0])) && (empshift != null)) {
                    empshiftTemp.setEmpshiftShiftNo(shift);
                    empshiftTemp.setEmpshiftLastChangeBy(currentEmp.getEmpName());
                    empshiftTemp.setEmpshiftLastChangeTime(new Date());
                    empshiftList.add(empshiftTemp);
                }
            }

        }

        Collections.sort(empshiftList, new Comparator() {
            public int compare(Object obj1, Object obj2) {
                Empshift shift1 = (Empshift) obj1;
                Empshift shift2 = (Empshift) obj2;
                return shift1.getEmpshiftDate().compareTo(shift2.getEmpshiftDate());
            }
        });
        return empshiftList;
    }

    private String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public String shiftSearch() {
        this.shiftList = this.attdShiftBo.searchAttendShift(null, Integer
                .valueOf(this.resultStatus));
        ILocationBO locBo = (ILocationBO) getBean("locationBO");
        this.locations = locBo.FindEnabledLocation();
        IDepartmentBO deptBo = (IDepartmentBO) getBean("departmentBO");
        this.departs = deptBo.FindEnabledDepartment();

        return "success";
    }

    public String insertShift(Attendshift attdShift) {
        if ((attdShift == null) || (attdShift.getAttsColor() == null)
                || (attdShift.getAttsName() == null) || (attdShift.getAttsShortName() == null)
                || (attdShift.getAttsName().trim().length() == 0)
                || (attdShift.getAttsSession() == null)) {
            return "fail";
        }

        List<Attendshift> existShifts = this.attdShiftBo.searchAttendShift(attdShift.getAttsShortName(), null);
        if (attdShift.getId() == null) {
            if ((existShifts != null) && (existShifts.size() > 0))
                return "[班次代码] 不允许重复！";
        } else if ((existShifts != null) && (existShifts.size() > 0)) {
            for (Attendshift shift : existShifts) {
                if (!shift.getId().equals(attdShift.getId())) {
                    return "[班次代码] 不允许重复！";
                }

            }

        }

        if ((attdShift.getId() != null) && (!"".equals(attdShift.getId()))) {
            List shiftIndbList = this.attdShiftBo.searchAttendShiftById(attdShift.getId());
            Attendshift shiftIndb = ((shiftIndbList != null) && (shiftIndbList.size() > 0)) ? (Attendshift) shiftIndbList
                    .get(0)
                    : null;
            if ((shiftIndb != null)
                    && (!shiftIndb.getAttsSession().equals(attdShift.getAttsSession()))
                    && (this.attdShiftBo.isUsed(shiftIndb).booleanValue()))
                return "班次正在使用，不允许修改班次时间段！";

        }

        String info = checkAttsSession(attdShift.getAttsSession());
        if (!"SUCC".equals(info))
            return info;

        computeWorkingHours(attdShift.getAttsSession(), attdShift);

        String locIds = attdShift.getLocIdString();
        String deptIds = attdShift.getDeptIdString();
        String[] locIdArr = ((locIds != null) && (locIds.length() > 0)) ? locIds.split(",") : null;
        String[] deptIdArr = ((deptIds != null) && (deptIds.length() > 0)) ? deptIds.split(",")
                : null;
        String result = this.attdShiftBo.saveOrUpdateShift(attdShift, locIdArr, deptIdArr);
        if (!"succ".equals(result))
            return result;

        return "succ,"
                + StringUtil.formatBDToS(attdShift.getAttsWorkingHour(), new String[] { "#0.0 " })
                + "," + attdShift.getAttsNightShift();
    }

    public String getRelaString(String shiftId) {
        List<Attendshiftareadept> asadList = this.attdShiftBo.getAsadListByShift(shiftId);
        String value = "";
        for (Attendshiftareadept asad : asadList) {
            if (asad.getAsadAreaId() != null) {
                value = value + asad.getAsadAreaId().getId() + ","
                        + asad.getAsadAreaId().getLocationName() + "##";
            }
            if (asad.getAsadDeptId() != null) {
                value = value + asad.getAsadDeptId().getId() + ","
                        + asad.getAsadDeptId().getDepartmentName() + "##";
            }
        }

        return value;
    }

    public String delShift(String id) {
        if ((id != null) && (id.trim().length() > 0)) {
            Attendshift attdShift = (Attendshift) this.attdShiftBo.loadObject(Attendshift.class,
                                                                              id, null,
                                                                              new boolean[0]);
            if (this.attdShiftBo.isUsed(attdShift).booleanValue())
                return "isused";
            if (this.attdShiftBo.deleteShift(attdShift))
                return "succ";
        }
        return "fail";
    }

    public String sortShift() {
        if ((this.operationId != null) && (this.operationId.trim().length() > 0)) {
            String[] idArray = this.operationId.split(",");
            this.attdShiftBo.saveSort(idArray);
        }
        return "success";
    }

    public String setDefault(String operationId, int isDefault) {
        try {
            if (isDefault == 0) {
                BigDecimal shiftHours = this.attdShiftBo.getShiftHours(operationId);
                if (shiftHours == null)
                    return "fail";
                double hoursPerDay = ExaminDateUtil.getDefaultHoursPerDay();
                if (shiftHours.doubleValue() != hoursPerDay)
                    return "notDefaultHours," + hoursPerDay;
                this.attdShiftBo.saveDefault(operationId, 1);
            }

            if (isDefault == 1)
                this.attdShiftBo.saveDefault(operationId, 0);

            return "succ";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fail";
    }

    public String setStatus(String shiftId, int status) {
        int insertedStatus = (status == 1) ? 0 : 1;
        this.attdShiftBo.saveStatus(shiftId, insertedStatus);
        return "succ";
    }

    public int getGlobalDefaultCount() {
        return this.attdShiftBo.getGlobalDefaultCount();
    }

    public void computeWorkingHours(String sessionStr, Attendshift attdShift) {
        Date date = DateUtil.convDateTimeToDate(new Date());
        Date[] lastESArr = ExaminDateUtil.getShiftArr(date, sessionStr);

        int minuteInShift = ExaminDateUtil.minutesInShift(lastESArr[0], lastESArr[1], date,
                                                          sessionStr);

        BigDecimal workingHours = new BigDecimal(MyTools.round(minuteInShift / 60, 1));
        attdShift.setAttsWorkingHour(workingHours);

        if (lastESArr[1].getTime() - date.getTime() > 86400000L)
            attdShift.setAttsNightShift(new Integer(1));
        else
            attdShift.setAttsNightShift(new Integer(0));
    }

    public String checkAttsSession(String sessionStr) {
        if ((sessionStr == null) || (sessionStr.trim().length() == 0)) {
            return "班次时间段为空，格式形如＄1�7:00-12:00,13:00-17:00";
        }
        String[] str = sessionStr.split(",");
        String[] hour = null;
        for (int i = 0; (str != null) && (i < str.length); ++i) {
            hour = ((str[i] != null) && (str[i].indexOf("-") > -1)) ? str[i].split("-") : null;

            if ((hour == null) || (hour.length < 2) || (!hour[0].matches("\\d{1,2}:\\d{1,2}"))
                    || (!hour[1].matches("\\d{1,2}:\\d{1,2}"))) {
                return "工时时间段格式不正确，格式形如：08:00-12:00,13:00-17:00";
            }
        }

        return "SUCC";
    }

    public double minusToHour(long minus) {
        SysConfigManager dbConfigManager = DatabaseSysConfigManager.getInstance();
        Map dbMap = dbConfigManager.getProperties();
        String config = (String) dbMap.get("sys.shift.card.calchour");
        String[] configArr = ((config != null) && (config.indexOf(",") > -1)) ? config.split(",")
                : null;
        long half = 0L;
        long one = 0L;
        if (configArr != null) {
            one = Long.parseLong(configArr[0]);
            half = Long.parseLong(configArr[1]);
        }

        double result = minus / 60L;
        long temp = minus % 60L;
        if ((half <= temp) && (temp < one))
            result += 0.5D;
        else if (one <= temp)
            result += 1.0D;

        return result;
    }

    public String formatBD(BigDecimal input) {
        if (input == null) {
            return null;
        }
        DecimalFormat df = new DecimalFormat("#0.0 ");
        return df.format(input);
    }

    public String getDepName(String id) {
        if ((id == null) || (id == ""))
            return "";
        Iterator iterator = this.departs.iterator();

        while (iterator.hasNext()) {
            Department dep = (Department) iterator.next();
            if (id.equals(dep.getId())) {
                return dep.getDepartmentName();
            }
        }
        return "";
    }

    public String getEmpType(String typeId) {
        if (typeId == null)
            return "";
        Iterator iterator = this.empTypes.iterator();

        while (iterator.hasNext()) {
            Emptype type = (Emptype) iterator.next();
            if (typeId.equals(type.getId())) {
                return type.getEmptypeName();
            }
        }
        return "";
    }

    public String getLocName(String id) {
        if ((id == null) || (id == ""))
            return "";
        Iterator iterator = this.locations.iterator();

        while (iterator.hasNext()) {
            Location loc = (Location) iterator.next();
            if (id.equals(loc.getId())) {
                return loc.getLocationName();
            }
        }
        return "";
    }

    public String getEmp() {
        return this.emp;
    }

    public void setEmp(String emp) {
        this.emp = emp;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List getDayList() {
        return this.dayList;
    }

    public void setDayList(List dayList) {
        this.dayList = dayList;
    }

    public List<Department> getDeparts() {
        return this.departs;
    }

    public void setDeparts(List<Department> departs) {
        this.departs = departs;
    }

    public List<Location> getLocations() {
        return this.locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public List getScheduleList() {
        return this.scheduleList;
    }

    public void setScheduleList(List scheduleList) {
        this.scheduleList = scheduleList;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Date getAttdDutyDate1() {
        return this.attdDutyDate1;
    }

    public void setAttdDutyDate1(Date attdDutyDate1) {
        this.attdDutyDate1 = attdDutyDate1;
    }

    public Date getAttdDutyDate2() {
        return this.attdDutyDate2;
    }

    public void setAttdDutyDate2(Date attdDutyDate2) {
        this.attdDutyDate2 = attdDutyDate2;
    }

    public Attendshift getAttdShift() {
        return this.attdShift;
    }

    public void setAttdShift(Attendshift attdShift) {
        this.attdShift = attdShift;
    }

    public Date getDefaultDate() {
        return this.defaultDate;
    }

    public void setDefaultDate(Date defaultDate) {
        this.defaultDate = defaultDate;
    }

    public String getEmpIdString() {
        return this.empIdString;
    }

    public void setEmpIdString(String empIdString) {
        this.empIdString = empIdString;
    }

    public List getShiftList() {
        return this.shiftList;
    }

    public void setShiftList(List shiftList) {
        this.shiftList = shiftList;
    }

    public String getOperation() {
        return this.operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperationId() {
        return this.operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public List<Employee> getEmpList() {
        return this.empList;
    }

    public void setEmpList(List<Employee> empList) {
        this.empList = empList;
    }

    public String[] getEmpId() {
        return this.empId;
    }

    public void setEmpId(String[] empId) {
        this.empId = empId;
    }

    public String getEmpNameString() {
        return this.empNameString;
    }

    public void setEmpNameString(String empNameString) {
        this.empNameString = empNameString;
    }

    public List getDaysList() {
        return this.daysList;
    }

    public void setDaysList(List daysList) {
        this.daysList = daysList;
    }

    public List getWeeksList() {
        return this.weeksList;
    }

    public void setWeeksList(List weeksList) {
        this.weeksList = weeksList;
    }

    public Date getDayFrom() {
        return this.dayFrom;
    }

    public void setDayFrom(Date dayFrom) {
        this.dayFrom = dayFrom;
    }

    public Date getDayTo() {
        return this.dayTo;
    }

    public void setDayTo(Date dayTo) {
        this.dayTo = dayTo;
    }

    public String getDayFlag() {
        return this.dayFlag;
    }

    public void setDayFlag(String dayFlag) {
        this.dayFlag = dayFlag;
    }

    public String getMonthFlag() {
        return this.monthFlag;
    }

    public void setMonthFlag(String monthFlag) {
        this.monthFlag = monthFlag;
    }

    public String getDayFrom1() {
        return this.dayFrom1;
    }

    public void setDayFrom1(String dayFrom1) {
        this.dayFrom1 = dayFrom1;
    }

    public String getDayTo1() {
        return this.dayTo1;
    }

    public void setDayTo1(String dayTo1) {
        this.dayTo1 = dayTo1;
    }

    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getSearchOrExport() {
        return this.searchOrExport;
    }

    public void setSearchOrExport(String searchOrExport) {
        this.searchOrExport = searchOrExport;
    }

    public List<Emptype> getEmpTypes() {
        return this.empTypes;
    }

    public void setEmpTypes(List<Emptype> empTypes) {
        this.empTypes = empTypes;
    }

    public String getOutmatchModelId() {
        return this.outmatchModelId;
    }

    public void setOutmatchModelId(String outmatchModelId) {
        this.outmatchModelId = outmatchModelId;
    }

    public int getResultStatus() {
        return this.resultStatus;
    }

    public void setResultStatus(int resultStatus) {
        this.resultStatus = resultStatus;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.action.ExaminScheduleAction JD-Core Version: 0.5.4
 */