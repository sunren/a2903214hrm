package com.hr.compensation.bo;

import com.hr.compensation.domain.Empbenefitplan;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryadj;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.profile.domain.Employee;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract interface IEmpSalaryAcctitemsBo {
    public abstract List getObjects(Class paramClass, String[] paramArrayOfString);

    public abstract Boolean insertEmpsalaryacctitems(Empsalaryacctitems paramEmpsalaryacctitems);

    public abstract <T> boolean saveOrupdate(Collection<T> paramCollection);

    public abstract Boolean deleteEmpsalaryacctitems(String paramString);

    public abstract Empsalaryacctitems loadObject(String paramString, String[] paramArrayOfString);

    public abstract List<Empsalaryacctitems> getItemsByAcctversion(String paramString);

    public abstract Map<String, List<Empsalaryacctitems>> getItemsByAcctversion(
            List<String> paramList);

    public abstract Map<String, List<Empsalaryacctitems>> getAcctItemsByPay(
            List<Empsalarypay> paramList);

    public abstract Map<String, List<Empsalaryacctitems>> getItemsBySalaryConf(
            List<Empsalaryconfig> paramList);

    public abstract Map<String, List<Empsalaryacctitems>> getItemsByEmployeeConf(
            List<Employee> paramList);

    public abstract boolean setAcctItemsConfig(List<Employee> paramList);

    public abstract boolean setAcctItemsPay(List<Empsalarypay> paramList);

    public abstract boolean setAcctItemsPay(Empsalarypay[] paramArrayOfEmpsalarypay);

    public abstract boolean setAcctItemsConfig(Empsalaryconfig[] paramArrayOfEmpsalaryconfig);

    public abstract Map<String, List<Empsalaryacctitems>> getItemsBySalaryAdj(
            List<Empsalaryadj> paramList);

    public abstract List<Empsalaryacctitems> findAll();

    public abstract List exeHqlList(String paramString);

    public abstract List<Empsalaryacctitems> getAllItemsByEsavIds(List<String> paramList);

    public abstract Map<String, List<Empsalaryacctitems>> getItemsByEmpbenefitplan(
            List<Empbenefitplan> paramList);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.bo.IEmpSalaryAcctitemsBo JD-Core Version: 0.5.4
 */