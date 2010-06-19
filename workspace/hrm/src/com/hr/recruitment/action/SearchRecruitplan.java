package com.hr.recruitment.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.base.Status;
import com.hr.compensation.bo.ICompaplanBo;
import com.hr.configuration.bo.IEmailsendBO;
import com.hr.configuration.bo.IJobTitleBo;
import com.hr.configuration.bo.ISysLogBO;
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

public class SearchRecruitplan extends BaseAction implements Status {
    private List<Recruitplan> recruitplanList;
    private Pager page;
    private Recruitplan plan;
    private List allLocation;
    private List allStatus;
    private String changIds;
    private String viewAll;
    Employee depLeader;
    private String comments;
    private List<JobTitle> jobTitles;

    public SearchRecruitplan() {
        this.page = new Pager();
    }

    public String getRecpStatus(int no) {
        IRecruitplanBo recruitplanBo = (IRecruitplanBo) getBean("recruitplanBO");
        return recruitplanBo.findStatusByRecpStatusNo(no);
    }

    public String execute() throws Exception {
        if (this.plan == null) {
            this.plan = new Recruitplan();
        }
        this.plan.setRecpCreateBy(new Employee(getCurrentEmpNo()));
        ICompaplanBo compaplanBo = (ICompaplanBo) getBean("compaplanBo");
        IRecruitplanBo recruitplanBo = (IRecruitplanBo) getBean("recruitplanBO");
        setAllLocation(compaplanBo.getObjects(Location.class, null));
        setAllStatus(recruitplanBo.getRecruitplanStatus());
        this.recruitplanList = recruitplanBo.searchRecruitplan(this.plan, this.page, this.viewAll);

        IJobTitleBo jobTitleBo = (IJobTitleBo) SpringBeanFactory.getBean("jobTitleBo");
        this.jobTitles = jobTitleBo.FindAllJobTitle();

        for (int i = 0; i < this.recruitplanList.size(); ++i) {
            Recruitplan TempPlan = (Recruitplan) this.recruitplanList.get(i);
            TempPlan.setRecpJobDesc((TempPlan.getRecpJobDesc() != null) ? TempPlan.getRecpJobDesc()
                    .replaceAll("\r\n", "<br>") : " ");
            TempPlan.setRecpComments((TempPlan.getRecpComments() != null) ? TempPlan
                    .getRecpComments().replaceAll("\r\n", "<br>") : " ");
            TempPlan.setRecpJobSkill((TempPlan.getRecpJobSkill() != null) ? TempPlan
                    .getRecpJobSkill().replaceAll("\r\n", "<br>") : " ");
            TempPlan.setRecpLanguageSkill((TempPlan.getRecpLanguageSkill() != null) ? TempPlan
                    .getRecpLanguageSkill().replaceAll("\r\n", "<br>") : " ");
            this.recruitplanList.set(i, TempPlan);
        }
        return "success";
    }

    public String submitPlan(String planId, String comments, HttpSession session) {
        IEmailsendBO emailsendBo = (IEmailsendBO) getBean("emailsendBO");
        IRecruitplanBo recruitplanBo = (IRecruitplanBo) getBean("recruitplanBO");
        ISysLogBO logBO = (ISysLogBO) BaseAction.getBean("logBO");
        String result = DWRUtil.checkAuth("SearchRecruitplan", "execute");
        Employee sessionEmp = ((Userinfo) session.getAttribute("userinfo")).getEmployee();
        if ((result != null) && ("HR".equalsIgnoreCase(result))) {
            String tempStr = recruitplanBo.updateRecruitplanAsComment(planId, comments, Integer
                    .valueOf(12));
            if (tempStr != null) {
                return "系统问题，没有提交成功，请重试！";
            }

            logBO.addToSyslog("recruitplan", getCurrentEmpNo(session), getCurrentEmpNo(session),
                              planId, 0, "HR备案", comments);
        } else if (((("ALL".equalsIgnoreCase(result)) || ("DEPT".equalsIgnoreCase(result))))
                && (result != null)) {
            String tempStr = recruitplanBo.updateRecruitplanAsComment(planId, comments, Integer
                    .valueOf(11));
            if (tempStr != null) {
                return "系统问题，没有提交成功，请重试！";
            }

            logBO.addToSyslog("recruitplan", getCurrentEmpNo(session), getCurrentEmpNo(session),
                              planId, 0, "HR备案", comments);
        } else {
            String tempStr = recruitplanBo.updateRecruitplanAsComment(planId, comments, Integer
                    .valueOf(2));
            if (tempStr != null) {
                return "系统问题，没有提交成功，请重试！";
            }

            logBO.addToSyslog("recruitplan", getCurrentEmpNo(session), getCurrentEmpNo(session),
                              planId, 0, "HR备案", comments);
            try {
                Map paramUserEmail = new HashMap();
                paramUserEmail.put("SENDER", sessionEmp);
                paramUserEmail.put("URL", "recruitment/ApproverRecruitplanDept.action");
                emailsendBo.sendEmailToDept(null, "RecPlanSubmit", sessionEmp.getId(),
                                            paramUserEmail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if ("HR".equalsIgnoreCase(result)) {
            return "HR" + planId;
        }
        if (("ALL".equalsIgnoreCase(result)) || ("DEPT".equalsIgnoreCase(result))) {
            return "DP" + planId;
        }

        return "OW" + planId;
    }

    public String batchSubmitPlan(String[] changIds, HttpSession session) {
        String result = DWRUtil.checkAuth("SearchRecruitplan", "execute");
        IRecruitplanBo recruitplanBo = (IRecruitplanBo) getBean("recruitplanBO");
        IEmailsendBO emailsendBo = (IEmailsendBO) getBean("emailsendBO");
        StringBuffer sbSuccess = new StringBuffer();
        Employee sessionEmp = ((Userinfo) session.getAttribute("userinfo")).getEmployee();
        if (changIds != null) {
            for (int i = 0; i < changIds.length; ++i) {
                Recruitplan temPlan = recruitplanBo.loadRecruitplan(changIds[i].trim(), null);
                if ((result != null) && ("HR".equalsIgnoreCase(result))) {
                    recruitplanBo.updateRecruitplanStatusAsSubmit(changIds[i], sessionEmp.getId(),
                                                                  12);
                } else if (("ALL".equalsIgnoreCase(result)) || ("DEPT".equalsIgnoreCase(result))) {
                    recruitplanBo.updateRecruitplanStatusAsSubmit(changIds[i], sessionEmp.getId(),
                                                                  11);
                } else if ("OWN".equalsIgnoreCase(result)) {
                    recruitplanBo.updateRecruitplanStatusAsSubmit(changIds[i], sessionEmp.getId(),
                                                                  2);
                    Map paramUserEmail = new HashMap();
                    paramUserEmail.put("SENDER", sessionEmp);
                    paramUserEmail.put("URL", "recruitment/ApproverRecruitplanDept.action");
                    emailsendBo.sendEmailToDept(null, "RecPlanSubmit", sessionEmp.getId(),
                                                paramUserEmail);
                }

                if (temPlan != null) {
                    sbSuccess.append(temPlan.getRecpNo() + ",");
                }
            }
        }
        return "友情提示：所选招聘计划中的" + sbSuccess.substring(0, sbSuccess.length() - 1) + "号招聘计划提交成功";
    }

    public String getCurrentEmpNo(HttpSession session) {
        try {
            return ((Userinfo) session.getAttribute("userinfo")).getId();
        } catch (Exception e) {
        }
        return null;
    }

    public String getChangIds() {
        return this.changIds;
    }

    public void setChangIds(String changIds) {
        this.changIds = changIds;
    }

    public List getAllStatus() {
        return this.allStatus;
    }

    public void setAllStatus(List allStatus) {
        this.allStatus = allStatus;
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

    public List getRecruitplanList() {
        return this.recruitplanList;
    }

    public void setRecruitplanList(List recruitplanList) {
        this.recruitplanList = recruitplanList;
    }

    public Recruitplan getPlan() {
        return this.plan;
    }

    public void setPlan(Recruitplan plan) {
        this.plan = plan;
    }

    public String getViewAll() {
        return this.viewAll;
    }

    public void setViewAll(String viewAll) {
        this.viewAll = viewAll;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
 * com.hr.recruitment.action.SearchRecruitplan JD-Core Version: 0.5.4
 */