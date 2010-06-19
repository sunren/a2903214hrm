package com.hr.io.domain;

import com.hr.profile.domain.Employee;
import java.util.Date;

public class ExaminCardBean {
    private Employee emp;
    private String aodMachineNo;
    private Date aodAttdDate;
    private int timeCount;
    private String aodCardTime1;
    private String aodCardTime2;
    private String aodCardTime3;
    private String aodCardTime4;
    private String aodCardTime5;
    private String aodCardTime6;
    private String aodCardTime7;
    private String aodCardTime8;
    private String aodMemo;

    public ExaminCardBean() {
        this.timeCount = 8;
    }

    public Date getAodAttdDate() {
        return this.aodAttdDate;
    }

    public void setAodAttdDate(Date aodAttdDate) {
        this.aodAttdDate = aodAttdDate;
    }

    public String getAodCardTime1() {
        return this.aodCardTime1;
    }

    public void setAodCardTime1(String aodCardTime1) {
        this.aodCardTime1 = aodCardTime1;
    }

    public String getAodCardTime2() {
        return this.aodCardTime2;
    }

    public void setAodCardTime2(String aodCardTime2) {
        this.aodCardTime2 = aodCardTime2;
    }

    public String getAodCardTime3() {
        return this.aodCardTime3;
    }

    public void setAodCardTime3(String aodCardTime3) {
        this.aodCardTime3 = aodCardTime3;
    }

    public String getAodCardTime4() {
        return this.aodCardTime4;
    }

    public void setAodCardTime4(String aodCardTime4) {
        this.aodCardTime4 = aodCardTime4;
    }

    public String getAodCardTime5() {
        return this.aodCardTime5;
    }

    public void setAodCardTime5(String aodCardTime5) {
        this.aodCardTime5 = aodCardTime5;
    }

    public String getAodCardTime6() {
        return this.aodCardTime6;
    }

    public void setAodCardTime6(String aodCardTime6) {
        this.aodCardTime6 = aodCardTime6;
    }

    public String getAodCardTime7() {
        return this.aodCardTime7;
    }

    public void setAodCardTime7(String aodCardTime7) {
        this.aodCardTime7 = aodCardTime7;
    }

    public String getAodCardTime8() {
        return this.aodCardTime8;
    }

    public void setAodCardTime8(String aodCardTime8) {
        this.aodCardTime8 = aodCardTime8;
    }

    public String getAodMachineNo() {
        return this.aodMachineNo;
    }

    public void setAodMachineNo(String aodMachineNo) {
        this.aodMachineNo = aodMachineNo;
    }

    public String getAodMemo() {
        return this.aodMemo;
    }

    public void setAodMemo(String aodMemo) {
        this.aodMemo = aodMemo;
    }

    public int getTimeCount() {
        return this.timeCount;
    }

    public void setTimeCount(int timeCount) {
        this.timeCount = timeCount;
    }

    public Employee getEmp() {
        return this.emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.io.domain.ExaminCardBean JD-Core Version: 0.5.4
 */