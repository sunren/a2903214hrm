package com.hr.configuration.bo;

import com.hr.configuration.domain.Statusconf;
import com.hr.configuration.domain.StatusconfPK;
import java.util.List;

public abstract interface IStatBO {
    public abstract List FindAllStatus();

    public abstract boolean addStat(Statusconf paramStatusconf);

    public abstract boolean delStat(Class<Statusconf> paramClass, StatusconfPK paramStatusconfPK);

    public abstract boolean updateStatus(Statusconf paramStatusconf);

    public abstract Statusconf getStatusByName(String paramString1, String paramString2);

    public abstract List<Statusconf> getAllTableStatus(String paramString);

    public abstract Statusconf getStatusByConfigAndTable(String paramString, int paramInt);

    public abstract List<Statusconf> getStatusByConfigsAndTable(String paramString,
            List<Integer> paramList);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.bo.IStatBO JD-Core Version: 0.5.4
 */