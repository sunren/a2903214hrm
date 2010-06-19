package com.hr.security.web.taglib;

import com.hr.base.UsersAuthority;
import com.hr.security.bo.IHasAuthBO;
import com.hr.spring.SpringBeanFactory;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class AuthTag extends BodyTagSupport {
    private static final long serialVersionUID = -5545889424412836832L;
    private String prefix;
    private String auths;
    private String noauths;
    private static IHasAuthBO authBo = (IHasAuthBO) SpringBeanFactory.getBean("hasService");

    public int doStartTag() throws JspException {
        if ((((this.prefix == null) || (this.prefix.length() == 0)))
                && (((this.auths == null) || (this.auths.length() == 0)))
                && (((this.noauths == null) || (this.noauths.length() == 0)))) {
            return 0;
        }

        if ((this.prefix != null) && (this.prefix.length() > 0)) {
            try {
                return (authBo.hasJSPAuth(this.pageContext, this.prefix)) ? 1 : 0;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
        if ((this.auths != null) && (this.auths.length() > 0)) {
            List userAuths = getAuthsList(this.auths, "or");
            for (int i = 0; i < userAuths.size(); ++i) {
                try {
                    if (authBo.hasJSPAuth(this.pageContext, (UsersAuthority) userAuths.get(i)))
                        return 1;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return 0;
        }
        if ((this.noauths != null) && (this.noauths.length() > 0)) {
            List userAuths = getAuthsList(this.noauths, "and");
            int hasAuthsSize = 0;
            for (int i = 0; i < userAuths.size(); ++i) {
                try {
                    if (!authBo.hasJSPAuth(this.pageContext, (UsersAuthority) userAuths.get(i)))
                        ++hasAuthsSize;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if ((hasAuthsSize > 0) && (hasAuthsSize == userAuths.size())) {
                return 1;
            }
            return 0;
        }

        return 1;
    }

    private List<UsersAuthority> getAuthsList(String auths, String splitString) {
        List resultList = new ArrayList();
        try {
            String[] auth = auths.split(splitString);
            int rowNum = auth.length;
            for (int i = 0; i < rowNum; ++i) {
                String[] intTemp = auth[i].trim().split(",");
                if (intTemp.length == 1)
                    resultList.add(new UsersAuthority(intTemp[0].trim(), null));
                else if (intTemp.length > 1)
                    resultList.add(new UsersAuthority(intTemp[0].trim(), intTemp[1].trim()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getAuths() {
        return this.auths;
    }

    public void setAuths(String auths) {
        this.auths = auths;
    }

    public String getNoauths() {
        return this.noauths;
    }

    public void setNoauths(String noauths) {
        this.noauths = noauths;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.web.taglib.AuthTag JD-Core Version: 0.5.4
 */