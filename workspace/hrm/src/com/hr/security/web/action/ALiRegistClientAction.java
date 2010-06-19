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
import org.apache.commons.validator.GenericValidator;

public class ALiRegistClientAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private String user_id;
    private String app_instance_id;
    private Employee employee;
    private Client client;
    private Userinfo user;

    public ALiRegistClientAction() {
        this.user_id = null;

        this.app_instance_id = null;

        this.employee = null;
    }

    public String execute() throws Exception {
        if ((this.user_id.length() < 1) || (this.app_instance_id.length() < 1))
            return "error";
        IClientBO clientBo = (IClientBO) SpringBeanFactory.getBean("clientBo");

        this.client.setId(clientBo.searchMaxClientId());
        this.client.setClientId(this.app_instance_id);

        clientBo
                .insertRegistClient(this.client,
                                    "EMP:100#USER:25#AUTH:1,2,3,4,5,11,12,13,14,15,16,71,72,73,81,82,85,86");
        this.employee.setEmpDistinctNo(this.user_id);
        clientBo.insertDefaultTables("00000001", this.client.getId());
        clientBo
                .insertFirstUser(
                                 this.employee,
                                 this.client,
                                 "tengyuan",
                                 "2,4,12,14,16,22,24,32,34,42,44,52,54,61,62,63,64,65,71,81,82,83,84,85,86,87,88,89,91,92",
                                 "5");

        HttpSession session = getSession();
        session.setAttribute("clientNo", this.client.getId());
        session.setAttribute("loginModel", "alimt");
        session.setAttribute("userId", this.user_id);
        return "success";
    }

    public void validate() {
        super.validate();
        if ((this.client == null) || (this.client.getClientName().length() < 1)) {
            addFieldError("client.clientName", "必填项！");
        }

        if ((this.client.getClientPhone() == null) || (this.client.getClientPhone().length() < 1)) {
            addFieldError("client.clientPhone", "必填项！");
        } else if (!isPhone(this.client.getClientPhone())) {
            addFieldError("client.clientPhone", "格式不符合要求！");
        }
        if ((this.client.getClientContactName() == null)
                || (this.client.getClientContactName().length() < 1)) {
            addFieldError("client.clientContactName", "必填项！");
        }

        if ((this.client.getClientEmail() == null) || (this.client.getClientEmail().length() < 1)) {
            addFieldError("client.clientEmail", "必填项！");
        } else if (!isEmail(this.client.getClientEmail())) {
            addFieldError("client.clientEmail", "格式不符合要求！");
        }
        if ((this.client.getClientZip() != null) && (this.client.getClientZip().length() > 1)
                && (!isZip(this.client.getClientZip()))) {
            addFieldError("client.clientZip", "格式不符合要求！");
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
        if ((this.employee.getEmpIdentificationNo() == null)
                || (this.employee.getEmpIdentificationNo().length() < 1)) {
            addFieldError("employee.empIdentificationNo", "必填项！");
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
        if (this.employee.getEmpIdentificationType().intValue() == 0) {
            String pattern15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
            String pattern18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}(\\d{1}|[xX])$";
            if ((Pattern.matches(pattern15, this.employee.getEmpIdentificationNo()))
                    || (Pattern.matches(pattern18, this.employee.getEmpIdentificationNo())))
                return;
            addFieldError("employee.empIdentificationNo", "格式错误");
        }
    }

    private boolean isPhone(String value) {
        return value.matches("^([0|1](\\d){1,3}[ ]?)?([-]?((\\d)|[ ]){1,12})(-(\\d){3,4})?$");
    }

    private boolean isZip(String value) {
        if (value.length() < 6)
            return false;
        return isNumber(value);
    }

    private boolean isNumber(String value) {
        for (int i = 0; i < value.length(); ++i) {
            if ((value.charAt(i) > '9') || (value.charAt(i) < '0'))
                return false;
        }
        return true;
    }

    private boolean isEmail(String value) {
        return GenericValidator.isEmail(value);
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
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

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Userinfo getUser() {
        return this.user;
    }

    public void setUser(Userinfo user) {
        this.user = user;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.web.action.ALiRegistClientAction JD-Core Version: 0.5.4
 */