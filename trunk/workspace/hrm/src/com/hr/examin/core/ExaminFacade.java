package com.hr.examin.core;

import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Overtimerequest;

public abstract interface ExaminFacade {
    public abstract String doLeaveRequest(Leaverequest paramLeaverequest);

    public abstract String doOvertimeRequest(Overtimerequest paramOvertimerequest);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.core.ExaminFacade JD-Core Version: 0.5.4
 */