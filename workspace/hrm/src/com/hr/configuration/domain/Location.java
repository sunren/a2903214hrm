package com.hr.configuration.domain;

import com.hr.configuration.domain.base.BaseLocation;

public class Location extends BaseLocation {
    private static final long serialVersionUID = 1L;
    private String headNo;
    private String headName;

    public Location() {
    }

    public Location(String id) {
        super(id);
    }

    public Location(String id, String locationName, Integer locationSortId) {
        super(id, locationName, locationSortId);
    }

    public String getHeadName() {
        return this.headName;
    }

    public String getHeadNo() {
        return this.headNo;
    }

    public void setHeadNo(String headNo) {
        this.headNo = headNo;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.domain.Location JD-Core Version: 0.5.4
 */