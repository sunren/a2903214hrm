package com.hr.configuration.action;

import com.hr.base.BaseAction;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.PropertiesFileConfigManager;
import com.hr.util.SysConfigManager;
import java.io.PrintStream;
import java.util.Map;
import java.util.StringTokenizer;

public class ConfigSalary extends BaseAction {
    private static final long serialVersionUID = 1L;
    private String sys_salary_tax_min;
    private String sys_salary_tax_range;
    private String sys_salary_tax_rate;
    private String sys_salary_config_update;
    private String salaryAdjDate;
    private String salaryAdjHour;
    SysConfigManager dbConfigManager;
    private SysConfigManager fileConfigManager;

    public ConfigSalary() {
        this.dbConfigManager = DatabaseSysConfigManager.getInstance();

        this.fileConfigManager = PropertiesFileConfigManager.getInstance();
    }

    public String showConfigSalary() throws Exception {
        if (!hasAuth(961)) {
            addErrorInfo("对不起，您无权进入该页面");
            return "noauth";
        }
        Map dbMap = this.dbConfigManager.getProperties();
        this.sys_salary_tax_min = ((String) dbMap.get("sys.salary.tax.min"));
        this.sys_salary_tax_min = SystemConfigInit.getValue(this.sys_salary_tax_min, "2000");

        this.sys_salary_tax_range = ((String) dbMap.get("sys.salary.tax.range"));
        this.sys_salary_tax_range = SystemConfigInit
                .getValue(this.sys_salary_tax_range,
                          "0,500,2000,5000,20000,40000,60000,80000,100000");

        this.sys_salary_tax_rate = ((String) dbMap.get("sys.salary.tax.rate"));
        this.sys_salary_tax_rate = SystemConfigInit
                .getValue(this.sys_salary_tax_rate, "0.05,0.10,0.15,0.20,0.25,0.30,0.35,0.40,0.45");

        this.sys_salary_config_update = ((String) dbMap.get("sys.salary.config.update"));
        this.sys_salary_config_update = SystemConfigInit.getValue(this.sys_salary_config_update,
                                                                  "0");

        String quartzSalaryAdj = this.fileConfigManager.getProperty("quartz.salaryAdj");
        quartzSalaryAdj = SystemConfigInit.getValue(quartzSalaryAdj, "0 0 2 1 * ?");
        StringTokenizer stotalToken = new StringTokenizer(quartzSalaryAdj);
        stotalToken.nextToken();
        stotalToken.nextToken();
        this.salaryAdjHour = stotalToken.nextToken();
        this.salaryAdjDate = stotalToken.nextToken();

        return "success";
    }

    public String executeSalary() throws Exception {
        if (!hasAuth(961)) {
            addErrorInfo("对不起，您无权进入该页面");
            return "noauth";
        }
        try {
            Map dbMap = this.dbConfigManager.getProperties();
            if (!((String) dbMap.get("sys.salary.tax.min")).equals(this.sys_salary_tax_min)) {
                this.dbConfigManager.setProperty("sys.salary.tax.min", this.sys_salary_tax_min);
            }
            if (!((String) dbMap.get("sys.salary.tax.range")).equals(this.sys_salary_tax_range)) {
                this.dbConfigManager.setProperty("sys.salary.tax.range", this.sys_salary_tax_range);
            }

            if (!((String) dbMap.get("sys.salary.tax.rate")).equals(this.sys_salary_tax_rate)) {
                this.dbConfigManager.setProperty("sys.salary.tax.rate", this.sys_salary_tax_rate);
            }

            if (!((String) dbMap.get("sys.salary.config.update"))
                    .equals(this.sys_salary_config_update)) {
                this.dbConfigManager.setProperty("sys.salary.config.update",
                                                 this.sys_salary_config_update);
            }

            if ((this.salaryAdjDate == null) || ("".equals(this.salaryAdjDate))) {
                this.salaryAdjDate = "*";
            }
            String salaryAdj = "0 0 " + this.salaryAdjHour + " " + this.salaryAdjDate + " * ?";
            this.fileConfigManager.setProperty("quartz.salaryAdj", salaryAdj);
            this.fileConfigManager.saveChange();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Visit  for updating  value error");
            return "input";
        }
        addSuccessInfo("薪资模块参数调整成功");
        return "success";
    }

    public String getSys_salary_tax_min() {
        return this.sys_salary_tax_min;
    }

    public void setSys_salary_tax_min(String sys_salary_tax_min) {
        this.sys_salary_tax_min = sys_salary_tax_min;
    }

    public String getSys_salary_tax_range() {
        return this.sys_salary_tax_range;
    }

    public void setSys_salary_tax_range(String sys_salary_tax_range) {
        this.sys_salary_tax_range = sys_salary_tax_range;
    }

    public String getSys_salary_tax_rate() {
        return this.sys_salary_tax_rate;
    }

    public void setSys_salary_tax_rate(String sys_salary_tax_rate) {
        this.sys_salary_tax_rate = sys_salary_tax_rate;
    }

    public String getSalaryAdjDate() {
        return this.salaryAdjDate;
    }

    public void setSalaryAdjDate(String salaryAdjDate) {
        this.salaryAdjDate = salaryAdjDate;
    }

    public String getSalaryAdjHour() {
        return this.salaryAdjHour;
    }

    public void setSalaryAdjHour(String salaryAdjHour) {
        this.salaryAdjHour = salaryAdjHour;
    }

    public String getSys_salary_config_update() {
        return this.sys_salary_config_update;
    }

    public void setSys_salary_config_update(String sys_salary_config_update) {
        this.sys_salary_config_update = sys_salary_config_update;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.action.ConfigSalary JD-Core Version: 0.5.4
 */