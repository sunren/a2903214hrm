package com.hr.io.bo;

import com.hr.io.domain.Iomatch;
import java.util.List;

public abstract interface IIomatchBo {
    public abstract List<Iomatch> getIomatches(String paramString);

    public abstract String deleteIomatch(Iomatch paramIomatch);

    public abstract String insertIomatch(Iomatch paramIomatch);

    public abstract String updateIomatch(Iomatch paramIomatch);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.bo.IIomatchBo JD-Core Version: 0.5.4
 */