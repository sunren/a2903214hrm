package com.hr.training.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.base.Status;
import com.hr.configuration.bo.IEmailsendBO;
import com.hr.configuration.bo.IEmailtemplateBO;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.configuration.domain.Emailsend;
import com.hr.configuration.domain.Emailtemplate;
import com.hr.profile.bo.IEmployeeBo;
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

public class TrepDeptApprove extends BaseAction implements Status {
    private static final Log log = LogFactory.getLog(TrepDeptApprove.class);
    private List trepList;
    private String emp;
    private String trcName;
    private String trcpLocation;
    private Pager page;

    public TrepDeptApprove() {
        this.page = new Pager();
    }

    public String approve(String trepId, String comments, HttpSession session) {
        String result = DWRUtil.checkAuth("trepDeptApprove", "execute");
        if (("ALL".equals(result)) || ("DEPT".equals(result))) {
            ITremployeeplanBO tremployeeplanBO = (ITremployeeplanBO) getBean("tremployeeplanBO");
            if (trepId == null) {
                return "培训计划编号不对！请核实";
            }
            Tremployeeplan trep = tremployeeplanBO.loadById(trepId.toString());
            if (trep == null) {
                return "该培训计划不存在＄1�7";
            }
            if (("DEPT".equals(result)) && (!getCurrentEmp().equals(trep.getTrpTraineeNo()))
                    && (!checkDeptInCharge(getCurrentEmp(), trep.getTrpTraineeDept()))) {
                return "noauth";
            }
            trep.setTrpStatus(new Integer(11));
            trep.setTrpLastChangeBy(((Userinfo) session.getAttribute("userinfo")).getEmployee());
            trep.setTrpLastChangeTime(new Date());
            tremployeeplanBO.saveOrupdate(trep);
            try {
                ISysLogBO logBo = (ISysLogBO) getBean("logBO");
                logBo.addToSyslog("tremployeeplan", getCurrentEmpNo(), trep.getTrpTraineeNo()
                        .getId(), trepId.toString(), 0, "部门批准", comments);

                String templateNo = "TREPApprove";
                IEmailtemplateBO templateBo = (IEmailtemplateBO) SpringBeanFactory
                        .getBean("emailtemplateBO");
                Emailtemplate template = templateBo.getEmailTemplateByNo(templateNo);
                if (template == null) {
                    log.error("Cant find template '" + templateNo + "'.");
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

    public String reject(String trepId, String comments, HttpSession session) {
        String result = DWRUtil.checkAuth("trepDeptApprove", "execute");
        if (("ALL".equals(result)) || ("DEPT".equals(result)) || (result == null)) {
            ITremployeeplanBO tremployeeplanBO = (ITremployeeplanBO) getBean("tremployeeplanBO");
            if (trepId == null) {
                return "培训计划编号不对！请核实";
            }
            Tremployeeplan trep = tremployeeplanBO.loadById(trepId.toString());
            if (trep == null) {
                return "该培训计划不存在＄1�7";
            }
            if (("DEPT".equals(result)) && (!getCurrentEmp().equals(trep.getTrpTraineeNo()))
                    && (!checkDeptInCharge(getCurrentEmp(), trep.getTrpTraineeDept()))) {
                return "noauth";
            }
            trep.setTrpStatus(new Integer(21));
            trep.setTrpLastChangeBy(((Userinfo) session.getAttribute("userinfo")).getEmployee());
            trep.setTrpLastChangeTime(new Date());
            tremployeeplanBO.saveOrupdate(trep);
            try {
                ISysLogBO logBo = (ISysLogBO) getBean("logBO");
                logBo.addToSyslog("tremployeeplan", getCurrentEmpNo(), trep.getTrpTraineeNo()
                        .getId(), trepId.toString(), 0, "部门拒绝", comments);

                String templateNo = "TREPReject";
                IEmailtemplateBO templateBo = (IEmailtemplateBO) SpringBeanFactory
                        .getBean("emailtemplateBO");
                Emailtemplate template = templateBo.getEmailTemplateByNo(templateNo);
                if (template == null) {
                    log.error("Cant find template '" + templateNo + "'.");
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
        String result = DWRUtil.checkAuth("trepDeptApprove", "execute");
        if (("ALL".equals(result)) || ("DEPT".equals(result)) || (result == null)) {
            ITremployeeplanBO tremployeeplanBO = (ITremployeeplanBO) getBean("tremployeeplanBO");
            StringBuffer sb = new StringBuffer();
            StringBuffer sbSuccess = new StringBuffer();
            String empNo = ((Userinfo) session.getAttribute("userinfo")).getId();
            if (trepIds != null) {
                ISysLogBO logBO = (ISysLogBO) BaseAction.getBean("logBO");

                String userId = ((Userinfo) session.getAttribute("userinfo")).getId();

                IEmailsendBO emailsendBo = (IEmailsendBO) getBean("emailsendBO");
                String templateNo = "TREPApprove";
                IEmailtemplateBO templateBo = (IEmailtemplateBO) SpringBeanFactory
                        .getBean("emailtemplateBO");
                Emailtemplate template = templateBo.getEmailTemplateByNo(templateNo);
                if (template == null) {
                    log.error("Cant find template '" + templateNo + "'.");
                }

                for (int i = 0; i < trepIds.length; ++i) {
                    Tremployeeplan TempPlan = tremployeeplanBO.loadById(trepIds[i].trim());
                    if (("DEPT".equals(result))
                            && (!getCurrentEmp().equals(TempPlan.getTrpTraineeNo()))
                            && (!checkDeptInCharge(getCurrentEmp(), TempPlan.getTrpTraineeDept()))) {
                        continue;
                    }
                    if (TempPlan.getTrpStatus().intValue() != 2) {
                        sb.append(TempPlan.getTrpId() + ",");
                    } else {
                        tremployeeplanBO.batchSetStatus(trepIds[i].trim(), 11, empNo);
                        sbSuccess.append(TempPlan.getTrpId() + ",");

                        String treNo = tremployeeplanBO.loadById(trepIds[i]).getTrpTraineeNo()
                                .getId();
                        logBO.addToSyslog("tremployeeplan", userId, treNo, trepIds[i], 0, "部门批准",
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
                return "友情提示＄1�7" + sb.toString().substring(0, sb.length() - 1)
                        + "的培训申请由于状态已经不在已提交状�1�7�，没有审批通过";
            }

            return "恭喜您：扄1�7选培训申请已经全部审批�1�7�过";
        }

        return null;
    }

    public String execute() {
        search();
        return "success";
    }

    public void search() {
        ITremployeeplanBO tremployeeplanBO = (ITremployeeplanBO) BaseAction
                .getBean("tremployeeplanBO");
        IEmployeeBo empBo = (IEmployeeBo) BaseAction.getBean("empBo");
        String[] depts;
        if ("ALL".equalsIgnoreCase(this.authorityCondition)) {
            depts = null;
        } else
            depts = getCurrentEmp().getDeptInChargeOld();

        this.trepList = tremployeeplanBO.search(depts, this.emp, this.trcName, null,
                                                this.trcpLocation, new Integer(2), this.page);
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

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.training.action.TrepDeptApprove JD-Core Version: 0.5.4
 */