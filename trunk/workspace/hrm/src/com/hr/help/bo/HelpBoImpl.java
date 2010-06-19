package com.hr.help.bo;

import com.hr.help.dao.IHelpDAO;
import com.hr.help.domain.Help;
import com.hr.help.domain.Helpclass;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class HelpBoImpl implements IHelpBo {
    private IHelpDAO helpDAO;

    public IHelpDAO getHelpDAO() {
        return this.helpDAO;
    }

    public void setHelpDAO(IHelpDAO helpDAO) {
        this.helpDAO = helpDAO;
    }

    public Object loadObject(Class clazz, Object id, String[] fetch) {
        return this.helpDAO.loadObject(clazz, id, fetch, new boolean[0]);
    }

    public boolean saveObject(Object obj) {
        this.helpDAO.saveObject(obj);
        return true;
    }

    public boolean updateObject(Object obj) {
        this.helpDAO.updateObject(obj);
        return true;
    }

    public boolean deleteObject(Object obj) {
        this.helpDAO.deleteObject(obj);
        return true;
    }

    public List getClassList() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Helpclass.class);
        detachedCriteria.addOrder(Order.asc(Helpclass.PROP_HC_FATHER));
        detachedCriteria.addOrder(Order.asc(Helpclass.PROP_HC_SORT_ID));
        List result = this.helpDAO.findByCriteria(detachedCriteria);
        return result;
    }

    public List getHelpByClass(String classId) {
        if (classId == null)
            return null;
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Help.class);
        detachedCriteria.add(Restrictions.eq("helpClass.id", classId));
        List result = this.helpDAO.findByCriteria(detachedCriteria);
        if ((result != null) && (result.size() > 0))
            return result;
        return null;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.help.bo.HelpBoImpl JD-Core Version: 0.5.4
 */