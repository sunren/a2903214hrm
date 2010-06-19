package com.hr.compensation.bo;

import com.hr.compensation.domain.Empsalaryacct;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.util.Pager;
import java.util.List;

public abstract interface IEmpSalaryAcctBo {
    public abstract List getObjects(Class paramClass, String[] paramArrayOfString);

    public abstract Empsalaryacct loadObject(String paramString, String[] paramArrayOfString);

    public abstract boolean searchAcctNames(String paramString1, String paramString2);

    /** @deprecated */
    public abstract List searchAllEmpsalaryacct();

    public abstract List searchEmpsalaryacct(Empsalaryacct paramEmpsalaryacct, Pager paramPager,
            String paramString);

    public abstract Boolean insertEmpsalaryacct(Empsalaryacct paramEmpsalaryacct);

    public abstract Empsalaryacctversion getMaxValidToEsav(String paramString);

    public abstract Boolean updateEmpsalaryacct(Empsalaryacct paramEmpsalaryacct);

    public abstract Boolean deleteEmpsalaryacct(Object paramObject);

    public abstract Boolean deleteEmpsalaryacct(String paramString);

    public abstract Empsalaryacctversion getAcctVersionByAcctId(String paramString);

    public abstract List<Empsalaryacctversion> searchAllEmpsalaryacctVersionInUse();
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.bo.IEmpSalaryAcctBo JD-Core Version: 0.5.4
 */