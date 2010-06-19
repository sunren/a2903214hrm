package com.hr.examin.action.beans;

import java.math.BigDecimal;
import java.util.List;

public class BatUpdAttdDailyBean {
    private List<String> batchIds;
    private Integer batchStatus;
    private BigDecimal batchLate;
    private BigDecimal batchEarlyLeave;
    private BigDecimal batchWorkHour;
    private BigDecimal batchAbsent;
    private BigDecimal batchOtNormalHours;
    private BigDecimal batchOtWeekendHours;
    private BigDecimal batchOtHolidayHours;
    private BigDecimal batchLeaveAnnualHours;
    private BigDecimal batchLeaveCasualHours;
    private BigDecimal batchLeaveSickHours;
    private BigDecimal batchLeaveWeddingHours;
    private BigDecimal batchLeaveMaternityHours;
    private BigDecimal batchLeaveTravelHours;
    private BigDecimal batchLeaveOtherHours;
    private String batchComments;
    private String batchShiftId;
    private String needCalAttd;

    public List<String> getBatchIds() {
        return this.batchIds;
    }

    public void setBatchIds(List<String> batchIds) {
        this.batchIds = batchIds;
    }

    public Integer getBatchStatus() {
        return this.batchStatus;
    }

    public void setBatchStatus(Integer batchStatus) {
        this.batchStatus = batchStatus;
    }

    public BigDecimal getBatchLate() {
        return this.batchLate;
    }

    public void setBatchLate(BigDecimal batchLate) {
        this.batchLate = batchLate;
    }

    public BigDecimal getBatchEarlyLeave() {
        return this.batchEarlyLeave;
    }

    public void setBatchEarlyLeave(BigDecimal batchEarlyLeave) {
        this.batchEarlyLeave = batchEarlyLeave;
    }

    public BigDecimal getBatchWorkHour() {
        return this.batchWorkHour;
    }

    public void setBatchWorkHour(BigDecimal batchWorkHour) {
        this.batchWorkHour = batchWorkHour;
    }

    public BigDecimal getBatchAbsent() {
        return this.batchAbsent;
    }

    public void setBatchAbsent(BigDecimal batchAbsent) {
        this.batchAbsent = batchAbsent;
    }

    public String getBatchComments() {
        return this.batchComments;
    }

    public void setBatchComments(String batchComments) {
        this.batchComments = batchComments;
    }

    public String getBatchShiftId() {
        return this.batchShiftId;
    }

    public void setBatchShiftId(String batchShiftId) {
        this.batchShiftId = batchShiftId;
    }

    public String getNeedCalAttd() {
        return this.needCalAttd;
    }

    public void setNeedCalAttd(String needCalAttd) {
        this.needCalAttd = needCalAttd;
    }

    public BigDecimal getBatchLeaveAnnualHours() {
        return this.batchLeaveAnnualHours;
    }

    public void setBatchLeaveAnnualHours(BigDecimal batchLeaveAnnualHours) {
        this.batchLeaveAnnualHours = batchLeaveAnnualHours;
    }

    public BigDecimal getBatchLeaveCasualHours() {
        return this.batchLeaveCasualHours;
    }

    public void setBatchLeaveCasualHours(BigDecimal batchLeaveCasualHours) {
        this.batchLeaveCasualHours = batchLeaveCasualHours;
    }

    public BigDecimal getBatchLeaveMaternityHours() {
        return this.batchLeaveMaternityHours;
    }

    public void setBatchLeaveMaternityHours(BigDecimal batchLeaveMaternityHours) {
        this.batchLeaveMaternityHours = batchLeaveMaternityHours;
    }

    public BigDecimal getBatchLeaveOtherHours() {
        return this.batchLeaveOtherHours;
    }

    public void setBatchLeaveOtherHours(BigDecimal batchLeaveOtherHours) {
        this.batchLeaveOtherHours = batchLeaveOtherHours;
    }

    public BigDecimal getBatchLeaveSickHours() {
        return this.batchLeaveSickHours;
    }

    public void setBatchLeaveSickHours(BigDecimal batchLeaveSickHours) {
        this.batchLeaveSickHours = batchLeaveSickHours;
    }

    public BigDecimal getBatchLeaveTravelHours() {
        return this.batchLeaveTravelHours;
    }

    public void setBatchLeaveTravelHours(BigDecimal batchLeaveTravelHours) {
        this.batchLeaveTravelHours = batchLeaveTravelHours;
    }

    public BigDecimal getBatchLeaveWeddingHours() {
        return this.batchLeaveWeddingHours;
    }

    public void setBatchLeaveWeddingHours(BigDecimal batchLeaveWeddingHours) {
        this.batchLeaveWeddingHours = batchLeaveWeddingHours;
    }

    public BigDecimal getBatchOtHolidayHours() {
        return this.batchOtHolidayHours;
    }

    public void setBatchOtHolidayHours(BigDecimal batchOtHolidayHours) {
        this.batchOtHolidayHours = batchOtHolidayHours;
    }

    public BigDecimal getBatchOtNormalHours() {
        return this.batchOtNormalHours;
    }

    public void setBatchOtNormalHours(BigDecimal batchOtNormalHours) {
        this.batchOtNormalHours = batchOtNormalHours;
    }

    public BigDecimal getBatchOtWeekendHours() {
        return this.batchOtWeekendHours;
    }

    public void setBatchOtWeekendHours(BigDecimal batchOtWeekendHours) {
        this.batchOtWeekendHours = batchOtWeekendHours;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.action.beans.BatUpdAttdDailyBean JD-Core Version: 0.5.4
 */