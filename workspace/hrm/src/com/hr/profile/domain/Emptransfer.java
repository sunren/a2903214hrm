package com.hr.profile.domain;

import com.hr.configuration.domain.Department;
import com.hr.profile.domain.base.BaseEmptransfer;

public class Emptransfer extends BaseEmptransfer {
    private static final long serialVersionUID = 1L;
    private Department eftOldBranch;
    private Department eftOldDeptNo1;
    private Department eftOldDeptNo2;
    private Department eftOldDeptNo3;
    private Department eftOldDeptNo4;
    private Department eftOldDeptNo5;
    private Department eftNewBranch;
    private Department eftNewDeptNo1;
    private Department eftNewDeptNo2;
    private Department eftNewDeptNo3;
    private Department eftNewDeptNo4;
    private Department eftNewDeptNo5;
    private Employee eftNewSupNo;

    public Emptransfer() {
    }

    public Emptransfer(String eftId) {
        super(eftId);
    }

    public Department getEftOldBranch() {
        return this.eftOldBranch;
    }

    public void setEftOldBranch(Department eftOldBranch) {
        this.eftOldBranch = eftOldBranch;
    }

    public Department getEftOldDeptNo1() {
        return this.eftOldDeptNo1;
    }

    public void setEftOldDeptNo1(Department eftOldDeptNo1) {
        this.eftOldDeptNo1 = eftOldDeptNo1;
    }

    public Department getEftOldDeptNo2() {
        return this.eftOldDeptNo2;
    }

    public void setEftOldDeptNo2(Department eftOldDeptNo2) {
        this.eftOldDeptNo2 = eftOldDeptNo2;
    }

    public Department getEftOldDeptNo3() {
        return this.eftOldDeptNo3;
    }

    public void setEftOldDeptNo3(Department eftOldDeptNo3) {
        this.eftOldDeptNo3 = eftOldDeptNo3;
    }

    public Department getEftNewBranch() {
        return this.eftNewBranch;
    }

    public void setEftNewBranch(Department eftNewBranch) {
        this.eftNewBranch = eftNewBranch;
    }

    public Department getEftNewDeptNo1() {
        return this.eftNewDeptNo1;
    }

    public void setEftNewDeptNo1(Department eftNewDeptNo1) {
        this.eftNewDeptNo1 = eftNewDeptNo1;
    }

    public Department getEftNewDeptNo2() {
        return this.eftNewDeptNo2;
    }

    public void setEftNewDeptNo2(Department eftNewDeptNo2) {
        this.eftNewDeptNo2 = eftNewDeptNo2;
    }

    public Department getEftNewDeptNo3() {
        return this.eftNewDeptNo3;
    }

    public void setEftNewDeptNo3(Department eftNewDeptNo3) {
        this.eftNewDeptNo3 = eftNewDeptNo3;
    }

    public Department getEftOldDeptNo4() {
        return this.eftOldDeptNo4;
    }

    public void setEftOldDeptNo4(Department eftOldDeptNo4) {
        this.eftOldDeptNo4 = eftOldDeptNo4;
    }

    public Department getEftOldDeptNo5() {
        return this.eftOldDeptNo5;
    }

    public void setEftOldDeptNo5(Department eftOldDeptNo5) {
        this.eftOldDeptNo5 = eftOldDeptNo5;
    }

    public Department getEftNewDeptNo4() {
        return this.eftNewDeptNo4;
    }

    public void setEftNewDeptNo4(Department eftNewDeptNo4) {
        this.eftNewDeptNo4 = eftNewDeptNo4;
    }

    public Department getEftNewDeptNo5() {
        return this.eftNewDeptNo5;
    }

    public void setEftNewDeptNo5(Department eftNewDeptNo5) {
        this.eftNewDeptNo5 = eftNewDeptNo5;
    }

    public Employee getEftNewSupNo() {
        return this.eftNewSupNo;
    }

    public void setEftNewSupNo(Employee eftNewSupNo) {
        this.eftNewSupNo = eftNewSupNo;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.domain.Emptransfer JD-Core Version: 0.5.4
 */