package com.hr.security.dao;

import com.hr.hibernate.IHibernateUtil;
import com.hr.security.domain.Role;

public abstract interface RoleDao extends IHibernateUtil {
    public abstract Role findRoleByName(String paramString);

    public abstract boolean findRoleBySortid(int paramInt);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.dao.RoleDao JD-Core Version: 0.5.4
 */