package com.hr.examin.action;

import com.hr.examin.action.beans.ExaminSearchBean;
import com.hr.examin.bo.interfaces.IOvertimerequestBo;
import com.hr.examin.domain.Overtimerequest;
import com.hr.profile.domain.Employee;
import com.hr.security.domain.Userinfo;
import com.hr.util.Pager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.axis.utils.StringUtils;

public class MyOvertimeSearchAction extends EmpExaminAction {
    private static final long serialVersionUID = 1L;
    private ExaminSearchBean es_Bean;
    private Pager page;
    private String[][] overtimeTotal;

    public String execute() throws Exception {
        initOTLists();
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
        this.overtimeTotal = or_Bo.getOvertimeTotal(getCurrentEmpNo());

        or_Bo.ExaminOwnSearch(this.es_Bean, this.page, getCurrentEmpNo());
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

    public String delete(String orId, HttpSession session) {
        String result = "suc";

        IOvertimerequestBo overtimerequestBo = (IOvertimerequestBo) getBean("overtimerequestBO");

        Overtimerequest or = overtimerequestBo.loadOvertimerequest(orId);
        if ((or == null)
                || (or.getOrStatus().intValue() != 21)
                || ((!getCurrentEmp().equals(or.getOrEmpNo())) && (!getCurrentEmp()
                        .equals(or.getOrCreateBy())))) {
            return "您无权执行此操作＄1�7";
        }
        Employee currentEmployee = ((Userinfo) session.getAttribute("userinfo")).getEmployee();
        List errors = overtimerequestBo.deleteOvertimereuqest(orId, currentEmployee);
        if (errors.size() >= 1)
            result = "删除加班申请失败＄1�7";
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

    public String[][] getOvertimeTotal() {
        return this.overtimeTotal;
    }

    public void setOvertimeTotal(String[][] overtimeTotal) {
        this.overtimeTotal = overtimeTotal;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.action.MyOvertimeSearchAction JD-Core Version: 0.5.4
 */