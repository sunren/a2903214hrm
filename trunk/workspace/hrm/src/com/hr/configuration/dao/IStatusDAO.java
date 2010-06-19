package com.hr.configuration.dao;

import com.hr.configuration.domain.Statusconf;
import com.hr.hibernate.IHibernateUtil;
import java.util.List;

public abstract interface IStatusDAO extends IHibernateUtil {
    public abstract List<Statusconf> getStatusByTable(String paramString);

    public abstract Statusconf getStatusByName(String paramString1, String paramString2);

    public abstract List getCompeplanStatusInRecruitplan(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.dao.IStatusDAO JD-Core Version: 0.5.4
 */