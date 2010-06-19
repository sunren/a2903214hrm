package com.hr.report.template;

import com.hr.report.domain.ReportDef;
import com.hr.report.factory.ReportFactory;
import javax.servlet.ServletContext;
import org.eclipse.birt.chart.model.Chart;

public class LineTemplate extends AbstractTemplate {
    public static final String DEFAULT_FACTORY_CLASS = "com.hr.report.factory.LineFactory";

    public LineTemplate(ServletContext context, ReportDef model) {
        super(context, model);
    }

    protected Chart createChartInstance() {
        if (this.model.getReportChartDimension().intValue() == 2) {
            return ReportFactory.getChartModel("com.hr.report.factory.LineFactory",
                                               "create3DLineChart", this.model);
        }
        return ReportFactory.getChartModel("com.hr.report.factory.LineFactory", null, this.model);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.template.LineTemplate JD-Core Version: 0.5.4
 */