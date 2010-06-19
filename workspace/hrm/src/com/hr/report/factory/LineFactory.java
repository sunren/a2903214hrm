package com.hr.report.factory;

import com.hr.report.domain.ReportDef;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.ChartWithAxes;
import org.eclipse.birt.chart.model.attribute.Angle3D;
import org.eclipse.birt.chart.model.attribute.AxisOrigin;
import org.eclipse.birt.chart.model.attribute.AxisType;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.birt.chart.model.attribute.IntersectionType;
import org.eclipse.birt.chart.model.attribute.LegendItemType;
import org.eclipse.birt.chart.model.attribute.LineAttributes;
import org.eclipse.birt.chart.model.attribute.Marker;
import org.eclipse.birt.chart.model.attribute.MarkerType;
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
import org.eclipse.birt.chart.model.type.LineSeries;
import org.eclipse.birt.chart.model.type.impl.LineSeriesImpl;
import org.eclipse.emf.common.util.EList;

public class LineFactory extends AbstractChartFactory {
    public final Chart createChart(ReportDef model) {
        ChartWithAxes cwaLine = ChartWithAxesImpl.create();

        cwaLine.setDimension(getDimension(model.getReportChartDimension().intValue()));

        cwaLine.setType("Line Chart");
        cwaLine.setSubType(model.getReportChartType());
        cwaLine.setOrientation(Orientation.VERTICAL_LITERAL);
        cwaLine.getBlock().setBounds(BoundsImpl.create(0.0D, 0.0D, 570.0D, 225.0D));
        cwaLine.getPlot().getClientArea().getOutline().setVisible(true);
        cwaLine.getPlot().getClientArea().setBackground(ColorDefinitionImpl.create(254, 251, 233));

        cwaLine.getBlock().setBackground(
                                         getColorDefinitionByGivenString(model
                                                 .getReportBackGround()));
        Plot p = cwaLine.getPlot();
        p.getClientArea().setBackground(ColorDefinitionImpl.YELLOW());

        cwaLine.getTitle().getLabel().getCaption().setValue(model.getReportChartTitle());

        cwaLine.getLegend().setItemType(LegendItemType.CATEGORIES_LITERAL);
        cwaLine.getLegend().setVisible(true);

        Axis xAxisPrimary = cwaLine.getPrimaryBaseAxes()[0];
        xAxisPrimary.setType(AxisType.TEXT_LITERAL);
        xAxisPrimary.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
        xAxisPrimary.getOrigin().setType(IntersectionType.MIN_LITERAL);

        Axis yAxisPrimary = cwaLine.getPrimaryOrthogonalAxis(xAxisPrimary);
        yAxisPrimary.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);

        Series seCategory = SeriesImpl.create();
        Query query = QueryImpl.create("row[\"" + model.getReportXSeries() + "\"]");
        seCategory.getDataDefinition().add(query);

        SeriesDefinition sdX = SeriesDefinitionImpl.create();
        sdX.getSeriesPalette().update(0);
        xAxisPrimary.getSeriesDefinitions().add(sdX);
        sdX.getSeries().add(seCategory);

        LineSeries ls1 = (LineSeries) LineSeriesImpl.create();
        Query query2 = QueryImpl.create("row[\"" + model.getReportYSeries() + "\"]");
        ls1.getDataDefinition().add(query2);
        ls1.getLineAttributes().setColor(ColorDefinitionImpl.CREAM());
        for (int i = 0; i < ls1.getMarkers().size(); ++i) {
            ((Marker) ls1.getMarkers().get(i)).setType(MarkerType.TRIANGLE_LITERAL);
        }

        ls1.getLabel().setVisible(true);

        SeriesDefinition sdY = SeriesDefinitionImpl.create();
        sdY.getSeriesPalette().shift(0);
        yAxisPrimary.getSeriesDefinitions().add(sdY);
        sdY.getSeries().add(ls1);

        return cwaLine;
    }

    public final Chart create3DLineChart(ReportDef model) {
        ChartWithAxes cwa3DLine = ChartWithAxesImpl.create();
        cwa3DLine.setDimension(ChartDimension.THREE_DIMENSIONAL_LITERAL);
        cwa3DLine.setType("Line Chart");
        cwa3DLine.setSubType("Overlay");
        cwa3DLine.setType("Line Chart");
        cwa3DLine.setSubType(model.getReportChartType());
        cwa3DLine.setOrientation(Orientation.VERTICAL_LITERAL);
        cwa3DLine.getBlock().setBounds(BoundsImpl.create(0.0D, 0.0D, 570.0D, 225.0D));
        cwa3DLine.getPlot().getClientArea().getOutline().setVisible(true);
        cwa3DLine.getPlot().getClientArea()
                .setBackground(ColorDefinitionImpl.create(254, 251, 233));

        cwa3DLine.getBlock().setBackground(
                                           getColorDefinitionByGivenString(model
                                                   .getReportBackGround()));
        Plot p = cwa3DLine.getPlot();
        p.getClientArea().setBackground(ColorDefinitionImpl.YELLOW());

        cwa3DLine.getTitle().getLabel().getCaption().setValue(model.getReportChartTitle());

        Axis xAxisPrimary = cwa3DLine.getPrimaryBaseAxes()[0];
        xAxisPrimary.setType(AxisType.TEXT_LITERAL);
        xAxisPrimary.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
        xAxisPrimary.getOrigin().setType(IntersectionType.VALUE_LITERAL);

        Axis yAxisPrimary = cwa3DLine.getPrimaryOrthogonalAxis(xAxisPrimary);
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
        xAxisPrimary.getSeriesDefinitions().add(sdX);
        sdX.getSeries().add(seCategory);

        LineSeries ls1 = (LineSeries) LineSeriesImpl.create();
        Query query2 = QueryImpl.create("row[\"" + model.getReportYSeries() + "\"]");
        ls1.getDataDefinition().add(query2);
        ls1.getLineAttributes().setColor(ColorDefinitionImpl.CREAM());
        for (int i = 0; i < ls1.getMarkers().size(); ++i) {
            ((Marker) ls1.getMarkers().get(i)).setType(MarkerType.TRIANGLE_LITERAL);
        }
        ls1.getLabel().setVisible(true);
        ls1.setPaletteLineColor(true);

        LineSeries ls2 = (LineSeries) LineSeriesImpl.create();
        Query query3 = QueryImpl.create("row[\"" + model.getReportYSeries() + "\"]");
        ls2.getDataDefinition().add(query3);
        for (int i = 0; i < ls2.getMarkers().size(); ++i) {
            ((Marker) ls2.getMarkers().get(i)).setType(MarkerType.TRIANGLE_LITERAL);
        }

        ls2.getLabel().setVisible(true);
        ls2.setPaletteLineColor(true);

        SeriesDefinition sdY = SeriesDefinitionImpl.create();
        sdY.getSeriesPalette().shift(-1);
        yAxisPrimary.getSeriesDefinitions().add(sdY);
        sdY.getSeries().add(ls1);

        SeriesDefinition sdZ = SeriesDefinitionImpl.create();
        zAxis.getSeriesDefinitions().add(sdZ);

        cwa3DLine.setRotation(Rotation3DImpl
                .create(new Angle3D[] { Angle3DImpl.create(-10.0D, 25.0D, 0.0D) }));

        return cwa3DLine;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.factory.LineFactory JD-Core Version: 0.5.4
 */