package com.hr.configuration.bo;

import com.hr.configuration.domain.Ecptype;
import java.util.List;

public abstract interface IEcptypeBO {
    public abstract List FindAllEcptype();

    public abstract boolean addEcptype(Ecptype paramEcptype);

    public abstract boolean delEcptype(Class<Ecptype> paramClass, String paramString);

    public abstract boolean updateEpcategory(Ecptype paramEcptype);

    public abstract void saveEcpTypeByBatch(String[] paramArrayOfString);

    public abstract List getEcptypeList(Ecptype paramEcptype);

    public abstract List getEcptypeByName(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.bo.IEcptypeBO JD-Core Version: 0.5.4
 */