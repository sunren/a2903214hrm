package com.hr.compensation.bo;

import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.profile.domain.Employee;
import com.hr.util.Pager;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;

public abstract interface ISalaryconfBo {
    public abstract Empsalaryconfig loadSalaryconf(Object paramObject, String[] paramArrayOfString);

    public abstract List getObjects(Class paramClass, String[] paramArrayOfString);

    public abstract boolean updateSalaryconf(Empsalaryconfig paramEmpsalaryconfig,
            String paramString);

    public abstract boolean deleteSalaryconf(String paramString, Employee paramEmployee);

    public abstract boolean saveOrUpdateEsc(Empsalaryconfig paramEmpsalaryconfig,
            String paramString, Employee paramEmployee);

    public abstract boolean insertSalaryconf(Empsalaryconfig paramEmpsalaryconfig,
            String paramString, Employee paramEmployee);

    public abstract Employee findConfigByEmpId(String paramString);

    public abstract List<Empsalaryconfig> findConfig(DetachedCriteria paramDetachedCriteria,
            Pager paramPager);

    public abstract List<Employee> findSalaryConfig(DetachedCriteria paramDetachedCriteria,
            Pager paramPager, Employee paramEmployee);

    public abstract Map<String, List[]> findSalaryConfigByNoPage(
            DetachedCriteria paramDetachedCriteria, Pager paramPager, Employee paramEmployee);

    public abstract String[][] findSalaryBenefitValue(String paramString);

    public abstract String batchUpdateConfig(String paramString, List<Empsalaryconfig> paramList);

    public abstract List<Empsalaryconfig> getSalaryConfigByAcctVersion(String paramString);

    public abstract int hasSalaryConfigByAcctVersion(String paramString);

    public abstract List<Empsalaryacctitems> getConfigItemsById(String paramString);

    public abstract Object getObject(Class paramClass, Object paramObject,
            String[] paramArrayOfString);

    public abstract boolean saveOrupdateObject(Object paramObject);

    public abstract <T> boolean saveOrupdate(Collection<T> paramCollection);

    public abstract Empsalaryconfig calcSalaryConfByType(List<Empsalaryacctitems> paramList,
            Empsalaryconfig paramEmpsalaryconfig);

    public abstract Boolean deleteSalaryConfig(String paramString);

    public abstract List<Empsalaryconfig> changeSalaryConfig(String paramString,
            Map<String, Integer> paramMap, List<Empsalaryacctitems> paramList);

    public abstract void interpretConfig(Empsalaryconfig[] paramArrayOfEmpsalaryconfig);

    public abstract List<Empsalaryconfig> getConfigByIds(String[] paramArrayOfString);

    public abstract List<String> getAllCostCenter();

    public abstract List<Employee> getEmployeeWithConfig(String[] paramArrayOfString);

    public abstract List<Empsalaryconfig> getAllEmpsalaryconfig();

    public abstract boolean calcSalaryConfByTypes(List<Employee> paramList);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.bo.ISalaryconfBo JD-Core Version: 0.5.4
 */