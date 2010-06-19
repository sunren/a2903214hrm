package com.hr.base;

import com.hr.security.bo.IHasAuthBO;
import com.hr.spring.SpringBeanFactory;
import java.util.Iterator;
import java.util.List;

public class DWRUtil implements Constants {
    public static final String DWR_ERROR = "error";
    private static IHasAuthBO bo = (IHasAuthBO) SpringBeanFactory.getBean("hasService");

    public static String checkAuth(String actionName, String methodName) {
        List userAuthorityList = ActionAuths.getActionAuths(actionName, methodName);
        if ((userAuthorityList == null) || (userAuthorityList.size() < 1)) {
            return null;
        }
        Iterator iterator = userAuthorityList.iterator();
        while (iterator.hasNext()) {
            UsersAuthority userAuthority = (UsersAuthority) iterator.next();
            try {
                if (bo.hasDWRAuth(userAuthority)) {
                    String filter = userAuthority.getFilter();
                    return filter;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        }
        return "error";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name: com.hr.base.DWRUtil
 * JD-Core Version: 0.5.4
 */