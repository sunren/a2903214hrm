package com.hr.profile.domain;

import com.hr.profile.domain.base.BasePosition;

public class Position extends BasePosition {
    private static final long serialVersionUID = 1L;

    public Position() {
    }

    public Position(String id) {
        super(id);
    }

    public Position(String id, String clientNo) {
        super(id, clientNo);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.domain.Position JD-Core Version: 0.5.4
 */