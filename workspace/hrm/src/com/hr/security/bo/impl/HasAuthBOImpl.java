package com.hr.security.bo.impl;

import com.hr.base.Constants;
import com.hr.base.UsersAuthority;
import com.hr.security.bo.IHasAuthBO;
import com.opensymphony.xwork2.ActionContext;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

public class HasAuthBOImpl implements IHasAuthBO, Constants {
    private synchronized HashMap<UsersAuthority, String> initAction() throws Exception {
        return (HashMap) ActionContext.getContext().getSession().get("authList");
    }

    private synchronized HashMap<UsersAuthority, String> initDWR() throws Exception {
        return (HashMap) WebContextFactory.get().getSession().getAttribute("authList");
    }

    private synchronized HashMap<UsersAuthority, String> initJSP(PageContext pageContext)
            throws Exception {
        return (HashMap) pageContext.getSession().getAttribute("authList");
    }

    public boolean hasActionAuth(UsersAuthority inputUsersAuthority) throws Exception {
        HashMap usrAuthList = initAction();
        return (usrAuthList == null) ? false : usrAuthList.containsKey(inputUsersAuthority);
    }

    public boolean hasActionAuth(List<UsersAuthority> inputUsersAuthoritySet) throws Exception {
        HashMap usrAuthList = initAction();
        if (usrAuthList == null) {
            return false;
        }
        Iterator inputUsersAuthorityIterator = inputUsersAuthoritySet.iterator();
        while (inputUsersAuthorityIterator.hasNext()) {
            if (usrAuthList.containsKey(inputUsersAuthorityIterator.next())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasActionAuth(String inputPrefix) throws Exception {
        HashMap usrAuthList = initAction();
        return (usrAuthList == null) ? false : usrAuthList.containsValue(inputPrefix);
    }

    public boolean hasDWRAuth(UsersAuthority inputUsersAuthority) throws Exception {
        HashMap usrAuthList = initDWR();
        return (usrAuthList == null) ? false : usrAuthList.containsKey(inputUsersAuthority);
    }

    public boolean hasDWRAuth(List<UsersAuthority> inputUsersAuthoritySet) throws Exception {
        HashMap usrAuthList = initDWR();
        if (usrAuthList == null) {
            return false;
        }
        Iterator inputUsersAuthorityIterator = inputUsersAuthoritySet.iterator();
        while (inputUsersAuthorityIterator.hasNext()) {
            if (usrAuthList.containsKey(inputUsersAuthorityIterator.next())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasDWRAuth(String inputPrefix) throws Exception {
        HashMap usrAuthList = initDWR();
        return (usrAuthList == null) ? false : usrAuthList.containsValue(inputPrefix);
    }

    public boolean hasJSPAuth(PageContext pageContext, UsersAuthority inputUsersAuthority)
            throws Exception {
        HashMap<UsersAuthority, String> usrAuthList = initJSP(pageContext);
        return (usrAuthList == null) ? false : usrAuthList.containsKey(inputUsersAuthority);
    }

    public boolean hasJSPAuth(PageContext pageContext, List<UsersAuthority> inputUsersAuthoritySet)
            throws Exception {
        HashMap usrAuthList = initJSP(pageContext);
        if (usrAuthList == null) {
            return false;
        }
        Iterator inputUsersAuthorityIterator = inputUsersAuthoritySet.iterator();
        while (inputUsersAuthorityIterator.hasNext()) {
            if (usrAuthList.containsKey(inputUsersAuthorityIterator.next())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasJSPAuth(PageContext pageContext, String inputPrefix) throws Exception {
        HashMap usrAuthList = initJSP(pageContext);
        return (usrAuthList == null) ? false : usrAuthList.containsValue(inputPrefix);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.bo.impl.HasAuthBOImpl JD-Core Version: 0.5.4
 */