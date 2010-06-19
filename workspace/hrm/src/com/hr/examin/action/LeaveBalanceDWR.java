package com.hr.examin.action;

import com.hr.base.DWRUtil;
import com.hr.examin.bo.interfaces.ILeavebalanceBO;
import com.hr.examin.domain.Leavebalance;
import com.hr.spring.SpringBeanFactory;

public class LeaveBalanceDWR {
    public Leavebalance getLeavebalanceById(String id) {
        if ("error".equals(DWRUtil.checkAuth("initLeaveBalance", "initLeaveBalance"))) {
            return null;
        }
        ILeavebalanceBO leaveBalanceBo = (ILeavebalanceBO) SpringBeanFactory
                .getBean("leavebalanceBO");
        Leavebalance lb = leaveBalanceBo.getLeaveBalanceById(id);
        if (lb == null) {
            return null;
        }
        lb.setLbEmpNo(null);
        return lb;
    }

    public String deleteLeaveBalanceById(String id) {
        if ("error".equals(DWRUtil.checkAuth("initLeaveBalance", "initLeaveBalance"))) {
            return "无权操作!";
        }
        ILeavebalanceBO leaveBalanceBo = (ILeavebalanceBO) SpringBeanFactory
                .getBean("leavebalanceBO");
        leaveBalanceBo.deleteLeaveBalance(id);
        return null;
    }

    public String updateLeaveBalance(Leavebalance leaveBalance) {
        if ("error".equals(DWRUtil.checkAuth("initLeaveBalance", "initLeaveBalance"))) {
            return "无权操作!";
        }
        ILeavebalanceBO leaveBalanceBo = (ILeavebalanceBO) SpringBeanFactory
                .getBean("leavebalanceBO");
        Leavebalance old = leaveBalanceBo.getLeaveBalanceById(leaveBalance.getLbId());
        if (old == null) {
            return "要更新的数据不存在或已经被删除!";
        }
        old.setLbBalForwardDay(leaveBalance.getLbBalForwardDay());
        old.setLbBalForwardHour(leaveBalance.getLbBalForwardHour());
        old.setLbBalEndDate(leaveBalance.getLbBalEndDate());
        old.setLbComments(leaveBalance.getLbComments());
        old.setLbDaysOfYear(leaveBalance.getLbDaysOfYear());
        old.setLbDaysForRelease(leaveBalance.getLbDaysForRelease());
        old.setLbHoursForRelease(leaveBalance.getLbHoursForRelease());
        old.setLbReleaseStartDate(leaveBalance.getLbReleaseStartDate());
        old.setLbHoursOfYear(leaveBalance.getLbHoursOfYear());
        old.setLbOtherDays(leaveBalance.getLbOtherDays());
        old.setLbOtherHours(leaveBalance.getLbOtherHours());

        leaveBalanceBo.updateLeaveBalance(old);
        return null;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.action.LeaveBalanceDWR JD-Core Version: 0.5.4
 */