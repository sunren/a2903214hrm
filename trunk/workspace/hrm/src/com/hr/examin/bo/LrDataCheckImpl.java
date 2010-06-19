package com.hr.examin.bo;

import com.hr.base.Status;
import com.hr.examin.bo.interfaces.IAttendshiftBO;
import com.hr.examin.bo.interfaces.IEmpshiftBo;
import com.hr.examin.bo.interfaces.ILeavebalanceBO;
import com.hr.examin.bo.interfaces.ILeavecalendarBO;
import com.hr.examin.bo.interfaces.ILeavetypeBO;
import com.hr.examin.bo.interfaces.IOvertimerequestBo;
import com.hr.examin.dao.interfaces.ILeaverequestDAO;
import com.hr.examin.domain.Attendshift;
import com.hr.examin.domain.Empshift;
import com.hr.examin.domain.Leavebalance;
import com.hr.examin.domain.Leavecalendar;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Leavetype;
import com.hr.examin.domain.Overtimerequest;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DateUtil;
import com.hr.util.MyTools;
import com.hr.util.StringUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class LrDataCheckImpl {
    public static int[] statusSet_approved = { 15, 16 };
    public static final int MILLSECONDONEDAY = 86400000;
    private IEmpshiftBo empShiftBo;
    private IAttendshiftBO attendBo;
    private IOvertimerequestBo or_Bo;
    private ILeaverequestDAO lr_DAO;
    private ILeavebalanceBO lb_BO;
    private ILeavecalendarBO lc_BO;

    public String getLrTimeInfo(Leaverequest lr) {
        String msgReturnDays = "您请了{0}天{1}，确定要提交本次请假吗？";
        String msgReturnHours = "您请了{0}小时({1}处1�7){2}，确定要提交本次请假吗？";

        Employee emp = (Employee) this.attendBo.loadObject(Employee.class, lr.getLrEmpNo().getId(),
                                                           null, new boolean[0]);
        lr.setLrEmpNo(emp);

        Integer empShiftType = lr.getLrEmpNo().getEmpShiftType();
        List lcList = null;
        List empShiftList = null;
        Attendshift defaultAs = null;
        if ((empShiftType.intValue() == 0) || (empShiftType.intValue() == 2)) {
            lcList = this.lc_BO.getCalendarListByDay(lr.getLrStartDate(), lr.getLrEndDate());
            defaultAs = this.attendBo.getDefaultAttendshiftByEmp(lr.getLrEmpNo());
            empShiftList = generateDefaultESList(lr, defaultAs, lcList);
        } else {
            empShiftList = getEmpShiftBo().getEmpShiftList(lr.getLrStartDate(), lr.getLrEndDate(),
                                                           lr.getLrEmpNo().getId());
        }

        if (lr.getLrStartApm() != null) {
            double totalDays = 0.0D;
            if ((empShiftType.intValue() == 0) || (empShiftType.intValue() == 2))
                totalDays = this.lc_BO.getWorkDays(lr.getLrStartDate(), lr.getLrEndDate(), lcList,
                                                   lr.getLrEmpNo().getEmpLocationNo());
            else {
                totalDays = empShiftList.size();
            }
            if (lr.getLrStartApm().intValue() == 1)
                totalDays -= 0.5D;
            if (lr.getLrEndApm().intValue() == 0)
                totalDays -= 0.5D;
            return StringUtil.message(msgReturnDays, new Object[] { Double.valueOf(totalDays),
                    lr.getLrLtNo().getLtName() });
        }

        double hoursPerDay = ExaminDateUtil.getDefaultHoursPerDay();
        double totalHours = getEmpShiftBo().computeTotalLeaveMinutes(lr.getLrStartDate(),
                                                                     lr.getLrEndDate(),
                                                                     empShiftList) / 60.0D;
        totalHours = MyTools.round(totalHours, 2);
        double totalDays = MyTools.round(totalHours / hoursPerDay, 2);
        return StringUtil.message(msgReturnHours, new Object[] { Double.valueOf(totalHours),
                Double.valueOf(totalDays), lr.getLrLtNo().getLtName() });
    }

    public String validateLRDate(Leaverequest lr) {
        String msgNoWorkDayIncl = "{0}请假未包含任何工作日＄1�7";
        String msgNightShift = "存在隔夜班次，必须按小时请假＄1�7";
        String msgShiftErr = "班次无法区分上下午，必须按小时请假！";
        String msgNoWorkDay = "{0}请假{1}日期不是工作日！";
        String msgNoShiftIncl = "{0}请假{1}时间不在您的班次中！";

        Integer empShiftType = lr.getLrEmpNo().getEmpShiftType();
        String empShiftDesc = "员工";
        if (empShiftType.intValue() == 3)
            empShiftDesc = "排班员工";

        List lcList = null;
        List empShiftList = null;
        Attendshift defaultAs = null;

        if ((empShiftType.intValue() == 0) || (empShiftType.intValue() == 2)) {
            defaultAs = this.attendBo.getDefaultAttendshiftByEmp(lr.getLrEmpNo());
            lcList = this.lc_BO.getCalendarListByDay(lr.getLrStartDate(), lr.getLrEndDate());
            empShiftList = generateDefaultESList(lr, defaultAs, lcList);
        } else {
            empShiftList = getEmpShiftBo().getEmpShiftList(lr.getLrStartDate(), lr.getLrEndDate(),
                                                           lr.getLrEmpNo().getId());
        }

        if (empShiftList.size() == 0) {
            return StringUtil.message(msgNoWorkDayIncl, new Object[] { empShiftDesc });
        }

        if (lr.isApplyLRByDay()) {
            String info = checkShiftsValid(empShiftList, lr.getLrStartDate(), lr.getLrEndDate());
            if ("NIGHTSHIFT".equals(info))
                return msgNightShift;
            if ("SHIFTERR".equals(info))
                return msgShiftErr;

            if (DateUtil.compareTwoDay(((Empshift) empShiftList.get(0)).getEmpshiftDate(), lr
                    .getLrStartDate()) != 0) {
                return StringUtil.message(msgNoWorkDay, new Object[] { empShiftDesc, "弄1�7姄1�7" });
            }
            if (DateUtil.compareTwoDay(((Empshift) empShiftList.get(empShiftList.size() - 1))
                    .getEmpshiftDate(), lr.getLrEndDate()) != 0) {
                return StringUtil.message(msgNoWorkDay, new Object[] { empShiftDesc, "结束" });
            }

            String attsSession = ((Empshift) empShiftList.get(0)).getEmpshiftShiftNo()
                    .getAttsSession();
            Date[] shiftDateTime = ExaminDateUtil.getSplitDateByAttendShift(lr.getLrStartDate(),
                                                                            attsSession);
            if (lr.getLrStartApm() == null)
                lr.setLrStartApm(Integer.valueOf(0));
            if (lr.getLrStartApm().intValue() == 0)
                lr.setLrStartDate(shiftDateTime[0]);
            else {
                lr.setLrStartDate(shiftDateTime[2]);
            }

            attsSession = ((Empshift) empShiftList.get(empShiftList.size() - 1))
                    .getEmpshiftShiftNo().getAttsSession();
            shiftDateTime = ExaminDateUtil
                    .getSplitDateByAttendShift(lr.getLrEndDate(), attsSession);
            if (lr.getLrEndApm() == null)
                lr.setLrEndApm(Integer.valueOf(1));
            if (lr.getLrEndApm().intValue() == 0)
                lr.setLrEndDate(shiftDateTime[1]);
            else
                lr.setLrEndDate(shiftDateTime[3]);
        } else {
            int inShift = getEmpShiftBo().isInWorkShift(lr.getLrStartDate(), empShiftList)
                    .intValue();
            if ((inShift == 0) || (inShift == 3)) {
                return StringUtil
                        .message(msgNoShiftIncl, new Object[] { empShiftDesc, "弄1�7姄1�7" });
            }
            inShift = getEmpShiftBo().isInWorkShift(lr.getLrEndDate(), empShiftList).intValue();
            if ((inShift == 0) || (inShift == 1))
                return StringUtil.message(msgNoShiftIncl, new Object[] { empShiftDesc, "结束" });
        }
        double hoursPerDay;
        if ((empShiftType.intValue() == 0) || (empShiftType.intValue() == 2))
            hoursPerDay = defaultAs.getAttsWorkingHour().doubleValue();
        else {
            hoursPerDay = ExaminDateUtil.getDefaultHoursPerDay();
        }
        lr.setHoursPerDay(hoursPerDay);

        double totalHours = 0.0D;
        double totalDays = 0.0D;
        if (lr.isApplyLRByDay()) {
            totalDays = empShiftList.size();

            if (lr.getLrStartApm().intValue() == 1)
                totalDays -= 0.5D;
            if (lr.getLrEndApm().intValue() == 0)
                totalDays -= 0.5D;
            totalHours = totalDays * hoursPerDay;
        } else {
            totalHours = getEmpShiftBo().computeTotalLeaveMinutes(lr.getLrStartDate(),
                                                                  lr.getLrEndDate(), empShiftList) / 60.0D;
            totalDays = totalHours / hoursPerDay;
        }
        lr.setLrTotalHours(new BigDecimal(MyTools.round(totalHours, 2)));
        lr.setLrTotalDays(new BigDecimal(MyTools.round(totalDays, 2)));

        return "SUCC";
    }

    private List<Empshift> generateDefaultESList(Leaverequest lr, Attendshift defaultShift,
            List<Leavecalendar> lcList) {
        List list = new ArrayList();
        Date startDate = lr.getLrStartDate();
        Date endDate = lr.getLrEndDate();
        Date temp = (Date) startDate.clone();
        temp = DateUtil.parseDateByFormat(DateUtil.formatDate(temp), "yyyy-MM-dd");
        while (temp.compareTo(endDate) <= 0) {
            if (1 == this.lc_BO.isACalendarDay(temp, lcList, lr.getLrEmpNo().getEmpLocationNo())) {
                Empshift shift = new Empshift(null, lr.getLrEmpNo(), defaultShift, temp);
                list.add(shift);
            }
            temp = DateUtil.dateAdd(temp, 1);
        }

        return list;
    }

    private String checkShiftsValid(List<Empshift> empshiftList, Date startDate, Date endDate) {
        if (empshiftList == null)
            return "SUCC";

        startDate = DateUtil.convDateTimeToDate(startDate);
        endDate = DateUtil.convDateTimeToDate(endDate);

        Attendshift shift = null;
        for (Empshift empshift : empshiftList) {
            if (empshift.getEmpshiftDate().compareTo(startDate) < 0)
                continue;
            if (empshift.getEmpshiftDate().compareTo(endDate) > 0) {
                continue;
            }
            shift = empshift.getEmpshiftShiftNo();
            if (shift.getAttsNightShift().intValue() == 1)
                return "NIGHTSHIFT";
            if (shift.getAttsSession().split(",").length != 2) {
                return "SHIFTERR";
            }
        }
        return "SUCC";
    }

    public String validateLRConflict(Leaverequest lr) {
        String msgIDConflict = "与编号为{0}的请假单存在时间冲突＄1�7";
        String msgLTNotSetup = "{0}年的{1}额度未设置！";
        String msgLTNotApply = "{0}年的{1}禁止申请＄1�7";

        int repeatID = hasDateRepeat(lr, lr.getLrStartDate(), lr.getLrEndDate(),
                                     Status.PROCESS_VALID);
        if (repeatID != -1) {
            return StringUtil.message(msgIDConflict, new Object[] { Integer.valueOf(repeatID) });
        }

        String info = checkOther(lr);
        if (!"SUCC".equals(info))
            return info;

        Leavetype lt = lr.getLrLtNo();
        if ((lt.getLtSpecialCat().intValue() == 1) || (lt.getLtSpecialCat().intValue() == 5)
                || (lt.getLtSpecialCat().intValue() == 10)) {
            int calYear = DateUtil.getYear(lr.getLrStartDate());
            Leavebalance lb = this.lb_BO.getLeavebalance(lr.getLrEmpNo(), "" + calYear, lt);

            if (lb == null)
                return StringUtil.message(msgLTNotSetup, new Object[] { Integer.valueOf(calYear),
                        lt.getLtName() });
            if (lb.getLbStatus().intValue() != 2) {
                return StringUtil.message(msgLTNotApply, new Object[] { Integer.valueOf(calYear),
                        lt.getLtName() });
            }

            if (lt.getLtSpecialCat().intValue() == 1) {
                return checkAnnual(lr, lt, lb);
            }

            if (lt.getLtSpecialCat().intValue() == 5) {
                return checkSick02(lr, lt, lb);
            }

            if (lt.getLtSpecialCat().intValue() == 10) {
                return checkTiaoXiuNoExp(lr, lb);
            }
        }

        if (lt.getLtSpecialCat().intValue() == 11) {
            return checkTiaoXiuExp(lr);
        }
        return "SUCC";
    }

    public String checkOther(Leaverequest lr) {
        String msgTooSmall = "{0}每次请假时间不能少于{1}{2}＄1�7";
        String msgTooBig = "{0}每次请假时间不能大于{1}{2}＄1�7";

        GregorianCalendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(lr.getLrEndDate());
        ILeavetypeBO ltBo = (ILeavetypeBO) SpringBeanFactory.getBean("leavetypeBO");
        Leavetype lt = ltBo.getLeavetype(lr.getLrLtNo().getLtNo());

        double minPermitDays = 0.0D;
        double minPermitHours = 0.0D;
        if (lt.getLtMinApplyDays() != null) {
            minPermitDays = lt.getLtMinApplyDays().doubleValue();
            if ((minPermitDays > 0.0D) && (minPermitDays > lr.getLrTotalDays().doubleValue()))
                return StringUtil.message(msgTooSmall, new Object[] { lt.getLtName(),
                        Double.valueOf(minPermitDays), "处1�7" });
        } else if (lt.getLtMinApplyHours() != null) {
            minPermitHours = lt.getLtMinApplyHours().doubleValue();
            if ((minPermitHours > 0.0D) && (minPermitHours > lr.getLrTotalHours().doubleValue())) {
                return StringUtil.message(msgTooSmall, new Object[] { lt.getLtName(),
                        Double.valueOf(minPermitHours), "小时" });
            }
        }

        double maxPermitDays = 0.0D;
        double maxPermitHours = 0.0D;
        if (lt.getLtMaxApplyDays() != null) {
            maxPermitDays = lt.getLtMaxApplyDays().doubleValue();
            if ((maxPermitDays > 0.0D) && (maxPermitDays < lr.getLrTotalDays().doubleValue()))
                return StringUtil.message(msgTooBig, new Object[] { lt.getLtName(),
                        Double.valueOf(maxPermitDays), "处1�7" });
        } else if (lt.getLtMaxApplyHours() != null) {
            maxPermitHours = lt.getLtMaxApplyHours().doubleValue();
            if ((maxPermitHours > 0.0D) && (maxPermitHours < lr.getLrTotalHours().doubleValue())) {
                return StringUtil.message(msgTooBig, new Object[] { lt.getLtName(),
                        Double.valueOf(maxPermitHours), "小时" });
            }
        }
        return "SUCC";
    }

    public String checkTiaoXiuNoExp(Leaverequest lr, Leavebalance lb) {
        int currentYear = DateUtil.getYear(lr.getLrStartDate());
        Date yearBeginDate = DateUtil.getYearFirstDay(currentYear);
        Date yearEndDate = DateUtil.getYearEndDay(currentYear);
        double hourPerDay = ExaminDateUtil.getDefaultHoursPerDay();

        Double balFwdHours = new Double(0.0D);
        Double wastedHours = new Double(0.0D);

        List<Leaverequest> tiaoXiuLRList = getValidLRs(lr, yearBeginDate, yearEndDate);
        Double allHours = new Double(0.0D);
        for (Leaverequest lrTemp : tiaoXiuLRList) {
            allHours = Double.valueOf(allHours.doubleValue()
                    + lrTemp.getLrTotalHours().doubleValue());
        }

        if (lb != null) {
            balFwdHours = getBalFwdHours(lb, hourPerDay);

            if (lb.getLbBalEndDate() == null) {
                wastedHours = new Double(0.0D);
            } else {
                double[] allHoursArr = getLeaveHoursBeforAfter(tiaoXiuLRList, lb.getLbBalEndDate());
                wastedHours = Double
                        .valueOf((allHoursArr[0] < balFwdHours.doubleValue()) ? balFwdHours
                                .doubleValue()
                                - allHoursArr[0] : new Double(0.0D).doubleValue());
            }

        }

        List tiaoXiuORList = getTiaoXiuORs(lr.getLrEmpNo(), yearBeginDate,
                                           ((Leaverequest) tiaoXiuLRList
                                                   .get(tiaoXiuLRList.size() - 1)).getLrStartDate());

        Double releaseHours = getReleaseHours(tiaoXiuORList, null);
        if (allHours.doubleValue() > balFwdHours.doubleValue() + releaseHours.doubleValue()
                - wastedHours.doubleValue())
            return "已请调休假�1�7�时间超过可请上限，操作失败＄1�7";

        setUsageTime(lr, allHours.doubleValue(), balFwdHours.doubleValue(), releaseHours
                .doubleValue(), wastedHours.doubleValue());

        for (int i = tiaoXiuLRList.size() - 1; i >= 0; --i) {
            if (lr.getLrStartDate().compareTo(
                                              ((Leaverequest) tiaoXiuLRList.get(i))
                                                      .getLrStartDate()) == 0)
                break;
            allHours = Double.valueOf(allHours.doubleValue()
                    - ((Leaverequest) tiaoXiuLRList.get(i)).getLrTotalHours().doubleValue());
            releaseHours = getReleaseHours(tiaoXiuORList, ((Leaverequest) tiaoXiuLRList.get(i))
                    .getLrStartDate());
            if (allHours.doubleValue() > balFwdHours.doubleValue() + releaseHours.doubleValue()
                    - wastedHours.doubleValue())
                return "已请调休假�1�7�时间超过可请上限，操作失败＄1�7";
        }
        return "SUCC";
    }

    private Double getReleaseHours(List<Overtimerequest> tiaoXiuORList, Date endDate) {
        Double releaseHours = new Double(0.0D);
        if (tiaoXiuORList == null)
            return releaseHours;

        Overtimerequest tiaoXiuOR = null;
        for (int i = 0; i < tiaoXiuORList.size(); ++i) {
            tiaoXiuOR = (Overtimerequest) tiaoXiuORList.get(i);
            if ((endDate == null) || (tiaoXiuOR.getOrStartDate().compareTo(endDate) < 0)) {
                releaseHours = Double.valueOf(releaseHours.doubleValue()
                        + tiaoXiuOR.getOrTiaoxiuHours().doubleValue());
            }
        }

        return releaseHours;
    }

    public String checkTiaoXiuExp(Leaverequest lr) {
        List tiaoxiuLrList = getTotalTiaoxiuLrList(lr, Status.PROCESS_VALID);

        Overtimerequest or = new Overtimerequest();
        or.setOrEmpNo(lr.getLrEmpNo());
        List tiaoxiuOrList = this.or_Bo.getEmpAllTiaoxiuOrList(or, statusSet_approved);

        String info = "SUCC";

        Iterator iter = null;
        Overtimerequest tiaoxiuOr = null;
        Iterator i$ = tiaoxiuLrList.iterator();
        Leaverequest currLr;
        double lrHours;
        do {
            if (!i$.hasNext())
                return null;
            currLr = (Leaverequest) i$.next();
            lrHours = currLr.getLrTotalHours().doubleValue();

            iter = tiaoxiuOrList.iterator();
            while (iter.hasNext()) {
                tiaoxiuOr = (Overtimerequest) iter.next();

                if ((tiaoxiuOr.getOrTiaoxiuExpire() != null)
                        && (tiaoxiuOr.getOrTiaoxiuExpire().compareTo(currLr.getLrStartDate()) < 0)) {
                    iter.remove();
                }

                double tiaoxiuHours = tiaoxiuOr.getOrTiaoxiuHours().doubleValue();

                if (lrHours > tiaoxiuHours) {
                    iter.remove();
                    lrHours -= tiaoxiuHours;
                }

                if (lrHours == tiaoxiuHours) {
                    iter.remove();
                    lrHours = 0.0D;
                    break;
                }

                if (lrHours < tiaoxiuHours) {
                    tiaoxiuOr.setOrTiaoxiuHours(new BigDecimal(tiaoxiuHours - lrHours));
                    lrHours = 0.0D;
                    break;
                }
            }
        }

        while (lrHours == 0.0D);

        if (currLr.getLrNo() == null)
            info = "本次调休假请假时间超过了最大额度！";
        else {
            info = "调休编号(" + currLr.getLrNo() + ")的请假时间超过了最大额度！";
        }

        return info;
    }

    public String checkAnnual(Leaverequest lr, Leavetype lt, Leavebalance lb) {
        int currentYear = Integer.parseInt(lb.getLbYear());
        Date yearBeginDate = DateUtil.getYearFirstDay(currentYear);
        Date yearEndDate = DateUtil.getYearEndDay(currentYear);
        Date nextYearBeginDate = DateUtil.getNextYearFirstDay(currentYear);

        List annualLRList = getValidLRs(lr, yearBeginDate, yearEndDate);

        Double balFwdHours = getBalFwdHours(lb, lr.getHoursPerDay());
        Double allHours;
        Double wastedHours;
        if (lb.getLbBalEndDate() == null) {
            wastedHours = new Double(0.0D);
            allHours = Double.valueOf(getLeaveHoursBeforAfter(annualLRList, nextYearBeginDate)[0]);
        } else {
            double[] allHoursArr = getLeaveHoursBeforAfter(annualLRList, lb.getLbBalEndDate());
            wastedHours = Double.valueOf((allHoursArr[0] < balFwdHours.doubleValue()) ? balFwdHours
                    .doubleValue()
                    - allHoursArr[0] : new Double(0.0D).doubleValue());
            allHours = Double.valueOf(allHoursArr[0] + allHoursArr[1]);
        }

        Date endDate = ((Leaverequest) annualLRList.get(annualLRList.size() - 1)).getLrStartDate();
        Double releaseHours = getReleaseHours(lb, lt, endDate, lr.getHoursPerDay());
        if (allHours.doubleValue() > balFwdHours.doubleValue() + releaseHours.doubleValue()
                - wastedHours.doubleValue())
            return "已请年假总时间超过可请上限，操作失败＄1�7";

        setUsageTime(lr, allHours.doubleValue(), balFwdHours.doubleValue(), releaseHours
                .doubleValue(), wastedHours.doubleValue());

        for (int i = annualLRList.size() - 1; i >= 0; --i) {
            if (lr.getLrStartDate()
                    .compareTo(((Leaverequest) annualLRList.get(i)).getLrStartDate()) == 0)
                break;
            allHours = Double.valueOf(allHours.doubleValue()
                    - ((Leaverequest) annualLRList.get(i)).getLrTotalHours().doubleValue());
            releaseHours = getReleaseHours(lb, lt, ((Leaverequest) annualLRList.get(i))
                    .getLrStartDate(), lr.getHoursPerDay());
            if (allHours.doubleValue() > balFwdHours.doubleValue() + releaseHours.doubleValue()
                    - wastedHours.doubleValue())
                return "已请年假总时间超过可请上限，操作失败＄1�7";
        }
        return "SUCC";
    }

    public Double getBalFwdHours(Leavebalance lb, double hourPerDay) {
        if (lb.getLbBalForwardDay() != null)
            return Double.valueOf(lb.getLbBalForwardDay().doubleValue() * hourPerDay);
        if (lb.getLbBalForwardHour() != null)
            return Double.valueOf(lb.getLbBalForwardHour().doubleValue());
        return new Double(0.0D);
    }

    public Double getCurrYearHours(Leavebalance lb, double hourPerDay) {
        if (lb.getLbDaysOfYear() != null)
            return Double.valueOf(lb.getLbDaysOfYear().doubleValue() * hourPerDay);
        if (lb.getLbHoursOfYear() != null)
            return Double.valueOf(lb.getLbHoursOfYear().doubleValue());
        return new Double(0.0D);
    }

    public Double getReleaseHours(Leavebalance lb, Leavetype lt, Date yearToDate, double hourPerDay) {
        Double currYearHours = getCurrYearHours(lb, hourPerDay);
        if (lt.getLtReleaseMethod() == null)
            return currYearHours;

        Double releaseHours = new Double(0.0D);
        if (lb.getLbDaysForRelease() != null)
            releaseHours = Double.valueOf(lb.getLbDaysForRelease().doubleValue() * hourPerDay);
        else if (lb.getLbHoursForRelease() != null)
            releaseHours = Double.valueOf(lb.getLbHoursForRelease().doubleValue());
        else
            releaseHours = currYearHours;

        int currentYear = DateUtil.getYear(yearToDate);
        Date yearBeginDate = DateUtil.getYearFirstDay(currentYear);
        Date yearEndDate = DateUtil.getYearEndDay(currentYear);

        Date relBeginDate = yearBeginDate;
        if ((lb.getLbReleaseStartDate() != null)
                && (DateUtil.getYear(lb.getLbReleaseStartDate()) == DateUtil.getYear(yearBeginDate))) {
            relBeginDate = lb.getLbReleaseStartDate();
        }

        double monthsOfYear = 12 - DateUtil.getMonth(relBeginDate) + 1;
        double monthToDate = DateUtil.getMonth(yearToDate) - DateUtil.getMonth(relBeginDate) + 1;
        double factor_M = monthToDate / monthsOfYear;

        double daysOfYear = 1 + DateUtil.dateDiff(relBeginDate, yearEndDate, 5);
        double daysToDate = 1 + DateUtil.dateDiff(relBeginDate, yearToDate, 5);
        double factor_D = daysToDate / daysOfYear;

        if ("M".equals(lt.getLtReleaseMethod()))
            releaseHours = Double.valueOf(factor_M * releaseHours.doubleValue());
        else if ("D".equals(lt.getLtReleaseMethod()))
            releaseHours = Double.valueOf(factor_D * releaseHours.doubleValue());
        else
            releaseHours = currYearHours;

        if (releaseHours.doubleValue() > currYearHours.doubleValue())
            return currYearHours;

        releaseHours = Double.valueOf(processByRound(releaseHours.doubleValue(), lt
                .getLtReleaseRounding()));
        return releaseHours;
    }

    public String checkSick02(Leaverequest lr, Leavetype lt, Leavebalance lb) {
        int currentYear = Integer.parseInt(lb.getLbYear());
        Date yearBeginDate = DateUtil.getYearFirstDay(currentYear);
        Date yearEndDate = DateUtil.getYearEndDay(currentYear);
        Date nextYearBeginDate = DateUtil.getNextYearFirstDay(currentYear);
        double hourPerDay = ExaminDateUtil.getDefaultHoursPerDay();

        List sick02LRList = getValidLRs(lr, yearBeginDate, yearEndDate);

        Double allHours = Double
                .valueOf(getLeaveHoursBeforAfter(sick02LRList, nextYearBeginDate)[0]);

        Date endDate = ((Leaverequest) sick02LRList.get(sick02LRList.size() - 1)).getLrStartDate();
        Double releaseHours = getReleaseHours(lb, lt, endDate, hourPerDay);
        if (allHours.doubleValue() > releaseHours.doubleValue())
            return "已请带薪病假总时间超过可请上限，操作失败＄1�7";

        setUsageTime(lr, allHours.doubleValue(), 0.0D, releaseHours.doubleValue(), 0.0D);

        for (int i = sick02LRList.size() - 1; i >= 0; --i) {
            if (lr.getLrStartDate()
                    .compareTo(((Leaverequest) sick02LRList.get(i)).getLrStartDate()) == 0)
                break;
            allHours = Double.valueOf(allHours.doubleValue()
                    - ((Leaverequest) sick02LRList.get(i)).getLrTotalHours().doubleValue());
            releaseHours = getReleaseHours(lb, lt, ((Leaverequest) sick02LRList.get(i))
                    .getLrStartDate(), hourPerDay);
            if (allHours.doubleValue() > releaseHours.doubleValue())
                return "已请带薪病假总时间超过可请上限，操作失败＄1�7";
        }
        return "SUCC";
    }

    private void setUsageTime(Leaverequest lr, double allHours, double balFwdHours,
            double releaseHours, double wastedHours) {
        double temp = balFwdHours + releaseHours - wastedHours;
        lr.setUseableHours(new BigDecimal(MyTools.round(temp, 2)));
        lr.setUseableDays(new BigDecimal(MyTools.round(temp / lr.getHoursPerDay(), 2)));

        temp = allHours;
        lr.setUsedHours(new BigDecimal(MyTools.round(temp, 2)));
        lr.setUsedDays(new BigDecimal(MyTools.round(temp / lr.getHoursPerDay(), 2)));

        temp = balFwdHours + releaseHours - wastedHours - allHours;
        lr.setRemainHours(new BigDecimal(MyTools.round(temp, 2)));
        lr.setRemainDays(new BigDecimal(MyTools.round(temp / lr.getHoursPerDay(), 2)));
    }

    public double processByRound(double value, Integer roundType) {
        double result;
        switch (roundType.intValue()) {
        case 1:
            result = MyTools.floor(value, 0);
            break;
        case 2:
            result = MyTools.round(value, 0);
            break;
        case 3:
            result = MyTools.ceil(value, 0);
            break;
        case 4:
            result = MyTools.floor(value * 2.0D, 0) / 2.0D;
            break;
        case 5:
            result = MyTools.round(value * 2.0D, 0) / 2.0D;
            break;
        case 6:
            result = MyTools.ceil(value * 2.0D, 0) / 2.0D;
            break;
        default:
            result = MyTools.round(value, 2);
        }

        return result;
    }

    /** @deprecated */
    public List<Leaverequest> getTotalStatusSetList(Leaverequest lr, Date startDate, Date endDate,
            int[] statusSet) {
        Integer[] statusSet1 = new Integer[statusSet.length];
        for (int i = 0; i < statusSet.length; ++i) {
            statusSet1[i] = Integer.valueOf(statusSet[i]);
        }
        DetachedCriteria detachedCrteria = DetachedCriteria.forClass(Leaverequest.class);
        detachedCrteria.add(Restrictions.eq(Leaverequest.PROP_LR_EMP_NO, lr.getLrEmpNo()));

        detachedCrteria.add(Restrictions.or(Restrictions.and(Restrictions
                .ge(Leaverequest.PROP_LR_START_DATE, startDate), Restrictions
                .lt(Leaverequest.PROP_LR_START_DATE, endDate)), Restrictions.and(Restrictions
                .le(Leaverequest.PROP_LR_END_DATE, endDate), Restrictions
                .gt(Leaverequest.PROP_LR_END_DATE, startDate))));

        detachedCrteria.add(Restrictions.eq(Leaverequest.PROP_LR_LT_NO, lr.getLrLtNo()));
        detachedCrteria.add(Restrictions.in(Leaverequest.PROP_LR_STATUS, statusSet1));
        if ((lr.getLrId() != null) && (!lr.getLrId().equals(""))) {
            detachedCrteria.add(Restrictions.ne(Leaverequest.PROP_LR_ID, lr.getLrId()));
        }
        List result = getLr_DAO().findByCriteria(detachedCrteria);

        return result;
    }

    private List<Leaverequest> getValidLRs(Leaverequest lr, Date startDate, Date endDate) {
        if (startDate == null)
            startDate = DateUtil.convYMToMonFirst("190001");
        if (endDate == null)
            endDate = DateUtil.convYMToMonEnd("209912");

        DetachedCriteria detachedCrteria = DetachedCriteria.forClass(Leaverequest.class);
        detachedCrteria.add(Restrictions.eq(Leaverequest.PROP_LR_EMP_NO, lr.getLrEmpNo()));
        detachedCrteria.add(Restrictions.or(Restrictions.and(Restrictions
                .ge(Leaverequest.PROP_LR_START_DATE, startDate), Restrictions
                .lt(Leaverequest.PROP_LR_START_DATE, endDate)), Restrictions.and(Restrictions
                .le(Leaverequest.PROP_LR_END_DATE, endDate), Restrictions
                .gt(Leaverequest.PROP_LR_END_DATE, startDate))));

        detachedCrteria.add(Restrictions.eq(Leaverequest.PROP_LR_LT_NO, lr.getLrLtNo()));
        detachedCrteria.addOrder(Order.asc(Leaverequest.PROP_LR_START_DATE));
        List<Leaverequest> lrList = getLr_DAO().findByCriteria(detachedCrteria);
        List result = new LinkedList();
        int index = 0;
        for (Leaverequest lrTemp : lrList) {
            if (lrTemp.getLrStatus().intValue() == 21)
                continue;
            if (lrTemp.getLrStatus().intValue() == 22) {
                continue;
            }
            if (lrTemp.getLrId().equals(lr.getLrId()))
                continue;
            result.add(lrTemp);
            if (lr.getLrStartDate().after(lrTemp.getLrStartDate())) {
                ++index;
            }
        }
        result.add(index, lr);
        return result;
    }

    private List<Overtimerequest> getTiaoXiuORs(Employee emp, Date startDate, Date endDate) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Overtimerequest.class);
        detachedCriteria.add(Restrictions.eq(Overtimerequest.PROP_OR_EMP_NO, emp));

        detachedCriteria.add(Restrictions.gt(Overtimerequest.PROP_OR_TIAOXIU_HOURS, new BigDecimal(
                0)));

        detachedCriteria.add(Restrictions.ge(Overtimerequest.PROP_OR_START_DATE, startDate));
        detachedCriteria.add(Restrictions.le(Overtimerequest.PROP_OR_END_DATE, endDate));

        Integer[] status = { Integer.valueOf(15), Integer.valueOf(16) };
        detachedCriteria.add(Restrictions.in(Overtimerequest.PROP_OR_STATUS, status));

        List orList = getLr_DAO().findByCriteria(detachedCriteria);
        if (orList.size() == 0)
            return null;
        return orList;
    }

    public List<Leaverequest> getTotalTiaoxiuLrList(Leaverequest lr, Integer[] statusSet) {
        DetachedCriteria detachedCrteria = DetachedCriteria.forClass(Leaverequest.class);
        detachedCrteria.add(Restrictions.eq(Leaverequest.PROP_LR_EMP_NO, lr.getLrEmpNo()));
        detachedCrteria.add(Restrictions.eq(Leaverequest.PROP_LR_LT_NO, lr.getLrLtNo()));
        detachedCrteria.add(Restrictions.in(Leaverequest.PROP_LR_STATUS, statusSet));
        detachedCrteria.addOrder(Order.asc(Leaverequest.PROP_LR_START_DATE));
        List result = getLr_DAO().findByCriteria(detachedCrteria);

        int index = -1;

        if ((lr.getLrId() != null) && (!lr.getLrId().equals("")) && (result != null)
                && (result.size() > 0))
            for (int i = 0; i < result.size(); ++i) {
                Leaverequest lrTemp = (Leaverequest) result.get(i);
                if (lrTemp.getLrId().equals(lr.getLrId())) {
                    result.set(i, lr);
                    index = i;
                    break;
                }
            }
        if (index == -1) {
            result.add(result.size(), lr);
        }
        return result;
    }

    public int hasDateRepeat(Leaverequest lr, Date startDate, Date endDate, Integer[] statusSet) {
        DetachedCriteria detachedCrteria = DetachedCriteria.forClass(Leaverequest.class);

        detachedCrteria.add(Restrictions.eq(Leaverequest.PROP_LR_EMP_NO, lr.getLrEmpNo()));
        detachedCrteria.add(Restrictions.in(Leaverequest.PROP_LR_STATUS, statusSet));

        Date startTime = lr.getLrStartDate();
        Date endTime = lr.getLrEndDate();
        detachedCrteria.add(Restrictions.lt(Leaverequest.PROP_LR_START_DATE, endTime));
        detachedCrteria.add(Restrictions.gt(Leaverequest.PROP_LR_END_DATE, startTime));

        List resultList = this.lr_DAO.findByCriteria(detachedCrteria);
        if ((resultList == null) || (resultList.size() == 0))
            return -1;
        if ((resultList.size() == 1)
                && (((Leaverequest) resultList.get(0)).getLrId().equals(lr.getLrId()))) {
            return -1;
        }
        return ((Leaverequest) resultList.get(0)).getLrNo().intValue();
    }

    public double[] getLeaveHoursBeforAfter(List<Leaverequest> inputlist, Date checkDate) {
        double[] result = new double[2];

        for (Leaverequest lr : inputlist) {
            if (DateUtil.dateDiff(checkDate, lr.getLrStartDate(), 5) > 1) {
                result[1] += lr.getLrTotalHours().doubleValue();
            }

            if (DateUtil.dateDiff(checkDate, lr.getLrEndDate(), 5) <= 0) {
                result[0] += lr.getLrTotalHours().doubleValue();
            }

            double totalBefore = 0.0D;

            if (lr.getLrStartApm() == null) {
                List shiftList = null;
                if (lr.getLrEmpNo().getEmpShiftType().intValue() == 3) {
                    shiftList = this.empShiftBo.getEmpShiftList(DateUtil.dateAdd(lr
                            .getLrStartDate(), -1), checkDate, lr.getLrEmpNo().getId());
                } else {
                    List lcList = this.lc_BO.getCalendarListByDay(lr.getLrStartDate(), checkDate);
                    Attendshift defaultAs = this.attendBo.getDefaultAttendshiftByEmp(lr
                            .getLrEmpNo());
                    shiftList = generateDefaultESList(lr, defaultAs, lcList);
                }
                totalBefore = getHoursByShift(lr, checkDate, shiftList);
            } else if (lr.getLrEmpNo().getEmpShiftType().intValue() == 3) {
                totalBefore = getDaysByShift(lr, checkDate);
            } else {
                totalBefore = getDaysByCal(lr, checkDate);
            }

            if (totalBefore > lr.getLrTotalHours().doubleValue()) {
                totalBefore = lr.getLrTotalHours().doubleValue();
            }
            result[0] += totalBefore;
            result[1] += lr.getLrTotalHours().doubleValue() - totalBefore;
        }
        return result;
    }

    public double getDaysByCal(Leaverequest lr, Date endDate) {
        List lcList = this.lc_BO.getCalendarListByDay(lr.getLrStartDate(), endDate);

        double totalDays = this.lc_BO.getWorkDays(lr.getLrStartDate(), endDate, lcList, lr
                .getLrEmpNo().getEmpLocationNo());

        if (lr.getLrStartApm().intValue() == 1)
            totalDays -= 0.5D;

        double hoursPerDay = ExaminDateUtil.getDefaultHoursPerDay();
        return totalDays * hoursPerDay;
    }

    private double getHoursByShift(Leaverequest lr, Date checkDate, List<Empshift> shiftList) {
        Empshift lastES = null;
        if ((shiftList == null) || (shiftList.size() == 0))
            return 0.0D;

        for (Empshift es : shiftList) {
            if (DateUtil.dateDiff(checkDate, es.getEmpshiftDate(), 5) > 0)
                break;
            lastES = es.clone();
        }

        if (lastES == null)
            return 0.0D;
        Date[] lastESArr = ExaminDateUtil.getShiftArr(lastES.getEmpshiftDate(), lastES
                .getEmpshiftShiftNo().getAttsSession());
        double totalHours = 0.0D;
        if (lr.getLrEndDate().before(lastESArr[1]))
            totalHours = getEmpShiftBo().computeTotalLeaveMinutes(lr.getLrStartDate(),
                                                                  lr.getLrEndDate(), shiftList) / 60.0D;
        else {
            totalHours = getEmpShiftBo().computeTotalLeaveMinutes(lr.getLrStartDate(),
                                                                  lastESArr[1], shiftList) / 60.0D;
        }
        totalHours = MyTools.round(totalHours, 2);
        return totalHours;
    }

    private double getDaysByShift(Leaverequest lr, Date checkDate) {
        List list = getEmpShiftBo().getEmpShiftList(lr.getLrStartDate(), checkDate,
                                                    lr.getLrEmpNo().getId());
        if ((list == null) || (list.size() == 0))
            return 0.0D;
        double totalDays = list.size();
        if (lr.getLrStartApm().intValue() == 1)
            totalDays -= 0.5D;
        double hoursPerDay = ExaminDateUtil.getDefaultHoursPerDay();
        return totalDays * hoursPerDay;
    }

    public ILeaverequestDAO getLr_DAO() {
        return this.lr_DAO;
    }

    public void setLr_DAO(ILeaverequestDAO lr_DAO) {
        this.lr_DAO = lr_DAO;
    }

    public ILeavebalanceBO getLb_BO() {
        return this.lb_BO;
    }

    public void setLb_BO(ILeavebalanceBO lb_BO) {
        this.lb_BO = lb_BO;
    }

    public ILeavecalendarBO getLc_BO() {
        return this.lc_BO;
    }

    public void setLc_BO(ILeavecalendarBO lc_BO) {
        this.lc_BO = lc_BO;
    }

    public IEmpshiftBo getEmpShiftBo() {
        return this.empShiftBo;
    }

    public void setEmpShiftBo(IEmpshiftBo empShiftBo) {
        this.empShiftBo = empShiftBo;
    }

    public IAttendshiftBO getAttendBo() {
        return this.attendBo;
    }

    public void setAttendBo(IAttendshiftBO attendBo) {
        this.attendBo = attendBo;
    }

    public IOvertimerequestBo getOr_Bo() {
        return this.or_Bo;
    }

    public void setOr_Bo(IOvertimerequestBo or_Bo) {
        this.or_Bo = or_Bo;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.bo.LrDataCheckImpl JD-Core Version: 0.5.4
 */