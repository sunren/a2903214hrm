package com.hr.profile.bo;

import com.hr.profile.domain.Empaddconf;
import java.util.List;

public abstract interface IEmpAddConfBo {
    public abstract List<Empaddconf> listAll();

    public abstract List<Empaddconf> listByTable(String paramString);

    public abstract void insertEmpaddconf(Empaddconf paramEmpaddconf);

    public abstract Empaddconf loadEmpaddconf(String paramString);

    public abstract void deleteEmpaddconf(String paramString);

    public abstract void updateEmpaddconf(Empaddconf paramEmpaddconf);

    public abstract List<Integer> listAllActiveFields();

    public abstract List<Integer> listAllActiveFieldsByTable(String paramString,
            Integer paramInteger);

    public abstract List findByName(String paramString);

    public abstract List findByConf(Empaddconf paramEmpaddconf);

    public abstract List findByTableName(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.IEmpAddConfBo JD-Core Version: 0.5.4
 */