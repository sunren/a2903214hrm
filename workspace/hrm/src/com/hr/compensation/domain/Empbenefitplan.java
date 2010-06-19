package com.hr.compensation.domain;

import com.hr.base.Constants;
import com.hr.compensation.domain.base.BaseEmpbenefitplan;
import com.hr.profile.domain.Employee;
import com.hr.util.MyTools;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Empbenefitplan extends BaseEmpbenefitplan implements Constants {
    private static final long serialVersionUID = 1L;
    private BigDecimal showColumn15;
    private BigDecimal showColumn16;
    private List<String> outputList;
    private Integer empMonthCount = Integer.valueOf(0);
    private Map<String, BigDecimal> outPutList;

    public Empbenefitplan() {
    }

    public Empbenefitplan(Employee emp, String yearMonth, String belongYearMonth, Integer ebpStatus) {
        setEbpEmpno(emp);
        setEbpYearMonth(yearMonth);
        setEbpBelongYearmonth(belongYearMonth);
        setEbpStatus(ebpStatus);
    }

    public Empbenefitplan(Empbenefit benefit) {
        setEbpEmpno(benefit.getEmployee());
        setEbpPensionAmountb(benefit.getEbfPensionAmount());
        setEbpHousingAmountb(benefit.getEbfHousingAmount());
        setEbpInsuranceAmountb(benefit.getEbfInsuranceAmount());
    }

    public void encryEMPPlan(Empbenefitplan plan) {
        String empId = plan.getEbpEmpno().getId();
        Class ownerClass = plan.getClass();
        try {
            for (int i = 1; i <= 48; ++i) {
                Method ebpMethod = ownerClass.getMethod("getEbpColumn" + i, new Class[0]);
                BigDecimal temp = (BigDecimal) ebpMethod.invoke(plan, new Object[0]);
                plan.setEncry("EbpColumn" + i, temp, empId, MyTools.BIGDECIMAL);
            }

            plan.setEncry("EbpComments", plan.getEbpComments(), empId, MyTools.CHINESE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decryEMPPlan(Empbenefitplan plan) {
        String empId = plan.getEbpEmpno().getId();
        try {
            Class ownerClass = plan.getClass();

            for (int i = 1; i <= 48; ++i) {
                Method ebpMethod = ownerClass.getMethod("setEbpColumn" + i,
                                                        new Class[] { BigDecimal.class });
                ebpMethod.invoke(plan, new Object[] { (BigDecimal) plan
                        .getDecry("EbpColumn" + i, empId, MyTools.BIGDECIMAL) });
            }

            plan.setEbpComments((String) plan.getDecry("EbpComments", empId, MyTools.CHINESE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BigDecimal getShowColumn15() {
        return this.showColumn15;
    }

    public void setShowColumn15(BigDecimal showColumn15) {
        this.showColumn15 = showColumn15;
    }

    public BigDecimal getShowColumn16() {
        return this.showColumn16;
    }

    public void setShowColumn16(BigDecimal showColumn16) {
        this.showColumn16 = showColumn16;
    }

    public List<String> getOutputList() {
        return this.outputList;
    }

    public void setOutputList(List<String> outputList) {
        this.outputList = outputList;
    }

    public Integer getEmpMonthCount() {
        return this.empMonthCount;
    }

    public void setEmpMonthCount(Integer empMonthCount) {
        this.empMonthCount = empMonthCount;
    }

    public Map<String, BigDecimal> getOutPutList() {
        return this.outPutList;
    }

    public void setOutPutList(Map<String, BigDecimal> outPutList) {
        this.outPutList = outPutList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.domain.Empbenefitplan JD-Core Version: 0.5.4
 */