package com.hr.configuration.bo;

import java.io.IOException;
import java.util.Map;

public abstract interface TemplateService {
    public abstract String getTemplateFilePath();

    public abstract String getContent(String paramString, Map<String, Object> paramMap);

    public abstract void createTemplate(String paramString1, String paramString2)
            throws IOException;

    public abstract void deleteTemplate(String paramString);

    public abstract void doTemplateCopy(String paramString1, String paramString2);

    public abstract String getTemplateContent(String paramString);

    public abstract String modifyTemplateContent(String paramString1, String paramString2);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.bo.TemplateService JD-Core Version: 0.5.4
 */