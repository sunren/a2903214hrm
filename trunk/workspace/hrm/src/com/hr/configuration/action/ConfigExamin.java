package com.hr.configuration.action;

import com.hr.base.BaseAction;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.SysConfigManager;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class ConfigExamin extends BaseAction {
    private static final long serialVersionUID = 1L;
    public static final String SUCC = "SUCC";
    public static final String FAIL = "FAIL";
    private String sys_examin_create_level;
    private String sys_examin_update_level;
    private String sys_examin_lr_confirm;
    private String sys_examin_ot_confirm;
    private Integer beginMonth;
    private String beginDay;
    private String leaveMode;
    private String hoursPerDay;
    private String sys_examin_shift_enable;
    private String sys_examin_shiftimport_override;
    private String sys_examin_leave_type;
    private SysConfigManager dbConfigManager;

    public ConfigExamin() {
        this.dbConfigManager = DatabaseSysConfigManager.getInstance();
    }

    public String showConfigExamin() throws Exception {
        Map dbMap = this.dbConfigManager.getProperties();

        this.sys_examin_create_level = StringUtils.defaultIfEmpty((String) dbMap
                .get("sys.examin.create.level"), "1");
        this.sys_examin_update_level = StringUtils.defaultIfEmpty((String) dbMap
                .get("sys.examin.update.level"), "1");
        this.sys_examin_lr_confirm = StringUtils.defaultIfEmpty((String) dbMap
                .get("sys.examin.lr.confirm"), "0");
        this.sys_examin_ot_confirm = StringUtils.defaultIfEmpty((String) dbMap
                .get("sys.examin.ot.confirm"), "0");
        this.sys_examin_shift_enable = StringUtils.defaultIfEmpty((String) dbMap
                .get("sys.examin.shift.enable"), "0");
        this.sys_examin_shiftimport_override = StringUtils.defaultIfEmpty((String) dbMap
                .get("sys.examin.shiftimport.override"), "0");
        this.sys_examin_leave_type = StringUtils.defaultIfEmpty((String) dbMap
                .get("sys.examin.leave.type"), "0");

        String sys_examin_month_sum = StringUtils.defaultIfEmpty((String) dbMap
                .get("sys.examin.month.sum"), "1-1");
        if (!sys_examin_month_sum.matches("\\d+-\\d+")) {
            sys_examin_month_sum = "1-1";
        }
        String[] strs = sys_examin_month_sum.split("-");
        this.beginMonth = Integer.valueOf(Integer.parseInt(strs[0]));
        this.beginDay = strs[1];

        String sys_examin_leave_mode = StringUtils.defaultIfEmpty((String) dbMap
                .get("sys.examin.leave.mode"), "day,8");
        if (!sys_examin_leave_mode.matches("\\w+,\\w+")) {
            this.leaveMode = "day";
            this.hoursPerDay = "8";
        } else {
            String[] str = sys_examin_leave_mode.split(",");
            this.leaveMode = str[0];
            this.hoursPerDay = str[1];
        }

        return "success";
    }

    public String executeExamin() throws Exception {
        Map dbMap = this.dbConfigManager.getProperties();

        if (!((String) dbMap.get("sys.examin.create.level")).equals(this.sys_examin_create_level)) {
            this.dbConfigManager.setProperty("sys.examin.create.level",
                                             this.sys_examin_create_level);
        }

        if (!((String) dbMap.get("sys.examin.update.level")).equals(this.sys_examin_update_level)) {
            this.dbConfigManager.setProperty("sys.examin.update.level",
                                             this.sys_examin_update_level);
        }

        if (!((String) dbMap.get("sys.examin.lr.confirm")).equals(this.sys_examin_lr_confirm)) {
            this.dbConfigManager.setProperty("sys.examin.lr.confirm", this.sys_examin_lr_confirm);
        }

        if (!((String) dbMap.get("sys.examin.ot.confirm")).equals(this.sys_examin_ot_confirm)) {
            this.dbConfigManager.setProperty("sys.examin.ot.confirm", this.sys_examin_ot_confirm);
        }

        String sys_examin_month_sum = Integer.toString(this.beginMonth.intValue()) + "-"
                + this.beginDay;
        if (!((String) dbMap.get("sys.examin.month.sum")).equals(sys_examin_month_sum)) {
            this.dbConfigManager.setProperty("sys.examin.month.sum", sys_examin_month_sum);
        }
        if ((this.leaveMode == null) || (this.leaveMode.equals(""))) {
            this.leaveMode = "day";
        }
        String temp = this.leaveMode + "," + this.hoursPerDay;
        if (!((String) dbMap.get("sys.examin.leave.mode")).equals(temp)) {
            this.dbConfigManager.setProperty("sys.examin.leave.mode", temp);
        }

        if (!((String) dbMap.get("sys.examin.shift.enable")).equals(this.sys_examin_shift_enable)) {
            this.dbConfigManager.setProperty("sys.examin.shift.enable",
                                             this.sys_examin_shift_enable);
        }

        if (!((String) dbMap.get("sys.examin.shiftimport.override"))
                .equals(this.sys_examin_shiftimport_override)) {
            this.dbConfigManager.setProperty("sys.examin.shiftimport.override",
                                             this.sys_examin_shiftimport_override);
        }
        if (!((String) dbMap.get("sys.examin.leave.type")).equals(this.sys_examin_leave_type)) {
            this.dbConfigManager.setProperty("sys.examin.leave.type", this.sys_examin_leave_type);
        }
        addSuccessInfo("考勤模块参数调整成功");
        return "success";
    }

    public String getSys_examin_create_level() {
        return this.sys_examin_create_level;
    }

    public void setSys_examin_create_level(String sys_examin_create_level) {
        this.sys_examin_create_level = sys_examin_create_level;
    }

    public String getSys_examin_update_level() {
        return this.sys_examin_update_level;
    }

    public void setSys_examin_update_level(String sys_examin_update_level) {
        this.sys_examin_update_level = sys_examin_update_level;
    }

    public String getSys_examin_lr_confirm() {
        return this.sys_examin_lr_confirm;
    }

    public void setSys_examin_lr_confirm(String sys_examin_lr_confirm) {
        this.sys_examin_lr_confirm = sys_examin_lr_confirm;
    }

    public String getSys_examin_ot_confirm() {
        return this.sys_examin_ot_confirm;
    }

    public void setSys_examin_ot_confirm(String sys_examin_ot_confirm) {
        this.sys_examin_ot_confirm = sys_examin_ot_confirm;
    }

    public Integer getBeginMonth() {
        return this.beginMonth;
    }

    public void setBeginMonth(Integer beginMonth) {
        this.beginMonth = beginMonth;
    }

    public String getBeginDay() {
        return this.beginDay;
    }

    public void setBeginDay(String beginDay) {
        this.beginDay = beginDay;
    }

    public String getSys_examin_shift_enable() {
        return this.sys_examin_shift_enable;
    }

    public void setSys_examin_shift_enable(String sys_examin_shift_enable) {
        this.sys_examin_shift_enable = sys_examin_shift_enable;
    }

    public String getLeaveMode() {
        return this.leaveMode;
    }

    public void setLeaveMode(String leaveMode) {
        this.leaveMode = leaveMode;
    }

    public String getHoursPerDay() {
        return this.hoursPerDay;
    }

    public void setHoursPerDay(String hoursPerDay) {
        this.hoursPerDay = hoursPerDay;
    }

    public String getSys_examin_shiftimport_override() {
        return this.sys_examin_shiftimport_override;
    }

    public void setSys_examin_shiftimport_override(String sys_examin_shiftimport_override) {
        this.sys_examin_shiftimport_override = sys_examin_shiftimport_override;
    }

    public String getSys_examin_leave_type() {
        return this.sys_examin_leave_type;
    }

    public void setSys_examin_leave_type(String sys_examin_leave_type) {
        this.sys_examin_leave_type = sys_examin_leave_type;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.action.ConfigExamin JD-Core Version: 0.5.4
 */