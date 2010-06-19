package com.hr.report.factory;

import com.hr.report.domain.ReportDef;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.ChartWithoutAxes;
import org.eclipse.birt.chart.model.attribute.LineAttributes;
import org.eclipse.birt.chart.model.attribute.Palette;
import org.eclipse.birt.chart.model.attribute.Text;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.component.Label;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.Query;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.impl.QueryImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.impl.ChartWithoutAxesImpl;
import org.eclipse.birt.chart.model.layout.Block;
import org.eclipse.birt.chart.model.layout.ClientArea;
import org.eclipse.birt.chart.model.layout.Legend;
import org.eclipse.birt.chart.model.layout.Plot;
import org.eclipse.birt.chart.model.layout.TitleBlock;
import org.eclipse.birt.chart.model.type.PieSeries;
import org.eclipse.birt.chart.model.type.impl.PieSeriesImpl;
import org.eclipse.emf.common.util.EList;

public class PieFactory extends AbstractChartFactory {
    public final Chart createChart(ReportDef model) {
        ChartWithoutAxes cwoaPie = ChartWithoutAxesImpl.create();

        cwoaPie.setDimension(getDimension(model.getReportChartDimension().intValue()));

        cwoaPie.setType("Pie Chart");
        cwoaPie.setSubType("Standard Pie Chart");
        cwoaPie.getBlock().setBounds(BoundsImpl.create(0.0D, 0.0D, 570.0D, 225.0D));
        cwoaPie.getPlot().getClientArea().getOutline().setVisible(true);
        cwoaPie.getPlot().getClientArea().setBackground(ColorDefinitionImpl.create(254, 251, 233));

        cwoaPie.getPlot()
                .setBackground(getColorDefinitionByGivenString(model.getReportBackGround()));
        cwoaPie.setSeriesThickness(10.0D);

        Legend lg = cwoaPie.getLegend();
        lg.getOutline().setVisible(true);

        cwoaPie.getTitle().getLabel().getCaption().setValue(model.getReportChartTitle());

        Series seCategory = SeriesImpl.create();
        Query query = QueryImpl.create("row[\"" + model.getReportXSeries() + "\"]");
        seCategory.getDataDefinition().add(query);

        SeriesDefinition sd = SeriesDefinitionImpl.create();
        cwoaPie.getSeriesDefinitions().add(sd);
        sd.getSeriesPalette().shift(0);
        sd.getSeries().add(seCategory);

        PieSeries sePie = (PieSeries) PieSeriesImpl.create();
        Query query1 = QueryImpl.create("row[\"" + model.getReportYSeries() + "\"]");
        sePie.getDataDefinition().add(query1);
        sePie.setExplosion(5);

        SeriesDefinition sdCity = SeriesDefinitionImpl.create();
        sd.getSeriesDefinitions().add(sdCity);
        sdCity.getSeries().add(sePie);

        return cwoaPie;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.factory.PieFactory JD-Core Version: 0.5.4
 */