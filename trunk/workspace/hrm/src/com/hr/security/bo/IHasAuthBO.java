package com.hr.security.bo;

import com.hr.base.UsersAuthority;
import java.util.List;
import javax.servlet.jsp.PageContext;

public abstract interface IHasAuthBO {
    public abstract boolean hasActionAuth(UsersAuthority paramUsersAuthority) throws Exception;

    public abstract boolean hasActionAuth(List<UsersAuthority> paramList) throws Exception;

    public abstract boolean hasActionAuth(String paramString) throws Exception;

    public abstract boolean hasDWRAuth(UsersAuthority paramUsersAuthority) throws Exception;

    public abstract boolean hasDWRAuth(List<UsersAuthority> paramList) throws Exception;

    public abstract boolean hasDWRAuth(String paramString) throws Exception;

    public abstract boolean hasJSPAuth(PageContext paramPageContext,
            UsersAuthority paramUsersAuthority) throws Exception;

    public abstract boolean hasJSPAuth(PageContext paramPageContext, List<UsersAuthority> paramList)
            throws Exception;

    public abstract boolean hasJSPAuth(PageContext paramPageContext, String paramString)
            throws Exception;
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.bo.IHasAuthBO JD-Core Version: 0.5.4
 */