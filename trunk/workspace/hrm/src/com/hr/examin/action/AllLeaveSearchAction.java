package com.hr.examin.action;

import com.hr.base.DWRUtil;
import com.hr.examin.action.beans.ExaminSearchBean;
import com.hr.examin.action.beans.LeaveFormBean;
import com.hr.examin.bo.interfaces.ILeaverequestBO;
import com.hr.examin.domain.Leaverequest;
import com.hr.util.Pager;
import com.hr.util.SysConfigManager;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.axis.utils.StringUtils;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.hibernate.criterion.DetachedCriteria;

public class AllLeaveSearchAction extends EmpExaminAction {
    private static final long serialVersionUID = 1L;
    private ExaminSearchBean es_Bean;
    private Pager page;
    private String srcAction;
    private String isXiaojia;
    private String infoMeg;
    private String approveOper;
    private LeaveFormBean lf_Bean;
    private String lrUpdateId;
    private String startAPM;
    private String endAPM;

    public String execute() throws Exception {
        this.isXiaojia = "";
        this.srcAction = "allLeaveSearch";
        initLRLists();

        if (this.page == null) {
            this.page = new Pager();
            this.page.setOrder("");
        }
        if (this.es_Bean == null) {
            this.es_Bean = new ExaminSearchBean();
        }

        setLeaveType((String) this.dbConfigManager.getProperties().get("sys.examin.leave.type"));
        ILeaverequestBO lr_BO = (ILeaverequestBO) getBean("leaverequestBO");

        DetachedCriteria dc = DetachedCriteria.forClass(Leaverequest.class);

        if ("SUB".equals(this.authorityCondition)) {
            addSubDC(dc, "emp", 4);
        }

        lr_BO.examinSearch(this.es_Bean, dc, this.page);

        if (!StringUtils.isEmpty(this.infoMeg)) {
            addSuccessInfo(this.infoMeg);
        }
        return "success";
    }

    public String cancelled(String lrID, String logMsg, HttpSession session) {
        if ((session == null) || (WebContextFactory.get() == null)
                || (WebContextFactory.get().getSession() == null)) {
            return "权限过期，请重新登录";
        }

        String auth = DWRUtil.checkAuth("allLeaveSearch", "execute");
        if (("error".equals(auth)) || (!"HR".equals(auth))) {
            return "您无权执行此操作";
        }

        if ((logMsg == null) || (logMsg.trim().equals(""))) {
            return "作废时，必须填写备注!";
        }

        if (logMsg.trim().length() > 255) {
            return "备注最长为255个字符！";
        }

        ILeaverequestBO lr_BO = (ILeaverequestBO) getBean("leaverequestBO");
        Leaverequest lr = lr_BO.loadLeaverequest(lrID);

        if (!wfInit("hr-cancel", logMsg, new Object[] { lr }))
            return "input";

        lr_BO.saveOrUpdateExaminData(new Object[] { lr });
        return "suc";
    }

    public String hrConfirmWithModify() throws Exception {
        if (this.lf_Bean == null) {
            return "error";
        }
        String auth = checkActionAuth("allLeaveSearch", "execute");
        if (!"HR".equals(auth)) {
            addErrorInfo("您无权执行此操作");
            return "noauth";
        }

        return wfObjOpSingle("hr-confirm", this.lf_Bean, this.lrUpdateId);
    }

    /** @deprecated */
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

    public String getSrcAction() {
        return this.srcAction;
    }

    public void setSrcAction(String srcAction) {
        this.srcAction = srcAction;
    }

    public String getIsXiaojia() {
        return this.isXiaojia;
    }

    public void setIsXiaojia(String isXiaojia) {
        this.isXiaojia = isXiaojia;
    }

    public String getApproveOper() {
        return this.approveOper;
    }

    public void setApproveOper(String approveOper) {
        this.approveOper = approveOper;
    }

    public String getEndAPM() {
        return this.endAPM;
    }

    public void setEndAPM(String endAPM) {
        this.endAPM = endAPM;
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

    public String getStartAPM() {
        return this.startAPM;
    }

    public void setStartAPM(String startAPM) {
        this.startAPM = startAPM;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.action.AllLeaveSearchAction JD-Core Version: 0.5.4
 */