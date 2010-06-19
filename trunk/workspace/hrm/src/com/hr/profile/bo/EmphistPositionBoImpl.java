package com.hr.profile.bo;

import com.hr.profile.dao.IEmphistpositionDao;

public class EmphistPositionBoImpl implements IEmphistPositionBo {
    private IEmphistpositionDao emphistpositionDao;

    public IEmphistpositionDao getEmphistpositionDao() {
        return this.emphistpositionDao;
    }

    public void setEmphistpositionDao(IEmphistpositionDao emphistpositionDao) {
        this.emphistpositionDao = emphistpositionDao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.EmphistPositionBoImpl JD-Core Version: 0.5.4
 */