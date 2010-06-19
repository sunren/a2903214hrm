package com.hr.security.web.interceptor;

import com.hr.base.Constants;
import com.hr.security.domain.Userinfo;
import com.hr.util.MyTools;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class LoginCheck implements Interceptor, Constants {
    private static final long serialVersionUID = 1L;

    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ActionContext ctx = actionInvocation.getInvocationContext();
        Map session = ctx.getSession();
        Userinfo userinfo = (Userinfo) session.get("userinfo");
        if (userinfo == null)
            try {
                session.put("requiredURL", MyTools.getFromURL((HttpServletRequest) ctx
                        .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest")));
            } catch (Exception e) {
            }
        return (userinfo == null) ? "login" : actionInvocation.invoke();
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.web.interceptor.LoginCheck JD-Core Version: 0.5.4
 */