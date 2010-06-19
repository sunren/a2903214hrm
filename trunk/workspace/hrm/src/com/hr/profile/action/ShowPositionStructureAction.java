package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.profile.bo.IPositionBo;
import com.hr.profile.domain.base.TreeNode;
import com.hr.spring.SpringBeanFactory;
import java.util.ArrayList;
import java.util.List;

public class ShowPositionStructureAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private List<TreeNode> nodeList;

    public ShowPositionStructureAction() {
        this.nodeList = new ArrayList();
    }

    public String showPositionStructure() {
        IDepartmentBO deptbo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        IPositionBo positionBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");

        this.nodeList = positionBo.getPositionTreeListNode();
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
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.ShowPositionStructureAction JD-Core Version: 0.5.4
 */