package com.hr.security.domain;

import com.hr.base.DaoBean;
import com.hr.profile.domain.Employee;
import com.hr.util.MyTools;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.axis.utils.StringUtils;

public class Userinfo extends DaoBean {
    private static final long serialVersionUID = 1L;
    public static String REF = "Userinfo";

    public static String PROP_UI_CREATE_BY = "uiCreateBy";

    public static String PROP_UI_LAST_LOGIN_IP = "uiLastLoginIp";

    public static String PROP_UI_ROLE = "uiRole";

    public static String PROP_UI_AUTH = "uiAuth";

    public static String PROP_UI_USERNAME = "uiUsername";

    public static String PROP_UI_LAST_CHANGE_BY = "uiLastChangeBy";

    public static String PROP_UI_STATUS = "uiStatus";

    public static String PROP_UI_CREATE_TIME = "uiCreateTime";

    public static String PROP_UI_LAST_LOGIN_TIME = "uiLastLoginTime";

    public static String PROP_UI_LAST_CHANGE_TIME = "uiLastChangeTime";

    public static String PROP_UI_LEVEL_RESTRICT = "uiLevelRestrict";

    public static String PROP_UI_MAC_RESTRICT = "uiMacRestrict";

    public static String PROP_UI_IP_RESTRICT = "uiIpRestrict";

    public static String PROP_UI_TIME_RESTRICT = "uiTimeRestrict";

    public static String PROP_UI_PASSWORD = "uiPassword";

    public static String PROP_UI_IS_GM_HR = "uiIsGmHr";

    public static String PROP_UI_REFERENCE_ID = "uiReferenceId";

    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private String uiUsername;
    private String uiPassword;
    private String uiRole;
    private String uiAuth;
    private Integer uiStatus;
    private String uiLastLoginIp;
    private Date uiLastLoginTime;
    private Date uiCurrentLoginTime;
    private Date uiCreateTime;
    private Date uiLastChangeTime;
    private String uiMacRestrict;
    private String uiIpRestrict;
    private String uiTimeRestrict;
    private String uiReferenceId;
    private Integer uiLevelRestrict;
    private String uiIsGmHr;
    private Employee uiCreateBy;
    private Employee uiLastChangeBy;
    private Employee employee;

    public Userinfo() {
    }

    public Userinfo(String id) {
        this.id = id;
    }

    public void setUiPasswordEncrypt(String uiPassword) {
        this.uiPassword = MyTools.vigenere(uiPassword, MyTools.getUpKey(getId(), MyTools.STRING),
                                           MyTools.ENCRYPT_MODE);
    }

    public void setUiRoleEncrypt(String uiRole) {
        this.uiRole = MyTools.vigenere(uiRole, MyTools.getUpKey(getId(), MyTools.STRING),
                                       MyTools.ENCRYPT_MODE);
    }

    public void setUiAuthEncrypt(String uiAuth) {
        this.uiAuth = MyTools.vigenere(uiAuth, MyTools.getUpKey(getId(), MyTools.STRING),
                                       MyTools.ENCRYPT_MODE);
    }

    public void setUiIsGmHrCalc(String uiAuth) {
        String[] auths = uiAuth.split(",");
        if ((auths != null) && (auths.length == 0)) {
            setUiIsGmHr(null);
            return;
        }

        Set authSet = new HashSet();
        for (int i = 0; i < auths.length; ++i) {
            authSet.add(auths[i]);
        }

        StringBuffer newFlag = new StringBuffer();
        if ((authSet.contains("1")) || (authSet.contains("2")))
            newFlag.append("H1,");
        if (authSet.contains("5"))
            newFlag.append("G1,");
        if ((authSet.contains("11")) || (authSet.contains("12")))
            newFlag.append("H2,");
        if (authSet.contains("15"))
            newFlag.append("G2,");
        if ((authSet.contains("21")) || (authSet.contains("22")))
            newFlag.append("H3,");
        if (authSet.contains("25"))
            newFlag.append("G3,");
        if ((authSet.contains("31")) || (authSet.contains("32")))
            newFlag.append("H4,");
        if (authSet.contains("35"))
            newFlag.append("G4,");

        if ((authSet.contains("51")) || (authSet.contains("52")))
            newFlag.append("H6,");
        if (authSet.contains("55"))
            newFlag.append("G6,");

        String flagString = newFlag.toString();
        if (!StringUtils.isEmpty(flagString)) {
            flagString = flagString.substring(0, flagString.length() - 1);
            setUiIsGmHr(flagString);
            return;
        }

        setUiIsGmHr(null);
    }

    public String getUiPasswordDecrypt() {
        return MyTools.vigenere(this.uiPassword, MyTools.getUpKey(getId(), MyTools.STRING),
                                MyTools.DECRYPT_MODE);
    }

    public String getUiRoleDecrypt() {
        return MyTools.vigenere(this.uiRole, MyTools.getUpKey(getId(), MyTools.STRING),
                                MyTools.DECRYPT_MODE);
    }

    public String getUiAuthDecrypt() {
        return MyTools.vigenere(this.uiAuth, MyTools.getUpKey(getId(), MyTools.STRING),
                                MyTools.DECRYPT_MODE);
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

    public String toString() {
        return super.toString();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<String> getUiRoleList() {
        String uiRole = getUiRoleDecrypt();
        if (uiRole == null)
            return null;
        String[] uiRoleArray = uiRole.split(",");
        List roleList = new ArrayList();
        int length = uiRoleArray.length;
        for (int i = 0; i < length; ++i)
            roleList.add(uiRoleArray[i].trim());
        if (roleList.size() < 1)
            return null;
        return roleList;
    }

    public List<String> getUiAuthList() {
        String uiAuth = getUiAuthDecrypt();
        if (uiAuth == null)
            return null;
        String[] uiAuthArray = uiAuth.split(",");
        List uiAuthList = new ArrayList();
        int length = uiAuthArray.length;
        for (int i = 0; i < length; ++i)
            uiAuthList.add(uiAuthArray[i].trim());
        if (uiAuthList.size() < 1)
            return null;
        return uiAuthList;
    }

    public List<Integer> getUiAuthIntegerList() {
        String uiAuth = getUiAuthDecrypt();
        if (uiAuth == null)
            return null;
        String[] uiAuthArray = uiAuth.split(",");
        List uiAuthList = new ArrayList();
        int length = uiAuthArray.length;
        for (int i = 0; i < length; ++i)
            uiAuthList.add(Integer.valueOf(Integer.parseInt(uiAuthArray[i].trim())));
        if (uiAuthList.size() < 1)
            return null;
        return uiAuthList;
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

    public String getUiAuth() {
        return this.uiAuth;
    }

    public void setUiAuth(String uiAuth) {
        this.uiAuth = uiAuth;
    }

    public String getUiRole() {
        return this.uiRole;
    }

    public void setUiRole(String uiRole) {
        this.uiRole = uiRole;
    }

    public Date getUiCurrentLoginTime() {
        return this.uiCurrentLoginTime;
    }

    public void setUiCurrentLoginTime(Date uiCurrentLoginTime) {
        this.uiCurrentLoginTime = uiCurrentLoginTime;
    }

    public String getUiIpRestrict() {
        return this.uiIpRestrict;
    }

    public void setUiIpRestrict(String uiIpRestrict) {
        this.uiIpRestrict = uiIpRestrict;
    }

    public Integer getUiLevelRestrict() {
        return this.uiLevelRestrict;
    }

    public void setUiLevelRestrict(Integer uiLevelRestrict) {
        this.uiLevelRestrict = uiLevelRestrict;
    }

    public String getUiMacRestrict() {
        return this.uiMacRestrict;
    }

    public void setUiMacRestrict(String uiMacRestrict) {
        this.uiMacRestrict = uiMacRestrict;
    }

    public String getUiTimeRestrict() {
        return this.uiTimeRestrict;
    }

    public void setUiTimeRestrict(String uiTimeRestrict) {
        this.uiTimeRestrict = uiTimeRestrict;
    }

    public String getUiIsGmHr() {
        return this.uiIsGmHr;
    }

    public void setUiIsGmHr(String uiIsGmHr) {
        this.uiIsGmHr = uiIsGmHr;
    }

    public String getUiReferenceId() {
        return this.uiReferenceId;
    }

    public void setUiReferenceId(String uiReferenceId) {
        this.uiReferenceId = uiReferenceId;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.domain.Userinfo JD-Core Version: 0.5.4
 */