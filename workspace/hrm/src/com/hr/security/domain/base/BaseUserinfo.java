package com.hr.security.domain.base;

import com.hr.base.BaseDomain;
import com.hr.profile.domain.Employee;
import com.hr.security.domain.Userinfo;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseUserinfo extends BaseDomain implements Serializable {
    public static String REF = "Userinfo";
    public static String PROP_UI_CREATE_BY = "uiCreateBy";
    public static String PROP_UI_LAST_LOGIN_IP = "uiLastLoginIp";
    public static String PROP_UI_LAST_CHANGE_BY = "uiLastChangeBy";
    public static String PROP_EMPLOYEE = "employee";
    public static String PROP_UI_STATUS = "uiStatus";
    public static String PROP_UI_TIME_RESTRICT = "uiTimeRestrict";
    public static String PROP_UI_CURRENT_LOGIN_TIME = "uiCurrentLoginTime";
    public static String PROP_UI_LAST_LOGIN_TIME = "uiLastLoginTime";
    public static String PROP_UI_CREATE_TIME = "uiCreateTime";
    public static String PROP_UI_LAST_CHANGE_TIME = "uiLastChangeTime";
    public static String PROP_UI_PASSWORD = "uiPassword";
    public static String PROP_UI_ROLE = "uiRole";
    public static String PROP_UI_LEVEL_RESTRICT = "uiLevelRestrict";
    public static String PROP_UI_USERNAME = "uiUsername";
    public static String PROP_UI_AUTH = "uiAuth";
    public static String PROP_UI_MAC_RESTRICT = "uiMacRestrict";
    public static String PROP_ID = "id";
    public static String PROP_UI_IP_RESTRICT = "uiIpRestrict";

    private int hashCode = -2147483648;
    private String id;
    private String uiUsername;
    private String uiPassword;
    private String uiRole;
    private String uiAuth;
    private Integer uiStatus;
    private String uiLastLoginIp;
    private String uiMacRestrict;
    private String uiIpRestrict;
    private String uiTimeRestrict;
    private Integer uiLevelRestrict;
    private Date uiCurrentLoginTime;
    private Date uiLastLoginTime;
    private Date uiCreateTime;
    private Date uiLastChangeTime;
    private Employee employee;
    private Employee uiCreateBy;
    private Employee uiLastChangeBy;

    public BaseUserinfo() {
        initialize();
    }

    public BaseUserinfo(String id) {
        setId(id);
        initialize();
    }

    public BaseUserinfo(String id, Employee uiCreateBy, Employee uiLastChangeBy, String uiUsername,
            String uiPassword, String uiRole, String uiAuth, Integer uiStatus, Date uiCreateTime,
            Date uiLastChangeTime) {
        setId(id);
        setUiCreateBy(uiCreateBy);
        setUiLastChangeBy(uiLastChangeBy);
        setUiUsername(uiUsername);
        setUiPassword(uiPassword);
        setUiRole(uiRole);
        setUiAuth(uiAuth);
        setUiStatus(uiStatus);
        setUiCreateTime(uiCreateTime);
        setUiLastChangeTime(uiLastChangeTime);
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

    public String getUiUsername() {
        return this.uiUsername;
    }

    public void setUiUsername(String uiUsername) {
        this.uiUsername = uiUsername;
    }

    public String getUiPassword() {
        return this.uiPassword;
    }

    public void setUiPassword(String uiPassword) {
        this.uiPassword = uiPassword;
    }

    public String getUiRole() {
        return this.uiRole;
    }

    public void setUiRole(String uiRole) {
        this.uiRole = uiRole;
    }

    public String getUiAuth() {
        return this.uiAuth;
    }

    public void setUiAuth(String uiAuth) {
        this.uiAuth = uiAuth;
    }

    public Integer getUiStatus() {
        return this.uiStatus;
    }

    public void setUiStatus(Integer uiStatus) {
        this.uiStatus = uiStatus;
    }

    public String getUiLastLoginIp() {
        return this.uiLastLoginIp;
    }

    public void setUiLastLoginIp(String uiLastLoginIp) {
        this.uiLastLoginIp = uiLastLoginIp;
    }

    public String getUiMacRestrict() {
        return this.uiMacRestrict;
    }

    public void setUiMacRestrict(String uiMacRestrict) {
        this.uiMacRestrict = uiMacRestrict;
    }

    public String getUiIpRestrict() {
        return this.uiIpRestrict;
    }

    public void setUiIpRestrict(String uiIpRestrict) {
        this.uiIpRestrict = uiIpRestrict;
    }

    public String getUiTimeRestrict() {
        return this.uiTimeRestrict;
    }

    public void setUiTimeRestrict(String uiTimeRestrict) {
        this.uiTimeRestrict = uiTimeRestrict;
    }

    public Integer getUiLevelRestrict() {
        return this.uiLevelRestrict;
    }

    public void setUiLevelRestrict(Integer uiLevelRestrict) {
        this.uiLevelRestrict = uiLevelRestrict;
    }

    public Date getUiCurrentLoginTime() {
        return this.uiCurrentLoginTime;
    }

    public void setUiCurrentLoginTime(Date uiCurrentLoginTime) {
        this.uiCurrentLoginTime = uiCurrentLoginTime;
    }

    public Date getUiLastLoginTime() {
        return this.uiLastLoginTime;
    }

    public void setUiLastLoginTime(Date uiLastLoginTime) {
        this.uiLastLoginTime = uiLastLoginTime;
    }

    public Date getUiCreateTime() {
        return this.uiCreateTime;
    }

    public void setUiCreateTime(Date uiCreateTime) {
        this.uiCreateTime = uiCreateTime;
    }

    public Date getUiLastChangeTime() {
        return this.uiLastChangeTime;
    }

    public void setUiLastChangeTime(Date uiLastChangeTime) {
        this.uiLastChangeTime = uiLastChangeTime;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getUiCreateBy() {
        return this.uiCreateBy;
    }

    public void setUiCreateBy(Employee uiCreateBy) {
        this.uiCreateBy = uiCreateBy;
    }

    public Employee getUiLastChangeBy() {
        return this.uiLastChangeBy;
    }

    public void setUiLastChangeBy(Employee uiLastChangeBy) {
        this.uiLastChangeBy = uiLastChangeBy;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Userinfo))
            return false;

        Userinfo userinfo = (Userinfo) obj;
        if ((null == getId()) || (null == userinfo.getId()))
            return false;
        return getId().equals(userinfo.getId());
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
 * com.hr.security.domain.base.BaseUserinfo JD-Core Version: 0.5.4
 */