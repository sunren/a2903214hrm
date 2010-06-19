package com.hr.configuration.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.bo.IEmailsendBO;
import com.hr.configuration.bo.ILocationBO;
import com.hr.configuration.domain.Emailsend;
import com.hr.configuration.domain.Emailtemplate;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.PropertiesFileConfigManager;
import com.opensymphony.xwork2.ActionContext;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class EmailsendBatch extends BaseAction implements Constants {
    private static final long serialVersionUID = -4234940898871087425L;
    private List empList;
    private List locationList;
    private List departmentList;
    private List buList;
    private List templateList;
    private String operation;
    private String[] empNo;
    private Integer ccEmp;
    private IEmployeeBo empBo;
    private Emailsend mail;
    private static final Logger logger = Logger.getLogger(EmailsendBatch.class);

    public EmailsendBatch() {
        this.locationList = null;

        this.departmentList = null;

        this.buList = null;

        this.templateList = null;

        this.operation = null;

        this.empNo = new String[0];

        this.ccEmp = Integer.valueOf(0);

        this.empBo = ((IEmployeeBo) getBean("empBo"));

        this.mail = null;
    }

    public String execute() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("测试：execute() - EmailsendBatch.action");
        }

        if ((this.operation == null) || (this.operation.equals(""))) {
            this.empList = this.empBo.getObjects(Employee.class, null);

            ILocationBO localbo = (ILocationBO) SpringBeanFactory.getBean("locationBO");
            this.locationList = localbo.FindEnabledLocation();
            IDepartmentBO deptbo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
            this.departmentList = deptbo.FindEnabledDepartment();

            this.templateList = this.empBo.getObjects(Emailtemplate.class, null);
        } else if ((this.operation.equalsIgnoreCase("send")) && (this.mail.getEsTitle() != null)
                && (this.mail.getEsTitle().trim().length() > 0)
                && (this.mail.getEsContent() != null)
                && (this.mail.getEsContent().trim().length() > 0) && (this.empNo.length > 0)
                && (saveEmail())) {
            return "success";
        }

        if (logger.isDebugEnabled()) {
            logger.debug("execute() -EmailsendBatch- end");
        }

        return "input";
    }

    public boolean saveEmail() {
        PropertiesFileConfigManager reader = PropertiesFileConfigManager.getInstance();
        String fromEmp = reader.getProperty("email.sys.sender");
        String mailSystemName = reader.getProperty("email.sys.mailSystemName");
        String mailAdminPhone = reader.getProperty("email.sys.mailAdminPhone");

        Map session = ServletActionContext.getContext().getSession();
        if (session == null)
            return false;
        Userinfo loginUser = (Userinfo) session.get("userinfo");
        if (loginUser == null)
            return false;
        List toEmp = this.empBo.searchEmpArray(this.empNo);
        IEmailsendBO sendBo = (IEmailsendBO) getBean("emailsendBO");
        if ((fromEmp == null) || (fromEmp.trim().length() == 0) || (toEmp == null)) {
            addErrorInfo("发件人或收信人Email地址为空!");
            return false;
        }
        for (int i = 0; i < toEmp.size(); ++i) {
            Emailsend newmail = new Emailsend();
            newmail.setEsFrom(fromEmp);
            Employee tmpEmp = (Employee) toEmp.get(i);
            newmail.setEsTo(tmpEmp.getEmpEmail());
            newmail.setEsTitle(transferExp(this.mail.getEsTitle(), tmpEmp, mailSystemName,
                                           mailAdminPhone));
            newmail.setEsContent(transferExp(this.mail.getEsContent(), tmpEmp, mailSystemName,
                                             mailAdminPhone));

            sendBo.addEmailsend(newmail);
        }
        addSuccessInfo(toEmp.size() + " 条邮件保存成功！");
        return true;
    }

    private String transferExp(String str, Employee toEmp, String mailSystemName,
            String mailAdminPhone) {
        str = str.replace("{APPLIER}", toEmp.getEmpName());
        str = str.replace("{SYSNAME}", mailSystemName);
        str = str.replace("{PHONE}", mailAdminPhone);
        return str;
    }

    public List getEmpList() {
        return this.empList;
    }

    public void setEmpList(List empList) {
        this.empList = empList;
    }

    public List getBuList() {
        return this.buList;
    }

    public void setBuList(List buList) {
        this.buList = buList;
    }

    public List getDepartmentList() {
        return this.departmentList;
    }

    public void setDepartmentList(List departmentList) {
        this.departmentList = departmentList;
    }

    public List getLocationList() {
        return this.locationList;
    }

    public void setLocationList(List locationList) {
        this.locationList = locationList;
    }

    public String getOperation() {
        return this.operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String[] getEmpNo() {
        return this.empNo;
    }

    public void setEmpNo(String[] empNo) {
        this.empNo = empNo;
    }

    public Integer getCcEmp() {
        return this.ccEmp;
    }

    public void setCcEmp(Integer ccEmp) {
        this.ccEmp = ccEmp;
    }

    public List getTemplateList() {
        return this.templateList;
    }

    public void setTemplateList(List templateList) {
        this.templateList = templateList;
    }

    public Emailsend getMail() {
        return this.mail;
    }

    public void setMail(Emailsend mail) {
        this.mail = mail;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.action.EmailsendBatch JD-Core Version: 0.5.4
 */