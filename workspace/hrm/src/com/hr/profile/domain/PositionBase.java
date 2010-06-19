package com.hr.profile.domain;

import com.hr.profile.domain.base.BasePositionBase;
import java.util.Map;

public class PositionBase extends BasePositionBase {
    private static final long serialVersionUID = 1L;
    private Integer activeEmpNum;
    private Integer lackEmpNum;
    private String pbInChargeStr;
    private String pbStatusStr;
    private Map<String, String> pbOperateMap;
    private Map<String, String> pbMoveToMap;
    private Boolean maxEmpChanged = Boolean.valueOf(false);
    private Integer histMaxEmp = null;

    public Integer getActiveEmpNum() {
        return this.activeEmpNum;
    }

    public void setActiveEmpNum(Integer activeEmpNum) {
        this.activeEmpNum = activeEmpNum;
    }

    public Integer getLackEmpNum() {
        return this.lackEmpNum;
    }

    public void setLackEmpNum(Integer lackEmpNum) {
        this.lackEmpNum = lackEmpNum;
    }

    public Boolean getMaxEmpChanged() {
        return this.maxEmpChanged;
    }

    public void setMaxEmpChanged(Boolean maxEmpChanged) {
        this.maxEmpChanged = maxEmpChanged;
    }

    public PositionBase() {
    }

    public PositionBase(String id) {
        super(id);
    }

    public PositionBase(String id, String pbName, Integer pbMaxExceed, Integer pbStatus) {
        super(id, pbName, pbMaxExceed, pbStatus);
    }

    public String getPbInChargeStr() {
        return this.pbInChargeStr;
    }

    public void setPbInChargeStr(String pbInChargeStr) {
        this.pbInChargeStr = pbInChargeStr;
    }

    public String getPbStatusStr() {
        return this.pbStatusStr;
    }

    public void setPbStatusStr(String pbStatusStr) {
        this.pbStatusStr = pbStatusStr;
    }

    public Map<String, String> getPbOperateMap() {
        return this.pbOperateMap;
    }

    public void setPbOperateMap(Map<String, String> pbOperateMap) {
        this.pbOperateMap = pbOperateMap;
    }

    public Map<String, String> getPbMoveToMap() {
        return this.pbMoveToMap;
    }

    public void setPbMoveToMap(Map<String, String> pbMoveToMap) {
        this.pbMoveToMap = pbMoveToMap;
    }

    public Integer getHistMaxEmp() {
        return this.histMaxEmp;
    }

    public void setHistMaxEmp(Integer histMaxEmp) {
        this.histMaxEmp = histMaxEmp;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.domain.PositionBase JD-Core Version: 0.5.4
 */