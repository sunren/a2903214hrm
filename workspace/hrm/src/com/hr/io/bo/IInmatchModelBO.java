package com.hr.io.bo;

import com.hr.io.domain.InmatchModel;
import com.hr.io.domain.Iodef;
import java.util.Collection;
import java.util.List;

public abstract interface IInmatchModelBO {
    public abstract InmatchModel loadObject(String paramString, String[] paramArrayOfString);

    public abstract InmatchModel loadDefaultObject(Iodef paramIodef);

    public abstract InmatchModel getObject(Iodef paramIodef, String paramString);

    public abstract boolean saveOrUpdate(InmatchModel paramInmatchModel);

    public abstract boolean saveOrUpdate(Collection<InmatchModel> paramCollection);

    public abstract boolean deleteInmatchModel(InmatchModel paramInmatchModel);

    public abstract List<InmatchModel> getListByIodef(Iodef paramIodef);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.io.bo.IInmatchModelBO JD-Core Version: 0.5.4
 */