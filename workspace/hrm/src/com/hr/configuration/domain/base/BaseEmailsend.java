package com.hr.configuration.domain.base;

import com.hr.base.BaseDomain;
import com.hr.configuration.domain.Emailsend;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseEmailsend extends BaseDomain implements Serializable {
    public static String REF = "Emailsend";

    public static String PROP_ES_TITLE = "esTitle";

    public static String PROP_ES_CREATETIME = "esCreatetime";

    public static String PROP_ES_STATUS = "esStatus";

    public static String PROP_ES_CONTENT = "esContent";

    public static String PROP_ES_TO = "esTo";

    public static String PROP_ES_SENDTIME = "esSendtime";

    public static String PROP_ES_FROM = "esFrom";

    public static String PROP_ES_CC = "esCc";

    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private String esFrom;
    private String esTo;
    private String esCc;
    private String esTitle;
    private String esContent;
    private Integer esStatus;
    private Date esCreatetime;
    private Date esSendtime;

    public BaseEmailsend() {
        initialize();
    }

    public BaseEmailsend(String id) {
        setId(id);
        initialize();
    }

    public BaseEmailsend(String id, String esTo, String esTitle, String esContent,
            Integer esStatus, Date esCreatetime) {
        setId(id);
        setEsTo(esTo);
        setEsTitle(esTitle);
        setEsContent(esContent);
        setEsStatus(esStatus);
        setEsCreatetime(esCreatetime);
        initialize();
    }

    protected void initialize() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getEsFrom() {
        return this.esFrom;
    }

    public void setEsFrom(String esFrom) {
        this.esFrom = esFrom;
    }

    public String getEsTo() {
        return this.esTo;
    }

    public void setEsTo(String esTo) {
        this.esTo = esTo;
    }

    public String getEsCc() {
        return this.esCc;
    }

    public void setEsCc(String esCc) {
        this.esCc = esCc;
    }

    public String getEsTitle() {
        return this.esTitle;
    }

    public void setEsTitle(String esTitle) {
        this.esTitle = esTitle;
    }

    public String getEsContent() {
        return this.esContent;
    }

    public void setEsContent(String esContent) {
        this.esContent = esContent;
    }

    public Integer getEsStatus() {
        return this.esStatus;
    }

    public void setEsStatus(Integer esStatus) {
        this.esStatus = esStatus;
    }

    public Date getEsCreatetime() {
        return this.esCreatetime;
    }

    public void setEsCreatetime(Date esCreatetime) {
        this.esCreatetime = esCreatetime;
    }

    public Date getEsSendtime() {
        return this.esSendtime;
    }

    public void setEsSendtime(Date esSendtime) {
        this.esSendtime = esSendtime;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Emailsend)) {
            return false;
        }
        Emailsend emailsend = (Emailsend) obj;
        if ((null == getId()) || (null == emailsend.getId())) {
            return false;
        }
        return getId().equals(emailsend.getId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getId()) {
                return super.hashCode();
            }
            String hashStr = super.getClass().getName() + ":" + getId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }

    public String getStatusString() {
        switch (this.esStatus.intValue()) {
        case 0:
            return "未发送";
        case 1:
            return "已发送";
        case 2:
            return "发送失败";
        }
        return "发送失败";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.domain.base.BaseEmailsend JD-Core Version: 0.5.4
 */