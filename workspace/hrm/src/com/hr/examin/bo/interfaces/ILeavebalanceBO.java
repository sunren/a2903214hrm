package com.hr.examin.bo.interfaces;

import com.hr.examin.action.beans.LeaveBalanceSearchBean;
import com.hr.examin.action.beans.LeaveReportBean;
import com.hr.examin.domain.Leavebalance;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Leavetype;
import com.hr.examin.domain.Overtimerequest;
import com.hr.profile.domain.Employee;
import com.hr.util.Pager;
import java.math.BigDecimal;
import java.util.List;

public abstract interface ILeavebalanceBO {
    /** @deprecated */
    public abstract Leavebalance getLeavebalance(Employee paramEmployee, String paramString);

    public abstract List<Leavebalance> getHistoryLeavebalanceList(
            LeaveBalanceSearchBean paramLeaveBalanceSearchBean, Pager paramPager);

    public abstract List<Employee> getLeavebalanceList(
            LeaveBalanceSearchBean paramLeaveBalanceSearchBean, Pager paramPager,
            List<Overtimerequest> paramList);

    public abstract List<Employee> getEmpWithLB(Leavebalance paramLeavebalance, Pager paramPager);

    public abstract List<Employee> getEmpWithoutLB(Leavebalance paramLeavebalance, Pager paramPager);

    public abstract List<String> exeLeaveBalanceInit(String[] paramArrayOfString,
            Integer[] paramArrayOfInteger, String paramString);

    public abstract List<String> exeSubmitLeaveBalance(String[] paramArrayOfString,
            Integer[] paramArrayOfInteger, String paramString);

    public abstract List<String> exeSubmitLeaveBalance(String[] paramArrayOfString);

    public abstract List<String> exeDeleteLeaveBalance(String[] paramArrayOfString);

    public abstract List<String> exeDeleteLeaveBalance(String[] paramArrayOfString,
            Integer[] paramArrayOfInteger, String paramString);

    public abstract Leavebalance getLeaveBalanceById(String paramString);

    public abstract void updateLeaveBalance(Leavebalance paramLeavebalance);

    public abstract void deleteLeaveBalance(String paramString);

    public abstract List<String> delLeavebalanceByYearAndEmp(Leavebalance paramLeavebalance);

    public abstract List getAllYears();

    public abstract List<Leavebalance> getLeavebalanceForwardOfYear(int paramInt);

    public abstract List<Overtimerequest> getOtForwardOfYear(int paramInt);

    public abstract List<Leaverequest> getLrForwardOfYear(int paramInt);

    public abstract BigDecimal[] getYearLeaveToYearCanuse(String paramString, int paramInt);

    public abstract Leavebalance getLeavebalance(Employee paramEmployee, String paramString,
            Leavetype paramLeavetype);

    public abstract List<LeaveReportBean> doSummaryEmpLeaveRequest(String paramString1,
            String paramString2, String paramString3);

    public abstract List<Leavebalance> getEmpLBOfYear(List<Employee> paramList,
            Integer paramInteger, String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.bo.interfaces.ILeavebalanceBO JD-Core Version: 0.5.4
 */