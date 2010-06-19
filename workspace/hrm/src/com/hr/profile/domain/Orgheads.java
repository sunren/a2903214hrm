package com.hr.profile.domain;

import com.hr.base.BaseDomain;
import java.io.Serializable;

public class Orgheads extends BaseDomain implements Serializable {
    private String orgheadsId;
    private String orgheadsOrgNo;
    private String orgheadsResponsibility;
    private String orgheadsEmpNo;
    private String orgName;
    private String empName;

    public Orgheads() {
    }

    public Orgheads(String orgheadsId, String orgheadsOrgNo, String orgheadsResponsibility,
            String orgheadsEmpNo) {
        this.orgheadsId = orgheadsId;
        this.orgheadsOrgNo = orgheadsOrgNo;
        this.orgheadsResponsibility = orgheadsResponsibility;
        this.orgheadsEmpNo = orgheadsEmpNo;
    }

    public String getOrgheadsId() {
        return this.orgheadsId;
    }

    public void setOrgheadsId(String orgheadsId) {
        this.orgheadsId = orgheadsId;
    }

    public String getOrgheadsOrgNo() {
        return this.orgheadsOrgNo;
    }

    public void setOrgheadsOrgNo(String orgheadsOrgNo) {
        this.orgheadsOrgNo = orgheadsOrgNo;
    }

    public String getOrgheadsResponsibility() {
        return this.orgheadsResponsibility;
    }

    public void setOrgheadsResponsibility(String orgheadsResponsibility) {
        this.orgheadsResponsibility = orgheadsResponsibility;
    }

    public String getOrgheadsEmpNo() {
        return this.orgheadsEmpNo;
    }

    public void setOrgheadsEmpNo(String orgheadsEmpNo) {
        this.orgheadsEmpNo = orgheadsEmpNo;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getEmpName() {
        return this.empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.domain.Orgheads JD-Core Version: 0.5.4
 */