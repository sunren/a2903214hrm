package com.hr.exportinfo.bo.assist;

import com.hr.base.io.Container;
import com.hr.base.io.IIExportList;
import java.util.ArrayList;
import java.util.List;

public class ExportSalaryPaidInit implements IIExportList {
    private List<Container> salaryPaidInitList = new ArrayList();

    public ExportSalaryPaidInit() {
        this.salaryPaidInitList.add(new Container("员工叄1�7", "espEmpno", "String", false));
        this.salaryPaidInitList.add(new Container("员工姓名", "empName", "String", false));
        this.salaryPaidInitList.add(new Container("银行帐号", "escBankAccountNo", "StringD", false));
        this.salaryPaidInitList.add(new Container("税后收入", "espNetamt", "BigDecimal", false));
    }

    public String getHeadName(int idex) {
        return ((Container) this.salaryPaidInitList.get(idex)).getHeadName();
    }

    public List getList() {
        return this.salaryPaidInitList;
    }

    public String getName(Object obj, String idName) {
        return null;
    }

    public String getPropertyName(int idex) {
        return ((Container) this.salaryPaidInitList.get(idex)).getPropertyName();
    }

    public String getTypeName(int idex) {
        return ((Container) this.salaryPaidInitList.get(idex)).getTypeName();
    }

    public boolean isNeedChangToName(int idex) {
        return false;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.exportinfo.bo.assist.ExportSalaryPaidInit JD-Core Version: 0.5.4
 */