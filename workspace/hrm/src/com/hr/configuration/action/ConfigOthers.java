package com.hr.configuration.action;

import com.hr.base.BaseAction;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.PropertiesFileConfigManager;
import com.hr.util.SpringBeanObserver;
import com.hr.util.SysConfigManager;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class ConfigOthers extends BaseAction {
    private static final long serialVersionUID = 1L;
    private String sys_backup_copies;
    private String sys_backup_frequency;
    SysConfigManager dbConfigManager;
    private SysConfigManager fileConfigManager;

    public ConfigOthers() {
        this.dbConfigManager = DatabaseSysConfigManager.getInstance();
        this.fileConfigManager = PropertiesFileConfigManager.getInstance();
    }

    public String showConfigOthers() throws Exception {
        if (!hasAuth(961)) {
            addErrorInfo("对不起，您无权进入该页面＄1�7");
            return "noauth";
        }
        Map dbMap = this.dbConfigManager.getProperties();
        this.sys_backup_copies = ((String) dbMap.get("sys.backup.copies"));
        this.sys_backup_copies = StringUtils.defaultIfEmpty(this.sys_backup_copies, "30");
        this.sys_backup_frequency = ((String) dbMap.get("sys.backup.frequency"));
        this.sys_backup_frequency = StringUtils.defaultIfEmpty(this.sys_backup_frequency, "3");
        return "success";
    }

    public String executeOthers() throws Exception {
        if (!hasAuth(961)) {
            addErrorInfo("对不起，您无权进入该页面!");
            return "noauth";
        }
        try {
            Map dbMap = this.dbConfigManager.getProperties();
            if (!((String) dbMap.get("sys.backup.copies")).equals(this.sys_backup_copies)) {
                this.dbConfigManager.setProperty("sys.backup.copies", this.sys_backup_copies);
            }

            if (!((String) dbMap.get("sys.backup.frequency")).equals(this.sys_backup_frequency)) {
                this.dbConfigManager.setProperty("sys.backup.frequency", this.sys_backup_frequency);
            }

            String quartz_DbBackup = "";
            if ("0".equals(this.sys_backup_frequency))
                quartz_DbBackup = "0 10 2 1 1 ?";
            else {
                quartz_DbBackup = "0 10 2 1/" + this.sys_backup_frequency + " * ?";
            }

            this.fileConfigManager.setProperty("quartz.dbBackup", quartz_DbBackup);
            this.fileConfigManager.saveChange();

            SpringBeanObserver.notifySpringBeanFactoryWithQuartzChange();
        } catch (Exception e) {
            e.printStackTrace();
            return "input";
        }
        return "success";
    }

    public void setSys_backup_frequency(String sys_backup_frequency) {
        this.sys_backup_frequency = sys_backup_frequency;
    }

    public String getSys_backup_frequency() {
        return this.sys_backup_frequency;
    }

    public void setSys_backup_copies(String sys_backup_copies) {
        this.sys_backup_copies = sys_backup_copies;
    }

    public String getSys_backup_copies() {
        return this.sys_backup_copies;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.action.ConfigOthers JD-Core Version: 0.5.4
 */