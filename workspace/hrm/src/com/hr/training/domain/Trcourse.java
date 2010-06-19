package com.hr.training.domain;

import com.hr.profile.domain.Employee;
import com.hr.training.domain.base.BaseTrcourse;
import java.util.Date;

public class Trcourse extends BaseTrcourse {
    private static final long serialVersionUID = 1L;

    public Trcourse() {
    }

    public Trcourse(String trcNo) {
        super(trcNo);
    }

    public Trcourse(String trcNo, Trtype trcType, Employee trcCreateBy, Employee trcLastChangeBy,
            String trcName, String trcInfo, Integer trcStatus, Date trcCreateTime,
            Date trcLastChangeTime) {
        super(trcNo, trcType, trcCreateBy, trcLastChangeBy, trcName, trcInfo, trcStatus,
                trcCreateTime, trcLastChangeTime);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.training.domain.Trcourse JD-Core Version: 0.5.4
 */