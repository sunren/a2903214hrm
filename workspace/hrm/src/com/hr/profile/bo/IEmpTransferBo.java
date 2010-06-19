package com.hr.profile.bo;

import com.hr.profile.domain.EmpHistOrg;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Emptransfer;
import com.hr.profile.domain.Position;
import com.hr.util.Pager;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

public abstract interface IEmpTransferBo {
    public abstract List<Emptransfer> findTransferByEmpNo(String paramString);

    public abstract List<Emptransfer> findTransfer(DetachedCriteria paramDetachedCriteria,
            Pager paramPager, String paramString);

    public abstract void update(Emptransfer paramEmptransfer, String paramString);

    public abstract void delete(String paramString);

    public abstract Emptransfer getById(String paramString);

    public abstract List<Emptransfer> getEftByEmp(Employee paramEmployee);

    public abstract boolean saveImportList(List<Emptransfer> paramList, List<Employee> paramList1,
            List<Position> paramList2, List<EmpHistOrg> paramList3);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.IEmpTransferBo JD-Core Version: 0.5.4
 */