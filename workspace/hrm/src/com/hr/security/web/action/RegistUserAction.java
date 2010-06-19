package com.hr.security.web.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IClientBO;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Location;
import com.hr.profile.domain.Employee;
import com.hr.security.domain.Client;
import com.hr.spring.SpringBeanFactory;
import java.util.Date;
import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;

public class RegistUserAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private Employee employee;
    private String clientId;
    private String password;
    private String confirmPassword;

    public String execute() throws Exception {
        IClientBO clientBo = (IClientBO) SpringBeanFactory.getBean("clientBo");
        Client client = clientBo.loadOneClient(this.clientId);
        if (client == null) {
            addActionError("该客户编号不存在，请先创建一个客户！");
            return "error";
        }

        clientBo.insertDefaultTables("00000001", client.getId());
        clientBo
                .insertFirstUser(
                                 this.employee,
                                 client,
                                 this.password,
                                 "2,4,12,14,16,22,24,32,34,42,44,52,54,61,62,63,64,65,71,81,82,83,84,85,86,87,88,89,91,92",
                                 "5");
        HttpSession session = getSession();
        session.setAttribute("clientNo", client.getId());
        session.setAttribute("loginModel", "multiple");
        return "success";
    }

    public void validate() {
        super.validate();
        if ((this.employee == null) || (this.employee.getEmpDistinctNo() == null)
                || (this.employee.getEmpDistinctNo().length() < 1)) {
            addFieldError("employee.empDistinctNo", "必填项！");
        }

        if ((this.password == null) || (this.password.length() < 1)) {
            addFieldError("password", "必填项！");
        } else if ((this.password.length() > 32) || (this.password.length() < 6)) {
            addFieldError("password", "密码长度必须6-32 位！");
        }
        if ((this.confirmPassword == null) || (this.confirmPassword.length() < 1)) {
            addFieldError("confirmPassword", "必填项！");
        } else if (!this.confirmPassword.equals(this.password)) {
            addFieldError("confirmPassword", "两次密码输入不同");
        }

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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.web.action.RegistUserAction JD-Core Version: 0.5.4
 */