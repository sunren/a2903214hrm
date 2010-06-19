package com.hr.configuration.domain.base;

import com.hr.base.BaseDomain;
import com.hr.configuration.domain.Location;
import java.io.Serializable;
import java.math.BigDecimal;

public class BaseBenefitType extends BaseDomain implements Serializable {
    private String id;
    private String benefitTypeName;
    private String benefitTypeDescription;
    private int benefitTypeSortId;
    private BigDecimal ebfTypePensionUplimit;
    private BigDecimal ebfTypePensionLowlimit;
    private BigDecimal ebfTypeUnemploymentUplimit;
    private BigDecimal ebfTypeUnemploymentLowlimit;
    private BigDecimal ebfTypeMedicareUplimit;
    private BigDecimal ebfTypeMedicareLowlimit;
    private BigDecimal ebfTypeInjuryUplimit;
    private BigDecimal ebfTypeInjuryLowlimit;
    private BigDecimal ebfTypeChildbirthUplimit;
    private BigDecimal ebfTypeChildbirthLowlimit;
    private BigDecimal ebfTypeHousingUplimit;
    private BigDecimal ebfTypeHousingLowlimit;
    private BigDecimal ebfTypeOtherUplimit;
    private BigDecimal ebfTypeOtherLowlimit;
    private Integer benefitTypePayType;
    private Location benefitTypeLocNo;

    public BaseBenefitType() {
        initialize();
    }

    public BaseBenefitType(String id) {
        setId(id);
        initialize();
    }

    protected void initialize() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBenefitTypeName() {
        return this.benefitTypeName;
    }

    public void setBenefitTypeName(String benefitTypeName) {
        this.benefitTypeName = benefitTypeName;
    }

    public String getBenefitTypeDescription() {
        return this.benefitTypeDescription;
    }

    public void setBenefitTypeDescription(String benefitTypeDescription) {
        this.benefitTypeDescription = benefitTypeDescription;
    }

    public int getBenefitTypeSortId() {
        return this.benefitTypeSortId;
    }

    public void setBenefitTypeSortId(int benefitTypeSortId) {
        this.benefitTypeSortId = benefitTypeSortId;
    }

    public BigDecimal getEbfTypePensionUplimit() {
        return this.ebfTypePensionUplimit;
    }

    public void setEbfTypePensionUplimit(BigDecimal ebfTypePensionUplimit) {
        this.ebfTypePensionUplimit = ebfTypePensionUplimit;
    }

    public BigDecimal getEbfTypePensionLowlimit() {
        return this.ebfTypePensionLowlimit;
    }

    public void setEbfTypePensionLowlimit(BigDecimal ebfTypePensionLowlimit) {
        this.ebfTypePensionLowlimit = ebfTypePensionLowlimit;
    }

    public BigDecimal getEbfTypeHousingUplimit() {
        return this.ebfTypeHousingUplimit;
    }

    public void setEbfTypeHousingUplimit(BigDecimal ebfTypeHousingUplimit) {
        this.ebfTypeHousingUplimit = ebfTypeHousingUplimit;
    }

    public BigDecimal getEbfTypeHousingLowlimit() {
        return this.ebfTypeHousingLowlimit;
    }

    public void setEbfTypeHousingLowlimit(BigDecimal ebfTypeHousingLowlimit) {
        this.ebfTypeHousingLowlimit = ebfTypeHousingLowlimit;
    }

    public BigDecimal getEbfTypeOtherUplimit() {
        return this.ebfTypeOtherUplimit;
    }

    public void setEbfTypeOtherUplimit(BigDecimal ebfTypeOtherUplimit) {
        this.ebfTypeOtherUplimit = ebfTypeOtherUplimit;
    }

    public BigDecimal getEbfTypeOtherLowlimit() {
        return this.ebfTypeOtherLowlimit;
    }

    public void setEbfTypeOtherLowlimit(BigDecimal ebfTypeOtherLowlimit) {
        this.ebfTypeOtherLowlimit = ebfTypeOtherLowlimit;
    }

    public Location getBenefitTypeLocNo() {
        return this.benefitTypeLocNo;
    }

    public void setBenefitTypeLocNo(Location benefitTypeLocNo) {
        this.benefitTypeLocNo = benefitTypeLocNo;
    }

    public Integer getBenefitTypePayType() {
        return this.benefitTypePayType;
    }

    public void setBenefitTypePayType(Integer benefitTypePayType) {
        this.benefitTypePayType = benefitTypePayType;
    }

    public BigDecimal getEbfTypeUnemploymentUplimit() {
        return this.ebfTypeUnemploymentUplimit;
    }

    public void setEbfTypeUnemploymentUplimit(BigDecimal ebfTypeUnemploymentUplimit) {
        this.ebfTypeUnemploymentUplimit = ebfTypeUnemploymentUplimit;
    }

    public BigDecimal getEbfTypeUnemploymentLowlimit() {
        return this.ebfTypeUnemploymentLowlimit;
    }

    public void setEbfTypeUnemploymentLowlimit(BigDecimal ebfTypeUnemploymentLowlimit) {
        this.ebfTypeUnemploymentLowlimit = ebfTypeUnemploymentLowlimit;
    }

    public BigDecimal getEbfTypeMedicareUplimit() {
        return this.ebfTypeMedicareUplimit;
    }

    public void setEbfTypeMedicareUplimit(BigDecimal ebfTypeMedicareUplimit) {
        this.ebfTypeMedicareUplimit = ebfTypeMedicareUplimit;
    }

    public BigDecimal getEbfTypeMedicareLowlimit() {
        return this.ebfTypeMedicareLowlimit;
    }

    public void setEbfTypeMedicareLowlimit(BigDecimal ebfTypeMedicareLowlimit) {
        this.ebfTypeMedicareLowlimit = ebfTypeMedicareLowlimit;
    }

    public BigDecimal getEbfTypeInjuryUplimit() {
        return this.ebfTypeInjuryUplimit;
    }

    public void setEbfTypeInjuryUplimit(BigDecimal ebfTypeInjuryUplimit) {
        this.ebfTypeInjuryUplimit = ebfTypeInjuryUplimit;
    }

    public BigDecimal getEbfTypeInjuryLowlimit() {
        return this.ebfTypeInjuryLowlimit;
    }

    public void setEbfTypeInjuryLowlimit(BigDecimal ebfTypeInjuryLowlimit) {
        this.ebfTypeInjuryLowlimit = ebfTypeInjuryLowlimit;
    }

    public BigDecimal getEbfTypeChildbirthUplimit() {
        return this.ebfTypeChildbirthUplimit;
    }

    public void setEbfTypeChildbirthUplimit(BigDecimal ebfTypeChildbirthUplimit) {
        this.ebfTypeChildbirthUplimit = ebfTypeChildbirthUplimit;
    }

    public BigDecimal getEbfTypeChildbirthLowlimit() {
        return this.ebfTypeChildbirthLowlimit;
    }

    public void setEbfTypeChildbirthLowlimit(BigDecimal ebfTypeChildbirthLowlimit) {
        this.ebfTypeChildbirthLowlimit = ebfTypeChildbirthLowlimit;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.domain.base.BaseBenefitType JD-Core Version: 0.5.4
 */