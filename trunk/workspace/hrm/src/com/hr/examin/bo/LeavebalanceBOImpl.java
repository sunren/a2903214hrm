package com.hr.examin.bo;

import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Location;
import com.hr.examin.action.beans.LeaveBalanceSearchBean;
import com.hr.examin.action.beans.LeaveReportBean;
import com.hr.examin.bo.interfaces.ILeavebalanceBO;
import com.hr.examin.bo.interfaces.ILeaverequestBO;
import com.hr.examin.bo.interfaces.ILeavetypeBO;
import com.hr.examin.dao.interfaces.ILeavebalanceDAO;
import com.hr.examin.domain.Leavebalance;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Leavetype;
import com.hr.examin.domain.Overtimerequest;
import com.hr.examin.support.BigDecimalUtils;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.dao.IEmployeeDAO;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.BaseCrit;
import com.hr.util.DateUtil;
import com.hr.util.InterpreterException;
import com.hr.util.InterpreterExecuteContext;
import com.hr.util.MyTools;
import com.hr.util.Pager;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class LeavebalanceBOImpl implements ILeavebalanceBO {
    private ILeavebalanceDAO lb_DAO;
    private IEmployeeDAO e_DAO;
    private IDepartmentBO dp_BO;
    private ILeaverequestBO lr_BO;

    public Leavebalance getLeavebalance(Employee currentEmp, String year) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Leavebalance.class);
        detachedCriteria.add(Restrictions.eq(Leavebalance.PROP_LB_EMP_NO, currentEmp));
        detachedCriteria.add(Restrictions.eq(Leavebalance.PROP_LB_YEAR, year));
        List<Leavebalance> result = this.lb_DAO.findByCriteria(detachedCriteria);
        if (result.size() == 0) {
            return null;
        }

        List otList = getOtForwardOfYear(Integer.parseInt(year) + 1);

        BigDecimal lbTiaoxiuForward = null;
        for (Leavebalance lb : result) {
            lbTiaoxiuForward = (lb.getLbTiaoxiuForward() != null) ? lb.getLbTiaoxiuForward()
                    : new BigDecimal(0);
            lb.setLbTiaoxiuCurrent(getCurrEmpTiaoxiuOt(lb.getLbEmpNo().getId(), otList));
            lb.setLbTiaoxiuYearCanUse(lbTiaoxiuForward.add(lb.getLbTiaoxiuCurrent()));
        }

        return (Leavebalance) result.get(0);
    }

    public Leavebalance getLeavebalance(Employee currentEmp, String year, Leavetype lrLtNo) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Leavebalance.class);
        detachedCriteria.add(Restrictions.eq(Leavebalance.PROP_LB_EMP_NO, currentEmp));
        detachedCriteria.add(Restrictions.eq(Leavebalance.PROP_LB_YEAR, year));
        detachedCriteria.add(Restrictions.eq(Leavebalance.PROP_LB_LEAVE_TYPE, lrLtNo
                .getLtSpecialCat()));

        List result = this.lb_DAO.findByCriteria(detachedCriteria);
        if (result.size() == 0) {
            return null;
        }

        return (Leavebalance) result.get(0);
    }

    public List<Leavebalance> getHistoryLeavebalanceList(LeaveBalanceSearchBean lb_Bean, Pager page) {
        List<Leavebalance> result = null;
        if ((lb_Bean.getDepNo() != null) && (lb_Bean.getDepNo().trim().length() > 0)
                && (this.dp_BO.loadDepartmentByNo(lb_Bean.getDepNo(), new String[0]) == null)) {
            return null;
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Leavebalance.class);
        detachedCriteria.setFetchMode(Leavebalance.PROP_LB_EMP_NO, FetchMode.JOIN);
        detachedCriteria
                .setFetchMode(Leavebalance.PROP_LB_EMP_NO + "." + Employee.PROP_EMP_DEPT_NO,
                              FetchMode.JOIN);
        detachedCriteria.createAlias(Leavebalance.PROP_LB_EMP_NO, Leavebalance.PROP_LB_EMP_NO);
        detachedCriteria.add(Restrictions.eq(Leavebalance.PROP_LB_YEAR, String.valueOf(lb_Bean
                .getYear())));
        if ((lb_Bean.getEmp() != null) && (lb_Bean.getEmp().trim().length() > 0)) {
            detachedCriteria.add(Restrictions.or(Restrictions.ilike(Leavebalance.PROP_LB_EMP_NO
                    + "." + Employee.PROP_EMP_DISTINCT_NO, "%" + lb_Bean.getEmp() + "%"),
                                                 Restrictions.ilike(Leavebalance.PROP_LB_EMP_NO
                                                         + "." + Employee.PROP_EMP_NAME, "%"
                                                         + lb_Bean.getEmp() + "%")));
        }

        if ((lb_Bean.getDepNo() != null) && (lb_Bean.getDepNo().trim().length() > 0)) {
            detachedCriteria.add(Restrictions.eq(Leavebalance.PROP_LB_EMP_NO + "."
                    + Employee.PROP_EMP_DEPT_NO, new Department(lb_Bean.getDepNo())));
        }

        page.splitPage(detachedCriteria);
        result = this.lb_DAO.findByCriteria(detachedCriteria, page.getPageSize(), page
                .getCurrentPage());

        List otList = getOtForwardOfYear(lb_Bean.getYear() + 1);

        BigDecimal lbTiaoxiuForward = null;
        for (Leavebalance lb : result) {
            lbTiaoxiuForward = (lb.getLbTiaoxiuForward() != null) ? lb.getLbTiaoxiuForward()
                    : new BigDecimal(0);
            lb.setLbTiaoxiuCurrent(getCurrEmpTiaoxiuOt(lb.getLbEmpNo().getId(), otList));
            lb.setLbTiaoxiuYearCanUse(lbTiaoxiuForward.add(lb.getLbTiaoxiuCurrent()));
        }

        return result;
    }

    public List<Employee> getLeavebalanceList(LeaveBalanceSearchBean lb_Bean, Pager page,
            List<Overtimerequest> otList) {
        List result = null;

        if ((lb_Bean.getDepNo() != null) && (lb_Bean.getDepNo().trim().length() > 0)
                && (this.dp_BO.loadDepartmentByNo(lb_Bean.getDepNo(), new String[0]) == null)) {
            return null;
        }

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.setFetchMode(Employee.PROP_EMP_DEPT_NO, FetchMode.JOIN);

        if (lb_Bean.getYear() >= Calendar.getInstance().get(1)) {
            detachedCriteria.add(Restrictions.eq(Employee.PROP_EMP_STATUS, Integer.valueOf(1)));
        }
        if ((lb_Bean.getEmp() != null) && (lb_Bean.getEmp().trim().length() > 0)) {
            detachedCriteria.add(Restrictions.or(Restrictions.ilike(Employee.PROP_EMP_DISTINCT_NO,
                                                                    "%" + lb_Bean.getEmp() + "%"),
                                                 Restrictions.ilike(Employee.PROP_EMP_NAME, "%"
                                                         + lb_Bean.getEmp() + "%")));
        }
        if ((lb_Bean.getDepNo() != null) && (lb_Bean.getDepNo().trim().length() > 0)) {
            detachedCriteria.add(Restrictions.eq(Employee.PROP_EMP_DEPT_NO, new Department(lb_Bean
                    .getDepNo())));
        }
        detachedCriteria.addOrder(Order.asc(Employee.PROP_ID));

        page.splitPage(detachedCriteria);
        result = this.e_DAO.findByCriteria(detachedCriteria, page.getPageSize(), page
                .getCurrentPage());
        DetachedCriteria detachedCriteria_lb = DetachedCriteria.forClass(Leavebalance.class);
        detachedCriteria.setFetchMode(Leavebalance.PROP_LB_EMP_NO, FetchMode.JOIN);
        if (lb_Bean.getYear() > 0) {
            detachedCriteria_lb.add(Restrictions.eq(Leavebalance.PROP_LB_YEAR, ""
                    + lb_Bean.getYear()));
        }
        detachedCriteria_lb.addOrder(Order.asc(Leavebalance.PROP_LB_EMP_NO));
        List lb_temp = this.lb_DAO.findByCriteria(detachedCriteria_lb);

        int j = 0;
        for (int i = 0; i < result.size(); ++i) {
            if (j >= lb_temp.size()) {
                ((Employee) result.get(i)).setEmpleaveblances(null);
                ++j;
            } else if (((Employee) result.get(i)).getId().compareTo(
                                                                    ((Leavebalance) lb_temp.get(j))
                                                                            .getLbEmpNo().getId()) > 0) {
                ++j;
                --i;
            } else if (((Employee) result.get(i)).getId().compareTo(
                                                                    ((Leavebalance) lb_temp.get(j))
                                                                            .getLbEmpNo().getId()) < 0) {
                ((Employee) result.get(i)).setEmpleaveblances(null);
            } else {
                Set lbSet = new HashSet();
                Leavebalance lb = (Leavebalance) lb_temp.get(j);

                lb.setLbTiaoxiuCurrent(getCurrEmpTiaoxiuOt(lb.getLbEmpNo().getId(), otList));

                if (lb.getLbTiaoxiuForward() == null) {
                    lb.setLbTiaoxiuForward(new BigDecimal(0));
                }
                lb.setLbTiaoxiuYearCanUse(lb.getLbTiaoxiuCurrent().add(lb.getLbTiaoxiuForward()));

                lbSet.add(lb);
                ((Employee) result.get(i)).setEmpleaveblances(lbSet);
                ++j;
            }
        }
        return result;
    }

    public List<Employee> getEmpWithLB(Leavebalance leaveBalance, Pager page) {
        DetachedCriteria dc = createDCEmp_LB(leaveBalance);
        List<Leavebalance> lbList;
        if (page != null) {
            page.splitPage(dc);
            lbList = this.e_DAO.findByCriteria(dc, page.getPageSize(), page.getCurrentPage());
        } else {
            lbList = this.e_DAO.findByCriteria(dc);
        }

        if (lbList.size() == 0)
            return null;

        List resultEmpList = new ArrayList(lbList.size());
        for (Leavebalance bal : lbList) {
            Employee emp = bal.getLbEmpNo();
            emp.setLeaveBalance(bal);
            resultEmpList.add(emp);
        }
        return resultEmpList;
    }

    public List<Employee> getEmpWithoutLB(Leavebalance leaveBalance, Pager page) {
        DetachedCriteria dc = createDCEmp(leaveBalance);
        List<Employee> empList = this.e_DAO.findByCriteria(dc);

        dc = createDCEmp_LB(leaveBalance);
        List<Leavebalance> lbList = this.e_DAO.findByCriteria(dc);

        if (empList.size() == 0)
            return empList;

        Map lbMap = new HashMap();
        if (lbList.size() > 0) {
            for (Leavebalance lb : lbList) {
                String empId = lb.getLbEmpNo().getId();
                lbMap.put(empId, empId);
            }
        }

        Employee emp = null;
        int count = 0;
        Iterator iter = empList.iterator();
        while (iter.hasNext()) {
            emp = (Employee) iter.next();
            if (lbMap.get(emp.getId()) != null)
                iter.remove();
            ++count;
        }

        if (page == null)
            return empList;
        if (count == 0)
            return empList;

        String[] empIds = new String[count];
        count = 0;
        for (Employee empTemp : empList) {
            empIds[count] = empTemp.getId();
            ++count;
        }

        dc = createDCEmp(leaveBalance);
        dc.add(Restrictions.in(Employee.PROP_ID, empIds));

        page.splitPage(dc);
        empList = this.e_DAO.findByCriteria(dc, page.getPageSize(), page.getCurrentPage());
        return empList;
    }

    private DetachedCriteria createDCEmp_LB(Leavebalance leaveBalance) {
        DetachedCriteria dc = DetachedCriteria.forClass(Leavebalance.class);
        dc.createAlias(Leavebalance.PROP_LB_EMP_NO, "emp", 1);

        dc.add(Restrictions.eq(Leavebalance.PROP_LB_YEAR, leaveBalance.getLbYear()));
        dc.add(Restrictions.eq(Leavebalance.PROP_LB_LEAVE_TYPE, leaveBalance.getLbLeaveType()));

        Date startDate = DateUtil.getYearFirstDay(Integer.parseInt(leaveBalance.getLbYear()));
        Date endDate = DateUtil.getYearEndDay(Integer.parseInt(leaveBalance.getLbYear()));

        dc.add(Restrictions.le("emp." + Employee.PROP_EMP_JOIN_DATE, endDate));
        dc.add(Restrictions.or(Restrictions.eq("emp." + Employee.PROP_EMP_STATUS, Integer
                .valueOf(1)), Restrictions.and(Restrictions.eq("emp." + Employee.PROP_EMP_STATUS,
                                                               Integer.valueOf(0)), Restrictions
                .ge("emp." + Employee.PROP_EMP_TERMINATE_DATE, startDate))));

        Employee emp = leaveBalance.getLbEmpNo();
        if (emp != null) {
            BaseCrit.addEmpDC(dc, "emp", emp.getEmpNoName());
            BaseCrit.addDeptDC(dc, "emp." + Employee.PROP_EMP_DEPT_NO, "emp."
                    + Employee.PROP_EMP_PB_NO, null, emp.getEmpDeptNo());
            BaseCrit.addDC(dc, "emp." + Employee.PROP_EMP_LOCATION_NO, Location.PROP_ID,
                           new Object[] { emp.getEmpLocationNo() });
        }

        if ((leaveBalance.getLbStatus().intValue() == 1)
                || (leaveBalance.getLbStatus().intValue() == 2)
                || (leaveBalance.getLbStatus().intValue() == 9)) {
            dc.add(Restrictions.eq(Leavebalance.PROP_LB_STATUS, leaveBalance.getLbStatus()));
        }

        return dc;
    }

    private DetachedCriteria createDCEmp(Leavebalance leaveBalance) {
        DetachedCriteria dc = DetachedCriteria.forClass(Employee.class);

        Date startDate = DateUtil.getYearFirstDay(Integer.parseInt(leaveBalance.getLbYear()));
        Date endDate = DateUtil.getYearEndDay(Integer.parseInt(leaveBalance.getLbYear()));

        dc.add(Restrictions.le(Employee.PROP_EMP_JOIN_DATE, endDate));
        dc.add(Restrictions.or(Restrictions.eq(Employee.PROP_EMP_STATUS, Integer.valueOf(1)),
                               Restrictions.and(Restrictions.eq(Employee.PROP_EMP_STATUS, Integer
                                       .valueOf(0)), Restrictions
                                       .ge(Employee.PROP_EMP_TERMINATE_DATE, startDate))));

        Employee emp = leaveBalance.getLbEmpNo();
        if (emp != null) {
            BaseCrit.addEmpDC(dc, null, emp.getEmpNoName());
            BaseCrit.addDeptDC(dc, Employee.PROP_EMP_DEPT_NO, Employee.PROP_EMP_PB_NO, null, emp
                    .getEmpDeptNo());
            BaseCrit.addDC(dc, Employee.PROP_EMP_LOCATION_NO, Location.PROP_ID, new Object[] { emp
                    .getEmpLocationNo() });
        }

        return dc;
    }

    public List<String> delLeavebalanceByYearAndEmp(Leavebalance lb) {
        List result = null;
        Leavebalance old_lb = null;
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Leavebalance.class);
        detachedCriteria.add(Restrictions.eq(Leavebalance.PROP_LB_EMP_NO, lb.getLbEmpNo()));
        detachedCriteria.add(Restrictions.eq(Leavebalance.PROP_LB_YEAR, lb.getLbYear()));
        List tempList = this.lb_DAO.findByCriteria(detachedCriteria);
        if (tempList.size() > 0)
            old_lb = (Leavebalance) tempList.get(0);
        try {
            if (old_lb != null)
                this.lb_DAO.deleteObject(old_lb);
        } catch (Exception e) {
            result = new ArrayList();
            result.add(e.getMessage());
            return result;
        }
        return null;
    }

    public List<String> exeLeaveBalanceInit(String[] empIds, Integer[] ltSpecialCat, String year)
  {
    if ((empIds == null) || (empIds.length == 0) || (ltSpecialCat == null) || (ltSpecialCat.length == 0)) {
      return new ArrayList(0);
    }

    Map<Integer, Leavetype> typeMap = getLeaveTypeSpecial(ltSpecialCat);
    BigDecimal hourPerDay = new BigDecimal(8);

    int currentYear = Integer.valueOf(year).intValue();
    int lastYear = currentYear - 1;

    Map lbLastYearMap = getLeaveBalanceMap(empIds, lastYear);
    Map annualLastYearMap = getLeaveRequestMap(empIds, lastYear, (Leavetype)typeMap.get(Integer.valueOf(1)));
    Map txLastYearMap = getLeaveRequestMap(empIds, lastYear, (Leavetype)typeMap.get(Integer.valueOf(10)));
    Map txOTLastYearMap = getOTTiaoXiuMap(empIds, lastYear);

    List errors = new ArrayList();
    List leaveBalanceList = new ArrayList();

    IEmployeeBo empBo = (IEmployeeBo)SpringBeanFactory.getBean("empBo");
    List<Employee> emps = empBo.searchEmpArray(empIds);

    String yearMonth = year + "01";
    InterpreterExecuteContext context = new InterpreterExecuteContext(yearMonth);
    
    Leavetype leavetype;
    for (Integer specialCat : ltSpecialCat) {
      leavetype = (Leavetype)typeMap.get(specialCat);
      if (leavetype == null)
      {
        continue;
      }

      for (Employee emp : emps) {
        try {
          Leavebalance leavebalance = context.execute(leavetype, emp);
          leavebalance.setLbEmpNo(emp);
          leavebalance.setLbLeaveType(specialCat);
          leavebalance.setLbYear(year);
          leavebalance.setLbStatus(Integer.valueOf(1));

          if ((DateUtil.getYear(emp.getEmpJoinDate()) == currentYear) && (leavetype.getLtReleaseMethod() != null))
          {
            leavebalance.setLbReleaseStartDate(emp.getEmpJoinDate());
          }

          if (leavetype.getLtSpecialCat().intValue() == 1) {
            List balanceList = (List)lbLastYearMap.get(emp.getId());
            if (balanceList != null)
            {
              List lrList = (List)annualLastYearMap.get(emp.getId());
              Leavebalance lb = getBalanceFromList(balanceList, Integer.valueOf(1));
              BigDecimal balance = getLastYearBalance(lrList, leavetype, lb);
              BigDecimal release = getReleaseHours(lb, hourPerDay.doubleValue());
              BigDecimal totalLr = sumLRTotalHours(lrList);
              leavebalance.setLbBalForwardHour(balance.add(release).subtract(totalLr));
              leavebalance.setLbBalForwardDay(leavebalance.getLbBalForwardHour().divide(hourPerDay));
            }
            leaveBalanceList.add(leavebalance);
          }

          if (leavetype.getLtSpecialCat().intValue() == 10) {
            List balanceList = (List)lbLastYearMap.get(emp.getId());
            if (balanceList != null) {
              List lrList = (List)txLastYearMap.get(emp.getId());
              Leavebalance lb = getBalanceFromList(balanceList, Integer.valueOf(10));
              BigDecimal balance = getLastYearBalance(lrList, leavetype, lb);
              BigDecimal release = (BigDecimal)txOTLastYearMap.get(emp.getId());
              BigDecimal totalLr = sumLRTotalHours(lrList);
              leavebalance.setLbBalForwardHour(balance.subtract(totalLr));
              leavebalance.setLbBalForwardDay(leavebalance.getLbBalForwardHour().divide(hourPerDay));
              if (release != null) {
                leavebalance.setLbBalForwardHour(leavebalance.getLbBalForwardHour().add(release));
                leavebalance.setLbBalForwardDay(leavebalance.getLbBalForwardHour().divide(hourPerDay));
              }
            }
            leaveBalanceList.add(leavebalance);
          }

          if (leavetype.getLtSpecialCat().intValue() == 5)
            leaveBalanceList.add(leavebalance);
        }
        catch (InterpreterException e)
        {
          errors.add(e.getMessage());
        }
      }
    }
    if (!errors.isEmpty()) {
      return errors;
    }

    String hql = "delete from Leavebalance where lbLeaveType in" + buildInCondition(ltSpecialCat) + " and lbEmpNo.id in" + buildInCondition(empIds) + " and lbYear='" + year + "'";

    this.lb_DAO.exeHql(hql);

    this.lb_DAO.saveOrupdate(leaveBalanceList);
    return new ArrayList(0);
  }

    private BigDecimal sumLRTotalHours(List<Leaverequest> list) {
        BigDecimal result = new BigDecimal(0);
        if (list == null) {
            return result;
        }

        for (Leaverequest leave : list) {
            result = result.add(leave.getLrTotalHours());
        }
        return result;
    }

    private Leavebalance getBalanceFromList(List<Leavebalance> list, Integer leaveType) {
        for (Leavebalance lb : list) {
            if (lb.getLbLeaveType().equals(leaveType)) {
                return lb;
            }
        }
        return null;
    }

    private Map<String, List<Leaverequest>> getLeaveRequestMap(String[] empIds, int year,
            Leavetype leaveType) {
        if (leaveType == null) {
            return new HashMap(0);
        }
        Date startDate = DateUtil.getYearFirstDay(year);
        Date endDate = DateUtil.getNextYearFirstDay(year);
        DetachedCriteria dc = DetachedCriteria.forClass(Leaverequest.class);
        dc.add(Restrictions.in(Leaverequest.PROP_LR_EMP_NO + ".id", empIds));
        dc.add(Restrictions.or(Restrictions.and(Restrictions.ge(Leaverequest.PROP_LR_START_DATE,
                                                                startDate), Restrictions
                .lt(Leaverequest.PROP_LR_START_DATE, endDate)), Restrictions.and(Restrictions
                .le(Leaverequest.PROP_LR_END_DATE, endDate), Restrictions
                .gt(Leaverequest.PROP_LR_END_DATE, startDate))));

        dc.add(Restrictions.eq(Leaverequest.PROP_LR_LT_NO, leaveType));
        dc.addOrder(Order.asc(Leaverequest.PROP_LR_EMP_NO));
        List lrList = this.lb_DAO.findByCriteria(dc);
        if (lrList.size() == 0)
            return new HashMap(0);

        Map resultMap = new HashMap();
        String emp = ((Leaverequest) lrList.get(0)).getLrEmpNo().getId();
        List resultList = new ArrayList();
        resultList.add(lrList.get(0));

        for (int i = 1; i < lrList.size(); ++i) {
            if (emp.equals(((Leaverequest) lrList.get(i)).getLrEmpNo().getId())) {
                resultList.add(lrList.get(i));
            } else {
                resultMap.put(emp, resultList);
                emp = ((Leaverequest) lrList.get(i)).getLrEmpNo().getId();
                resultList.clear();
                resultList.add(lrList.get(i));
            }
        }

        resultMap.put(emp, resultList);
        return resultMap;
    }

    private BigDecimal getLastYearBalance(List<Leaverequest> annualLRList, Leavetype lt,
            Leavebalance lb) {
        if ((annualLRList == null) || (lb == null)) {
            return new BigDecimal(0);
        }

        LrDataCheckImpl check = (LrDataCheckImpl) SpringBeanFactory.getBean("lrDataCheck");
        double hourPerDay = ExaminDateUtil.getDefaultHoursPerDay();

        Double balFwdHours = check.getBalFwdHours(lb, hourPerDay);
        Double wastedHours;
        if (lb.getLbBalEndDate() == null) {
            wastedHours = new Double(0.0D);
        } else {
            double[] allHoursArr = check
                    .getLeaveHoursBeforAfter(annualLRList, lb.getLbBalEndDate());
            wastedHours = Double.valueOf((allHoursArr[0] < balFwdHours.doubleValue()) ? balFwdHours
                    .doubleValue()
                    - allHoursArr[0] : new Double(0.0D).doubleValue());
        }

        return new BigDecimal(balFwdHours.doubleValue() - wastedHours.doubleValue());
    }

    private BigDecimal getReleaseHours(Leavebalance lb, double hourPerDay) {
        if (lb == null) {
            return new BigDecimal(0);
        }
        LrDataCheckImpl check = (LrDataCheckImpl) SpringBeanFactory.getBean("lrDataCheck");
        return new BigDecimal(check.getCurrYearHours(lb, hourPerDay).doubleValue());
    }

    private Map<String, List<Leavebalance>> getLeaveBalanceMap(String[] empIds, int year) {
        Integer[] leaveTypes = { Integer.valueOf(1), Integer.valueOf(10) };

        DetachedCriteria dc = DetachedCriteria.forClass(Leavebalance.class);
        dc.add(Restrictions.eq(Leavebalance.PROP_LB_YEAR, String.valueOf(year)));
        dc.add(Restrictions.in(Leavebalance.PROP_LB_EMP_NO + ".id", empIds));
        dc.add(Restrictions.in(Leavebalance.PROP_LB_LEAVE_TYPE, leaveTypes));
        dc.addOrder(Order.asc(Leavebalance.PROP_LB_EMP_NO));
        List lbList = this.lb_DAO.findByCriteria(dc);
        if (lbList.size() == 0)
            return new HashMap(0);

        Map resultMap = new HashMap();
        String emp = ((Leavebalance) lbList.get(0)).getLbEmpNo().getId();
        List resultList = new ArrayList();
        resultList.add(lbList.get(0));

        for (int i = 1; i < lbList.size(); ++i) {
            if (emp.equals(((Leavebalance) lbList.get(i)).getLbEmpNo().getId())) {
                resultList.add(lbList.get(i));
            } else {
                resultMap.put(emp, resultList);
                emp = ((Leavebalance) lbList.get(i)).getLbEmpNo().getId();
                resultList.clear();
                resultList.add(lbList.get(i));
            }
        }

        resultMap.put(emp, resultList);
        return resultMap;
    }

    private Map<String, BigDecimal> getOTTiaoXiuMap(String[] empIds, int year) {
        Integer[] otStatus = { Integer.valueOf(15), Integer.valueOf(16) };
        Date startDate = DateUtil.getYearFirstDay(year);
        Date endDate = DateUtil.getNextYearFirstDay(year);

        DetachedCriteria dc = DetachedCriteria.forClass(Overtimerequest.class);
        dc.add(Restrictions.in(Overtimerequest.PROP_OR_EMP_NO + ".id", empIds));
        dc.add(Restrictions.gt(Overtimerequest.PROP_OR_TIAOXIU_HOURS, new BigDecimal(0)));
        dc.add(Restrictions.ge(Overtimerequest.PROP_OR_START_DATE, startDate));
        dc.add(Restrictions.le(Overtimerequest.PROP_OR_END_DATE, endDate));
        dc.add(Restrictions.in(Overtimerequest.PROP_OR_STATUS, otStatus));
        List otList = this.lb_DAO.findByCriteria(dc);
        if (otList.size() == 0)
            return new HashMap(0);

        Map resultMap = new HashMap();
        String emp = ((Overtimerequest) otList.get(0)).getOrEmpNo().getId();
        BigDecimal resultHours = ((Overtimerequest) otList.get(0)).getOrTiaoxiuHours();

        for (int i = 1; i < otList.size(); ++i) {
            if (emp.equals(((Overtimerequest) otList.get(i)).getOrEmpNo().getId())) {
                resultHours.add(((Overtimerequest) otList.get(i)).getOrTiaoxiuHours());
            } else {
                resultMap.put(emp, resultHours);
                emp = ((Overtimerequest) otList.get(i)).getOrEmpNo().getId();
                resultHours.equals(((Overtimerequest) otList.get(i)).getOrTiaoxiuHours());
            }
        }

        resultMap.put(emp, resultHours);
        return resultMap;
    }

    public List<String> exeDeleteLeaveBalance(String[] empIds, Integer[] leaveType, String year) {
        if ((empIds == null) || (empIds.length == 0)) {
            return new ArrayList(0);
        }

        StringBuffer buffer = new StringBuffer("delete from Leavebalance where lbEmpNo.id in ");
        buffer.append(buildInCondition(empIds));
        buffer.append(" and lbLeaveType in ");
        buffer.append(buildInCondition(leaveType));
        buffer.append(" and lbYear = '" + year + "'");
        this.lb_DAO.exeHql(buffer.toString());

        String lastYear = String.valueOf(Integer.valueOf(year).intValue() - 1);
        String lastYearHql = "update Leavebalance set lbStatus=2 where lbEmpNo.id in "
                + buildInCondition(empIds) + " and lbLeaveType in " + buildInCondition(leaveType)
                + " and lbYear = '" + lastYear + "'";

        this.lb_DAO.exeHql(lastYearHql);

        return new ArrayList(0);
    }

    public List<String> exeDeleteLeaveBalance(String[] recordIds) {
        if ((recordIds == null) || (recordIds.length == 0)) {
            return new ArrayList(0);
        }

        String hql = "select lbEmpNo.id,lbYear,lbLeaveType from Leavebalance where lbId in"
                + buildInCondition(recordIds);
        List list = this.lb_DAO.exeHqlList(hql);

        int size = list.size();
        String year = null;
        Integer leaveType = null;
        String[] empIds = new String[size];
        for (int i = 0; i < size; ++i) {
            empIds[i] = ((Object[]) (Object[]) list.get(i))[0].toString();
            year = ((Object[]) (Object[]) list.get(i))[1].toString();
            leaveType = (Integer) ((Object[]) (Object[]) list.get(i))[2];
        }

        List result = exeDeleteLeaveBalance(empIds, new Integer[] { leaveType }, year);
        return result;
    }

    private Map<Integer, Leavetype> getLeaveTypeSpecial(Integer[] ltSpecialCat) {
        DetachedCriteria dc = DetachedCriteria.forClass(Leavetype.class);
        dc.add(Restrictions.in("ltSpecialCat", ltSpecialCat));
        List<Leavetype> leaveTypes = this.lb_DAO.findByCriteria(dc);
        Map typeMap = new HashMap();
        for (Leavetype lt : leaveTypes) {
            typeMap.put(lt.getLtSpecialCat(), lt);
        }
        return typeMap;
    }

    public List<String> exeSubmitLeaveBalance(String[] empIds, Integer[] leaveType, String year) {
        if ((empIds == null) || (empIds.length == 0)) {
            return new ArrayList(0);
        }

        String thisYearHql = "update Leavebalance set lbStatus=2 where lbEmpNo.id in "
                + buildInCondition(empIds) + " and lbLeaveType in " + buildInCondition(leaveType)
                + " and lbYear = '" + year + "'";

        this.lb_DAO.exeHql(thisYearHql);

        String lastYear = String.valueOf(Integer.valueOf(year).intValue() - 1);
        String lastYearHql = "update Leavebalance set lbStatus=9 where lbEmpNo.id in "
                + buildInCondition(empIds) + " and lbLeaveType in " + buildInCondition(leaveType)
                + " and lbYear = '" + lastYear + "'";

        this.lb_DAO.exeHql(lastYearHql);

        return new ArrayList(0);
    }

    public List<String> exeSubmitLeaveBalance(String[] recordIds) {
        if ((recordIds == null) || (recordIds.length == 0)) {
            return new ArrayList(0);
        }

        String hql = "select lbEmpNo.id,lbYear,lbLeaveType from Leavebalance where lbId in"
                + buildInCondition(recordIds);
        List list = this.lb_DAO.exeHqlList(hql);

        int size = list.size();
        String year = null;
        Integer leaveType = null;
        String[] empIds = new String[size];
        for (int i = 0; i < size; ++i) {
            empIds[i] = ((Object[]) (Object[]) list.get(i))[0].toString();
            year = ((Object[]) (Object[]) list.get(i))[1].toString();
            leaveType = (Integer) ((Object[]) (Object[]) list.get(i))[2];
        }

        List result = exeSubmitLeaveBalance(empIds, new Integer[] { leaveType }, year);
        return result;
    }

    private String buildInCondition(Integer[] ids) {
        StringBuffer buffer = new StringBuffer(2 * ids.length + 2);
        buffer.append("(");
        int size = ids.length;
        for (int i = 0; i < size; ++i) {
            buffer.append(ids[i]);
            if (i != size - 1) {
                buffer.append(",");
            }
        }
        buffer.append(")");
        return buffer.toString();
    }

    private String buildInCondition(String[] empIds) {
        StringBuffer buffer = new StringBuffer(39 * empIds.length + 2);
        buffer.append("(");
        int size = empIds.length;
        for (int i = 0; i < size; ++i) {
            buffer.append("'").append(empIds[i]).append("'");
            if (i != size - 1) {
                buffer.append(",");
            }
        }
        buffer.append(")");
        return buffer.toString();
    }

    public BigDecimal[] getYearLeaveToYearCanuse(String empid, int year) {
        ILeavetypeBO lt_BO = (ILeavetypeBO) SpringBeanFactory.getBean("leavetypeBO");
        Leavetype lt = lt_BO.getAnnualLeave();
        if (lt == null) {
            return new BigDecimal[] { new BigDecimal(0), new BigDecimal(0) };
        }

        ILeavebalanceBO lb_BO = (ILeavebalanceBO) SpringBeanFactory.getBean("leavebalanceBO");
        Leavebalance lb = lb_BO.getLeavebalance(new Employee(empid), String.valueOf(year), lt);
        if ((lb == null) || (lb.getLbStatus().intValue() != 2)) {
            return new BigDecimal[] { new BigDecimal(0), new BigDecimal(0) };
        }

        Leaverequest tempLr = new Leaverequest();
        Employee emp = new Employee(empid);
        tempLr.setLrEmpNo(emp);
        tempLr.setLrLtNo(lt);
        int[] statusSet1 = { 15, 16 };
        Date start = DateUtil.getYearFirstDay(year);
        Date end = DateUtil.getYearFirstDay(year + 1);
        LrDataCheckImpl lrDataCheck = (LrDataCheckImpl) SpringBeanFactory.getBean("lrDataCheck");
        List allYearLeaveList = lrDataCheck.getTotalStatusSetList(tempLr, start, end, statusSet1);

        Date balEndDate = (lb.getLbBalEndDate() != null) ? lb.getLbBalEndDate() : DateUtil
                .getYearFirstDay(9999);
        double[] twoValue = lrDataCheck.getLeaveHoursBeforAfter(allYearLeaveList, balEndDate);
        double hourOfDay = ExaminDateUtil.getDefaultHoursPerDay();
        double before = twoValue[0] / hourOfDay;
        double after = twoValue[1] / hourOfDay;

        BigDecimal[] result = new BigDecimal[2];

        if (DateUtil.dateDiff(new Date(), balEndDate, 5) >= 0) {
            result[0] = formatBigDecimal(lb.getLbHoursOfYear())
                    .add(formatBigDecimal(lb.getLbBalForwardHour()))
                    .subtract(new BigDecimal(before));
            result[1] = formatBigDecimal(lb.getLbDaysOfYear()).add(
                                                                   formatBigDecimal(lb
                                                                           .getLbBalForwardDay()))
                    .subtract(new BigDecimal(twoValue[0]));
            return result;
        }

        if (new BigDecimal(before).compareTo(formatBigDecimal(lb.getLbBalForwardDay())) > 0) {
            result[0] = formatBigDecimal(lb.getLbHoursOfYear())
                    .add(formatBigDecimal(lb.getLbBalForwardHour()))
                    .subtract(new BigDecimal(before)).subtract(new BigDecimal(after));

            result[1] = formatBigDecimal(lb.getLbDaysOfYear()).add(
                                                                   formatBigDecimal(lb
                                                                           .getLbBalForwardDay()))
                    .subtract(new BigDecimal(twoValue[0])).subtract(new BigDecimal(twoValue[1]));

            return result;
        }
        return new BigDecimal[] { new BigDecimal(0), new BigDecimal(0) };
    }

    public List getAllYears() {
        Calendar date = Calendar.getInstance();
        Integer nextyear = Integer.valueOf(date.get(1) + 1);
        Integer currentyear = Integer.valueOf(date.get(1));
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Leavebalance.class);
        List year = this.lb_DAO.findByCriteria(detachedCriteria.setProjection(Projections
                .distinct(Projections.min("lbYear"))));
        List years = new LinkedList();
        String temp = (String) year.get(0);
        if ((temp == null) || ("".equals(temp))) {
            temp = currentyear + "";
        }
        Integer init = Integer.valueOf(MyTools.parseNumber(temp, Integer.class).intValue());
        year.clear();
        Integer localInteger1;
        Integer localInteger2;
        for (Integer i = init; i.intValue() <= nextyear.intValue(); localInteger2 = i = Integer
                .valueOf(i.intValue() + 1)) {
            years.add(i);

            localInteger1 = i;
        }

        return years;
    }

    public ILeavebalanceDAO getLb_DAO() {
        return this.lb_DAO;
    }

    public void setLb_DAO(ILeavebalanceDAO lb_DAO) {
        this.lb_DAO = lb_DAO;
    }

    public IEmployeeDAO getE_DAO() {
        return this.e_DAO;
    }

    public void setE_DAO(IEmployeeDAO e_dao) {
        this.e_DAO = e_dao;
    }

    public IDepartmentBO getDp_BO() {
        return this.dp_BO;
    }

    public void setDp_BO(IDepartmentBO dp_BO) {
        this.dp_BO = dp_BO;
    }

    public ILeaverequestBO getLr_BO() {
        return this.lr_BO;
    }

    public void setLr_BO(ILeaverequestBO lr_BO) {
        this.lr_BO = lr_BO;
    }

    public List<Leavebalance> getLeavebalanceForwardOfYear(int yearCurr) {
        List result = null;
        int year = yearCurr - 1;
        DetachedCriteria dc = DetachedCriteria.forClass(Leavebalance.class);
        dc.setFetchMode(Leavebalance.PROP_LB_EMP_NO, FetchMode.JOIN);
        dc.add(Restrictions.eq(Leavebalance.PROP_LB_YEAR, "" + year));
        result = this.lb_DAO.findByCriteria(dc);
        return result;
    }

    public List<Overtimerequest> getOtForwardOfYear(int yearCurr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        List result = null;
        int year = yearCurr - 1;
        Date start = null;
        Date end = null;
        Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(2, 0);
        cal.set(5, 1);
        try {
            start = format.parse(format.format(cal.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        cal.add(1, 1);
        try {
            end = format.parse(format.format(cal.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Integer[] status = { Integer.valueOf(15), Integer.valueOf(16) };
        DetachedCriteria dc = DetachedCriteria.forClass(Overtimerequest.class);
        dc.setFetchMode(Overtimerequest.PROP_OR_EMP_NO, FetchMode.JOIN);
        dc.add(Restrictions.in(Overtimerequest.PROP_OR_STATUS, status));
        dc.add(Restrictions.isNotNull("orTiaoxiuHours"));
        dc.add(Restrictions.ge("orTiaoxiuHours", new BigDecimal(0)));
        dc.add(Restrictions.ge("orStartDate", start));
        dc.add(Restrictions.le("orStartDate", end));

        result = this.lb_DAO.findByCriteria(dc);
        return result;
    }

    public List<Leaverequest> getLrForwardOfYear(int yearCurr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        List result = null;
        int year = yearCurr - 1;
        Date start = null;
        Date end = null;
        Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(2, 0);
        cal.set(5, 1);
        try {
            start = format.parse(format.format(cal.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        cal.add(1, 1);
        try {
            end = format.parse(format.format(cal.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Leavetype tiaoxiuType = null;
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Leavetype.class);
        detachedCriteria.add(Restrictions.like(Leavetype.PROP_LT_NAME, "调休"));
        List ltList = this.lb_DAO.findByCriteria(detachedCriteria);
        tiaoxiuType = (ltList != null) ? (Leavetype) ltList.get(0) : null;

        Integer[] status = { Integer.valueOf(15), Integer.valueOf(16) };
        DetachedCriteria detachedCriteria1 = DetachedCriteria.forClass(Leaverequest.class);
        detachedCriteria1.setFetchMode(Leaverequest.PROP_LR_EMP_NO, FetchMode.JOIN);
        detachedCriteria1.add(Restrictions.in(Leaverequest.PROP_LR_STATUS, status));
        detachedCriteria1.add(Restrictions.eq(Leaverequest.PROP_LR_LT_NO, tiaoxiuType));
        detachedCriteria1.add(Restrictions.ge("lrStartDate", start));
        detachedCriteria1.add(Restrictions.le("lrStartDate", end));

        result = this.lb_DAO.findByCriteria(detachedCriteria1);
        return result;
    }

    public BigDecimal getCurrEmpTiaoxiuRemind(String empId, List<Leavebalance> lbList) {
        BigDecimal tiaoxiuRemind = new BigDecimal(0);
        for (Leavebalance lb : lbList) {
            if ((lb != null) && (empId != null) && (empId.equals(lb.getLbEmpNo().getId()))) {
                tiaoxiuRemind = lb.getLbTiaoxiuForward();
                break;
            }
        }
        if (tiaoxiuRemind == null) {
            tiaoxiuRemind = new BigDecimal(0);
        }

        return tiaoxiuRemind;
    }

    public BigDecimal getCurrEmpTiaoxiuOt(String empId, List<Overtimerequest> orList) {
        BigDecimal tiaoxiuOt = new BigDecimal(0);
        for (Overtimerequest or : orList) {
            if ((or != null) && (empId != null) && (empId.equals(or.getOrEmpNo().getId()))) {
                tiaoxiuOt = tiaoxiuOt.add(or.getOrTiaoxiuHours());
            }
        }
        return tiaoxiuOt;
    }

    public BigDecimal getCurrEmpTiaoxiuLR(String empId, List<Leaverequest> lrList) {
        BigDecimal tiaoxiuLr = new BigDecimal(0);
        for (Leaverequest lr : lrList) {
            if ((lr != null) && (empId != null) && (empId.equals(lr.getLrEmpNo().getId()))) {
                tiaoxiuLr = tiaoxiuLr.add(lr.getLrTotalHours());
            }
        }

        return tiaoxiuLr;
    }

    public List<LeaveReportBean> doSummaryEmpLeaveRequest(String empId, String leaveTypeId,
            String year) {
        if (StringUtils.isEmpty(year)) {
            return new ArrayList(0);
        }
        List emps = getEmps(empId);
        if (emps.isEmpty()) {
            return new ArrayList(0);
        }
        List<Leavetype> leaveTypes = getLeavetTypes(leaveTypeId);
        if (leaveTypes.isEmpty()) {
            return new ArrayList(0);
        }

        Map resultMap = getReportBeanMap(emps, leaveTypes);

        List lrList = getLeaveRequest(emps, leaveTypes, year);
        Map lrMap = injectLeaveRequestData(lrList, resultMap);

        Map specialMap = new HashMap();
        Set catIds = new HashSet();
        for (Leavetype lt : leaveTypes) {
            specialMap.put(lt.getLtSpecialCat(), lt);
            catIds.add(lt.getLtSpecialCat());
        }

        Map tiaoxiuMap = new HashMap();
        if (catIds.contains(Integer.valueOf(10))) {
            String[] empIds = getEmpIds(emps);
            tiaoxiuMap = getOTTiaoXiuMap(empIds, Integer.valueOf(year).intValue());
        }

        List balanceList = getLeaveBalance(emps, catIds, year);
        injectLeavebalanceData(specialMap, balanceList, resultMap, lrMap, tiaoxiuMap);

        List result = new ArrayList();
        result.addAll(resultMap.values());
        return result;
    }

    private String[] getEmpIds(List<Employee> empList) {
        String[] result = new String[empList.size()];
        for (int i = 0; i < empList.size(); ++i) {
            result[i] = ((Employee) empList.get(i)).getId();
        }
        return result;
    }

    private void injectLeavebalanceData(Map<Integer, Leavetype> specialMap,
            List<Leavebalance> balanceList, Map<String, LeaveReportBean> resultMap,
            Map<String, List<Leaverequest>> lrMap, Map<String, BigDecimal> tiaoxiuMap) {
        Double hourPerday = Double.valueOf(ExaminDateUtil.getDefaultHoursPerDay());
        BigDecimal hourPerdayDecimal = new BigDecimal(hourPerday.doubleValue());

        for (Leavebalance balance : balanceList) {
            int spType = balance.getLbLeaveType().intValue();
            if (spType == 20) {
                continue;
            }

            String key = balance.getLbEmpNo().getId() + "_"
                    + ((Leavetype) specialMap.get(balance.getLbLeaveType())).getLtNo();
            LeaveReportBean bean = (LeaveReportBean) resultMap.get(key);

            if (spType == 1) {
                BigDecimal[] wasted = getWasted((List) lrMap.get(key), balance, hourPerday
                        .doubleValue());

                bean.setUsableDays(formatBigDecimal(balance.getLbDaysOfYear())
                        .add(formatBigDecimal(balance.getLbBalForwardDay())));
                bean.setUsableHours(formatBigDecimal(balance.getLbHoursOfYear())
                        .add(formatBigDecimal(balance.getLbBalForwardHour())));

                bean.setYearEndUsableDays(bean.getUsableDays().subtract(bean.getUsedDays())
                        .subtract(bean.getApprovingDays()).subtract(wasted[1]));
                bean.setYearEndUsableHours(bean.getUsableHours().subtract(bean.getUsedHours())
                        .subtract(bean.getApprovingHours()).subtract(wasted[0]));
            }

            if (spType == 10) {
                BigDecimal[] wasted = getWasted((List) lrMap.get(key), balance, hourPerday
                        .doubleValue());

                BigDecimal release = (BigDecimal) tiaoxiuMap.get(balance.getLbEmpNo().getId());
                bean.setUsableDays(formatBigDecimal(release.divide(hourPerdayDecimal))
                        .add(formatBigDecimal(balance.getLbBalForwardDay())));
                bean.setUsableHours(formatBigDecimal(release).add(
                                                                  formatBigDecimal(balance
                                                                          .getLbBalForwardHour())));

                bean.setYearEndUsableDays(bean.getUsableDays().subtract(bean.getUsedDays())
                        .subtract(bean.getApprovingDays()).subtract(wasted[1]));
                bean.setYearEndUsableHours(bean.getUsableHours().subtract(bean.getUsedHours())
                        .subtract(bean.getApprovingHours()).subtract(wasted[0]));
            }
        }
    }

    private BigDecimal[] getWasted(List<Leaverequest> lrList, Leavebalance leavebalance,
            double hourPerDay) {
        if (lrList == null) {
            return new BigDecimal[] { new BigDecimal(0), new BigDecimal(0) };
        }

        LrDataCheckImpl check = (LrDataCheckImpl) SpringBeanFactory.getBean("lrDataCheck");
        Double balFwdHours = check.getBalFwdHours(leavebalance, hourPerDay);
        Double wastedHours;
        if (leavebalance.getLbBalEndDate() == null) {
            wastedHours = new Double(0.0D);
        } else {
            double[] allHoursArr = check.getLeaveHoursBeforAfter(lrList, leavebalance
                    .getLbBalEndDate());
            wastedHours = Double.valueOf((allHoursArr[0] < balFwdHours.doubleValue()) ? balFwdHours
                    .doubleValue()
                    - allHoursArr[0] : new Double(0.0D).doubleValue());
        }
        BigDecimal[] rs = new BigDecimal[2];
        rs[0] = new BigDecimal(wastedHours.doubleValue());
        rs[1] = new BigDecimal(wastedHours.doubleValue() / hourPerDay);
        return rs;
    }

    private Map<String, List<Leaverequest>> injectLeaveRequestData(List<Leaverequest> lrList,
            Map<String, LeaveReportBean> resultMap) {
        Map lrMap = new HashMap();
        for (Leaverequest lr : lrList) {
            String key = lr.getLrEmpNo().getId() + "_" + lr.getLrLtNo().getLtNo();

            List empLrList = (List) lrMap.get(key);
            if (empLrList == null) {
                empLrList = new ArrayList();
            }
            empLrList.add(lr);
            lrMap.put(key, empLrList);

            LeaveReportBean bean = (LeaveReportBean) resultMap.get(key);

            if ((lr.getLrStatus().intValue() == 15) || (lr.getLrStatus().intValue() == 16)) {
                bean.setUsedDays(bean.getUsedDays().add(formatBigDecimal(lr.getLrTotalDays())));
                bean.setUsedHours(bean.getUsedHours().add(formatBigDecimal(lr.getLrTotalHours())));
            }

            if ((lr.getLrStatus().intValue() != 21) && (lr.getLrStatus().intValue() != 22)) {
                bean.setApprovingDays(bean.getApprovingDays().add(
                                                                  formatBigDecimal(lr
                                                                          .getLrTotalDays())));
                bean.setApprovingHours(bean.getApprovingHours().add(
                                                                    formatBigDecimal(lr
                                                                            .getLrTotalHours())));
            }
        }

        return lrMap;
    }

    private Map<String, LeaveReportBean> getReportBeanMap(List<Employee> emps,
            List<Leavetype> leaveTypes) {
        Map specialCatMap = getLeaveTypeMap();

        Map resultMap = new HashMap();
        for (Iterator i$ = emps.iterator(); i$.hasNext();) {
            Employee emp = (Employee) i$.next();
            for (Leavetype lt : leaveTypes) {
                String key = emp.getId() + "_" + lt.getLtNo();

                LeaveReportBean bean = new LeaveReportBean();
                bean.setLeaveTypeCategory(lt.getLtSpecialCat());
                bean.setLeaveTypeCategoryName((String) specialCatMap.get(bean
                        .getLeaveTypeCategory()));
                bean.setLeaveTypeId(lt.getLtNo());
                bean.setLeaveTypeName(lt.getLtName());
                bean.setEmpId(emp.getId());
                bean.setEmpName(emp.getEmpName());

                resultMap.put(key, bean);
            }
        }

        Employee emp;
        return resultMap;
    }

    private List<Employee> getEmps(String empid) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.add(Restrictions.eq(Employee.PROP_EMP_STATUS, Integer.valueOf(1)));
        if (StringUtils.isNotEmpty(empid)) {
            detachedCriteria.add(Restrictions.eq(Employee.PROP_ID, empid));
        }
        return this.e_DAO.findByCriteria(detachedCriteria);
    }

    private List<Leavetype> getLeavetTypes(String leaveTypeId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Leavetype.class);
        if (StringUtils.isNotEmpty(leaveTypeId)) {
            detachedCriteria.add(Restrictions.eq(Leavetype.PROP_LT_NO, leaveTypeId));
        }
        return this.e_DAO.findByCriteria(detachedCriteria);
    }

    private BigDecimal formatBigDecimal(BigDecimal bigDecimal) {
        return BigDecimalUtils.convertToZeroIfNull(bigDecimal);
    }

    private Map<Integer, String> getLeaveTypeMap() {
        Map result = new HashMap();
        result.put(Integer.valueOf(0), "事假");
        result.put(Integer.valueOf(1), "年假");
        result.put(Integer.valueOf(3), "病假");
        result.put(Integer.valueOf(4), "病假住院");
        result.put(Integer.valueOf(5), "带薪病假");
        result.put(Integer.valueOf(6), "婚假");
        result.put(Integer.valueOf(7), "产假");
        result.put(Integer.valueOf(8), "出差");
        result.put(Integer.valueOf(9), "因公外出");
        result.put(Integer.valueOf(10), "调休(不过期)");
        result.put(Integer.valueOf(11), "调休(过期)");
        result.put(Integer.valueOf(20), "其他");
        return result;
    }

    private List<Leavebalance> getLeaveBalance(List<Employee> empId, Set<Integer> leaveTypeIds,
            String year) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Leavebalance.class);
        detachedCriteria.setFetchMode(Leavebalance.PROP_LB_EMP_NO, FetchMode.JOIN);
        detachedCriteria.add(Restrictions.in(Leavebalance.PROP_LB_EMP_NO, empId));
        if (!leaveTypeIds.isEmpty()) {
            detachedCriteria.add(Restrictions.in(Leavebalance.PROP_LB_LEAVE_TYPE, leaveTypeIds));
        }
        detachedCriteria.add(Restrictions.eq(Leavebalance.PROP_LB_YEAR, year));
        List result = this.lb_DAO.findByCriteria(detachedCriteria);
        return result;
    }

    private List<Leaverequest> getLeaveRequest(List<Employee> empId, List<Leavetype> leaveTypeId,
            String year) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Leaverequest.class);
        detachedCriteria.setFetchMode(Leaverequest.PROP_LR_LT_NO, FetchMode.JOIN);
        detachedCriteria.setFetchMode(Leaverequest.PROP_LR_EMP_NO, FetchMode.JOIN);
        int intYear = Integer.valueOf(year).intValue();
        Date startDate = DateUtil.getYearFirstDay(intYear);
        Date endDate = DateUtil.getYearFirstDay(intYear + 1);
        detachedCriteria.add(Restrictions.ge(Leaverequest.PROP_LR_START_DATE, startDate));
        detachedCriteria.add(Restrictions.lt(Leaverequest.PROP_LR_END_DATE, endDate));
        detachedCriteria.add(Restrictions.in(Leaverequest.PROP_LR_EMP_NO, empId));
        detachedCriteria.add(Restrictions.in(Leaverequest.PROP_LR_LT_NO, leaveTypeId));
        List lrList = this.lb_DAO.findByCriteria(detachedCriteria);
        return lrList;
    }

    public Leavebalance getLeaveBalanceById(String id) {
        return (Leavebalance) this.lb_DAO.loadObject(Leavebalance.class, id, null, new boolean[0]);
    }

    public void updateLeaveBalance(Leavebalance leavebalance) {
        this.lb_DAO.updateObject(leavebalance);
    }

    public void deleteLeaveBalance(String id) {
        this.lb_DAO.deleteObject(Leavebalance.class, id);
    }

    public List<Leavebalance> getEmpLBOfYear(List<Employee> empList, Integer leaveType, String year) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Leavebalance.class);
        detachedCriteria.add(Restrictions.in(Leavebalance.PROP_LB_EMP_NO, empList));
        detachedCriteria.add(Restrictions.eq(Leavebalance.PROP_LB_LEAVE_TYPE, leaveType));
        detachedCriteria.add(Restrictions.eq(Leavebalance.PROP_LB_YEAR, year));
        List result = this.lb_DAO.findByCriteria(detachedCriteria);
        return result;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.bo.LeavebalanceBOImpl JD-Core Version: 0.5.4
 */