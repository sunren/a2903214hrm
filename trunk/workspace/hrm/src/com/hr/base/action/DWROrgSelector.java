package com.hr.base.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.profile.bo.IPositionBaseBo;
import com.hr.profile.domain.base.TreeNode;
import com.hr.spring.SpringBeanFactory;
import java.util.ArrayList;
import java.util.List;

public class DWROrgSelector extends BaseAction {
    private static final long serialVersionUID = 1L;

    public List<TreeNode> getOrgTreeList(String isShowDisable, String isShowPb) {
        List nodeList = new ArrayList();

        Integer status = Integer.valueOf(0);
        if ("show".equals(isShowDisable))
            status = Integer.valueOf(1);

        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        List activeDepts = deptBo.setDeptOfNodeList(nodeList, status.intValue());

        if ("show".equals(isShowPb)) {
            IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
            pbBo.setPbOfNodeList(activeDepts, nodeList, status.intValue());
        }

        return nodeList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.base.action.DWROrgSelector JD-Core Version: 0.5.4
 */