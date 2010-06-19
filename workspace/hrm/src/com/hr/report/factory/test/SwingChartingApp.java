package com.hr.report.factory.test;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.eclipse.birt.chart.device.IDeviceRenderer;
import org.eclipse.birt.chart.device.IDisplayServer;
import org.eclipse.birt.chart.device.IUpdateNotifier;
import org.eclipse.birt.chart.exception.ChartException;
import org.eclipse.birt.chart.factory.GeneratedChartState;
import org.eclipse.birt.chart.factory.Generator;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.ChartWithAxes;
import org.eclipse.birt.chart.model.attribute.Anchor;
import org.eclipse.birt.chart.model.attribute.AxisOrigin;
import org.eclipse.birt.chart.model.attribute.AxisType;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.attribute.FontDefinition;
import org.eclipse.birt.chart.model.attribute.Insets;
import org.eclipse.birt.chart.model.attribute.IntersectionType;
import org.eclipse.birt.chart.model.attribute.LineAttributes;
import org.eclipse.birt.chart.model.attribute.Palette;
import org.eclipse.birt.chart.model.attribute.Position;
import org.eclipse.birt.chart.model.attribute.Text;
import org.eclipse.birt.chart.model.attribute.TickStyle;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.component.Axis;
import org.eclipse.birt.chart.model.component.Grid;
import org.eclipse.birt.chart.model.component.Label;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.BaseSampleData;
import org.eclipse.birt.chart.model.data.DataFactory;
import org.eclipse.birt.chart.model.data.NumberDataSet;
import org.eclipse.birt.chart.model.data.OrthogonalSampleData;
import org.eclipse.birt.chart.model.data.SampleData;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.TextDataSet;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.impl.ChartWithAxesImpl;
import org.eclipse.birt.chart.model.layout.Block;
import org.eclipse.birt.chart.model.layout.ClientArea;
import org.eclipse.birt.chart.model.layout.Legend;
import org.eclipse.birt.chart.model.layout.Plot;
import org.eclipse.birt.chart.model.layout.TitleBlock;
import org.eclipse.birt.chart.model.type.BarSeries;
import org.eclipse.birt.chart.model.type.impl.BarSeriesImpl;
import org.eclipse.birt.chart.util.PluginSettings;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.emf.common.util.EList;

public final class SwingChartingApp extends JPanel implements IUpdateNotifier, ComponentListener {
    private static final long serialVersionUID = 1L;
    private boolean bNeedsGeneration = true;
    private GeneratedChartState gcs = null;
    private Chart cm = null;
    private IDeviceRenderer idr = null;
    private Map contextMap;

    public static void main(String[] args) throws SemanticException, IOException {
        SwingChartingApp scv = new SwingChartingApp();
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(2);
        jf.addComponentListener(scv);
        Container co = jf.getContentPane();
        co.setLayout(new BorderLayout());
        co.add(scv, "Center");
        Dimension dScreen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dApp = new Dimension(800, 600);
        jf.setSize(dApp);
        jf.setLocation((dScreen.width - dApp.width) / 2, (dScreen.height - dApp.height) / 2);

        jf.setTitle(scv.getClass().getName() + " [device=" + scv.idr.getClass().getName() + "]");

        jf.show();
    }

    SwingChartingApp() throws SemanticException, IOException {
        System.setProperty("STANDALONE", "true");
        System.setProperty("BIRT_HOME",
                           "D:/wxz/birt 安装/birt-runtime-2_2_1/birt-runtime-2_2_1/ReportEngine");
        this.contextMap = new HashMap();
        PluginSettings ps = PluginSettings.instance();
        try {
            this.idr = ps.getDevice("dv.SWING");
        } catch (ChartException ex) {
            ex.printStackTrace();
        }
        this.cm = createBarChart();
    }

    public static final Chart createBarChart() {
        ChartWithAxes cwaBar = ChartWithAxesImpl.create();
        cwaBar.setType("Bar Chart");
        cwaBar.setSubType("Side-by-side");

        cwaBar.getBlock().setBackground(ColorDefinitionImpl.WHITE());
        Plot p = cwaBar.getPlot();
        p.getClientArea().setBackground(ColorDefinitionImpl.create(255, 255, 225));

        p.getOutline().setVisible(true);

        cwaBar.getTitle().getLabel().getCaption().setValue("Bar Chart with Multiple Y Series");

        Legend lg = cwaBar.getLegend();
        lg.getText().getFont().setBold(true);
        lg.getInsets().set(10.0D, 5.0D, 0.0D, 0.0D);
        lg.setAnchor(Anchor.NORTH_LITERAL);

        Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes()[0];
        xAxisPrimary.setType(AxisType.TEXT_LITERAL);
        xAxisPrimary.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
        xAxisPrimary.getOrigin().setType(IntersectionType.MIN_LITERAL);
        xAxisPrimary.setLabelPosition(Position.BELOW_LITERAL);

        Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis(xAxisPrimary);
        yAxisPrimary.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);

        TextDataSet categoryValues = TextDataSetImpl.create(new String[] { "Europe", "Asia",
                "North America" });

        NumberDataSet orthoValues1 = NumberDataSetImpl.create(new double[] { 26.170000000000002D,
                34.210000000000001D, 21.5D });

        NumberDataSet orthoValues2 = NumberDataSetImpl
                .create(new double[] { 4.81D, 3.55D, -5.26D });

        SampleData sd = DataFactory.eINSTANCE.createSampleData();
        BaseSampleData sdBase = DataFactory.eINSTANCE.createBaseSampleData();
        sdBase.setDataSetRepresentation("");
        sd.getBaseSampleData().add(sdBase);

        OrthogonalSampleData sdOrthogonal1 = DataFactory.eINSTANCE.createOrthogonalSampleData();
        sdOrthogonal1.setDataSetRepresentation("");
        sdOrthogonal1.setSeriesDefinitionIndex(0);
        sd.getOrthogonalSampleData().add(sdOrthogonal1);

        OrthogonalSampleData sdOrthogonal2 = DataFactory.eINSTANCE.createOrthogonalSampleData();
        sdOrthogonal2.setDataSetRepresentation("");
        sdOrthogonal2.setSeriesDefinitionIndex(1);
        sd.getOrthogonalSampleData().add(sdOrthogonal2);

        cwaBar.setSampleData(sd);

        Series seCategory = SeriesImpl.create();
        seCategory.setDataSet(categoryValues);

        SeriesDefinition sdX = SeriesDefinitionImpl.create();
        xAxisPrimary.getSeriesDefinitions().add(sdX);
        sdX.getSeries().add(seCategory);

        BarSeries bs = (BarSeries) BarSeriesImpl.create();
        bs.setSeriesIdentifier("Sales");
        bs.setDataSet(orthoValues1);
        bs.getLabel().setVisible(true);
        bs.setLabelPosition(Position.INSIDE_LITERAL);

        SeriesDefinition sdY1 = SeriesDefinitionImpl.create();
        sdY1.getSeriesPalette().shift(-2);
        yAxisPrimary.getSeriesDefinitions().add(sdY1);
        sdY1.getSeries().add(bs);

        return cwaBar;
    }

    public void regenerateChart() {
        this.bNeedsGeneration = true;
        repaint();
    }

    public void repaintChart() {
        repaint();
    }

    public Object peerInstance() {
        return this;
    }

    public Chart getDesignTimeModel() {
        return this.cm;
    }

    public Object getContext(Object key) {
        return this.contextMap.get(key);
    }

    public Object putContext(Object key, Object value) {
        return this.contextMap.put(key, value);
    }

    public Object removeContext(Object key) {
        return this.contextMap.remove(key);
    }

    public Chart getRunTimeModel() {
        return this.gcs.getChartModel();
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        this.idr.setProperty("device.output.context", g2d);
        this.idr.setProperty("device.component", this);
        Dimension d = getSize();
        Bounds bo = BoundsImpl.create(0.0D, 0.0D, d.width, d.height);
        bo.scale(72.0D / this.idr.getDisplayServer().getDpiResolution());
        Generator gr = Generator.instance();

        if (this.bNeedsGeneration) {
            this.bNeedsGeneration = false;
            try {
                this.gcs = gr.build(this.idr.getDisplayServer(), this.cm, bo, null, null, null);
            } catch (ChartException ex) {
                System.out.println(ex);
            }
        }
        try {
            gr.render(this.idr, this.gcs);
        } catch (ChartException ex) {
            System.out.println(ex);
        }
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        this.bNeedsGeneration = true;
    }

    public void componentShown(ComponentEvent e) {
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.factory.test.SwingChartingApp JD-Core Version: 0.5.4
 */