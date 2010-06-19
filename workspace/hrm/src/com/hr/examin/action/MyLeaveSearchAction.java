package com.hr.examin.action;

import com.hr.examin.action.beans.ExaminSearchBean;
import com.hr.examin.action.beans.LeaveTotalBean;
import com.hr.examin.bo.interfaces.ILeaverequestBO;
import com.hr.examin.domain.Leaverequest;
import com.hr.profile.domain.Employee;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.Pager;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.axis.utils.StringUtils;

public class MyLeaveSearchAction extends EmpExaminAction {
    private static final long serialVersionUID = 1L;
    private ExaminSearchBean es_Bean;
    private Pager page;
    private List<LeaveTotalBean> leaveTotalList;
    private String srcAction;

    public List<LeaveTotalBean> getLeaveTotalList() {
        return this.leaveTotalList;
    }

    public void setLeaveTotalList(List<LeaveTotalBean> leaveTotalList) {
        this.leaveTotalList = leaveTotalList;
    }

    public String execute() throws Exception {
        this.srcAction = "myLeaveSearch";
        initLRLists();

        if (this.page == null) {
            this.page = new Pager();

            this.page.setOrder("");
        } else if (this.page.getOrder().trim().length() == 0) {
            this.page.setOrder("");
        }
        if (this.es_Bean == null) {
            this.es_Bean = new ExaminSearchBean();
        }

        ILeaverequestBO lr_BO = (ILeaverequestBO) getBean("leaverequestBO");
        this.leaveTotalList = lr_BO.getLeaveTotal(getCurrentEmpNo());

        String leaveType = DatabaseSysConfigManager.getInstance()
                .getProperty("sys.examin.leave.type");
        setLeaveType(leaveType);

        lr_BO.lrOwnSearch(this.es_Bean, getCurrentEmpNo(), this.page);
        if (getRequest().getAttribute("msg") != null) {
            addSuccessInfo((String) getRequest().getAttribute("msg"));
        }
        if (!StringUtils.isEmpty(this.infoMeg)) {
            addSuccessInfo(this.infoMeg);
        }
        if (!StringUtils.isEmpty(this.errorMsg)) {
            addErrorInfo(this.errorMsg);
        }
        return "success";
    }

    public void validate() {
        validateDate(this.es_Bean);
        validateLRStatus(this.es_Bean);
    }

    public String delete(String lrId, HttpSession session) {
        ILeaverequestBO lr_BO = (ILeaverequestBO) getBean("leaverequestBO");

        Leaverequest lr = lr_BO.loadOneLeaverequest(lrId, null);
        if ((lr == null)
                || (lr.getLrStatus().intValue() != 21)
                || ((!getCurrentEmp().equals(lr.getLrEmpNo())) && (!getCurrentEmp()
                        .equals(lr.getLrCreateBy())))) {
            return "您无权执行此操作";
        }
        List result = lr_BO.delLeaveRequest(lrId, getCurrentEmp());
        if (result.size() > 0) {
            return (String) result.get(0);
        }
        return "suc";
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
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.action.MyLeaveSearchAction JD-Core Version: 0.5.4
 */