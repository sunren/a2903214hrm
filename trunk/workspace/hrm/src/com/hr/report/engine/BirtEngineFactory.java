package com.hr.report.engine;

import com.hr.base.FileOperate;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.MyTools;
import com.hr.util.PropertiesFileConfigManager;
import com.hr.util.SysConfigManager;
import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.IPlatformContext;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.core.framework.PlatformServletContext;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.HTMLActionHandler;
import org.eclipse.birt.report.engine.api.HTMLEmitterConfig;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.model.api.DesignConfig;
import org.eclipse.birt.report.model.api.DesignEngine;
import org.eclipse.birt.report.model.api.IDesignEngine;

public class BirtEngineFactory {
    private static IReportEngine birtReportEngine = null;

    private static IDesignEngine birtDesignEngine = null;

    public static synchronized IDesignEngine getBirtDesignEngine(ServletContext sc) {
        if (birtDesignEngine == null) {
            DesignConfig config = new DesignConfig();

            config.setPlatformContext(new PlatformServletContext(sc));
            config.setProperty("BIRT_HOME", "");
            birtDesignEngine = new DesignEngine(config);

            String reportDir = sc.getRealPath("/") + getPropertyFromConfigFile("report.dir");
            File rF = new File(reportDir);
            if (!rF.exists()) {
                rF.mkdirs();
            }

            reportDir = sc.getRealPath("/") + getPropertyFromConfigFile("report.custpredefine");
            rF = new File(reportDir);
            if (!rF.exists()) {
                rF.mkdirs();
            }

            reportDir = sc.getRealPath("/") + getPropertyFromConfigFile("report.syspredefine");
            rF = new File(reportDir);
            if (!rF.exists()) {
                rF.mkdirs();
            }
        }
        return birtDesignEngine;
    }

    public static synchronized IReportEngine getBirtReportEngine(ServletContext sc) {
        if (birtReportEngine == null) {
            EngineConfig config = new EngineConfig();

            HashMap hm = config.getAppContext();
            hm.put("ctx", SpringBeanFactory.getSpringContext());
            hm.put("MyTools", new MyTools());
            hm.put("PropertiesReader", PropertiesFileConfigManager.getInstance());

            setLogLevel(config);

            config.setEngineHome("");
            IPlatformContext context = new PlatformServletContext(sc);
            config.setPlatformContext(context);

            HTMLEmitterConfig emitterConfig = new HTMLEmitterConfig();
            emitterConfig.setActionHandler(new HTMLActionHandler());
            HTMLServerImageHandler imageHandler = new HTMLServerImageHandler();
            emitterConfig.setImageHandler(imageHandler);
            config.getEmitterConfigs().put("html", emitterConfig);
            try {
                Platform.startup(config);

                IReportEngineFactory factory = (IReportEngineFactory) Platform
                        .createFactoryObject("org.eclipse.birt.report.engine.ReportEngineFactory");

                birtReportEngine = factory.createReportEngine(config);
            } catch (BirtException e) {
                e.printStackTrace();
            }
        }

        return birtReportEngine;
    }

    public static synchronized void destroyBirtReportEngine() {
        if (birtReportEngine == null) {
            return;
        }
        birtReportEngine.destroy();
        Platform.shutdown();
        birtReportEngine = null;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    private static void setLogLevel(EngineConfig config) {
        String logLevel = getPropertyFromConfigFile("logLevel");
        if ((logLevel != null) || (logLevel.trim().length() > 0)) {
            Level level = Level.OFF;
            if ("SEVERE".equalsIgnoreCase(logLevel))
                level = Level.SEVERE;
            else if ("WARNING".equalsIgnoreCase(logLevel))
                level = Level.WARNING;
            else if ("INFO".equalsIgnoreCase(logLevel))
                level = Level.INFO;
            else if ("CONFIG".equalsIgnoreCase(logLevel))
                level = Level.CONFIG;
            else if ("FINE".equalsIgnoreCase(logLevel))
                level = Level.FINE;
            else if ("FINER".equalsIgnoreCase(logLevel))
                level = Level.FINER;
            else if ("FINEST".equalsIgnoreCase(logLevel))
                level = Level.FINEST;
            else if ("OFF".equalsIgnoreCase(logLevel)) {
                level = Level.OFF;
            }

            String logDir = FileOperate.getFileHomePath()
                    + getPropertyFromConfigFile("logDirectory");

            File logFile = new File(logDir);
            if (!logFile.exists()) {
                logFile.mkdirs();
            }
            config.setLogConfig(logDir, level);
        }
    }

    private static String getPropertyFromConfigFile(String key) {
        SysConfigManager manager = PropertiesFileConfigManager.getInstance();
        return manager.getProperty(key);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.engine.BirtEngineFactory JD-Core Version: 0.5.4
 */