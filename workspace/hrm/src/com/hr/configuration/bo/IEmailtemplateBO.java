package com.hr.configuration.bo;

import com.hr.configuration.domain.Emailtemplate;
import java.util.List;

public abstract interface IEmailtemplateBO {
    public abstract boolean addEmailtemplate(Emailtemplate paramEmailtemplate);

    public abstract boolean delEmailtemplate(Class<Emailtemplate> paramClass, String paramString);

    public abstract Emailtemplate loadEmailtemplate(Class<Emailtemplate> paramClass,
            String paramString);

    public abstract String updateEmailtemplate(Emailtemplate paramEmailtemplate);

    public abstract List getList();

    public abstract Emailtemplate getEmailTemplateByNo(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.bo.IEmailtemplateBO JD-Core Version: 0.5.4
 */