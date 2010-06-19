package com.hr.configuration.domain;

import com.hr.configuration.domain.base.BaseAttdMachine;
import java.io.Serializable;

public class AttdMachine extends BaseAttdMachine implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    public AttdMachine() {
    }

    public AttdMachine(String id) {
        super(id);
    }

    public AttdMachine(String macId, String macNo, String macIP, String macPort,
            Integer macPassword, Integer macType, Integer macStatus, String macDesc,
            Integer macSortId) {
        super(macId, macNo, macIP, macPort, macPassword, macType, macStatus, macDesc, macSortId);
    }

    public AttdMachine clone() {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return (AttdMachine) obj;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.domain.AttdMachine JD-Core Version: 0.5.4
 */