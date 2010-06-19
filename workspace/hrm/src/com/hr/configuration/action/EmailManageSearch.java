package com.hr.configuration.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IEmailsendBO;
import com.hr.configuration.domain.Emailsend;
import com.hr.util.Pager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class EmailManageSearch extends BaseAction {
    private static final long serialVersionUID = 1L;
    private Emailsend emailsend;
    private String begainCreatDate;
    private String endCreatDate;
    private String begainSendDate;
    private String endSendDate;
    private Pager page;
    private List mailList;

    public EmailManageSearch() {
        this.emailsend = new Emailsend();

        this.begainCreatDate = null;

        this.endCreatDate = null;

        this.begainSendDate = null;

        this.endSendDate = null;

        this.page = new Pager();
    }

    public String execute() throws Exception {
        IEmailsendBO bo = (IEmailsendBO) getBean("emailsendBO");
        this.mailList = bo.getListByPager(this.emailsend, this.page, getDate(this.begainCreatDate),
                                          getDate(this.endCreatDate), getDate(this.begainSendDate),
                                          getDate(this.endSendDate));
        return "success";
    }

    public List getMailList() {
        return this.mailList;
    }

    public void setMailList(List mailList) {
        this.mailList = mailList;
    }

    public Emailsend getEmailsend() {
        return this.emailsend;
    }

    public void setEmailsend(Emailsend emailsend) {
        this.emailsend = emailsend;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public Date getDate(String date) {
        String tempDate = "";
        try {
            if (date.indexOf('/') != -1) {
                StringTokenizer stk = new StringTokenizer(date, "/");
                tempDate = tempDate + stk.nextToken().trim() + "-" + stk.nextToken().trim() + "-"
                        + stk.nextToken().trim();
            } else {
                tempDate = date.trim();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(tempDate);
        } catch (Exception e) {
        }
        return null;
    }

    public String getBegainCreatDate() {
        return this.begainCreatDate;
    }

    public void setBegainCreatDate(String begainCreatDate) {
        this.begainCreatDate = begainCreatDate;
    }

    public String getBegainSendDate() {
        return this.begainSendDate;
    }

    public void setBegainSendDate(String begainSendDate) {
        this.begainSendDate = begainSendDate;
    }

    public String getEndCreatDate() {
        return this.endCreatDate;
    }

    public void setEndCreatDate(String endCreatDate) {
        this.endCreatDate = endCreatDate;
    }

    public String getEndSendDate() {
        return this.endSendDate;
    }

    public void setEndSendDate(String endSendDate) {
        this.endSendDate = endSendDate;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.action.EmailManageSearch JD-Core Version: 0.5.4
 */