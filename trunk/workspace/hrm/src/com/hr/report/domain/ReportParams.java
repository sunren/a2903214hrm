package com.hr.report.domain;

import com.hr.report.domain.base.AbstractReportParams;
import java.io.Serializable;

public class ReportParams extends AbstractReportParams implements Serializable {
    private String source;
    private String fieldName;
    private String fieldType;
    private String defaultValue;

    public ReportParams() {
    }

    public ReportParams(String reportParamsId) {
        super(reportParamsId);
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

    public String getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.domain.ReportParams JD-Core Version: 0.5.4
 */