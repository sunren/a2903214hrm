package com.hr.report.factory.test;

import com.ibm.icu.util.ULocale;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.eclipse.birt.chart.model.ChartWithAxes;
import org.eclipse.birt.chart.model.attribute.AxisType;
import org.eclipse.birt.chart.model.attribute.DataPoint;
import org.eclipse.birt.chart.model.attribute.DataPointComponent;
import org.eclipse.birt.chart.model.attribute.DataPointComponentType;
import org.eclipse.birt.chart.model.attribute.LineAttributes;
import org.eclipse.birt.chart.model.attribute.LineStyle;
import org.eclipse.birt.chart.model.attribute.Orientation;
import org.eclipse.birt.chart.model.attribute.Palette;
import org.eclipse.birt.chart.model.attribute.Text;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.attribute.impl.DataPointComponentImpl;
import org.eclipse.birt.chart.model.attribute.impl.JavaDateFormatSpecifierImpl;
import org.eclipse.birt.chart.model.attribute.impl.JavaNumberFormatSpecifierImpl;
import org.eclipse.birt.chart.model.component.Axis;
import org.eclipse.birt.chart.model.component.Grid;
import org.eclipse.birt.chart.model.component.Label;
import org.eclipse.birt.chart.model.component.Scale;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.BaseSampleData;
import org.eclipse.birt.chart.model.data.DataFactory;
import org.eclipse.birt.chart.model.data.OrthogonalSampleData;
import org.eclipse.birt.chart.model.data.Query;
import org.eclipse.birt.chart.model.data.SampleData;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.impl.NumberDataElementImpl;
import org.eclipse.birt.chart.model.data.impl.QueryImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.impl.ChartWithAxesImpl;
import org.eclipse.birt.chart.model.layout.Block;
import org.eclipse.birt.chart.model.layout.ClientArea;
import org.eclipse.birt.chart.model.layout.Legend;
import org.eclipse.birt.chart.model.layout.Plot;
import org.eclipse.birt.chart.model.layout.TitleBlock;
import org.eclipse.birt.chart.model.type.BarSeries;
import org.eclipse.birt.chart.model.type.StockSeries;
import org.eclipse.birt.chart.model.type.impl.BarSeriesImpl;
import org.eclipse.birt.chart.model.type.impl.StockSeriesImpl;
import org.eclipse.birt.report.model.api.CellHandle;
import org.eclipse.birt.report.model.api.DesignConfig;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.DesignEngine;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.GridHandle;
import org.eclipse.birt.report.model.api.LabelHandle;
import org.eclipse.birt.report.model.api.PropertyHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.RowHandle;
import org.eclipse.birt.report.model.api.ScriptDataSetHandle;
import org.eclipse.birt.report.model.api.ScriptDataSourceHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.SlotHandle;
import org.eclipse.birt.report.model.api.StructureFactory;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.command.ContentException;
import org.eclipse.birt.report.model.api.command.NameException;
import org.eclipse.birt.report.model.api.elements.structures.ComputedColumn;
import org.eclipse.birt.report.model.api.extension.ExtendedElementException;
import org.eclipse.birt.report.model.api.extension.IReportItem;
import org.eclipse.birt.report.model.api.metadata.IMetaDataDictionary;
import org.eclipse.emf.common.util.EList;

public class StockReport {
    ReportDesignHandle reportDesignHandle;
    ElementFactory elementFactory;
    StructureFactory structFactory;
    IMetaDataDictionary dict;
    ComputedColumn cs1;
    ComputedColumn cs2;
    ComputedColumn cs3;
    ComputedColumn cs4;
    ComputedColumn cs5;
    ComputedColumn cs6;

    public StockReport() {
        this.reportDesignHandle = null;

        this.elementFactory = null;

        this.structFactory = null;

        this.dict = null;

        this.cs6 = null;
    }

    public static void main(String[] args) throws SemanticException, IOException {
        new StockReport().createReport();
    }

    void createReport() throws SemanticException, IOException {
        DesignConfig config = new DesignConfig();
        config.setProperty("BIRT_HOME",
                           "D:/wxz/birt 安装/birt-runtime-2_2_1/birt-runtime-2_2_1/ReportEngine");

        SessionHandle session = new DesignEngine(config).newSessionHandle((ULocale) null);

        this.reportDesignHandle = session.createDesign();

        this.elementFactory = this.reportDesignHandle.getElementFactory();

        this.structFactory = new StructureFactory();

        this.dict = new DesignEngine(config).getMetaData();

        createMasterPages();
        createDataSources();
        createDataSets();
        createBody();

        String outputPath = "output";
        File outputFolder = new File(outputPath);
        if ((!outputFolder.exists()) && (!outputFolder.mkdir())) {
            throw new IOException("Can not create the output folder");
        }
        this.reportDesignHandle.saveAs(outputPath + "/" + "StockAnalysis.rptdesign");
    }

    private void createDataSources() throws SemanticException {
        ScriptDataSourceHandle dataSourceHandle = this.elementFactory
                .newScriptDataSource("Data Source");

        this.reportDesignHandle.getDataSources().add(dataSourceHandle);
    }

    private void createDataSets() throws SemanticException {
        ScriptDataSetHandle dataSetHandle = this.elementFactory.newScriptDataSet("Data Set");

        dataSetHandle.setDataSource("Data Source");

        dataSetHandle
                .setOpen("i=0;sourcedata = new Array( new Array(6), new Array(6), new Array(6), new Array(6),new Array(6), new Array(6), new Array(6), new Array(6));sourcedata[0][0] = \"3/1/2005\"; sourcedata[0][1] = 2.77;sourcedata[0][2] = 2.73;sourcedata[0][3] = 2.69; sourcedata[0][4] = 2.71;sourcedata[0][5] = 341900;sourcedata[1][0] = \"3/2/2005\"; sourcedata[1][1] = 2.8;sourcedata[1][2] = 2.64;sourcedata[1][3] = 2.6; sourcedata[1][4] = 2.71;sourcedata[1][5] = 249900;sourcedata[2][0] = \"3/3/2005\"; sourcedata[2][1] = 2.6;sourcedata[2][2] = 2.28;sourcedata[2][3] = 2.13; sourcedata[2][4] = 2.59;sourcedata[2][5] = 394800;sourcedata[3][0] = \"3/4/2005\"; sourcedata[3][1] = 2.87;sourcedata[3][2] = 2.87;sourcedata[3][3] = 2.03; sourcedata[3][4] = 2.21;sourcedata[3][5] = 358200;sourcedata[4][0] = \"3/5/2005\"; sourcedata[4][1] = 2.48;sourcedata[4][2] = 2.26;sourcedata[4][3] = 2.16; sourcedata[4][4] = 2.39;sourcedata[4][5] = 339000;sourcedata[5][0] = \"3/6/2005\"; sourcedata[5][1] = 2.98;sourcedata[5][2] = 2.86;sourcedata[5][3] = 2.04; sourcedata[5][4] = 2.19;sourcedata[5][5] = 221000;sourcedata[6][0] = \"3/7/2005\"; sourcedata[6][1] = 2.87;sourcedata[6][2] = 2.17;sourcedata[6][3] = 2.14; sourcedata[6][4] = 2.62;sourcedata[6][5] = 183600;sourcedata[7][0] = \"3/8/2005\"; sourcedata[7][1] = 2.8;sourcedata[7][2] = 2.78;sourcedata[7][3] = 2.65; sourcedata[7][4] = 2.66;sourcedata[7][5] = 194800;");

        dataSetHandle
                .setFetch("if ( i < 8 ){row[\"Date\"] = sourcedata[i][0];row[\"High\"] = sourcedata[i][1];row[\"Close\"] = sourcedata[i][2];row[\"Low\"] = sourcedata[i][3];row[\"Open\"] = sourcedata[i][4];row[\"Volume\"] = sourcedata[i][5];i++;return true;}else return false;");

        this.cs1 = StructureFactory.createComputedColumn();
        this.cs1.setName("Date");
        this.cs1.setExpression("row[\"Date\"]");
        this.cs1.setDataType("date-time");

        this.cs2 = StructureFactory.createComputedColumn();
        this.cs2.setName("High");
        this.cs2.setExpression("row[\"High\"]");
        this.cs2.setDataType("float");

        this.cs3 = StructureFactory.createComputedColumn();
        this.cs3.setName("Close");
        this.cs3.setExpression("row[\"Close\"]");
        this.cs3.setDataType("float");

        this.cs4 = StructureFactory.createComputedColumn();
        this.cs4.setName("Low");
        this.cs4.setExpression("row[\"Low\"]");
        this.cs4.setDataType("float");

        this.cs5 = StructureFactory.createComputedColumn();
        this.cs5.setName("Open");
        this.cs5.setExpression("row[\"Open\"]");
        this.cs5.setDataType("float");

        this.cs6 = StructureFactory.createComputedColumn();
        this.cs6.setName("Volume");
        this.cs6.setExpression("row[\"Volume\"]");
        this.cs6.setDataType("integer");

        PropertyHandle computedSet = dataSetHandle.getPropertyHandle("computedColumns");
        computedSet.addItem(this.cs1);
        computedSet.addItem(this.cs2);
        computedSet.addItem(this.cs3);
        computedSet.addItem(this.cs4);
        computedSet.addItem(this.cs5);
        computedSet.addItem(this.cs6);

        this.reportDesignHandle.getDataSets().add(dataSetHandle);
    }

    private void createMasterPages() throws ContentException, NameException {
        DesignElementHandle simpleMasterPage = this.elementFactory
                .newSimpleMasterPage("Master Page");
        try {
            simpleMasterPage.setProperty("leftMargin", "0.2in");

            simpleMasterPage.setProperty("rightMargin", "0.2in");
        } catch (SemanticException e) {
            e.printStackTrace();
        }
        this.reportDesignHandle.getMasterPages().add(simpleMasterPage);
    }

    private void createBody() throws SemanticException {
        GridHandle mainGrid = this.elementFactory.newGridItem("main", 1, 3);
        mainGrid.setWidth("100%");
        this.reportDesignHandle.getBody().add(mainGrid);

        RowHandle row1 = (RowHandle) mainGrid.getRows().get(0);
        row1.setProperty("backgroundColor", "#FEFBE9");

        CellHandle row1Cell = (CellHandle) row1.getCells().get(0);

        LabelHandle label = this.elementFactory.newLabel(null);
        label.setText("Corporation Stock");
        label.setProperty("fontSize", "x-large");

        label.setProperty("fontFamily", "Arial Black");
        label.setProperty("color", "#6E6E6E");
        label.setProperty("paddingBottom", "0.5in");
        label.setProperty("textAlign", "center");

        row1Cell.getContent().add(label);

        RowHandle row2 = (RowHandle) mainGrid.getRows().get(1);

        CellHandle row2Cell1 = (CellHandle) row2.getCells().get(0);
        row2Cell1.getContent().add(createStockChart());

        RowHandle row3 = (RowHandle) mainGrid.getRows().get(2);

        CellHandle row3Cell1 = (CellHandle) row3.getCells().get(0);
        row3Cell1.getContent().add(createVolumeChart());
    }

    private ExtendedItemHandle createStockChart() {
        ExtendedItemHandle eih = this.elementFactory.newExtendedItem(null, "Chart");
        try {
            eih.setHeight("175pt");
            eih.setWidth("450pt");
            eih.setProperty("dataSet", "Data Set");
            PropertyHandle computedSet = eih.getColumnBindings();
            this.cs1.setExpression("dataSetRow[\"Date\"]");
            computedSet.addItem(this.cs1);
            this.cs2.setExpression("dataSetRow[\"High\"]");
            computedSet.addItem(this.cs2);
            this.cs3.setExpression("dataSetRow[\"Close\"]");
            computedSet.addItem(this.cs3);
            this.cs4.setExpression("dataSetRow[\"Low\"]");
            computedSet.addItem(this.cs4);
            this.cs5.setExpression("dataSetRow[\"Open\"]");
            computedSet.addItem(this.cs5);
            this.cs6.setExpression("dataSetRow[\"Volume\"]");
            computedSet.addItem(this.cs6);
        } catch (SemanticException e) {
            e.printStackTrace();
        }

        ChartWithAxes cwaStock = ChartWithAxesImpl.create();
        cwaStock.setType("Stock Chart");
        cwaStock.setSubType("Standard Stock Chart");
        cwaStock.getTitle().setVisible(false);
        cwaStock.getLegend().setVisible(false);
        cwaStock.setOrientation(Orientation.VERTICAL_LITERAL);
        cwaStock.getBlock().setBounds(BoundsImpl.create(0.0D, 0.0D, 450.0D, 175.0D));
        cwaStock.getPlot().getClientArea().getOutline().setVisible(true);
        cwaStock.getPlot().getClientArea().setBackground(ColorDefinitionImpl.create(254, 251, 233));

        Axis xAxisPrimary = cwaStock.getPrimaryBaseAxes()[0];
        xAxisPrimary.setCategoryAxis(true);
        xAxisPrimary.getTitle().getCaption().setValue("Trading Date");
        xAxisPrimary.getMajorGrid().getLineAttributes().setStyle(LineStyle.DOTTED_LITERAL);

        xAxisPrimary.getMajorGrid().getLineAttributes().setVisible(true);
        xAxisPrimary.getMajorGrid().getLineAttributes().setColor(ColorDefinitionImpl.GREY());

        xAxisPrimary.setType(AxisType.DATE_TIME_LITERAL);
        xAxisPrimary.setFormatSpecifier(JavaDateFormatSpecifierImpl.create("MM/dd/yyyy"));

        Axis yAxisPrimary = cwaStock.getPrimaryOrthogonalAxis(xAxisPrimary);
        yAxisPrimary.getTitle().getCaption().setValue("Price");
        yAxisPrimary.getMajorGrid().getLineAttributes().setStyle(LineStyle.DOTTED_LITERAL);

        yAxisPrimary.getMajorGrid().getLineAttributes().setVisible(true);
        yAxisPrimary.getMajorGrid().getLineAttributes().setColor(ColorDefinitionImpl.GREY());

        yAxisPrimary.getScale().setMin(NumberDataElementImpl.create(2.0D));
        yAxisPrimary.getScale().setMax(NumberDataElementImpl.create(3.0D));
        yAxisPrimary.getScale().setStep(0.2D);

        SampleData sd = DataFactory.eINSTANCE.createSampleData();
        BaseSampleData sdBase = DataFactory.eINSTANCE.createBaseSampleData();
        sdBase.setDataSetRepresentation("01/25/2005");
        sd.getBaseSampleData().add(sdBase);

        OrthogonalSampleData sdOrthogonal = DataFactory.eINSTANCE.createOrthogonalSampleData();

        sdOrthogonal.setDataSetRepresentation("H5.3 L1.3 O4.5 C3.4");
        sdOrthogonal.setSeriesDefinitionIndex(0);
        sd.getOrthogonalSampleData().add(sdOrthogonal);

        cwaStock.setSampleData(sd);

        Series seCategory = SeriesImpl.create();
        Query query = QueryImpl.create("row[\"Date\"]");
        seCategory.getDataDefinition().add(query);

        SeriesDefinition sdX = SeriesDefinitionImpl.create();
        sdX.getSeries().add(seCategory);
        xAxisPrimary.getSeriesDefinitions().add(sdX);

        StockSeries ss = (StockSeries) StockSeriesImpl.create();
        ss.setSeriesIdentifier("Stock Price");
        Query query1 = QueryImpl.create("row[\"High\"]");
        Query query2 = QueryImpl.create("row[\"Low\"]");
        Query query3 = QueryImpl.create("row[\"Open\"]");
        Query query4 = QueryImpl.create("row[\"Close\"]");
        ArrayList list = new ArrayList();
        list.add(query1);
        list.add(query2);
        list.add(query3);
        list.add(query4);
        ss.getDataDefinition().addAll(list);

        SeriesDefinition sdY = SeriesDefinitionImpl.create();
        sdY.getSeriesPalette().update(ColorDefinitionImpl.create(168, 225, 253));

        yAxisPrimary.getSeriesDefinitions().add(sdY);
        sdY.getSeries().add(ss);
        try {
            eih.getReportItem().setProperty("chart.instance", cwaStock);
        } catch (ExtendedElementException e) {
            e.printStackTrace();
        }

        return eih;
    }

    private ExtendedItemHandle createVolumeChart() {
        ExtendedItemHandle eih = this.elementFactory.newExtendedItem(null, "Chart");
        try {
            eih.setHeight("175pt");
            eih.setWidth("450pt");
            eih.setProperty("dataSet", "Data Set");
            PropertyHandle computedSet = eih.getColumnBindings();
            this.cs1.setExpression("dataSetRow[\"Date\"]");
            computedSet.addItem(this.cs1);
            this.cs2.setExpression("dataSetRow[\"High\"]");
            computedSet.addItem(this.cs2);
            this.cs3.setExpression("dataSetRow[\"Close\"]");
            computedSet.addItem(this.cs3);
            this.cs4.setExpression("dataSetRow[\"Low\"]");
            computedSet.addItem(this.cs4);
            this.cs5.setExpression("dataSetRow[\"Open\"]");
            computedSet.addItem(this.cs5);
            this.cs6.setExpression("dataSetRow[\"Volume\"]");
            computedSet.addItem(this.cs6);
        } catch (SemanticException e) {
            e.printStackTrace();
        }

        ChartWithAxes cwaBar = ChartWithAxesImpl.create();
        cwaBar.setType("Bar Chart");
        cwaBar.setSubType("Side-by-side");
        cwaBar.getTitle().setVisible(false);
        cwaBar.getLegend().setVisible(false);
        cwaBar.setOrientation(Orientation.VERTICAL_LITERAL);
        cwaBar.getBlock().setBounds(BoundsImpl.create(0.0D, 0.0D, 450.0D, 175.0D));
        cwaBar.getPlot().getClientArea().getOutline().setVisible(true);
        cwaBar.getPlot().getClientArea().setBackground(ColorDefinitionImpl.create(254, 251, 233));

        Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes()[0];
        xAxisPrimary.setCategoryAxis(true);
        xAxisPrimary.getTitle().getCaption().setValue("Trading Date");
        xAxisPrimary.getMajorGrid().getLineAttributes().setStyle(LineStyle.DOTTED_LITERAL);

        xAxisPrimary.getMajorGrid().getLineAttributes().setVisible(true);
        xAxisPrimary.getMajorGrid().getLineAttributes().setColor(ColorDefinitionImpl.GREY());

        xAxisPrimary.setType(AxisType.DATE_TIME_LITERAL);
        xAxisPrimary.setFormatSpecifier(JavaDateFormatSpecifierImpl.create("MM/dd/yyyy"));

        Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis(xAxisPrimary);
        yAxisPrimary.getTitle().getCaption().setValue("Volume");
        yAxisPrimary.getMajorGrid().getLineAttributes().setStyle(LineStyle.DOTTED_LITERAL);

        yAxisPrimary.getMajorGrid().getLineAttributes().setVisible(true);
        yAxisPrimary.getMajorGrid().getLineAttributes().setColor(ColorDefinitionImpl.GREY());

        SampleData sd = DataFactory.eINSTANCE.createSampleData();
        BaseSampleData sdBase = DataFactory.eINSTANCE.createBaseSampleData();
        sdBase.setDataSetRepresentation("01/25/2005");
        sd.getBaseSampleData().add(sdBase);

        OrthogonalSampleData sdOrthogonal = DataFactory.eINSTANCE.createOrthogonalSampleData();

        sdOrthogonal.setDataSetRepresentation("5");
        sdOrthogonal.setSeriesDefinitionIndex(0);
        sd.getOrthogonalSampleData().add(sdOrthogonal);

        cwaBar.setSampleData(sd);

        Series seCategory = SeriesImpl.create();
        Query query = QueryImpl.create("row[\"Date\"]");
        seCategory.getDataDefinition().add(query);

        SeriesDefinition sdX = SeriesDefinitionImpl.create();
        sdX.getSeries().add(seCategory);
        xAxisPrimary.getSeriesDefinitions().add(sdX);

        BarSeries bs = (BarSeries) BarSeriesImpl.create();
        Query query2 = QueryImpl.create("row[\"Volume\"]");
        bs.getDataDefinition().add(query2);

        SeriesDefinition sdY = SeriesDefinitionImpl.create();
        sdY.getSeriesPalette().update(ColorDefinitionImpl.create(168, 225, 253));

        sdY.getSeries().add(bs);
        yAxisPrimary.getSeriesDefinitions().add(sdY);

        DataPointComponent dpc = DataPointComponentImpl
                .create(DataPointComponentType.ORTHOGONAL_VALUE_LITERAL,
                        JavaNumberFormatSpecifierImpl.create("###,###"));

        bs.getDataPoint().getComponents().clear();
        bs.getDataPoint().getComponents().add(dpc);
        bs.getLabel().setVisible(true);
        try {
            eih.getReportItem().setProperty("chart.instance", cwaBar);
        } catch (ExtendedElementException e) {
            e.printStackTrace();
        }

        return eih;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.factory.test.StockReport JD-Core Version: 0.5.4
 */