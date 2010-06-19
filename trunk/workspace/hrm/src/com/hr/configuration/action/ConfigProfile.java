package com.hr.configuration.action;

import com.hr.base.BaseAction;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.SysConfigManager;
import java.util.Map;

public class ConfigProfile extends BaseAction {
    private static final long serialVersionUID = 1L;
    private String sys_contract_expire_remind;
    private String sys_birthday_remind;
    private String sys_trial_expire_remind;
    private String sys_salary_joinyear_calc;
    private String sys_profile_save_history;
    private String sys_position_max_exceed;
    SysConfigManager dbConfigManager;

    public ConfigProfile() {
        this.dbConfigManager = DatabaseSysConfigManager.getInstance();
    }

    public String execute() throws Exception {
        if (!hasAuth(961)) {
            addErrorInfo("对不起，您无权进入该页面");
            return "noauth";
        }
        Map dbMap = this.dbConfigManager.getProperties();
        this.sys_contract_expire_remind = ((String) dbMap.get("sys.contract.expire.remind"));
        this.sys_contract_expire_remind = SystemConfigInit
                .getValue(this.sys_contract_expire_remind, "45");

        this.sys_birthday_remind = ((String) dbMap.get("sys.birthday.remind"));
        this.sys_birthday_remind = SystemConfigInit.getValue(this.sys_birthday_remind, "30");

        this.sys_trial_expire_remind = ((String) dbMap.get("sys.trial.expire.remind"));
        this.sys_trial_expire_remind = SystemConfigInit
                .getValue(this.sys_trial_expire_remind, "30");

        this.sys_salary_joinyear_calc = ((String) dbMap.get("sys.salary.joinyear.calc"));
        this.sys_salary_joinyear_calc = SystemConfigInit.getValue(this.sys_salary_joinyear_calc,
                                                                  "15");

        this.sys_profile_save_history = ((String) dbMap.get("sys.profile.save.history"));
        this.sys_profile_save_history = SystemConfigInit.getValue(this.sys_profile_save_history,
                                                                  "0");

        this.sys_position_max_exceed = ((String) dbMap.get("sys.position.max.exceed"));
        this.sys_position_max_exceed = SystemConfigInit.getValue(this.sys_position_max_exceed, "0");

        return "success";
    }

    public String executeUpdate() throws Exception {
        if (!hasAuth(961)) {
            addErrorInfo("对不起，您无权进入该页面");
            return "noauth";
        }
        try {
            Map dbMap = this.dbConfigManager.getProperties();
            if (!((String) dbMap.get("sys.contract.expire.remind"))
                    .equals(this.sys_contract_expire_remind)) {
                this.dbConfigManager.setProperty("sys.contract.expire.remind",
                                                 this.sys_contract_expire_remind);
            }

            if (!((String) dbMap.get("sys.birthday.remind")).equals(this.sys_birthday_remind)) {
                this.dbConfigManager.setProperty("sys.birthday.remind", this.sys_birthday_remind);
            }

            if (!((String) dbMap.get("sys.trial.expire.remind"))
                    .equals(this.sys_trial_expire_remind)) {
                this.dbConfigManager.setProperty("sys.trial.expire.remind",
                                                 this.sys_trial_expire_remind);
            }

            if (!((String) dbMap.get("sys.salary.joinyear.calc"))
                    .equals(this.sys_salary_joinyear_calc)) {
                this.dbConfigManager.setProperty("sys.salary.joinyear.calc",
                                                 this.sys_salary_joinyear_calc);
            }

            if (!this.sys_profile_save_history.equals(dbMap.get("sys.profile.save.history"))) {
                this.dbConfigManager.setProperty("sys.profile.save.history",
                                                 this.sys_profile_save_history);
            }

            if (!this.sys_position_max_exceed.equals(dbMap.get("sys.position.max.exceed")))
                this.dbConfigManager.setProperty("sys.position.max.exceed",
                                                 this.sys_position_max_exceed);
        } catch (Exception e) {
            e.printStackTrace();
            return "input";
        }

        return "success";
    }

    public String getSys_contract_expire_remind() {
        return this.sys_contract_expire_remind;
    }

    public void setSys_contract_expire_remind(String sys_contract_expire_remind) {
        this.sys_contract_expire_remind = sys_contract_expire_remind;
    }

    public String getSys_birthday_remind() {
        return this.sys_birthday_remind;
    }

    public void setSys_birthday_remind(String sys_birthday_remind) {
        this.sys_birthday_remind = sys_birthday_remind;
    }

    public String getSys_trial_expire_remind() {
        return this.sys_trial_expire_remind;
    }

    public void setSys_trial_expire_remind(String sys_trial_expire_remind) {
        this.sys_trial_expire_remind = sys_trial_expire_remind;
    }

    public String getSys_salary_joinyear_calc() {
        return this.sys_salary_joinyear_calc;
    }

    public void setSys_salary_joinyear_calc(String sys_salary_joinyear_calc) {
        this.sys_salary_joinyear_calc = sys_salary_joinyear_calc;
    }

    public String getSys_profile_save_history() {
        return this.sys_profile_save_history;
    }

    public void setSys_profile_save_history(String sys_profile_save_history) {
        this.sys_profile_save_history = sys_profile_save_history;
    }

    public String getSys_position_max_exceed() {
        return this.sys_position_max_exceed;
    }

    public void setSys_position_max_exceed(String sys_position_max_exceed) {
        this.sys_position_max_exceed = sys_position_max_exceed;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.action.ConfigProfile JD-Core Version: 0.5.4
 */