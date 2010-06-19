package com.hr.profile.bo;

import com.hr.configuration.domain.Department;
import com.hr.profile.domain.PositionBase;
import com.hr.profile.domain.base.TreeNode;
import java.util.List;
import java.util.Map;

public abstract interface IPositionBaseBo {
    public abstract List<PositionBase> findAllActivePb();

    public abstract List<PositionBase> findAllPbs();

    public abstract List<PositionBase> findAllRespPb();

    public abstract List<PositionBase> getActivePbsByDept(String paramString);

    public abstract List<PositionBase> getSupActivePbsByDept(String paramString);

    public abstract PositionBase loadPb(Object paramObject, String[] paramArrayOfString);

    public abstract List<PositionBase> getAllPbsOfDept(String[] paramArrayOfString);

    public abstract PositionBase getRespPBofDept(String paramString);

    public abstract PositionBase getPBById(String paramString);

    public abstract Integer getMaxSortId();

    public abstract Integer getNextPbSortIdOfDept(String paramString);

    public abstract List<PositionBase> getPbsOfDepts(List<Department> paramList);

    public abstract void saveOrupdatePb(PositionBase paramPositionBase, String paramString);

    public abstract String startPb(String paramString);

    public abstract String disablePb(String paramString);

    public abstract boolean duplicateName(String paramString1, String paramString2,
            String paramString3);

    public abstract boolean savePbOrder(String[] paramArrayOfString);

    public abstract String delPb(String paramString);

    public abstract List<PositionBase> getSelectSubList(String paramString);

    public abstract List<String> getSupPBsOfPB(String paramString);

    public abstract boolean checkPBExceed(String paramString, int paramInt);

    public abstract Map<String, List<PositionBase>> getDeptPBMap(String[] paramArrayOfString);

    public abstract void setPbOfNodeList(List<Department> paramList, List<TreeNode> paramList1,
            int paramInt);

    public abstract PositionBase copyPbToAnotherDept(String paramString1, String paramString2,
            String paramString3);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.IPositionBaseBo JD-Core Version: 0.5.4
 */