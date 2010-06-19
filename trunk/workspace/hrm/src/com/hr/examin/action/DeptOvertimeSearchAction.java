package com.hr.examin.action;

import com.hr.examin.action.beans.ExaminSearchBean;
import com.hr.examin.action.beans.OvertimeFormBean;
import com.hr.examin.bo.interfaces.IOvertimerequestBo;
import com.hr.profile.domain.Employee;
import com.hr.util.Pager;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.axis.utils.StringUtils;
import org.apache.struts2.ServletActionContext;

public class DeptOvertimeSearchAction extends EmpExaminAction {
    private static final long serialVersionUID = 1L;
    private ExaminSearchBean es_Bean;
    private Pager page;
    private String actionSrc;
    private int gmanager;
    private String srcAction;
    private String approveOper;
    private String rejectLog;
    private String orIdUp;
    private OvertimeFormBean of_Bean;
    private String infoMeg;
    private String isTiaoxiu;
    private String tiaoXiuTime;

    public String execute() throws Exception {
        initOTLists();
        this.srcAction = "deptOverTimeSearch";
        this.actionSrc = "deptOvertimeSearch.action";

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

        IOvertimerequestBo or_Bo = (IOvertimerequestBo) getBean("overtimerequestBO");
        or_Bo.approverMgrSearch(this.es_Bean, currentEmp, this.page);
        if (!StringUtils.isEmpty(this.infoMeg)) {
            addSuccessInfo(this.infoMeg);
        }
        return "success";
    }

    public String deptOtApproveOrReject() throws Exception {
        String[] Ids = ServletActionContext.getRequest().getParameterValues("changIds");

        String auth = checkActionAuth("deptOvertimeSearch", "execute");

        String operate = this.approveOper;
        if ("ALL".equals(auth))
            operate = "gm-" + operate;
        if ("SUB".equals(auth))
            operate = "mgr-" + operate;

        return wfObjOpBatch(operate, this.of_Bean, Ids);
    }

    public String deptOtApproveOrRejectWithModify() throws Exception {
        if (this.of_Bean == null) {
            return "error";
        }

        String auth = checkActionAuth("deptOvertimeSearch", "execute");

        String operate = ("approve".equals(this.approveOper)) ? "modify" : "reject";
        if ("ALL".equals(auth))
            operate = "gm-" + operate;
        if ("SUB".equals(auth))
            operate = "mgr-" + operate;

        return wfObjOpSingle(operate, this.of_Bean, this.orIdUp);
    }

    public void validate() {
        validateDate(this.es_Bean);
        validateOTStatus(this.es_Bean);
    }

    /** @deprecated */
    public String get24Point(Date date) {
        if (date == null) {
            date = new Date();
        }
        String result = null;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        if ("00:00".equals(format.format(date)))
            result = "24:00";
        else {
            result = format.format(date);
        }

        return result;
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

    public String getApproveOper() {
        return this.approveOper;
    }

    public void setApproveOper(String approveOper) {
        this.approveOper = approveOper;
    }

    public String getRejectLog() {
        return this.rejectLog;
    }

    public void setRejectLog(String rejectLog) {
        this.rejectLog = rejectLog;
    }

    public String getInfoMeg() {
        return this.infoMeg;
    }

    public void setInfoMeg(String infoMeg) {
        this.infoMeg = infoMeg;
    }

    public String getIsTiaoxiu() {
        return this.isTiaoxiu;
    }

    public void setIsTiaoxiu(String isTiaoxiu) {
        this.isTiaoxiu = isTiaoxiu;
    }

    public String getOrIdUp() {
        return this.orIdUp;
    }

    public void setOrIdUp(String orIdUp) {
        this.orIdUp = orIdUp;
    }

    public OvertimeFormBean getOf_Bean() {
        return this.of_Bean;
    }

    public void setOf_Bean(OvertimeFormBean of_Bean) {
        this.of_Bean = of_Bean;
    }

    public String getTiaoXiuTime() {
        return this.tiaoXiuTime;
    }

    public void setTiaoXiuTime(String tiaoXiuTime) {
        this.tiaoXiuTime = tiaoXiuTime;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.action.DeptOvertimeSearchAction JD-Core Version: 0.5.4
 */