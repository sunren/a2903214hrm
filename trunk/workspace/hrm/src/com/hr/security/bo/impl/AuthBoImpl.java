package com.hr.security.bo.impl;

import com.hr.security.bo.AuthBo;
import com.hr.security.dao.AuthDao;
import com.hr.security.domain.Authority;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class AuthBoImpl implements AuthBo {
    private AuthDao authDao;

    public List getAuthList() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Authority.class);
        detachedCriteria.addOrder(Order.asc("authorityModuleNo"));
        detachedCriteria.addOrder(Order.desc("authorityConditionNo"));
        return this.authDao.findByCriteria(detachedCriteria);
    }

    public Authority findAuthById(int id) {
        return (Authority) this.authDao.loadObject(Authority.class, Integer.valueOf(id), null,
                                                   new boolean[0]);
    }

    public boolean updateAuth(Authority authority, Integer[] actionIds) {
        StringBuffer actionId = new StringBuffer();

        if ((actionIds != null) && (actionIds.length > 0)) {
            for (int i = 0; i < actionIds.length; ++i) {
                actionId.append(actionIds[i].toString());
                if (i == actionIds.length - 1) {
                    break;
                }
                actionId.append(",");
            }
        }

        authority.setAuthorityActionNo(actionId.toString());
        try {
            this.authDao.updateObject(authority);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Authority> findAuthByIdList(List idList) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Authority.class);
        detachedCriteria.add(Restrictions.in("id", idList));
        List findByCriteria = this.authDao.findByCriteria(detachedCriteria);
        return findByCriteria;
    }

    public Authority getAuthorityId(String moduleNo, String conditionNo) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Authority.class);
        if ((moduleNo != null) && (!moduleNo.trim().equals(""))) {
            detachedCriteria.add(Restrictions.eq(Authority.PROP_AUTHORITY_MODULE_NO, moduleNo));
        }
        if ((conditionNo != null) && (!conditionNo.trim().equals(""))) {
            Integer con = new Integer(conditionNo);
            detachedCriteria.add(Restrictions.eq(Authority.PROP_AUTHORITY_CONDITION_NO, con));
        }
        List findByCriteria = this.authDao.findByCriteria(detachedCriteria);
        if ((findByCriteria == null) || (findByCriteria.size() < 1)) {
            return null;
        }
        return (Authority) findByCriteria.get(0);
    }

    public AuthDao getAuthDao() {
        return this.authDao;
    }

    public void setAuthDao(AuthDao authDao) {
        this.authDao = authDao;
    }

    public List<Authority> getAuthList(String modulePrifex) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Authority.class);
        detachedCriteria.add(Restrictions.like("authorityModuleNo", modulePrifex, MatchMode.START));
        return this.authDao.findByCriteria(detachedCriteria);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.bo.impl.AuthBoImpl JD-Core Version: 0.5.4
 */