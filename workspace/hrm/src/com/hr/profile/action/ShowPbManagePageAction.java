package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.profile.bo.IPositionBaseBo;
import com.hr.profile.domain.base.TreeNode;
import com.hr.spring.SpringBeanFactory;
import java.util.ArrayList;
import java.util.List;

public class ShowPbManagePageAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private List<TreeNode> nodeList;

    public ShowPbManagePageAction() {
        this.nodeList = new ArrayList();
    }

    public String showPbManagePage() {
        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        List activeDepts = deptBo.setDeptOfNodeList(this.nodeList, 0);

        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
        pbBo.setPbOfNodeList(activeDepts, this.nodeList, 1);
        return "success";
    }

    public List<TreeNode> getNodeList() {
        return this.nodeList;
    }

    public void setNodeList(List<TreeNode> nodeList) {
        this.nodeList = nodeList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.ShowPbManagePageAction JD-Core Version: 0.5.4
 */