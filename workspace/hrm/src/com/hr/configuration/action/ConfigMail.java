package com.hr.configuration.action;

import com.hr.base.BaseAction;
import com.hr.util.PropertiesFileConfigManager;
import com.hr.util.SpringBeanObserver;
import com.hr.util.SysConfigManager;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class ConfigMail extends BaseAction {
    private static final long serialVersionUID = 1L;
    private static String SUCC = "SUCC";
    private static String FAIL = "FAIL";
    private String emailRepeat;
    private String email_smtp_host;
    private String email_sys_sender;
    private String email_user_name;
    private String email_user_password;
    private String email_send_pages;
    private String email_sys_url;
    private String email_sys_mailSystemName;
    private String email_sys_mailAdminPhone;
    private String email_smtp_auth;
    private String email_smtp_timeout;
    private SysConfigManager fileConfigManager;

    public ConfigMail() {
        this.fileConfigManager = PropertiesFileConfigManager.getInstance();
    }

    public String showConfigMail() throws Exception {
        if (!hasAuth(961)) {
            addErrorInfo("对不起，您无权进入该页面");
            return "input";
        }
        Map dbMap = this.fileConfigManager.getProperties();

        Long emailSecond = Long.valueOf(new Long((String) dbMap.get("quartz.emailRepeatInterval"))
                .longValue() / 60000L);
        this.emailRepeat = emailSecond.toString();

        this.email_sys_url = ((String) dbMap.get("email.sys.url"));
        this.email_sys_mailSystemName = ((String) dbMap.get("email.sys.mailSystemName"));
        this.email_sys_mailAdminPhone = ((String) dbMap.get("email.sys.mailAdminPhone"));

        this.email_smtp_host = ((String) dbMap.get("email.smtp.host"));
        this.email_sys_sender = ((String) dbMap.get("email.sys.sender"));
        this.email_user_name = ((String) dbMap.get("email.user.name"));
        this.email_user_password = ((String) dbMap.get("email.user.password"));

        this.email_smtp_auth = ((String) dbMap.get("email.smtp.auth"));

        if (this.email_smtp_auth.trim().equalsIgnoreCase("true"))
            this.email_smtp_auth = "1";
        else {
            this.email_smtp_auth = "0";
        }

        String timeout = (String) dbMap.get("email.smtp.timeout");
        int length = timeout.length();
        this.email_smtp_timeout = timeout.substring(0, length - 3);

        this.email_send_pages = ((String) dbMap.get("email.send.pages"));
        return "success";
    }

    public String executeMail() throws Exception {
        if (!hasAuth(961)) {
            addErrorInfo("对不起，您无权进入该页面");
            return "input";
        }
        try {
            Map dbMap = this.fileConfigManager.getProperties();
            if (!((String) dbMap.get("email.sys.sender")).equals(this.email_sys_sender)) {
                this.fileConfigManager.setProperty("email.sys.sender", this.email_sys_sender);
            }

            if (!((String) dbMap.get("email.smtp.host")).equals(this.email_smtp_host)) {
                this.fileConfigManager.setProperty("email.smtp.host", this.email_smtp_host);
            }

            if (!((String) dbMap.get("email.user.name")).equals(this.email_user_name)) {
                this.fileConfigManager.setProperty("email.user.name", this.email_user_name);
            }

            if (!((String) dbMap.get("email.user.password")).equals(this.email_user_password)) {
                this.fileConfigManager.setProperty("email.user.password", this.email_user_password);
            }

            if (!((String) dbMap.get("email.sys.url")).equals(this.email_sys_url)) {
                this.fileConfigManager.setProperty("email.sys.url", this.email_sys_url);
            }

            if (!((String) dbMap.get("email.sys.mailSystemName"))
                    .equals(this.email_sys_mailSystemName)) {
                this.fileConfigManager.setProperty("email.sys.mailSystemName",
                                                   this.email_sys_mailSystemName);
            }

            if (!((String) dbMap.get("email.sys.mailAdminPhone"))
                    .equals(this.email_sys_mailAdminPhone)) {
                this.fileConfigManager.setProperty("email.sys.mailAdminPhone",
                                                   this.email_sys_mailAdminPhone);
            }

            if (this.email_smtp_auth.trim().equals("1"))
                this.email_smtp_auth = "true";
            else {
                this.email_smtp_auth = "false";
            }

            if (!((String) dbMap.get("email.send.pages")).equals(this.email_send_pages)) {
                this.fileConfigManager.setProperty("email.send.pages", this.email_send_pages);
            }

            if (!((String) dbMap.get("email.smtp.auth")).equals(this.email_smtp_auth)) {
                this.fileConfigManager.setProperty("email.smtp.auth", this.email_smtp_auth);
            }

            this.fileConfigManager.setProperty("email.smtp.connectiontimeout",
                                               this.email_smtp_timeout + "000");
            this.fileConfigManager.setProperty("email.smtp.timeout", this.email_smtp_timeout
                    + "000");

            Long emailrepeat = Long.valueOf(new Long(this.emailRepeat).longValue() * 60000L);
            String repeate = emailrepeat.toString();
            if (!((String) dbMap.get("quartz.emailRepeatInterval")).equals(repeate)) {
                this.fileConfigManager.setProperty("quartz.emailRepeatInterval", repeate);
            }

            this.fileConfigManager.saveChange();

            SpringBeanObserver.notifySpringBeanFactoryWithEmailChange();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Visit  for updating  value error");
            return "input";
        }

        return "success";
    }

    public String testMail(String email_smtp_host, String email_smtp_timeout,
            final String email_sys_sender, String email_user_name, String email_user_password,
            String email_smtp_auth) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(email_smtp_host);
        mailSender.setUsername(email_user_name);
        mailSender.setPassword(email_user_password);
        Properties props = new Properties();
        props.put("mail.smtp.auth", email_smtp_auth.trim());
        props.put("mail.smtp.from", email_sys_sender);
        props.put("mail.smtp.connectiontimeout", email_smtp_timeout.trim() + "000");
        if ("smtp.gmail.com".equals(email_smtp_host)) {
            System.out.println("使用gmail邮箱");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.socketFactory.port", "465");
        }

        mailSender.setJavaMailProperties(props);
        try {
            MimeMessagePreparator preparator = new MimeMessagePreparator() {
                String email_sys_sender_in = email_sys_sender;
                public void prepare(MimeMessage mimeMessage) throws MessagingException, IOException {
                    MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "GBK");
                    messageHelper.setTo(email_sys_sender_in);
                    messageHelper.setFrom(email_sys_sender_in);
                    messageHelper.setSubject("365HRM系统邮箱连接测试");
                    messageHelper.setText("系统邮箱测试成功!");
                }
            };
            mailSender.send(preparator);
        } catch (Exception ex) {
            System.out.println("邮件测试失败");
            return FAIL;
        }
        return SUCC;
    }

    public String getEmailRepeat() {
        return this.emailRepeat;
    }

    public void setEmailRepeat(String emailRepeat) {
        this.emailRepeat = emailRepeat;
    }

    public String getEmail_smtp_host() {
        return this.email_smtp_host;
    }

    public void setEmail_smtp_host(String email_smtp_host) {
        this.email_smtp_host = email_smtp_host;
    }

    public String getEmail_sys_sender() {
        return this.email_sys_sender;
    }

    public void setEmail_sys_sender(String email_sys_sender) {
        this.email_sys_sender = email_sys_sender;
    }

    public String getEmail_user_name() {
        return this.email_user_name;
    }

    public void setEmail_user_name(String email_user_name) {
        this.email_user_name = email_user_name;
    }

    public String getEmail_user_password() {
        return this.email_user_password;
    }

    public void setEmail_user_password(String email_user_password) {
        this.email_user_password = email_user_password;
    }

    public String getEmail_send_pages() {
        return this.email_send_pages;
    }

    public void setEmail_send_pages(String email_send_pages) {
        this.email_send_pages = email_send_pages;
    }

    public String getEmail_sys_url() {
        return this.email_sys_url;
    }

    public void setEmail_sys_url(String email_sys_url) {
        this.email_sys_url = email_sys_url;
    }

    public String getEmail_sys_mailSystemName() {
        return this.email_sys_mailSystemName;
    }

    public void setEmail_sys_mailSystemName(String email_sys_mailSystemName) {
        this.email_sys_mailSystemName = email_sys_mailSystemName;
    }

    public String getEmail_sys_mailAdminPhone() {
        return this.email_sys_mailAdminPhone;
    }

    public void setEmail_sys_mailAdminPhone(String email_sys_mailAdminPhone) {
        this.email_sys_mailAdminPhone = email_sys_mailAdminPhone;
    }

    public String getEmail_smtp_auth() {
        return this.email_smtp_auth;
    }

    public void setEmail_smtp_auth(String email_smtp_auth) {
        this.email_smtp_auth = email_smtp_auth;
    }

    public String getEmail_smtp_timeout() {
        return this.email_smtp_timeout;
    }

    public void setEmail_smtp_timeout(String email_smtp_timeout) {
        this.email_smtp_timeout = email_smtp_timeout;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.action.ConfigMail JD-Core Version: 0.5.4
 */