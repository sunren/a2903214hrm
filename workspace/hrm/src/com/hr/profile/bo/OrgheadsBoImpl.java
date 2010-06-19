package com.hr.profile.bo;

import com.hr.profile.dao.IOrgheadsDao;
import com.hr.profile.domain.Orgheads;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class OrgheadsBoImpl implements IOrgheadsBo {
    IOrgheadsDao headsDao;

    public boolean insert(Orgheads head) {
        try {
            String hql = "delete from Orgheads where orgheadsOrgNo='" + head.getOrgheadsOrgNo()
                    + "'";
            this.headsDao.exeHql(hql);
            this.headsDao.saveObject(head);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addOrgheads(Orgheads head) {
        try {
            String hql2 = "select count(*) from Orgheads where orgheadsOrgNo='"
                    + head.getOrgheadsOrgNo() + "' and orgheadsEmpNo  ='" + head.getOrgheadsEmpNo()
                    + "'";
            List check = this.headsDao.exeHqlList(hql2);
            if (Integer.valueOf(check.get(0).toString()).intValue() > 0) {
                return false;
            }

            check = this.headsDao.exeHqlList("select count(*) from Orgheads where orgheadsOrgNo='"
                    + head.getOrgheadsOrgNo() + "' and orgheadsResponsibility like '%header%'");
            if (Integer.valueOf(check.get(0).toString()).intValue() == 0) {
                if (head.getOrgheadsResponsibility().equalsIgnoreCase("bradeputy"))
                    head.setOrgheadsResponsibility("braheader");
                else if (head.getOrgheadsResponsibility().equalsIgnoreCase("deptdeputy"))
                    head.setOrgheadsResponsibility("deptheader");
                else if (head.getOrgheadsResponsibility().equalsIgnoreCase("budeputy"))
                    head.setOrgheadsResponsibility("buheader");
                else if (head.getOrgheadsResponsibility().equalsIgnoreCase("grpdeputy"))
                    head.setOrgheadsResponsibility("grpheader");
                else if (head.getOrgheadsResponsibility().equalsIgnoreCase("locationdeputy")) {
                    head.setOrgheadsResponsibility("locationheader");
                }
            }
            this.headsDao.saveObject(head);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateOrgheads(Orgheads head) {
        try {
            String hql = "select count(*) from Orgheads where orgheadsId != '"
                    + head.getOrgheadsId() + "' and orgheadsOrgNo='" + head.getOrgheadsOrgNo()
                    + "' and orgheadsEmpNo='" + head.getOrgheadsEmpNo() + "'";
            List check = this.headsDao.exeHqlList(hql);
            if (Integer.valueOf(check.get(0).toString()).intValue() > 0) {
                return false;
            }

            Orgheads orgheads = (Orgheads) this.headsDao.loadObject(Orgheads.class, head
                    .getOrgheadsId(), null, new boolean[0]);
            orgheads.setOrgheadsResponsibility(head.getOrgheadsResponsibility());
            orgheads.setOrgheadsEmpNo(head.getOrgheadsEmpNo());
            orgheads.setOrgheadsOrgNo(head.getOrgheadsOrgNo());

            this.headsDao.updateObject(orgheads);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delOrgheads(Class<Orgheads> head, String id) {
        Orgheads orgheads = (Orgheads) this.headsDao.loadObject(head, id, null, new boolean[0]);
        if (orgheads == null) {
            return false;
        }

        String deputy = orgheads.getOrgheadsResponsibility();
        if (deputy.endsWith("header")) {
            String hql = "select count(*) from Orgheads";
            hql = hql + " where orgheadsOrgNo='" + orgheads.getOrgheadsOrgNo() + "'";
            hql = hql + " and orgheadsResponsibility like '%deputy%'";
            List check = this.headsDao.exeHqlList(hql);
            if (Integer.valueOf(check.get(0).toString()).intValue() > 0) {
                return false;
            }
        }
        this.headsDao.deleteObject(orgheads);
        return true;
    }

    public void saveOrgHeadsSortIdByBatch(String[] ids) {
        int length = ids.length;
        int sortId = 1;
        for (int i = 0; i < length; ++i) {
            this.headsDao.exeHql("update Orgheads set orgHeadsSortId=" + sortId + " where id ='"
                    + ids[i] + "'");

            ++sortId;
        }
    }

    public List<Orgheads> listOrgheadNo(String orgType) {
        if (orgType == null)
            return null;

        String hql = "from Orgheads where orgheadsResponsibility like '" + orgType + "%'";
        return this.headsDao.exeHqlList(hql);
    }

    public List FindAllOrgheads() {
        DetachedCriteria dc = DetachedCriteria.forClass(Orgheads.class);
        dc.addOrder(Order.asc("orgHeadsSortId"));
        return this.headsDao.findByCriteria(dc);
    }

    public List<Orgheads> FindOrgheadsByEmpId(String empNo) {
        DetachedCriteria dc = DetachedCriteria.forClass(Orgheads.class);
        dc.add(Restrictions.eq("orgheadsEmpNo", empNo));

        return this.headsDao.findByCriteria(dc);
    }

    public List exeHqlList(String hql) {
        return this.headsDao.exeHqlList(hql);
    }

    public List<Orgheads> getOrgheadsByOrgId(String orgId) {
        DetachedCriteria dc = DetachedCriteria.forClass(Orgheads.class);
        dc.add(Restrictions.eq("orgheadsOrgNo", orgId.trim()));
        return this.headsDao.findByCriteria(dc);
    }

    public IOrgheadsDao getHeadsDao() {
        return this.headsDao;
    }

    public void setHeadsDao(IOrgheadsDao headsDao) {
        this.headsDao = headsDao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.OrgheadsBoImpl JD-Core Version: 0.5.4
 */