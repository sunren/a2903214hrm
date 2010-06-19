package com.hr.examin.action;

import com.hr.examin.action.beans.ExaminSearchBean;
import com.hr.examin.action.beans.LeaveFormBean;
import com.hr.examin.bo.interfaces.ILeaverequestBO;
import com.hr.util.Pager;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

public class HRLeaveSearchAction extends EmpExaminAction {
    private static final long serialVersionUID = 1L;
    private ExaminSearchBean es_Bean;
    private Pager page;
    private String actionSrc;
    private String srcAction;
    private String infoMeg;
    private String approveOper;
    private String rejectLog;
    private LeaveFormBean lf_Bean;
    private String lrUpdateId;

    public String execute() throws Exception {
        initLRLists();
        this.actionSrc = "hrLeaveSearch.action";

        if (this.page == null) {
            this.page = new Pager();
            this.page.setOrder("");
        } else if (this.page.getOrder().trim().length() == 0) {
            this.page.setOrder("");
        }
        if (this.es_Bean == null) {
            this.es_Bean = new ExaminSearchBean();
        }
        if (this.lf_Bean == null) {
            this.lf_Bean = new LeaveFormBean();
        }

        ILeaverequestBO lr_BO = (ILeaverequestBO) getBean("leaverequestBO");
        lr_BO.approverHRSearch(this.es_Bean, this.page);
        if (!StringUtils.isEmpty(getInfoMeg())) {
            addSuccessInfo(getInfoMeg());
        }
        return "success";
    }

    public String hrLeaveApproveOrReject() throws Exception {
        String[] Ids = ServletActionContext.getRequest().getParameterValues("changIds");

        String operate = ("approve".equals(this.approveOper)) ? "hr-approve" : "hr-reject";

        return wfObjOpBatch(operate, this.lf_Bean, Ids);
    }

    public void validate() {
        validateDate(this.es_Bean);
        validateLRStatus(this.es_Bean);
    }

    public ExaminSearchBean getEs_Bean() {
        return this.es_Bean;
    }

    public void setEs_Bean(ExaminSearchBean es_Bean) {
        this.es_Bean = es_Bean;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public String getActionSrc() {
        return this.actionSrc;
    }

    public void setActionSrc(String actionSrc) {
        this.actionSrc = actionSrc;
    }

    public String getInfoMeg() {
        return this.infoMeg;
    }

    public void setInfoMeg(String infoMeg) {
        this.infoMeg = infoMeg;
    }

    public String getApproveOper() {
        return this.approveOper;
    }

    public void setApproveOper(String approveOper) {
        this.approveOper = approveOper;
    }

    public LeaveFormBean getLf_Bean() {
        return this.lf_Bean;
    }

    public void setLf_Bean(LeaveFormBean lf_Bean) {
        this.lf_Bean = lf_Bean;
    }

    public String getLrUpdateId() {
        return this.lrUpdateId;
    }

    public void setLrUpdateId(String lrUpdateId) {
        this.lrUpdateId = lrUpdateId;
    }

    public String getSrcAction() {
        return this.srcAction;
    }

    public void setSrcAction(String srcAction) {
        this.srcAction = srcAction;
    }

    public String getRejectLog() {
        return this.rejectLog;
    }

    public void setRejectLog(String rejectLog) {
        this.rejectLog = rejectLog;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.action.HRLeaveSearchAction JD-Core Version: 0.5.4
 */