package com.hr.security.domain;

import com.hr.security.domain.base.BaseClient;

public class Client extends BaseClient {
    private static final long serialVersionUID = 1L;

    public Client() {
    }

    public Client(String id) {
        super(id);
    }

    public Client(String id, String clientLimit, String clientRemarks) {
        super(id, clientLimit, clientRemarks);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.domain.Client JD-Core Version: 0.5.4
 */