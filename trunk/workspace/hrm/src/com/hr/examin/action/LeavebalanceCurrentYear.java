package com.hr.examin.action;

import com.hr.configuration.bo.ILocationBO;
import com.hr.configuration.domain.Location;
import com.hr.examin.action.beans.ExaminSearchBean;
import com.hr.examin.bo.ExaminDateUtil;
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
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DateUtil;
import com.hr.util.Pager;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class LeavebalanceCurrentYear extends EmpExaminAction {
    private static final long serialVersionUID = 1L;
    private Pager page;
    private List<Location> locationList;
    private List<Employee> result;
    private List<Employee> empList;
    private Leavebalance leaveBalance;
    private String updateIDs;
    private String specialIds;
    private String searchType;

    public LeavebalanceCurrentYear() {
        this.page = new Pager();

        this.searchType = "1";
    }

    public String execute() throws Exception {
        if (this.leaveBalance == null) {
            this.leaveBalance = new Leavebalance();
            this.leaveBalance.setLbYear(String.valueOf(Calendar.getInstance().get(1)));
            this.leaveBalance.setLbLeaveType(Integer.valueOf(1));
            this.leaveBalance.setLbStatus(Integer.valueOf(0));
        }

        ILocationBO locationBO = (ILocationBO) SpringBeanFactory.getBean("locationBO");
        this.locationList = locationBO.FindAllLocation();

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        this.empList = empBo.findAllActiveEmployees(this.leaveBalance.getLbYear());

        ILeavebalanceBO lb_BO = (ILeavebalanceBO) getBean("leavebalanceBO");
        if (this.searchType.equals("1")) {
            this.result = lb_BO.getEmpWithLB(this.leaveBalance, this.page);
        } else {
            this.leaveBalance.setLbStatus(Integer.valueOf(1));
            this.result = lb_BO.getEmpWithoutLB(this.leaveBalance, this.page);
        }

        return "success";
    }

    public String initLeaveBalance() {
        if (StringUtils.isEmpty(this.updateIDs)) {
            addActionError("请选择要进行初始化的员工记录!");
            return "success";
        }
        if (StringUtils.isEmpty(this.specialIds)) {
            addActionError("请选择要初始化的假期种类!");
            return "success";
        }
        if ((this.leaveBalance == null) || (StringUtils.isEmpty(this.leaveBalance.getLbYear()))) {
            addActionError("请选择要进行初始化的年份");
            return "success";
        }

        ILeavebalanceBO lb_BO = (ILeavebalanceBO) getBean("leavebalanceBO");
        String[] empIds = this.updateIDs.split(",");
        Integer[] ltSpecialCat = converToIntegerArray(this.specialIds);
        String year = this.leaveBalance.getLbYear();
        List result = lb_BO.exeLeaveBalanceInit(empIds, ltSpecialCat, year);
        if (!result.isEmpty()) {
            addErrorInfo(result);
            return "success";
        }

        addSuccessInfo(year + "年的假期初始化成功！");
        return "success";
    }

    private Integer[] converToIntegerArray(String str) {
        String[] strArr = str.split(",");
        Integer[] intArr = new Integer[strArr.length];
        for (int i = 0; i < strArr.length; ++i) {
            intArr[i] = Integer.valueOf(strArr[i]);
        }
        return intArr;
    }

    public String doSubmitLeaveBalance() {
        if (StringUtils.isEmpty(this.updateIDs)) {
            addActionError("请选择要进行提交的休假记录!");
            return "success";
        }
        String[] recordIds = this.updateIDs.split(",");
        ILeavebalanceBO leaveBalanceBo = (ILeavebalanceBO) getBean("leavebalanceBO");
        List result = leaveBalanceBo.exeSubmitLeaveBalance(recordIds);

        if (!result.isEmpty()) {
            addErrorInfo(result);
            return "success";
        }
        addSuccessInfo("操作成功!");
        return "success";
    }

    public String doDeleteLeaveBalance() {
        if (StringUtils.isEmpty(this.updateIDs)) {
            addActionError("请选择要进行审核的休假记录!");
            return "success";
        }

        ILeavebalanceBO leaveBalanceBo = (ILeavebalanceBO) getBean("leavebalanceBO");
        String[] recordIds = this.updateIDs.split(",");
        List result = leaveBalanceBo.exeDeleteLeaveBalance(recordIds);

        if (!result.isEmpty()) {
            addErrorInfo(result);
            return "success";
        }
        addSuccessInfo("操作成功!");
        return "success";
    }

    public List getEmpLtInfo(String year, String empId, int ltSpecialCat, int dayOrHour) {
        int currentYear = Integer.parseInt(year);
        Date yearBeginDate = DateUtil.getYearFirstDay(currentYear);
        Date nextYearBeginDate = DateUtil.getNextYearFirstDay(currentYear);
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        Employee currEmp = empBo.loadEmp(empId, null);

        IAttendshiftBO attendBo = (IAttendshiftBO) getBean("attendshiftBO");
        Attendshift defaultAs = attendBo.getDefaultAttendshiftByEmp(currEmp);
        double hoursPerDay;
        if ((currEmp.getEmpShiftType().intValue() == 0)
                || (currEmp.getEmpShiftType().intValue() == 2))
            hoursPerDay = defaultAs.getAttsWorkingHour().doubleValue();
        else {
            hoursPerDay = ExaminDateUtil.getDefaultHoursPerDay();
        }

        ExaminSearchBean es_Bean = new ExaminSearchBean();
        ILeavetypeBO lt_BO = (ILeavetypeBO) getBean("leavetypeBO");
        Leavetype lt = lt_BO.getTiaoxiuLeavetype(Integer.valueOf(ltSpecialCat));

        ILeaverequestBO lr_BO = (ILeaverequestBO) getBean("leaverequestBO");

        int[] statusSet = { 2, 5, 14, 15, 16 };

        List dataList = lr_BO.getTotalStatusSetList(currEmp, yearBeginDate, nextYearBeginDate, lt,
                                                    statusSet);

        BigDecimal zero = new BigDecimal(0);
        BigDecimal[] values = new BigDecimal[4];
        Date today = DateUtil.parseDateByFormat(DateUtil.formatDate(new Date()), "yyyy-MM-dd");

        if ((dataList == null) || (dataList.size() == 0)) {
            ILeavebalanceBO lbBO = (ILeavebalanceBO) getBean("leavebalanceBO");
            Leavebalance lb = lbBO.getLeavebalance(new Employee(empId), year, lt);

            if (dayOrHour == 0) {
                BigDecimal dayForward = (lb.getLbBalForwardDay() != null) ? lb.getLbBalForwardDay()
                        : zero;
                BigDecimal dayYear = (lb.getLbDaysOfYear() != null) ? lb.getLbDaysOfYear() : zero;
                if ((lb.getLbBalEndDate() == null) || (today.compareTo(lb.getLbBalEndDate()) >= 0))
                    values[0] = dayForward.add(dayYear);
                else {
                    values[0] = dayYear;
                }
                values[1] = zero;
                values[2] = values[0];
                values[3] = zero;
            } else {
                BigDecimal hourForward = (lb.getLbBalForwardHour() != null) ? lb
                        .getLbBalForwardHour() : zero;
                BigDecimal hourYear = (lb.getLbHoursOfYear() != null) ? lb.getLbHoursOfYear()
                        : zero;
                if ((lb.getLbBalEndDate() == null) || (today.compareTo(lb.getLbBalEndDate()) <= 0))
                    values[0] = hourForward.add(hourYear);
                else {
                    values[0] = hourYear;
                }
                values[1] = new BigDecimal(0);
                values[2] = values[0];
                values[3] = new BigDecimal(1);
            }
        } else {
            Leaverequest lr = (Leaverequest) dataList.get(dataList.size() - 1);
            lr.setHoursPerDay(hoursPerDay);
            LrDataCheckImpl lrAddCheckData = (LrDataCheckImpl) getBean("lrDataCheck");
            String info = lrAddCheckData.validateLRConflict(lr);

            if (dayOrHour == 0) {
                values[0] = lr.getUseableDays();
                values[1] = lr.getUsedDays();
                values[2] = lr.getRemainDays();
                values[3] = zero;
            } else {
                values[0] = lr.getUseableHours();
                values[1] = lr.getUsedDays();
                values[2] = lr.getRemainHours();
                values[3] = new BigDecimal(1);
            }

        }

        dataList.add(values);
        return dataList;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public List<Location> getLocationList() {
        return this.locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public List<Employee> getResult() {
        return this.result;
    }

    public void setResult(List<Employee> result) {
        this.result = result;
    }

    public Leavebalance getLeaveBalance() {
        return this.leaveBalance;
    }

    public void setLeaveBalance(Leavebalance leaveBalance) {
        this.leaveBalance = leaveBalance;
    }

    public String getUpdateIDs() {
        return this.updateIDs;
    }

    public void setUpdateIDs(String updateIDs) {
        this.updateIDs = updateIDs;
    }

    public String getSpecialIds() {
        return this.specialIds;
    }

    public void setSpecialIds(String specialIds) {
        this.specialIds = specialIds;
    }

    public List<Employee> getEmpList() {
        return this.empList;
    }

    public void setEmpList(List<Employee> empList) {
        this.empList = empList;
    }

    public String getSearchType() {
        return this.searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
}