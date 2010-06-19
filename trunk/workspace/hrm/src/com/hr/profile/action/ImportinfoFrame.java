package com.hr.profile.action;

import com.hr.base.BaseAction;

public class ImportinfoFrame extends BaseAction {
    private String postListName;
    private static final long serialVersionUID = 1L;

    public ImportinfoFrame() {
        this.postListName = "Employee";
    }

    public String execute() throws Exception {
        return "success";
    }

    public String getPostListName() {
        return this.postListName;
    }

    public void setPostListName(String postListName) {
        this.postListName = postListName;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.ImportinfoFrame JD-Core Version: 0.5.4
 */