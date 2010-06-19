package com.hr.examin.domain.base;

import com.hr.base.BaseDomain;
import com.hr.profile.domain.Employee;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseAttdoriginaldata extends BaseDomain implements Serializable {
    public static String REF = "Attdoriginaldata";
    public static String PROP_AOD_ID = "aodId";
    public static String PROP_AOD_EMP_NO = "attdEmp";
    public static String PROP_AOD_ATTD_DATE = "aodAttdDate";
    public static String PROP_AOD_CARD_TIME = "aodCardTime";
    public static String PROP_AOD_TTD_MACHINE_NO = "aodTtdMachineNo";
    public static String PROP_AOD_LAST_MODIFY_USER = "aodLastModifyUser";
    public static String PROP_AOD_LAST_MODIFY_TIME = "aodLastModifyTime";
    public static String PROP_AOD_STATUS = "aodStatus";

    private int hashCode = -2147483648;
    private String aodId;
    private Employee attdEmp;
    private Date aodCardTime;
    private String aodEmpDistinctNo;
    private Date aodAttdDate;
    private String aodTtdMachineNo;
    private Integer aodStatus;
    private String aodLastModifyUser;
    private Date aodLastModifyTime;
    private String aodMemo;

    public BaseAttdoriginaldata() {
        initialize();
    }

    public BaseAttdoriginaldata(String aodId) {
        setAodId(aodId);
        initialize();
    }

    public BaseAttdoriginaldata(String aodEmpDistinctNo, Date aodAttdDate, Date aodCardTime) {
        this.aodEmpDistinctNo = aodEmpDistinctNo;
        this.aodAttdDate = aodAttdDate;
        this.aodCardTime = aodCardTime;
    }

    public BaseAttdoriginaldata(String aodId, Employee attdEmp, Date aodCardTime,
            String aodEmpDistinctNo, Date aodAttdDate, String aodTtdMachineNo, Integer aodStatus,
            String aodLastModifyUser, Date aodLastModifyTime, String aodMemo) {
        this.aodId = aodId;
        this.attdEmp = attdEmp;
        this.aodCardTime = aodCardTime;
        this.aodEmpDistinctNo = aodEmpDistinctNo;
        this.aodAttdDate = aodAttdDate;
        this.aodTtdMachineNo = aodTtdMachineNo;
        this.aodStatus = aodStatus;
        this.aodLastModifyUser = aodLastModifyUser;
        this.aodLastModifyTime = aodLastModifyTime;
        this.aodMemo = aodMemo;
    }

    protected void initialize() {
    }

    public String getAodId() {
        return this.aodId;
    }

    public void setAodId(String aodId) {
        this.aodId = aodId;
    }

    public Employee getAttdEmp() {
        return this.attdEmp;
    }

    public void setAttdEmp(Employee attdEmp) {
        this.attdEmp = attdEmp;
    }

    public Date getAodCardTime() {
        return this.aodCardTime;
    }

    public void setAodCardTime(Date aodCardTime) {
        this.aodCardTime = aodCardTime;
    }

    public String getAodEmpDistinctNo() {
        return this.aodEmpDistinctNo;
    }

    public void setAodEmpDistinctNo(String aodEmpDistinctNo) {
        this.aodEmpDistinctNo = aodEmpDistinctNo;
    }

    public Date getAodAttdDate() {
        return this.aodAttdDate;
    }

    public void setAodAttdDate(Date aodAttdDate) {
        this.aodAttdDate = aodAttdDate;
    }

    public String getAodTtdMachineNo() {
        return this.aodTtdMachineNo;
    }

    public void setAodTtdMachineNo(String aodTtdMachineNo) {
        this.aodTtdMachineNo = aodTtdMachineNo;
    }

    public Integer getAodStatus() {
        return this.aodStatus;
    }

    public void setAodStatus(Integer aodStatus) {
        this.aodStatus = aodStatus;
    }

    public String getAodLastModifyUser() {
        return this.aodLastModifyUser;
    }

    public void setAodLastModifyUser(String aodLastModifyUser) {
        this.aodLastModifyUser = aodLastModifyUser;
    }

    public Date getAodLastModifyTime() {
        return this.aodLastModifyTime;
    }

    public void setAodLastModifyTime(Date aodLastModifyTime) {
        this.aodLastModifyTime = aodLastModifyTime;
    }

    public String getAodMemo() {
        return this.aodMemo;
    }

    public void setAodMemo(String aodMemo) {
        this.aodMemo = aodMemo;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.domain.base.BaseAttdoriginaldata JD-Core Version: 0.5.4
 */