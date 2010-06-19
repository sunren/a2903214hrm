package com.hr.help.domain;

import com.hr.help.domain.base.BaseHelp;

public class Help extends BaseHelp {
    private static final long serialVersionUID = 1L;

    public Help() {
    }

    public Help(String id) {
        super(id);
    }

    public Help(String id, Helpclass helpClass, String helpDesc, String helpTitle) {
        super(id, helpClass, helpDesc, helpTitle);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.help.domain.Help JD-Core Version: 0.5.4
 */