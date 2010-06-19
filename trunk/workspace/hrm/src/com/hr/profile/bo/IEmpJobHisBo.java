package com.hr.profile.bo;

import com.hr.profile.domain.Emphistoryjob;
import java.util.List;

public abstract interface IEmpJobHisBo {
    public abstract String insert(Emphistoryjob paramEmphistoryjob);

    public abstract List<Emphistoryjob> search(String paramString);

    public abstract void delete(Emphistoryjob paramEmphistoryjob);

    public abstract Emphistoryjob load(String paramString, String[] paramArrayOfString);

    public abstract void update(Emphistoryjob paramEmphistoryjob);

    public abstract List<Emphistoryjob> search(Emphistoryjob paramEmphistoryjob);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.IEmpJobHisBo JD-Core Version: 0.5.4
 */