package com.hr.configuration.domain;

import com.hr.configuration.domain.base.BaseSyslog;
import com.hr.profile.domain.Employee;
import java.util.Date;

public class Syslog extends BaseSyslog {
    private static final long serialVersionUID = 1L;

    public Syslog() {
    }

    public Syslog(String id) {
        super(id);
    }

    public Syslog(String id, Employee slChangeEmpno, Integer slType, String slTableName,
            String slRecordId, String slAction, Date slChangeTime) {
        super(id, slChangeEmpno, slType, slTableName, slRecordId, slAction, slChangeTime);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.domain.Syslog JD-Core Version: 0.5.4
 */