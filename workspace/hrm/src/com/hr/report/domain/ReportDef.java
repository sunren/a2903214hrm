package com.hr.report.domain;

import com.hr.report.domain.base.AbstractReportDef;
import java.io.Serializable;

public class ReportDef extends AbstractReportDef implements Serializable {
    private String sql;

    public ReportDef() {
    }

    public ReportDef(String reportId) {
        super(reportId);
    }

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.domain.ReportDef JD-Core Version: 0.5.4
 */