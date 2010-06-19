package com.hr.io.extend;

import com.hr.configuration.domain.ContractType;
import com.hr.io.domain.InmatchModel;
import com.hr.profile.bo.IEmpContractBo;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Empcontract;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Emptransfer;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DateUtil;
import com.hr.util.comparator.ObjectFieldComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IEmpContract extends ICheckAndInsert {
    String msgDbInsertError;
    String msgGreaterThan;
    String msgRepeat;
    String msgConflictInTime;
    String msgNoNull;
    String msgNotExist;
    String msgConEndDate1;
    String msgConEndDate2;
    String msgConStatus;
    String msgConNeedJoinDate;
    private IEmpContractBo contractBo;

    public IEmpContract() {
        this.msgDbInsertError = "数据库插入失败！";
        this.msgGreaterThan = "{0}大于{1}";
        this.msgRepeat = "数据重复";
        this.msgConflictInTime = "时间冲突";
        this.msgNoNull = "{0}不能为空";
        this.msgNotExist = "{0}不存在！";
        this.msgConEndDate1 = "无限期合同不能填写{0}";
        this.msgConEndDate2 = "有限期合同必须填写{0}";
        this.msgConStatus = "只有新合同才能为有效合同";
        this.msgConNeedJoinDate = "员工入职日期为空，不能导入合同！";

        this.contractBo = ((IEmpContractBo) SpringBeanFactory.getBean("empContractBo"));
    }

    public int[] insertTransmit(List insertList, CommonParameters commonParas) throws Exception {
        List<Empcontract> contractList = insertList;
        int[] result = { 0, 0 };
        Date nowDate = new Date();

        Map contractTypeMap = getNameIdMap("ContractType", "id", "ectName", new String[0]);
        Employee currEmp = commonParas.getCurrEmp();
        int rowNum = 1 - commonParas.getInmatchModel().getImmNoTitle().intValue();

        Map<String, List<Empcontract>> empcontractMap = new HashMap<String, List<Empcontract>>();

        for (Empcontract contract : contractList) {
            ++rowNum;
            contract.setEctId(rowNum + "");
            if (empcontractMap.containsKey(contract.getEmployee().getEmpDistinctNo())) {
                ((List) empcontractMap.get(contract.getEmployee().getEmpDistinctNo()))
                        .add(contract);
            } else {
                List ecList = new ArrayList();
                ecList.add(contract);
                empcontractMap.put(contract.getEmployee().getEmpDistinctNo(), ecList);
            }

            if ((contract.getEtcExpire().intValue() == 1) && (contract.getEctEndDate() != null))
                commonParas.addErrorMessage(this.msgConEndDate1, Integer.valueOf(rowNum),
                                            new String[] { Empcontract.PROP_ECT_END_DATE });
            else if ((contract.getEtcExpire().intValue() == 0)
                    && (contract.getEctEndDate() == null)) {
                commonParas.addErrorMessage(this.msgConEndDate2, Integer.valueOf(rowNum),
                                            new String[] { Empcontract.PROP_ECT_END_DATE });
            }

            if (DateUtil.compareTwoDay(contract.getEctEndDate(), contract.getEctStartDate()) >= 0) {
                commonParas.addErrorMessage(this.msgGreaterThan, Integer.valueOf(rowNum),
                                            new String[] { Empcontract.PROP_ECT_START_DATE,
                                                    Empcontract.PROP_ECT_END_DATE });
            }

            if (contractTypeMap.containsKey(contract.getContractType().getEctName()))
                contract.getContractType().setId(
                                                 (String) contractTypeMap.get(contract
                                                         .getContractType().getEctName()));
            else {
                commonParas.addErrorMessage(this.msgNotExist, Integer.valueOf(rowNum),
                                            new String[] { Empcontract.PROP_CONTRACT_TYPE
                                                    + ".ectName" });
            }
        }

        if (commonParas.getIoMessages().hasErrorMsg()) {
            return result;
        }

        ObjectFieldComparator objectFieldComparator = new ObjectFieldComparator(Empcontract.class,
                new String[] { Empcontract.PROP_ECT_START_DATE }, new int[0]);
        for (String empDistinctNo : empcontractMap.keySet()) {
            Employee emp = this.employeeBo.loadEmpByDistinctNo(empDistinctNo);
            List<Empcontract> conList = (List) empcontractMap.get(empDistinctNo);
            if (emp == null) {
                for (Empcontract contract : conList) {
                    rowNum = Integer.parseInt(contract.getEctId());
                    commonParas.addErrorMessage(this.msgNotExist, Integer.valueOf(rowNum),
                                                new String[] { Emptransfer.PROP_EMPLOYEE + "."
                                                        + Employee.PROP_EMP_DISTINCT_NO });
                }
            }

            for (Empcontract contract : conList) {
                contract.setEmployee(emp);
            }

            List dbList = this.contractBo.getContractListByEmp(emp);
            int dbNum = dbList.size();
            List dateList = new ArrayList();
            for (int i = 0; i < dbNum; ++i) {
                Empcontract contract = (Empcontract) dbList.get(i);
                dateList.add(contract.getEctStartDate());
                dateList.add(contract.getEctEndDate());
            }

            Collections.sort(conList, objectFieldComparator);
            for (int i = conList.size() - 1; i >= 0; --i) {
                if (emp.getEmpJoinDate() == null) {
                    commonParas.addErrorMessage(this.msgConNeedJoinDate, Integer.valueOf(rowNum),
                                                new String[0]);
                } else {
                    Empcontract contract = (Empcontract) conList.get(i);
                    rowNum = Integer.parseInt(contract.getEctId());

                    if (DateUtil.compareTwoDay(contract.getEctStartDate(), emp.getEmpJoinDate()) == 1) {
                        commonParas.addErrorMessage(this.msgGreaterThan, Integer.valueOf(rowNum),
                                                    new String[] { "入职日期",
                                                            Empcontract.PROP_ECT_START_DATE });
                    }

                    if (DateUtil.compareTwoDay(emp.getEmpTerminateDate(), contract.getEctEndDate()) == 1) {
                        commonParas.addErrorMessage(this.msgGreaterThan, Integer.valueOf(rowNum),
                                                    new String[] { Empcontract.PROP_ECT_END_DATE,
                                                            "离职日期" });
                    }

                    int addResult = DateUtil.addDate(dateList, contract.getEctStartDate(), contract
                            .getEctEndDate());
                    if (addResult == -1) {
                        commonParas.addErrorMessage(this.msgConflictInTime,
                                                    Integer.valueOf(rowNum), new String[0]);
                    } else if (addResult == -2) {
                        result[1] += 1;
                        conList.remove(i);
                    } else {
                        if ((addResult >= dateList.size() - 2)
                                || ((contract.getEctStatus().compareTo("1") != 0) && (i == conList
                                        .size() - 1)))
                            continue;
                        commonParas.addErrorMessage(this.msgConStatus, Integer.valueOf(rowNum),
                                                    new String[] { Empcontract.PROP_ECT_STATUS });
                    }
                }
            }

            if (conList.size() == 0) {
                continue;
            }

            Empcontract conLast = (Empcontract) conList.get(conList.size() - 1);
            if (dbNum > 0) {
                Empcontract conUpdate = (Empcontract) dbList.get(dbNum - 1);
                if (DateUtil.compareTwoDay(conUpdate.getEctStartDate(), conLast.getEctStartDate()) == 1) {
                    if (conUpdate.getEctStatus().compareTo("1") == 0) {
                        conUpdate.setEctStatus("2");
                        conList.add(conUpdate);
                    }
                    emp.setContract(conLast);
                }
            } else {
                emp.setContract(conLast);
            }
        }

        if (commonParas.getIoMessages().hasErrorMsg()) {
            return result;
        }

        for (String empDistinctNo : empcontractMap.keySet()) {
            List<Empcontract> conList = (List) empcontractMap.get(empDistinctNo);
            List rowNumArr = new ArrayList();
            int i = 0;
            for (Empcontract contract : conList) {
                if (contract.getEctId().length() > 30) {
                    continue;
                }
                rowNumArr.add(Integer.valueOf(Integer.parseInt(contract.getEctId())));
                contract.setEctId(null);
                contract.setEctCreateBy(currEmp.getId());
                contract.setEctCreateDate(nowDate);
                contract.setEctLastChangeBy(currEmp.getId());
                contract.setEctLastChangeTime(nowDate);
            }
            if (this.contractBo.insertList(conList))
                result[0] += conList.size();
            else {
                for (i = 0;; ++i) {
                    if (i >= rowNumArr.size())
                        return result;
                    commonParas.addErrorMessage(this.msgDbInsertError, (Integer) rowNumArr.get(i),
                                                new String[0]);
                }

            }
        }

        return result;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.io.extend.IEmpContract JD-Core Version: 0.5.4
 */