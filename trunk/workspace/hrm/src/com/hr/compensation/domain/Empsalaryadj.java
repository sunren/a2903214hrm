package com.hr.compensation.domain;

import com.hr.base.Constants;
import com.hr.compensation.domain.base.BaseEmpsalaryadj;
import com.hr.configuration.domain.Ecptype;
import com.hr.profile.domain.Employee;
import com.hr.util.MyTools;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;

public class Empsalaryadj extends BaseEmpsalaryadj implements Constants {
    private static final long serialVersionUID = 1L;
    private BigDecimal showColumn1;
    private BigDecimal showColumn2;
    private BigDecimal showColumn3;
    private BigDecimal showColumn4;
    private BigDecimal showColumn5;
    private BigDecimal showColumn6;
    private BigDecimal showColumn7;
    private BigDecimal moneybeforetax;
    private BigDecimal basicSalary;
    private BigDecimal newmoneybeforetax;
    private BigDecimal newBasicSalary;
    private Integer basicSalaryIsCal = Integer.valueOf(0);

    public Empsalaryadj() {
    }

    public Empsalaryadj(String id) {
        super(id);
    }

    public Empsalaryadj(String id, Employee esaEmpno, Employee esaLastChangeBy,
            Ecptype esaEcptypeId, Employee esaCreateBy, Integer esaStatus, Date esaCreateTime,
            Date esaLastChangeTime) {
        super(id, esaEmpno, esaLastChangeBy, esaEcptypeId, esaCreateBy, esaStatus, esaCreateTime,
                esaLastChangeTime);
    }

    public BigDecimal getMoneybeforetax() {
        return this.moneybeforetax;
    }

    public void setMoneybeforetax(BigDecimal moneybeforetax) {
        this.moneybeforetax = moneybeforetax;
    }

    public BigDecimal getNewmoneybeforetax() {
        return this.newmoneybeforetax;
    }

    public void setNewmoneybeforetax(BigDecimal newmoneybeforetax) {
        this.newmoneybeforetax = newmoneybeforetax;
    }

    public void decryEmpSalaryAdj(Empsalaryadj esa) {
        String empId = esa.getEsaEmpno().getId();
        try {
            Class esaClass = esa.getClass();

            for (int i = 1; i <= 48; ++i) {
                Method esaMethod = esaClass.getMethod("setEsaColumn" + i + "Cur",
                                                      new Class[] { BigDecimal.class });
                esaMethod.invoke(esa, new Object[] { (BigDecimal) esa.getDecry("EsaColumn" + i
                        + "Cur", empId, MyTools.BIGDECIMAL) });
                esaMethod = esaClass.getMethod("setEsaColumn" + i + "Pro",
                                               new Class[] { BigDecimal.class });
                esaMethod.invoke(esa, new Object[] { (BigDecimal) esa.getDecry("EsaColumn" + i
                        + "Pro", empId, MyTools.BIGDECIMAL) });
            }

            esa.setEsaCurIncrRate((BigDecimal) esa.getDecry("EsaCurIncrRate", empId,
                                                            MyTools.BIGDECIMAL));
            esa.setEsaCurIncrRate1((BigDecimal) esa.getDecry("EsaCurIncrRate1", empId,
                                                             MyTools.BIGDECIMAL));
            esa.setEsaComments((String) esa.getDecry("EsaComments", empId, MyTools.CHINESE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void encryEmpSalaryAdj(Empsalaryadj esa) throws Exception {
        String empId = esa.getEsaEmpno().getId();
        Class ownerClass = esa.getClass();

        for (int i = 1; i <= 48; ++i) {
            Method espMethod = ownerClass.getMethod("getEsaColumn" + i + "Cur", new Class[0]);
            BigDecimal temp = (BigDecimal) espMethod.invoke(esa, new Object[0]);
            esa.setEncry("EsaColumn" + i + "Cur", temp, empId, MyTools.BIGDECIMAL);
            espMethod = ownerClass.getMethod("getEsaColumn" + i + "Pro", new Class[0]);
            temp = (BigDecimal) espMethod.invoke(esa, new Object[0]);
            esa.setEncry("EsaColumn" + i + "Pro", temp, empId, MyTools.BIGDECIMAL);
        }

        esa.setEncry("EsaCurIncrRate", esa.getEsaCurIncrRate(), empId, MyTools.BIGDECIMAL);
        esa.setEncry("EsaCurIncrRate1", esa.getEsaCurIncrRate1(), empId, MyTools.BIGDECIMAL);

        esa.setEncry("EsaComments", esa.getEsaComments(), empId, MyTools.CHINESE);
    }

    public BigDecimal getShowColumn1() {
        return this.showColumn1;
    }

    public void setShowColumn1(BigDecimal showColumn1) {
        this.showColumn1 = showColumn1;
    }

    public BigDecimal getShowColumn2() {
        return this.showColumn2;
    }

    public void setShowColumn2(BigDecimal showColumn2) {
        this.showColumn2 = showColumn2;
    }

    public BigDecimal getShowColumn3() {
        return this.showColumn3;
    }

    public void setShowColumn3(BigDecimal showColumn3) {
        this.showColumn3 = showColumn3;
    }

    public BigDecimal getShowColumn4() {
        return this.showColumn4;
    }

    public void setShowColumn4(BigDecimal showColumn4) {
        this.showColumn4 = showColumn4;
    }

    public BigDecimal getShowColumn5() {
        return this.showColumn5;
    }

    public void setShowColumn5(BigDecimal showColumn5) {
        this.showColumn5 = showColumn5;
    }

    public BigDecimal getShowColumn6() {
        return this.showColumn6;
    }

    public void setShowColumn6(BigDecimal showColumn6) {
        this.showColumn6 = showColumn6;
    }

    public BigDecimal getShowColumn7() {
        return this.showColumn7;
    }

    public void setShowColumn7(BigDecimal showColumn7) {
        this.showColumn7 = showColumn7;
    }

    public BigDecimal getBasicSalary() {
        return this.basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    public BigDecimal getNewBasicSalary() {
        return this.newBasicSalary;
    }

    public void setNewBasicSalary(BigDecimal newBasicSalary) {
        this.newBasicSalary = newBasicSalary;
    }

    public Integer getBasicSalaryIsCal() {
        return this.basicSalaryIsCal;
    }

    public void setBasicSalaryIsCal(Integer basicSalaryIsCal) {
        this.basicSalaryIsCal = basicSalaryIsCal;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.domain.Empsalaryadj JD-Core Version: 0.5.4
 */