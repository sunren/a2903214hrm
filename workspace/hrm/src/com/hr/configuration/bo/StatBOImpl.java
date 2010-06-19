package com.hr.configuration.bo;

import com.hr.configuration.dao.IStatusDAO;
import com.hr.configuration.domain.Statusconf;
import com.hr.configuration.domain.StatusconfPK;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class StatBOImpl implements IStatBO {
    private IStatusDAO statusDAO;

    public StatBOImpl() {
        this.statusDAO = null;
    }

    public List FindAllStatus() {
        DetachedCriteria dc = DetachedCriteria.forClass(Statusconf.class);
        return this.statusDAO.findByCriteria(dc);
    }

    public boolean addStat(Statusconf stat) {
        try {
            String trimmedStatusconfTablename = stat.getId().getStatusconfTablename().trim();
            List old = this.statusDAO.exeHqlList(" from Statusconf where id.statusconfNo ="
                    + stat.getId().getStatusconfNo() + " and id.statusconfTablename ='"
                    + trimmedStatusconfTablename + "'");

            if ((old == null) || (old.size() == 0)) {
                stat.getId().setStatusconfTablename(trimmedStatusconfTablename);
                this.statusDAO.saveObject(stat);
                return true;
            }
            return false;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean delStat(Class<Statusconf> name, StatusconfPK id) {
        try {
            Statusconf oldstat = (Statusconf) this.statusDAO.loadObject(name, id, null,
                                                                        new boolean[0]);
            if (oldstat != null) {
                this.statusDAO.deleteObject(oldstat);
                return true;
            }
            return false;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean updateStatus(Statusconf stat) {
        Statusconf oldstat = (Statusconf) this.statusDAO.loadObject(Statusconf.class, stat.getId(),
                                                                    null, new boolean[0]);
        if (oldstat != null) {
            String trimmedStatusconfTablename = stat.getId().getStatusconfTablename().trim();
            stat.getId().setStatusconfTablename(trimmedStatusconfTablename);
            oldstat.setId(stat.getId());
            oldstat.setStatusconfDesc(stat.getStatusconfDesc());
            this.statusDAO.updateObject(oldstat);
            return true;
        }
        return false;
    }

    public Statusconf getStatusByName(String tableName, String statusName) {
        return this.statusDAO.getStatusByName(tableName, statusName);
    }

    public List<Statusconf> getAllTableStatus(String tableName) {
        return this.statusDAO.getStatusByTable(tableName);
    }

    public List<Statusconf> getStatusByConfigsAndTable(String tableName,
            List<Integer> statusConfigNos) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Statusconf.class);
        detachedCriteria
                .add(Restrictions.in(Statusconf.PROP_ID + ".statusconfNo", statusConfigNos));
        detachedCriteria.add(Restrictions
                .eq(Statusconf.PROP_ID + ".statusconfTablename", tableName));
        List result = this.statusDAO.findByCriteria(detachedCriteria);
        if (result.size() > 0)
            return result;
        return null;
    }

    public Statusconf getStatusByConfigAndTable(String tableName, int statusConfigNo) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Statusconf.class);
        detachedCriteria.add(Restrictions.eq(Statusconf.PROP_ID, new StatusconfPK(new Integer(
                statusConfigNo), tableName)));
        List result = this.statusDAO.findByCriteria(detachedCriteria);
        if (result.size() > 0)
            return (Statusconf) result.get(0);
        return null;
    }

    public IStatusDAO getStatusDAO() {
        return this.statusDAO;
    }

    public void setStatusDAO(IStatusDAO statusDAO) {
        this.statusDAO = statusDAO;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.bo.StatBOImpl JD-Core Version: 0.5.4
 */