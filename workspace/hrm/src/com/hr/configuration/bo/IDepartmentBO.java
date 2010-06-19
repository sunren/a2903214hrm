package com.hr.configuration.bo;

import com.hr.configuration.domain.Department;
import com.hr.profile.domain.Depthist;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Position;
import com.hr.profile.domain.PositionBase;
import com.hr.profile.domain.PositionBaseHist;
import com.hr.profile.domain.base.TreeNode;
import com.hr.util.Message;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract interface IDepartmentBO {
    public abstract List<Department> FindAllDepartment();

    public abstract List<Department> FindEnabledDepartment();

    public abstract List<Department> getSubDeptsByParentId(String paramString);

    public abstract Integer getMaxSortId();

    public abstract boolean addDeptSave(Department paramDepartment, PositionBase paramPositionBase,
            Position paramPosition, Depthist paramDepthist, PositionBaseHist paramPositionBaseHist);

    public abstract List<Department> FindAllDepartmentConHead();

    public abstract boolean addDepartment(Department paramDepartment);

    public abstract String delDepartment(Class<Department> paramClass, String paramString);

    public abstract String updateDepartment(Department paramDepartment);

    public abstract List getDepartmentsByNos(String[] paramArrayOfString);

    public abstract Department loadDepartmentByNo(String paramString, String[] paramArrayOfString);

    public abstract void saveDepartmentSortIdByBatch(String[] paramArrayOfString);

    public abstract int getActiveEmpNos(String paramString);

    public abstract boolean saveorUpdateDepartment(Department paramDepartment);

    public abstract void saveMergeDept(String paramString1, String paramString2,
            String[][] paramArrayOfString, Message paramMessage);

    public abstract int getOrderEmpNos(String paramString);

    public abstract Department getDeptById(String paramString);

    public abstract Department getDeptByName(String paramString1, String paramString2);

    public abstract String getDeptInCharge(String paramString);

    public abstract String[] getAllSubDeptIds(int paramInt, String paramString);

    public abstract String[] getAllSubDeptIds(String[] paramArrayOfString);

    public abstract List<Department> getAllSubDepts(Integer paramInteger,
            String[] paramArrayOfString);

    public abstract List<Department> getAllSubDeptsOfDept(Department paramDepartment);

    public abstract void update_disableDept(String paramString, Message paramMessage);

    public abstract String update_enableDept(String paramString);

    public abstract void deleteDept(String paramString, Message paramMessage);

    public abstract String saveProcessDeptRename(Department paramDepartment);

    public abstract String saveDeptMove(String paramString1, String paramString2);

    public abstract boolean saveEmpTransferData(Employee paramEmployee, Department paramDepartment,
            PositionBase paramPositionBase);

    public abstract List<TreeNode> FindEnabledDepartmentNode();

    public abstract List getAllActiveDeptEmpNumList();

    public abstract Map<String, Department[]> getDeptLevels(Department[] paramArrayOfDepartment,
            Date paramDate);

    public abstract <T> void setListDeptLevels(List<T> paramList, String paramString1,
            String paramString2, String paramString3, String[] paramArrayOfString);

    public abstract List<Department> setDeptOfNodeList(List<TreeNode> paramList, int paramInt);

    public abstract Map<String, String> getDeptLevelNameMap();

    public abstract String checkInputDeptLevel(Map<String, String> paramMap,
            String[] paramArrayOfString);

    public abstract boolean saveDeptOrder(String[] paramArrayOfString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.bo.IDepartmentBO JD-Core Version: 0.5.4
 */