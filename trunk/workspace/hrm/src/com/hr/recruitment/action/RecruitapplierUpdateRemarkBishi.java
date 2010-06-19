package com.hr.recruitment.action;

import com.hr.base.BaseAction;
import com.hr.base.Status;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.profile.domain.Employee;
import com.hr.recruitment.dao.IRecruitapplierDAO;
import com.hr.recruitment.domain.Recruitapplier;
import com.hr.spring.SpringBeanFactory;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class RecruitapplierUpdateRemarkBishi extends BaseAction implements Status {
    private Recruitapplier applier;
    private List error;

    public RecruitapplierUpdateRemarkBishi() {
        this.error = null;
    }

    public String execute() throws Exception {
        String id = getRequest().getParameter("applierID_bishi");
        String name = getRequest().getParameter("applierName_bishi");
        String comment = getRequest().getParameter("common_id_bishi");
        String reviewtime = getRequest().getParameter("interviewDate_bishi");
        String reviewer = getRequest().getParameter("recaInterviewer_bishi");
        Integer status = Integer.valueOf(getRequest().getParameter("applierStatus_bishi"));
        IRecruitapplierDAO applierDao = (IRecruitapplierDAO) getBean("applierDAO");
        Recruitapplier tempApplier = (Recruitapplier) applierDao.loadObject(Recruitapplier.class,
                                                                            id.trim(), null,
                                                                            new boolean[0]);
        if (tempApplier.getRecaStatus().equals(status)) {
            return "error";
        }
        applierDao.updateHqlQuery("update Recruitapplier r  set r.recaComment='" + comment
                + "',r.recaStatus='" + status + "',r.recaInterviewTime='" + reviewtime
                + "',r.recaInterviewer='" + reviewer + "' where id='" + id + "'");

        addSuccessInfo("应聘耄1�7" + name + "状�1�7�更新成功�1�7�1�7");
        String statusText = "";
        switch (status.intValue()) {
        case 2:
            statusText = "笔试";
            break;
        case 3:
            statusText = "面试";
            break;
        case 4:
            statusText = "电话面试";
        }
        try {
            ISysLogBO logBO = (ISysLogBO) SpringBeanFactory.getBean("logBO");
            logBO.addToSyslog("recruitapplier", getCurrentEmpNo(), tempApplier.getRecaCreateBy()
                    .getId(), id, 0, "状�1�7�更改为" + statusText, comment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    public Recruitapplier getApplier() {
        return this.applier;
    }

    public void setApplier(Recruitapplier applier) {
        this.applier = applier;
    }

    public List getError() {
        return this.error;
    }

    public void setError(List error) {
        this.error = error;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.recruitment.action.RecruitapplierUpdateRemarkBishi JD-Core Version: 0.5.4
 */