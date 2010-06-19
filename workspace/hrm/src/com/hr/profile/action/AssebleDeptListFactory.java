package com.hr.profile.action;

import java.util.Date;

public class AssebleDeptListFactory {
    public static AssembleDeptList creator(Date queryDate, String queryDeptId, Integer queryDepth) {
        AssembleDeptList as;
        if (queryDate == null)
            as = new AssembleCurrent();
        else {
            as = new AssembleHist();
        }
        as.setQueryDate(queryDate);
        as.setQueryDeptId(queryDeptId);
        as.setQueryDepth(queryDepth);
        return as;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.AssebleDeptListFactory JD-Core Version: 0.5.4
 */