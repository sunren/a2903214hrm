package com.hr.io.extend;

import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.domain.Department;
import com.hr.io.domain.InmatchModel;
import com.hr.profile.bo.IEmpHistOrgBo;
import com.hr.profile.bo.IEmpTransferBo;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.bo.IPositionBaseBo;
import com.hr.profile.bo.IPositionBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Emptransfer;
import com.hr.profile.domain.Position;
import com.hr.profile.domain.PositionBase;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DateUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanComparator;

public class IEmptransfer extends ICheckAndInsert {
    String msgDbInsertError;
    String msgGreaterThan;
    String msgExist;
    String msgNoNull;
    String msgNotExist;
    String msgNotExistOrQuit;
    String msgNotExistOrStop;
    String msgNumLimit;
    String msgCanNotQuit;
    String msgQuitNeedInfo;
    String msgEmpCircle;
    String msgDbRepeat;
    String msgOldDeptLevelError;
    String msgNewDeptLevelError;
    String msgPBExceed;
    String msgDeptNoRespPos;
    String msgSupDeptError;
    private IEmpTransferBo empTransferBo;

    public IEmptransfer()
    {
      this.msgDbInsertError = "数据库插入失败";

      this.msgGreaterThan = "{0}大于{1}";

      this.msgExist = "{0}已存在";

      this.msgNoNull = "{0}不能为空";

      this.msgNotExist = "{0}不存在";

      this.msgNotExistOrQuit = "{0}不存在或已经离职";

      this.msgNotExistOrStop = "{0}不存在或已经被停用";

      this.msgNumLimit = "导入员工数目超过限制";

      this.msgCanNotQuit = "该员工有下属员工，无法离职";

      this.msgQuitNeedInfo = "离职人员必须填写离职相关信息";

      this.msgEmpCircle = "出现员工环，对应的员工编号为：{0}";

      this.msgDbRepeat = "数据库数据出现重复，请先检查数据库：{0}";

      this.msgOldDeptLevelError = "变动前部门层级设置错误或有重复！";

      this.msgNewDeptLevelError = "变动后部门层级设置错误或有重复！";

      this.msgPBExceed = "职位超编！对应的员工编号为：{0}";

      this.msgDeptNoRespPos = "数据错误，部门{0}无负责职位！";

      this.msgSupDeptError = "经理{0}不属于部门{1}";

      this.empTransferBo = ((IEmpTransferBo)SpringBeanFactory.getBean("empTransferBo"));
    }
    
    public int[] insertTransmit(List insertList, CommonParameters commonParas) throws Exception {
        Employee currEmp = commonParas.getCurrEmp();
        int[] result = { 0, 0 };
        Date nowDate = new Date();
        List<Emptransfer> transferList = insertList;
        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");

        Map departmentMap = getNameIdMap(Department.REF, Department.PROP_ID,
                                         Department.PROP_DEPARTMENT_NAME,
                                         new String[] { Department.PROP_DEPARTMENT_STATUS + "=1" });

        Map jobTitleMap = getNameIdMap("JobTitle", "jobtitleNo", "jobtitleName",
                                       new String[] { "jobtitleStatus=1" });

        Map deptLevelNameMap = deptBo.getDeptLevelNameMap();
        Map deptPBMap = pbBo.getDeptPBMap(null);

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

        int rowNum = 1 - commonParas.getInmatchModel().getImmNoTitle().intValue();

        Map<String, List<Emptransfer>> emptransferMap = new HashMap<String, List<Emptransfer>>();
        for (Emptransfer transfer : transferList) {
            ++rowNum;
            transfer.setEftId(rowNum + "");
            if (emptransferMap.containsKey(transfer.getEmployee().getEmpDistinctNo())) {
                ((List) emptransferMap.get(transfer.getEmployee().getEmpDistinctNo()))
                        .add(transfer);
            } else {
                List<Emptransfer> ecList = new ArrayList<Emptransfer>();
                ecList.add(transfer);
                emptransferMap.put(transfer.getEmployee().getEmpDistinctNo(), ecList);
            }

            String[] inputOldNameArr = new String[6];
            inputOldNameArr[0] = ((transfer.getEftOldDeptNo1() != null) ? transfer
                    .getEftOldDeptNo1().getDepartmentName() : null);
            inputOldNameArr[1] = ((transfer.getEftOldDeptNo2() != null) ? transfer
                    .getEftOldDeptNo2().getDepartmentName() : null);
            inputOldNameArr[2] = ((transfer.getEftOldDeptNo3() != null) ? transfer
                    .getEftOldDeptNo3().getDepartmentName() : null);
            inputOldNameArr[3] = ((transfer.getEftOldDeptNo4() != null) ? transfer
                    .getEftOldDeptNo4().getDepartmentName() : null);
            inputOldNameArr[4] = ((transfer.getEftOldDeptNo5() != null) ? transfer
                    .getEftOldDeptNo5().getDepartmentName() : null);
            String empOldDeptId = null;
            if ((transfer.getEftOldDeptNo() != null)
                    && (transfer.getEftOldDeptNo().getDepartmentName() != null)) {
                inputOldNameArr[5] = transfer.getEftOldDeptNo().getDepartmentName();
                empOldDeptId = deptBo.checkInputDeptLevel(deptLevelNameMap, inputOldNameArr);

                if (empOldDeptId == null)
                    commonParas.addErrorMessage(this.msgOldDeptLevelError, Integer.valueOf(rowNum),
                                                new String[] { "" });
                else {
                    transfer.getEftOldDeptNo().setId(empOldDeptId);
                }

            }

            if ((empOldDeptId != null) && (transfer.getEftOldPbId() != null)
                    && (transfer.getEftOldPbId().getPbName() != null)) {
                List pbList = (List) deptPBMap.get(empOldDeptId);
                PositionBase pb = getPBByNameFromList(pbList, transfer.getEftOldPbId().getPbName());
                if (pb != null)
                    transfer.setEftOldPbId(pb);
                else {
                    commonParas.addErrorMessage(this.msgNotExistOrStop, Integer.valueOf(rowNum),
                                                new String[] { Emptransfer.PROP_EFT_OLD_PB_NO + "."
                                                        + PositionBase.PROP_PB_NAME });
                }

            }

            String[] inputNewNameArr = new String[6];
            inputNewNameArr[0] = ((transfer.getEftNewDeptNo1() != null) ? transfer
                    .getEftNewDeptNo1().getDepartmentName() : null);
            inputNewNameArr[1] = ((transfer.getEftNewDeptNo2() != null) ? transfer
                    .getEftNewDeptNo2().getDepartmentName() : null);
            inputNewNameArr[2] = ((transfer.getEftNewDeptNo3() != null) ? transfer
                    .getEftNewDeptNo3().getDepartmentName() : null);
            inputNewNameArr[3] = ((transfer.getEftNewDeptNo4() != null) ? transfer
                    .getEftNewDeptNo4().getDepartmentName() : null);
            inputNewNameArr[4] = ((transfer.getEftNewDeptNo5() != null) ? transfer
                    .getEftNewDeptNo5().getDepartmentName() : null);
            String empNewDeptId = null;
            if ((transfer.getEftNewDeptNo() != null)
                    && (transfer.getEftNewDeptNo().getDepartmentName() != null)) {
                inputNewNameArr[5] = transfer.getEftNewDeptNo().getDepartmentName();
                empNewDeptId = deptBo.checkInputDeptLevel(deptLevelNameMap, inputNewNameArr);

                if (empNewDeptId == null)
                    commonParas.addErrorMessage(this.msgNewDeptLevelError, Integer.valueOf(rowNum),
                                                new String[] { "" });
                else
                    transfer.getEftNewDeptNo().setId(empNewDeptId);
            } else {
                commonParas.addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                            new String[] { Emptransfer.PROP_EFT_NEW_DEPT_NO + "."
                                                    + Department.PROP_DEPARTMENT_NAME });
            }

            if ((empNewDeptId != null) && (transfer.getEftNewPbId() != null)
                    && (transfer.getEftNewPbId().getPbName() != null)) {
                List pbList = (List) deptPBMap.get(empNewDeptId);
                PositionBase pb = getPBByNameFromList(pbList, transfer.getEftNewPbId().getPbName());
                if (pb != null)
                    transfer.setEftNewPbId(pb);
                else
                    commonParas.addErrorMessage(this.msgNotExistOrStop, Integer.valueOf(rowNum),
                                                new String[] { Emptransfer.PROP_EFT_NEW_PB_NO + "."
                                                        + PositionBase.PROP_PB_NAME });
            } else if ((transfer.getEftNewPbId() == null)
                    || (transfer.getEftNewPbId().getPbName() == null)) {
                commonParas.addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                            new String[] { Emptransfer.PROP_EFT_NEW_PB_NO + "."
                                                    + PositionBase.PROP_PB_NAME });
            }

            if ((empNewDeptId != null) && (transfer.getEftNewSupNo() != null)
                    && (transfer.getEftNewSupNo().getEmpDistinctNo() != null)) {
                String supDistinctNo = transfer.getEftNewSupNo().getEmpDistinctNo();
                Position supPos = (Position) empPosMap.get(supDistinctNo);
                if (supPos == null) {
                    commonParas.addErrorMessage(this.msgNotExistOrQuit, Integer.valueOf(rowNum),
                                                new String[] { supDistinctNo });
                }

            }

        }

        if (commonParas.getIoMessages().hasErrorMsg()) {
            return result;
        }

        BeanComparator comparator = new BeanComparator(Emptransfer.PROP_EFT_TRANSFER_DATE);
        List saveEmpList = new ArrayList();
        for (String empDistinctNo : emptransferMap.keySet()) {
            Employee emp = this.employeeBo.loadEmpByDistinctNo(empDistinctNo);
            List<Emptransfer> transList = (List) emptransferMap.get(empDistinctNo);
            if (emp == null) {
                for (Emptransfer transfer : transList) {
                    rowNum = Integer.parseInt(transfer.getEftId());
                    commonParas.addErrorMessage(this.msgNotExist, Integer.valueOf(rowNum),
                                                new String[] { Emptransfer.PROP_EMPLOYEE + "."
                                                        + Employee.PROP_EMP_DISTINCT_NO });
                }
            }

            for (Emptransfer transfer : transList) {
                transfer.setEmployee(emp);
            }

            List<Emptransfer> dbList = this.empTransferBo.getEftByEmp(emp);
            for (Emptransfer transfer : dbList) {
                transfer.setEftTransferDate(new Date(transfer.getEftTransferDate().getTime()));
            }
            for (Emptransfer transfer : transList) {
                dbList.add(transfer);
            }
            Collections.sort(dbList, comparator);

            for (int i = dbList.size() - 1; i > 0; --i) {
                Emptransfer transfer1 = (Emptransfer) dbList.get(i);
                Emptransfer transfer2 = (Emptransfer) dbList.get(i - 1);

                if (DateUtil.compareTwoDay(transfer1.getEftTransferDate(), transfer2
                        .getEftTransferDate()) == 0) {
                    if (transfer1.getEftId().length() < 30) {
                        result[1] += 1;
                        dbList.remove(i);
                        transList.remove(transfer1);
                    } else if (transfer2.getEftId().length() < 30) {
                        result[1] += 1;
                        dbList.remove(i - 1);
                        transList.remove(transfer2);
                    }

                } else {
                    if (transfer1.getEftId().length() > 30) {
                        continue;
                    }

                    if (transfer1.getEftOldPbId() == null) {
                        transfer1.setEftOldPbId(transfer2.getEftNewPbId());
                    }
                    if (transfer1.getEftOldDeptNo() == null)
                        transfer1.setEftOldDeptNo(transfer2.getEftNewDeptNo());
                }
            }
            Emptransfer transfer = (Emptransfer) dbList.get(0);

            if (transfer.getEftId().length() < 30) {
                if (transfer.getEftOldPbId() == null) {
                    transfer.setEftOldPbId(emp.getEmpPbNo());
                }
                if (transfer.getEftOldDeptNo() == null) {
                    transfer.setEftOldDeptNo(emp.getEmpDeptNo());
                }
            }

            transfer = (Emptransfer) dbList.get(dbList.size() - 1);
            emp.setEmpPbNo(transfer.getEftNewPbId());
            emp.setEmpDeptNo(transfer.getEftNewDeptNo());
            saveEmpList.add(emp);
        }

        if (commonParas.getIoMessages().hasErrorMsg()) {
            return result;
        }

        List savePosList = new ArrayList();
        List emphistorgList = new ArrayList();
        for (String empDistinctNo : emptransferMap.keySet()) {
            List transList = (List) emptransferMap.get(empDistinctNo);
            Collections.sort(transList, comparator);
            Employee emp = null;
            for (int i = 0; (transList != null) && (i < transList.size()); ++i) {
                Emptransfer transfer = (Emptransfer) transList.get(i);

                if (i == transList.size() - 1) {
                    emp = transfer.getEmployee();
                    Position nowPos = (Position) empPosMap.get(empDistinctNo);
                    if ((nowPos.getPositionPbId() != null)
                            && (nowPos.getPositionPbId().getPbDeptId() != null)
                            && (((!nowPos.getPositionPbId().getId().equals(
                                                                           transfer.getEftNewPbId()
                                                                                   .getId())) || (!nowPos
                                    .getPositionPbId().getPbDeptId().getId()
                                    .equals(transfer.getEftNewDeptNo()))))) {
                        nowPos.setPositionEmpNo(null);
                        savePosList.add(nowPos);

                        Object obj = posBo.putEmpToPB(dbPosList, filePosList, transfer
                                .getEmployee(), transfer.getEftNewPbId());
                        if (obj instanceof String) {
                            commonParas.addErrorMessage(this.msgPBExceed, null,
                                                        new String[] { empDistinctNo });
                        } else {
                            Position pos = (Position) obj;
                            if (pos.getId() == null) {
                                filePosList.add(pos);
                            } else {
                                dbPosList.remove(pos);
                                filePosList.add(pos);
                            }

                            empPosMap.put(pos.getPositionEmpNo().getEmpDistinctNo(), pos);
                            savePosList.add(pos);

                            String supEmpDistinctNo = (transfer.getEftNewSupNo() != null) ? transfer
                                    .getEftNewSupNo().getEmpDistinctNo()
                                    : null;

                            if (supEmpDistinctNo == null) {
                                Position deptRespPos = (Position) deptRespPosMap.get(emp
                                        .getEmpDeptNo().getId());
                                if (deptRespPos == null)
                                    commonParas.addErrorMessage(this.msgDeptNoRespPos, Integer
                                            .valueOf(rowNum), new String[] { emp.getEmpDeptNo()
                                            .getDepartmentName() });
                                else
                                    pos.setPositionParentId(deptRespPos);
                            } else {
                                Position parentPos = (Position) empPosMap.get(transfer
                                        .getEftNewSupNo().getEmpDistinctNo());
                                if (parentPos == null)
                                    commonParas.addErrorMessage(this.msgNotExistOrQuit, Integer
                                            .valueOf(rowNum), new String[] { transfer
                                            .getEftNewSupNo().getEmpDistinctNo() });
                                else if (!emp.getEmpDeptNo().getId()
                                        .equals(parentPos.getPositionPbId().getPbDeptId().getId()))
                                    commonParas.addErrorMessage(this.msgSupDeptError, Integer
                                            .valueOf(rowNum), new String[] { supEmpDistinctNo,
                                            emp.getEmpDeptNo().getDepartmentName() });
                                else {
                                    pos.setPositionParentId(parentPos);
                                }
                            }
                        }
                    }

                }

                if (emp != null) {
                    IEmpHistOrgBo emphistorgBo = (IEmpHistOrgBo) SpringBeanFactory
                            .getBean("empHistOrgBo");
                    List tempList = emphistorgBo.gernerateEmpHistOrg(emp, emp.getEmpDeptNo(), emp
                            .getEmpPbNo(), transfer.getEftTransferDate());
                    emphistorgList.addAll(tempList);
                }
            }

        }

        if (commonParas.getIoMessages().hasErrorMsg()) {
            return result;
        }

        if ((dbPosList.size() > 0) && (filePosList.size() > 0)) {
            boolean finish = false;
            while (!finish) {
                finish = true;
                Iterator itPos = filePosList.iterator();
                while (itPos.hasNext()) {
                    Position pos = (Position) itPos.next();
                    if (posBo.parentPosInList(dbPosList, pos)) {
                        finish = false;
                        dbPosList.add(pos);
                        itPos.remove();
                    }
                }
            }
        }

        if (filePosList.size() > 0) {
            String empDistinctNoStr = "";
            for (int i = 0; i < filePosList.size(); ++i) {
                Position pos = (Position) filePosList.get(i);
                String empDistinctNo = pos.getPositionEmpNo().getEmpDistinctNo();
                empDistinctNoStr = empDistinctNoStr + "," + empDistinctNo;
            }
            empDistinctNoStr = empDistinctNoStr.substring(1);
            commonParas.addErrorMessage(this.msgEmpCircle, null, new String[] { empDistinctNoStr });
        }

        if (commonParas.getIoMessages().hasErrorMsg()) {
            return result;
        }

        List saveTransList = new ArrayList();
        for (String empDistinctNo : emptransferMap.keySet()) {
            List<Emptransfer> transList = (List) emptransferMap.get(empDistinctNo);
            for (Emptransfer transfer : transList) {
                transfer.setEftId(null);
                transfer.setEftCreateBy(currEmp.getId());
                transfer.setEftCreateDate(nowDate);
                transfer.setEftLastChangeBy(currEmp.getId());
                transfer.setEftLastChangeTime(nowDate);
            }
            saveTransList.addAll(transList);
        }
        if (this.empTransferBo.saveImportList(saveTransList, saveEmpList, savePosList,
                                              emphistorgList)) {
            result[0] = saveTransList.size();
        }

        return result;
    }

    private PositionBase getPBByNameFromList(List<PositionBase> pbList, String pbName) {
        for (int i = 0; (pbList != null) && (i < pbList.size()); ++i) {
            PositionBase pb = (PositionBase) pbList.get(i);
            if (pb.getPbName().equals(pbName)) {
                return pb;
            }
        }

        return null;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.extend.IEmptransfer JD-Core Version: 0.5.4
 */