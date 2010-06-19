package com.hr.security.web.action;

import com.hr.base.BaseAction;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.security.bo.RoleBo;
import com.hr.spring.SpringBeanFactory;
import java.util.List;

public class UserAddOrEditInit extends BaseAction {
    private static final long serialVersionUID = 10001258L;
    private List roleList;
    private List empList;

    public List getEmpList() {
        return this.empList;
    }

    public void setEmplist(List empList) {
        this.empList = empList;
    }

    public List getRoleList() {
        return this.roleList;
    }

    public void setRoleList(List roleList) {
        this.roleList = roleList;
    }

    public String execute() throws Exception {
        try {
            RoleBo roleService = (RoleBo) SpringBeanFactory.getBean("roleService");
            this.roleList = roleService.getRoleList();

            IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");

            this.empList = empBo.findNouserEmps();

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.web.action.UserAddOrEditInit JD-Core Version: 0.5.4
 */