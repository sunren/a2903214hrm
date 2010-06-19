package com.hr.training.domain;

import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.JobTitle;
import com.hr.profile.domain.Employee;
import com.hr.training.domain.base.BaseTremployeeplan;
import java.util.Date;

public class Tremployeeplan extends BaseTremployeeplan {
    private static final long serialVersionUID = 1L;

    public Tremployeeplan() {
    }

    public Tremployeeplan(String trpId) {
        super(trpId);
    }

    public Tremployeeplan(String trpId, Department trpTraineeDept, Employee trpCreateBy,
            Employee trpTraineeNo, Trcourseplan trpTrcp, Employee trpLastChangeBy,
            JobTitle trpTraineeJobTitle, Integer trpStatus, Date trpCreateTime,
            Date trpLastChangeTime) {
        super(trpId, trpTraineeDept, trpCreateBy, trpTraineeNo, trpTrcp, trpLastChangeBy,
                trpTraineeJobTitle, trpStatus, trpCreateTime, trpLastChangeTime);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.training.domain.Tremployeeplan JD-Core Version: 0.5.4
 */