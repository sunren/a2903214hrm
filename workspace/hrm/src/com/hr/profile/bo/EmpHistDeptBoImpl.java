package com.hr.profile.bo;

import com.hr.profile.dao.IEmpHistDeptDao;

public class EmpHistDeptBoImpl implements IEmpHistDeptBo {
    private IEmpHistDeptDao empHistDeptDao;

    public IEmpHistDeptDao getEmpHistDeptDao() {
        return this.empHistDeptDao;
    }

    public void setEmpHistDeptDao(IEmpHistDeptDao empHistDeptDao) {
        this.empHistDeptDao = empHistDeptDao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.EmpHistDeptBoImpl JD-Core Version: 0.5.4
 */