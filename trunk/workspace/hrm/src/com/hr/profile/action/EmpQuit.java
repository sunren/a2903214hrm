package com.hr.profile.action;

import com.hr.base.BaseDownloadAction;
import com.hr.base.ComonBeans;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.JobTitle;
import com.hr.profile.bo.IEmpQuitBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Empquit;
import com.hr.profile.domain.Position;
import com.hr.security.bo.UserBo;
import com.hr.security.domain.Userinfo;
import com.hr.util.BaseCrit;
import com.hr.util.Pager;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;

public class EmpQuit extends BaseDownloadAction {
    private static final long serialVersionUID = 1L;
    private static final String AUTHMODULE = "911";
    private String employeeId;
    private String eqPermission;
    private String departmentId;
    private String departmentName;
    private Date eqDate;
    private Date eqDateTo;
    private String eqType;
    private String eqReason;
    private String eqComments;
    private String addPositionId;
    private Map<String, String> eqTypeMap;
    private Map<String, Map<String, String>> eqReasonMap;
    private Map<String, String> eqRTypeMap;
    private Map<String, Map<String, String>> eqRReasonMap;
    private List<Department> departments;
    private List<JobTitle> jobtitles;
    private List<Emptype> emptypes;
    private Employee quitemp;
    private String empNo;
    private String equReason;
    private String updateEqId;
    private List<Empquit> quitList;
    private Pager page;
    private String outmatchModelId;
    private String outputIoName;
    private String searchOrExport;

    public EmpQuit() {
        this.page = new Pager();

        this.outputIoName = "OEmpQuit";

        this.searchOrExport = null;
    }

    public String list() throws Exception {
        getDrillDownList();

        this.quitList = searchQuitEmp();

        if ("export".equals(this.searchOrExport)) {
            return downloadToExcel(this.quitList, this.outputIoName, this.outmatchModelId,
                                   "employee");
        }

        return "success";
    }

    private List<Empquit> searchQuitEmp() {
        DetachedCriteria dcQuitEmp = searchQuitWithEmp_DC();

        addCriteria(dcQuitEmp);

        IEmpQuitBo empQuit = (IEmpQuitBo) getBean("empQuitBo");
        return empQuit.findQuitEmp(dcQuitEmp, this.page, this.searchOrExport);
    }

    private DetachedCriteria searchQuitWithEmp_DC() {
        DetachedCriteria dc = DetachedCriteria.forClass(Empquit.class);

        dc.createAlias(Empquit.PROP_EMPLOYEE, "emp", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_DEPT_NO, "emp.empDeptNo", 1);
        dc.createAlias(Empquit.PROP_EQ_DEPT_NO, "department", 1);
        dc.createAlias(Empquit.PROP_EQ_PERMISSION, "manager", 1);

        if ("export".equals(this.searchOrExport)) {
            dc.createAlias("emp." + Employee.PROP_EMP_PB_NO, Employee.PROP_EMP_PB_NO, 1);
            dc
                    .createAlias("emp." + Employee.PROP_EMP_LOCATION_NO,
                                 Employee.PROP_EMP_LOCATION_NO, 1);
            dc.createAlias("emp." + Employee.PROP_EMP_TYPE, Employee.PROP_EMP_TYPE, 1);
            dc.createAlias("emp." + Employee.PROP_EMP_BENEFIT_TYPE, Employee.PROP_EMP_BENEFIT_TYPE,
                           1);
        }

        return dc;
    }

    public void addCriteria(DetachedCriteria dc) {
        if ("SUB".equals(this.authorityCondition)) {
            addSubDC(dc, "emp", 1);
        }

        addOrders(dc, this.page, new String[] { Empquit.PROP_EQ_DATE + "-down",
                "emp." + Employee.PROP_EMP_DISTINCT_NO + "-up" });

        BaseCrit.addEmpDC(dc, "emp", this.employeeId);
        BaseCrit.addDeptDC(dc, "emp." + Employee.PROP_EMP_DEPT_NO,
                           "emp." + Employee.PROP_EMP_PB_NO, null,
                           new Department(this.departmentId));
        BaseCrit.addDC(dc, Empquit.PROP_EQ_TYPE, "eq", new String[] { this.eqType });
        BaseCrit.addDC(dc, Empquit.PROP_EQ_REASON, "eq", new String[] { this.eqReason });

        BaseCrit.addEmpDC(dc, "manager", this.eqPermission);
        BaseCrit.addDC(dc, Empquit.PROP_ER_COMMENTS, "like", new String[] { this.eqComments });
        BaseCrit.addDCDateRange(dc, Empquit.PROP_EQ_DATE, this.eqDate, this.eqDateTo);
    }

    private void getDrillDownList() {
        this.departments = getDrillDown("Department", new String[] { "1" });

        this.eqTypeMap = ComonBeans.getValuesToMap(Empquit.PROP_EQ_TYPE, new boolean[] { true });
        this.eqReasonMap = ComonBeans.getSecondLevelSelect(this.eqTypeMap, Empquit.PROP_EQ_REASON,
                                                           new boolean[] { true });
    }

    public String addQuit() throws Exception {
        if (StringUtils.isEmpty(this.employeeId)) {
            addErrorInfo("请求的参数错误，请选择要离职/复职的员工！");
            return "success";
        }

        String currentEmpNo = getCurrentEmpNo();
        if (this.employeeId.equals(currentEmpNo)) {
            addErrorInfo("不能将本人离职！");
            return "success";
        }

        if (("SUB".equals(this.authorityCondition)) && (!checkAuth(this.employeeId))) {
            addErrorInfo("您没有修改权限执行本操作");
            return "error";
        }

        UserBo userBo = (UserBo) getBean("userService");
        Userinfo oldUserinfo = userBo.getUserById(this.employeeId);
        if (userBo.checkAuthModule(oldUserinfo, "911")) {
            addErrorInfo("该员工为系统管理员，请到系统模块取消次该用户的管理员权限后离职！");
            return "success";
        }
        Empquit empquit = new Empquit();
        empquit.setEmployee(new Employee(this.employeeId));
        empquit.setEqDate(this.eqDate);
        empquit.setEqPermission(new Employee(this.eqPermission));
        empquit.setEqReason(this.eqReason);
        empquit.setEqType(this.eqType.toString());
        empquit.setErComments(this.eqComments);

        empquit.setEqCreateBy(currentEmpNo);
        empquit.setEqCreateDate(new Date());
        empquit.setEqLastChangeBy(currentEmpNo);
        empquit.setEqLastChangeTime(new Date());
        empquit.setPosition(new Position(this.addPositionId));

        IEmpQuitBo empQuitBo = (IEmpQuitBo) getBean("empQuitBo");
        String result = empQuitBo.saveEmpQuit(empquit);
        if (!"success".equals(result)) {
            addActionError(result);
            return "error";
        }

        setQRMap();
        addActionMessage("新增员工离职信息成功");
        return "success";
    }

    public String deleteQuit() throws Exception {
        IEmpQuitBo empConstractBo = (IEmpQuitBo) getBean("empQuitBo");
        Empquit etf = empConstractBo.getById(this.updateEqId);
        if (etf == null) {
            return "error";
        }

        if (("SUB".equals(this.authorityCondition)) && (!checkAuth(etf.getEmployee().getId()))) {
            addErrorInfo("您没有删除权限执行本操作");
            return "error";
        }

        String error = empConstractBo.delete(this.updateEqId);
        if (StringUtils.isEmpty(error)) {
            addActionMessage("删除" + etf.getEmployee().getEmpName()
                    + (("1".equals(etf.getEqType())) ? "复职" : "离职") + "信息成功");
        } else {
            addErrorInfo(error);
        }
        return "success";
    }

    public String updateQuit() throws Exception {
        String currentEmpNo = getCurrentEmpNo();

        if ((this.employeeId == null) || ("".equals(this.employeeId))) {
            this.employeeId = currentEmpNo;
        }
        if ((this.updateEqId == null) || ("".equals(this.updateEqId))) {
            return "noId";
        }

        if (("SUB".equals(this.authorityCondition)) && (!checkAuth(this.employeeId))) {
            addErrorInfo("您没有修改权限执行本操作");
            return "success";
        }

        Empquit empquit = new Empquit();

        empquit.setEmployee(new Employee(this.employeeId));
        empquit.setEqDate(this.eqDate);
        empquit.setEqPermission(new Employee(this.eqPermission));
        empquit.setEqReason(this.equReason);
        empquit.setEqType(this.eqType.toString());
        empquit.setErComments(this.eqComments);

        empquit.setEqLastChangeBy(currentEmpNo);
        empquit.setEqLastChangeTime(new Date());

        IEmpQuitBo empQuitBo = (IEmpQuitBo) getBean("empQuitBo");

        empQuitBo.update(empquit, this.updateEqId);
        clear();
        addActionMessage("更新员工离职信息成功");

        setQRMap();
        return "success";
    }

    private void setQRMap() {
        this.eqTypeMap = ComonBeans.getValuesToMap(Empquit.PROP_EQ_TYPE, new boolean[] { false });
        this.eqReasonMap = ComonBeans.getSecondLevelSelect(this.eqTypeMap, Empquit.PROP_EQ_REASON,
                                                           new boolean[0]);

        this.eqRTypeMap = new TreeMap();
        this.eqRReasonMap = new TreeMap();
        this.eqRTypeMap.put("1", this.eqTypeMap.get("1"));
        this.eqRReasonMap.put("1", this.eqReasonMap.get("1"));

        this.eqTypeMap.remove("1");
        this.eqReasonMap.remove("1");
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public void setEmptypes(List<Emptype> emptypes) {
        this.emptypes = emptypes;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEqType() {
        return this.eqType;
    }

    public void setEqType(String eqType) {
        this.eqType = eqType;
    }

    public Date getEqDate() {
        return this.eqDate;
    }

    public void setEqDate(Date eqDate) {
        this.eqDate = eqDate;
    }

    public String getEqReason() {
        return this.eqReason;
    }

    public void setEqReason(String eqReason) {
        this.eqReason = eqReason;
    }

    public String getEqComments() {
        return this.eqComments;
    }

    public void setEqComments(String eqComments) {
        this.eqComments = eqComments;
    }

    public String getEqPermission() {
        return this.eqPermission;
    }

    public void setEqPermission(String eqPermission) {
        this.eqPermission = eqPermission;
    }

    public String getUpdateEqId() {
        return this.updateEqId;
    }

    public void setUpdateEqId(String updateEqId) {
        this.updateEqId = updateEqId;
    }

    public List<Empquit> getQuitList() {
        return this.quitList;
    }

    public void setQuitList(List<Empquit> quitList) {
        this.quitList = quitList;
    }

    public Date getEqDateTo() {
        return this.eqDateTo;
    }

    public void setEqDateTo(Date eqDateTo) {
        this.eqDateTo = eqDateTo;
    }

    private void clear() {
        this.employeeId = null;
        this.eqDate = null;
        this.eqPermission = null;
        this.eqReason = null;
        this.eqType = null;
        this.eqComments = null;
        this.updateEqId = null;
    }

    public String getEquReason() {
        return this.equReason;
    }

    public void setEquReason(String equReason) {
        this.equReason = equReason;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
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

    public String getSearchOrExport() {
        return this.searchOrExport;
    }

    public void setSearchOrExport(String searchOrExport) {
        this.searchOrExport = searchOrExport;
    }

    public Map<String, String> getEqTypeMap() {
        return this.eqTypeMap;
    }

    public Map<String, Map<String, String>> getEqReasonMap() {
        return this.eqReasonMap;
    }

    public String getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public List<JobTitle> getJobtitles() {
        return this.jobtitles;
    }

    public void setJobtitles(List<JobTitle> jobtitles) {
        this.jobtitles = jobtitles;
    }

    public List<Department> getDepartments() {
        return this.departments;
    }

    public List<Emptype> getEmptypes() {
        return this.emptypes;
    }

    public Employee getQuitemp() {
        return this.quitemp;
    }

    public void setQuitemp(Employee quitemp) {
        this.quitemp = quitemp;
    }

    public String getEmpNo() {
        return this.empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getAddPositionId() {
        return this.addPositionId;
    }

    public void setAddPositionId(String addPositionId) {
        this.addPositionId = addPositionId;
    }

    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.EmpQuit JD-Core Version: 0.5.4
 */