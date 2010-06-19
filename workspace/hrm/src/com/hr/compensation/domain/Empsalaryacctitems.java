package com.hr.compensation.domain;

import com.hr.compensation.domain.base.BaseEmpsalaryacctitems;
import java.math.BigDecimal;

public class Empsalaryacctitems extends BaseEmpsalaryacctitems implements Cloneable {
    private static final long serialVersionUID = 1L;
    private BigDecimal itemValue;
    private BigDecimal itemConfigValue;
    private Integer itemChanged;
    private String esaiDataSeqStr;

    public String getEsaiDataSeqStr() {
        return "A" + getEsaiDataSeq();
    }

    public void setEsaiDataSeqStr(String esaiDataSeqStr) {
        setEsaiDataSeq(Integer.valueOf(Integer.parseInt(esaiDataSeqStr.substring(1))));
    }

    public Integer getItemChanged() {
        return this.itemChanged;
    }

    public void setItemChanged(Integer itemDiff) {
        this.itemChanged = itemDiff;
    }

    public Empsalaryacctitems() {
    }

    public Empsalaryacctitems(String id) {
        super(id);
    }

    public Empsalaryacctitems(String id, Empsalaryacctversion esaiEsav, Integer esaiDataSeq,
            Integer esaiDataIsCalc, Integer esaiDataRounding) {
        super(id, esaiEsav, esaiDataSeq, esaiDataIsCalc, esaiDataRounding);
    }

    public BigDecimal getItemValue() {
        return this.itemValue;
    }

    public void setItemValue(BigDecimal itemValue) {
        this.itemValue = itemValue;
    }

    public BigDecimal getItemConfigValue() {
        return this.itemConfigValue;
    }

    public void setItemConfigValue(BigDecimal itemConfigValue) {
        this.itemConfigValue = itemConfigValue;
    }

    public Empsalaryacctitems clone() {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return (Empsalaryacctitems) obj;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.domain.Empsalaryacctitems JD-Core Version: 0.5.4
 */