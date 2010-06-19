package com.hr.security.web.action;

import com.hr.base.BaseAction;
import com.hr.security.bo.RoleBo;
import com.hr.security.bo.UserBo;
import com.hr.security.domain.Role;
import com.hr.spring.SpringBeanFactory;
import java.util.ArrayList;
import java.util.List;

public class RoleListAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private RoleBo roleBo;
    private UserBo userBo;
    private List roleList;
    private List authorities;
    private Integer roleNo;
    private Role role;

    public String execute() throws Exception {
        this.roleList = ((RoleBo) SpringBeanFactory.getBean("roleService")).getRoleList();
        return "success";
    }

    public String view() throws Exception {
        if (this.role == null) {
            return "error";
        }
        this.role = getRoleBo().getRole(this.role.getId());
        List list = new ArrayList();
        String[] auths = this.role.getRoleAuthority().split(",");
        for (int i = 0; i < auths.length; ++i) {
            list.add(Integer.valueOf(Integer.parseInt(auths[i])));
        }
        this.authorities = getRoleBo().getAllAuthoritys(list);
        if (this.role == null) {
            addActionError("无此角色!");
            return "error";
        }
        return "success";
    }

    public String del() throws Exception {
        if ((this.roleNo == null) || (this.roleNo.toString().trim().equals(""))) {
            clearErrorsAndMessages();
            return "success";
        }
        String callBack = getRoleBo().delRole(this.roleNo, getUserBo());
        if ("yes".equals(callBack)) {
            addSuccessInfo("删除成功〄1�7");
            this.roleList = this.roleBo.getRoleList();
        } else if ("err".equals(callBack)) {
            addErrorInfo("此角色有用户与之关联，不能删除！");
        } else if ("ref".equals(callBack)) {
            addErrorInfo("不能重复提交＄1�7");
        }
        return "success";
    }

    public RoleBo getRoleBo() {
        if (this.roleBo == null)
            this.roleBo = ((RoleBo) SpringBeanFactory.getBean("roleService"));
        return this.roleBo;
    }

    public UserBo getUserBo() {
        if (this.userBo == null)
            this.userBo = ((UserBo) getBean("userService"));
        return this.userBo;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List getRoleList() {
        return this.roleList;
    }

    public void setRoleList(List roleList) {
        this.roleList = roleList;
    }

    public Integer getRoleNo() {
        return this.roleNo;
    }

    public void setRoleNo(Integer roleNo) {
        this.roleNo = roleNo;
    }

    public List getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(List authorities) {
        this.authorities = authorities;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.web.action.RoleListAction JD-Core Version: 0.5.4
 */