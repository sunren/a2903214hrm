package com.hr.profile.domain;

import com.hr.configuration.domain.JobTitle;
import com.hr.profile.domain.base.BaseEmphistjobtitle;
import java.util.Date;

public class Emphistjobtitle extends BaseEmphistjobtitle {
    private static final long serialVersionUID = 1L;

    public Emphistjobtitle() {
    }

    public Emphistjobtitle(String id) {
        super(id);
    }

    public Emphistjobtitle(String id, JobTitle ehjtJobtitleNo, Employee ehjtEmpNo,
            Date ehjtValidFrom, Integer ehjtIsLatest) {
        super(id, ehjtJobtitleNo, ehjtEmpNo, ehjtValidFrom, ehjtIsLatest);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.domain.Emphistjobtitle JD-Core Version: 0.5.4
 */