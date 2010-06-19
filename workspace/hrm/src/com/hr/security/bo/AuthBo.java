package com.hr.security.bo;

import com.hr.security.domain.Authority;
import java.util.List;

public abstract interface AuthBo {
    public abstract List getAuthList();

    public abstract Authority findAuthById(int paramInt);

    public abstract List<Authority> findAuthByIdList(List paramList);

    public abstract boolean updateAuth(Authority paramAuthority, Integer[] paramArrayOfInteger);

    public abstract List<Authority> getAuthList(String paramString);

    public abstract Authority getAuthorityId(String paramString1, String paramString2);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.bo.AuthBo JD-Core Version: 0.5.4
 */