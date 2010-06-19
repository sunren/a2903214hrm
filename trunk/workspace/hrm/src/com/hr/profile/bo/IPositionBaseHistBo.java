package com.hr.profile.bo;

import com.hr.profile.domain.PositionBase;
import com.hr.profile.domain.PositionBaseHist;
import java.util.Date;
import java.util.List;

public abstract interface IPositionBaseHistBo {
    public abstract List exeHqlList(String paramString);

    public abstract List<PositionBaseHist> getRequirePbhList(Date paramDate, String paramString);

    public abstract String getDeptChargePb(Date paramDate, String paramString);

    public abstract PositionBaseHist getLatestPBHist(PositionBase paramPositionBase);

    public abstract List<PositionBaseHist> getPbHistsOfPb(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.IPositionBaseHistBo JD-Core Version: 0.5.4
 */