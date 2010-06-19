package com.hr.report.bo;

import com.hr.report.action.compensation.support.SalaryReportParamBean;
import java.util.List;

public abstract interface SalaryReportBo {
    public abstract List getObjects(String paramString1, String paramString2);

    public abstract <E> E loadObject(Class<E> paramClass, Object paramObject,
            String[] paramArrayOfString);

    public abstract List getSalaryReportHistory(SalaryReportParamBean paramSalaryReportParamBean);

    public abstract List getSalaryReportCost(SalaryReportParamBean paramSalaryReportParamBean);

    public abstract List getEmployeeDimissionRate(String paramString1, String paramString2);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.bo.SalaryReportBo JD-Core Version: 0.5.4
 */