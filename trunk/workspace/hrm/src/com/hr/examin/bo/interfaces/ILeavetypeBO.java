package com.hr.examin.bo.interfaces;

import com.hr.examin.domain.Leavetype;
import java.util.List;

public abstract interface ILeavetypeBO {
    public abstract Leavetype getLeavetype(String paramString);

    public abstract Leavetype getAnnualLeave();

    public abstract Leavetype getTiaoxiuLeavetype(Integer paramInteger);

    public abstract boolean isTypeExist(String paramString);

    public abstract List FindAllLeaveType();

    public abstract String addLeaveType(Leavetype paramLeavetype);

    public abstract String delLeaveType(Class<Leavetype> paramClass, String paramString);

    public abstract String updateLeaveType(Leavetype paramLeavetype);

    public abstract void saveLeavetypeByBatch(String[] paramArrayOfString);

    public abstract List findLeaveTypeList(Leavetype paramLeavetype);

    public abstract List findLeaveTypeByName(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.bo.interfaces.ILeavetypeBO JD-Core Version: 0.5.4
 */