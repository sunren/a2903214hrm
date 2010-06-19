package com.hr.examin.domain;

import com.hr.base.ComonBeans;
import com.hr.base.wf.Workflow;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Location;
import com.hr.examin.domain.base.BaseLeaverequest;
import com.hr.profile.domain.Employee;
import java.math.BigDecimal;
import java.util.Date;

public class Leaverequest extends BaseLeaverequest {
    private static final long serialVersionUID = 1L;
    private BigDecimal useableDays;
    private BigDecimal useableHours;
    private BigDecimal usedDays;
    private BigDecimal usedHours;
    private BigDecimal remainDays;
    private BigDecimal remainHours;
    private boolean applyLRByDay;
    private double hoursPerDay;
    private String lrStatusMean;
    private Workflow workflow;

    public Leaverequest() {
    }

    public Leaverequest(String lrId) {
        super(lrId);
    }

    public Leaverequest(String lrId, Department lrEmpDept, Leavetype lrLtNo, Employee lrEmpNo,
            Location lrEmpLocationNo, Integer lrNo, String lrReason, Date lrStartDate,
            Date lrEndDate, BigDecimal lrTotalHours, Integer lrStatus, Date lrCreateTime,
            Date lrLastChangeTime) {
        super(lrId, lrEmpDept, lrLtNo, lrEmpNo, lrEmpLocationNo, lrNo, lrReason, lrStartDate,
                lrEndDate, lrTotalHours, lrStatus, lrCreateTime, lrLastChangeTime);
    }

    public void setLrStatus(Integer lrStatus) {
        super.setLrStatus(lrStatus);
        if (lrStatus == null) {
            this.lrStatusMean = null;
        } else {
            String status = ComonBeans.getParameterValue(PROP_LR_STATUS, new String[] { lrStatus
                    .toString() });
            if (status != null)
                this.lrStatusMean = status.trim();
            else
                this.lrStatusMean = null;
        }
    }

    public BigDecimal getUseableDays() {
        return this.useableDays;
    }

    public void setUseableDays(BigDecimal useableDays) {
        this.useableDays = useableDays;
    }

    public void setUseableDays(Double useableDays) {
        this.useableDays = new BigDecimal(useableDays.doubleValue());
    }

    public BigDecimal getUseableHours() {
        return this.useableHours;
    }

    public void setUseableHours(BigDecimal useableHours) {
        this.useableHours = useableHours;
    }

    public void setUseableHours(Double useableHours) {
        this.useableHours = new BigDecimal(useableHours.doubleValue());
    }

    public BigDecimal getUsedDays() {
        return this.usedDays;
    }

    public void setUsedDays(BigDecimal usedDays) {
        this.usedDays = usedDays;
    }

    public void setUsedDays(Double usedDays) {
        this.usedDays = new BigDecimal(usedDays.doubleValue());
    }

    public BigDecimal getUsedHours() {
        return this.usedHours;
    }

    public void setUsedHours(BigDecimal usedHours) {
        this.usedHours = usedHours;
    }

    public void setUsedHours(Double usedHours) {
        this.usedHours = new BigDecimal(usedHours.doubleValue());
    }

    public BigDecimal getRemainDays() {
        return this.remainDays;
    }

    public void setRemainDays(BigDecimal remainDays) {
        this.remainDays = remainDays;
    }

    public void setRemainDays(Double remainDays) {
        this.remainDays = new BigDecimal(remainDays.doubleValue());
    }

    public BigDecimal getRemainHours() {
        return this.remainHours;
    }

    public void setRemainHours(BigDecimal remainHours) {
        this.remainHours = remainHours;
    }

    public void setRemainHours(Double remainHours) {
        this.remainHours = new BigDecimal(remainHours.doubleValue());
    }

    public boolean isApplyLRByDay() {
        return this.applyLRByDay;
    }

    public void setApplyLRByDay(boolean applyLRByDay) {
        this.applyLRByDay = applyLRByDay;
    }

    public double getHoursPerDay() {
        return this.hoursPerDay;
    }

    public void setHoursPerDay(double hoursPerDay) {
        this.hoursPerDay = hoursPerDay;
    }

    public Workflow getWorkflow() {
        return this.workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public String getLrStatusMean() {
        return this.lrStatusMean;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.domain.Leaverequest JD-Core Version: 0.5.4
 */