package com.hr.profile.bo;

import com.hr.profile.dao.IEmpHistJobtitleDao;

public class EmpHistJobtitleBoImpl implements IEmpHistJobtitleBo {
    private IEmpHistJobtitleDao empHistJobtitleDao;

    public IEmpHistJobtitleDao getEmpHistJobtitleDao() {
        return this.empHistJobtitleDao;
    }

    public void setEmpHistJobtitleDao(IEmpHistJobtitleDao empHistJobtitleDao) {
        this.empHistJobtitleDao = empHistJobtitleDao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.EmpHistJobtitleBoImpl JD-Core Version: 0.5.4
 */