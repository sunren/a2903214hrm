package com.hr.compensation.bo;

import com.hr.compensation.domain.Empbenefitplan;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalarydatadef;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.util.Pager;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;

public abstract interface IEmpBenefitPlanBo {
    public abstract List<Empbenefitplan> findEbp(DetachedCriteria paramDetachedCriteria,
            Pager paramPager, String paramString);

    public abstract void calcEbp(List<Empbenefitplan> paramList);

    public abstract List<Empbenefitplan> searchEbpByEmpandMonth(String paramString1,
            String paramString2, String[] paramArrayOfString);

    public abstract Map<String, Empbenefitplan> findEbpYMs(String[] paramArrayOfString1,
            Integer paramInteger, boolean paramBoolean, String[] paramArrayOfString2);

    public abstract Map<String, Empbenefitplan> findEbpBelongYMs(String[] paramArrayOfString1,
            Integer paramInteger, boolean paramBoolean, String[] paramArrayOfString2);

    public abstract Empbenefitplan deductEbpYMs(Empbenefitplan paramEmpbenefitplan1,
            Empbenefitplan paramEmpbenefitplan2);

    public abstract boolean deleteEbpById(String paramString);

    public abstract boolean deleteEbpByYm(String paramString, Integer paramInteger,
            String[] paramArrayOfString);

    public abstract Empbenefitplan searchEbpById(String paramString);

    public abstract Empbenefitplan searchEmpbenefitByEmpMonth(String paramString1,
            String paramString2);

    public abstract List<Empbenefitplan> searchEbpByEmpMonths(String paramString,
            String[] paramArrayOfString);

    public abstract Empbenefitplan searchInitEbpByEmpMonth(String paramString1, String paramString2);

    public abstract boolean updateEbp(Empbenefitplan paramEmpbenefitplan);

    public abstract Integer getBeneAddCount(String paramString);

    public abstract List<Empbenefitplan> getEbpList(String paramString1, String paramString2);

    public abstract String batchSaveEbp(List<Empbenefitplan> paramList);

    public abstract boolean setAddEbpSum(String paramString, Empsalarypay[] paramArrayOfEmpsalarypay);

    public abstract Map<String, Empbenefitplan> getSumEbpsOfMonth(String paramString,
            String[] paramArrayOfString);

    public abstract Map<String, Empbenefitplan> getSumEbpsOfAllMonth(String paramString);

    public abstract boolean addCurrPlan(String paramString, Empsalarypay paramEmpsalarypay);

    public abstract Empbenefitplan calcEbpForPage(Empbenefitplan paramEmpbenefitplan,
            List<Empsalaryacctitems> paramList);

    public abstract List<String> getAllYears();

    public abstract void processDataForExport(List<Empbenefitplan> paramList,
            List<Empsalarydatadef> paramList1);

    public abstract int hasBenefitPlanByAcctVersion(String paramString);

    public abstract List<Empbenefitplan> sumEbpList(List<Empbenefitplan> paramList,
            String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.bo.IEmpBenefitPlanBo JD-Core Version: 0.5.4
 */