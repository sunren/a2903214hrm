package com.hr.security.web.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IClientBO;
import com.hr.configuration.bo.IEmailsendBO;
import com.hr.configuration.bo.IEmailtemplateBO;
import com.hr.configuration.domain.Emailsend;
import com.hr.configuration.domain.Emailtemplate;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.security.bo.UserBo;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.StringUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class UserCreateAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private Userinfo user;
    private List userIdList;
    private String uiPassword;
    private String userNameString;
    private String userChangeName;
    private String roleIds;
    private Integer[] roleId;

    public String execute() throws Exception {
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        String[] array = this.userNameString.split(";");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.add(Restrictions.in(Employee.PROP_ID, array));
        List list = empBo.findByCriteria(detachedCriteria);

        IEmailsendBO emailsendBo = (IEmailsendBO) SpringBeanFactory.getBean("emailsendBO");
        HttpSession session = ServletActionContext.getRequest().getSession();
        Employee currentEmployee = ((Userinfo) session.getAttribute("userinfo")).getEmployee();
        IClientBO clientLimit = (IClientBO) SpringBeanFactory.getBean("clientBo");
        UserBo userService = (UserBo) SpringBeanFactory.getBean("userService");

        int number = array.length;
        int unSuccessNumber = 0;

        String[] roleIdString = this.roleIds.split(",");
        if (roleIdString.length > 45) {
            addActionError("用户关联的角色过多！");
            return "input";
        }

        this.roleId = new Integer[roleIdString.length];
        for (int i = 0; i < roleIdString.length; ++i) {
            this.roleId[i] = Integer.valueOf(roleIdString[i]);
        }

        String limitInfo = clientLimit.checkUserLimit(this.roleId, number);
        if (!"success".equals(limitInfo)) {
            return limitInfo;
        }

        if ((this.userChangeName != null) && (this.userChangeName.length() > 0)
                && (userService.getUserByName(this.userChangeName) != null)) {
            addFieldError("user.uiUsername", "已存在该用户名！");
            return "input";
        }

        List mailsendList = new ArrayList();

        for (int j = 0; j < number; ++j) {
            Employee emp = (Employee) list.get(j);
            this.user.setId(emp.getId());

            if (this.user.getUiUsername() == null)
                this.user.setUiUsername(emp.getEmpDistinctNo());
            this.user.setEmployee(emp);

            String password = this.uiPassword;
            if (StringUtils.isEmpty(this.uiPassword)) {
                password = RandomStringUtils.random(6, true, true);
            }
            this.user.setUiPasswordEncrypt(StringUtil.encodePassword(password));

            if (!userService.addUser(this.user, this.roleId, currentEmployee.getId())) {
                ++unSuccessNumber;
            }

            Employee receiver = empBo.loadEmp(this.user.getId(), null);
            Map paramUserEmail = new HashMap();
            paramUserEmail.put("SENDER", currentEmployee);
            paramUserEmail.put("APPLIER", receiver);
            paramUserEmail.put("USERNAME", this.user.getUiUsername());
            paramUserEmail.put("PASSWORD", password);
            paramUserEmail.put("URL", "configuration/login.jsp");
            Emailsend send = new Emailsend();
            if (receiver.getEmpEmail() != null) {
                send.setEsTo(receiver.getEmpEmail());
                send.setEsCc(getCurrentEmp().getEmpEmail());
            } else {
                send.setEsTo(getCurrentEmp().getEmpEmail());
            }
            Map map = emailsendBo.getEmailContent("NewUser", paramUserEmail);
            send.setEsTitle((String) map.get("email_title"));
            send.setEsContent((String) map.get("email_content"));
            send.setEsStatus(Integer.valueOf(0));
            send.setEsCreatetime(new Date());
            mailsendList.add(send);
        }

        IEmailtemplateBO templateBo = (IEmailtemplateBO) SpringBeanFactory
                .getBean("emailtemplateBO");
        Emailtemplate template = templateBo.getEmailTemplateByNo("NewUser");
        emailsendBo.sendMail(mailsendList, template.getEtSendMode().intValue());

        addSuccessInfo("添加" + number + "个用户成功");
        if (unSuccessNumber > 0) {
            addErrorInfo("添加" + unSuccessNumber + "个用户失败(用户权限为空或权限长度超过限制!");
        }
        return "success";
    }

    public Integer[] getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Integer[] roleId) {
        this.roleId = roleId;
    }

    public Userinfo getUser() {
        return this.user;
    }

    public void setUser(Userinfo user) {
        this.user = user;
    }

    public String getRoleIds() {
        return this.roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public List getUserIdList() {
        return this.userIdList;
    }

    public void setUserIdList(List userIdList) {
        this.userIdList = userIdList;
    }

    public String getUiPassword() {
        return this.uiPassword;
    }

    public void setUiPassword(String uiPassword) {
        this.uiPassword = uiPassword;
    }

    public String getUserNameString() {
        return this.userNameString;
    }

    public void setUserNameString(String userNameString) {
        this.userNameString = userNameString;
    }

    public String getUserChangeName() {
        return this.userChangeName;
    }

    public void setUserChangeName(String userNameString) {
        this.userChangeName = userNameString;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.web.action.UserCreateAction JD-Core Version: 0.5.4
 */