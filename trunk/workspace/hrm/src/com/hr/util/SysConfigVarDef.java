package com.hr.util;

import java.util.Map;

public class SysConfigVarDef {
    private Integer joinYearCalc = Integer.valueOf(15);

    private Double taxBase = Double.valueOf(2000.0D);
    private String taxRange = "0,500,2000,5000,20000,40000,60000,80000,100000";
    private String taxRate = "0.05,0.10,0.15,0.20,0.25,0.30,0.35,0.40,0.45";

    private String monthSum = "1-1";

    public SysConfigVarDef(Map<String, String> dbMap) {
        if (dbMap == null)
            return;

        String dbconfig = (String) dbMap.get("sys.salary.joinyear.calc");
        if (dbconfig != null)
            this.joinYearCalc = Integer.valueOf(dbconfig);

        dbconfig = (String) dbMap.get("sys.salary.tax.min");
        if (dbconfig != null)
            this.taxBase = Double.valueOf(dbconfig);

        this.taxRange = ((String) dbMap.get("sys.salary.tax.range"));
        this.taxRate = ((String) dbMap.get("sys.salary.tax.rate"));

        this.monthSum = ((String) dbMap.get("sys.examin.month.sum"));
    }

    public Integer getJoinYearCalc() {
        return this.joinYearCalc;
    }

    public void setJoinYearCalc(Integer joinYearCalc) {
        this.joinYearCalc = joinYearCalc;
    }

    public Double getTaxBase() {
        return this.taxBase;
    }

    public void setTaxBase(Double taxBase) {
        this.taxBase = taxBase;
    }

    public String getTaxRange() {
        return this.taxRange;
    }

    public void setTaxRange(String taxRange) {
        this.taxRange = taxRange;
    }

    public String getTaxRate() {
        return this.taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getMonthSum() {
        return this.monthSum;
    }

    public void setMonthSum(String monthSum) {
        this.monthSum = monthSum;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.SysConfigVarDef JD-Core Version: 0.5.4
 */