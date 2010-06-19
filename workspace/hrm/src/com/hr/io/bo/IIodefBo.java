package com.hr.io.bo;

import com.hr.io.domain.Iodef;
import com.hr.io.domain.Outmatch;
import com.hr.io.domain.OutmatchBasic;
import com.hr.util.Pager;
import java.util.List;

public abstract interface IIodefBo {
    public abstract Iodef loadObject(String paramString);

    public abstract Iodef loadObjectByName(String paramString);

    public abstract Iodef loadObject(String paramString, String[] paramArrayOfString);

    public abstract boolean saveObject(Iodef paramIodef);

    public abstract boolean updateObject(Iodef paramIodef);

    public abstract boolean deleteObject(String paramString);

    public abstract List<Iodef> searchIodef(Iodef paramIodef, Pager paramPager);

    public abstract Iodef searchOneIodef(String paramString);

    public abstract Iodef searchIodefByName(String paramString);

    public abstract String updateIodef(Iodef paramIodef);

    public abstract String insertIodef(Iodef paramIodef);

    public abstract String deletIodef(String paramString);

    public abstract boolean updateIodef(Iodef paramIodef, List<OutmatchBasic> paramList,
            List<Outmatch> paramList1);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.bo.IIodefBo JD-Core Version: 0.5.4
 */