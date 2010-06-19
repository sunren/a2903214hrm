package com.hr.profile.action;

import com.hr.base.BaseDownloadAction;
import com.hr.base.FileOperate;
import com.hr.configuration.domain.ContractType;
import com.hr.configuration.domain.Department;
import com.hr.profile.bo.IEmpContractBo;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Empcontract;
import com.hr.profile.domain.Employee;
import com.hr.util.BaseCrit;
import com.hr.util.Pager;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;

public class EmpContract extends BaseDownloadAction {
    private static final long serialVersionUID = 1L;
    private String ectNo;
    private Date ectStartDate;
    private Date ectEndDate;
    private Date ectStartDateTo;
    private Date ectEndDateTo;
    private Integer etcExpire;
    private String fileFileName;
    private File file;
    private String departmentId;
    private String departmentName;
    private String ectComments;
    private String ectStatus;
    private String empTypeId;
    private String employeeId;
    private String empNo;
    private String input;
    private Pager page;
    private String updateEctId;
    private String continueEctId;
    private List<Empcontract> contractList;
    private List<ContractType> empTypeList;
    private List<Department> deptList;
    private Empcontract searchContract;
    private String outmatchModelId;
    private String outputIoName;
    private String searchOrExport;

    public EmpContract() {
        this.page = new Pager();

        this.outputIoName = "OEmpContract";
        this.searchOrExport = null;
    }

    public String list() throws Exception {
        getDrillDownList();

        this.contractList = searchContract();

        if ("export".equals(this.searchOrExport)) {
            return downloadToExcel(this.contractList, this.outputIoName, this.outmatchModelId,
                                   "employee");
        }

        return "success";
    }

    private List<Empcontract> searchContract() {
        DetachedCriteria dcCon = searchConWithEmp_DC();

        addCriteria(dcCon);

        IEmpContractBo empContractBo = (IEmpContractBo) getBean("empContractBo");
        return empContractBo.findContract(dcCon, this.page, this.searchOrExport);
    }

    private DetachedCriteria searchConWithEmp_DC() {
        DetachedCriteria dc = DetachedCriteria.forClass(Empcontract.class);

        dc.setFetchMode(Empcontract.PROP_EMPLOYEE, FetchMode.JOIN);
        dc.createAlias(Empcontract.PROP_EMPLOYEE, "emp", 1);
        dc.createAlias(Empcontract.PROP_CONTRACT_TYPE, "contractType", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_DEPT_NO, "empDept", 1);

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

        addOrders(dc, this.page, new String[] { Empcontract.PROP_ECT_START_DATE + "-down",
                "emp.empDistinctNo-up" });

        BaseCrit.addEmpDC(dc, "emp", this.employeeId);
        BaseCrit.addDC(dc, Empcontract.PROP_CONTRACT_TYPE, "id", new Object[] { new ContractType(
                this.empTypeId) });
        BaseCrit.addDCDateRange(dc, Empcontract.PROP_ECT_START_DATE, this.ectStartDate,
                                this.ectStartDateTo);

        BaseCrit.addDeptDC(dc, "emp." + Employee.PROP_EMP_DEPT_NO,
                           "emp." + Employee.PROP_EMP_PB_NO, null,
                           new Department(this.departmentId));
        BaseCrit.addDC(dc, Empcontract.PROP_ETC_EXPIRE, "eq", new Integer[] { this.etcExpire });
        BaseCrit.addDCDateRange(dc, Empcontract.PROP_ECT_END_DATE, this.ectEndDate,
                                this.ectEndDateTo);

        BaseCrit.addDC(dc, Empcontract.PROP_ECT_NO, "like", new String[] { this.ectNo });
        BaseCrit.addDC(dc, Empcontract.PROP_ECT_STATUS, "eq", new String[] { this.ectStatus });
        BaseCrit
                .addDC(dc, Empcontract.PROP_ECT_COMMENTS, "like", new String[] { this.ectComments });
    }

    private void getDrillDownList() {
        this.deptList = getDrillDown("Department", new String[0]);
        this.empTypeList = getDrillDown("ContractType", new String[0]);
    }

    public String addContract() throws Exception {
        if (StringUtils.isEmpty(this.employeeId)) {
            addErrorInfo("错误的请求参数！");
            return "error";
        }

        if (("SUB".equals(this.authorityCondition)) && (!checkAuth(this.employeeId))) {
            addActionError("您没有新增权限执行本操作＄1�7");
            return "error";
        }

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        String[] fetch = { "contract" };
        Employee employee = empBo.loadEmp(this.employeeId, fetch);

        if ((employee.getContract() != null)
                && (StringUtils.isNotEmpty(employee.getContract().getEctId()))) {
            return continueContract();
        }

        IEmpContractBo empContractBo = (IEmpContractBo) getBean("empContractBo");
        Empcontract empcontract = new Empcontract();
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
                empcontract.setEctAttatchment(this.fileFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String currentEmpNo = getCurrentEmpNo();
        empcontract.setEctNo(this.ectNo);
        empcontract.setEctStartDate(this.ectStartDate);
        empcontract.setEctEndDate(this.ectEndDate);
        empcontract.setEtcExpire(this.etcExpire);
        empcontract.setEctComments(this.ectComments);
        empcontract.setEctStatus(this.ectStatus);
        empcontract.setEmployee(new Employee(this.employeeId));
        empcontract.setContractType(new ContractType(this.empTypeId));
        empcontract.setEctCreateBy(currentEmpNo);
        empcontract.setEctCreateDate(new Date());
        empcontract.setEctLastChangeBy(currentEmpNo);
        empcontract.setEctLastChangeTime(new Date());

        String error = empContractBo.insert(empcontract);
        if (StringUtils.isNotEmpty(error)) {
            addActionError(error);
            return "input";
        }
        employee.setContract(empcontract);
        empContractBo.updateObj(employee);
        addActionMessage("新增员工合同成功〄1�7");
        return "success";
    }

    public String continueContract() throws Exception {
        if (StringUtils.isEmpty(this.employeeId)) {
            addErrorInfo("错误的请求参数！");
            return "error";
        }

        if (("SUB".equals(this.authorityCondition)) && (!checkAuth(this.employeeId))) {
            addActionError("您没有新增权限执行本操作＄1�7");
            return "error";
        }

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        String[] fetch = { "contract" };
        Employee employee = empBo.loadEmp(this.employeeId, fetch);
        if ((employee.getContract() == null)
                || (StringUtils.isEmpty(employee.getContract().getEctId()))) {
            addErrorInfo("员工无合同，无法进行续签操作＄1�7");
            return "error";
        }

        Empcontract empcontract = new Empcontract();
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
                empcontract.setEctAttatchment(this.fileFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String currentEmpNo = getCurrentEmpNo();
        empcontract.setEctNo(this.ectNo);
        empcontract.setEctStartDate(this.ectStartDate);
        empcontract.setEctEndDate(this.ectEndDate);
        empcontract.setEtcExpire(this.etcExpire);
        empcontract.setEctComments(this.ectComments);
        empcontract.setEctStatus(this.ectStatus);
        empcontract.setEmployee(new Employee(this.employeeId));
        empcontract.setContractType(new ContractType(this.empTypeId));
        empcontract.setEctCreateBy(currentEmpNo);
        empcontract.setEctCreateDate(new Date());
        empcontract.setEctLastChangeBy(currentEmpNo);
        empcontract.setEctLastChangeTime(new Date());

        IEmpContractBo empContractBo = (IEmpContractBo) getBean("empContractBo");
        String error = empContractBo.insert(empcontract);
        if (StringUtils.isNotEmpty(error)) {
            addActionError(error);
            return "input";
        }
        employee.setContract(empcontract);
        empContractBo.updateObj(employee);
        addActionMessage("续签员工合同成功〄1�7");
        return "success";
    }

    public String deleteContract() {
        if (StringUtils.isEmpty(this.updateEctId)) {
            addErrorInfo("请�1�7�择要删除的合同!");
            return "success";
        }
        IEmpContractBo empContractBo = (IEmpContractBo) getBean("empContractBo");
        Empcontract ect = empContractBo.getById(this.updateEctId);
        if (ect == null) {
            addErrorInfo("合同不存在或已经被删附1�7!");
            return "success";
        }

        if (("SUB".equals(this.authorityCondition)) && (!checkAuth(ect.getEmployee().getId()))) {
            addErrorInfo("您没有删除权限执行本操作＄1�7");
            return "success";
        }

        String fileName = ect.getEctAttatchment();
        if ((fileName != null) && (!"".equals(fileName))) {
            FileOperate.deleteFile("sys.profile.file.path", fileName);
        }
        empContractBo.delete(this.updateEctId);
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        Employee emp = empBo.loadEmp(ect.getEmployee().getId(), null);
        List oldList = empContractBo.searchByEmpNo(emp.getId());
        if ((oldList != null) && (oldList.size() > 0))
            emp.setContract((Empcontract) oldList.get(0));
        else {
            emp.setContract(null);
        }
        empContractBo.updateObj(emp);
        addSuccessInfo("删除员工合同成功〄1�7");
        return "success";
    }

    public String updateContract() throws Exception {
        if (StringUtils.isEmpty(this.employeeId)) {
            return "error";
        }

        if (("SUB".equals(this.authorityCondition)) && (!checkAuth(this.employeeId))) {
            addActionError("您没有修改权限执行本操作＄1�7");
            return "error";
        }

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        Employee temp = empBo.loadEmp(this.employeeId, null);
        if ((temp.getContract() == null) || (temp.getContract().getEctId() == null)) {
            return "error";
        }

        String currentEmpNo = getCurrentEmpNo();
        IEmpContractBo empContractBo = (IEmpContractBo) getBean("empContractBo");
        Empcontract empcontract = new Empcontract();
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
                empcontract.setEctAttatchment(this.fileFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        empcontract.setEctNo(this.ectNo);
        empcontract.setEctStartDate(this.ectStartDate);
        if (this.etcExpire.intValue() != 1) {
            empcontract.setEctEndDate(this.ectEndDate);
        }
        empcontract.setEtcExpire(this.etcExpire);
        empcontract.setEctComments(this.ectComments);
        empcontract.setEctStatus(this.ectStatus);
        empcontract.setEmployee(new Employee(this.employeeId));
        empcontract.setContractType(new ContractType(this.empTypeId));
        empcontract.setEctLastChangeBy(currentEmpNo);
        empcontract.setEctLastChangeTime(new Date());

        String error = empContractBo.update(empcontract, this.updateEctId);
        if (StringUtils.isNotEmpty(error))
            addActionError(error);
        else {
            addActionMessage("更新员工合同成功〄1�7");
        }
        clear();
        return "success";
    }

    public String attachDelete() throws Exception {
        if ((this.updateEctId == null) || (this.updateEctId.equals(""))
                || (this.fileFileName == null) || ("".equals(this.fileFileName))) {
            addActionError("参数传�1�7�错误！");
            return "error";
        }
        IEmpContractBo empContractBo = (IEmpContractBo) getBean("empContractBo");
        Empcontract ect = empContractBo.getById(this.updateEctId);
        if (ect == null) {
            addActionError("参数传�1�7�错误！");
            return "error";
        }

        if (("SUB".equals(this.authorityCondition)) && (!checkAuth(ect.getEmployee().getId()))) {
            addActionError("您没有修改权限执行本操作＄1�7");
            return "error";
        }

        if (!empContractBo.deleteAttach(this.updateEctId, this.fileFileName)) {
            addActionError("附件删除失败＄1�7");
            return "error";
        }
        addActionMessage("附件删除成功〄1�7");
        return "success";
    }

    public String getEctNo() {
        return this.ectNo;
    }

    public void setEctNo(String ectNo) {
        this.ectNo = ectNo;
    }

    public Date getEctStartDate() {
        return this.ectStartDate;
    }

    public void setEctStartDate(Date ectStartDate) {
        this.ectStartDate = ectStartDate;
    }

    public Date getEctEndDate() {
        return this.ectEndDate;
    }

    public void setEctEndDate(Date ectEndDate) {
        this.ectEndDate = ectEndDate;
    }

    public Integer getEtcExpire() {
        return this.etcExpire;
    }

    public void setEtcExpire(Integer etcExpire) {
        this.etcExpire = etcExpire;
    }

    public String getEctComments() {
        return this.ectComments;
    }

    public void setEctComments(String ectComments) {
        this.ectComments = ectComments;
    }

    public String getEctStatus() {
        return this.ectStatus;
    }

    public void setEctStatus(String ectStatus) {
        this.ectStatus = ectStatus;
    }

    public String getEmpTypeId() {
        return this.empTypeId;
    }

    public void setEmpTypeId(String empTypeId) {
        this.empTypeId = empTypeId;
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

    public String getUpdateEctId() {
        return this.updateEctId;
    }

    public void setUpdateEctId(String updateEctId) {
        this.updateEctId = updateEctId;
    }

    public List<Empcontract> getContractList() {
        return this.contractList;
    }

    public void setContractList(List<Empcontract> contractList) {
        this.contractList = contractList;
    }

    public List<ContractType> getEmpTypeList() {
        return this.empTypeList;
    }

    public void setEmpTypeList(List<ContractType> empTypeList) {
        this.empTypeList = empTypeList;
    }

    public String getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public List<Department> getDeptList() {
        return this.deptList;
    }

    public void setDeptList(List<Department> deptList) {
        this.deptList = deptList;
    }

    public Date getEctStartDateTo() {
        return this.ectStartDateTo;
    }

    public void setEctStartDateTo(Date ectStartDateTo) {
        this.ectStartDateTo = ectStartDateTo;
    }

    public Date getEctEndDateTo() {
        return this.ectEndDateTo;
    }

    public void setEctEndDateTo(Date ectEndDateTo) {
        this.ectEndDateTo = ectEndDateTo;
    }

    private void clear() {
        this.ectNo = null;
        this.ectStartDate = null;
        this.ectEndDate = null;
        this.ectStartDateTo = null;
        this.ectEndDateTo = null;
        this.etcExpire = null;
        this.fileFileName = null;
        this.file = null;
        this.ectComments = null;
        this.ectStatus = null;
        this.empTypeId = null;
        this.employeeId = null;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public String getInput() {
        return this.input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Empcontract getSearchContract() {
        return this.searchContract;
    }

    public void setSearchContract(Empcontract searchContract) {
        this.searchContract = searchContract;
    }

    public String getContinueEctId() {
        return this.continueEctId;
    }

    public void setContinueEctId(String continueEctId) {
        this.continueEctId = continueEctId;
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
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.EmpContract JD-Core Version: 0.5.4
 */