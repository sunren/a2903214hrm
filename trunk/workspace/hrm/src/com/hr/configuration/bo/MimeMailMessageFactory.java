package com.hr.configuration.bo;

import com.hr.configuration.domain.Emailsend;
import com.hr.util.PropertiesFileConfigManager;
import com.hr.util.SysConfigManager;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MimeMailMessageFactory {
    public static MimeMessage createMailMessage(JavaMailSender mailSender, Emailsend send) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            if (StringUtils.isEmpty(send.getEsFrom())) {
                SysConfigManager fileConfigManager = PropertiesFileConfigManager.getInstance();
                String from = fileConfigManager.getProperty("email.sys.sender");
                send.setEsFrom(from);
            }
            helper.setFrom(send.getEsFrom());

            if (StringUtils.isNotEmpty(send.getEsTo())) {
                helper.setTo(send.getEsTo().split(","));
            }

            if (StringUtils.isNotEmpty(send.getEsCc())) {
                helper.setCc(send.getEsCc().split(","));
            }

            helper.setSubject(send.getEsTitle());
            helper.setText(send.getEsContent(), true);
        } catch (MessagingException e) {
        }
        return mimeMessage;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.bo.MimeMailMessageFactory JD-Core Version: 0.5.4
 */