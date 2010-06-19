package com.hr.report.action.customize;

import com.hr.base.BaseAction;
import com.hr.report.bo.CustomizeReportBo;
import com.hr.report.bo.CustomizeReportJoinDefBo;
import com.hr.report.bo.CustomizeReportModuleDefBo;
import com.hr.report.bo.CustomizeReportParamsBo;
import com.hr.report.bo.CustomizeReportSetsBo;
import com.hr.report.domain.ReportDef;
import com.hr.report.domain.ReportJoinDef;
import com.hr.report.domain.ReportParams;
import com.hr.report.domain.ReportSets;
import com.hr.report.domain.ReportSetsDef;
import com.hr.spring.SpringBeanFactory;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class UpdateReportInitAction extends BaseAction {
    private String reportId;
    private List reportSets;
    private List reportParams;
    private ReportDef report;

    public String execute() throws Exception {
        if (StringUtils.isEmpty(this.reportId)) {
            return "error";
        }

        CustomizeReportBo customizeReportBo = (CustomizeReportBo) SpringBeanFactory
                .getBean("customizeReportBo");
        this.report = customizeReportBo.loadReport(this.reportId, null);

        CustomizeReportSetsBo reportSetsBo = (CustomizeReportSetsBo) SpringBeanFactory
                .getBean("customizeReportSetsBo");
        this.reportSets = reportSetsBo.getOutputColumnsByReportDefId(this.reportId);

        CustomizeReportParamsBo paramsBo = (CustomizeReportParamsBo) SpringBeanFactory
                .getBean("customizeReportParamsBo");
        this.reportParams = paramsBo.findReportParams(this.reportId);
        coifg();
        return "success";
    }

    private void coifg() {
        String mainTable = this.report.getReportMainTable();
        CustomizeReportJoinDefBo reportJoinDefBo = (CustomizeReportJoinDefBo) SpringBeanFactory
                .getBean("customizeReportJoinDefBo");
        CustomizeReportModuleDefBo moduleDefBo = (CustomizeReportModuleDefBo) SpringBeanFactory
                .getBean("customizeReportModuleDefBo");
        List list = reportJoinDefBo.findJoinDefListByMainTableName(mainTable);
        int assLen = list.size();
        ReportJoinDef joinDef = null;
        String tmp = null;

        int setLen = this.reportSets.size();
        ReportSets set = null;
        for (int i = 0; i < setLen; ++i) {
            set = (ReportSets) this.reportSets.get(i);
            tmp = set.getReportSetsReportSetsDef().getReportSetsDefTable();
            if (!mainTable.equalsIgnoreCase(tmp)) {
                for (int j = 0; j < assLen; ++j) {
                    joinDef = (ReportJoinDef) list.get(j);
                    if (joinDef.getReportJoinDefAssistantTable().equalsIgnoreCase(tmp)) {
                        break;
                    }
                    if (j == assLen - 1) {
                        set
                                .getReportSetsReportSetsDef()
                                .setReportSetsDefTable(
                                                       set.getReportSetsReportSetsDef()
                                                               .getReportSetsDefTable()
                                                               + "&"
                                                               + joinDef
                                                                       .getReportJoinDefAssistantTable());
                        this.reportSets.set(i, set);
                    }

                }

            }

        }

        int parLen = this.reportParams.size();
        ReportParams param = null;
        for (int i = 0; i < parLen; ++i) {
            param = (ReportParams) this.reportParams.get(i);
            tmp = param.getReportSetsDef().getReportSetsDefTable();
            if (!mainTable.equalsIgnoreCase(tmp))
                for (int j = 0; j < assLen; ++j) {
                    joinDef = (ReportJoinDef) list.get(j);
                    if (joinDef.getReportJoinDefAssistantTable().equalsIgnoreCase(tmp)) {
                        break;
                    }
                    if (j == assLen - 1) {
                        param
                                .getReportSetsDef()
                                .setReportSetsDefTable(
                                                       set.getReportSetsReportSetsDef()
                                                               .getReportSetsDefTable()
                                                               + "&"
                                                               + joinDef
                                                                       .getReportJoinDefAssistantTable());
                        this.reportParams.set(i, param);
                    }
                }
        }
    }

    public String getReportId() {
        return this.reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public ReportDef getReport() {
        return this.report;
    }

    public void setReport(ReportDef report) {
        this.report = report;
    }

    public List getReportSets() {
        return this.reportSets;
    }

    public void setReportSets(List reportSets) {
        this.reportSets = reportSets;
    }

    public List getReportParams() {
        return this.reportParams;
    }

    public void setReportParams(List reportParams) {
        this.reportParams = reportParams;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.action.customize.UpdateReportInitAction JD-Core Version: 0.5.4
 */