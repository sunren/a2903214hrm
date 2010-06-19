package com.hr.compensation.action;

import com.hr.base.DWRUtil;
import com.hr.compensation.bo.IEmpSalaryAcctitemsBo;
import com.hr.compensation.bo.IEmpSalaryAcctversionBo;
import com.hr.compensation.bo.ISalaryconfBo;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.configuration.bo.IJobgradeBO;
import com.hr.configuration.domain.Jobgrade;
import com.hr.spring.SpringBeanFactory;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;
import org.apache.axis.utils.StringUtils;
import org.apache.commons.beanutils.PropertyUtils;

public class DWRforSalaryCalc extends CompAction {
    private static final long serialVersionUID = 1L;

    public Empsalaryconfig calculateSalary(String acctVersionId, String empId, String jobGradeId,
            Empsalaryconfig config) {
        if ("error".equalsIgnoreCase(DWRUtil.checkAuth("searchSalary", "execute")))
            return null;
        if ((StringUtils.isEmpty(acctVersionId)) || (StringUtils.isEmpty(empId))
                || (StringUtils.isEmpty(jobGradeId))) {
            return null;
        }

        Empsalaryconfig cfg = loadConfig(empId, null);
        if (cfg == null)
            return null;

        if (!new Empsalaryacctversion(acctVersionId).equals(cfg.getEscEsavId())) {
            IEmpSalaryAcctversionBo esvaBo = (IEmpSalaryAcctversionBo) SpringBeanFactory
                    .getBean("empsalaryacctversionBo");
            Empsalaryacctversion version = esvaBo.loadObject(acctVersionId, new String[0]);
            cfg.setEscEsavId(version);
        }

        if (!new Jobgrade(jobGradeId).equals(cfg.getEscJobgrade())) {
            IJobgradeBO jobgradebo = (IJobgradeBO) SpringBeanFactory.getBean("jobgradeBO");
            Jobgrade jobgrade = jobgradebo.loadJobgrade(jobGradeId);
            cfg.setEscJobgrade(jobgrade);
        }

        IEmpSalaryAcctitemsBo esaiBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");
        esaiBo.setAcctItemsConfig(new Empsalaryconfig[] { cfg });

        setConfigValues(config, cfg);

        ISalaryconfBo confBo = (ISalaryconfBo) getBean("salaryconfBo");
        confBo.interpretConfig(new Empsalaryconfig[] { cfg });

        cfg.setEscEsavId(null);
        cfg.setEmployee(null);
        cfg.setEscJobgrade(null);
        return cfg;
    }

    protected boolean setConfigValues(Empsalaryconfig source, Empsalaryconfig target) {
        if ((source == null) || (target == null))
            return false;

        List acctItems = target.getAcctItems();
        boolean result = true;
        for (int i = 0; i < acctItems.size(); ++i) {
            Integer isCalc = ((Empsalaryacctitems) acctItems.get(i)).getEsaiDataIsCalc();
            try {
                if (isCalc.intValue() != 2) {
                    if (isCalc.intValue() == 1) {
                        PropertyUtils.setProperty(target, "escColumn" + (i + 1), new BigDecimal(
                                "0.00"));
                    } else {
                        Object value = PropertyUtils.getProperty(source, "escColumn" + (i + 1));
                        if (value == null)
                            PropertyUtils.setProperty(target, "escColumn" + (i + 1),
                                                      new BigDecimal("0.00"));
                        else
                            PropertyUtils.setProperty(target, "escColumn" + (i + 1),
                                                      ((BigDecimal) value).setScale(2));
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                result = false;
            }
        }
        return result;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.action.DWRforSalaryCalc JD-Core Version: 0.5.4
 */