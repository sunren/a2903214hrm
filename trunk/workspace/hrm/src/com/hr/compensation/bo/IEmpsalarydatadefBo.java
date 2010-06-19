package com.hr.compensation.bo;

import com.hr.compensation.domain.Empsalarydatadef;
import java.util.List;

public abstract interface IEmpsalarydatadefBo {
    public abstract List<Empsalarydatadef> searchByType(Integer paramInteger);

    public abstract Empsalarydatadef searchById(String paramString);

    public abstract List<Empsalarydatadef> searchAll();

    public abstract boolean insert(Empsalarydatadef paramEmpsalarydatadef);

    public abstract boolean update(Empsalarydatadef paramEmpsalarydatadef);

    public abstract boolean delete(String paramString);

    public abstract boolean saveSort(String[] paramArrayOfString);

    public abstract List<Empsalarydatadef> findByName(String paramString);

    public abstract List<Empsalarydatadef> findSameName(Empsalarydatadef paramEmpsalarydatadef);

    public abstract List<Empsalarydatadef> findOutput(Integer paramInteger);

    public abstract List<Empsalarydatadef> getBenedatadefs();

    public abstract Integer getMaxSortIdEsdd();

    public abstract boolean batchRefreshOMConfig();
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.bo.IEmpsalarydatadefBo JD-Core Version: 0.5.4
 */