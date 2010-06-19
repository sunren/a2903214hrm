package com.hr.compensation.bo;

import com.hr.compensation.domain.Empsalaryperiod;
import java.util.List;

public abstract interface IEmpsalaryperiodBo {
    public abstract List<String> getAllYear();

    public abstract List<String> getPaidPeriod(Integer paramInteger, String paramString1,
            String paramString2);

    public abstract List<Empsalaryperiod> getPayPeriods(Integer paramInteger,
            String[] paramArrayOfString);

    public abstract Integer getEspdStatus(String paramString);

    public abstract Integer getEspdStatusNew(String paramString);

    public abstract void updateConfirmSubmit(String paramString, Integer paramInteger);

    public abstract Empsalaryperiod loadEspdStatus(String paramString);

    public abstract boolean saveOrupdateObject(Object paramObject);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.bo.IEmpsalaryperiodBo JD-Core Version: 0.5.4
 */