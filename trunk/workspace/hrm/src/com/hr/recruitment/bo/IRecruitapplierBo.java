package com.hr.recruitment.bo;

import com.hr.recruitment.domain.Recruitapplier;
import com.hr.util.Pager;
import java.util.List;

public abstract interface IRecruitapplierBo {
    public abstract List getObjects(Class paramClass, String[] paramArrayOfString);

    public abstract Recruitapplier loadApplier(Object paramObject, String[] paramArrayOfString);

    public abstract List updateApplier(Recruitapplier paramRecruitapplier, String paramString);

    public abstract List deleteApplier(String paramString);

    public abstract List searchApplierWithoutId(Recruitapplier paramRecruitapplier, Pager paramPager);

    public abstract List searchApplier(Recruitapplier paramRecruitapplier, Pager paramPager,
            String paramString);

    public abstract List insertApplier(Recruitapplier paramRecruitapplier, String paramString);

    public abstract List getApplierStatus();

    public abstract List getAllAplierDepartment();

    public abstract void updateApplierRemark(String paramString1, Integer paramInteger,
            String paramString2);

    public abstract Integer getNumberOfApplierInPlan(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.recruitment.bo.IRecruitapplierBo JD-Core Version: 0.5.4
 */