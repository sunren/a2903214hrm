package com.hr.training.domain.base;

import com.hr.base.BaseAction;
import com.hr.base.BaseDomain;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.profile.domain.Employee;
import com.hr.training.domain.Trcourse;
import com.hr.training.domain.Trcourseplan;
import com.hr.training.domain.Tremployeeplan;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public abstract class BaseTrcourseplan extends BaseDomain implements Serializable {
    public static String REF = "Trcourseplan";
    public static String PROP_TRCP_END_DATE = "trcpEndDate";
    public static String PROP_TRCP_LAST_CHANGE_BY = "trcpLastChangeBy";
    public static String PROP_TRCP_COMMENTS = "trcpComments";
    public static String PROP_TRCP_CREATE_BY = "trcpCreateBy";
    public static String PROP_TRCP_COURSE_NO = "trcpCourseNo";
    public static String PROP_TRCP_BUDGET_YEAR = "trcpBudgetYear";
    public static String PROP_TRCP_FILE_NAME = "trcpFileName";
    public static String PROP_TRCP_STATUS = "trcpStatus";
    public static String PROP_TRCP_ENROLL_START_DATE = "trcpEnrollStartDate";
    public static String PROP_TRCP_ID = "trcpId";
    public static String PROP_TRCP_START_DATE = "trcpStartDate";
    public static String PROP_TRCP_ATTENDEE_NO = "trcpAttendeeNo";
    public static String PROP_TRCP_LAST_CHANGE_TIME = "trcpLastChangeTime";
    public static String PROP_TRCP_LOCATION = "trcpLocation";
    public static String PROP_TRCP_TEACHER = "trcpTeacher";
    public static String PROP_TRCP_INSTITUTION = "trcpInstitution";
    public static String PROP_TRCP_ENROLL_END_DATE = "trcpEnrollEndDate";
    public static String PROP_TRCP_CREATE_TIME = "trcpCreateTime";
    public static String PROP_TRCP_BUDGET_FEE = "trcpBudgetFee";
    public static String PROP_TRCP_COORDINATOR = "trcpCoordinator";
    public static String PROP_TRCP_DEPT_LIMIT = "trcpDeptLimit";
    public static String PROP_TRCP_BUDGET_HOUR = "trcpBudgetHour";

    private int hashCode = -2147483648;
    private String trcpId;
    private String trcpBudgetYear;
    private BigDecimal trcpBudgetFee;
    private BigDecimal trcpBudgetHour;
    private Integer trcpAttendeeNo;
    private String trcpDeptLimit;
    private String trcpTeacher;
    private String trcpLocation;
    private String trcpInstitution;
    private Date trcpStartDate;
    private Date trcpEndDate;
    private Date trcpEnrollStartDate;
    private Date trcpEnrollEndDate;
    private Integer trcpStatus;
    private String trcpComments;
    private String trcpFileName;
    private Date trcpCreateTime;
    private Date trcpLastChangeTime;
    private Employee trcpCreateBy;
    private Employee trcpCoordinator;
    private Trcourse trcpCourseNo;
    private Employee trcpLastChangeBy;
    private List deptList;
    private Set<Tremployeeplan> tremployeeplans;

    public BaseTrcourseplan() {
        initialize();
    }

    public BaseTrcourseplan(String trcpId) {
        setTrcpId(trcpId);
        initialize();
    }

    public BaseTrcourseplan(String trcpId, Employee trcpCreateBy, Trcourse trcpCourseNo,
            Employee trcpLastChangeBy, BigDecimal trcpBudgetHour, Date trcpStartDate,
            Date trcpEndDate, Integer trcpStatus, Date trcpCreateTime, Date trcpLastChangeTime) {
        setTrcpId(trcpId);
        setTrcpCreateBy(trcpCreateBy);
        setTrcpCourseNo(trcpCourseNo);
        setTrcpLastChangeBy(trcpLastChangeBy);
        setTrcpBudgetHour(trcpBudgetHour);
        setTrcpStartDate(trcpStartDate);
        setTrcpEndDate(trcpEndDate);
        setTrcpStatus(trcpStatus);
        setTrcpCreateTime(trcpCreateTime);
        setTrcpLastChangeTime(trcpLastChangeTime);
        initialize();
    }

    protected void initialize() {
    }

    public String getTrcpId() {
        return this.trcpId;
    }

    public void setTrcpId(String trcpId) {
        this.trcpId = trcpId;
        this.hashCode = -2147483648;
    }

    public String getTrcpBudgetYear() {
        return this.trcpBudgetYear;
    }

    public void setTrcpBudgetYear(String trcpBudgetYear) {
        this.trcpBudgetYear = trcpBudgetYear;
    }

    public BigDecimal getTrcpBudgetFee() {
        return this.trcpBudgetFee;
    }

    public void setTrcpBudgetFee(BigDecimal trcpBudgetFee) {
        this.trcpBudgetFee = trcpBudgetFee;
    }

    public BigDecimal getTrcpBudgetHour() {
        return this.trcpBudgetHour;
    }

    public void setTrcpBudgetHour(BigDecimal trcpBudgetHour) {
        this.trcpBudgetHour = trcpBudgetHour;
    }

    public Integer getTrcpAttendeeNo() {
        return this.trcpAttendeeNo;
    }

    public void setTrcpAttendeeNo(Integer trcpAttendeeNo) {
        this.trcpAttendeeNo = trcpAttendeeNo;
    }

    public String getTrcpDeptLimit() {
        return this.trcpDeptLimit;
    }

    public void setTrcpDeptLimit(String trcpDeptLimit) {
        this.trcpDeptLimit = trcpDeptLimit;
    }

    public String getTrcpTeacher() {
        return this.trcpTeacher;
    }

    public void setTrcpTeacher(String trcpTeacher) {
        this.trcpTeacher = trcpTeacher;
    }

    public String getTrcpLocation() {
        return this.trcpLocation;
    }

    public void setTrcpLocation(String trcpLocation) {
        this.trcpLocation = trcpLocation;
    }

    public String getTrcpInstitution() {
        return this.trcpInstitution;
    }

    public void setTrcpInstitution(String trcpInstitution) {
        this.trcpInstitution = trcpInstitution;
    }

    public Date getTrcpStartDate() {
        return this.trcpStartDate;
    }

    public void setTrcpStartDate(Date trcpStartDate) {
        this.trcpStartDate = trcpStartDate;
    }

    public Date getTrcpEndDate() {
        return this.trcpEndDate;
    }

    public void setTrcpEndDate(Date trcpEndDate) {
        this.trcpEndDate = trcpEndDate;
    }

    public Date getTrcpEnrollStartDate() {
        return this.trcpEnrollStartDate;
    }

    public void setTrcpEnrollStartDate(Date trcpEnrollStartDate) {
        this.trcpEnrollStartDate = trcpEnrollStartDate;
    }

    public Date getTrcpEnrollEndDate() {
        return this.trcpEnrollEndDate;
    }

    public void setTrcpEnrollEndDate(Date trcpEnrollEndDate) {
        this.trcpEnrollEndDate = trcpEnrollEndDate;
    }

    public Integer getTrcpStatus() {
        return this.trcpStatus;
    }

    public void setTrcpStatus(Integer trcpStatus) {
        this.trcpStatus = trcpStatus;
    }

    public String getTrcpComments() {
        return this.trcpComments;
    }

    public void setTrcpComments(String trcpComments) {
        this.trcpComments = trcpComments;
    }

    public String getTrcpFileName() {
        return this.trcpFileName;
    }

    public void setTrcpFileName(String trcpFileName) {
        this.trcpFileName = trcpFileName;
    }

    public Date getTrcpCreateTime() {
        return this.trcpCreateTime;
    }

    public void setTrcpCreateTime(Date trcpCreateTime) {
        this.trcpCreateTime = trcpCreateTime;
    }

    public Date getTrcpLastChangeTime() {
        return this.trcpLastChangeTime;
    }

    public void setTrcpLastChangeTime(Date trcpLastChangeTime) {
        this.trcpLastChangeTime = trcpLastChangeTime;
    }

    public Employee getTrcpCreateBy() {
        return this.trcpCreateBy;
    }

    public void setTrcpCreateBy(Employee trcpCreateBy) {
        this.trcpCreateBy = trcpCreateBy;
    }

    public Employee getTrcpCoordinator() {
        return this.trcpCoordinator;
    }

    public void setTrcpCoordinator(Employee trcpCoordinator) {
        this.trcpCoordinator = trcpCoordinator;
    }

    public Trcourse getTrcpCourseNo() {
        return this.trcpCourseNo;
    }

    public void setTrcpCourseNo(Trcourse trcpCourseNo) {
        this.trcpCourseNo = trcpCourseNo;
    }

    public Employee getTrcpLastChangeBy() {
        return this.trcpLastChangeBy;
    }

    public void setTrcpLastChangeBy(Employee trcpLastChangeBy) {
        this.trcpLastChangeBy = trcpLastChangeBy;
    }

    public Set<Tremployeeplan> getTremployeeplans() {
        return this.tremployeeplans;
    }

    public void setTremployeeplans(Set<Tremployeeplan> tremployeeplans) {
        this.tremployeeplans = tremployeeplans;
    }

    public void addTotremployeeplans(Tremployeeplan tremployeeplan) {
        if (null == getTremployeeplans())
            setTremployeeplans(new TreeSet());
        getTremployeeplans().add(tremployeeplan);
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Trcourseplan)) {
            return false;
        }
        Trcourseplan trcourseplan = (Trcourseplan) obj;
        if ((null == getTrcpId()) || (null == trcourseplan.getTrcpId())) {
            return false;
        }
        return getTrcpId().equals(trcourseplan.getTrcpId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getTrcpId()) {
                return super.hashCode();
            }
            String hashStr = super.getClass().getName() + ":" + getTrcpId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }

    public List getDeptList() {
        IDepartmentBO departmentBO = (IDepartmentBO) BaseAction.getBean("departmentBO");
        if (getTrcpDeptLimit() == null) {
            this.deptList = new ArrayList();
        } else {
            String[] deptNos = getTrcpDeptLimit().split(", ");
            this.deptList = departmentBO.getDepartmentsByNos(deptNos);
        }
        return this.deptList;
    }

    public String getDeptNames() {
        String result = getTrcpDeptLimit();
        if (result == null)
            result = "";
        result = result.trim();
        if (!result.equals("")) {
            if (result.charAt(0) == ',')
                result = result.substring(1, result.length());
            if ((!result.equals("")) && (result.charAt(result.length() - 1) == ','))
                result = result.substring(0, result.length() - 1);
        }
        return result;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.training.domain.base.BaseTrcourseplan JD-Core Version: 0.5.4
 */