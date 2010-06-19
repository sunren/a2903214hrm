package com.hr.recruitment.bo;

import com.hr.recruitment.domain.Recruitchannel;
import com.hr.util.Pager;
import java.util.List;

public abstract interface IRecruitchannelBo {
    public abstract List getObjects(Class paramClass, String[] paramArrayOfString);

    public abstract Boolean updateChannel(Recruitchannel paramRecruitchannel);

    public abstract Boolean deleteChannel(String paramString);

    public abstract List searchChannel(Recruitchannel paramRecruitchannel, Pager paramPager);

    public abstract Boolean insertChannel(Recruitchannel paramRecruitchannel);

    public abstract Integer searchChannelCountByName(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.recruitment.bo.IRecruitchannelBo JD-Core Version: 0.5.4
 */