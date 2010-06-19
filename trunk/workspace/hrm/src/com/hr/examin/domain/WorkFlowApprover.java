package com.hr.examin.domain;

import com.hr.examin.domain.base.BaseWorkFlowApprover;

public class WorkFlowApprover extends BaseWorkFlowApprover {
    private String nextApproverDesc;

    public String getNextApproverDesc() {
        return this.nextApproverDesc;
    }

    public void setNextApproverDesc(String nextApproverDesc) {
        this.nextApproverDesc = nextApproverDesc;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.domain.WorkFlowApprover JD-Core Version: 0.5.4
 */