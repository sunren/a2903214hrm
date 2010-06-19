package com.hr.examin.bo;

import com.hr.examin.bo.interfaces.ILeaverequestBO;

public class HourBasedAttendMonthlyBo extends AbstractAttendmonthlyBo {
    private ILeaverequestBO leaveRequestBo;

    public ILeaverequestBO getLeaveRequestBo() {
        return this.leaveRequestBo;
    }

    public void setLeaveRequestBo(ILeaverequestBO leaveRequestBo) {
        this.leaveRequestBo = leaveRequestBo;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.bo.HourBasedAttendMonthlyBo JD-Core Version: 0.5.4
 */