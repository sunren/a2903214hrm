package com.hr.profile.bo;

import com.hr.configuration.domain.Department;
import com.hr.profile.dao.IDeptHistDao;
import com.hr.profile.domain.Depthist;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class DeptHistBoImpl implements IDeptHistBo {
    private IDeptHistDao deptHistDao;

    public IDeptHistDao getDeptHistDao() {
        return this.deptHistDao;
    }

    public void setDeptHistDao(IDeptHistDao deptHistDao) {
        this.deptHistDao = deptHistDao;
    }

    @SuppressWarnings("unchecked")
    public List<Depthist> getDhsBeforeQueryDate(Date queryDate) {
        DetachedCriteria dc = DetachedCriteria.forClass(Depthist.class);
        dc.add(Restrictions.eq(Depthist.PROP_DHI_DEPT_STATUS, Integer.valueOf(1)));
        dc.add(Restrictions.le(Depthist.PROP_DHI_VALID_FROM, queryDate));
        dc.add(Restrictions.or(Restrictions.ge(Depthist.PROP_DHI_VALID_TO, queryDate), Restrictions
                .isNull(Depthist.PROP_DHI_VALID_TO)));

        return this.deptHistDao.findByCriteria(dc);
    }

    @SuppressWarnings("unchecked")
    public Depthist getLatestHistOfDept(Department dept) {
        DetachedCriteria dc = DetachedCriteria.forClass(Depthist.class);
        dc.add(Restrictions.eq(Depthist.PROP_DHI_DEPT_NO, dept));
        dc.add(Restrictions.isNull(Depthist.PROP_DHI_VALID_TO));

        List<Depthist> deptHistList = this.deptHistDao.findByCriteria(dc);
        return (deptHistList.size() > 0) ? (Depthist) deptHistList.get(0) : null;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Depthist> getLatestHistOfDept(List<Department> deptList) {
        DetachedCriteria dc = DetachedCriteria.forClass(Depthist.class);
        dc.add(Restrictions.in(Depthist.PROP_DHI_DEPT_NO, deptList));
        dc.add(Restrictions.isNull(Depthist.PROP_DHI_VALID_TO));
        List<Depthist> deptHistList = this.deptHistDao.findByCriteria(dc);

        Map<String, Depthist> deptHistMap = new HashMap<String, Depthist>();
        for (Depthist hist : deptHistList) {
            deptHistMap.put(hist.getDhiDeptNo().getId(), hist);
        }
        return deptHistMap;
    }

    @SuppressWarnings("unchecked")
    public List<Depthist> getAllDhsOfDept(String deptId) {
        DetachedCriteria dc = DetachedCriteria.forClass(Depthist.class);
        dc.setFetchMode(Depthist.PROP_DHI_DEPT_NAME, FetchMode.JOIN);
        dc.setFetchMode(Depthist.PROP_DHI_DEPT_SUP_ID, FetchMode.JOIN);
        dc.add(Restrictions.eq(Depthist.PROP_DHI_DEPT_NO + ".id", deptId));
        dc.addOrder(Order.desc(Depthist.PROP_DHI_VALID_FROM));
        List<Depthist> deptHistList = this.deptHistDao.findByCriteria(dc);

        return deptHistList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.DeptHistBoImpl JD-Core Version: 0.5.4
 */