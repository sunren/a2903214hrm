package com.hr.report.action.customize;

import com.hr.base.BaseAction;
import com.hr.report.bo.CustomizeReportBo;
import com.hr.report.domain.ReportDef;
import com.hr.report.domain.ReportParams;
import com.hr.report.util.BirtReportFilelocator;
import com.hr.spring.SpringBeanFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;

public class ViewReportAction extends BaseAction {
    private List<ReportParams> paramsList;
    private String reportId;
    private String format;

    public ViewReportAction() {
        this.format = "HTML";
    }

    public String execute() throws Exception {
        if (StringUtils.isEmpty(this.reportId)) {
            addErrorInfo("错误的请求参数！您要查看的报表记录不存在或已经被删除");
            return "input";
        }
        CustomizeReportBo customizeReportBo = (CustomizeReportBo) SpringBeanFactory
                .getBean("customizeReportBo");
        ReportDef report = customizeReportBo.loadReport(this.reportId, null);
        if (report == null) {
            addErrorInfo("错误的请求参数！您要查看的报表记录不存在或已经被删除");
            return "input";
        }

        boolean auth = false;
        String authList = report.getReportAuthority();
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
            return "INPUT";
        }

        HttpServletRequest request = super.getRequest();

        if (!this.format.equalsIgnoreCase("HTML")) {
            request.setAttribute("_contentType", "attachment");
            request.setAttribute("_fileName", "report_file");
            this.format = "HTML";
        }

        String fullFilePath = BirtReportFilelocator.getBirtReportFileRelativeLocation(report
                .getReportType())
                + report.getReportFile();
        if (fullFilePath.indexOf(".rptdesign") == -1) {
            fullFilePath = fullFilePath + ".rptdesign";
        }

        String basePath = request.getSession().getServletContext().getRealPath("") + "/";
        String fileRealPath = basePath + fullFilePath;
        if (!new File(fileRealPath).exists()) {
            addErrorInfo("报表文件不存在或已经被删除！");
            return "input";
        }
        request.setAttribute("_report", fullFilePath);

        Map map = request.getParameterMap();
        Map tmpMap = new HashMap();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String[]) {
                String[] str = (String[]) (String[]) value;
                String tmp = new String(str[0].getBytes("iso-8859-1"), "GBK");
                if ("_contentType".equals((String) key))
                    request.setAttribute("_contentType", tmp);
                else if ("_format".equals((String) key))
                    request.setAttribute("_format", tmp);
                else if ("_fileName".equals((String) key)) {
                    request.setAttribute("_fileName", tmp);
                }
                tmpMap.put(key, tmp);
            } else {
                String str = (String) value;
                if ("_contentType".equals((String) key))
                    request.setAttribute("_contentType", str);
                else if ("_format".equals((String) key))
                    request.setAttribute("_format", str);
                else if ("_fileName".equals((String) key)) {
                    request.setAttribute("_fileName", str);
                }
                tmpMap.put(key, new String(str.getBytes("iso-8859-1"), "GBK"));
            }
        }
        request.setAttribute("_params", tmpMap);
        return "success";
    }

    public List<ReportParams> getParamsList() {
        return this.paramsList;
    }

    public void setParamsList(List<ReportParams> paramsList) {
        this.paramsList = paramsList;
    }

    public String getReportId() {
        return this.reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.action.customize.ViewReportAction JD-Core Version: 0.5.4
 */