package com.hr.profile.bo;

import com.hr.profile.domain.Orgheads;
import java.util.List;

public abstract interface IOrgheadsBo {
    public abstract boolean insert(Orgheads paramOrgheads);

    public abstract boolean addOrgheads(Orgheads paramOrgheads);

    public abstract boolean updateOrgheads(Orgheads paramOrgheads);

    public abstract boolean delOrgheads(Class<Orgheads> paramClass, String paramString);

    public abstract List<Orgheads> listOrgheadNo(String paramString);

    public abstract List FindAllOrgheads();

    public abstract void saveOrgHeadsSortIdByBatch(String[] paramArrayOfString);

    public abstract List<Orgheads> FindOrgheadsByEmpId(String paramString);

    public abstract List exeHqlList(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.IOrgheadsBo JD-Core Version: 0.5.4
 */