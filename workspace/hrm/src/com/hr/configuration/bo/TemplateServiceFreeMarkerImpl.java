package com.hr.configuration.bo;

import com.hr.util.Environment;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class TemplateServiceFreeMarkerImpl implements TemplateService {
    private static final Logger logger = Logger.getLogger(TemplateServiceFreeMarkerImpl.class);
    private Configuration freeMarkerConfiguration;
    private Map<String, Object> supportedVariables;
    private Properties freemarkerSettings;
    private String templateFilePath;

    public void setTemplateFilePath(String templateFilePath) {
        this.templateFilePath = templateFilePath;
    }

    public void setFreeMarkerConfiguration(Configuration freeMarkerConfiguration) {
        this.freeMarkerConfiguration = freeMarkerConfiguration;
    }

    public void setSupportedVariables(Map<String, Object> supportedVariables) {
        this.supportedVariables = supportedVariables;
    }

    public void setFreemarkerSettings(Properties freemarkerSettings) {
        this.freemarkerSettings = freemarkerSettings;
    }

    public String getContent(String templateName, Map<String, Object> model) {
        String mailURL = StringUtils.defaultIfEmpty((String) model.get("URL"), "");
        String sysURL = (String) this.supportedVariables.get("URL");
        model.put("URL", sysURL + mailURL);
        model.put("APPROVEURL", sysURL
                + StringUtils.defaultIfEmpty((String) model.get("APPROVEURL"), ""));
        try {
            Template textTemplate = createConfiguration().getTemplate(templateName + ".ftl");
            StringWriter textWriter = new StringWriter();
            textTemplate.process(model, textWriter);
            return textWriter.toString();
        } catch (IOException e) {
            logger.warn("找不到模板文仄1�7" + templateName);
            e.printStackTrace();
        } catch (TemplateException e) {
            logger.warn("参数传�1�7�错评1�7" + templateName);
            e.printStackTrace();
        }
        return "";
    }

    public Configuration createConfiguration() throws TemplateException, IOException {
        if (this.freeMarkerConfiguration == null) {
            this.freeMarkerConfiguration = new Configuration();
            this.freeMarkerConfiguration.setSettings(this.freemarkerSettings);
            this.freeMarkerConfiguration.setObjectWrapper(new DefaultObjectWrapper());
            for (String key : this.supportedVariables.keySet()) {
                this.freeMarkerConfiguration.setSharedVariable(key, this.supportedVariables
                        .get(key));
            }
            String filePath = Environment.getEmailTemplateHome();
            this.freeMarkerConfiguration.setDirectoryForTemplateLoading(new File(filePath));
        }
        return this.freeMarkerConfiguration;
    }

    public void doTemplateCopy(String fromFolder, String toFolder) {
        File custFolder = new File(toFolder);
        custFolder.mkdirs();

        File standardTemplateFolder = new File(fromFolder);
        File[] standardTemplates = standardTemplateFolder.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".ftl");
            }
        });
        for (int i = 0; i < standardTemplates.length; ++i) {
            String templateName = standardTemplates[i].getName();
            if (new File(custFolder, templateName).exists())
                continue;
            try {
                logger.info("Copy template:" + standardTemplates[i].getName());
                FileUtils.copyFileToDirectory(standardTemplates[i], custFolder);
            } catch (IOException e) {
                logger.error("复制模板" + standardTemplates[i] + "时发生错评1�7" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public String getTemplateContent(String templateFileName) {
        File file = new File(Environment.getEmailTemplateHome() + templateFileName);
        try {
            return FileUtils.readFileToString(file, "gbk");
        } catch (IOException e) {
            logger.warn("读取模板文件" + templateFileName + "时发生错评1�7" + e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

    public String modifyTemplateContent(String templateFileName, String content) {
        File file = new File(Environment.getEmailTemplateHome() + templateFileName);
        try {
            FileUtils.writeStringToFile(file, content, "gbk");
        } catch (IOException e) {
            String error = "写模板文仄1�7" + templateFileName + "时发生错评1�7" + e.getMessage();
            logger.warn(error);
            return error;
        }
        return null;
    }

    public void createTemplate(String content, String fullPath) throws IOException {
        File file = new File(fullPath);
        FileUtils.writeStringToFile(file, content, "gbk");
    }

    public String getTemplateFilePath() {
        return this.templateFilePath;
    }

    public void deleteTemplate(String fileName) {
        try {
            FileUtils.forceDelete(new File(fileName));
        } catch (IOException e) {
        }
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.bo.TemplateServiceFreeMarkerImpl JD-Core Version: 0.5.4
 */