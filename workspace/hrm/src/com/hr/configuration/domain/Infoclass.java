package com.hr.configuration.domain;

import com.hr.configuration.domain.base.BaseInfoclass;

public class Infoclass extends BaseInfoclass {
    private static final long serialVersionUID = 1L;

    public Infoclass() {
    }

    public Infoclass(String id) {
        super(id);
    }

    public Infoclass(String id, String infoclassName, Integer infoclassSortId,
            Integer infoclassStatus) {
        super(id, infoclassName, infoclassSortId, infoclassStatus);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.domain.Infoclass JD-Core Version: 0.5.4
 */