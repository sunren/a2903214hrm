package com.hr.profile.bo;

import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.configuration.dao.IDepartmentDAO;
import com.hr.configuration.domain.Department;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Position;
import com.hr.profile.domain.PositionBase;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class OrgDeptMergeHelper extends OrgDeptOperateHelper {
    private Department fromDept;
    private Department toDept;
    private IDepartmentDAO dao = (IDepartmentDAO) SpringBeanFactory.getBean("departmentDAO");
    private IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
    private IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
    private IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
    private ISysLogBO logBO = (ISysLogBO) SpringBeanFactory.getBean("logBO");

    public OrgDeptMergeHelper(Department fromDept, Department toDept) {
        setFromDept(fromDept);
        setToDept(toDept);
    }

    public String cancelResp(String respPosId) {
        String info = "SUCC";

        Position fromRespPos = this.posBo.getPosById(respPosId, new String[0]);

        Position toRespPos = this.posBo.getRespPosOfDept(this.toDept.getId());
        List fromSubPosList = this.posBo.getDirectSubPos(fromRespPos.getId());
        for (int i = 0; (fromSubPosList != null) && (i < fromSubPosList.size()); ++i) {
            Position pos = (Position) fromSubPosList.get(i);
            pos.setPositionParentId(toRespPos);
        }
        this.dao.saveOrupdate(fromSubPosList);

        return info;
    }

    public String respPBCopy(String pbId) {
        Position fromRespPos = this.posBo.getRespPosOfDept(this.fromDept.getId());
        Position toRespPos = this.posBo.getRespPosOfDept(this.toDept.getId());

        PositionBase newPb = this.pbBo.copyPbToAnotherDept(pbId, this.toDept.getId(),
                                                           getCurrentEmp().getId());

        fromRespPos.setPositionParentId(toRespPos);
        fromRespPos.setPositionPbId(newPb);
        this.dao.saveOrupdate(fromRespPos);
        if ((fromRespPos.getPositionEmpNo() != null)
                && (!StringUtils.isEmpty(fromRespPos.getPositionEmpNo().getId()))) {
            this.deptBo.saveEmpTransferData(fromRespPos.getPositionEmpNo(), this.toDept, newPb);
        }
        return "SUCC";
    }

    public String respPBMoveTo(String pbId, String toPBId) {
        PositionBase toPB = this.pbBo.getPBById(toPBId);

        Position fromRespPos = this.posBo.getRespPosOfDept(this.fromDept.getId());
        Position toRespPos = this.posBo.getRespPosOfDept(this.toDept.getId());
        if ((fromRespPos.getPositionEmpNo() != null)
                && (!StringUtils.isEmpty(fromRespPos.getPositionEmpNo().getId()))) {
            Employee emp = fromRespPos.getPositionEmpNo();

            String info = this.posBo.batchSetEmpPos(toRespPos.getId(), toPBId,
                                                    new String[] { fromRespPos.getPositionEmpNo()
                                                            .getId() });
            if (!"SUCC".equals(info))
                return info;

            this.deptBo.saveEmpTransferData(emp, this.toDept, toPB);
        }

        PositionBase fromRespPB = fromRespPos.getPositionPbId();
        fromRespPB.setPbStatus(new Integer(0));
        operateDisablePB(fromRespPB);

        return "SUCC";
    }

    public String pbCopy(String pbId) {
        PositionBase pb = this.pbBo.getPBById(pbId);

        PositionBase newPb = this.pbBo.copyPbToAnotherDept(pbId, this.toDept.getId(),
                                                           getCurrentEmp().getId());

        List posList = this.posBo.getPosListByPB(pb);
        for (int i = 0; (posList != null) && (i < posList.size()); ++i) {
            Position tempPos = (Position) posList.get(i);
            tempPos.setPositionPbId(newPb);
            this.dao.saveOrupdate(tempPos);
            if ((tempPos.getPositionEmpNo() != null)
                    && (!StringUtils.isEmpty(tempPos.getPositionEmpNo().getId()))) {
                this.deptBo.saveEmpTransferData(tempPos.getPositionEmpNo(), this.toDept, newPb);
            }
        }
        return "SUCC";
    }

    public String pbMoveTo(String pbId, String toPBId) {
        PositionBase pb = this.pbBo.getPBById(pbId);
        PositionBase toPB = this.pbBo.getPBById(toPBId);

        List posList = this.posBo.getPosListByPB(pb);
        List empIdList = new ArrayList();
        for (int i = 0; (posList != null) && (i < posList.size()); ++i) {
            Position tempPos = (Position) posList.get(i);
            if ((tempPos.getPositionEmpNo() != null)
                    && (!StringUtils.isEmpty(tempPos.getPositionEmpNo().getId()))) {
                empIdList.add(tempPos.getPositionEmpNo().getId());
            }
        }
        if (this.pbBo.checkPBExceed(toPBId, empIdList.size()))
            StringUtil.message(this.msgPBExceed, new Object[] { this.toDept.getDepartmentName(),
                    toPB.getPbName() });

        String hql = "delete from Position where positionPbId.id='" + toPBId
                + "' and positionEmpNo.id is null";
        this.dao.exeHql(hql);

        for (int i = 0; (posList != null) && (i < posList.size()); ++i) {
            Position tempPos = (Position) posList.get(i);
            tempPos.setPositionPbId(toPB);

            if ((tempPos.getPositionEmpNo() != null)
                    && (!StringUtils.isEmpty(tempPos.getPositionEmpNo().getId())))
                this.deptBo.saveEmpTransferData(tempPos.getPositionEmpNo(), this.toDept, toPB);
        }
        this.dao.saveOrupdate(posList);

        pb.setPbStatus(new Integer(0));
        String tempInfo = operateDisablePB(pb);
        if (!"SUCC".equals(tempInfo))
            return tempInfo;

        return "SUCC";
    }

    public String deptOperate() {
        List fromSubDeptList = this.deptBo.getSubDeptsByParentId(this.fromDept.getId());
        for (int i = 0; (fromSubDeptList != null) && (i < fromSubDeptList.size()); ++i) {
            Department tempDept = (Department) fromSubDeptList.get(i);
            this.deptBo.saveDeptMove(tempDept.getId(), this.toDept.getId());
        }

        this.fromDept.setDepartmentStatus(new Integer(0));
        String tempInfo = operateDisableCurrentDept(this.fromDept);
        if (!"SUCC".equals(tempInfo))
            return tempInfo;

        return "SUCC";
    }

    public Department getToDept() {
        return this.toDept;
    }

    public void setToDept(Department toDept) {
        this.toDept = toDept;
    }

    public Department getFromDept() {
        return this.fromDept;
    }

    public void setFromDept(Department fromDept) {
        this.fromDept = fromDept;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.OrgDeptMergeHelper JD-Core Version: 0.5.4
 */