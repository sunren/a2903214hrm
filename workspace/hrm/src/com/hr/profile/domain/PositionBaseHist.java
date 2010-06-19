package com.hr.profile.domain;

import com.hr.configuration.domain.Department;
import com.hr.profile.domain.base.BasePositionBaseHist;
import java.util.Date;

public class PositionBaseHist extends BasePositionBaseHist {
    private static final long serialVersionUID = 1L;

    public PositionBaseHist() {
    }

    public PositionBaseHist(String id) {
        super(id);
    }

    public PositionBaseHist(String id, PositionBase pbhiPbId, Integer pbhiPbStatus,
            Date pbhiValidFrom) {
        super(id, pbhiPbId, pbhiPbStatus, pbhiValidFrom);
    }

    public PositionBaseHist(String id, PositionBase pbhiPbId, Department pbhiDeptId,
            Integer status, Integer pbhiMaxEmployee, Integer pbhiInCharge, Date pbhiValidFrom,
            Date pbhiValidTo) {
        super(id, pbhiPbId, pbhiDeptId, status, pbhiMaxEmployee, pbhiInCharge, pbhiValidFrom,
                pbhiValidTo);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.domain.PositionBaseHist JD-Core Version: 0.5.4
 */