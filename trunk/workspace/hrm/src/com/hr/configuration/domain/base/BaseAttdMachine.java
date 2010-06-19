package com.hr.configuration.domain.base;

import com.hr.base.BaseDomain;
import java.io.Serializable;

public abstract class BaseAttdMachine extends BaseDomain implements Serializable {
    public static String REF = "AttdSyncRecord";
    public static String PROP_MAC_ID = "macId";
    public static String PROP_MAC_NO = "macNo";
    public static String PROP_MAC_IP = "macIP";
    public static String PROP_MAC_PORT = "macPort";
    public static String PROP_MAC_PASSWORD = "macPassword";
    public static String PROP_MAC_TYPE = "macType";
    public static String PROP_MAC_STATUS = "macStatus";
    public static String PROP_MAC_DESC = "macDesc";
    public static String PROP_MAC_SORT_ID = "macSortId";
    private String macId;
    private String macNo;
    private String macIP;
    private String macPort;
    private Integer macPassword;
    private Integer macType;
    private Integer macStatus;
    private String macDesc;
    private Integer macSortId;

    public BaseAttdMachine() {
        initialize();
    }

    public BaseAttdMachine(String id) {
        setMacId(id);
        initialize();
    }

    public BaseAttdMachine(String macId, String macNo, String macIP, String macPort,
            Integer macPassword, Integer macType, Integer macStatus, String macDesc,
            Integer macSortId) {
        this.macId = macId;
        this.macNo = macNo;
        this.macIP = macIP;
        this.macPort = macPort;
        this.macPassword = macPassword;
        this.macType = macType;
        this.macStatus = macStatus;
        this.macDesc = macDesc;
        this.macSortId = macSortId;
    }

    protected void initialize() {
    }

    public String getMacId() {
        return this.macId;
    }

    public void setMacId(String macId) {
        this.macId = macId;
    }

    public String getMacNo() {
        return this.macNo;
    }

    public void setMacNo(String macNo) {
        this.macNo = macNo;
    }

    public String getMacIP() {
        return this.macIP;
    }

    public void setMacIP(String macIP) {
        this.macIP = macIP;
    }

    public String getMacPort() {
        return this.macPort;
    }

    public void setMacPort(String macPort) {
        this.macPort = macPort;
    }

    public Integer getMacPassword() {
        return this.macPassword;
    }

    public void setMacPassword(Integer macPassword) {
        this.macPassword = macPassword;
    }

    public Integer getMacType() {
        return this.macType;
    }

    public void setMacType(Integer macType) {
        this.macType = macType;
    }

    public Integer getMacStatus() {
        return this.macStatus;
    }

    public void setMacStatus(Integer macStatus) {
        this.macStatus = macStatus;
    }

    public String getMacDesc() {
        return this.macDesc;
    }

    public void setMacDesc(String macDesc) {
        this.macDesc = macDesc;
    }

    public Integer getMacSortId() {
        return this.macSortId;
    }

    public void setMacSortId(Integer macSortId) {
        this.macSortId = macSortId;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.domain.base.BaseAttdMachine JD-Core Version: 0.5.4
 */