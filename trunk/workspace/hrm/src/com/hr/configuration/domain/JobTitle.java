package com.hr.configuration.domain;

import com.hr.configuration.domain.base.BaseJobTitle;

public class JobTitle extends BaseJobTitle {
    private String defaultAccount;
    private String defaultJg;

    public JobTitle() {
    }

    public JobTitle(String jobtitleNo) {
        setJobtitleNo(jobtitleNo);
    }

    public JobTitle(String jobtitleName, Integer jobtitleNeedGmApprove, Integer jobtitleSortId) {
        super(jobtitleName, jobtitleNeedGmApprove, jobtitleSortId);
    }

    public String getDefaultAccount() {
        return this.defaultAccount;
    }

    public void setDefaultAccount(String defaultAccount) {
        this.defaultAccount = defaultAccount;
    }

    public String getDefaultJg() {
        return this.defaultJg;
    }

    public void setDefaultJg(String defaultJg) {
        this.defaultJg = defaultJg;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.domain.JobTitle JD-Core Version: 0.5.4
 */