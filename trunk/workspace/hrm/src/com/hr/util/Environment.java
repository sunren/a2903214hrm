package com.hr.util;

import com.hr.base.quartz.DatabaseBackupService;
import com.hr.configuration.bo.TemplateService;
import com.hr.spring.SpringBeanFactory;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;

public class Environment implements ServletContextListener {
    private static Logger logger = Logger.getLogger(Environment.class);
    private static ServletContext application;
    private static Map<SystemVariables, Object> sysMap = Collections.synchronizedMap(new HashMap());
    SysConfigManager config;

    public Environment() {
        this.config = PropertiesFileConfigManager.getInstance();
    }

    public static String getFileSeparator() {
        return (String) sysMap.get(SystemVariables.FILE_SEPARATOR);
    }

    public static String getEmailTemplateHome() {
        return (String) sysMap.get(SystemVariables.TEMPLATE_HOME);
    }

    public static String getApplicationHome() {
        return (String) sysMap.get(SystemVariables.APPLICATION_HOME);
    }

    public static String getFileHome() {
        return (String) sysMap.get(SystemVariables.CUSTOMER_FILE_HOME);
    }

    public void contextDestroyed(ServletContextEvent contextEvent) {
    }

    public void contextInitialized(ServletContextEvent contextEvent) {
        application = contextEvent.getServletContext();
        doInit();
    }

    private void doInit() {
        sysMap.put(SystemVariables.FILE_SEPARATOR, SystemUtils.FILE_SEPARATOR);

        logger.info("365hrm系统初始化开姄1�7...");
        Map props = this.config.getProperties();

        String path = application.getRealPath("");
        String filePath = new File(path).getParentFile().getAbsolutePath();
        String readPath = StringUtils.defaultIfEmpty((String) props.get("sys.file.path"), "file/");
        readPath = "/" + readPath;
        logger.info("用户文件的完整存放路径为＄1�7" + filePath + readPath);
        File file = new File(filePath + readPath);
        if (!file.exists())
            file.mkdirs();
        sysMap.put(SystemVariables.CUSTOMER_FILE_HOME, filePath + readPath);

        sysMap.put(SystemVariables.TEMPLATE_HOME, getFileHome() + "emailtemplate"
                + getFileSeparator());

        String applicationHome = application.getRealPath("/");
        sysMap.put(SystemVariables.APPLICATION_HOME, applicationHome);
        logger.info("web完整存放路径为：" + applicationHome);

        doBackupDatabase(filePath + readPath);

        doCopyEmailTemplate();

        logger.info("365hrm系统初始化结杄1�7");
    }

    private void doCopyEmailTemplate() {
        String fromFolder = getApplicationHome() + "WEB-INF" + getFileSeparator() + "emailtemplate"
                + getFileSeparator();
        String toFolder = getFileHome() + "emailtemplate" + getFileSeparator();
        TemplateService templateService = (TemplateService) SpringBeanFactory
                .getBean("emailTemplateService");
        logger.info("Start to copy Email template from folder " + fromFolder + " to folder "
                + toFolder);
        templateService.doTemplateCopy(fromFolder, toFolder);
        logger.info("End of copy email templates.");
    }

    private void doBackupDatabase(String path) {
        DataSource dataSource = (DataSource) SpringBeanFactory.getBean("dataSource");
        try {
            dataSource.getConnection().close();
        } catch (SQLException e) {
            logger.warn("无法连接数据库，跳过数据备份〄1�7");
            return;
        }
        String folderPath = path + this.config.getProperty("sys.security.backup.path");
        logger.info("数据备份弄1�7始，棄1�7查备份目录：" + folderPath);
        File files = new File(folderPath);
        if (!files.exists()) {
            files.mkdirs();
        }
        String[] names = files.list();
        Arrays.sort(names);
        String serString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        for (int i = names.length - 1; i > 0; --i) {
            if (StringUtils.contains(names[i], serString)) {
                logger.info(serString + "存在数据备份文件");
                return;
            }
        }
        DatabaseBackupService service = new DatabaseBackupService();
        service.executeBackup();
        logger.info("数据库备份完毄1�7");
    }

    private static enum SystemVariables {
        FILE_SEPARATOR, APPLICATION_HOME, TEMPLATE_HOME, PAGE_SIZE, CUSTOMER_FILE_HOME;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.util.Environment JD-Core Version: 0.5.4
 */