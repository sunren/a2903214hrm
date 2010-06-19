package com.hr.profile.bo;

import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Position;
import com.hr.profile.domain.PositionBase;
import com.hr.profile.domain.base.TreeNode;
import java.util.List;
import java.util.Map;

public abstract interface IPositionBo {
    public abstract List<Position> findAllActivePosition();

    public abstract Position getRespPosOfDept(String paramString);

    public abstract Position getPosById(String paramString, String[] paramArrayOfString);

    public abstract Position getPositionById(String paramString);

    public abstract boolean savePos(Position paramPosition);

    public abstract boolean savePosList(List<Position> paramList);

    public abstract List<Position> getPosListOfDept(String paramString);

    public abstract List<Position> getRespPosListOfDept(String[] paramArrayOfString);

    public abstract List<Position> getPosListOfEmps(List<Employee> paramList);

    public abstract List<String> getAllParentPos();

    public abstract boolean isSupPos(String paramString1, String paramString2);

    public abstract String[] getAllSubPosIds(int paramInt, String paramString);

    public abstract String[] getAllSubEmpIds(int paramInt, String paramString);

    public abstract Position[] getAllSupPosIds(int paramInt, Employee paramEmployee);

    public abstract Position[] getAllSupPosIds(int paramInt, String paramString);

    public abstract List<Position> getAllSubPositions(String[] paramArrayOfString1,
            String[] paramArrayOfString2);

    public abstract Position getPosByEmpNo(String paramString, String[] paramArrayOfString);

    public abstract Employee getEmpWithPos(String paramString, String[] paramArrayOfString);

    public abstract List<Position> getDirectSubPos(String paramString);

    public abstract List<Position> getPosListByPB(PositionBase paramPositionBase);

    public abstract String delPosition(String paramString);

    public abstract List<TreeNode> getPositionTreeListNode();

    public abstract List<TreeNode> getChangeSupTreeListNode(String paramString);

    public abstract Position getPosInCharge(String paramString);

    public abstract String batchSetEmpPos(String paramString1, String paramString2,
            String[] paramArrayOfString);

    public abstract Map<String, String> getPosDBMap();

    public abstract List<Position> getDBPosList();

    public abstract Map<String, Position> getAllDeptRespPosMap();

    public abstract boolean parentPosInList(List<Position> paramList, Position paramPosition);

    public abstract Object putEmpToPB(List<Position> paramList1, List<Position> paramList2,
            Employee paramEmployee, PositionBase paramPositionBase);

    public abstract List<TreeNode> generateEmpConnTreeNodes();
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.IPositionBo JD-Core Version: 0.5.4
 */