package com.hr.examin.action;

import com.hr.examin.action.beans.ExaminSearchBean;
import com.hr.examin.action.beans.OvertimeFormBean;
import com.hr.examin.bo.interfaces.ILeavetypeBO;
import com.hr.examin.bo.interfaces.IOvertimerequestBo;
import com.hr.examin.domain.Leavetype;
import com.hr.examin.domain.Overtimerequest;
import com.hr.util.DateUtil;
import com.hr.util.Pager;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

public class HROvertimeSearchAction extends EmpExaminAction {
    private static final long serialVersionUID = 1L;
    private ExaminSearchBean es_Bean;
    private Pager page;
    private String actionSrc;
    private String approveOper;
    private String rejectLog;
    private String orIdUp;
    private OvertimeFormBean of_Bean;
    private String infoMeg;
    private String isTiaoxiu;
    private String tiaoXiuTime;
    private String srcAction;
    private Date otTiaoxiuExpire;

    public String execute() throws Exception {
        initOTLists();
        this.actionSrc = "hrOvertimeSearch.action";

        if (this.page == null) {
            this.page = new Pager();
            this.page.setOrder("");
        }
        if (this.page.getOrder().trim().length() == 0) {
            this.page.setOrder("");
        }
        if (this.es_Bean == null) {
            this.es_Bean = new ExaminSearchBean();
        }

        IOvertimerequestBo or_Bo = (IOvertimerequestBo) getBean("overtimerequestBO");

        or_Bo.approverHRSearch(this.es_Bean, this.page);
        if ((getInfoMeg() != null) && (getInfoMeg().length() > 0)) {
            addSuccessInfo(getInfoMeg());
        }
        return "success";
    }

    public String hrOtApproveOrReject() throws Exception {
        String[] Ids = ServletActionContext.getRequest().getParameterValues("changIds");
        String operate;
        if (this.approveOper.equals("tiaoxiu")) {
            operate = "tiaoxiu";
            String[] temp = { this.orIdUp };
            Ids = temp;
        } else {
            operate = ("approve".equals(this.approveOper)) ? "hr-approve" : "hr-reject";
        }

        return wfObjOpBatch(operate, this.of_Bean, Ids);
    }

    public String getTiaoxiuExpire(String orId) {
        IOvertimerequestBo or_BO = (IOvertimerequestBo) getBean("overtimerequestBO");
        Overtimerequest or = or_BO
                .loadOvertimerequest(orId, new String[] { Overtimerequest.PROP_OR_EMP_NO });
        ILeavetypeBO lt_BO = (ILeavetypeBO) getBean("leavetypeBO");
        Leavetype lt = lt_BO.getTiaoxiuLeavetype(Integer.valueOf(11));

        if (lt != null) {
            int expireDays = getOrTiaoxiuExpire(lt, or.getOrEmpNo());
            Date otTiaoxiuExpire = DateUtil.dateAdd(or.getOrStartDate(), expireDays);
            return DateUtil.formatDate(otTiaoxiuExpire);
        }

        return null;
    }

    public void validate() {
        validateDate(this.es_Bean);
        validateOTStatus(this.es_Bean);
    }

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

    public String getTiaoXiuTime() {
        return this.tiaoXiuTime;
    }

    public void setTiaoXiuTime(String tiaoXiuTime) {
        this.tiaoXiuTime = tiaoXiuTime;
    }

    public String getSrcAction() {
        return this.srcAction;
    }

    public void setSrcAction(String srcAction) {
        this.srcAction = srcAction;
    }

    public Date getOtTiaoxiuExpire() {
        return this.otTiaoxiuExpire;
    }

    public void setOtTiaoxiuExpire(Date otTiaoxiuExpire) {
        this.otTiaoxiuExpire = otTiaoxiuExpire;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.action.HROvertimeSearchAction JD-Core Version: 0.5.4
 */