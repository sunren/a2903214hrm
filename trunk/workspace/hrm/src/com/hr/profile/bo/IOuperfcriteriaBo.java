package com.hr.profile.bo;

import com.hr.profile.domain.Ouperfcriteria;
import java.util.List;

public abstract interface IOuperfcriteriaBo {
    public abstract List<Ouperfcriteria> getPbPerfcriteria(String paramString);

    public abstract List<Ouperfcriteria> getDeptPerfcriteria(String paramString);

    public abstract boolean addPbPerfcr(Ouperfcriteria paramOuperfcriteria);

    public abstract boolean updatePbPerfcr(Ouperfcriteria paramOuperfcriteria);

    public abstract boolean delPbPerfcr(String paramString);

    public abstract void savePbPerfcrOrder(String[] paramArrayOfString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.IOuperfcriteriaBo JD-Core Version: 0.5.4
 */