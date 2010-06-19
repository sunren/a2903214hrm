package com.hr.security.web.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.profile.domain.Employee;
import com.hr.security.bo.UserBo;
import com.hr.security.bo.impl.EmpDistinctNo;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import javax.servlet.http.HttpSession;

public class ModifyUserStatus extends BaseAction {
    private static final long serialVersionUID = -4234940898871087425L;
    private static final String AUTHMODULE = "911";

    public int updateUserStatus(String userId, int userStatus, HttpSession session) {
        try {
            String flt = DWRUtil.checkAuth("userList", "execute");
            if ("error".equalsIgnoreCase(flt)) {
                return 4;
            }

            if (("demo".equals(session.getAttribute("loginModel")))
                    && (userId.length() >= 5)
                    && ("TYHRM".equalsIgnoreCase(EmpDistinctNo.getEmpDistinctNo(userId)
                            .substring(0, 5)))) {
                return 6;
            }
            UserBo userBo = (UserBo) SpringBeanFactory.getBean("userService");
            Userinfo user = userBo.getUserById(userId);

            if (user == null) {
                return 5;
            }
            Userinfo userinfo = (Userinfo) session.getAttribute("userinfo");
            if ((user.getId() != null) && (userinfo.getId().equals(user.getId()))) {
                return 2;
            }

            Userinfo oldUserinfo = userBo.getUserById(user.getId());
            if ((userBo.checkAuthModule(oldUserinfo, "911")) && (userBo.getAdminCount("911") < 2)) {
                return 3;
            }

            Employee currentEmployee = ((Userinfo) session.getAttribute("userinfo")).getEmployee();
            String createrId = currentEmployee.getId();
            if (userBo.updateStatus(userId, userStatus, createrId)) {
                return userStatus;
            }
            return 5;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 5;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.web.action.ModifyUserStatus JD-Core Version: 0.5.4
 */