package com.hr.report.domain.base;

import com.hr.base.BaseDomain;
import java.io.Serializable;

public abstract class AbstractReportJoinDef extends BaseDomain implements Serializable {
    private String reportJoinDefId;
    private String reportJoinDefMainTable;
    private String reportJoinDefAssistantTable;
    private String reportJoinDefMainJoin;
    private String reportJoinDefAssistantJoin;

    public String getReportJoinDefId() {
        return this.reportJoinDefId;
    }

    public void setReportJoinDefId(String reportJoinDefId) {
        this.reportJoinDefId = reportJoinDefId;
    }

    public String getReportJoinDefMainTable() {
        return this.reportJoinDefMainTable;
    }

    public void setReportJoinDefMainTable(String reportJoinDefMainTable) {
        this.reportJoinDefMainTable = reportJoinDefMainTable;
    }

    public String getReportJoinDefAssistantTable() {
        return this.reportJoinDefAssistantTable;
    }

    public void setReportJoinDefAssistantTable(String reportJoinDefAssistantTable) {
        this.reportJoinDefAssistantTable = reportJoinDefAssistantTable;
    }

    public String getReportJoinDefMainJoin() {
        return this.reportJoinDefMainJoin;
    }

    public void setReportJoinDefMainJoin(String reportJoinDefMainJoin) {
        this.reportJoinDefMainJoin = reportJoinDefMainJoin;
    }

    public String getReportJoinDefAssistantJoin() {
        return this.reportJoinDefAssistantJoin;
    }

    public void setReportJoinDefAssistantJoin(String reportJoinDefAssistantJoin) {
        this.reportJoinDefAssistantJoin = reportJoinDefAssistantJoin;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.domain.base.AbstractReportJoinDef JD-Core Version: 0.5.4
 */