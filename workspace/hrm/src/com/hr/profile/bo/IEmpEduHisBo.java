package com.hr.profile.bo;

import com.hr.profile.domain.Emphistoryedu;
import java.util.List;

public abstract interface IEmpEduHisBo {
    public abstract String insert(Emphistoryedu paramEmphistoryedu);

    public abstract List<Emphistoryedu> search(String paramString);

    public abstract Emphistoryedu load(String paramString, String[] paramArrayOfString);

    public abstract void delete(String paramString);

    public abstract void update(Emphistoryedu paramEmphistoryedu);

    public abstract List<Emphistoryedu> search(Emphistoryedu paramEmphistoryedu);

    public abstract boolean deleteAttach(String paramString1, String paramString2);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.IEmpEduHisBo JD-Core Version: 0.5.4
 */