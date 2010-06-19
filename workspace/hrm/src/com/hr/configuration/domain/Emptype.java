package com.hr.configuration.domain;

import com.hr.configuration.domain.base.BaseEmptype;

public class Emptype extends BaseEmptype {
    private static final long serialVersionUID = 1L;

    public Emptype() {
    }

    public Emptype(String id) {
        super(id);
    }

    public Emptype(String id, String emptypeName, Integer emptypeSortId) {
        super(id, emptypeName, emptypeSortId);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.domain.Emptype JD-Core Version: 0.5.4
 */