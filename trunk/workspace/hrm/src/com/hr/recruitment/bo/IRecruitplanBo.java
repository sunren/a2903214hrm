package com.hr.recruitment.bo;

import com.hr.recruitment.domain.Recruitplan;
import com.hr.util.Pager;
import java.util.List;

public abstract interface IRecruitplanBo {
    public abstract List getObjects(Class paramClass, String[] paramArrayOfString);

    public abstract Recruitplan loadRecruitplan(String paramString, String[] paramArrayOfString);

    public abstract List updateRecruitplan(Recruitplan paramRecruitplan, String paramString);

    public abstract List deleteRecruitplan(String paramString);

    public abstract List searchRecruitplan(Recruitplan paramRecruitplan, Pager paramPager,
            String paramString);

    public abstract List insertRecruitplan(Recruitplan paramRecruitplan, String paramString);

    public abstract List update_closeRecruitplan(String paramString1, String paramString2);

    public abstract String findStatusByRecpStatusNo(int paramInt);

    public abstract String updateRecruitplanAsComment(String paramString1, String paramString2,
            Integer paramInteger);

    public abstract boolean updateRecruitplan(Integer paramInteger, String paramString1,
            String paramString2, String paramString3);

    public abstract List searchApproveRecruitplanHR(Recruitplan paramRecruitplan, Pager paramPager);

    public abstract List searchApproveRecruitplanDept(Recruitplan paramRecruitplan, Pager paramPager);

    public abstract List getRecruitplanStatus();

    public abstract void updateRecruitplanStatusAsSubmit(String paramString1, String paramString2,
            int paramInt);

    public abstract List searchRecruitplanforHR(Recruitplan paramRecruitplan, Pager paramPager,
            String paramString);

    public abstract List getApprovedPlanList();

    public abstract void updatePlan(Recruitplan paramRecruitplan);

    public abstract List searchApprovePlanWithCondition(String paramString);

    public abstract Integer getMaxRecordNo(String paramString1, String paramString2);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.recruitment.bo.IRecruitplanBo JD-Core Version: 0.5.4
 */