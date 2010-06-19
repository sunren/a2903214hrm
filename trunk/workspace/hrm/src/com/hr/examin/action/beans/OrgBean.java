package com.hr.examin.action.beans;

public class OrgBean {
    private String orgId;
    private int level;
    private double limitHours;
    private String nextApprover;
    private String nextApproverDesc;

    public OrgBean() {
        this.orgId = "";
        this.level = 0;
        this.limitHours = 0.0D;
    }

    public OrgBean(String orgId, int level) {
        this.orgId = orgId;
        this.level = level;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getLimitHours() {
        return this.limitHours;
    }

    public void setLimitHours(double limitHours) {
        this.limitHours = limitHours;
    }

    public String getNextApprover() {
        return this.nextApprover;
    }

    public void setNextApprover(String nextApprover) {
        this.nextApprover = nextApprover;
    }

    public String getNextApproverDesc() {
        return this.nextApproverDesc;
    }

    public void setNextApproverDesc(String nextApproverDesc) {
        this.nextApproverDesc = nextApproverDesc;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.action.beans.OrgBean JD-Core Version: 0.5.4
 */