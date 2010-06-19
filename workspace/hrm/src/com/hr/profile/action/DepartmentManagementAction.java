package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.spring.SpringBeanFactory;
import java.util.List;

public class DepartmentManagementAction extends BaseAction {
    private List allDept;

    public String execute() throws Exception {
        IDepartmentBO deptbo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        this.allDept = deptbo.FindAllDepartmentConHead();
        return "success";
    }

    public List getAllDept() {
        return this.allDept;
    }

    public void setAllDept(List allDept) {
        this.allDept = allDept;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.DepartmentManagementAction JD-Core Version: 0.5.4
 */