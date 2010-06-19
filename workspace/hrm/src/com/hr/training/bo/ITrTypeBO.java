package com.hr.training.bo;

import com.hr.training.domain.Trtype;
import java.util.List;

public abstract interface ITrTypeBO {
    public abstract void save(Trtype paramTrtype);

    public abstract void delete(Trtype paramTrtype);

    public abstract void delete(String paramString);

    public abstract Trtype load(String paramString);

    public abstract void update(Trtype paramTrtype);

    public abstract List loadAll();
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.training.bo.ITrTypeBO JD-Core Version: 0.5.4
 */