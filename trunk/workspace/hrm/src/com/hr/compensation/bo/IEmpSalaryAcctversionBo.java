package com.hr.compensation.bo;

import com.hr.compensation.domain.Empbenefitplan;
import com.hr.compensation.domain.Empsalaryacct;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.util.Pager;
import java.util.List;
import java.util.Map;

public abstract interface IEmpSalaryAcctversionBo {
    public abstract List<Empsalaryacctversion> getObjects(Class<Empsalaryacctversion> paramClass,
            String[] paramArrayOfString);

    public abstract Boolean insertEmpsalaryacctversion(
            Empsalaryacctversion paramEmpsalaryacctversion);

    public abstract Boolean updateEmpsalaryacctversion(
            Empsalaryacctversion paramEmpsalaryacctversion);

    public abstract Boolean useEsaversion(String paramString, int paramInt);

    public abstract List<Empsalaryacctversion> searchEsav(Empsalaryacct paramEmpsalaryacct,
            Pager paramPager, String paramString);

    public abstract Boolean addAcctVersion(Empsalaryacctversion paramEmpsalaryacctversion,
            List<Empsalaryacctitems> paramList);

    public abstract Boolean deleteAcctVersion(String paramString1, String paramString2);

    public abstract Boolean updateAcctVersion(Empsalaryacctversion paramEmpsalaryacctversion,
            List<Empsalaryacctitems> paramList, List<Empsalaryconfig> paramList1,
            List<Empsalarypay> paramList2, List<Empbenefitplan> paramList3);

    public abstract Boolean updateAcctVersion(Empsalaryacctversion paramEmpsalaryacctversion,
            List<Empsalaryacctitems> paramList, List<Empsalaryconfig> paramList1);

    public abstract Map<String, Integer> getOldAcctItems(String paramString);

    public abstract Boolean deleteEmpsalaryacctversion(String paramString);

    public abstract Empsalaryacctversion loadObject(String paramString, String[] paramArrayOfString);

    public abstract List<Empsalaryacctversion> exeHqlList(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.bo.IEmpSalaryAcctversionBo JD-Core Version: 0.5.4
 */