package com.hr.security.web.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IClientBO;
import com.hr.security.bo.IValidateUser;
import com.hr.security.bo.UserBo;
import com.hr.security.domain.Client;
import com.hr.spring.SpringBeanFactory;
import javax.servlet.http.HttpSession;

public class ValidateUserAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private String user_id;
    private String app_instance_id;
    private String app_id;
    private String token;

    public ValidateUserAction() {
        this.user_id = null;

        this.app_instance_id = null;

        this.app_id = null;

        this.token = null;
    }

    public String execute() throws Exception {
        IValidateUser validateUser = (IValidateUser) SpringBeanFactory.getBean("validateuser");
        String result = validateUser.validateUser(getSession().getId(), this.user_id,
                                                  this.app_instance_id, this.app_id, this.token);

        if ("error".equals(result)) {
            return "error";
        }
        if ("1".equals(result)) {
            IClientBO clientBo = (IClientBO) SpringBeanFactory.getBean("clientBo");
            Client client = clientBo.loadOneClientByClientId(this.app_instance_id);

            if (client == null)
                return "registClient";

            return validateUser(client.getId());
        }
        if ("0".equals(result)) {
            IClientBO clientBo = (IClientBO) SpringBeanFactory.getBean("clientBo");
            Client client = clientBo.loadOneClientByClientId(this.app_instance_id);

            if (client == null)
                return "error";

            return validateUser(client.getId());
        }
        if ("-1".equals(result)) {
            return "error";
        }
        if ("-2".equals(result)) {
            return "error";
        }
        return "error";
    }

    private String validateUser(String clientId) {
        HttpSession session = getSession();
        session.setAttribute("clientNo", clientId);
        session.setAttribute("loginModel", "alimt");
        session.setAttribute("userId", this.user_id);

        UserBo userBo = (UserBo) SpringBeanFactory.getBean("userService");
        String message = userBo.updateSignFree(this.user_id);
        if (message.equals("noUser")) {
            return "registUser";
        }
        if (message.equals("hasLeave")) {
            addActionError("您的状�1�7�是已离职，不能登录本系统！");
            return "error";
        }
        if (message.equals("hasStoped")) {
            addActionError("您已经被停用，不能登录本系统＄1�7");
            return "error";
        }
        if (message.equals("ipError")) {
            addActionError("您未通过IP验证，不能登录本系统＄1�7");
            return "error";
        }
        return "success";
    }

    public String getApp_id() {
        return this.app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getApp_instance_id() {
        return this.app_instance_id;
    }

    public void setApp_instance_id(String app_instance_id) {
        this.app_instance_id = app_instance_id;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.web.action.ValidateUserAction JD-Core Version: 0.5.4
 */