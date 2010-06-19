package com.hr.configuration.domain.base;

import com.hr.base.BaseDomain;
import com.hr.compensation.domain.Empsalaryacct;
import com.hr.configuration.domain.Jobgrade;
import java.io.Serializable;

public abstract class BaseJobTitle extends BaseDomain implements Serializable {
    public static String REF = "JobTitle";
    public static String PROP_JOBTITLE_NAME = "jobtitleName";
    public static String PROP_JOBTITLE_NAME_DESC = "jobtitleNameDesc";
    public static String PROP_JOBTITLE_NEED_GM_APPROVE = "jobtitleNeedGmApprove";
    public static String PROP_JOBTITLE_STATUS = "jobtitleStatus";
    public static String PROP_JOBTITLE_SORT_ID = "jobtitleSortId";
    public static String PROP_ID = "jobtitleNo";
    private String jobtitleName;
    private String jobtitleNameDesc;
    private Integer jobtitleNeedGmApprove;
    private Integer jobtitleSortId;
    private Integer jobtitleStatus;
    private String jobtitleNo;
    private Empsalaryacct jobtitleDefaultAcct;
    private Jobgrade jobtitleDefaultJg;

    public BaseJobTitle() {
    }

    public BaseJobTitle(String jobtitleName, Integer jobtitleNeedGmApprove, Integer jobtitleSortId) {
        this.jobtitleName = jobtitleName;
        this.jobtitleNeedGmApprove = jobtitleNeedGmApprove;
        this.jobtitleSortId = jobtitleSortId;
    }

    public BaseJobTitle(String jobtitleName, String jobtitleNameDesc,
            Empsalaryacct jobtitleDefaultAcct, Jobgrade jobtitleDefaultJg,
            Integer jobtitleNeedGmApprove, Integer jobtitleSortId) {
        this.jobtitleName = jobtitleName;
        this.jobtitleNameDesc = jobtitleNameDesc;
        this.jobtitleDefaultAcct = jobtitleDefaultAcct;
        this.jobtitleDefaultJg = jobtitleDefaultJg;
        this.jobtitleNeedGmApprove = jobtitleNeedGmApprove;
        this.jobtitleSortId = jobtitleSortId;
    }

    public String getJobtitleNo() {
        return this.jobtitleNo;
    }

    public void setJobtitleNo(String jobtitleNo) {
        this.jobtitleNo = jobtitleNo;
    }

    public String getJobtitleName() {
        return this.jobtitleName;
    }

    public void setJobtitleName(String jobtitleName) {
        this.jobtitleName = jobtitleName;
    }

    public String getJobtitleNameDesc() {
        return this.jobtitleNameDesc;
    }

    public void setJobtitleNameDesc(String jobtitleNameDesc) {
        this.jobtitleNameDesc = jobtitleNameDesc;
    }

    public Empsalaryacct getJobtitleDefaultAcct() {
        return this.jobtitleDefaultAcct;
    }

    public void setJobtitleDefaultAcct(Empsalaryacct jobtitleDefaultAcct) {
        this.jobtitleDefaultAcct = jobtitleDefaultAcct;
    }

    public Jobgrade getJobtitleDefaultJg() {
        return this.jobtitleDefaultJg;
    }

    public void setJobtitleDefaultJg(Jobgrade jobtitleDefaultJg) {
        this.jobtitleDefaultJg = jobtitleDefaultJg;
    }

    public Integer getJobtitleNeedGmApprove() {
        return this.jobtitleNeedGmApprove;
    }

    public void setJobtitleNeedGmApprove(Integer jobtitleNeedGmApprove) {
        this.jobtitleNeedGmApprove = jobtitleNeedGmApprove;
    }

    public Integer getJobtitleSortId() {
        return this.jobtitleSortId;
    }

    public void setJobtitleSortId(Integer jobtitleSortId) {
        this.jobtitleSortId = jobtitleSortId;
    }

    public Integer getJobtitleStatus() {
        return this.jobtitleStatus;
    }

    public void setJobtitleStatus(Integer jobtitleStatus) {
        this.jobtitleStatus = jobtitleStatus;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.domain.base.BaseJobTitle JD-Core Version: 0.5.4
 */