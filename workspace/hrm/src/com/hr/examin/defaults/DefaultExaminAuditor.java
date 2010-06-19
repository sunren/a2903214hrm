package com.hr.examin.defaults;

import com.hr.examin.core.ExaminAuditor;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Overtimerequest;

public class DefaultExaminAuditor implements ExaminAuditor {
    public String auditLeaveRequest(Leaverequest leaverequest, String auditor) {
        return null;
    }

    public String auditOvertimeRequest(Overtimerequest overtimerequest, String auditor) {
        return null;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.defaults.DefaultExaminAuditor JD-Core Version: 0.5.4
 */