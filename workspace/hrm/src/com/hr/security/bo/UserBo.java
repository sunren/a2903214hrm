package com.hr.security.bo;

import com.hr.security.domain.Userinfo;
import com.hr.util.Pager;
import java.util.List;

public abstract interface UserBo {
    public abstract boolean addUser(Userinfo paramUserinfo, Integer[] paramArrayOfInteger,
            String paramString);

    public abstract boolean delUser(String paramString);

    public abstract String updateUser(Userinfo paramUserinfo, Integer[] paramArrayOfInteger,
            String paramString);

    public abstract List<Userinfo> listUser();

    public abstract Userinfo getUserByName(String paramString);

    public abstract Userinfo getUserById(String paramString);

    public abstract String doLogin(Userinfo paramUserinfo, String paramString1,
            String paramString2, String paramString3);

    public abstract boolean updatePassword(String paramString1, String paramString2);

    public abstract boolean updatePasswordBy(String paramString1, String paramString2,
            String paramString3, String paramString4);

    public abstract boolean updateLimit(Userinfo paramUserinfo);

    public abstract boolean updateStatus(String paramString1, int paramInt, String paramString2);

    public abstract List getObjects(Class paramClass);

    public abstract List searchUserList(String paramString1, Userinfo paramUserinfo,
            String paramString2, String paramString3, String paramString4, Pager paramPager);

    public abstract int getAdminCount(String paramString);

    public abstract boolean checkAuthModule(Userinfo paramUserinfo, String paramString);

    public abstract boolean checkAuthModule(Userinfo paramUserinfo, String paramString1,
            String paramString2);

    public abstract List searchAuthByEmpNo(String paramString);

    public abstract List getEmpNoByDepAuth(String paramString1, String paramString2);

    public abstract List getEmpNoByAuth(String paramString);

    public abstract String getAuthoritysByRole(String paramString);

    public abstract String updateUserAuth(Userinfo paramUserinfo, String paramString);

    public abstract boolean isRoleNoHasAuth(Integer[] paramArrayOfInteger, String paramString);

    public abstract String updateSignFree(String paramString);

    public abstract List<Userinfo> getUserByIds(String[] paramArrayOfString);

    public abstract List<Userinfo> getEmpByAuth(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.bo.UserBo JD-Core Version: 0.5.4
 */