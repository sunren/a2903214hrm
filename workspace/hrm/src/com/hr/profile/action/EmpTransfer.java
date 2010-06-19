package com.hr.profile.action;

import com.hr.base.BaseDownloadAction;
import com.hr.base.DWRUtil;
import com.hr.configuration.domain.Department;
import com.hr.profile.bo.IEmpTransferBo;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.bo.IPositionBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Emptransfer;
import com.hr.profile.domain.Position;
import com.hr.util.BaseCrit;
import com.hr.util.Pager;
import java.util.Date;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;

public class EmpTransfer extends BaseDownloadAction {
    private static final long serialVersionUID = 1L;
    private Emptransfer empTransfer;
    private String eftId;
    private String employeeId;
    private Date eftTransferDate;
    private Date eftTransferDateTo;
    private String eftTransferType;
    private String eftReason;
    private String eftComments;
    private String updateEtfId;
    private String eftOldDeptNo;
    private String eftNewDeptNo;
    private String eftOldDeptName;
    private String eftNewDeptName;
    private List<Department> deptList;
    private List<Emptransfer> transferList;
    private String eftNewPosition;
    private Pager page;
    private String outmatchModelId;
    private String outputIoName;
    private String searchOrExport;

    public EmpTransfer() {
        this.page = new Pager();

        this.outputIoName = "OEmpTransfer";
        this.searchOrExport = null;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public String list() throws Exception {
        getDrillDownList();

        this.transferList = searchTransfer();

        if ("export".equals(this.searchOrExport)) {
            return downloadToExcel(this.transferList, this.outputIoName, this.outmatchModelId,
                                   "transfer");
        }

        return "success";
    }

    private List<Emptransfer> searchTransfer() {
        DetachedCriteria dcTransfer = searchTransferWithEmp_DC();

        addCriteria(dcTransfer);

        IEmpTransferBo empTransferBo = (IEmpTransferBo) getBean("empTransferBo");
        return empTransferBo.findTransfer(dcTransfer, this.page, this.searchOrExport);
    }

    private DetachedCriteria searchTransferWithEmp_DC() {
        DetachedCriteria dc = DetachedCriteria.forClass(Emptransfer.class);

        dc.createAlias("employee", "emp", 1);
        dc.createAlias(Emptransfer.PROP_EFT_OLD_DEPT_NO, "oldDepartment", 1);
        dc.createAlias(Emptransfer.PROP_EFT_NEW_DEPT_NO, "newDepartment", 1);
        dc.createAlias(Emptransfer.PROP_EFT_NEW_PB_NO, "oldPbNo", 1);
        dc.createAlias(Emptransfer.PROP_EFT_OLD_PB_NO, "newPbNo", 1);

        if ("export".equals(this.searchOrExport)) {
            dc.setFetchMode("emp." + Employee.PROP_EMP_DEPT_NO, FetchMode.JOIN);
            dc.setFetchMode("emp." + Employee.PROP_EMP_PB_NO, FetchMode.JOIN);
            dc.createAlias("emp." + Employee.PROP_EMP_LOCATION_NO, "location", 1);
            dc.setFetchMode("emp." + Employee.PROP_EMP_TYPE, FetchMode.JOIN);
            dc.setFetchMode("emp." + Employee.PROP_EMP_BENEFIT_TYPE, FetchMode.JOIN);
        }

        return dc;
    }

    public void addCriteria(DetachedCriteria dc) {
        if ("SUB".equals(this.authorityCondition)) {
            addSubDC(dc, "emp", 1);
        }

        addOrders(dc, this.page, new String[] { Emptransfer.PROP_EFT_TRANSFER_DATE + "-down",
                "emp." + Employee.PROP_EMP_DISTINCT_NO + "-up" });

        BaseCrit.addEmpDC(dc, "emp", this.employeeId);
        BaseCrit.addDC(dc, Emptransfer.PROP_EFT_TRANSFER_TYPE, "eq",
                       new String[] { this.eftTransferType });
        BaseCrit.addDeptDC(dc, Emptransfer.PROP_EFT_OLD_DEPT_NO, Emptransfer.PROP_EFT_OLD_PB_NO,
                           null, new Department(this.eftOldDeptNo));
        BaseCrit.addDeptDC(dc, Emptransfer.PROP_EFT_NEW_DEPT_NO, Emptransfer.PROP_EFT_NEW_PB_NO,
                           null, new Department(this.eftNewDeptNo));
        BaseCrit.addDCDateRange(dc, Emptransfer.PROP_EFT_TRANSFER_DATE, this.eftTransferDate,
                                this.eftTransferDateTo);
        BaseCrit.addDC(dc, Emptransfer.PROP_EFT_REASON, "like", new String[] { this.eftReason });
    }

    private void getDrillDownList() {
        this.deptList = getDrillDown("Department", new String[0]);
    }

    public String addTransfer() throws Exception {
        String currentEmpNo = getCurrentEmpNo();
        if ((this.employeeId == null) || ("".equals(this.employeeId))) {
            this.employeeId = currentEmpNo;
        }

        if (("SUB".equals(this.authorityCondition)) && (!checkAuth(this.employeeId))) {
            addErrorInfo("您没有新增权限执行本操作");
            return "success";
        }
        String[] fetch = { Position.PROP_POSITION_PB_ID };
        IPositionBo positionBo = (IPositionBo) getBean("positionBo");
        Position position = positionBo.getPosById(this.eftNewPosition, fetch);
        if ((this.eftTransferType.equals("4"))
                && (((this.eftNewPosition == null) || (this.eftNewPosition.equals(""))))) {
            position = positionBo.getPosByEmpNo(this.employeeId, fetch);
        }

        Emptransfer emptransfer = new Emptransfer();
        emptransfer.setEftTransferDate(this.eftTransferDate);
        emptransfer.setEftTransferType(this.eftTransferType);
        emptransfer.setEftReason(this.eftReason);
        emptransfer.setEftComments(this.eftComments);
        emptransfer.setEmployee(new Employee(this.employeeId));
        emptransfer.setEftOldDeptNo(new Department(this.eftOldDeptNo));
        emptransfer.setEftNewDeptNo(new Department(this.eftNewDeptNo));
        emptransfer.setEftCreateBy(currentEmpNo);
        emptransfer.setEftCreateDate(new Date());
        emptransfer.setEftLastChangeBy(currentEmpNo);
        emptransfer.setEftLastChangeTime(new Date());

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        List result = empBo.batchTransfer(emptransfer, new String[] { this.employeeId },
                                          currentEmpNo, position);
        if ((result != null) && (result.size() > 0)) {
            addActionError((String) result.get(0));
            return "success";
        }
        addActionMessage("新增员工人员变动成功");
        return "success";
    }

    public String deleteTransfer(String etfId) {
        String auth = DWRUtil.checkAuth("EmpTransfer", "deleteTransfer");
        if ("error".equalsIgnoreCase(auth)) {
            return "noauth";
        }

        IEmpTransferBo empConstractBo = (IEmpTransferBo) getBean("empTransferBo");
        Emptransfer etf = empConstractBo.getById(etfId);
        if (etf == null) {
            return "noId";
        }

        if (("SUB".equals(auth)) && (!checkAuth(etf.getEmployee().getId())))
            return "noauth";

        empConstractBo.delete(etfId);
        return "success";
    }

    public String updateTransfer() throws Exception {
        String currentEmpNo = getCurrentEmpNo();
        if ((this.updateEtfId == null) || ("".equals(this.updateEtfId))) {
            return "noId";
        }

        if (("SUB".equals(this.authorityCondition)) && (!checkAuth(this.employeeId))) {
            addErrorInfo("您没有修改权限执行本操作");
            return "success";
        }

        Emptransfer emptransfer = new Emptransfer();

        emptransfer.setEftTransferDate(this.eftTransferDate);
        emptransfer.setEftTransferType(this.eftTransferType);
        emptransfer.setEftReason(this.eftReason);
        emptransfer.setEftComments(this.eftComments);
        emptransfer.setEmployee(new Employee(this.employeeId));
        emptransfer.setEftNewDeptNo(new Department(this.eftNewDeptNo));
        emptransfer.setEftLastChangeBy(currentEmpNo);
        emptransfer.setEftLastChangeTime(new Date());

        IEmpTransferBo empTransferBo = (IEmpTransferBo) getBean("empTransferBo");
        empTransferBo.update(emptransfer, this.updateEtfId);
        clear();
        addActionMessage("员工变动修改成功");
        return "success";
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEftId() {
        return this.eftId;
    }

    public void setEftId(String eftId) {
        this.eftId = eftId;
    }

    public Date getEftTransferDate() {
        return this.eftTransferDate;
    }

    public void setEftTransferDate(Date eftTransferDate) {
        this.eftTransferDate = eftTransferDate;
    }

    public String getEftReason() {
        return this.eftReason;
    }

    public void setEftReason(String eftReason) {
        this.eftReason = eftReason;
    }

    public String getEftComments() {
        return this.eftComments;
    }

    public void setEftComments(String eftComments) {
        this.eftComments = eftComments;
    }

    public String getUpdateEtfId() {
        return this.updateEtfId;
    }

    public void setUpdateEtfId(String updateEtfId) {
        this.updateEtfId = updateEtfId;
    }

    public String getEftOldDeptNo() {
        return this.eftOldDeptNo;
    }

    public void setEftOldDeptNo(String eftOldDeptNo) {
        this.eftOldDeptNo = eftOldDeptNo;
    }

    public String getEftNewDeptNo() {
        return this.eftNewDeptNo;
    }

    public void setEftNewDeptNo(String eftNewDeptNo) {
        this.eftNewDeptNo = eftNewDeptNo;
    }

    public String getEftTransferType() {
        return this.eftTransferType;
    }

    public void setEftTransferType(String eftTransferType) {
        this.eftTransferType = eftTransferType;
    }

    public List<Department> getDeptList() {
        return this.deptList;
    }

    public void setDeptList(List<Department> deptList) {
        this.deptList = deptList;
    }

    public List<Emptransfer> getTransferList() {
        return this.transferList;
    }

    public void setTransferList(List<Emptransfer> transferList) {
        this.transferList = transferList;
    }

    public Date getEftTransferDateTo() {
        return this.eftTransferDateTo;
    }

    public void setEftTransferDateTo(Date eftTransferDateTo) {
        this.eftTransferDateTo = eftTransferDateTo;
    }

    private void clear() {
        this.eftId = null;
        this.eftOldDeptNo = null;
        this.eftNewDeptNo = null;
        this.eftTransferDate = null;
        this.eftTransferType = null;
        this.eftTransferDate = null;
        this.eftReason = null;
        this.eftTransferDateTo = null;
        this.employeeId = null;
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

    public String getEftNewPosition() {
        return this.eftNewPosition;
    }

    public void setEftNewPosition(String eftNewPosition) {
        this.eftNewPosition = eftNewPosition;
    }

    public String getEftOldDeptName() {
        return this.eftOldDeptName;
    }

    public void setEftOldDeptName(String eftOldDeptName) {
        this.eftOldDeptName = eftOldDeptName;
    }

    public String getEftNewDeptName() {
        return this.eftNewDeptName;
    }

    public void setEftNewDeptName(String eftNewDeptName) {
        this.eftNewDeptName = eftNewDeptName;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.EmpTransfer JD-Core Version: 0.5.4
 */