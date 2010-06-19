package com.hr.report.factory.test;

import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.io.PrintStream;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.model.api.CellHandle;
import org.eclipse.birt.report.model.api.DesignConfig;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.GridHandle;
import org.eclipse.birt.report.model.api.IDesignEngine;
import org.eclipse.birt.report.model.api.IDesignEngineFactory;
import org.eclipse.birt.report.model.api.ImageHandle;
import org.eclipse.birt.report.model.api.LabelHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.RowHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.SlotHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;

public class DeDemo {
    public static void main(String[] args) {
        try {
            buildReport();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SemanticException e) {
            e.printStackTrace();
        }
    }

    static void buildReport() throws IOException, SemanticException {
        DesignConfig config = new DesignConfig();

        config.setProperty("BIRT_HOME",
                           "D:/wxz/birt 安装/birt-runtime-2_2_1/birt-runtime-2_2_1/ReportEngine");

        IDesignEngine engine = null;
        try {
            Platform.startup(config);
            IDesignEngineFactory factory = (IDesignEngineFactory) Platform
                    .createFactoryObject("org.eclipse.birt.report.model.DesignEngineFactory");

            engine = factory.createDesignEngine(config);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SessionHandle session = engine.newSessionHandle(ULocale.ENGLISH);

        ReportDesignHandle design = session.createDesign();

        ElementFactory factory = design.getElementFactory();

        DesignElementHandle element = factory.newSimpleMasterPage("Page Master");

        design.getMasterPages().add(element);

        GridHandle grid = factory.newGridItem(null, 2, 1);

        design.getBody().add(grid);

        grid.setWidth("100%");

        RowHandle row = (RowHandle) grid.getRows().get(0);

        ImageHandle image = factory.newImage(null);
        CellHandle cell = (CellHandle) row.getCells().get(0);
        cell.getContent().add(image);
        image.setURL("\"http://www.eclipse.org/birt/phoenix/tutorial/basic/multichip-4.jpg\"");

        LabelHandle label = factory.newLabel(null);
        cell = (CellHandle) row.getCells().get(1);
        cell.getContent().add(label);
        label.setText("Hello, world!");

        design.saveAs("c:/tmp/sample.rptdesign");
        design.close();
        System.out.println("Finished");
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.factory.test.DeDemo JD-Core Version: 0.5.4
 */