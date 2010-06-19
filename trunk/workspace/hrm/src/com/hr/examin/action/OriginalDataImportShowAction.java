package com.hr.examin.action;

import com.hr.base.BaseAction;
import com.hr.configuration.domain.AttdMachine;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Location;
import com.hr.examin.bo.ExaminDateUtil;
import com.hr.examin.bo.interfaces.IAttdoriginaldataBO;
import com.hr.examin.domain.Attdoriginaldata;
import com.hr.io.bo.IIodefBo;
import com.hr.io.domain.Iodef;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.DateUtil;
import com.hr.util.Pager;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

public class OriginalDataImportShowAction extends BaseAction {
    private static final long serialVersionUID = -2323155389025834514L;
    private Date attdDateFrom;
    private Date attdDateTo;
    private String dept;
    private String deptName;
    private String machineNo;
    private String status;
    private String iodefId;
    private Iodef iodef;
    private Pager page;
    private List<Attdoriginaldata> originalDataList;
    private String aodId;
    private String emp;
    private Employee attdEmp;
    private List<Employee> empList;
    private List<Location> locationList;
    private List<AttdMachine> machineList;
    private List<Department> departmentList;
    private String empIdStr;
    private String[] empNo;
    private Date attdDate;
    private Date attdCardTime;
    private String aodTtdMachineNo;
    private String memo;
    private String operate;

    public OriginalDataImportShowAction() {
        this.iodefId = "IExamBasic";

        this.iodef = null;

        this.page = new Pager();

        this.locationList = null;

        this.machineList = null;

        this.departmentList = null;

        this.empIdStr = null;
        this.empNo = new String[0];
    }

    public String execute() throws Exception {
        if ((this.attdDateFrom == null) || (this.attdDateTo == null)) {
            String config = DatabaseSysConfigManager.getInstance()
                    .getProperty("sys.examin.month.sum");
            Date date = new Date();
            Date[] dateArr = ExaminDateUtil.getDateArray(DateUtil.getYear(date), DateUtil
                    .getMonth(date), config);
            this.attdDateFrom = dateArr[0];
            this.attdDateTo = dateArr[1];
        }

        Attdoriginaldata paramData = new Attdoriginaldata();
        paramData.setAodTtdMachineNo(this.machineNo);
        if ((this.status != null) && (this.status.trim().length() > 0)) {
            paramData.setAodStatus(Integer.valueOf(Integer.parseInt(this.status)));
        }

        if ((this.operate != null) && ("deleteSome".equals(this.operate))) {
            deleteOriginalData();
            this.operate = null;
        }

        if ((this.operate != null) && ("deleteOne".equals(this.operate))) {
            deleteOneOriginalData();
            this.operate = null;
        }

        IAttdoriginaldataBO originaldataBo = (IAttdoriginaldataBO) SpringBeanFactory
                .getBean("attdoriginaldataBO");
        DetachedCriteria dc = DetachedCriteria.forClass(Attdoriginaldata.class);

        if ("SUB".equals(this.authorityCondition))
            addSubDC(dc, "emp", 4);
        this.originalDataList = originaldataBo.getOriginalDataList(dc, this.page, this.emp,
                                                                   this.dept, paramData,
                                                                   this.attdDateFrom,
                                                                   this.attdDateTo);

        this.departmentList = getDrillDown("Department", new String[0]);
        this.locationList = getDrillDown("Location", new String[0]);
        this.machineList = getDrillDown("AttdMachine", new String[0]);

        IIodefBo iodefBo = (IIodefBo) SpringBeanFactory.getBean("iodefbo");
        this.iodef = iodefBo.searchIodefByName(this.iodefId);

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        this.empList = empBo.getEmpsNeedCard(new String[0]);

        return "success";
    }

    public String deleteOriginalData() throws Exception {
        IAttdoriginaldataBO originaldataBo = (IAttdoriginaldataBO) SpringBeanFactory
                .getBean("attdoriginaldataBO");
        originaldataBo.deleteDataInDateDomain(this.attdDateFrom, this.attdDateTo);
        addSuccessInfo("考勤机刷卡数据清理成功");
        return "success";
    }

    public String deleteOneOriginalData() throws Exception {
        IAttdoriginaldataBO originaldataBo = (IAttdoriginaldataBO) SpringBeanFactory
                .getBean("attdoriginaldataBO");
        originaldataBo.deleteAttdoriginaldata(this.aodId);
        addSuccessInfo("考勤机刷卡数据删除成功");
        return "success";
    }

    public List<Attdoriginaldata> getOriginalDataList() {
        return this.originalDataList;
    }

    public void setOriginalDataList(List<Attdoriginaldata> originalDataList) {
        this.originalDataList = originalDataList;
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

    public Date getAttdDateFrom() {
        return this.attdDateFrom;
    }

    public void setAttdDateFrom(Date attdDateFrom) {
        this.attdDateFrom = attdDateFrom;
    }

    public Date getAttdDateTo() {
        return this.attdDateTo;
    }

    public void setAttdDateTo(Date attdDateTo) {
        this.attdDateTo = attdDateTo;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public String getAodId() {
        return this.aodId;
    }

    public void setAodId(String aodId) {
        this.aodId = aodId;
    }

    public Employee getAttdEmp() {
        return this.attdEmp;
    }

    public void setAttdEmp(Employee attdEmp) {
        this.attdEmp = attdEmp;
    }

    public String getEmp() {
        return this.emp;
    }

    public void setEmp(String emp) {
        this.emp = emp;
    }

    public List<Department> getDepartmentList() {
        return this.departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public List<Employee> getEmpList() {
        return this.empList;
    }

    public void setEmpList(List<Employee> empList) {
        this.empList = empList;
    }

    public List<Location> getLocationList() {
        return this.locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public List<AttdMachine> getMachineList() {
        return this.machineList;
    }

    public void setMachineList(List<AttdMachine> machineList) {
        this.machineList = machineList;
    }

    public String getAodTtdMachineNo() {
        return this.aodTtdMachineNo;
    }

    public void setAodTtdMachineNo(String aodTtdMachineNo) {
        this.aodTtdMachineNo = aodTtdMachineNo;
    }

    public Date getAttdDate() {
        return this.attdDate;
    }

    public void setAttdDate(Date attdDate) {
        this.attdDate = attdDate;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String[] getEmpNo() {
        return this.empNo;
    }

    public void setEmpNo(String[] empNo) {
        this.empNo = empNo;
    }

    public Date getAttdCardTime() {
        return this.attdCardTime;
    }

    public void setAttdCardTime(Date attdCardTime) {
        this.attdCardTime = attdCardTime;
    }

    public String getEmpIdStr() {
        return this.empIdStr;
    }

    public void setEmpIdStr(String empIdStr) {
        this.empIdStr = empIdStr;
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

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperate() {
        return this.operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.action.OriginalDataImportShowAction JD-Core Version: 0.5.4
 */