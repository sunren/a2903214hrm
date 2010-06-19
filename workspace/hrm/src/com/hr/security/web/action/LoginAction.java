package com.hr.security.web.action;

import com.hr.base.BaseAction;
import com.hr.base.FileOperate;
import com.hr.configuration.bo.IClientBO;
import com.hr.security.bo.LogBo;
import com.hr.security.bo.UserBo;
import com.hr.security.domain.Client;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class LoginAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private Userinfo user;
    private String uiPassword;
    private String checkCode;
    private String macAddress;
    static Logger logger = Logger.getLogger(LoginAction.class.getName());

    public LoginAction() {
        this.uiPassword = null;

        this.checkCode = null;

        this.macAddress = null;
    }

    public String demoLoginExecute() throws Exception {
        if ((this.user == null) || (this.user.getUiUsername() == null)) {
            addActionError("用户初始化出错！");
            return "demoerror";
        }

        IClientBO clientBo = (IClientBO) SpringBeanFactory.getBean("clientBo");
        Client client = (Client) clientBo.loadClients().get(0);

        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute("clientNo", client.getId());
        session.setAttribute("loginModel", "demo");

        String savedCode = (String) session.getAttribute("check_code");
        if ((savedCode == null) || (!savedCode.equalsIgnoreCase(this.checkCode))) {
            addActionError("验证码错误！");
            return "demoerror";
        }

        UserBo userBo = (UserBo) SpringBeanFactory.getBean("userService");
        String message = userBo.doLogin(this.user, this.uiPassword, this.macAddress, "demo");

        if (message.equals("yes")) {
            updateLoggers();
            return "success";
        }

        addActionError(message);
        return "demoerror";
    }

    public String mutipleLoginExecute() throws Exception {
        if ((this.user == null) || (this.user.getUiUsername() == null)) {
            addActionError("用户初始化出错！");
            return "error";
        }

        IClientBO clientBo = (IClientBO) SpringBeanFactory.getBean("clientBo");
        Client client = clientBo.loadOneClient(this.user.getClientNo());
        if (client == null) {
            addActionError("不存在的客户编号");
            return "input";
        }

        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute("clientNo", this.user.getClientNo());
        session.setAttribute("loginModel", "multiple");

        UserBo userBo = (UserBo) SpringBeanFactory.getBean("userService");
        String message = userBo.doLogin(this.user, this.uiPassword, this.macAddress, "multiple");

        if (message.equals("yes")) {
            if (client.getClientStatus().intValue() == 7) {
                updateLoggers();
                return "success";
            }

            String checkLoginLimit = clientBo.updateLoginLimit(client);
            if (checkLoginLimit.equals("overLimit")) {
                addActionError("软件试用已到期，登录将进入DEMO版本，如果要继续使用所有功能请与客服联系");
                return "error";
            }
        } else {
            if (message.equals("mac")) {
                this.macAddress = getMac();
                addActionError("机器限制已经启动，此用户只能在本台机器登录！");
                return "error";
            }

            addActionError(message);
            return "error";
        }

        updateLoggers();
        return "success";
    }

    public String singleLoginExecute() throws Exception {
        if ((this.user == null) || (this.user.getUiUsername() == null)) {
            addActionError("用户初始化出错！");
            return "error";
        }

        IClientBO clientBo = (IClientBO) SpringBeanFactory.getBean("clientBo");
        Client client = (Client) clientBo.loadClients().get(0);

        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute("clientNo", client.getId());
        session.setAttribute("loginModel", "single");

        UserBo userBo = (UserBo) SpringBeanFactory.getBean("userService");
        String message = userBo.doLogin(this.user, this.uiPassword, this.macAddress, "single");

        if (message.equals("yes")) {
            if (client.getClientStatus().intValue() == 7) {
                updateLoggers();
                return "success";
            }

            String checkLoginLimit = clientBo.updateLoginLimit(client);
            if (checkLoginLimit.equals("overLimit")) {
                addActionError("软件试用已到期，登录将进入DEMO版本，如果要继续使用所有功能请与客服联系");
                return "error";
            }
        } else {
            if (message.equals("mac")) {
                this.macAddress = getMac();
                addActionError("机器限制已经启动，此用户只能在本台机器登录！");
                return "error";
            }

            addActionError(message);
            return "error";
        }

        updateLoggers();
        return "success";
    }

    private void updateLoggers() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String info = "登录时间：" + df.format(new Date()) + "  用户名：" + this.user.getUiUsername()
                + "  登录IP：" + this.user.getUiLastLoginIp();

        LogBo logBo = (LogBo) SpringBeanFactory.getBean("logmanager");
        logBo.updateXML(FileOperate.getFileHomePath() + "login_log/login.xml", info + "\n");
    }

    public String getMac() {
        String uuid = UUID.randomUUID().toString();

        uuid = uuid.substring(uuid.lastIndexOf('-') + 1);

        char[] macChar = new char[17];
        int j = 0;
        int k = 0;
        for (int i = 0; i < 17; ++i) {
            ++j;
            if (j % 3 == 0) {
                macChar[i] = '-';
            } else {
                macChar[i] = uuid.charAt(k);
                ++k;
            }
        }
        return new String(macChar);
    }

    public Userinfo getUser() {
        return this.user;
    }

    public void setUser(Userinfo user) {
        this.user = user;
    }

    public String getUiPassword() {
        return this.uiPassword;
    }

    public void setUiPassword(String uiPassword) {
        this.uiPassword = uiPassword;
    }

    public String getCheckCode() {
        return this.checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public String getMacAddress() {
        return this.macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.web.action.LoginAction JD-Core Version: 0.5.4
 */