package com.hr.examin.domain.base;

import com.hr.base.BaseDomain;
import java.io.Serializable;
import java.math.BigDecimal;

public class BaseWorkFlowApprover extends BaseDomain implements Serializable {
    public static String REF = "WorkFlowApprover";
    public static String PROP_ID = "id";
    public static String PROP_WF_ID = "workFlowApproverId";
    public static String PROP_WF_TYPE = "workFlowApproverType";
    public static String PROP_WF_LIMIT = "workFlowLimit";
    public static String PROP_WF_LEVEL = "workFlowApproverLevel";
    public static String PROP_WF_IND = "workFlowApproverInd";
    private String id;
    private String workFlowApproverId;
    private String workFlowApproverType;
    private BigDecimal workFlowLimit;
    private Integer workFlowApproverLevel;
    private Integer workFlowApproverInd;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkFlowApproverId() {
        return this.workFlowApproverId;
    }

    public void setWorkFlowApproverId(String workFlowApproverId) {
        this.workFlowApproverId = workFlowApproverId;
    }

    public String getWorkFlowApproverType() {
        return this.workFlowApproverType;
    }

    public void setWorkFlowApproverType(String workFlowApproverType) {
        this.workFlowApproverType = workFlowApproverType;
    }

    public BigDecimal getWorkFlowLimit() {
        return this.workFlowLimit;
    }

    public void setWorkFlowLimit(BigDecimal workFlowLimit) {
        this.workFlowLimit = workFlowLimit;
    }

    public Integer getWorkFlowApproverLevel() {
        return this.workFlowApproverLevel;
    }

    public void setWorkFlowApproverLevel(Integer workFlowApproverLevel) {
        this.workFlowApproverLevel = workFlowApproverLevel;
    }

    public Integer getWorkFlowApproverInd() {
        return this.workFlowApproverInd;
    }

    public void setWorkFlowApproverInd(Integer workFlowApproverInd) {
        this.workFlowApproverInd = workFlowApproverInd;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.domain.base.BaseWorkFlowApprover JD-Core Version: 0.5.4
 */