package com.hr.help.bo;

import java.util.List;

public abstract interface IHelpBo {
    public abstract Object loadObject(Class paramClass, Object paramObject,
            String[] paramArrayOfString);

    public abstract boolean saveObject(Object paramObject);

    public abstract boolean updateObject(Object paramObject);

    public abstract boolean deleteObject(Object paramObject);

    public abstract List getClassList();

    public abstract List getHelpByClass(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.help.bo.IHelpBo JD-Core Version: 0.5.4
 */