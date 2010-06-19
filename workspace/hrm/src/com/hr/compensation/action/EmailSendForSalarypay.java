package com.hr.compensation.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.compensation.bo.IEmpsalaryperiodBo;
import com.hr.compensation.bo.ISalaryPaidBo;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.compensation.domain.Empsalaryperiod;
import com.hr.configuration.bo.IEmailsendBO;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import java.util.ArrayList;
import java.util.List;

public class EmailSendForSalarypay extends BaseAction {
    private static final long serialVersionUID = 1L;

    public List<String> sendEmail(String[] emps, String year, Integer month) {
        List err = new ArrayList();
        String flt = DWRUtil.checkAuth("salarypayEmailSend", "sendEmail");
        if ("error".equalsIgnoreCase(flt)) {
            err.add("您无权发送邮件！");
            return err;
        }

        String strMonth = "0" + month;
        IEmpsalaryperiodBo salaryperiod = (IEmpsalaryperiodBo) SpringBeanFactory
                .getBean("empsalaryperiodBO");
        Empsalaryperiod period = salaryperiod.loadEspdStatus(year + strMonth);
        if ((period == null) || (period.getEspdStatus().intValue() != 2)) {
            err.add(year + "幄1�7" + month + "月薪资未审核完毕，不能发送薪资邮件！");
            return err;
        }

        ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) getBean("salaryPaidBo");
        List<Empsalarypay> result = salaryPaidBo
                .getDecriedSalaryPaidForEmailSend(emps, year, month);

        List noPayEmp = new ArrayList();
        for (int i = 0; i < emps.length; ++i) {
            boolean flag = false;
            for (Empsalarypay pay : result) {
                if (emps[i].equals(pay.getEspEmpno().getId())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                noPayEmp.add(emps[i]);
            }
        }
        if (!noPayEmp.isEmpty()) {
            IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
            int size = noPayEmp.size();
            String[] arr = new String[size];
            for (int i = 0; i < size; ++i) {
                arr[i] = ((String) noPayEmp.get(i));
            }
            List<Employee> employees = empBo.searchEmpArray(arr);
            String er = "";
            for (Employee e : employees) {
                er = er + e.getEmpName() + "＄1�7";
            }
            err.add(er.substring(0, er.length() - 1) + "当月无薪资数据！");
        }

        List emailList = salaryPaidBo.getEmailList(result, getCurrentEmp().getId());
        IEmailsendBO emailsendBO = (IEmailsendBO) SpringBeanFactory.getBean("emailsendBO");
        List errors = emailsendBO.sendMail(emailList);
        errors.addAll(err);
        return errors;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.action.EmailSendForSalarypay JD-Core Version: 0.5.4
 */