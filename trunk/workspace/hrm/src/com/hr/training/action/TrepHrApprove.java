package com.hr.training.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.configuration.bo.IEmailsendBO;
import com.hr.configuration.bo.IEmailtemplateBO;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.configuration.domain.Emailsend;
import com.hr.configuration.domain.Emailtemplate;
import com.hr.profile.domain.Employee;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.hr.training.bo.ITremployeeplanBO;
import com.hr.training.domain.Tremployeeplan;
import com.hr.util.Pager;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TrepHrApprove extends BaseAction {
    private static final Log log = LogFactory.getLog(TrepHrApprove.class);
    private List trepList;
    private String emp;
    private String trcName;
    private String trcpLocation;
    private Pager page;

    public TrepHrApprove() {
        this.page = new Pager();
    }

    public String approve(String trepId, String comments, HttpSession session) {
        String result = DWRUtil.checkAuth("trepHrApprove", "execute");
        if (("HR".equals(result)) || (result == null)) {
            ITremployeeplanBO tremployeeplanBO = (ITremployeeplanBO) getBean("tremployeeplanBO");
            if (trepId == null) {
                return "培训计划编号不对！请核实";
            }
            Tremployeeplan trep = tremployeeplanBO.loadById(trepId.toString());
            if (trep == null) {
                return "该培训计划不存在！";
            }
            trep.setTrpStatus(new Integer(12));
            trep.setTrpLastChangeBy(getCurrentEmp());
            trep.setTrpLastChangeTime(new Date());
            tremployeeplanBO.saveOrupdate(trep);
            try {
                String userId = ((Userinfo) session.getAttribute("userinfo")).getId();

                ISysLogBO logBo = (ISysLogBO) getBean("logBO");
                logBo.addToSyslog("tremployeeplan", userId, trep.getTrpTraineeNo().getId(), trepId
                        .toString(), 0, "HR批准", comments);

                String templateNo = "TREPApprove";
                Emailtemplate template = getTemplate(templateNo);
                if (template == null) {
                    return "id" + trep.getTrpId() + "ag";
                }
                Employee emp = tremployeeplanBO.loadById(trepId.toString()).getTrpCreateBy();
                IEmailsendBO emailsendBo = (IEmailsendBO) getBean("emailsendBO");
                Map paramUserEmail = new HashMap();
                paramUserEmail.put("SENDER", getCurrentEmp());
                paramUserEmail.put("APPLIER", emp);
                paramUserEmail.put("URL", "recruitment/SearchRecruitplan.action");
                Emailsend send = new Emailsend();
                send.setEsTo(emp.getEmpEmail());
                String error = emailsendBo.addEmailSend(template, send, paramUserEmail);
                log.info(error);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "id" + trep.getTrpId() + "ag";
        }
        return null;
    }

    private Emailtemplate getTemplate(String templateNo) {
        IEmailtemplateBO templateBo = (IEmailtemplateBO) SpringBeanFactory
                .getBean("emailtemplateBO");
        Emailtemplate template = templateBo.getEmailTemplateByNo(templateNo);
        if (template == null) {
            log.error("Cant find template '" + templateNo + "'.");
        }
        return template;
    }

    public String reject(String trepId, String comments, HttpSession session) {
        String result = DWRUtil.checkAuth("trepHrApprove", "execute");
        if (("HR".equals(result)) || (result == null)) {
            ITremployeeplanBO tremployeeplanBO = (ITremployeeplanBO) getBean("tremployeeplanBO");
            if (trepId == null) {
                return "培训计划编号不对！请核实";
            }
            Tremployeeplan trep = tremployeeplanBO.loadById(trepId.toString());
            if (trep == null) {
                return "该培训计划不存在！";
            }
            trep.setTrpStatus(new Integer(21));
            trep.setTrpLastChangeBy(getCurrentEmp());
            trep.setTrpLastChangeTime(new Date());
            tremployeeplanBO.saveOrupdate(trep);
            try {
                ISysLogBO logBo = (ISysLogBO) getBean("logBO");
                logBo.addToSyslog("tremployeeplan", getCurrentEmpNo(), trep.getTrpTraineeNo()
                        .getId(), trepId.toString(), 0, "HR拒绝", comments);

                String templateNo = "TREPReject";
                Emailtemplate template = getTemplate(templateNo);
                if (template == null) {
                    return "id" + trep.getTrpId() + "re";
                }
                Employee emp = tremployeeplanBO.loadById(trepId.toString()).getTrpCreateBy();
                IEmailsendBO emailsendBo = (IEmailsendBO) getBean("emailsendBO");
                Map paramUserEmail = new HashMap();
                paramUserEmail.put("SENDER", getCurrentEmp());
                paramUserEmail.put("APPLIER", emp);
                Emailsend send = new Emailsend();
                send.setEsTo(emp.getEmpEmail());
                String error = emailsendBo.addEmailSend(template, send, paramUserEmail);
                log.info(error);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "id" + trep.getTrpId() + "re";
        }
        return null;
    }

    public String batchApprove(String[] trepIds, HttpSession session) {
        String result = DWRUtil.checkAuth("trepHrApprove", "execute");
        if (("HR".equals(result)) || (result == null)) {
            ITremployeeplanBO tremployeeplanBO = (ITremployeeplanBO) getBean("tremployeeplanBO");
            StringBuffer sb = new StringBuffer();
            StringBuffer sbSuccess = new StringBuffer();

            String empNo = ((Userinfo) session.getAttribute("userinfo")).getId();
            if (trepIds != null) {
                String userId = ((Userinfo) session.getAttribute("userinfo")).getId();
                ISysLogBO logBO = (ISysLogBO) BaseAction.getBean("logBO");
                IEmailsendBO emailsendBo = (IEmailsendBO) getBean("emailsendBO");

                String templateNo = "TREPApprove";
                Emailtemplate template = getTemplate(templateNo);

                for (int i = 0; i < trepIds.length; ++i) {
                    Tremployeeplan TempPlan = tremployeeplanBO.loadById(trepIds[i].trim());
                    if (TempPlan.getTrpStatus().intValue() != 11) {
                        sb.append(TempPlan.getTrpId() + ",");
                    } else {
                        tremployeeplanBO.batchSetStatus(trepIds[i].trim(), 12, empNo);
                        sbSuccess.append(TempPlan.getTrpId() + ",");

                        String treId = tremployeeplanBO.loadById(trepIds[i]).getTrpTraineeNo()
                                .getId();
                        logBO.addToSyslog("tremployeeplan", userId, treId, trepIds[i], 0, "HR批准",
                                          null);

                        if (template != null) {
                            Employee emp = tremployeeplanBO.loadById(TempPlan.getTrpId())
                                    .getTrpCreateBy();
                            Map paramUserEmail = new HashMap();
                            paramUserEmail.put("SENDER", getCurrentEmp());
                            paramUserEmail.put("APPLIER", emp);
                            Emailsend send = new Emailsend();
                            send.setEsTo(emp.getEmpEmail());
                            String error = emailsendBo.addEmailSend(template, send, paramUserEmail);
                            log.info(error);
                        }
                    }
                }
            }
            if (sb.length() > 0) {
                return "友情提示：" + sb.toString().substring(0, sb.length() - 1)
                        + "的培训申请由于状态已经不在部门已审状态，没有审批通过";
            }

            return "所选培训申请已经全部审批通过";
        }

        return null;
    }

    public String execute() {
        ITremployeeplanBO tremployeeplanBO = (ITremployeeplanBO) BaseAction
                .getBean("tremployeeplanBO");
        this.trepList = tremployeeplanBO.search(null, this.emp, this.trcName, null,
                                                this.trcpLocation, new Integer(11), this.page);

        return "success";
    }

    public String trepHrApproveInit() {
        ITremployeeplanBO tremployeeplanBO = (ITremployeeplanBO) BaseAction
                .getBean("tremployeeplanBO");
        this.trepList = tremployeeplanBO.search(null, null, null, null, null, new Integer(11),
                                                this.page);

        return "success";
    }

    public static String getTreStatus(int statusNo) {
        return TrepStatus.getTreStatus(statusNo);
    }

    public List getTrepList() {
        return this.trepList;
    }

    public void setTrepList(List trepList) {
        this.trepList = trepList;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public String getEmp() {
        return this.emp;
    }

    public void setEmp(String emp) {
        this.emp = emp;
    }

    public String getTrcName() {
        return this.trcName;
    }

    public void setTrcName(String trcName) {
        this.trcName = trcName;
    }

    public String getTrcpLocation() {
        return this.trcpLocation;
    }

    public void setTrcpLocation(String trcpLocation) {
        this.trcpLocation = trcpLocation;
    }
}