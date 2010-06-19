package com.hr.compensation.bo;

import com.hr.compensation.action.SalaryPaidConverge;
import com.hr.compensation.domain.Empbenefitplan;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalarydatadef;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.compensation.domain.Empsalaryperiod;
import com.hr.configuration.domain.Emailsend;
import com.hr.profile.domain.Employee;
import com.hr.util.Pager;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;

public abstract interface ISalaryPaidBo {
    public abstract List<Empsalarypay> findByCriteria(DetachedCriteria paramDetachedCriteria);

    public abstract List<Empsalarypay> findPay(DetachedCriteria paramDetachedCriteria,
            Pager paramPager);

    public abstract List<Empsalarypay> findPayPaid(DetachedCriteria paramDetachedCriteria,
            Pager paramPager);

    public abstract List<Employee> findPayNoPaid(DetachedCriteria paramDetachedCriteria1,
            DetachedCriteria paramDetachedCriteria2, Pager paramPager);

    public abstract List getObjects(Class paramClass, String[] paramArrayOfString);

    public abstract List<SalaryPaidConverge> findNeedApprovePaids(List paramList,
            String paramString, Empsalaryperiod paramEmpsalaryperiod);

    public abstract boolean deleteSalaryPaid(Empsalarypay paramEmpsalarypay);

    public abstract List<Empsalarypay> getDecriedSalaryPaidForEmailSend(
            String[] paramArrayOfString, String paramString, Integer paramInteger);

    public abstract List<Empsalarypay> processForOutput(List<Empsalarypay> paramList,
            List<Empsalarydatadef> paramList1);

    public abstract List<Empsalarypay> findMySalaryPaid(DetachedCriteria paramDetachedCriteria,
            Pager paramPager);

    public abstract List<Empsalarypay> searchMySalaryPaid(List<Empsalarypay> paramList,
            String paramString1, String paramString2);

    public abstract Empsalarypay getSalaryByEmpId(String paramString1, String paramString2);

    public abstract Empsalarypay loadSalaryPay(Object paramObject, String[] paramArrayOfString);

    public abstract Employee getEmployeeByESP(Empsalarypay paramEmpsalarypay);

    public abstract boolean saveOrupdateObject(Object paramObject);

    public abstract <T> boolean saveOrupdate(Collection<T> paramCollection);

    public abstract List<Empsalarypay> getSalaryPaidbyEsav(String paramString);

    public abstract Empsalarypay getSalaryPaidbyEspId(String paramString);

    public abstract int hasSalaryPayByAcctVersion(String paramString);

    public abstract String checkSalaryPay(String paramString1, String paramString2,
            String paramString3, String paramString4, Integer paramInteger) throws Exception;

    public abstract List<Emailsend> getEmailList(List<Empsalarypay> paramList, String paramString);

    public abstract Boolean shuffleSalaryPay(List<Empsalarypay> paramList,
            Map<String, Integer> paramMap, List<Empsalaryacctitems> paramList1);

    public abstract boolean isSalaryPayRecordsExist(String paramString);

    public abstract Empsalarypay calSalaryPayforPage(Empsalarypay paramEmpsalarypay,
            List<Empsalaryacctitems> paramList) throws Exception;

    public abstract boolean batchInitPaidSave(String paramString, List<Empsalarypay> paramList);

    public abstract String batchUpdatePay(String paramString, List<Empsalarypay> paramList);

    public abstract boolean batchUpdatePayImport(String paramString, List<Empsalarypay> paramList);

    public abstract List<Employee> searchEmployeeHasSalaryPay(String paramString);

    public abstract String getGivenMonthAvgPay(String paramString, String[] paramArrayOfString);

    public abstract String[][] getBeneItemsByPay(String paramString);

    public abstract boolean savePayAndInitEbp(Empsalarypay paramEmpsalarypay);

    public abstract void addAddtionalEbpToPay(List<Empsalaryacctitems> paramList,
            Empsalarypay paramEmpsalarypay, Empbenefitplan paramEmpbenefitplan, boolean paramBoolean);

    public abstract boolean haveBeneItems(List<Empsalaryacctitems> paramList);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.bo.ISalaryPaidBo JD-Core Version: 0.5.4
 */