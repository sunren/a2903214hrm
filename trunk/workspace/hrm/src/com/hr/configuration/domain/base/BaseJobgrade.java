package com.hr.configuration.domain.base;

import com.hr.base.DaoBean;
import com.hr.configuration.domain.Jobgrade;
import java.math.BigDecimal;

public abstract class BaseJobgrade extends DaoBean {
    public static String REF = "Jobgrade";
    public static String PROP_JOB_GRADE_NAME = "jobGradeName";
    public static String PROP_JOB_GRADE_LEVEL = "jobGradeLevel";
    public static String PROP_JOB_GRADE_MRP = "jobGradeMrp";
    public static String PROP_JOB_GRADE_NO = "jobGradeNo";
    public static String PROP_JOB_GRADE_SORT_ID = "jobGradeSortId";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private String jobGradeNo;
    private Integer jobGradeLevel;
    private BigDecimal jobGradeMrp;
    private String jobGradeName;
    private Integer jobGradeSortId;

    public BaseJobgrade() {
        initialize();
    }

    public BaseJobgrade(String id) {
        setId(id);
        initialize();
    }

    public BaseJobgrade(String id, String jobGradeNo, Integer jobGradeLevel,
            BigDecimal jobGradeMrp, String jobGradeName) {
        setId(id);
        setJobGradeNo(jobGradeNo);
        setJobGradeLevel(jobGradeLevel);
        setJobGradeMrp(jobGradeMrp);
        setJobGradeName(jobGradeName);
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

    public String getJobGradeNo() {
        return this.jobGradeNo;
    }

    public void setJobGradeNo(String jobGradeNo) {
        this.jobGradeNo = jobGradeNo;
    }

    public Integer getJobGradeLevel() {
        return this.jobGradeLevel;
    }

    public void setJobGradeLevel(Integer jobGradeLevel) {
        this.jobGradeLevel = jobGradeLevel;
    }

    public BigDecimal getJobGradeMrp() {
        return this.jobGradeMrp;
    }

    public void setJobGradeMrp(BigDecimal jobGradeMrp) {
        this.jobGradeMrp = jobGradeMrp;
    }

    public String getJobGradeName() {
        return this.jobGradeName;
    }

    public void setJobGradeName(String jobGradeName) {
        this.jobGradeName = jobGradeName;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Jobgrade))
            return false;

        Jobgrade jobgrade = (Jobgrade) obj;
        if ((null == getId()) || (null == jobgrade.getId()))
            return false;
        return getId().equals(jobgrade.getId());
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

    public Integer getJobGradeSortId() {
        return this.jobGradeSortId;
    }

    public void setJobGradeSortId(Integer jobGradeSortId) {
        this.jobGradeSortId = jobGradeSortId;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.domain.base.BaseJobgrade JD-Core Version: 0.5.4
 */