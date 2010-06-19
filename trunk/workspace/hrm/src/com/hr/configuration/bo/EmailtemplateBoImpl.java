package com.hr.configuration.bo;

import com.hr.configuration.dao.IEmailtemplateDAO;
import com.hr.configuration.domain.Emailtemplate;
import com.hr.util.Environment;
import java.io.File;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class EmailtemplateBoImpl implements IEmailtemplateBO {
    private IEmailtemplateDAO dao;
    private TemplateService templateService;

    public EmailtemplateBoImpl() {
        this.dao = null;
    }

    public void setTemplateService(TemplateService templateService) {
        this.templateService = templateService;
    }

    public IEmailtemplateDAO getDao() {
        return this.dao;
    }

    public void setDao(IEmailtemplateDAO dao) {
        this.dao = dao;
    }

    public boolean addEmailtemplate(Emailtemplate one) {
        try {
            this.dao.saveObject(one);
            String savePath = createIfNotExistTemplateFloder();
            this.templateService.createTemplate(one.getEtTitle(), savePath + one.getId()
                    + "-title.ftl");
            this.templateService.createTemplate(one.getEtContent(), savePath + one.getId()
                    + "-body.ftl");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String createIfNotExistTemplateFloder() {
        String savePath = Environment.getFileHome() + this.templateService.getTemplateFilePath();
        File tmp = new File(savePath);
        if (!tmp.exists()) {
            tmp.mkdirs();
        }
        return savePath;
    }

    public Emailtemplate loadEmailtemplate(Class<Emailtemplate> name, String id) {
        Emailtemplate template = (Emailtemplate) this.dao
                .loadObject(name, id, null, new boolean[0]);
        if (template == null) {
            return null;
        }
        String templateNo = template.getEtTitleNo();
        String title = this.templateService.getTemplateContent(templateNo + "_title.ftl");
        String content = this.templateService.getTemplateContent(templateNo + "_body.ftl");
        template.setEtContent(content);
        template.setEtTitle(title);
        return template;
    }

    /** @deprecated */
    public boolean delEmailtemplate(Class<Emailtemplate> name, String id) {
        try {
            Emailtemplate type = (Emailtemplate) this.dao
                    .loadObject(name, id, null, new boolean[0]);
            this.dao.deleteObject(type);
            String path = Environment.getFileHome() + this.templateService.getTemplateFilePath();
            this.templateService.deleteTemplate(path + type.getId() + "_title.ftl");
            this.templateService.deleteTemplate(path + type.getId() + "_body.ftl");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String updateEmailtemplate(Emailtemplate one) {
        Emailtemplate db = loadEmailtemplate(Emailtemplate.class, one.getId());
        if (db == null) {
            return "不存在的模板!";
        }
        db.setEtSendMode(one.getEtSendMode());
        db.setEtStatus(one.getEtStatus());
        this.dao.updateObject(db);

        String templateNo = one.getEtTitleNo();
        String titleError = this.templateService.modifyTemplateContent(templateNo + "_title.ftl",
                                                                       one.getEtTitle());
        String contentError = this.templateService.modifyTemplateContent(templateNo + "_body.ftl",
                                                                         one.getEtContent());
        StringBuffer errorBuffer = new StringBuffer();
        if (titleError != null) {
            errorBuffer.append(titleError);
        }
        if (contentError != null) {
            errorBuffer.append(contentError);
        }
        if (errorBuffer.length() == 0) {
            return null;
        }
        return errorBuffer.toString();
    }

    public List getList() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Emailtemplate.class);
        detachedCriteria.addOrder(Order.asc(Emailtemplate.PROP_ET_TITLE_NO));
        List result = this.dao.findByCriteria(detachedCriteria);
        return result;
    }

    public Emailtemplate getEmailTemplateByNo(String templateNo) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Emailtemplate.class);
        detachedCriteria.add(Restrictions.eq(Emailtemplate.PROP_ET_TITLE_NO, templateNo));
        List result = this.dao.findByCriteria(detachedCriteria);
        return (result.isEmpty()) ? null : (Emailtemplate) result.get(0);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.bo.EmailtemplateBoImpl JD-Core Version: 0.5.4
 */