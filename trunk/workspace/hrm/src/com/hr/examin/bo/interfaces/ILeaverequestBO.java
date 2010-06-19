package com.hr.examin.bo.interfaces;

import com.hr.examin.action.beans.ExaminSearchBean;
import com.hr.examin.action.beans.LeaveTotalBean;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Leavetype;
import com.hr.profile.domain.Employee;
import com.hr.util.Pager;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

public abstract interface ILeaverequestBO extends IExaminBo {
    public abstract Leaverequest loadOneLeaverequest(String paramString, String[] paramArrayOfString);

    public abstract List<Leaverequest> getTotalStatusSetList(Employee paramEmployee,
            Date paramDate1, Date paramDate2, Leavetype paramLeavetype, int[] paramArrayOfInt);

    public abstract List<String> delLeaveRequest(String paramString, Employee paramEmployee);

    public abstract List<Leaverequest> loadLeaveRequests(String[] paramArrayOfString1,
            String[] paramArrayOfString2);

    public abstract Leaverequest loadLeaverequest(String paramString);

    public abstract void examinSearch(ExaminSearchBean paramExaminSearchBean,
            DetachedCriteria paramDetachedCriteria, Pager paramPager);

    public abstract void approverMgrSearch(ExaminSearchBean paramExaminSearchBean,
            Employee paramEmployee, Pager paramPager);

    public abstract void approverHRSearch(ExaminSearchBean paramExaminSearchBean, Pager paramPager);

    public abstract void lrOwnSearch(ExaminSearchBean paramExaminSearchBean, String paramString,
            Pager paramPager);

    public abstract void lrAllSearch(ExaminSearchBean paramExaminSearchBean, Pager paramPager);

    public abstract BigDecimal[] getTotalUsedDay(Employee paramEmployee, int paramInt,
            Leavetype paramLeavetype, int[] paramArrayOfInt);

    public abstract double[] getLeaveDaysBeforAfter(List<Leaverequest> paramList, Date paramDate);

    public abstract List<LeaveTotalBean> getLeaveTotal(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.bo.interfaces.ILeaverequestBO JD-Core Version: 0.5.4
 */