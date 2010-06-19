package com.hr.profile.bo;

import com.hr.profile.dao.IDeptHeadsDao;
import com.hr.profile.domain.Deptheads;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;

public class DeptHeadsBoImpl implements IDeptHeadsBo {
    private IDeptHeadsDao deptHeadsDao;

    public IDeptHeadsDao getDeptHeadsDao() {
        return this.deptHeadsDao;
    }

    public void setDeptHeadsDao(IDeptHeadsDao deptHeadsDao) {
        this.deptHeadsDao = deptHeadsDao;
    }

    public List<Deptheads> getHeadsByDeptId(String deptId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Deptheads.class);
        detachedCriteria.setFetchMode(Deptheads.PROP_DHE_EMP_NO, FetchMode.JOIN);
        List headsList = this.deptHeadsDao.findByCriteria(detachedCriteria);

        return headsList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.DeptHeadsBoImpl JD-Core Version: 0.5.4
 */