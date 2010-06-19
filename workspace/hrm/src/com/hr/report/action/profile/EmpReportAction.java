package com.hr.report.action.profile;

import com.hr.base.BaseAction;
import com.hr.util.DateUtil;
import java.util.Calendar;
import java.util.Date;

public class EmpReportAction extends BaseAction {
    private Date startDate;
    private Date endDate;

    public String execute() throws Exception {
        return "success";
    }

    public Date getStartDate() {
        this.startDate = DateUtil.getFirstDayInYear(Calendar.getInstance()).getTime();
        return this.startDate;
    }

    public Date getEndDate() {
        this.endDate = new Date();
        return this.endDate;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.action.profile.EmpReportAction JD-Core Version: 0.5.4
 */