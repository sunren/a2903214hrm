package com.hr.report.domain;

import com.hr.report.domain.base.AbstractReportSets;
import java.io.Serializable;

public class ReportSets extends AbstractReportSets implements Serializable {
    private String source;
    private String fieldName;

    public ReportSets() {
    }

    public ReportSets(String reportSetsId) {
        super(reportSetsId);
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.domain.ReportSets JD-Core Version: 0.5.4
 */