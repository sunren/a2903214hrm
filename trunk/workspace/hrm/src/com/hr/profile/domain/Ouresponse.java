package com.hr.profile.domain;

import com.hr.profile.domain.base.BaseOuresponse;

public class Ouresponse extends BaseOuresponse {
    private static final long serialVersionUID = 1L;
    private String info;

    public Ouresponse() {
    }

    public Ouresponse(String id) {
        super(id);
    }

    public Ouresponse(String id, String ourName, Integer ourSortId) {
        super(id, ourName, ourSortId);
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.domain.Ouresponse JD-Core Version: 0.5.4
 */