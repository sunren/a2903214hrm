package com.hr.compensation.action;

import com.hr.base.BaseAction;
import com.hr.base.Status;
import com.hr.compensation.bo.IEmpSalaryAcctitemsBo;
import com.hr.compensation.bo.ISalaryconfBo;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

public class ViewDetail extends BaseAction implements Status {
    private Employee empWithSalaryInfo;
    private String detailid;
    List<Empsalaryacctitems> acctItems;
    private String paramString;

    public String execute() throws Exception {
        this.paramString = "searchSalary";
        if (this.detailid == null) {
            this.detailid = getCurrentEmpNo();
            this.paramString = "mySalaryConf";
        }

        ISalaryconfBo salaryBo = (ISalaryconfBo) getBean("salaryconfBo");
        this.empWithSalaryInfo = salaryBo.findConfigByEmpId(this.detailid);
        if (this.empWithSalaryInfo == null) {
            addErrorInfo("对不起，您要查看的员工不存在＄1�7");
            return "error";
        }
        Empsalaryconfig salaryconfig = this.empWithSalaryInfo.getConfig();
        if (salaryconfig == null) {
            addErrorInfo("对不起，您要查看的员工没有配置薪资！");
            return "error";
        }

        salaryconfig.decryEmpSalaryConf(salaryconfig);
        IEmpSalaryAcctitemsBo empSalaryAcctitemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");

        this.acctItems = empSalaryAcctitemsBo.getItemsByAcctversion(salaryconfig.getEscEsavId()
                .getId());
        int size = this.acctItems.size();
        for (int i = 0; i < size; ++i) {
            Empsalaryacctitems item = (Empsalaryacctitems) this.acctItems.get(i);
            Class ownerClass = salaryconfig.getClass();
            Method method = ownerClass.getMethod("getEscColumn" + (i + 1), new Class[0]);
            Object object = method.invoke(salaryconfig, new Object[0]);
            item.setItemValue((BigDecimal) object);
        }
        return "success";
    }

    public String getDetailid() {
        return this.detailid;
    }

    public void setDetailid(String detailid) {
        this.detailid = detailid;
    }

    public Employee getEmpWithSalaryInfo() {
        return this.empWithSalaryInfo;
    }

    public void setEmpWithSalaryInfo(Employee empWithSalaryInfo) {
        this.empWithSalaryInfo = empWithSalaryInfo;
    }

    public List<Empsalaryacctitems> getAcctItems() {
        return this.acctItems;
    }

    public void setAcctItems(List<Empsalaryacctitems> acctItems) {
        this.acctItems = acctItems;
    }

    public String getParamString() {
        return this.paramString;
    }

    public void setParamString(String paramString) {
        this.paramString = paramString;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.action.ViewDetail JD-Core Version: 0.5.4
 */