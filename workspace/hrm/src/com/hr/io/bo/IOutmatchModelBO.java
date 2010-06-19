package com.hr.io.bo;

import com.hr.io.domain.Iodef;
import com.hr.io.domain.Outmatch;
import com.hr.io.domain.OutmatchModel;
import java.util.Collection;
import java.util.List;

public abstract interface IOutmatchModelBO {
    public abstract OutmatchModel loadObject(String paramString);

    public abstract OutmatchModel loadObject(String paramString, String[] paramArrayOfString);

    public abstract OutmatchModel loadDefaultObject(Iodef paramIodef);

    public abstract OutmatchModel getObject(Iodef paramIodef, String paramString);

    public abstract boolean saveObject(OutmatchModel paramOutmatchModel);

    public abstract boolean updateObject(OutmatchModel paramOutmatchModel);

    public abstract boolean saveOrUpdateOutmatchModel(OutmatchModel paramOutmatchModel,
            List<Outmatch> paramList);

    public abstract boolean saveOrUpdate(Collection<OutmatchModel> paramCollection);

    public abstract boolean deleteObject(String paramString);

    public abstract boolean deleteOutmatchModel(OutmatchModel paramOutmatchModel);

    public abstract List<OutmatchModel> getListByIodef(Iodef paramIodef);

    public abstract List<OutmatchModel> getListByIodef(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.io.bo.IOutmatchModelBO JD-Core Version: 0.5.4
 */