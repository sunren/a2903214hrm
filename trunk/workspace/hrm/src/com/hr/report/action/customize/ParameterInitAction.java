package com.hr.report.action.customize;

import com.hr.base.BaseAction;
import com.hr.report.bo.CustomizeReportBo;
import com.hr.report.bo.CustomizeReportParamsBo;
import com.hr.report.domain.ReportDef;
import com.hr.report.util.BirtReportFilelocator;
import com.hr.spring.SpringBeanFactory;
import java.io.File;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;

public class ParameterInitAction extends BaseAction {
    private List params;
    private ReportDef report;
    private String reportId;

    public String execute() throws Exception {
        if (StringUtils.isEmpty(this.reportId)) {
            addErrorInfo("错误的请求参数！");
            return "input";
        }

        boolean auth = false;
        CustomizeReportBo defBo = (CustomizeReportBo) SpringBeanFactory
                .getBean("customizeReportBo");
        ReportDef reportDef = defBo.loadReport(this.reportId, null);
        String authList = reportDef.getReportAuthority();
        String[] authArray = authList.split(",");
        for (int i = 0; i < authArray.length; ++i) {
            String tmp = authArray[i];
            if (tmp.contains("&")) {
                String[] tmpArray = tmp.split("&");
                auth = hasAuthModuleCondition(Integer.valueOf(StringUtils.trim(tmpArray[0]))
                        .intValue(), Integer.valueOf(StringUtils.trim(tmpArray[1])).intValue());
            } else {
                auth = hasAuth(Integer.valueOf(StringUtils.trim(tmp)).intValue());
            }
            if (auth) {
                break;
            }
        }
        if (!auth) {
            addErrorInfo("您没有运行此报表的权限！");
            return "ban";
        }

        if (StringUtils.isEmpty(this.reportId)) {
            addErrorInfo("错误的请求参数！您要查看的报表记录不存在或已经被删除＄1�7");
            return "input";
        }
        CustomizeReportBo customizeReportBo = (CustomizeReportBo) SpringBeanFactory
                .getBean("customizeReportBo");
        ReportDef report = customizeReportBo.loadReport(this.reportId, null);
        if (report == null) {
            addErrorInfo("错误的请求参数！您要查看的报表记录不存在或已经被删除＄1�7");
            return "input";
        }

        String fullFilePath = BirtReportFilelocator.getBirtReportFileRelativeLocation(report
                .getReportType())
                + report.getReportFile();
        if (fullFilePath.indexOf(".rptdesign") == -1) {
            fullFilePath = fullFilePath + ".rptdesign";
        }

        HttpServletRequest request = getRequest();
        String basePath = request.getSession().getServletContext().getRealPath("") + "/";
        String fileRealPath = basePath + fullFilePath;
        if (!new File(fileRealPath).exists()) {
            addErrorInfo("报表文件不存在或已经被删除！");
            return "input";
        }

        CustomizeReportParamsBo paramBo = (CustomizeReportParamsBo) SpringBeanFactory
                .getBean("customizeReportParamsBo");
        this.params = paramBo.findReportParams(this.reportId);
        return "success";
    }

    public List getParams() {
        return this.params;
    }

    public void setParams(List params) {
        this.params = params;
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
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.action.customize.ParameterInitAction JD-Core Version: 0.5.4
 */