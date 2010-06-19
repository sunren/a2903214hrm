package com.hr.examin.action;

import com.hr.examin.action.beans.ExaminSearchBean;
import com.hr.examin.action.beans.LeaveFormBean;
import com.hr.examin.bo.interfaces.ILeaverequestBO;
import com.hr.profile.domain.Employee;
import com.hr.util.Pager;
import com.hr.util.SysConfigManager;
import javax.servlet.http.HttpServletRequest;
import org.apache.axis.utils.StringUtils;
import org.apache.struts2.ServletActionContext;

public class DeptLeaveSearchAction extends EmpExaminAction {
    private static final long serialVersionUID = 1L;
    private ExaminSearchBean es_Bean;
    private Pager page;
    private int gmanager;
    private String srcAction;
    private String actionSrc;
    private String infoMeg;
    private String approveOper;
    private String rejectLog;
    private LeaveFormBean lf_Bean;
    private String lrUpdateId;
    private String role;

    public DeptLeaveSearchAction() {
        this.role = "dept";
    }

    public String execute() throws Exception {
        initLRLists();
        this.actionSrc = "deptLeaveSearch.action";
        this.srcAction = "deptLeaveSearch";

        Employee currentEmp = getCurrentEmp();

        if (this.page == null) {
            this.page = new Pager();
            this.page.setOrder("");
        } else if (this.page.getOrder().trim().length() == 0) {
            this.page.setOrder("");
        }
        if (this.es_Bean == null) {
            this.es_Bean = new ExaminSearchBean();
        }

        setLeaveType(this.dbConfigManager.getProperty("sys.examin.leave.type"));

        ILeaverequestBO lr_BO = (ILeaverequestBO) getBean("leaverequestBO");
        lr_BO.approverMgrSearch(this.es_Bean, currentEmp, this.page);
        if (!StringUtils.isEmpty(this.infoMeg)) {
            addSuccessInfo(this.infoMeg);
        }
        return "success";
    }

    public String deptApproveOrReject() throws Exception {
        String[] Ids = ServletActionContext.getRequest().getParameterValues("changIds");

        String auth = checkActionAuth("deptLeaveSearch", "execute");

        String operate = this.approveOper;
        if ("ALL".equals(auth))
            operate = "gm-" + operate;
        if ("SUB".equals(auth))
            operate = "mgr-" + operate;

        return wfObjOpBatch(operate, this.lf_Bean, Ids);
    }

    public String deptApproveOrRejectWithModify() {
        if (this.lf_Bean == null) {
            return "error";
        }

        String auth = checkActionAuth("deptLeaveSearch", "execute");

        String operate = ("approve".equals(this.approveOper)) ? "modify" : "reject";
        if ("ALL".equals(auth))
            operate = "gm-" + operate;
        if ("SUB".equals(auth))
            operate = "mgr-" + operate;

        return wfObjOpSingle(operate, this.lf_Bean, this.lrUpdateId);
    }

    public String redirectToUpdateAction() {
        if (this.srcAction != null)
            if (!this.srcAction.equals("")) {
                if (this.srcAction.equals("allLeaveSearch")) {
                    return "gotoAllLeaveSearch";
                }
                if (this.srcAction.equals("deptLeaveSearch")) {
                    return "gotoDeptLeaveSearch";
                }
                if (this.srcAction.equals("myLeaveSearch")) {
                    return "gotoMyLeaveSearch";
                }
            }
        return "gotoLeaveAdd";
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

    public int getGmanager() {
        return this.gmanager;
    }

    public void setGmanager(int gmanager) {
        this.gmanager = gmanager;
    }

    public String getSrcAction() {
        return this.srcAction;
    }

    public void setSrcAction(String srcAction) {
        this.srcAction = srcAction;
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

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
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
 * com.hr.examin.action.DeptLeaveSearchAction JD-Core Version: 0.5.4
 */