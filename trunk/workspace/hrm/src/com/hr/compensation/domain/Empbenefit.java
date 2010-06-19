package com.hr.compensation.domain;

import com.hr.compensation.domain.base.BaseEmpbenefit;

public class Empbenefit extends BaseEmpbenefit {
    private static final long serialVersionUID = 1L;
    public static String REF = "Empbenefit";
    private String belongYearMonth;
    private String beneType;

    public Empbenefit() {
    }

    public Empbenefit(String ebfId) {
    }

    public String getBeneType() {
        return this.beneType;
    }

    public void setBeneType(String beneType) {
        this.beneType = beneType;
    }

    public String getBelongYearMonth() {
        return this.belongYearMonth;
    }

    public void setBelongYearMonth(String belongYearMonth) {
        this.belongYearMonth = belongYearMonth;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.domain.Empbenefit JD-Core Version: 0.5.4
 */