package com.hr.report.action.customize;

import com.hr.base.BaseAction;
import com.hr.report.bo.CustomizeReportBo;
import com.hr.report.domain.ReportDef;
import com.hr.report.domain.ReportParams;
import com.hr.report.domain.ReportSets;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;

public class EditReportAction extends BaseAction {
    private ReportDef report;
    private List<ReportParams> params;
    private List<ReportSets> reportSets;
    private String[] setsSource;
    private String[] setsFieldName;
    private String[] setsGroup;
    private String[] setsFunction;
    private String[] setsDesc;
    private String[] paramsSortId;
    private String[] paramsSource;
    private String[] paramsFieldName;
    private String[] paramsCondition;
    private String[] paramsDefaultValue;
    private String updateFlag;

    public EditReportAction() {
        this.params = new ArrayList();

        this.reportSets = new ArrayList();
    }

    public String execute() throws Exception {
        if (this.setsSource == null) {
            return "input";
        }
        if ("create".equalsIgnoreCase(this.updateFlag))
            return doCreate();
        if ("update".equalsIgnoreCase(this.updateFlag)) {
            return doUpdate();
        }

        return "input";
    }

    private String doCreate() {
        CustomizeReportBo customizeReportBo = (CustomizeReportBo) SpringBeanFactory
                .getBean("customizeReportBo");
        Userinfo user = (Userinfo) getSession().getAttribute("userinfo");
        this.report.setReportCreateTime(new Date());
        this.report.setReportLastChangeBy(user.getId());
        this.report.setReportCreateBy(user.getId());
        befroeExecute();
        List message = customizeReportBo.addReport(this.reportSets, this.report, this.params);
        if (message.isEmpty()) {
            addSuccessInfo("新增定制报表成功");
            this.report = null;
            this.params = null;
            return "success";
        }
        addErrorInfo(message);
        return "input";
    }

    public String doUpdate() {
        CustomizeReportBo customizeReportBo = (CustomizeReportBo) SpringBeanFactory
                .getBean("customizeReportBo");
        Userinfo user = (Userinfo) getSession().getAttribute("userinfo");
        this.report.setReportLastChangeBy(user.getId());
        this.report.setReportCreateTime(new Date());
        befroeExecute();
        List message = customizeReportBo.updateReport(this.reportSets, this.report, this.params);
        if (message.isEmpty()) {
            addSuccessInfo("更新定制报表成功");
            this.report = null;
            this.params = null;
            return "success";
        }
        addErrorInfo(message);
        return "input";
    }

    private void befroeExecute() {
        this.report.setReportType(Integer.valueOf(0));
        this.report.setReportUrl(Integer.valueOf(0));

        int setsLen = this.setsSource.length;
        ReportSets set = null;
        for (int i = 0; i < setsLen; ++i) {
            set = new ReportSets();
            set.setSource(this.setsSource[i]);
            set.setFieldName(this.setsFieldName[i]);
            set.setReportSetsGroupFunction(this.setsGroup[i]);
            set.setReportSetsFunction(this.setsFunction[i]);
            set.setReportSetsDesc(this.setsDesc[i]);
            set.setReportSetsSortId(Integer.valueOf(i));
            this.reportSets.add(set);
        }

        if (null == this.paramsSource) {
            return;
        }

        int paramsLen = this.paramsSource.length;
        ReportParams param = null;
        for (int i = 0; i < paramsLen; ++i) {
            param = new ReportParams();
            param.setSource(this.paramsSource[i]);
            param.setFieldName(this.paramsFieldName[i]);
            param.setReportParamsCondition(this.paramsCondition[i]);
            param.setDefaultValue(this.paramsDefaultValue[i]);
            param.setReportParamsSortId(Integer.valueOf(i));
            param.setReportParamsCondition(this.paramsCondition[i]);
            this.params.add(param);
        }
    }

    public ReportDef getReport() {
        return this.report;
    }

    public void setReport(ReportDef report) {
        this.report = report;
    }

    public List<ReportParams> getParams() {
        return this.params;
    }

    public void setParams(List<ReportParams> params) {
        this.params = params;
    }

    public List<ReportSets> getReportSets() {
        return this.reportSets;
    }

    public void setReportSets(List<ReportSets> reportSets) {
        this.reportSets = reportSets;
    }

    public String[] getSetsSource() {
        return this.setsSource;
    }

    public void setSetsSource(String[] setsSource) {
        this.setsSource = setsSource;
    }

    public String[] getSetsFieldName() {
        return this.setsFieldName;
    }

    public void setSetsFieldName(String[] setsFieldName) {
        this.setsFieldName = setsFieldName;
    }

    public String[] getSetsGroup() {
        return this.setsGroup;
    }

    public void setSetsGroup(String[] setsGroup) {
        this.setsGroup = setsGroup;
    }

    public String[] getSetsFunction() {
        return this.setsFunction;
    }

    public void setSetsFunction(String[] setsFunction) {
        this.setsFunction = setsFunction;
    }

    public String[] getSetsDesc() {
        return this.setsDesc;
    }

    public void setSetsDesc(String[] setsDesc) {
        this.setsDesc = setsDesc;
    }

    public String[] getParamsSource() {
        return this.paramsSource;
    }

    public void setParamsSource(String[] paramsSource) {
        this.paramsSource = paramsSource;
    }

    public String[] getParamsFieldName() {
        return this.paramsFieldName;
    }

    public void setParamsFieldName(String[] paramsFieldName) {
        this.paramsFieldName = paramsFieldName;
    }

    public String[] getParamsCondition() {
        return this.paramsCondition;
    }

    public void setParamsCondition(String[] paramsCondition) {
        this.paramsCondition = paramsCondition;
    }

    public String[] getParamsDefaultValue() {
        return this.paramsDefaultValue;
    }

    public void setParamsDefaultValue(String[] paramsDefaultValue) {
        this.paramsDefaultValue = paramsDefaultValue;
    }

    public String[] getParamsSortId() {
        return this.paramsSortId;
    }

    public void setParamsSortId(String[] paramsSortId) {
        this.paramsSortId = paramsSortId;
    }

    public String getUpdateFlag() {
        return this.updateFlag;
    }

    public void setUpdateFlag(String updateFlag) {
        this.updateFlag = updateFlag;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.action.customize.EditReportAction JD-Core Version: 0.5.4
 */