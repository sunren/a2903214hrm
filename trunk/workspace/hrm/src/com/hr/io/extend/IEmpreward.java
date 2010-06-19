package com.hr.io.extend;

import com.hr.configuration.domain.Department;
import com.hr.io.domain.InmatchModel;
import com.hr.profile.bo.IEmpRewardBo;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Empreward;
import com.hr.profile.domain.PositionBase;
import com.hr.spring.SpringBeanFactory;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class IEmpreward extends ICheckAndInsert {
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
    private IEmpRewardBo rewardBo;

    public IEmpreward() {
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

        this.msgEmpCircle = "出现员工环，对应的行数为：{0}";

        this.rewardBo = ((IEmpRewardBo) SpringBeanFactory.getBean("empRewardBo"));
    }

    public int[] insertTransmit(List insertList, CommonParameters commonParas) throws Exception {
        Employee currEmp = commonParas.getCurrEmp();
        int[] result = { 0, 0 };
        Date nowDate = new Date();
        List<Empreward> emprewardList = insertList;

        Map pbMap = getNameIdMap("PositionBase", "id", "pbName", new String[] { "pbStatus=1" });

        Map departmentMap = getNameIdMap(Department.REF, Department.PROP_ID,
                                         Department.PROP_DEPARTMENT_NAME,
                                         new String[] { Department.PROP_DEPARTMENT_STATUS + "=1" });

        int rowNum = 1 - commonParas.getInmatchModel().getImmNoTitle().intValue();
        for (Empreward empreward : emprewardList) {
            ++rowNum;
            empreward.setErId(rowNum + "");
        }

        for (Empreward empreward : emprewardList) {
            rowNum = Integer.parseInt(empreward.getErId());

            Employee emp = this.employeeBo.loadEmpByDistinctNo(empreward.getEmployee()
                    .getEmpDistinctNo());
            if (emp == null) {
                commonParas.addErrorMessage(this.msgNotExist, Integer.valueOf(rowNum),
                                            new String[] { Empreward.PROP_EMPLOYEE + "."
                                                    + Employee.PROP_EMP_DISTINCT_NO });
            } else {
                empreward.setEmployee(emp);
            }

            if ((empreward.getErPbNo() != null) && (empreward.getErPbNo().getPbName() != null)) {
                if (pbMap.containsKey(empreward.getErPbNo().getPbName()))
                    empreward.getErPbNo().setId(
                                                (String) pbMap.get(empreward.getErPbNo()
                                                        .getPbName()));
                else {
                    commonParas
                            .addErrorMessage(this.msgNotExistOrStop, Integer.valueOf(rowNum),
                                             new String[] { Empreward.PROP_ER_PB_NO + ".pbName" });
                }
            }

            if ((empreward.getDepartment() != null)
                    && (empreward.getDepartment().getDepartmentName() != null)) {
                if (departmentMap.containsKey(empreward.getDepartment().getDepartmentName()))
                    empreward.getDepartment().setId(
                                                    (String) departmentMap.get(empreward
                                                            .getDepartment().getDepartmentName()));
                else {
                    commonParas.addErrorMessage(this.msgNotExistOrStop, Integer.valueOf(rowNum),
                                                new String[] { Empreward.PROP_DEPARTMENT + "."
                                                        + Department.PROP_DEPARTMENT_NAME });
                }
            }
            empreward.setErCreateBy(currEmp.getId());
            empreward.setErLastChangeBy(currEmp.getId());
            empreward.setErCreateDate(nowDate);
            empreward.setErLastChangeTime(nowDate);
        }

        if (commonParas.getIoMessages().hasErrorMsg()) {
            return result;
        }

        for (Empreward empreward : emprewardList) {
            int rowNum1 = Integer.parseInt(empreward.getErId());
            empreward.setErId(null);

            if (this.rewardBo.insertEmp(empreward)) {
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
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.io.extend.IEmpreward JD-Core Version: 0.5.4
 */