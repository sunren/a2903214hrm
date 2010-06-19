package com.hr.configuration.bo;

import com.hr.configuration.domain.Emailsend;
import com.hr.configuration.domain.Emailtemplate;
import com.hr.util.Pager;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract interface IEmailsendBO {
    public abstract void updateSendEmailOut();

    public abstract boolean addEmailsend(Emailsend paramEmailsend);

    public abstract boolean delEmailsend(Class<Emailsend> paramClass, String paramString);

    public abstract boolean updateEmailsend(Emailsend paramEmailsend);

    public abstract boolean updateetStatus(String paramString, Integer paramInteger);

    public abstract List getListByPager(Emailsend paramEmailsend, Pager paramPager,
            Date paramDate1, Date paramDate2, Date paramDate3, Date paramDate4);

    public abstract Map<String, String> getEmailContent(String paramString, Map paramMap);

    /** @deprecated */
    public abstract boolean sendEmailToDept(String paramString1, String paramString2,
            String paramString3, Map<String, Object> paramMap);

    public abstract String addEmailSend(Emailtemplate paramEmailtemplate, Emailsend paramEmailsend,
            Map<String, Object> paramMap);

    public abstract String addEmailSend(Emailtemplate paramEmailtemplate,
            List<Emailsend> paramList, List<Map<String, Object>> paramList1);

    public abstract String sendEmail(Emailsend paramEmailsend);

    public abstract List<String> sendMail(List<Emailsend> paramList);

    public abstract List<String> sendMail(List<Emailsend> paramList, int paramInt);

    public abstract void saveAndSendMail(List<Object> paramList);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.bo.IEmailsendBO JD-Core Version: 0.5.4
 */