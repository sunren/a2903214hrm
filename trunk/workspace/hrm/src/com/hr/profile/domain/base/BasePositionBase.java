package com.hr.profile.domain.base;

import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.JobTitle;
import com.hr.profile.domain.PositionBase;
import java.io.Serializable;

public abstract class BasePositionBase implements Serializable {
    public static String REF = "PositionBase";
    public static String PROP_PB_MAX_EMPLOYEE = "pbMaxEmployee";
    public static String PROP_PB_SUP_ID = "pbSupId";
    public static String PROP_PB_IN_CHARGE = "pbInCharge";
    public static String PROP_PB_SORT_ID = "pbSortId";
    public static String PROP_PB_WORK_TIME = "pbWorkTime";
    public static String PROP_PB_DEPT_ID = "pbDeptId";
    public static String PROP_PB_STATUS = "pbStatus";
    public static String PROP_PB_WORK_ADDRESS = "pbWorkAddress";
    public static String PROP_PB_NAME = "pbName";
    public static String PROP_PB_DESC_ATTACH = "pbDescAttach";
    public static String PROP_PB_MAX_EXCEED = "pbMaxExceed";
    public static String PROP_PB_WORK_TOOL = "pbWorkTool";
    public static String PROP_PB_JOB_TITLE = "pbJobTitle";
    public static String PROP_PB_DESC = "pbDesc";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private String pbName;
    private String pbDesc;
    private String pbDescAttach;
    private Integer pbInCharge;
    private Integer pbMaxEmployee;
    private Integer pbMaxExceed;
    private Integer pbStatus;
    private Integer pbSortId;
    private String pbWorkAddress;
    private String pbWorkTime;
    private String pbWorkTool;
    private JobTitle pbJobTitle;
    private Department pbDeptId;
    private PositionBase pbSupId;

    public BasePositionBase() {
        initialize();
    }

    public BasePositionBase(String id) {
        setId(id);
        initialize();
    }

    public BasePositionBase(String id, String pbName, Integer pbMaxExceed, Integer pbStatus) {
        setId(id);
        setPbName(pbName);
        setPbMaxExceed(pbMaxExceed);
        setPbStatus(pbStatus);
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

    public String getPbName() {
        return this.pbName;
    }

    public void setPbName(String pbName) {
        this.pbName = pbName;
    }

    public String getPbDesc() {
        return this.pbDesc;
    }

    public void setPbDesc(String pbDesc) {
        this.pbDesc = pbDesc;
    }

    public String getPbDescAttach() {
        return this.pbDescAttach;
    }

    public void setPbDescAttach(String pbDescAttach) {
        this.pbDescAttach = pbDescAttach;
    }

    public Integer getPbInCharge() {
        return this.pbInCharge;
    }

    public void setPbInCharge(Integer pbInCharge) {
        this.pbInCharge = pbInCharge;
    }

    public Integer getPbMaxEmployee() {
        return this.pbMaxEmployee;
    }

    public void setPbMaxEmployee(Integer pbMaxEmployee) {
        this.pbMaxEmployee = pbMaxEmployee;
    }

    public Integer getPbMaxExceed() {
        return this.pbMaxExceed;
    }

    public void setPbMaxExceed(Integer pbMaxExceed) {
        this.pbMaxExceed = pbMaxExceed;
    }

    public Integer getPbStatus() {
        return this.pbStatus;
    }

    public void setPbStatus(Integer pbStatus) {
        this.pbStatus = pbStatus;
    }

    public Integer getPbSortId() {
        return this.pbSortId;
    }

    public void setPbSortId(Integer pbSortId) {
        this.pbSortId = pbSortId;
    }

    public String getPbWorkAddress() {
        return this.pbWorkAddress;
    }

    public void setPbWorkAddress(String pbWorkAddress) {
        this.pbWorkAddress = pbWorkAddress;
    }

    public String getPbWorkTime() {
        return this.pbWorkTime;
    }

    public void setPbWorkTime(String pbWorkTime) {
        this.pbWorkTime = pbWorkTime;
    }

    public String getPbWorkTool() {
        return this.pbWorkTool;
    }

    public void setPbWorkTool(String pbWorkTool) {
        this.pbWorkTool = pbWorkTool;
    }

    public JobTitle getPbJobTitle() {
        return this.pbJobTitle;
    }

    public void setPbJobTitle(JobTitle pbJobTitle) {
        this.pbJobTitle = pbJobTitle;
    }

    public Department getPbDeptId() {
        return this.pbDeptId;
    }

    public void setPbDeptId(Department pbDeptId) {
        this.pbDeptId = pbDeptId;
    }

    public PositionBase getPbSupId() {
        return this.pbSupId;
    }

    public void setPbSupId(PositionBase pbSupId) {
        this.pbSupId = pbSupId;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof PositionBase))
            return false;

        PositionBase positionBase = (PositionBase) obj;
        if ((null == getId()) || (null == positionBase.getId()))
            return false;
        return getId().equals(positionBase.getId());
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
 * com.hr.profile.domain.base.BasePositionBase JD-Core Version: 0.5.4
 */