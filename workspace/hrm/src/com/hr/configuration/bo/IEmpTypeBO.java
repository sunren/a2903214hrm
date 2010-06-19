package com.hr.configuration.bo;

import com.hr.configuration.domain.Emptype;
import java.util.List;

public abstract interface IEmpTypeBO {
    public abstract List FindAllEmpType();

    public abstract List<Emptype> FindEnabledEmpType();

    public abstract boolean addEmptype(Emptype paramEmptype);

    public abstract boolean delEmptype(Class<Emptype> paramClass, String paramString);

    public abstract String updateEmptype(Emptype paramEmptype);

    public abstract void saveEmpTypeIdByBatch(String[] paramArrayOfString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.bo.IEmpTypeBO JD-Core Version: 0.5.4
 */