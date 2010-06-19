package com.hr.base.bo;

import com.hr.base.Constants;
import com.hr.profile.dao.IEmployeeDAO;
import com.hr.profile.domain.Employee;
import com.hr.training.domain.Trcourse;
import com.hr.util.GenericValidator;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class BaseBoImpl implements IBaseBo, Constants {
    private IEmployeeDAO employeeDAO;

    public IEmployeeDAO getEmployeeDAO() {
        return this.employeeDAO;
    }

    public void setEmployeeDAO(IEmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public String getNoByPrefix(String domainName, String pre, String colName) {
        pre = pre.toUpperCase();
        List result = this.employeeDAO.exeHqlList("from " + domainName + " where " + colName
                + " like '" + pre + "%' order by " + colName + " desc");
        if (result.size() > 0) {
            String no = null;
            if (domainName.equalsIgnoreCase("Employee"))
                no = ((Employee) result.get(0)).getEmpDistinctNo();
            else if (domainName.equalsIgnoreCase("Trcourse")) {
                no = ((Trcourse) result.get(0)).getTrcNo();
            }
            return getNewNo(no, pre);
        }
        if (GenericValidator.isNumber(pre))
            return pre;
        return pre.toUpperCase() + "0001";
    }

    public String getNewNo(String no, String pre) {
        try {
            if (GenericValidator.isNumber(no)) {
                DecimalFormat allNumbers = new DecimalFormat("###########");
                return allNumbers.format(new BigDecimal(no).add(new BigDecimal(1)));
            }
            DecimalFormat decimalFormat = new DecimalFormat("0000");
            String temp = no.replaceAll(pre, "");
            if (GenericValidator.isNumber(temp)) {
                return pre + decimalFormat.format(Double.valueOf(temp).doubleValue() + 1.0D);
            }
            StringBuffer fix = new StringBuffer();
            StringBuffer num = new StringBuffer();
            for (int len = 0; len < no.length(); ++len) {
                String str = no.substring(len, len + 1);
                if (GenericValidator.isNumber(str))
                    num.append(str);
                else {
                    fix.append(str);
                }
            }
            if (num.length() > 0) {
                return decimalFormat.format(Double.valueOf(num.toString()).doubleValue() + 1.0D);
            }
            return fix.toString() + "0001";
        } catch (Exception e) {
        }
        return pre.toUpperCase() + "0001";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.base.bo.BaseBoImpl JD-Core Version: 0.5.4
 */