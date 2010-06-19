package com.hr.profile.bo;

import com.hr.profile.domain.Empreward;
import com.hr.util.Pager;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

public abstract interface IEmpRewardBo {
    public abstract List<Empreward> findRewardByEmpNo(String paramString);

    public abstract List<Empreward> findReward(DetachedCriteria paramDetachedCriteria,
            Pager paramPager, String paramString);

    public abstract boolean insertEmp(Empreward paramEmpreward);

    public abstract String insert(Empreward paramEmpreward);

    public abstract void update(Empreward paramEmpreward, String paramString);

    public abstract void delete(String paramString);

    public abstract Empreward getById(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.IEmpRewardBo JD-Core Version: 0.5.4
 */