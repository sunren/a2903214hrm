package com.hr.report.domain.base;

import com.hr.base.BaseDomain;
import com.hr.report.domain.ReportDef;
import com.hr.report.domain.ReportSetsDef;
import java.io.Serializable;

public abstract class AbstractReportSets extends BaseDomain implements Serializable {
    private String reportSetsId;
    private ReportDef reportSetsReportDef;
    private ReportSetsDef reportSetsReportSetsDef;
    private String reportSetsFunction;
    private Integer reportSetsDisplayLength = Integer.valueOf(0);
    private String reportSetsGroupFunction;
    private Integer reportSetsSortId;
    private String reportSetsDesc;

    public AbstractReportSets() {
    }

    public AbstractReportSets(String reportSetsId) {
        this.reportSetsId = reportSetsId;
    }

    public String getReportSetsId() {
        return this.reportSetsId;
    }

    public void setReportSetsId(String reportSetsId) {
        this.reportSetsId = reportSetsId;
    }

    public ReportDef getReportSetsReportDef() {
        return this.reportSetsReportDef;
    }

    public void setReportSetsReportDef(ReportDef reportSetsReportDef) {
        this.reportSetsReportDef = reportSetsReportDef;
    }

    public ReportSetsDef getReportSetsReportSetsDef() {
        return this.reportSetsReportSetsDef;
    }

    public void setReportSetsReportSetsDef(ReportSetsDef reportSetsReportSetsDef) {
        this.reportSetsReportSetsDef = reportSetsReportSetsDef;
    }

    public String getReportSetsFunction() {
        return this.reportSetsFunction;
    }

    public void setReportSetsFunction(String reportSetsFunction) {
        this.reportSetsFunction = reportSetsFunction;
    }

    public Integer getReportSetsDisplayLength() {
        return this.reportSetsDisplayLength;
    }

    public void setReportSetsDisplayLength(Integer reportSetsDisplayLength) {
        this.reportSetsDisplayLength = reportSetsDisplayLength;
    }

    public String getReportSetsGroupFunction() {
        return this.reportSetsGroupFunction;
    }

    public void setReportSetsGroupFunction(String reportSetsGroupFunction) {
        this.reportSetsGroupFunction = reportSetsGroupFunction;
    }

    public Integer getReportSetsSortId() {
        return this.reportSetsSortId;
    }

    public void setReportSetsSortId(Integer reportSetsSortId) {
        this.reportSetsSortId = reportSetsSortId;
    }

    public String getReportSetsDesc() {
        return this.reportSetsDesc;
    }

    public void setReportSetsDesc(String reportSetsDesc) {
        this.reportSetsDesc = reportSetsDesc;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.domain.base.AbstractReportSets JD-Core Version: 0.5.4
 */