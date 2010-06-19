package com.hr.report.domain.base;

import com.hr.base.BaseDomain;
import com.hr.report.domain.ReportDef;
import com.hr.report.domain.ReportSetsDef;
import java.io.Serializable;

public abstract class AbstractReportParams extends BaseDomain implements Serializable {
    private String reportParamsId;
    private ReportSetsDef reportSetsDef;
    private Integer reportParamsSortId;
    private String reportParamsType;
    private ReportDef reportDef;
    private String reportParamsCondition;
    private String reportParamsRangeLow;
    private String reportParamsRangeHigh;

    public AbstractReportParams() {
    }

    public AbstractReportParams(String reportParamsId) {
        this.reportParamsId = reportParamsId;
    }

    public String getReportParamsId() {
        return this.reportParamsId;
    }

    public void setReportParamsId(String reportParamsId) {
        this.reportParamsId = reportParamsId;
    }

    public ReportSetsDef getReportSetsDef() {
        return this.reportSetsDef;
    }

    public void setReportSetsDef(ReportSetsDef reportSetsDef) {
        this.reportSetsDef = reportSetsDef;
    }

    public Integer getReportParamsSortId() {
        return this.reportParamsSortId;
    }

    public void setReportParamsSortId(Integer reportParamsSortId) {
        this.reportParamsSortId = reportParamsSortId;
    }

    public String getReportParamsType() {
        return this.reportParamsType;
    }

    public void setReportParamsType(String reportParamsType) {
        this.reportParamsType = reportParamsType;
    }

    public ReportDef getReportDef() {
        return this.reportDef;
    }

    public void setReportDef(ReportDef reportDef) {
        this.reportDef = reportDef;
    }

    public String getReportParamsCondition() {
        return this.reportParamsCondition;
    }

    public void setReportParamsCondition(String reportParamsCondition) {
        this.reportParamsCondition = reportParamsCondition;
    }

    public String getReportParamsRangeLow() {
        return this.reportParamsRangeLow;
    }

    public void setReportParamsRangeLow(String reportParamsRangeLow) {
        this.reportParamsRangeLow = reportParamsRangeLow;
    }

    public String getReportParamsRangeHigh() {
        return this.reportParamsRangeHigh;
    }

    public void setReportParamsRangeHigh(String reportParamsRangeHigh) {
        this.reportParamsRangeHigh = reportParamsRangeHigh;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.domain.base.AbstractReportParams JD-Core Version: 0.5.4
 */