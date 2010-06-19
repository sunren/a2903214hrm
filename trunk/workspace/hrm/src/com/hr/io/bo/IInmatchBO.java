package com.hr.io.bo;

import com.hr.io.domain.Inmatch;
import com.hr.io.domain.InmatchModel;
import java.util.List;

public abstract interface IInmatchBO {
    public abstract void initInmatchMaps(List<Inmatch> paramList);

    public abstract List<Inmatch> getInmatchList(InmatchModel paramInmatchModel);

    public abstract List<Inmatch> getInmatchListByImb(String paramString);

    public abstract List<Inmatch> getInputList(InmatchModel paramInmatchModel);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.io.bo.IInmatchBO JD-Core Version: 0.5.4
 */