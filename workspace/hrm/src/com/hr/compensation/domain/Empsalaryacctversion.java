package com.hr.compensation.domain;

import com.hr.compensation.domain.base.BaseEmpsalaryacctversion;
import java.util.Date;

public class Empsalaryacctversion extends BaseEmpsalaryacctversion {
    private static final long serialVersionUID = 1L;

    public Empsalaryacctversion() {
    }

    public Empsalaryacctversion(String id) {
        super(id);
    }

    public Empsalaryacctversion(String id, Empsalaryacct esavEsac, Date esavValidFrom,
            String esavCreateBy, Date esavCreateTime, String esavLastChangeBy,
            Date esavLastChangeTime) {
        super(id, esavEsac, esavValidFrom, esavCreateBy, esavCreateTime, esavLastChangeBy,
                esavLastChangeTime);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.domain.Empsalaryacctversion JD-Core Version: 0.5.4
 */