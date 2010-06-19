package com.hr.security.domain;

import com.hr.security.domain.base.BaseRole;

public class Role extends BaseRole {
    private static final long serialVersionUID = 1L;

    public Role() {
    }

    public Role(String id) {
        super(id);
    }

    public Role(String id, String roleName, Integer roleSortId, String roleAuthority) {
        super(id, roleName, roleSortId, roleAuthority);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.domain.Role JD-Core Version: 0.5.4
 */