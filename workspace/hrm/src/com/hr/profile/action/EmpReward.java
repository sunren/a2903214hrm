package com.hr.profile.action;

import com.hr.base.BaseDownloadAction;
import com.hr.base.ComonBeans;
import com.hr.base.DWRUtil;
import com.hr.configuration.domain.Department;
import com.hr.profile.bo.IEmpRewardBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Empreward;
import com.hr.profile.domain.PositionBase;
import com.hr.util.BaseCrit;
import com.hr.util.Pager;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;

public class EmpReward extends BaseDownloadAction {
    private static final long serialVersionUID = 1L;
    private String erPbNo;
    private String erType;
    private Map<String, String> erTypeMap;
    private String erForm;
    private Map<String, Map<String, String>> erFormMap;
    private String erContent;
    private Date erExeDate;
    private Date erExeDateTo;
    private String deptId;
    private String deptName;
    private String departmentName;
    private String departmentId;
    private String employeeId;
    private String erPbId;
    private String empNo;
    private String updateErId;
    private List<Department> deptList;
    private List<Empreward> rewardList;
    private Pager page;
    private List<PositionBase> empPbList;
    private String outmatchModelId;
    private String outputIoName;
    private String searchOrExport;

    public EmpReward() {
        this.erTypeMap = ComonBeans.getValuesToMap(Empreward.PROP_ER_TYPE, new boolean[] { true });

        this.erFormMap = ComonBeans.getSecondLevelSelect(this.erTypeMap, Empreward.PROP_ER_FORM,
                                                         new boolean[] { true });

        this.page = new Pager();

        this.outputIoName = "OEmpReward";

        this.searchOrExport = null;
    }

    public String list() throws Exception {
        this.rewardList = searchRewardWithEmp();

        if ("export".equals(this.searchOrExport)) {
            return downloadToExcel(this.rewardList, this.outputIoName, this.outmatchModelId,
                                   "employee");
        }

        return "success";
    }

    private List<Empreward> searchRewardWithEmp() {
        DetachedCriteria dcReward = searchRewardWithEmp_DC();

        addCriteria(dcReward);

        IEmpRewardBo empRewardBo = (IEmpRewardBo) getBean("empRewardBo");
        return empRewardBo.findReward(dcReward, this.page, this.searchOrExport);
    }

    private DetachedCriteria searchRewardWithEmp_DC() {
        DetachedCriteria dc = DetachedCriteria.forClass(Empreward.class);

        dc.createAlias(Empreward.PROP_EMPLOYEE, "emp", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_DEPT_NO, "emp.empDeptNo", 1);
        dc.createAlias(Empreward.PROP_DEPARTMENT, "department", 1);
        dc.createAlias(Empreward.PROP_ER_PB_NO, "erPbNo", 1);

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

        addOrders(dc, this.page, new String[] { Empreward.PROP_ER_EXE_DATE + "-down",
                "emp." + Employee.PROP_EMP_DISTINCT_NO + "-up" });

        BaseCrit.addEmpDC(dc, "emp", this.employeeId);
        BaseCrit.addDeptDC(dc, Empreward.PROP_DEPARTMENT, Empreward.PROP_ER_PB_NO, null,
                           new Department(this.departmentId));
        BaseCrit.addDC(dc, Empreward.PROP_ER_TYPE, "eq", new String[] { this.erType });
        BaseCrit.addDC(dc, Empreward.PROP_ER_FORM, "eq", new String[] { this.erForm });

        BaseCrit.addDeptDC(dc, "emp." + Employee.PROP_EMP_DEPT_NO,
                           "emp." + Employee.PROP_EMP_PB_NO, null, new Department(this.deptId));
        BaseCrit.addDC(dc, Empreward.PROP_ER_CONTENT, "like", new String[] { this.erContent });
        BaseCrit.addDCDateRange(dc, Empreward.PROP_ER_EXE_DATE, this.erExeDate, this.erExeDateTo);
    }

    public String addReward() throws Exception {
        String currentEmpNo = getCurrentEmpNo();
        if ((StringUtils.isEmpty(this.erType)) || (StringUtils.isEmpty(this.erForm))
                || (!this.erTypeMap.containsKey(this.erType))) {
            addActionError("非法参数");
            return "error";
        }
        if ((this.employeeId == null) || ("".equals(this.employeeId))) {
            this.employeeId = currentEmpNo;
        }

        if (("SUB".equals(this.authorityCondition)) && (!checkAuth(this.employeeId))) {
            addErrorInfo("您没有新增权限执行本操作＄1�7");
            return "success";
        }

        Empreward empreward = new Empreward();

        if (StringUtils.isNotEmpty(this.erPbNo)) {
            empreward.setErPbNo(new PositionBase(this.erPbNo));
        }

        empreward.setDepartment(new Department(this.departmentId));
        empreward.setEmployee(new Employee(this.employeeId));
        empreward.setErPbNo(new PositionBase(this.erPbId));
        empreward.setErContent(this.erContent);
        empreward.setErExeDate(this.erExeDate);
        empreward.setErForm(this.erForm);
        empreward.setErType(this.erType);

        empreward.setErCreateBy(currentEmpNo);
        empreward.setErCreateDate(new Date());
        empreward.setErLastChangeBy(currentEmpNo);
        empreward.setErLastChangeTime(new Date());

        IEmpRewardBo empRewardBo = (IEmpRewardBo) getBean("empRewardBo");
        empRewardBo.insert(empreward);
        addActionMessage("新增员工奖惩信息成功〄1�7");
        return "success";
    }

    public String deleteReward(String etfId) {
        String auth = DWRUtil.checkAuth("EmpReward", "deleteReward");
        if ("error".equalsIgnoreCase(auth)) {
            return "noauth";
        }
        IEmpRewardBo empRewardBo = (IEmpRewardBo) getBean("empRewardBo");
        Empreward etf = empRewardBo.getById(etfId);
        if (etf == null) {
            return "noId";
        }

        if (("SUB".equals(auth)) && (!checkAuth(etf.getEmployee().getId())))
            return "noauth";

        empRewardBo.delete(etfId);
        return "success";
    }

    public String updateReward() throws Exception {
        String currentEmpNo = getCurrentEmpNo();
        if ((StringUtils.isEmpty(this.erType)) || (StringUtils.isEmpty(this.erForm))
                || (!this.erTypeMap.containsKey(this.erType))) {
            addActionError("非法参数");
            return "error";
        }
        if ((this.employeeId == null) || ("".equals(this.employeeId))) {
            this.employeeId = currentEmpNo;
        }
        if ((this.updateErId == null) || ("".equals(this.updateErId))) {
            return "noId";
        }

        if (("SUB".equals(this.authorityCondition)) && (!checkAuth(this.employeeId))) {
            addErrorInfo("您没有修改权限执行本操作＄1�7");
            return "success";
        }

        Empreward empreward = new Empreward();

        empreward.setErContent(this.erContent);
        empreward.setErExeDate(this.erExeDate);
        empreward.setErType(this.erType);
        empreward.setErForm(this.erForm);
        empreward.setErLastChangeBy(currentEmpNo);
        empreward.setErLastChangeTime(new Date());

        IEmpRewardBo empRewardBo = (IEmpRewardBo) getBean("empRewardBo");
        empRewardBo.update(empreward, this.updateErId);
        addActionMessage("更新员工奖惩信息成功〄1�7");
        this.employeeId = null;
        this.erContent = null;
        this.erExeDate = null;
        this.erType = null;
        this.erForm = null;
        this.departmentId = null;

        return "success";
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getErPbNo() {
        return this.erPbNo;
    }

    public void setErPbNo(String erPbNo) {
        this.erPbNo = erPbNo;
    }

    public String getErType() {
        return this.erType;
    }

    public void setErType(String erType) {
        this.erType = erType;
    }

    public String getErContent() {
        return this.erContent;
    }

    public void setErContent(String erContent) {
        this.erContent = erContent;
    }

    public Date getErExeDate() {
        return this.erExeDate;
    }

    public void setErExeDate(Date erExeDate) {
        this.erExeDate = erExeDate;
    }

    public Date getErExeDateTo() {
        return this.erExeDateTo;
    }

    public void setErExeDateTo(Date erExeDateTo) {
        this.erExeDateTo = erExeDateTo;
    }

    public String getDeptId() {
        return this.deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getUpdateErId() {
        return this.updateErId;
    }

    public void setUpdateErId(String updateErId) {
        this.updateErId = updateErId;
    }

    public List<Department> getDeptList() {
        return this.deptList;
    }

    public void setDeptList(List<Department> deptList) {
        this.deptList = deptList;
    }

    public List<Empreward> getRewardList() {
        return this.rewardList;
    }

    public void setRewardList(List<Empreward> rewardList) {
        this.rewardList = rewardList;
    }

    public String getErForm() {
        return this.erForm;
    }

    public void setErForm(String erForm) {
        this.erForm = erForm;
    }

    public List<PositionBase> getEmpPbList() {
        return this.empPbList;
    }

    public void setEmpPbList(List<PositionBase> empPbList) {
        this.empPbList = empPbList;
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

    public Map<String, String> getErTypeMap() {
        return this.erTypeMap;
    }

    public Map<String, Map<String, String>> getErFormMap() {
        return this.erFormMap;
    }

    public String getErPbId() {
        return this.erPbId;
    }

    public void setErPbId(String erPbId) {
        this.erPbId = erPbId;
    }

    public String getEmpNo() {
        return this.empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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
 * com.hr.profile.action.EmpReward JD-Core Version: 0.5.4
 */