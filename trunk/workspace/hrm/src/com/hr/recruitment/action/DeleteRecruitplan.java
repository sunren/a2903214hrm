package com.hr.recruitment.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.configuration.domain.JobTitle;
import com.hr.profile.domain.Employee;
import com.hr.recruitment.bo.IRecruitplanBo;
import com.hr.recruitment.domain.Recruitplan;
import java.util.List;

public class DeleteRecruitplan extends BaseAction implements Constants {
    private String id;

    public String execute() throws Exception {
        IRecruitplanBo recruitplanBo = (IRecruitplanBo) getBean("recruitplanBO");
        String[] fetch = { "recpCreateBy", "recpJobTitle" };
        Recruitplan tempPlan = recruitplanBo.loadRecruitplan(this.id, fetch);

        if (tempPlan != null) {
            if ((!"ADM".equalsIgnoreCase(this.authorityCondition))
                    && (!tempPlan.getRecpCreateBy().getId().equals(getCurrentEmpNo()))) {
                return "noauth";
            }
            if (recruitplanBo.deleteRecruitplan(this.id).size() < 1) {
                addSuccessInfo("删除招聘计划" + tempPlan.getRecpJobTitle().getJobtitleName() + "("
                        + tempPlan.getRecpNo() + ")成功");
                return "success";
            }
            addErrorInfo("删除招聘计划" + tempPlan.getRecpJobTitle().getJobtitleName() + "("
                    + tempPlan.getRecpNo() + ")失败");
            return "error";
        }

        return "error";
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.recruitment.action.DeleteRecruitplan JD-Core Version: 0.5.4
 */