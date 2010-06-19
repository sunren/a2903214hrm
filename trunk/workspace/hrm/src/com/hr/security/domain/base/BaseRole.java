package com.hr.security.domain.base;

import com.hr.base.BaseDomain;
import com.hr.security.domain.Role;
import java.io.Serializable;

public abstract class BaseRole extends BaseDomain implements Serializable {
    public static String REF = "Role";
    public static String PROP_ROLE_NAME = "roleName";
    public static String PROP_ROLE_NO = "roleNo";
    public static String PROP_ROLE_AUTHORITY = "roleAuthority";
    public static String PROP_ROLE_DESC = "roleDesc";
    public static String PROP_ID = "id";
    public static String PROP_ROLE_SORT_ID = "roleSortId";

    private int hashCode = -2147483648;
    private String id;
    private int roleNo;
    private String roleName;
    private String roleDesc;
    private Integer roleSortId;
    private String roleAuthority;

    public BaseRole() {
        initialize();
    }

    public BaseRole(String id) {
        setId(id);
        initialize();
    }

    public BaseRole(String id, String roleName, Integer roleSortId, String roleAuthority) {
        setId(id);
        setRoleName(roleName);
        setRoleSortId(roleSortId);
        setRoleAuthority(roleAuthority);
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

    public int getRoleNo() {
        return this.roleNo;
    }

    public void setRoleNo(int roleNo) {
        this.roleNo = roleNo;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return this.roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Integer getRoleSortId() {
        return this.roleSortId;
    }

    public void setRoleSortId(Integer roleSortId) {
        this.roleSortId = roleSortId;
    }

    public String getRoleAuthority() {
        return this.roleAuthority;
    }

    public void setRoleAuthority(String roleAuthority) {
        this.roleAuthority = roleAuthority;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Role))
            return false;

        Role role = (Role) obj;
        if ((null == getId()) || (null == role.getId()))
            return false;
        return getId().equals(role.getId());
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
 * com.hr.security.domain.base.BaseRole JD-Core Version: 0.5.4
 */