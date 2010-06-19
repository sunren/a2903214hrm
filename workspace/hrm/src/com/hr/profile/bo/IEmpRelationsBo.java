package com.hr.profile.bo;

import com.hr.profile.domain.Emprelations;
import java.util.List;

public abstract interface IEmpRelationsBo {
    public abstract String insert(Emprelations paramEmprelations);

    public abstract List<Emprelations> search(String paramString);

    public abstract void delete(Emprelations paramEmprelations);

    public abstract Emprelations load(String paramString, String[] paramArrayOfString);

    public abstract void update(Emprelations paramEmprelations);

    public abstract List<Emprelations> search(Emprelations paramEmprelations);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.IEmpRelationsBo JD-Core Version: 0.5.4
 */