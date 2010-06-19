package com.hr.recruitment.domain.base;

import com.hr.base.BaseDomain;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.JobTitle;
import com.hr.configuration.domain.Location;
import com.hr.profile.domain.Employee;
import com.hr.recruitment.domain.Recruitapplier;
import com.hr.recruitment.domain.Recruitplan;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public abstract class BaseRecruitplan extends BaseDomain implements Serializable {
    public static String REF = "Recruitplan";
    public static String PROP_RECP_START_DATE = "recpStartDate";
    public static String PROP_RECP_NO = "recpNo";
    public static String PROP_RECP_JOB_SKILL = "recpJobSkill";
    public static String PROP_RECP_WORK_LOCATION = "recpWorkLocation";
    public static String PROP_RECP_LAST_CHANGE_TIME = "recpLastChangeTime";
    public static String PROP_RECP_DEPARTMENT_NO = "recpDepartmentNo";
    public static String PROP_RECP_TYPE = "recpType";
    public static String PROP_RECP_STATUS = "recpStatus";
    public static String PROP_RECP_LAST_CHANGE_BY = "recpLastChangeBy";
    public static String PROP_RECP_CREATE_TIME = "recpCreateTime";
    public static String PROP_RECP_COMMENTS = "recpComments";
    public static String PROP_RECP_NUMBER_EXPECT = "recpNumberExpect";
    public static String PROP_RECP_LANGUAGE_SKILL = "recpLanguageSkill";
    public static String PROP_RECP_JOB_DESC = "recpJobDesc";
    public static String PROP_ID = "id";
    public static String PROP_RECP_CREATE_BY = "recpCreateBy";
    public static String PROP_RECP_DEGREE = "recpDegree";
    public static String PROP_RECP_END_DATE = "recpEndDate";
    public static String PROP_RECP_JOB_TITLE = "recpJobTitle";

    private int hashCode = -2147483648;
    private String id;
    private Integer recpNo;
    private String recpJobDesc;
    private Date recpStartDate;
    private Date recpEndDate;
    private Integer recpNumberExpect;
    private String recpDegree;
    private String recpLanguageSkill;
    private String recpJobSkill;
    private String recpComments;
    private Integer recpStatus;
    private Date recpCreateTime;
    private Date recpLastChangeTime;
    private Location recpWorkLocation;
    private Employee recpLastChangeBy;
    private Department recpDepartmentNo;
    private Employee recpCreateBy;
    private Emptype recpType;
    private JobTitle recpJobTitle;
    private Set<Recruitapplier> recruitappliers;

    public BaseRecruitplan() {
        initialize();
    }

    public BaseRecruitplan(String id) {
        setId(id);
        initialize();
    }

    public BaseRecruitplan(String id, Location recpWorkLocation, Employee recpLastChangeBy,
            Department recpDepartmentNo, Employee recpCreateBy, Emptype recpType, Integer recpNo,
            JobTitle recpJobTitle, Date recpStartDate, Integer recpNumberExpect,
            Integer recpStatus, Date recpCreateTime, Date recpLastChangeTime) {
        setId(id);
        setRecpWorkLocation(recpWorkLocation);
        setRecpLastChangeBy(recpLastChangeBy);
        setRecpDepartmentNo(recpDepartmentNo);
        setRecpCreateBy(recpCreateBy);
        setRecpType(recpType);
        setRecpNo(recpNo);
        setRecpJobTitle(recpJobTitle);
        setRecpStartDate(recpStartDate);
        setRecpNumberExpect(recpNumberExpect);
        setRecpStatus(recpStatus);
        setRecpCreateTime(recpCreateTime);
        setRecpLastChangeTime(recpLastChangeTime);
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

    public Integer getRecpNo() {
        return this.recpNo;
    }

    public void setRecpNo(Integer recpNo) {
        this.recpNo = recpNo;
    }

    public JobTitle getRecpJobTitle() {
        return this.recpJobTitle;
    }

    public void setRecpJobTitle(JobTitle recpJobTitle) {
        this.recpJobTitle = recpJobTitle;
    }

    public String getRecpJobDesc() {
        return this.recpJobDesc;
    }

    public void setRecpJobDesc(String recpJobDesc) {
        this.recpJobDesc = recpJobDesc;
    }

    public Date getRecpStartDate() {
        return this.recpStartDate;
    }

    public void setRecpStartDate(Date recpStartDate) {
        this.recpStartDate = recpStartDate;
    }

    public Date getRecpEndDate() {
        return this.recpEndDate;
    }

    public void setRecpEndDate(Date recpEndDate) {
        this.recpEndDate = recpEndDate;
    }

    public Integer getRecpNumberExpect() {
        return this.recpNumberExpect;
    }

    public void setRecpNumberExpect(Integer recpNumberExpect) {
        this.recpNumberExpect = recpNumberExpect;
    }

    public String getRecpDegree() {
        return this.recpDegree;
    }

    public void setRecpDegree(String recpDegree) {
        this.recpDegree = recpDegree;
    }

    public String getRecpLanguageSkill() {
        return this.recpLanguageSkill;
    }

    public void setRecpLanguageSkill(String recpLanguageSkill) {
        this.recpLanguageSkill = recpLanguageSkill;
    }

    public String getRecpJobSkill() {
        return this.recpJobSkill;
    }

    public void setRecpJobSkill(String recpJobSkill) {
        this.recpJobSkill = recpJobSkill;
    }

    public String getRecpComments() {
        return this.recpComments;
    }

    public void setRecpComments(String recpComments) {
        this.recpComments = recpComments;
    }

    public Integer getRecpStatus() {
        return this.recpStatus;
    }

    public void setRecpStatus(Integer recpStatus) {
        this.recpStatus = recpStatus;
    }

    public Date getRecpCreateTime() {
        return this.recpCreateTime;
    }

    public void setRecpCreateTime(Date recpCreateTime) {
        this.recpCreateTime = recpCreateTime;
    }

    public Date getRecpLastChangeTime() {
        return this.recpLastChangeTime;
    }

    public void setRecpLastChangeTime(Date recpLastChangeTime) {
        this.recpLastChangeTime = recpLastChangeTime;
    }

    public Location getRecpWorkLocation() {
        return this.recpWorkLocation;
    }

    public void setRecpWorkLocation(Location recpWorkLocation) {
        this.recpWorkLocation = recpWorkLocation;
    }

    public Employee getRecpLastChangeBy() {
        return this.recpLastChangeBy;
    }

    public void setRecpLastChangeBy(Employee recpLastChangeBy) {
        this.recpLastChangeBy = recpLastChangeBy;
    }

    public Department getRecpDepartmentNo() {
        return this.recpDepartmentNo;
    }

    public void setRecpDepartmentNo(Department recpDepartmentNo) {
        this.recpDepartmentNo = recpDepartmentNo;
    }

    public Employee getRecpCreateBy() {
        return this.recpCreateBy;
    }

    public void setRecpCreateBy(Employee recpCreateBy) {
        this.recpCreateBy = recpCreateBy;
    }

    public Emptype getRecpType() {
        return this.recpType;
    }

    public void setRecpType(Emptype recpType) {
        this.recpType = recpType;
    }

    public Set<Recruitapplier> getRecruitappliers() {
        return this.recruitappliers;
    }

    public void setRecruitappliers(Set<Recruitapplier> recruitappliers) {
        this.recruitappliers = recruitappliers;
    }

    public void addTorecruitappliers(Recruitapplier recruitapplier) {
        if (null == getRecruitappliers())
            setRecruitappliers(new TreeSet());
        getRecruitappliers().add(recruitapplier);
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Recruitplan))
            return false;

        Recruitplan recruitplan = (Recruitplan) obj;
        if ((null == getId()) || (null == recruitplan.getId()))
            return false;
        return getId().equals(recruitplan.getId());
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
 * com.hr.recruitment.domain.base.BaseRecruitplan JD-Core Version: 0.5.4
 */