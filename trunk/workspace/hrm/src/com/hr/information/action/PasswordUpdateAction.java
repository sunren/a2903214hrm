package com.hr.information.action;

import com.hr.base.BaseAction;
import com.hr.security.bo.RoleBo;
import com.hr.security.bo.UserBo;
import com.hr.security.domain.Role;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.StringUtil;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class PasswordUpdateAction extends BaseAction {
    private static final long serialVersionUID = 103697L;
    private Userinfo user;
    private String newpassword;
    private String confirmpassword;
    private String roleInfo;

    public String execute() throws Exception {
        String no = getCurrentEmpNo();
        if (StringUtils.isEmpty(no)) {
            return "login";
        }

        UserBo userService = (UserBo) SpringBeanFactory.getBean("userService");
        Userinfo user0 = userService.getUserById(no);
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

        if (!user0.getUiPasswordDecrypt().equals(
                                                 StringUtil.encodePassword(this.user
                                                         .getUiPassword()))) {
            addFieldError("user.uiPassword", "原密码不正确＄1�7");
            return "input";
        }
        this.user.setId(user0.getId());

        if (!this.newpassword.equals(this.confirmpassword)) {
            addFieldError("confirmpassword", "重复密码不一致！");
            return "input";
        }
        if (userService.updatePassword(this.user.getId(), this.newpassword)) {
            addSuccessInfo("密码修改成功〄1�7");
            return "success";
        }
        addErrorInfo("密码修改失败＄1�7");
        return "input";
    }

    public String getNewpassword() {
        return this.newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public Userinfo getUser() {
        return this.user;
    }

    public void setUser(Userinfo user) {
        this.user = user;
    }

    public String getConfirmpassword() {
        return this.confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
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
 * com.hr.information.action.PasswordUpdateAction JD-Core Version: 0.5.4
 */