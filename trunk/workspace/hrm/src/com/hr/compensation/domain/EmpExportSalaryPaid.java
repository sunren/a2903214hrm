package com.hr.compensation.domain;

import com.hr.base.BaseDomain;
import java.math.BigDecimal;

public class EmpExportSalaryPaid extends BaseDomain {
    String empId;
    String espEmpno;
    String empName;
    String escBankAccountNo;
    BigDecimal espNetamt;

    public EmpExportSalaryPaid() {
        this.empId = null;
        this.espEmpno = null;
        this.empName = null;
        this.escBankAccountNo = null;
        this.espNetamt = null;
    }

    public String getEmpName() {
        return this.empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEscBankAccountNo() {
        return this.escBankAccountNo;
    }

    public void setEscBankAccountNo(String escBankAccountNo) {
        this.escBankAccountNo = escBankAccountNo;
    }

    public String getEspEmpno() {
        return this.espEmpno;
    }

    public void setEspEmpno(String espEmpno) {
        this.espEmpno = espEmpno;
    }

    public BigDecimal getEspNetamt() {
        return this.espNetamt;
    }

    public void setEspNetamt(BigDecimal espNetamt) {
        this.espNetamt = espNetamt;
    }

    public String getEmpId() {
        return this.empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.domain.EmpExportSalaryPaid JD-Core Version: 0.5.4
 */