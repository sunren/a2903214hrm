package com.hr.security.bo;

import com.hr.security.domain.AuthorityPos;
import java.util.List;

public abstract interface AuthorityPosBo {
    public abstract String delAuthPos(String paramString);

    public abstract AuthorityPos getAuthPosById(String paramString);

    public abstract boolean addAuthPos(AuthorityPos paramAuthorityPos);

    public abstract boolean updateAuthPos(AuthorityPos paramAuthorityPos);

    public abstract AuthorityPos getAuthPosByDept(String paramString1, String paramString2);

    public abstract AuthorityPos getAuthPosByLoc(String paramString1, String paramString2);

    public abstract List<AuthorityPos> getAuthPosInfoList(String paramString1, String paramString2);

    public abstract List<AuthorityPos> getAuthPosList(String paramString1, String paramString2);

    public abstract String[][] getDeptAndLocInCharge(String paramString, Integer paramInteger);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.bo.AuthorityPosBo JD-Core Version: 0.5.4
 */