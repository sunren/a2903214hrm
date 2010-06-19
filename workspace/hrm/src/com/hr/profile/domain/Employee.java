package com.hr.profile.domain;

import com.hr.compensation.domain.Empsalarypay;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.examin.domain.Leavebalance;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.base.BaseEmployee;
import com.hr.spring.SpringBeanFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Employee extends BaseEmployee {
    private static final long serialVersionUID = 1L;
    private Department empBranch;
    private Department empDeptNo1;
    private Department empDeptNo2;
    private Department empDeptNo3;
    private Department empDeptNo4;
    private Department empDeptNo5;
    private Employee empSupNo;
    private String empNoName;
    private String deptNo;
    private String manager;
    private String jobTitle;
    private String ids;
    private String empSupName;
    private String newStatus;
    private String changeReason;
    private String changeDate;
    private int changed;
    private String locationNo;
    private String typeNo;
    private Empsalarypay empsalarypay;
    private List logs;
    private String[] empInCharge;
    private boolean hasApproveAuth;
    private Float joinYear;
    private Float practiceMonth;
    private Date workDate;
    private Date joinDate;
    private Date confirmDate;
    private Date birthDate;
    private Float workYear;
    private String costCenter;
    private String englishName;
    private List<String> ssNameList = new ArrayList();
    private Leavebalance leaveBalance;
    private Position position;
    private Integer syncResult = Integer.valueOf(0);

    public Employee() {
    }

    public String getIds() {
        return this.ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Employee(String id) {
        super(id);
    }

    public Employee(String id, String empDistinctNo, Integer empMachineNo, Emptype empType,
            Department empDeptNo, String empName, Date empJoinDate, Integer empStatus,
            Integer empGender, Integer empIdentificationType, String empIdentificationNo,
            Date empBirthDate, Date empCreateTime, Date empLastChangeTime) {
        super(id, empDistinctNo, empMachineNo, empType, empDeptNo, empName, empJoinDate, empStatus,
                empGender, empIdentificationType, empIdentificationNo, empBirthDate, empCreateTime,
                empLastChangeTime);
    }

    public String[] getOrgInCharge() {
        if (getId() == null) {
            return null;
        }
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        String hql = "SELECT orgheadsOrgNo from Orgheads where orgheadsEmpNo ='" + getId() + "'";

        List list = empBo.exeHqlList(hql, new int[0]);
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return (String[]) (String[]) list.toArray(new String[list.size()]);
    }

    /** @deprecated */
    public String[] getDeptInChargeOld() {
        if (getId() == null) {
            return null;
        }
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        String hql = "SELECT orgheadsOrgNo from Orgheads where orgheadsResponsibility like 'dept%' and orgheadsEmpNo ='"
                + getId() + "'";

        List list = empBo.exeHqlList(hql, new int[0]);
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return (String[]) (String[]) list.toArray(new String[list.size()]);
    }

    public String[] getAreaInCharge() {
        if (getId() == null) {
            return null;
        }
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        String hql = "SELECT orgheadsOrgNo from Orgheads where orgheadsResponsibility like 'area%' and  orgheadsEmpNo='"
                + getId() + "'";

        List list = empBo.exeHqlList(hql, new int[0]);
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return (String[]) (String[]) list.toArray(new String[list.size()]);
    }

    public String[] getGroupInCharge() {
        if (getId() == null) {
            return null;
        }
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        String hql = "SELECT orgheadsOrgNo from Orgheads where orgheadsResponsibility like 'grp%' and  orgheadsEmpNo='"
                + getId() + "'";

        List list = empBo.exeHqlList(hql, new int[0]);
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return (String[]) (String[]) list.toArray(new String[list.size()]);
    }

    public String[] getBuInCharge() {
        if (getId() == null) {
            return null;
        }
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        String hql = "SELECT orgheadsOrgNo from Orgheads where orgheadsResponsibility like 'bu%' and orgheadsEmpNo ='"
                + getId() + "'";

        List list = empBo.exeHqlList(hql, new int[0]);
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return (String[]) (String[]) list.toArray(new String[list.size()]);
    }

    public String[] getBranchInCharge() {
        if (getId() == null) {
            return null;
        }
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        String hql = "SELECT orgheadsOrgNo from Orgheads where orgheadsResponsibility like 'bra%' and orgheadsEmpNo ='"
                + getId() + "'";

        List list = empBo.exeHqlList(hql, new int[0]);
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return (String[]) (String[]) list.toArray(new String[list.size()]);
    }

    public String getDeptNo() {
        return this.deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getJobTitle() {
        if ((this.jobTitle != null) && (this.jobTitle.length() > 0)) {
            return this.jobTitle.trim();
        }
        return this.jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getManager() {
        return this.manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public int getChanged() {
        return this.changed;
    }

    public void setChanged(int changed) {
        this.changed = changed;
    }

    public String getChangeDate() {
        return this.changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public String getChangeReason() {
        return this.changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public String getNewStatus() {
        return this.newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public String getEmpSupName() {
        return this.empSupName;
    }

    public void setEmpSupName(String empSupName) {
        this.empSupName = empSupName;
    }

    public String getEmpNoName() {
        return this.empNoName;
    }

    public void setEmpNoName(String empNoName) {
        this.empNoName = empNoName;
    }

    public List getLogs() {
        return this.logs;
    }

    public void setLogs(List logs) {
        this.logs = logs;
    }

    public String getLocationNo() {
        return this.locationNo;
    }

    public void setLocationNo(String locationNo) {
        this.locationNo = locationNo;
    }

    public String getTypeNo() {
        return this.typeNo;
    }

    public void setTypeNo(String typeNo) {
        this.typeNo = typeNo;
    }

    public Empsalarypay getEmpsalarypay() {
        return this.empsalarypay;
    }

    public void setEmpsalarypay(Empsalarypay empsalarypay) {
        this.empsalarypay = empsalarypay;
    }

    public boolean isHasApproveAuth() {
        return this.hasApproveAuth;
    }

    public void setHasApproveAuth(boolean hasApproveAuth) {
        this.hasApproveAuth = hasApproveAuth;
    }

    public String[] getEmpInCharge() {
        return this.empInCharge;
    }

    public void setEmpInCharge(String[] empInCharge) {
        this.empInCharge = empInCharge;
    }

    public Float getJoinYear() {
        return this.joinYear;
    }

    public void setJoinYear(Float joinYear) {
        this.joinYear = joinYear;
    }

    public Float getPracticeMonth() {
        return this.practiceMonth;
    }

    public void setPracticeMonth(Float practiceMonth) {
        this.practiceMonth = practiceMonth;
    }

    public Date getConfirmDate() {
        return this.confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Date getJoinDate() {
        return this.joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getWorkDate() {
        return this.workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public String getYearOrMonth(Float joinYear) {
        int value = 0;
        String result = "";
        if (joinYear.floatValue() >= 1.0F) {
            value = Integer.parseInt(joinYear.intValue() + "");
            result = result + value + "个月";
        } else {
            joinYear = Float.valueOf(joinYear.floatValue() * 100.0F);
            value = Integer.parseInt(joinYear.intValue() + "");
            result = result + value + "个月";
        }

        return result;
    }

    public Float getWorkYear() {
        return this.workYear;
    }

    public void setWorkYear(Float workYear) {
        this.workYear = workYear;
    }

    public String getCostCenter() {
        return this.costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public List<String> getSsNameList() {
        return this.ssNameList;
    }

    public void setSsNameList(List<String> ssNameList) {
        this.ssNameList = ssNameList;
    }

    public String getEnglishName() {
        String first = (getEmpFname() == null) ? "" : getEmpFname();
        String middle = (getEmpMname() == null) ? "" : getEmpMname();
        String last = (getEmpLname() == null) ? "" : getEmpLname();
        return first + " " + middle + " " + last;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Leavebalance getLeaveBalance() {
        return this.leaveBalance;
    }

    public void setLeaveBalance(Leavebalance leaveBalance) {
        this.leaveBalance = leaveBalance;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Department getEmpBranch() {
        return this.empBranch;
    }

    public void setEmpBranch(Department empBranch) {
        this.empBranch = empBranch;
    }

    public Department getEmpDeptNo1() {
        return this.empDeptNo1;
    }

    public void setEmpDeptNo1(Department empDeptNo1) {
        this.empDeptNo1 = empDeptNo1;
    }

    public Department getEmpDeptNo2() {
        return this.empDeptNo2;
    }

    public void setEmpDeptNo2(Department empDeptNo2) {
        this.empDeptNo2 = empDeptNo2;
    }

    public Department getEmpDeptNo3() {
        return this.empDeptNo3;
    }

    public void setEmpDeptNo3(Department empDeptNo3) {
        this.empDeptNo3 = empDeptNo3;
    }

    public Integer getSyncResult() {
        return this.syncResult;
    }

    public void setSyncResult(Integer syncResult) {
        this.syncResult = syncResult;
    }

    public Department getEmpDeptNo4() {
        return this.empDeptNo4;
    }

    public void setEmpDeptNo4(Department empDeptNo4) {
        this.empDeptNo4 = empDeptNo4;
    }

    public Department getEmpDeptNo5() {
        return this.empDeptNo5;
    }

    public void setEmpDeptNo5(Department empDeptNo5) {
        this.empDeptNo5 = empDeptNo5;
    }

    public Employee getEmpSupNo() {
        return this.empSupNo;
    }

    public void setEmpSupNo(Employee empSupNo) {
        this.empSupNo = empSupNo;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.domain.Employee JD-Core Version: 0.5.4
 */