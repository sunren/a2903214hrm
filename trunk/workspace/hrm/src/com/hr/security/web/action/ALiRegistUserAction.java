package com.hr.security.web.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IClientBO;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Location;
import com.hr.profile.domain.Employee;
import com.hr.security.domain.Client;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import java.util.Date;
import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;

public class ALiRegistUserAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private Employee employee;
    private String user_id;
    private String app_instance_id;
    private Userinfo user;

    public ALiRegistUserAction() {
        this.user_id = null;

        this.app_instance_id = null;
    }

    public String execute() throws Exception {
        IClientBO clientBo = (IClientBO) SpringBeanFactory.getBean("clientBo");
        Client client = clientBo.loadOneClientByClientId(this.app_instance_id);
        if (client == null)
            return "error";

        this.employee.setEmpDistinctNo(this.user_id);
        clientBo.insertFirstUser(this.employee, client, "tengyuan", "3,16,23,33,72", "1");
        HttpSession session = getSession();

        session.setAttribute("clientNo", client.getId());
        session.setAttribute("loginModel", "alimt");
        session.setAttribute("userId", this.user_id);
        return "success";
    }

    public void validate() {
        super.validate();
        if ((this.employee.getEmpName() == null) || (this.employee.getEmpName().length() < 1)) {
            addFieldError("employee.empName", "必填项！");
        }
        if ((this.employee.getEmpBirthDate() == null)
                || (this.employee.getEmpBirthDate().toString().length() < 1)) {
            addFieldError("employee.empBirthDate", "必填项！");
        }

        if ((this.employee.getEmpWorkDate() == null)
                || (this.employee.getEmpWorkDate().toString().length() < 1)) {
            addFieldError("employee.empWorkDate", "必填项！");
        }
        if ((this.employee.getEmpJoinDate() == null)
                || (this.employee.getEmpJoinDate().toString().length() < 1)) {
            addFieldError("employee.empJoinDate", "必填项！");
        }

        if ((this.employee.getEmpType() == null)
                || (this.employee.getEmpType().getEmptypeName().length() < 1)) {
            addFieldError("employee.empType.emptypeName", "必填项！");
        }
        if ((this.employee.getEmpLocationNo() == null)
                || (this.employee.getEmpLocationNo().getLocationName().length() < 1)) {
            addFieldError("employee.empLocationNo.locationName", "必填项！");
        }
        if ((this.employee.getEmpDeptNo() == null)
                || (this.employee.getEmpDeptNo().getDepartmentName().length() < 1)) {
            addFieldError("employee.empDeptNo.departmentName", "必填项！");
        }
        if ((this.employee.getEmpIdentificationNo() == null)
                || (this.employee.getEmpIdentificationNo().length() < 1)) {
            addFieldError("employee.empIdentificationNo", "必填项！");
        } else if (this.employee.getEmpIdentificationType().intValue() == 0) {
            String pattern15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
            String pattern18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}(\\d{1}|[xX])$";
            if ((Pattern.matches(pattern15, this.employee.getEmpIdentificationNo()))
                    || (Pattern.matches(pattern18, this.employee.getEmpIdentificationNo())))
                return;
            addFieldError("employee.empIdentificationNo", "格式错误");
        }
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getApp_instance_id() {
        return this.app_instance_id;
    }

    public void setApp_instance_id(String app_instance_id) {
        this.app_instance_id = app_instance_id;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Userinfo getUser() {
        return this.user;
    }

    public void setUser(Userinfo user) {
        this.user = user;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.web.action.ALiRegistUserAction JD-Core Version: 0.5.4
 */