package com.hr.information.action;

import com.hr.base.BaseAction;
import com.hr.security.bo.RoleBo;
import com.hr.security.bo.UserBo;
import com.hr.security.domain.Role;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import java.util.List;

public class PasswordAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private String ownPass;
    private String roleInfo;

    public String own() throws Exception {
        String no = getCurrentEmpNo();
        UserBo userService = (UserBo) SpringBeanFactory.getBean("userService");
        Userinfo userTemp = userService.getUserById(no);

        List userRoleList = userTemp.getUiRoleList();
        int len = userRoleList.size();

        RoleBo roleService = (RoleBo) SpringBeanFactory.getBean("roleService");
        List roleList = roleService.getRoleList();
        int roleListLen = roleList.size();

        String userRoleInfo = "";

        for (int j = 0; j < len; ++j) {
            for (int i = 0; i < roleListLen; ++i) {
                Role tempRole = (Role) roleList.get(i);

                if (tempRole.getRoleNo() == Integer.parseInt((String) (String) userRoleList.get(j))) {
                    if (j == 0) {
                        userRoleInfo = userRoleInfo + tempRole.getRoleName();
                        break;
                    }
                    if (i % 5 == 0) {
                        userRoleInfo = userRoleInfo + "<br>" + tempRole.getRoleName();
                        break;
                    }
                    userRoleInfo = userRoleInfo + " | " + tempRole.getRoleName();
                    break;
                }
            }
        }

        setRoleInfo(userRoleInfo);

        setOwnPass("OWN");
        return "success";
    }

    public String getOwnPass() {
        return this.ownPass;
    }

    public void setOwnPass(String ownPass) {
        this.ownPass = ownPass;
    }

    public String getRoleInfo() {
        return this.roleInfo;
    }

    public void setRoleInfo(String roleInfo) {
        this.roleInfo = roleInfo;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.information.action.PasswordAction JD-Core Version: 0.5.4
 */