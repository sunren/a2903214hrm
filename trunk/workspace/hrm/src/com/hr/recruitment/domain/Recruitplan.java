package com.hr.recruitment.domain;

import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.JobTitle;
import com.hr.configuration.domain.Location;
import com.hr.profile.domain.Employee;
import com.hr.recruitment.domain.base.BaseRecruitplan;
import java.util.Date;

public class Recruitplan extends BaseRecruitplan {
    private static final long serialVersionUID = 1L;
    private String[] depts;

    public Recruitplan() {
    }

    public Recruitplan(String id) {
        super(id);
    }

    public Recruitplan(String id, Location recpWorkLocation, Employee recpLastChangeBy,
            Department recpDepartmentNo, Employee recpCreateBy, Emptype recpType, Integer recpNo,
            JobTitle recpJobTitle, Date recpStartDate, Integer recpNumberExpect,
            Integer recpStatus, Date recpCreateTime, Date recpLastChangeTime) {
        super(id, recpWorkLocation, recpLastChangeBy, recpDepartmentNo, recpCreateBy, recpType,
                recpNo, recpJobTitle, recpStartDate, recpNumberExpect, recpStatus, recpCreateTime,
                recpLastChangeTime);
    }

    public String[] getDepts() {
        return this.depts;
    }

    public void setDepts(String[] depts) {
        this.depts = depts;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.recruitment.domain.Recruitplan JD-Core Version: 0.5.4
 */