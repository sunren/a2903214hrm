package com.hr.profile.bo;

import com.hr.profile.domain.Ouresponse;
import java.util.List;

public abstract interface IOuresponseBo {
    public abstract List<Ouresponse> getPbRespByPbId(String paramString);

    public abstract boolean addPbResp(Ouresponse paramOuresponse);

    public abstract boolean updatePbResp(Ouresponse paramOuresponse);

    public abstract boolean delPbResp(String paramString);

    public abstract void savePbRespOrder(String[] paramArrayOfString);

    public abstract Ouresponse saveDeptRes(Ouresponse paramOuresponse);

    public abstract boolean deleteDeptResById(String paramString);

    public abstract boolean saveSortIdBatch(String[] paramArrayOfString);

    public abstract Ouresponse getDeptRespByName(Ouresponse paramOuresponse);

    public abstract List<Ouresponse> getDeptRespLisByDept(String paramString);

    public abstract Integer getMaxSortId();
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.IOuresponseBo JD-Core Version: 0.5.4
 */