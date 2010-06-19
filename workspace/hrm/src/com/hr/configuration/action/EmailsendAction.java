package com.hr.configuration.action;

import com.hr.base.DWRUtil;
import com.hr.configuration.bo.IEmailsendBO;
import com.hr.configuration.domain.Emailsend;
import com.hr.spring.SpringBeanFactory;

public class EmailsendAction {
    protected static final String SUCC = "SUCC";
    protected static final String FAIL = "FAIL";
    protected static final String NOAUTH = "NOAUTH";
    private static final long serialVersionUID = -4234940898871087425L;
    private IEmailsendBO mailBo;

    private void setIEmailsendBO() {
        this.mailBo = ((IEmailsendBO) SpringBeanFactory.getBean("emailsendBO"));
    }

    public String delEmailsend(String id) {
        String flt = DWRUtil.checkAuth("emailSearch", "execute");
        if ("error".equalsIgnoreCase(flt))
            return "NOAUTH";
        setIEmailsendBO();
        return (this.mailBo.delEmailsend(Emailsend.class, id)) ? "SUCC" : "related";
    }

    public String updateEmailsend(Emailsend email) {
        String flt = DWRUtil.checkAuth("emailSearch", "execute");
        if ("error".equalsIgnoreCase(flt))
            return "NOAUTH";
        setIEmailsendBO();
        if ((email != null) && (this.mailBo.updateEmailsend(email))) {
            return "SUCC";
        }
        return "FAIL";
    }

    public int updateetStatus(String id, Integer status) {
        String flt = DWRUtil.checkAuth("etSearch", "execute");
        if ("error".equalsIgnoreCase(flt))
            return 2;
        setIEmailsendBO();
        if ((id != null) && (this.mailBo.updateetStatus(id, status))) {
            return status.intValue();
        }
        return 3;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.action.EmailsendAction JD-Core Version: 0.5.4
 */