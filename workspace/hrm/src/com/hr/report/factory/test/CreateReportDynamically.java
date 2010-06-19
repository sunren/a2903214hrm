package com.hr.report.factory.test;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.eclipse.birt.report.model.api.CellHandle;
import org.eclipse.birt.report.model.api.DataItemHandle;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.DesignEngine;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.LabelHandle;
import org.eclipse.birt.report.model.api.PropertyHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.RowHandle;
import org.eclipse.birt.report.model.api.ScriptDataSetHandle;
import org.eclipse.birt.report.model.api.ScriptDataSourceHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.SlotHandle;
import org.eclipse.birt.report.model.api.StructureFactory;
import org.eclipse.birt.report.model.api.TableHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.elements.structures.ColumnHint;
import org.eclipse.birt.report.model.api.elements.structures.ResultSetColumn;

public class CreateReportDynamically {
    public String body;
    private TableHandle table = null;

    private RowHandle headerRow = null;

    private RowHandle detailRow = null;
    private int cellCounter;

    public void buildReport(Map data) throws IOException, SemanticException {
        SessionHandle session = DesignEngine.newSession(null);

        ReportDesignHandle design = session.createDesign();

        ElementFactory elementFactory = design.getElementFactory();

        ScriptDataSourceHandle dsrc = elementFactory.newScriptDataSource("DataSource");
        design.getDataSources().add(dsrc);

        ScriptDataSetHandle dsh = elementFactory.newScriptDataSet("DataSet");
        dsh.setProperty("dataSource", "DataSource");

        this.table = elementFactory.newTableItem("table1", data.size(), 1, 1, 0);
        design.getBody().add(this.table);

        this.table.setWidth("100%");

        this.table.setDataSet(dsh);

        this.headerRow = ((RowHandle) this.table.getHeader().get(0));
        this.detailRow = ((RowHandle) this.table.getDetail().get(0));
        this.cellCounter = 0;

        Iterator iter = data.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String lable = (String) entry.getKey();
            String field = (String) entry.getValue();

            LabelHandle label = elementFactory.newLabel(null);

            CellHandle c = (CellHandle) this.headerRow.getCells().get(this.cellCounter);
            c.getContent().add(label);

            label.setText(field);

            DataItemHandle detail = elementFactory.newDataItem(null);

            CellHandle d = (CellHandle) this.detailRow.getCells().get(this.cellCounter);
            d.getContent().add(detail);
            detail.setValueExpr("row[\"" + lable + "\"]");

            this.cellCounter += 1;

            ResultSetColumn rs = StructureFactory.createResultSetColumn();
            rs.setColumnName(lable);

            rs.setPosition(new Integer(this.cellCounter + 1));
            rs.setDataType("any");

            PropertyHandle resultSet = dsh.getPropertyHandle("resultSet");

            resultSet.addItem(rs);

            ColumnHint ch = StructureFactory.createColumnHint();
            ch.setProperty("columnName", lable);

            PropertyHandle columnHint = dsh.getPropertyHandle("columnHints");
            columnHint.addItem(ch);
        }

        design.getDataSets().add(dsh);

        DesignElementHandle element = elementFactory.newSimpleMasterPage("Page Master");
        design.getMasterPages().add(element);

        design.saveAs("c:\\first_report.rptdesign");
        design.close();
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.factory.test.CreateReportDynamically JD-Core Version: 0.5.4
 */