package com.hr.report.template;

import com.hr.report.domain.ReportDef;
import com.hr.report.factory.ReportFactory;
import javax.servlet.ServletContext;
import org.eclipse.birt.chart.model.Chart;

public class BarTemplate extends AbstractTemplate {
    public static final String DEFAULT_FACTORY_CLASS = "com.hr.report.factory.BarFactory";

    public BarTemplate(ServletContext context, ReportDef model) {
        super(context, model);
    }

    protected Chart createChartInstance() {
        if (this.model.getReportChartDimension().intValue() == 2) {
            return ReportFactory.getChartModel("com.hr.report.factory.BarFactory",
                                               "create3DBarChart", this.model);
        }
        return ReportFactory.getChartModel("com.hr.report.factory.BarFactory", null, this.model);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.template.BarTemplate JD-Core Version: 0.5.4
 */