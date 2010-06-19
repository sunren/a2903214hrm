package com.hr.examin.support;

import com.hr.examin.bo.ExaminDateUtil;
import com.hr.examin.domain.Attendshift;
import com.hr.profile.domain.Employee;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class AttendDailyMemory {
    private Employee empObj;
    private String id;
    private String empId;
    private String empName;
    private String empDistinctNo;
    private Date examinDate;
    private String shiftId;
    private String shiftName;
    private Date onDutyTime;
    private Date offDutyTime;
    private BigDecimal oughtDutyHours;
    private BigDecimal oughtDutyDays;
    private BigDecimal lateMinutes;
    private BigDecimal earlyMinutes;
    private BigDecimal absentHours;
    private BigDecimal absentDays;
    private BigDecimal leaveHours;
    private BigDecimal leaveDays;
    private BigDecimal overtimeHours;
    private BigDecimal dutyHours;
    private BigDecimal origDutyHours;
    private String displayColor;
    private String comments;
    private Attendshift shift;
    long t5 = 0L;
    long t0 = 0L;
    long t1 = 0L;
    long t2 = 0L;
    long t3 = 0L;

    public AttendDailyMemory() {
        init();
    }

    private void init() {
        this.dutyHours = new BigDecimal(0);
        this.lateMinutes = new BigDecimal(0);
        this.earlyMinutes = new BigDecimal(0);
        this.leaveHours = new BigDecimal(0);
        this.leaveDays = new BigDecimal(0);
        this.overtimeHours = new BigDecimal(0);
        this.absentHours = new BigDecimal(0);
        this.absentDays = new BigDecimal(0);
        this.oughtDutyHours = new BigDecimal(0);
        this.oughtDutyDays = new BigDecimal(0);
        this.id = UUID.randomUUID().toString();
    }

    public void convertFieldsOfDay(String calcDayConfig, String dayHourConfig) {
        this.leaveDays = ExaminDateUtil.convertHourToDay(this.leaveHours, calcDayConfig,
                                                         dayHourConfig);
        this.absentDays = ExaminDateUtil.convertHourToDay(this.absentHours, calcDayConfig,
                                                          dayHourConfig);
        this.oughtDutyDays = ExaminDateUtil.convertHourToDay(this.oughtDutyHours, calcDayConfig,
                                                             dayHourConfig);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmpId() {
        return this.empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return this.empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Date getOnDutyTime() {
        return this.onDutyTime;
    }

    public void setOnDutyTime(Date onDutyTime) {
        this.onDutyTime = onDutyTime;
    }

    public Date getOffDutyTime() {
        return this.offDutyTime;
    }

    public void setOffDutyTime(Date offDutyTime) {
        this.offDutyTime = offDutyTime;
    }

    public String getShiftId() {
        return this.shiftId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    public String getShiftName() {
        return this.shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public BigDecimal getDutyHours() {
        return this.dutyHours;
    }

    public void setDutyHours(BigDecimal dutyHours) {
        this.dutyHours = dutyHours;
    }

    public BigDecimal getLateMinutes() {
        return this.lateMinutes;
    }

    public void setLateMinutes(BigDecimal lateMinutes) {
        this.lateMinutes = lateMinutes;
    }

    public BigDecimal getEarlyMinutes() {
        return this.earlyMinutes;
    }

    public void setEarlyMinutes(BigDecimal earlyMinutes) {
        this.earlyMinutes = earlyMinutes;
    }

    public BigDecimal getLeaveHours() {
        return this.leaveHours;
    }

    public void setLeaveHours(BigDecimal leaveHours) {
        this.leaveHours = leaveHours;
    }

    public BigDecimal getOvertimeHours() {
        return this.overtimeHours;
    }

    public void setOvertimeHours(BigDecimal overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public String getDisplayColor() {
        return this.displayColor;
    }

    public void setDisplayColor(String displayColor) {
        this.displayColor = displayColor;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public BigDecimal getAbsentHours() {
        return this.absentHours;
    }

    public void setAbsentHours(BigDecimal absentTimes) {
        this.absentHours = absentTimes;
    }

    public Date getExaminDate() {
        return this.examinDate;
    }

    public void setExaminDate(Date examinDate) {
        this.examinDate = examinDate;
    }

    public BigDecimal getOrigDutyHours() {
        return this.origDutyHours;
    }

    public void setOrigDutyHours(BigDecimal origDutyHours) {
        this.origDutyHours = origDutyHours;
    }

    public String getEmpDistinctNo() {
        return this.empDistinctNo;
    }

    public void setEmpDistinctNo(String empDistinctNo) {
        this.empDistinctNo = empDistinctNo;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public long getT1() {
        return this.t1;
    }

    public void setT1(long t1) {
        this.t1 = t1;
    }

    public long getT2() {
        return this.t2;
    }

    public void setT2(long t2) {
        this.t2 = t2;
    }

    public long getT3() {
        return this.t3;
    }

    public void setT3(long t3) {
        this.t3 = t3;
    }

    public long getT0() {
        return this.t0;
    }

    public void setT0(long t0) {
        this.t0 = t0;
    }

    public long getT5() {
        return this.t5;
    }

    public void setT5(long t5) {
        this.t5 = t5;
    }

    public Attendshift getShift() {
        return this.shift;
    }

    public void setShift(Attendshift shift) {
        this.shift = shift;
    }

    public Employee getEmpObj() {
        return this.empObj;
    }

    public void setEmpObj(Employee empObj) {
        this.empObj = empObj;
    }

    public BigDecimal getOughtDutyHours() {
        return this.oughtDutyHours;
    }

    public void setOughtDutyHours(BigDecimal oughtDutyHours) {
        this.oughtDutyHours = oughtDutyHours;
    }

    public BigDecimal getAbsentDays() {
        return this.absentDays;
    }

    public void setAbsentDays(BigDecimal absentDays) {
        this.absentDays = absentDays;
    }

    public BigDecimal getLeaveDays() {
        return this.leaveDays;
    }

    public void setLeaveDays(BigDecimal leaveDays) {
        this.leaveDays = leaveDays;
    }

    public BigDecimal getOughtDutyDays() {
        return this.oughtDutyDays;
    }

    public void setOughtDutyDays(BigDecimal oughtDutyDays) {
        this.oughtDutyDays = oughtDutyDays;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.support.AttendDailyMemory JD-Core Version: 0.5.4
 */