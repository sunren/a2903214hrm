package com.hr.examin.bo.interfaces;

import com.hr.examin.action.beans.ExaminSearchBean;
import com.hr.examin.action.beans.OvertimeFormBean;
import com.hr.examin.domain.Overtimerequest;
import com.hr.profile.domain.Employee;
import com.hr.util.Pager;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

public abstract interface IOvertimerequestBo extends IExaminBo {
    public abstract String orCheckTime(Overtimerequest paramOvertimerequest);

    public abstract double getTotalTime(Overtimerequest paramOvertimerequest, Date paramDate1,
            Date paramDate2, Integer[] paramArrayOfInteger);

    public abstract double getTotalTimeRecal(Overtimerequest paramOvertimerequest, Date paramDate1,
            Date paramDate2, Integer[] paramArrayOfInteger);

    public abstract double getTotalOTTiaoxiuTime(Overtimerequest paramOvertimerequest,
            Date paramDate1, Date paramDate2, int[] paramArrayOfInt);

    public abstract List<Overtimerequest> getEmpAllTiaoxiuOrList(
            Overtimerequest paramOvertimerequest, int[] paramArrayOfInt);

    public abstract Overtimerequest loadOvertimerequest(String paramString,
            String[] paramArrayOfString);

    public abstract String checkMonthLimit(Overtimerequest paramOvertimerequest);

    public abstract double getMonthTotal(Overtimerequest paramOvertimerequest,
            Integer[] paramArrayOfInteger);

    public abstract Overtimerequest searchByOrId(String paramString);

    public abstract List<String> deleteOvertimereuqest(String paramString, Employee paramEmployee);

    public abstract List<Overtimerequest> loadOvertimeRequests(String[] paramArrayOfString1,
            String[] paramArrayOfString2);

    public abstract Overtimerequest loadOvertimerequest(String paramString);

    public abstract void examinSearch(ExaminSearchBean paramExaminSearchBean,
            DetachedCriteria paramDetachedCriteria, Pager paramPager);

    public abstract void approverMgrSearch(ExaminSearchBean paramExaminSearchBean,
            Employee paramEmployee, Pager paramPager);

    public abstract void approverHRSearch(ExaminSearchBean paramExaminSearchBean, Pager paramPager);

    public abstract void ExaminOwnSearch(ExaminSearchBean paramExaminSearchBean, Pager paramPager,
            String paramString);

    public abstract void orAllSearch(ExaminSearchBean paramExaminSearchBean, Pager paramPager);

    public abstract String[][] getOvertimeTotal(String paramString);

    public abstract String orInitTime(OvertimeFormBean paramOvertimeFormBean) throws Exception;

    public abstract String orCheckShifts(Overtimerequest paramOvertimerequest) throws Exception;

    public abstract void deleteExtraOvertimerequest();
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.bo.interfaces.IOvertimerequestBo JD-Core Version: 0.5.4
 */