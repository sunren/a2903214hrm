package com.hr.compensation.domain;

import com.hr.base.Constants;
import com.hr.compensation.domain.base.BaseEmpsalarypay;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Jobgrade;
import com.hr.configuration.domain.Location;
import com.hr.examin.domain.Attendmonthly;
import com.hr.profile.domain.Employee;
import com.hr.util.MyTools;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Empsalarypay extends BaseEmpsalarypay implements Constants {
    private static final long serialVersionUID = 1L;
    private BigDecimal showColumn1;
    private BigDecimal showColumn4;
    private BigDecimal showColumn7;
    private BigDecimal showColumn8;
    private BigDecimal showColumn15;
    private BigDecimal showColumn16;
    private BigDecimal showColumn17;
    private BigDecimal showColumn18;
    private BigDecimal showColumn19;
    private List<Empsalaryacctitems> acctItems;
    private Attendmonthly attendMonthly;
    private Empbenefitplan benefitPlan;
    private Empbenefitplan addBenefitPlan;
    private Department espBranch;
    private Department espDeptNo1;
    private Department espDeptNo2;
    private Department espDeptNo3;
    private Department espDeptNo4;
    private Department espDeptNo5;
    private String yearAndMonth;
    private Map<String, BigDecimal> outPutList;

    public Empsalarypay() {
    }

    public Empsalarypay(String id) {
        super(id);
    }

    public Empsalarypay(String id, Jobgrade espJobgrade, Department espDept, Emptype espEmptype,
            Employee espEmpno, Location espLocation, Empsalaryconfig espEmpconfig,
            Empsalaryacctversion espEsavId, String espYearmonth, Integer espBenefitPlans,
            Integer espChanged) {
        super(id, espJobgrade, espDept, espEmptype, espEmpno, espLocation, espEmpconfig, espEsavId,
                espYearmonth, espBenefitPlans, espChanged);
    }

    public void encryEMPPaid(Empsalarypay paid) {
        String empId = paid.getEspEmpno().getId();
        Class ownerClass = paid.getClass();
        try {
            for (int i = 1; i <= 48; ++i) {
                Method espMethod = ownerClass.getMethod("getEspColumn" + i, new Class[0]);
                BigDecimal temp = (BigDecimal) espMethod.invoke(paid, new Object[0]);
                paid.setEncry("EspColumn" + i, temp, empId, MyTools.BIGDECIMAL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        paid.setEncry("espBankAccountNo", paid.getEspBankAccountNo(), paid.getEspEmpno().getId(),
                      MyTools.STRING);
        paid.setEncry("EspComment", paid.getEspComment(), paid.getEspEmpno().getId(),
                      MyTools.CHINESE);
    }

    public void decryEMPPaid(Empsalarypay paid) {
        String empId = paid.getEspEmpno().getId();
        try {
            Class esaClass = paid.getClass();

            for (int i = 1; i <= 48; ++i) {
                Method esaMethod = esaClass.getMethod("setEspColumn" + i,
                                                      new Class[] { BigDecimal.class });
                esaMethod.invoke(paid, new Object[] { (BigDecimal) paid
                        .getDecry("EspColumn" + i, empId, MyTools.BIGDECIMAL) });
            }
            paid.setEspBankAccountNo((String) paid.getDecry("espBankAccountNo", empId,
                                                            MyTools.STRING));
            paid.setEspComment((String) paid.getDecry("EspComment", empId, MyTools.CHINESE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BigDecimal getShowColumn1() {
        return this.showColumn1;
    }

    public void setShowColumn1(BigDecimal showColumn1) {
        this.showColumn1 = showColumn1;
    }

    public BigDecimal getShowColumn4() {
        return this.showColumn4;
    }

    public void setShowColumn4(BigDecimal showColumn4) {
        this.showColumn4 = showColumn4;
    }

    public BigDecimal getShowColumn7() {
        return this.showColumn7;
    }

    public void setShowColumn7(BigDecimal showColumn7) {
        this.showColumn7 = showColumn7;
    }

    public List<Empsalaryacctitems> getAcctItems() {
        return this.acctItems;
    }

    public void setAcctItems(List<Empsalaryacctitems> acctItems) {
        this.acctItems = acctItems;
    }

    public Attendmonthly getAttendMonthly() {
        return this.attendMonthly;
    }

    public void setAttendMonthly(Attendmonthly attendMonthly) {
        this.attendMonthly = attendMonthly;
    }

    public Empbenefitplan getBenefitPlan() {
        return this.benefitPlan;
    }

    public void setBenefitPlan(Empbenefitplan benefitPlan) {
        this.benefitPlan = benefitPlan;
    }

    public Empbenefitplan getAddBenefitPlan() {
        return this.addBenefitPlan;
    }

    public void setAddBenefitPlan(Empbenefitplan addBenefitPlan) {
        this.addBenefitPlan = addBenefitPlan;
    }

    public Department getEspBranch() {
        return this.espBranch;
    }

    public void setEspBranch(Department espBranch) {
        this.espBranch = espBranch;
    }

    public Department getEspDeptNo1() {
        return this.espDeptNo1;
    }

    public void setEspDeptNo1(Department espDeptNo1) {
        this.espDeptNo1 = espDeptNo1;
    }

    public Department getEspDeptNo2() {
        return this.espDeptNo2;
    }

    public void setEspDeptNo2(Department espDeptNo2) {
        this.espDeptNo2 = espDeptNo2;
    }

    public Department getEspDeptNo3() {
        return this.espDeptNo3;
    }

    public void setEspDeptNo3(Department espDeptNo3) {
        this.espDeptNo3 = espDeptNo3;
    }

    public Department getEspDeptNo4() {
        return this.espDeptNo4;
    }

    public void setEspDeptNo4(Department espDeptNo4) {
        this.espDeptNo4 = espDeptNo4;
    }

    public Department getEspDeptNo5() {
        return this.espDeptNo5;
    }

    public void setEspDeptNo5(Department espDeptNo5) {
        this.espDeptNo5 = espDeptNo5;
    }

    public String getYearAndMonth() {
        return this.yearAndMonth;
    }

    public void setYearAndMonth(String yearAndMonth) {
        this.yearAndMonth = yearAndMonth;
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

    public BigDecimal getShowColumn17() {
        return this.showColumn17;
    }

    public void setShowColumn17(BigDecimal showColumn17) {
        this.showColumn17 = showColumn17;
    }

    public BigDecimal getShowColumn18() {
        return this.showColumn18;
    }

    public void setShowColumn18(BigDecimal showColumn18) {
        this.showColumn18 = showColumn18;
    }

    public BigDecimal getShowColumn19() {
        return this.showColumn19;
    }

    public void setShowColumn19(BigDecimal showColumn19) {
        this.showColumn19 = showColumn19;
    }

    public BigDecimal getShowColumn8() {
        return this.showColumn8;
    }

    public void setShowColumn8(BigDecimal showColumn8) {
        this.showColumn8 = showColumn8;
    }

    public Map<String, BigDecimal> getOutPutList() {
        return this.outPutList;
    }

    public void setOutPutList(Map<String, BigDecimal> outPutList) {
        this.outPutList = outPutList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.domain.Empsalarypay JD-Core Version: 0.5.4
 */