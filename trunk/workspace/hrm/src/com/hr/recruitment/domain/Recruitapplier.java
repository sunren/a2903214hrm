package com.hr.recruitment.domain;

import com.hr.profile.domain.Employee;
import com.hr.recruitment.domain.base.BaseRecruitapplier;
import java.util.Date;

public class Recruitapplier extends BaseRecruitapplier {
    private static final long serialVersionUID = 1L;

    public Recruitapplier() {
    }

    public Recruitapplier(String id) {
        super(id);
    }

    public Recruitapplier(String id, Recruitchannel recaChannel, Employee recaLastChangeBy,
            Recruitplan recaPlan, Employee recaCreateBy, String recaName, Date recaCreateTime,
            Date recaLastChangeTime) {
        super(id, recaChannel, recaLastChangeBy, recaPlan, recaCreateBy, recaName, recaCreateTime,
                recaLastChangeTime);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.recruitment.domain.Recruitapplier JD-Core Version: 0.5.4
 */