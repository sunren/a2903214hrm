package com.hr.base.io;

import java.util.List;

public abstract interface IIExportList {
    public static final String ERROR_READ = " ";

    public abstract List getList();

    public abstract String getPropertyName(int paramInt);

    public abstract String getHeadName(int paramInt);

    public abstract String getTypeName(int paramInt);

    public abstract boolean isNeedChangToName(int paramInt);

    public abstract String getName(Object paramObject, String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.base.io.IIExportList JD-Core Version: 0.5.4
 */