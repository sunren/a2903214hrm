package com.hr.examin.domain;

import com.hr.base.ComonBeans;
import com.hr.base.wf.Workflow;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Location;
import com.hr.examin.domain.base.BaseOvertimerequest;
import com.hr.profile.domain.Employee;
import java.math.BigDecimal;
import java.util.Date;

public class Overtimerequest extends BaseOvertimerequest {
    private static final long serialVersionUID = 1L;
    private Workflow workflow;
    private String orStatusMean;

    public Overtimerequest() {
    }

    public Overtimerequest(String orId) {
        super(orId);
    }

    public Overtimerequest(String orId, Overtimetype orOtNo, Department orEmpDept,
            Employee orEmpNo, Location orEmpLocationNo, Integer orNo, String orReason,
            Date orStartDate, Date orEndDate, BigDecimal orTotalHours, Integer orStatus,
            Date orCreateTime, Date orLastChangeTime) {
        super(orId, orOtNo, orEmpDept, orEmpNo, orEmpLocationNo, orNo, orReason, orStartDate,
                orEndDate, orTotalHours, orStatus, orCreateTime, orLastChangeTime);
    }

    public Workflow getWorkflow() {
        return this.workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public void setOrStatus(Integer orStatus) {
        super.setOrStatus(orStatus);
        if (orStatus == null) {
            this.orStatusMean = null;
        } else {
            String status = ComonBeans.getParameterValue(PROP_OR_STATUS, new String[] { orStatus
                    .toString() });
            if (status != null)
                this.orStatusMean = status.trim();
            else
                this.orStatusMean = null;
        }
    }

    public String getOrStatusMean() {
        return this.orStatusMean;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.domain.Overtimerequest JD-Core Version: 0.5.4
 */