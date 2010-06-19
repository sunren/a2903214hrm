package com.hr.io.bo;

import com.hr.io.domain.Inmatch;
import com.hr.io.domain.InmatchBasic;
import com.hr.io.domain.Iodef;
import java.util.List;

public abstract interface IInmatchBasicBO {
    public abstract InmatchBasic loadObject(String paramString);

    public abstract boolean saveOrUpdateObject(InmatchBasic paramInmatchBasic);

    public abstract boolean deleteObject(InmatchBasic paramInmatchBasic);

    public abstract List<InmatchBasic> getListByIodef(Iodef paramIodef);

    public abstract List<Inmatch> createInmatch(Iodef paramIodef);

    public abstract void initInmatchBasicMaps(List<InmatchBasic> paramList);

    public abstract boolean updateImbImList(List<InmatchBasic> paramList, List<Inmatch> paramList1);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.bo.IInmatchBasicBO JD-Core Version: 0.5.4
 */