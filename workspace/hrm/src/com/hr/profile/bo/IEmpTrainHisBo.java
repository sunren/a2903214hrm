package com.hr.profile.bo;

import com.hr.profile.domain.Emphistorytrain;
import java.util.List;

public abstract interface IEmpTrainHisBo {
    public abstract String insert(Emphistorytrain paramEmphistorytrain);

    public abstract List<Emphistorytrain> search(String paramString);

    public abstract void delete(String paramString);

    public abstract Emphistorytrain load(String paramString, String[] paramArrayOfString);

    public abstract void update(Emphistorytrain paramEmphistorytrain);

    public abstract List<Emphistorytrain> search(Emphistorytrain paramEmphistorytrain);

    public abstract boolean deleteAttach(String paramString1, String paramString2);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.IEmpTrainHisBo JD-Core Version: 0.5.4
 */