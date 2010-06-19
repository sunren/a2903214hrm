package com.hr.examin.action;

import com.hr.base.BaseAction;
import com.hr.base.BaseDownloadAction;
import com.hr.configuration.bo.IEmpTypeBO;
import com.hr.configuration.bo.ILocationBO;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Location;
import com.hr.examin.bo.ExaminDateUtil;
import com.hr.examin.bo.interfaces.IAttendmonthlyBO;
import com.hr.examin.core.ExaminBoFactory;
import com.hr.examin.domain.Attendmonthly;
import com.hr.examin.domain.Attendperiod;
import com.hr.profile.bo.IEmpAddConfBo;
import com.hr.profile.domain.Empaddconf;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.BaseCrit;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.DateUtil;
import com.hr.util.Pager;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class SearchAttendmonthly extends BaseDownloadAction {
    private static final long serialVersionUID = -5413735265L;
    private Pager page;
    private List<Attendmonthly> attendmonthlyList;
    private String emp;
    private List<Location> locations;
    private List<Emptype> empType;
    private Employee employee;
    private Integer status;
    private Attendperiod period;
    private Integer auth;
    private String year;
    private String month;
    private String delAttendId;
    private int searchType;
    private List<Empaddconf> attdConfList;
    private String startDate;
    private String endDate;
    private String searchOrExport;
    private String outmatchModelId;
    public static final String outputIoName = "OExamMonthly";
    private FileInputStream docStream;
    private String contentDisposition;
    private String serverFileName;
    private boolean displayByDay;
    private IAttendmonthlyBO attendmothlyBO;

    public SearchAttendmonthly() {
        this.page = new Pager();

        this.searchOrExport = null;

        this.docStream = null;
        this.contentDisposition = null;

        this.serverFileName = null;

        this.attendmothlyBO = ((IAttendmonthlyBO) SpringBeanFactory.getBean("attendmonthlyBo"));
    }

    public String execute() throws Exception {
        getDrillDownList();

        String yearMonth = execParameter();

        if ("export".equals(this.searchOrExport)) {
            this.attendmonthlyList = searchAttendmonthly(null, yearMonth);

            if (StringUtils.equals(this.searchOrExport, "export")) {
                setSearchOrExport("");
                return downloadToExcel(this.attendmonthlyList, "OExamMonthly",
                                       this.outmatchModelId, "attmEmpId");
            }

        }

        this.attendmonthlyList = searchAttendmonthly(this.page, yearMonth);

        return "success";
    }

    private List<Attendmonthly> searchAttendmonthly(Pager pager, String yearMonth) {
        DetachedCriteria dc = DetachedCriteria.forClass(Attendmonthly.class);
        dc.createAlias(Attendmonthly.PROP_ATTM_EMP_ID, "emp", 1);

        if ("SUB".equals(this.authorityCondition)) {
            addSubDC(dc, "emp", 4);
        }

        addCriteria(dc, yearMonth);

        return this.attendmothlyBO.searchAttendmonthly(pager, dc);
    }

    private void addCriteria(DetachedCriteria dc, String yearMonth) {
        dc.createAlias("emp." + Employee.PROP_EMP_DEPT_NO, "empOrgDept", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_LOCATION_NO, "empOrgLoc", 1);
        dc.createAlias(Attendmonthly.PROP_ATTM_PB_NO, "attmPbNo", 1);

        dc.add(Restrictions.eq(Attendmonthly.PROP_ATTM_YEARMONTH, yearMonth));
        BaseCrit.addEmpDC(dc, "emp", this.emp);

        if (this.employee != null) {
            BaseCrit.addDC(dc, "emp." + Employee.PROP_ID, "eq", new String[] { this.employee
                    .getId() });

            BaseCrit.addDeptDC(dc, "emp." + Employee.PROP_EMP_DEPT_NO, "emp."
                    + Employee.PROP_EMP_PB_NO, null, this.employee.getEmpDeptNo());
            BaseCrit.addDC(dc, "emp." + Employee.PROP_EMP_TYPE, Emptype.PROP_ID,
                           new Object[] { this.employee.getEmpType() });
            BaseCrit.addDC(dc, "emp." + Employee.PROP_EMP_LOCATION_NO, Location.PROP_ID,
                           new Object[] { this.employee.getEmpLocationNo() });
        }

        if (this.searchType != 0) {
            switch (this.searchType) {
            case 1:
                dc.add(Restrictions.gt("attmLeaveHours", new BigDecimal(0)));
                break;
            case 2:
                dc.add(Restrictions.gt("attmLateTimes", new BigDecimal(0)));
                break;
            case 3:
                dc.add(Restrictions.gt("attmEarlyLeave", new BigDecimal(0)));
                break;
            case 4:
                dc.add(Restrictions.gt("attmOvertimeHours", new BigDecimal(0)));
                break;
            case 5:
                dc.add(Restrictions.gt("attmOffDutyHours", new BigDecimal(0)));
            }

        }

        if ("export".equals(this.searchOrExport)) {
            dc.setFetchMode(Attendmonthly.PROP_ATTM_DEPT, FetchMode.JOIN);
            dc.setFetchMode(Attendmonthly.PROP_ATTM_LOCATION, FetchMode.JOIN);
            dc.setFetchMode(Attendmonthly.PROP_ATTM_EMPTYPE, FetchMode.JOIN);
            dc.setFetchMode(Attendmonthly.PROP_ATTM_PB_NO, FetchMode.JOIN);

            addOrders(dc, null, new String[] { "emp." + Employee.PROP_EMP_DISTINCT_NO + "-up" });
        } else {
            addOrders(dc, this.page,
                      new String[] { "emp." + Employee.PROP_EMP_DISTINCT_NO + "-up" });

            this.page.splitPage(dc);
        }
    }

    private String execParameter() {
        if ((this.year == null) || (this.month == null) || (this.year.equals(""))
                || (this.month.equals(""))) {
            this.year = DateUtil.formatDateToS(new Date(), "yyyy");
            this.month = DateUtil.formatDateToS(new Date(), "MM");
        }

        DatabaseSysConfigManager db = DatabaseSysConfigManager.getInstance();
        Map props = db.getProperties();
        String mode = (String) props.get("sys.examin.leave.mode");
        this.displayByDay = mode.split(",")[0].equals("day");
        String config = (String) props.get("sys.examin.month.sum");
        Date[] dateArr = ExaminDateUtil.getDateArray(Integer.valueOf(this.year).intValue(), Integer
                .valueOf(this.month).intValue(), config);
        this.startDate = formatDate(dateArr[0]);
        this.endDate = formatDate(dateArr[1]);
        ExaminBoFactory factory = ExaminBoFactory.getInstance();
        IAttendmonthlyBO attendmothlyBO = factory.createAttendmonthlyBo();
        this.period = attendmothlyBO.loadAttendperiod(this.year, this.month);
        if (this.period == null)
            setStatus(Integer.valueOf(0));
        else {
            setStatus(this.period.getAttpStatus());
        }

        return this.year + this.month;
    }

    private void getDrillDownList() {
        ILocationBO localbo = (ILocationBO) SpringBeanFactory.getBean("locationBO");
        this.locations = localbo.FindEnabledLocation();
        IEmpTypeBO emptypebo = (IEmpTypeBO) SpringBeanFactory.getBean("emptypeBO");
        this.empType = emptypebo.FindEnabledEmpType();

        IEmpAddConfBo empAddConfBo = (IEmpAddConfBo) BaseAction.getBean("empAddConfBo");
        this.attdConfList = empAddConfBo.listByTable("attend");
        for (Empaddconf conf : this.attdConfList)
            if (conf != null)
                conf.setFieldValueList(getListByString(conf.getEadcFieldValue()));
    }

    public String reOpenAttendperiod() {
        if ((StringUtils.isEmpty(this.year)) || (StringUtils.isEmpty(this.month))) {
            return "success";
        }
        if (this.month.length() == 1) {
            this.month = ("0" + this.month);
        }
        String msg = this.year + "幄1�7" + this.month + "朄1�7";
        Attendperiod period = this.attendmothlyBO.loadAttendperiod(this.year, this.month);
        if (period == null) {
            addErrorInfo(msg + "的�1�7�勤数据不是封帐状�1�7�，不能解封＄1�7");
        } else if (period.getAttpStatus().intValue() == 2) {
            period.setAttpStatus(Integer.valueOf(0));
            this.attendmothlyBO.updateObject(period);
            addSuccessInfo(msg + "的�1�7�勤数据解封成功＄1�7");
        } else {
            addErrorInfo(msg + "的�1�7�勤数据不是封帐状�1�7�，不能解封＄1�7");
        }

        return "success";
    }

    public String closeAttendperiod() {
        if ((StringUtils.isEmpty(this.year)) || (StringUtils.isEmpty(this.month))) {
            return "success";
        }
        if (this.month.length() == 1) {
            this.month = ("0" + this.month);
        }
        String msg = this.year + "幄1�7" + this.month + "朄1�7";
        ExaminBoFactory factory = ExaminBoFactory.getInstance();
        IAttendmonthlyBO attendmothlyBO = factory.createAttendmonthlyBo();
        Attendperiod period = attendmothlyBO.loadAttendperiod(this.year, this.month);
        if (period == null) {
            addErrorInfo(msg + "的�1�7�勤数据为初始化状�1�7�，不能进行封帐＄1�7");
        } else if (period.getAttpStatus().intValue() == 1) {
            period.setAttpStatus(Integer.valueOf(2));
            attendmothlyBO.updateObject(period);
            addSuccessInfo(msg + "的�1�7�勤数据封帐成功＄1�7");
        } else {
            addErrorInfo(msg + "的�1�7�勤数据不是初始化状态，不能封帐＄1�7");
        }

        return "success";
    }

    public String applyAttendperiod() {
        if ((StringUtils.isEmpty(this.year)) || (StringUtils.isEmpty(this.month))) {
            return "success";
        }
        if (this.month.length() == 1) {
            this.month = ("0" + this.month);
        }
        String msg = this.year + "幄1�7" + this.month + "朄1�7";
        ExaminBoFactory factory = ExaminBoFactory.getInstance();
        IAttendmonthlyBO attendmothlyBO = factory.createAttendmonthlyBo();
        Attendperiod period = attendmothlyBO.loadAttendperiod(this.year, this.month);
        if (period == null) {
            addErrorInfo(msg + "的�1�7�勤数据不是初始化状态，不能进行封帐申请操作＄1�7");
        } else if (period.getAttpStatus().intValue() == 0) {
            period.setAttpStatus(Integer.valueOf(1));
            attendmothlyBO.updateObject(period);
            addSuccessInfo(msg + "的�1�7�勤数据封帐申请成功＄1�7");
        } else {
            addErrorInfo(msg + "的�1�7�勤数据不是初始化状态，不能进行封帐申请操作＄1�7");
        }

        return "success";
    }

    private String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public String deleteAttendmonthly() {
        ExaminBoFactory factory = ExaminBoFactory.getInstance();
        IAttendmonthlyBO attendmothlyBO = factory.createAttendmonthlyBo();
        if (attendmothlyBO.deleteAttendmonthly(this.delAttendId)) {
            addSuccessInfo("删除成功〄1�7");
            return "success";
        }
        addErrorInfo("删除失败，请重试＄1�7");
        return "error";
    }

    public String batchDelAttendmonthly() {
        ExaminBoFactory factory = ExaminBoFactory.getInstance();
        IAttendmonthlyBO attendmothlyBO = factory.createAttendmonthlyBo();
        if (attendmothlyBO.deleteAttendmonthlyByDate(this.year, this.month)) {
            addSuccessInfo("删除考勤数据成功〄1�7");
            return "success";
        }
        addErrorInfo("删除失败，请重试＄1�7");
        return "error";
    }

    public String calDailyToAttendmonthly() {
        ExaminBoFactory factory = ExaminBoFactory.getInstance();
        IAttendmonthlyBO attendmothlyBO = factory.createAttendmonthlyBo();
        if ((StringUtils.isEmpty(this.year)) || (StringUtils.isEmpty(this.month))) {
            addSuccessInfo("要初始化的年月不能为空！请�1�7�择年月〄1�7");
            return "success";
        }
        if (attendmothlyBO.exeMonthlySummary(this.year, this.month)) {
            addSuccessInfo("初始化�1�7�勤数据成功〄1�7");
            return "success";
        }
        addErrorInfo("初始化失败，请重试！");
        return "error";
    }

    public String getEmpType(String id) {
        if ((id == null) || (id == ""))
            return "";
        Iterator iterator = this.empType.iterator();

        while (iterator.hasNext()) {
            Emptype type = (Emptype) iterator.next();
            if (id.equals(type.getId())) {
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

    public List<String> getListByString(String str) {
        if ((str == null) || ("".equals(str))) {
            return null;
        }
        List result = new ArrayList();
        String[] temp = str.split(",");
        for (int i = 0; i < temp.length; ++i) {
            result.add(temp[i]);
        }
        return result;
    }

    public String formatBD(BigDecimal input) {
        if (input == null) {
            return null;
        }
        DecimalFormat df = new DecimalFormat("#0.0 ");
        return df.format(input);
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public String getEmp() {
        return this.emp;
    }

    public void setEmp(String emp) {
        this.emp = emp;
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

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAuth() {
        return this.auth;
    }

    public void setAuth(Integer auth) {
        this.auth = auth;
    }

    public String getDelAttendId() {
        return this.delAttendId;
    }

    public void setDelAttendId(String delAttendId) {
        this.delAttendId = delAttendId;
    }

    public int getSearchType() {
        return this.searchType;
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }

    public List<Attendmonthly> getAttendmonthlyList() {
        return this.attendmonthlyList;
    }

    public void setAttendmonthlyList(List<Attendmonthly> attendmonthlyList) {
        this.attendmonthlyList = attendmonthlyList;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<Empaddconf> getAttdConfList() {
        return this.attdConfList;
    }

    public void setAttdConfList(List<Empaddconf> attdConfList) {
        this.attdConfList = attdConfList;
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

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Attendperiod getPeriod() {
        return this.period;
    }

    public void setPeriod(Attendperiod period) {
        this.period = period;
    }

    public String getOutmatchModelId() {
        return this.outmatchModelId;
    }

    public void setOutmatchModelId(String outmatchModelId) {
        this.outmatchModelId = outmatchModelId;
    }

    public static String getOutputIoName() {
        return "OExamMonthly";
    }

    public boolean isDisplayByDay() {
        return this.displayByDay;
    }

    public void setDisplayByDay(boolean displayByDay) {
        this.displayByDay = displayByDay;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.action.SearchAttendmonthly JD-Core Version: 0.5.4
 */