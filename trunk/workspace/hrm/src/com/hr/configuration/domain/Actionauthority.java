package com.hr.configuration.domain;

import com.hr.configuration.domain.base.BaseActionauthority;

public class Actionauthority extends BaseActionauthority {
    private static final long serialVersionUID = 1L;

    public Actionauthority() {
    }

    public Actionauthority(Integer id) {
        super(id);
    }

    public Actionauthority(Integer id, String actionauthActionName, Integer actionauthLookupSeq,
            String actionauthModuleNo, String actionauthDesc) {
        super(id, actionauthActionName, actionauthLookupSeq, actionauthModuleNo, actionauthDesc);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.domain.Actionauthority JD-Core Version: 0.5.4
 */