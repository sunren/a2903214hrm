package com.hr.configuration.action;

import com.hr.base.BaseAction;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.PropertiesFileConfigManager;
import com.hr.util.SysConfigManager;
import java.io.PrintStream;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.mail.Message.RecipientType;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SystemConfig extends BaseAction {
    private static final long serialVersionUID = 1L;
    private String jdbc_driverClassName;
    private String jdbc_url;
    private String jdbc_username;
    private String jdbc_password;
    private String email_sys_url;
    private String email_sys_mailSystemName;
    private String email_sys_mailAdminPhone;
    private String email_smtp_host;
    private String email_sys_sender;
    private String email_user_name;
    private String email_user_password;
    private String email_send_pages;
    private String email_smtp_auth;
    private String email_smtp_timeout;
    private String email_smtp_connectiontimeout;
    private String email_smtp_debug;
    private String sys_split_pages;
    private String sys_sub_window_pages;
    private String sys_profile_file_path;
    private String sys_profile_file_type;
    private String sys_profile_file_length;
    private String sys_user_image_path;
    private String sys_user_image_type;
    private String sys_user_image_length;
    private String sys_saveDir_path;
    private String sys_saveDir_type;
    private String sys_saveDir_length;
    private String sys_information_file_path;
    private String sys_information_file_type;
    private String sys_information_file_length;
    private String sys_information_image_path;
    private String sys_information_image_type;
    private String sys_information_image_length;
    private String sys_training_material_path;
    private String sys_training_material_type;
    private String sys_training_material_length;
    private String sys_recruitment_applier_path;
    private String sys_recruitment_applier_type;
    private String sys_recruitment_applier_length;
    private String logDirectory;
    private String logLevel;
    private String sys_file_path;
    private int sys_examin_gm_lrdays;
    private int sys_examin_gm_othours;
    private String sys_examin_create_level;
    private String sys_examin_update_level;
    private String sys_examin_appove_level;
    private String sys_examin_lr_confirm;
    private String sys_examin_ot_confirm;
    private String sys_examin_month_sum;
    private Integer beginMonth;
    private String beginDay;
    private String sys_salary_tax_min;
    private String sys_salary_tax_range;
    private String sys_salary_tax_rate;
    private String sys_salary_config_update;
    private String emailRepeat;
    private String salaryTotalDate;
    private String salaryTotalHour;
    private String salaryAdjDate;
    private String salaryAdjHour;
    private String empTotalDate;
    private String empTotalHour;
    private SysConfigManager fileConfigManager;
    private SysConfigManager dbConfigManager;
    private String errorMessage;

    public SystemConfig() {
        this.fileConfigManager = PropertiesFileConfigManager.getInstance();
        this.dbConfigManager = DatabaseSysConfigManager.getInstance();
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public String getSys_file_path() {
        return this.sys_file_path;
    }

    public void setSys_file_path(String sys_file_path) {
        this.sys_file_path = sys_file_path;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSys_information_file_path() {
        return this.sys_information_file_path;
    }

    public String executeExamin() throws Exception {
        try {
            this.dbConfigManager.setProperty("sys.examin.gm.lrdays", String
                    .valueOf(this.sys_examin_gm_lrdays));
            this.dbConfigManager.setProperty("sys.examin.gm.othours", String
                    .valueOf(this.sys_examin_gm_othours));
            this.dbConfigManager.setProperty("sys.examin.create.level",
                                             this.sys_examin_create_level);
            this.dbConfigManager.setProperty("sys.examin.update.level",
                                             this.sys_examin_update_level);
            this.dbConfigManager.setProperty("sys.examin.lr.confirm", this.sys_examin_lr_confirm);
            this.dbConfigManager.setProperty("sys.examin.ot.confirm", this.sys_examin_ot_confirm);

            this.sys_examin_month_sum = (Integer.toString(this.beginMonth.intValue()) + "-" + this.beginDay);
            this.dbConfigManager.setProperty("sys.examin.month.sum", this.sys_examin_month_sum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        resetParemeter();
        resetEmailModuleParameters();
        addSuccessInfo("考勤模块参数调整成功＄1�7");
        return "success";
    }

    public String executeTax() throws Exception {
        try {
            this.dbConfigManager.setProperty("sys.salary.tax.min", this.sys_salary_tax_min.trim());
            this.dbConfigManager.setProperty("sys.salary.tax.range", this.sys_salary_tax_range
                    .trim());
            this.dbConfigManager
                    .setProperty("sys.salary.tax.rate", this.sys_salary_tax_rate.trim());
            this.dbConfigManager.setProperty("sys.salary.config.update",
                                             this.sys_salary_config_update.trim());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Visit  for updating  value error");
        }
        resetParemeter();
        resetEmailModuleParameters();
        addSuccessInfo("薪资模块参数调整成功＄1�7");
        return "success";
    }

    public String executeMail() throws Exception {
        try {
            this.fileConfigManager.setProperty("email.sys.sender", this.email_sys_sender.trim());
            this.fileConfigManager.setProperty("email.smtp.host", this.email_smtp_host.trim());
            this.fileConfigManager.setProperty("email.user.name", this.email_user_name.trim());
            this.fileConfigManager.setProperty("email.user.password", this.email_user_password
                    .trim());
            this.fileConfigManager.setProperty("email.sys.url", this.email_sys_url.trim());
            this.fileConfigManager.setProperty("email.sys.mailSystemName",
                                               this.email_sys_mailSystemName.trim());
            this.fileConfigManager.setProperty("email.sys.mailAdminPhone",
                                               this.email_sys_mailAdminPhone.trim());

            this.fileConfigManager.setProperty("email.send.pages", this.email_send_pages.trim());
            this.fileConfigManager.setProperty("email.smtp.auth", this.email_smtp_auth.trim());
            this.fileConfigManager.setProperty("email.smtp.connectiontimeout",
                                               this.email_smtp_timeout.trim() + "000");
            this.fileConfigManager.setProperty("email.smtp.timeout", this.email_smtp_timeout.trim()
                    + "000");

            this.fileConfigManager.saveChange();
            resetParemeter();
            resetEmailModuleParameters();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Visit  for updating  value error");
        }

        addSuccessInfo("邮箱参数调整成功＄1�7");
        return "success";
    }

    public String testMail() {
        resetParemeter();
        Properties props = new Properties();

        props.put("mail.smtp.host", this.email_smtp_host.trim());
        props.put("mail.smtp.auth", this.email_smtp_auth.trim());
        props.put("mail.smtp.connectiontimeout", this.email_smtp_timeout.trim() + "000");
        props.put("mail.smtp.timeout", this.email_smtp_timeout.trim() + "000");

        Session session = Session.getDefaultInstance(props);

        session.setDebug("1".equals(this.fileConfigManager.getProperty("email.smtp.debug")));
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(this.email_sys_sender.trim()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    this.email_sys_sender.trim()));

            message.setSubject("系统邮箱连接测试");
            message.setText("系统邮箱测试成功！");

            message.saveChanges();
            Transport transport = session.getTransport("smtp");
            transport.connect(this.email_smtp_host.trim(), this.email_user_name.trim(),
                              this.email_user_password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            addSuccessInfo("测试成功！");
            return "success";
        } catch (Exception ex) {
            ex.printStackTrace();
            addErrorInfo("连接失败，请检查！");
        }
        return "success";
    }

    public String executeQuartz() throws Exception {
        try {
            Integer emailrepeat = Integer.valueOf(new Integer(this.emailRepeat).intValue() * 60000);
            if ((this.salaryTotalDate == null) || ("".equals(this.salaryTotalDate))) {
                this.salaryTotalDate = "*";
            }
            if ((this.salaryAdjDate == null) || ("".equals(this.salaryAdjDate))) {
                this.salaryAdjDate = "*";
            }
            if ((this.empTotalDate == null) || ("".equals(this.empTotalDate))) {
                this.empTotalDate = "*";
            }
            String salaryTotal = "0 0 " + this.salaryTotalHour + " " + this.salaryTotalDate
                    + " * ?";
            String salaryAdj = "0 0 " + this.salaryAdjHour + " " + this.salaryAdjDate + " * ?";
            String empTotal = "0 0 " + this.empTotalHour + " " + this.empTotalDate + " * ?";
            this.fileConfigManager
                    .setProperty("quartz.emailRepeatInterval", emailrepeat.toString());
            this.fileConfigManager.setProperty("quartz.salaryTotal", salaryTotal);
            this.fileConfigManager.setProperty("quartz.salaryAdj", salaryAdj);
            this.fileConfigManager.setProperty("quartz.empTotal", empTotal);
            resetParemeter();
            resetEmailModuleParameters();

            addSuccessInfo("定时器参数调整成功");
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    public void resetParemeter() {
        this.sys_split_pages = this.dbConfigManager.getProperty("sys.split.pages");
        this.sys_sub_window_pages = this.dbConfigManager.getProperty("sys.sub.window.pages");

        this.sys_examin_gm_lrdays = Integer.parseInt(this.dbConfigManager
                .getProperty("sys.examin.gm.lrdays"));
        this.sys_examin_gm_othours = Integer.parseInt(this.dbConfigManager
                .getProperty("sys.examin.gm.othours"));
        this.sys_examin_create_level = this.dbConfigManager.getProperty("sys.examin.create.level");
        this.sys_examin_update_level = this.dbConfigManager.getProperty("sys.examin.update.level");

        this.sys_examin_lr_confirm = this.dbConfigManager.getProperty("sys.examin.lr.confirm");

        this.sys_examin_ot_confirm = this.dbConfigManager.getProperty("sys.examin.ot.confirm");

        this.sys_examin_month_sum = this.dbConfigManager.getProperty("sys.examin.month.sum");
        String[] strs = this.sys_examin_month_sum.split("-");

        this.beginMonth = Integer.valueOf(Integer.parseInt(strs[0]));

        this.beginDay = strs[1];

        this.sys_salary_tax_min = this.dbConfigManager.getProperty("sys.salary.tax.min");
        this.sys_salary_tax_range = this.dbConfigManager.getProperty("sys.salary.tax.range");
        this.sys_salary_tax_rate = this.dbConfigManager.getProperty("sys.salary.tax.rate");
        this.sys_salary_config_update = this.dbConfigManager
                .getProperty("sys.salary.config.update");

        Integer emailSecond = Integer.valueOf(new Integer(this.fileConfigManager
                .getProperty("quartz.emailRepeatInterval")).intValue() / 60000);
        this.emailRepeat = emailSecond.toString();
        StringTokenizer stotalToken = new StringTokenizer(this.fileConfigManager
                .getProperty("quartz.salaryTotal"));
        stotalToken.nextToken();
        stotalToken.nextToken();
        this.salaryTotalHour = stotalToken.nextToken();
        this.salaryTotalDate = stotalToken.nextToken();

        stotalToken = new StringTokenizer(this.fileConfigManager.getProperty("quartz.salaryAdj"));
        stotalToken.nextToken();
        stotalToken.nextToken();
        this.salaryAdjHour = stotalToken.nextToken();
        this.salaryAdjDate = stotalToken.nextToken();

        stotalToken = new StringTokenizer(this.fileConfigManager.getProperty("quartz.empTotal"));
        stotalToken.nextToken();
        stotalToken.nextToken();
        this.empTotalHour = stotalToken.nextToken();
        this.empTotalDate = stotalToken.nextToken();
    }

    public void resetEmailModuleParameters() {
        this.email_sys_url = this.fileConfigManager.getProperty("email.sys.url");
        this.email_sys_mailSystemName = this.fileConfigManager
                .getProperty("email.sys.mailSystemName");
        this.email_sys_mailAdminPhone = this.fileConfigManager
                .getProperty("email.sys.mailAdminPhone");

        this.email_smtp_host = this.fileConfigManager.getProperty("email.smtp.host");
        this.email_sys_sender = this.fileConfigManager.getProperty("email.sys.sender");
        this.email_user_name = this.fileConfigManager.getProperty("email.user.name");
        this.email_user_password = this.fileConfigManager.getProperty("email.user.password");

        this.email_smtp_auth = this.fileConfigManager.getProperty("email.smtp.auth");
        this.email_smtp_connectiontimeout = this.fileConfigManager
                .getProperty("email.sys.connectiontimeout");
        this.email_smtp_timeout = this.fileConfigManager
                .getProperty("email.smtp.timeout")
                .substring(0, this.fileConfigManager.getProperty("email.smtp.timeout").length() - 3);
        this.email_smtp_debug = this.fileConfigManager.getProperty("email.smtp.debug");

        this.email_send_pages = this.fileConfigManager.getProperty("email.send.pages");
    }

    public String getEmail_send_pages() {
        return this.email_send_pages;
    }

    public void setEmail_send_pages(String email_send_pages) {
        this.email_send_pages = email_send_pages;
    }

    public String getEmail_smtp_host() {
        return this.email_smtp_host;
    }

    public void setEmail_smtp_host(String email_smtp_host) {
        this.email_smtp_host = email_smtp_host;
    }

    public String getEmail_sys_mailAdminPhone() {
        return this.email_sys_mailAdminPhone;
    }

    public void setEmail_sys_mailAdminPhone(String email_sys_mailAdminPhone) {
        this.email_sys_mailAdminPhone = email_sys_mailAdminPhone;
    }

    public String getEmail_sys_mailSystemName() {
        return this.email_sys_mailSystemName;
    }

    public void setEmail_sys_mailSystemName(String email_sys_mailSystemName) {
        this.email_sys_mailSystemName = email_sys_mailSystemName;
    }

    public String getEmail_sys_sender() {
        return this.email_sys_sender;
    }

    public void setEmail_sys_sender(String email_sys_sender) {
        this.email_sys_sender = email_sys_sender;
    }

    public String getEmail_sys_url() {
        return this.email_sys_url;
    }

    public void setEmail_sys_url(String email_sys_url) {
        this.email_sys_url = email_sys_url;
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

    public String getJdbc_driverClassName() {
        return this.jdbc_driverClassName;
    }

    public void setJdbc_driverClassName(String jdbc_driverClassName) {
        this.jdbc_driverClassName = jdbc_driverClassName;
    }

    public String getJdbc_password() {
        return this.jdbc_password;
    }

    public void setJdbc_password(String jdbc_password) {
        this.jdbc_password = jdbc_password;
    }

    public String getJdbc_url() {
        return this.jdbc_url;
    }

    public void setJdbc_url(String jdbc_url) {
        this.jdbc_url = jdbc_url;
    }

    public String getJdbc_username() {
        return this.jdbc_username;
    }

    public void setJdbc_username(String jdbc_username) {
        this.jdbc_username = jdbc_username;
    }

    public String getLogDirectory() {
        return this.logDirectory;
    }

    public void setLogDirectory(String logDirectory) {
        this.logDirectory = logDirectory;
    }

    public String getLogLevel() {
        return this.logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getSys_information_file_length() {
        return this.sys_information_file_length;
    }

    public void setSys_information_file_length(String sys_information_file_length) {
        this.sys_information_file_length = sys_information_file_length;
    }

    public String getSys_information_file_() {
        return this.sys_information_file_path;
    }

    public void setSys_information_file_path(String sys_information_file_path) {
        this.sys_information_file_path = sys_information_file_path;
    }

    public String getSys_information_file_type() {
        return this.sys_information_file_type;
    }

    public void setSys_information_file_type(String sys_information_file_type) {
        this.sys_information_file_type = sys_information_file_type;
    }

    public String getSys_information_image_length() {
        return this.sys_information_image_length;
    }

    public void setSys_information_image_length(String sys_information_image_length) {
        this.sys_information_image_length = sys_information_image_length;
    }

    public String getSys_information_image_path() {
        return this.sys_information_image_path;
    }

    public void setSys_information_image_path(String sys_information_image_path) {
        this.sys_information_image_path = sys_information_image_path;
    }

    public String getSys_information_image_type() {
        return this.sys_information_image_type;
    }

    public void setSys_information_image_type(String sys_information_image_type) {
        this.sys_information_image_type = sys_information_image_type;
    }

    public String getSys_profile_file_length() {
        return this.sys_profile_file_length;
    }

    public void setSys_profile_file_length(String sys_profile_file_length) {
        this.sys_profile_file_length = sys_profile_file_length;
    }

    public String getSys_profile_file_path() {
        return this.sys_profile_file_path;
    }

    public void setSys_profile_file_path(String sys_profile_file_path) {
        this.sys_profile_file_path = sys_profile_file_path;
    }

    public String getSys_profile_file_type() {
        return this.sys_profile_file_type;
    }

    public void setSys_profile_file_type(String sys_profile_file_type) {
        this.sys_profile_file_type = sys_profile_file_type;
    }

    public String getSys_recruitment_applier_length() {
        return this.sys_recruitment_applier_length;
    }

    public void setSys_recruitment_applier_length(String sys_recruitment_applier_length) {
        this.sys_recruitment_applier_length = sys_recruitment_applier_length;
    }

    public String getSys_recruitment_applier_path() {
        return this.sys_recruitment_applier_path;
    }

    public void setSys_recruitment_applier_path(String sys_recruitment_applier_path) {
        this.sys_recruitment_applier_path = sys_recruitment_applier_path;
    }

    public String getSys_recruitment_applier_type() {
        return this.sys_recruitment_applier_type;
    }

    public void setSys_recruitment_applier_type(String sys_recruitment_applier_type) {
        this.sys_recruitment_applier_type = sys_recruitment_applier_type;
    }

    public String getSys_saveDir_length() {
        return this.sys_saveDir_length;
    }

    public void setSys_saveDir_length(String sys_saveDir_length) {
        this.sys_saveDir_length = sys_saveDir_length;
    }

    public String getSys_saveDir_path() {
        return this.sys_saveDir_path;
    }

    public void setSys_saveDir_path(String sys_saveDir_path) {
        this.sys_saveDir_path = sys_saveDir_path;
    }

    public String getSys_saveDir_type() {
        return this.sys_saveDir_type;
    }

    public void setSys_saveDir_type(String sys_saveDir_type) {
        this.sys_saveDir_type = sys_saveDir_type;
    }

    public String getSys_split_pages() {
        return this.sys_split_pages;
    }

    public void setSys_split_pages(String sys_split_pages) {
        this.sys_split_pages = sys_split_pages;
    }

    public String getSys_sub_window_pages() {
        return this.sys_sub_window_pages;
    }

    public void setSys_sub_window_pages(String sys_sub_window_pages) {
        this.sys_sub_window_pages = sys_sub_window_pages;
    }

    public String getSys_training_material_length() {
        return this.sys_training_material_length;
    }

    public void setSys_training_material_length(String sys_training_material_length) {
        this.sys_training_material_length = sys_training_material_length;
    }

    public String getSys_training_material_path() {
        return this.sys_training_material_path;
    }

    public void setSys_training_material_path(String sys_training_material_path) {
        this.sys_training_material_path = sys_training_material_path;
    }

    public String getSys_training_material_type() {
        return this.sys_training_material_type;
    }

    public void setSys_training_material_type(String sys_training_material_type) {
        this.sys_training_material_type = sys_training_material_type;
    }

    public String getSys_user_image_length() {
        return this.sys_user_image_length;
    }

    public void setSys_user_image_length(String sys_user_image_length) {
        this.sys_user_image_length = sys_user_image_length;
    }

    public String getSys_user_image_path() {
        return this.sys_user_image_path;
    }

    public void setSys_user_image_path(String sys_user_image_path) {
        this.sys_user_image_path = sys_user_image_path;
    }

    public String getSys_user_image_type() {
        return this.sys_user_image_type;
    }

    public void setSys_user_image_type(String sys_user_image_type) {
        this.sys_user_image_type = sys_user_image_type;
    }

    public int getSys_examin_gm_lrdays() {
        return this.sys_examin_gm_lrdays;
    }

    public void setSys_examin_gm_lrdays(int sys_examin_gm_lrdays) {
        this.sys_examin_gm_lrdays = sys_examin_gm_lrdays;
    }

    public int getSys_examin_gm_othours() {
        return this.sys_examin_gm_othours;
    }

    public void setSys_examin_gm_othours(int sys_examin_gm_othours) {
        this.sys_examin_gm_othours = sys_examin_gm_othours;
    }

    public String getSys_salary_tax_min() {
        return this.sys_salary_tax_min;
    }

    public void setSys_salary_tax_min(String sys_salary_tax_min) {
        this.sys_salary_tax_min = sys_salary_tax_min;
    }

    public String getSys_salary_tax_range() {
        return this.sys_salary_tax_range;
    }

    public void setSys_salary_tax_range(String sys_salary_tax_range) {
        this.sys_salary_tax_range = sys_salary_tax_range;
    }

    public String getSys_salary_tax_rate() {
        return this.sys_salary_tax_rate;
    }

    public void setSys_salary_tax_rate(String sys_salary_tax_rate) {
        this.sys_salary_tax_rate = sys_salary_tax_rate;
    }

    public String getEmailRepeat() {
        return this.emailRepeat;
    }

    public void setEmailRepeat(String emailRepeat) {
        this.emailRepeat = emailRepeat;
    }

    public String getSalaryTotalDate() {
        return this.salaryTotalDate;
    }

    public void setSalaryTotalDate(String salaryTotalDate) {
        this.salaryTotalDate = salaryTotalDate;
    }

    public String getSalaryTotalHour() {
        return this.salaryTotalHour;
    }

    public void setSalaryTotalHour(String salaryTotalHour) {
        this.salaryTotalHour = salaryTotalHour;
    }

    public String getSalaryAdjDate() {
        return this.salaryAdjDate;
    }

    public void setSalaryAdjDate(String salaryAdjDate) {
        this.salaryAdjDate = salaryAdjDate;
    }

    public String getSalaryAdjHour() {
        return this.salaryAdjHour;
    }

    public void setSalaryAdjHour(String salaryAdjHour) {
        this.salaryAdjHour = salaryAdjHour;
    }

    public String getEmpTotalDate() {
        return this.empTotalDate;
    }

    public void setEmpTotalDate(String empTotalDate) {
        this.empTotalDate = empTotalDate;
    }

    public String getEmpTotalHour() {
        return this.empTotalHour;
    }

    public void setEmpTotalHour(String empTotalHour) {
        this.empTotalHour = empTotalHour;
    }

    public String getEmail_smtp_auth() {
        return this.email_smtp_auth;
    }

    public void setEmail_smtp_auth(String email_smtp_auth) {
        this.email_smtp_auth = email_smtp_auth;
    }

    public String getEmail_smtp_connectiontimeout() {
        return this.email_smtp_connectiontimeout;
    }

    public void setEmail_smtp_connectiontimeout(String email_smtp_connectiontimeout) {
        this.email_smtp_connectiontimeout = email_smtp_connectiontimeout;
    }

    public String getEmail_smtp_debug() {
        return this.email_smtp_debug;
    }

    public void setEmail_smtp_debug(String email_smtp_debug) {
        this.email_smtp_debug = email_smtp_debug;
    }

    public String getEmail_smtp_timeout() {
        return this.email_smtp_timeout;
    }

    public void setEmail_smtp_timeout(String email_smtp_timeout) {
        this.email_smtp_timeout = email_smtp_timeout;
    }

    public String getSys_examin_update_level() {
        return this.sys_examin_update_level;
    }

    public void setSys_examin_update_level(String sys_examin_update_level) {
        this.sys_examin_update_level = sys_examin_update_level;
    }

    public String getSys_examin_appove_level() {
        return this.sys_examin_appove_level;
    }

    public void setSys_examin_appove_level(String sys_examin_appove_level) {
        this.sys_examin_appove_level = sys_examin_appove_level;
    }

    public String getSys_examin_create_level() {
        return this.sys_examin_create_level;
    }

    public void setSys_examin_create_level(String sys_examin_create_level) {
        this.sys_examin_create_level = sys_examin_create_level;
    }

    public Integer getBeginMonth() {
        return this.beginMonth;
    }

    public void setBeginMonth(Integer beginMonth) {
        this.beginMonth = beginMonth;
    }

    public String getBeginDay() {
        return this.beginDay;
    }

    public void setBeginDay(String beginDay) {
        this.beginDay = beginDay;
    }

    public String getSys_examin_lr_confirm() {
        return this.sys_examin_lr_confirm;
    }

    public void setSys_examin_lr_confirm(String sys_examin_lr_confirm) {
        this.sys_examin_lr_confirm = sys_examin_lr_confirm;
    }

    public String getSys_examin_ot_confirm() {
        return this.sys_examin_ot_confirm;
    }

    public void setSys_examin_ot_confirm(String sys_examin_ot_confirm) {
        this.sys_examin_ot_confirm = sys_examin_ot_confirm;
    }

    public SysConfigManager getFileConfigManager() {
        return this.fileConfigManager;
    }

    public void setFileConfigManager(SysConfigManager fileConfigManager) {
        this.fileConfigManager = fileConfigManager;
    }

    public String getSys_salary_config_update() {
        return this.sys_salary_config_update;
    }

    public void setSys_salary_config_update(String sys_salary_config_update) {
        this.sys_salary_config_update = sys_salary_config_update;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.action.SystemConfig JD-Core Version: 0.5.4
 */