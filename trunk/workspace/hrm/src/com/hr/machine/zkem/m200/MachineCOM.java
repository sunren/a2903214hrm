package com.hr.machine.zkem.m200;

import com.jacob.com.Dispatch;

public class MachineCOM {
    private Dispatch comObj;

    public void initCOM() {
        Dispatch czkem = new Dispatch("tipinf_att_zkem.ATTZKEM");
        Dispatch.call(czkem, "createZKEM");
        setComObj(czkem);
    }

    public Dispatch getComObj() {
        return this.comObj;
    }

    public void setComObj(Dispatch comObj) {
        this.comObj = comObj;
    }

    public static void main(String[] args) {
        MachineCOM com = new MachineCOM();
        com.initCOM();
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.machine.zkem.m200.MachineCOM JD-Core Version: 0.5.4
 */