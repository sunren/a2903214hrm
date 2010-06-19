package com.hr.examin.bo.interfaces;

import com.hr.compensation.domain.Empsalarypay;
import com.hr.examin.domain.Attendmonthly;
import com.hr.examin.domain.Attendperiod;
import com.hr.profile.domain.Employee;
import com.hr.util.Pager;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;

public abstract interface IAttendmonthlyBO {
    public abstract List<Attendmonthly> searchAttendmonthly(Pager paramPager,
            DetachedCriteria paramDetachedCriteria);

    public abstract Attendmonthly getAttendmonthly(String paramString);

    public abstract boolean addAttendmonthly(Attendmonthly paramAttendmonthly);

    public abstract boolean deleteAttendmonthly(String paramString);

    public abstract boolean updateAttendmonthly(Attendmonthly paramAttendmonthly);

    public abstract Attendmonthly saveOrUpdateAttendmonthly(Attendmonthly paramAttendmonthly);

    public abstract boolean deleteAttendmonthlyByDate(String paramString1, String paramString2);

    public abstract boolean exeMonthlySummary(String paramString1, String paramString2);

    public abstract List getYearList();

    public abstract void updateObject(Object paramObject);

    public abstract void saveObject(Object paramObject);

    public abstract Attendperiod loadAttendperiod(String paramString1, String paramString2);

    public abstract Map<String, Attendmonthly> getAttmByMonth(String paramString);

    public abstract boolean setAttmByPay(String paramString, Empsalarypay[] paramArrayOfEmpsalarypay);

    public abstract Map<String, Attendmonthly> getAttmMapByEmp(String paramString,
            List<Employee> paramList);

    public abstract Map<String, Attendmonthly> getAttmByPay(List<Empsalarypay> paramList,
            String paramString);

    public abstract Attendmonthly getAttmByEmpId(String paramString1, String paramString2);

    public abstract List<Employee> computeEmployeeList(Date paramDate1, Date paramDate2);

    public abstract List<Attendmonthly> getEmpMonthlyOfYear(List<Employee> paramList,
            String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.bo.interfaces.IAttendmonthlyBO JD-Core Version: 0.5.4
 */