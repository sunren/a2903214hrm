package com.hr.profile.bo;

import com.hr.profile.dao.IOuresponseDao;
import com.hr.profile.domain.Ouresponse;
import com.hr.profile.domain.PositionBase;
import java.util.List;
import org.apache.axis.utils.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class OuresponseBoImpl implements IOuresponseBo {
    private IOuresponseDao ouresponseDao;

    public IOuresponseDao getOuresponseDao() {
        return this.ouresponseDao;
    }

    public void setOuresponseDao(IOuresponseDao ouresponseDao) {
        this.ouresponseDao = ouresponseDao;
    }

    public Ouresponse saveDeptRes(Ouresponse deptRes) {
        if (StringUtils.isEmpty(deptRes.getId())) {
            if (getDeptRespByName(deptRes) != null) {
                deptRes.setInfo("EXISTED");
                return deptRes;
            }
            deptRes.setOurSortId(getMaxSortId());
            this.ouresponseDao.saveObject(deptRes);
        } else {
            this.ouresponseDao.updateObject(deptRes);
        }

        deptRes.setInfo("SUCC");
        return deptRes;
    }

    public boolean deleteDeptResById(String deptResId) {
        String hql = "delete from Ouresponse where id = '" + deptResId + "'";
        this.ouresponseDao.exeHql(hql);
        return true;
    }

    public boolean saveSortIdBatch(String[] ids) {
        int sortId = 1;
        for (int i = 0; (ids.length > 0) && (i < ids.length); ++i) {
            this.ouresponseDao.exeHql("update Ouresponse set ourSortId=" + sortId + " where id ='"
                    + ids[i] + "'");
            ++sortId;
        }

        return true;
    }

    public Ouresponse getDeptRespByName(Ouresponse deptRes) {
        DetachedCriteria dc = DetachedCriteria.forClass(Ouresponse.class);
        dc.add(Restrictions.eq(Ouresponse.PROP_OUR_DEPT, deptRes.getOurDept()));
        dc.add(Restrictions.eq(Ouresponse.PROP_OUR_NAME, deptRes.getOurName()));
        List resList = this.ouresponseDao.findByCriteria(dc);

        if (resList.size() == 0)
            return null;
        return (Ouresponse) resList.get(0);
    }

    public List<Ouresponse> getDeptRespLisByDept(String deptId) {
        DetachedCriteria dc = DetachedCriteria.forClass(Ouresponse.class);
        dc.add(Restrictions.eq(Ouresponse.PROP_OUR_DEPT + ".id", deptId));
        dc.addOrder(Order.asc(Ouresponse.PROP_OUR_SORT_ID));
        List resList = this.ouresponseDao.findByCriteria(dc);

        return resList;
    }

    public synchronized Integer getMaxSortId() {
        String hql = "select max(ourSortId) as maxSortId from Ouresponse res";
        List maxNoList = this.ouresponseDao.exeHqlList(hql);
        Integer maxID;
        if ((maxNoList == null) || (maxNoList.size() < 1) || (maxNoList.get(0) == null))
            maxID = Integer.valueOf(1);
        else {
            maxID = Integer.valueOf(1 + ((Integer) (Integer) maxNoList.get(0)).intValue());
        }
        return maxID;
    }

    public List<Ouresponse> getPbRespByPbId(String pbId) {
        DetachedCriteria dc = DetachedCriteria.forClass(Ouresponse.class);
        dc.add(Restrictions.eq(Ouresponse.PROP_OUR_PB, new PositionBase(pbId)));
        dc.addOrder(Order.asc(Ouresponse.PROP_OUR_SORT_ID));
        return this.ouresponseDao.findByCriteria(dc);
    }

    public boolean addPbResp(Ouresponse pbResp) {
        boolean duplicateName = duplicateName(pbResp, "add");

        if (!duplicateName) {
            pbResp.setOurSortId(Integer.valueOf(0));
            this.ouresponseDao.saveObject(pbResp);
            return true;
        }
        return false;
    }

    public boolean updatePbResp(Ouresponse pbResp) {
        boolean duplicateName = duplicateName(pbResp, "update");

        if (!duplicateName) {
            Ouresponse old = (Ouresponse) this.ouresponseDao.loadObject(Ouresponse.class, pbResp
                    .getId(), null, new boolean[0]);
            old.setOurName(pbResp.getOurName());
            old.setOurDesc(pbResp.getOurDesc());
            old.setOurRate(pbResp.getOurRate());
            this.ouresponseDao.updateObject(old);
            return true;
        }
        return false;
    }

    private boolean duplicateName(Ouresponse pbResp, String operate) {
        DetachedCriteria dc = DetachedCriteria.forClass(Ouresponse.class);
        dc
                .add(Restrictions.eq(Ouresponse.PROP_OUR_PB, new PositionBase(pbResp.getOurPb()
                        .getId())));
        dc.add(Restrictions.eq(Ouresponse.PROP_OUR_NAME, pbResp.getOurName()));
        if (operate.equals("update")) {
            dc.add(Restrictions.not(Restrictions.eq(Ouresponse.PROP_ID, pbResp.getId().trim())));
        }
        List pbRespByName = this.ouresponseDao.findByCriteria(dc);

        return (pbRespByName != null) && (pbRespByName.size() != 0);
    }

    public boolean delPbResp(String respId) {
        try {
            this.ouresponseDao.deleteObject(Ouresponse.class, respId);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void savePbRespOrder(String[] ids) {
        if (ids == null)
            return;
        int sort = 1;
        int len = ids.length;
        for (int i = 0; i < len; ++i) {
            this.ouresponseDao.exeHql("update Ouresponse set ourSortId=" + sort + " where id ='"
                    + ids[i] + "'");

            ++sort;
        }
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.OuresponseBoImpl JD-Core Version: 0.5.4
 */