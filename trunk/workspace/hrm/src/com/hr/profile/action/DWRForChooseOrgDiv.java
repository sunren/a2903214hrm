package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.profile.bo.IPositionBo;
import com.hr.profile.domain.Position;
import com.hr.profile.domain.PositionBase;
import com.hr.profile.domain.base.TreeNode;
import com.hr.spring.SpringBeanFactory;
import java.util.ArrayList;
import java.util.List;

public class DWRForChooseOrgDiv extends BaseAction {
    private static final long serialVersionUID = 1L;

    public List<TreeNode> getPositionTreeList() {
        IPositionBo positionBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");

        List nodeList = new ArrayList();
        nodeList = positionBo.getPositionTreeListNode();
        return nodeList;
    }

    public List<TreeNode> changeSupPos(String posId) {
        IPositionBo positionBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        Position pos = positionBo.getPosById(posId, new String[] { Position.PROP_POSITION_PB_ID });

        List nodeList = new ArrayList();
        if (pos.getPositionPbId().getPbInCharge().intValue() == 0)
            nodeList = positionBo.getChangeSupTreeListNode(posId);
        else if (pos.getPositionPbId().getPbInCharge().intValue() == 1) {
            nodeList = positionBo.generateEmpConnTreeNodes();
        }
        return nodeList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.DWRForChooseOrgDiv JD-Core Version: 0.5.4
 */