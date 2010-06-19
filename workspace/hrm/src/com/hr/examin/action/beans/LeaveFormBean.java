package com.hr.examin.action.beans;

import com.hr.configuration.domain.Syslog;
import com.hr.examin.domain.Leavetype;
import com.hr.profile.domain.Employee;
import java.util.Date;
import java.util.List;

public class LeaveFormBean {
    private Date beginDate;
    private Integer beginHour;
    private Integer beginMinute;
    private Integer beginApm;
    private Date endDate;
    private Integer endHour;
    private Integer endMinute;
    private Integer endApm;
    private Employee lrEmpNo;
    private Leavetype lrLtNo;
    private String lrReason;
    private String lrAppComment;
    private boolean applyLRByDay;
    private List<Leavetype> allLtType;
    private String startTime;
    private String endTime;
    private String totalTime;
    private String lrStatus;
    private String useableTime;
    private String usedTime;
    private String remainTime;
    private List<Syslog> logList;
    private String lrLtNoError;
    private String lrReasonError;
    private String lrDateError;
    private String lrStatusError;
    private String Errors;
    private boolean hasError = false;

    public String getErrors() {
        return this.Errors;
    }

    public void setErrors(String errors) {
        this.hasError = true;
        this.Errors = errors;
    }

    public String getLrDateError() {
        return this.lrDateError;
    }

    public void setLrDateError(String lrDateError) {
        this.hasError = true;
        this.lrDateError = lrDateError;
    }

    public String getLrLtNoError() {
        return this.lrLtNoError;
    }

    public void setLrLtNoError(String lrLtNoError) {
        this.hasError = true;
        this.lrLtNoError = lrLtNoError;
    }

    public String getLrReasonError() {
        return this.lrReasonError;
    }

    public void setLrReasonError(String lrReasonError) {
        this.hasError = true;
        this.lrReasonError = lrReasonError;
    }

    public String getLrStatusError() {
        return this.lrStatusError;
    }

    public void setLrStatusError(String lrStatusError) {
        this.hasError = true;
        this.lrStatusError = lrStatusError;
    }

    public boolean isHasError() {
        return this.hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public Integer getBeginApm() {
        return this.beginApm;
    }

    public void setBeginApm(Integer beginApm) {
        this.beginApm = beginApm;
    }

    public Date getBeginDate() {
        return this.beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Integer getBeginHour() {
        return this.beginHour;
    }

    public void setBeginHour(Integer beginHour) {
        this.beginHour = beginHour;
    }

    public Integer getBeginMinute() {
        return this.beginMinute;
    }

    public void setBeginMinute(Integer beginMinute) {
        this.beginMinute = beginMinute;
    }

    public Integer getEndApm() {
        return this.endApm;
    }

    public void setEndApm(Integer endApm) {
        this.endApm = endApm;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getEndHour() {
        return this.endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public Integer getEndMinute() {
        return this.endMinute;
    }

    public void setEndMinute(Integer endMinute) {
        this.endMinute = endMinute;
    }

    public String getLrReason() {
        return this.lrReason;
    }

    public void setLrReason(String lrReason) {
        this.lrReason = lrReason;
    }

    public String getLrAppComment() {
        return this.lrAppComment;
    }

    public void setLrAppComment(String lrAppComment) {
        this.lrAppComment = lrAppComment;
    }

    public Employee getLrEmpNo() {
        return this.lrEmpNo;
    }

    public void setLrEmpNo(Employee lrEmpNo) {
        this.lrEmpNo = lrEmpNo;
    }

    public Leavetype getLrLtNo() {
        return this.lrLtNo;
    }

    public void setLrLtNo(Leavetype lrLtNo) {
        this.lrLtNo = lrLtNo;
    }

    public boolean isApplyLRByDay() {
        return this.applyLRByDay;
    }

    public void setApplyLRByDay(boolean applyLRByDay) {
        this.applyLRByDay = applyLRByDay;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<Syslog> getLogList() {
        return this.logList;
    }

    public void setLogList(List<Syslog> logList) {
        this.logList = logList;
    }

    public String getLrStatus() {
        return this.lrStatus;
    }

    public void setLrStatus(String lrStatus) {
        this.lrStatus = lrStatus;
    }

    public String getRemainTime() {
        return this.remainTime;
    }

    public void setRemainTime(String remainTime) {
        this.remainTime = remainTime;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTotalTime() {
        return this.totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getUseableTime() {
        return this.useableTime;
    }

    public void setUseableTime(String useableTime) {
        this.useableTime = useableTime;
    }

    public String getUsedTime() {
        return this.usedTime;
    }

    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
    }

    public List<Leavetype> getAllLtType() {
        return this.allLtType;
    }

    public void setAllLtType(List<Leavetype> allLtType) {
        this.allLtType = allLtType;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.action.beans.LeaveFormBean JD-Core Version: 0.5.4
 */