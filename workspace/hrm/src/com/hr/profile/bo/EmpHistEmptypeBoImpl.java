package com.hr.profile.bo;

import com.hr.profile.dao.IEmpHistEmptypeDao;

public class EmpHistEmptypeBoImpl implements IEmpHistEmptypeBo {
    private IEmpHistEmptypeDao empHistEmptypeDao;

    public IEmpHistEmptypeDao getEmpHistEmptypeDao() {
        return this.empHistEmptypeDao;
    }

    public void setEmpHistEmptypeDao(IEmpHistEmptypeDao empHistEmptypeDao) {
        this.empHistEmptypeDao = empHistEmptypeDao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.EmpHistEmptypeBoImpl JD-Core Version: 0.5.4
 */