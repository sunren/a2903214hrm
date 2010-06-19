package com.hr.io.bo;

import com.hr.io.domain.Iodef;
import com.hr.io.domain.Outmatch;
import com.hr.io.domain.OutmatchBasic;
import java.util.List;

public abstract interface IOutmatchBasicBO {
    public abstract OutmatchBasic loadObject(String paramString);

    public abstract OutmatchBasic loadObject(String paramString, String[] paramArrayOfString);

    public abstract OutmatchBasic loadObject(Iodef paramIodef, String paramString);

    public abstract boolean saveObject(OutmatchBasic paramOutmatchBasic);

    public abstract boolean saveOrUpdateObject(OutmatchBasic paramOutmatchBasic);

    public abstract boolean updateObject(OutmatchBasic paramOutmatchBasic);

    public abstract boolean deleteObject(OutmatchBasic paramOutmatchBasic);

    public abstract List<OutmatchBasic> getListByIodef(Iodef paramIodef);

    public abstract List<Outmatch> createOutmatch(Iodef paramIodef);

    public abstract void initOutmatchBasicMaps(List<OutmatchBasic> paramList);

    public abstract boolean updateOmbOmList(List<OutmatchBasic> paramList, List<Outmatch> paramList1);

    public abstract List<OutmatchBasic> getOmbListByFieldNameArr(String paramString,
            String[] paramArrayOfString);

    public abstract String getOmbListByFieldName(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.bo.IOutmatchBasicBO JD-Core Version: 0.5.4
 */