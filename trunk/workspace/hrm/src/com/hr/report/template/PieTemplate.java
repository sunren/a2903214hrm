package com.hr.report.template;

import com.hr.report.domain.ReportDef;
import com.hr.report.factory.ReportFactory;
import javax.servlet.ServletContext;
import org.eclipse.birt.chart.model.Chart;

public class PieTemplate extends AbstractTemplate {
    public static final String DEFAULT_FACTORY_CLASS = "com.hr.report.factory.PieFactory";

    public PieTemplate(ServletContext context, ReportDef model) {
        super(context, model);
    }

    protected Chart createChartInstance() {
        return ReportFactory.getChartModel("com.hr.report.factory.PieFactory", null, this.model);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.template.PieTemplate JD-Core Version: 0.5.4
 */