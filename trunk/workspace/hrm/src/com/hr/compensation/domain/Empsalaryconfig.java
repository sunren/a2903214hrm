package com.hr.compensation.domain;

import com.hr.base.Constants;
import com.hr.compensation.domain.base.BaseEmpsalaryconfig;
import com.hr.configuration.domain.Jobgrade;
import com.hr.util.MyTools;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Empsalaryconfig extends BaseEmpsalaryconfig implements Constants {
    private static final long serialVersionUID = 1L;
    private BigDecimal moneybeforetax;
    private BigDecimal newmoneybeforetax;
    private BigDecimal basicSalary;
    private BigDecimal newBasicSalary;
    private Empsalaryadj comp = new Empsalaryadj();

    private int canUpdateOrNot = 2;

    private String salaryadjcheck = "null";
    private BigDecimal moneyaftertax;
    private BigDecimal pensioncom;
    private BigDecimal medicalcom;
    private BigDecimal unempcom;
    private BigDecimal housecom;
    private BigDecimal compinsurcom;
    private BigDecimal otherscom;
    private BigDecimal pension;
    private BigDecimal medical;
    private BigDecimal unemp;
    private BigDecimal house;
    private BigDecimal compinsur;
    private BigDecimal others;
    private BigDecimal taxamt;
    private BigDecimal butie;
    private BigDecimal otherbutie;
    private BigDecimal overtimemoney;
    private BigDecimal travelmoney;
    private List<Empsalaryacctitems> acctItems;
    private BigDecimal showColumn1;
    private BigDecimal showColumn4;
    private BigDecimal showColumn7;
    private BigDecimal showColumn8;
    private BigDecimal showColumn17;
    private BigDecimal showColumn18;
    private BigDecimal showColumn19;
    private BigDecimal showColumn20;
    private BigDecimal showColumn10;
    private BigDecimal showColumn11;
    private BigDecimal showColumn12;
    private BigDecimal showColumn13;
    private BigDecimal showColumn14;
    private BigDecimal showColumn15;
    private BigDecimal showColumn16;

    public Empsalaryconfig() {
    }

    public BigDecimal getButie() {
        return this.butie;
    }

    public void setButie(BigDecimal butie) {
        this.butie = butie;
    }

    public Empsalaryadj getComp() {
        return this.comp;
    }

    public void setComp(Empsalaryadj comp) {
        this.comp = comp;
    }

    public BigDecimal getCompinsur() {
        return this.compinsur;
    }

    public void setCompinsur(BigDecimal compinsur) {
        this.compinsur = compinsur;
    }

    public BigDecimal getCompinsurcom() {
        return this.compinsurcom;
    }

    public void setCompinsurcom(BigDecimal compinsurcom) {
        this.compinsurcom = compinsurcom;
    }

    public BigDecimal getHouse() {
        return this.house;
    }

    public void setHouse(BigDecimal house) {
        this.house = house;
    }

    public BigDecimal getHousecom() {
        return this.housecom;
    }

    public void setHousecom(BigDecimal housecom) {
        this.housecom = housecom;
    }

    public int getCanUpdateOrNot() {
        return this.canUpdateOrNot;
    }

    public void setCanUpdateOrNot(int canUpdateOrNot) {
        this.canUpdateOrNot = canUpdateOrNot;
    }

    public BigDecimal getMedical() {
        return this.medical;
    }

    public void setMedical(BigDecimal medical) {
        this.medical = medical;
    }

    public BigDecimal getMedicalcom() {
        return this.medicalcom;
    }

    public void setMedicalcom(BigDecimal medicalcom) {
        this.medicalcom = medicalcom;
    }

    public BigDecimal getMoneyaftertax() {
        return this.moneyaftertax;
    }

    public void setMoneyaftertax(BigDecimal moneyaftertax) {
        this.moneyaftertax = moneyaftertax;
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

    public BigDecimal getOtherbutie() {
        return this.otherbutie;
    }

    public void setOtherbutie(BigDecimal otherbutie) {
        this.otherbutie = otherbutie;
    }

    public BigDecimal getOthers() {
        return this.others;
    }

    public void setOthers(BigDecimal others) {
        this.others = others;
    }

    public BigDecimal getOtherscom() {
        return this.otherscom;
    }

    public void setOtherscom(BigDecimal otherscom) {
        this.otherscom = otherscom;
    }

    public BigDecimal getOvertimemoney() {
        return this.overtimemoney;
    }

    public void setOvertimemoney(BigDecimal overtimemoney) {
        this.overtimemoney = overtimemoney;
    }

    public BigDecimal getPension() {
        return this.pension;
    }

    public void setPension(BigDecimal pension) {
        this.pension = pension;
    }

    public BigDecimal getPensioncom() {
        return this.pensioncom;
    }

    public void setPensioncom(BigDecimal pensioncom) {
        this.pensioncom = pensioncom;
    }

    public BigDecimal getTaxamt() {
        return this.taxamt;
    }

    public void setTaxamt(BigDecimal taxamt) {
        this.taxamt = taxamt;
    }

    public BigDecimal getTravelmoney() {
        return this.travelmoney;
    }

    public void setTravelmoney(BigDecimal travelmoney) {
        this.travelmoney = travelmoney;
    }

    public BigDecimal getUnemp() {
        return this.unemp;
    }

    public void setUnemp(BigDecimal unemp) {
        this.unemp = unemp;
    }

    public BigDecimal getUnempcom() {
        return this.unempcom;
    }

    public void setUnempcom(BigDecimal unempcom) {
        this.unempcom = unempcom;
    }

    public Empsalaryconfig(String id) {
        super(id);
    }

    public Empsalaryconfig(String id, Jobgrade escJobgrade, String escCreateBy, Date escCreateTime,
            String escLastChangeBy, Date escLastChangeTime) {
        super(id, escJobgrade, escCreateBy, escCreateTime, escLastChangeBy, escLastChangeTime);
    }

    public BigDecimal getShowColumn7() {
        return this.showColumn7;
    }

    public void setShowColumn7(BigDecimal showColumn7) {
        this.showColumn7 = showColumn7;
    }

    public void encryEmpSalaryConf(Empsalaryconfig conf) {
        String empId = conf.getId();
        Class ownerClass = conf.getClass();
        try {
            for (int i = 1; i <= 48; ++i) {
                Method espMethod = ownerClass.getMethod("getEscColumn" + i, new Class[0]);
                BigDecimal temp = (BigDecimal) espMethod.invoke(conf, new Object[0]);
                conf.setEncry("EscColumn" + i, temp, empId, MyTools.BIGDECIMAL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        conf.setEncry("escBankAccountNo", conf.getEscBankAccountNo(), empId, MyTools.STRING);
    }

    public void decryEmpSalaryConf(Empsalaryconfig conf) {
        String empId = conf.getId();
        try {
            Class esaClass = conf.getClass();

            for (int i = 1; i <= 48; ++i) {
                Method esaMethod = esaClass.getMethod("setEscColumn" + i,
                                                      new Class[] { BigDecimal.class });
                esaMethod.invoke(conf, new Object[] { (BigDecimal) conf
                        .getDecry("EscColumn" + i, empId, MyTools.BIGDECIMAL) });
            }
            conf.setEscBankAccountNo((String) conf.getDecry("escBankAccountNo", empId,
                                                            MyTools.STRING));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Empsalaryconfig decryTheEmpSalaryConf(Empsalaryconfig conf) {
        Empsalaryconfig esc = null;
        try {
            esc = (Empsalaryconfig) conf.clone();
        } catch (CloneNotSupportedException e) {
            esc = new Empsalaryconfig();
            esc.setEmployee(conf.getEmployee());
        }
        String empId = conf.getId();
        try {
            Class esaClass = esc.getClass();

            for (int i = 1; i <= 48; ++i) {
                Method esaMethod = esaClass.getMethod("setEscColumn" + i,
                                                      new Class[] { BigDecimal.class });
                esaMethod.invoke(esc,
                                 new Object[] { (BigDecimal) conf.getDecry("EscColumn" + i, empId,
                                                                           MyTools.BIGDECIMAL) });
            }
            esc.setEscBankAccountNo((String) conf.getDecry("escBankAccountNo", empId,
                                                           MyTools.STRING));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return esc;
    }

    public List<Empsalaryacctitems> getAcctItems() {
        return this.acctItems;
    }

    public void setAcctItems(List<Empsalaryacctitems> acctItems) {
        this.acctItems = acctItems;
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

    public String getSalaryadjcheck() {
        return this.salaryadjcheck;
    }

    public void setSalaryadjcheck(String salaryadjcheck) {
        this.salaryadjcheck = salaryadjcheck;
    }

    public BigDecimal getShowColumn10() {
        return this.showColumn10;
    }

    public void setShowColumn10(BigDecimal showColumn10) {
        this.showColumn10 = showColumn10;
    }

    public BigDecimal getShowColumn11() {
        return this.showColumn11;
    }

    public void setShowColumn11(BigDecimal showColumn11) {
        this.showColumn11 = showColumn11;
    }

    public BigDecimal getShowColumn12() {
        return this.showColumn12;
    }

    public void setShowColumn12(BigDecimal showColumn12) {
        this.showColumn12 = showColumn12;
    }

    public BigDecimal getShowColumn13() {
        return this.showColumn13;
    }

    public void setShowColumn13(BigDecimal showColumn13) {
        this.showColumn13 = showColumn13;
    }

    public BigDecimal getShowColumn14() {
        return this.showColumn14;
    }

    public void setShowColumn14(BigDecimal showColumn14) {
        this.showColumn14 = showColumn14;
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

    public BigDecimal getShowColumn20() {
        return this.showColumn20;
    }

    public void setShowColumn20(BigDecimal showColumn20) {
        this.showColumn20 = showColumn20;
    }

    public BigDecimal getShowColumn8() {
        return this.showColumn8;
    }

    public void setShowColumn8(BigDecimal showColumn8) {
        this.showColumn8 = showColumn8;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.domain.Empsalaryconfig JD-Core Version: 0.5.4
 */