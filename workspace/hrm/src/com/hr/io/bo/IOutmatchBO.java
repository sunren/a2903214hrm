package com.hr.io.bo;

import com.hr.io.domain.Iodef;
import com.hr.io.domain.Outmatch;
import com.hr.io.domain.OutmatchModel;
import com.hr.util.output.FieldOperate;
import java.util.List;

public abstract interface IOutmatchBO {
    public abstract Outmatch loadObject(String paramString);

    public abstract Outmatch loadObject(String paramString, String[] paramArrayOfString);

    public abstract boolean saveObject(Outmatch paramOutmatch);

    public abstract boolean updateObject(Outmatch paramOutmatch);

    public abstract boolean saveOrUpdateList(List<Outmatch> paramList);

    public abstract boolean deleteObject(String paramString);

    public abstract Iodef loadIodefByName(String paramString);

    public abstract List<Outmatch> getOutmatchList(OutmatchModel paramOutmatchModel);

    public abstract List<Outmatch> getFullOutmatchList(OutmatchModel paramOutmatchModel);

    public abstract void initOutmatchMaps(List<Outmatch> paramList);

    public abstract List<Outmatch> getOutmatchListOrderById(OutmatchModel paramOutmatchModel);

    public abstract List<FieldOperate> getOutputList(OutmatchModel paramOutmatchModel);

    public abstract List<Outmatch> getOutmatchListByOmb(String paramString);

    public abstract List<Outmatch> getOmListByOmbFieldNameArr(String[] paramArrayOfString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.bo.IOutmatchBO JD-Core Version: 0.5.4
 */