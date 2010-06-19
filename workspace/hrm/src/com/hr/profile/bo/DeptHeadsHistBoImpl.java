package com.hr.profile.bo;

import com.hr.profile.dao.IDeptHeadsHistDao;

public class DeptHeadsHistBoImpl implements IDeptHeadsHistBo {
    private IDeptHeadsHistDao deptHeadsHistDao;

    public IDeptHeadsHistDao getDeptHeadsHistDao() {
        return this.deptHeadsHistDao;
    }

    public void setDeptHeadsHistDao(IDeptHeadsHistDao deptHeadsHistDao) {
        this.deptHeadsHistDao = deptHeadsHistDao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.DeptHeadsHistBoImpl JD-Core Version: 0.5.4
 */