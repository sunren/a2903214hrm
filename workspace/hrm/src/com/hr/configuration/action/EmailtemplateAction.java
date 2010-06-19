package com.hr.configuration.action;

import com.hr.base.DWRUtil;
import com.hr.configuration.bo.IEmailtemplateBO;
import com.hr.configuration.bo.TemplateService;
import com.hr.configuration.domain.Emailtemplate;
import com.hr.spring.SpringBeanFactory;
import java.util.HashMap;
import java.util.Map;

public class EmailtemplateAction {
    protected static final String SUCC = "SUCC";
    protected static final String FAIL = "FAIL";
    protected static final String NOAUTH = "NOAUTH";
    private static final long serialVersionUID = -4234940898871087425L;
    private IEmailtemplateBO templateBo;

    private void setITemplateBo() {
        if (this.templateBo == null)
            this.templateBo = ((IEmailtemplateBO) SpringBeanFactory.getBean("emailtemplateBO"));
    }

    public String addTemplate(Emailtemplate template) {
        String flt = DWRUtil.checkAuth("etSearch", "execute");
        if ("error".equalsIgnoreCase(flt))
            return "NOAUTH";
        setITemplateBo();
        template.setEtStatus(Integer.valueOf(1));
        if (this.templateBo.addEmailtemplate(template)) {
            return template.getId();
        }
        return "FAIL";
    }

    public String delTemplate(String templateId) {
        String flt = DWRUtil.checkAuth("etSearch", "execute");
        if ("error".equalsIgnoreCase(flt))
            return "NOAUTH";
        setITemplateBo();
        return (this.templateBo.delEmailtemplate(Emailtemplate.class, templateId)) ? "SUCC"
                : "related";
    }

    public Map<String, String> getTemplateContent(String templateNo) {
        String flt = DWRUtil.checkAuth("etSearch", "execute");
        if ("error".equalsIgnoreCase(flt)) {
            return null;
        }
        TemplateService templateService = (TemplateService) SpringBeanFactory
                .getBean("emailTemplateService");
        String title = templateService.getTemplateContent(templateNo + "_title.ftl");
        String body = templateService.getTemplateContent(templateNo + "_body.ftl");
        Map result = new HashMap();
        result.put("title", title);
        result.put("body", body);
        return result;
    }

    /** @deprecated */
    public String updateTemplate(Emailtemplate template) {
        String flt = DWRUtil.checkAuth("etSearch", "execute");
        if ("error".equalsIgnoreCase(flt))
            return "NOAUTH";
        setITemplateBo();

        return "FAIL";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.action.EmailtemplateAction JD-Core Version: 0.5.4
 */