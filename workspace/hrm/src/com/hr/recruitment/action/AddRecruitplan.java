package com.hr.recruitment.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.base.Status;
import com.hr.configuration.bo.IEmailsendBO;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.configuration.domain.JobTitle;
import com.hr.profile.domain.Employee;
import com.hr.recruitment.bo.IRecruitplanBo;
import com.hr.recruitment.domain.Recruitplan;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddRecruitplan extends BaseAction implements Constants, Status {
    private static final long serialVersionUID = 1L;
    private Recruitplan recruitplan;
    private List errors;
    private String DraftOrSubmit;

    public String execute() throws Exception {
        IRecruitplanBo recruitplanBo = (IRecruitplanBo) getBean("recruitplanBO");
        ISysLogBO logBO = (ISysLogBO) BaseAction.getBean("logBO");

        if ((this.recruitplan.getRecpEndDate() != null)
                && (this.recruitplan.getRecpStartDate().after(this.recruitplan.getRecpEndDate()))) {
            addErrorInfo("职位发布日期要在职位关闭日期之前＄1�7");
            return "input";
        }

        if ("submit".equals(this.DraftOrSubmit)) {
            if ("HR".equalsIgnoreCase(this.authorityCondition))
                this.recruitplan.setRecpStatus(Integer.valueOf(12));
            else if (("ALL".equalsIgnoreCase(this.authorityCondition))
                    || ("DEPT".equalsIgnoreCase(this.authorityCondition))) {
                this.recruitplan.setRecpStatus(Integer.valueOf(11));
            } else if ("OWN".equalsIgnoreCase(this.authorityCondition))
                this.recruitplan.setRecpStatus(Integer.valueOf(2));
        } else {
            this.recruitplan.setRecpStatus(Integer.valueOf(1));
        }

        this.recruitplan.setRecpJobTitle(new JobTitle(this.recruitplan.getRecpJobTitle()
                .getJobtitleNo().trim()));

        this.recruitplan.setRecpNo(recruitplanBo.getMaxRecordNo("Recruitplan",
                                                                Recruitplan.PROP_RECP_NO));

        this.errors = recruitplanBo.insertRecruitplan(this.recruitplan, getCurrentEmpNo());
        if (this.errors.size() < 1) {
            addSuccessInfo("添加招聘计划(" + this.recruitplan.getRecpNo() + ")成功〄1�7");

            if (this.recruitplan.getRecpStatus().intValue() != 1) {
                try {
                    IEmailsendBO emailsendBo = (IEmailsendBO) getBean("emailsendBO");

                    Map paramUserEmail = new HashMap();
                    paramUserEmail.put("SENDER", getCurrentEmp());
                    paramUserEmail.put("URL", "recruitment/ApproverRecruitplanDept.action");
                    emailsendBo.sendEmailToDept(null, "RecPlanSubmit", getCurrentEmpNo(),
                                                paramUserEmail);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (this.recruitplan.getRecpStatus().intValue() != 1)
                    ;
            } else if (this.recruitplan.getRecpStatus().intValue() == 2) {
                logBO.addToSyslog("recruitplan", getCurrentEmpNo(), this.recruitplan
                        .getRecpCreateBy().getId(), this.recruitplan.getId(), 0, "提交计划", "");
            } else if (this.recruitplan.getRecpStatus().intValue() == 11) {
                logBO.addToSyslog("recruitplan", getCurrentEmpNo(), this.recruitplan
                        .getRecpCreateBy().getId(), this.recruitplan.getId(), 0, "部门批准", "");
            } else if (this.recruitplan.getRecpStatus().intValue() == 12) {
                logBO.addToSyslog("recruitplan", getCurrentEmpNo(), this.recruitplan
                        .getRecpCreateBy().getId(), this.recruitplan.getId(), 0, "HR备案", "");
            }

            this.recruitplan = new Recruitplan();
            return "success";
        }
        String error = (String) this.errors.get(0);
        addErrorInfo(error);
        return "input";
    }

    public Recruitplan getRecruitplan() {
        return this.recruitplan;
    }

    public void setRecruitplan(Recruitplan recruitplan) {
        this.recruitplan = recruitplan;
    }

    public String getDraftOrSubmit() {
        return this.DraftOrSubmit;
    }

    public void setDraftOrSubmit(String draftOrSubmit) {
        this.DraftOrSubmit = draftOrSubmit;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.recruitment.action.AddRecruitplan JD-Core Version: 0.5.4
 */