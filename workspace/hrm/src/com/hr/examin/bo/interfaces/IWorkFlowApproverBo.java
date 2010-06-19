package com.hr.examin.bo.interfaces;

import com.hr.examin.domain.WorkFlowApprover;
import java.util.List;

public abstract interface IWorkFlowApproverBo {
    public abstract List findItems(String paramString);

    public abstract List getManagers();

    public abstract List findAll();

    public abstract void deleteWorkFlowApproverById(String paramString);

    public abstract String addWorkFlowApprover(WorkFlowApprover paramWorkFlowApprover);

    public abstract String updateWorkFlowApprover(WorkFlowApprover paramWorkFlowApprover);

    public abstract List<WorkFlowApprover> getAllWorkFlowApprover(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.bo.interfaces.IWorkFlowApproverBo JD-Core Version: 0.5.4
 */