package com.hr.io.extend;

import com.hr.configuration.bo.IClientBO;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.domain.BenefitType;
import com.hr.configuration.domain.ContractType;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Location;
import com.hr.io.domain.InmatchModel;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.bo.IPositionBaseBo;
import com.hr.profile.bo.IPositionBo;
import com.hr.profile.domain.Empcontract;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Empquit;
import com.hr.profile.domain.Position;
import com.hr.profile.domain.PositionBase;
import com.hr.profile.domain.base.TreeNode;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DateUtil;
import com.hr.util.MyTools;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.axis.utils.StringUtils;

public class IEmployeeBasic extends ICheckAndInsert {
    String msgDbInsertError;
    String msgGreaterThan;
    String msgExist;
    String msgNoNull;
    String msgNotExist;
    String msgNotExistOrQuit;
    String msgNotExistOrStop;
    String msgEqual;
    String msgNumLimit;
    String msgCanNotQuit;
    String msgQuitNeedInfo;
    String msgEmpCircle;
    String msgNoQuitTerminateDate;
    String msgNoQuitInfo;
    String msgDeptLevelError;
    String msgPBExceed;
    String msgDeptNoRespPos;
    String msgSupDeptError;

    public IEmployeeBasic() {
        this.msgDbInsertError = "数据库插入失败！";
        this.msgGreaterThan = "{0}大于{1}＄1�7";
        this.msgExist = "{0}已存在！";
        this.msgNoNull = "{0}不能为空＄1�7";
        this.msgNotExist = "{0}不存在！";
        this.msgNotExistOrQuit = "{0}不存在或已经离职＄1�7";
        this.msgNotExistOrStop = "{0}不存在或已经被停用！";
        this.msgEqual = "{0}与{1}必须相同＄1�7";
        this.msgNumLimit = "导入员工数量超过限制＄1�7";
        this.msgCanNotQuit = "该员工有下属员工，无法离职！";
        this.msgQuitNeedInfo = "离职员工必须填写离职相关信息＄1�7";
        this.msgEmpCircle = "出现经理环，对应的行数为：{0}＄1�7";
        this.msgNoQuitTerminateDate = "在职员工不该填写{0}＄1�7";
        this.msgNoQuitInfo = "在职员工不该填写离职信息＄1�7";
        this.msgDeptLevelError = "部门层级设置错误或部门有重复＄1�7";
        this.msgPBExceed = "职位超编＄1�7";
        this.msgDeptNoRespPos = "数据错误，部门{0}无负责职位！";
        this.msgSupDeptError = "经理{0}不属于部门{1}";
    }

    public int[] insertTransmit(List insertList, CommonParameters commonParas) throws Exception {
        Employee currEmp = commonParas.getCurrEmp();
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");

        int[] result = { 0, 0 };
        Date nowDate = new Date();
        List<Employee> empAddList = insertList;

        List<Employee> empDbList = getEmployeeList();

        Map emptypeMap = getNameIdMap(Emptype.REF, Emptype.PROP_ID, Emptype.PROP_EMPTYPE_NAME,
                                      new String[] { Emptype.PROP_EMPTYPE_STATUS + "=1" });

        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        Map deptLevelNameMap = deptBo.getDeptLevelNameMap();

        Map deptPBMap = pbBo.getDeptPBMap(null);

        Map locationMap = getNameIdMap(Location.REF, Location.PROP_ID, Location.PROP_LOCATION_NAME,
                                       new String[] { Location.PROP_LOCATION_STATUS + "=1" });

        Map benefitTypeMap = getNameIdMap("BenefitType", "id", "benefitTypeName", new String[0]);

        Map contractTypeMap = getNameIdMap("ContractType", "id", "ectName", new String[0]);

        Set empDistinctNoDbSet = new HashSet();

        Set empDistinctNoFileSet = new HashSet();

        Map empAllMap = new HashMap();

        Set empShiftNoSet = new HashSet();

        Set empIdentificationNoSet = new HashSet();

        List dbPosList = posBo.getDBPosList();

        List filePosList = new ArrayList();

        Map empPosMap = new HashMap();

        Map deptRespPosMap = new HashMap();
        for (int i = 0; (dbPosList != null) && (i < dbPosList.size()); ++i) {
            Position pos = (Position) dbPosList.get(i);
            if (pos.getPositionPbId().getPbInCharge().intValue() == 1) {
                deptRespPosMap.put(pos.getPositionPbId().getPbDeptId().getId(), pos);
            }
            if (pos.getPositionEmpNo() != null) {
                empPosMap.put(pos.getPositionEmpNo().getEmpDistinctNo(), pos);
            }

        }

        for (Employee emp : empDbList) {
            if (emp.getEmpStatus().intValue() == 1) {
                empDistinctNoDbSet.add(emp.getEmpDistinctNo());
            }
            empAllMap.put(emp.getEmpDistinctNo(), emp);
            empShiftNoSet.add(emp.getEmpShiftNo());
            if (emp.getEmpIdentificationType().intValue() == 0) {
                empIdentificationNoSet.add(emp.getEmpIdentificationNo());
            }

        }

        Map<String, EmpAndRow> empInsertMap = new HashMap();
        int rowNum = 2 - commonParas.getInmatchModel().getImmNoTitle().intValue();
        for (Employee emp : empAddList) {
            if (emp.getEmpDistinctNo() == null)
                commonParas.addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                            new String[] { Employee.PROP_EMP_DISTINCT_NO });
            else if (empInsertMap.containsKey(emp.getEmpDistinctNo()))
                result[1] += 1;
            else {
                empInsertMap.put(emp.getEmpDistinctNo(), new EmpAndRow(Integer.valueOf(rowNum++),
                        emp));
            }

        }

        Iterator it = empInsertMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String empDistinctNo = (String) entry.getKey();
            rowNum = ((EmpAndRow) entry.getValue()).rowNum.intValue();
            Employee emp = ((EmpAndRow) entry.getValue()).emp;

            if (empAllMap.containsKey(empDistinctNo)) {
                result[1] += 1;
                it.remove();
            }

            empAllMap.put(empDistinctNo, emp);

            empDistinctNoFileSet.add(empDistinctNo);

            if (emp.getEmpName() == null) {
                commonParas.addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                            new String[] { Employee.PROP_EMP_NAME });
            }

            if (emp.getEmpBirthDate() == null) {
                commonParas.addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                            new String[] { Employee.PROP_EMP_BIRTH_DATE });
            }

            if (emp.getEmpGender() == null) {
                commonParas.addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                            new String[] { Employee.PROP_EMP_GENDER });
            }

            if (emp.getEmpIdentificationType() == null) {
                commonParas.addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                            new String[] { Employee.PROP_EMP_IDENTIFICATION_TYPE });
            }

            if (emp.getEmpJoinDate() == null) {
                commonParas.addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                            new String[] { Employee.PROP_EMP_JOIN_DATE });
            }

            if (emp.getEmpShiftNo() != null) {
                if (empShiftNoSet.contains(emp.getEmpShiftNo()))
                    commonParas.addErrorMessage(this.msgExist, Integer.valueOf(rowNum),
                                                new String[] { Employee.PROP_EMP_SHIFT_NO });
                else {
                    empShiftNoSet.add(emp.getEmpShiftNo());
                }

            }

            if ((emp.getEmpIdentificationType() != null)
                    && (emp.getEmpIdentificationType().intValue() == 0)) {
                if (emp.getEmpIdentificationNo() == null)
                    commonParas
                            .addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                             new String[] { Employee.PROP_EMP_IDENTIFICATION_NO });
                else if (empIdentificationNoSet.contains(emp.getEmpIdentificationNo()))
                    commonParas
                            .addErrorMessage(this.msgExist, Integer.valueOf(rowNum),
                                             new String[] { Employee.PROP_EMP_IDENTIFICATION_NO });
                else {
                    empIdentificationNoSet.add(emp.getEmpIdentificationNo());
                }

            }

            if ((emp.getEmpType() != null) && (emp.getEmpType().getEmptypeName() != null)) {
                if (emptypeMap.containsKey(emp.getEmpType().getEmptypeName()))
                    emp.getEmpType().setId(
                                           (String) emptypeMap.get(emp.getEmpType()
                                                   .getEmptypeName()));
                else
                    commonParas.addErrorMessage(this.msgNotExistOrStop, Integer.valueOf(rowNum),
                                                new String[] { Employee.PROP_EMP_TYPE + "."
                                                        + Emptype.PROP_EMPTYPE_NAME });
            } else {
                commonParas.addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                            new String[] { Employee.PROP_EMP_TYPE + "."
                                                    + Emptype.PROP_EMPTYPE_NAME });
            }

            String[] inputNameArr = new String[6];
            inputNameArr[0] = ((emp.getEmpDeptNo1() != null) ? emp.getEmpDeptNo1()
                    .getDepartmentName() : null);
            inputNameArr[1] = ((emp.getEmpDeptNo2() != null) ? emp.getEmpDeptNo2()
                    .getDepartmentName() : null);
            inputNameArr[2] = ((emp.getEmpDeptNo3() != null) ? emp.getEmpDeptNo3()
                    .getDepartmentName() : null);
            inputNameArr[3] = ((emp.getEmpDeptNo4() != null) ? emp.getEmpDeptNo4()
                    .getDepartmentName() : null);
            inputNameArr[4] = ((emp.getEmpDeptNo5() != null) ? emp.getEmpDeptNo5()
                    .getDepartmentName() : null);
            String empDeptId = null;
            if ((emp.getEmpDeptNo() != null) && (emp.getEmpDeptNo().getDepartmentName() != null)) {
                inputNameArr[5] = emp.getEmpDeptNo().getDepartmentName();
                empDeptId = deptBo.checkInputDeptLevel(deptLevelNameMap, inputNameArr);

                if (empDeptId == null)
                    commonParas.addErrorMessage(this.msgDeptLevelError, Integer.valueOf(rowNum),
                                                new String[] { "" });
                else
                    emp.getEmpDeptNo().setId(empDeptId);
            } else {
                commonParas.addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                            new String[] { Employee.PROP_EMP_DEPT_NO + "."
                                                    + Department.PROP_DEPARTMENT_NAME });
            }

            if ((empDeptId != null) && (emp.getEmpPbNo() != null)
                    && (emp.getEmpPbNo().getPbName() != null)) {
                List pbList = (List) deptPBMap.get(emp.getEmpDeptNo().getId());
                boolean pbExist = false;
                for (int i = 0; (pbList != null) && (i < pbList.size()); ++i) {
                    PositionBase pb = (PositionBase) pbList.get(i);
                    if (pb.getPbName().equals(emp.getEmpPbNo().getPbName())) {
                        pbExist = true;
                        emp.setEmpPbNo(pb);
                    }

                }

                if (!pbExist) {
                    commonParas.addErrorMessage(this.msgNotExistOrStop, Integer.valueOf(rowNum),
                                                new String[] { Employee.PROP_EMP_PB_NO + "."
                                                        + PositionBase.PROP_PB_NAME });
                } else {
                    Object obj = posBo.putEmpToPB(dbPosList, filePosList, emp, emp.getEmpPbNo());
                    if (obj instanceof String) {
                        commonParas.addErrorMessage(this.msgPBExceed, Integer.valueOf(rowNum),
                                                    new String[] { "" });
                    } else {
                        Position pos = (Position) obj;
                        if (pos.getId() == null) {
                            filePosList.add(pos);
                        } else {
                            dbPosList.remove(pos);
                            filePosList.add(pos);
                        }

                        empPosMap.put(pos.getPositionEmpNo().getEmpDistinctNo(), pos);
                    }
                }
            } else if ((emp.getEmpPbNo() != null) && (emp.getEmpPbNo().getPbName() == null)) {
                commonParas.addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                            new String[] { Employee.PROP_EMP_PB_NO + "."
                                                    + PositionBase.PROP_PB_NAME });
            }

            if ((emp.getEmpLocationNo() != null)
                    && (emp.getEmpLocationNo().getLocationName() != null)) {
                if (locationMap.containsKey(emp.getEmpLocationNo().getLocationName()))
                    emp.getEmpLocationNo().setId(
                                                 (String) locationMap.get(emp.getEmpLocationNo()
                                                         .getLocationName()));
                else
                    commonParas.addErrorMessage(this.msgNotExistOrStop, Integer.valueOf(rowNum),
                                                new String[] { Employee.PROP_EMP_LOCATION_NO + "."
                                                        + Location.PROP_LOCATION_NAME });
            } else {
                commonParas.addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                            new String[] { Employee.PROP_EMP_LOCATION_NO + "."
                                                    + Location.PROP_LOCATION_NAME });
            }

            if ((emp.getEmpBenefitType() != null)
                    && (emp.getEmpBenefitType().getBenefitTypeName() != null)) {
                if (benefitTypeMap.containsKey(emp.getEmpBenefitType().getBenefitTypeName()))
                    emp.getEmpBenefitType().setId(
                                                  (String) benefitTypeMap
                                                          .get(emp.getEmpBenefitType()
                                                                  .getBenefitTypeName()));
                else {
                    commonParas.addErrorMessage(this.msgNotExist, Integer.valueOf(rowNum),
                                                new String[] { Employee.PROP_EMP_BENEFIT_TYPE
                                                        + ".benefitTypeName" });
                }
            } else {
                emp.setEmpBenefitType(null);
            }

            if (DateUtil.compareTwoDay(emp.getEmpBirthDate(), nowDate) == -1) {
                commonParas.addErrorMessage(this.msgGreaterThan, Integer.valueOf(rowNum),
                                            new String[] { Employee.PROP_EMP_BIRTH_DATE, "当前日期" });
            }

            if (DateUtil.compareTwoDay(emp.getEmpBirthDate(), emp.getEmpWorkDate()) == -1) {
                commonParas.addErrorMessage(this.msgGreaterThan, Integer.valueOf(rowNum),
                                            new String[] { Employee.PROP_EMP_BIRTH_DATE,
                                                    Employee.PROP_EMP_WORK_DATE });
            }

            if (DateUtil.compareTwoDay(emp.getEmpWorkDate(), emp.getEmpJoinDate()) == -1) {
                commonParas.addErrorMessage(this.msgGreaterThan, Integer.valueOf(rowNum),
                                            new String[] { Employee.PROP_EMP_WORK_DATE,
                                                    Employee.PROP_EMP_JOIN_DATE });
            }

            if (DateUtil.compareTwoDay(emp.getEmpJoinDate(), emp.getEmpConfirmDate()) == -1) {
                commonParas.addErrorMessage(this.msgGreaterThan, Integer.valueOf(rowNum),
                                            new String[] { Employee.PROP_EMP_JOIN_DATE,
                                                    Employee.PROP_EMP_CONFIRM_DATE });
            }

        }

        int insertNum = empInsertMap.size();
        if (insertNum == 0) {
            return result;
        }
        IClientBO clientLimit = (IClientBO) SpringBeanFactory.getBean("clientBo");
        String limitInfo = clientLimit.checkEmpImport(insertNum);
        if (!"success".equals(limitInfo)) {
            commonParas.addErrorMessage(this.msgNumLimit, null, new String[0]);
        }

        for (String empDistinctNo : empInsertMap.keySet()) {
            rowNum = ((EmpAndRow) empInsertMap.get(empDistinctNo)).rowNum.intValue();
            Employee emp = ((EmpAndRow) empInsertMap.get(empDistinctNo)).emp;
            if (StringUtils.isEmpty(emp.getEmpDeptNo().getId()))
                continue;
            if (StringUtils.isEmpty(emp.getEmpPbNo().getId())) {
                continue;
            }
            Position pos = (Position) empPosMap.get(empDistinctNo);
            if (pos != null)
                ;
            if (emp.getEmpPbNo().getPbInCharge().intValue() != 1)
                ;
            String empSupNo = (emp.getEmpSupNo() != null) ? emp.getEmpSupNo().getEmpDistinctNo()
                    : null;

            if (empSupNo == null) {
                Position deptRespPos = (Position) deptRespPosMap.get(emp.getEmpDeptNo().getId());
                if (deptRespPos == null)
                    commonParas.addErrorMessage(this.msgDeptNoRespPos, Integer.valueOf(rowNum),
                                                new String[] { emp.getEmpDeptNo()
                                                        .getDepartmentName() });
                else
                    pos.setPositionParentId(deptRespPos);
            } else {
                Position parentPos = (Position) empPosMap.get(emp.getEmpSupNo().getEmpDistinctNo());
                if (parentPos == null)
                    commonParas
                            .addErrorMessage(this.msgNotExistOrQuit, Integer.valueOf(rowNum),
                                             new String[] { emp.getEmpSupNo().getEmpDistinctNo() });
                else if (!emp.getEmpDeptNo().getId().equals(
                                                            parentPos.getPositionPbId()
                                                                    .getPbDeptId().getId()))
                    commonParas.addErrorMessage(this.msgSupDeptError, Integer.valueOf(rowNum),
                                                new String[] { empSupNo,
                                                        emp.getEmpDeptNo().getDepartmentName() });
                else {
                    pos.setPositionParentId(parentPos);
                }

            }

        }

        for (String empDistinctNo : empInsertMap.keySet()) {
            rowNum = ((EmpAndRow) empInsertMap.get(empDistinctNo)).rowNum.intValue();
            Employee emp = ((EmpAndRow) empInsertMap.get(empDistinctNo)).emp;

            if (emp.getEmpStatus() == null) {
                commonParas.addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                            new String[] { Employee.PROP_EMP_STATUS });
            } else if (emp.getEmpStatus().intValue() == 0) {
                Empquit empquit = emp.getQuit();
                if (empquit == null) {
                    commonParas.addErrorMessage(this.msgQuitNeedInfo, Integer.valueOf(rowNum),
                                                new String[0]);
                } else {
                    empquit.setEmployee(emp);

                    if (empquit.getEqType() == null) {
                        commonParas
                                .addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                                 new String[] { "quit." + Empquit.PROP_EQ_TYPE });
                    }

                    if ((empquit.getEqDate() == null) && (emp.getEmpTerminateDate() == null))
                        commonParas
                                .addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                                 new String[] { "quit." + Empquit.PROP_EQ_DATE });
                    else if (empquit.getEqDate() == null)
                        empquit.setEqDate(emp.getEmpTerminateDate());
                    else if (emp.getEmpTerminateDate() == null)
                        emp.setEmpTerminateDate(empquit.getEqDate());
                    else if (DateUtil.compareTwoDay(emp.getEmpTerminateDate(), empquit.getEqDate()) != 0) {
                        commonParas.addErrorMessage(this.msgEqual, Integer.valueOf(rowNum),
                                                    new String[] { "quit." + Empquit.PROP_EQ_DATE,
                                                            Employee.PROP_EMP_TERMINATE_DATE });
                    }

                    if ((empquit.getEqPermission() == null)
                            || (empquit.getEqPermission().getEmpDistinctNo() == null))
                        empquit.setEqPermission(currEmp);
                    else if (empAllMap.containsKey(empquit.getEqPermission().getEmpDistinctNo()))
                        empquit.setEqPermission((Employee) empAllMap.get(empquit.getEqPermission()
                                .getEmpDistinctNo()));
                    else {
                        commonParas.addErrorMessage(this.msgNotExist, Integer.valueOf(rowNum),
                                                    new String[] { "quit."
                                                            + Empquit.PROP_EQ_PERMISSION + "."
                                                            + Employee.PROP_EMP_DISTINCT_NO });
                    }

                    empquit.setEqDeptNo(emp.getEmpDeptNo());
                    empquit.setEqPbNo(emp.getEmpPbNo());
                    empquit.setEqCreateBy(currEmp.getId());
                    empquit.setEqCreateDate(nowDate);
                    empquit.setEqLastChangeBy(currEmp.getId());
                    empquit.setEqLastChangeTime(nowDate);
                }
            } else {
                if (emp.getQuit() != null) {
                    commonParas.addErrorMessage(this.msgNoQuitInfo, Integer.valueOf(rowNum),
                                                new String[0]);
                }
                emp.setQuit(null);
                if (emp.getEmpTerminateDate() != null) {
                    commonParas.addErrorMessage(this.msgNoQuitTerminateDate, Integer
                            .valueOf(rowNum), new String[] { Employee.PROP_EMP_TERMINATE_DATE });
                }

            }

            if (DateUtil.compareTwoDay(emp.getEmpJoinDate(), emp.getEmpTerminateDate()) == -1) {
                commonParas.addErrorMessage(this.msgGreaterThan, Integer.valueOf(rowNum),
                                            new String[] { Employee.PROP_EMP_JOIN_DATE,
                                                    Employee.PROP_EMP_TERMINATE_DATE });
            }

            if (emp.getContract() != null) {
                Empcontract empContract = emp.getContract();

                String fieldNameBase = Employee.PROP_EMP_CONTRACT + ".";
                if (empContract.getEctStartDate() == null) {
                    commonParas.addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                                new String[] { fieldNameBase
                                                        + Empcontract.PROP_ECT_START_DATE });
                }

                if (empContract.getEtcExpire() == null) {
                    commonParas.addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                                new String[] { fieldNameBase
                                                        + Empcontract.PROP_ETC_EXPIRE });
                }

                if (empContract.getEctStatus() == null) {
                    commonParas.addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                                new String[] { fieldNameBase
                                                        + Empcontract.PROP_ECT_STATUS });
                }

                String fieldName = fieldNameBase + Empcontract.PROP_CONTRACT_TYPE + ".ectName";
                if ((empContract.getContractType() == null)
                        || (empContract.getContractType().getEctName() == null)) {
                    commonParas.addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                                new String[] { fieldName });
                } else if (contractTypeMap.containsKey(empContract.getContractType().getEctName()))
                    empContract.setContractType(new ContractType((String) contractTypeMap
                            .get(empContract.getContractType().getEctName())));
                else {
                    commonParas.addErrorMessage(this.msgNotExist, Integer.valueOf(rowNum),
                                                new String[] { fieldName });
                }

                empContract.setEmployee(emp);
                empContract.setEctCreateBy(currEmp.getId());
                empContract.setEctCreateDate(nowDate);
                empContract.setEctLastChangeBy(currEmp.getId());
                empContract.setEctLastChangeTime(nowDate);
            }

        }

        if ((dbPosList.size() > 0) && (filePosList.size() > 0)) {
            boolean finish = false;
            while (!finish) {
                finish = true;
                Iterator itPos = filePosList.iterator();
                while (itPos.hasNext()) {
                    Position pos = (Position) itPos.next();
                    if ((posBo.parentPosInList(dbPosList, pos))
                            || (pos.getPositionParentId() == null)) {
                        finish = false;
                        dbPosList.add(pos);
                        itPos.remove();
                    }
                }
            }
        }

        if (filePosList.size() > 0) {
            String errRowNum = "";
            for (int i = 0; i < filePosList.size(); ++i) {
                Position pos = (Position) filePosList.get(i);
                String empDistinctNo = pos.getPositionEmpNo().getEmpDistinctNo();
                errRowNum = errRowNum + "," + ((EmpAndRow) empInsertMap.get(empDistinctNo)).rowNum;
            }
            errRowNum = errRowNum.substring(1);
            commonParas.addErrorMessage(this.msgEmpCircle, null, new String[] { errRowNum });
        }

        if (!commonParas.getIoMessages().hasErrorMsg()) {
            List empList = new ArrayList();
            List quitList = new ArrayList();
            List contractList = new ArrayList();
            List savePosList = new ArrayList();
            Integer currMachineNo = this.employeeBo.findEmployeeMaxMachineNo();
            for (int i = 0; i < dbPosList.size(); ++i) {
                Position pos = (Position) dbPosList.get(i);
                if (pos.getPositionEmpNo() != null) {
                    String empDistinctNo = pos.getPositionEmpNo().getEmpDistinctNo();

                    EmpAndRow empAndRowTmp = (EmpAndRow) empInsertMap.get(empDistinctNo);
                    if (empAndRowTmp != null) {
                        Employee emp = empAndRowTmp.emp;
                        rowNum = empAndRowTmp.rowNum.intValue();
                        emp.setId(MyTools.getUUID());
                        emp.setEmpMachineNo(currMachineNo = Integer.valueOf(currMachineNo
                                .intValue() + 1));
                        if (emp.getEmpCreateBy() == null) {
                            emp.setEmpCreateBy(currEmp);
                            emp.setEmpCreateTime(nowDate);
                        }
                        emp.setEmpLastChangeBy(currEmp);
                        emp.setEmpLastChangeTime(nowDate);

                        emp.setEmpSupNo(null);

                        empList.add(emp);
                        if (emp.getQuit() != null)
                            quitList.add(emp.getQuit());
                        if (emp.getContract() != null)
                            contractList.add(emp.getContract());

                        if (emp.getEmpStatus().intValue() != 1)
                            continue;
                        savePosList.add(pos);
                    }
                }
            }
            boolean value = this.employeeBo.saveOrUpdateEmpInfo(empList, quitList, contractList,
                                                                savePosList);
            if (value)
                result[0] = empList.size();
        }

        return result;
    }

    private TreeNode checkEmpDeptSet(List<String> nameList, String deptName,
            List<TreeNode> deptNodeList) {
        if (!deptName.equals(nameList.get(nameList.size() - 1)))
            nameList.add(deptName);

        TreeNode parentNode = getRootDept(deptNodeList);
        TreeNode currentNode = null;
        for (int i = 0; i < nameList.size(); ++i) {
            String tempName = (String) nameList.get(i);
            if (tempName != null) {
                currentNode = getDeptByNameOfParent(tempName, parentNode.getNodeId(), deptNodeList);
                if (currentNode == null)
                    return null;
            }
        }

        return currentNode;
    }

    private TreeNode getRootDept(List<TreeNode> deptNodeList) {
        for (int i = 0; (deptNodeList != null) && (i < deptNodeList.size()); ++i) {
            TreeNode node = (TreeNode) deptNodeList.get(i);
            if (StringUtils.isEmpty(node.getNodeParentId()))
                return node;
        }

        return null;
    }

    private TreeNode getDeptByNameOfParent(String deptName, String parentId,
            List<TreeNode> deptNodeList) {
        for (int i = 0; (deptNodeList != null) && (i < deptNodeList.size()); ++i) {
            TreeNode node = (TreeNode) deptNodeList.get(i);
            if ((!StringUtils.isEmpty(node.getNodeParentId()))
                    && (node.getNodeParentId().equals(parentId))
                    && (deptName.equals(node.getNodeName()))) {
                return node;
            }
        }
        return null;
    }

    private class EmpAndRow {
        public Integer rowNum;
        public Employee emp;

        EmpAndRow(Integer rowNum, Employee emp) {
            this.rowNum = rowNum;
            this.emp = emp;
        }
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.extend.IEmployeeBasic JD-Core Version: 0.5.4
 */