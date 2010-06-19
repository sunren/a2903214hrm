package com.hr.examin.domain;

import com.hr.examin.domain.base.BaseAttdSyncRecord;
import java.io.Serializable;

public class AttdSyncRecord extends BaseAttdSyncRecord implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    public AttdSyncRecord clone() {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return (AttdSyncRecord) obj;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.domain.AttdSyncRecord JD-Core Version: 0.5.4
 */