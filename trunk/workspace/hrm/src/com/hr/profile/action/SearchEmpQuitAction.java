package com.hr.profile.action;

import com.hr.base.BaseDownloadAction;
import com.hr.base.ComonBeans;
import com.hr.configuration.domain.Department;
import com.hr.profile.bo.IEmpQuitBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Empquit;
import com.hr.profile.domain.SimpleEmployee;
import com.hr.util.BaseCrit;
import com.hr.util.Pager;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;

public class SearchEmpQuitAction extends BaseDownloadAction {
    private List<Employee> quitList;
    private String employeeId;
    private String departmentId;
    private String departmentName;
    private String eqType;
    private String eqReason;
    private String eqPermission;
    private String eqComments;
    private Date eqDate;
    private Date eqDateTo;
    private Empquit quit;
    private Employee quitemp;
    private Date toDate;
    private List departments;
    private List jobtitles;
    private List emptypes;
    private List<SimpleEmployee> managerList;
    private Pager page;
    private Map<String, String> eqTypeMap;
    private Map<String, Map<String, String>> eqReasonMap;
    private Map<String, String> eqRTypeMap;
    private Map<String, Map<String, String>> eqRReasonMap;
    private String exportFlag;
    private String outmatchModelId;
    public String outputIoName;
    private String searchOrExport;

    public SearchEmpQuitAction() {
        this.page = new Pager();

        this.outputIoName = "OEmployeeQuit";

        this.searchOrExport = null;
    }

    public String execute() throws Exception {
        getDrillDownList();

        this.quitList = searchEmpQuit();

        if ("export".equals(this.searchOrExport)) {
            return downloadToExcel(this.quitList, this.outputIoName, this.outmatchModelId, "");
        }

        return "success";
    }

    private List<Employee> searchEmpQuit() {
        DetachedCriteria dcEmpQuit = searchEmpWithQuit_DC();

        addCriteria(dcEmpQuit);

        IEmpQuitBo empQuit = (IEmpQuitBo) getBean("empQuitBo");
        return empQuit.findEmpQuit(dcEmpQuit, this.page, this.searchOrExport);
    }

    private DetachedCriteria searchEmpWithQuit_DC() {
        DetachedCriteria dc = DetachedCriteria.forClass(Employee.class);

        dc.createAlias(Employee.PROP_EMP_QUIT_ID, "quit", 1);
        dc.createAlias(Employee.PROP_EMP_DEPT_NO, "empDeptNo", 1);
        dc.createAlias("quit." + Empquit.PROP_EQ_PERMISSION, "manager", 1);

        if ("export".equals(this.searchOrExport)) {
            dc.setFetchMode(Employee.PROP_EMP_PB_NO, FetchMode.JOIN);
            dc.setFetchMode(Employee.PROP_EMP_LOCATION_NO, FetchMode.JOIN);
            dc.setFetchMode(Employee.PROP_EMP_TYPE, FetchMode.JOIN);
            dc.setFetchMode(Employee.PROP_EMP_BENEFIT_TYPE, FetchMode.JOIN);
        }

        return dc;
    }

    public void addCriteria(DetachedCriteria dc) {
        if ("SUB".equals(this.authorityCondition)) {
            addSubDC(dc, "", 1);
        }

        addOrders(dc, this.page, new String[] { Employee.PROP_EMP_TERMINATE_DATE + "-down",
                Employee.PROP_EMP_DISTINCT_NO + "-up" });

        BaseCrit.addDC(dc, Employee.PROP_EMP_STATUS, "eq", new Integer[] { Integer.valueOf(0) });
        BaseCrit.addEmpDC(dc, "", this.employeeId);
        BaseCrit.addDeptDC(dc, Employee.PROP_EMP_DEPT_NO, Employee.PROP_EMP_PB_NO, null,
                           new Department(this.departmentId));
        BaseCrit.addDC(dc, "quit." + Empquit.PROP_EQ_TYPE, "eq", new String[] { this.eqType });
        BaseCrit.addDC(dc, "quit." + Empquit.PROP_EQ_REASON, "eq", new String[] { this.eqReason });

        BaseCrit.addEmpDC(dc, "manager", this.eqPermission);
        BaseCrit.addDC(dc, "quit." + Empquit.PROP_ER_COMMENTS, "like",
                       new String[] { this.eqComments });
        BaseCrit.addDCDateRange(dc, Employee.PROP_EMP_TERMINATE_DATE, this.eqDate, this.eqDateTo);
    }

    private void getDrillDownList() {
        this.departments = getDrillDown("Department", new String[] { "1" });

        this.eqTypeMap = ComonBeans.getValuesToMap(Empquit.PROP_EQ_TYPE, new boolean[] { true });
        this.eqReasonMap = ComonBeans.getSecondLevelSelect(this.eqTypeMap, Empquit.PROP_EQ_REASON,
                                                           new boolean[] { true });

        this.eqRTypeMap = new TreeMap();
        this.eqRReasonMap = new TreeMap();
        this.eqRTypeMap.put("1", this.eqTypeMap.get("1"));
        this.eqRReasonMap.put("1", this.eqReasonMap.get("1"));

        this.eqTypeMap.remove("1");
        this.eqReasonMap.remove("1");
    }

    private void setQRMap() {
        this.eqTypeMap = ComonBeans.getValuesToMap(Empquit.PROP_EQ_TYPE, new boolean[] { true });
        this.eqReasonMap = ComonBeans.getSecondLevelSelect(this.eqTypeMap, Empquit.PROP_EQ_REASON,
                                                           new boolean[] { true });

        this.eqRTypeMap = new TreeMap();
        this.eqRReasonMap = new TreeMap();
        this.eqRTypeMap.put("1", this.eqTypeMap.get("1"));
        this.eqRReasonMap.put("1", this.eqReasonMap.get("1"));

        this.eqTypeMap.remove("1");
        this.eqReasonMap.remove("1");
    }

    public Empquit getQuit() {
        return this.quit;
    }

    public void setQuit(Empquit quit) {
        this.quit = quit;
    }

    public Date getToDate() {
        return this.toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public List getDepartments() {
        return this.departments;
    }

    public void setDepartments(List departments) {
        this.departments = departments;
    }

    public List getJobtitles() {
        return this.jobtitles;
    }

    public void setJobtitles(List jobtitles) {
        this.jobtitles = jobtitles;
    }

    public List getEmptypes() {
        return this.emptypes;
    }

    public void setEmptypes(List emptypes) {
        this.emptypes = emptypes;
    }

    public List<SimpleEmployee> getManagerList() {
        return this.managerList;
    }

    public void setManagerList(List<SimpleEmployee> managerList) {
        this.managerList = managerList;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public String getExportFlag() {
        return this.exportFlag;
    }

    public void setExportFlag(String exportFlag) {
        this.exportFlag = exportFlag;
    }

    public List<Employee> getQuitList() {
        return this.quitList;
    }

    public void setQuitList(List<Employee> quitList) {
        this.quitList = quitList;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getEqType() {
        return this.eqType;
    }

    public void setEqType(String eqType) {
        this.eqType = eqType;
    }

    public String getEqReason() {
        return this.eqReason;
    }

    public void setEqReason(String eqReason) {
        this.eqReason = eqReason;
    }

    public String getEqPermission() {
        return this.eqPermission;
    }

    public void setEqPermission(String eqPermission) {
        this.eqPermission = eqPermission;
    }

    public String getEqComments() {
        return this.eqComments;
    }

    public void setEqComments(String eqComments) {
        this.eqComments = eqComments;
    }

    public Date getEqDate() {
        return this.eqDate;
    }

    public void setEqDate(Date eqDate) {
        this.eqDate = eqDate;
    }

    public Date getEqDateTo() {
        return this.eqDateTo;
    }

    public void setEqDateTo(Date eqDateTo) {
        this.eqDateTo = eqDateTo;
    }

    public Employee getQuitemp() {
        return this.quitemp;
    }

    public void setQuitemp(Employee quitemp) {
        this.quitemp = quitemp;
    }

    public String getOutmatchModelId() {
        return this.outmatchModelId;
    }

    public void setOutmatchModelId(String outmatchModelId) {
        this.outmatchModelId = outmatchModelId;
    }

    public String getOutputIoName() {
        return this.outputIoName;
    }

    public void setOutputIoName(String outputIoName) {
        this.outputIoName = outputIoName;
    }

    public Map<String, String> getEqTypeMap() {
        return this.eqTypeMap;
    }

    public Map<String, Map<String, String>> getEqReasonMap() {
        return this.eqReasonMap;
    }

    public Map<String, String> getEqRTypeMap() {
        return this.eqRTypeMap;
    }

    public void setEqRTypeMap(Map<String, String> eqRTypeMap) {
        this.eqRTypeMap = eqRTypeMap;
    }

    public Map<String, Map<String, String>> getEqRReasonMap() {
        return this.eqRReasonMap;
    }

    public void setEqRReasonMap(Map<String, Map<String, String>> eqRReasonMap) {
        this.eqRReasonMap = eqRReasonMap;
    }

    public String getSearchOrExport() {
        return this.searchOrExport;
    }

    public void setSearchOrExport(String searchOrExport) {
        this.searchOrExport = searchOrExport;
    }

    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.SearchEmpQuitAction JD-Core Version: 0.5.4
 */