package com.hr.examin.action;

import com.hr.base.BaseAction;
import com.hr.configuration.domain.AttdMachine;
import com.hr.configuration.domain.Department;
import com.hr.examin.bo.interfaces.IAttdSyncRecordBO;
import com.hr.examin.domain.AttdSyncRecord;
import com.hr.io.bo.IIodefBo;
import com.hr.io.domain.Iodef;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.BaseCrit;
import com.hr.util.Pager;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class AttdSyncRecordShowAction extends BaseAction {
    private static final long serialVersionUID = -2323155389025834514L;
    private String emp;
    private String dept;
    private String deptName;
    private String machineNo;
    private Integer sync;
    private List<AttdSyncRecord> attdSyncRecordList;
    private List<Department> departmentList;
    private List<AttdMachine> machineList;
    private String iodefId;
    private Iodef iodef;
    private Pager page;
    private String asrId;
    private String operate;
    private Employee asrEmp;

    public AttdSyncRecordShowAction() {
        this.departmentList = null;
        this.machineList = null;

        this.iodefId = "IExamBasic";
        this.iodef = null;

        this.page = new Pager();
    }

    public String execute() throws Exception {
        getDrillDownList();

        this.attdSyncRecordList = searchattdSyncRecord();

        return "success";
    }

    private List<AttdSyncRecord> searchattdSyncRecord() {
        DetachedCriteria dc = searchAttdSyncRec_DC();
        addCriteria(dc);

        IAttdSyncRecordBO attdSyncRecordBo = (IAttdSyncRecordBO) SpringBeanFactory
                .getBean("attdSyncRecordBO");
        return attdSyncRecordBo.getAttdSyncRecordList(dc, this.page);
    }

    private DetachedCriteria searchAttdSyncRec_DC() {
        DetachedCriteria dc = DetachedCriteria.forClass(AttdSyncRecord.class);
        dc.createAlias(AttdSyncRecord.PROP_ASR_EMP_NO, "emp", 1);
        dc.createAlias(AttdSyncRecord.PROP_ASR_ATTD_MACHINE_NO, "asrAttdMachine", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_DEPT_NO, "empDeptNo", 1);
        return dc;
    }

    private void addCriteria(DetachedCriteria dc) {
        if ((this.sync != null) && (this.sync.intValue() != 2)) {
            dc.add(Restrictions.eq(AttdSyncRecord.PROP_ASR_SYNC, this.sync));
        }
        BaseCrit.addDC(dc, AttdSyncRecord.PROP_ASR_ATTD_MACHINE_NO, AttdMachine.PROP_MAC_ID,
                       new Object[] { new AttdMachine(this.machineNo) });
        BaseCrit.addEmpDC(dc, "emp", this.emp);
        BaseCrit.addDeptDC(dc, "emp." + Employee.PROP_EMP_DEPT_NO,
                           "emp." + Employee.PROP_EMP_PB_NO, null, new Department(this.dept));
    }

    private void getDrillDownList() {
        IIodefBo iodefBo = (IIodefBo) SpringBeanFactory.getBean("iodefbo");
        this.iodef = iodefBo.searchIodefByName(this.iodefId);

        this.departmentList = getDrillDown("Department", new String[0]);
        this.machineList = getDrillDown("AttdMachine", new String[0]);
    }

    public String getIodefId() {
        return this.iodefId;
    }

    public void setIodefId(String iodefId) {
        this.iodefId = iodefId;
    }

    public Iodef getIodef() {
        return this.iodef;
    }

    public void setIodef(Iodef iodef) {
        this.iodef = iodef;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public List<AttdSyncRecord> getAttdSyncRecordList() {
        return this.attdSyncRecordList;
    }

    public void setAttdSyncRecordList(List<AttdSyncRecord> attdSyncRecordList) {
        this.attdSyncRecordList = attdSyncRecordList;
    }

    public String getEmp() {
        return this.emp;
    }

    public void setEmp(String emp) {
        this.emp = emp;
    }

    public Employee getAsrEmp() {
        return this.asrEmp;
    }

    public void setAsrEmp(Employee asrEmp) {
        this.asrEmp = asrEmp;
    }

    public List<Department> getDepartmentList() {
        return this.departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public List<AttdMachine> getMachineList() {
        return this.machineList;
    }

    public void setMachineList(List<AttdMachine> machineList) {
        this.machineList = machineList;
    }

    public String getDept() {
        return this.dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getMachineNo() {
        return this.machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public Integer getSync() {
        return this.sync;
    }

    public void setSync(Integer sync) {
        this.sync = sync;
    }

    public String getOperate() {
        return this.operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getAsrId() {
        return this.asrId;
    }

    public void setAsrId(String asrId) {
        this.asrId = asrId;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.action.AttdSyncRecordShowAction JD-Core Version: 0.5.4
 */