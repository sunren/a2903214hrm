package com.hr.profile.domain;

public class SimpleEmployee {
    private String id;
    private String empDistinctNo;
    private String empName;
    private Integer empGender;
    private String empSupNo;

    public SimpleEmployee(String id, String empSupNo, String empDistinctNo, String empName,
            Integer empGender) {
        setId(id);
        setEmpDistinctNo(empDistinctNo);
        setEmpName(empName);
        setEmpGender(empGender);
        setEmpSupNo(empSupNo);
    }

    public String getEmpDistinctNo() {
        return this.empDistinctNo;
    }

    public void setEmpDistinctNo(String empDistinctNo) {
        this.empDistinctNo = empDistinctNo;
    }

    public Integer getEmpGender() {
        return this.empGender;
    }

    public void setEmpGender(Integer empGender) {
        this.empGender = empGender;
    }

    public String getEmpName() {
        return this.empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmpSupNo() {
        return this.empSupNo;
    }

    public void setEmpSupNo(String empSupNo) {
        this.empSupNo = empSupNo;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.domain.SimpleEmployee JD-Core Version: 0.5.4
 */