package com.hr.training.bo;

import com.hr.training.domain.Tremployeeplan;
import com.hr.util.Pager;
import java.util.List;

public abstract interface ITremployeeplanBO {
    public abstract Tremployeeplan loadById(String paramString);

    public abstract void save(Tremployeeplan paramTremployeeplan);

    public abstract void delete(Tremployeeplan paramTremployeeplan);

    public abstract void delete(String paramString);

    public abstract void saveOrupdate(Tremployeeplan paramTremployeeplan);

    public abstract List<Tremployeeplan> search(String paramString1, String paramString2,
            String paramString3, String paramString4, Integer paramInteger, Pager paramPager);

    public abstract List search(String[] paramArrayOfString, String paramString1,
            String paramString2, String paramString3, String paramString4, Integer paramInteger,
            Pager paramPager);

    public abstract boolean hasApplied(String paramString1, String paramString2);

    public abstract int countAppliedAmount(String paramString);

    public abstract int hasAppliedEmp(String paramString);

    public abstract void batchSetStatus(String paramString1, int paramInt, String paramString2);

    public abstract List getCompeplanStatusInRecruitplan();
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.training.bo.ITremployeeplanBO JD-Core Version: 0.5.4
 */