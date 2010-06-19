package com.hr.examin.domain;

import com.hr.examin.domain.base.BaseAttdoriginaldata;
import com.hr.profile.domain.Employee;
import java.util.Date;

public class Attdoriginaldata extends BaseAttdoriginaldata {
    private static final long serialVersionUID = 1L;

    public Attdoriginaldata() {
    }

    public Attdoriginaldata(String id) {
        super(id);
    }

    public Attdoriginaldata(String aodId, Employee attdEmp, Date aodCardTime,
            String aodEmpDistinctNo, Date aodAttdDate, String aodTtdMachineNo, Integer aodStatus,
            String aodLastModifyUser, Date aodLastModifyTime, String memo) {
        super(aodId, attdEmp, aodCardTime, aodEmpDistinctNo, aodAttdDate, aodTtdMachineNo,
                aodStatus, aodLastModifyUser, aodLastModifyTime, memo);
    }

    public Attdoriginaldata clone() {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return (Attdoriginaldata) obj;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.domain.Attdoriginaldata JD-Core Version: 0.5.4
 */