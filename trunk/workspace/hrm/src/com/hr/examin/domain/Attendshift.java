package com.hr.examin.domain;

import com.hr.examin.domain.base.BaseAttendshift;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Attendshift extends BaseAttendshift {
    private static final long serialVersionUID = 1L;
    private String flag;
    private String onDutyTime;
    private String offDutyTime;
    private List<Date[]> newAttdSessionList;
    private String newAttdSession;
    private String locIdString;
    private String deptIdString;

    public Attendshift() {
    }

    public Attendshift(String id) {
        super(id);
    }

    public Attendshift(String id, String attsShortName) {
        setId(id);
        setAttsShortName(attsShortName);
    }

    public Attendshift(String id, String attsName, Integer attsNightShift, String attsColor,
            Integer attsSortId) {
        super(id, attsName, attsNightShift, attsColor, attsSortId);
    }

    public String getOffDutyTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String[] cut = getAttsSession().split(",");

        String offDutyTime = cut[(cut.length - 1)].split("-")[1];
        Date off = null;
        try {
            off = (Date) format.parseObject(format.format(date).substring(0, 10) + " "
                    + offDutyTime);
            offDutyTime = format.format(off).substring(11);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return offDutyTime;
    }

    public void setOffDutyTime(String offDutyTime) {
        this.offDutyTime = offDutyTime;
    }

    public String getOnDutyTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String[] cut = getAttsSession().split(",");

        String onDutyTime = cut[0].split("-")[0];
        Date on = null;
        try {
            on = (Date) format.parseObject(format.format(date).substring(0, 10) + " " + onDutyTime);
            onDutyTime = format.format(on).substring(11);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return onDutyTime;
    }

    public void setOnDutyTime(String onDutyTime) {
        this.onDutyTime = onDutyTime;
    }

    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getNewAttdSession() {
        return this.newAttdSession;
    }

    public void setNewAttdSession(String newAttdSession) {
        this.newAttdSession = newAttdSession;
    }

    public List<Date[]> getNewAttdSessionList() {
        return this.newAttdSessionList;
    }

    public void setNewAttdSessionList(List<Date[]> newAttdSessionList) {
        this.newAttdSessionList = newAttdSessionList;
    }

    public String getDeptIdString() {
        return this.deptIdString;
    }

    public void setDeptIdString(String deptIdString) {
        this.deptIdString = deptIdString;
    }

    public String getLocIdString() {
        return this.locIdString;
    }

    public void setLocIdString(String locIdString) {
        this.locIdString = locIdString;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.domain.Attendshift JD-Core Version: 0.5.4
 */