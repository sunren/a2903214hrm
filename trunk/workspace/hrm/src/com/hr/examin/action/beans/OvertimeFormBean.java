package com.hr.examin.action.beans;

import com.hr.configuration.domain.Syslog;
import com.hr.examin.domain.Overtimetype;
import com.hr.profile.domain.Employee;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OvertimeFormBean {
    private Date startDate;
    private Date endDate;
    private Integer startTime;
    private Integer endTime;
    private Integer startTimeMinute;
    private Integer endTimeMinute;
    private Employee orEmpNo;
    private Overtimetype orOtNo;
    private String orReason;
    private String orAppComment;
    private String otStatus;
    private List<Overtimetype> allOtType;
    private Boolean isTiaoxiu;
    private BigDecimal orTiaoxiuHours;
    private Date orTiaoxiuExpire;
    private BigDecimal usedTime;
    private List<Syslog> logList;

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getStartTimeMinute() {
        return this.startTimeMinute;
    }

    public void setStartTimeMinute(Integer startTimeMinute) {
        this.startTimeMinute = startTimeMinute;
    }

    public Integer getEndTimeMinute() {
        return this.endTimeMinute;
    }

    public void setEndTimeMinute(Integer endTimeMinute) {
        this.endTimeMinute = endTimeMinute;
    }

    public List<Syslog> getLogList() {
        return this.logList;
    }

    public void setLogList(List<Syslog> logList) {
        this.logList = logList;
    }

    public String getOrAppComment() {
        return this.orAppComment;
    }

    public void setOrAppComment(String orAppComment) {
        this.orAppComment = orAppComment;
    }

    public Employee getOrEmpNo() {
        return this.orEmpNo;
    }

    public void setOrEmpNo(Employee orEmpNo) {
        this.orEmpNo = orEmpNo;
    }

    public Overtimetype getOrOtNo() {
        return this.orOtNo;
    }

    public void setOrOtNo(Overtimetype orOtNo) {
        this.orOtNo = orOtNo;
    }

    public BigDecimal getUsedTime() {
        return this.usedTime;
    }

    public void setUsedTime(BigDecimal usedTime) {
        this.usedTime = usedTime;
    }

    public String getOrReason() {
        return this.orReason;
    }

    public void setOrReason(String orReason) {
        this.orReason = orReason;
    }

    public Date getOrTiaoxiuExpire() {
        return this.orTiaoxiuExpire;
    }

    public void setOrTiaoxiuExpire(Date orTiaoxiuExpire) {
        this.orTiaoxiuExpire = orTiaoxiuExpire;
    }

    public BigDecimal getOrTiaoxiuHours() {
        return this.orTiaoxiuHours;
    }

    public void setOrTiaoxiuHours(BigDecimal orTiaoxiuHours) {
        this.orTiaoxiuHours = orTiaoxiuHours;
    }

    public String getOtStatus() {
        return this.otStatus;
    }

    public void setOtStatus(String otStatus) {
        this.otStatus = otStatus;
    }

    public List<Overtimetype> getAllOtType() {
        return this.allOtType;
    }

    public void setAllOtType(List<Overtimetype> allOtType) {
        this.allOtType = allOtType;
    }

    public Boolean getIsTiaoxiu() {
        return this.isTiaoxiu;
    }

    public void setIsTiaoxiu(Boolean isTiaoxiu) {
        this.isTiaoxiu = isTiaoxiu;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.action.beans.OvertimeFormBean JD-Core Version: 0.5.4
 */