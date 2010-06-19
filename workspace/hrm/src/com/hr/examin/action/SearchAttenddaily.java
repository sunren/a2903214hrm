package com.hr.examin.action;

import com.hr.base.BaseDownloadAction;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.bo.IEmpTypeBO;
import com.hr.configuration.bo.ILocationBO;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Location;
import com.hr.examin.bo.interfaces.IAttenddailyBO;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.BaseCrit;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.DateUtil;
import com.hr.util.Pager;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class SearchAttenddaily extends BaseDownloadAction {
    private static final long serialVersionUID = -5413735265L;
    private Pager page;
    private List attenddailyList;
    private String emp;
    private List<Department> departs;
    private List<Location> locations;
    private List<Emptype> empType;
    private Employee employee;
    private String attendDate;
    private String attendDateTo;
    private Integer searchType;
    private Integer attdType;
    private Date requireDateFrom;
    private Date requireDateTo;
    private String searchOrExport;
    private String outmatchModelId;
    public static final String outputIoName = "OAttendDaily";
    private FileInputStream docStream;
    private String contentDisposition;
    private String serverFileName;
    private String dayHourMode;
    private IAttenddailyBO attenddailyBO;

    public SearchAttenddaily() {
        this.page = new Pager();

        this.searchOrExport = null;

        this.docStream = null;
        this.contentDisposition = null;

        this.serverFileName = null;

        this.dayHourMode = null;

        this.attenddailyBO = ((IAttenddailyBO) getBean("attenddailyBO"));
    }

    public String execute() throws Exception {
        getDrillDownList();

        execParameter();

        List empList = searchEmp();

        if ("export".equals(this.searchOrExport)) {
            setSearchOrExport("");
            List results = this.attenddailyBO.searchAttenddaily(null, this.emp, this.employee,
                                                                this.requireDateFrom,
                                                                this.requireDateTo,
                                                                this.searchType, this.attdType,
                                                                true, empList);
            if ((results == null) || (results.size() == 0)) {
                addActionError("无每日数据可以导出！");
                return "success";
            }
            return downloadToExcel(results, "OAttendDaily", this.outmatchModelId, "empObj");
        }

        this.attenddailyList = this.attenddailyBO.searchAttenddaily(this.page, this.emp,
                                                                    this.employee,
                                                                    this.requireDateFrom,
                                                                    this.requireDateTo,
                                                                    this.searchType, this.attdType,
                                                                    false, empList);

        Map dbMap = DatabaseSysConfigManager.getInstance().getProperties();
        String leaveMode = (String) dbMap.get("sys.examin.leave.mode");
        if ((leaveMode != null) && (leaveMode.indexOf("day") > -1))
            this.dayHourMode = "day";
        else {
            this.dayHourMode = "hour";
        }
        return "success";
    }

    private void execParameter() {
        if (this.employee == null)
            this.employee = new Employee();

        String dateReg = "^\\d{4}-\\d{1,2}-\\d{1,2}$";
        if ((StringUtils.isEmpty(this.attendDate)) || (!this.attendDate.matches(dateReg))) {
            this.attendDate = DateUtil.formatDateToS(new Date(), "yyyy-MM-dd");
        }
        if ((StringUtils.isEmpty(this.attendDateTo)) || (!this.attendDateTo.matches(dateReg))) {
            this.attendDateTo = DateUtil.formatDateToS(new Date(), "yyyy-MM-dd");
        }

        this.requireDateFrom = DateUtil.parseDateByFormat(this.attendDate, "yyyy-MM-dd");
        this.requireDateTo = DateUtil.parseDateByFormat(this.attendDateTo, "yyyy-MM-dd");
    }

    private List<Employee> searchEmp() {
        DetachedCriteria dc = DetachedCriteria.forClass(Employee.class, "emp");

        if ("SUB".equals(this.authorityCondition)) {
            addSubDC(dc, "emp", 4);
        }

        if ("OWN".equals(this.authorityCondition)) {
            BaseCrit.addDC(dc, Employee.PROP_ID, "eq", new String[] { getCurrentEmpNo() });
        }
        addCriteria(dc);

        return this.attenddailyBO.searchEmployee(dc);
    }

    private void addCriteria(DetachedCriteria dc) {
        dc.createAlias("emp." + Employee.PROP_EMP_DEPT_NO, "empOrgDept", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_LOCATION_NO, "empOrgLoc", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_TYPE, "empType", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_PB_NO, "empPbNo", 1);

        BaseCrit.addEmpDC(dc, "emp", this.emp);
        BaseCrit.addDC(dc, "emp." + Employee.PROP_EMP_LOCATION_NO, Location.PROP_ID,
                       new Object[] { this.employee.getEmpLocationNo() });
        BaseCrit.addDeptDC(dc, "emp." + Employee.PROP_EMP_DEPT_NO,
                           "emp." + Employee.PROP_EMP_PB_NO, null, this.employee.getEmpDeptNo());

        dc.add(Restrictions.le("emp." + Employee.PROP_EMP_JOIN_DATE, this.requireDateTo));
        dc.add(Restrictions.or(Restrictions.eq("emp." + Employee.PROP_EMP_STATUS, Integer
                .valueOf(1)), Restrictions.and(Restrictions.eq("emp." + Employee.PROP_EMP_STATUS,
                                                               Integer.valueOf(0)), Restrictions
                .ge("emp." + Employee.PROP_EMP_TERMINATE_DATE, this.requireDateFrom))));
    }

    private void getDrillDownList() {
        ILocationBO localbo = (ILocationBO) SpringBeanFactory.getBean("locationBO");
        this.locations = localbo.FindEnabledLocation();
        IEmpTypeBO emptypebo = (IEmpTypeBO) SpringBeanFactory.getBean("emptypeBO");
        this.empType = emptypebo.FindEnabledEmpType();
        IDepartmentBO deptbo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        this.departs = deptbo.FindEnabledDepartment();
    }

    public String getEmpType(String typeId) {
        if (typeId == null)
            return "";
        Iterator iterator = this.empType.iterator();

        while (iterator.hasNext()) {
            Emptype type = (Emptype) iterator.next();
            if (typeId.equals(type.getId()))
                return type.getEmptypeName();
        }
        return "";
    }

    public String getLocName(String id) {
        if ((id == null) || (id == ""))
            return "";
        Iterator iterator = this.locations.iterator();

        while (iterator.hasNext()) {
            Location loc = (Location) iterator.next();
            if (id.equals(loc.getId()))
                return loc.getLocationName();
        }
        return "";
    }

    public String getDepName(String id) {
        if ((id == null) || (id == ""))
            return "";
        Iterator iterator = this.departs.iterator();

        while (iterator.hasNext()) {
            Department dep = (Department) iterator.next();
            if (id.equals(dep.getId()))
                return dep.getDepartmentName();
        }
        return "";
    }

    public String formatBD(BigDecimal input) {
        if (input == null)
            return null;
        DecimalFormat df = new DecimalFormat("#0.0 ");
        return df.format(input);
    }

    public String formatBD_m(BigDecimal input) {
        if (input == null)
            return null;
        DecimalFormat df = new DecimalFormat("#0 ");
        return df.format(input);
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public List getAttenddailyList() {
        return this.attenddailyList;
    }

    public void setAttenddailyList(List attenddailyList) {
        this.attenddailyList = attenddailyList;
    }

    public String getEmp() {
        return this.emp;
    }

    public void setEmp(String emp) {
        this.emp = emp;
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

    public List<Emptype> getEmpType() {
        return this.empType;
    }

    public void setEmpType(List<Emptype> empType) {
        this.empType = empType;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getAttendDate() {
        return this.attendDate;
    }

    public void setAttendDate(String attendDate) {
        this.attendDate = attendDate;
    }

    public Integer getSearchType() {
        return this.searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    public String getAttendDateTo() {
        return this.attendDateTo;
    }

    public void setAttendDateTo(String attendDateTo) {
        this.attendDateTo = attendDateTo;
    }

    public Integer getAttdType() {
        return this.attdType;
    }

    public void setAttdType(Integer attdType) {
        this.attdType = attdType;
    }

    public String getSearchOrExport() {
        return this.searchOrExport;
    }

    public void setSearchOrExport(String searchOrExport) {
        this.searchOrExport = searchOrExport;
    }

    public FileInputStream getDocStream() {
        return this.docStream;
    }

    public void setDocStream(FileInputStream docStream) {
        this.docStream = docStream;
    }

    public String getContentDisposition() {
        return this.contentDisposition;
    }

    public void setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    public String getServerFileName() {
        return this.serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    public String getOutmatchModelId() {
        return this.outmatchModelId;
    }

    public void setOutmatchModelId(String outmatchModelId) {
        this.outmatchModelId = outmatchModelId;
    }

    public static String getOutputIoName() {
        return "OAttendDaily";
    }

    public String getDayHourMode() {
        return this.dayHourMode;
    }

    public void setDayHourMode(String dayHourMode) {
        this.dayHourMode = dayHourMode;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.action.SearchAttenddaily JD-Core Version: 0.5.4
 */