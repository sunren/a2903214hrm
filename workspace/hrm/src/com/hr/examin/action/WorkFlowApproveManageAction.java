package com.hr.examin.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.ILocationBO;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Location;
import com.hr.examin.action.beans.WorkFlowApproverBean;
import com.hr.examin.bo.interfaces.IWorkFlowApproverBo;
import com.hr.examin.domain.WorkFlowApprover;
import com.hr.profile.bo.IPositionBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Position;
import com.hr.profile.domain.PositionBase;
import com.hr.profile.domain.base.TreeNode;
import com.hr.security.bo.AuthorityPosBo;
import com.hr.security.domain.AuthorityPos;
import com.hr.spring.SpringBeanFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.birt.report.model.api.util.StringUtil;

public class WorkFlowApproveManageAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private String positionId;
    private List<WorkFlowApproverBean> items;
    private List<TreeNode> nodeList;
    private List<AuthorityPos> authList;
    private List<Location> locationList;
    private WorkFlowApprover workFlow1;
    private String positionName;
    private WorkFlowApprover workFlow2;
    private IWorkFlowApproverBo workFlowApproverBo;

    public WorkFlowApproveManageAction() {
        this.nodeList = new ArrayList();

        this.workFlowApproverBo = ((IWorkFlowApproverBo) getBean("workFlowApproverBo"));
    }

    public String workFlowConfig() {
        IPositionBo positionBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");

        this.nodeList = positionBo.getPositionTreeListNode();
        return "success";
    }

    public String workFlowShow() {
        return "success";
    }

    public String workFlowList() throws Exception {
        IPositionBo positionBO = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        Position position = positionBO.getPositionById(this.positionId);

        List list = this.workFlowApproverBo.getAllWorkFlowApprover(position.getId());
        this.items = formatList(position, list);

        return "success";
    }

    public String authorityPos() {
        AuthorityPosBo authorityPosBo = (AuthorityPosBo) SpringBeanFactory
                .getBean("authPosService");
        this.authList = authorityPosBo.getAuthPosInfoList(this.positionId, String.valueOf(4));

        if (this.authList != null) {
            this.positionName = ((AuthorityPos) this.authList.get(0)).getApPos().getPositionPbId()
                    .getPbName();
        } else {
            IPositionBo positionBO = (IPositionBo) SpringBeanFactory.getBean("positionBo");
            Position position = positionBO.getPositionById(this.positionId);
            this.positionName = position.getPositionPbId().getPbName();
        }

        ILocationBO localbo = (ILocationBO) SpringBeanFactory.getBean("locationBO");
        this.locationList = localbo.FindEnabledLocation();

        return "success";
    }

    public String add() {
        this.workFlow1.setWorkFlowApproverId(this.positionId);
        this.workFlowApproverBo.addWorkFlowApprover(this.workFlow1);

        return "success";
    }

    public String update() {
        this.workFlow1.setWorkFlowApproverId(this.positionId);
        if (StringUtil.isEmpty(this.workFlow1.getId())) {
            if (this.workFlow1.getWorkFlowApproverInd().intValue() != 2) {
                this.workFlowApproverBo.addWorkFlowApprover(this.workFlow1);
            }
        } else if (this.workFlow1.getWorkFlowApproverInd().intValue() == 2)
            this.workFlowApproverBo.deleteWorkFlowApproverById(this.workFlow1.getId());
        else {
            this.workFlowApproverBo.updateWorkFlowApprover(this.workFlow1);
        }

        this.workFlow2.setWorkFlowApproverId(this.positionId);
        if (StringUtil.isEmpty(this.workFlow2.getId())) {
            if (this.workFlow1.getWorkFlowApproverInd().intValue() != 2) {
                this.workFlowApproverBo.addWorkFlowApprover(this.workFlow2);
            }
        } else if (this.workFlow2.getWorkFlowApproverInd().intValue() == 2)
            this.workFlowApproverBo.deleteWorkFlowApproverById(this.workFlow2.getId());
        else {
            this.workFlowApproverBo.updateWorkFlowApprover(this.workFlow2);
        }

        return "success";
    }

    public String delete() {
        this.workFlowApproverBo.deleteWorkFlowApproverById(this.workFlow1.getId());
        return "success";
    }

    private List<WorkFlowApproverBean> formatList(Position position, List<WorkFlowApprover> list) {
        List result = new ArrayList();

        if ((list == null) || (list.isEmpty())) {
            WorkFlowApproverBean bean = new WorkFlowApproverBean();
            bean = newWorkFlowApproverBean(position, "leaverequest");
            bean.setWorkFlow(newWorkFlowApprover(position, "leaverequest"));
            result.add(bean);

            bean = newWorkFlowApproverBean(position, "overtimerequest");
            bean.setWorkFlow(newWorkFlowApprover(position, "overtimerequest"));
            result.add(bean);
        } else {
            Map map = new HashMap();
            for (WorkFlowApprover wf : list) {
                map.put(wf.getWorkFlowApproverType(), wf);
            }
            WorkFlowApprover wf = (WorkFlowApprover) map.get("leaverequest");
            WorkFlowApproverBean bean = new WorkFlowApproverBean();
            if (wf == null) {
                bean = newWorkFlowApproverBean(position, "leaverequest");
                bean.setWorkFlow(newWorkFlowApprover(position, "leaverequest"));
            } else {
                bean = newWorkFlowApproverBean(position, "leaverequest");
                bean.setWorkFlow(wf);
            }
            result.add(bean);
            wf = (WorkFlowApprover) map.get("overtimerequest");
            if (wf == null) {
                bean = newWorkFlowApproverBean(position, "overtimerequest");
                bean.setWorkFlow(newWorkFlowApprover(position, "overtimerequest"));
            } else {
                bean = newWorkFlowApproverBean(position, "overtimerequest");
                bean.setWorkFlow(wf);
            }
            result.add(bean);
        }
        return result;
    }

    private WorkFlowApproverBean newWorkFlowApproverBean(Position position, String type) {
        WorkFlowApproverBean bean = new WorkFlowApproverBean();
        bean.setId(type);
        bean.setDescription(position.getPositionPbId().getPbName());
        bean.setDeptName(position.getPositionPbId().getPbDeptId().getDepartmentName());
        if (position.getPositionEmpNo() != null)
            bean.setName(position.getPositionEmpNo().getEmpName());
        else {
            bean.setName("空缺");
        }
        return bean;
    }

    private WorkFlowApprover newWorkFlowApprover(Position position, String type) {
        WorkFlowApprover wf = new WorkFlowApprover();
        wf.setWorkFlowApproverType(type);
        wf.setWorkFlowApproverId(position.getId());
        wf.setWorkFlowApproverInd(Integer.valueOf(2));
        return wf;
    }

    public String getPositionId() {
        return this.positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public List<WorkFlowApproverBean> getItems() {
        return this.items;
    }

    public void setItems(List<WorkFlowApproverBean> items) {
        this.items = items;
    }

    public List<TreeNode> getNodeList() {
        return this.nodeList;
    }

    public void setNodeList(List<TreeNode> nodeList) {
        this.nodeList = nodeList;
    }

    public List<AuthorityPos> getAuthList() {
        return this.authList;
    }

    public void setAuthList(List<AuthorityPos> authList) {
        this.authList = authList;
    }

    public List<Location> getLocationList() {
        return this.locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public WorkFlowApprover getWorkFlow1() {
        return this.workFlow1;
    }

    public void setWorkFlow1(WorkFlowApprover workFlow1) {
        this.workFlow1 = workFlow1;
    }

    public String getPositionName() {
        return this.positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public WorkFlowApprover getWorkFlow2() {
        return this.workFlow2;
    }

    public void setWorkFlow2(WorkFlowApprover workFlow2) {
        this.workFlow2 = workFlow2;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.action.WorkFlowApproveManageAction JD-Core Version: 0.5.4
 */