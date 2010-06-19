package com.hr.report.domain.base;

import com.hr.base.BaseDomain;
import java.io.Serializable;

public abstract class AbstractReportSetsDef extends BaseDomain implements Serializable {
    private String reportSetsDefID;
    private Integer reportSetsDefParamsLength = Integer.valueOf(0);
    private String reportSetsDefTable;
    private String reportSetsDefParamsType;
    private String reportSetsDefField;
    private String reportSetsDefDesc;
    private String reportSetsDefType;

    public AbstractReportSetsDef() {
    }

    public AbstractReportSetsDef(String reportSetsDefID) {
        this.reportSetsDefID = reportSetsDefID;
    }

    public String getReportSetsDefID() {
        return this.reportSetsDefID;
    }

    public void setReportSetsDefID(String reportSetsDefID) {
        this.reportSetsDefID = reportSetsDefID;
    }

    public String getReportSetsDefTable() {
        return this.reportSetsDefTable;
    }

    public void setReportSetsDefTable(String reportSetsDefTable) {
        this.reportSetsDefTable = reportSetsDefTable;
    }

    public Integer getReportSetsDefParamsLength() {
        return this.reportSetsDefParamsLength;
    }

    public void setReportSetsDefParamsLength(Integer reportSetsDefParamsLength) {
        this.reportSetsDefParamsLength = reportSetsDefParamsLength;
    }

    public String getReportSetsDefParamsType() {
        return this.reportSetsDefParamsType;
    }

    public void setReportSetsDefParamsType(String reportSetsDefParamsType) {
        this.reportSetsDefParamsType = reportSetsDefParamsType;
    }

    public String getReportSetsDefField() {
        return this.reportSetsDefField;
    }

    public void setReportSetsDefField(String reportSetsDefField) {
        this.reportSetsDefField = reportSetsDefField;
    }

    public String getReportSetsDefDesc() {
        return this.reportSetsDefDesc;
    }

    public void setReportSetsDefDesc(String reportSetsDefDesc) {
        this.reportSetsDefDesc = reportSetsDefDesc;
    }

    public String getReportSetsDefType() {
        return this.reportSetsDefType;
    }

    public void setReportSetsDefType(String reportSetsDefType) {
        this.reportSetsDefType = reportSetsDefType;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.domain.base.AbstractReportSetsDef JD-Core Version: 0.5.4
 */