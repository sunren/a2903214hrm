package com.hr.configuration.action;

import com.hr.base.BaseAction;
import com.hr.util.PropertiesFileConfigManager;
import com.hr.util.SysConfigManager;
import java.util.StringTokenizer;

public class ConfigQuarz extends BaseAction {
    private static final long serialVersionUID = 1L;
    private String emailRepeat;
    private String empTotalDate;
    private String empTotalHour;
    private String salaryTotalDate;
    private String salaryTotalHour;
    private String salaryAdjDate;
    private String salaryAdjHour;
    private SysConfigManager fileConfigManager;

    public ConfigQuarz() {
        this.fileConfigManager = PropertiesFileConfigManager.getInstance();
    }

    public String showConfigQuarz() throws Exception {
        Integer emailSecond = Integer.valueOf(new Integer(this.fileConfigManager
                .getProperty("quartz.emailRepeatInterval")).intValue() / 60000);
        this.emailRepeat = emailSecond.toString();

        StringTokenizer stotalToken = new StringTokenizer(this.fileConfigManager
                .getProperty("quartz.empTotal"));
        stotalToken.nextToken();
        stotalToken.nextToken();
        this.empTotalHour = stotalToken.nextToken();
        this.empTotalDate = stotalToken.nextToken();

        stotalToken = new StringTokenizer(this.fileConfigManager.getProperty("quartz.salaryTotal"));
        stotalToken.nextToken();
        stotalToken.nextToken();
        this.salaryTotalHour = stotalToken.nextToken();
        this.salaryTotalDate = stotalToken.nextToken();

        stotalToken = new StringTokenizer(this.fileConfigManager.getProperty("quartz.salaryAdj"));
        stotalToken.nextToken();
        stotalToken.nextToken();
        this.salaryAdjHour = stotalToken.nextToken();
        this.salaryAdjDate = stotalToken.nextToken();
        return "success";
    }

    public String updateQuarz(String emailRepeat, String empTotalHour, String empTotalDate,
            String salaryTotalHour, String salaryTotalDate, String salaryAdjHour,
            String salaryAdjDate) {
        try {
            Integer emailrepeat = Integer.valueOf(new Integer(emailRepeat).intValue() * 60000);
            if ((empTotalDate == null) || ("".equals(empTotalDate))) {
                empTotalDate = "*";
            }
            if ((salaryTotalDate == null) || ("".equals(salaryTotalDate))) {
                salaryTotalDate = "*";
            }
            if ((salaryAdjDate == null) || ("".equals(salaryAdjDate))) {
                salaryAdjDate = "*";
            }

            String empTotal = "0 0 " + empTotalHour + " " + empTotalDate + " * ?";
            String salaryTotal = "0 0 " + salaryTotalHour + " " + salaryTotalDate + " * ?";
            String salaryAdj = "0 0 " + salaryAdjHour + " " + salaryAdjDate + " * ?";

            this.fileConfigManager
                    .setProperty("quartz.emailRepeatInterval", emailrepeat.toString());
            this.fileConfigManager.setProperty("quartz.salaryTotal", salaryTotal);
            this.fileConfigManager.setProperty("quartz.salaryAdj", salaryAdj);
            this.fileConfigManager.setProperty("quartz.empTotal", empTotal);
            this.fileConfigManager.saveChange();
        } catch (Exception e) {
            e.printStackTrace();
            return "FAIL";
        }
        return "SUCC";
    }

    public String getEmailRepeat() {
        return this.emailRepeat;
    }

    public void setEmailRepeat(String emailRepeat) {
        this.emailRepeat = emailRepeat;
    }

    public String getSalaryTotalDate() {
        return this.salaryTotalDate;
    }

    public void setSalaryTotalDate(String salaryTotalDate) {
        this.salaryTotalDate = salaryTotalDate;
    }

    public String getSalaryTotalHour() {
        return this.salaryTotalHour;
    }

    public void setSalaryTotalHour(String salaryTotalHour) {
        this.salaryTotalHour = salaryTotalHour;
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

    public String getEmpTotalDate() {
        return this.empTotalDate;
    }

    public void setEmpTotalDate(String empTotalDate) {
        this.empTotalDate = empTotalDate;
    }

    public String getEmpTotalHour() {
        return this.empTotalHour;
    }

    public void setEmpTotalHour(String empTotalHour) {
        this.empTotalHour = empTotalHour;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.action.ConfigQuarz JD-Core Version: 0.5.4
 */