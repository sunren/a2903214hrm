package com.hr.util;

import com.hr.configuration.domain.BenefitType;
import com.hr.configuration.domain.Jobgrade;
import com.hr.profile.domain.Employee;
import java.math.BigDecimal;

public class BenefitVarDef {
    private Double D1 = Double.valueOf(0.0D);

    private Double D2 = Double.valueOf(0.0D);

    private Double D3 = Double.valueOf(0.0D);

    private Double D4 = Double.valueOf(0.0D);

    private Double D11 = Double.valueOf(0.0D);

    private Double D20 = Double.valueOf(0.0D);

    private Double D21 = Double.valueOf(0.0D);

    private Double D22 = Double.valueOf(0.0D);

    private Double D23 = Double.valueOf(0.0D);

    private Double D24 = Double.valueOf(0.0D);

    private Double D25 = Double.valueOf(0.0D);

    private Double D26 = Double.valueOf(0.0D);

    private Double D27 = Double.valueOf(0.0D);

    private Double D28 = Double.valueOf(0.0D);

    private Double D29 = Double.valueOf(0.0D);

    private Double D30 = Double.valueOf(0.0D);

    private Double D31 = Double.valueOf(0.0D);

    private Double D40 = Double.valueOf(0.0D);

    private Double D41 = Double.valueOf(0.0D);

    public BenefitVarDef(Employee employee, Jobgrade empJobgrade) {
        if (employee == null) {
            return;
        }
        BenefitType benefitType = employee.getEmpBenefitType();
        if (benefitType != null) {
            if (benefitType.getEbfTypePensionUplimit() != null)
                setD20(Double.valueOf(benefitType.getEbfTypePensionUplimit().doubleValue()));
            if (benefitType.getEbfTypePensionLowlimit() != null)
                setD21(Double.valueOf(benefitType.getEbfTypePensionLowlimit().doubleValue()));
            if (benefitType.getEbfTypeUnemploymentUplimit() != null)
                setD22(Double.valueOf(benefitType.getEbfTypeUnemploymentUplimit().doubleValue()));
            if (benefitType.getEbfTypeUnemploymentLowlimit() != null)
                setD23(Double.valueOf(benefitType.getEbfTypeUnemploymentLowlimit().doubleValue()));
            if (benefitType.getEbfTypeMedicareUplimit() != null)
                setD24(Double.valueOf(benefitType.getEbfTypeMedicareUplimit().doubleValue()));
            if (benefitType.getEbfTypeMedicareLowlimit() != null)
                setD25(Double.valueOf(benefitType.getEbfTypeMedicareLowlimit().doubleValue()));
            if (benefitType.getEbfTypeInjuryUplimit() != null)
                setD26(Double.valueOf(benefitType.getEbfTypeInjuryUplimit().doubleValue()));
            if (benefitType.getEbfTypeInjuryLowlimit() != null)
                setD27(Double.valueOf(benefitType.getEbfTypeInjuryLowlimit().doubleValue()));
            if (benefitType.getEbfTypeChildbirthUplimit() != null)
                setD28(Double.valueOf(benefitType.getEbfTypeChildbirthUplimit().doubleValue()));
            if (benefitType.getEbfTypeChildbirthLowlimit() != null)
                setD29(Double.valueOf(benefitType.getEbfTypeChildbirthLowlimit().doubleValue()));
            if (benefitType.getEbfTypeHousingUplimit() != null)
                setD30(Double.valueOf(benefitType.getEbfTypeHousingUplimit().doubleValue()));
            if (benefitType.getEbfTypeHousingLowlimit() != null)
                setD31(Double.valueOf(benefitType.getEbfTypeHousingLowlimit().doubleValue()));
            if (benefitType.getEbfTypeOtherUplimit() != null)
                setD40(Double.valueOf(benefitType.getEbfTypeOtherUplimit().doubleValue()));
            if (benefitType.getEbfTypeOtherLowlimit() != null) {
                setD41(Double.valueOf(benefitType.getEbfTypeOtherLowlimit().doubleValue()));
            }
        }
        if (empJobgrade != null) {
            if (empJobgrade.getJobGradeLevel() != null)
                setD1(Double.valueOf(empJobgrade.getJobGradeLevel().doubleValue()));
            if (empJobgrade.getJobGradeMrp() != null)
                setD11(Double.valueOf(MyTools.decryDecimal(employee.getId(),
                                                           empJobgrade.getJobGradeMrp())
                        .doubleValue()));
        }
    }

    public Double getD2() {
        return this.D2;
    }

    public void setD2(Double d2) {
        this.D2 = d2;
    }

    public Double getD3() {
        return this.D3;
    }

    public void setD3(Double d3) {
        this.D3 = d3;
    }

    public Double getD4() {
        return this.D4;
    }

    public void setD4(Double d4) {
        this.D4 = d4;
    }

    public Double getD1() {
        return new Double(this.D1.doubleValue());
    }

    public void setD1(Double d1) {
        this.D1 = d1;
    }

    public Double getD11() {
        return this.D11;
    }

    public void setD11(Double d11) {
        this.D11 = d11;
    }

    public Double getD20() {
        return this.D20;
    }

    public void setD20(Double d20) {
        this.D20 = d20;
    }

    public Double getD21() {
        return this.D21;
    }

    public void setD21(Double d21) {
        this.D21 = d21;
    }

    public Double getD22() {
        return this.D22;
    }

    public void setD22(Double d22) {
        this.D22 = d22;
    }

    public Double getD23() {
        return this.D23;
    }

    public void setD23(Double d23) {
        this.D23 = d23;
    }

    public Double getD24() {
        return this.D24;
    }

    public void setD24(Double d24) {
        this.D24 = d24;
    }

    public Double getD25() {
        return this.D25;
    }

    public void setD25(Double d25) {
        this.D25 = d25;
    }

    public Double getD26() {
        return this.D26;
    }

    public void setD26(Double d26) {
        this.D26 = d26;
    }

    public Double getD27() {
        return this.D27;
    }

    public void setD27(Double d27) {
        this.D27 = d27;
    }

    public Double getD28() {
        return this.D28;
    }

    public void setD28(Double d28) {
        this.D28 = d28;
    }

    public Double getD29() {
        return this.D29;
    }

    public void setD29(Double d29) {
        this.D29 = d29;
    }

    public Double getD30() {
        return this.D30;
    }

    public void setD30(Double d30) {
        this.D30 = d30;
    }

    public Double getD31() {
        return this.D31;
    }

    public void setD31(Double d31) {
        this.D31 = d31;
    }

    public Double getD40() {
        return this.D40;
    }

    public void setD40(Double d40) {
        this.D40 = d40;
    }

    public Double getD41() {
        return this.D41;
    }

    public void setD41(Double d41) {
        this.D41 = d41;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.BenefitVarDef JD-Core Version: 0.5.4
 */