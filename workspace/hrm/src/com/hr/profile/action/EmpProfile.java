package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.base.ComonBeans;
import com.hr.configuration.domain.ContractType;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.profile.bo.IEmpContractBo;
import com.hr.profile.bo.IEmpEvalBo;
import com.hr.profile.bo.IEmpQuitBo;
import com.hr.profile.bo.IEmpRewardBo;
import com.hr.profile.bo.IEmpTransferBo;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Empcontract;
import com.hr.profile.domain.Empeval;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Empquit;
import com.hr.profile.domain.Empreward;
import com.hr.profile.domain.Emptransfer;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.axis.utils.StringUtils;

public class EmpProfile extends BaseAction {
    private static final long serialVersionUID = 1L;
    private List<Empcontract> contractList;
    private List<ContractType> empTypeList;
    private String empNo;
    private String empName;
    private List<Emptransfer> transferList;
    private List<Empeval> evalList;
    private List<Empreward> rewardList;
    private List<Empquit> quitList;
    private List<Department> deptList;
    private boolean empStatus;
    private Employee currentEmp;
    private List<Emptype> empTypes;
    private Map<String, String> erTypeMap;
    private Map<String, Map<String, String>> erFormMap;
    private Map<String, String> eqTypeMap;
    private Map<String, Map<String, String>> eqReasonMap;
    private Map<String, String> eqRTypeMap;
    private Map<String, Map<String, String>> eqRReasonMap;

    public EmpProfile() {
        this.erTypeMap = ComonBeans.getValuesToMap(Empreward.PROP_ER_TYPE, new boolean[0]);

        this.erFormMap = ComonBeans.getSecondLevelSelect(this.erTypeMap, Empreward.PROP_ER_FORM,
                                                         new boolean[0]);
    }

    public String execute() throws Exception {
        if ((StringUtils.isEmpty(this.empNo)) || (this.empNo.equals("null")))
            this.empNo = getCurrentEmpNo();

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        this.currentEmp = empBo.loadEmp(this.empNo, new String[] { Employee.PROP_EMP_DEPT_NO,
                Employee.PROP_EMP_PB_NO, Employee.PROP_EMP_TYPE });
        if (this.currentEmp == null)
            return "noemp";
        this.empName = this.currentEmp.getEmpName();

        this.empStatus = false;
        if (this.currentEmp.getEmpStatus().equals(Integer.valueOf(1)))
            this.empStatus = true;

        if (!this.empNo.equals(getCurrentEmpNo())) {
            if ("OWN".equals(this.authorityCondition))
                return "noauth";
            if (((("SUB".equals(this.authorityCondition)) || ("HRSUB"
                    .equals(this.authorityCondition))))
                    && (!checkEmpInCharge(this.currentEmp, 1))) {
                return "noauth";
            }
        }

        IEmpContractBo empContractBo = (IEmpContractBo) getBean("empContractBo");
        this.contractList = empContractBo.searchByEmpNo(this.empNo);
        this.empTypeList = empBo.getObjects(ContractType.class, null);
        IEmpTransferBo empTransfer = (IEmpTransferBo) getBean("empTransferBo");
        this.transferList = empTransfer.findTransferByEmpNo(this.empNo);
        IEmpEvalBo empEval = (IEmpEvalBo) getBean("empEvalBo");
        this.evalList = empEval.findEvalByEmpNo(this.empNo);
        IEmpRewardBo empReward = (IEmpRewardBo) getBean("empRewardBo");
        this.rewardList = empReward.findRewardByEmpNo(this.empNo);
        IEmpQuitBo empQuit = (IEmpQuitBo) getBean("empQuitBo");
        this.quitList = empQuit.searchByEmpNo(this.empNo);

        this.deptList = getDrillDown("Department", new String[0]);
        this.empTypes = getDrillDown("EmpType", new String[0]);

        setQRMap();
        return "success";
    }

    private void setQRMap() {
        this.eqTypeMap = ComonBeans.getValuesToMap(Empquit.PROP_EQ_TYPE, new boolean[0]);
        this.eqReasonMap = ComonBeans.getSecondLevelSelect(this.eqTypeMap, Empquit.PROP_EQ_REASON,
                                                           new boolean[0]);

        this.eqRTypeMap = new TreeMap();
        this.eqRReasonMap = new TreeMap();
        this.eqRTypeMap.put("1", this.eqTypeMap.get("1"));
        this.eqRReasonMap.put("1", this.eqReasonMap.get("1"));

        this.eqTypeMap.remove("1");
        this.eqReasonMap.remove("1");
    }

    public List<Empcontract> getContractList() {
        return this.contractList;
    }

    public void setContractList(List<Empcontract> contractList) {
        this.contractList = contractList;
    }

    public String getEmpNo() {
        return this.empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpName() {
        return this.empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public List<ContractType> getEmpTypeList() {
        return this.empTypeList;
    }

    public void setEmpTypeList(List<ContractType> empTypeList) {
        this.empTypeList = empTypeList;
    }

    public List<Emptransfer> getTransferList() {
        return this.transferList;
    }

    public void setTransferList(List<Emptransfer> transferList) {
        this.transferList = transferList;
    }

    public List<Department> getDeptList() {
        return this.deptList;
    }

    public void setDeptList(List<Department> deptList) {
        this.deptList = deptList;
    }

    public List<Empeval> getEvalList() {
        return this.evalList;
    }

    public void setEvalList(List<Empeval> evalList) {
        this.evalList = evalList;
    }

    public List<Empreward> getRewardList() {
        return this.rewardList;
    }

    public void setRewardList(List<Empreward> rewardList) {
        this.rewardList = rewardList;
    }

    public List<Empquit> getQuitList() {
        return this.quitList;
    }

    public void setQuitList(List<Empquit> quitList) {
        this.quitList = quitList;
    }

    public boolean isEmpStatus() {
        return this.empStatus;
    }

    public Employee getCurrentEmp() {
        return this.currentEmp;
    }

    public void setCurrentEmp(Employee currentEmp) {
        this.currentEmp = currentEmp;
    }

    public void setEmpStatus(boolean empStatus) {
        this.empStatus = empStatus;
    }

    public Map<String, String> getErTypeMap() {
        return this.erTypeMap;
    }

    public Map<String, Map<String, String>> getErFormMap() {
        return this.erFormMap;
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

    public Map<String, Map<String, String>> getEqRReasonMap() {
        return this.eqRReasonMap;
    }

    public List<Emptype> getEmpTypes() {
        return this.empTypes;
    }

    public void setEmpTypes(List<Emptype> empTypes) {
        this.empTypes = empTypes;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.EmpProfile JD-Core Version: 0.5.4
 */