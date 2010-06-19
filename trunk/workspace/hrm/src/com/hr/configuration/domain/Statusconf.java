package com.hr.configuration.domain;

import com.hr.configuration.domain.base.BaseStatusconf;
import java.util.List;

public class Statusconf extends BaseStatusconf {
    private static final long serialVersionUID = 1L;

    public Statusconf() {
    }

    public Statusconf(StatusconfPK id) {
        super(id);
    }

    public static String getEcpStatus(int statusNo, List<Statusconf> status) {
        for (Statusconf stconf : status) {
            if (stconf.getId().getStatusconfNo().intValue() == statusNo) {
                return stconf.getStatusconfDesc();
            }
        }

        return "";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.domain.Statusconf JD-Core Version: 0.5.4
 */