package com.hr.io.extend;

import com.hr.compensation.bo.IEmpSalaryAcctBo;
import com.hr.compensation.bo.IEmpSalaryAcctitemsBo;
import com.hr.compensation.bo.ISalaryconfBo;
import com.hr.compensation.domain.Empsalaryacct;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.configuration.bo.IJobgradeBO;
import com.hr.configuration.domain.Jobgrade;
import com.hr.io.domain.InmatchModel;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.SysConfigManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class IEmpSalaryConfig extends ICheckAndInsert {
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
    private ISalaryconfBo salaryconfBo;

    public IEmpSalaryConfig() {
        this.msgDbInsertError = "数据库插入失败！";
        this.msgGreaterThan = "{0}大于{1}＄1�7";
        this.msgExist = "{0}已存在！";
        this.msgNoNull = "{0}不能为空＄1�7";
        this.msgNotExist = "{0}不存在！";
        this.msgNotExistOrQuit = "{0}不存在或已经离职＄1�7";
        this.msgNotExistOrStop = "{0}不存在或已经被停用！";
        this.msgNumLimit = "导入员工数目超过限制＄1�7";
        this.msgCanNotQuit = "该员工有下属员工，无法离职！";
        this.msgQuitNeedInfo = "离职人员必须填写离职相关信息＄1�7";
        this.msgEmpCircle = "出现员工环，对应的行数为：{0}＄1�7";
        this.msgDbRepeat = "数据库数据出现重复，导入失败＄1�7";

        this.salaryconfBo = ((ISalaryconfBo) SpringBeanFactory.getBean("salaryconfBo"));
    }

    public int[] insertTransmit(List insertList, CommonParameters commonParas) throws Exception {
        List<Empsalaryconfig> originalList = insertList;
        Employee currEmp = commonParas.getCurrEmp();
        int[] result = { 0, 0 };

        SysConfigManager dbConfigManager = DatabaseSysConfigManager.getInstance();
        String updateConf = dbConfigManager.getProperty("sys.salary.config.update");

        Map jobgradeMap = getJobgradeMap();
        Map empsalaryacctMap = getEsaNameVersionMap();
        Map itemsMap = createAcctItemsMap();
        Map empDbMap = getEmployeeMap();

        Map escDbMap = new HashMap();
        escDbMap = getEmpsalaryconfigMap();

        List<Empsalaryconfig> configList = new ArrayList();
        int rowNum = 1 - commonParas.getInmatchModel().getImmNoTitle().intValue();
        for (Empsalaryconfig newConfig : originalList) {
            ++rowNum;
            Employee employee = newConfig.getEmployee();
            if ((employee == null) || (employee.getEmpDistinctNo() == null)) {
                commonParas.addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                            new String[] { Empsalaryconfig.PROP_EMPLOYEE + "."
                                                    + Employee.PROP_EMP_DISTINCT_NO });
            } else if (!empDbMap.containsKey(newConfig.getEmployee().getEmpDistinctNo())) {
                commonParas.addErrorMessage(this.msgNotExist, Integer.valueOf(rowNum),
                                            new String[] { Empsalaryconfig.PROP_EMPLOYEE + "."
                                                    + Employee.PROP_EMP_DISTINCT_NO });
            } else {
                Employee emp = (Employee) empDbMap.get(employee.getEmpDistinctNo());
                newConfig.setEmployee(emp);
                newConfig.setId(emp.getId());
            }

            Empsalaryconfig oldConfig = (Empsalaryconfig) escDbMap.get(newConfig.getId());
            if (oldConfig != null) {
                if ("0".equals(updateConf)) {
                    commonParas.addMessage(this.msgDbRepeat, Integer.valueOf(1), Integer
                            .valueOf(rowNum), new String[0]);
                    result[1] += 1;
                }

                oldConfig.decryEmpSalaryConf(oldConfig);
                commonParas.copy1To2(newConfig, oldConfig);
                newConfig = oldConfig;
            }

            Jobgrade jobgrade = newConfig.getEscJobgrade();
            if ((jobgrade == null) || (jobgrade.getJobGradeName() == null))
                commonParas.addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                            new String[] { Empsalaryconfig.PROP_ESC_JOBGRADE + "."
                                                    + Jobgrade.PROP_JOB_GRADE_NAME });
            else if (!jobgradeMap.containsKey(jobgrade.getJobGradeName()))
                commonParas.addErrorMessage(this.msgNotExist, Integer.valueOf(rowNum),
                                            new String[] { Empsalaryconfig.PROP_ESC_JOBGRADE + "."
                                                    + Jobgrade.PROP_JOB_GRADE_NAME });
            else {
                newConfig.setEscJobgrade((Jobgrade) jobgradeMap.get(jobgrade.getJobGradeName()));
            }

            Empsalaryacctversion esav = newConfig.getEscEsavId();
            if ((esav == null) || (esav.getEsavEsac() == null)
                    || (esav.getEsavEsac().getEsacName() == null)) {
                commonParas.addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                            new String[] { "escEsavId."
                                                    + Empsalaryacctversion.PROP_ESAV_ESAC + "."
                                                    + Empsalaryacct.PROP_ESAC_NAME });
            } else if (!empsalaryacctMap.containsKey(esav.getEsavEsac().getEsacName())) {
                commonParas.addErrorMessage(this.msgNotExist, Integer.valueOf(rowNum),
                                            new String[] { "escEsavId."
                                                    + Empsalaryacctversion.PROP_ESAV_ESAC + "."
                                                    + Empsalaryacct.PROP_ESAC_NAME });
            } else {
                newConfig.setEscEsavId((Empsalaryacctversion) empsalaryacctMap.get(esav
                        .getEsavEsac().getEsacName()));
                newConfig.setAcctItems((List) itemsMap.get(newConfig.getEscEsavId().getId()));
            }

            if (newConfig.getEscBankAccountNo() == null) {
                commonParas
                        .addErrorMessage(this.msgNoNull, Integer.valueOf(rowNum),
                                         new String[] { Empsalaryconfig.PROP_ESC_BANK_ACCOUNT_NO });
            }

            List acctItems = newConfig.getAcctItems();
            for (int i = 0; i < acctItems.size(); ++i) {
                Integer isCalc = ((Empsalaryacctitems) acctItems.get(i)).getEsaiDataIsCalc();
                try {
                    if ((isCalc.intValue() == 1) || (isCalc.intValue() == 2))
                        PropertyUtils.setProperty(newConfig, "escColumn" + (i + 1), new BigDecimal(
                                "0.00"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            configList.add(newConfig);
        }

        if (commonParas.getIoMessages().hasErrorMsg()) {
            return result;
        }

        this.salaryconfBo.interpretConfig((Empsalaryconfig[]) configList
                .toArray(new Empsalaryconfig[configList.size()]));
        result[0] = configList.size();

        for (Empsalaryconfig newConfig : configList) {
            this.salaryconfBo.saveOrUpdateEsc(newConfig, currEmp.getId(), newConfig.getEmployee());
        }

        return result;
    }

    private Map<String, Jobgrade> getJobgradeMap() {
        Map jobgradeTable = new HashMap();
        IJobgradeBO jobgradeBO = (IJobgradeBO) SpringBeanFactory.getBean("jobgradeBO");
        List jobgradeBOList = jobgradeBO.FindAllJobgrade();
        for (int i = 0; i < jobgradeBOList.size(); ++i) {
            Jobgrade jobgrade = (Jobgrade) jobgradeBOList.get(i);
            jobgradeTable.put(jobgrade.getJobGradeName(), jobgrade);
        }
        return jobgradeTable;
    }

    private Map<String, Empsalaryacctversion> getEsaNameVersionMap() {
        Map result = new HashMap();
        IEmpSalaryAcctBo esaBo = (IEmpSalaryAcctBo) SpringBeanFactory.getBean("empsalaryacctBo");
        List<Empsalaryacctversion> salaryAcctList = esaBo.searchAllEmpsalaryacctVersionInUse();
        for (Empsalaryacctversion esav : salaryAcctList) {
            result.put(esav.getEsavEsac().getEsacName(), esav);
        }
        return result;
    }

    private Map<String, List<Empsalaryacctitems>> createAcctItemsMap() {
        Map result = new HashMap();
        IEmpSalaryAcctitemsBo empSalaryAcctitemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");
        List<Empsalaryacctitems> acct = empSalaryAcctitemsBo.findAll();
        for (Empsalaryacctitems item : acct) {
            String key = item.getEsaiEsav().getId();
            if (result.containsKey(key)) {
                ((List) result.get(key)).add(item);
            } else {
                List tmpList = new ArrayList();
                tmpList.add(item);
                result.put(key, tmpList);
            }
        }
        return result;
    }

    private Map<String, Empsalaryconfig> getEmpsalaryconfigMap() {
        Map result = new HashMap();
        List<Empsalaryconfig> escDbList = this.salaryconfBo.getAllEmpsalaryconfig();
        for (Empsalaryconfig esc : escDbList) {
            result.put(esc.getId(), esc);
        }
        return result;
    }

    public Map<String, Employee> getEmployeeMap() {
        Map employeeHashtable = new HashMap();
        IEmployeeBo esaBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        DetachedCriteria dc = DetachedCriteria.forClass(Employee.class);
        dc.createAlias(Employee.PROP_EMP_BENEFIT_TYPE, "empBenefitType", 1);
        dc.createAlias(Employee.PROP_EMP_BENEFIT, "benefit", 1);
        dc.createAlias(Employee.PROP_EMP_DEPT_NO, "empDeptNo", 1);
        dc.createAlias(Employee.PROP_CONFIG, "config", 1);

        dc.add(Restrictions.eq(Employee.PROP_EMP_STATUS, Integer.valueOf(1)));
        List<Employee> empList = esaBo.findByCriteria(dc);
        for (Employee emp : empList) {
            employeeHashtable.put(emp.getEmpDistinctNo(), emp);
        }
        return employeeHashtable;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.extend.IEmpSalaryConfig JD-Core Version: 0.5.4
 */