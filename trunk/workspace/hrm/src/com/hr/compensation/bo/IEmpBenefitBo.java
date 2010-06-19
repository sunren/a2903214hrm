package com.hr.compensation.bo;

import com.hr.compensation.domain.Empbenefit;
import com.hr.compensation.domain.Empbenefitplan;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.profile.domain.Employee;
import com.hr.util.Pager;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.criterion.DetachedCriteria;

public abstract interface IEmpBenefitBo {
    public abstract List<Employee> findEmpBenefit(DetachedCriteria paramDetachedCriteria,
            Pager paramPager, String paramString);

    public abstract String insertBenefit(Empbenefit paramEmpbenefit, String paramString);

    public abstract String insertNewBenefit(Empbenefit paramEmpbenefit1,
            Empbenefit paramEmpbenefit2, Employee paramEmployee);

    public abstract String addBenefit(Employee paramEmployee);

    public abstract String updateBenefit(Empbenefit paramEmpbenefit, Employee paramEmployee);

    public abstract String deleteBenefit(Employee paramEmployee, String paramString1,
            String paramString2);

    public abstract boolean deleteBenefitPlan(String paramString, Integer paramInteger,
            String[] paramArrayOfString);

    public abstract List<Empbenefit> searchByEmpNo(String paramString);

    public abstract Empbenefit loadPrevBenefit(String paramString);

    public abstract Empbenefit getEmpbenefitById(String paramString);

    public abstract void getEmpBenefitNew(String paramString,
            Empsalarypay[] paramArrayOfEmpsalarypay);

    public abstract void getEmpBenefitNew(String paramString,
            Empsalaryconfig[] paramArrayOfEmpsalaryconfig);

    public abstract void getEmpBenefit(String paramString, Employee[] paramArrayOfEmployee);

    public abstract List<Empsalarypay> searchbenefitpayvalueByEmpId(String paramString);

    public abstract List<Empbenefitplan> getEmpAdditionalBenefitPlanList(String paramString,
            Set<String> paramSet);

    public abstract List<Empbenefitplan> getBenefitPlanByEsav(String paramString);

    public abstract boolean shuffleBenefitPlan(List<Empbenefitplan> paramList,
            Map<String, Integer> paramMap, List<Empsalaryacctitems> paramList1);

    public abstract boolean saveOrupdate(List<Empbenefitplan> paramList);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.bo.IEmpBenefitBo JD-Core Version: 0.5.4
 */