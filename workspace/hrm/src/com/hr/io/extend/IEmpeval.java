package com.hr.io.extend;

import com.hr.configuration.domain.Department;
import com.hr.io.domain.InmatchModel;
import com.hr.profile.bo.IEmpEvalBo;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Empeval;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.PositionBase;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DateUtil;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class IEmpeval extends ICheckAndInsert {
    String msgDbInsertError;
    String msgGreaterThan;
    String msgExist;
    String msgNoNull;
    String msgNotExist;
    String msgNotValid;
    String msgNotExistOrQuit;
    String msgNotExistOrStop;
    String msgNumLimit;
    String msgCanNotQuit;
    String msgQuitNeedInfo;
    String msgEmpCircle;
    String msgEvalTestOnlyOne;
    private IEmpEvalBo evaldBo;

    public IEmpeval() {
        this.msgDbInsertError = "数据库插入失贄1�7";

        this.msgGreaterThan = "{0}大于{1}";

        this.msgExist = "{0}已存圄1�7";

        this.msgNoNull = "{0}不能为空";

        this.msgNotExist = "{0}不存圄1�7";

        this.msgNotValid = "{0}不正硄1�7";

        this.msgNotExistOrQuit = "{0}不存在或已经离职";

        this.msgNotExistOrStop = "{0}不存在或已经被停甄1�7";

        this.msgNumLimit = "导入员工数目超过限制";

        this.msgCanNotQuit = "该员工有下属员工，无法离聄1�7";

        this.msgQuitNeedInfo = "离职人员必须填写离职相关信息";

        this.msgEmpCircle = "出现员工环，对应的行数为：{0}";

        this.msgEvalTestOnlyOne = "试用期�1�7�评只能有一条记彄1�7";

        this.evaldBo = ((IEmpEvalBo) SpringBeanFactory.getBean("empEvalBo"));
    }

    public int[] insertTransmit(List insertList, CommonParameters commonParas) throws Exception {
        Employee currEmp = commonParas.getCurrEmp();
        int[] result = { 0, 0 };
        Date nowDate = new Date();
        List<Empeval> evalList = insertList;

        Map pbMap = getNameIdMap("PositionBase", "id", "pbName", new String[] { "pbStatus=1" });

        Map departmentMap = getNameIdMap(Department.REF, Department.PROP_ID,
                                         Department.PROP_DEPARTMENT_NAME,
                                         new String[] { Department.PROP_DEPARTMENT_STATUS + "=1" });

        int rowNum = 1 - commonParas.getInmatchModel().getImmNoTitle().intValue();
        for (Empeval empeval : evalList) {
            ++rowNum;
            empeval.setEeId(rowNum + "");
        }

        for (Empeval empeval : evalList) {
            rowNum = Integer.parseInt(empeval.getEeId());

            if ((empeval.getEePbNo() != null) && (empeval.getEePbNo().getPbName() != null)) {
                if (pbMap.containsKey(empeval.getEePbNo().getPbName()))
                    empeval.getEePbNo().setId((String) pbMap.get(empeval.getEePbNo().getPbName()));
                else {
                    commonParas.addErrorMessage(this.msgNotExistOrStop, Integer.valueOf(rowNum),
                                                new String[] { Empeval.PROP_EE_PB_NO + ".pbName" });
                }
            }

            if ((empeval.getDepartment() != null)
                    && (empeval.getDepartment().getDepartmentName() != null)) {
                if (departmentMap.containsKey(empeval.getDepartment().getDepartmentName()))
                    empeval.getDepartment().setId(
                                                  (String) departmentMap.get(empeval
                                                          .getDepartment().getDepartmentName()));
                else {
                    commonParas.addErrorMessage(this.msgNotExistOrStop, Integer.valueOf(rowNum),
                                                new String[] { Empeval.PROP_DEPARTMENT + "."
                                                        + Department.PROP_DEPARTMENT_NAME });
                }
            }

            Employee emp = this.employeeBo.loadEmpByDistinctNo(empeval.getEmployeeByEeEmpNo()
                    .getEmpDistinctNo());
            if (emp == null) {
                commonParas.addErrorMessage(this.msgNotExist, Integer.valueOf(rowNum),
                                            new String[] { Empeval.PROP_EMPLOYEE_BY_EE_EMP_NO + "."
                                                    + Employee.PROP_EMP_DISTINCT_NO });
            } else {
                empeval.setEmployeeByEeEmpNo(emp);
            }

            Employee supEmp = this.employeeBo.loadEmpByDistinctNo(empeval.getEmployeeByEeManager()
                    .getEmpDistinctNo());
            if (supEmp == null) {
                commonParas.addErrorMessage(this.msgNotExist, Integer.valueOf(rowNum),
                                            new String[] { Empeval.PROP_EMPLOYEE_BY_EE_MANAGER
                                                    + "." + Employee.PROP_EMP_DISTINCT_NO });
            } else {
                empeval.setEmployeeByEeManager(supEmp);
            }

            if (empeval.getEeStartDate() == null) {
                commonParas.addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                            new String[] { Empeval.PROP_EE_START_DATE });
            }

            if (empeval.getEeEndDate() == null) {
                commonParas.addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                            new String[] { Empeval.PROP_EE_END_DATE });
            }

            if (DateUtil.compareTwoDay(empeval.getEeEndDate(), empeval.getEeStartDate()) >= 0) {
                commonParas.addErrorMessage(this.msgGreaterThan, Integer.valueOf(rowNum),
                                            new String[] { Empeval.PROP_EE_START_DATE,
                                                    Empeval.PROP_EE_END_DATE });
            }

            Date[] startEndDate = empeval.getStartEndDate(empeval.getEeStartDate());
            if (DateUtil.compareTwoDay(empeval.getEeStartDate(), startEndDate[0]) != 0) {
                commonParas.addErrorMessage(this.msgNotValid, Integer.valueOf(rowNum),
                                            new String[] { Empeval.PROP_EE_START_DATE });
            }
            if (DateUtil.compareTwoDay(empeval.getEeEndDate(), startEndDate[1]) != 0) {
                commonParas.addErrorMessage(this.msgNotValid, Integer.valueOf(rowNum),
                                            new String[] { Empeval.PROP_EE_END_DATE });
            }
        }

        if (commonParas.getIoMessages().hasErrorMsg()) {
            return result;
        }

        for (Empeval empeval : evalList) {
            int rowNum1 = Integer.parseInt(empeval.getEeId());
            empeval.setEeId(null);

            if (this.evaldBo.repeatInTimeNum(empeval) > 0) {
                result[1] += 1;
            }

            if ((empeval.getEeType().compareTo("试用朄1�7") == 0)
                    && (this.evaldBo.getEmpevalInTest(empeval.getEmployeeByEeEmpNo()
                            .getEmpDistinctNo()) != null)) {
                commonParas.addErrorMessage(this.msgEvalTestOnlyOne, Integer.valueOf(rowNum),
                                            new String[0]);
                break;
            }

            empeval.setEeCreateBy(currEmp.getId());
            empeval.setEeCreateDate(nowDate);
            empeval.setEeLastChangeBy(currEmp.getId());
            empeval.setEeLastChangeTime(nowDate);
            if (this.evaldBo.insertEval(empeval)) {
                result[0] += 1;
            } else {
                commonParas.addErrorMessage(this.msgDbInsertError, Integer.valueOf(rowNum1),
                                            new String[0]);
                break;
            }

        }

        return result;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.extend.IEmpeval JD-Core Version: 0.5.4
 */