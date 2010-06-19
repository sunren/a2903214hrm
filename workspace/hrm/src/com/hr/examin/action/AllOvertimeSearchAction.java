package com.hr.examin.action;

import com.hr.base.DWRUtil;
import com.hr.examin.action.beans.ExaminSearchBean;
import com.hr.examin.action.beans.OvertimeFormBean;
import com.hr.examin.bo.interfaces.IOvertimerequestBo;
import com.hr.examin.domain.Overtimerequest;
import com.hr.util.Pager;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.apache.axis.utils.StringUtils;
import org.hibernate.criterion.DetachedCriteria;

public class AllOvertimeSearchAction extends EmpExaminAction {
    private static final long serialVersionUID = 1L;
    private ExaminSearchBean es_Bean;
    private Pager page;
    private String srcAction;
    private OvertimeFormBean of_Bean;
    private String orIdUp;

    public String execute() throws Exception {
        this.srcAction = "allOverTimeSearch";
        initOTLists();
        if (this.page == null) {
            this.page = new Pager();
            this.page.setOrder("");
        }
        if (this.es_Bean == null) {
            this.es_Bean = new ExaminSearchBean();
        }

        IOvertimerequestBo or_BO = (IOvertimerequestBo) getBean("overtimerequestBO");

        String auth = checkActionAuth("allOvertimeSearch", "execute");

        DetachedCriteria dc = DetachedCriteria.forClass(Overtimerequest.class);

        if (auth.equals("SUB")) {
            addSubDC(dc, "emp", 4);
        }

        or_BO.examinSearch(this.es_Bean, dc, this.page);

        if (!StringUtils.isEmpty(this.infoMeg)) {
            addSuccessInfo(this.infoMeg);
        }
        return "success";
    }

    public String cancelled(String orId, String logMeg, HttpSession session) {
        if ((logMeg == null) || (logMeg.trim().length() == 0)) {
            return "废弃时，必须填写备注";
        }
        if ((logMeg != null) && (logMeg.trim().length() > 255)) {
            return "备注朄1�7长为255个字笄1�7!";
        }
        String auth = DWRUtil.checkAuth("allOvertimeSearch", "execute");
        if ("error".equals(auth)) {
            return "没有权限＄1�7";
        }

        IOvertimerequestBo or_BO = (IOvertimerequestBo) getBean("overtimerequestBO");
        Overtimerequest or = or_BO.loadOvertimerequest(orId);

        if (!wfInit("hr-cancel", logMeg, new Object[] { or }))
            return "input";

        or_BO.saveOrUpdateExaminData(new Object[] { or });
        return "suc";
    }

    public String hrOtConfirmWithModify() throws Exception {
        if (this.of_Bean == null) {
            return "error";
        }

        String auth = this.authorityCondition;
        if (!"HR".equals(auth)) {
            addErrorInfo("您无权执行此操作＄1�7");
            return "noauth";
        }

        return wfObjOpSingle("hr-confirm", this.of_Bean, this.orIdUp);
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

    public String getSrcAction() {
        return this.srcAction;
    }

    public void setSrcAction(String srcAction) {
        this.srcAction = srcAction;
    }

    public OvertimeFormBean getOf_Bean() {
        return this.of_Bean;
    }

    public void setOf_Bean(OvertimeFormBean ofBean) {
        this.of_Bean = ofBean;
    }

    public String getOrIdUp() {
        return this.orIdUp;
    }

    public void setOrIdUp(String orIdUp) {
        this.orIdUp = orIdUp;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.action.AllOvertimeSearchAction JD-Core Version: 0.5.4
 */