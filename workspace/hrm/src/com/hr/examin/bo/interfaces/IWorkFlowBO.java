package com.hr.examin.bo.interfaces;

import com.hr.base.wf.Workflow;
import com.hr.examin.action.beans.OrgBean;
import com.hr.examin.domain.WorkFlowApprover;
import com.hr.profile.domain.Employee;
import java.util.List;

public abstract interface IWorkFlowBO {
    public abstract OrgBean getNextApprover(Workflow paramWorkflow);

    public abstract OrgBean getNextApproverOfOwn(Workflow paramWorkflow);

    public abstract WorkFlowApprover getWfApproverById(String paramString1, String paramString2);

    public abstract List<WorkFlowApprover> getWfApproverByIds(String[] paramArrayOfString,
            String paramString);

    public abstract String checkEmpCharge(String[][] paramArrayOfString, Employee paramEmployee);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.bo.interfaces.IWorkFlowBO JD-Core Version: 0.5.4
 */