package com.hr.examin.bo.interfaces;

import com.hr.profile.domain.Employee;
import com.hr.util.Pager;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

public abstract interface IAttenddailyBO {
    public abstract List searchAttenddaily(Pager paramPager, String paramString,
            Employee paramEmployee, Date paramDate1, Date paramDate2, Integer paramInteger1,
            Integer paramInteger2, boolean paramBoolean, List paramList);

    public abstract List<Employee> searchEmployee(DetachedCriteria paramDetachedCriteria);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.bo.interfaces.IAttenddailyBO JD-Core Version: 0.5.4
 */