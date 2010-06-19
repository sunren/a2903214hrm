package com.hr.examin.domain.base;

import com.hr.base.BaseDomain;
import com.hr.configuration.domain.AttdMachine;
import com.hr.profile.domain.Employee;
import java.io.Serializable;

public class BaseAttdSyncRecord extends BaseDomain implements Serializable {
    public static String REF = "AttdSyncRecord";
    public static String PROP_ASR_ID = "srcId";
    public static String PROP_ASR_EMP_NO = "asrEmp";
    public static String PROP_ASR_ATTD_MACHINE_NO = "asrAttdMachine";
    public static String PROP_ASR_EMP_MACHINE_NO = "asrEmpMachineNo";
    public static String PROP_ASR_CARD_NO = "asrEmpCardNo";
    public static String PROP_ASR_SYNC = "asrSync";
    private String asrId;
    private Employee asrEmp;
    private AttdMachine asrAttdMachine;
    private Integer asrEmpMachineNo;
    private String asrEmpCardNo;
    private Integer asrSync;

    public BaseAttdSyncRecord() {
        initialize();
    }

    public BaseAttdSyncRecord(String id) {
        setAsrId(id);
        initialize();
    }

    public BaseAttdSyncRecord(String asrId, Employee asrEmp, AttdMachine asrAttdMachine,
            Integer asrEmpMachineNo, String asrEmpCardNo, Integer asrSync) {
        this.asrId = asrId;
        this.asrEmp = asrEmp;
        this.asrAttdMachine = asrAttdMachine;
        this.asrEmpMachineNo = asrEmpMachineNo;
        this.asrEmpCardNo = asrEmpCardNo;
        this.asrSync = asrSync;
    }

    protected void initialize() {
    }

    public String getAsrId() {
        return this.asrId;
    }

    public void setAsrId(String asrId) {
        this.asrId = asrId;
    }

    public String getAsrEmpCardNo() {
        return this.asrEmpCardNo;
    }

    public void setAsrEmpCardNo(String asrEmpCardNo) {
        this.asrEmpCardNo = asrEmpCardNo;
    }

    public Employee getAsrEmp() {
        return this.asrEmp;
    }

    public void setAsrEmp(Employee asrEmp) {
        this.asrEmp = asrEmp;
    }

    public AttdMachine getAsrAttdMachine() {
        return this.asrAttdMachine;
    }

    public void setAsrAttdMachine(AttdMachine asrAttdMachine) {
        this.asrAttdMachine = asrAttdMachine;
    }

    public Integer getAsrEmpMachineNo() {
        return this.asrEmpMachineNo;
    }

    public void setAsrEmpMachineNo(Integer asrEmpMachineNo) {
        this.asrEmpMachineNo = asrEmpMachineNo;
    }

    public Integer getAsrSync() {
        return this.asrSync;
    }

    public void setAsrSync(Integer asrSync) {
        this.asrSync = asrSync;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.domain.base.BaseAttdSyncRecord JD-Core Version: 0.5.4
 */