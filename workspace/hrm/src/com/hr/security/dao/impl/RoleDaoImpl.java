package com.hr.security.dao.impl;

import com.hr.hibernate.HibernateUtil;
import com.hr.security.dao.RoleDao;
import com.hr.security.domain.Role;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class RoleDaoImpl extends HibernateUtil implements RoleDao {
    public Role findRoleByName(String roleName) {
        Session session = getSession();
        return (Role) session.createCriteria(Role.class).add(Restrictions.eq("roleName", roleName))
                .uniqueResult();
    }

    public boolean findRoleBySortid(int sortId) {
        Session session = getSession();
        Role r = (Role) session.createCriteria(Role.class).add(
                                                               Restrictions
                                                                       .eq("roleSortId", Integer
                                                                               .valueOf(sortId)))
                .uniqueResult();
        return r != null;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.dao.impl.RoleDaoImpl JD-Core Version: 0.5.4
 */