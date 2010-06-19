package com.hr.security.web.action;

import com.hr.base.Constants;
import com.hr.base.FileOperate;
import com.hr.security.bo.LogBo;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.opensymphony.xwork2.Action;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class LoginOutAction implements Action, Constants {
    static Logger logger = Logger.getLogger(LoginOutAction.class.getName());

    public String execute() throws Exception {
        HttpSession session = ServletActionContext.getRequest().getSession();
        Userinfo user = (Userinfo) session.getAttribute("userinfo");
        String userNo = null;
        String ip = null;
        if (user != null) {
            userNo = user.getUiUsername();
            ip = user.getUiLastLoginIp();
        }
        session.setAttribute("authList", null);
        session.setAttribute("userinfo", null);
        session.removeAttribute("authList");
        session.removeAttribute("userinfo");
        session.removeAttribute("requiredURL");
        if ("demo".equals(session.getAttribute("loginModel"))) {
            writeLoggers(userNo, ip, (String) session.getAttribute("clientNo"));
            session.setAttribute("loginModel", null);
            session.removeAttribute("loginModel");
            session.setAttribute("clientNo", null);
            session.removeAttribute("clientNo");
            session.invalidate();
            return "demosuccess";
        }
        if ("alimt".equals(session.getAttribute("loginModel"))) {
            writeLoggers(userNo, ip, (String) session.getAttribute("clientNo"));
            session.setAttribute("loginModel", null);
            session.removeAttribute("loginModel");
            session.setAttribute("clientNo", null);
            session.removeAttribute("clientNo");
            session.setAttribute("userId", null);
            session.removeAttribute("userId");
            session.invalidate();
            return "alimtsuccess";
        }

        writeLoggers(userNo, ip, (String) session.getAttribute("clientNo"));
        session.setAttribute("loginModel", null);
        session.removeAttribute("loginModel");
        session.setAttribute("clientNo", null);
        session.removeAttribute("clientNo");
        session.invalidate();
        return "login";
    }

    private void writeLoggers(String userNo, String ip, String clientNo) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String info = "登出时间：" + df.format(new Date()) + "  客户编号：" + clientNo + "  用户名："
                + userNo + "  登出IP：" + ip;
        LogBo logBo = (LogBo) SpringBeanFactory.getBean("logmanager");
        logBo.updateXML(FileOperate.getFileHomePath() + "login_log/login.xml", info + "\n");
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.web.action.LoginOutAction JD-Core Version: 0.5.4
 */