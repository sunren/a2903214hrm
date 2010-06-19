package com.hr.examin.core;

import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Overtimerequest;

public abstract interface ExaminAuditor {
    public abstract String auditLeaveRequest(Leaverequest paramLeaverequest, String paramString);

    public abstract String auditOvertimeRequest(Overtimerequest paramOvertimerequest,
            String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.core.ExaminAuditor JD-Core Version: 0.5.4
 */