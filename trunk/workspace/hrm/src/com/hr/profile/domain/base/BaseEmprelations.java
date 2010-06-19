package com.hr.profile.domain.base;

import com.hr.base.BaseDomain;
import com.hr.profile.domain.Employee;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseEmprelations extends BaseDomain implements Serializable {
    public static String REF = "Emprelations";
    public static String PROP_EREL_ERELID = "erelId";
    public static String PROP_EREL_EMPLOYEE = "employee";
    public static String PROP_EREL_ERELRELATIONSHIP = "erelRelationship";
    public static String PROP_EREL_ERELNAME = "erelName";
    public static String PROP_EREL_ERELBIRTHDAY = "erelBirthday";
    public static String PROP_EREL_ERELBIRTHDAYREMIND = "erelBirthdayRemind";
    public static String PROP_EREL_ERELCOMPANY = "erelCompany";
    public static String PROP_EREL_ERELPOSTION = "erelPosition";
    public static String PROP_EREL_ERELCONTACTINFO = "erelContactInfo";
    public static String PROP_EREL_ERELCREATEBY = "erelCreateBy";
    public static String PROP_EREL_ERELCREATEDATE = "erelCreateDate";
    public static String PROP_EREL_ERELLASTCHANGEBY = "erelLastChangeBy";
    public static String PROP_EREL_ERELLASTCHANGETIME = "erelLastChangeTime";
    private String erelId;
    private Employee employee;
    private String erelRelationship;
    private String erelName;
    private Date erelBirthday;
    private Integer erelBirthdayRemind = Integer.valueOf(0);
    private String erelCompany;
    private String erelPosition;
    private String erelContactInfo;
    private String erelCreateBy;
    private Date erelCreateDate;
    private String erelLastChangeBy;
    private Date erelLastChangeTime;

    public BaseEmprelations() {
        initialize();
    }

    protected void initialize() {
    }

    public BaseEmprelations(String erelId) {
        this.erelId = erelId;
        initialize();
    }

    public BaseEmprelations(String erelId, Employee employee, String erelRelationship,
            String erelName, String erelCreateBy, Date erelCreateDate, String erelLastChangeBy,
            Date erelLastChangeTime) {
        this.erelId = erelId;
        this.employee = employee;
        this.erelRelationship = erelRelationship;
        this.erelName = erelName;
        this.erelCreateBy = erelCreateBy;
        this.erelCreateDate = erelCreateDate;
        this.erelLastChangeBy = erelLastChangeBy;
        this.erelLastChangeTime = erelLastChangeTime;
    }

    public BaseEmprelations(String erelId, Employee employee, String erelRelationship,
            String erelName, Date erelBirthday, Integer erelBirthdayRemind, String erelCompany,
            String erelPosition, String erelContactInfo, String erelCreateBy, Date erelCreateDate,
            String erelLastChangeBy, Date erelLastChangeTime) {
        this.erelId = erelId;
        this.employee = employee;
        this.erelRelationship = erelRelationship;
        this.erelName = erelName;
        this.erelBirthday = erelBirthday;
        this.erelBirthdayRemind = erelBirthdayRemind;
        this.erelCompany = erelCompany;
        this.erelPosition = erelPosition;
        this.erelContactInfo = erelContactInfo;
        this.erelCreateBy = erelCreateBy;
        this.erelCreateDate = erelCreateDate;
        this.erelLastChangeBy = erelLastChangeBy;
        this.erelLastChangeTime = erelLastChangeTime;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getErelBirthday() {
        return this.erelBirthday;
    }

    public void setErelBirthday(Date erelBirthday) {
        this.erelBirthday = erelBirthday;
    }

    public Integer getErelBirthdayRemind() {
        return this.erelBirthdayRemind;
    }

    public void setErelBirthdayRemind(Integer erelBirthdayRemind) {
        this.erelBirthdayRemind = erelBirthdayRemind;
    }

    public String getErelCompany() {
        return this.erelCompany;
    }

    public void setErelCompany(String erelCompany) {
        this.erelCompany = erelCompany;
    }

    public String getErelContactInfo() {
        return this.erelContactInfo;
    }

    public void setErelContactInfo(String erelContactInfo) {
        this.erelContactInfo = erelContactInfo;
    }

    public String getErelCreateBy() {
        return this.erelCreateBy;
    }

    public void setErelCreateBy(String erelCreateBy) {
        this.erelCreateBy = erelCreateBy;
    }

    public Date getErelCreateDate() {
        return this.erelCreateDate;
    }

    public void setErelCreateDate(Date erelCreateDate) {
        this.erelCreateDate = erelCreateDate;
    }

    public String getErelId() {
        return this.erelId;
    }

    public void setErelId(String erelId) {
        this.erelId = erelId;
    }

    public String getErelLastChangeBy() {
        return this.erelLastChangeBy;
    }

    public void setErelLastChangeBy(String erelLastChangeBy) {
        this.erelLastChangeBy = erelLastChangeBy;
    }

    public Date getErelLastChangeTime() {
        return this.erelLastChangeTime;
    }

    public void setErelLastChangeTime(Date erelLastChangeTime) {
        this.erelLastChangeTime = erelLastChangeTime;
    }

    public String getErelName() {
        return this.erelName;
    }

    public void setErelName(String erelName) {
        this.erelName = erelName;
    }

    public String getErelPosition() {
        return this.erelPosition;
    }

    public void setErelPosition(String erelPosition) {
        this.erelPosition = erelPosition;
    }

    public String getErelRelationship() {
        return this.erelRelationship;
    }

    public void setErelRelationship(String erelRelationship) {
        this.erelRelationship = erelRelationship;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.domain.base.BaseEmprelations JD-Core Version: 0.5.4
 */