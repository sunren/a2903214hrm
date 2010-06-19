package com.hr.report.action.compensation;

import com.hr.base.BaseAction;
import com.hr.compensation.domain.Empsalarydatadef;
import com.hr.report.bo.SalaryReportBo;
import com.hr.spring.SpringBeanFactory;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import org.apache.commons.lang.StringUtils;

public class SalaryRport extends BaseAction {
    private String reportFormat;
    private String itemId;
    private String expression;
    private String reportFileLocation;

    public SalaryRport() {
        this.reportFormat = "HTML";

        this.itemId = "";

        this.expression = "";

        this.reportFileLocation = "";
    }

    public String execute() throws Exception {
        if ((StringUtils.isEmpty(this.reportFileLocation)) || (StringUtils.isEmpty(this.itemId))
                || (StringUtils.isEmpty(this.expression))) {
            return "input";
        }
        SalaryReportBo salaryReportBo = (SalaryReportBo) SpringBeanFactory
                .getBean("salaryReportBo");

        Empsalarydatadef dataDef = (Empsalarydatadef) salaryReportBo
                .loadObject(Empsalarydatadef.class, this.itemId, null);
        if (dataDef == null) {
            addErrorInfo("不存在的帐套项目＄1�7");
            return "input";
        }

        String title = dataDef.getEsddName();
        title = (title == null) ? "" : title;
        title = getCurrentYearAndMonth() + title;

        List reportItems = salaryReportBo.getObjects(this.itemId, this.expression);

        ServletRequest request = getRequest();
        if ("PDF".equalsIgnoreCase(this.reportFormat)) {
            request.setAttribute("_format", "PDF");
            request.setAttribute("_contentType", "attachment");
            request.setAttribute("_fileName", "empReport");
        } else {
            request.setAttribute("_format", "HTML");
        }
        Map params = new HashMap();
        params.put("itemId", this.itemId);
        params.put("reportObjects", reportItems);
        params.put("title", title);
        request.setAttribute("_params", params);
        request.setAttribute("_report", this.reportFileLocation);
        return "success";
    }

    private String getCurrentYearAndMonth() {
        Calendar c = Calendar.getInstance();
        String result = String.valueOf(c.get(1)) + "幄1�7";
        result = result + String.valueOf(c.get(2) + 1) + "朄1�7";
        return result;
    }

    public String getReportFormat() {
        return this.reportFormat;
    }

    public void setReportFormat(String reportFormat) {
        this.reportFormat = reportFormat;
    }

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getReportFileLocation() {
        return this.reportFileLocation;
    }

    public void setReportFileLocation(String reportFileLocation) {
        this.reportFileLocation = reportFileLocation;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.action.compensation.SalaryRport JD-Core Version: 0.5.4
 */