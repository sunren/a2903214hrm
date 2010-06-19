package com.hr.profile.bo;

import com.hr.configuration.domain.Department;
import com.hr.profile.domain.Depthist;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract interface IDeptHistBo {
    public abstract List<Depthist> getDhsBeforeQueryDate(Date paramDate);

    public abstract Depthist getLatestHistOfDept(Department paramDepartment);

    public abstract Map<String, Depthist> getLatestHistOfDept(List<Department> paramList);

    public abstract List<Depthist> getAllDhsOfDept(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.IDeptHistBo JD-Core Version: 0.5.4
 */