package com.hr.exportinfo.bo.assist;

import com.hr.base.io.Container;
import com.hr.base.io.IIExportList;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.configuration.bo.IJobgradeBO;
import com.hr.configuration.domain.Jobgrade;
import com.hr.security.bo.impl.EmpDistinctNo;
import com.hr.spring.SpringBeanFactory;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ExportSalaryInit implements IIExportList {
    private List<Container> salaryInitList = new ArrayList();

    private Hashtable<String, String> escJobgradeTable = null;

    public ExportSalaryInit() {
        this.salaryInitList.add(new Container("员工号", "id", "id", true));
        this.salaryInitList.add(new Container("薪资级别", "escJobgrade", "EscJobgrade", true));
        this.salaryInitList.add(new Container("上次加薪生效日期", "escLastEffDate", "Date", false));
        this.salaryInitList.add(new Container("上次加薪幅度", "escLastIncrRate", "BigDecimal", false));
        this.salaryInitList.add(new Container("货币种类", "escCurrency", "Currency", true));
        this.salaryInitList.add(new Container("基本工资", "escBasesalary", "BigDecimal", false));
        this.salaryInitList.add(new Container("基本工资计算周期", "escBsPeriod", "Bsperiod", true));
        this.salaryInitList.add(new Container("固定补贴", "escAllowance", "BigDecimal", false));
        this.salaryInitList.add(new Container("固定奖金", "escFixbonus", "BigDecimal", false));
        this.salaryInitList.add(new Container("绩效奖金", "escVaribonus", "BigDecimal", false));
        this.salaryInitList.add(new Container("其它补贴", "escOtherallow", "BigDecimal", false));
        this.salaryInitList.add(new Container("福利标准", "escBenefitType", "EscBenefittype", true));
        this.salaryInitList.add(new Container("员工银行帐号", "escBankAccountNo", "StringD", false));
    }

    public List getList() {
        return this.salaryInitList;
    }

    public String getHeadName(int index) {
        return ((Container) this.salaryInitList.get(index)).getHeadName();
    }

    public String getName(Object obj, String idName) {
        Empsalaryconfig empsalaryconf = (Empsalaryconfig) obj;
        if (idName.equals("Currency"))
            return getCurrency(empsalaryconf);
        if (idName.equals("Bsperiod"))
            return getBsperiod(empsalaryconf);
        if (idName.equals("id"))
            return EmpDistinctNo.getEmpDistinctNo(empsalaryconf.getId());
        if (idName.equals("EscJobgrade"))
            return getEscJobgrade(empsalaryconf);
        if (idName.equals("EscBenefittype")) {
            return getEscBenefittype(empsalaryconf);
        }
        return null;
    }

    public String getPropertyName(int index) {
        return ((Container) this.salaryInitList.get(index)).getPropertyName();
    }

    public String getTypeName(int index) {
        return ((Container) this.salaryInitList.get(index)).getTypeName();
    }

    public boolean isNeedChangToName(int index) {
        return ((Container) this.salaryInitList.get(index)).isNeedChangToName();
    }

    public String getCurrency(Empsalaryconfig empsalaryconf) {
        return null;
    }

    public String getBsperiod(Empsalaryconfig empsalaryconf) {
        return null;
    }

    public String getEscJobgrade(Empsalaryconfig empsalaryconf) {
        if (this.escJobgradeTable == null) {
            this.escJobgradeTable = getEscJobgradeHashtable();
        }
        String empsalaryconfId = empsalaryconf.getEscJobgrade().getId().trim();
        String empsalaryconfString = (String) this.escJobgradeTable.get(empsalaryconfId);
        if (empsalaryconfString != null)
            return empsalaryconfString;
        return empsalaryconfId;
    }

    public String getEscBenefittype(Empsalaryconfig empsalaryconf) {
        return null;
    }

    private Hashtable<String, String> getEscJobgradeHashtable() {
        IJobgradeBO jobgradeBO = (IJobgradeBO) SpringBeanFactory.getBean("jobgradeBO");
        List jobgradeBOList = jobgradeBO.FindAllJobgrade();
        int jobgradeBOListSize = jobgradeBOList.size();
        Jobgrade jobgrade = null;
        Hashtable escJobgradeTable = new Hashtable();
        for (int i = 0; i < jobgradeBOListSize; ++i) {
            jobgrade = (Jobgrade) jobgradeBOList.get(i);
            escJobgradeTable.put(jobgrade.getId().trim(), jobgrade.getJobGradeName());
        }
        return escJobgradeTable;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.exportinfo.bo.assist.ExportSalaryInit JD-Core Version: 0.5.4
 */