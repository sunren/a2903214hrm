package com.hr.report.domain.base;

import com.hr.base.BaseDomain;
import java.io.Serializable;

public abstract class AbstractReportModuleDef extends BaseDomain implements Serializable {
    private String reportModuleDefId;
    private Integer reportModuleDefModule;
    private Integer reportModuleDefFlag;
    private String reportModuleDefTable;
    private String reportModuleDefTableDescription;

    public String getReportModuleDefId() {
        return this.reportModuleDefId;
    }

    public void setReportModuleDefId(String reportModuleDefId) {
        this.reportModuleDefId = reportModuleDefId;
    }

    public Integer getReportModuleDefModule() {
        return this.reportModuleDefModule;
    }

    public void setReportModuleDefModule(Integer reportModuleDefModule) {
        this.reportModuleDefModule = reportModuleDefModule;
    }

    public Integer getReportModuleDefFlag() {
        return this.reportModuleDefFlag;
    }

    public void setReportModuleDefFlag(Integer reportModuleDefFlag) {
        this.reportModuleDefFlag = reportModuleDefFlag;
    }

    public String getReportModuleDefTable() {
        return this.reportModuleDefTable;
    }

    public void setReportModuleDefTable(String reportModuleDefTable) {
        this.reportModuleDefTable = reportModuleDefTable;
    }

    public String getReportModuleDefTableDescription() {
        return this.reportModuleDefTableDescription;
    }

    public void setReportModuleDefTableDescription(String reportModuleDefTableDescription) {
        this.reportModuleDefTableDescription = reportModuleDefTableDescription;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.domain.base.AbstractReportModuleDef JD-Core Version: 0.5.4
 */