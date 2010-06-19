package com.hr.examin.bo;

import com.hr.examin.bo.interfaces.IOvertimetypeBo;
import com.hr.examin.dao.interfaces.IOvertimetypeDAO;
import com.hr.examin.domain.Overtimetype;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class OvertimetypeBoImpl implements IOvertimetypeBo {
    private IOvertimetypeDAO dao;

    public Overtimetype getOvertimetype(String inputOtNo) {
        String[] fetch = null;
        return (Overtimetype) this.dao.loadObject(Overtimetype.class, inputOtNo, fetch,
                                                  new boolean[0]);
    }

    public boolean addOvertimetype(Overtimetype overtimetype) {
        try {
            String trimmedOvertimetypeName = overtimetype.getOtName().trim();
            List old = this.dao.exeHqlList(" from Overtimetype where otName ='"
                    + trimmedOvertimetypeName + "'");

            if ((old == null) || (old.size() == 0)) {
                overtimetype.setOtName(trimmedOvertimetypeName);
                overtimetype.setOtSortId(getMaxRecordNo("Overtimetype", "otSortId"));
                this.dao.saveObject(overtimetype);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Integer getMaxRecordNo(String tableName, String columnName) {
        List maxNoList = this.dao.exeHqlList("select max(" + columnName + ") from " + tableName);
        Integer maxID;
        if ((maxNoList == null) || (maxNoList.size() < 1) || (maxNoList.get(0) == null))
            maxID = Integer.valueOf(1);
        else
            maxID = Integer.valueOf(1 + ((Integer) (Integer) maxNoList.get(0)).intValue());
        return maxID;
    }

    public List FindAllOtType() {
        DetachedCriteria dc = DetachedCriteria.forClass(Overtimetype.class);
        dc.addOrder(Order.asc(Overtimetype.PROP_OT_SORT_ID));
        return this.dao.findByCriteria(dc);
    }

    public Overtimetype searchByID(String otNo) {
        this.dao.loadObject(Overtimetype.class, otNo, null, new boolean[0]);
        Object ob = this.dao.loadObject(Overtimetype.class, otNo, null, new boolean[0]);
        Overtimetype overtimetype;
        if (ob == null)
            overtimetype = null;
        else {
            overtimetype = (Overtimetype) ob;
        }
        return overtimetype;
    }

    public void saveOvertimetypeByBatch(String[] ids) {
        if (ids == null)
            return;
        int sort = 1;
        int len = ids.length;
        for (int i = 0; i < len; ++i) {
            this.dao.exeHql("update Overtimetype set otSortId=" + sort + " where id ='" + ids[i]
                    + "'");

            ++sort;
        }
    }

    public boolean delOvertimetype(Class<Overtimetype> name, String id) {
        try {
            List check = new ArrayList();
            boolean isRefed = false;
            check = this.dao.exeHqlList("select count(*) from Overtimerequest where orOtNo = '"
                    + id + "'");
            if (Integer.valueOf(check.get(0).toString()).intValue() > 0) {
                isRefed = true;
            }
            Overtimetype overtimetype = (Overtimetype) this.dao.loadObject(name, id, null,
                                                                           new boolean[0]);
            if ((!isRefed) && (overtimetype != null)) {
                this.dao.deleteObject(overtimetype);
                return true;
            }
            return false;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean updateOvertimetype(Overtimetype overtimetype) {
        Overtimetype oldOvertimetype = (Overtimetype) this.dao.loadObject(Overtimetype.class,
                                                                          overtimetype.getOtNo(),
                                                                          null, new boolean[0]);
        if (oldOvertimetype != null) {
            String trimmedOvertimetypeName = overtimetype.getOtName().trim();
            oldOvertimetype.setOtName(trimmedOvertimetypeName);
            oldOvertimetype.setOtDesc(overtimetype.getOtDesc());
            oldOvertimetype.setOtOverLimit(overtimetype.getOtOverLimit());
            oldOvertimetype.setOtHourRate(overtimetype.getOtHourRate());
            this.dao.updateObject(oldOvertimetype);
            return true;
        }
        return false;
    }

    public List findOvertimetypeList(Overtimetype overtimetype) {
        DetachedCriteria dc = DetachedCriteria.forClass(Overtimetype.class);
        dc.add(Restrictions.eq("otName", overtimetype.getOtName().trim()));
        dc.add(Restrictions.ne("otNo", overtimetype.getOtNo()));
        List t = this.dao.findByCriteria(dc);
        return t;
    }

    public List findByName(String name) {
        DetachedCriteria dc = DetachedCriteria.forClass(Overtimetype.class);
        dc.add(Restrictions.eq("otName", name.trim()));
        List t = this.dao.findByCriteria(dc);
        return t;
    }

    public IOvertimetypeDAO getDao() {
        return this.dao;
    }

    public void setDao(IOvertimetypeDAO dao) {
        this.dao = dao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.bo.OvertimetypeBoImpl JD-Core Version: 0.5.4
 */