package com.hr.report.template;

import com.hr.report.bo.ReportParametersHandler;
import com.hr.report.domain.ReportDef;
import com.hr.report.engine.BirtEngineFactory;
import com.hr.report.util.BirtReportFilelocator;
import com.hr.util.PropertiesFileConfigManager;
import com.hr.util.SysConfigManager;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.report.model.api.CellHandle;
import org.eclipse.birt.report.model.api.DataItemHandle;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.GridHandle;
import org.eclipse.birt.report.model.api.IDesignEngine;
import org.eclipse.birt.report.model.api.LabelHandle;
import org.eclipse.birt.report.model.api.OdaDataSetHandle;
import org.eclipse.birt.report.model.api.OdaDataSourceHandle;
import org.eclipse.birt.report.model.api.PropertyHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.RowHandle;
import org.eclipse.birt.report.model.api.ScalarParameterHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.SlotHandle;
import org.eclipse.birt.report.model.api.StructureFactory;
import org.eclipse.birt.report.model.api.TableHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.command.ContentException;
import org.eclipse.birt.report.model.api.command.NameException;
import org.eclipse.birt.report.model.api.elements.structures.ComputedColumn;
import org.eclipse.birt.report.model.api.elements.structures.DataSetParameter;
import org.eclipse.birt.report.model.api.elements.structures.HighlightRule;
import org.eclipse.birt.report.model.api.elements.structures.ParamBinding;
import org.eclipse.birt.report.model.api.extension.ExtendedElementException;
import org.eclipse.birt.report.model.api.extension.IReportItem;

public abstract class AbstractTemplate {
    protected ReportDesignHandle reportDesignHandle = null;

    protected ElementFactory elementFactory = null;

    protected StructureFactory structFactory = null;
    protected OdaDataSetHandle dsHandle;
    protected ServletContext context;
    protected ReportDef model;
    private static final Map<String, String> JDBC_PROPERTIES = new HashMap<String, String>();

    public AbstractTemplate(ServletContext context, ReportDef model) {
        this.context = context;
        this.model = model;
    }

    public void buildReport(String fileName, String reportId) throws IOException, SemanticException {
        config();

        createMasterPages();

        createDataSources();

        createDataSets();

        createBody();

        setScalarParameters(reportId);

        saveAsRptdesignFile(fileName);

        shutdown();
    }

    protected void createMasterPages() {
        DesignElementHandle simpleMasterPage = this.elementFactory
                .newSimpleMasterPage("Master Page");
        try {
            simpleMasterPage.setProperty("leftMargin", "0.2in");

            simpleMasterPage.setProperty("rightMargin", "0.2in");

            this.reportDesignHandle.getMasterPages().add(simpleMasterPage);
        } catch (SemanticException e) {
            e.printStackTrace();
        }
    }

    protected void createDataSources() throws SemanticException {
        OdaDataSourceHandle dsHandle = this.elementFactory
                .newOdaDataSource("Data Source", "org.eclipse.birt.report.data.oda.jdbc");

        dsHandle.setProperties(JDBC_PROPERTIES);
        this.reportDesignHandle.getDataSources().add(dsHandle);
    }

    protected void createDataSets() throws SemanticException {
        this.dsHandle = this.elementFactory
                .newOdaDataSet("Data Set",
                               "org.eclipse.birt.report.data.oda.jdbc.JdbcSelectDataSet");

        this.dsHandle.setDataSource("Data Source");
        ParamBinding paramBinding = new ParamBinding();

        String sql = this.model.getSql();
        this.dsHandle.setQueryText(sql);

        DataSetParameter param = StructureFactory.createDataSetParameter();
        param.setName("empName");
        param.setPosition(Integer.valueOf(1));
        param.setIsOutput(false);
        this.reportDesignHandle.getDataSets().add(this.dsHandle);
    }

    protected void setScalarParameters(String reportId) {
        try {
            List<ScalarParameterHandle> parameters = ReportParametersHandler.createReportParams(this.elementFactory,
                                                                         this.dsHandle, reportId);
            for (ScalarParameterHandle param : parameters)
                this.reportDesignHandle.getParameters().add(param);
        } catch (ContentException e) {
            e.printStackTrace();
        } catch (NameException e) {
            e.printStackTrace();
        } catch (SemanticException e) {
            e.printStackTrace();
        }
    }

    protected void createBody() throws SemanticException {
        GridHandle mainGrid = this.elementFactory.newGridItem("main", 1, 2);

        mainGrid.setWidth("100%");
        this.reportDesignHandle.getBody().add(mainGrid);

        if (displayChart()) {
            RowHandle row = (RowHandle) mainGrid.getRows().get(0);
            row.setProperty("backgroundColor", "#F9F9F9");
            CellHandle cell = (CellHandle) row.getCells().get(0);
            cell.getContent().add(createChart());
        }

        if (displayTable()) {
            RowHandle row1 = (RowHandle) mainGrid.getRows().get(1);
            row1.setProperty("backgroundColor", "#F9F9F9");
            CellHandle cell1 = (CellHandle) row1.getCells().get(0);
            cell1.getContent().add(createTable());
        }
    }

    private ExtendedItemHandle createChart() {
        ExtendedItemHandle eih = this.elementFactory.newExtendedItem(null, "Chart");
        try {
            eih.setHeight("225pt");
            eih.setWidth("570pt");
            eih.setProperty("dataSet", "Data Set");
        } catch (SemanticException e) {
            e.printStackTrace();
        }

        try {
            eih.getReportItem().setProperty("chart.instance", createChartInstance());
        } catch (ExtendedElementException e) {
            e.printStackTrace();
        }
        return eih;
    }

    protected abstract Chart createChartInstance();

    protected boolean displayChart() {
        return this.model.getReportDisplayMode().intValue() != 0;
    }

    protected boolean displayTable() {
        return this.model.getReportDisplayMode().intValue() != 1;
    }

    protected TableHandle createTable() {
        TableHandle table = null;
        try {
            String[] cols = getOutputColumns();
            table = this.elementFactory.newTableItem("table", cols.length);
            table.setWidth("100%");
            table.setDataSet(this.reportDesignHandle.findDataSet("Data Set"));

            PropertyHandle computedSet = table.getColumnBindings();
            ComputedColumn cs1 = null;

            for (int i = 0; i < cols.length; ++i) {
                cs1 = StructureFactory.createComputedColumn();
                cs1.setName(cols[i]);
                cs1.setExpression("dataSetRow[\"" + cols[i] + "\"]");
                computedSet.addItem(cs1);
            }

            RowHandle tableheader = (RowHandle) table.getHeader().get(0);
            tableheader.setProperty("backgroundColor", "#5C6C7B");
            tableheader.setProperty("fontFamily", "sans-serif");
            tableheader.setProperty("fontWeight", "bold");
            tableheader.setProperty("color", "#DEDCD3");
            tableheader.setProperty("fontSize", "small");

            String[] header = getOutputDisplayColumns();
            for (int i = 0; i < header.length; ++i) {
                LabelHandle label1 = this.elementFactory.newLabel(header[i]);
                label1.setText(header[i]);
                CellHandle cell = (CellHandle) tableheader.getCells().get(i);
                cell.getContent().add(label1);
            }

            RowHandle tabledetail = (RowHandle) table.getDetail().get(0);
            for (int i = 0; i < cols.length; ++i) {
                CellHandle cell = (CellHandle) tabledetail.getCells().get(i);

                DataItemHandle data = this.elementFactory.newDataItem("data_" + cols[i]);

                data.setResultSetColumn(cols[i]);
                cell.getContent().add(data);
            }

            tabledetail.setProperty("backgroundColor", "#DEDCD3");
            tabledetail.setProperty("fontFamily", "sans-serif");
            tabledetail.setProperty("fontSize", "x-small");
            tabledetail.setProperty("fontWeight", "bold");
            tabledetail.setProperty("color", "#1C515A");
            tabledetail.setProperty("borderBottomColor", "#859CA1");
            tabledetail.setProperty("borderBottomStyle", "solid");
            tabledetail.setProperty("borderBottomWidth", "thin");
            tabledetail.setProperty("borderLeftColor", "#859CA1");
            tabledetail.setProperty("borderLeftStyle", "solid");
            tabledetail.setProperty("borderLeftWidth", "thin");
            tabledetail.setProperty("borderRightColor", "#859CA1");
            tabledetail.setProperty("borderRightStyle", "solid");
            tabledetail.setProperty("borderRightWidth", "thin");
            tabledetail.setProperty("borderTopColor", "#859CA1");
            tabledetail.setProperty("borderTopStyle", "solid");
            tabledetail.setProperty("borderTopWidth", "thin");

            PropertyHandle highHandle = tabledetail.getPropertyHandle("highlightRules");
            highHandle.addItem(createHighlightRule());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    protected HighlightRule createHighlightRule() throws SemanticException {
        HighlightRule highLight = new HighlightRule();

        highLight.setOperator("eq");
        highLight.setTestExpression("row.__rownum%2");
        highLight.setValue1("0");
        highLight.setProperty("backgroundColor", "#C0C0C0");

        highLight.setProperty("color", "#46523D");
        highLight.setProperty("fontWeight", "bold");

        return highLight;
    }

    private void saveAsRptdesignFile(String fileName) throws IOException {
        String fullName = BirtReportFilelocator
                .getBirtReportFileAbsoluteLocation(this.context, Integer.valueOf(0))
                + fileName;

        this.reportDesignHandle.saveAs(fullName);
    }

    private void config() {
        SessionHandle session = BirtEngineFactory.getBirtDesignEngine(this.context)
                .newSessionHandle(ULocale.getDefault());

        this.reportDesignHandle = session.createDesign();

        this.elementFactory = this.reportDesignHandle.getElementFactory();
        this.structFactory = new StructureFactory();
    }

    private void shutdown() {
        if (this.reportDesignHandle != null)
            this.reportDesignHandle.close();
    }

    private String[] getOutputColumns() {
        String sql = this.model.getSql();
        int beginIndex = sql.indexOf("select") + 6;
        int endIndex = sql.indexOf("from");
        String columns = sql.substring(beginIndex, endIndex);
        columns = columns.replaceAll(" ", "");
        return columns.split(",");
    }

    private String[] getOutputDisplayColumns() {
        return ReportParametersHandler.getOutputColumns();
    }

    static {
        SysConfigManager fileConfigManager = PropertiesFileConfigManager.getInstance();
        JDBC_PROPERTIES
                .put("odaDriverClass", fileConfigManager.getProperty("jdbc.driverClassName"));
        JDBC_PROPERTIES.put("odaURL", fileConfigManager.getProperty("jdbc.url"));
        JDBC_PROPERTIES.put("odaUser", fileConfigManager.getProperty("jdbc.username"));
        JDBC_PROPERTIES.put("odaPassword", fileConfigManager.getProperty("jdbc.password"));
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.template.AbstractTemplate JD-Core Version: 0.5.4
 */