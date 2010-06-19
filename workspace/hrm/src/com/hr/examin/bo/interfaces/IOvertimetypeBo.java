package com.hr.examin.bo.interfaces;

import com.hr.examin.domain.Overtimetype;
import java.util.List;

public abstract interface IOvertimetypeBo {
    public abstract Overtimetype getOvertimetype(String paramString);

    public abstract Overtimetype searchByID(String paramString);

    public abstract boolean addOvertimetype(Overtimetype paramOvertimetype);

    public abstract List FindAllOtType();

    public abstract boolean delOvertimetype(Class<Overtimetype> paramClass, String paramString);

    public abstract boolean updateOvertimetype(Overtimetype paramOvertimetype);

    public abstract void saveOvertimetypeByBatch(String[] paramArrayOfString);

    public abstract List findOvertimetypeList(Overtimetype paramOvertimetype);

    public abstract List findByName(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.bo.interfaces.IOvertimetypeBo JD-Core Version: 0.5.4
 */