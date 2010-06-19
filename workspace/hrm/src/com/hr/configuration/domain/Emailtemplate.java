package com.hr.configuration.domain;

import com.hr.configuration.domain.base.BaseEmailtemplate;

public class Emailtemplate extends BaseEmailtemplate {
    private static final long serialVersionUID = 1L;

    public Emailtemplate() {
    }

    public Emailtemplate(String id) {
        super(id);
    }

    public Emailtemplate(String id, String etTitleNo, String etTitle, String etContent,
            Integer etStatus, Integer etSendMode) {
        super(id, etTitleNo, etTitle, etContent, etStatus, etSendMode);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.domain.Emailtemplate JD-Core Version: 0.5.4
 */