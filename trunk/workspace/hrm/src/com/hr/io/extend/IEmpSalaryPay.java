package com.hr.io.extend;

import com.hr.compensation.action.CompAction;
import com.hr.compensation.bo.IEmpBenefitBo;
import com.hr.compensation.bo.IEmpBenefitPlanBo;
import com.hr.compensation.bo.IEmpSalaryAcctitemsBo;
import com.hr.compensation.bo.IEmpsalaryperiodBo;
import com.hr.compensation.bo.ISalaryPaidBo;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.examin.bo.interfaces.IAttendmonthlyBO;
import com.hr.hibernate.IHibernateUtil;
import com.hr.io.domain.InmatchModel;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.BaseCrit;
import com.hr.util.InterpreterExecuteContext;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.util.ObjectUtils;

public class IEmpSalaryPay extends ICheckAndInsert {
    String msgNoPayExist;
    String msgEmpNotExist;
    String msgPayNotInited;
    String msgItemCannotnull;

    public IEmpSalaryPay() {
        this.msgNoPayExist = "请先初始化要导入员工的薪资数据！";
        this.msgEmpNotExist = "员工编号为{0}的员工不存在";
        this.msgPayNotInited = "员工编号为{0}的员工本月薪资未初始化";
        this.msgItemCannotnull = "{0}为固定项或浮动项不能为空";
    }

    public int[] insertTransmit(List insertList, CommonParameters commonParas) throws Exception {
        String msgPeriodInitErr = "计薪周期必须为初始化状态，才可以执行本操作！";
        String msgNoPayExist = "找不到可以导入的薪资发放数据，请先初始化薪资！";
        String msgNoAcctExist = "薪资帐套异常，不能导入员工的薪资发放数据！";

        List<Empsalarypay> excelDataList = insertList;
        String yearmonth = commonParas.getImportParameter();
        int hasTitle = 1 - commonParas.getInmatchModel().getImmNoTitle().intValue();
        int rowNum = hasTitle + 1;
        int[] result = { 0, 0 };

        IEmpsalaryperiodBo periodBo = (IEmpsalaryperiodBo) SpringBeanFactory
                .getBean("empsalaryperiodBO");
        if (!new Integer(0).equals(periodBo.getEspdStatusNew(yearmonth))) {
            commonParas.addErrorMessage(msgPeriodInitErr, null, new String[0]);
            return result;
        }

        List payList = updatePayBatch_DC(yearmonth);
        if (payList.size() == 0) {
            commonParas.addErrorMessage(msgNoPayExist, null, new String[0]);
            return result;
        }

        Map existEmpMap = getEmpDistinctNoMap();
        Map existPayMap = convertPayListToMap(payList);

        for (Empsalarypay excelpay : excelDataList) {
            String empDistinctNo = excelpay.getEspEmpno().getEmpDistinctNo();

            if (!existEmpMap.containsKey(empDistinctNo)) {
                commonParas.addErrorMessage(this.msgEmpNotExist, Integer.valueOf(rowNum),
                                            new String[] { empDistinctNo });
                ++rowNum;
            }

            if (!existPayMap.containsKey(empDistinctNo)) {
                commonParas.addErrorMessage(this.msgPayNotInited, Integer.valueOf(rowNum),
                                            new String[] { empDistinctNo });
                ++rowNum;
            }

            ++rowNum;
        }

        if (commonParas.getIoMessages().hasErrorMsg()) {
            return result;
        }

        Map excelDataMap = convertPayListToMap(excelDataList);
        for (Iterator it = payList.iterator(); it.hasNext();) {
            Empsalarypay pay = (Empsalarypay) it.next();

            if (excelDataMap.containsKey(pay.getEspEmpno().getEmpDistinctNo()))
                continue;
            it.remove();
        }

        Empsalarypay[] payArray = (Empsalarypay[]) payList
                .toArray(new Empsalarypay[payList.size()]);

        if (!setPayAdd(yearmonth, payArray)) {
            commonParas.addErrorMessage(msgNoAcctExist, null, new String[0]);
            return result;
        }

        setPayValues(excelDataMap, payArray);

        interpretPay(yearmonth, commonParas.getCurrEmp().getId(), true, (Empsalarypay[]) payList
                .toArray(new Empsalarypay[payList.size()]));
        if (payList.size() == 0) {
            commonParas.addErrorMessage(msgNoPayExist, null, new String[0]);
            return result;
        }

        ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) SpringBeanFactory.getBean("salaryPaidBo");
        boolean insert = salaryPaidBo.batchUpdatePayImport(yearmonth, payList);
        if (insert) {
            result[0] = excelDataList.size();
        }

        return result;
    }

    private Map<String, Empsalarypay> convertPayListToMap(List<Empsalarypay> payList) {
        Map payMap = new HashMap();
        for (Empsalarypay pay : payList) {
            payMap.put(pay.getEspEmpno().getEmpDistinctNo(), pay);
        }
        return payMap;
    }

    private Map<String, Employee> getEmpDistinctNoMap() {
        IHibernateUtil dao = (IHibernateUtil) SpringBeanFactory.getBean("dao");
        String hql = "select empDistinctNo, id from Employee";
        List empList = dao.exeHqlList(hql);
        Object[] info = null;
        Map empMap = new HashMap();
        for (int i = 0; i < empList.size(); ++i) {
            info = (Object[]) (Object[]) empList.get(i);
            empMap.put((String) info[0], new Employee((String) info[1]));
        }

        return empMap;
    }

    private List<Empsalarypay> updatePayBatch_DC(String yearmonth) {
        DetachedCriteria dc = CompAction.updatePayBatch_DC();

        BaseCrit.addDC(dc, Empsalarypay.PROP_ESP_YEARMONTH, "eq", new String[] { yearmonth });
        BaseCrit.addStatusEmpDC(dc, "emp", yearmonth, new String[] { "" });

        ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) SpringBeanFactory.getBean("salaryPaidBo");
        List<Empsalarypay> payList = salaryPaidBo.findPay(dc, null);
        if ((payList == null) || (payList.size() == 0))
            return null;

        for (Empsalarypay pay : payList) {
            Empsalaryconfig config = pay.getEspEmpconfig();
            pay.decryEMPPaid(pay);
            config.decryEmpSalaryConf(config);
        }
        return payList;
    }

    private boolean setPayAdd(String yearmonth, Empsalarypay[] payArray) {
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

    private boolean setPayValues(Map<String, Empsalarypay> excelDataMap, Empsalarypay[] payArray) {
        if ((payArray == null) || (payArray.length == 0))
            return false;

        boolean result = true;
        for (Empsalarypay pay : payArray) {
            Empsalaryconfig config = pay.getEspEmpconfig();
            List acctItems = pay.getAcctItems();
            Empsalarypay excelPay = (Empsalarypay) excelDataMap.get(pay.getEspEmpno()
                    .getEmpDistinctNo());

            pay.setEspChanged(Integer.valueOf(0));

            for (int i = 0; i < acctItems.size(); ++i) {
                Empsalaryacctitems item = (Empsalaryacctitems) acctItems.get(i);
                try {
                    if (item.getEsaiDataIsCalc().intValue() != 2) {
                        Object newPayValue = PropertyUtils.getProperty(excelPay, "espColumn"
                                + (i + 1));
                        if ((newPayValue != null)
                                && (CompAction.changePayItem(pay, config, (BigDecimal) newPayValue,
                                                             i + 1)))
                            pay.setEspChanged(Integer.valueOf(1));
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    result = false;
                }
            }
        }
        return result;
    }

    private boolean interpretPay(String yearmonth, String currentEmp, boolean add0And1,
            Empsalarypay[] payArray) {
        if ((StringUtils.isEmpty(yearmonth)) || (ObjectUtils.isEmpty(payArray)))
            return false;

        InterpreterExecuteContext context = new InterpreterExecuteContext(yearmonth);
        context.execute(currentEmp, add0And1, payArray);
        return true;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.extend.IEmpSalaryPay JD-Core Version: 0.5.4
 */