package com.hr.examin.shift;

import com.hr.examin.domain.Attdoriginaldata;
import com.hr.examin.domain.Attendshift;
import com.hr.examin.domain.Empshift;
import com.hr.profile.domain.Employee;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExaminDataFilter {
    private Date examinDate;
    private List leaveRequestList;
    private List overtimeRequestList;
    private List empShiftList;
    private Map<String, List<Attdoriginaldata>> origEmpDateMap;
    private Attendshift shift;
    private Employee currentEmp;
    private Map<String, String> databaseConfigProperties;
    private Attendshift defaultShift;
    private List leaveCalendarList;

    public List filterLeaveRequest() {
        return null;
    }

    public List filterOvertimeRequest() {
        return null;
    }

    public List filterOriginalData() {
        return null;
    }

    public Attendshift filterShift() {
        int size = this.empShiftList.size();
        for (int i = 0; i < size; ++i) {
            Empshift empShift = (Empshift) this.empShiftList.get(i);
            if ((!empShift.getEmpshiftEmpNo().getId().equals(this.currentEmp.getId()))
                    || (!getFormatedDateString(empShift.getEmpshiftDate())
                            .equals(getFormatedDateString(this.examinDate))))
                continue;
            setShift(empShift.getEmpshiftShiftNo());
            return empShift.getEmpshiftShiftNo();
        }

        return null;
    }

    public Attendshift filterDefaultShift() {
        return this.defaultShift;
    }

    public Map<String, String> filterDatabaseConfigProperties() {
        return getDatabaseConfigProperties();
    }

    private String getFormatedDateString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public Date getExaminDate() {
        return this.examinDate;
    }

    public void setExaminDate(Date examinDate) {
        this.examinDate = examinDate;
    }

    public List getLeaveRequestList() {
        return this.leaveRequestList;
    }

    public void setLeaveRequestList(List leaveRequestList) {
        this.leaveRequestList = leaveRequestList;
    }

    public List getOvertimeRequestList() {
        return this.overtimeRequestList;
    }

    public void setOvertimeRequestList(List overtimeRequestList) {
        this.overtimeRequestList = overtimeRequestList;
    }

    public Attendshift getShift() {
        return this.shift;
    }

    public void setShift(Attendshift shift) {
        this.shift = shift;
    }

    public void setEmpShiftList(List empShiftList) {
        this.empShiftList = empShiftList;
    }

    public List getEmpShiftList() {
        return this.empShiftList;
    }

    public Map<String, String> getDatabaseConfigProperties() {
        return this.databaseConfigProperties;
    }

    public void setDatabaseConfigProperties(Map<String, String> databaseConfigProperties) {
        this.databaseConfigProperties = databaseConfigProperties;
    }

    public Employee getCurrentEmp() {
        return this.currentEmp;
    }

    public void setCurrentEmp(Employee currentEmp) {
        this.currentEmp = currentEmp;
    }

    public Attendshift getDefaultShift() {
        return this.defaultShift;
    }

    public void setDefaultShift(Attendshift defaultShift) {
        this.defaultShift = defaultShift;
    }

    public Map<String, List<Attdoriginaldata>> getOrigEmpDateMap() {
        return this.origEmpDateMap;
    }

    public void setOrigEmpDateMap(Map<String, List<Attdoriginaldata>> origEmpDateMap) {
        this.origEmpDateMap = origEmpDateMap;
    }

    public List getLeaveCalendarList() {
        return this.leaveCalendarList;
    }

    public void setLeaveCalendarList(List leaveCalendarList) {
        this.leaveCalendarList = leaveCalendarList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.shift.ExaminDataFilter JD-Core Version: 0.5.4
 */