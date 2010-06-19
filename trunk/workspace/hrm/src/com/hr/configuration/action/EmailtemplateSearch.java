package com.hr.configuration.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.configuration.bo.IEmailtemplateBO;
import com.hr.configuration.domain.Emailtemplate;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.Pager;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class EmailtemplateSearch extends BaseAction implements Constants {
    private static final long serialVersionUID = -4234940898871087425L;
    private static final Logger logger = Logger.getLogger(EmailtemplateSearch.class);
    private List templateList;
    private Pager page;
    private String searchKey;
    private String templateId;
    private Emailtemplate template;

    public EmailtemplateSearch() {
        this.page = new Pager();

        this.searchKey = null;

        this.templateId = null;
    }

    public String execute() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("测试：execute() - SearchInfo.action");
        }

        IEmailtemplateBO templateBo = (IEmailtemplateBO) SpringBeanFactory
                .getBean("emailtemplateBO");
        this.templateList = templateBo.getList();
        if (logger.isDebugEnabled()) {
            logger.debug("execute() -SearchInfo- end");
        }
        return "success";
    }

    public String modifyTemplate() {
        IEmailtemplateBO templateBo = (IEmailtemplateBO) SpringBeanFactory
                .getBean("emailtemplateBO");
        String error = templateBo.updateEmailtemplate(this.template);
        if (error == null) {
            addSuccessInfo("邮件模板" + getTemplate().getEtTitleNo() + "修改成功");
            return "success";
        }
        addActionError(error);
        return "input";
    }

    public String modifyTemplateInit() {
        if (StringUtils.isEmpty(this.templateId)) {
            return "success";
        }
        IEmailtemplateBO templateBo = (IEmailtemplateBO) SpringBeanFactory
                .getBean("emailtemplateBO");
        this.template = templateBo.loadEmailtemplate(Emailtemplate.class, this.templateId);
        return "success";
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public String getSearchKey() {
        return this.searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public List getTemplateList() {
        return this.templateList;
    }

    public void setTemplateList(List templateList) {
        this.templateList = templateList;
    }

    public Emailtemplate getTemplate() {
        return this.template;
    }

    public void setTemplate(Emailtemplate template) {
        this.template = template;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.action.EmailtemplateSearch JD-Core Version: 0.5.4
 */