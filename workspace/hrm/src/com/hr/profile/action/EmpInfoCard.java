package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Statusconf;
import com.hr.profile.bo.IEmpAddConfBo;
import com.hr.profile.bo.IEmpContractBo;
import com.hr.profile.bo.IEmpEduHisBo;
import com.hr.profile.bo.IEmpEvalBo;
import com.hr.profile.bo.IEmpJobHisBo;
import com.hr.profile.bo.IEmpQuitBo;
import com.hr.profile.bo.IEmpRelationsBo;
import com.hr.profile.bo.IEmpRewardBo;
import com.hr.profile.bo.IEmpTrainHisBo;
import com.hr.profile.bo.IEmpTransferBo;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Empaddconf;
import com.hr.profile.domain.Empcontract;
import com.hr.profile.domain.Empeval;
import com.hr.profile.domain.Emphistoryedu;
import com.hr.profile.domain.Emphistoryjob;
import com.hr.profile.domain.Emphistorytrain;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Empquit;
import com.hr.profile.domain.Emprelations;
import com.hr.profile.domain.Empreward;
import com.hr.profile.domain.Emptransfer;
import com.hr.spring.SpringBeanFactory;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

public class EmpInfoCard extends BaseAction {
    private static final long serialVersionUID = 1L;
    private Employee employee;
    private List<Emphistoryjob> ehjList;
    private List<Emphistoryedu> eheList;
    private List<Emphistorytrain> ehtList;
    List<Emprelations> erelList;
    private List<Empaddconf> empaddconfList;
    private List<Emptransfer> transferList;
    private List<Empreward> rewardList;
    private List<Empeval> evalList;
    private List<Statusconf> status;
    private List<Empcontract> contractList;
    private List<Empquit> quitList;
    private String empId;
    private String connectionType;
    private String connectionNo;

    public String execute() throws Exception {
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");

        if (StringUtils.isEmpty(this.empId)) {
            this.empId = getCurrentEmpNo();
        }

        String[] fetch = { Employee.PROP_EMP_TYPE, Employee.PROP_EMP_DEPT_NO,
                Employee.PROP_EMP_PB_NO, Employee.PROP_EMP_LOCATION_NO,
                Employee.PROP_EMP_BENEFIT_TYPE };

        setEmployee(empBo.loadEmp(this.empId, fetch));
        Department dept = this.employee.getEmpDeptNo();
        IDepartmentBO departmentBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        List<Department> allResultList = departmentBo.FindEnabledDepartment();

        if (dept.getDepartmentParentId() != null) {
            String turnDeptId = dept.getDepartmentParentId().getId();
            boolean turnIdExist;
            do {
                turnIdExist = false;
                for (Department deptInWhile : allResultList) {
                    if (deptInWhile.getId().equals(turnDeptId)) {
                        turnIdExist = true;
                        String deptName = deptInWhile.getDepartmentName() + "-"
                                + dept.getDepartmentName();
                        dept.setDepartmentName(deptName);
                        if (deptInWhile.getDepartmentParentId() == null)
                            turnDeptId = null;
                        else
                            turnDeptId = deptInWhile.getDepartmentParentId().getId();
                    }
                }
            } while ((turnDeptId != null) && (turnIdExist));
        }

        if (getEmployee() == null) {
            addErrorInfo("错误的请求参数，要查看的员工不存在或已经被删除！");
            return "noemp";
        }

        String empMsn = this.employee.getEmpMsn();
        if ((empMsn != null) && (!"".equals(empMsn)))
            if (empMsn.startsWith("QQ:")) {
                this.connectionType = "QQ";
                this.connectionNo = empMsn.replace("QQ:", "");
            } else if (empMsn.startsWith("WW:")) {
                this.connectionType = "旺旺";
                this.connectionNo = empMsn.replace("WW:", "");
            } else {
                this.connectionType = "MSN";
                this.connectionNo = empMsn;
            }
        else {
            this.connectionType = "即时通讯";
        }

        setStatus(empBo.getEmpStatus());

        IEmpJobHisBo empJobHisBo = (IEmpJobHisBo) BaseAction.getBean("empJobHisBo");
        this.ehjList = empJobHisBo.search(this.empId);

        IEmpEduHisBo empEduHisBo = (IEmpEduHisBo) BaseAction.getBean("empEduHisBo");
        this.eheList = empEduHisBo.search(this.empId);

        IEmpTrainHisBo empTrainHisBo = (IEmpTrainHisBo) BaseAction.getBean("empTrainHisBo");
        this.ehtList = empTrainHisBo.search(this.empId);

        IEmpRelationsBo empRelationsBo = (IEmpRelationsBo) BaseAction.getBean("empRelationsBo");
        this.erelList = empRelationsBo.search(this.empId);

        IEmpAddConfBo empAddConfBo = (IEmpAddConfBo) BaseAction.getBean("empAddConfBo");
        this.empaddconfList = empAddConfBo.listByTable("empAdditional");

        int size = this.empaddconfList.size();
        for (int i = 0; i < size; ++i) {
            Empaddconf eac = (Empaddconf) this.empaddconfList.get(i);
            int index = eac.getEadcSeq().intValue();
            eac.setFieldName("empAdditional" + index);
            BeanUtils.setProperty(eac, "value", BeanUtils.getProperty(this.employee,
                                                                      "empAdditional" + index));
            if ("select".equals(eac.getEadcFieldType())) {
                eac.setFieldValueList(Arrays.asList(eac.getEadcFieldValue().split(",")));
            }

        }

        IEmpContractBo empContractBo = (IEmpContractBo) getBean("empContractBo");
        this.contractList = empContractBo.searchByEmpNo(this.empId);

        IEmpQuitBo empQuit = (IEmpQuitBo) getBean("empQuitBo");
        this.quitList = empQuit.searchByEmpNo(this.empId);

        IEmpRewardBo empReward = (IEmpRewardBo) getBean("empRewardBo");
        this.rewardList = empReward.findRewardByEmpNo(this.empId);

        IEmpTransferBo empTransfer = (IEmpTransferBo) getBean("empTransferBo");
        this.transferList = empTransfer.findTransferByEmpNo(this.empId);

        IEmpEvalBo empEval = (IEmpEvalBo) getBean("empEvalBo");
        this.evalList = empEval.findEvalByEmpNo(this.empId);
        return "success";
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Emphistoryjob> getEhjList() {
        return this.ehjList;
    }

    public void setEhjList(List<Emphistoryjob> ehjList) {
        this.ehjList = ehjList;
    }

    public List<Emphistoryedu> getEheList() {
        return this.eheList;
    }

    public void setEheList(List<Emphistoryedu> eheList) {
        this.eheList = eheList;
    }

    public List<Emphistorytrain> getEhtList() {
        return this.ehtList;
    }

    public void setEhtList(List<Emphistorytrain> ehtList) {
        this.ehtList = ehtList;
    }

    public List<Empaddconf> getEmpaddconfList() {
        return this.empaddconfList;
    }

    public void setEmpaddconfList(List<Empaddconf> empaddconfList) {
        this.empaddconfList = empaddconfList;
    }

    public List<Emptransfer> getTransferList() {
        return this.transferList;
    }

    public void setTransferList(List<Emptransfer> transferList) {
        this.transferList = transferList;
    }

    public List<Empreward> getRewardList() {
        return this.rewardList;
    }

    public void setRewardList(List<Empreward> rewardList) {
        this.rewardList = rewardList;
    }

    public List<Empeval> getEvalList() {
        return this.evalList;
    }

    public void setEvalList(List<Empeval> evalList) {
        this.evalList = evalList;
    }

    public List<Statusconf> getStatus() {
        return this.status;
    }

    public void setStatus(List<Statusconf> status) {
        this.status = status;
    }

    public List<Empcontract> getContractList() {
        return this.contractList;
    }

    public void setContractList(List<Empcontract> contractList) {
        this.contractList = contractList;
    }

    public List<Empquit> getQuitList() {
        return this.quitList;
    }

    public void setQuitList(List<Empquit> quitList) {
        this.quitList = quitList;
    }

    public String getEmpId() {
        return this.empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public List<Emprelations> getErelList() {
        return this.erelList;
    }

    public void setErelList(List<Emprelations> erelList) {
        this.erelList = erelList;
    }

    public String getConnectionType() {
        return this.connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public String getConnectionNo() {
        return this.connectionNo;
    }

    public void setConnectionNo(String connectionNo) {
        this.connectionNo = connectionNo;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.EmpInfoCard JD-Core Version: 0.5.4
 */