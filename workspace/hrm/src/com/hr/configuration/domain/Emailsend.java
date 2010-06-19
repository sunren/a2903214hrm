package com.hr.configuration.domain;

import com.hr.configuration.domain.base.BaseEmailsend;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Emailsend extends BaseEmailsend {
    private static final long serialVersionUID = 1L;
    private String empName;

    public Emailsend() {
    }

    public Emailsend(String id) {
        super(id);
    }

    public Emailsend(String id, String esTo, String esTitle, String esContent, Integer esStatus,
            Date esCreatetime) {
        super(id, esTo, esTitle, esContent, esStatus, esCreatetime);
    }

    public String getEmpName() {
        return this.empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.domain.Emailsend JD-Core Version: 0.5.4
 */