package com.hr.util;

import java.io.PrintStream;
import java.net.InetAddress;
import java.util.Map;
import java.util.Properties;
import javax.mail.Message.RecipientType;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
    private Session mailSession;
    private Transport transport;
    private String mailTo;
    private String mailCc;
    private String mailFrom;
    private String mailSubject;
    private String mailText;

    public SendMail() {
        SysConfigManager sysConfigManager = PropertiesFileConfigManager.getInstance();
        Map fileMap = sysConfigManager.getProperties();
        String smptIp = null;
        try {
            smptIp = InetAddress.getByName((String) fileMap.get("email.smtp.host")).toString();
        } catch (Exception e) {
        }
        this.mailSession = null;
        Properties mailProps = new Properties();
        mailProps.put("mail.smtp.auth", fileMap.get("email.smtp.auth"));
        mailProps.put("mail.smtp.connectiontimeout", fileMap.get("email.smtp.connectiontimeout"));
        mailProps.put("mail.smtp.timeout", fileMap.get("email.smtp.timeout"));
        if (smptIp != null) {
            mailProps.put("java,mail.smtp.from", smptIp);
        }
        this.mailSession = Session.getDefaultInstance(mailProps);
        this.mailSession.setDebug("1".equals(fileMap.get("email.smtp.debug")));
    }

    public boolean openConnect(String smtpHost, String smtpUser, String smtpPasswd) {
        try {
            this.transport = this.mailSession.getTransport("smtp");
            this.transport.connect(smtpHost, smtpUser, smtpPasswd);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public void closeConnect() {
        try {
            this.transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean send() {
        InternetAddress[] internetAddress;
        try {
            MimeMessage message = new MimeMessage(this.mailSession);
            message.setFrom(new InternetAddress(getMailFrom()));

            internetAddress = InternetAddress.parse(getMailTo());
            message.setRecipients(Message.RecipientType.TO, internetAddress);
            if ((this.mailCc != null) && (this.mailCc.trim().length() > 0)) {
                internetAddress = InternetAddress.parse(getMailCc());
                message.setRecipients(Message.RecipientType.CC, internetAddress);
            }

            message.setSubject(getMailSubject(), "gbk");
            message.setText(getMailText());
            message.setHeader("Mime-Version", "1.0");
            message.setHeader("Content-Type", "text/html; charset=gbk");
            message.setHeader("Content-Transfer-Encoding", "quoted-printable");
            message.setHeader("X-Mailer", "sendhtml");

            message.saveChanges();
            this.transport.sendMessage(message, message.getAllRecipients());

            return true;
        } catch (Exception e) {
            System.out.println(e);

            return false;
        } finally {
            setMailFrom(null);
            setMailSubject(null);
            setMailText(null);
            setMailTo(null);
            setMailCc(null);
        }
    }

    public String getMailTo() {
        return this.mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getMailFrom() {
        return this.mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getMailSubject() {
        return this.mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getMailText() {
        return this.mailText;
    }

    public void setMailText(String mailText) {
        this.mailText = mailText;
    }

    public String getMailCc() {
        return this.mailCc;
    }

    public void setMailCc(String mailCc) {
        this.mailCc = mailCc;
    }

    public static void main(String[] arg) {
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name: com.hr.util.SendMail
 * JD-Core Version: 0.5.4
 */