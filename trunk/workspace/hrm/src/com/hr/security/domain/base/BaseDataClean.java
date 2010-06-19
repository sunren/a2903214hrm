package com.hr.security.domain.base;

import com.hr.base.BaseDomain;

public class BaseDataClean extends BaseDomain {
    private String id;
    private String descript;
    private String model;
    private String creatBy;
    private String creatTime;
    private String changeBy;
    private String changeTime;
    private String state;
    private String tag;

    public BaseDataClean() {
        this.id = null;

        this.descript = null;

        this.model = null;

        this.creatBy = null;

        this.creatTime = null;

        this.changeBy = null;

        this.changeTime = null;

        this.state = null;

        this.tag = null;
    }

    public String getChangeBy() {
        return this.changeBy;
    }

    public void setChangeBy(String changeBy) {
        this.changeBy = changeBy;
    }

    public String getChangeTime() {
        return this.changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public String getCreatBy() {
        return this.creatBy;
    }

    public void setCreatBy(String creatBy) {
        this.creatBy = creatBy;
    }

    public String getCreatTime() {
        return this.creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getDescript() {
        return this.descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.domain.base.BaseDataClean JD-Core Version: 0.5.4
 */