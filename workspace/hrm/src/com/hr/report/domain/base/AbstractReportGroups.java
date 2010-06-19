package com.hr.report.domain.base;

import com.hr.base.BaseDomain;
import com.hr.report.domain.ReportDef;
import com.hr.report.domain.ReportSetsDef;
import java.io.Serializable;

public abstract class AbstractReportGroups extends BaseDomain implements Serializable {
    private String reportGroupsId;
    private ReportSetsDef reportSetsDef;
    private Integer reportGroupsSortId;
    private String reportGroupsFunction;
    private ReportDef reportDef;

    public AbstractReportGroups() {
    }

    public AbstractReportGroups(String reportGroupsId) {
        this.reportGroupsId = reportGroupsId;
    }

    public String getReportGroupsId() {
        return this.reportGroupsId;
    }

    public void setReportGroupsId(String reportGroupsId) {
        this.reportGroupsId = reportGroupsId;
    }

    public ReportSetsDef getReportSetsDef() {
        return this.reportSetsDef;
    }

    public void setReportSetsDef(ReportSetsDef reportSetsDef) {
        this.reportSetsDef = reportSetsDef;
    }

    public Integer getReportGroupsSortId() {
        return this.reportGroupsSortId;
    }

    public void setReportGroupsSortId(Integer reportGroupsSortId) {
        this.reportGroupsSortId = reportGroupsSortId;
    }

    public String getReportGroupsFunction() {
        return this.reportGroupsFunction;
    }

    public void setReportGroupsFunction(String reportGroupsFunction) {
        this.reportGroupsFunction = reportGroupsFunction;
    }

    public ReportDef getReportDef() {
        return this.reportDef;
    }

    public void setReportDef(ReportDef reportDef) {
        this.reportDef = reportDef;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.domain.base.AbstractReportGroups JD-Core Version: 0.5.4
 */