package com.hr.profile.domain;

import com.hr.profile.domain.base.BaseOuperfcriteria;

public class Ouperfcriteria extends BaseOuperfcriteria {
    private static final long serialVersionUID = 1L;

    public Ouperfcriteria() {
    }

    public Ouperfcriteria(String id) {
        super(id);
    }

    public Ouperfcriteria(String id, String oupName, Integer oupRate, Integer oupSortId) {
        super(id, oupName, oupRate, oupSortId);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.domain.Ouperfcriteria JD-Core Version: 0.5.4
 */