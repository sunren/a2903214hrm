package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.profile.bo.IPositionBaseBo;
import com.hr.profile.bo.IPositionBo;
import com.hr.profile.domain.Position;
import com.hr.profile.domain.PositionBase;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.SysConfigManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PositionManageAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private String positionId;
    private PositionBase pb;
    private Position parentPosition;
    private PositionBase parentPb;
    private Position position;
    private SysConfigManager dbConfigManager;

    public PositionManageAction() {
        this.dbConfigManager = DatabaseSysConfigManager.getInstance();
    }

    public String positionInfo() {
        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        String[] fetch = { Position.PROP_POSITION_EMP_NO, Position.PROP_POSITION_PARENT_ID };
        this.position = posBo.getPosById(this.positionId, fetch);
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
        fetch = new String[] { PositionBase.PROP_PB_DEPT_ID };
        this.pb = pbBo.loadPb(this.position.getPositionPbId().getId(), fetch);
        if ((this.position.getPositionParentId() != null)
                && (this.position.getPositionParentId().getPositionPbId() != null)) {
            fetch = new String[] { Position.PROP_POSITION_EMP_NO };
            this.parentPosition = posBo.getPosById(this.position.getPositionParentId().getId(),
                                                   fetch);
            this.parentPb = pbBo.getPBById(this.position.getPositionParentId().getPositionPbId()
                    .getId());
        }
        return "success";
    }

    public List<PositionBase> getSelectSubList(String id) {
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
        List selectSubList = pbBo.getSelectSubList(id);
        return selectSubList;
    }

    public List<Position> savePosition(String positionId, String selectedPbId, Integer num) {
        String auth = DWRUtil.checkAuth("PositionManageAction", "positionInfo");
        if (!"ADM".equals(auth)) {
            return null;
        }
        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
        PositionBase pb = pbBo.getPBById(selectedPbId);

        Map dbMap = this.dbConfigManager.getProperties();
        String exceedConfig = (String) dbMap.get("sys.position.max.exceed");
        if (exceedConfig == null) {
            exceedConfig = "0";
        }
        if (exceedConfig.equals("1")) {
            Integer maxEmpConfig = pb.getPbMaxEmployee();
            Integer actualNum = Integer.valueOf(posBo.getPosListByPB(pb).size());
            if ((maxEmpConfig == null)
                    || (maxEmpConfig.intValue() - actualNum.intValue() < num.intValue())) {
                return null;
            }
        }

        List newList = new ArrayList();
        for (int i = 0; i < num.intValue(); ++i) {
            Position pos = new Position();
            pos.setPositionParentId(new Position(positionId));
            pos.setPositionPbId(pb);
            pos.setPositionTakeNum(Integer.valueOf(1));
            newList.add(pos);
        }

        if (posBo.savePosList(newList)) {
            return newList;
        }
        return null;
    }

    public String delPosition(String posId) {
        String auth = DWRUtil.checkAuth("PositionManageAction", "positionInfo");
        if (!"ADM".equals(auth)) {
            return "没有权限";
        }

        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        String msg = posBo.delPosition(posId);
        if (msg == null) {
            return "SUCC";
        }
        return msg;
    }

    public String changePosSup(String posId, String selectId) {
        String auth = DWRUtil.checkAuth("PositionManageAction", "positionInfo");
        if (!"ADM".equals(auth)) {
            return "没有权限";
        }
        Position targetPos = null;
        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");

        if (selectId.equals("rootid"))
            targetPos = posBo.getPosInCharge(posId);
        else {
            targetPos = posBo.getPosById(selectId, new String[] { Position.PROP_POSITION_PB_ID });
        }

        if (posId.equals(targetPos.getId()))
            return "不能移动到自身！";

        if (posBo.isSupPos(posId, targetPos.getId()))
            return "不能移动到下级职位！";

        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
        List<String> supPbIds = pbBo.getSupPBsOfPB(targetPos.getId());
        for (String pbIdInSup : supPbIds) {
            if (pbIdInSup.equals(posId))
                return "不能移动到该职位";
        }

        Position pos = posBo.getPosById(posId, new String[] { Position.PROP_POSITION_PB_ID });
        pos.setPositionParentId(new Position(targetPos.getId()));
        posBo.savePos(pos);

        return "SUCC," + selectId + "," + pos.getPositionPbId().getPbInCharge();
    }

    public String getPositionId() {
        return this.positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public PositionBase getPb() {
        return this.pb;
    }

    public void setPb(PositionBase pb) {
        this.pb = pb;
    }

    public PositionBase getParentPb() {
        return this.parentPb;
    }

    public Position getParentPosition() {
        return this.parentPosition;
    }

    public void setParentPosition(Position parentPosition) {
        this.parentPosition = parentPosition;
    }

    public void setParentPb(PositionBase parentPb) {
        this.parentPb = parentPb;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.PositionManageAction JD-Core Version: 0.5.4
 */