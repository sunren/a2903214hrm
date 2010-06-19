package com.hr.examin.bo.interfaces;

import com.hr.examin.domain.Attendperiod;
import com.hr.examin.domain.Attendshift;
import com.hr.examin.domain.Attendshiftareadept;
import com.hr.examin.domain.Empshift;
import com.hr.profile.domain.Employee;
import com.hr.util.Pager;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

public abstract interface IAttendshiftBO {
    public abstract List getObjects(Class paramClass, String[] paramArrayOfString);

    public abstract <E> E loadObject(Class<E> paramClass, Object paramObject,
            String[] paramArrayOfString, boolean[] paramArrayOfBoolean);

    public abstract boolean saveObject(Object paramObject);

    public abstract boolean updateObject(Object paramObject);

    public abstract boolean delObject(Object paramObject);

    public abstract Boolean isUsed(Attendshift paramAttendshift);

    public abstract Date nextDate(Date paramDate);

    public abstract List searchEmployee(Pager paramPager, DetachedCriteria paramDetachedCriteria);

    public abstract boolean saveSchedule(String[] paramArrayOfString, Attendshift paramAttendshift,
            Date paramDate1, Date paramDate2, Date paramDate3);

    public abstract boolean batchUpdateEmpshift(List<Empshift> paramList);

    public abstract List<Empshift> searchAttendSchedule(Pager paramPager,
            DetachedCriteria paramDetachedCriteria);

    public abstract List<Attendshift> searchAttendShift(String paramString, Integer paramInteger);

    public abstract List<Attendshift> searchAttendShiftById(String paramString);

    public abstract Attendshift getNonStrictDefaultAttendshift();

    public abstract List<Attendshiftareadept> searchDefaultAttendShiftRela();

    public abstract Attendshift getDefaultAttendshiftByEmp(List<Attendshiftareadept> paramList,
            Attendshift paramAttendshift, Employee paramEmployee);

    public abstract Attendshift getDefaultAttendshiftByEmp(Employee paramEmployee);

    public abstract void saveSort(String[] paramArrayOfString);

    public abstract void saveDefault(String paramString, int paramInt);

    public abstract void saveStatus(String paramString, int paramInt);

    public abstract int getGlobalDefaultCount();

    public abstract int getMaxSortId();

    public abstract boolean isDefault(String paramString);

    public abstract BigDecimal getShiftHours(String paramString);

    public abstract Attendperiod getCurrentPeriod(String paramString1, String paramString2);

    public abstract String getRealDate(String paramString1, String paramString2, String paramString3);

    public abstract String saveOrUpdateShift(Attendshift paramAttendshift,
            String[] paramArrayOfString1, String[] paramArrayOfString2);

    public abstract List<Attendshiftareadept> getAsadListByShift(String paramString);

    public abstract void deleteAsadListByShift(String paramString);

    public abstract boolean deleteShift(Attendshift paramAttendshift);

    public abstract List<Attendshift> getShiftIdsByRestrict(String[] paramArrayOfString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.bo.interfaces.IAttendshiftBO JD-Core Version: 0.5.4
 */