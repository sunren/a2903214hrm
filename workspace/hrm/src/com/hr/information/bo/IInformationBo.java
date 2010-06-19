package com.hr.information.bo;

import com.hr.configuration.domain.Infoclass;
import com.hr.information.domain.Information;
import com.hr.security.domain.Userinfo;
import com.hr.util.Pager;
import java.util.List;

public abstract interface IInformationBo {
    public abstract boolean deleteInfo(Information paramInformation);

    public abstract List getObjects(Class paramClass, String[] paramArrayOfString);

    public abstract List insertInfo(Information paramInformation);

    public abstract Infoclass getInfoclass(String paramString);

    public abstract List getListbyClass(String paramString, Userinfo paramUserinfo);

    public abstract List getListbyClassId(String paramString1, Pager paramPager,
            String paramString2, Userinfo paramUserinfo, Integer paramInteger);

    public abstract Information loadInfo(Object paramObject, String[] paramArrayOfString);

    public abstract Information loadOne(String paramString);

    public abstract boolean updateInfo(Information paramInformation);

    public abstract List getOrderInfos(String paramString1, String paramString2);

    public abstract boolean saveObject(Information paramInformation);

    public abstract String getElementId(String paramString);

    public abstract List getInfoClassBySortId();
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.information.bo.IInformationBo JD-Core Version: 0.5.4
 */