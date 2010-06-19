package com.hr.compensation.action;

import com.hr.base.DWRUtil;
import com.hr.compensation.bo.IEmpBenefitBo;
import com.hr.compensation.bo.IEmpSalaryAcctitemsBo;
import com.hr.compensation.bo.ISalaryconfBo;
import com.hr.compensation.domain.Empbenefit;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DateUtil;
import com.hr.util.MyTools;
import com.hr.util.StringUtil;
import java.util.Date;
import org.apache.commons.lang.StringUtils;

public class DWRforEmpBenefit extends CompAction {
    private static final long serialVersionUID = 1L;

    public String empBenefitAdd(Empbenefit newEbf, String empId) {
        if ("error".equalsIgnoreCase(DWRUtil.checkAuth("updateBenefit", "execute")))
            return StringUtil.message(this.msgNoAuth, new Object[] { "noauth" });
        if (StringUtils.isEmpty(empId)) {
            return StringUtil.message(this.msgNoEmp, new Object[] { "noemp" });
        }

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        Employee emp = empBo.loadEmp(empId, new String[] { "empBenefitType", "benefit", "config",
                "config.escJobgrade", "config.escEsavId" });

        if (emp == null)
            return StringUtil.message(this.msgNoEmp, new Object[] { "noemp" });
        if (emp.getBenefit() == null) {
            return StringUtil.message(this.msgNoBenefit, new Object[] { "nobenefit" });
        }

        Empbenefit oldEbf = emp.getBenefit();
        if (newEbf.getEbfStartMonth().compareTo(oldEbf.getEbfStartMonth()) < 0) {
            return this.msgStartYMTooEarly;
        }

        newEbf.setEbfId(MyTools.getUUID());
        newEbf.setEmployee(emp);
        newEbf.setEbfPensionNo(oldEbf.getEbfPensionNo());
        newEbf.setEbfHousingNo(oldEbf.getEbfHousingNo());
        newEbf.setEbfMedicalNo(oldEbf.getEbfMedicalNo());

        oldEbf.setEbfEndMonth(DateUtil.yearMonthAdd(newEbf.getEbfStartMonth(), -1, 2));

        emp.setBenefit(newEbf);
        emp.setEmpLastChangeBy(getCurrentEmp());
        emp.setEmpLastChangeTime(new Date());

        Empsalaryconfig config = emp.getConfig();
        if (config != null) {
            config.decryEmpSalaryConf(config);
            IEmpSalaryAcctitemsBo esaitemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                    .getBean("empsalaryacctitemsBo");
            esaitemsBo.setAcctItemsConfig(new Empsalaryconfig[] { config });
            config.setEscLastChangeBy(getCurrentEmpNo());
            config.setEscLastChangeTime(new Date());

            ISalaryconfBo confBo = (ISalaryconfBo) getBean("salaryconfBo");
            confBo.interpretConfig(new Empsalaryconfig[] { config });
            config.encryEmpSalaryConf(config);
        }

        IEmpBenefitBo empbenefitBo = (IEmpBenefitBo) SpringBeanFactory.getBean("empbenefitBo");
        String error = empbenefitBo.insertNewBenefit(oldEbf, newEbf, emp);
        if (!"SUCC".equals(error)) {
            return this.msgSystemEx;
        }
        return StringUtil.message(this.msgAdjBeneSucc, new Object[] { "SUCC", emp.getEmpName() });
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.action.DWRforEmpBenefit JD-Core Version: 0.5.4
 */