package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.configuration.domain.Department;
import com.hr.examin.bo.LrDataCheckImpl;
import com.hr.examin.bo.interfaces.IAttendshiftBO;
import com.hr.examin.bo.interfaces.ILeavebalanceBO;
import com.hr.examin.bo.interfaces.ILeaverequestBO;
import com.hr.examin.bo.interfaces.ILeavetypeBO;
import com.hr.examin.domain.Attendshift;
import com.hr.examin.domain.Leavebalance;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Leavetype;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.MyTools;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

public class MyAttendance extends BaseAction {
    private Leavebalance leaveBalance;
    private Employee emp;
    private List<Leavetype> allLeaveType;
    private List leaveRequestList;
    private BigDecimal lbLastYear;
    private BigDecimal lbrLastYear;
    private BigDecimal lbCurrYear;
    private BigDecimal lbrCurrYear;
    private static final int[] STATUS_SET = { 15, 16 };

    private static final BigDecimal ZERO = new BigDecimal(0);

    public MyAttendance() {
        this.leaveRequestList = new ArrayList();
    }

    public String execute() throws Exception {
        String empNo = (String) getSession().getAttribute("curr_oper_no");
        Userinfo userTemp = (Userinfo) getSession().getAttribute("userinfo");
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        ILeavebalanceBO lb_BO = (ILeavebalanceBO) getBean("leavebalanceBO");

        if ((empNo == null) || (empNo.length() == 0)) {
            empNo = getCurrentEmpNo();
        }
        String[] fetch = { "empType", "empDeptNo" };
        this.emp = empBo.loadEmp(empNo, fetch);
        if (this.emp == null) {
            return "input";
        }

        if ((!hasAuth(401)) && (!hasAuthModuleCondition(411, 3))) {
            if (!hasAuthModuleCondition(411, 2)) {
                if (!hasAuthModuleCondition(411, 1)) {
                    return "noauth";
                }
                if (!this.emp.getId().equals(userTemp.getId())) {
                    return "noauth";
                }

            } else if (!this.emp.getEmpDeptNo().getId().equals(
                                                               userTemp.getEmployee()
                                                                       .getEmpDeptNo().getId())) {
                return "noauth";
            }

        }

        ILeavetypeBO lt_BO = (ILeavetypeBO) SpringBeanFactory.getBean("leavetypeBO");
        this.allLeaveType = lt_BO.FindAllLeaveType();

        Calendar c = Calendar.getInstance();
        String thisYear = String.valueOf(c.get(1));

        Leavetype annualLt = lt_BO.getAnnualLeave();
        this.leaveBalance = lb_BO.getLeavebalance(this.emp, thisYear, annualLt);

        if (this.leaveBalance == null) {
            this.lbLastYear = ZERO;
            this.lbrLastYear = ZERO;
            this.lbCurrYear = ZERO;
            this.lbrCurrYear = ZERO;
            return "success";
        }

        if ((this.leaveBalance.getLbBalForwardDay() == null)
                || (this.leaveBalance.getLbBalForwardDay().compareTo(ZERO) == 0)) {
            this.lbLastYear = ZERO;
            this.lbrLastYear = ZERO;
            this.lbCurrYear = this.leaveBalance.getLbDaysOfYear();
            this.lbrCurrYear = this.lbCurrYear.subtract(getCurrentYearLeaveRequestDays(this.emp));
            if (this.lbrCurrYear.compareTo(ZERO) <= 0) {
                this.lbrCurrYear = ZERO;
            }

            return "success";
        }

        if (this.leaveBalance.getLbBalEndDate() == null) {
            this.lbLastYear = this.leaveBalance.getLbBalForwardDay();
            this.lbrLastYear = ZERO;
            this.lbCurrYear = this.leaveBalance.getLbDaysOfYear();
            this.lbrCurrYear = this.lbCurrYear.subtract(getCurrentYearLeaveRequestDays(this.emp));
            if (this.lbrCurrYear.compareTo(ZERO) <= 0) {
                this.lbrCurrYear = ZERO;
            }

            return "success";
        }

        List allLeaveRequest = getCurrentYearLeaveRequest(this.emp);

        Date lbBalEndDate = this.leaveBalance.getLbBalEndDate();

        Map map = getRequest(allLeaveRequest, lbBalEndDate, this.emp);

        BigDecimal lrBfExpdate = (BigDecimal) map.get("lrBfExpdate");
        BigDecimal lrAfExpdate = (BigDecimal) map.get("lrAfExpdate");

        this.lbLastYear = this.leaveBalance.getLbBalForwardDay();
        this.lbCurrYear = this.leaveBalance.getLbDaysOfYear();

        if (Calendar.getInstance().getTime().compareTo(lbBalEndDate) <= 0) {
            if (this.lbLastYear.subtract(lrBfExpdate).compareTo(ZERO) > 0) {
                this.lbrLastYear = this.lbLastYear.subtract(lrBfExpdate);
                this.lbrCurrYear = this.lbCurrYear.subtract(lrAfExpdate);
            } else {
                this.lbrLastYear = ZERO;
                this.lbrCurrYear = this.lbCurrYear.subtract(lrAfExpdate).subtract(lrBfExpdate)
                        .add(this.lbLastYear);
            }

        } else if (this.lbLastYear.compareTo(lrBfExpdate) > 0) {
            this.lbrLastYear = ZERO;
            this.lbrCurrYear = this.lbCurrYear.subtract(lrAfExpdate);
        } else {
            this.lbrLastYear = ZERO;
            this.lbrCurrYear = this.lbCurrYear.subtract(lrAfExpdate).subtract(lrBfExpdate)
                    .add(this.lbLastYear);
        }

        return "success";
    }

    private BigDecimal getCurrentYearLeaveRequestDays(Employee employee) {
        List tempResultLasyYear = getCurrentYearLeaveRequest(employee);

        int lastYearSize = tempResultLasyYear.size();
        BigDecimal d = new BigDecimal(0);
        for (int i = 0; i < lastYearSize; ++i) {
            Leaverequest leaveReq = (Leaverequest) tempResultLasyYear.get(i);
            d = d.add(leaveReq.getLrTotalHours());
        }

        IAttendshiftBO attendBo = (IAttendshiftBO) SpringBeanFactory.getBean("attendshiftBO");
        Attendshift defaultAs = attendBo.getDefaultAttendshiftByEmp(employee);
        BigDecimal days = d.divide(defaultAs.getAttsWorkingHour(), 2);
        return days;
    }

    private List<Leaverequest> getCurrentYearLeaveRequest(Employee employee) {
        Calendar begin = Calendar.getInstance();
        begin.set(2, 0);
        begin.set(5, 1);

        Calendar end = (Calendar) begin.clone();
        end.set(2, 11);
        end.set(5, 31);
        ILeaverequestBO lr_BO = (ILeaverequestBO) getBean("leaverequestBO");
        List tempResultLasyYear = new ArrayList();
        for (Leavetype leaveType : this.allLeaveType) {
            if (leaveType.getLtSpecialCat().intValue() != 1) {
                continue;
            }
            List tmp = lr_BO.getTotalStatusSetList(this.emp, begin.getTime(), end.getTime(),
                                                   leaveType, STATUS_SET);

            if ((tmp != null) && (tmp.size() > 0)) {
                tempResultLasyYear.addAll(tmp);
            }
        }
        return tempResultLasyYear;
    }

    private Map<String, BigDecimal> getRequest(List<Leaverequest> requestList, Date expDate,
            Employee emp) {
        Map map = new HashMap();

        LrDataCheckImpl lrAddCheckData = (LrDataCheckImpl) getBean("lrDataCheck");
        double[] total = lrAddCheckData.getLeaveHoursBeforAfter(requestList, expDate);

        IAttendshiftBO attendBo = (IAttendshiftBO) SpringBeanFactory.getBean("attendshiftBO");
        Attendshift defaultAs = attendBo.getDefaultAttendshiftByEmp(emp);
        double hoursPerDay = defaultAs.getAttsWorkingHour().doubleValue();

        total[0] = MyTools.round(total[0] / hoursPerDay, 2);
        total[1] = MyTools.round(total[1] / hoursPerDay, 2);
        map.put("lrBfExpdate", new BigDecimal(total[0]));
        map.put("lrAfExpdate", new BigDecimal(total[1]));
        return map;
    }

    public List<Leavetype> getAllLeaveType() {
        return this.allLeaveType;
    }

    public void setAllLeaveType(List<Leavetype> allLeaveType) {
        this.allLeaveType = allLeaveType;
    }

    public Employee getEmp() {
        return this.emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public BigDecimal getLbCurrYear() {
        return this.lbCurrYear;
    }

    public void setLbCurrYear(BigDecimal lbCurrYear) {
        this.lbCurrYear = lbCurrYear;
    }

    public BigDecimal getLbLastYear() {
        return this.lbLastYear;
    }

    public void setLbLastYear(BigDecimal lbLastYear) {
        this.lbLastYear = lbLastYear;
    }

    public BigDecimal getLbrCurrYear() {
        return this.lbrCurrYear;
    }

    public void setLbrCurrYear(BigDecimal lbrCurrYear) {
        this.lbrCurrYear = lbrCurrYear;
    }

    public BigDecimal getLbrLastYear() {
        return this.lbrLastYear;
    }

    public void setLbrLastYear(BigDecimal lbrLastYear) {
        this.lbrLastYear = lbrLastYear;
    }

    public Leavebalance getLeaveBalance() {
        return this.leaveBalance;
    }

    public void setLeaveBalance(Leavebalance leaveBalance) {
        this.leaveBalance = leaveBalance;
    }

    public List getLeaveRequestList() {
        return this.leaveRequestList;
    }

    public void setLeaveRequestList(List leaveRequestList) {
        this.leaveRequestList = leaveRequestList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.MyAttendance JD-Core Version: 0.5.4
 */