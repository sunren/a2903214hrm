package com.hr.profile.action;

import com.hr.base.BaseDownloadAction;
import com.hr.base.DWRUtil;
import com.hr.base.FileOperate;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.JobTitle;
import com.hr.profile.bo.IEmpEvalBo;
import com.hr.profile.domain.Empeval;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.PositionBase;
import com.hr.profile.domain.SimpleEmployee;
import com.hr.util.BaseCrit;
import com.hr.util.DateUtil;
import com.hr.util.Pager;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;

public class EmpEval extends BaseDownloadAction {
    private static final long serialVersionUID = 1L;
    private String eePbNo;
    private String eeType;
    private Date eeStartDate;
    private Date eeEndDate;
    private Date eeStartDateTo;
    private Date eeEndDateTo;
    private String eeRate;
    private String eeRateSelect;
    private String eeComments;
    private String fileFileName;
    private File file;
    private String departmentId;
    private String departmentName;
    private String eeDepartmentName;
    private String eeDepartmentId;
    private String eeManager;
    private String employeeId;
    private String empNo;
    private String updateEeId;
    private List<Department> deptList;
    private List<SimpleEmployee> managerList;
    private List<Empeval> evalList;
    private List<JobTitle> empJobTitles;
    private static Date[] sdateFrom = new Date[8];
    private static Date[] sdateTo = new Date[8];
    private Pager page;
    private static String today;
    private String outmatchModelId;
    private String outputIoName;
    private String searchOrExport;
    private Integer eeTypeDetail;

    public EmpEval() {
        this.page = new Pager();

        this.outputIoName = "OEmpEval";
        this.searchOrExport = null;
    }

    private void setDateArray() {
        Calendar gc = Calendar.getInstance();

        gc.add(1, -1);
        sdateFrom[0] = DateUtil.getFirstDayInYear(gc).getTime();
        sdateTo[0] = DateUtil.getLastDayInYear(gc).getTime();
        gc.add(1, 1);

        gc.add(2, -6);
        sdateFrom[2] = DateUtil.getFirstDayInHalfYear(gc).getTime();
        sdateTo[2] = DateUtil.getLastDayInHalfYear(gc).getTime();
        gc.add(2, 6);

        gc.add(2, -3);
        sdateFrom[4] = DateUtil.getFirstDayInQuartz(gc).getTime();
        sdateTo[4] = DateUtil.getLastDayInQuartz(gc).getTime();
        gc.add(2, 3);

        gc.add(2, -1);
        sdateFrom[6] = DateUtil.getFirstDayInMonth(gc).getTime();
        sdateTo[6] = DateUtil.getLastDayInMonth(gc).getTime();
        gc.add(2, 1);

        sdateFrom[1] = DateUtil.getFirstDayInYear(gc).getTime();
        sdateTo[1] = DateUtil.getLastDayInYear(gc).getTime();
        sdateFrom[3] = DateUtil.getFirstDayInHalfYear(gc).getTime();
        sdateTo[3] = DateUtil.getLastDayInHalfYear(gc).getTime();
        sdateFrom[5] = DateUtil.getFirstDayInQuartz(gc).getTime();
        sdateTo[5] = DateUtil.getLastDayInQuartz(gc).getTime();
        sdateFrom[7] = DateUtil.getFirstDayInMonth(gc).getTime();
        sdateTo[7] = DateUtil.getLastDayInMonth(gc).getTime();
    }

    public Integer getEeTypeDetail() {
        return this.eeTypeDetail;
    }

    public void setEeTypeDetail(Integer eeTypeDetail) {
        this.eeTypeDetail = eeTypeDetail;
    }

    public String list() throws Exception {
        getDrillDownList();

        this.evalList = searchEval();

        if ("export".equals(this.searchOrExport)) {
            return downloadToExcel(this.evalList, this.outputIoName, this.outmatchModelId,
                                   "employeeByEeEmpNo");
        }

        return "success";
    }

    private List<Empeval> searchEval() {
        DetachedCriteria dcEval = searchEval_DC();

        addCriteria(dcEval);

        IEmpEvalBo empEvalBo = (IEmpEvalBo) getBean("empEvalBo");
        return empEvalBo.findEval(dcEval, this.page, this.searchOrExport);
    }

    private DetachedCriteria searchEval_DC() {
        DetachedCriteria dc = DetachedCriteria.forClass(Empeval.class);

        dc.createAlias(Empeval.PROP_EMPLOYEE_BY_EE_EMP_NO, "emp", 1);
        dc.createAlias(Empeval.PROP_EMPLOYEE_BY_EE_MANAGER, "manager", 1);
        dc.createAlias(Empeval.PROP_DEPARTMENT, "department", 1);
        dc.createAlias(Empeval.PROP_EE_PB_NO, "eePbNo", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_DEPT_NO, "emp.empDeptNo", 1);

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

        addOrders(dc, this.page, new String[] { Empeval.PROP_EE_START_DATE + "-down",
                "emp." + Employee.PROP_EMP_DISTINCT_NO + "-up" });

        BaseCrit.addEmpDC(dc, "emp", this.employeeId);
        BaseCrit.addDC(dc, Empeval.PROP_EE_TYPE, "eq", new String[] { this.eeType });
        BaseCrit.addDCDateRange(dc, Empeval.PROP_EE_START_DATE, this.eeStartDate,
                                this.eeStartDateTo);

        BaseCrit.addDeptDC(dc, "emp." + Employee.PROP_EMP_DEPT_NO,
                           "emp." + Employee.PROP_EMP_PB_NO, null,
                           new Department(this.departmentId));
        BaseCrit.addDeptDC(dc, Empeval.PROP_DEPARTMENT, Empeval.PROP_EE_PB_NO, null,
                           new Department(this.eeDepartmentId));
        BaseCrit.addDCDateRange(dc, Empeval.PROP_EE_END_DATE, this.eeEndDate, this.eeEndDateTo);

        BaseCrit.addDC(dc, Empeval.PROP_EE_RATE, "eq", new String[] { this.eeRateSelect });
        BaseCrit.addEmpDC(dc, "manager", this.eeManager);
        BaseCrit.addDC(dc, Empeval.PROP_EE_COMMENTS, "like", new String[] { this.eeComments });
    }

    private void getDrillDownList() {
        this.deptList = getDrillDown("Department", new String[0]);
        this.empJobTitles = getDrillDown("Jobtitle", new String[0]);
    }

    public String addEval() throws Exception {
        if (StringUtils.isEmpty(this.eeType)) {
            return "error";
        }
        String currentEmpNo = getCurrentEmpNo();
        if (StringUtils.isEmpty(this.employeeId)) {
            this.employeeId = currentEmpNo;
        }

        if (("SUB".equals(this.authorityCondition)) && (!checkAuth(this.employeeId))) {
            addErrorInfo("您没有新增权限执行本操作＄1�7");
            return "success";
        }

        Calendar date = Calendar.getInstance();
        String tmpToday = "" + date.get(1) + date.get(2) + date.get(5);
        if (!tmpToday.equals(today)) {
            setDateArray();
            today = tmpToday;
        }
        IEmpEvalBo empEvalBo = (IEmpEvalBo) getBean("empEvalBo");

        if ((this.eeType.equals("试用朄1�7"))
                && (empEvalBo.hasPprobationaryPeriodEvalRecord(this.employeeId))) {
            addErrorInfo("试用期�1�7�评只允许添加一次！");
            return "error";
        }

        this.eeType = this.eeType.trim();
        if ((!this.eeType.equals("项目")) && (!this.eeType.equals("试用朄1�7"))) {
            this.eeStartDate = sdateFrom[this.eeTypeDetail.intValue()];
            this.eeEndDate = sdateTo[this.eeTypeDetail.intValue()];
        }

        Empeval empeval = new Empeval();
        if ((this.fileFileName != null) && (!"".equals(this.fileFileName))) {
            try {
                String pathConfig = "sys.profile.file.path";
                String typeConfig = "sys.profile.file.type";
                String lengthConfig = "sys.profile.file.length";

                String postfix = this.fileFileName.substring(this.fileFileName.lastIndexOf("."));
                this.fileFileName = (UUID.randomUUID() + postfix);
                String UploadResult = FileOperate.buildFile(this.file, pathConfig,
                                                            this.fileFileName, typeConfig,
                                                            lengthConfig);
                if ("property".equals(UploadResult)) {
                    addActionError("文件的上传错误！");
                    return "error";
                }
                if ("fileExtendNameError".equals(UploadResult)) {
                    addActionError("文件的后缄1�7名不合法＄1�7");
                    return "error";
                }
                empeval.setEeAttachment(this.fileFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        empeval.setDepartment(new Department(this.departmentId));
        empeval.setEeComments(this.eeComments);
        empeval.setEeEndDate(this.eeEndDate);
        empeval.setEePbNo(new PositionBase(this.eePbNo));
        empeval.setEeRate(this.eeRate);
        empeval.setEeStartDate(this.eeStartDate);
        empeval.setEeType(this.eeType);
        empeval.setEmployeeByEeEmpNo(new Employee(this.employeeId));
        empeval.setEmployeeByEeManager(new Employee(this.eeManager));
        empeval.setEeCreateBy(currentEmpNo);
        empeval.setEeCreateDate(new Date());
        empeval.setEeLastChangeBy(currentEmpNo);
        empeval.setEeLastChangeTime(new Date());

        empEvalBo.insert(empeval);
        addActionMessage("新增考评信息成功〄1�7");
        return "success";
    }

    public String deleteEval(String eeId) {
        String auth = DWRUtil.checkAuth("EmpEval", "deleteEval");
        if ("error".equalsIgnoreCase(auth)) {
            return "noauth";
        }
        IEmpEvalBo empConstractBo = (IEmpEvalBo) getBean("empEvalBo");
        Empeval ee = empConstractBo.getById(eeId);
        if (ee == null) {
            return "noId";
        }

        if (("SUB".equals(auth)) && (!checkAuth(ee.getEmployeeByEeEmpNo().getId())))
            return "noauth";

        String fileName = ee.getEeAttachment();
        if ((fileName != null) && (!"".equals(fileName))) {
            FileOperate.deleteFile("sys.profile.file.path", fileName);
        }
        empConstractBo.delete(eeId);

        return "success";
    }

    public String updateEval() throws Exception {
        String currentEmpNo = getCurrentEmpNo();

        if ((this.employeeId == null) || ("".equals(this.employeeId))) {
            this.employeeId = currentEmpNo;
        }

        if (("SUB".equals(this.authorityCondition)) && (!checkAuth(this.employeeId))) {
            addActionError("您没有修改权限执行本操作＄1�7");
            return "error";
        }

        Empeval empeval = new Empeval();
        if ((this.fileFileName != null) && (!"".equals(this.fileFileName))) {
            try {
                String pathConfig = "sys.profile.file.path";
                String typeConfig = "sys.profile.file.type";
                String lengthConfig = "sys.profile.file.length";

                String postfix = this.fileFileName.substring(this.fileFileName.lastIndexOf("."));
                this.fileFileName = (UUID.randomUUID() + postfix);
                String UploadResult = FileOperate.buildFile(this.file, pathConfig,
                                                            this.fileFileName, typeConfig,
                                                            lengthConfig);
                if ("property".equals(UploadResult)) {
                    addActionError("文件的上传错误！");
                    return "error";
                }
                if ("fileExtendNameError".equals(UploadResult)) {
                    addActionError("文件的后缄1�7名不合法＄1�7");
                    return "error";
                }
                empeval.setEeAttachment(this.fileFileName);
                this.fileFileName = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        empeval.setEeComments(this.eeComments);
        empeval.setEeRate(this.eeRate);
        empeval.setEmployeeByEeManager(new Employee(this.eeManager));
        empeval.setEeLastChangeBy(currentEmpNo);
        empeval.setEeLastChangeTime(new Date());

        IEmpEvalBo empConstractBo = (IEmpEvalBo) getBean("empEvalBo");
        empConstractBo.update(empeval, this.updateEeId);
        addActionMessage("更新考评信息成功〄1�7");
        this.eePbNo = null;
        this.eeRate = null;
        this.eeComments = null;
        this.eeManager = null;
        this.updateEeId = null;
        this.employeeId = null;

        return "success";
    }

    public String attachDelete() throws Exception {
        if ((this.updateEeId == null) || (this.updateEeId.equals(""))
                || (this.fileFileName == null) || ("".equals(this.fileFileName))) {
            addActionError("参数传�1�7�错误！");
            return "error";
        }

        IEmpEvalBo empConstractBo = (IEmpEvalBo) getBean("empEvalBo");
        Empeval ee = empConstractBo.getById(this.updateEeId);
        if (ee == null) {
            addActionError("参数传�1�7�错误！");
            return "error";
        }

        if (("SUB".equals(this.authorityCondition))
                && (!checkAuth(ee.getEmployeeByEeEmpNo().getId()))) {
            addActionError("您没有删除权限执行本操作＄1�7");
            return "error";
        }

        if (!empConstractBo.deleteAttach(this.updateEeId, this.fileFileName)) {
            addActionError("附件删除失败＄1�7");
            return "error";
        }
        addActionMessage("附件删除成功〄1�7");
        return "success";
    }

    public String getEePbNo() {
        return this.eePbNo;
    }

    public void setEePbNo(String eePbNo) {
        this.eePbNo = eePbNo;
    }

    public String getEeType() {
        return this.eeType;
    }

    public void setEeType(String eeType) {
        this.eeType = eeType;
    }

    public Date getEeStartDate() {
        return this.eeStartDate;
    }

    public void setEeStartDate(Date eeStartDate) {
        this.eeStartDate = eeStartDate;
    }

    public Date getEeEndDate() {
        return this.eeEndDate;
    }

    public void setEeEndDate(Date eeEndDate) {
        this.eeEndDate = eeEndDate;
    }

    public String getEeRate() {
        return this.eeRate;
    }

    public void setEeRate(String eeRate) {
        this.eeRate = eeRate;
    }

    public String getEeComments() {
        return this.eeComments;
    }

    public void setEeComments(String eeComments) {
        this.eeComments = eeComments;
    }

    public String getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getEeManager() {
        return this.eeManager;
    }

    public void setEeManager(String eeManager) {
        this.eeManager = eeManager;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmpNo() {
        return this.empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getUpdateEeId() {
        return this.updateEeId;
    }

    public void setUpdateEeId(String updateEeId) {
        this.updateEeId = updateEeId;
    }

    public String getFileFileName() {
        return this.fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public List<Department> getDeptList() {
        return this.deptList;
    }

    public void setDeptList(List<Department> deptList) {
        this.deptList = deptList;
    }

    public String getEeDepartmentId() {
        return this.eeDepartmentId;
    }

    public void setEeDepartmentId(String eeDepartmentId) {
        this.eeDepartmentId = eeDepartmentId;
    }

    public List<SimpleEmployee> getManagerList() {
        return this.managerList;
    }

    public void setManagerList(List<SimpleEmployee> managerList) {
        this.managerList = managerList;
    }

    public List<Empeval> getEvalList() {
        return this.evalList;
    }

    public void setEvalList(List<Empeval> evalList) {
        this.evalList = evalList;
    }

    public Date getEeStartDateTo() {
        return this.eeStartDateTo;
    }

    public void setEeStartDateTo(Date eeStartDateTo) {
        this.eeStartDateTo = eeStartDateTo;
    }

    public Date getEeEndDateTo() {
        return this.eeEndDateTo;
    }

    public void setEeEndDateTo(Date eeEndDateTo) {
        this.eeEndDateTo = eeEndDateTo;
    }

    public String getEeRateSelect() {
        return this.eeRateSelect;
    }

    public void setEeRateSelect(String eeRateSelect) {
        this.eeRateSelect = eeRateSelect;
    }

    public List<JobTitle> getEmpJobTitles() {
        return this.empJobTitles;
    }

    public void setEmpJobTitles(List<JobTitle> empJobTitles) {
        this.empJobTitles = empJobTitles;
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

    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getEeDepartmentName() {
        return this.eeDepartmentName;
    }

    public void setEeDepartmentName(String eeDepartmentName) {
        this.eeDepartmentName = eeDepartmentName;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.EmpEval JD-Core Version: 0.5.4
 */