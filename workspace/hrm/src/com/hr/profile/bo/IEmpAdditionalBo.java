package com.hr.profile.bo;

import com.hr.profile.domain.Employee;

public abstract interface IEmpAdditionalBo {
    public abstract void saveResume(String paramString1, String paramString2, String paramString3);

    public abstract void save(Employee paramEmployee, String paramString);

    public abstract String deleteResume(String paramString, int paramInt);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.IEmpAdditionalBo JD-Core Version: 0.5.4
 */