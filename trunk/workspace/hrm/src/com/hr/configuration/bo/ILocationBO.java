package com.hr.configuration.bo;

import com.hr.configuration.domain.Location;
import java.util.List;

public abstract interface ILocationBO {
    public abstract List FindAllLocation();

    public abstract List<Location> FindEnabledLocation();

    public abstract boolean addLocation(Location paramLocation);

    public abstract String delLocation(Class<Location> paramClass, String paramString);

    public abstract String updateLocation(Location paramLocation);

    public abstract Location loadLocation(String paramString);

    public abstract void updateLocationSwapSortedId(String paramString1, String paramString2);

    public abstract void saveLocationSortIdByBatch(String[] paramArrayOfString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.bo.ILocationBO JD-Core Version: 0.5.4
 */