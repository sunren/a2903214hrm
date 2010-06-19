package com.hr.recruitment.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.base.Status;
import com.hr.compensation.bo.ICompaplanBo;
import com.hr.configuration.bo.IEmailsendBO;
import com.hr.configuration.bo.IEmailtemplateBO;
import com.hr.configuration.bo.IJobTitleBo;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emailsend;
import com.hr.configuration.domain.Emailtemplate;
import com.hr.configuration.domain.JobTitle;
import com.hr.configuration.domain.Location;
import com.hr.profile.domain.Employee;
import com.hr.recruitment.bo.IRecruitplanBo;
import com.hr.recruitment.domain.Recruitplan;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.Pager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ApproverRecruitplanHR extends BaseAction implements Status {
    private static final Log log = LogFactory.getLog(ApproverRecruitplanHR.class);
    private Pager page;
    private List<Recruitplan> recruitplanList;
    private List allDept;
    private List allLocation;
    private List<JobTitle> jobTitles;
    private Recruitplan plan;
    IEmailsendBO emailsendBo;
    IRecruitplanBo planBo;

    public ApproverRecruitplanHR() {
        this.page = new Pager();

        this.emailsendBo = ((IEmailsendBO) getBean("emailsendBO"));
        this.planBo = ((IRecruitplanBo) getBean("recruitplanBO"));
    }

    public String getRecpStatus(int no) {
        IRecruitplanBo recruitplanBo = (IRecruitplanBo) getBean("recruitplanBO");
        return recruitplanBo.findStatusByRecpStatusNo(no);
    }

    public String reject(String approve_recordId, String comments, HttpSession session) {
        String result = DWRUtil.checkAuth("ApproverRecruitplanHR", "execute");
        if ("HR".equals(result)) {
            IRecruitplanBo recruitplanBo = (IRecruitplanBo) getBean("recruitplanBO");
            ISysLogBO logBO = (ISysLogBO) BaseAction.getBean("logBO");
            String empNo = ((Userinfo) session.getAttribute("userinfo")).getId();
            Recruitplan temPlan = recruitplanBo.loadRecruitplan(approve_recordId, null);
            if (temPlan.getRecpStatus().intValue() != 11)
                return "友情提示：招聘计刄1�7 " + temPlan.getRecpJobTitle() + "(" + temPlan.getRecpNo()
                        + ") 已不在部门已审状态，现在您无权拒绝了＄1�7";
            if (recruitplanBo.updateRecruitplan(Integer.valueOf(21), approve_recordId, null, empNo)) {
                try {
                    logBO.addToSyslog("recruitplan", getCurrentEmpNo(session), temPlan
                            .getRecpCreateBy().getId(), approve_recordId, 0, "HR拒绝", comments);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                IEmailtemplateBO templateBo = (IEmailtemplateBO) SpringBeanFactory
                        .getBean("emailtemplateBO");
                String templateNo = "RecPlanReject";
                Emailtemplate template = templateBo.getEmailTemplateByNo(templateNo);
                if (template == null) {
                    log.error("Cant find template '" + templateNo + "'.");
                    return "id" + temPlan.getId() + "re";
                }

                String[] fetch = { "recpCreateBy" };
                Employee emp = this.planBo.loadRecruitplan(approve_recordId, fetch)
                        .getRecpCreateBy();
                Map paramUserEmail = new HashMap();
                paramUserEmail.put("SENDER", getCurrentEmp());
                paramUserEmail.put("APPLIER", emp);
                paramUserEmail.put("URL", "recruitment/SearchRecruitplan.action");
                Emailsend send = new Emailsend();
                send.setEsTo(emp.getEmpEmail());
                String error = this.emailsendBo.addEmailSend(template, send, paramUserEmail);
                log.info(error);

                return "id" + temPlan.getId() + "re";
            }
            return "由于系统问题，拒绝没有成功，请重试！";
        }
        return null;
    }

    public String agreeOne(String approve_recordId, String comments, HttpSession session) {
        String result = DWRUtil.checkAuth("ApproverRecruitplanHR", "execute");
        if ("HR".equals(result)) {
            IRecruitplanBo recruitplanBo = (IRecruitplanBo) getBean("recruitplanBO");
            ISysLogBO logBO = (ISysLogBO) BaseAction.getBean("logBO");
            String empNo = ((Userinfo) session.getAttribute("userinfo")).getId();
            Recruitplan temPlan = recruitplanBo.loadRecruitplan(approve_recordId, null);
            if (temPlan.getRecpStatus().intValue() != 11)
                return "招聘计划 " + temPlan.getRecpJobTitle() + "(" + temPlan.getRecpNo()
                        + ")已不在部门已审状态，现在您无权审批！";
            if (recruitplanBo.updateRecruitplan(Integer.valueOf(12), approve_recordId, null, empNo)) {
                try {
                    logBO.addToSyslog("recruitplan", getCurrentEmpNo(session), temPlan
                            .getRecpCreateBy().getId(), approve_recordId, 0, "HR备案", comments);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    IEmailtemplateBO templateBo = (IEmailtemplateBO) SpringBeanFactory
                            .getBean("emailtemplateBO");
                    String templateNo = "RecPlanApprove";
                    Emailtemplate template = templateBo.getEmailTemplateByNo(templateNo);
                    if (template == null) {
                        log.error("Cant find template '" + templateNo + "'.");
                        return "id" + temPlan.getId() + "ag";
                    }

                    String[] fetch = { "recpCreateBy" };
                    Employee emp = this.planBo.loadRecruitplan(approve_recordId, fetch)
                            .getRecpCreateBy();
                    Map paramUserEmail = new HashMap();
                    paramUserEmail.put("SENDER", getCurrentEmp());
                    paramUserEmail.put("APPLIER", emp);
                    paramUserEmail.put("URL", "recruitment/SearchRecruitplan.action");
                    Emailsend send = new Emailsend();
                    send.setEsTo(emp.getEmpEmail());
                    String error = this.emailsendBo.addEmailSend(template, send, paramUserEmail);
                    log.info(error);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return "id" + temPlan.getId() + "ag";
            }
            return "由于系统问题，审批没有成功，请重试！";
        }
        return null;
    }

    public String batchApprove(String[] changIds, HttpSession session) {
        String result = DWRUtil.checkAuth("ApproverRecruitplanHR", "execute");
        ISysLogBO logBO = (ISysLogBO) BaseAction.getBean("logBO");
        if ("HR".equals(result)) {
            StringBuffer sb = new StringBuffer();
            StringBuffer sbSuccess = new StringBuffer();
            IRecruitplanBo recruitplanBo = (IRecruitplanBo) getBean("recruitplanBO");
            String empNo = ((Userinfo) session.getAttribute("userinfo")).getId();
            if (changIds != null) {
                IEmailtemplateBO templateBo = (IEmailtemplateBO) SpringBeanFactory
                        .getBean("emailtemplateBO");
                String templateNo = "RecPlanReject";
                Emailtemplate template = templateBo.getEmailTemplateByNo(templateNo);

                for (int i = 0; i < changIds.length; ++i) {
                    Recruitplan temPlan = recruitplanBo.loadRecruitplan(changIds[i].trim(), null);
                    if (temPlan.getRecpStatus().intValue() != 11) {
                        sb.append(temPlan.getId() + ",");
                    } else {
                        recruitplanBo.updateRecruitplan(Integer.valueOf(12), changIds[i].trim(),
                                                        null, empNo);
                        try {
                            logBO.addToSyslog("recruitplan", getCurrentEmpNo(session), temPlan
                                    .getRecpCreateBy().getId(), changIds[i].trim(), 0, "HR备案", "");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (template != null) {
                            String[] fetch = { "recpCreateBy" };
                            Employee emp = this.planBo.loadRecruitplan(changIds[i].trim(), fetch)
                                    .getRecpCreateBy();
                            Map paramUserEmail = new HashMap();
                            paramUserEmail.put("SENDER", getCurrentEmp());
                            paramUserEmail.put("APPLIER", emp);
                            paramUserEmail.put("URL", "recruitment/ApproverRecruitplanDept.action");
                            Emailsend send = new Emailsend();
                            send.setEsTo(emp.getEmpEmail());
                            String error = this.emailsendBo.addEmailSend(template, send,
                                                                         paramUserEmail);
                            log.info(error);
                        }

                        sbSuccess.append(temPlan.getId() + ",");
                    }
                }
            }
            if (sb.length() > 0) {
                return "友情提示＄1�7" + sb.toString().substring(0, sb.length())
                        + "的招聘计划由于状态已经不在部门已审状态，没有审批通过";
            }

            return "友情提示：所选招聘计划已经全部审批�1�7�过";
        }

        return null;
    }

    public String execute() throws Exception {
        ICompaplanBo compaplanBo = (ICompaplanBo) getBean("compaplanBo");
        IRecruitplanBo recruitplanBo = (IRecruitplanBo) getBean("recruitplanBO");
        if (this.plan == null) {
            this.plan = new Recruitplan();
        }

        setAllDept(compaplanBo.getObjects(Department.class, null));
        setAllLocation(compaplanBo.getObjects(Location.class, null));

        IJobTitleBo jobTitleBo = (IJobTitleBo) SpringBeanFactory.getBean("jobTitleBo");
        this.jobTitles = jobTitleBo.FindAllJobTitle();

        setRecruitplanList(recruitplanBo.searchApproveRecruitplanHR(this.plan, this.page));

        int sizeOfRecruitplanList = this.recruitplanList.size();
        for (int i = 0; i < sizeOfRecruitplanList; ++i) {
            Recruitplan tempPlan = (Recruitplan) this.recruitplanList.get(i);
            tempPlan.setRecpJobDesc((tempPlan.getRecpJobDesc() != null) ? tempPlan.getRecpJobDesc()
                    .replaceAll("\r\n", "<br>") : " ");
            tempPlan.setRecpComments((tempPlan.getRecpComments() != null) ? tempPlan
                    .getRecpComments().replaceAll("\r\n", "<br>") : " ");
            tempPlan.setRecpJobSkill((tempPlan.getRecpJobSkill() != null) ? tempPlan
                    .getRecpJobSkill().replaceAll("\r\n", "<br>") : " ");
            tempPlan.setRecpLanguageSkill((tempPlan.getRecpLanguageSkill() != null) ? tempPlan
                    .getRecpLanguageSkill().replaceAll("\r\n", "<br>") : " ");
            this.recruitplanList.set(i, tempPlan);
        }
        return "success";
    }

    public String getCurrentEmpNo(HttpSession session) {
        try {
            return ((Userinfo) session.getAttribute("userinfo")).getId();
        } catch (Exception e) {
        }
        return null;
    }

    public List getAllDept() {
        return this.allDept;
    }

    public void setAllDept(List allDept) {
        this.allDept = allDept;
    }

    public List getAllLocation() {
        return this.allLocation;
    }

    public void setAllLocation(List allLocation) {
        this.allLocation = allLocation;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public Recruitplan getPlan() {
        return this.plan;
    }

    public void setPlan(Recruitplan plan) {
        this.plan = plan;
    }

    public List<Recruitplan> getRecruitplanList() {
        return this.recruitplanList;
    }

    public void setRecruitplanList(List<Recruitplan> recruitplanList) {
        this.recruitplanList = recruitplanList;
    }

    public List<JobTitle> getJobTitles() {
        return this.jobTitles;
    }

    public void setJobTitles(List<JobTitle> jobTitles) {
        this.jobTitles = jobTitles;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.recruitment.action.ApproverRecruitplanHR JD-Core Version: 0.5.4
 */