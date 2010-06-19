package com.hr.security.web.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.security.bo.RoleBo;
import com.hr.security.bo.UserBo;
import com.hr.security.bo.impl.EmpDistinctNo;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.Pager;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.struts2.ServletActionContext;

public class UserListAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private static final String AUTHMODULE = "911";
    private List userList;
    private List roleList;
    private List departList;
    private int roleIndex;
    private String role;
    private Userinfo user;
    private UserBo userBo;
    private String depart;
    private String manager;
    private String userDelId;
    private String empName;
    private String password;
    private Pager pager;

    public UserListAction() {
        this.pager = new Pager();
    }

    public String execute() throws Exception {
        try {
            RoleBo roleService = (RoleBo) SpringBeanFactory.getBean("roleService");
            UserBo userBo = (UserBo) SpringBeanFactory.getBean("userService");
            if (this.roleList == null)
                this.roleList = roleService.getRoleList();

            IDepartmentBO deptbo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
            this.departList = deptbo.FindEnabledDepartment();
            this.userList = userBo.searchUserList(this.empName, this.user, this.depart,
                                                  this.manager, this.role, this.pager);
            this.password = RandomStringUtils.random(6, true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    public String del() throws Exception {
        clearErrorsAndMessages();
        if (this.userDelId == null)
            return "success";
        UserBo userBo = (UserBo) SpringBeanFactory.getBean("userService");
        this.user = userBo.getUserById(this.userDelId);
        if (this.user == null)
            return "error";
        HttpSession session = ServletActionContext.getRequest().getSession();
        String userNo = EmpDistinctNo.getEmpDistinctNo(this.userDelId).toUpperCase();
        if (("demo".equals(session.getAttribute("loginModel"))) && (userNo.indexOf("TYHRM") != -1)) {
            addActionError("TYHRMXXX是受保护的用户，Demo版不能操作！");
            this.user = null;
            return "error";
        }
        Userinfo userinfo = (Userinfo) session.getAttribute("userinfo");
        if ((this.user.getId() != null) && (userinfo.getId().equals(this.user.getId()))) {
            addErrorInfo("此用户是您本人，不能删除!");
            this.user = null;
            return "error";
        }
        Userinfo oldUserinfo = getUserBo().getUserById(this.user.getId());
        if ((getUserBo().checkAuthModule(oldUserinfo, "911"))
                && (getUserBo().getAdminCount("911") < 2)) {
            addErrorInfo("删除失败，此用户为唯一的系统管理员，删除后会导致系统无人能管理");
            this.user = null;
            return "error";
        }
        String name = EmpDistinctNo.getEmpName(this.userDelId);
        if (getUserBo().delUser(this.user.getId())) {
            addSuccessInfo("删除用户(" + name + ")成功");
        } else {
            addErrorInfo("删除用户(" + name + ")失败!");
        }
        this.user = null;
        return "success";
    }

    public UserBo getUserBo() {
        if (this.userBo == null)
            this.userBo = ((UserBo) SpringBeanFactory.getBean("userService"));
        return this.userBo;
    }

    public List getUserList() {
        return this.userList;
    }

    public void setUserList(List userList) {
        this.userList = userList;
    }

    public List getRoleList() {
        return this.roleList;
    }

    public void setRoleList(List roleList) {
        this.roleList = roleList;
    }

    public List getDepartList() {
        return this.departList;
    }

    public void setDepartList(List departList) {
        this.departList = departList;
    }

    public String getDepart() {
        return this.depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getManager() {
        return this.manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Userinfo getUser() {
        return this.user;
    }

    public void setUser(Userinfo user) {
        this.user = user;
    }

    public Pager getPager() {
        return this.pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getRoleIndex() {
        if ((this.role == null) || (this.role.equals("")))
            return 0;
        this.roleIndex = Integer.parseInt(this.role.trim());
        return this.roleIndex;
    }

    public void setRoleIndex(int roleIndex) {
        if (this.role == null)
            this.roleIndex = 0;
        else
            this.roleIndex = Integer.parseInt(this.role.trim());
    }

    public String getUserDelId() {
        return this.userDelId;
    }

    public void setUserDelId(String userDelId) {
        this.userDelId = userDelId;
    }

    public String getEmpName() {
        return this.empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.web.action.UserListAction JD-Core Version: 0.5.4
 */