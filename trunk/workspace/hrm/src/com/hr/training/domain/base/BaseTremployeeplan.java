package com.hr.training.domain.base;

import com.hr.base.BaseDomain;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.JobTitle;
import com.hr.profile.domain.Employee;
import com.hr.training.domain.Trcourseplan;
import com.hr.training.domain.Tremployeeplan;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseTremployeeplan extends BaseDomain implements Serializable {
    public static String REF = "Tremployeeplan";
    public static String PROP_TRP_FILE_NAME = "trpFileName";
    public static String PROP_TRP_CREATE_TIME = "trpCreateTime";
    public static String PROP_TRP_TRAINEE_JOB_TITLE = "trpTraineeJobTitle";
    public static String PROP_TRP_ID = "trpId";
    public static String PROP_TRP_TRAINEE_NO = "trpTraineeNo";
    public static String PROP_TRP_COMMENTS = "trpComments";
    public static String PROP_TRP_LAST_CHANGE_BY = "trpLastChangeBy";
    public static String PROP_TRP_TRAINEE_DEPT = "trpTraineeDept";
    public static String PROP_TRP_CREATE_BY = "trpCreateBy";
    public static String PROP_TRP_LAST_CHANGE_TIME = "trpLastChangeTime";
    public static String PROP_TRP_STATUS = "trpStatus";
    public static String PROP_TRP_TRCP = "trpTrcp";

    private int hashCode = -2147483648;
    private String trpId;
    private Integer trpStatus;
    private String trpComments;
    private String trpFileName;
    private Date trpCreateTime;
    private Date trpLastChangeTime;
    private Department trpTraineeDept;
    private Employee trpCreateBy;
    private Employee trpTraineeNo;
    private Trcourseplan trpTrcp;
    private Employee trpLastChangeBy;
    private JobTitle trpTraineeJobTitle;

    public BaseTremployeeplan() {
        initialize();
    }

    public BaseTremployeeplan(String trpId) {
        setTrpId(trpId);
        initialize();
    }

    public BaseTremployeeplan(String trpId, Department trpTraineeDept, Employee trpCreateBy,
            Employee trpTraineeNo, Trcourseplan trpTrcp, Employee trpLastChangeBy,
            JobTitle trpTraineeJobTitle, Integer trpStatus, Date trpCreateTime,
            Date trpLastChangeTime) {
        setTrpId(trpId);
        setTrpTraineeDept(trpTraineeDept);
        setTrpCreateBy(trpCreateBy);
        setTrpTraineeNo(trpTraineeNo);
        setTrpTrcp(trpTrcp);
        setTrpLastChangeBy(trpLastChangeBy);
        setTrpTraineeJobTitle(trpTraineeJobTitle);
        setTrpStatus(trpStatus);
        setTrpCreateTime(trpCreateTime);
        setTrpLastChangeTime(trpLastChangeTime);
        initialize();
    }

    protected void initialize() {
    }

    public String getTrpId() {
        return this.trpId;
    }

    public void setTrpId(String trpId) {
        this.trpId = trpId;
        this.hashCode = -2147483648;
    }

    public JobTitle getTrpTraineeJobTitle() {
        return this.trpTraineeJobTitle;
    }

    public void setTrpTraineeJobTitle(JobTitle trpTraineeJobTitle2) {
        this.trpTraineeJobTitle = trpTraineeJobTitle2;
    }

    public Integer getTrpStatus() {
        return this.trpStatus;
    }

    public void setTrpStatus(Integer trpStatus) {
        this.trpStatus = trpStatus;
    }

    public String getTrpComments() {
        return this.trpComments;
    }

    public void setTrpComments(String trpComments) {
        this.trpComments = trpComments;
    }

    public String getTrpFileName() {
        return this.trpFileName;
    }

    public void setTrpFileName(String trpFileName) {
        this.trpFileName = trpFileName;
    }

    public Date getTrpCreateTime() {
        return this.trpCreateTime;
    }

    public void setTrpCreateTime(Date trpCreateTime) {
        this.trpCreateTime = trpCreateTime;
    }

    public Date getTrpLastChangeTime() {
        return this.trpLastChangeTime;
    }

    public void setTrpLastChangeTime(Date trpLastChangeTime) {
        this.trpLastChangeTime = trpLastChangeTime;
    }

    public Department getTrpTraineeDept() {
        return this.trpTraineeDept;
    }

    public void setTrpTraineeDept(Department trpTraineeDept) {
        this.trpTraineeDept = trpTraineeDept;
    }

    public Employee getTrpCreateBy() {
        return this.trpCreateBy;
    }

    public void setTrpCreateBy(Employee trpCreateBy) {
        this.trpCreateBy = trpCreateBy;
    }

    public Employee getTrpTraineeNo() {
        return this.trpTraineeNo;
    }

    public void setTrpTraineeNo(Employee trpTraineeNo) {
        this.trpTraineeNo = trpTraineeNo;
    }

    public Trcourseplan getTrpTrcp() {
        return this.trpTrcp;
    }

    public void setTrpTrcp(Trcourseplan trpTrcp) {
        this.trpTrcp = trpTrcp;
    }

    public Employee getTrpLastChangeBy() {
        return this.trpLastChangeBy;
    }

    public void setTrpLastChangeBy(Employee trpLastChangeBy) {
        this.trpLastChangeBy = trpLastChangeBy;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Tremployeeplan))
            return false;

        Tremployeeplan tremployeeplan = (Tremployeeplan) obj;
        if ((null == getTrpId()) || (null == tremployeeplan.getTrpId()))
            return false;
        return getTrpId().equals(tremployeeplan.getTrpId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getTrpId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getTrpId().hashCode();
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
 * com.hr.training.domain.base.BaseTremployeeplan JD-Core Version: 0.5.4
 */