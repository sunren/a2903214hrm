package com.hr.recruitment.action;

import com.hr.base.BaseAction;
import com.hr.base.Status;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.profile.domain.Employee;
import com.hr.recruitment.bo.IRecruitapplierBo;
import com.hr.recruitment.domain.Recruitapplier;
import com.hr.spring.SpringBeanFactory;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class RecruitapplierUpdateRemark extends BaseAction implements Status {
    private Recruitapplier applier;
    private List error;

    public RecruitapplierUpdateRemark() {
        this.error = null;
    }

    public String execute() throws Exception {
        String id = getRequest().getParameter("applierID_bishi");
        String name = getRequest().getParameter("applierName_bishi");
        String comment = getRequest().getParameter("common_id_bishi");
        Integer status = Integer.valueOf(getRequest().getParameter("applierStatus_bishi"));
        IRecruitapplierBo applierBo = (IRecruitapplierBo) getBean("applierBo");
        Recruitapplier tempApplier = applierBo.loadApplier(id.trim(), null);
        if (tempApplier.getRecaStatus().equals(status)) {
            return "error";
        }
        applierBo.updateApplierRemark(comment, status, id);
        String statusText = "";
        switch (status.intValue()) {
        case 1:
            statusText = "初�1�7��1�7�过";
            break;
        case 21:
            statusText = "黑名卄1�7";
            break;
        case 11:
            statusText = "待定候�1�7�人";
            break;
        case 13:
            statusText = "接受录取通知";
            break;
        case 19:
            statusText = "拒绝录取通知";
            break;
        case 12:
            statusText = "已发录取通知";
            break;
        case 9:
            statusText = "不合栄1�7";
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 10:
        case 14:
        case 15:
        case 16:
        case 17:
        case 18:
        case 20:
        }
        try {
            ISysLogBO logBO = (ISysLogBO) SpringBeanFactory.getBean("logBO");
            logBO.addToSyslog("recruitapplier", getCurrentEmpNo(), tempApplier.getRecaCreateBy()
                    .getId(), id, 0, "状�1�7�更改为" + statusText, comment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addSuccessInfo("应聘耄1�7" + name + "更新备注成功〄1�7");
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
 * com.hr.recruitment.action.RecruitapplierUpdateRemark JD-Core Version: 0.5.4
 */