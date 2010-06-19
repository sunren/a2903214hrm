package com.hr.configuration.domain.base;

import com.hr.base.BaseDomain;
import com.hr.configuration.domain.Emailtemplate;
import java.io.Serializable;

public abstract class BaseEmailtemplate extends BaseDomain implements Serializable {
    public static String REF = "Emailtemplate";
    public static String PROP_ID = "id";
    public static String PROP_ET_TITLE_NO = "etTitleNo";
    public static String PROP_ET_TITLE = "etTitle";
    public static String PROP_ET_CONTENT = "etContent";
    public static String PROP_ET_NOTES = "etNotes";
    public static String PROP_ET_STATUS = "etStatus";
    public static String PROP_ET_SEND_MODE = "etSendMode";

    private int hashCode = -2147483648;
    private String id;
    private String etTitleNo;
    private String etTitle;
    private String etContent;
    private String etNotes;
    private Integer etStatus = Integer.valueOf(1);
    private Integer etSendMode = Integer.valueOf(2);

    public BaseEmailtemplate() {
        initialize();
    }

    public BaseEmailtemplate(String id) {
        setId(id);
        initialize();
    }

    public BaseEmailtemplate(String id, String etTitleNo, String etTitle, String etContent,
            Integer etStatus, Integer etSendMode) {
        setId(id);
        setEtTitleNo(etTitleNo);
        setEtTitle(etTitle);
        setEtContent(etContent);
        setEtStatus(etStatus);
        setEtSendMode(etSendMode);
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

    public String getEtTitleNo() {
        return this.etTitleNo;
    }

    public void setEtTitleNo(String etTitleNo) {
        this.etTitleNo = etTitleNo;
    }

    public String getEtTitle() {
        return this.etTitle;
    }

    public void setEtTitle(String etTitle) {
        this.etTitle = etTitle;
    }

    public String getEtContent() {
        return this.etContent;
    }

    public void setEtContent(String etContent) {
        this.etContent = etContent;
    }

    public String getEtNotes() {
        return this.etNotes;
    }

    public void setEtNotes(String etNotes) {
        this.etNotes = etNotes;
    }

    public void setEtStatus(Integer etStatus) {
        this.etStatus = etStatus;
    }

    public Integer getEtStatus() {
        return this.etStatus;
    }

    public void setEtSendMode(Integer etSendMode) {
        this.etSendMode = etSendMode;
    }

    public Integer getEtSendMode() {
        return this.etSendMode;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Emailtemplate))
            return false;

        Emailtemplate emailtemplate = (Emailtemplate) obj;
        if ((null == getId()) || (null == emailtemplate.getId()))
            return false;
        return getId().equals(emailtemplate.getId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.domain.base.BaseEmailtemplate JD-Core Version: 0.5.4
 */