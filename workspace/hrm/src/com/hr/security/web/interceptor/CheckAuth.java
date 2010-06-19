package com.hr.security.web.interceptor;

import com.hr.base.ActionAuths;
import com.hr.base.UsersAuthority;
import com.hr.security.bo.IHasAuthBO;
import com.hr.spring.SpringBeanFactory;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.ValueStack;
import java.util.Iterator;
import java.util.List;

public class CheckAuth implements Interceptor {
    private static final long serialVersionUID = 1L;
    private static final String NOAUTH = "noauth";
    private static IHasAuthBO hasAuthBo = (IHasAuthBO) SpringBeanFactory.getBean("hasService");

    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        List<UsersAuthority> authorityList = ActionAuths.getActionAuths(actionInvocation.getProxy().getActionName(), actionInvocation.getProxy().getMethod());
        if (authorityList == null || authorityList.size() == 0) {
            actionInvocation.getStack().setValue("authorityCondition", null);
            return actionInvocation.invoke();
        }
        for (UsersAuthority userAuthority : authorityList) {
            if (hasAuthBo.hasActionAuth(userAuthority)) {
                actionInvocation.getStack().setValue("authorityCondition", userAuthority.getFilter());
                return actionInvocation.invoke();
            }
        }

        return "noauth";
    }
//    public String intercept(ActionInvocation actionInvocation) throws Exception {
//        List authorityList = ActionAuths
//                .getActionAuths(actionInvocation.getProxy().getActionName(), actionInvocation
//                        .getProxy().getMethod());
//
//        if ((authorityList == null) || (authorityList.size() == 0)) {
//            actionInvocation.getStack().setValue("authorityCondition", null);
//            return actionInvocation.invoke();
//        }
//        Iterator iterator = authorityList.iterator();
//        UsersAuthority userAuthority;
//        do {
//            if (!iterator.hasNext())
//                break label125;
//            userAuthority = (UsersAuthority) iterator.next();
//        } while (!hasAuthBo.hasActionAuth(userAuthority));
//        actionInvocation.getStack().setValue("authorityCondition", userAuthority.getFilter());
//        return actionInvocation.invoke();
//
//        label125: return "noauth";
//    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.web.interceptor.CheckAuth JD-Core Version: 0.5.4
 */