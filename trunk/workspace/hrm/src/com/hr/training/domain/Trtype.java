package com.hr.training.domain;

import com.hr.training.domain.base.BaseTrtype;

public class Trtype extends BaseTrtype {
    private static final long serialVersionUID = 1L;

    public Trtype() {
    }

    public Trtype(String trtNo) {
        super(trtNo);
    }

    public Trtype(String trtNo, String trtName) {
        super(trtNo, trtName);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.training.domain.Trtype JD-Core Version: 0.5.4
 */