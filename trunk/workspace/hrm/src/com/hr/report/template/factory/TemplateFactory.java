package com.hr.report.template.factory;

import com.hr.report.domain.ReportDef;
import com.hr.report.template.AbstractTemplate;
import com.hr.report.template.BarTemplate;
import com.hr.report.template.LineTemplate;
import com.hr.report.template.PieTemplate;
import javax.servlet.ServletContext;
import org.apache.struts2.ServletActionContext;

public class TemplateFactory {
    public static AbstractTemplate getReportTemplate(ReportDef model) {
        ServletContext context = ServletActionContext.getServletContext();
        AbstractTemplate template = null;
        String type = model.getReportChartMode();
        if (type.indexOf("Pie") != -1)
            template = new PieTemplate(context, model);
        else if (type.indexOf("Bar") != -1)
            template = new BarTemplate(context, model);
        else if (type.indexOf("Line") != -1)
            template = new LineTemplate(context, model);
        else {
            throw new IllegalArgumentException("不支持的报表类型");
        }

        return template;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.template.factory.TemplateFactory JD-Core Version: 0.5.4
 */