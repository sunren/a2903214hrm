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

public class ApproverRecruitplanDept extends BaseAction implements Status {
    private static final Log log = LogFactory.getLog(ApproverRecruitplanDept.class);
    private Pager page;
    private List<Recruitplan> recruitplanList;
    private List allDept;
    private List allLocation;
    private Recruitplan plan;
    private List<JobTitle> jobTitles;

    public ApproverRecruitplanDept() {
        this.page = new Pager();
    }

    public String getRecpStatus(int no) {
        IRecruitplanBo recruitplanBo = (IRecruitplanBo) getBean("recruitplanBO");
        return recruitplanBo.findStatusByRecpStatusNo(no);
    }

    public String reject(String approve_recordId, String comments, HttpSession session) {
        IEmailsendBO emailsendBo = (IEmailsendBO) getBean("emailsendBO");
        IRecruitplanBo planBo = (IRecruitplanBo) getBean("recruitplanBO");
        String result = DWRUtil.checkAuth("ApproverRecruitplanDept", "execute");
        if (("ALL".equals(result)) || ("DEPT".equals(result))) {
            IRecruitplanBo recruitplanBo = (IRecruitplanBo) getBean("recruitplanBO");
            ISysLogBO logBO = (ISysLogBO) BaseAction.getBean("logBO");
            Employee sessionEmp = ((Userinfo) session.getAttribute("userinfo")).getEmployee();
            String empNo = sessionEmp.getId();
            Recruitplan temPlan = recruitplanBo.loadRecruitplan(approve_recordId, null);
            if (temPlan.getRecpStatus().intValue() != 2) {
                return "友情提示：招聘计刄1�7 " + temPlan.getRecpJobTitle() + "(" + temPlan.getRecpNo()
                        + ") 已不在已提交状�1�7�，现在您无权拒绝了＄1�7";
            }

            if (("DEPT".equals(result))
                    && (!checkDeptInCharge(getCurrentEmp(), temPlan.getRecpDepartmentNo()))) {
                return "权限不够＄1�7";
            }
            if (recruitplanBo.updateRecruitplan(Integer.valueOf(21), approve_recordId, null, empNo)) {
                try {
                    logBO.addToSyslog("recruitplan", getCurrentEmpNo(session), temPlan
                            .getRecpCreateBy().getId(), approve_recordId, 0, "部门拒绝", comments);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    IEmailtemplateBO templateBo = (IEmailtemplateBO) SpringBeanFactory
                            .getBean("emailtemplateBO");
                    String templateNo = "RecPlanReject";
                    Emailtemplate template = getTemplate(templateNo);
                    if (template == null) {
                        return "id" + temPlan.getId() + "re";
                    }

                    String[] fetch = { "recpCreateBy" };
                    Employee emp = planBo.loadRecruitplan(approve_recordId, fetch)
                            .getRecpCreateBy();
                    Map params = new HashMap();
                    params.put("SENDER", sessionEmp);
                    params.put("APPLIER", emp);
                    params.put("URL", "recruitment/SearchRecruitplan.action");
                    Emailsend send = new Emailsend();
                    send.setEsTo(emp.getEmpEmail());
                    String error = emailsendBo.addEmailSend(template, send, params);
                    log.info(error);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "id" + temPlan.getId() + "re";
            }
            return "错误提示：由于系统问题，拒绝没有成功，请重试＄1�7";
        }

        return null;
    }

    private Emailtemplate getTemplate(String templateNo) {
        IEmailtemplateBO templateBo = (IEmailtemplateBO) SpringBeanFactory
                .getBean("emailtemplateBO");
        Emailtemplate template = templateBo.getEmailTemplateByNo(templateNo);
        return template;
    }

    public String agreeOne(String approve_recordId, String comments, HttpSession session) {
        IEmailsendBO emailsendBo = (IEmailsendBO) getBean("emailsendBO");
        IRecruitplanBo planBo = (IRecruitplanBo) getBean("recruitplanBO");
        String result = DWRUtil.checkAuth("ApproverRecruitplanDept", "execute");
        if (("ALL".equals(result)) || ("DEPT".equals(result))) {
            IRecruitplanBo recruitplanBo = (IRecruitplanBo) getBean("recruitplanBO");
            Employee sessionEmp = ((Userinfo) session.getAttribute("userinfo")).getEmployee();
            String empNo = sessionEmp.getId();
            ISysLogBO logBO = (ISysLogBO) BaseAction.getBean("logBO");
            Recruitplan temPlan = recruitplanBo.loadRecruitplan(approve_recordId, null);
            if (temPlan.getRecpStatus().intValue() != 2) {
                return "友情提示：招聘计刄1�7 " + temPlan.getRecpJobTitle() + "(" + temPlan.getRecpNo()
                        + ")  已不在已提交状�1�7�，现在您无权审批！";
            }
            if (("DEPT".equals(result))
                    && (!checkDeptInCharge(getCurrentEmp(), temPlan.getRecpDepartmentNo()))) {
                return "权限不够＄1�7";
            }
            if (recruitplanBo.updateRecruitplan(Integer.valueOf(11), approve_recordId, null, empNo)) {
                try {
                    logBO.addToSyslog("recruitplan", getCurrentEmpNo(session), temPlan
                            .getRecpCreateBy().getId(), approve_recordId, 0, "部门批准", comments);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String templateNo = "RecPlanReject";
                Emailtemplate template = getTemplate(templateNo);
                if (template != null) {
                    String[] fetch = { "recpCreateBy" };
                    Employee emp = planBo.loadRecruitplan(approve_recordId, fetch)
                            .getRecpCreateBy();
                    Map paramUserEmail = new HashMap();
                    paramUserEmail.put("SENDER", sessionEmp);
                    paramUserEmail.put("URL", "recruitment/SearchRecruitplan.action");
                    Map contentMap = emailsendBo.getEmailContent("RecPlanApprove", paramUserEmail);
                    Emailsend send = new Emailsend();
                    send.setEsTitle(((String) contentMap.get("email_title")).toString());
                    send.setEsContent(((String) contentMap.get("email_content")).toString());
                    send.setEsTo(emp.getEmpEmail());
                    String error = emailsendBo.addEmailSend(template, send, paramUserEmail);
                    log.info(error);
                }
                return "id" + temPlan.getId() + "ag";
            }
            return "错误提示：由于系统问题，审批没有成功，请重试＄1�7";
        }

        return null;
    }

    public String batchApprove(String[] changIds, HttpSession session) {
        IEmailsendBO emailsendBo = (IEmailsendBO) getBean("emailsendBO");
        IRecruitplanBo planBo = (IRecruitplanBo) getBean("recruitplanBO");
        ISysLogBO logBO = (ISysLogBO) BaseAction.getBean("logBO");
        String result = DWRUtil.checkAuth("ApproverRecruitplanDept", "execute");
        if (("ALL".equals(result)) || ("DEPT".equals(result))) {
            StringBuffer sb = new StringBuffer();
            StringBuffer sbSuccess = new StringBuffer();
            IRecruitplanBo recruitplanBo = (IRecruitplanBo) getBean("recruitplanBO");
            Employee sender = ((Userinfo) session.getAttribute("userinfo")).getEmployee();
            String empNo = ((Userinfo) session.getAttribute("userinfo")).getId();
            if (changIds != null) {
                Emailtemplate template = getTemplate("RecPlanApprove");
                int lengthOfChangIds = changIds.length;
                for (int i = 0; i < lengthOfChangIds; ++i) {
                    Recruitplan temPlan = recruitplanBo.loadRecruitplan(changIds[i].trim(), null);
                    if (temPlan.getRecpStatus().intValue() != 2) {
                        sb.append(temPlan.getId() + ",");
                    } else {
                        if (("DEPT".equals(result))
                                && (!checkDeptInCharge(getCurrentEmp(), temPlan
                                        .getRecpDepartmentNo()))) {
                            continue;
                        }
                        recruitplanBo.updateRecruitplan(Integer.valueOf(11), changIds[i].trim(),
                                                        null, empNo);
                        try {
                            logBO.addToSyslog("recruitplan", getCurrentEmpNo(session), temPlan
                                    .getRecpCreateBy().getId(), changIds[i].trim(), 0, "部门批准", "");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (template != null) {
                            String[] fetch = { "recpCreateBy" };
                            Employee emp = planBo.loadRecruitplan(changIds[i].trim(), fetch)
                                    .getRecpCreateBy();
                            Map paramUserEmail = new HashMap();
                            paramUserEmail.put("SENDER", sender);
                            paramUserEmail.put("URL", "recruitment/SearchRecruitplan.action");
                            Map contentMap = emailsendBo.getEmailContent("RecPlanApprove",
                                                                         paramUserEmail);
                            Emailsend send = new Emailsend();
                            send.setEsTitle(((String) contentMap.get("email_title")).toString());
                            send
                                    .setEsContent(((String) contentMap.get("email_content"))
                                            .toString());
                            send.setEsTo(emp.getEmpEmail());
                            String error = emailsendBo.addEmailSend(template, send, paramUserEmail);
                            log.info(error);
                        }

                        sbSuccess.append(temPlan.getId() + ",");
                    }
                }
            }
            if (sb.length() > 0) {
                return "友情提示＄1�7" + sb.toString().substring(0, sb.length() - 1)
                        + "的招聘计划由于状态已经不在已提交状�1�7�，没有审批通过";
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

        if ((this.authorityCondition != null) && (this.authorityCondition.equalsIgnoreCase("DEPT"))) {
            this.plan.setDepts(getCurrentEmp().getDeptInChargeOld());
        }

        setAllDept(compaplanBo.getObjects(Department.class, null));
        setAllLocation(compaplanBo.getObjects(Location.class, null));

        setRecruitplanList(recruitplanBo.searchApproveRecruitplanDept(this.plan, this.page));

        IJobTitleBo jobTitleBo = (IJobTitleBo) SpringBeanFactory.getBean("jobTitleBo");
        this.jobTitles = jobTitleBo.FindAllJobTitle();

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
 * com.hr.recruitment.action.ApproverRecruitplanDept JD-Core Version: 0.5.4
 */