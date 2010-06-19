package com.hr.configuration.action;

import com.hr.base.BaseAction;

public class SystemConfigInit extends BaseAction {
    private static final long serialVersionUID = 1L;

    public String execute() throws Exception {
        return "success";
    }

    public static String getValue(String param, String value) {
        if ((param == null) || (param.trim().equals(""))) {
            return value;
        }
        return param;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.action.SystemConfigInit JD-Core Version: 0.5.4
 */