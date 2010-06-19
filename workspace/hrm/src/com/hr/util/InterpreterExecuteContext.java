package com.hr.util;

import com.hr.compensation.bo.IEmpBenefitPlanBo;
import com.hr.compensation.bo.ISalaryPaidBo;
import com.hr.compensation.domain.Empbenefit;
import com.hr.compensation.domain.Empbenefitplan;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.configuration.domain.Jobgrade;
import com.hr.examin.bo.ExaminDateUtil;
import com.hr.examin.bo.interfaces.ILeavecalendarBO;
import com.hr.examin.domain.Attendmonthly;
import com.hr.examin.domain.Leavebalance;
import com.hr.examin.domain.Leavecalendar;
import com.hr.examin.domain.Leavetype;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

public class InterpreterExecuteContext {
    private SysConfigVarDef sysConfigVarDef;
    private Date[] monthDateArr;
    private Integer joinYearCalc;
    private List<Leavecalendar> lcList;

    public InterpreterExecuteContext(String yearMonth) {
        Map dbMap = DatabaseSysConfigManager.getInstance().getProperties();
        this.sysConfigVarDef = new SysConfigVarDef(dbMap);
        int month;
        int year;
        if (yearMonth == null) {
            year = DateUtil.getYear(new Date());
            month = DateUtil.getMonth(new Date());
        } else {
            year = Integer.valueOf(yearMonth.substring(0, 4)).intValue();
            month = Integer.valueOf(yearMonth.substring(4)).intValue();
        }

        this.monthDateArr = ExaminDateUtil.getDateArray(year, month, this.sysConfigVarDef
                .getMonthSum());
        this.joinYearCalc = this.sysConfigVarDef.getJoinYearCalc();

        ILeavecalendarBO lc_BO = (ILeavecalendarBO) SpringBeanFactory.getBean("leavecalendarBO");
        this.lcList = lc_BO.getCalendarListByDay(this.monthDateArr[0], this.monthDateArr[1]);
    }

    public Leavebalance execute(Leavetype leavetype, Employee employee) throws InterpreterException {
        Interpreter interpreter = new Interpreter();

        interpreter.setSysConfigVarDef(this.sysConfigVarDef);

        DateVarDef dateVarDef = new DateVarDef(employee, this.monthDateArr, this.joinYearCalc,
                this.lcList);
        interpreter.setDateVarDef(dateVarDef);

        Leavebalance leavebalance = new Leavebalance();
        if (leavetype.getLtSpecialCat().intValue() == 1) {
            if (StringUtils.isNotEmpty(leavetype.getLtDaysOfYear()))
                leavebalance.setLbDaysOfYear(interpreter
                        .calculateLeave(leavetype.getLtDaysOfYear()));
            if (StringUtils.isNotEmpty(leavetype.getLtHoursOfYear()))
                leavebalance.setLbHoursOfYear(interpreter.calculateLeave(leavetype
                        .getLtHoursOfYear()));
            if (StringUtils.isNotEmpty(leavetype.getLtDaysForRelease()))
                leavebalance.setLbDaysForRelease(interpreter.calculateLeave(leavetype
                        .getLtDaysForRelease()));
            if (StringUtils.isNotEmpty(leavetype.getLtHoursForRelease()))
                leavebalance.setLbHoursForRelease(interpreter.calculateLeave(leavetype
                        .getLtHoursForRelease()));
            if (StringUtils.isNotEmpty(leavetype.getLtOtherParameter()))
                leavebalance.setLbOtherDays(interpreter.calculateLeave(leavetype
                        .getLtOtherParameter()));
            if (leavetype.getLtBalForward() != null)
                leavebalance.setLbBalEndDate(leavetype.getLtBalForward());
        } else if (leavetype.getLtSpecialCat().intValue() == 5) {
            if (StringUtils.isNotEmpty(leavetype.getLtDaysOfYear()))
                leavebalance.setLbDaysOfYear(interpreter
                        .calculateLeave(leavetype.getLtDaysOfYear()));
            if (StringUtils.isNotEmpty(leavetype.getLtHoursOfYear()))
                leavebalance.setLbHoursOfYear(interpreter.calculateLeave(leavetype
                        .getLtHoursOfYear()));
            if (StringUtils.isNotEmpty(leavetype.getLtDaysForRelease()))
                leavebalance.setLbDaysForRelease(interpreter.calculateLeave(leavetype
                        .getLtDaysForRelease()));
            if (StringUtils.isNotEmpty(leavetype.getLtHoursForRelease()))
                leavebalance.setLbHoursForRelease(interpreter.calculateLeave(leavetype
                        .getLtHoursForRelease()));
        } else if ((leavetype.getLtSpecialCat().intValue() == 11)
                && (StringUtils.isNotEmpty(leavetype.getLtOtherParameter()))) {
            leavebalance
                    .setLbOtherDays(interpreter.calculateLeave(leavetype.getLtOtherParameter()));
        }
        return leavebalance;
    }

    public void execute(Empsalaryconfig[] configArr) {
        for (Empsalaryconfig config : configArr) {
            List acctItems = config.getAcctItems();

            Interpreter interpreter = new Interpreter();

            interpreter.setSalaryConfig(config);
            interpreter.setFillDataMode(0);

            interpreter.setSysConfigVarDef(this.sysConfigVarDef);

            DateVarDef dateVarDef = new DateVarDef(config.getEmployee(), this.monthDateArr,
                    this.joinYearCalc, this.lcList);
            interpreter.setDateVarDef(dateVarDef);

            BenefitVarDef bfVarDef = createEmpBenefit(config.getEmployee(), config.getEscJobgrade());
            interpreter.setBenefitVarDef(bfVarDef);
            try {
                interpreter.calculateSalary(acctItems, false);
            } catch (InterpreterException ie) {
                List<String> ieList = ie.getErrorList();
                for (String msg : ieList) {
                    System.out.println(config.getEmployee().getEmpName()
                            + " 薪资配置错误-SalaryConfigError" + msg);
                }
                return;
            }

            for (int i = acctItems.size(); i < 48; ++i)
                try {
                    PropertyUtils.setProperty(config, "escColumn" + (i + 1), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    public void execute(String currentEmp, boolean add0And1, Empsalarypay[] payArray) {
        for (Empsalarypay pay : payArray) {
            List acctItems = pay.getAcctItems();
            Attendmonthly attendMonthly = pay.getAttendMonthly();

            Interpreter interpreter = new Interpreter();

            interpreter.setSalaryPay(pay);
            interpreter.setFillDataMode(1);

            interpreter.setSysConfigVarDef(this.sysConfigVarDef);

            DateVarDef dateVarDef = new DateVarDef(pay.getEspEmpno(), this.monthDateArr,
                    this.joinYearCalc, this.lcList);
            interpreter.setDateVarDef(dateVarDef);

            BenefitVarDef bfVarDef = createEmpBenefit(pay.getEspEmpno(), pay.getEspJobgrade());
            interpreter.setBenefitVarDef(bfVarDef);

            ExaminVarDef examinVardef = new ExaminVarDef(attendMonthly);
            interpreter.setExaminVarDef(examinVardef);
            try {
                interpreter.calculateSalary(acctItems, false);

                if (pay.getEspBenefitPlans().intValue() > 0) {
                    ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) SpringBeanFactory
                            .getBean("salaryPaidBo");
                    salaryPaidBo.addAddtionalEbpToPay(pay.getAcctItems(), pay, pay
                            .getAddBenefitPlan(), add0And1);
                    pay.setEspChanged(Integer.valueOf(1));
                    interpreter.calculateSalary(acctItems, true);
                }

                IEmpBenefitPlanBo ebpBo = (IEmpBenefitPlanBo) SpringBeanFactory
                        .getBean("empbenefitplanBo");
                ebpBo.addCurrPlan(currentEmp, pay);
            } catch (InterpreterException ie) {
                List<String> ieList = ie.getErrorList();
                for (String msg : ieList) {
                    System.out.println(pay.getEspEmpno().getEmpName() + " 薪资配置错误-SalaryPayError"
                            + msg);
                }
                return;
            }

            for (int i = acctItems.size(); i < 48; ++i)
                try {
                    PropertyUtils.setProperty(pay, "espColumn" + (i + 1), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    private BenefitVarDef createEmpBenefit(Employee emp, Jobgrade jobgrade) {
        BenefitVarDef varDef = new BenefitVarDef(emp, jobgrade);

        if (emp.getBenefit() == null)
            return varDef;

        varDef.setD2(getDoubleValue(emp.getBenefit().getEbfPensionAmount()));
        varDef.setD3(getDoubleValue(emp.getBenefit().getEbfHousingAmount()));
        varDef.setD4(getDoubleValue(emp.getBenefit().getEbfInsuranceAmount()));
        return varDef;
    }

    private BenefitVarDef createEmpBenefit(Employee emp, Jobgrade jobgrade,
            Empbenefitplan benefitPlan) {
        BenefitVarDef varDef = new BenefitVarDef(emp, jobgrade);
        if (emp.getBenefit() != null) {
            varDef.setD2(getDoubleValue(emp.getBenefit().getEbfPensionAmount()));
            varDef.setD3(getDoubleValue(emp.getBenefit().getEbfHousingAmount()));
            varDef.setD4(getDoubleValue(emp.getBenefit().getEbfInsuranceAmount()));
            return varDef;
        }

        if (benefitPlan == null)
            return varDef;

        varDef.setD2(getDoubleValue(benefitPlan.getEbpPensionAmountb()));
        varDef.setD3(getDoubleValue(benefitPlan.getEbpHousingAmountb()));
        varDef.setD4(getDoubleValue(benefitPlan.getEbpInsuranceAmountb()));
        return varDef;
    }

    private Double getDoubleValue(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return Double.valueOf(0.0D);
        }
        return Double.valueOf(bigDecimal.doubleValue());
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.util.InterpreterExecuteContext JD-Core Version: 0.5.4
 */