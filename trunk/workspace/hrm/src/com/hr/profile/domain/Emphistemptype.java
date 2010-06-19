package com.hr.profile.domain;

import com.hr.configuration.domain.Emptype;
import com.hr.profile.domain.base.BaseEmphistemptype;
import java.util.Date;

public class Emphistemptype extends BaseEmphistemptype {
    private static final long serialVersionUID = 1L;

    public Emphistemptype() {
    }

    public Emphistemptype(String id) {
        super(id);
    }

    public Emphistemptype(String id, Emptype ehetEmptypeNo, Employee ehetEmpNo, Date ehetValidFrom,
            Integer ehetIsLatest) {
        super(id, ehetEmptypeNo, ehetEmpNo, ehetValidFrom, ehetIsLatest);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.domain.Emphistemptype JD-Core Version: 0.5.4
 */