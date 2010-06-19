package com.hr.compensation.action;

import com.hr.base.DWRUtil;
import com.hr.compensation.bo.IEmpSalaryAcctitemsBo;
import com.hr.compensation.bo.ISalaryPaidBo;
import com.hr.compensation.bo.ISalaryconfBo;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.BaseCrit;
import com.hr.util.StringUtil;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.axis.utils.StringUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.util.ObjectUtils;
import org.testng.log4testng.Logger;

public class UpdateSalaryConfigBatch extends CompAction {
    private static Logger logger = Logger.getLogger(UpdateSalaryConfigBatch.class);
    private static final long serialVersionUID = 1L;

    public String updateConfigBatch(String empIds, String itemId, BigDecimal salaryValue)
    {
      if ("error".equalsIgnoreCase(DWRUtil.checkAuth("updateSalaryConfig", "updateSalaryConfig")))
        return StringUtil.message(this.msgNoAuth, new Object[] { "noauth" });
      if ((StringUtils.isEmpty(empIds)) || (StringUtils.isEmpty(itemId)) || (salaryValue == null)) {
        return StringUtil.message(this.msgNoParam, new Object[] { "noparam" });
      }

      List configList = updateConfigBatch_DC(empIds.split(","));
      if ((configList == null) || (configList.size() == 0)) {
        return StringUtil.message(this.msgNoData, new Object[] { "noconf", "可以修改的薪资配置" });
      }

      IEmpSalaryAcctitemsBo esaiBo = (IEmpSalaryAcctitemsBo)SpringBeanFactory.getBean("empsalaryacctitemsBo");
      if (!esaiBo.setAcctItemsConfig((Empsalaryconfig[])configList.toArray(new Empsalaryconfig[configList.size()]))) {
        return StringUtil.message(this.msgNoData, new Object[] { "noacct", "帐套数据" });
      }

      setConfigValues(itemId, salaryValue, configList);
      if ((configList == null) || (configList.size() == 0)) {
        return StringUtil.message(this.msgNoData, new Object[] { "noconf", "可以修改的薪资配置" });
      }

      ISalaryconfBo confBo = (ISalaryconfBo)getBean("salaryconfBo");
      confBo.interpretConfig((Empsalaryconfig[])configList.toArray(new Empsalaryconfig[configList.size()]));
      if ((configList == null) || (configList.size() == 0)) {
        return StringUtil.message(this.msgNoData, new Object[] { "noconf", "可以修改的薪资配置" });
      }

      String str = confBo.batchUpdateConfig(getCurrentEmpNo(), configList);
      return StringUtil.message(this.msgAdjConfSucc, new Object[] { "SUCC", str });
    }


    public String updatePayBatch(String empIds, String itemId, String salaryValue, String yearmonth) {
        if ("error".equalsIgnoreCase(DWRUtil.checkAuth("searchSalaryPaid", "execute")))
            return StringUtil.message(this.msgNoAuth, new Object[] { "noauth" });
        if ((StringUtils.isEmpty(salaryValue)) || (StringUtils.isEmpty(itemId)))
            return StringUtil.message(this.msgNoParam, new Object[] { "noparam" });
        if (StringUtils.isEmpty(yearmonth))
            return StringUtil.message(this.msgNoYm, new Object[] { "noym" });
        if (!checkSalaryPeriod(yearmonth, new Integer[] { Integer.valueOf(0) })) {
            return StringUtil.message(this.msgPeriodInitErr, new Object[0]);
        }

        if (StringUtils.isEmpty(empIds))
            return StringUtil.message(this.msgNoEmp, new Object[] { "noemp" });
        String[] empIdArr = empIds.split(",");
        if (ObjectUtils.isEmpty(empIdArr))
            return StringUtil.message(this.msgNoEmp, new Object[] { "noemp" });
        BigDecimal bdValue;
        try {
            bdValue = new BigDecimal(salaryValue);
        } catch (Exception e) {
            return StringUtil.message(this.msgFormatErr, new Object[] { "format", "项目数值" });
        }

        List payList = updatePayBatch_DC(yearmonth, empIdArr);
        if ((payList == null) || (payList.size() == 0)) {
            return StringUtil.message(this.msgNoData, new Object[] { "nopay", "可以修改的薪资发放" });
        }

        setPayAdd(yearmonth, (Empsalarypay[]) payList.toArray(new Empsalarypay[payList.size()]));

        setPayValues(itemId, bdValue, payList);

        interpretPay(yearmonth, getCurrentEmpNo(), true, (Empsalarypay[]) payList
                .toArray(new Empsalarypay[payList.size()]));
        if ((payList == null) || (payList.size() == 0)) {
            return StringUtil.message(this.msgNoData, new Object[] { "nopay", "可以修改的薪资发放" });
        }

        ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) getBean("salaryPaidBo");
        String str = salaryPaidBo.batchUpdatePay(yearmonth, payList);
        return StringUtil.message(this.msgAdjPaySucc, new Object[] { "SUCC", str });
    }

    public List<Empsalaryconfig> updateConfigBatch_DC(String[] empIdArr) {
        DetachedCriteria dc = updateConfigBatch_DC();

        if ("SUB".equals(this.authorityCondition)) {
            addSubDC(dc, "emp", 2);
        }

        BaseCrit.addDC(dc, Employee.PROP_ID, "in", empIdArr);
        BaseCrit.addDC(dc, Employee.PROP_EMP_STATUS, "eq", new Integer[] { Integer.valueOf(1) });

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        List<Employee> empList = empBo.findByCriteria(dc);
        if ((empList == null) || (empList.size() == 0))
            return null;

        List configList = new ArrayList();
        for (Employee emp : empList) {
            Empsalaryconfig config = emp.getConfig();
            config.decryEmpSalaryConf(config);
            configList.add(config);
        }
        return configList;
    }

    public List<Empsalarypay> updatePayBatch_DC(String yearmonth, String[] empIdArr) {
        DetachedCriteria dc = updatePayBatch_DC();

        if ("SUB".equals(this.authorityCondition)) {
            addSubDC(dc, "emp", 2);
        }

        BaseCrit.addDC(dc, Empsalarypay.PROP_ESP_YEARMONTH, "eq", new String[] { yearmonth });
        BaseCrit.addDC(dc, "emp." + Employee.PROP_ID, "in", empIdArr);
        BaseCrit.addStatusEmpDC(dc, "emp", yearmonth, new String[] { "" });

        ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) getBean("salaryPaidBo");
        List<Empsalarypay> payList = salaryPaidBo.findPay(dc, null);
        if (payList.size() == 0)
            return null;

        for (Empsalarypay pay : payList) {
            Empsalaryconfig config = pay.getEspEmpconfig();
            pay.decryEMPPaid(pay);
            config.decryEmpSalaryConf(config);
        }
        return payList;
    }

    private boolean setConfigValues(String itemId, BigDecimal bdValue,
            List<Empsalaryconfig> configList) {
        if ((configList == null) || (configList.size() == 0))
            return false;

        for (Iterator it = configList.iterator(); it.hasNext();) {
            Empsalaryconfig config = (Empsalaryconfig) it.next();
            List acctItems = config.getAcctItems();

            boolean result = false;
            for (int i = 0; i < acctItems.size(); ++i) {
                Empsalaryacctitems item = (Empsalaryacctitems) acctItems.get(i);
                try {
                    if (item.getEsaiDataIsCalc().intValue() == 0) {
                        if (item.getId().equals(itemId)) {
                            PropertyUtils.setProperty(config, "escColumn" + (i + 1), bdValue
                                    .setScale(2));
                            result = true;
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    result = false;
                }
            }
            if (!result)
                it.remove();
        }
        return configList.size() != 0;
    }

    private boolean setPayValues(String itemId, BigDecimal bdValue, List<Empsalarypay> payList) {
        if ((payList == null) || (payList.size() == 0))
            return false;

        for (Iterator it = payList.iterator(); it.hasNext();) {
            Empsalarypay pay = (Empsalarypay) it.next();
            Empsalaryconfig config = pay.getEspEmpconfig();
            List acctItems = pay.getAcctItems();
            pay.setEspChanged(Integer.valueOf(0));

            boolean result = false;
            for (int i = 0; i < acctItems.size(); ++i) {
                Empsalaryacctitems item = (Empsalaryacctitems) acctItems.get(i);
                try {
                    if (item.getEsaiDataIsCalc().intValue() != 2) {
                        if (item.getId().equals(itemId)) {
                            PropertyUtils.setProperty(pay, "espColumn" + (i + 1), bdValue);
                            result = true;
                        }

                        Object escValue = PropertyUtils.getProperty(config, "escColumn" + (i + 1));
                        Object espValue = PropertyUtils.getProperty(pay, "espColumn" + (i + 1));

                        if ((escValue != null) || (espValue != null)) {
                            if ((escValue == null)
                                    || (((BigDecimal) escValue).compareTo((BigDecimal) espValue) != 0))
                                pay.setEspChanged(Integer.valueOf(1));
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    result = false;
                }
            }
            if (!result)
                it.remove();
        }
        return payList.size() != 0;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.action.UpdateSalaryConfigBatch JD-Core Version: 0.5.4
 */