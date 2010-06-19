package com.hr.base.quartz;

import com.hr.configuration.bo.IEmailsendBO;
import com.hr.security.web.action.LoginAction;
import com.hr.spring.SpringBeanFactory;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class EmailSend implements Job {
    static Logger logger = Logger.getLogger(LoginAction.class.getName());

    public void execute() throws JobExecutionException {
        IEmailsendBO es_BO = (IEmailsendBO) SpringBeanFactory.getBean("emailsendBO");
        logger.info("弄1�7始发送邮仄1�7");
        es_BO.updateSendEmailOut();
        logger.info("发�1�7�邮件结杄1�7!");
    }

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.base.quartz.EmailSend JD-Core Version: 0.5.4
 */