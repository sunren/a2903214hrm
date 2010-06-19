package com.hr.profile.bo;

import com.hr.profile.domain.Ouqualify;
import java.util.List;

public abstract interface IOuqualifyBo {
    public abstract List<Ouqualify> getpbQualifyByPbId(String paramString);

    public abstract boolean addPbQualify(Ouqualify paramOuqualify);

    public abstract boolean updatePbQualify(Ouqualify paramOuqualify);

    public abstract boolean delPbQualify(String paramString);

    public abstract void savePbQualifyOrder(String[] paramArrayOfString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.IOuqualifyBo JD-Core Version: 0.5.4
 */