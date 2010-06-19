package com.hr.recruitment.domain;

import com.hr.recruitment.domain.base.BaseRecruitchannel;

public class Recruitchannel extends BaseRecruitchannel {
    private static final long serialVersionUID = 1L;

    public Recruitchannel() {
    }

    public Recruitchannel(String id) {
        super(id);
    }

    public Recruitchannel(String id, String recchName, String recchRespPerson) {
        super(id, recchName, recchRespPerson);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.recruitment.domain.Recruitchannel JD-Core Version: 0.5.4
 */