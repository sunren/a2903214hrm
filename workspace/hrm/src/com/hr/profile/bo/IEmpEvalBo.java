package com.hr.profile.bo;

import com.hr.profile.domain.Empeval;
import com.hr.util.Pager;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

public abstract interface IEmpEvalBo {
    public abstract List<Empeval> findEvalByEmpNo(String paramString);

    public abstract List<Empeval> findEval(DetachedCriteria paramDetachedCriteria,
            Pager paramPager, String paramString);

    public abstract boolean insertEval(Empeval paramEmpeval);

    public abstract String insert(Empeval paramEmpeval);

    public abstract void update(Empeval paramEmpeval, String paramString);

    public abstract void delete(String paramString);

    public abstract Empeval getById(String paramString);

    public abstract boolean deleteAttach(String paramString1, String paramString2);

    public abstract int repeatInTimeNum(Empeval paramEmpeval);

    public abstract Empeval getEmpevalInTest(String paramString);

    public abstract boolean hasPprobationaryPeriodEvalRecord(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.IEmpEvalBo JD-Core Version: 0.5.4
 */