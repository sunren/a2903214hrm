package com.hr.report.servlet;

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

public class BirtEngine {
    private static IReportEngine birtEngine = null;

    public static synchronized IReportEngine getBirtEngine(ServletContext sc) {
        if (birtEngine == null) {
            EngineConfig config = new EngineConfig();

            HashMap hm = config.getAppContext();
            hm.put("ctx", SpringBeanFactory.getSpringContext());
            hm.put("MyTools", new MyTools());
            hm.put("PropertiesReader", PropertiesFileConfigManager.getInstance());

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

                String logDir = sc.getRealPath("/") + getPropertyFromConfigFile("logDirectory");
                File logFile = new File(logDir);
                if (!logFile.exists()) {
                    logFile.mkdirs();
                }
                config.setLogConfig(logDir, level);
            }

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

                birtEngine = factory.createReportEngine(config);
            } catch (BirtException e) {
                e.printStackTrace();
            }
        }

        return birtEngine;
    }

    public static synchronized void destroyBirtEngine() {
        if (birtEngine == null) {
            return;
        }
        birtEngine.shutdown();
        Platform.shutdown();
        birtEngine = null;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    private static String getPropertyFromConfigFile(String key) {
        SysConfigManager manager = PropertiesFileConfigManager.getInstance();
        return manager.getProperty(key);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.servlet.BirtEngine JD-Core Version: 0.5.4
 */