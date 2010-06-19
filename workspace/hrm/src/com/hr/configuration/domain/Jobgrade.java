package com.hr.configuration.domain;

import com.hr.configuration.domain.base.BaseJobgrade;
import java.math.BigDecimal;

public class Jobgrade extends BaseJobgrade {
    private static final long serialVersionUID = 1L;

    public Jobgrade() {
    }

    public Jobgrade(String id) {
        super(id);
    }

    public Jobgrade(String id, String jobGradeNo, Integer jobGradeLevel, BigDecimal jobGradeMrp,
            String jobGradeName) {
        super(id, jobGradeNo, jobGradeLevel, jobGradeMrp, jobGradeName);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.domain.Jobgrade JD-Core Version: 0.5.4
 */