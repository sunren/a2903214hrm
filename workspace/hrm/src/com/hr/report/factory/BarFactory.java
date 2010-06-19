package com.hr.report.factory;

import com.hr.report.domain.ReportDef;
import org.apache.commons.lang.StringUtils;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.ChartWithAxes;
import org.eclipse.birt.chart.model.attribute.Angle3D;
import org.eclipse.birt.chart.model.attribute.AxisOrigin;
import org.eclipse.birt.chart.model.attribute.AxisType;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.birt.chart.model.attribute.FontDefinition;
import org.eclipse.birt.chart.model.attribute.IntersectionType;
import org.eclipse.birt.chart.model.attribute.LegendItemType;
import org.eclipse.birt.chart.model.attribute.LineAttributes;
import org.eclipse.birt.chart.model.attribute.LineStyle;
import org.eclipse.birt.chart.model.attribute.Orientation;
import org.eclipse.birt.chart.model.attribute.Palette;
import org.eclipse.birt.chart.model.attribute.Position;
import org.eclipse.birt.chart.model.attribute.Text;
import org.eclipse.birt.chart.model.attribute.TickStyle;
import org.eclipse.birt.chart.model.attribute.impl.Angle3DImpl;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.attribute.impl.Rotation3DImpl;
import org.eclipse.birt.chart.model.component.Axis;
import org.eclipse.birt.chart.model.component.Grid;
import org.eclipse.birt.chart.model.component.Label;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.AxisImpl;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.Query;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.impl.QueryImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.impl.ChartWithAxesImpl;
import org.eclipse.birt.chart.model.layout.Block;
import org.eclipse.birt.chart.model.layout.ClientArea;
import org.eclipse.birt.chart.model.layout.Legend;
import org.eclipse.birt.chart.model.layout.Plot;
import org.eclipse.birt.chart.model.layout.TitleBlock;
import org.eclipse.birt.chart.model.type.BarSeries;
import org.eclipse.birt.chart.model.type.impl.BarSeriesImpl;
import org.eclipse.emf.common.util.EList;

public class BarFactory extends AbstractChartFactory {
    public final Chart createChart(ReportDef model) {
        ChartWithAxes cwaBar = ChartWithAxesImpl.create();

        cwaBar.setDimension(getDimension(model.getReportChartDimension().intValue()));

        cwaBar.setType("Bar Chart");
        cwaBar.setSubType(model.getReportChartType());

        cwaBar.setOrientation(Orientation.VERTICAL_LITERAL);
        cwaBar.getBlock().setBounds(BoundsImpl.create(0.0D, 0.0D, 570.0D, 225.0D));
        cwaBar.getPlot().getClientArea().getOutline().setVisible(true);
        cwaBar.getPlot().getClientArea().setBackground(ColorDefinitionImpl.create(254, 251, 233));

        cwaBar.getPlot()
                .setBackground(getColorDefinitionByGivenString(model.getReportBackGround()));
        cwaBar.getPlot().getClientArea().setBackground(ColorDefinitionImpl.YELLOW());

        TitleBlock tb = cwaBar.getTitle();

        tb.getLabel().getCaption().setValue(model.getReportChartTitle());
        tb.getLabel().getCaption().setColor(
                                            getColorDefinitionByGivenString(model
                                                    .getReportFontColor()));

        if (!StringUtils.isEmpty(model.getReportOptionalYSeries()))
            cwaBar.getLegend().setItemType(LegendItemType.SERIES_LITERAL);
        else {
            cwaBar.getLegend().setItemType(LegendItemType.CATEGORIES_LITERAL);
        }
        cwaBar.getLegend().setVisible(true);

        Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes()[0];

        xAxisPrimary.getMajorGrid().getLineAttributes().setStyle(LineStyle.DOTTED_LITERAL);
        xAxisPrimary.getMajorGrid().getLineAttributes().setVisible(true);
        xAxisPrimary.getMajorGrid().getLineAttributes().setColor(ColorDefinitionImpl.GREY());
        xAxisPrimary.setType(AxisType.TEXT_LITERAL);
        xAxisPrimary.getOrigin().setType(IntersectionType.MIN_LITERAL);

        Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis(xAxisPrimary);
        yAxisPrimary.getLabel().getCaption().getFont().setRotation(90.0D);

        yAxisPrimary.getMajorGrid().getLineAttributes().setStyle(LineStyle.DOTTED_LITERAL);
        yAxisPrimary.getMajorGrid().getLineAttributes().setVisible(true);
        yAxisPrimary.getMajorGrid().getLineAttributes().setColor(ColorDefinitionImpl.GREY());
        yAxisPrimary.setType(AxisType.LINEAR_LITERAL);

        Series seCategory = SeriesImpl.create();
        Query query = QueryImpl.create("row[\"" + model.getReportXSeries() + "\"]");
        seCategory.getDataDefinition().add(query);

        SeriesDefinition sdX = SeriesDefinitionImpl.create();
        sdX.getSeriesPalette().update(0);
        sdX.getSeries().add(seCategory);
        xAxisPrimary.getSeriesDefinitions().add(sdX);

        BarSeries bs = (BarSeries) BarSeriesImpl.create();
        Query query1 = QueryImpl.create("row[\"" + model.getReportYSeries() + "\"]");
        bs.getDataDefinition().add(query1);

        SeriesDefinition sdY1 = SeriesDefinitionImpl.create();
        sdY1.getSeriesPalette().update(1);
        sdY1.getSeries().add(bs);
        yAxisPrimary.getSeriesDefinitions().add(sdY1);

        bs.getLabel().setVisible(true);
        bs.setLabelPosition(Position.INSIDE_LITERAL);

        if (!StringUtils.isEmpty(model.getReportOptionalYSeries())) {
            SeriesDefinition sdGroup = SeriesDefinitionImpl.create();
            Query q = QueryImpl.create("row[\"" + model.getReportOptionalYSeries() + "\"]");
            sdGroup.setQuery(q);
            sdGroup.getSeriesPalette().update(0);
            yAxisPrimary.getSeriesDefinitions().clear();
            yAxisPrimary.getSeriesDefinitions().add(0, sdGroup);
            sdGroup.getSeries().add(sdY1.getSeries().get(0));
        }

        return cwaBar;
    }

    public Chart create3DBarChart(ReportDef model) {
        ChartWithAxes cwaBar = ChartWithAxesImpl.create();

        cwaBar.setDimension(ChartDimension.THREE_DIMENSIONAL_LITERAL);

        cwaBar.setType("Bar Chart");
        cwaBar.setSubType("Side-by-side");

        cwaBar.setOrientation(Orientation.VERTICAL_LITERAL);
        cwaBar.getBlock().setBounds(BoundsImpl.create(0.0D, 0.0D, 570.0D, 225.0D));
        cwaBar.getPlot().getClientArea().getOutline().setVisible(true);
        cwaBar.getPlot().getClientArea().setBackground(ColorDefinitionImpl.create(254, 251, 233));

        cwaBar.getPlot()
                .setBackground(getColorDefinitionByGivenString(model.getReportBackGround()));
        cwaBar.getPlot().getClientArea().setBackground(ColorDefinitionImpl.YELLOW());

        TitleBlock tb = cwaBar.getTitle();
        tb.getLabel().getCaption().setValue(model.getReportChartTitle());
        tb.getLabel().getCaption().setColor(
                                            getColorDefinitionByGivenString(model
                                                    .getReportFontColor()));

        tb.getLabel().getOutline().setVisible(true);

        if (!StringUtils.isEmpty(model.getReportOptionalYSeries()))
            cwaBar.getLegend().setItemType(LegendItemType.SERIES_LITERAL);
        else {
            cwaBar.getLegend().setItemType(LegendItemType.CATEGORIES_LITERAL);
        }
        cwaBar.getLegend().setVisible(true);

        Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes()[0];
        xAxisPrimary.setCategoryAxis(true);
        xAxisPrimary.getMajorGrid().getLineAttributes().setStyle(LineStyle.DOTTED_LITERAL);
        xAxisPrimary.getMajorGrid().getLineAttributes().setVisible(true);
        xAxisPrimary.getMajorGrid().getLineAttributes().setColor(ColorDefinitionImpl.GREY());
        xAxisPrimary.getOrigin().setType(IntersectionType.VALUE_LITERAL);
        xAxisPrimary.setType(AxisType.TEXT_LITERAL);
        xAxisPrimary.getOrigin().setType(IntersectionType.MIN_LITERAL);
        xAxisPrimary.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);

        Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis(xAxisPrimary);
        yAxisPrimary.getLabel().getCaption().getFont().setRotation(90.0D);

        yAxisPrimary.getMajorGrid().getLineAttributes().setStyle(LineStyle.DOTTED_LITERAL);
        yAxisPrimary.getMajorGrid().getLineAttributes().setVisible(true);
        yAxisPrimary.getMajorGrid().getLineAttributes().setColor(ColorDefinitionImpl.GREY());
        yAxisPrimary.setType(AxisType.LINEAR_LITERAL);
        yAxisPrimary.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);

        Axis zAxis = AxisImpl.create(3);
        zAxis.setType(AxisType.TEXT_LITERAL);
        zAxis.setLabelPosition(Position.BELOW_LITERAL);
        zAxis.setTitlePosition(Position.BELOW_LITERAL);
        zAxis.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
        zAxis.setOrientation(Orientation.HORIZONTAL_LITERAL);
        xAxisPrimary.getAncillaryAxes().add(zAxis);

        Series seCategory = SeriesImpl.create();
        Query query = QueryImpl.create("row[\"" + model.getReportXSeries() + "\"]");
        seCategory.getDataDefinition().add(query);

        SeriesDefinition sdX = SeriesDefinitionImpl.create();
        sdX.getSeriesPalette().update(0);
        sdX.getSeries().add(seCategory);
        xAxisPrimary.getSeriesDefinitions().add(sdX);

        BarSeries bs = (BarSeries) BarSeriesImpl.create();
        Query query1 = QueryImpl.create("row[\"" + model.getReportYSeries() + "\"]");
        bs.getDataDefinition().add(query1);

        SeriesDefinition sdY1 = SeriesDefinitionImpl.create();
        sdY1.getSeriesPalette().update(1);
        sdY1.getSeries().add(bs);
        yAxisPrimary.getSeriesDefinitions().add(sdY1);

        if (!StringUtils.isEmpty(model.getReportOptionalYSeries())) {
            SeriesDefinition sdGroup = SeriesDefinitionImpl.create();
            Query q = QueryImpl.create("row[\"" + model.getReportOptionalYSeries() + "\"]");
            sdGroup.setQuery(q);
            sdGroup.getSeriesPalette().update(0);
            yAxisPrimary.getSeriesDefinitions().clear();
            yAxisPrimary.getSeriesDefinitions().add(0, sdGroup);
            sdGroup.getSeries().add(sdY1.getSeries().get(0));
        }

        cwaBar.setRotation(Rotation3DImpl.create(new Angle3D[] { Angle3DImpl.create(-10.0D, 25.0D,
                                                                                    0.0D) }));

        return cwaBar;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.factory.BarFactory JD-Core Version: 0.5.4
 */