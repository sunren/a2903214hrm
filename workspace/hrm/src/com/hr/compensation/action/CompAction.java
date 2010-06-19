package com.hr.compensation.action;

import com.hr.base.BaseDownloadAction;
import com.hr.compensation.bo.IEmpBenefitBo;
import com.hr.compensation.bo.IEmpBenefitPlanBo;
import com.hr.compensation.bo.IEmpSalaryAcctitemsBo;
import com.hr.compensation.bo.IEmpsalaryperiodBo;
import com.hr.compensation.bo.ISalaryPaidBo;
import com.hr.compensation.bo.ISalaryconfBo;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Location;
import com.hr.configuration.domain.Statusconf;
import com.hr.configuration.domain.StatusconfPK;
import com.hr.examin.bo.interfaces.IAttendmonthlyBO;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.InterpreterExecuteContext;
import com.hr.util.ObjectUtil;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.util.ObjectUtils;

public class CompAction extends BaseDownloadAction {
    private static final long serialVersionUID = 1L;
    public String msgNoAuth = "{0}:您无权执行此操作，请重新登陆＄1�7";
    public String msgNoParam = "{0}:输入参数错误，请刷新后重试！";
    public String msgFormatErr = "{0}:{1}数据格式错误＄1�7";
    public String msgOperErr = "{0}:{1}操作失败＄1�7";
    public String msgOperSucc = "{0}:{1}操作成功＄1�7";
    public String msgNoData = "{0}:没有{1}数据，请刷新后重试！";
    public String msgSystemEx = "系统异常，请刷新后重试！";
    public String msgDataEx = "数据异常，请刷新后重试！";
    public String msgParamEx = "参数异常，请刷新后重试！";

    public String msgNoConfig = "{0}:{1}薪资配置数据错误，请刷新后重试！";
    public String msgHasConfig = "{0}:{1}薪资配置数据已经存在，创建失败！";
    public String msgCreateConfSucc = "{0}:创建{1}的薪资配置成功�1�7�1�7";
    public String msgCreateConfFail = "{0}:创建{1}的薪资配置失败！";
    public String msgUpdConfSucc = "{0}:已调整{1}的薪资配置�1�7�1�7";
    public String msgUpdConfFail = "{0}:调整{1}的薪资配置失败！";
    public String msgDelConfSucc = "{0}:已删除{1}的薪资配置�1�7�1�7";
    public String msgDelConfFail = "{0}:{1}删除{2}的薪资配置失败！";
    public String msgAdjConfSucc = "{0}:调整{1}的薪资配置成功�1�7�1�7";

    public String msgNoEmp = "{0}:员工错误，请刷新后重试！";
    public String msgNoPay = "{0}:薪资发放数据错误，请刷新后重试！";
    public String msgAdjPaySucc = "{0}:调整{1}的薪资发放成功�1�7�1�7";
    public String msgFormulaIllegal = "您输入的公式{0}不合法：{1}";
    public String msgFormulaLegal = "{0}:您输入的公式{1}合法〄1�7";

    public String msgNoBenefit = "{0}:找不到员工社保记录，请重试！";
    public String msghasBenefit = "{0}:员工社保记录已经存在，请重试＄1�7";
    public String msgNoBaseErr = "{0}:请至少填写一项基数！";
    public String msgStartYMTooEarly = "新记录的起始年月必须晚于老记录的起始年月＄1�7";
    public String msgCreateBeneSucc = "{0}:创建{1}的社保记录成功�1�7�1�7";
    public String msgUpdBeneSucc = "{0}:修改{1}的社保记录成功�1�7�1�7";
    public String msgAdjBeneSucc = "{0}:调整{1}的社保基数成功�1�7�1�7";
    public String msgNoYmBenefitC = "{0}:不能补缴{1}社保，应正常缴纳＄1�7";
    public String msgNoYmBenefitP = "{0}:本月已补过{1}社保，不能重复补缴！";
    public String msgStatErrBeneC = "{0}:计薪周期{1}已提交或已封帐，不能补缴社保＄1�7";
    public String msgStatErrBeneP = "{0}:计薪周期{1}尚未封帐，不能补缴社保！";
    public String msgAddBeneSucc = "{0}:补缴社保成功，您必须重新初始化{1}的{2}薪资，改动才会生效�1�7�1�7";

    public String msgNoYm = "{0}:年月数据错误，请刷新后重试！";

    public String msgPeriodInitErr = "计薪周期必须为初始化状�1�7�，才可以执行本操作＄1�7";
    public String msgPeriodSubmitErr = "计薪周期必须为已提交状�1�7�，才可以执行本操作＄1�7";
    public String msgPeriodAppErr = "计薪周期必须为已封帐状�1�7�，才可以执行本操作＄1�7";
    public String msgPeriodErr = "计薪周期状�1�7�不正确，请刷新后重试！";

    public String msgAcctUpdConf = "{0}:帐套版本已启用，系统将自动更新员工薪资配置，您确定要修改吗？";
    public String msgAcctUpdConfPay = "{0}:帐套版本已启用，系统将自动更新员工薪资配置，并调整薪资发放历史，您确定要修改吗？";
    public String msgAcctUpdErr = "{0}:帐套版本有重大改变，薪资发放历史无法修正，请点击“另存�1�7�按钮，保存为新版本＄1�7";
    protected List<Department> departs;
    protected List<Emptype> empTypeList;
    protected List<Statusconf> empStatus;
    protected List<Location> locationList;

    public Empsalaryconfig loadConfig(String empId, String configId) {
        Empsalaryconfig config;
        if (empId != null) {
            IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
            String[] fetch = { Employee.PROP_EMP_BENEFIT_TYPE, Employee.PROP_EMP_BENEFIT,
                    Employee.PROP_EMP_DEPT_NO, Employee.PROP_CONFIG,
                    Employee.PROP_CONFIG + "." + Empsalaryconfig.PROP_ESC_JOBGRADE,
                    Employee.PROP_CONFIG + "." + Empsalaryconfig.PROP_ESC_ESAV_ID };

            Employee emp = empBo.loadEmp(empId, fetch);
            if (emp.getConfig() == null) {
                config = new Empsalaryconfig();
                config.setEmployee(emp);
            } else {
                config = emp.getConfig();
            }
        } else {
            ISalaryconfBo configBo = (ISalaryconfBo) SpringBeanFactory.getBean("salaryconfBo");
            config = configBo.loadSalaryconf(configId, new String[] {
                    Empsalaryconfig.PROP_ESC_JOBGRADE, Empsalaryconfig.PROP_ESC_ESAV_ID,
                    Empsalaryconfig.PROP_EMPLOYEE,
                    Empsalaryconfig.PROP_EMPLOYEE + "." + Employee.PROP_EMP_BENEFIT_TYPE,
                    Empsalaryconfig.PROP_EMPLOYEE + "." + Employee.PROP_EMP_BENEFIT,
                    Empsalaryconfig.PROP_EMPLOYEE + "." + Employee.PROP_EMP_DEPT_NO });

            if (config == null) {
                return null;
            }
        }
        config.decryEmpSalaryConf(config);
        return config;
    }

    protected Empsalarypay loadPay(String payId, String empId, String yearmonth) {
        ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) getBean("salaryPaidBo");
        Empsalarypay pay = new Empsalarypay();

        if (!StringUtils.isEmpty(payId)) {
            pay = salaryPaidBo.loadSalaryPay(payId, new String[] { Empsalarypay.PROP_ESP_JOBGRADE,
                    Empsalarypay.PROP_ESP_ESAV_ID, Empsalarypay.PROP_ESP_EMPCONFIG,
                    Empsalarypay.PROP_ESP_EMPNO,
                    Empsalarypay.PROP_ESP_EMPNO + "." + Employee.PROP_EMP_BENEFIT_TYPE });

            if (pay == null)
                return null;

            pay.decryEMPPaid(pay);
            Empsalaryconfig config = pay.getEspEmpconfig();
            if (config != null)
                config.decryEmpSalaryConf(config);

        } else {
            IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
            String[] fetch = { Employee.PROP_EMP_BENEFIT_TYPE, Employee.PROP_CONFIG,
                    Employee.PROP_CONFIG + "." + Empsalaryconfig.PROP_ESC_JOBGRADE,
                    Employee.PROP_CONFIG + "." + Empsalaryconfig.PROP_ESC_ESAV_ID };

            Employee emp = empBo.loadEmp(empId, fetch);

            Empsalaryconfig config = emp.getConfig();
            if (config == null)
                return null;

            config.decryEmpSalaryConf(config);
            pay = copyConfigToPay(config);
            pay.setEspYearmonth(yearmonth);
        }
        return pay;
    }

    public static DetachedCriteria updatePayBatch_DC() {
        DetachedCriteria dc = DetachedCriteria.forClass(Empsalarypay.class);
        dc.createAlias(Empsalarypay.PROP_ESP_EMPNO, "emp", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_BENEFIT_TYPE, "empBenefitType", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_DEPT_NO, "empDeptNo", 1);
        dc.createAlias(Empsalarypay.PROP_ESP_EMPCONFIG, "config", 1);
        dc.createAlias("config." + Empsalaryconfig.PROP_ESC_JOBGRADE, "escJobgrade", 1);
        dc.createAlias("config." + Empsalaryconfig.PROP_ESC_ESAV_ID, "escEsavId", 1);
        return dc;
    }

    public static DetachedCriteria updateConfigBatch_DC() {
        DetachedCriteria dc = DetachedCriteria.forClass(Employee.class);
        dc.createAlias(Employee.PROP_EMP_BENEFIT_TYPE, "empBenefitType", 1);
        dc.createAlias(Employee.PROP_EMP_BENEFIT, "benefit", 1);
        dc.createAlias(Employee.PROP_EMP_DEPT_NO, "empDeptNo", 1);
        dc.createAlias(Employee.PROP_CONFIG, "config", 0);
        dc.createAlias("config." + Empsalaryconfig.PROP_ESC_JOBGRADE, "escJobgrade", 1);
        dc.createAlias("config." + Empsalaryconfig.PROP_ESC_ESAV_ID, "escEsavId", 1);
        return dc;
    }

    protected boolean setPayAdd(String yearmonth, Empsalarypay[] payArray) {
        if ((StringUtils.isEmpty(yearmonth)) || (ObjectUtils.isEmpty(payArray)))
            return false;

        IEmpSalaryAcctitemsBo esaiBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");
        if (!esaiBo.setAcctItemsPay(payArray))
            return false;

        IEmpBenefitBo empbenefitBo = (IEmpBenefitBo) SpringBeanFactory.getBean("empbenefitBo");
        empbenefitBo.getEmpBenefitNew(yearmonth, payArray);

        IAttendmonthlyBO attendmonBo = (IAttendmonthlyBO) SpringBeanFactory
                .getBean("attendmonthlyBo");
        attendmonBo.setAttmByPay(yearmonth, payArray);

        IEmpBenefitPlanBo ebpBo = (IEmpBenefitPlanBo) SpringBeanFactory.getBean("empbenefitplanBo");
        ebpBo.setAddEbpSum(yearmonth, payArray);

        return true;
    }

    protected boolean setPayValues(String salaryValue, Empsalarypay pay) {
        if ((StringUtils.isEmpty(salaryValue)) || (pay == null))
            return false;
        Empsalaryconfig config = pay.getEspEmpconfig();
        List acctItems = pay.getAcctItems();

        pay.setEspChanged(Integer.valueOf(0));
        try {
            String[] salary = salaryValue.split(",");
            List salaryValueItems = new ArrayList();
            for (int i = 0; i < salary.length; ++i) {
                salaryValueItems.add(new BigDecimal(salary[i]));
            }

            for (int i = 0; i < acctItems.size(); ++i) {
                Empsalaryacctitems item = (Empsalaryacctitems) acctItems.get(i);

                if (item.getEsaiDataIsCalc().intValue() == 2) {
                    continue;
                }
                BigDecimal newValue = (BigDecimal) salaryValueItems.get(i);
                if (changePayItem(pay, config, newValue, i + 1))
                    pay.setEspChanged(Integer.valueOf(1));
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    protected boolean interpretPay(String yearmonth, String currentEmp, boolean add0And1,
            Empsalarypay[] payArray) {
        if ((StringUtils.isEmpty(yearmonth)) || (ObjectUtils.isEmpty(payArray)))
            return false;

        InterpreterExecuteContext context = new InterpreterExecuteContext(yearmonth);
        context.execute(currentEmp, add0And1, payArray);
        return true;
    }

    protected List<Empsalaryacctitems> getPayItemsPage(Empsalarypay pay) {
        if (pay == null)
            return null;

        List acctItems = pay.getAcctItems();
        Empsalaryconfig config = pay.getEspEmpconfig();
        try {
            for (int i = 0; i < acctItems.size(); ++i) {
                Empsalaryacctitems item = (Empsalaryacctitems) acctItems.get(i);
                Object escValue = PropertyUtils.getProperty(config, "escColumn" + (i + 1));
                Object espValue = PropertyUtils.getProperty(pay, "espColumn" + (i + 1));

                if ((escValue != null) && (espValue != null)) {
                    item.setItemValue((BigDecimal) escValue);
                    item.setItemConfigValue((BigDecimal) espValue);
                    if (!escValue.equals(espValue))
                        item.setItemChanged(Integer.valueOf(1));
                } else if (escValue != null) {
                    item.setItemValue((BigDecimal) escValue);
                    item.setItemConfigValue(new BigDecimal(0.0D));
                    item.setItemChanged(Integer.valueOf(1));
                } else if (espValue != null) {
                    item.setItemValue(new BigDecimal(0.0D));
                    item.setItemConfigValue((BigDecimal) espValue);
                    item.setItemChanged(Integer.valueOf(1));
                } else {
                    item.setItemValue(new BigDecimal(0.0D));
                    item.setItemConfigValue(new BigDecimal(0.0D));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return acctItems;
    }

    public Empsalarypay copyConfigToPay(Empsalaryconfig config) {
        Empsalarypay pay = new Empsalarypay();
        if (config == null)
            return null;

        try {
            for (int i = 1; i <= 48; ++i) {
                Object value = PropertyUtils.getProperty(config, "escColumn" + i);
                PropertyUtils.setProperty(pay, "espColumn" + i, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        pay.setEspEmpconfig(config);
        pay.setEspEmpno(config.getEmployee());
        pay.setEspEsavId(config.getEscEsavId());
        pay.setEspJobgrade(config.getEscJobgrade());

        pay.setEspDept(config.getEmployee().getEmpDeptNo());
        pay.setEspEmptype(config.getEmployee().getEmpType());
        pay.setEspLocation(config.getEmployee().getEmpLocationNo());
        pay.setEspPbNo(config.getEmployee().getEmpPbNo());

        pay.setEspBankAccountNo(config.getEscBankAccountNo());
        pay.setEspBankName(config.getEscBankName());
        pay.setEspCostCenter(config.getEscCostCenter());

        return pay;
    }

    public String exportData(List exportDataList, String outputIoName, String outmatchModelId,
            String errorMsg) {
        String msgNoData = "没有{0}数据可以导出＄1�7";
        String msgError = "{0}数据导出失败＄1�7";

        if ((exportDataList == null) || (exportDataList.size() == 0)) {
            addActionErrorInfo(msgNoData, new Object[] { errorMsg });
            return "success";
        }

        if (!excelDownload(exportDataList, outputIoName, outmatchModelId)) {
            addActionErrorInfo(msgError, new Object[] { errorMsg });
            return "success";
        }

        clearErrorsAndMessages();
        return "download";
    }

    public boolean checkSalaryPeriod(String yearmonth, Integer[] status) {
        IEmpsalaryperiodBo periodBo = (IEmpsalaryperiodBo) SpringBeanFactory
                .getBean("empsalaryperiodBO");
        Integer currStatus = periodBo.getEspdStatusNew(yearmonth);

        return ObjectUtil.contains(status, currStatus);
    }

    public String getEmpType(String id) {
        if (StringUtils.isEmpty(id))
            return "";
        for (Emptype empType : this.empTypeList) {
            if (empType.getId().equals(id))
                return empType.getEmptypeName();
        }
        return "";
    }

    public String getLocation(String id) {
        if (StringUtils.isEmpty(id))
            return "";
        for (Location location : this.locationList) {
            if (location.getId().equals(id))
                return location.getLocationName();
        }
        return "";
    }

    public String getDepName(String id) {
        if (StringUtils.isEmpty(id))
            return "";
        for (Department dep : this.departs) {
            if (dep.getId().equals(id))
                return dep.getDepartmentName();
        }
        return "";
    }

    public String getEmpStatusName(int id) {
        for (Statusconf statusconf : this.empStatus) {
            if (statusconf.getId().getStatusconfNo().intValue() == id) {
                return statusconf.getStatusconfDesc();
            }
        }
        return "无状怄1�7";
    }

    public static boolean changePayItem(Empsalarypay pay, Empsalaryconfig config,
            Empsalarypay newPay, int index) throws Exception {
        Object newPayValue = PropertyUtils.getProperty(newPay, "espColumn" + index);
        if (newPayValue == null) {
            return changePayItem(pay, config, new BigDecimal(0.0D), index);
        }
        return changePayItem(pay, config, (BigDecimal) newPayValue, index);
    }

    public static boolean changePayItem(Empsalarypay pay, Empsalaryconfig config,
            BigDecimal newPayValue, int index) throws Exception {
        Object escValue = PropertyUtils.getProperty(config, "escColumn" + index);
        PropertyUtils.setProperty(pay, "espColumn" + index, newPayValue.setScale(2));

        if ((escValue == null) && (newPayValue.compareTo(new BigDecimal(0.0D)) == 0)) {
            return false;
        }

        return newPayValue.compareTo((BigDecimal) escValue) != 0;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.action.CompAction JD-Core Version: 0.5.4
 */