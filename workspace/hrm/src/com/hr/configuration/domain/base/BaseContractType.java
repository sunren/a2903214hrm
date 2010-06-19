package com.hr.configuration.domain.base;

import com.hr.base.BaseDomain;
import java.io.Serializable;

public abstract class BaseContractType extends BaseDomain implements Serializable {
    private String id;
    private String ectName;
    private String ectDescription;
    private int ectSortId;

    public BaseContractType() {
    }

    public BaseContractType(String id, String ectName, String ectDescription, int sortId) {
        this.id = id;
        this.ectName = ectName;
        this.ectDescription = ectDescription;
        this.ectSortId = sortId;
    }

    public String getEctDescription() {
        return this.ectDescription;
    }

    public void setEctDescription(String ectDescription) {
        this.ectDescription = ectDescription;
    }

    public String getEctName() {
        return this.ectName;
    }

    public void setEctName(String ectName) {
        this.ectName = ectName;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEctSortId() {
        return this.ectSortId;
    }

    public void setEctSortId(int ectSortId) {
        this.ectSortId = ectSortId;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.domain.base.BaseContractType JD-Core Version: 0.5.4
 */