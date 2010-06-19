package com.hr.training.action;

import com.hr.base.BaseAction;
import com.hr.base.FileOperate;
import com.hr.configuration.bo.IEmailsendBO;
import com.hr.configuration.bo.IEmailtemplateBO;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.configuration.domain.Emailsend;
import com.hr.configuration.domain.Emailtemplate;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.training.bo.ITremployeeplanBO;
import com.hr.training.domain.Tremployeeplan;
import com.hr.util.Pager;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TrepList extends BaseAction {
    private static final Log log = LogFactory.getLog(TrepList.class);
    private static final long serialVersionUID = 1L;
    private List trepList;
    private String[] checkbox;
    private String emp;
    private String trcName;
    private String trcpLocation;
    private Integer trpStatus;
    private Pager page;
    private String trepId;
    private String comments;
    private String fileFileName;
    private File file;

    public TrepList() {
        this.page = new Pager();
    }

    public String execute() {
        IEmployeeBo empBo = (IEmployeeBo) BaseAction.getBean("empBo");
        ITremployeeplanBO tremployeeplanBO = (ITremployeeplanBO) getBean("tremployeeplanBO");
        if ((this.trpStatus != null) && (this.trpStatus.intValue() == 0)) {
            this.trpStatus = null;
        }

        if ("DEPT".equalsIgnoreCase(this.authorityCondition)) {
            String[] depts = getCurrentEmp().getDeptInChargeOld();
            this.trepList = tremployeeplanBO.search(depts, this.emp, this.trcName, "",
                                                    this.trcpLocation, this.trpStatus, this.page);
        } else {
            this.trepList = tremployeeplanBO.search(null, this.emp, this.trcName, "",
                                                    this.trcpLocation, this.trpStatus, this.page);
        }

        return "success";
    }

    public String cancelTrep() {
        if (this.trepId == null) {
            return "params_error";
        }

        ITremployeeplanBO tremployeeplanBO = (ITremployeeplanBO) getBean("tremployeeplanBO");
        Tremployeeplan trep = tremployeeplanBO.loadById(this.trepId.toString());
        if (trep == null) {
            addActionError("该培训计划不存在＄1�7");
            return "error";
        }

        if (trep.getTrpStatus().intValue() == 22) {
            return "repeat";
        }
        trep.setTrpStatus(new Integer(22));
        trep.setTrpLastChangeBy(getCurrentEmp());
        trep.setTrpLastChangeTime(new Date());
        tremployeeplanBO.saveOrupdate(trep);
        addActionMessage("操作成功〄1�7");
        try {
            ISysLogBO logBO = (ISysLogBO) BaseAction.getBean("logBO");
            logBO.addToSyslog("tremployeeplan", getCurrentEmpNo(), trep.getTrpTraineeNo().getId(),
                              this.trepId.toString(), 0, "作废", this.comments);

            this.comments = null;

            IEmailtemplateBO templateBo = (IEmailtemplateBO) SpringBeanFactory
                    .getBean("emailtemplateBO");
            String templateNo = "TREPCancel";
            Emailtemplate template = templateBo.getEmailTemplateByNo(templateNo);
            if (template == null) {
                log.error("Cant find template '" + templateNo + "'.");
                return "success";
            }
            IEmailsendBO emailsendBo = (IEmailsendBO) getBean("emailsendBO");
            Map paramUserEmail = new HashMap();
            paramUserEmail.put("SENDER", getCurrentEmp());
            paramUserEmail.put("APPLIER", trep.getTrpTraineeNo());
            Emailsend send = new Emailsend();
            send.setEsTo(trep.getTrpTraineeNo().getEmpEmail());
            String error = emailsendBo.addEmailSend(template, send, paramUserEmail);
            log.info(error);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "success";
    }

    public static String getTreStatus(int statusNo) {
        return TrepStatus.getTreStatus(statusNo);
    }

    public String feedback() {
        if (this.trepId == null) {
            return "params_error";
        }

        ITremployeeplanBO tremployeeplanBO = (ITremployeeplanBO) getBean("tremployeeplanBO");
        Tremployeeplan trep = tremployeeplanBO.loadById(this.trepId.toString());
        if (trep == null) {
            addActionError("培训计划" + this.trepId + "不存在！");
            return "error";
        }

        if ((((this.comments == null) || (this.comments.trim().equals(""))))
                && (((this.fileFileName == null) || (this.fileFileName.trim().length() == 0)))) {
            addActionError("备注或附件必须填写其中之丄1�7");
            return "error";
        }

        if ((this.fileFileName != null) && (this.fileFileName.trim().length() > 0)) {
            String newFileName = upload(this.fileFileName);
            try {
                if (newFileName.equals("error")) {
                    addActionError("上传文件失败，请重试＄1�7");
                    return "error";
                }
            } catch (Exception e) {
                e.printStackTrace();
                addActionError("上传文件失败，请重试＄1�7");
                return "error";
            }
            trep.setTrpFileName(newFileName);
        }
        trep.setTrpComments(this.comments);
        trep.setTrpStatus(Integer.valueOf(31));
        tremployeeplanBO.saveOrupdate(trep);

        ISysLogBO logBO = (ISysLogBO) BaseAction.getBean("logBO");
        logBO.addToSyslog("tremployeeplan", getCurrentEmpNo(), trep.getTrpTraineeNo().getId(), trep
                .getTrpId().toString(), 0, "反馈", this.comments);

        this.comments = null;
        addActionMessage("添加反馈成功〄1�7");
        return "success";
    }

    private String upload(String name) {
        String postfix = name.substring(name.lastIndexOf("."));
        String fileName = UUID.randomUUID() + postfix;

        String pathConfig = "sys.training.material.path";
        String typeConfig = "sys.training.material.type";
        String lengthConfig = "sys.training.material.length";
        try {
            if (!FileOperate.buildFile(this.file, pathConfig, fileName, typeConfig, lengthConfig)
                    .equals("success"))
                return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return fileName;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return this.fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String[] getCheckbox() {
        return this.checkbox;
    }

    public void setCheckbox(String[] checkbox) {
        this.checkbox = checkbox;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public List getTrepList() {
        return this.trepList;
    }

    public void setTrepList(List trepList) {
        this.trepList = trepList;
    }

    public String getEmp() {
        return this.emp;
    }

    public void setEmp(String emp) {
        this.emp = emp;
    }

    public String getTrcpLocation() {
        return this.trcpLocation;
    }

    public void setTrcpLocation(String trcpLocation) {
        this.trcpLocation = trcpLocation;
    }

    public String getTrcName() {
        return this.trcName;
    }

    public void setTrcName(String trcName) {
        this.trcName = trcName;
    }

    public Integer getTrpStatus() {
        return this.trpStatus;
    }

    public void setTrpStatus(Integer trpStatus) {
        this.trpStatus = trpStatus;
    }

    public String getTrepId() {
        return this.trepId;
    }

    public void setTrepId(String trepId) {
        this.trepId = trepId;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.training.action.TrepList JD-Core Version: 0.5.4
 */