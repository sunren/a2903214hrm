package com.hr.security.domain;

import com.hr.security.domain.base.BaseBackup;

public class Backup extends BaseBackup {
    private static final long serialVersionUID = 1L;

    public Backup() {
    }

    public Backup(String id) {
        super(id);
    }

    public Backup(String id, String fileName, Integer fileSize, String fileCreatTime) {
        super(id, fileName, fileSize, fileCreatTime);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.domain.Backup JD-Core Version: 0.5.4
 */