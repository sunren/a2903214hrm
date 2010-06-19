package com.hr.training.bo;

import com.hr.profile.domain.Employee;
import com.hr.training.domain.Trcourseplan;
import com.hr.util.Pager;
import java.util.List;

public abstract interface ITrcourseplanBO {
    public abstract Trcourseplan loadById(String paramString);

    public abstract void save(Trcourseplan paramTrcourseplan);

    public abstract void delete(Trcourseplan paramTrcourseplan);

    public abstract void delete(String paramString);

    public abstract void saveOrupdate(Trcourseplan paramTrcourseplan);

    public abstract List getTrcpByTrc(String paramString, Pager paramPager);

    public abstract List getEnrollableTrcp(Employee paramEmployee, String paramString1,
            String paramString2, String paramString3, Pager paramPager);

    public abstract List search(String paramString1, String paramString2, Integer paramInteger,
            Pager paramPager);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.training.bo.ITrcourseplanBO JD-Core Version: 0.5.4
 */