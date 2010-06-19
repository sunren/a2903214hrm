package com.hr.util;

import com.hr.spring.SpringBeanFactory;
import java.text.ParseException;
import java.util.Map;
import java.util.Properties;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.SimpleTriggerBean;

public class SpringBeanObserver {
    public static synchronized void notifySpringBeanFactoryWithEmailChange() {
        PropertiesFileConfigManager manager = PropertiesFileConfigManager.getInstance();
        Map props = manager.getProperties();
        String host = (String) props.get("email.smtp.host");
        String sender = (String) props.get("email.sys.sender");
        String username = (String) props.get("email.user.name");
        String password = (String) props.get("email.user.password");
        String auth = (String) props.get("email.smtp.auth");
        String connectionTimeout = (String) props.get("email.smtp.connectiontimeout");
        String timeout = (String) props.get("email.smtp.timeout");
        String debug = (String) props.get("email.smtp.debug");

        Properties javaMailProperties = new Properties();
        javaMailProperties.setProperty("mail.smtp.auth", auth);
        javaMailProperties.setProperty("mail.smtp.connectiontimeout", connectionTimeout);
        javaMailProperties.setProperty("mail.smtp.timeout", timeout);
        javaMailProperties.setProperty("mail.debug", debug);
        javaMailProperties.setProperty("java,mail.smtp.from", sender);

        JavaMailSenderImpl mailSender = (JavaMailSenderImpl) SpringBeanFactory
                .getBean("mailSender");
        mailSender.setJavaMailProperties(javaMailProperties);
        mailSender.setHost(host);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        String mailSystemName = (String) props.get("email.sys.mailSystemName");
        String mailAdminPhone = (String) props.get("email.sys.mailAdminPhone");
        String url = (String) props.get("email.sys.url");
        Map emailParams = (Map) SpringBeanFactory.getBean("emailTemplateParams");
        emailParams.put("SYSNAME", mailSystemName);
        emailParams.put("PHONE", mailAdminPhone);
        emailParams.put("URL", url);

        SimpleTriggerBean trigger = (SimpleTriggerBean) SpringBeanFactory.getBean("emailTrigger");
        long repeatInterval = Long.valueOf((String) props.get("quartz.emailRepeatInterval"))
                .longValue();
        trigger.setRepeatInterval(repeatInterval);
    }

    public static synchronized void notifySpringBeanFactoryWithQuartzChange() {
        PropertiesFileConfigManager manager = PropertiesFileConfigManager.getInstance();
        String cronExpression = manager.getProperty("quartz.dbBackup");
        CronTriggerBean bean = (CronTriggerBean) SpringBeanFactory.getBean("backupJob");
        try {
            bean.setCronExpression(cronExpression);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.SpringBeanObserver JD-Core Version: 0.5.4
 */