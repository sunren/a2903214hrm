package com.hr.recruitment.domain.base;

import com.hr.base.BaseDomain;
import com.hr.profile.domain.Employee;
import com.hr.recruitment.domain.Recruitapplier;
import com.hr.recruitment.domain.Recruitchannel;
import com.hr.recruitment.domain.Recruitplan;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseRecruitapplier extends BaseDomain implements Serializable {
    public static String REF = "Recruitapplier";
    public static String PROP_RECA_NAME = "recaName";
    public static String PROP_RECA_INTERVIEWER = "recaInterviewer";
    public static String PROP_RECA_DIPLOMA = "recaDiploma";
    public static String PROP_RECA_COMMENT = "recaComment";
    public static String PROP_RECA_EMAIL = "recaEmail";
    public static String PROP_RECA_CHANNEL = "recaChannel";
    public static String PROP_RECA_PHONE = "recaPhone";
    public static String PROP_RECA_LAST_CHANGE_TIME = "recaLastChangeTime";
    public static String PROP_RECA_LAST_CHANGE_BY = "recaLastChangeBy";
    public static String PROP_RECA_CREATE_TIME = "recaCreateTime";
    public static String PROP_RECA_ATTACHMENT_NAME = "recaAttachmentName";
    public static String PROP_ID = "id";
    public static String PROP_RECA_CREATE_BY = "recaCreateBy";
    public static String PROP_RECA_INTERVIEW_TIME = "recaInterviewTime";
    public static String PROP_RECA_PLAN = "recaPlan";
    public static String PROP_RECA_STATUS = "recaStatus";

    private int hashCode = -2147483648;
    private String id;
    private String recaName;
    private String recaPhone;
    private String recaEmail;
    private String recaDiploma;
    private Date recaInterviewTime;
    private String recaInterviewer;
    private String recaAttachmentName;
    private Integer recaStatus;
    private String recaComment;
    private Date recaCreateTime;
    private Date recaLastChangeTime;
    private Recruitchannel recaChannel;
    private Employee recaLastChangeBy;
    private Recruitplan recaPlan;
    private Employee recaCreateBy;

    public BaseRecruitapplier() {
        initialize();
    }

    public BaseRecruitapplier(String id) {
        setId(id);
        initialize();
    }

    public BaseRecruitapplier(String id, Recruitchannel recaChannel, Employee recaLastChangeBy,
            Recruitplan recaPlan, Employee recaCreateBy, String recaName, Date recaCreateTime,
            Date recaLastChangeTime) {
        setId(id);
        setRecaChannel(recaChannel);
        setRecaLastChangeBy(recaLastChangeBy);
        setRecaPlan(recaPlan);
        setRecaCreateBy(recaCreateBy);
        setRecaName(recaName);
        setRecaCreateTime(recaCreateTime);
        setRecaLastChangeTime(recaLastChangeTime);
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

    public String getRecaName() {
        return this.recaName;
    }

    public void setRecaName(String recaName) {
        this.recaName = recaName;
    }

    public String getRecaPhone() {
        return this.recaPhone;
    }

    public void setRecaPhone(String recaPhone) {
        this.recaPhone = recaPhone;
    }

    public String getRecaEmail() {
        return this.recaEmail;
    }

    public void setRecaEmail(String recaEmail) {
        this.recaEmail = recaEmail;
    }

    public String getRecaDiploma() {
        return this.recaDiploma;
    }

    public void setRecaDiploma(String recaDiploma) {
        this.recaDiploma = recaDiploma;
    }

    public Date getRecaInterviewTime() {
        return this.recaInterviewTime;
    }

    public void setRecaInterviewTime(Date recaInterviewTime) {
        this.recaInterviewTime = recaInterviewTime;
    }

    public String getRecaInterviewer() {
        return this.recaInterviewer;
    }

    public void setRecaInterviewer(String recaInterviewer) {
        this.recaInterviewer = recaInterviewer;
    }

    public String getRecaAttachmentName() {
        return this.recaAttachmentName;
    }

    public void setRecaAttachmentName(String recaAttachmentName) {
        this.recaAttachmentName = recaAttachmentName;
    }

    public Integer getRecaStatus() {
        return this.recaStatus;
    }

    public void setRecaStatus(Integer recaStatus) {
        this.recaStatus = recaStatus;
    }

    public String getRecaComment() {
        return this.recaComment;
    }

    public void setRecaComment(String recaComment) {
        this.recaComment = recaComment;
    }

    public Date getRecaCreateTime() {
        return this.recaCreateTime;
    }

    public void setRecaCreateTime(Date recaCreateTime) {
        this.recaCreateTime = recaCreateTime;
    }

    public Date getRecaLastChangeTime() {
        return this.recaLastChangeTime;
    }

    public void setRecaLastChangeTime(Date recaLastChangeTime) {
        this.recaLastChangeTime = recaLastChangeTime;
    }

    public Recruitchannel getRecaChannel() {
        return this.recaChannel;
    }

    public void setRecaChannel(Recruitchannel recaChannel) {
        this.recaChannel = recaChannel;
    }

    public Employee getRecaLastChangeBy() {
        return this.recaLastChangeBy;
    }

    public void setRecaLastChangeBy(Employee recaLastChangeBy) {
        this.recaLastChangeBy = recaLastChangeBy;
    }

    public Recruitplan getRecaPlan() {
        return this.recaPlan;
    }

    public void setRecaPlan(Recruitplan recaPlan) {
        this.recaPlan = recaPlan;
    }

    public Employee getRecaCreateBy() {
        return this.recaCreateBy;
    }

    public void setRecaCreateBy(Employee recaCreateBy) {
        this.recaCreateBy = recaCreateBy;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Recruitapplier))
            return false;

        Recruitapplier recruitapplier = (Recruitapplier) obj;
        if ((null == getId()) || (null == recruitapplier.getId()))
            return false;
        return getId().equals(recruitapplier.getId());
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
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.recruitment.domain.base.BaseRecruitapplier JD-Core Version: 0.5.4
 */