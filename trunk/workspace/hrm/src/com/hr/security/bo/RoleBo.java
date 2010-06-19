package com.hr.security.bo;

import com.hr.security.domain.Role;
import java.util.List;

public abstract interface RoleBo {
    public abstract List getRoleList();

    public abstract List<Role> getRoleList(Integer[] paramArrayOfInteger, String paramString);

    public abstract Role getRole(String paramString);

    public abstract boolean addRole(Role paramRole);

    public abstract String addRole(Role paramRole, Integer[] paramArrayOfInteger);

    public abstract String delRole(Integer paramInteger, UserBo paramUserBo);

    public abstract String updateRole(Role paramRole, Integer[] paramArrayOfInteger,
            UserBo paramUserBo);

    public abstract boolean findRoleByName(String paramString);

    public abstract boolean findRoleBySortid(int paramInt);

    public abstract List getAllAuthoritys(List<Integer> paramList);

    public abstract boolean isHasAuth(String paramString, Integer paramInteger);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.bo.RoleBo JD-Core Version: 0.5.4
 */