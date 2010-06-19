package com.hr.examin.action.beans;

import com.hr.examin.domain.WorkFlowApprover;
import java.io.Serializable;

public class WorkFlowApproverBean implements Serializable {
    private String id;
    private String name;
    private String deptName;
    private String description;
    private WorkFlowApprover workFlow;

    public WorkFlowApproverBean() {
        this.workFlow = new WorkFlowApprover();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WorkFlowApprover getWorkFlow() {
        return this.workFlow;
    }

    public void setWorkFlow(WorkFlowApprover workFlow) {
        this.workFlow = workFlow;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.action.beans.WorkFlowApproverBean JD-Core Version: 0.5.4
 */