package com.hr.examin.bo;

import com.hr.base.wf.Workflow;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Location;
import com.hr.examin.action.beans.OrgBean;
import com.hr.examin.bo.interfaces.IWorkFlowBO;
import com.hr.examin.dao.interfaces.IWorkFlowApproverDAO;
import com.hr.examin.domain.WorkFlowApprover;
import com.hr.profile.bo.IPositionBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Position;
import com.hr.profile.domain.PositionBase;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.ObjectUtil;
import com.hr.util.StringUtil;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.ObjectUtils;

public class WorkFlowBOImpl implements IWorkFlowBO {
    public IWorkFlowApproverDAO wfdao;

    public OrgBean getNextApprover(Workflow wf) {
        Employee sup = wf.getOperator();
        BigDecimal amount = wf.getAmount();
        String flowType = wf.getFlowType();

        OrgBean orgBean = new OrgBean();

        WorkFlowApprover wfapprover = getWfApproverById(sup.getPosition().getId(), flowType);
        if (wfapprover == null)
            return null;

        orgBean.setLevel(5);

        BigDecimal limitAmount = wfapprover.getWorkFlowLimit();
        if ((wfapprover.getWorkFlowApproverInd().intValue() == 1)
                && (wfapprover.getWorkFlowLimit().doubleValue() == 0.0D)) {
            limitAmount = new BigDecimal(99999999);
        }

        if (limitAmount.compareTo(amount) >= 0) {
            orgBean.setNextApprover(null);
            orgBean.setNextApproverDesc("HR");
            return orgBean;
        }

        wfapprover = getWfApprover_tree(sup, flowType);
        if (wfapprover != null) {
            orgBean.setNextApprover(wfapprover.getWorkFlowApproverId());
            orgBean.setNextApproverDesc(wfapprover.getNextApproverDesc());
            return orgBean;
        }

        orgBean.setNextApprover(null);
        orgBean.setNextApproverDesc("HR");
        return orgBean;
    }

    public OrgBean getNextApproverOfOwn(Workflow wf) {
        Employee sub = wf.getApplier();
        String flowType = wf.getFlowType();

        OrgBean orgBean = new OrgBean();

        orgBean.setLevel(2);

        WorkFlowApprover wfapprover = getWfApprover_tree(sub, flowType);
        if (wfapprover != null) {
            orgBean.setNextApprover(wfapprover.getWorkFlowApproverId());
            orgBean.setNextApproverDesc(wfapprover.getNextApproverDesc());
            return orgBean;
        }

        orgBean.setNextApprover(null);
        orgBean.setNextApproverDesc("HR");
        return orgBean;
    }

    private WorkFlowApprover getWfApprover_tree(Employee emp, String flowType) {
        String wfAppDesc = "{0}({1})";

        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        Position[] supPosArr;
        if (emp.getPosition() != null)
            supPosArr = posBo.getAllSupPosIds(0, emp.getPosition().getId());
        else {
            supPosArr = posBo.getAllSupPosIds(0, emp);
        }
        if (ObjectUtils.isEmpty(supPosArr))
            return null;

        List<WorkFlowApprover> supWFAppList = getWfApproverByIds(ObjectUtil.convObjToIdArr(supPosArr, new String[0]),
                                               flowType);
        if ((supWFAppList == null) || (supWFAppList.size() == 0))
            return null;
        Map supWFAppMap = new HashMap();
        for (WorkFlowApprover wfapprover : supWFAppList) {
            supWFAppMap.put(wfapprover.getWorkFlowApproverId(), wfapprover);
        }

        WorkFlowApprover wfapprover = null;
        for (Position pos : supPosArr) {
            wfapprover = (WorkFlowApprover) supWFAppMap.get(pos.getId());
            if (pos.getPositionEmpNo() != null) {
                if (wfapprover == null)
                    continue;
                String empName = pos.getPositionEmpNo().getEmpName();

                wfapprover.setNextApproverDesc(StringUtil.message(wfAppDesc, new Object[] {
                        pos.getPositionPbId().getPbName(), empName }));

                return wfapprover;
            }
        }

        return null;
    }

    public IWorkFlowApproverDAO getWfdao() {
        return this.wfdao;
    }

    public void setWfdao(IWorkFlowApproverDAO wfdao) {
        this.wfdao = wfdao;
    }

    public WorkFlowApprover getWfApproverById(String approveId, String flowType) {
        DetachedCriteria dc = DetachedCriteria.forClass(WorkFlowApprover.class);
        dc.add(Restrictions.eq("workFlowApproverId", approveId));
        dc.add(Restrictions.eq("workFlowApproverType", flowType));
        List list = this.wfdao.findByCriteria(dc);
        return ((list != null) && (list.size() > 0)) ? (WorkFlowApprover) list.get(0) : null;
    }

    public List<WorkFlowApprover> getWfApproverByIds(String[] approveIds, String flowType) {
        DetachedCriteria dc = DetachedCriteria.forClass(WorkFlowApprover.class);
        dc.add(Restrictions.in("workFlowApproverId", approveIds));
        dc.add(Restrictions.eq("workFlowApproverType", flowType));
        return this.wfdao.findByCriteria(dc);
    }

    public String checkEmpCharge(String[][] deptLocsInCharge, Employee emp) {
        String[] deptsInCharge = deptLocsInCharge[0];
        String[] locsInCharge = deptLocsInCharge[1];

        if ((deptsInCharge != null) && (deptsInCharge.length > 0)
                && (ObjectUtil.contains(deptsInCharge, emp.getEmpDeptNo().getId()))) {
            return "DEPTINCHARGE";
        }
        if ((locsInCharge != null) && (locsInCharge.length > 0)
                && (ObjectUtil.contains(locsInCharge, emp.getEmpLocationNo().getId()))) {
            return "LOCINCHARGE";
        }
        return "NOCHARGE";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.bo.WorkFlowBOImpl JD-Core Version: 0.5.4
 */