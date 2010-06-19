package com.hr.report.domain.base;

import com.hr.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractReportDef extends BaseDomain implements Serializable {
    private String reportId;
    private String reportName;
    private String reportDescription;
    private Integer reportModule;
    private String reportMainTable;
    private String reportFile;
    private String reportAuthority;
    private Integer reportDisplayMode;
    private String reportChartMode;
    private Integer reportChartDimension = Integer.valueOf(0);
    private String reportChartType;
    private String reportChartTitle;
    private Date reportCreateTime;
    private Date reportLastChangeTime;
    private Integer reportType = Integer.valueOf(0);
    private Integer reportUrl = Integer.valueOf(0);
    private String reportBackGround;
    private String reportXSeries;
    private String reportYSeries;
    private String reportFontColor;
    private String reportOptionalYSeries;
    private String reportLastChangeBy;
    private String reportCreateBy;
    private Set reportParams = new HashSet();
    private Set reportSets = new HashSet();

    public AbstractReportDef() {
    }

    public AbstractReportDef(String reportId) {
        this.reportId = reportId;
    }

    public String getReportId() {
        return this.reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return this.reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportFile() {
        return this.reportFile;
    }

    public void setReportFile(String reportFile) {
        this.reportFile = reportFile;
    }

    public String getReportDescription() {
        return this.reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    public Integer getReportModule() {
        return this.reportModule;
    }

    public void setReportModule(Integer reportModule) {
        this.reportModule = reportModule;
    }

    public String getReportAuthority() {
        return this.reportAuthority;
    }

    public void setReportAuthority(String reportAuthority) {
        this.reportAuthority = reportAuthority;
    }

    public Date getReportCreateTime() {
        return this.reportCreateTime;
    }

    public void setReportCreateTime(Date reportCreateTime) {
        this.reportCreateTime = reportCreateTime;
    }

    public Date getReportLastChangeTime() {
        return this.reportLastChangeTime;
    }

    public void setReportLastChangeTime(Date reportLastChangeTime) {
        this.reportLastChangeTime = reportLastChangeTime;
    }

    public String getReportLastChangeBy() {
        return this.reportLastChangeBy;
    }

    public void setReportLastChangeBy(String reportLastChangeBy) {
        this.reportLastChangeBy = reportLastChangeBy;
    }

    public Integer getReportDisplayMode() {
        return this.reportDisplayMode;
    }

    public void setReportDisplayMode(Integer reportDisplayMode) {
        this.reportDisplayMode = reportDisplayMode;
    }

    public String getReportMainTable() {
        return this.reportMainTable;
    }

    public void setReportMainTable(String reportMainTable) {
        this.reportMainTable = reportMainTable;
    }

    public String getReportCreateBy() {
        return this.reportCreateBy;
    }

    public void setReportCreateBy(String reportCreateBy) {
        this.reportCreateBy = reportCreateBy;
    }

    public Set getReportParams() {
        return this.reportParams;
    }

    public void setReportParams(Set reportParams) {
        this.reportParams = reportParams;
    }

    public Set getReportSets() {
        return this.reportSets;
    }

    public void setReportSets(Set reportSets) {
        this.reportSets = reportSets;
    }

    public String getReportChartMode() {
        return this.reportChartMode;
    }

    public void setReportChartMode(String reportChartMode) {
        this.reportChartMode = reportChartMode;
    }

    public Integer getReportChartDimension() {
        return this.reportChartDimension;
    }

    public void setReportChartDimension(Integer reportChartDimension) {
        this.reportChartDimension = reportChartDimension;
    }

    public String getReportChartType() {
        return this.reportChartType;
    }

    public void setReportChartType(String reportChartType) {
        this.reportChartType = reportChartType;
    }

    public String getReportChartTitle() {
        return this.reportChartTitle;
    }

    public void setReportChartTitle(String reportChartTitle) {
        this.reportChartTitle = reportChartTitle;
    }

    public Integer getReportType() {
        return this.reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    public Integer getReportUrl() {
        return this.reportUrl;
    }

    public void setReportUrl(Integer reportUrl) {
        this.reportUrl = reportUrl;
    }

    public String getReportBackGround() {
        return this.reportBackGround;
    }

    public void setReportBackGround(String reportBackGround) {
        this.reportBackGround = reportBackGround;
    }

    public String getReportXSeries() {
        return this.reportXSeries;
    }

    public void setReportXSeries(String reportXSeries) {
        this.reportXSeries = reportXSeries;
    }

    public String getReportYSeries() {
        return this.reportYSeries;
    }

    public void setReportYSeries(String reportYSeries) {
        this.reportYSeries = reportYSeries;
    }

    public String getReportFontColor() {
        return this.reportFontColor;
    }

    public void setReportFontColor(String reportFontColor) {
        this.reportFontColor = reportFontColor;
    }

    public String getReportOptionalYSeries() {
        return this.reportOptionalYSeries;
    }

    public void setReportOptionalYSeries(String reportOptionalYSeries) {
        this.reportOptionalYSeries = reportOptionalYSeries;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.domain.base.AbstractReportDef JD-Core Version: 0.5.4
 */