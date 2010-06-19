package com.hr.report.action.profile.support;

import java.io.Serializable;

public class DimissionRateBean implements Serializable {
    private String name;
    private int original;
    private int active;
    private int dimission;
    private int total;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActive() {
        return this.active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getDimission() {
        return this.dimission;
    }

    public void setDimission(int dimission) {
        this.dimission = dimission;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOriginal() {
        return this.original;
    }

    public void setOriginal(int original) {
        this.original = original;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.action.profile.support.DimissionRateBean JD-Core Version: 0.5.4
 */