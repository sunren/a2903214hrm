package com.hr.configuration.bo;

import com.hr.configuration.dao.IInfoDAO;
import com.hr.configuration.domain.Infoclass;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

public class InfoBOImpl implements IInfoBO {
    private IInfoDAO dao;

    public InfoBOImpl() {
        this.dao = null;
    }

    public List FindAllInfo() {
        DetachedCriteria dc = DetachedCriteria.forClass(Infoclass.class);
        dc.addOrder(Order.asc("infoclassSortId"));
        return this.dao.findByCriteria(dc);
    }

    public boolean addInfo(Infoclass info) {
        try {
            String trimmedInfoclassName = info.getInfoclassName().trim();

            List old = this.dao.exeHqlList(" from Infoclass  where  infoclassName = '"
                    + trimmedInfoclassName + "'");

            if ((old == null) || (old.size() == 0)) {
                info.setInfoclassName(trimmedInfoclassName);
                this.dao.saveObject(info);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delInfo(Class<Infoclass> name, String id) {
        try {
            List check = new ArrayList();
            boolean isRefed = false;
            check = this.dao.exeHqlList("select count(*) from Information where infoClass = '" + id
                    + "'");
            if (Integer.valueOf(check.get(0).toString()).intValue() > 0) {
                isRefed = true;
            }
            Infoclass old = (Infoclass) this.dao.loadObject(name, id, null, new boolean[0]);
            if ((!isRefed) && (old != null)) {
                this.dao.deleteObject(old);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateInfo(Infoclass info) {
        Infoclass old = (Infoclass) this.dao.loadObject(Infoclass.class, info.getId(), null,
                                                        new boolean[0]);
        if (old != null) {
            String trimmedInfoclassName = info.getInfoclassName().trim();
            old.setInfoclassName(trimmedInfoclassName);
            old.setInfoclassStatus(info.getInfoclassStatus());
            this.dao.updateObject(old);
            return true;
        }
        return false;
    }

    public void saveInforclassIdByBatch(String[] ids) {
        int length = ids.length;
        int sortId = 1;
        for (int i = 0; i < length; ++i) {
            this.dao.exeHql("update Infoclass set infoclassSortId=" + sortId + " where id ='"
                    + ids[i] + "'");
            ++sortId;
        }
    }

    public IInfoDAO getDao() {
        return this.dao;
    }

    public void setDao(IInfoDAO dao) {
        this.dao = dao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.bo.InfoBOImpl JD-Core Version: 0.5.4
 */