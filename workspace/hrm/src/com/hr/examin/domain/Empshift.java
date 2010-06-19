package com.hr.examin.domain;

import com.hr.examin.domain.base.BaseEmpshift;
import com.hr.profile.domain.Employee;
import java.io.Serializable;
import java.util.Date;

public class Empshift extends BaseEmpshift implements Serializable, Cloneable {
    private static final long serialVersionUID = -5789938553758212621L;

    public Empshift() {
    }

    public Empshift(String id) {
        super(id);
    }

    public Empshift(String id, Employee empshiftEmpNo, Attendshift empshiftShiftNo,
            Date empshiftDate) {
        super(id, empshiftEmpNo, empshiftShiftNo, empshiftDate);
    }

    public Empshift clone() {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return (Empshift) obj;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.domain.Empshift JD-Core Version: 0.5.4
 */