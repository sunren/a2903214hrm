package com.hr.compensation.action;

import com.hr.base.BaseAction;

public class CompensationConfig extends BaseAction {
    private static final long serialVersionUID = 1L;
    private String changeIodef;

    public String execute() throws Exception {
        return "success";
    }

    public String getChangeIodef() {
        return this.changeIodef;
    }

    public void setChangeIodef(String changeIodef) {
        this.changeIodef = changeIodef;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.action.CompensationConfig JD-Core Version: 0.5.4
 */