package com.hr.profile.bo;

import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Empquit;
import com.hr.util.Pager;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

public abstract interface IEmpQuitBo {
    public abstract List<Empquit> searchByEmpNo(String paramString);

    public abstract List<Empquit> findQuitEmp(DetachedCriteria paramDetachedCriteria,
            Pager paramPager, String paramString);

    public abstract String saveEmpQuit(Empquit paramEmpquit);

    public abstract void update(Empquit paramEmpquit, String paramString);

    public abstract String delete(String paramString);

    public abstract Empquit getById(String paramString);

    public abstract List<Employee> findEmpQuit(DetachedCriteria paramDetachedCriteria,
            Pager paramPager, String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.IEmpQuitBo JD-Core Version: 0.5.4
 */