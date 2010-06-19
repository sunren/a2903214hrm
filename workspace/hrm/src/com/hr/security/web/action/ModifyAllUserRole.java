package com.hr.security.web.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.profile.domain.Employee;
import com.hr.security.bo.UserBo;
import com.hr.security.bo.impl.EmpDistinctNo;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.axis.utils.StringUtils;

public class ModifyAllUserRole extends BaseAction {
    private static final long serialVersionUID = -4234940898871087425L;
    private static final String AUTHMODULE = "911";
    private List roleList;

    public String updateUserRoles(String userId, String userRoles, HttpSession session) {
        try {
            String flt = DWRUtil.checkAuth("userList", "execute");
            if ("error".equalsIgnoreCase(flt))
                return "noauth";

            if ((StringUtils.isEmpty(userId)) || (StringUtils.isEmpty(userRoles)))
                return "error";

            if (("demo".equals(session.getAttribute("loginModel")))
                    && ("TYHRM".equalsIgnoreCase(EmpDistinctNo.getEmpDistinctNo(userId)
                            .substring(0, 5)))) {
                return "pro";
            }

            UserBo userBo = (UserBo) SpringBeanFactory.getBean("userService");
            Userinfo oldUser = userBo.getUserById(userId);
            if (oldUser == null)
                return "error";

            String[] newRolesStmp = null;
            Integer[] newRoles = null;
            newRolesStmp = userRoles.split(",");

            if (newRolesStmp.length > 45) {
                return "roleOver";
            }

            if (newRolesStmp != null) {
                newRoles = new Integer[newRolesStmp.length];
                for (int i = 0; i < newRolesStmp.length; ++i) {
                    newRoles[i] = Integer.valueOf(newRolesStmp[i]);
                }
            }
            if ((newRoles != null) && (!userBo.isRoleNoHasAuth(newRoles, "911"))
                    && (userBo.checkAuthModule(oldUser, "911"))
                    && (userBo.getAdminCount("911") < 2)) {
                return "no";
            }

            Employee currentEmployee = ((Userinfo) session.getAttribute("userinfo")).getEmployee();
            String createrId = currentEmployee.getId();
            String msg = userBo.updateUser(oldUser, newRoles, createrId);
            if (msg.equals("yes")) {
                return userRoles;
            }

            return "error";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    public String doLimitUpdate(String userId, String status, String ip, String mac,
            HttpSession session) {
        try {
            String flt = DWRUtil.checkAuth("userList", "execute");
            if ("error".equalsIgnoreCase(flt))
                return "noauth";

            if ((StringUtils.isEmpty(userId)) || (StringUtils.isEmpty(status)))
                return "error";

            if (("demo".equals(session.getAttribute("loginModel")))
                    && ("TYHRM".equalsIgnoreCase(EmpDistinctNo.getEmpDistinctNo(userId)
                            .substring(0, 5)))) {
                return "pro";
            }

            UserBo userBo = (UserBo) SpringBeanFactory.getBean("userService");
            Userinfo oldUser = userBo.getUserById(userId);
            if (oldUser == null)
                return "error";

            int statusInt = Integer.parseInt(status.trim());
            oldUser.setUiLevelRestrict(Integer.valueOf(statusInt));
            if (statusInt == 1) {
                oldUser.setUiIpRestrict(ip);
            } else if (statusInt == 2) {
                oldUser.setUiIpRestrict(ip);
                oldUser.setUiMacRestrict(mac);
            }
            userBo.updateLimit(oldUser);
            return "yes";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    public List getRoleList() {
        return this.roleList;
    }

    public void setRoleList(List roleList) {
        this.roleList = roleList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.web.action.ModifyAllUserRole JD-Core Version: 0.5.4
 */