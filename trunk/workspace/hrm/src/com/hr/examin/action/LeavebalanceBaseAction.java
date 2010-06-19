package com.hr.examin.action;

import com.hr.base.DWRUtil;
import com.hr.examin.action.beans.APM;
import com.hr.examin.action.beans.LeaveBalanceLR;
import com.hr.examin.bo.ExaminDateUtil;
import com.hr.examin.bo.LrDataCheckImpl;
import com.hr.examin.bo.interfaces.ILeavebalanceBO;
import com.hr.examin.bo.interfaces.ILeaverequestBO;
import com.hr.examin.bo.interfaces.ILeavetypeBO;
import com.hr.examin.domain.Leavebalance;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Leavetype;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.DateUtil;
import com.hr.util.MyTools;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpSession;

public class LeavebalanceBaseAction extends EmpExaminAction {
    private static final long serialVersionUID = 1176430906128473566L;

    public List getEmpLRList(String empid, int year, HttpSession session) {
        if (!DWRUtil.checkAuth("leavebalanceManage", "execute").equals("HR")) {
            return null;
        }
        ILeaverequestBO lr_BO = (ILeaverequestBO) getBean("leaverequestBO");
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.set(year, 0, 1, 0, 0, 0);
        start.set(14, 0);
        end.set(year, 11, 31, 23, 59, 59);
        end.set(14, 0);
        int[] statusSet = null;
        List retur = new LinkedList();
        ILeavetypeBO lt_BO = (ILeavetypeBO) getBean("leavetypeBO");
        Leavetype lt = lt_BO.getAnnualLeave();
        List lrset = lr_BO.getTotalStatusSetList(new Employee(empid), start.getTime(), end
                .getTime(), lt, statusSet);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        double hoursPerDay = ExaminDateUtil.getDefaultHoursPerDay();

        for (int j = 0; j < lrset.size(); ++j) {
            retur.add(new LeaveBalanceLR(((Leaverequest) lrset.get(j)).getLrNo().toString(), df
                    .format(((Leaverequest) lrset.get(j)).getLrStartDate())
                    + APM.getDescription(((Leaverequest) lrset.get(j)).getLrStartDate()), df
                    .format(((Leaverequest) lrset.get(j)).getLrEndDate())
                    + APM.getDescription(((Leaverequest) lrset.get(j)).getLrEndDate()), MyTools
                    .round(((Leaverequest) lrset.get(j)).getLrTotalHours().doubleValue()
                            / hoursPerDay, 2), ((Leaverequest) lrset.get(j)).getLrStatusMean()));
        }

        return retur;
    }

    public List getEmpLRList_tiaoxiu(String empid, int year, HttpSession session) {
        if (!DWRUtil.checkAuth("leavebalanceManage", "execute").equals("HR")) {
            return null;
        }

        ILeavetypeBO lt_BO = (ILeavetypeBO) getBean("leavetypeBO");
        ILeaverequestBO lr_BO = (ILeaverequestBO) getBean("leaverequestBO");
        Leavetype lt = lt_BO.getTiaoxiuLeavetype(Integer.valueOf(10));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        Date end = null;
        Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(2, 0);
        cal.set(5, 1);
        try {
            start = format.parse(format.format(cal.getTime()));
            cal.add(1, 1);
            end = format.parse(format.format(cal.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Employee emp = new Employee();
        emp.setId(empid);
        int[] statusSet = null;
        List lrset = lr_BO.getTotalStatusSetList(emp, start, end, lt, statusSet);

        List retur = new LinkedList();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        for (int j = 0; (lrset != null) && (j < lrset.size()); ++j) {
            retur.add(new LeaveBalanceLR(((Leaverequest) lrset.get(j)).getLrNo().toString(), df
                    .format(((Leaverequest) lrset.get(j)).getLrStartDate())
                    + APM.getDescription(((Leaverequest) lrset.get(j)).getLrStartDate()), df
                    .format(((Leaverequest) lrset.get(j)).getLrEndDate())
                    + APM.getDescription(((Leaverequest) lrset.get(j)).getLrEndDate()),
                    ((Leaverequest) lrset.get(j)).getLrTotalHours().doubleValue(),
                    ((Leaverequest) lrset.get(j)).getLrStatusMean()));
        }

        return retur;
    }

    public String getLBLRUsedDays(String empid, int year, HttpSession session) {
        if (!DWRUtil.checkAuth("leavebalanceManage", "execute").equals("HR")) {
            return "0,0";
        }

        ILeavetypeBO lt_BO = (ILeavetypeBO) getBean("leavetypeBO");
        Leavetype lt = lt_BO.getAnnualLeave();
        if (lt == null) {
            return "0,0";
        }

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        Employee emp = empBo.loadEmp(empid, null);

        ILeaverequestBO lr_BO = (ILeaverequestBO) getBean("leaverequestBO");
        int[] statusSet = { 15, 16 };
        BigDecimal[] totalhours = lr_BO.getTotalUsedDay(emp, year, lt, statusSet);

        ILeavebalanceBO lb_BO = (ILeavebalanceBO) SpringBeanFactory.getBean("leavebalanceBO");
        BigDecimal[] toYearRemind = lb_BO.getYearLeaveToYearCanuse(empid, year);

        String leaveType = DatabaseSysConfigManager.getInstance()
                .getProperty("sys.examin.leave.type");
        if (ExaminDateUtil.applyLRByDay(leaveType, emp.getEmpShiftType())) {
            return totalhours[0] + "," + toYearRemind[0];
        }
        return totalhours[1] + "," + toYearRemind[1];
    }

    public String getLBLRUsedDays_tiaoxiu(String empid, int year, HttpSession session) {
        if (!DWRUtil.checkAuth("leavebalanceManage", "execute").equals("HR")) {
            return "0,0";
        }

        ILeavetypeBO lt_BO = (ILeavetypeBO) getBean("leavetypeBO");
        Leavetype lt = lt_BO.getTiaoxiuLeavetype(Integer.valueOf(10));
        if (lt == null) {
            return "0,0";
        }

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        Employee emp = empBo.loadEmp(empid, null);

        double toYearRemind = 0.0D;
        ILeavebalanceBO lb_BO = (ILeavebalanceBO) getBean("leavebalanceBO");
        Leavebalance lb = lb_BO.getLeavebalance(emp, String.valueOf(year), lt);
        if ((lb == null) || (lb.getLbStatus().intValue() != 2)) {
            return "0,0";
        }

        ILeaverequestBO lr_BO = (ILeaverequestBO) getBean("leaverequestBO");
        int[] statusSet = { 15, 16 };
        BigDecimal[] usedHours = lr_BO.getTotalUsedDay(new Employee(empid), year, lt, statusSet);

        Date start = DateUtil.getYearFirstDay(year);
        Date end = DateUtil.getYearFirstDay(year + 1);

        LrDataCheckImpl lrDataCheck = (LrDataCheckImpl) getBean("lrDataCheck");

        Leaverequest tempLr = new Leaverequest();
        tempLr.setLrEmpNo(emp);
        tempLr.setLrLtNo(lt);
        int[] statusSet1 = { 15, 16 };
        List<Leaverequest> allTiaoxiuLeaveList = lrDataCheck
                .getTotalStatusSetList(tempLr, start, end, statusSet1);
        BigDecimal allTiaoxiuLeave = new BigDecimal(0);
        for (Leaverequest lr : allTiaoxiuLeaveList) {
            if ((lr != null) && (lr.getLrTotalHours() != null)) {
                allTiaoxiuLeave = allTiaoxiuLeave.add(lr.getLrTotalHours());
            }
        }
        toYearRemind = lb.getLbTiaoxiuYearCanUse().subtract(allTiaoxiuLeave).doubleValue();

        return usedHours + "," + toYearRemind;
    }

    public double getLBLRCanUsedDays(double hoursBefore, double hoursAfter) {
        double total = hoursBefore + hoursAfter;
        double hoursPerDay = ExaminDateUtil.getDefaultHoursPerDay();
        total = MyTools.round(total / hoursPerDay, 2);
        return total;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.action.LeavebalanceBaseAction JD-Core Version: 0.5.4
 */