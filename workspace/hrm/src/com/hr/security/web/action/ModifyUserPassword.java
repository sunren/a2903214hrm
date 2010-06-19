package com.hr.security.web.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.configuration.bo.IEmailsendBO;
import com.hr.configuration.bo.IEmailtemplateBO;
import com.hr.configuration.domain.Emailsend;
import com.hr.configuration.domain.Emailtemplate;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.security.bo.UserBo;
import com.hr.security.bo.impl.EmpDistinctNo;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

public class ModifyUserPassword extends BaseAction {
    private static final long serialVersionUID = -4234940898871087425L;
    private Userinfo user;

    public String updateUserPassword(String userId, String password, String userName,
            String oldUserName, String flag, HttpSession session) {
        String flt = DWRUtil.checkAuth("userList", "execute");
        if ("error".equalsIgnoreCase(flt)) {
            return "noauth";
        }
        UserBo userService = (UserBo) SpringBeanFactory.getBean("userService");
        this.user = userService.getUserById(userId);
        if (this.user == null) {
            return "nouser";
        }
        if (("demo".equals(session.getAttribute("loginModel")))
                && (userId.length() >= 5)
                && ("TYHRM"
                        .equalsIgnoreCase(EmpDistinctNo.getEmpDistinctNo(userId).substring(0, 5)))) {
            return "demo";
        }
        if ((userName.trim().length() > 0) && (password.trim().length() > 0)) {
            Employee currentEmployee = ((Userinfo) session.getAttribute("userinfo")).getEmployee();
            String createrId = currentEmployee.getId();

            boolean updateResult = true;
            if (userName.equals(oldUserName)) {
                updateResult = userService.updatePasswordBy(userId, null, password, createrId);
            } else {
                Userinfo userInfo = userService.getUserByName(userName);
                if (userInfo != null)
                    return "exist";

                updateResult = userService.updatePasswordBy(userId, userName, password, createrId);
            }

            if (!updateResult) {
                return "error";
            }

            if ("1".equals(flag)) {
                IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
                Employee receiver = empBo.loadEmp(userId, null);
                IEmailsendBO emailsendBo = (IEmailsendBO) SpringBeanFactory.getBean("emailsendBO");

                this.user = userService.getUserById(userId);

                Map paramUserEmail = new HashMap();
                paramUserEmail.put("SENDER", currentEmployee);
                paramUserEmail.put("APPLIER", receiver);
                paramUserEmail.put("USERNAME", this.user.getUiUsername());
                paramUserEmail.put("PASSWORD", password);
                paramUserEmail.put("URL", "configuration/login.jsp");
                Map contentMap = emailsendBo.getEmailContent("NewUserPassword", paramUserEmail);
                Emailsend send = new Emailsend();
                send.setEsTitle(((String) contentMap.get("email_title")).toString());
                send.setEsContent(((String) contentMap.get("email_content")).toString());
                if (receiver.getEmpEmail() != null) {
                    send.setEsTo(receiver.getEmpEmail());
                    send.setEsCc(getCurrentEmp().getEmpEmail());
                } else {
                    send.setEsTo(getCurrentEmp().getEmpEmail());
                }
                send.setEsStatus(Integer.valueOf(0));
                send.setEsCreatetime(new Date());

                List mailsendList = new ArrayList();
                mailsendList.add(send);

                IEmailtemplateBO templateBo = (IEmailtemplateBO) SpringBeanFactory
                        .getBean("emailtemplateBO");
                Emailtemplate template = templateBo.getEmailTemplateByNo("NewUserPassword");
                emailsendBo.sendMail(mailsendList, template.getEtSendMode().intValue());
            }
            return "success";
        }
        return "error";
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
 * com.hr.security.web.action.ModifyUserPassword JD-Core Version: 0.5.4
 */