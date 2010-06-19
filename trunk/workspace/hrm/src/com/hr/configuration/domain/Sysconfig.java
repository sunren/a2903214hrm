package com.hr.configuration.domain;

import com.hr.base.BaseDomain;
import java.io.Serializable;

public class Sysconfig extends BaseDomain implements Serializable {
    public static String PROP_SYSCONFIG_ID = "sysconfigId";
    public static String PROP_SYSCONFIG_KEY = "sysconfigKey";
    public static String PROP_SYSCONFIG_VALUE = "sysconfigValue";
    private String sysconfigId;
    private String sysconfigKey;
    private String sysconfigValue;

    public Sysconfig() {
    }

    public Sysconfig(String sysconfigId, String sysconfigKey, String sysconfigValue) {
        this.sysconfigId = sysconfigId;
        this.sysconfigKey = sysconfigKey;
        this.sysconfigValue = sysconfigValue;
    }

    public String getSysconfigId() {
        return this.sysconfigId;
    }

    public void setSysconfigId(String sysconfigId) {
        this.sysconfigId = sysconfigId;
    }

    public String getSysconfigKey() {
        return this.sysconfigKey;
    }

    public void setSysconfigKey(String sysconfigKey) {
        this.sysconfigKey = sysconfigKey;
    }

    public String getSysconfigValue() {
        return this.sysconfigValue;
    }

    public void setSysconfigValue(String sysconfigValue) {
        this.sysconfigValue = sysconfigValue;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.domain.Sysconfig JD-Core Version: 0.5.4
 */