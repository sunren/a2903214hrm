package com.hr.information.domain;

import com.hr.configuration.domain.Infoclass;
import com.hr.information.domain.base.BaseInformation;
import com.hr.profile.domain.Employee;
import java.util.Date;

public class Information extends BaseInformation {
    private static final long serialVersionUID = 1L;

    public Information() {
    }

    public Information(String id) {
        super(id);
    }

    public Information(String id, Employee infoCreateBy, Infoclass infoClass,
            Employee infoLastChangeBy, String infoTitle, String infoBreif, String infoContent,
            Integer infoViewedNum, Integer infoStatus, Date infoCreateTime, Date infoLastChangeTime) {
        super(id, infoCreateBy, infoClass, infoLastChangeBy, infoTitle, infoBreif, infoContent,
                infoViewedNum, infoStatus, infoCreateTime, infoLastChangeTime);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.information.domain.Information JD-Core Version: 0.5.4
 */