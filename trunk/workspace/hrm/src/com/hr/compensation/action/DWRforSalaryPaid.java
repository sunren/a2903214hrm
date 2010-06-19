package com.hr.compensation.action;

import com.hr.base.DWRUtil;
import com.hr.base.Status;
import com.hr.compensation.bo.IEmpSalaryAcctversionBo;
import com.hr.compensation.bo.ISalaryPaidBo;
import com.hr.compensation.domain.Empsalaryacct;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.profile.domain.Employee;
import com.hr.util.StringUtil;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;

public class DWRforSalaryPaid extends CompAction implements Status {
    private static final long serialVersionUID = -4234940898871087425L;

    public String deleteSalaryPaid(String payId, HttpSession session) {
        String flt = DWRUtil.checkAuth("searchSalaryPaid", "execute");
        if ("error".equalsIgnoreCase(flt))
            return StringUtil.message(this.msgNoAuth, new Object[] { "noauth" });
        ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) getBean("salaryPaidBo");
        Empsalarypay empSalaryPaid = salaryPaidBo.loadSalaryPay(payId, new String[0]);

        if (empSalaryPaid == null) {
            return StringUtil.message(this.msgNoPay, new Object[] { "nopay" });
        }

        if (!salaryPaidBo.deleteSalaryPaid(empSalaryPaid)) {
            return StringUtil.message(this.msgOperErr, new Object[] { "fail", "薪资发放删除" });
        }

        return StringUtil.message(this.msgOperSucc, new Object[] { "SUCC", "薪资发放删除" });
    }

    public String updatePayOne(String salaryValue, String payId, String empId, String comment,
            String yearmonth) throws Exception {
        if ("error".equalsIgnoreCase(DWRUtil.checkAuth("searchSalaryPaid", "execute")))
            return StringUtil.message(this.msgNoAuth, new Object[] { "noauth" });
        if (StringUtils.isEmpty(salaryValue))
            return StringUtil.message(this.msgNoPay, new Object[] { "nopay" });
        if (StringUtils.isEmpty(empId))
            return StringUtil.message(this.msgNoEmp, new Object[] { "noemp" });
        if (StringUtils.isEmpty(yearmonth))
            return StringUtil.message(this.msgNoYm, new Object[] { "noym" });
        if (!checkSalaryPeriod(yearmonth, new Integer[] { Integer.valueOf(0) })) {
            return StringUtil.message(this.msgPeriodInitErr, new Object[0]);
        }

        Empsalarypay pay = loadPay(payId, empId, yearmonth);

        setPayAdd(yearmonth, new Empsalarypay[] { pay });

        setPayValues(salaryValue, pay);

        interpretPay(yearmonth, getCurrentEmpNo(), false, new Empsalarypay[] { pay });

        if (pay == null)
            return StringUtil.message(this.msgNoPay, new Object[] { "null" });

        if (!StringUtils.isEmpty(comment)) {
            pay.setEspComment(comment.trim());
            pay.setEspChanged(Integer.valueOf(1));
        }

        Employee emp = pay.getEspEmpno();
        Empsalaryconfig config = emp.getConfig();
        pay.setEspEmpno(new Employee(emp.getId()));
        pay.setEspEmpconfig(new Empsalaryconfig(config.getId()));

        ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) getBean("salaryPaidBo");
        Empsalarypay payForPage = salaryPaidBo.calSalaryPayforPage(pay, pay.getAcctItems());
        StringBuffer strColumn = new StringBuffer("SUCC:");
        strColumn.append(payForPage.getShowColumn1()).append(",");
        strColumn.append(payForPage.getShowColumn4()).append(",");
        strColumn.append(payForPage.getShowColumn7()).append(",");
        strColumn.append(payForPage.getShowColumn8()).append(",");
        strColumn.append(payForPage.getShowColumn15()).append(",");
        strColumn.append(payForPage.getShowColumn18()).append(",");
        strColumn.append(payForPage.getShowColumn19()).append(",");

        salaryPaidBo.savePayAndInitEbp(pay);

        strColumn.append(pay.getEspChanged()).append(",");
        strColumn.append(pay.getId()).append(",");
        strColumn.append(pay.getEspBenefitPlans());

        return strColumn.toString();
    }

    public List<Empsalaryacctitems> initCalcPay(String payId, String empId, String yearmonth) {
        if ("error".equalsIgnoreCase(DWRUtil.checkAuth("mySalaryPaid", "execute")))
            return null;
        if ((StringUtils.isEmpty(payId)) && (StringUtils.isEmpty(empId)))
            return null;
        if (StringUtils.isEmpty(yearmonth)) {
            return null;
        }
        Empsalarypay pay = loadPay(payId, empId, yearmonth);

        if (pay.getId() == null)
            if (checkSalaryPeriod(yearmonth,
                                  new Integer[] { Integer.valueOf(1), Integer.valueOf(2) })) {
                return null;
            }

        setPayAdd(yearmonth, new Empsalarypay[] { pay });

        if (pay.getId() == null) {
            interpretPay(yearmonth, getCurrentEmpNo(), true, new Empsalarypay[] { pay });
        }

        return getPayItemsPage(pay);
    }

    public List<Empsalaryacctitems> reCalcPayOne(String salaryValue, String espId, String empId,
            String yearmonth) {
        if ("error".equalsIgnoreCase(DWRUtil.checkAuth("searchSalaryPaid", "execute")))
            return null;
        if (StringUtils.isEmpty(salaryValue))
            return null;
        if (StringUtils.isEmpty(empId))
            return null;
        if (StringUtils.isEmpty(yearmonth))
            return null;
        if (!checkSalaryPeriod(yearmonth, new Integer[] { Integer.valueOf(0) })) {
            return null;
        }

        Empsalarypay pay = loadPay(espId, empId, yearmonth);

        setPayAdd(yearmonth, new Empsalarypay[] { pay });

        setPayValues(salaryValue, pay);

        interpretPay(yearmonth, getCurrentEmpNo(), false, new Empsalarypay[] { pay });

        return getPayItemsPage(pay);
    }

    public String[][] getBeneItemsByPay(String payId) {
        ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) getBean("salaryPaidBo");
        String[][] result = salaryPaidBo.getBeneItemsByPay(payId);
        return result;
    }

    public String getEsacNameByVersionId(String espEsavId) {
        try {
            IEmpSalaryAcctversionBo acctVersionBo = (IEmpSalaryAcctversionBo) getBean("empsalaryacctversionBo");
            Empsalaryacctversion version = acctVersionBo.loadObject(espEsavId,
                                                                    new String[] { "esavEsac" });
            if (version == null) {
                return null;
            }
            return version.getEsavEsac().getEsacName();
        } catch (Exception e) {
        }
        return "";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.action.DWRforSalaryPaid JD-Core Version: 0.5.4
 */