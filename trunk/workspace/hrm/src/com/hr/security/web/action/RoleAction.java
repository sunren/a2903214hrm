package com.hr.security.web.action;

import com.hr.base.BaseAction;
import com.hr.security.bo.RoleBo;
import com.hr.security.bo.UserBo;
import com.hr.security.domain.Role;
import java.util.List;
import org.apache.log4j.Logger;

public class RoleAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    static Logger logger = Logger.getLogger(RoleAction.class.getName());
    private UserBo userBo;
    private RoleBo roleBo;
    private List authList;
    private Role role;
    private Integer[] authId;

    public String execute() throws Exception {
        if (this.authId == null) {
            addErrorInfo("此角色未设置权限");
            return "input";
        }
        if ((this.authId != null) && (this.authId.length > 85)) {
            addErrorInfo("此角色所关联的权限过多！");
            return "input";
        }
        if (getRoleBo().findRoleByName(this.role.getRoleName())) {
            addActionError("该角色名已存在!");
            return "input";
        }

        String msg = getRoleBo().addRole(this.role, this.authId);
        if ("yes".equals(msg)) {
            addSuccessInfo("添加角色成功");
            return "success";
        }
        if ("noAuth".equals(msg)) {
            addErrorInfo("此角色未设置权限");
            return "input";
        }
        if ("authOver".equals(msg)) {
            addErrorInfo("此角色所关联的权限过多！");
            return "input";
        }
        if ("roleOver".equals(msg)) {
            addErrorInfo("您所创建的角色数量过多！");
            return "input";
        }
        if ("no".equals(msg)) {
            addErrorInfo("创建角色出现异常");
            return "input";
        }
        return "success";
    }

    public String update() throws Exception {
        if ((this.role == null) || (this.authId == null)) {
            clearErrorsAndMessages();
            return "success";
        }
        String msg = getRoleBo().updateRole(this.role, this.authId, getUserBo());
        if (msg.equals("yes")) {
            addSuccessInfo("角色修改成功");
            return "success";
        }
        if ("noAuth".equals(msg)) {
            addErrorInfo("此角色未设置权限");
            return "input";
        }
        if ("nullAuth".equals(msg)) {
            addErrorInfo("无法获取用户权限，无法更新！");
            return "input";
        }
        if ("authOver".equals(msg)) {
            addErrorInfo("此角色所关联的权限过多！");
            return "input";
        }
        if ("no".equals(msg)) {
            addErrorInfo("创建角色出现异常");
            return "input";
        }
        if ("exist".equals(msg)) {
            addErrorInfo("该角色名已经存在，请重新输入另一个角色名");
            return "input";
        }
        return "success";
    }

    public RoleBo getRoleBo() {
        if (this.roleBo == null)
            this.roleBo = ((RoleBo) getBean("roleService"));
        return this.roleBo;
    }

    public UserBo getUserBo() {
        if (this.userBo == null)
            this.userBo = ((UserBo) getBean("userService"));
        return this.userBo;
    }

    public List getAuthList() {
        return this.authList;
    }

    public void setAuthList(List authList) {
        this.authList = authList;
    }

    public Integer[] getAuthId() {
        return this.authId;
    }

    public void setAuthId(Integer[] authId) {
        this.authId = authId;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.web.action.RoleAction JD-Core Version: 0.5.4
 */