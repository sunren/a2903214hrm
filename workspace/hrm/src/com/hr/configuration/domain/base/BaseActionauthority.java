package com.hr.configuration.domain.base;

import com.hr.base.BaseDomain;
import com.hr.configuration.domain.Actionauthority;
import java.io.Serializable;

public abstract class BaseActionauthority extends BaseDomain implements Serializable {
    public static String REF = "Actionauthority";
    public static String PROP_ACTIONAUTH_ACTION_NAME = "actionauthActionName";
    public static String PROP_ACTIONAUTH_CONDITION_NO = "actionauthConditionNo";
    public static String PROP_ACTIONAUTH_LOOKUP_SEQ = "actionauthLookupSeq";
    public static String PROP_ACTIONAUTH_DESC = "actionauthDesc";
    public static String PROP_ACTIONAUTH_FILTER = "actionauthFilter";
    public static String PROP_ID = "id";
    public static String PROP_ACTIONAUTH_METHOD_NAME = "actionauthMethodName";
    public static String PROP_ACTIONAUTH_MODULE_NO = "actionauthModuleNo";

    private int hashCode = -2147483648;
    private Integer id;
    private String actionauthActionName;
    private String actionauthMethodName;
    private Integer actionauthLookupSeq;
    private String actionauthModuleNo;
    private String actionauthConditionNo;
    private String actionauthFilter;
    private String actionauthDesc;

    public BaseActionauthority() {
        initialize();
    }

    public BaseActionauthority(Integer id) {
        setId(id);
        initialize();
    }

    public BaseActionauthority(Integer id, String actionauthActionName,
            Integer actionauthLookupSeq, String actionauthModuleNo, String actionauthDesc) {
        setId(id);
        setActionauthActionName(actionauthActionName);
        setActionauthLookupSeq(actionauthLookupSeq);
        setActionauthModuleNo(actionauthModuleNo);
        setActionauthDesc(actionauthDesc);
        initialize();
    }

    protected void initialize() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getActionauthActionName() {
        return this.actionauthActionName;
    }

    public void setActionauthActionName(String actionauthActionName) {
        this.actionauthActionName = actionauthActionName;
    }

    public String getActionauthMethodName() {
        return this.actionauthMethodName;
    }

    public void setActionauthMethodName(String actionauthMethodName) {
        this.actionauthMethodName = actionauthMethodName;
    }

    public Integer getActionauthLookupSeq() {
        return this.actionauthLookupSeq;
    }

    public void setActionauthLookupSeq(Integer actionauthLookupSeq) {
        this.actionauthLookupSeq = actionauthLookupSeq;
    }

    public String getActionauthModuleNo() {
        return this.actionauthModuleNo;
    }

    public void setActionauthModuleNo(String actionauthModuleNo) {
        this.actionauthModuleNo = actionauthModuleNo;
    }

    public String getActionauthConditionNo() {
        return this.actionauthConditionNo;
    }

    public void setActionauthConditionNo(String actionauthConditionNo) {
        this.actionauthConditionNo = actionauthConditionNo;
    }

    public String getActionauthFilter() {
        return this.actionauthFilter;
    }

    public void setActionauthFilter(String actionauthFilter) {
        this.actionauthFilter = actionauthFilter;
    }

    public String getActionauthDesc() {
        return this.actionauthDesc;
    }

    public void setActionauthDesc(String actionauthDesc) {
        this.actionauthDesc = actionauthDesc;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Actionauthority))
            return false;

        Actionauthority actionauthority = (Actionauthority) obj;
        if ((null == getId()) || (null == actionauthority.getId()))
            return false;
        return getId().equals(actionauthority.getId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.domain.base.BaseActionauthority JD-Core Version: 0.5.4
 */