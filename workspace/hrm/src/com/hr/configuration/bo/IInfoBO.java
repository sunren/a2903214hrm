package com.hr.configuration.bo;

import com.hr.configuration.domain.Infoclass;
import java.util.List;

public abstract interface IInfoBO {
    public abstract List FindAllInfo();

    public abstract boolean addInfo(Infoclass paramInfoclass);

    public abstract boolean delInfo(Class<Infoclass> paramClass, String paramString);

    public abstract boolean updateInfo(Infoclass paramInfoclass);

    public abstract void saveInforclassIdByBatch(String[] paramArrayOfString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.bo.IInfoBO JD-Core Version: 0.5.4
 */