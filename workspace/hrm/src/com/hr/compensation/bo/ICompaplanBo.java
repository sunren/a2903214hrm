package com.hr.compensation.bo;

import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryadj;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.configuration.domain.Statusconf;
import com.hr.util.Pager;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

public abstract interface ICompaplanBo {
    public abstract Empsalaryadj loadCompaplan(Object paramObject, String[] paramArrayOfString);

    public abstract <E> List<E> getObjects(Class<E> paramClass, String[] paramArrayOfString);

    public abstract List<Empsalaryconfig> mysearchBatchCompaplan(
            DetachedCriteria paramDetachedCriteria, Pager paramPager);

    public abstract boolean calcSalaryConfWithComp(List<Empsalaryconfig> paramList);

    public abstract boolean calcSalaryAdj(List<Empsalaryadj> paramList);

    public abstract Integer searchCompaplanSubmitStatus(String paramString);

    public abstract List<Empsalaryadj> mysearchCompaplan(DetachedCriteria paramDetachedCriteria,
            Pager paramPager);

    public abstract int hasSalaryAdjByAcctVersion(String paramString);

    public abstract boolean insertCompaplan(Empsalaryadj paramEmpsalaryadj, String paramString);

    public abstract List<Statusconf> getCompStatus();

    public abstract boolean deleteCompaplan(Empsalaryadj paramEmpsalaryadj);

    public abstract boolean updateSalaryConf(Empsalaryadj paramEmpsalaryadj, String paramString);

    public abstract Empsalaryadj loadCompaplanInfoByEmpNo(String paramString);

    public abstract Empsalaryadj loadCompaplanInfoByEmpNo(String paramString, Date paramDate);

    public abstract void updateCompaplan(Empsalaryadj paramEmpsalaryadj1,
            Empsalaryadj paramEmpsalaryadj2, String paramString);

    public abstract boolean updateCompaplanForSlary(String paramString1, String paramString2);

    public abstract int updateBackupSalaryConfig(String paramString);

    public abstract List<Empsalaryacctitems> getCompaItemsById(String paramString1,
            String paramString2);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.bo.ICompaplanBo JD-Core Version: 0.5.4
 */