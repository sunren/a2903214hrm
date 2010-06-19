package com.hr.configuration.bo;

import com.hr.configuration.dao.IEcptypeDAO;
import com.hr.configuration.domain.Ecptype;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class EcptypeBOImpl implements IEcptypeBO {
    private IEcptypeDAO dao;

    public EcptypeBOImpl() {
        this.dao = null;
    }

    public List FindAllEcptype() {
        DetachedCriteria dc = DetachedCriteria.forClass(Ecptype.class);
        dc.addOrder(Order.asc("ecptypeSortId"));
        return this.dao.findByCriteria(dc);
    }

    public boolean addEcptype(Ecptype ecptype) {
        try {
            String trimmedEcptypeName = ecptype.getEcptypeName().trim();
            ecptype.setEcptypeName(trimmedEcptypeName);
            ecptype.setEcptypeSortId(Integer.valueOf(0));
            this.dao.saveObject(ecptype);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean delEcptype(Class<Ecptype> name, String id) {
        try {
            List check = new ArrayList();
            boolean isRefed = false;
            check = this.dao.exeHqlList("select count(*) from Empsalaryadj where esaEcptypeId = '"
                    + id + "'");
            if (Integer.valueOf(check.get(0).toString()).intValue() > 0) {
                isRefed = true;
            }
            Ecptype oldEcptype = (Ecptype) this.dao.loadObject(name, id, null, new boolean[0]);
            if ((!isRefed) && (oldEcptype != null)) {
                this.dao.deleteObject(oldEcptype);
                return true;
            }
            return false;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean updateEpcategory(Ecptype ecptype) {
        Ecptype oldEcptype = (Ecptype) this.dao.loadObject(Ecptype.class, ecptype.getId(), null,
                                                           new boolean[0]);
        if (oldEcptype != null) {
            String trimmedEcptypeName = ecptype.getEcptypeName().trim();
            oldEcptype.setEcptypeName(trimmedEcptypeName);
            oldEcptype.setEcptypeDesc(ecptype.getEcptypeDesc());
            this.dao.updateObject(oldEcptype);
            return true;
        }
        return false;
    }

    public void saveEcpTypeByBatch(String[] ids) {
        if (ids == null)
            return;
        int sort = 1;
        int len = ids.length;
        for (int i = 0; i < len; ++i) {
            this.dao.exeHql("update Ecptype set ecptypeSortId=" + sort + " where id ='" + ids[i]
                    + "'");

            ++sort;
        }
    }

    public List getEcptypeList(Ecptype ecptype) {
        DetachedCriteria dc = DetachedCriteria.forClass(Ecptype.class);
        dc.add(Restrictions.eq("ecptypeName", ecptype.getEcptypeName().trim()));
        dc.add(Restrictions.ne("id", ecptype.getId()));

        return this.dao.findByCriteria(dc);
    }

    public List getEcptypeByName(String name) {
        DetachedCriteria dc = DetachedCriteria.forClass(Ecptype.class);
        dc.add(Restrictions.eq("ecptypeName", name.trim()));

        return this.dao.findByCriteria(dc);
    }

    public IEcptypeDAO getDao() {
        return this.dao;
    }

    public void setDao(IEcptypeDAO dao) {
        this.dao = dao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.bo.EcptypeBOImpl JD-Core Version: 0.5.4
 */