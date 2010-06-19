package com.hr.configuration.bo;

import com.hr.base.Constants;
import com.hr.base.wf.Workflow;
import com.hr.configuration.dao.IEmailsendDAO;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emailsend;
import com.hr.configuration.domain.Emailtemplate;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Overtimerequest;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.security.bo.UserBo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.Pager;
import com.hr.util.PropertiesFileConfigManager;
import com.hr.util.SysConfigManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailsendBoImpl implements IEmailsendBO, Constants {
    private static final Logger logger = Logger.getLogger(EmailsendBoImpl.class);
    private IEmailsendDAO dao;
    private JavaMailSender mailSender;
    private TemplateService emailTemplateService;

    public EmailsendBoImpl() {
        this.dao = null;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setEmailTemplateService(TemplateService emailTemplateService) {
        this.emailTemplateService = emailTemplateService;
    }

    public List FindAllEmailsend() {
        DetachedCriteria dc = DetachedCriteria.forClass(Emailsend.class);
        return this.dao.findByCriteria(dc);
    }

    public boolean addEmailsend(Emailsend one) {
        if (StringUtils.isEmpty(one.getEsFrom())) {
            SysConfigManager fileConfigManager = PropertiesFileConfigManager.getInstance();
            one.setEsFrom(fileConfigManager.getProperty("email.sys.sender"));
        }

        one.setEsCreatetime(new Date());

        one.setEsStatus(Integer.valueOf(0));
        try {
            this.dao.saveObject(one);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delEmailsend(Class<Emailsend> name, String id) {
        try {
            this.dao.deleteObject(name, id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateEmailsend(Emailsend one) {
        Emailsend emailsend = (Emailsend) this.dao.loadObject(Emailsend.class, one.getId(), null,
                                                              new boolean[0]);
        if (emailsend != null) {
            emailsend.setEsStatus(one.getEsStatus());
            emailsend.setEsSendtime(one.getEsSendtime());
            this.dao.updateObject(emailsend);
            return true;
        }
        return false;
    }

    public boolean updateetStatus(String id, Integer status) {
        Emailtemplate emailtemplte = (Emailtemplate) this.dao.loadObject(Emailtemplate.class, id,
                                                                         null, new boolean[0]);
        try {
            if (emailtemplte != null) {
                emailtemplte.setEtStatus(status);
                this.dao.updateObject(emailtemplte);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List getListByPager(Emailsend emailsend, Pager page, Date begainC, Date endC,
            Date begainS, Date endS) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Emailsend.class);
        if ((emailsend.getEsTo() != null) && (emailsend.getEsTo().trim().length() > 0)) {
            detachedCriteria.add(Restrictions.like(Emailsend.PROP_ES_TO, "%"
                    + emailsend.getEsTo().trim() + "%"));
        }
        if ((emailsend.getEsTitle() != null) && (emailsend.getEsTitle().trim().length() > 0)) {
            detachedCriteria.add(Restrictions.like(Emailsend.PROP_ES_TITLE, "%"
                    + emailsend.getEsTitle().trim() + "%"));
        }
        if (emailsend.getEsStatus() != null) {
            detachedCriteria
                    .add(Restrictions.eq(Emailsend.PROP_ES_STATUS, emailsend.getEsStatus()));
        }
        if ((begainC != null) && (endC != null))
            detachedCriteria.add(Restrictions.between(Emailsend.PROP_ES_CREATETIME, begainC, endC));
        else if (begainC != null)
            detachedCriteria.add(Restrictions.ge(Emailsend.PROP_ES_CREATETIME, begainC));
        else if (endC != null)
            detachedCriteria.add(Restrictions.le(Emailsend.PROP_ES_CREATETIME, endC));
        if ((begainS != null) && (endS != null))
            detachedCriteria.add(Restrictions.between(Emailsend.PROP_ES_SENDTIME, begainS, endS));
        else if (begainS != null)
            detachedCriteria.add(Restrictions.ge(Emailsend.PROP_ES_SENDTIME, begainS));
        else if (endS != null)
            detachedCriteria.add(Restrictions.le(Emailsend.PROP_ES_SENDTIME, endS));
        try {
            int size = this.dao.findRowCountByCriteria(detachedCriteria);

            int pageSize = Integer.valueOf(
                                           DatabaseSysConfigManager.getInstance()
                                                   .getProperty("sys.split.pages")).intValue();

            detachedCriteria.setProjection(null);
            page.init(size, pageSize);
            if ((page.getOperate() != null) && ("previous".equalsIgnoreCase(page.getOperate())))
                page.previous();
            if ((page.getOperate() != null) && ("next".equalsIgnoreCase(page.getOperate())))
                page.next();
            if ((page.getOperate() != null) && ("first".equalsIgnoreCase(page.getOperate())))
                page.first();
            if ((page.getOperate() != null) && ("last".equalsIgnoreCase(page.getOperate())))
                page.last();
            checkOrderMethod(detachedCriteria, page.getOrder());
            List result = this.dao
                    .findByCriteria(detachedCriteria, pageSize, page.getCurrentPage());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void checkOrderMethod(DetachedCriteria detachedCriteria, String order) {
        if ((order != null) && (order.trim().length() > 1) && (order.indexOf('-') != -1)) {
            String[] pram = order.split("-");
            if ((pram[0] == null) || (pram[0].length() < 1)) {
                return;
            }
            String[] fetch = pram[0].split("\\.");
            String orde = pram[0];
            if (fetch.length > 1) {
                detachedCriteria.createAlias(pram[0].substring(0, pram[0].lastIndexOf(".")), "ord");
                orde = "ord." + pram[0].substring(pram[0].lastIndexOf(".") + 1);
            }
            if ((pram.length == 2) && (pram[1].equalsIgnoreCase("up")))
                detachedCriteria.addOrder(Order.asc(orde));
            else
                detachedCriteria.addOrder(Order.desc(orde));
        } else {
            detachedCriteria.addOrder(Order.desc("esCreatetime"));
        }
    }

    public void updateSendEmailOut() {
        SysConfigManager fileConfigManager = PropertiesFileConfigManager.getInstance();
        String pages = fileConfigManager.getProperty("email.send.pages");
        int mailSplit = Integer.valueOf(pages).intValue();

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Emailsend.class);
        detachedCriteria.add(Restrictions.eq("esStatus", Integer.valueOf(0)));
        detachedCriteria.add(Restrictions.isNotNull(Emailsend.PROP_ES_TO));
        detachedCriteria.add(Restrictions.ne(Emailsend.PROP_ES_TO, ""));
        List sendList = this.dao.findByCriteria(detachedCriteria, mailSplit, 1);

        sendMail(sendList, 1);
    }

    public void saveAndSendMail(List<Object> objList) {
        List<Emailsend> sendMailList = new ArrayList();
        List<Emailsend> saveMailList = new ArrayList();
        for (Iterator i$ = objList.iterator(); i$.hasNext();) {
            Object obj = i$.next();

            List<Emailsend> mailList = null;
            if (obj instanceof Leaverequest)
                mailList = ((Leaverequest) obj).getWorkflow().getSendList();
            else if (obj instanceof Overtimerequest) {
                mailList = ((Overtimerequest) obj).getWorkflow().getSendList();
            }

            if (mailList != null) {
                for (Emailsend es : mailList) {
                    if ((es.getEsStatus() == null) || (es.getEsStatus().intValue() == 1))
                        sendMailList.add(es);
                    if ((es.getEsStatus().intValue() == 0) || (es.getEsStatus().intValue() == 1)) {
                        saveMailList.add(es);
                    }
                }
            }
        }

        Map mailMap = new HashMap();
        List mailList = new ArrayList();
        for (Emailsend send : sendMailList) {
            if (StringUtils.isEmpty(send.getEsTo())) {
                logger.info("邮件收件人为空：" + send);
            }

            MimeMessage mailMessage = MimeMailMessageFactory.createMailMessage(this.mailSender,
                                                                               send);
            mailMap.put(mailMessage, send);
            mailList.add(mailMessage);
        }

        MimeMessage[] mailArray = (MimeMessage[]) mailList.toArray(new MimeMessage[mailMap.size()]);

        boolean connectError = false;
        try {
            this.mailSender.send(mailArray);
        } catch (MailSendException ex) {
            if (!ex.getFailedMessages().isEmpty()) {
                Map exMap = ex.getFailedMessages();
                for (Object me : exMap.keySet()) {
                    Emailsend send = (Emailsend) mailMap.get(me);
                    if (send.getEsStatus().intValue() == 1) {
                        send.setEsStatus(Integer.valueOf(2));
                    }
                    mailMap.remove(me);
                    String error = "从" + send.getEsFrom() + "往" + send.getEsTo() + "发送邮件失败.";
                    logger.info(error);
                }
            } else {
                String error = "服务器连接异常";
                logger.info(error);
                connectError = true;
            }
        } catch (MailException ex) {
            String error = "服务器连接异常邮箱服务器用户名或密码错误！";
            logger.info(error);
            connectError = true;
        }

        Date sendTime = new Date();
        for (Emailsend send : saveMailList) {
            send.setEsCreatetime(sendTime);

            if (connectError) {
                send.setEsStatus(Integer.valueOf(2));
            }

            if ((send.getEsStatus().intValue() == 1) || (send.getEsStatus().intValue() == 2))
                send.setEsSendtime(sendTime);
        }
        this.dao.saveOrupdate(saveMailList);
    }

    public List<String> sendMail(List<Emailsend> sendList, int sendMode) {
        if (sendList.isEmpty())
            return new ArrayList(0);
        if (sendMode == 2) {
            this.dao.saveOrupdate(sendList);
            return null;
        }

        List errorList = new ArrayList();
        Map<MimeMessage, Emailsend> mailMap = new HashMap();
        List mailList = new ArrayList();
        for (Emailsend send : sendList) {
            if (StringUtils.isEmpty(send.getEsTo())) {
                logger.info("邮件收件人为空：" + send);
            }

            MimeMessage mailMessage = MimeMailMessageFactory.createMailMessage(this.mailSender,
                                                                               send);
            mailMap.put(mailMessage, send);
            mailList.add(mailMessage);
        }

        MimeMessage[] mailArray = (MimeMessage[]) mailList.toArray(new MimeMessage[mailMap.size()]);

        boolean connectError = false;
        try {
            this.mailSender.send(mailArray);
        } catch (MailSendException ex) {
            if (!ex.getFailedMessages().isEmpty()) {
                Map exMap = ex.getFailedMessages();
                for (Object me : exMap.keySet()) {
                    Emailsend send = (Emailsend) mailMap.get(me);
                    if (sendMode == 1) {
                        send.setEsStatus(Integer.valueOf(2));
                        send.setEsSendtime(new Date());
                        this.dao.saveOrupdate(send);
                    }
                    mailMap.remove(me);
                    String error = "从" + send.getEsFrom() + "往" + send.getEsTo() + "发送邮件失败.";
                    logger.info(error);
                    errorList.add(error);
                }
            } else {
                String error = "服务器连接异常";
                logger.info(error);
                errorList.add(error);
                connectError = true;
            }
        } catch (MailException ex) {
            String error = "服务器连接异常邮箱服务器用户名或密码错误！";
            logger.info(error);
            errorList.add(error);
            connectError = true;
        }

        if (sendMode == 1) {
            for (Emailsend send : mailMap.values()) {
                if ((send.getEsStatus() == null) || (2 != send.getEsStatus().intValue()))
                    ;
                if (!connectError) {
                    send.setEsStatus(Integer.valueOf(1));
                    send.setEsSendtime(new Date());
                } else {
                    send.setEsStatus(Integer.valueOf(2));
                    send.setEsSendtime(new Date());
                }
                this.dao.saveOrupdate(send);
            }
        }

        return errorList;
    }

    public String sendEmail(Emailsend send) {
        List emailList = Arrays.asList(new Emailsend[] { send });
        List error = sendMail(emailList);
        if (error.isEmpty()) {
            return null;
        }
        return (String) error.get(0);
    }

    public List<String> sendMail(List<Emailsend> emailList) {
        return sendMail(emailList, 0);
    }

    public boolean sendEmailToDept(String authGMHR, String mailTemplateNo, String empId,
            Map<String, Object> paramUserEmail) {
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        UserBo userService = (UserBo) SpringBeanFactory.getBean("userService");
        IDepartmentBO deptBO = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");

        String[] fetch = { "empDeptNo" };
        Employee currentEmp = empBo.loadEmp(empId, fetch);

        String deptNo = currentEmp.getEmpDeptNo().getId();

        List deptLeaders = new ArrayList();
        List deptLeader = new ArrayList();

        boolean needCc = false;

        if ((authGMHR == null) || (authGMHR.length() == 0)) {
            Department dept = deptBO.loadDepartmentByNo(deptNo, new String[0]);

            deptLeader.add(dept.getDepartmentHead());

            String otherHeads = dept.getDepartmentOtherHeads();
            if (otherHeads != null) {
                needCc = true;
                String[] heads = otherHeads.split(",");
                for (String head : heads)
                    deptLeader.add(head);
            }
        } else {
            deptLeader = userService.getEmpNoByAuth(authGMHR);
        }
        for (int i = 0; i < deptLeader.size(); ++i) {
            deptLeaders.add(empBo.loadEmp(((String) deptLeader.get(i)).toString(), null));
        }
        if (deptLeaders.contains(currentEmp)) {
            return true;
        }

        try {
            Emailsend es = new Emailsend();
            String esTo = "";
            for (int i = 0; i < deptLeaders.size(); ++i) {
                if (i == 0) {
                    esTo = ((Employee) deptLeaders.get(i)).getEmpEmail();
                } else if (needCc) {
                    String esCc = es.getEsCc() + ",";

                    es.setEsCc(esCc + ((Employee) deptLeaders.get(i)).getEmpEmail());
                } else {
                    esTo = ((Employee) deptLeaders.get(i)).getEmpEmail()
                            + (((esTo == null) || (esTo.length() == 0)) ? esTo
                                    : new StringBuilder().append(",").append(esTo).toString());
                }

            }

            paramUserEmail.put("SENDER", currentEmp);
            paramUserEmail.put("MANAGER", deptLeaders.get(0));

            String content = this.emailTemplateService.getContent(mailTemplateNo + "_body",
                                                                  paramUserEmail);
            String title = this.emailTemplateService.getContent(mailTemplateNo + "_title",
                                                                paramUserEmail);

            es.setEsTitle(title);
            es.setEsContent(content);
            es.setEsTo(esTo);

            addEmailsend(es);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String addEmailSend(Emailtemplate template, Emailsend send,
            Map<String, Object> paramUserEmail) {
        List sendList = new ArrayList();
        sendList.add(send);
        List paramUserEmailList = new ArrayList();
        paramUserEmailList.add(paramUserEmail);
        return addEmailSend(template, sendList, paramUserEmailList);
    }

    public String addEmailSend(Emailtemplate template, List<Emailsend> emailList,
            List<Map<String, Object>> paramUserEmailList) {
        if (template.getEtStatus().intValue() == 0) {
            return "模板" + template.getEtTitleNo() + "已停用!";
        }

        StringBuffer sendError = new StringBuffer();

        List sendList = new ArrayList();
        List sendListSave = new ArrayList();
        List saveList = new ArrayList();
        for (int i = 0; i < emailList.size(); ++i) {
            Emailsend send = (Emailsend) emailList.get(i);
            Map emailContent = getEmailContent(template.getEtTitleNo(), (Map) paramUserEmailList
                    .get(i));
            if (emailContent.isEmpty()) {
                sendError.append("模板" + template.getEtTitleNo() + "设置错误，请联系管理员!");
            } else {
                send.setEsTitle(emailContent.get("email_title").toString());
                send.setEsContent(emailContent.get("email_content").toString());

                if ((template.getEtSendMode() == null)
                        || (template.getEtSendMode().intValue() == 0)) {
                    sendList.add(send);
                }

                if (template.getEtSendMode().intValue() == 1) {
                    sendListSave.add(send);
                }

                if (template.getEtSendMode().intValue() == 2) {
                    send.setEsStatus(Integer.valueOf(0));
                    send.setEsCreatetime(new Date());
                    saveList.add(send);
                }
            }
        }
        List<String> errors = sendMail(sendList, 0);
        List<String> errors2 = sendMail(sendListSave, 1);
        this.dao.saveOrupdate(saveList);
        for (String error : errors) {
            sendError.append(error);
        }
        for (String error : errors2) {
            sendError.append(error);
        }
        return sendError.toString();
    }

    public Map<String, String> getEmailContent(String templateNo, Map params) {
        Map result = new HashMap();
        String content = this.emailTemplateService.getContent(templateNo + "_body", params);
        String title = this.emailTemplateService.getContent(templateNo + "_title", params);
        result.put("email_title", title);
        result.put("email_content", content);
        return result;
    }

    public IEmailsendDAO getDao() {
        return this.dao;
    }

    public void setDao(IEmailsendDAO dao) {
        this.dao = dao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.bo.EmailsendBoImpl JD-Core Version: 0.5.4
 */