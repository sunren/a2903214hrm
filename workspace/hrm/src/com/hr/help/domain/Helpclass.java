package com.hr.help.domain;

import com.hr.help.domain.base.BaseHelpclass;

public class Helpclass extends BaseHelpclass {
    private static final long serialVersionUID = 1L;

    public Helpclass() {
    }

    public Helpclass(String id) {
        super(id);
    }

    public Helpclass(String id, String hcClassName, Integer hcSortId, String hcBrief) {
        super(id, hcClassName, hcSortId, hcBrief);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.help.domain.Helpclass JD-Core Version: 0.5.4
 */