package com.hr.recruitment.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.base.Status;
import com.hr.configuration.domain.JobTitle;
import com.hr.recruitment.bo.IRecruitplanBo;
import com.hr.recruitment.domain.Recruitplan;
import java.util.Date;
import java.util.List;

public class UpdateRecruitplanADM extends BaseAction implements Status, Constants {
    private Recruitplan recruitplan;
    private List error;

    public String execute() throws Exception {
        IRecruitplanBo recruitplanBo = (IRecruitplanBo) getBean("recruitplanBO");
        if ((this.recruitplan.getRecpEndDate() != null)
                && (this.recruitplan.getRecpStartDate().after(this.recruitplan.getRecpEndDate()))) {
            addErrorInfo("职位发布日期要在职位关闭日期之前");
            return "input";
        }

        this.recruitplan.setRecpJobTitle(new JobTitle(this.recruitplan.getRecpJobTitle()
                .getJobtitleNo().trim()));
        this.error = recruitplanBo.updateRecruitplan(this.recruitplan, getCurrentEmpNo());
        if (this.error.size() < 1) {
            addSuccessInfo("更新编号为" + this.recruitplan.getRecpNo() + "的招聘计划成功");
            return "success";
        }
        addErrorInfo(this.error);
        return "error";
    }

    public Recruitplan getRecruitplan() {
        return this.recruitplan;
    }

    public void setRecruitplan(Recruitplan recruitplan) {
        this.recruitplan = recruitplan;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.recruitment.action.UpdateRecruitplanADM JD-Core Version: 0.5.4
 */