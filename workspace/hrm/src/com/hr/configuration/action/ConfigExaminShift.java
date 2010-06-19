package com.hr.configuration.action;

import com.hr.base.BaseAction;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.SysConfigManager;
import java.util.Map;

public class ConfigExaminShift extends BaseAction {
    private static final long serialVersionUID = 1L;
    private String sys_shift_late_minute;
    private String firstLateBegin;
    private String firstLateEnd;
    private String secondLateBegin;
    private String secondLateEnd;
    private String thirdLateBegin;
    private String thirdLateEnd;
    private String sys_shift_earlyleave_minute;
    private String firstEarlyLeaveBegin;
    private String firstEarlyLeaveEnd;
    private String secondEarlyLeaveBegin;
    private String secondEarlyLeaveEnd;
    private String thirdEarlyLeaveBegin;
    private String thirdEarlyLeaveEnd;
    private String sys_examin_absent_minute;
    private String sys_shift_start_minute;
    private String sys_shift_end_minute;
    private String sys_shift_cardrepeat_minute;
    private String sys_shift_card_calchour;
    private String sys_shift_asHour;
    private String sys_shift_asHalfHour;
    private String sys_shift_card_calcday;
    private String sys_absent_asDay;
    private String sys_absent_asHalfDay;
    private static SysConfigManager dbManager = DatabaseSysConfigManager.getInstance();

    public String showExaminShift() throws Exception {
        if (!hasAuth(961)) {
            addErrorInfo("对不起，您无权进入该页面＄1�7");
            return "noauth";
        }
        Map dbMap = dbManager.getProperties();

        this.sys_shift_late_minute = ((String) dbMap.get("sys.shift.late.minute"));
        this.sys_shift_late_minute = SystemConfigInit.getValue(this.sys_shift_late_minute, "");
        if (!this.sys_shift_late_minute.equals("")) {
            String[] str = splitString(this.sys_shift_late_minute, 6);
            this.firstLateBegin = str[0];
            this.firstLateEnd = str[1];
            this.secondLateBegin = str[2];
            this.secondLateEnd = str[3];
            this.thirdLateBegin = str[4];
            this.thirdLateEnd = str[5];
        }

        this.sys_shift_earlyleave_minute = ((String) dbMap.get("sys.shift.earlyleave.minute"));
        this.sys_shift_earlyleave_minute = SystemConfigInit
                .getValue(this.sys_shift_earlyleave_minute, "");
        if (!this.sys_shift_earlyleave_minute.equals("")) {
            String[] str = splitString(this.sys_shift_earlyleave_minute, 6);
            this.firstEarlyLeaveBegin = str[0];
            this.firstEarlyLeaveEnd = str[1];
            this.secondEarlyLeaveBegin = str[2];
            this.secondEarlyLeaveEnd = str[3];
            this.thirdEarlyLeaveBegin = str[4];
            this.thirdEarlyLeaveEnd = str[5];
        }

        this.sys_examin_absent_minute = ((String) dbMap.get("sys.examin.absent.minute"));
        this.sys_examin_absent_minute = SystemConfigInit.getValue(this.sys_examin_absent_minute,
                                                                  "120");

        this.sys_shift_start_minute = ((String) dbMap.get("sys.shift.start.minute"));
        this.sys_shift_start_minute = SystemConfigInit.getValue(this.sys_shift_start_minute, "");

        this.sys_shift_end_minute = ((String) dbMap.get("sys.shift.end.minute"));
        this.sys_shift_end_minute = SystemConfigInit.getValue(this.sys_shift_end_minute, "");

        this.sys_shift_cardrepeat_minute = ((String) dbMap.get("sys.shift.cardrepeat.minute"));
        this.sys_shift_cardrepeat_minute = SystemConfigInit
                .getValue(this.sys_shift_cardrepeat_minute, "");

        this.sys_shift_card_calchour = ((String) dbMap.get("sys.shift.card.calchour"));
        this.sys_shift_card_calchour = SystemConfigInit.getValue(this.sys_shift_card_calchour, "");
        if (!this.sys_shift_card_calchour.equals("")) {
            String[] temp = splitString(this.sys_shift_card_calchour, 2);
            this.sys_shift_asHour = temp[0];
            this.sys_shift_asHalfHour = temp[1];
        }

        this.sys_shift_card_calcday = ((String) dbMap.get("sys.shift.card.calcday"));
        this.sys_shift_card_calcday = SystemConfigInit.getValue(this.sys_shift_card_calcday, "");
        if (!this.sys_shift_card_calcday.equals("")) {
            String[] temp = splitString(this.sys_shift_card_calcday, 4);
            this.sys_absent_asDay = temp[0];
            this.sys_absent_asHalfDay = temp[1];
        }

        return "success";
    }

    public String updateExaminShift() throws Exception {
        if (!hasAuth(961)) {
            addErrorInfo("对不起，您无权进入该页面＄1�7");
            return "noauth";
        }
        Map dbMap = dbManager.getProperties();

        String[] temp_late_minute = { this.firstLateBegin, this.firstLateEnd, this.secondLateBegin,
                this.secondLateEnd, this.thirdLateBegin, this.thirdLateEnd };

        this.sys_shift_late_minute = combineString(temp_late_minute);
        if (!((String) dbMap.get("sys.shift.late.minute")).equals(this.sys_shift_late_minute)) {
            dbManager.setProperty("sys.shift.late.minute", this.sys_shift_late_minute);
        }

        String[] temp_earlyleave_minute = { this.firstEarlyLeaveBegin, this.firstEarlyLeaveEnd,
                this.secondEarlyLeaveBegin, this.secondEarlyLeaveEnd, this.thirdEarlyLeaveBegin,
                this.thirdEarlyLeaveEnd };

        this.sys_shift_earlyleave_minute = combineString(temp_earlyleave_minute);
        if (!((String) dbMap.get("sys.shift.earlyleave.minute"))
                .equals(this.sys_shift_earlyleave_minute)) {
            dbManager.setProperty("sys.shift.earlyleave.minute", this.sys_shift_earlyleave_minute);
        }

        if (!((String) dbMap.get("sys.examin.absent.minute")).equals(this.sys_examin_absent_minute)) {
            dbManager.setProperty("sys.examin.absent.minute", this.sys_examin_absent_minute);
        }

        if (!((String) dbMap.get("sys.shift.start.minute")).equals(this.sys_shift_start_minute)) {
            dbManager.setProperty("sys.shift.start.minute", this.sys_shift_start_minute);
        }

        if (!((String) dbMap.get("sys.shift.end.minute")).equals(this.sys_shift_end_minute)) {
            dbManager.setProperty("sys.shift.end.minute", this.sys_shift_end_minute);
        }

        if (!((String) dbMap.get("sys.shift.cardrepeat.minute"))
                .equals(this.sys_shift_cardrepeat_minute)) {
            dbManager.setProperty("sys.shift.cardrepeat.minute", this.sys_shift_cardrepeat_minute);
        }

        if ((this.sys_shift_asHour.equals("")) || (this.sys_shift_asHalfHour.equals("")))
            this.sys_shift_card_calchour = "";
        else {
            this.sys_shift_card_calchour = (this.sys_shift_asHour + "," + this.sys_shift_asHalfHour);
        }
        if (!((String) dbMap.get("sys.shift.card.calchour")).equals(this.sys_shift_card_calchour)) {
            dbManager.setProperty("sys.shift.card.calchour", this.sys_shift_card_calchour);
        }

        this.sys_shift_card_calcday = (this.sys_absent_asDay + "," + this.sys_absent_asHalfDay);
        if (!((String) dbMap.get("sys.shift.card.calcday")).equals(this.sys_shift_card_calcday)) {
            dbManager.setProperty("sys.shift.card.calcday", this.sys_shift_card_calcday);
        }

        return "success";
    }

    public String[] splitString(String str, int length) {
        String[] s = new String[length];
        for (int i = 0; i < s.length; ++i) {
            s[i] = "";
        }
        String[] temp1 = str.split(";");
        for (int i = 0; i < temp1.length; ++i) {
            String[] temp2 = temp1[i].split(",");
            s[(2 * i)] = temp2[0];
            if (temp2.length > 1) {
                s[(2 * i + 1)] = temp2[1];
            }
        }
        return s;
    }

    public String combineString(String[] str) {
        String temp = "";
        String blank = "";
        String semicoln = "";
        for (int i = 0; i < str.length; ++i) {
            blank = "";
            semicoln = "";
            if (i % 2 == 1) {
                if (!str[i].equals("")) {
                    blank = ",";
                }
                if (i != str.length - 1) {
                    semicoln = ";";
                }
            }
            temp = temp + blank + str[i] + semicoln;
        }
        return temp;
    }

    public String getSys_shift_late_minute() {
        return this.sys_shift_late_minute;
    }

    public void setSys_shift_late_minute(String sys_shift_late_minute) {
        this.sys_shift_late_minute = sys_shift_late_minute;
    }

    public String getSys_shift_earlyleave_minute() {
        return this.sys_shift_earlyleave_minute;
    }

    public void setSys_shift_earlyleave_minute(String sys_shift_earlyleave_minute) {
        this.sys_shift_earlyleave_minute = sys_shift_earlyleave_minute;
    }

    public String getSys_shift_start_minute() {
        return this.sys_shift_start_minute;
    }

    public void setSys_shift_start_minute(String sys_shift_start_minute) {
        this.sys_shift_start_minute = sys_shift_start_minute;
    }

    public String getSys_shift_end_minute() {
        return this.sys_shift_end_minute;
    }

    public void setSys_shift_end_minute(String sys_shift_end_minute) {
        this.sys_shift_end_minute = sys_shift_end_minute;
    }

    public String getSys_shift_cardrepeat_minute() {
        return this.sys_shift_cardrepeat_minute;
    }

    public void setSys_shift_cardrepeat_minute(String sys_shift_cardrepeat_minute) {
        this.sys_shift_cardrepeat_minute = sys_shift_cardrepeat_minute;
    }

    public String getSys_shift_card_calchour() {
        return this.sys_shift_card_calchour;
    }

    public void setSys_shift_card_calchour(String sys_shift_card_calchour) {
        this.sys_shift_card_calchour = sys_shift_card_calchour;
    }

    public String getSys_shift_card_calcday() {
        return this.sys_shift_card_calcday;
    }

    public void setSys_shift_card_calcday(String sys_shift_card_calcday) {
        this.sys_shift_card_calcday = sys_shift_card_calcday;
    }

    public String getFirstLateBegin() {
        return this.firstLateBegin;
    }

    public void setFirstLateBegin(String firstLateBegin) {
        this.firstLateBegin = firstLateBegin;
    }

    public String getFirstLateEnd() {
        return this.firstLateEnd;
    }

    public void setFirstLateEnd(String firstLateEnd) {
        this.firstLateEnd = firstLateEnd;
    }

    public String getSecondLateBegin() {
        return this.secondLateBegin;
    }

    public void setSecondLateBegin(String secondLateBegin) {
        this.secondLateBegin = secondLateBegin;
    }

    public String getSecondLateEnd() {
        return this.secondLateEnd;
    }

    public void setSecondLateEnd(String secondLateEnd) {
        this.secondLateEnd = secondLateEnd;
    }

    public String getThirdLateBegin() {
        return this.thirdLateBegin;
    }

    public void setThirdLateBegin(String thirdLateBegin) {
        this.thirdLateBegin = thirdLateBegin;
    }

    public String getThirdLateEnd() {
        return this.thirdLateEnd;
    }

    public void setThirdLateEnd(String thirdLateEnd) {
        this.thirdLateEnd = thirdLateEnd;
    }

    public String getFirstEarlyLeaveBegin() {
        return this.firstEarlyLeaveBegin;
    }

    public void setFirstEarlyLeaveBegin(String firstEarlyLeaveBegin) {
        this.firstEarlyLeaveBegin = firstEarlyLeaveBegin;
    }

    public String getFirstEarlyLeaveEnd() {
        return this.firstEarlyLeaveEnd;
    }

    public void setFirstEarlyLeaveEnd(String firstEarlyLeaveEnd) {
        this.firstEarlyLeaveEnd = firstEarlyLeaveEnd;
    }

    public String getSecondEarlyLeaveBegin() {
        return this.secondEarlyLeaveBegin;
    }

    public void setSecondEarlyLeaveBegin(String secondEarlyLeaveBegin) {
        this.secondEarlyLeaveBegin = secondEarlyLeaveBegin;
    }

    public String getSecondEarlyLeaveEnd() {
        return this.secondEarlyLeaveEnd;
    }

    public void setSecondEarlyLeaveEnd(String secondEarlyLeaveEnd) {
        this.secondEarlyLeaveEnd = secondEarlyLeaveEnd;
    }

    public String getThirdEarlyLeaveBegin() {
        return this.thirdEarlyLeaveBegin;
    }

    public void setThirdEarlyLeaveBegin(String thirdEarlyLeaveBegin) {
        this.thirdEarlyLeaveBegin = thirdEarlyLeaveBegin;
    }

    public String getThirdEarlyLeaveEnd() {
        return this.thirdEarlyLeaveEnd;
    }

    public void setThirdEarlyLeaveEnd(String thirdEarlyLeaveEnd) {
        this.thirdEarlyLeaveEnd = thirdEarlyLeaveEnd;
    }

    public String getSys_shift_asHour() {
        return this.sys_shift_asHour;
    }

    public void setSys_shift_asHour(String sys_shift_asHour) {
        this.sys_shift_asHour = sys_shift_asHour;
    }

    public String getSys_shift_asHalfHour() {
        return this.sys_shift_asHalfHour;
    }

    public void setSys_shift_asHalfHour(String sys_shift_asHalfHour) {
        this.sys_shift_asHalfHour = sys_shift_asHalfHour;
    }

    public String getSys_absent_asDay() {
        return this.sys_absent_asDay;
    }

    public void setSys_absent_asDay(String sys_absent_asDay) {
        this.sys_absent_asDay = sys_absent_asDay;
    }

    public String getSys_absent_asHalfDay() {
        return this.sys_absent_asHalfDay;
    }

    public void setSys_absent_asHalfDay(String sys_absent_asHalfDay) {
        this.sys_absent_asHalfDay = sys_absent_asHalfDay;
    }

    public String getSys_examin_absent_minute() {
        return this.sys_examin_absent_minute;
    }

    public void setSys_examin_absent_minute(String sys_examin_absent_minute) {
        this.sys_examin_absent_minute = sys_examin_absent_minute;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.action.ConfigExaminShift JD-Core Version: 0.5.4
 */