package com.hr.profile.bo;

import com.hr.profile.domain.Empcontract;
import com.hr.profile.domain.Employee;
import com.hr.util.Pager;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

public abstract interface IEmpContractBo {
    public abstract boolean saveContract(Empcontract paramEmpcontract);

    public abstract List<Empcontract> searchByEmpNo(String paramString);

    public abstract List<Empcontract> findContract(DetachedCriteria paramDetachedCriteria,
            Pager paramPager, String paramString);

    public abstract List<Employee> findContractByEmp(DetachedCriteria paramDetachedCriteria,
            Pager paramPager, String paramString);

    public abstract String insert(Empcontract paramEmpcontract);

    public abstract String update(Empcontract paramEmpcontract, String paramString);

    public abstract void delete(String paramString);

    public abstract Empcontract getById(String paramString);

    public abstract boolean deleteAttach(String paramString1, String paramString2);

    public abstract void updateObj(Object paramObject);

    public abstract int hasConflictInTime(Empcontract paramEmpcontract);

    public abstract List<Empcontract> getContractListByEmp(Employee paramEmployee);

    public abstract boolean insertList(List<Empcontract> paramList);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.IEmpContractBo JD-Core Version: 0.5.4
 */